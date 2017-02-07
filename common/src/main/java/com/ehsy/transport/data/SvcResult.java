package com.ehsy.transport.data;

import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public class SvcResult<R> {
    private boolean success;
    private String respCode;
    private String respMsg;
    private Map<String, R> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Map<String, R> getData() {
        return data;
    }

    public void setData(Map<String, R> data) {
        this.data = data;
    }
}
