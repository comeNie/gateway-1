package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.model.SvcSign;
import com.ehsy.svccfg.svc.model.SvcSignExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcSignMapper {
    int countByExample(SvcSignExample example);

    int deleteByExample(SvcSignExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcSign record);

    int insertSelective(SvcSign record);

    List<SvcSign> selectByExample(SvcSignExample example);

    SvcSign selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcSign record, @Param("example") SvcSignExample example);

    int updateByExample(@Param("record") SvcSign record, @Param("example") SvcSignExample example);

    int updateByPrimaryKeySelective(SvcSign record);

    int updateByPrimaryKey(SvcSign record);
}