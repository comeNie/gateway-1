package com.ehsy.svccfg.svc.service.impl;

import com.alibaba.fastjson.JSON;
import com.ehsy.common.utils.IdUtils;
import com.ehsy.gateway.AggregatedMsg;
import com.ehsy.gateway.AggregationMsg;
import com.ehsy.gateway.ComponentMsg;
import com.ehsy.svccfg.dao.SvcRelationMapper;
import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svccfg.svc.model.SvcRelation;
import com.ehsy.svccfg.svc.model.SvcRelationExample;
import com.ehsy.svccfg.svc.service.SvcDescInfoService;
import com.ehsy.svccfg.svc.service.SvcRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ehsy_it on 2016/7/7.
 */
@Service
public class SvcRelationServiceImpl implements SvcRelationService {
    @Autowired
    private SvcRelationMapper svcRelationMapper;

    @Autowired
    private SvcDescInfoService svcDescInfoService;

    @Override
    public List<SvcRelation> getRelations(String code) {
        SvcRelationExample example = new SvcRelationExample();
        example.createCriteria().andSvcCodeMasterEqualTo(code);
        return svcRelationMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public int setRelation(SvcRelation svcRelation) {
        if (checkExist(svcRelation)) {
            return 0;
        } else {
            synchDescDescriptionByCode(svcRelation.getType(), svcRelation.getSvcCodeMaster(), svcRelation.getSvcCodeBranch(), svcRelation.getDegrade());
            //保存关系
            svcRelation.setId(IdUtils.makeUUID());
            svcRelation.setCreateTime(new Date());
            return svcRelationMapper.insertSelective(svcRelation);
        }
    }

    private void synchDescDescriptionByCode(String type, String svcCodeMaster, String svcCodeBranch, String degrade) {
        SvcDescInfo masterInfo = null;
        if (SvcRelation.SVC_RELATION_TYPE_PROVIDER.equals(type)) {
            //修改描述文件中"proxiedService":""--》"proxiedService":"PC0000001"
            masterInfo = svcDescInfoService.getSvcDescInfoByCode(svcCodeMaster);
            String description = masterInfo.getDescription();
            if (!StringUtils.isEmpty(description)) {
                masterInfo.setDescription(description.replaceAll("\\r|\\n|\\t", "").replaceAll("\"proxiedService\":\\s*\"\\w*\"", "\"proxiedService\":\"" + svcCodeBranch + "\""));
            }
        }else if(SvcRelation.Svc_Relation_type_AGGREGATION.equals(type)){
            //在"serviceList"中添加被代理项
            masterInfo = svcDescInfoService.getSvcDescInfoByCode(svcCodeMaster);
            AggregationMsg aggregationMsg = JSON.parseObject(masterInfo.getDescription(), AggregationMsg.class);
            SvcDescInfo branchInfo = svcDescInfoService.getSvcDescInfoByCode(svcCodeBranch);
            ComponentMsg componentMsg = branchInfo.getComponentMsg();
            List<AggregatedMsg> aggregatedMsgList = aggregationMsg.getServiceList();
            AggregatedMsg aggregatedMsg = new AggregatedMsg();
            aggregatedMsg.setCode(branchInfo.getCode());
            aggregatedMsg.setNamespace(branchInfo.getAlias());
            //TODO
            aggregatedMsg.setDegrade(degrade);
            aggregatedMsg.setRequestMsg(componentMsg.getRequestMsg());
            aggregatedMsg.setResponseMsg(componentMsg.getResponseMsg());
            aggregatedMsgList.add(aggregatedMsg);
            aggregationMsg.setServiceList(aggregatedMsgList);
            masterInfo.setDescription(JSON.toJSONString(aggregationMsg));
        }
        if(null != masterInfo){
            masterInfo.setUpdateTime(new Date());
            svcDescInfoService.updateSelective(masterInfo);
        }

    }

    private boolean checkExist(SvcRelation svcRelation) {
        if (null == svcRelation) {
            return false;
        }
        SvcRelationExample example = new SvcRelationExample();
        SvcRelationExample.Criteria criteria = example.createCriteria();
        String svcCodeMaster = svcRelation.getSvcCodeMaster();
        if (!StringUtils.isEmpty(svcCodeMaster)) {
            criteria.andSvcCodeMasterEqualTo(svcCodeMaster);
        }
        String svcCodeBranch = svcRelation.getSvcCodeBranch();
        if (!StringUtils.isEmpty(svcCodeBranch)) {
            criteria.andSvcCodeBranchEqualTo(svcCodeBranch);
        }
        List<SvcRelation> list = svcRelationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    @Override
    public int updateRelation(SvcRelation svcRelation) {
        //代理时同步描述文件中的proxiedService
        synchDescDescriptionByCode(svcRelation.getType(), svcRelation.getSvcCodeMaster(), svcRelation.getSvcCodeBranch(), svcRelation.getDegrade());
        SvcRelationExample example = new SvcRelationExample();
        example.createCriteria().andSvcCodeMasterEqualTo(svcRelation.getSvcCodeMaster());
        svcRelation.setCreateTime(new Date());
        return svcRelationMapper.updateByExampleSelective(svcRelation, example);
    }

    @Transactional
    @Override
    public int delRelation(SvcRelation svcRelation) {
        synchDescDescriptionByCode(svcRelation.getSvcCodeMaster(), svcRelation.getSvcCodeBranch());
        SvcRelationExample example = new SvcRelationExample();
        example.createCriteria().andSvcCodeMasterEqualTo(svcRelation.getSvcCodeMaster())
                .andSvcCodeBranchEqualTo(svcRelation.getSvcCodeBranch());
        return svcRelationMapper.deleteByExample(example);
    }

    private void synchDescDescriptionByCode(String svcCodeMaster, String svcCodeBranch) {
        SvcDescInfo masterInfo = svcDescInfoService.getSvcDescInfoByCode(svcCodeMaster);
        AggregationMsg aggregationMsg = JSON.parseObject(masterInfo.getDescription(), AggregationMsg.class);
        List<AggregatedMsg> aggregatedMsgList = aggregationMsg.getServiceList();
        for(AggregatedMsg aggregatedMsg : aggregatedMsgList){
            if(svcCodeBranch.equals(aggregatedMsg.getCode())){
                aggregatedMsgList.remove(aggregatedMsg);
                break;
            }
        }
        aggregationMsg.setServiceList(aggregatedMsgList);
        masterInfo.setDescription(JSON.toJSONString(aggregationMsg));
        svcDescInfoService.updateSelective(masterInfo);
    }
}
