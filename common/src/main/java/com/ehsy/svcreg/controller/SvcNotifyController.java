package com.ehsy.svcreg.controller;

import com.ehsy.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SvcNotifyController {

    private static final Logger LOG = LoggerFactory.getLogger(SvcNotifyController.class);

    /**
     * 可用服务变更通知
     * @param notifyInfo
     * @return
     */
    @RequestMapping(value="/svc/notify")
    @ResponseBody
    public String svcNotify(@RequestBody String notifyInfo){

        LOG.debug("{}", notifyInfo);

        return Constants.SUCCESS;
    }

}