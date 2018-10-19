package com.gq.ged.file.dao.mapper;

import com.gq.ged.file.dao.model.SystemFile;
import com.gq.ged.file.dao.model.SystemFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemFileMapper {
    int countByExample(SystemFileExample example);

    int deleteByExample(SystemFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemFile record);

    int insertSelective(SystemFile record);

    List<SystemFile> selectByExample(SystemFileExample example);

    SystemFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemFile record, @Param("example") SystemFileExample example);

    int updateByExample(@Param("record") SystemFile record, @Param("example") SystemFileExample example);

    int updateByPrimaryKeySelective(SystemFile record);

    int updateByPrimaryKey(SystemFile record);
}