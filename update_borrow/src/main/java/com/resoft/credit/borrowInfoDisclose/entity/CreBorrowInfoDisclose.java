package com.resoft.credit.borrowInfoDisclose.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借后信息披露Entity
 * @author zcl
 * @version 2018-03-09
 */
public class CreBorrowInfoDisclose extends DataEntity<CreBorrowInfoDisclose> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String custName;		// 客户名称
	private Date loanDate;		// 放款日期
	private String contractAmount;		// 合同金额
	private String periodValue;		// 期限
	private String orgLevel2;		// 大区
	private String orgLevel3;		// 区域
	private String orgLevel4;		// 分公司
	private Date firstDiscloDate;		// 首次披露时间
	private String discloFrequency;		// 披露次数
	private String push;		// 是否已推送冠E通（0未推送，1已推送）
	private Date beginLoanDate;		// 开始 放款日期
	private Date endLoanDate;		// 结束 放款日期
	private Date beginFirstDiscloDate;		// 开始 首次披露时间
	private Date endFirstDiscloDate;		// 结束 首次披露时间
	
	public CreBorrowInfoDisclose() {
		super();
	}

	public CreBorrowInfoDisclose(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	
	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	@Length(min=0, max=10, message="期限长度必须介于 0 和 10 之间")
	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	
	@Length(min=0, max=32, message="大区长度必须介于 0 和 32 之间")
	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=32, message="区域长度必须介于 0 和 32 之间")
	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="分公司长度必须介于 0 和 32 之间")
	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstDiscloDate() {
		return firstDiscloDate;
	}

	public void setFirstDiscloDate(Date firstDiscloDate) {
		this.firstDiscloDate = firstDiscloDate;
	}
	
	@Length(min=0, max=10, message="披露次数长度必须介于 0 和 10 之间")
	public String getDiscloFrequency() {
		return discloFrequency;
	}

	public void setDiscloFrequency(String discloFrequency) {
		this.discloFrequency = discloFrequency;
	}
	
	@Length(min=0, max=1, message="是否已推送冠E通（0未推送，1已推送）长度必须介于 0 和 1 之间")
	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}
	
	public Date getBeginLoanDate() {
		return beginLoanDate;
	}

	public void setBeginLoanDate(Date beginLoanDate) {
		this.beginLoanDate = beginLoanDate;
	}
	
	public Date getEndLoanDate() {
		return endLoanDate;
	}

	public void setEndLoanDate(Date endLoanDate) {
		this.endLoanDate = endLoanDate;
	}
		
	public Date getBeginFirstDiscloDate() {
		return beginFirstDiscloDate;
	}

	public void setBeginFirstDiscloDate(Date beginFirstDiscloDate) {
		this.beginFirstDiscloDate = beginFirstDiscloDate;
	}
	
	public Date getEndFirstDiscloDate() {
		return endFirstDiscloDate;
	}

	public void setEndFirstDiscloDate(Date endFirstDiscloDate) {
		this.endFirstDiscloDate = endFirstDiscloDate;
	}
		
}