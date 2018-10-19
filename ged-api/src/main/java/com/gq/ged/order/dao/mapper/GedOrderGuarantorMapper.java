package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.GedOrderGuarantor;
import com.gq.ged.order.dao.model.GedOrderGuarantorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedOrderGuarantorMapper {
    int countByExample(GedOrderGuarantorExample example);

    int deleteByExample(GedOrderGuarantorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedOrderGuarantor record);

    int insertSelective(GedOrderGuarantor record);

    List<GedOrderGuarantor> selectByExample(GedOrderGuarantorExample example);

    GedOrderGuarantor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedOrderGuarantor record, @Param("example") GedOrderGuarantorExample example);

    int updateByExample(@Param("record") GedOrderGuarantor record, @Param("example") GedOrderGuarantorExample example);

    int updateByPrimaryKeySelective(GedOrderGuarantor record);

    int updateByPrimaryKey(GedOrderGuarantor record);
}