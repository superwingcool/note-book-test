package com.wing.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class HBaseEntity{

    protected static final String ROW_KEY_APPEND = "_";

    private String rowKey;

    public String getRowKey() {
        return rowKey;
    }

    @JsonIgnore
    public String getRowKeyTime() {
        if(rowKey.indexOf(ROW_KEY_APPEND) != -1) {
            return rowKey;
        }
        return rowKey + ROW_KEY_APPEND + getCurrentTimeMillis();
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @JsonIgnore
    protected Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


}