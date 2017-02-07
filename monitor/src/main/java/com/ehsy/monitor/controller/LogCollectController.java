package com.ehsy.monitor.controller;

import com.ehsy.common.controller.BaseController;
import com.ehsy.monitor.service.LogProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhuangmg on 6/22/16.
 */
@Controller
@RequestMapping("/log")
public class LogCollectController  extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LogCollectController.class);

    @Autowired
    private LogProducer logProducer;

    @RequestMapping(value="/collect", produces = "image/gif")
    @ResponseBody
    public String collectLog(@RequestParam String info){

        LOG.debug("数据采集内容: {}", info);

        this.logProducer.send("AC", info);

        return null;
    }
}
