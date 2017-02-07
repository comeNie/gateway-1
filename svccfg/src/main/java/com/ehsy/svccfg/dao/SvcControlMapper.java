package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.svc.model.SvcControl;
import com.ehsy.svccfg.svc.model.SvcControlExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcControlMapper {
    int countByExample(SvcControlExample example);

    int deleteByExample(SvcControlExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcControl record);

    int insertSelective(SvcControl record);

    List<SvcControl> selectByExample(SvcControlExample example);

    SvcControl selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcControl record, @Param("example") SvcControlExample example);

    int updateByExample(@Param("record") SvcControl record, @Param("example") SvcControlExample example);

    int updateByPrimaryKeySelective(SvcControl record);

    int updateByPrimaryKey(SvcControl record);
}