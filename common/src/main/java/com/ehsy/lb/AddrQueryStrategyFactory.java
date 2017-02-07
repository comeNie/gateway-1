package com.ehsy.lb;

import com.ehsy.common.utils.CMUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhuangmg on 6/12/16.
 */
public class AddrQueryStrategyFactory implements QueryStrategyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AddrQueryStrategyFactory.class);

    private String strategy;

    private Map<String, AddressQuery> queryStrategyMap;

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public void setQueryStrategyMap(Map<String, AddressQuery> queryStrategyMap) {
        this.queryStrategyMap = queryStrategyMap;
    }

    @Override
    public AddressQuery getQueryStrategy() {

        if(this.queryStrategyMap.containsKey(this.strategy)){
            return this.queryStrategyMap.get(this.strategy);
        }else{
            LOG.debug("地址查询策略不存在,随机获取策略");

            int idx = (int)Math.random() * this.queryStrategyMap.size();

            return this.queryStrategyMap.get(String.valueOf(idx));
        }
    }
}
