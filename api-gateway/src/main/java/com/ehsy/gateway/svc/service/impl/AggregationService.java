package com.ehsy.gateway.svc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.request.RequestMsg;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.gateway.ComponentMsg;
import com.ehsy.gateway.svc.cfg.SvcCfgCache;
import com.ehsy.gateway.AggregatedMsg;
import com.ehsy.gateway.AggregationMsg;
import com.ehsy.gateway.svc.parser.SvcMsgParser;
import com.ehsy.gateway.svc.service.APIService;
import com.ehsy.transport.SvcExecutor;
import com.ehsy.transport.data.SvcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by zhuangmg on 6/16/16.
 */
@Service("aggregationService")
public class AggregationService implements APIService {
    private static final Logger LOG = LoggerFactory.getLogger(AggregationService.class);

    @Autowired
    private SvcExecutor svcExecutor;

    @Override
    public ResponseMsg<?> doService(final String requestMsg, String svcDesc) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<Map<String, Map<String, Object>>> completionService = new ExecutorCompletionService<>(executorService);
        AggregationMsg aggregationMsg = JSON.parseObject(svcDesc, AggregationMsg.class);
        //得到聚合中的服务列表
        List<AggregatedMsg> serviceList = aggregationMsg.getServiceList();
        //循环聚合服务描述文件列表
        for(final AggregatedMsg aggregatedMsg : serviceList){
            completionService.submit(new CmpSvcCallable(svcExecutor, aggregatedMsg, requestMsg));
        }

        Map<String, Map<String, Object>> respBody = new HashMap<>();
        for(int i=0;i<serviceList.size();i++){
            AggregatedMsg aggregatedMsg = serviceList.get(i);
            try {
                Future<Map<String, Map<String, Object>>> future = completionService.take();
                respBody.putAll(future.get());
            } catch (Exception e) {
                LOG.error("{}",e);
                if(aggregatedMsg.getDegrade().equalsIgnoreCase("true")){
                    LOG.warn("服务[{}]可降级,忽略服务调用异常", aggregatedMsg);
                }else{
                    CMUtils.throwException(e.getMessage());
                }
            }
        }
        executorService.shutdown();

        return MsgUtils.newSuccessResponseMsg(respBody);
    }

    class CmpSvcCallable implements Callable<Map<String, Map<String, Object>>>{

        private SvcExecutor svcExecutor;
        private AggregatedMsg aggregatedMsg;
        private String requestMsg;

        public CmpSvcCallable(SvcExecutor svcExecutor, AggregatedMsg aggregatedMsg, String requestMsg){
            this.svcExecutor = svcExecutor;
            this.aggregatedMsg = aggregatedMsg;
            this.requestMsg = requestMsg;
        }

        @Override
        public Map<String, Map<String, Object>> call() throws Exception {
            String code = aggregatedMsg.getCode();
            LOG.debug("调用聚合服务, 服务码 = {}", code);
            final String ns = aggregatedMsg.getNamespace();
            JSONObject nsObject = JSON.parseObject(requestMsg).getJSONObject("body").getJSONObject(ns);
            String cmpSvcDesc = SvcCfgCache.getSvcCfg(code);
            ComponentMsg componentMsg = JSON.parseObject(cmpSvcDesc, ComponentMsg.class);
            if (null == componentMsg) {
                CMUtils.throwException("组件服务描述文件未配置：", code);
            }
            Map<String, Object> reqBody = SvcMsgParser.convertRequestBody(nsObject, componentMsg);

            RequestMsg cmpRequestMsg = MsgUtils.newRequestMsg(componentMsg.getCode(), reqBody);
            //请求数据
            SvcResult<Object> svcResult = this.svcExecutor.execute(componentMsg.getCode(), cmpRequestMsg, new TypeRef<ResponseMsg<Object>>(){});
            if (svcResult.isSuccess()) {
                //代理服务响应报文
                Map<String, Object> respBody = SvcMsgParser.convertResponseBody(JSON.toJSONString(svcResult.getData()), aggregatedMsg);
                Map<String, Map<String, Object>> resultMap = new HashMap<>();
                resultMap.put(aggregatedMsg.getNamespace(), respBody);
                return resultMap;
            } else {
                LOG.error("代理服务请求组件服务失败, 响应信息=[{}][{}]", svcResult.getRespCode(), svcResult.getRespMsg());
                if(this.aggregatedMsg.getDegrade().equalsIgnoreCase("true")){
                    LOG.warn("服务[{}]可降级,忽略服务调用异常", this.aggregatedMsg);
                }else{
                    CMUtils.throwException("代理服务请求组件服务失败");
                }
            }
            return null;
        }
    }
}
