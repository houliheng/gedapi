package com.resoft.postloan.checkDaily.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 日常检查Entity
 * @author wuxi01
 * @version 2016-05-23
 */
public class CheckDaily extends DataEntity<CheckDaily> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String checkedBy;		// 复核人员
	private String checkDailyResult;		// 检查结果（1保存通过2催收3法务4展期5借新还旧6特殊处理7签署保险合同）
	private String checkDailyAdvice;		// 日常检查结果具体建议
	private String checkDailyProc;		// 日常检查后进入下一流程：1正常保存2催收3法务4展期5借新还旧6特殊处理7签署保证合同
	
	public CheckDaily() {
		super();
	}

	public CheckDaily(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=30, message="复核人员长度必须介于 0 和 30 之间")
	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	
	@Length(min=0, max=4, message="检查结果（1保存通过2催收3法务4展期5借新还旧6特殊处理7签署保险合同）长度必须介于 0 和 4 之间")
	public String getCheckDailyResult() {
		return checkDailyResult;
	}

	public void setCheckDailyResult(String checkDailyResult) {
		this.checkDailyResult = checkDailyResult;
	}
	
	@Length(min=0, max=1000, message="日常检查结果具体建议长度必须介于 0 和 1000 之间")
	public String getCheckDailyAdvice() {
		return checkDailyAdvice;
	}

	public void setCheckDailyAdvice(String checkDailyAdvice) {
		this.checkDailyAdvice = checkDailyAdvice;
	}
	
	@Length(min=0, max=4, message="日常检查后进入下一流程：1正常保存2催收3法务4展期5借新还旧6特殊处理7签署保证合同长度必须介于 0 和 4 之间")
	public String getCheckDailyProc() {
		return checkDailyProc;
	}

	public void setCheckDailyProc(String checkDailyProc) {
		this.checkDailyProc = checkDailyProc;
	}
	
}