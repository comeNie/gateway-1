package com.ehsy.svcreg.registry;

import com.ehsy.svcreg.model.Svc;

/**
 * Created by zhuangmg on 5/31/16.
 */
public interface Registry {

    /**
     * 注册组件提供的所有服务
     */
    void register();

    /**
     * 注册服务
     * @param svc
     */
    void register(Svc svc);

    /**
     * 取消服务注册
     * @param svc
     */
    void deregister(Svc svc);

}
