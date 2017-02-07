package com.ehsy.monitor.service.kafka;

import com.ehsy.monitor.service.LogProducer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 6/27/16.
 */
public class KafkaLogProducer implements LogProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaLogProducer.class);

    private static KafkaProducer<String, String> kafkaProducer = null;

    private Lock lock = new ReentrantLock();

    private String hosts;

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    @Override
    public void send(String topic, String msg) {
        if(kafkaProducer == null){
            try {
                lock.lock();

                if(kafkaProducer == null){
                    Properties props = new Properties();
                    props.put("bootstrap.servers", Arrays.asList(hosts.split(",")));
                    props.put("acks", "all");
                    props.put("retries", 1);
                    props.put("batch.size", 16384);
                    props.put("linger.ms", 1);
                    props.put("buffer.memory", 33554432);
                    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                    kafkaProducer = new KafkaProducer<>(props);
                }

                LOG.debug("kafka connected");
            } catch (Exception e) {
                LOG.error("{}", e);
            } finally {
                lock.unlock();
            }
        }

        kafkaProducer.send(new ProducerRecord<String, String>(topic, msg));

    }

    @Override
    public void close() {
        if(kafkaProducer != null){
            kafkaProducer.close();
        }
    }
}
