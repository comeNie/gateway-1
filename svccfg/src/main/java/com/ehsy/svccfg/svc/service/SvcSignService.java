package com.ehsy.svccfg.svc.service;

import com.ehsy.svccfg.model.SvcSign;

/**
 * Created by ehsy_it on 2016/7/13.
 */
public interface SvcSignService {

    SvcSign getSvcSignByAppID(String appID);
}
