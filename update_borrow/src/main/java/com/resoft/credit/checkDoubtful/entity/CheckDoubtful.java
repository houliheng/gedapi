package com.resoft.credit.checkDoubtful.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借前外访Entity
 * @author yanwanmei
 * @version 2016-03-01
 */
public class CheckDoubtful extends DataEntity<CheckDoubtful> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private Date checkDate;		// 外访日期
	private String checkUserId;		// 检查人ID
	private String checkUserName;		// 外访人
	private String riskPoint;		// 异常风险点
	private String description;		// 外访详情
	private String checkAddress;		// 外访地点
	private String serialNumber;		// 序号（5位）
	
	public CheckDoubtful() {
		super();
	}

	public CheckDoubtful(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=32, message="检查人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	@Length(min=0, max=20, message="外访人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	@Length(min=0, max=300, message="异常风险点长度必须介于 0 和 300 之间")
	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}
	
	@Length(min=0, max=1000, message="外访详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=300, message="外访地点长度必须介于 0 和 300 之间")
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	@Length(min=0, max=5, message="序号（5位）长度必须介于 0 和 5 之间")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}