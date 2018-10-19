package com.gq.ged.city.dao.mapper;

import com.gq.ged.city.dao.model.LoanCityInfo;
import com.gq.ged.city.dao.model.LoanCityInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoanCityInfoMapper {
    int countByExample(LoanCityInfoExample example);

    int deleteByExample(LoanCityInfoExample example);

    int deleteByPrimaryKey(Long cityid);

    int insert(LoanCityInfo record);

    int insertSelective(LoanCityInfo record);

    List<LoanCityInfo> selectByExample(LoanCityInfoExample example);

    LoanCityInfo selectByPrimaryKey(Long cityid);

    int updateByExampleSelective(@Param("record") LoanCityInfo record, @Param("example") LoanCityInfoExample example);

    int updateByExample(@Param("record") LoanCityInfo record, @Param("example") LoanCityInfoExample example);

    int updateByPrimaryKeySelective(LoanCityInfo record);

    int updateByPrimaryKey(LoanCityInfo record);
}