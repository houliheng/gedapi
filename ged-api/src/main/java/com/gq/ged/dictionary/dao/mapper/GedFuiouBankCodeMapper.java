package com.gq.ged.dictionary.dao.mapper;

import com.gq.ged.dictionary.dao.model.GedFuiouBankCode;
import com.gq.ged.dictionary.dao.model.GedFuiouBankCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedFuiouBankCodeMapper {
    int countByExample(GedFuiouBankCodeExample example);

    int deleteByExample(GedFuiouBankCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GedFuiouBankCode record);

    int insertSelective(GedFuiouBankCode record);

    List<GedFuiouBankCode> selectByExample(GedFuiouBankCodeExample example);

    GedFuiouBankCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GedFuiouBankCode record, @Param("example") GedFuiouBankCodeExample example);

    int updateByExample(@Param("record") GedFuiouBankCode record, @Param("example") GedFuiouBankCodeExample example);

    int updateByPrimaryKeySelective(GedFuiouBankCode record);

    int updateByPrimaryKey(GedFuiouBankCode record);
}