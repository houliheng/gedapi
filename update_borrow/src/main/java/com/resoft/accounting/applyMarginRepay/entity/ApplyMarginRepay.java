package com.resoft.accounting.applyMarginRepay.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 保证金退还申请Entity
 * @author wuxi01
 * @version 2016-03-04
 */
public class ApplyMarginRepay extends DataEntity<ApplyMarginRepay> {
	
	private static final long serialVersionUID = 1L;
	private Office orgLevel2;		// 2级公司
	private Office orgLevel3;		// 3级公司
	private Office orgLevel4;		// 4级公司
	private String operateId;		// 经办人ID
	private String operateOrgId;		// 经办机构ID（合同归属公司）
	private String contractNo;		// 合同编号
	private Date applyTime;		// 申请时间
	private String marginAmount;		// 保证金
	private String applyMarginAmount;		// 申请退还保证金
	private String approMarginAmount;		// 批复退还保证金
	private String marginAmountStatus;		// 保证金退还状态
	private String marginApplyDesc;		// 申请说明
	private String marginApproDesc;		// 批复说明
	private String marginReceivedMode;		// 保证金到账模式
	private String doneOrDoFlag; //已办代办标记
	private String procInsId;   // 流程id PROC_INS_ID
	private Date marginAmountTime; //保证金退换时间
	
	public Date getMarginAmountTime() {
		return marginAmountTime;
	}

	public void setMarginAmountTime(Date marginAmountTime) {
		this.marginAmountTime = marginAmountTime;
	}

	public String getDoneOrDoFlag() {
		return doneOrDoFlag;
	}

	public void setDoneOrDoFlag(String doneOrDoFlag) {
		this.doneOrDoFlag = doneOrDoFlag;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApplyMarginRepay() {
		super();
	}

	public ApplyMarginRepay(String id){
		super(id);
	}

	@Length(min=0, max=32, message="2级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=32, message="3级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="4级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@Length(min=0, max=32, message="经办人ID长度必须介于 0 和 32 之间")
	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	
	@Length(min=0, max=32, message="经办机构ID（合同归属公司）长度必须介于 0 和 32 之间")
	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}
	
	@Length(min=0, max=32, message="合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}
	
	public String getApplyMarginAmount() {
		return applyMarginAmount;
	}

	public void setApplyMarginAmount(String applyMarginAmount) {
		this.applyMarginAmount = applyMarginAmount;
	}
	
	public String getApproMarginAmount() {
		return approMarginAmount;
	}

	public void setApproMarginAmount(String approMarginAmount) {
		this.approMarginAmount = approMarginAmount;
	}
	
	@Length(min=0, max=4, message="保证金退还状态长度必须介于 0 和 4 之间")
	public String getMarginAmountStatus() {
		return marginAmountStatus;
	}

	public void setMarginAmountStatus(String marginAmountStatus) {
		this.marginAmountStatus = marginAmountStatus;
	}
	
	@Length(min=0, max=1000, message="申请说明长度必须介于 0 和 1000 之间")
	public String getMarginApplyDesc() {
		return marginApplyDesc;
	}

	public void setMarginApplyDesc(String marginApplyDesc) {
		this.marginApplyDesc = marginApplyDesc;
	}
	
	@Length(min=0, max=1000, message="批复说明长度必须介于 0 和 1000 之间")
	public String getMarginApproDesc() {
		return marginApproDesc;
	}

	public void setMarginApproDesc(String marginApproDesc) {
		this.marginApproDesc = marginApproDesc;
	}
	
	@Length(min=0, max=4, message="保证金到账模式长度必须介于 0 和 4 之间")
	public String getMarginReceivedMode() {
		return marginReceivedMode;
	}

	public void setMarginReceivedMode(String marginReceivedMode) {
		this.marginReceivedMode = marginReceivedMode;
	}
	
}