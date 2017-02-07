package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.svc.model.SvcAccess;
import com.ehsy.svccfg.svc.model.SvcAccessExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcAccessMapper {
    int countByExample(SvcAccessExample example);

    int deleteByExample(SvcAccessExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcAccess record);

    int insertSelective(SvcAccess record);

    List<SvcAccess> selectByExample(SvcAccessExample example);

    SvcAccess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcAccess record, @Param("example") SvcAccessExample example);

    int updateByExample(@Param("record") SvcAccess record, @Param("example") SvcAccessExample example);

    int updateByPrimaryKeySelective(SvcAccess record);

    int updateByPrimaryKey(SvcAccess record);
}