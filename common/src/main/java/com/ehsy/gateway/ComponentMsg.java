package com.ehsy.gateway;

import java.util.List;

/**
 * 组件服务对象
 * Created by ehsy_it on 2016/6/7.
 */
public class ComponentMsg extends BaseMsg{
    private List<ComplexModel> requestMsg;
    private List<ComplexModel> responseMsg;

    public List<ComplexModel> getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(List<ComplexModel> requestMsg) {
        this.requestMsg = requestMsg;
    }

    public List<ComplexModel> getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(List<ComplexModel> responseMsg) {
        this.responseMsg = responseMsg;
    }
}
