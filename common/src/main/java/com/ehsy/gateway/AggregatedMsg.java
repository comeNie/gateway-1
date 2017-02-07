package com.ehsy.gateway;

/**
 * Created by ehsy_it on 2016/6/15.
 */
public class AggregatedMsg extends ComponentMsg {
    private String namespace;
    private String degrade;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDegrade() {
        return degrade;
    }

    public void setDegrade(String degrade) {
        this.degrade = degrade;
    }
}
