package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.dao.model.GedRepaymentRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedRepaymentRecordMapper {
    int countByExample(GedRepaymentRecordExample example);

    int deleteByExample(GedRepaymentRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedRepaymentRecord record);

    int insertSelective(GedRepaymentRecord record);

    List<GedRepaymentRecord> selectByExample(GedRepaymentRecordExample example);

    GedRepaymentRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedRepaymentRecord record, @Param("example") GedRepaymentRecordExample example);

    int updateByExample(@Param("record") GedRepaymentRecord record, @Param("example") GedRepaymentRecordExample example);

    int updateByPrimaryKeySelective(GedRepaymentRecord record);

    int updateByPrimaryKey(GedRepaymentRecord record);
}