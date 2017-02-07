package com.ehsy.svccfg.svc.model;

import java.util.Date;

public class SvcAccess {
    private String id;

    private String compId;

    private String compAbb;

    private String svrId;

    private String svrCode;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId == null ? null : compId.trim();
    }

    public String getCompAbb() {
        return compAbb;
    }

    public void setCompAbb(String compAbb) {
        this.compAbb = compAbb == null ? null : compAbb.trim();
    }

    public String getSvrId() {
        return svrId;
    }

    public void setSvrId(String svrId) {
        this.svrId = svrId == null ? null : svrId.trim();
    }

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode == null ? null : svrCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}