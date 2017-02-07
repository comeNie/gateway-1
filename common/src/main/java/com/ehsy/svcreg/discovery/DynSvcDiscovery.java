package com.ehsy.svcreg.discovery;

import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.CMUtils;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.svcreg.client.SvcRegClient;
import com.ehsy.svcreg.model.Svc;
import com.ehsy.svcreg.utils.SvcRegConst;
import com.ehsy.transport.SvcExecutor;
import com.ehsy.transport.data.SvcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class DynSvcDiscovery extends SvcDiscovery{

    private static final Logger LOG = LoggerFactory.getLogger(DynSvcDiscovery.class);

    private Lock lock = new ReentrantLock();

    private SvcExecutor svcExecutor;

    public void setSvcExecutor(SvcExecutor svcExecutor) {
        this.svcExecutor = svcExecutor;
    }

    @Override
    public List<Svc> discoverBySvcName(String svcName) {

        List<Svc> svcs = SvcLocalCache.getSvcList(svcName);
        if(svcs == null || svcs.size() == 0){
            try{
                lock.lock();
                if(svcs == null || svcs.size() == 0){
                    SvcRegClient svcRegClient = this.clientFactory.getClient();
                    svcs = svcRegClient.getHealthyServices(svcName);
                    //本地服务缓存
                    SvcLocalCache.setSvcList(svcName, svcs);
                    //注册监听
                    svcRegClient.subscribeService(svcName, new SvcNotifyListener());
                }
            }finally {
                lock.unlock();
            }
            svcs = SvcLocalCache.getSvcList(svcName);
        }
        return svcs;
    }

    @Override
    public List<Svc> discoverByCmpName(String cmpName) {

        LOG.debug("获取组件[{}]提供的所有服务定义", cmpName);

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("component", cmpName);
        SvcResult<List<Svc>> svcResult = this.svcExecutor.execute(SvcRegConst.CMP_CFG_SVC_CODE, pathParams, null, null, new TypeRef<ResponseMsg<List<Svc>>>(){});
        if (svcResult.isSuccess()) {
            return svcResult.getData().get("services");
        } else {
            LOG.error("获取远程组件发布的服务列表失败, 响应信息=[{}][{}]", svcResult.getRespCode(), svcResult.getRespMsg());
            CMUtils.throwException("获取远程服务列表失败");
        }
        return null;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
