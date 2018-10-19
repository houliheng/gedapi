package com.resoft.postloan.approveExtend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.applyExtend.entity.ApplyExtend;
import com.resoft.postloan.applyExtend.service.ApplyExtendService;
import com.resoft.postloan.approveExtend.entity.ApproveExtend;
import com.resoft.postloan.approveExtend.dao.ApproveExtendDao;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.checkDaily.service.CheckDailyService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.extendRepayPlan.service.ExtendRepayPlanService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;

/**
 * 合同展期审批Service
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class ApproveExtendService extends CrudService<ApproveExtendDao, ApproveExtend> {

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ExtendRepayPlanService extendRepayPlanService;

	@Autowired
	private ApplyExtendService applyExtendService;
	
	@Autowired
	private CheckDailyService checkDailyService;

	public ApproveExtend get(String id) {
		return super.get(id);
	}

	public List<ApproveExtend> findList(ApproveExtend approveExtend) {
		return super.findList(approveExtend);
	}

	public Page<ApproveExtend> findPage(Page<ApproveExtend> page, ApproveExtend approveExtend) {
		return super.findPage(page, approveExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(ApproveExtend approveExtend) {
		super.save(approveExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(ApproveExtend approveExtend) {
		super.delete(approveExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public AjaxView updateApplyExtendStatus(ActTaskParam actTaskParam, ApproveExtend approveExtend) {
		AjaxView ajaxView = new AjaxView();
		if ("yes".equals(approveExtend.getApproveResult())) {
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + approveExtend.getSuggestion(), "提交", null);
			if (Constants.EXTEND_APPROVE_ZBSJZXQR.equals(actTaskParam.getTaskDefKey())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", actTaskParam.getExtendId());
				map.put("extendApplyStatus", Constants.EXTEND_APPLY_STATUS_SHTG);
				applyExtendService.updateApplyStatus(map);
				// 更新日常检查状态
				Map<String, Object> params = Maps.newHashMap();
				params.put("contractNo", approveExtend.getContractNo());
				params.put("checkDailyProc", Constants.CHECK_DAILY_PROC_ZQSQZ);
				List<CheckDailyAllocate> checkDailyAllocateList = checkDailyService.findListByParams(params);
				if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
					CheckDailyAllocate checkDailyAllocate = checkDailyAllocateList.get(0);
					if (checkDailyAllocate != null) {
						CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
						if (checkDaily != null) {
							checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_ZQTG);
							checkDailyService.updateCheckDailyProc(checkDaily);
						}
					}
				}
			}
		} else if ("refuse".equals(approveExtend.getApproveResult())) {
			BigDecimal outStandingAmount = plContractService.getOutStandingCapitalAmount(approveExtend.getContractNo());
			ApplyExtend applyExtend = new ApplyExtend();
			applyExtend.setContractNo(approveExtend.getContractNo());
			applyExtend.setExtendApplyStatus(Constants.EXTEND_APPLY_STATUS_BCWFJ);
			applyExtend.setExtendAmount(outStandingAmount);
			actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【拒绝】" + approveExtend.getSuggestion());
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", actTaskParam.getExtendId());
			map.put("extendApplyStatus", Constants.EXTEND_APPLY_STATUS_SHJJ);
			applyExtendService.updateApplyStatus(map);
			applyExtendService.save(applyExtend);
			extendRepayPlanService.deleteExtendRepayPlan(applyExtend.getContractNo());
			// 更新日常检查状态
			Map<String, Object> params = Maps.newHashMap();
			params.put("contractNo", approveExtend.getContractNo());
			params.put("checkDailyProc", Constants.CHECK_DAILY_PROC_ZQSQZ);
			List<CheckDailyAllocate> checkDailyAllocateList = checkDailyService.findListByParams(params);
			if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
				CheckDailyAllocate checkDailyAllocate = checkDailyAllocateList.get(0);
				if (checkDailyAllocate != null) {
					CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
					if (checkDaily != null) {
						checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_ZQJJ);
						checkDailyService.updateCheckDailyProc(checkDaily);
					}
				}
			}
			
		} else {
			actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + approveExtend.getSuggestion());
		}
		ajaxView.setSuccess().setMessage("提交成功");
		return ajaxView;
	}
}