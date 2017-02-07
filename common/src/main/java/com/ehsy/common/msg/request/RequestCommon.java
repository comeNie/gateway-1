package com.ehsy.common.msg.request;

/**
 * Created by ehsy_it on 2016/6/7.
 */
public class RequestCommon {
    private String appID;
    private String nonceStr;
    private String sign;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
