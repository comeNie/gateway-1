package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.client.ClientFactory;
import com.ehsy.svcreg.client.SvcRegClient;
import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 6/3/16.
 */
public abstract class SvcDiscovery implements Discovery {

    protected ClientFactory clientFactory;

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void subscribe(String svcName, NotifyListener listener) {
    }

    @Override
    public void unsubscribe(String svcName, NotifyListener listener) {
    }

}
