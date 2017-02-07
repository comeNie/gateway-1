package com.ehsy.common.exception;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.enums.ResultCode;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.response.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhuangmg on 7/22/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    protected static HttpHeaders headers = new HttpHeaders();
    static {
        headers.add("Content-Type", "application/json; charset=utf-8");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseMsg> jsonErrorHandler(Exception exception) throws Exception {
        if(exception instanceof CMRuntimeException){
            LOG.error("平台异常[{}]", exception.getMessage());
        }else{
            LOG.error("系统异常[{}]", exception);
        }

        return new ResponseEntity<>(MsgUtils.newFailedResponseMsg(ResultCode.SVC_ERROR_500), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
