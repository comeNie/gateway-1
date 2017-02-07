package com.ehsy.lb;

import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhuangmg on 6/12/16.
 */
public class RoundRobinAddrQuery implements AddressQuery{

    private static final Logger LOG = LoggerFactory.getLogger(RoundRobinAddrQuery.class);

    private AtomicInteger idx = new AtomicInteger(0);

    @Override
    public Svc select(List<Svc> svcs) {

        LOG.info("轮询策略获取地址");

        if(svcs == null || svcs.size() == 0){
            return null;
        }

        int index = idx.getAndIncrement();
        if(index == svcs.size()){
            boolean result = idx.compareAndSet(svcs.size(), 0);
            if(!result){
                index = idx.getAndIncrement();
            }
        }

        return svcs.get(index % svcs.size());
    }
}
