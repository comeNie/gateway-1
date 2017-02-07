package com.ehsy.svcreg.consul;

/**
 * Created by zhuangmg on 5/31/16.
 */
public class ConsulUtils {

    /**
     * 获取服务ID
     * @param host 地址
     * @param port 端口
     * @param name 服务码
     * @return
     */
    public static String getServiceId(String host, int port, String name){
        return host + "-" + port + "-" + name;
    }

}
