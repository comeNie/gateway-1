package com.ehsy.svcreg.context;

import com.ehsy.svcreg.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhuangmg on 6/2/16.
 */
public class ContextInitializer implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ContextInitializer.class);

    private String registerDynamic; //服务动态注册标志
    private Registry registry; //注册服务

    public void setRegisterDynamic(String registerDynamic) {
        this.registerDynamic = registerDynamic;
    }

    public void setRegistry(Registry registry){
        this.registry = registry;
    }

    /**
     * 初始化服务注册上下文, 根据参数判断是否需要开启动态注册服务
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.debug("dynamic flag = {}", registerDynamic);
        try{
            if("1".equals(registerDynamic)){
                LOG.info("开启动态注册");
                this.registry.register();
            }else{
                LOG.info("关闭动态注册");
            }
        }catch (Exception e){
            LOG.error("{}", e);
            //try again
            LOG.info("try register service again...");
            tryAgain();
        }
    }

    private void tryAgain(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new RegistryTry(this.registry), 30, TimeUnit.SECONDS);
    }

    class RegistryTry implements Runnable{
        private Registry registry;
        public RegistryTry(Registry registry){
           this.registry = registry;
        }
        @Override
        public void run() {
            LOG.debug("run register service again.......");
            this.registry.register();
        }
    }
}
