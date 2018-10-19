package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.UserRechargeRecord;
import com.gq.ged.order.dao.model.UserRechargeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRechargeRecordMapper {
    int countByExample(UserRechargeRecordExample example);

    int deleteByExample(UserRechargeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRechargeRecord record);

    int insertSelective(UserRechargeRecord record);

    List<UserRechargeRecord> selectByExample(UserRechargeRecordExample example);

    UserRechargeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRechargeRecord record, @Param("example") UserRechargeRecordExample example);

    int updateByExample(@Param("record") UserRechargeRecord record, @Param("example") UserRechargeRecordExample example);

    int updateByPrimaryKeySelective(UserRechargeRecord record);

    int updateByPrimaryKey(UserRechargeRecord record);
}