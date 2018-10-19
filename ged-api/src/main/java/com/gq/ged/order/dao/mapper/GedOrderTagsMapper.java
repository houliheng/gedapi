package com.gq.ged.order.dao.mapper;

import com.gq.ged.order.dao.model.GedOrderTags;
import com.gq.ged.order.dao.model.GedOrderTagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedOrderTagsMapper {
    int countByExample(GedOrderTagsExample example);

    int deleteByExample(GedOrderTagsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedOrderTags record);

    int insertSelective(GedOrderTags record);

    List<GedOrderTags> selectByExample(GedOrderTagsExample example);

    GedOrderTags selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedOrderTags record, @Param("example") GedOrderTagsExample example);

    int updateByExample(@Param("record") GedOrderTags record, @Param("example") GedOrderTagsExample example);

    int updateByPrimaryKeySelective(GedOrderTags record);

    int updateByPrimaryKey(GedOrderTags record);
}