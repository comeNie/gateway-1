package com.ehsy.monitor.service;

/**
 * Created by zhuangmg on 6/22/16.
 */
public interface LogConsumer {

    void consume();

    void close();
}
