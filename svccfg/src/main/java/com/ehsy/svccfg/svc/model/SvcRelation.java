package com.ehsy.svccfg.svc.model;

import java.util.Date;

public class SvcRelation {

    //类型：1：代理；2：聚合
    public static final String SVC_RELATION_TYPE_PROVIDER = "1";

    //类型：1：代理；2：聚合
    public static final String Svc_Relation_type_AGGREGATION = "2";

    private String id;

    private String svcCodeMaster;

    private String svcCodeBranch;

    private String degrade;

    private String type;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSvcCodeMaster() {
        return svcCodeMaster;
    }

    public void setSvcCodeMaster(String svcCodeMaster) {
        this.svcCodeMaster = svcCodeMaster == null ? null : svcCodeMaster.trim();
    }

    public String getSvcCodeBranch() {
        return svcCodeBranch;
    }

    public void setSvcCodeBranch(String svcCodeBranch) {
        this.svcCodeBranch = svcCodeBranch == null ? null : svcCodeBranch.trim();
    }

    public String getDegrade() {
        return degrade;
    }

    public void setDegrade(String degrade) {
        this.degrade = degrade == null ? null : degrade.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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