package com.ehsy.lb;

import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * Created by zhuangmg on 6/12/16.
 */
public class RandomAddrQuery implements AddressQuery {

    private static final Logger LOG = LoggerFactory.getLogger(RandomAddrQuery.class);

    @Override
    public Svc select(List<Svc> svcs) {

        LOG.info("随机策略获取地址");

        if(svcs == null || svcs.size() == 0){
            return null;
        }

        int idx = (int)Math.random() * svcs.size();

        return svcs.get(idx);
    }
}
