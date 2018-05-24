package com.wing.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.util.Bytes;

@Data
@NoArgsConstructor
public class Note extends HBaseEntity {

    public static final String TABLE_NAME = "note";

    public static final byte[] COLUMN_FAMILY_BASIC_INFO = Bytes.toBytes("basic_info");
    public static final byte[] COLUMN_FAMILY_CONTENT_INFO = Bytes.toBytes("content_info");

    public static final byte[] COLUMN_NAME = Bytes.toBytes("name");
    public static final byte[] COLUMN_CREATE_DATE = Bytes.toBytes("createDate");
    public static final byte[] COLUMN_UPDATE_DATE = Bytes.toBytes("updateDate");
    public static final byte[] COLUMN_CONTENT= Bytes.toBytes("content");

    private String name;

    private Long createDate;

    private Long updateDate;

    private String content;


    public Note(String name, Long createDate, Long updateDate) {
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
