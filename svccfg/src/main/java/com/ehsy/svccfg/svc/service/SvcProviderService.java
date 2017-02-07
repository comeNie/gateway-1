package com.ehsy.svccfg.svc.service;

import com.ehsy.svccfg.svc.model.SvcProvider;

import java.util.List;

/**
 * Created by ehsy_it on 2016/6/22.
 */
public interface SvcProviderService {

    List<SvcProvider> getProviders();

    String getIdByComAbb(String abb);
}
