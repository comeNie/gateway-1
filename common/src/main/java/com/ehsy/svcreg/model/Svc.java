package com.ehsy.svcreg.model;

/**
 * Created by zhuangmg on 5/31/16.
 */
public class Svc {

    private String svcName;

    private String host;

    private Integer port;

    private String version;

    private String component;

    private String protocol;

    private String path;

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("服务[").append(svcName).append("]");
        sb.append("地址[").append(host).append("]");
        sb.append("端口[").append(port).append("]");
        sb.append("组件[").append(component).append("]");
        sb.append("路径[").append(path).append("]");
        return sb.toString();
    }
}
