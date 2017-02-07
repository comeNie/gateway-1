package com.ehsy.svccfg.model;

import com.ehsy.gateway.ComponentMsg;

import java.util.Date;

public class SvcDescInfo {
    /**
     * 状态：0：停用中；1：启用中
     */
    public static final String SVC_DESC_STATUS_RUNNING = "1";
    /**
     * 状态：0：停用中；1：启用中
     */
    public static final String SVC_DESC_STATUS_STOP = "0";

    public static final String SVC_DESC_CATEGORY_COM = "组件服务";

    private String id;

    private String code;

    private String alias;

    private String svcname;

    private String version;

    private String component;

    private String status;

    private String category;

    private String protocol;

    private String path;

    private Date createTime;

    private Date updateTime;

    private ComponentMsg componentMsg;

    public ComponentMsg getComponentMsg() {
        return componentMsg;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setComponentMsg(ComponentMsg componentMsg) {
        this.componentMsg = componentMsg;
    }

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSvcname() {
        return svcname;
    }

    public void setSvcname(String svcname) {
        this.svcname = svcname == null ? null : svcname.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}