package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.GedRechargeRecord;
import com.gq.ged.order.dao.model.GedRechargeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedRechargeRecordMapper {
    int countByExample(GedRechargeRecordExample example);

    int deleteByExample(GedRechargeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedRechargeRecord record);

    int insertSelective(GedRechargeRecord record);

    List<GedRechargeRecord> selectByExample(GedRechargeRecordExample example);

    GedRechargeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedRechargeRecord record, @Param("example") GedRechargeRecordExample example);

    int updateByExample(@Param("record") GedRechargeRecord record, @Param("example") GedRechargeRecordExample example);

    int updateByPrimaryKeySelective(GedRechargeRecord record);

    int updateByPrimaryKey(GedRechargeRecord record);
}