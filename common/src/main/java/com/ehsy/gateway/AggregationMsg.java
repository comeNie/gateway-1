package com.ehsy.gateway;

import java.util.List;

/**
 * Created by ehsy_it on 2016/6/15.
 */
public class AggregationMsg extends BaseMsg {
    private List<AggregatedMsg> serviceList;

    public List<AggregatedMsg> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<AggregatedMsg> serviceList) {
        this.serviceList = serviceList;
    }
}
