package com.ehsy.svccfg.svc.service.impl;

import com.ehsy.svccfg.dao.SvcSignMapper;
import com.ehsy.svccfg.model.SvcSign;
import com.ehsy.svccfg.svc.model.SvcSignExample;
import com.ehsy.svccfg.svc.service.SvcSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by ehsy_it on 2016/7/13.
 */
@Service("svcSignServiceImpl")
public class SvcSignServiceImpl implements SvcSignService {

    @Autowired
    private SvcSignMapper svcSignMapper;

    public SvcSign getSvcSignByAppID(String appID){
        SvcSign svcSign = new SvcSign();
        svcSign.setAppid(appID);
        svcSign.setStatus(SvcSign.SVC_SIGN_COMP_OK);
        SvcSignExample svcSignExample = convertSvcSignExample(svcSign);
        List<SvcSign> list = svcSignMapper.selectByExample(svcSignExample);
        if(!CollectionUtils.isEmpty(list)){
            return  list.get(0);
        }else{
            return null;
        }
    }

    private SvcSignExample convertSvcSignExample(SvcSign svcSign) {
        SvcSignExample svcSignExample = new SvcSignExample();
        SvcSignExample.Criteria criteria = svcSignExample.createCriteria();
        String appId = svcSign.getAppid();
        if(!StringUtils.isEmpty(appId)){
            criteria.andAppidEqualTo(appId);
        }
        String privateKey = svcSign.getPrivateKey();
        if(!StringUtils.isEmpty(privateKey)){
            criteria.andPrivateKeyEqualTo(privateKey);
        }
        String component = svcSign.getComponent();
        if(!StringUtils.isEmpty(component)){
            criteria.andComponentEqualTo(component);
        }
        String status = svcSign.getStatus();
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        return svcSignExample;
    }
}
