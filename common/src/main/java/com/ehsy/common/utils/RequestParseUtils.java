package com.ehsy.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.msg.request.RequestCommon;
import org.springframework.util.StringUtils;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by ehsy_it on 2016/7/16.
 */
public class RequestParseUtils {

    /**
     * 从请求报文中得到common域
     * @param msg
     * @return
     */
    public static RequestCommon getCommon(String msg) {
        if(!StringUtils.isEmpty(msg)){
            JSONObject obj = parseObject(msg);
            return JSON.parseObject(JSON.toJSONString(obj.get("common")), RequestCommon.class) ;
        }
        return null;
    }

    /**
     * 从header中获取指定字段的值
     * @param request
     * @return
     */
    public static String getServiceName(String request){
        if(!StringUtils.isEmpty(request)){
            JSONObject obj = parseObject(request);
            JSONObject obj_header = (JSONObject) obj.get("header");
            return (String)obj_header.get("serviceName");
        }
        return null;
    }
}
