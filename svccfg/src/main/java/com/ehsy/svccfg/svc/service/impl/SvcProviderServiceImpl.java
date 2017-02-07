package com.ehsy.svccfg.svc.service.impl;

import com.ehsy.svccfg.dao.SvcProviderMapper;
import com.ehsy.svccfg.svc.model.SvcProvider;
import com.ehsy.svccfg.svc.model.SvcProviderExample;
import com.ehsy.svccfg.svc.service.SvcProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ehsy_it on 2016/7/7.
 */
@Service
public class SvcProviderServiceImpl implements SvcProviderService {
    @Autowired
    private SvcProviderMapper svcProviderMapper;

    @Override
    public List<SvcProvider> getProviders() {
        return svcProviderMapper.selectByExample(null);
    }

    @Override
    public String getIdByComAbb(String abb) {
        SvcProviderExample svcProviderExample = new SvcProviderExample();
        svcProviderExample.createCriteria().andAbbEqualTo(abb);
        List<SvcProvider> list = svcProviderMapper.selectByExample(svcProviderExample);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0).getId();
        }else{
            return null;
        }
    }

}
