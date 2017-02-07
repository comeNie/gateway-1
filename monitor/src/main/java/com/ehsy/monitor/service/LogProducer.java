package com.ehsy.monitor.service;

/**
 * Created by zhuangmg on 6/27/16.
 */
public interface LogProducer {

    void send(String topic, String msg);

    void close();

}
