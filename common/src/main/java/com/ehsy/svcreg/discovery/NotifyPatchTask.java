package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.client.SvcRegClient;
import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ehsy_it on 2016/7/1.
 */
public class NotifyPatchTask {
    private static final Logger LOG = LoggerFactory.getLogger(NotifyPatchTask.class);

    ExecutorService pool = Executors.newCachedThreadPool();

    private DiscoveryFactory discoveryFactory;

    public void setDiscoveryFactory(DiscoveryFactory discoveryFactory) {
        this.discoveryFactory = discoveryFactory;
    }

    public void doCheck(){
       /* if(this.discoveryFactory != null){
            Discovery discovery = this.discoveryFactory.getDiscovery();
            if(discovery.isDynamic()){
                LOG.info("定时同步订阅服务列表, 补偿机制");
                ConcurrentHashMap<String, List<Svc>> svcMaps = SvcLocalCache.getSvcMaps();
                for(Map.Entry<String, List<Svc>> svcMap : svcMaps.entrySet()){
                    pool.execute(new NotifyPatchRunnable(svcMap.getKey(), svcMap.getValue(), discovery));
                }
            }
        }*/
        System.out.println(111);
    }

    class NotifyPatchRunnable implements Runnable{
        private String svcName;
        private List<Svc> svcList;
        private Discovery discovery;

        NotifyPatchRunnable(String svcName, List<Svc> svcList, Discovery discovery){
            this.svcName = svcName;
            this.svcList = svcList;
            this.discovery = discovery;
        }

        @Override
        public void run() {
            //得到远程组件发布的服务列表
            SvcRegClient svcRegClient = ((SvcDiscovery)discovery).clientFactory.getClient();
            List<Svc> listNow = svcRegClient.getHealthyServices(svcName);
            //比对列表，未缓存的需要进行缓存
            doCompare(svcName, svcList, listNow);
        }

        public void doCompare(String svcName, List<Svc> svcList, List<Svc> listNow){
            if(CollectionUtils.isEmpty(listNow)){
                SvcLocalCache.delSvcList(svcName);
                return;
            }
            int count = 0;
            for(Svc svcNow : listNow){
                for(Svc svcOld : svcList){
                    if(svcNow.getComponent() != null && svcNow.getComponent().equals(svcOld.getComponent())
                            && svcNow.getHost() != null && svcNow.getHost().equals(svcOld.getHost())
                            && svcNow.getPath() != null && svcNow.getPath().equals(svcOld.getPath())
                            && svcNow.getPort() != null && svcNow.getPort().equals(svcOld.getPort())
                            && svcNow.getProtocol() != null && svcNow.getProtocol().equals(svcOld.getProtocol())
                            && svcNow.getVersion() != null && svcNow.getVersion().equals(svcOld.getVersion())){
                        count += 1;
                    }
                    break;
                }
            }
            if(count != listNow.size()){
                SvcLocalCache.setSvcList(svcName, listNow);
            }
        }
    }
}
