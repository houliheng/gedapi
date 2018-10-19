package com.resoft.multds.accounting.PLContract.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.accounting.PLContract.dao.PLContractDao;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class PLContractService extends CrudService<PLContractDao, PLContract> {

	public PLContract get(String id) {
		return super.get(id);
	}

	public List<PLContract> findList(PLContract plContract) {
		return super.findList(plContract);
	}

	public Page<PLContract> findPage(Page<PLContract> page, PLContract plContract) {
		return super.findPage(page, plContract);
	}

	/**
	 * 查询详情页合同基本信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<MyMap> findCheckDailyContractBaseInfo(String contractNo) {
		return super.dao.findCheckDailyContractBaseInfo(contractNo);
	}

	public Page<MyMap> getToCheck25PLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getToCheck25PLContract(paramMap));
		return page;
	}

	/**
	 * 抵（质）押物管理 合同信息
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public Page<MyMap> findAssetContractList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(super.dao.findAssetContractList(paramMap));
		return page;
	}

	@Transactional(readOnly = false)
	public void updateContractStatusByContractNo(Map<String, Object> param) {
		this.dao.updateContractStatusByContractNo(param);
	}

	/**
	 * 获取指定合同未偿还金额
	 */
	public BigDecimal getOutStandingCapitalAmount(String contractNo) {
		return this.dao.getOutStandingCapitalAmount(contractNo);
	}

	@Transactional(readOnly = false)
	public void updateAccStaRepayPlanStatus(Map<String, String> param) {
		this.dao.updateAccStaRepayPlanStatus(param);
	}

	@Transactional(readOnly = false)
	public void insertAccDeductApply(Map<String, String> param) {
		this.dao.insertAccDeductApply(param);
	}

	@Transactional(readOnly = false)
	public void insertAccDeductResult(Map<String, String> param) {
		this.dao.insertAccDeductResult(param);
	}

	public String getPeriodNumByContractNo(String contractNo) {
		return this.dao.getPeriodNumByContractNo(contractNo);
	}

	/**
	 * 根据合同编号查询身份证号
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<String> findIdNumByContractNo(String contractNo) {
		return super.dao.findIdNumByContractNo(contractNo);
	}

	public Map<String, Object> getAccStaContractStatusByContractNo(String contractNo) {
		return this.dao.getAccStaContractStatusByContractNo(contractNo);
	}

	/**
	 * 根据合同编号查询合同信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<PLContract> findListByContractNo(String contractNo) {
		return super.dao.findListByContractNo(contractNo);
	}
}
