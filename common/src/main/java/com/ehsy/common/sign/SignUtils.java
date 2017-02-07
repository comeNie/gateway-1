package com.ehsy.common.sign;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.msg.request.RequestCommon;
import com.ehsy.common.msg.request.RequestMsg;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ehsy_it on 2016/7/13.
 */
public class SignUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SignUtils.class);

    private SignCache signCache;

    private String isCheck;

    public void setSignCache(SignCache signCache) {
        this.signCache = signCache;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    //serviceName=code
    public boolean checkSign(RequestMsg requestMsg) {
        if ("0".equals(isCheck)) {
            return true;
        }
        if (null == requestMsg) {
            LOG.info("请求报文异常");
            return false;
        }
        RequestCommon requestCommon = requestMsg.getCommon();
        if (null == requestCommon) {
            return false;
        }
        String body = JSON.toJSONString(requestMsg.getBody());
        String appID = requestCommon.getAppID();
        String nonceStr = requestCommon.getNonceStr();
        String sign = requestCommon.getSign();

        //根据appid获得对应的key值
        String key = signCache.getKeyMaps(appID);

        //签名：body&nonceStr&key
        String localSign = DigestUtils.md5Hex(body + "&" + nonceStr + "&" + key);
        //验证签名
        if (localSign.equals(sign)) {
            LOG.info("签名校验通过");
            return true;
        } else {
            LOG.info("签名校验失败");
            return false;
        }
    }
}
