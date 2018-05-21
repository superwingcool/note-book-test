package com.wing.test.entity;

import com.wing.test.dto.NoteBookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBook extends HBaseEntity implements Serializable {

    public static final String TABLE_NAME = "note_book";

    public static final byte[] COLUMN_FAMILY_NOTE_BOOK_INFO = Bytes.toBytes("note_book_info");

    public static final byte[] COLUMN_NAME = Bytes.toBytes("name");
    public static final byte[] COLUMN_CREATE_DATE = Bytes.toBytes("createDate");
    public static final byte[] COLUMN_UPDATE_DATE = Bytes.toBytes("updateDate");
    public static final byte[] COLUMN_NOTE_BOOK_STATUS = Bytes.toBytes("noteBookStatus");

    private String name;

    private Long createDate;

    private Long updateDate;

    private NoteBookStatus noteBookStatus;

}
