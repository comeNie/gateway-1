package com.ehsy.common.swap;

import com.ehsy.common.enums.MsgType;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.TypeRef;

/**
 * Created by zhuangmg
 */
public class SwapAreaUtils {

    private static ThreadLocal<SwapArea> swapAreaHolder = new ThreadLocal<SwapArea>(){
        @Override
        protected SwapArea initialValue() {
            return new SwapArea();
        }
    };

    public static <K,V> SwapArea<K,V> getSwapArea(){
        SwapArea<K,V> swapArea = swapAreaHolder.get();
        if(swapArea == null){
            swapArea = new SwapArea<K,V>();
            swapAreaHolder.set(swapArea);
        }
        return swapArea;
    }

    public static void releaseSwapArea(){
        swapAreaHolder.remove();
    }

    public static String getServiceCode(MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(swapArea != null && msgType != null){
            String key = msgType.name() + SwapAreaConst.SERVICE_CODE;
            if(swapArea.containsKey(key)){
                return (String)swapArea.get(key);
            }
        }
        return null;
    }

    public static RequestMsg getRequestMsg(MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(swapArea != null && msgType != null){
            String key = msgType.name() + SwapAreaConst.REQUEST_MSG;
            if(swapArea.containsKey(key)){
                return (RequestMsg)swapArea.get(key);
            }
        }
        return null;
    }

    public static <T> RequestMsg<T> getRequestMsg(TypeRef<T> t, MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(swapArea != null && msgType != null){
            String key = msgType.name() + SwapAreaConst.REQUEST_MSG;
            if(swapArea.containsKey(key)){
                return (RequestMsg<T>)swapArea.get(key);
            }
        }
        return null;
    }

    public static <T> void setRequestMsg(RequestMsg<T> requestMsg, MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(requestMsg != null && swapArea != null && msgType != null){
            swapArea.put(msgType.name() + SwapAreaConst.REQUEST_MSG, requestMsg);
            swapArea.put(msgType.name() + SwapAreaConst.SERVICE_CODE, requestMsg.getHeader().getServiceName());
        }
    }

    public static <T> ResponseMsg<T> getResponseMsg(TypeRef<T> t, MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(swapArea != null && msgType != null){
            String key = msgType.name() + SwapAreaConst.RESPONSE_MSG;
            if(swapArea.containsKey(key)){
                return (ResponseMsg<T>) swapArea.get(key);
            }
        }
        return null;
    }

    public static <T> void setResponseMsg(ResponseMsg<T> responseMsg, MsgType msgType){
        SwapArea swapArea = getSwapArea();
        if(responseMsg != null && msgType != null && swapArea != null){
            swapArea.put(msgType.name() + SwapAreaConst.RESPONSE_MSG, responseMsg);
            //swapArea.put(msgType.name() + SwapAreaConst.SERVICE_CODE, responseMsg.getHeader().getServiceName());
        }
    }

}
