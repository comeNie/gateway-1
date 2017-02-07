package com.ehsy.svcreg.discovery;

import com.ehsy.common.context.CMApplicationContext;
import com.ehsy.svcreg.model.Svc;
import com.ehsy.svcreg.registry.SvcDefFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class LocalSvcDiscovery extends SvcDiscovery {

    private static final Logger LOG = LoggerFactory.getLogger(LocalSvcDiscovery.class);

    private Lock lock = new ReentrantLock();

    @Override
    public List<Svc> discoverBySvcName(String svcName) {
        List<Svc> svcs = SvcLocalCache.getSvcList(svcName);
        if(svcs == null){
            try{
                lock.lock();
                if(svcs == null){
                    //初始化本地服务缓存
                    SvcDefFactory svcDefFactory = CMApplicationContext.getBean("localSvcDefFactory", SvcDefFactory.class);
                    if(svcDefFactory != null){
                        List<Svc> allSvcs = svcDefFactory.getConsumedSvcs();
                        SvcLocalCache.initCache(allSvcs);
                    }else{
                        LOG.error("未配置本地服务定义工厂[LocalSvcDefFactory]");
                    }
                }
            }finally {
                lock.unlock();
            }
            svcs = SvcLocalCache.getSvcList(svcName);
        }
        return svcs;
    }

    @Override
    public List<Svc> discoverByCmpName(String cmpName) {

        LOG.debug("获取组件[{}]本地提供的所有服务定义", cmpName);

        SvcDefFactory svcDefFactory = CMApplicationContext.getBean("localSvcDefFactory", SvcDefFactory.class);
        if(svcDefFactory != null){
            return svcDefFactory.getProvidedSvcs();
        }else{
            LOG.error("未配置本地服务定义工厂[LocalSvcDefFactory]");
            return null;
        }
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}
