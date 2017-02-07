package com.ehsy.transport;

import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public interface EndpointResolver {

    /**
     * 解析服务调用地址
     * @param svcName 服务码
     * @return 服务地址
     */
    public String resolveEndpoint(String svcName);

    /**
     * 解析服务调用地址
     * @param svcName 服务码
     * @param pathParams 路径参数
     * @return 服务地址
     */
    public String resolveEndpoint(String svcName, Map<String, String> pathParams);

}
