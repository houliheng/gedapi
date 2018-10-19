package com.resoft.accounting.applyAdvanceRepay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.accounting.applyAdvanceRepay.dao.ApplyAdvanceRepayDao;
import com.resoft.accounting.applyAdvanceRepay.entity.ApplyAdvanceRepay;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 提前还款Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class ApplyAdvanceRepayService extends CrudService<ApplyAdvanceRepayDao, ApplyAdvanceRepay> {

	@Autowired 
	private ActTaskService actTaskService;

	public ApplyAdvanceRepay get(String id) {
		return super.get(id);
	}

	public List<ApplyAdvanceRepay> findList(ApplyAdvanceRepay applyAdvanceRepay) {
		return super.findList(applyAdvanceRepay);
	}

	public Page<ApplyAdvanceRepay> findPage(Page<ApplyAdvanceRepay> page, ApplyAdvanceRepay applyAdvanceRepay) {
		return super.findPage(page, applyAdvanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(ApplyAdvanceRepay applyAdvanceRepay) {
		super.save(applyAdvanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveAdvanceRepay(ApplyAdvanceRepay applyAdvanceRepay) {
		applyAdvanceRepay.setOrgId(UserUtils.getUser().getCompany().getId());
		applyAdvanceRepay.setAdvanceDeductStatus(Constants.ADVANCE_DEDUCT_STATUS_SQDPF);
		save(applyAdvanceRepay);
		String procInsId = actTaskService.startProcess(Constants.WORKFLOW_ADVANCE_REPAY, "acc_advance_deduct_repay", applyAdvanceRepay.getId());
		applyAdvanceRepay.setProcInsId(procInsId);
		save(applyAdvanceRepay);
//		Contract contract = contractService.findContractByContractNo(applyAdvanceRepay.getContractNo());
//		DeductApply deductApply = new DeductApply();
//		deductApply.setCapitalTerraceNo(contract.getCapitalTerraceNo());
//		deductApply.setContractNo(applyAdvanceRepay.getContractNo());
//		deductApply.setDeductAmount(applyAdvanceRepay.getAllDeductAmountApply().toString());
//		deductApply.setPeriodNum(applyAdvanceRepay.getPeriodNum());
//		deductApply.setDataDt(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
//		deductApply.setIsLock("1");
//		deductApply.setDeductApplyNo(IdGen.uuid());
//		deductApplyService.save(deductApply);
//		ContractLock contractLock = new ContractLock();
//		contractLock.setContractNo(applyAdvanceRepay.getContractNo());
//		contractLockDao.saveLockInfo(contractLock);

	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(ApplyAdvanceRepay applyAdvanceRepay) {
		super.delete(applyAdvanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String getAdvanceRepayForOneMonth(ApplyAdvanceRepay advanceRepay) {
		return this.dao.getAdvanceRepayForOneMonth(advanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String getAdvanceRepayForThreePercents(ApplyAdvanceRepay advanceRepay) {
		return this.dao.getAdvanceRepayForThreePercents(advanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String remainRepayPlan(ApplyAdvanceRepay advanceRepay) {
		return this.dao.remainRepayPlan(advanceRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String getMinPeriodNum(String contractNo) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("contractNo", contractNo);
		param.put("deductDate", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		return this.dao.getMinPeriodNumByContractNoAndDeductDate(param);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveSuggestion(ApplyAdvanceRepay applyAdvanceRepay, ActTaskParam actTaskParam, String passFlag) {
		if ("yes".equals(passFlag)) {
			String statusTemp = applyAdvanceRepay.getAdvanceDeductStatus();
			applyAdvanceRepay.setAdvanceDeductStatus(Integer.toString(Integer.parseInt(statusTemp) + 1));
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + applyAdvanceRepay.getApproveDescription(), "提交", null);
		} else if ("no".equals(passFlag)) {
			applyAdvanceRepay.setAdvanceDeductStatus(Constants.ADVANCE_DEDUCT_STATUS_FQ);
			actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【拒绝】" + applyAdvanceRepay.getApproveDescription());
		} else if ("back".equals(passFlag)) {
			String statusTemp = applyAdvanceRepay.getAdvanceDeductStatus();
			applyAdvanceRepay.setAdvanceDeductStatus(Integer.toString(Integer.parseInt(statusTemp) - 1));
			actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + applyAdvanceRepay.getApproveDescription());
		}
		save(applyAdvanceRepay);
	}

	public List<ApplyAdvanceRepay> getApplyAdvanceRepayByContractNoAndstatus(Map<String, Object> params) {
		return this.dao.getApplyAdvanceRepayByContractNoAndstatus(params);
	}
	
	public List<ApplyAdvanceRepay> getApplyAdvanceRepayByContractNoAndProcInsId(Map<String, Object> params) {
		return this.dao.getApplyAdvanceRepayByContractNoAndProcInsId(params);
	}

}