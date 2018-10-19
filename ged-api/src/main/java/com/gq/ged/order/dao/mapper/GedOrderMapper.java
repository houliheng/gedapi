package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.controller.res.OrderGroupInfo;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GedOrderMapper {
    int countByExample(GedOrderExample example);

    int deleteByExample(GedOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedOrder record);

    int insertSelective(GedOrder record);

    List<GedOrder> selectByExample(GedOrderExample example);

    GedOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedOrder record, @Param("example") GedOrderExample example);

    int updateByExample(@Param("record") GedOrder record, @Param("example") GedOrderExample example);

    int updateByPrimaryKeySelective(GedOrder record);

    int updateByPrimaryKey(GedOrder record);

    int updateByOrderCodeSelective(GedOrder record);

    List<OrderGroupInfo> selectJoinOrderList(@Param("userId") Long userId);

    void updateOrderByContractNo(@Param("contractNo") String contractNo);

    /**
     * 更新其他平台借款金额
     * @param order 订单实体
     */
    void updateOtherLoanAmount(GedOrder order);
}