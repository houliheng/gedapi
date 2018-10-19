package com.gq.ged.underImport.service;

import com.gq.ged.underImport.dao.model.GedImportGetOrder;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * Created by Levi on 2018/7/18.
 */
public interface GedImportGetOrderService {

    /**
     * 将借款系统传输过来的数据放入mq
     * @param jsonRes
     */
    public void savePushInfos(String  jsonRes);

    /**
     * 保存列表
     * @param jsonRes
     */
    public void saveList(String  jsonRes);

    /**
     * 保存
     * @return
     */
    public Integer save(GedImportGetOrder gedImportGetOrder);


    public Integer  updateById(GedImportGetOrder gedImportGetOrder);

    /**
     * 定时器处理  未处理的单子
     */
    public void dealUnderInfos();

    /**
     * 根据查询条件查询记录
     * @param gedImportGetOrder
     * @return
     */
    public List<GedImportGetOrder> getListByCondition(GedImportGetOrder gedImportGetOrder);

}
