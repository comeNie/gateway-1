package com.ehsy.svccfg.dao;

import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svccfg.svc.model.SvcDescInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SvcDescInfoMapper {
    int countByExample(SvcDescInfoExample example);

    int deleteByExample(SvcDescInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(SvcDescInfo record);

    int insertSelective(SvcDescInfo record);

    List<SvcDescInfo> selectByExampleWithBLOBs(SvcDescInfoExample example);

    List<SvcDescInfo> selectByExample(SvcDescInfoExample example);

    SvcDescInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SvcDescInfo record, @Param("example") SvcDescInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") SvcDescInfo record, @Param("example") SvcDescInfoExample example);

    int updateByExample(@Param("record") SvcDescInfo record, @Param("example") SvcDescInfoExample example);

    int updateByPrimaryKeySelective(SvcDescInfo record);

    int updateByPrimaryKeyWithBLOBs(SvcDescInfo record);

    int updateByPrimaryKey(SvcDescInfo record);
}