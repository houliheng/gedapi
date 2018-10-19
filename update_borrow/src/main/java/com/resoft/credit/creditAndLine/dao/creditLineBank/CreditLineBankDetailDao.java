package com.resoft.credit.creditAndLine.dao.creditLineBank;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBankDetail;

/**
 * 流水信息列表DAO接口
 * @author wuxi01
 * @version 2016-02-26
 */
@MyBatisDao
public interface CreditLineBankDetailDao extends CrudDao<CreditLineBankDetail> {
	
	/**
	 * 根据银行卡ID计算出进项总额、出项总额、月均有效流水
	 * @param param
	 * @return
	 */
	public Map<String, String> getAvgAndSum(Map<String, String> param);
	
	/**
	 * 批量删除
	 * @param params
	 */
	public void banchDelete(Map<String, Object> params);
	
	/**
	 * 获取一个进件下，单张银行卡明细最大数量
	 * @param applyNo
	 * @return
	 */
	public List<String> getBankDetail(Map<String, String> params);
	
	/**
	 * 根据客户ID查询流水月份List
	 * @return
	 */
	public List<String> findLineMonthList(Map<String, String> params);
	
	/**
	 * 根绝银行卡ID，流水月份查询流水信息
	 * @param params
	 * @return
	 */
	public List<CreditLineBankDetail> findLineBankDetailByLineMonth(Map<String, String> params);
}
