package com.ehsy.svccfg.svc.service.impl;

import com.ehsy.common.utils.IdUtils;
import com.ehsy.svccfg.dao.SvcAccessMapper;
import com.ehsy.svccfg.svc.model.SvcAccess;
import com.ehsy.svccfg.svc.service.SvcAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by ehsy_it on 2016/7/7.
 */
@Service
public class SvcAccessServiceImpl implements SvcAccessService {

    @Autowired
    private SvcAccessMapper svcAccessMapper;

    public int insert(SvcAccess svcAccess){
        if(StringUtils.isEmpty(svcAccess.getId())){
            svcAccess.setId(IdUtils.makeUUID());
        }
        return svcAccessMapper.insertSelective(svcAccess);
    }

}
