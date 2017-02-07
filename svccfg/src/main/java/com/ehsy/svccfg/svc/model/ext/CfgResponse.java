package com.ehsy.svccfg.svc.model.ext;

import com.ehsy.gateway.ComplexModel;

import java.util.List;

/**
 * Created by ehsy_it on 2016/7/7.
 */
public class CfgResponse {
    private List<ComplexModel> responseMsg;

    public List<ComplexModel> getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(List<ComplexModel> responseMsg) {
        this.responseMsg = responseMsg;
    }
}
