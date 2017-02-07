package com.ehsy.gateway.svc.service.impl;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.gateway.ComponentMsg;
import com.ehsy.gateway.svc.cfg.SvcCfgCache;
import com.ehsy.gateway.ProxyMsg;
import com.ehsy.gateway.svc.parser.SvcMsgParser;
import com.ehsy.gateway.svc.service.APIService;
import com.ehsy.transport.SvcExecutor;
import com.ehsy.transport.data.SvcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhuangmg on 6/16/16.
 */
@Service("proxyService")
public class ProxyService implements APIService {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyService.class);

    @Autowired
    private SvcExecutor svcExecutor;

    @Override
    public ResponseMsg<?> doService(final String requestMsg, String svcDesc) {

        ProxyMsg proxyMsg = JSON.parseObject(svcDesc, ProxyMsg.class);
        //根据代理描述得到服务组件描述
        String proxiedService = proxyMsg.getProxiedService();

        String cmpSvcDesc = SvcCfgCache.getSvcCfg(proxiedService);

        ComponentMsg componentMsg = JSON.parseObject(cmpSvcDesc, ComponentMsg.class);

        if (null == componentMsg) {
            CMUtils.throwException("组件服务描述文件未配置：", proxiedService);
        }

        Map<String, Object> reqBody = SvcMsgParser.convertRequestBody(JSON.parseObject(requestMsg).getJSONObject("body"), componentMsg);

        RequestMsg cmpRequestMsg = MsgUtils.newRequestMsg(componentMsg.getCode(), reqBody);
        //请求数据
        SvcResult<Object> svcResult = this.svcExecutor.execute(componentMsg.getCode(), cmpRequestMsg, new TypeRef<ResponseMsg<Object>>(){});

        if (svcResult.isSuccess()) {
            //代理服务响应报文
            Map<String, Object> respBody = SvcMsgParser.convertResponseBody(JSON.toJSONString(svcResult.getData()), proxyMsg);

            return MsgUtils.newSuccessResponseMsg(respBody);
        } else {
            LOG.error("代理服务请求组件服务失败, 响应信息=[{}][{}]", svcResult.getRespCode(), svcResult.getRespMsg());
            CMUtils.throwException("代理服务请求组件服务失败");
        }

        return null;
    }

}
