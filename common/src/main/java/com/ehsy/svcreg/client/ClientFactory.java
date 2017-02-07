package com.ehsy.svcreg.client;

/**
 * Created by zhuangmg on 6/3/16.
 */
public interface ClientFactory {

    /**
     * 获取服务注册中心客户端示例
     * @return
     */
    SvcRegClient getClient();

}
