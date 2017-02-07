package com.ehsy.common.enums;

/**
 * Created by zhuangmg on 6/2/16.
 */
public enum ResultCode {
    SUCCESS("000000000", "成功"),
    CM_ERROR("ECM000000", "平台框架处理失败"),
    SVC_ERROR_400("ECM400000", "服务调用通讯报错"),
    SVC_ERROR_500("ECM500000", "调用服务异常报错"),
    SVC_NULL("ECMSVC000", "服务码不能为空"),
    SVC_ADDR_NULl("ECMSVC001", "服务地址未配置"),
    SVC_SIGN_FAILED("ECMSVC002", "签名校验失败");

    private String code;
    private String message;

    ResultCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
