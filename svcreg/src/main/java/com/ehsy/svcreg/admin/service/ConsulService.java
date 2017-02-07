package com.ehsy.svcreg.admin.service;

import com.ehsy.svcreg.admin.model.SvcInfo;
import com.orbitz.consul.model.catalog.CatalogNode;
import com.orbitz.consul.model.catalog.CatalogService;
import com.orbitz.consul.model.health.HealthCheck;
import com.orbitz.consul.model.health.Node;
import com.orbitz.consul.model.health.ServiceHealth;

import java.util.List;
import java.util.Map;

/**
 * Created by ehsy_it on 2016/7/4.
 */
public interface ConsulService {

    /**
     * Deregisters a node, service, or check
     */
    public void deregister(SvcInfo svcInfo);

    /**
     *  Lists known datacenters
     * @return
     */
    public List<String> getDatacenters();

    /**
     * Lists nodes in a given DC
     * @return
     */
    public List<Node> getNodes();

    /**
     * Lists services in a given DC
     */
    public Map<String, List<String>> getServices();

    /**
     * Lists the nodes in a given service
     * @return
     */
    public List<CatalogService> getService(String service);

    /**
     * Lists the services provided by a node
     */
    public CatalogNode getNode(String node);
    


    /**
     * Returns the health info of a node
     */
    public List<HealthCheck> getNodeChecks(String node);

    /**
     * Returns the checks of a service
     */
    public List<HealthCheck> getServiceChecks(String service);

    /**
     * Returns the nodes and health info of a service
     */
    public List<ServiceHealth> getServiceInstances(String service);

    /**
     * Returns the checks in a given state
     */
    public List<HealthCheck> getChecksByState(String state);

}
