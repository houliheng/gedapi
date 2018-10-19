package com.resoft.accounting.discountStream.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 贴息流水Entity
 * @author gsh
 * @version 2018-05-23
 */
public class AccDiscountStream extends DataEntity<AccDiscountStream> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String fromDiscountAccount;		// 出账户
	private String toAccount;		// 进账户
	private String seqNo;		// 流水号
	private String discountStatus;		// 贴息状态
	private String discountType;		// 贴息类型
	private String operateName;		// 操作者
	private Date discountDate;		// 贴息日期
	private String compnensatoryNo;//代偿账户资金平台账号
	private String mideCapitalNo;//中间人账户资金平台账号
	private String toCapitalNo;//借款人资金平台账号
	private String fromCapitalNo;
	private BigDecimal discountFee;//贴息金额
	private String custName;//贴息人
	private String periodNumStatus;
	private Office orgLevel2;// 大区
	private Office orgLevel3;// 区域
	private Office orgLevel4;// 登记门店
	private Office company;
	private String periodStatus;
	private BigDecimal factDiscountFee;
	public AccDiscountStream() {
		super();
	}

	public AccDiscountStream(String id){
		super(id);
	}

	@Length(min=0, max=32, message="合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=5, message="期数长度必须介于 0 和 5 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@Length(min=0, max=50, message="出账户长度必须介于 0 和 50 之间")
	public String getFromDiscountAccount() {
		return fromDiscountAccount;
	}

	public void setFromDiscountAccount(String fromDiscountAccount) {
		this.fromDiscountAccount = fromDiscountAccount;
	}
	
	@Length(min=0, max=50, message="进账户长度必须介于 0 和 50 之间")
	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	
	@Length(min=0, max=50, message="流水号长度必须介于 0 和 50 之间")
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	@Length(min=0, max=5, message="代偿状态长度必须介于 0 和 5 之间")
	public String getDiscountStatus() {
		return discountStatus;
	}

	public void setDiscountStatus(String discountStatus) {
		this.discountStatus = discountStatus;
	}
	
	@Length(min=0, max=1, message="代偿类型长度必须介于 0 和 1 之间")
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	
	@Length(min=0, max=20, message="操作者长度必须介于 0 和 20 之间")
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Date discountDate) {
		this.discountDate = discountDate;
	}

	public String getCompnensatoryNo() {
		return compnensatoryNo;
	}

	public void setCompnensatoryNo(String compnensatoryNo) {
		this.compnensatoryNo = compnensatoryNo;
	}

	public String getMideCapitalNo() {
		return mideCapitalNo;
	}

	public void setMideCapitalNo(String mideCapitalNo) {
		this.mideCapitalNo = mideCapitalNo;
	}

	public String getToCapitalNo() {
		return toCapitalNo;
	}

	public void setToCapitalNo(String toCapitalNo) {
		this.toCapitalNo = toCapitalNo;
	}

	public String getFromCapitalNo() {
		return fromCapitalNo;
	}

	public void setFromCapitalNo(String fromCapitalNo) {
		this.fromCapitalNo = fromCapitalNo;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPeriodNumStatus() {
		return periodNumStatus;
	}

	public void setPeriodNumStatus(String periodNumStatus) {
		this.periodNumStatus = periodNumStatus;
	}

	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(String periodStatus) {
		this.periodStatus = periodStatus;
	}

	public BigDecimal getFactDiscountFee() {
		return factDiscountFee;
	}

	public void setFactDiscountFee(BigDecimal factDiscountFee) {
		this.factDiscountFee = factDiscountFee;
	}
	
}