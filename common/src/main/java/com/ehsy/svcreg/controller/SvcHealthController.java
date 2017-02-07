package com.ehsy.svcreg.controller;

import com.ehsy.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SvcHealthController {

    private static final Logger LOG = LoggerFactory.getLogger(SvcHealthController.class);

    /**
     * 应用服务心跳检测
     * @return
     */
    @RequestMapping(value="/health")
    @ResponseBody
    public String check(){

        LOG.debug("health check");

        return Constants.SUCCESS;
    }

}