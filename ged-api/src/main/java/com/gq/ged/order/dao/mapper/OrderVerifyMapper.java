package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.OrderVerify;
import com.gq.ged.order.dao.model.OrderVerifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderVerifyMapper {
    int countByExample(OrderVerifyExample example);

    int deleteByExample(OrderVerifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderVerify record);

    int insertSelective(OrderVerify record);

    List<OrderVerify> selectByExample(OrderVerifyExample example);

    OrderVerify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderVerify record, @Param("example") OrderVerifyExample example);

    int updateByExample(@Param("record") OrderVerify record, @Param("example") OrderVerifyExample example);

    int updateByPrimaryKeySelective(OrderVerify record);

    int updateByPrimaryKey(OrderVerify record);
}