package com.ehsy.svcreg.client;

import com.ehsy.svcreg.consul.ConsulClient;
import com.ehsy.svcreg.registry.RegistryType;
import com.google.common.base.Preconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 6/3/16.
 */
public class DefaultClientFactory implements ClientFactory{

    private String type;
    private String host;
    private String port;
    private String projectContext;
    private String serverPort;

    private SvcRegClient svcRegClient;

    private Lock lock = new ReentrantLock();

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProjectContext(String projectContext) {
        this.projectContext = projectContext;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public SvcRegClient getClient() {
        RegistryType registryType = RegistryType.of(type);
        Preconditions.checkArgument(registryType == RegistryType.CONSUL, "其他注册方式暂不支持");

        if(this.svcRegClient == null){
            try{
                lock.lock();
                if(this.svcRegClient == null){
                    ConsulClient.Builder builder = ConsulClient.builder();
                    builder.withHost(this.host);
                    builder.withPort(this.port);
                    builder.withServerPort(this.serverPort);
                    builder.withProjectContext(this.projectContext);
                    this.svcRegClient = builder.build();
                }
            }finally {
                lock.unlock();
            }
        }

        return this.svcRegClient;
    }
}
