package com.gq.ged.version.dao.mapper;

import com.gq.ged.version.dao.model.SystemVersion;
import com.gq.ged.version.dao.model.SystemVersionExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SystemVersionMapper {
    int countByExample(SystemVersionExample example);

    int deleteByExample(SystemVersionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemVersion record);

    int insertSelective(SystemVersion record);

    List<SystemVersion> selectByExample(SystemVersionExample example);

    SystemVersion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemVersion record, @Param("example") SystemVersionExample example);

    int updateByExample(@Param("record") SystemVersion record, @Param("example") SystemVersionExample example);

    int updateByPrimaryKeySelective(SystemVersion record);

    int updateByPrimaryKey(SystemVersion record);
}