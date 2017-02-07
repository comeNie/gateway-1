package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.svc.model.SvcRelation;
import com.ehsy.svccfg.svc.model.SvcRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcRelationMapper {
    int countByExample(SvcRelationExample example);

    int deleteByExample(SvcRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcRelation record);

    int insertSelective(SvcRelation record);

    List<SvcRelation> selectByExample(SvcRelationExample example);

    SvcRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcRelation record, @Param("example") SvcRelationExample example);

    int updateByExample(@Param("record") SvcRelation record, @Param("example") SvcRelationExample example);

    int updateByPrimaryKeySelective(SvcRelation record);

    int updateByPrimaryKey(SvcRelation record);
}