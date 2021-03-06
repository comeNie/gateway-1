package com.ehsy.common.exception;

/**
 * Created by zhuangmg on 16/3/29.
 */
public class CMRuntimeException extends RuntimeException {

    private String code;
    private String message;

    public CMRuntimeException(String message){
        super(message);
        this.message = message;
    }

    public CMRuntimeException(String code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
