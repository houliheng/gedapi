package com.resoft.postloan.accountCleanAllocate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.accountCleanAllocate.entity.AccountCleanAllocate;
import com.resoft.postloan.accountCleanAllocate.dao.AccountCleanAllocateDao;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.common.utils.Constants;

/**
 * 借后清收任务分配表Service
 * 
 * @author yanwanmei
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class AccountCleanAllocateService extends CrudService<AccountCleanAllocateDao, AccountCleanAllocate> {

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;

	public AccountCleanAllocate get(String id) {
		return super.get(id);
	}

	public List<AccountCleanAllocate> findList(AccountCleanAllocate accountCleanAllocate) {
		return super.findList(accountCleanAllocate);
	}

	public Page<AccountCleanAllocate> findPage(Page<AccountCleanAllocate> page, AccountCleanAllocate accountCleanAllocate) {
		return super.findPage(page, accountCleanAllocate);
	}

	@Transactional(value="PL",readOnly = false)
	public void save(AccountCleanAllocate accountCleanAllocate) {

		AccountCleanAllocate hasAccountCleanAllocate = getAccountCleanAllocateByContractNo(accountCleanAllocate.getContractNo());
		if (hasAccountCleanAllocate != null) {
			super.dao.update(accountCleanAllocate);
		} else {
			super.dao.insert(accountCleanAllocate);
		}
		// 更新acc_sta_contract_status的合同状态
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("repayContractStatus", Constants.CONTRACT_STATE_QSWJQ);
		param.put("contractNo", accountCleanAllocate.getContractNo());
		plContractService.updateContractStatusByContractNo(param);
		// 更新pl_check_25_allocate的状态
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("checkedType", Constants.HAS_BEEN_ALLOCATED);
		paraMap.put("contractNo", accountCleanAllocate.getContractNo());
		checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(paraMap);

	}

	@Transactional(value="PL",readOnly = false)
	public void delete(AccountCleanAllocate accountCleanAllocate) {
		super.delete(accountCleanAllocate);
	}

	public List<String> getContractNosByAllocateType(Map<String, String> paramMap) {
		return this.dao.getContractNosByAllocateType(paramMap);
	}

	@Transactional(value="PL",readOnly = false)
	public void updateAllocateTypeByContractNo(Map<String, String> paraMap) {
		this.dao.updateAllocateTypeByContractNo(paraMap);
	}

	public AccountCleanAllocate getAccountCleanAllocateByContractNo(String contractNo) {
		return this.dao.getAccountCleanAllocateByContractNo(contractNo);
	}

	// 清收
	public Page<MyMap> getAccountCleanPLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getAccountCleanPLContract(paramMap));
		return page;
	}

	@Transactional(value="PL",readOnly = false)
	public String updateStatus(String contractNo) {
		String flag = null;
		try {
			// 更新acc_sta_contract_status的合同状态
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("repayContractStatus", Constants.CONTRACT_STATE_QSYJQ);
			paraMap.put("contractNo", contractNo);
			plContractService.updateContractStatusByContractNo(paraMap);

			// 结束清收时，将清收状态改为结束清收
			Map<String, String> paraM = new HashMap<String, String>();
			paraM.put("allocateType", Constants.ACCOUNT_CLEAN_OVER);
			paraM.put("contractNo", contractNo);
			updateAllocateTypeByContractNo(paraM);
			flag = "true";
		} catch (Exception e) {
			flag = "false";
		}

		return flag;
	}
}