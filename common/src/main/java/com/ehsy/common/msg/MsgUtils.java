package com.ehsy.common.msg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ehsy.common.context.CMApplicationContext;
import com.ehsy.common.enums.MsgType;
import com.ehsy.common.enums.ResultCode;
import com.ehsy.common.msg.request.RequestCommon;
import com.ehsy.common.msg.request.RequestHeader;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseCommon;
import com.ehsy.common.msg.response.ResponseHeader;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.swap.SwapAreaUtils;
import com.ehsy.common.utils.CMUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuangmg on 6/29/16.
 */
public class MsgUtils {

    public static <T> RequestMsg<T> newRequestMsg(String svcName, String entityName, T entity){
        RequestMsg<T> requestMsg = new RequestMsg<>();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setServiceName(svcName);
        requestHeader.setVersion("01");
        requestHeader.setRequestTime(CMUtils.formatDate19(new Date()));
        requestMsg.setHeader(requestHeader);
        RequestCommon requestCommon = new RequestCommon();
        requestCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        requestMsg.setCommon(requestCommon);
        Map<String, T> body = new HashMap<>();
        if(entityName != null){
            body.put(entityName, entity);
        }
        requestMsg.setBody(body);
        return requestMsg;
    }

    public static <T> RequestMsg<T> newRequestMsg(String svcName, Map<String, T> body){
        RequestMsg<T> requestMsg = new RequestMsg<>();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setServiceName(svcName);
        requestHeader.setVersion("01");
        requestHeader.setRequestTime(CMUtils.formatDate19(new Date()));
        requestMsg.setHeader(requestHeader);
        RequestCommon requestCommon = new RequestCommon();
        requestCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        requestMsg.setCommon(requestCommon);
        requestMsg.setBody(body);
        return requestMsg;
    }

    public static <T> RequestMsg<T> getRequestMsg(String msg, Class<T> c){
        RequestMsg<T> requestMsg = JSONObject.parseObject(msg, new TypeReference<RequestMsg<T>>(){});
        return requestMsg;
    }

    public static <T> T getBodyEntity(String msg, String entityName){
        RequestMsg<T> requestMsg = JSONObject.parseObject(msg, new TypeReference<RequestMsg<T>>(){});
        return requestMsg.getBody().get(entityName);
    }

    public static <T> T getReqBodyEntity(RequestMsg<T> requestMsg, String entityName){
        return requestMsg.getBody().get(entityName);
    }

    public static <T> T getRespBodyEntity(ResponseMsg<T> responseMsg, String entityName){
        return responseMsg.getBody().get(entityName);
    }

    public static <T> ResponseMsg<T> newSuccessResponseMsg(String entityName, T entity){
        ResponseMsg<T> responseMsg = new ResponseMsg<>();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setServiceName(SwapAreaUtils.getServiceCode(MsgType.IN));
        responseHeader.setResponseCode(ResultCode.SUCCESS.getCode());
        responseHeader.setResponseDesc(ResultCode.SUCCESS.getMessage());
        responseHeader.setResponseTime(CMUtils.formatDate19(new Date()));
        responseMsg.setHeader(responseHeader);
        responseMsg.setHeader(responseHeader);
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        responseMsg.setCommon(responseCommon);
        Map<String, T> body = new HashMap<>();
        if(entityName != null){
            body.put(entityName, entity);
        }
        responseMsg.setBody(body);
        return responseMsg;
    }

    public static <T> ResponseMsg<T> newSuccessResponseMsg(Map<String, T> body){
        ResponseMsg<T> responseMsg = new ResponseMsg<>();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setServiceName(SwapAreaUtils.getServiceCode(MsgType.IN));
        responseHeader.setResponseCode(ResultCode.SUCCESS.getCode());
        responseHeader.setResponseDesc(ResultCode.SUCCESS.getMessage());
        responseHeader.setResponseTime(CMUtils.formatDate19(new Date()));
        responseMsg.setHeader(responseHeader);
        responseMsg.setHeader(responseHeader);
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setAppID(CMApplicationContext.getId());
        responseMsg.setCommon(responseCommon);
        responseMsg.setBody(body);
        return responseMsg;
    }

    public static ResponseMsg newFailedResponseMsg(ResultCode resultCode){
        ResponseMsg responseMsg = new ResponseMsg();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setServiceName(SwapAreaUtils.getServiceCode(MsgType.IN));
        responseHeader.setResponseCode(resultCode.getCode());
        responseHeader.setResponseDesc(resultCode.getMessage());
        responseHeader.setResponseTime(CMUtils.formatDate19(new Date()));
        responseMsg.setHeader(responseHeader);
        responseMsg.setHeader(responseHeader);
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        responseMsg.setCommon(responseCommon);
        responseMsg.setBody(null);
        return responseMsg;
    }

    public static <T> ResponseMsg<T> newSuccessResponseMsg(String svcName, String entityName, T entity){
        ResponseMsg<T> responseMsg = new ResponseMsg<>();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setServiceName(svcName);
        responseHeader.setResponseCode(ResultCode.SUCCESS.getCode());
        responseHeader.setResponseDesc(ResultCode.SUCCESS.getMessage());
        responseHeader.setResponseTime(CMUtils.formatDate19(new Date()));
        responseMsg.setHeader(responseHeader);
        responseMsg.setHeader(responseHeader);
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        responseMsg.setCommon(responseCommon);
        Map<String, T> body = new HashMap<>();
        if(entityName != null){
            body.put(entityName, entity);
        }
        responseMsg.setBody(body);
        return responseMsg;
    }

    public static <T> ResponseMsg<T> newResponseMsg(String svcName, String responseCode, String responseDesc, String entityName, T entity){
        ResponseMsg<T> responseMsg = new ResponseMsg<>();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setServiceName(svcName);
        responseHeader.setResponseCode(responseCode);
        responseHeader.setResponseDesc(responseDesc);
        responseHeader.setResponseTime(CMUtils.formatDate19(new Date()));
        responseMsg.setHeader(responseHeader);
        responseMsg.setHeader(responseHeader);
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setAppID(CMApplicationContext.getId());
        //TODO 其他field
        responseMsg.setCommon(responseCommon);
        Map<String, T> body = new HashMap<>();
        if(entityName != null){
            body.put(entityName, entity);
        }
        responseMsg.setBody(body);
        return responseMsg;
    }

}
