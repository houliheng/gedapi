package com.resoft.accounting.applyMarginRepay.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.applyMarginRepay.dao.ApplyMarginRepayDao;
import com.resoft.accounting.applyMarginRepay.entity.ApplyMarginRepay;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.resoft.multds.credit.service.CreditService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

/**
 * 保证金退还申请Service
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class ApplyMarginRepayService extends CrudService<ApplyMarginRepayDao, ApplyMarginRepay> {
	private static final Logger logger = LoggerFactory.getLogger(ApplyMarginRepayService.class);
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ContractLockDao contractLockDao;
	
	@Autowired
	private AccRepayPlanService repayPlanService;

	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@Autowired
	private CreditService creditService;

	public ApplyMarginRepay get(String id) {
		return super.get(id);
	}

	public List<ApplyMarginRepay> findList(ApplyMarginRepay applyMarginRepay) {
		return super.findList(applyMarginRepay);
	}

	public Page<ApplyMarginRepay> findPage(Page<ApplyMarginRepay> page, ApplyMarginRepay applyMarginRepay) {
		return super.findPage(page, applyMarginRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(ApplyMarginRepay applyMarginRepay) {
		super.save(applyMarginRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void updateApplyMarginRepay(ApplyMarginRepay applyMarginRepay) {
		applyMarginRepay.preUpdate();
		this.dao.updateApplyMarginRepay(applyMarginRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(ApplyMarginRepay applyMarginRepay) {
		super.delete(applyMarginRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public ApplyMarginRepay findApplyMarginRepayByContractNo(ApplyMarginRepay applyMarginRepay) {
		return this.dao.findApplyMarginRepayByContractNo(applyMarginRepay);
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView validateRepayPlanStatus(ApplyMarginRepay applyMarginRepay) {
		AjaxView ajaxView = new AjaxView();
		//判断合同是否被锁，主要验证是否在 账务调整期
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(applyMarginRepay.getContractNo());
		ContractLock contractLock2 = contractLockDao.validateIsLock(contractLock);
		if(contractLock2 != null ){
			ajaxView.setFailed().setMessage("尚有未还账务，请优先处理。");
		}else{
			ApplyMarginRepay applyMarginRepay1 = findApplyMarginRepayByContractNo(applyMarginRepay);
			if (applyMarginRepay1 != null) { 
				ajaxView.setFailed().setMessage("此合同保证金暂时无法申请退还");
			} else {
				List<AccRepayPlan> repayPlans = repayPlanService.findRepayPlanByContractNo(applyMarginRepay.getContractNo());
				List<StaRepayPlanStatus> planStatusList = staRepayPlanStatusService.findStaRepayPlanStatus(applyMarginRepay.getContractNo());
				if (repayPlans.size() != 0) {
					if (Integer.parseInt((repayPlans.get(0).getPeriodValue())) == planStatusList.size()) {
						for (StaRepayPlanStatus planStatus : planStatusList) {
							if (!(Constants.PERIOD_STATE_ZCJQ.equals(planStatus.getRepayPeriodStatus()) || Constants.PERIOD_STATE_YQJQ.equals(planStatus.getRepayPeriodStatus()) || Constants.PERIOD_STATE_TQHK.equals(planStatus.getRepayPeriodStatus()))) {
								ajaxView.setFailed().setMessage("尚有未还账务，请优先处理。");
								return ajaxView;
							} else {
								ajaxView.setSuccess();
							}
						}
					} else {
						ajaxView.setFailed().setMessage("尚有未还账务，请优先处理。");
					}
				} else {
					ajaxView.setFailed().setMessage("数据出现问题，请及时联系管理员。");
				}
			}
			
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView dealApplyMarginRepayData(ApplyMarginRepay applyMarginRepay, ActTaskParam actTaskParam, String flag) {
		AjaxView ajaxView = new AjaxView();
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(applyMarginRepay.getContractNo());
		// 1 保存 2 结算中心审核 3 分公司确认
		try {
			if (Constants.MARGIN_AMOUNT_SQ.equals(flag)) {
				applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_STATUS_SQZ);
				save(applyMarginRepay);
				String procInsId = actTaskService.startProcess(Constants.WORKFLOW_MARGIN_REPAY, "ACC_APPLY_MARGIN_REPAY", applyMarginRepay.getId());
				applyMarginRepay.setProcInsId(procInsId);
				this.dao.updateProcInsIdById(applyMarginRepay);
				contractLockDao.saveLockInfo(contractLock);
				StaContractStatus staContractStatus = staContractStatusService.get(applyMarginRepay.getContractNo());
				creditService.updateLoanStatus(applyMarginRepay.getContractNo(), staContractStatus.getRepayContractStatus());
			} else if (Constants.MARGIN_AMOUNT_JSZXSH.equals(flag)) {
				applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_STATUS_THZ);
				actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + applyMarginRepay.getMarginApproDesc(), "提交", null);
				applyMarginRepay.preUpdate();
				updateApplyMarginRepay(applyMarginRepay);
			} else {
				if (Constants.MARGIN_AMOUNT_STATUS_YTH.equals(applyMarginRepay.getMarginAmountStatus())) {
					applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_STATUS_YTH);
					applyMarginRepay.setMarginAmountTime(new Date());
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】", "提交", null);
					applyMarginRepay.preUpdate();
					updateApplyMarginRepay(applyMarginRepay);
					contractLockDao.deleteLock(contractLock);
				} else {
					applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_STATUS_FQ);
					actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【放弃】");
					applyMarginRepay.preUpdate();
					updateApplyMarginRepay(applyMarginRepay);
					contractLockDao.deleteLock(contractLock);
				}
			}
			ajaxView.setSuccess().setMessage("操作成功！");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("操作失败！");
			logger.error("操作失败！", e);
		}
		return ajaxView;
	}
}