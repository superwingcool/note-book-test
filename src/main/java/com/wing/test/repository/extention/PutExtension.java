package com.wing.test.repository.extention;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class PutExtension extends Put {

    public PutExtension(byte[] row) {
        super(row);
    }

    public PutExtension build(byte[] columnFamilyName, byte[] paramName, Object param) throws IOException {
        if (param != null) {
            this.addColumn(columnFamilyName, paramName, Bytes.toBytes(param.toString()));
        }
        return this;
    }
}