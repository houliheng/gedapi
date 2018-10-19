package com.resoft.multds.accounting.PLContract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@MyBatisDao
public interface PLContractDao extends CrudDao<PLContract> {
	/**
	 * 根据合同编号查询合同基本信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<MyMap> findCheckDailyContractBaseInfo(String contractNo);

	public List<PLContract> getHasAllocatedContract(PLContract plContract);

	public List<MyMap> getToCheck25PLContract(MyMap paramMap);

	/**
	 * 查询资产管理合同信息列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MyMap> findAssetContractList(MyMap paramMap);

	public void updateContractStatusByContractNo(Map<String, Object> param);

	/**
	 * 获取指定合同未偿还金额
	 */
	public BigDecimal getOutStandingCapitalAmount(String contractNo);

	public void updateAccStaRepayPlanStatus(Map<String, String> param);

	public void insertAccDeductApply(Map<String, String> param);

	public String getPeriodNumByContractNo(String contractNo);

	public void insertAccDeductResult(Map<String, String> param);

	/**
	 * 根据合同编号查询身份证号
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<String> findIdNumByContractNo(String contractNo);

	public Map<String, Object> getAccStaContractStatusByContractNo(String contractNo);

	/**
	 * 根据合同编号查询合同信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<PLContract> findListByContractNo(String contractNo);

}