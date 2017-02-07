package com.ehsy.gateway.svc.cfg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.common.utils.Constants;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.svcreg.utils.SvcRegConst;
import com.ehsy.transport.SvcExecutor;
import com.ehsy.transport.data.SvcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 服务不多可直接缓存到本地
 */
@Component
public class SvcCfgCache {

    private static final Logger LOG = LoggerFactory.getLogger(SvcCfgCache.class);

    private static Map<String, String> cache = new ConcurrentHashMap<>();

    private static Lock lock = new ReentrantLock();

    private static SvcExecutor svcExecutor;

    public void setSvcExecutor(SvcExecutor svcExecutor) {
        this.svcExecutor = svcExecutor;
    }

    public static String getSvcCfg(String serviceName){

        //1. 从redis中获取
        //2. 或者直接请求配置中心获取服务描述
        String desc = cache.get(serviceName);
        if(StringUtils.isEmpty(desc)){
            try{
                lock.lock();
                desc = cache.get(serviceName);
                if(StringUtils.isEmpty(desc)){
                    JSONObject obj = new JSONObject();
                    obj.put(Constants.SVC_CFG_DESC_KEY, serviceName);
                    SvcResult<String> svcResult =  svcExecutor.execute(SvcRegConst.CMP_CFG_SVC_DESC_BY_CODE, Constants.SVC_CFG_DESC_KEY, serviceName, new TypeRef<ResponseMsg<String>>() {});
                    if (svcResult.isSuccess()) {
                        cache.put(serviceName, desc = svcResult.getData().get("description"));
                    } else {
                        LOG.error("获取服务描述失败, 响应信息=[{}][{}]", svcResult.getRespCode(), svcResult.getRespMsg());
                        CMUtils.throwException("获取服务描述失败", "服务码:", serviceName);
                    }
                }
            }finally {
                lock.unlock();
            }
        }
        return desc;
    }

}
