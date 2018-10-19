package com.resoft.accounting.deductApply.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductApply.dao.DeductApplyDao;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.staRepayPlanStatus.dao.StaRepayPlanStatusDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 还款划扣Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class DeductApplyService extends CrudService<DeductApplyDao, DeductApply> {

	@Autowired
	private ContractLockDao contractLockDao;

	@Autowired
	private StaRepayPlanStatusDao staRepayPlanStatusDao;

	public DeductApply get(String id) {
		return super.get(id);
	}

	public List<DeductApply> findList(DeductApply deductApply) {
		return super.findList(deductApply);
	}

	public Page<DeductApply> findPage(Page<DeductApply> page, DeductApply deductApply) {
		return super.findPage(page, deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(DeductApply deductApply) {
		super.save(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveDeductApply(DeductApply deductApply) {
		deductApply.setIsLock("1");
		save(deductApply);
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(deductApply.getContractNo());
		contractLockDao.saveLockInfo(contractLock);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(DeductApply deductApply) {
		super.delete(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public DeductApply getDeductApplyByContractNoAndPeriodNum(DeductApply deductApply) {
		return this.dao.getDeductApplyByContractNoAndPeriodNum(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveDeductApplyToDeductResult(DeductApply deductApply) {
		this.dao.saveDeductApplyToDeductResult(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<DeductApply> validateIsLockByContractNoAndPeriodNum(DeductApply deductApply) {
		return this.dao.validateIsLockByContractNo(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView enterAccountValidate(DeductApply deductApply) {
		AjaxView ajaxView = new AjaxView();
		List<String> capitalInterestRepayDates= Lists.newArrayList();
		ContractLock contractLock = null;
		String status = null;
		capitalInterestRepayDates = staRepayPlanStatusDao.getPreviousStageStatusByContractNo(deductApply);
		if(capitalInterestRepayDates != null && capitalInterestRepayDates.size() != 0){
			for(String date:capitalInterestRepayDates){
				if(StringUtils.isNull(date)){
					ajaxView.setFailed().setMessage("尚有本息未结清的期数，请优先处理");
					return ajaxView;
				}
			}
		}
		status = staRepayPlanStatusDao.getPreviousStageStatusByByContractNoAndPeriodNum(deductApply);
		if (Constants.PERIOD_STATE_ZCJQ.equals(status) || Constants.PERIOD_STATE_YQJQ.equals(status) || Constants.PERIOD_STATE_TQHK.equals(status)) {
			ajaxView.setFailed().setMessage("此期还款已还清。");
		} else {
			contractLock = new ContractLock();
			contractLock.setContractNo(deductApply.getContractNo());
			contractLock = contractLockDao.validateIsLock(contractLock);
			if (contractLock != null) {
				ajaxView.setFailed().setMessage("此合同号存在正在申请中的操作");
			} else {
				ajaxView.setSuccess();
			}
		}
		return ajaxView;
	}

	/**
	 * 查询冠e通同步的银行卡信息
	 */
	public List<Map<String, Object>> findBankDataList(Map<String, Object> params ){
		return this.dao.findBankDataList(params);
	}
	
	
	/**
	 * 查询冠e通同步的银行卡信息
	 */
	public Map<String, Object> queryMobileNumAndIdNum(String contractNo){
		return this.dao.queryMobileNumAndIdNum(contractNo);
	}
	
}