package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 5/31/16.
 */
public interface NotifyListener {

    /**
     * 消息通知
     * @param svcName
     * @param svcs
     */
    void notify(String svcName, List<Svc> svcs);

}
