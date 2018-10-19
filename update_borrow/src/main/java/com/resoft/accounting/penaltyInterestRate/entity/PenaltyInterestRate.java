package com.resoft.accounting.penaltyInterestRate.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 罚息利率调整Entity
 * @author wangguodong
 * @version 2016-08-11
 */
public class PenaltyInterestRate extends DataEntity<PenaltyInterestRate> {
	
	private static final long serialVersionUID = 1L;
	private String paramName;		// 参数名称
	private String paramValue;		// 参数值
	private String description;		// 备注
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	
	public PenaltyInterestRate() {
		super();
	}

	public PenaltyInterestRate(String id){
		super(id);
	}

	@Length(min=0, max=50, message="参数名称长度必须介于 0 和 50 之间")
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	@Length(min=0, max=10, message="参数值长度必须介于 0 和 10 之间")
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}