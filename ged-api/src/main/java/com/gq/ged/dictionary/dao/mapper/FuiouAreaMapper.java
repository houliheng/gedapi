package com.gq.ged.dictionary.dao.mapper;

import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.dao.model.FuiouAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FuiouAreaMapper {
    int countByExample(FuiouAreaExample example);

    int deleteByExample(FuiouAreaExample example);

    int insert(FuiouArea record);

    int insertSelective(FuiouArea record);

    List<FuiouArea> selectByExample(FuiouAreaExample example);

    int updateByExampleSelective(@Param("record") FuiouArea record, @Param("example") FuiouAreaExample example);

    int updateByExample(@Param("record") FuiouArea record, @Param("example") FuiouAreaExample example);

    List<FuiouArea> getFuiouArea(@Param("code") String code);
}