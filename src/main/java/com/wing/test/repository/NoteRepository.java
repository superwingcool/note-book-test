package com.wing.test.repository;

import com.wing.test.dto.NoteBookStatus;
import com.wing.test.entity.Note;
import com.wing.test.entity.NoteBook;
import com.wing.test.repository.extention.PutExtension;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class NoteRepository {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    public Note saveOrUpdate(Note note) {
        return hbaseTemplate.execute(Note.TABLE_NAME, (table) -> {
            String rowKey = note.getRowKeyTime();
            PutExtension put = new PutExtension(Bytes.toBytes(rowKey));
            put.build(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_NAME, note.getName())
                    .build(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_CREATE_DATE, note.getCreateDate())
                    .build(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_UPDATE_DATE, note.getUpdateDate())
                    .build(Note.COLUMN_FAMILY_CONTENT_INFO, Note.COLUMN_CONTENT, note.getContent());
            table.put(put);
            return note;
        });
    }

    public List<Note> findNoteBasicInfos(String rowKey) {
        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(rowKey + "_*"));
        scan.setFilter(filter);
        scan.addFamily(Note.COLUMN_FAMILY_BASIC_INFO);
        return hbaseTemplate.find(Note.TABLE_NAME, scan, new ResultsExtractor<List<Note>>() {
            @Override
            public List<Note> extractData(ResultScanner resultScanner) throws Exception {
                List<Note> notes = new ArrayList();
                Iterator<Result> results = resultScanner.iterator();
                while(results.hasNext()) {
                    Result result = results.next();
                    String name = new String(result.getValue(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_NAME));
                    String createTime = Bytes.toString(result.getValue(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_CREATE_DATE));
                    String updateTime = Bytes.toString(result.getValue(Note.COLUMN_FAMILY_BASIC_INFO, Note.COLUMN_UPDATE_DATE));
                    Note note = new Note(name,
                            Long.valueOf(createTime),
                            Long.valueOf(updateTime));
                    note.setRowKey(Bytes.toString(result.getRow()));
                    notes.add(note);
                }
                return notes;
            }
        });
    }

    public String getNoteContent(String rowKey) {
        return hbaseTemplate.get(Note.TABLE_NAME, rowKey, "content_info", new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                return Bytes.toString(result.getValue(Bytes.toBytes("content_info"), Bytes.toBytes("content")));
            }
        });
    }


    public String getNoteName(String rowKey) {
        return hbaseTemplate.get(Note.TABLE_NAME, rowKey, "basic_info", new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                return Bytes.toString(result.getValue(Bytes.toBytes("basic_info"), Bytes.toBytes("name")));
            }
        });
    }



}
