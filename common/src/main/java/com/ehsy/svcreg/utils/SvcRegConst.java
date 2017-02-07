package com.ehsy.svcreg.utils;

/**
 * Created by zhuangmg on 5/31/16.
 */
public class SvcRegConst {

    public static final long CHECK_INTERVAL = 10; //检查时间(秒)

    public static final String DEFAULT_PROTOCOL = "http";//默认通讯协议

    public static final String DEFAULT_HOST = "localhost"; //默认服务器地址

    public static final int DEFAULT_CONSUL_PORT = 8500; //默认consul端口

    public static final int DEFAULT_SERVER_PORT = 8081; //默认server端口

    public static final int READ_TIMEOUT_MILLS = 20000; //读超时时间(毫秒)

    public static final String CMP_CFG_SVC_CODE = "CFS000001"; //组件定义服务

    public static final String CMP_CFG_SVC_DESC_BY_CODE = "CFS000002"; //组件定义服务

    public static final String CMP_CFG_SVC_SIGN_BY_APPID = "CFS000003"; //根据AppID得到签名key

}
