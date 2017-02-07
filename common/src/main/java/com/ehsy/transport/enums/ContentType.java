package com.ehsy.transport.enums;

public enum ContentType {
    XML("text/xml"),
    JSON("application/json");

    private String value;

    ContentType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
