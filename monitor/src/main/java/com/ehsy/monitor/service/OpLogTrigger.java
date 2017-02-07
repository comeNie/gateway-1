package com.ehsy.monitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhuangmg on 6/23/16.
 */
@Service
public class OpLogTrigger implements InitializingBean {

    private static Logger LOG = LoggerFactory.getLogger(OpLogTrigger.class);

    @Autowired
    private LogConsumer logConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {

        LOG.info("启动日志收集服务");

        this.logConsumer.consume();
    }
}
