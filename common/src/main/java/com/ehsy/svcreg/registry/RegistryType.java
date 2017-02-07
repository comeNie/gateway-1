package com.ehsy.svcreg.registry;

/**
 * Created by zhuangmg on 6/2/16.
 */
public enum RegistryType {
    CONSUL("consul"),
    ZOOKEEPER("zookeeper"),
    EUREKA("eureka");

    private String type;

    RegistryType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RegistryType of(String type){
        if(type == null){
            return CONSUL;
        }
        if(type.equalsIgnoreCase("consul")){
            return CONSUL;
        }else if(type.equalsIgnoreCase("zookeeper")){
            return ZOOKEEPER;
        }else if(type.equalsIgnoreCase("eureka")){
            return EUREKA;
        }else{
            return CONSUL;
        }
    }
}
