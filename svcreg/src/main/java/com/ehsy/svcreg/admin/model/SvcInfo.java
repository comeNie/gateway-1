package com.ehsy.svcreg.admin.model;

/**
 * Created by ehsy_it on 2016/7/5.
 */
public class SvcInfo {

    private String datacenter;

    private String node;

    private String serviceId;

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
