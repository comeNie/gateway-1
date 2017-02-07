package com.ehsy.transport;

import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.transport.data.SvcResult;
import com.ehsy.transport.enums.ContentType;
import com.ehsy.transport.enums.SvcReqType;

import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public interface SvcExecutor {

    /**
     * 服务方法调用, 请求报文体为空
     * @param svcName 服务名称
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <R> SvcResult<R> execute(String svcName, TypeRef<ResponseMsg<R>> t);

    /**
     * 服务方法调用, 返回报文体为空
     * @param svcName 服务名称
     * @param reqEntityName entity key
     * @param reqEntity entity value
     * @return 响应报文
     */
    public <T> SvcResult<?> execute(String svcName, String reqEntityName, T reqEntity);

    /**
     * 服务方法调用
     * @param svcName 服务名称
     * @param reqEntityName entity key
     * @param reqEntity entity value
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <T, R> SvcResult<R> execute(String svcName, String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t);

    /**
     * 服务方法调用
     * @param svcName 服务名称
     * @param pathParams 路径参数
     * @param reqEntityName entity key
     * @param reqEntity entity value
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams,  String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t);

    /**
     * 服务方法调用
     * @param svcName 服务名称
     * @param pathParams 路径参数
     * @param contentType 内容类别
     * @param svcReqType 服务请求方式
     * @param reqEntityName entity key
     * @param reqEntity entity value
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams, ContentType contentType, SvcReqType svcReqType, String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t);

    /**
     * 服务方法调用
     * @param svcName 服务名称
     * @param requestMsg request message
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <T, R> SvcResult<R> execute(String svcName, RequestMsg<T> requestMsg, TypeRef<ResponseMsg<R>> t);

    /**
     * 服务方法调用
     * @param svcName 服务名称
     * @param pathParams 路径参数
     * @param contentType 内容类别
     * @param svcReqType 服务请求方式
     * @param requestMsg request message
     * @param t 响应报文类型
     * @return 响应报文
     */
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams, ContentType contentType, SvcReqType svcReqType, RequestMsg<T> requestMsg, TypeRef<ResponseMsg<R>> t);
}
