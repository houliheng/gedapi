package com.resoft.postloan.accountClean.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.accountClean.dao.AccountCleanDao;
import com.resoft.postloan.accountClean.entity.AccountClean;
import com.resoft.postloan.accountCleanAllocate.service.AccountCleanAllocateService;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 账务清收Service
 * @author yanwanmei
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class AccountCleanService extends CrudService<AccountCleanDao, AccountClean> {
	private static final Logger logger = LoggerFactory.getLogger(AccountCleanService.class);

	@Autowired
	private PLContractService plContractService;
	@Autowired
	private AccountCleanAllocateService accountCleanAllocateService;
	public AccountClean get(String id) {
		return super.get(id);
	}
	
	public List<AccountClean> findList(AccountClean accountClean) {
		return super.findList(accountClean);
	}
	
	public Page<AccountClean> findPage(Page<AccountClean> page, AccountClean accountClean) {
		return super.findPage(page, accountClean);
	}
	
	@Transactional(value="PL",readOnly = false)
	public String saveAccountClean(AccountClean accountClean) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			//新增清收数据时，将清收状态由待清收改为已清收
			Map<String, String> paraM = new HashMap<String, String>();
			paraM.put("allocateType", Constants.HAS_BEEN_ALLOCATED);
			paraM.put("contractNo", accountClean.getContractNo());
			accountCleanAllocateService.updateAllocateTypeByContractNo(paraM);
			
			super.save(accountClean);
			Map<String, String> param = new HashMap<String, String>();
			param.put("allocateType", Constants.HAS_BEEN_ALLOCATED);
			param.put("contractNo", accountClean.getContractNo());
			accountCleanAllocateService.updateAllocateTypeByContractNo(param);
			
			//向acc_sta_repay_plan_status中插入清收流水信息
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("factCapitalAmount", accountClean.getCleanReturnAmount());
			paraMap.put("contractNo", accountClean.getContractNo());
			String periodNum = plContractService.getPeriodNumByContractNo(accountClean.getContractNo());
			paraMap.put("periodNum", periodNum);
			plContractService.updateAccStaRepayPlanStatus(paraMap);
			//向acc_deduct_apply表中插入数据
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("dataDt", sdf.format(new Date()));
			paramMap.put("deductApplyNo", IdGen.uuid());
			paramMap.put("contractNo", accountClean.getContractNo());
			paramMap.put("deductTime", sdf.format(new Date()));
			paramMap.put("deductAmount", accountClean.getCleanReturnAmount());
			paramMap.put("deductCustId", accountClean.getCleanById());
			paramMap.put("isLock", Constants.IS_LOCK_NO);
			plContractService.insertAccDeductApply(paramMap);
			
			//向acc_deduct_result表中插入数据
			Map<String,String> paraResultMap = new HashMap<String,String>();
			paraResultMap.put("dataDt", sdf.format(new Date()));
			paraResultMap.put("contractNo", accountClean.getContractNo());
			paraResultMap.put("deductApplyNo", paramMap.get("deductApplyNo"));
			paraResultMap.put("streamNo", IdGen.uuid());
			paraResultMap.put("streamTime", sdf.format(new Date()));
			paraResultMap.put("deductAmount", accountClean.getCleanReturnAmount());
			paraResultMap.put("deductResult", Constants.DEDUCT_RESULT_PART);
			paraResultMap.put("deductUserId", accountClean.getCleanById());
			paraResultMap.put("isLock", Constants.IS_LOCK_NO);
			paraResultMap.put("description", "清收");
			plContractService.insertAccDeductResult(paraResultMap);
			
			
			return "success";
		} catch (Exception e) {
			logger.error("插入清收流水信息失败", e);
			return "false";
		}
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(AccountClean accountClean) {
		super.delete(accountClean);
	}
	
	//查询清收人员信息
	@Transactional(value="PL",readOnly = true)
	public Page<MyMap> getAccountCleanInfo(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(this.dao.getAccountCleanInfo(paramMap));
		return page;
	}
	
}