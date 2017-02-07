package com.ehsy.svcreg.admin.controller;

import com.ehsy.svcreg.admin.model.SvcInfo;
import com.ehsy.svcreg.admin.service.ConsulService;
import com.ehsy.svcreg.client.DefaultClientFactory;
import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dummysvc")
public class DummySvcController {

    private static final Logger LOG = LoggerFactory.getLogger(DummySvcController.class);

    @Autowired
    private DefaultClientFactory clientFactory;

    @Autowired
    private ConsulService consulService;

    @RequestMapping(value="/json", headers = {"Content-Type=application/json"})
    @ResponseBody
    public String jsonReq(@RequestBody String json){
        LOG.debug(json);
        Svc svc = new Svc();
        svc.setComponent("component");
        svc.setHost("192.168.240.130");
        svc.setPath("path");
        svc.setPort(8080);
        svc.setSvcName("gbj test1");
        svc.setVersion("v1");
        clientFactory.getClient().registerService(svc);
        return json;
    }

    @RequestMapping(value="/test")
    @ResponseBody
    public String test(){
        LOG.debug("test");
        SvcInfo svcInfo = new SvcInfo();
        svcInfo.setDatacenter("dc1");
        svcInfo.setNode("web");
        svcInfo.setServiceId("192.168.4.197-8080-API组合服务");
        consulService.deregister(svcInfo);

        /*List<HealthCheck> serviceChecks2 = consulService.getServiceChecks("tomcat");
        for(HealthCheck healthCheck : serviceChecks2){
            consulService.deregister(healthCheck.getServiceId().get());
        }*/

        /*List<String> dcs = consulService.getDatacenters();

        List<Node> nodes = consulService.getNodes();

        Map<String,List<String>> map = consulService.getServices();

        List<CatalogService> service1 = consulService.getService("SR001");
        List<CatalogService> service2 = consulService.getService("index");
        List<CatalogService> service3 = consulService.getService("web2");
        List<CatalogService> service4 = consulService.getService("consul");

        CatalogNode node1 = consulService.getNode("gbj1");
        CatalogNode node2 = consulService.getNode("web1");

        List<HealthCheck> nodeChecks1 = consulService.getNodeChecks("gbj1");
        List<HealthCheck> nodeChecks2 = consulService.getNodeChecks("web1");

        List<HealthCheck> serviceChecks1 = consulService.getServiceChecks("consul");
        List<HealthCheck> serviceChecks2 = consulService.getServiceChecks("apidemo");
        List<HealthCheck> serviceChecks3 = consulService.getServiceChecks("web2");

        List<ServiceHealth> serviceInstances1 = consulService.getServiceInstances("consul");
        List<ServiceHealth> serviceInstances2 = consulService.getServiceInstances("apidemo");
        List<ServiceHealth> serviceInstances3 = consulService.getServiceInstances("web2");

        List<HealthCheck> fail = consulService.getChecksByState(State.FAIL.getName());
        List<HealthCheck> pass = consulService.getChecksByState(State.PASS.getName());
        List<HealthCheck> any = consulService.getChecksByState(State.ANY.getName());
        List<HealthCheck> unknow = consulService.getChecksByState(State.UNKNOWN.getName());
        List<HealthCheck> warn = consulService.getChecksByState(State.WARN.getName());*/

        System.out.println(11);
        return "success\n";
    }

}