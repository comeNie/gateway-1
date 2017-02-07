package com.ehsy.svcreg.client;

import com.ehsy.svcreg.discovery.NotifyListener;
import com.ehsy.svcreg.model.Svc;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.CatalogClient;
import com.orbitz.consul.HealthClient;

import java.util.List;

/**
 * Created by zhuangmg on 6/1/16.
 */
public interface SvcRegClient {

    /**
     * 注册服务
     * @param svc
     */
    public void registerService(Svc svc);

    /**
     * 注销服务
     * @param svc
     */
    public void deregisterService(Svc svc);

    /**
     * 可用服务列表
     * @param serviceName
     * @return
     */
    public List<Svc> getHealthyServices(String serviceName);

    /**
     * 订阅服务变化通知
     * @param serviceName
     * @param notifyListener
     */
    public void subscribeService(final String serviceName, final NotifyListener notifyListener);

    /**
     * 取消定于服务变化通知
     * @param serviceName
     */
    public void unsubscribeService(String serviceName);

    /**
     * 使服务有效
     * @param svc
     */
    public void passService(Svc svc);

    /**
     * 使服务时效
     * @param svc
     */
    public void failService(Svc svc);

    /**
     * 获取注册的服务列表
     * @return
     */
    public List<Svc> getRegisteredServices();

    public AgentClient getAgentClient();

    public CatalogClient getCataClient();

    public HealthClient getHealthClient();

}
