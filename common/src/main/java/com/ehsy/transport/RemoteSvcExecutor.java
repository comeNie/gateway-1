package com.ehsy.transport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.enums.MsgType;
import com.ehsy.common.enums.ResultCode;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.swap.SwapAreaUtils;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.transport.data.ReqData;
import com.ehsy.transport.data.RespData;
import com.ehsy.transport.data.SvcResult;
import com.ehsy.transport.enums.ContentType;
import com.ehsy.transport.enums.SvcReqType;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public class RemoteSvcExecutor implements SvcExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteSvcExecutor.class);

    private EndpointResolver endpointResolver;

    private HttpRequest httpRequest;

    public RemoteSvcExecutor(){
        this.httpRequest = Requests.createDefault(HttpConsts.SOCKET_TIMEOUT, HttpConsts.CONNECT_TIMEOUT);
    }

    public void setEndpointResolver(EndpointResolver endpointResolver) {
        this.endpointResolver = endpointResolver;
    }

    @Override
    public <R> SvcResult<R> execute(String svcName, TypeRef<ResponseMsg<R>> t) {
        return execute(svcName, null, ContentType.JSON, SvcReqType.HTTP_POST_JSON, null, null, t);
    }

    @Override
    public <T> SvcResult<?> execute(String svcName, String reqEntityName, T reqEntity) {
        return execute(svcName, null, ContentType.JSON, SvcReqType.HTTP_POST_JSON, reqEntityName, reqEntity, null);
    }

    @Override
    public <T, R> SvcResult<R> execute(String svcName, String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t) {
        return execute(svcName, null, ContentType.JSON, SvcReqType.HTTP_POST_JSON, reqEntityName, reqEntity, t);
    }

    @Override
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams, String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t) {
        return execute(svcName, pathParams, ContentType.JSON, SvcReqType.HTTP_POST_JSON, reqEntityName, reqEntity, t);
    }

    @Override
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams, ContentType contentType, SvcReqType svcReqType, String reqEntityName, T reqEntity, TypeRef<ResponseMsg<R>> t) {

        RequestMsg<T> requestMsg = MsgUtils.newRequestMsg(svcName, reqEntityName, reqEntity);

        return execute(svcName, pathParams, ContentType.JSON, SvcReqType.HTTP_POST_JSON, requestMsg, t);
    }

    @Override
    public <T, R> SvcResult<R> execute(String svcName, RequestMsg<T> requestMsg, TypeRef<ResponseMsg<R>> t) {
        return execute(svcName, null, ContentType.JSON, SvcReqType.HTTP_POST_JSON, requestMsg, t);
    }

    @Override
    public <T, R> SvcResult<R> execute(String svcName, Map<String, String> pathParams, ContentType contentType, SvcReqType svcReqType, RequestMsg<T> requestMsg, TypeRef<ResponseMsg<R>> t) {

        if(StringUtils.isEmpty(svcName)){
            LOG.error("服务码不能为空");
            return this.returnErrorResult(ResultCode.SVC_NULL);
        }

        String svcURI = this.endpointResolver.resolveEndpoint(svcName, pathParams);

        if(svcURI == null){
            LOG.error("服务[{}]地址未配置", svcName);
            return this.returnErrorResult(ResultCode.SVC_ADDR_NULl);
        }

        LOG.debug("request msg = {}", requestMsg);

        ReqData<RequestMsg<T>> reqData = new ReqData<>();
        reqData.setContent(requestMsg);
        reqData.setSvcReqType(svcReqType);
        if(contentType != null){
            reqData.setContentType(contentType.getValue());
        }

        //记录外呼请求报文
        SwapAreaUtils.setRequestMsg(requestMsg, MsgType.OUT);

        RespData<String> respData = this.invoke(svcURI, reqData, String.class);

        if(respData != null) {
            if(respData.getStatusCode() != HttpStatus.SC_OK){
                return this.returnErrorResult(String.valueOf(respData.getStatusCode()), "HTTP请求异常");
            }else{
                LOG.debug("response msg = {}", respData.getData());
                SvcResult<R> svcResult = new SvcResult<>();
                svcResult.setSuccess(true);
                String data = respData.getData();
                if(!StringUtils.isEmpty(data)){
                    //JSON 2 object
                    ResponseMsg<R> responseMsg = JSONObject.parseObject(data, t.getType());

                    //记录外呼响应报文
                    SwapAreaUtils.setResponseMsg(responseMsg, MsgType.OUT);

                    svcResult.setData(responseMsg.getBody());
                }else{
                    svcResult.setData(null);
                }
                return svcResult;
            }
        }
        else{
            return this.returnErrorResult(ResultCode.SVC_ERROR_500);
        }
    }

    private <T, R> RespData<R> invoke(String svcURI, ReqData<T> reqData, Class<R> resClass) {

        RespData<R> respData = null;

        T content = reqData.getContent();

        SvcReqType svcReqType = reqData.getSvcReqType();

        LOG.debug("request type = {}", svcReqType);

        switch (svcReqType){
            case HTTP_GET:
                if(content instanceof Map)
                    respData = this.httpRequest.get(svcURI, (Map<String, Object>) content, resClass);
                break;
            case HTTP_POST_XML:
                if(content instanceof String)
                    respData = this.httpRequest.post(svcURI, reqData.getContentType(), (String)content, resClass);
                break;
            case HTTP_POST_FORM:
                if(content instanceof Map) {
                    respData = this.httpRequest.post(svcURI, reqData.getContentType(), (Map<String, Object>) content, resClass);
                }
                break;
            case HTTP_POST_JSON:
            default:
                respData = this.httpRequest.post(svcURI, reqData.getContentType(), content != null ? JSON.toJSONString(content) : null, resClass);
                break;
        }

        return respData;
    }

    private SvcResult returnErrorResult(String code, String msg){
        SvcResult svcResult = new SvcResult();
        svcResult.setSuccess(false);
        svcResult.setRespCode(code);
        svcResult.setRespMsg(msg);
        svcResult.setData(null);
        return svcResult;
    }

    private SvcResult returnErrorResult(ResultCode resultCode){
        return returnErrorResult(resultCode.getCode(), resultCode.getMessage());
    }
}
