package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhuangmg on 6/1/16.
 */
public class SvcNotifyListener implements NotifyListener {

    private static final Logger LOG = LoggerFactory.getLogger(SvcNotifyListener.class);

    @Override
    public void notify(String svcName, List<Svc> svcs) {

        LOG.debug("notify......name=[{}], size=[{}]", svcName, svcs.size());

        SvcLocalCache.setSvcList(svcName, svcs);
    }
}
