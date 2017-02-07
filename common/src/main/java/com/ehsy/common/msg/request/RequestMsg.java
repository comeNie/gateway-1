package com.ehsy.common.msg.request;

import java.util.Map;

/**
 * 请求对象
 * Created by ehsy_it on 2016/6/7.
 */
public class RequestMsg<T> {
    protected RequestHeader header;
    protected RequestCommon common;
    protected RequestFile file;
    protected Map<String, T> body;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public RequestCommon getCommon() {
        return common;
    }

    public void setCommon(RequestCommon common) {
        this.common = common;
    }

    public RequestFile getFile() {
        return file;
    }

    public void setFile(RequestFile file) {
        this.file = file;
    }

    public Map<String, T> getBody() {
        return body;
    }

    public void setBody(Map<String, T> body) {
        this.body = body;
    }
}
