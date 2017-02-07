package com.ehsy.common.msg.response;

/**
 * Created by zhuangmg on 6/15/16.
 */
public class ResponseCommon {
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
