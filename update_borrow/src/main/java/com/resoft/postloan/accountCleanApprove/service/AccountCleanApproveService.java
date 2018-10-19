package com.resoft.postloan.accountCleanApprove.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.postloan.accountCleanApprove.dao.AccountCleanApproveDao;
import com.resoft.postloan.accountCleanApprove.entity.AccountCleanApprove;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 账务清收审批Service
 * 
 * @author yanwanmei
 * @version 2016-06-21
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class AccountCleanApproveService extends CrudService<AccountCleanApproveDao, AccountCleanApprove> {
	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;

	@Autowired
	private DebtCollectionService debtCollectionService;

	public AccountCleanApprove get(String id) {
		return super.get(id);
	}

	public List<AccountCleanApprove> findList(AccountCleanApprove accountCleanApprove) {
		return super.findList(accountCleanApprove);
	}

	public Page<AccountCleanApprove> findPage(Page<AccountCleanApprove> page, AccountCleanApprove accountCleanApprove) {
		return super.findPage(page, accountCleanApprove);
	}

	@Transactional(value = "PL", readOnly = false)
	public String save(AccountCleanApprove accountCleanApprove, String status) {
		String legalFlag = "";
		Map<String, Object> params = Maps.newHashMap();
		params.put("currCollectionType", "5");
		params.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_YFPDCS);
		params.put("contractNo", accountCleanApprove.getContractNo());
		DebtCollection debtCollection = debtCollectionService.getDebtCollectionByLegalToClean(params);
		if (debtCollection != null) {
			legalFlag = "legalToClean";
		}
		super.save(accountCleanApprove);
		// 25日复核走清收流程后，将pl_check_25_allocate的CHECKED_TYPE改为已清收
		if (Constants.TO_BE_CHECKED.equals(accountCleanApprove.getCheckResult())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("contractNo", accountCleanApprove.getContractNo());
			param.put("checkedType", Constants.ACCOUNT_CLEAN_APPROVE);
			checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(param);
		}
		// 如果清收任务审批通过，则需要将总部审核状态设置为待审核
		if (Constants.DQ_STATUS.equalsIgnoreCase(status) && Constants.PASS_THROUGH.equals(accountCleanApprove.getCheckResult())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("contractNo", accountCleanApprove.getContractNo());
			param.put("hqCheckResult", Constants.TO_BE_CHECKED);
			this.updateHQCheckResultByContractNo(param);
		}

		// 如果清收任务被打回，则更新pl_check_25_allocate的CHECKED_TYPE改为清收打回
		if (Constants.BEAT_BACK.equals(accountCleanApprove.getCheckResult()) || Constants.BEAT_BACK.equals(accountCleanApprove.getHqCheckResult())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("contractNo", accountCleanApprove.getContractNo());
			param.put("checkedType", Constants.ACCOUNT_CLEAN_BEAT_BACK);
			checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(param);
		}
		if (Constants.PASS_THROUGH.equals(accountCleanApprove.getHqCheckResult())) {
			params.put("debtId", debtCollection.getId());
			params.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_FWZQS);
			debtCollectionService.updateCurrCollectionStatus(params);
		}
		return legalFlag;
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(AccountCleanApprove accountCleanApprove) {
		super.delete(accountCleanApprove);
	}

	// 清收任务分配
	public Page<MyMap> getAccountCleanApprovePLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getAccountCleanApprovePLContract(paramMap));
		return page;
	}

	@Transactional(value = "PL", readOnly = false)
	public void updateHQCheckResultByContractNo(Map<String, String> param) {
		this.dao.updateHQCheckResultByContractNo(param);
	}

	// 已分配
	public Page<MyMap> getAccountCleanApproveList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getAccountCleanApproveList(paramMap));
		return page;
	}
}