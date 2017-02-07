package com.ehsy.common.msg.response;

import java.util.Map;

/**
 * Created by zhuangmg on 6/15/16.
 */
public class ResponseMsg <T> {
    private ResponseHeader header;
    private ResponseCommon common;
    protected Map<String, T> body;

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public ResponseCommon getCommon() {
        return common;
    }

    public void setCommon(ResponseCommon common) {
        this.common = common;
    }

    public Map<String, T> getBody() {
        return body;
    }

    public void setBody(Map<String, T> body) {
        this.body = body;
    }
}
