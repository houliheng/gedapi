package com.resoft.credit.creditAndLine.dao.creditCust;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCustLoan;

/**
 * 征信个人贷款明细DAO接口
 * @author wuxi01
 * @version 2016-03-17
 */
@MyBatisDao
public interface CreditCustLoanDao extends CrudDao<CreditCustLoan> {
	/**
	 * 批量删除
	 * @param params
	 */
	public void banchDelete(Map<String, Object> params);
	
	/**
	 * 计算个人征信中贷款总额度、贷款总余额、贷款逾期总额
	 * @param creditCustLoanId
	 * @return
	 */
	public Map<String, Object> getSumElements(String creditCustId);
	
}