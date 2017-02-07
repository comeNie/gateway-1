package com.ehsy.common.msg.request;

/**
 * Created by ehsy_it on 2016/6/7.
 */
public class RequestHeader {
    private String serviceName;
    private String version;
    private String requestTime;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
