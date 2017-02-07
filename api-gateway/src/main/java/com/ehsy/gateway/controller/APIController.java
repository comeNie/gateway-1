package com.ehsy.gateway.controller;

import com.ehsy.common.controller.BaseController;
import com.ehsy.common.enums.MsgType;
import com.ehsy.common.enums.ResultCode;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.swap.SwapAreaUtils;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.gateway.svc.cfg.SvcCfgCache;
import com.ehsy.gateway.svc.parser.SvcMsgParser;
import com.ehsy.gateway.svc.service.APIService;
import com.ehsy.gateway.svc.service.APIServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private APIServiceFactory apiServiceFactory;

    @RequestMapping(value = "/api")
    @ResponseBody
    public ResponseEntity<ResponseMsg> doService(@RequestBody String msg) {

        LOG.debug("{}", msg);

        RequestMsg requestMsg = SwapAreaUtils.getRequestMsg(MsgType.IN);

        String serviceName = requestMsg.getHeader().getServiceName();

        String desc = SvcCfgCache.getSvcCfg(serviceName);

        String category = SvcMsgParser.getCategory(desc);

        APIService apiService = this.apiServiceFactory.getService(category);

        ResponseMsg responseMsg = apiService.doService(msg, desc);

        if(responseMsg == null){
            responseMsg = MsgUtils.newFailedResponseMsg(ResultCode.SVC_ERROR_500);
        }

        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }
}