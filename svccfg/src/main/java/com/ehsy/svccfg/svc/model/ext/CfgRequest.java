package com.ehsy.svccfg.svc.model.ext;

import com.ehsy.gateway.ComplexModel;

import java.util.List;

/**
 * Created by ehsy_it on 2016/7/7.
 */
public class CfgRequest {
    private List<ComplexModel> requestMsg;

    public List<ComplexModel> getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(List<ComplexModel> requestMsg) {
        this.requestMsg = requestMsg;
    }
}
