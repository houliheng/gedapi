package com.resoft.credit.GedImportGedOrder.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.GedImportGedOrder.entity.CreGedImportGetOrder;

/**
 * 冠e通线下数据导入给冠e贷DAO接口
 * @author lb
 * @version 2018-07-18
 */
@MyBatisDao
public interface CreGedImportGetOrderDao extends CrudDao<CreGedImportGetOrder> {

    /**
     * 根据合同号查询数据
     * @param contractNo
     * @return
     */
    public CreGedImportGetOrder findCreGedImportByContractNo(String contractNo);

    /**
     * 导入数据
     */
     public void importContract(CreGedImportGetOrder getOrder);

    /**
     * 修改数据
     */
     public void updateImportedContract(CreGedImportGetOrder getOrder);



}