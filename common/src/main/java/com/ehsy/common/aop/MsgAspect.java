package com.ehsy.common.aop;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.enums.MsgType;
import com.ehsy.common.exception.CMRuntimeException;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.sign.SignUtils;
import com.ehsy.common.swap.SwapAreaUtils;
import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangmg on 6/30/16.
 */
public class MsgAspect {

    private static final Logger LOG = LoggerFactory.getLogger(MsgAspect.class);

    private SignUtils signUtils;

    public void setSignUtils(SignUtils signUtils) {
        this.signUtils = signUtils;
    }

    /*@Pointcut("within(@org.springframework.stereotype.Controller *) && !execution(* com.ehsy.svcreg.controller..*.*(..))")
    public void pointcut() {
    }*/

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        boolean isNormal = true;
        if (args != null) {
            RequestMsg requestMsg = enableRequestMsg(args);
            if (null != requestMsg) {
                isNormal = signUtils.checkSign(requestMsg);
            }
        }
        if (!isNormal) {
            //抛出异常
            throw new CMRuntimeException("签名校验异常");
        }
        return joinPoint.proceed();
    }

    private RequestMsg enableRequestMsg(Object[] args) {
        Map<String, Object> map = Maps.newHashMap();
        RequestMsg requestMsg = null;
        for (Object arg : args) {
            if (arg instanceof RequestMsg) {
                requestMsg = (RequestMsg) arg;
                SwapAreaUtils.setRequestMsg(requestMsg, MsgType.IN);
                break;
            } else if (arg instanceof String) {
                Pattern pattern = Pattern.compile("header|common|body");
                Matcher matcher = pattern.matcher((String) arg);
                if (matcher.find()) {
                    try {
                        requestMsg = JSON.parseObject((String) arg, RequestMsg.class);
                        SwapAreaUtils.setRequestMsg(requestMsg, MsgType.IN);
                        break;
                    } catch (Exception e) {
                        LOG.error("{}", e);
                    }
                }
            }
        }
        return requestMsg;
    }
}
