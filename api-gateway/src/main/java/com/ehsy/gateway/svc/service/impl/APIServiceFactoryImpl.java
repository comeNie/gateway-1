package com.ehsy.gateway.svc.service.impl;

import com.ehsy.gateway.svc.enums.SvcCategory;
import com.ehsy.gateway.svc.service.APIService;
import com.ehsy.gateway.svc.service.APIServiceFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhuangmg on 6/20/16.
 */
@Service("apiServiceFactory")
public class APIServiceFactoryImpl implements APIServiceFactory {

    @Resource(name="proxyService")
    private APIService proxyService;

    @Resource(name="aggregationService")
    private APIService aggregationService;

    @Override
    public APIService getService(String category) {

        //获取服务描述
//        BaseMsg msg = SvcCfgCache.getSvcCfg(serviceName);
        if(category != null){
            SvcCategory svcCategory = SvcCategory.of(category);
            switch(svcCategory){
                case PROXY_SERVICE:
                    return this.proxyService;
                case AGGREGATION_SERVICE:
                    return this.aggregationService;
                default:
                    return null;
            }
        }
        return null;
    }
}
