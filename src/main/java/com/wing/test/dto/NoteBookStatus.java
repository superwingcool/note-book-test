package com.wing.test.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

public enum NoteBookStatus {
    NORMAL("NORMAL");

    private String value;
    NoteBookStatus(String value){
        this.value = value;
    }

    public static Optional<NoteBookStatus> getNotBookStatusByName(String name){
        return Arrays.stream(NoteBookStatus.values()).filter(status -> name.equals(status.value)).findFirst();
    }

    @JsonValue
    public String getValue(){
        return value;
    }

}
