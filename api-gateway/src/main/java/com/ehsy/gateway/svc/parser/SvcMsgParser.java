package com.ehsy.gateway.svc.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.gateway.ComplexModel;
import com.ehsy.gateway.ComponentMsg;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

public class SvcMsgParser {

    /**
     * 从描述文件中获取category
     * @param desc
     * @return
     */
    public static String getCategory(String desc) {
        if(!StringUtils.isEmpty(desc)){
            JSONObject obj = parseObject(desc);
            return (String) obj.get("category");
        }
        return null;
    }

    /**
     * 根据服务请求描述转换服务请求报文
     * @param obj_body
     * @param componentMsg
     * @return
     */
    public static RequestMsg convertRequest(JSONObject obj_body, ComponentMsg componentMsg){
        String obj_str = JSON.toJSONString(obj_body);
        if(!StringUtils.isEmpty(obj_str) && null != componentMsg){
            RequestMsg requestMsg = parseObject(obj_str, RequestMsg.class);
            List<ComplexModel> list = componentMsg.getRequestMsg();
            if(CollectionUtils.isEmpty(list)){
                requestMsg.setBody(null);
            }else{
                requestMsg.setBody(handleBody(obj_body, list));
            }
            return requestMsg;
        }
        return null;
    }

    /**
     * 根据服务请求描述转换服务请求报文
     * @param obj_body
     * @param componentMsg
     * @return
     */
    public static Map convertRequestBody(JSONObject obj_body, ComponentMsg componentMsg){
        String obj_str = JSON.toJSONString(obj_body);
        if(!StringUtils.isEmpty(obj_str) && null != componentMsg){
            List<ComplexModel> list = componentMsg.getRequestMsg();
            if(!CollectionUtils.isEmpty(list)){
                return handleBody(obj_body, list);
            }
        }
        return null;
    }

    /**
     * 根据服务请求描述转换服务响应报文
     * @param response
     * @param componentMsg
     * @return
     */
    public static ResponseMsg convertResponse(String response, ComponentMsg componentMsg){
        if(!StringUtils.isEmpty(response) && null != componentMsg){
            ResponseMsg responseMsg = new ResponseMsg();
            //body特殊处理
            List<ComplexModel> list = componentMsg.getResponseMsg();
            if(CollectionUtils.isEmpty(list)){
                responseMsg.setBody(null);
            }else{
                responseMsg.setBody(handleBody(JSON.parseObject(response), list));
            }
            return responseMsg;
        }
        return null;
    }

    /**
     * 根据服务请求描述转换服务响应报文
     * @param response
     * @param componentMsg
     * @return
     */
    public static Map convertResponseBody(String response, ComponentMsg componentMsg){
        if(!StringUtils.isEmpty(response) && null != componentMsg){
            List<ComplexModel> list = componentMsg.getResponseMsg();
            if(!CollectionUtils.isEmpty(list)){
                return handleBody(JSON.parseObject(response), list);
            }
        }
        return null;
    }

    /**
     * 根据描述文件处理body数据
     * @param obj_body  具体的json
     * @param list  描述文件列表
     * @return
     */
    private static Map handleBody(JSONObject obj_body, List<ComplexModel> list) {
        Map body = Maps.newHashMap();
        for(ComplexModel complexModel : list) {
            //字段名
            String fieldName = complexModel.getFieldName();
            String type = complexModel.getType();
            String required = complexModel.getRequired();
            if ("simple".equals(type)) {
                //字段值
                body.put(fieldName, handleSimple(fieldName, obj_body, required));
            }else if("complex".equals(type)){
                JSONObject obj_fields = (JSONObject) obj_body.get(fieldName);
                body.put(fieldName, handleComplex(fieldName, complexModel.getFields(), obj_fields, required));
            }else if("array".equals(type)){
                JSONArray obj_array = (JSONArray) obj_body.get(fieldName);
                if(obj_array.size() <= 0 && "true".equals(required)){
                    CMUtils.throwException("字段："+ fieldName +"为必填字段");
                }
                body.put(fieldName, handleArray(fieldName, complexModel.getArray(), obj_array, required));
            }else{
                CMUtils.throwException("类型在描述文件中不存在");
            }
        }
        return body;
    }

    /**
     * 必填字段检查
     * @param key
     * @param val
     * @param required
     */
    private static void checkRequired(String key, String val, String required){
        if("true".equals(required)){
            if(StringUtils.isEmpty(val)){
                CMUtils.throwException("字段："+key+"为必填字段");
            }
        }
    }

    /**
     * 检查JSON是否正常
     * @param
     */
    private static <T> void checkJSON(T t){
        if(null == t){
            CMUtils.throwException("JSON参数缺失或者与描述文件不一致");
        }
    }

    private static String handleSimple(String key, JSONObject obj, String required){
        checkJSON(obj);
        String val = String.valueOf(obj.get(key));
        checkRequired(key, val, required);
        return val;
    }

    private static <T> Map<String, T> handleComplex(String key, List<ComplexModel> list, JSONObject obj, String require){
        checkJSON(obj);
        Map<String, T> map = null;
        if(!CollectionUtils.isEmpty(list)){
            map = new HashMap<String, T>();
            for( ComplexModel complexModel : list){
                String fieldName = complexModel.getFieldName();
                String type = complexModel.getType();
                String required = complexModel.getRequired();
                if("simple".equals(type)){
                    map.put(fieldName, (T) handleSimple(fieldName, obj, required));
                }else if("complex".equals(type)){
                    map.put(fieldName, (T) handleComplex(fieldName, complexModel.getFields(), (JSONObject) obj.get(fieldName), required));
                }else if("array".equals(type)){
                    map.put(fieldName, (T) handleArray(fieldName, complexModel.getArray(), (JSONArray) obj.get(fieldName), required));
                }else{
                    CMUtils.throwException("类型在描述文件中不存在");
                }
            }
        }
        return map;
    }

    private static <T> List<T> handleArray(String key, ComplexModel complexModel, JSONArray jsonArray, String require){
        checkJSON(jsonArray);
        List<T> arrayList = null;
        if(null != complexModel){
            arrayList = new ArrayList<>();
            String type = complexModel.getType();
            String fieldName = complexModel.getFieldName();
            String required = complexModel.getRequired();
            if("simple".equals(type)){
                for(Object obj : jsonArray){
                    arrayList.add((T) obj);
                }
            }else if("complex".equals(type)){
                List<ComplexModel> list = complexModel.getFields();
                for(Object  obj : jsonArray){
                    arrayList.add((T) handleComplex(fieldName, list, (JSONObject) obj, required));
                }
            }else if("array".equals(type)){
                arrayList.add((T) handleArray(fieldName, complexModel.getArray(), jsonArray, required));
            }else{
                CMUtils.throwException("类型在描述文件中不存在");
            }
        }
        return arrayList;
    }
}
