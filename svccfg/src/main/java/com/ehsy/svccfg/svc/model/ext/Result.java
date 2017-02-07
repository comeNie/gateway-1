package com.ehsy.svccfg.svc.model.ext;

import com.alibaba.fastjson.JSON;

/**
 * Created by ehsy_it on 2016/7/9.
 */
public class Result {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    private String flg;
    private String msg;
    private String url;

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
