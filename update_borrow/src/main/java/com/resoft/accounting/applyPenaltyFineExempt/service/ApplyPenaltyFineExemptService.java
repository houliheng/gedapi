package com.resoft.accounting.applyPenaltyFineExempt.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.applyPenaltyFineExempt.dao.ApplyPenaltyFineExemptDao;
import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExempt;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

/**
 * 违约金罚息减免Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class ApplyPenaltyFineExemptService extends CrudService<ApplyPenaltyFineExemptDao, ApplyPenaltyFineExempt> {
	private static final Logger logger = LoggerFactory.getLogger(ApplyPenaltyFineExemptService.class);
	@Autowired
	private ActTaskService actTaskService;

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");

	public ApplyPenaltyFineExempt get(String id) {
		return super.get(id);
	}

	public List<ApplyPenaltyFineExempt> findList(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return super.findList(applyPenaltyFineExempt);
	}

	public Page<ApplyPenaltyFineExempt> findPage(Page<ApplyPenaltyFineExempt> page, ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return super.findPage(page, applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		super.save(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		super.delete(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public Map<String, Object> findApplyPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return this.dao.findApplyPenaltyFineExempt(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public Map<String, Object> findApplyPenaltyFineExemptOnStaRepayPlanStatus(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return this.dao.findApplyPenaltyFineExemptOnStaRepayPlanStatus(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public Map<String, BigDecimal> getOldApplyPenaltyFineExemptSumBycontractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return this.dao.getOldApplyPenaltyFineExemptSumBycontractNoAndPeriodNum(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public ApplyPenaltyFineExempt getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return this.dao.getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);

	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveResult(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		ApplyPenaltyFineExempt exempt = getCompanyPassApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
		exempt.setApplyStatus(Constants.APPLY_STATUS_SHTG);
		exempt.setThroughTime2(new Date());
		save(exempt);
		Map<String, BigDecimal> params = getOldApplyPenaltyFineExemptSumBycontractNoAndPeriodNum(exempt);
		exempt.setDataDt(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		if (params == null) {
			this.dao.saveStaPenaltyFineExempt(exempt);
		} else {
			exempt.setFineExemptAmount(Double.toString(params.get("fineExemptAmount").doubleValue() + Double.parseDouble(exempt.getFineExemptAmount())));
			exempt.setpenaltyExemptAmount(Double.toString(params.get("penaltyExemptAmount").doubleValue() + Double.parseDouble(exempt.getpenaltyExemptAmount())));
			this.dao.updateStaPenaltyFineExempt(exempt);
		}
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public AjaxView callSPAccExemptUpdateState(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：数据日期　合同号　期数
			CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_EXEMPT_UPDATE_STATE('" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "','" + applyPenaltyFineExempt.getContractNo() + "','" + Integer.parseInt((applyPenaltyFineExempt.getPeriodNum())) + "')}");
			callableStatement.execute();
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (SQLException e) {
			logger.error("调用存储SP_ACC_EXEMPT_UPDATE_STATE失败", e);
			ajaxView.setFailed().setMessage("减免通过，记账未成功");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("调用存储SP_ACC_EXEMPT_UPDATE_STATE释放资源失败", e);
				ajaxView.setFailed().setMessage("减免通过，记账未成功");
			}
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public ApplyPenaltyFineExempt getCompanyPassApplyPenaltyFineExemptByContractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		return this.dao.getCompanyPassApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView saveApplyPenaltyFineExemptResult(ApplyPenaltyFineExempt applyPenaltyFineExempt, ActTaskParam actTaskParam, String passFlag, String flag) {
		// 审核标记 0 未审核 1 通过 2 打回
		AjaxView ajaxView = new AjaxView();
		try {
			// all 结算中心 part 分公司
			if ("all".equals(flag)) {
				// yes 通过 no 拒绝
				if ("yes".equalsIgnoreCase(passFlag)) {
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + applyPenaltyFineExempt.getDescription(), "提交", null);
					saveResult(applyPenaltyFineExempt);
					ajaxView.put("callFlag", "yes");
				} else {
					// 申请被打回
					actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【放弃】" + applyPenaltyFineExempt.getDescription());
					ApplyPenaltyFineExempt exempt = getCompanyPassApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
					exempt.setApplyStatus(Constants.APPLY_STATUS_SHDH);
					save(exempt);
				}
			} else {
				if ("yes".equalsIgnoreCase(passFlag)) {
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + applyPenaltyFineExempt.getDescription(), "提交", null);
					applyPenaltyFineExempt.setThroughTime(new Date());
					save(applyPenaltyFineExempt);
				} else {
					// 申请被打回
					actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【放弃】" + applyPenaltyFineExempt.getDescription());
					ApplyPenaltyFineExempt exempt = getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
					exempt.setApplyStatus(Constants.APPLY_STATUS_SHDH);
					save(exempt);
				}
			}
			ajaxView.setSuccess().setMessage("操作成功！");
		} catch (Exception e) {
			logger.error("操作失败", e);
			ajaxView.setFailed().setMessage("操作失败！");
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public void updateProcInsIdById(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		this.dao.updateProcInsIdById(applyPenaltyFineExempt);
	}
	
	/**
	 * 根据合同号和期数退回减免金额
	 */
	public void deleteStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt){
		this.dao.deleteStaPenaltyFineExempt(applyPenaltyFineExempt);
	}
	
	/**
	 * 根据合同号和期数修改申请状态
	 */
	public void updateStaPenaltyFineExemptApplyStatus(ApplyPenaltyFineExempt applyPenaltyFineExempt){
		this.dao.updateStaPenaltyFineExemptApplyStatus(applyPenaltyFineExempt);
	}
	@Transactional(value = "ACC", readOnly = false)
	public void dealStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt){
		deleteStaPenaltyFineExempt(applyPenaltyFineExempt);
		updateStaPenaltyFineExemptApplyStatus(applyPenaltyFineExempt);
	}

}