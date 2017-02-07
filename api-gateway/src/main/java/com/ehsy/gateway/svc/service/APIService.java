package com.ehsy.gateway.svc.service;

import com.ehsy.common.msg.response.ResponseMsg;

/**
 * Created by zhuangmg on 6/16/16.
 */
public interface APIService {

    ResponseMsg<?> doService(final String requestMsg, String svcDesc);

}
