package com.ehsy.gateway.svc.enums;

/**
 * Created by zhuangmg on 6/16/16.
 */
public enum SvcCategory {
    CMP_SERVICE("组件服务"),
    PROXY_SERVICE("代理服务"),
    AGGREGATION_SERVICE("聚合服务");

    private String value;

    SvcCategory(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SvcCategory of(String category){
        if(CMP_SERVICE.getValue().equals(category)){
            return CMP_SERVICE;
        }else if(PROXY_SERVICE.getValue().equals(category)){
            return PROXY_SERVICE;
        }else if(AGGREGATION_SERVICE.getValue().equals(category)){
            return AGGREGATION_SERVICE;
        }
        return CMP_SERVICE;
    }
}
