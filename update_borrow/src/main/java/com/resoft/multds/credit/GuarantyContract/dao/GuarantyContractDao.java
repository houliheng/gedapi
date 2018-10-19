package com.resoft.multds.credit.GuarantyContract.dao;


import com.resoft.multds.credit.GuarantyContract.entity.GuarantyContract;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 根绝角色类型查询人员姓名DAO接口
 * @author zhaohuakui
 * @version 2016-05-25
 */
@MyBatisDao
public interface GuarantyContractDao extends CrudDao<GuarantyContract>{
}