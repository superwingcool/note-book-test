package com.wing.test.repository;

import com.wing.test.dto.NoteBookStatus;
import com.wing.test.entity.NoteBook;
import com.wing.test.repository.extention.PutExtension;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.stereotype.Repository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class NoteBookRepository {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    public NoteBook saveOrUpdate(NoteBook noteBook) {
        return hbaseTemplate.execute(NoteBook.TABLE_NAME, (table) -> {
            String rowKey = noteBook.getRowKeyTime();
            PutExtension put = new PutExtension(Bytes.toBytes(rowKey));
            put.build(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_NAME, noteBook.getName())
                    .build(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_CREATE_DATE, noteBook.getCreateDate())
                    .build(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_UPDATE_DATE, noteBook.getUpdateDate())
                    .build(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_NOTE_BOOK_STATUS, noteBook.getNoteBookStatus());
            table.put(put);
            return noteBook;
        });
    }

    public void deleteNoteBook(String rowKey) {
        hbaseTemplate.execute(NoteBook.TABLE_NAME, (table) -> {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
            return null;
        });
    }

    public List<NoteBook> findNoteBooksByRowKey(String rowKey) {
        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(rowKey + "_*"));
        scan.setFilter(filter);
        return hbaseTemplate.find(NoteBook.TABLE_NAME, scan, new ResultsExtractor<List<NoteBook>>() {
            @Override
            public List<NoteBook> extractData(ResultScanner resultScanner) throws Exception {
                List<NoteBook> noteBooks = new ArrayList();
                Iterator<Result> results = resultScanner.iterator();
                while(results.hasNext()) {
                    Result result = results.next();
                    String name = new String(result.getValue(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_NAME));
                    String createTime = Bytes.toString(result.getValue(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_CREATE_DATE));
                    String updateTime = Bytes.toString(result.getValue(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_UPDATE_DATE));
                    String status = Bytes.toString(result.getValue(NoteBook.COLUMN_FAMILY_NOTE_BOOK_INFO, NoteBook.COLUMN_NOTE_BOOK_STATUS));
                    NoteBook noteBook = new NoteBook(name,
                            Long.valueOf(createTime),
                            Long.valueOf(updateTime),
                            NoteBookStatus.getNotBookStatusByName(status).get());
                    noteBook.setRowKey(Bytes.toString(result.getRow()));
                    noteBooks.add(noteBook);
                }
                return noteBooks;
            }
        });
    }
}
