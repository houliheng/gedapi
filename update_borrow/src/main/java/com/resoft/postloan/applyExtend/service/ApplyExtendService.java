package com.resoft.postloan.applyExtend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.postloan.applyExtend.dao.ApplyExtendDao;
import com.resoft.postloan.applyExtend.entity.ApplyExtend;
import com.resoft.postloan.checkDaily.dao.CheckDailyAllocateDao;
import com.resoft.postloan.checkDaily.dao.CheckDailyDao;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.checkDaily.service.CheckDailyService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.common.utils.DateUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

/**
 * 合同展期申请Service
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class ApplyExtendService extends CrudService<ApplyExtendDao, ApplyExtend> {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private CheckDailyDao checkDailyDao;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private CheckDailyAllocateDao checkDailyAllocateDao;

	public ApplyExtend get(String id) {
		return super.get(id);
	}

	public ApplyExtend findApplyExtendByContractNo(String contractNo) {
		return this.dao.findApplyExtendByContractNo(contractNo);
	}

	public List<ApplyExtend> findList(ApplyExtend applyExtend) {
		return super.findList(applyExtend);
	}

	public Page<ApplyExtend> findPage(Page<ApplyExtend> page, ApplyExtend applyExtend) {
		return super.findPage(page, applyExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(ApplyExtend applyExtend) {
		super.save(applyExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public void saveApply(ApplyExtend applyExtend, String saveFlag, AjaxView ajaxView) throws Exception {
		if ("submit".equals(saveFlag)) {
			// 保存展期申请信息
			applyExtend.setExtendApplyStatus(Constants.EXTEND_APPLY_STATUS_TJDSP);
			this.save(applyExtend);
			// 更新日常检查状态
			Map<String, Object> params = Maps.newHashMap();
			params.put("contractNo", applyExtend.getContractNo());
			params.put("allocateType", Constants.ALLOCATE_TYPE_TODO);
			List<CheckDailyAllocate> checkDailyAllocateList = checkDailyService.findListByParams(params);
			if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
				CheckDailyAllocate checkDailyAllocate = checkDailyAllocateList.get(0);
				if (checkDailyAllocate != null) {
					CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
					if (checkDaily != null) {
						checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_ZQSQZ);
						checkDailyDao.updateCheckDailyProc(checkDaily);
					}
					checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
					checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
					checkDailyAllocateDao.updateAllocateType(checkDailyAllocate);
				}
			}
			// 启动流程
			startProcess(applyExtend);
			ajaxView.put("flag", "submit");
		} else {
			applyExtend.setExtendApplyStatus(Constants.EXTEND_APPLY_STATUS_BCWFJ);
			this.save(applyExtend);
			ajaxView.put("flag", "save");
		}
		ajaxView.put("id", applyExtend.getId());
		ajaxView.put("contractNo", applyExtend.getContractNo());
		ajaxView.setSuccess().setMessage("保存合同展期申请成功");
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(ApplyExtend applyExtend) {
		super.delete(applyExtend);
	}

	@Transactional(value = "PL", readOnly = false)
	public Page<PLContract> dealPLContractExtendPeriod(Page<PLContract> page) {
		List<PLContract> plContracts = page.getList();
		for (PLContract plContract : plContracts) {
			String extendPeriod = "0";
			ApplyExtend applyExtend = findApplyExtendByContractNo(plContract.getContractNo());
			if (applyExtend != null && applyExtend.getExtendPeriod() != null) {
				extendPeriod = applyExtend.getExtendPeriod();
			}
			plContract.setExtendPeriod(extendPeriod);
		}
		page.setList(plContracts);
		return page;
	}

	/**
	 * 验证指定合同号是否已进行过展期
	 */
	@Transactional(value = "PL", readOnly = false)
	public ApplyExtend validateApplyExtend(String contractNo) {
		return this.dao.validateApplyExtend(contractNo);
	}

	/**
	 * 修改申请状态
	 * 
	 * @param applyExtend
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateApplyStatus(Map<String, Object> map) {
		this.dao.updateApplyStatus(map);
	}

	/**
	 * 启动流程后修改业务表中流程实例ID
	 * 
	 * @param params
	 */
	@Transactional(value = "PL", readOnly = false)
	private void updateProcInsIdByBusinessId(Map<String, Object> params) {
		super.dao.updateProcInsIdByBusinessId(params);
	}

	/**
	 * 启动产品对应的流程,并将下个环节的任务指定为当前用户
	 * */
	@Transactional(value = "PL", readOnly = false)
	private void startProcess(ApplyExtend applyExtend) {
		String procInsId = actTaskService.startProcess(Constants.WORKFLOW_CONTRACT_EXTEND, "pl_apply_extend", applyExtend.getId());
		String id = applyExtend.getId();
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("procInsId", procInsId);
		updateProcInsIdByBusinessId(params);
		// historyService.createHistoricTaskInstanceQuery().processInstanceId(procInsId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
	}

}