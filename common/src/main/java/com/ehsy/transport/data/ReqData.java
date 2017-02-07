package com.ehsy.transport.data;

import com.ehsy.transport.enums.SvcReqType;

public class ReqData <T> {
    private String contentType;
    private SvcReqType svcReqType;
    private T content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public SvcReqType getSvcReqType() {
        return svcReqType;
    }

    public void setSvcReqType(SvcReqType svcReqType) {
        this.svcReqType = svcReqType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
