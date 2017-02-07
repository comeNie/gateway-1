package com.ehsy.svcreg.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.utils.HttpUtil;
import com.ehsy.svcreg.admin.model.SvcInfo;
import com.ehsy.svcreg.admin.service.ConsulService;
import com.ehsy.svcreg.client.DefaultClientFactory;
import com.orbitz.consul.model.State;
import com.orbitz.consul.model.catalog.CatalogNode;
import com.orbitz.consul.model.catalog.CatalogService;
import com.orbitz.consul.model.health.HealthCheck;
import com.orbitz.consul.model.health.Node;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ehsy_it on 2016/7/4.
 */
@Component
public class ConsulServiceImpl implements ConsulService {

    private  static final String DEREGISTER_PATH = "/v1/catalog/deregister";

    @Autowired
    private DefaultClientFactory defaultClientFactory;
    @Value("${consul.agent.host}")
    private String host;
    @Value("${consul.agent.port}")
    private String port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void deregister(SvcInfo svcInfo) {
        String url = "http://" + host + ":" + port + DEREGISTER_PATH;
        HttpUtil.getInstance().sendHttpCoon(url, JSON.toJSONString(svcInfo));
    }

    @Override
    public List<String> getDatacenters() {
            return defaultClientFactory.getClient().getCataClient().getDatacenters();
    }

    @Override
    public List<Node> getNodes() {
        return defaultClientFactory.getClient().getCataClient().getNodes().getResponse();
    }

    @Override
    public Map<String, List<String>> getServices() {
        return defaultClientFactory.getClient().getCataClient().getServices().getResponse();
    }

    @Override
    public List<CatalogService> getService(String service) {
        return defaultClientFactory.getClient().getCataClient().getService(service).getResponse();
    }

    @Override
    public CatalogNode getNode(String node) {
        return defaultClientFactory.getClient().getCataClient().getNode(node).getResponse();
    }

    @Override
    public List<HealthCheck> getNodeChecks(String node) {
        return defaultClientFactory.getClient().getHealthClient().getNodeChecks(node).getResponse();
    }

    @Override
    public List<HealthCheck> getServiceChecks(String service) {
        return defaultClientFactory.getClient().getHealthClient().getServiceChecks(service).getResponse();
    }

    @Override
    public List<ServiceHealth> getServiceInstances(String service) {
        return defaultClientFactory.getClient().getHealthClient().getAllServiceInstances(service).getResponse();
    }

    @Override
    public List<HealthCheck> getChecksByState(String state) {
       return defaultClientFactory.getClient().getHealthClient().getChecksByState(State.fromName(state)).getResponse();
    }
}
