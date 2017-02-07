package com.ehsy.svcreg.consul;

import com.ehsy.common.utils.CMUtils;
import com.ehsy.svcreg.client.AbstractClient;
import com.ehsy.svcreg.discovery.NotifyListener;
import com.ehsy.svcreg.model.Svc;
import com.ehsy.svcreg.utils.SvcRegConst;
import com.google.common.collect.Maps;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.*;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 5/30/16.
 */
public class ConsulClient extends AbstractClient {

    private static final Logger LOG = LoggerFactory.getLogger(ConsulClient.class);

    private String projectContext;
    private int serverPort;
    private Consul consul;
    private AgentClient agentClient;
    private HealthClient healthClient;
    private CatalogClient catalogClient;
    private Map<String, ServiceHealthCache> svcHealthCacheMap = Maps.newHashMap();
    private Map<String, ConsulCache.Listener<ServiceHealthKey, ServiceHealth>> svcHealthListenerMap = Maps.newHashMap();
    private Lock lock = new ReentrantLock();

    public ConsulClient(String host, int port, String projectContext, int serverPort){
        if(StringUtils.isEmpty(host)){
            host = SvcRegConst.DEFAULT_HOST;
        }
        if(port == 0){
            port = SvcRegConst.DEFAULT_CONSUL_PORT;
        }
        if(StringUtils.isEmpty(projectContext)){
            CMUtils.throwException("project context path is required!");
        }
        this.projectContext = projectContext;
        this.serverPort = serverPort;

        consul = Consul.builder().withHostAndPort(HostAndPort.fromParts(host, port)).withReadTimeoutMillis(SvcRegConst.READ_TIMEOUT_MILLS).build();
        agentClient = consul.agentClient();
        healthClient = consul.healthClient();
        catalogClient = consul.catalogClient();
    }

    public Consul getConsul() {
        return consul;
    }

    @Override
    public void registerService(Svc svc){
        String checkUrl = this.getCheckUrl(serverPort, projectContext);
        Registration.RegCheck regCheck = Registration.RegCheck.http(checkUrl, SvcRegConst.CHECK_INTERVAL);
        ImmutableRegistration.Builder builder = ImmutableRegistration.builder();
        builder.id(ConsulUtils.getServiceId(svc.getHost(), svc.getPort(), svc.getSvcName())); //ID
        builder.name(svc.getSvcName()); //service name
        builder.address(svc.getHost()); //service endpoint
        builder.port(svc.getPort()); // port
        builder.addTags(svc.getComponent(), "/" + projectContext + svc.getPath()); //component
        builder.check(regCheck); //health check

        Registration registration = builder.build();

        this.agentClient.register(registration);
    }

    @Override
    public void deregisterService(Svc svc){
        this.agentClient.deregister(ConsulUtils.getServiceId(svc.getHost(), svc.getPort(), svc.getSvcName()));
    }

    @Override
    public List<Svc> getHealthyServices(String serviceName){
        List<Svc> svcs = new ArrayList<>();

        HealthClient healthClient = consul.healthClient();
        // discover only "passing" nodes
        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances(serviceName).getResponse();

        for(ServiceHealth serviceHealth : nodes){
            Svc svc = new Svc();
            Service service = serviceHealth.getService();
            svc.setSvcName(service.getService());
            svc.setHost(service.getAddress());
            svc.setPort(service.getPort());
            List<String> tags = service.getTags();
            if(tags != null && tags.size() == 2){
                svc.setComponent(tags.get(0));
                svc.setPath(tags.get(1));
            }
            svcs.add(svc);
        }

        return svcs;
    }

