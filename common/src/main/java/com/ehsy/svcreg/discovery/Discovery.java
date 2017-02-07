package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 5/31/16.
 */
public interface Discovery {

    /**
     * 订阅服务变更通知
     * @param svcName
     * @param listener
     */
    void subscribe(String svcName, NotifyListener listener);

    /**
     * 取消订阅服务变更通知
     * @param svcName
     * @param listener
     */
    void unsubscribe(String svcName, NotifyListener listener);

    /**
     * 根据服务名从注册中心获取可用服务列表
     * @param svcName
     * @return
     */
    List<Svc> discoverBySvcName(String svcName);

    /**
     * 根据组件名从配置中心获取本组件提供的所有服务
     * @param cmpName
     * @return
     */
    List<Svc> discoverByCmpName(String cmpName);

    /**
     * 判断动态/本地查询
     * @return
     */
    public boolean isDynamic();

}
