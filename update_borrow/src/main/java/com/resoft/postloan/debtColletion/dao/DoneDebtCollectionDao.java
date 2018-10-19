package com.resoft.postloan.debtColletion.dao;

import com.resoft.postloan.debtColletion.entity.DoneDebtCollection;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同待催收统计DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DoneDebtCollectionDao extends CrudDao<DoneDebtCollection> {


}