package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.GedOrderRepaymentPlan;
import com.gq.ged.order.dao.model.GedOrderRepaymentPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedOrderRepaymentPlanMapper {
    int countByExample(GedOrderRepaymentPlanExample example);

    int deleteByExample(GedOrderRepaymentPlanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedOrderRepaymentPlan record);

    int insertSelective(GedOrderRepaymentPlan record);

    List<GedOrderRepaymentPlan> selectByExample(GedOrderRepaymentPlanExample example);

    GedOrderRepaymentPlan selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedOrderRepaymentPlan record, @Param("example") GedOrderRepaymentPlanExample example);

    int updateByExample(@Param("record") GedOrderRepaymentPlan record, @Param("example") GedOrderRepaymentPlanExample example);

    int updateByPrimaryKeySelective(GedOrderRepaymentPlan record);

    int updateByPrimaryKey(GedOrderRepaymentPlan record);
}