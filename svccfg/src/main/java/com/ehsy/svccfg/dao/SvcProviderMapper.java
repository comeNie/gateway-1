package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.svc.model.SvcProvider;
import com.ehsy.svccfg.svc.model.SvcProviderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcProviderMapper {
    int countByExample(SvcProviderExample example);

    int deleteByExample(SvcProviderExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcProvider record);

    int insertSelective(SvcProvider record);

    List<SvcProvider> selectByExample(SvcProviderExample example);

    SvcProvider selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcProvider record, @Param("example") SvcProviderExample example);

    int updateByExample(@Param("record") SvcProvider record, @Param("example") SvcProviderExample example);

    int updateByPrimaryKeySelective(SvcProvider record);

    int updateByPrimaryKey(SvcProvider record);
}