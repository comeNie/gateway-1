package com.ehsy.svccfg.svc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.page.Page;
import com.ehsy.common.utils.IdUtils;
import com.ehsy.gateway.ComponentMsg;
import com.ehsy.svccfg.dao.SvcDescInfoMapper;
import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svccfg.svc.model.SvcAccess;
import com.ehsy.svccfg.svc.model.SvcDescInfoExample;
import com.ehsy.svccfg.svc.service.SvcAccessService;
import com.ehsy.svccfg.svc.service.SvcDescInfoService;
import com.ehsy.svccfg.svc.service.SvcProviderService;
import com.ehsy.svcreg.model.Svc;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ehsy_it on 2016/6/22.
 */
@Service
public class SvcDescInfoServiceImpl implements SvcDescInfoService {

    @Autowired
    private SvcDescInfoMapper svcDescInfoMapper;

    @Autowired
    private SvcAccessService svcAccessService;

    @Autowired
    private SvcProviderService svcProviderService;

    /**
     * 根据服务码得到描述文件报文
     * @param code
     * @return
     */
    @Override
    public String getDescByCode(String code) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andCodeEqualTo(code);
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExampleWithBLOBs(svcDescInfoExample);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0).getDescription();
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public int insert(SvcDescInfo svcDescInfo) {
        int flg = 0;
        String code = svcDescInfo.getCode();
        Preconditions.checkArgument((null != svcDescInfo)&& !StringUtils.isEmpty(code), "新增时：服务码不能为空");
        boolean svcIsExist = checkSvcIsExist(code);
        if(!svcIsExist){
            String id = IdUtils.makeUUID();
            Date date = new Date();

            svcDescInfo.setId(id);
            svcDescInfo.setStatus(SvcDescInfo.SVC_DESC_STATUS_RUNNING);
            svcDescInfo.setCreateTime(date);
            svcDescInfo.setUpdateTime(date);
            flg = svcDescInfoMapper.insert(svcDescInfo);

            SvcAccess svcAccess = new SvcAccess();
            svcAccess.setCreateTime(date);
            svcAccess.setCompAbb(svcDescInfo.getComponent());
            svcAccess.setCompId(svcProviderService.getIdByComAbb(svcDescInfo.getComponent()));
            svcAccess.setSvrId(id);
            svcAccess.setSvrCode(svcDescInfo.getCode());
            svcAccessService.insert(svcAccess);
        }
        return flg;
    }

    public boolean checkSvcIsExist(String code, String ... ids){
        List<String> idList = Arrays.asList(ids);
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        SvcDescInfoExample.Criteria criteria = svcDescInfoExample.createCriteria().andCodeEqualTo(code);
        if(!CollectionUtils.isEmpty(idList)&& null != idList.get(0)){
            criteria.andIdNotIn(idList);
        }
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExample(svcDescInfoExample);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public int updateSelective(SvcDescInfo svcDescInfo) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andCodeEqualTo(svcDescInfo.getCode());
        return svcDescInfoMapper.updateByExampleSelective(svcDescInfo, svcDescInfoExample);
    }

    @Override
    public List<SvcDescInfo> getDescs() {
        return svcDescInfoMapper.selectByExampleWithBLOBs(null);
    }

    @Override
    public boolean checkAliasExist(String alias, String... ids) {
        List<String> idList = Arrays.asList(ids);
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        SvcDescInfoExample.Criteria criteria = svcDescInfoExample.createCriteria().andAliasEqualTo(alias);
        if(!CollectionUtils.isEmpty(idList)&& null != idList.get(0)){
            criteria.andIdNotIn(idList);
        }
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExample(svcDescInfoExample);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<SvcDescInfo> getAllServices() {
        return svcDescInfoMapper.selectByExample(null);
    }

    @Override
    public List<Svc> getServicesByCmp(String component) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andComponentEqualTo(component);
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExampleWithBLOBs(svcDescInfoExample);
        List<Svc> listExt = new ArrayList<>(list.size());
        for(SvcDescInfo svcDescInfo : list){
            //only need basic svc info...
            Svc svc = new Svc();
            svc.setComponent(svcDescInfo.getComponent());
            svc.setVersion(svcDescInfo.getVersion());
            svc.setPath(svcDescInfo.getPath());
            svc.setProtocol(svcDescInfo.getProtocol());
            svc.setSvcName(svcDescInfo.getCode());
            listExt.add(svc);
        }
        return listExt;
    }

    @Override
    public int stop(String code) {
        SvcDescInfo svcDescInfo = new SvcDescInfo();
        svcDescInfo.setStatus(SvcDescInfo.SVC_DESC_STATUS_STOP);
        svcDescInfo.setUpdateTime(new Date());
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andCodeEqualTo(code);
        return svcDescInfoMapper.updateByExampleSelective(svcDescInfo, svcDescInfoExample);
    }

    @Override
    public SvcDescInfo getSvcDescInfoById(String id) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andIdEqualTo(id);
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExampleWithBLOBs(svcDescInfoExample);
        return convertDec2Com(list);
    }

    private SvcDescInfo convertDec2Com(List<SvcDescInfo> list) {
        if(!CollectionUtils.isEmpty(list)){
            SvcDescInfo svcDescInfo = list.get(0);
            ComponentMsg componentMsg = JSONObject.parseObject(svcDescInfo.getDescription(), ComponentMsg.class);
            svcDescInfo.setComponentMsg(componentMsg);
            return svcDescInfo;
        }else{
            return null;
        }
    }

    @Override
    public SvcDescInfo getSvcDescInfoByCode(String code) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andCodeEqualTo(code);
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExampleWithBLOBs(svcDescInfoExample);
        return convertDec2Com(list);
    }

    @Override
    public int postEdit(SvcDescInfo svcDescInfo) {
        int flg = 0;
        String code = svcDescInfo.getCode();
        Preconditions.checkArgument((null != svcDescInfo)&& !StringUtils.isEmpty(code), "编辑时：服务码不能为空");
        boolean svcIsExist = checkSvcIsExist(code, svcDescInfo.getId());
        if(!svcIsExist){
//            svcDescInfo.setDescription(JSONObject.toJSONString(JSON.parse(svcDescInfo.getDescription())));
            svcDescInfo.setUpdateTime(new Date());
            SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
            svcDescInfoExample.createCriteria().andIdEqualTo(svcDescInfo.getId());
            flg = svcDescInfoMapper.updateByExampleSelective(svcDescInfo, svcDescInfoExample);
        }
        return flg;
    }

    @Override
    public Page<SvcDescInfo> list(SvcDescInfo svcDescInfo, int pageNo) {
        Page<SvcDescInfo> page = new Page<SvcDescInfo>();
        page.setPageNo(pageNo);
        page.setPageSize(10);
        page.start();
        SvcDescInfoExample svcDescInfoExample = getSvcDescInfoExample(svcDescInfo);
        List<SvcDescInfo> list = svcDescInfoMapper.selectByExampleWithBLOBs(svcDescInfoExample);
        page.end();
        page.setResults(list);
        return page;
    }

    @Override
    public int del(String id) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        svcDescInfoExample.createCriteria().andIdEqualTo(id);
        return svcDescInfoMapper.deleteByExample(svcDescInfoExample);
    }

    private SvcDescInfoExample getSvcDescInfoExample(SvcDescInfo svcDescInfo) {
        SvcDescInfoExample svcDescInfoExample = new SvcDescInfoExample();
        SvcDescInfoExample.Criteria criteria = svcDescInfoExample.createCriteria();
        svcDescInfoExample.setOrderByClause("update_time desc");

        String code = svcDescInfo.getCode();
        if(!StringUtils.isEmpty(code)){
            criteria.andCodeEqualTo(code);
        }

        String svcname = svcDescInfo.getSvcname();
        if(!StringUtils.isEmpty(svcname)){
            criteria.andSvcnameEqualTo(svcname);
        }

        String version = svcDescInfo.getVersion();
        if(!StringUtils.isEmpty(version)){
            criteria.andVersionEqualTo(version);
        }

        String component = svcDescInfo.getComponent();
        if(!StringUtils.isEmpty(component)){
            criteria.andComponentEqualTo(component);
        }

        String status = svcDescInfo.getStatus();
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        Date createTime = svcDescInfo.getCreateTime();
        if(null != createTime){
            criteria.andCreateTimeGreaterThanOrEqualTo(createTime);
        }

        String category = svcDescInfo.getCategory();
        if(!StringUtils.isEmpty(category)){
            criteria.andCategoryEqualTo(category);
        }

        String protocol = svcDescInfo.getProtocol();
        if(!StringUtils.isEmpty(protocol)){
            criteria.andProtocolEqualTo(protocol);
        }

        String alias = svcDescInfo.getAlias();
        if(!StringUtils.isEmpty(alias)){
            criteria.andAliasEqualTo(alias);
        }

        String path = svcDescInfo.getPath();
        if(!StringUtils.isEmpty(path)){
            criteria.andPathEqualTo(path);
        }
        return svcDescInfoExample;
    }

}
