package com.ehsy.gateway;

/**
 * 代理对象
 * Created by ehsy_it on 2016/6/7.
 */
public class ProxyMsg extends ComponentMsg {
    private String proxiedService;

    public String getProxiedService() {
        return proxiedService;
    }

    public void setProxiedService(String proxiedService) {
        this.proxiedService = proxiedService;
    }

}
