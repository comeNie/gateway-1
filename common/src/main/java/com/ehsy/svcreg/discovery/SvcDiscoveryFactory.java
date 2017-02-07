package com.ehsy.svcreg.discovery;

import com.ehsy.common.context.CMApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class SvcDiscoveryFactory implements DiscoveryFactory {

    private static final Logger LOG = LoggerFactory.getLogger(SvcDiscoveryFactory.class);

    private Discovery localSvcDiscovery;

    private Discovery dynSvcDiscovery;

    private String discoveryDynamic;

    public void setLocalSvcDiscovery(Discovery localSvcDiscovery) {
        this.localSvcDiscovery = localSvcDiscovery;
    }

    public void setDynSvcDiscovery(Discovery dynSvcDiscovery) {
        this.dynSvcDiscovery = dynSvcDiscovery;
    }

    public void setDiscoveryDynamic(String discoveryDynamic) {
        this.discoveryDynamic = discoveryDynamic;
    }

    @Override
    public Discovery getDiscovery(){
        if(!"0".equals(this.discoveryDynamic)){
            LOG.debug("获取远程服务查询服务");
            return this.dynSvcDiscovery;
        }else{
            LOG.debug("获取本地服务查询服务");
            return this.localSvcDiscovery;
        }
    }
}
