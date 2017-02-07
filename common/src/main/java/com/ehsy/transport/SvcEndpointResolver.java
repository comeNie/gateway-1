package com.ehsy.transport;

import com.ehsy.lb.AddressQuery;
import com.ehsy.lb.QueryStrategyFactory;
import com.ehsy.svcreg.discovery.Discovery;
import com.ehsy.svcreg.discovery.DiscoveryFactory;
import com.ehsy.svcreg.discovery.SvcLocalCache;
import com.ehsy.svcreg.model.Svc;
import com.ehsy.svcreg.utils.SvcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public class SvcEndpointResolver implements EndpointResolver {

    private static final Logger LOG = LoggerFactory.getLogger(SvcEndpointResolver.class);

    private QueryStrategyFactory addrQueryStrategyFactory;

    private DiscoveryFactory discoveryFactory;

    public void setAddrQueryStrategyFactory(QueryStrategyFactory addrQueryStrategyFactory) {
        this.addrQueryStrategyFactory = addrQueryStrategyFactory;
    }

    public void setDiscoveryFactory(DiscoveryFactory discoveryFactory) {
        this.discoveryFactory = discoveryFactory;
    }

    @Override
    public String resolveEndpoint(String svcName) {
        return this.resolveEndpoint(svcName, null);
    }

    @Override
    public String resolveEndpoint(String svcName, Map<String, String> pathParams) {
        AddressQuery addressQuery = this.addrQueryStrategyFactory.getQueryStrategy();
        List<Svc> svcs = this.discoveryFactory.getDiscovery().discoverBySvcName(svcName);
        Svc svc = addressQuery.select(svcs);

        if(svc == null){
            return null;
        }

        StringBuilder url = new StringBuilder();
        //http://localhost:port/test/{sub}
        url.append(svc.getProtocol() == null ? HttpConsts.HTTP_PROTOCOL : svc.getProtocol());
        url.append("://");
        url.append(svc.getHost());
        url.append(":");
        url.append(svc.getPort());
        String path = svc.getPath();
        if(pathParams != null){
            for(Map.Entry<String, String> entry : pathParams.entrySet()){
                String key = "\\{" + entry.getKey() + "\\}";
                String value = entry.getValue();
                path = path.replaceAll(key, value);
            }
        }
        url.append(path);

        LOG.debug("service [{}] address = [{}]", svc.getSvcName(), url.toString());

        return url.toString();
    }

}
