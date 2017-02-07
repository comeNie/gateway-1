package com.ehsy.svcreg.registry;

import com.ehsy.common.utils.CMUtils;
import com.ehsy.svcreg.client.ClientFactory;
import com.ehsy.svcreg.client.SvcRegClient;
import com.ehsy.svcreg.discovery.Discovery;
import com.ehsy.svcreg.discovery.DiscoveryFactory;
import com.ehsy.svcreg.model.Svc;
import com.ehsy.svcreg.utils.SvcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhuangmg on 6/3/16.
 */
public class SvcRegistry implements Registry {

    private static final Logger LOG = LoggerFactory.getLogger(SvcRegistry.class);

    private ClientFactory clientFactory;
    private DiscoveryFactory discoveryFactory;
    private String port;
    private String cmpName; //组件名

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public void setDiscoveryFactory(DiscoveryFactory discoveryFactory) {
        this.discoveryFactory = discoveryFactory;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setCmpName(String cmpName){
        this.cmpName = cmpName;
    }

    @Override
    public void register() {
        Discovery discovery = this.discoveryFactory.getDiscovery();
        List<Svc> svcs = discovery.discoverByCmpName(this.cmpName);
        if(svcs != null){
            for(Svc svc : svcs){
                svc.setHost(SvcUtils.getLocalIP());
                svc.setComponent(this.cmpName);
                if(CMUtils.isNumeric(this.port)){
                    svc.setPort(Integer.valueOf(this.port));
                }
                this.register(svc);
            }
        }
    }

    @Override
    public void register(Svc svc) {
        LOG.debug("注册[{}]", svc);
        SvcRegClient svcRegClient = this.clientFactory.getClient();
        svcRegClient.registerService(svc);
    }

    @Override
    public void deregister(Svc svc) {
        LOG.debug("取消注册[{}]", svc);
        SvcRegClient svcRegClient = this.clientFactory.getClient();
        svcRegClient.deregisterService(svc);
    }
}
