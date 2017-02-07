package com.ehsy.monitor.service.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.monitor.dao.LogDao;
import com.ehsy.monitor.dao.model.CollectLog;
import com.ehsy.monitor.dao.model.OpLog;
import com.ehsy.monitor.service.LogConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Kafka consumer
 */
public class KafkaLogConsumer implements LogConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaLogConsumer.class);

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    private static KafkaConsumer<String, String> kafkaConsumer = null;

    private Lock lock = new ReentrantLock();

    private String hosts;
    private List<String> topics;
    private LogDao logDao;

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    public void setLogDao(LogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public void consume() {
        if(kafkaConsumer == null){
            try {
                lock.lock();

                if(kafkaConsumer == null){
                    Properties props = new Properties();
                    props.put("bootstrap.servers", Arrays.asList(hosts.split(",")));
                    props.put("group.id", "loggrp");
                    props.put("enable.auto.commit", "true");
                    props.put("auto.commit.interval.ms", "30000");
                    props.put("session.timeout.ms", "60000");
                    props.put("request.timeout.ms", "90000");
                    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    //props.put("auto.offset.reset", "earliest");
                    kafkaConsumer = new KafkaConsumer<>(props);
                    kafkaConsumer.subscribe(this.topics);
                }

                LOG.debug("kafka connected");
            } catch (Exception e) {
                LOG.error("{}", e);
            } finally {
                lock.unlock();
            }
        }

        scheduledExecutorService.scheduleAtFixedRate(new LogRunnable(this.logDao), 1, 10, TimeUnit.SECONDS);
    }

    class LogRunnable implements Runnable{

        private LogDao logDao;

        public LogRunnable(LogDao logDao){
            this.logDao = logDao;
        }

        @Override
        public void run() {
            try{
                //LOG.debug("poll log");
                ConsumerRecords<String, String> records = kafkaConsumer.poll(1000*30);
                for (ConsumerRecord<String, String> record : records) {
                    String topic = record.topic();
                    String value = record.value();
                    LOG.debug("记录日志 [{}] - [{}]", topic, value);
                    if(value != null){
                        try{
                            if(topic.equals("AC")){
                                //前端界面采集日志
                                JSONObject info = JSON.parseObject(value);
                                CollectLog collectLog = new CollectLog();
                                collectLog.setInfo(info);
                                this.logDao.insert(topic, collectLog);
                            }else{
                                //操作日志
                                OpLog opLog = JSON.parseObject(value, OpLog.class);
                                this.logDao.insert(topic, opLog);
                            }
                        }catch (Exception e){
                            LOG.error("{}", e);
                        }
                    }
                }
            }catch (Exception e){
                LOG.error("{}", e);
            }
        }
    }

    @Override
    public void close(){
        if(kafkaConsumer != null){
            LOG.debug("close kafka consumer");
            kafkaConsumer.close();
        }
    }
}

