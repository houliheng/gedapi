package com.gq.ged.underImport.dao.mapper;

import com.gq.ged.underImport.dao.model.GedImportGetOrder;
import com.gq.ged.underImport.dao.model.GedImportGetOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GedImportGetOrderMapper {
    int countByExample(GedImportGetOrderExample example);

    int deleteByExample(GedImportGetOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GedImportGetOrder record);

    int insertSelective(GedImportGetOrder record);

    List<GedImportGetOrder> selectByExample(GedImportGetOrderExample example);

    GedImportGetOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GedImportGetOrder record, @Param("example") GedImportGetOrderExample example);

    int updateByExample(@Param("record") GedImportGetOrder record, @Param("example") GedImportGetOrderExample example);

    int updateByPrimaryKeySelective(GedImportGetOrder record);

    int updateByPrimaryKey(GedImportGetOrder record);

    int batchInsertOfflineOrder(@Param("list")List<GedImportGetOrder> list);
}