    @Override
    public void subscribeService(final String serviceName, final NotifyListener notifyListener){
        if(this.svcHealthCacheMap.containsKey(serviceName)){
            //已进行subscribe,直接返回
            return;
        }
        try {
            lock.lock();
            if(!this.svcHealthCacheMap.containsKey(serviceName)){
                ServiceHealthCache svcHealthCache = ServiceHealthCache.newCache(healthClient, serviceName);
                this.svcHealthCacheMap.put(serviceName, svcHealthCache);

                ConsulCache.Listener<ServiceHealthKey, ServiceHealth> listener = new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {
                    @Override
                    public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
                        List<Svc> svcs = new ArrayList<>();
                        for (ServiceHealth serviceHealth : newValues.values()) {
                            Service service = serviceHealth.getService();
                            Svc svc = new Svc();
                            svc.setSvcName(service.getService());
                            svc.setHost(service.getAddress());
                            svc.setPort(service.getPort());
                            List<String> tags = service.getTags();
                            if(tags != null && tags.size() == 2){
                                svc.setComponent(tags.get(0));
                                svc.setPath(tags.get(1));
                            }
                            svcs.add(svc);
                        }
                        notifyListener.notify(serviceName, svcs);
                    }
                };

                this.svcHealthListenerMap.put(serviceName, listener);

                svcHealthCache.addListener(listener);

                /*if (!svcHealthCache.awaitInitialized(10, TimeUnit.SECONDS)) {
                    LOG.error("cache initialization failed");
                    return;
                }*/
                svcHealthCache.start();
            }
        }catch(Exception e){
            LOG.error("{}", e);
        }finally {
            lock.unlock();;
        }
    }

    @Override
    public void unsubscribeService(String serviceName){
        try {
            lock.lock();
            if(this.svcHealthCacheMap.containsKey(serviceName) && this.svcHealthListenerMap.containsKey(serviceName)){
                ServiceHealthCache svcHealthCache = this.svcHealthCacheMap.get(serviceName);
                ConsulCache.Listener<ServiceHealthKey, ServiceHealth> listener = this.svcHealthListenerMap.get(serviceName);
                if(svcHealthCache != null && listener != null)
                    svcHealthCache.removeListener(listener);
            }
        }finally {
            lock.unlock();;
        }
    }

    @Override
    public void passService(Svc svc){
        try {
            this.agentClient.pass(ConsulUtils.getServiceId(svc.getHost(), svc.getPort(), svc.getSvcName()));
        } catch (NotRegisteredException e) {
            LOG.error("{}", e);
        }
    }

    @Override
    public void failService(Svc svc){
        try {
            this.agentClient.fail(ConsulUtils.getServiceId(svc.getHost(), svc.getPort(), svc.getSvcName()));
        } catch (NotRegisteredException e) {
            LOG.error("{}", e);
        }
    }

    @Override
    public List<Svc> getRegisteredServices(){
        List<Svc> svcs = new ArrayList<>();
        Map<String, Service> svcMap = this.agentClient.getServices();
        for(Service service : svcMap.values()){
            if(service.getService().equals("consul")){
                continue;
            }
            Svc svc = new Svc();
            svc.setSvcName(service.getService());
            svc.setHost(service.getAddress());
            svc.setPort(service.getPort());
            List<String> tags = service.getTags();
            if(tags != null && tags.size() == 2){
                svc.setComponent(tags.get(0));
                svc.setPath(tags.get(1));
            }
            svcs.add(svc);
        }
        return svcs;
    }

    @Override
    public CatalogClient getCataClient() {
        return this.catalogClient;
    }

    @Override
    public HealthClient getHealthClient() {
        return this.healthClient;
    }

    @Override
    public AgentClient getAgentClient() {
        return this.agentClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String host;
        private int port;
        private String projectContext;
        private int serverPort;

        public Builder withHost(String host){
            this.host = host;
            return this;
        }

        public Builder withPort(String port){
            if(CMUtils.isNumeric(port)){
                this.port = Integer.valueOf(port);
            }else{
                this.port = SvcRegConst.DEFAULT_CONSUL_PORT;
            }
            return this;
        }

        public Builder withProjectContext(String projectContext){
            this.projectContext = projectContext;
            return this;
        }

        public Builder withServerPort(String serverPort){
            if(CMUtils.isNumeric(serverPort)){
                this.serverPort = Integer.valueOf(serverPort);
            }else{
                this.serverPort = SvcRegConst.DEFAULT_SERVER_PORT;
            }
            return this;
        }

        public ConsulClient build(){
            return new ConsulClient(this.host, this.port, this.projectContext, this.serverPort);
        }
    }
}
