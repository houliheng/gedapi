package com.resoft.postloan.debtColletion.dao;

import java.util.Map;

import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同带催收统计DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DebtCollectionDao extends CrudDao<DebtCollection> {

	/**
	 * 给制订合同分配人员处理
	 */
	public void updateCollectionStatusAndType(Map<String, Object> params);

	/**
	 * 风险级别设置和转办的申请
	 * 
	 * @param toDoDebtCollection
	 */
	public void updateToDoDebtCollection(DebtCollection debtCollection);

	/**
	 * 修改催收字数
	 * @param param
	 */
	public void updateCurrCollectionStatus(Map<String, Object> param);
	
	/**
	 * 法务转清收用（查询数据）
	 */
	public DebtCollection getDebtCollectionByLegalToClean(Map<String, Object> param);

}