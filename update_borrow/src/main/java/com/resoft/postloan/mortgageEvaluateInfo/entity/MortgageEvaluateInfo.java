package com.resoft.postloan.mortgageEvaluateInfo.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资产管理Entity
 * @author zhaohuakui
 * @version 2016-05-25
 */
public class MortgageEvaluateInfo extends DataEntity<MortgageEvaluateInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 进件编号
	private String infoId;		// 进件编号
	private String isRegister;		// 是否登记
	private String registerAuthority;		// 登记机关
	private String isEvaluate;		// 是否进行抵质押评估
	private String evaluateOrgName;		// 评估机构名称
	private String outerEvaluatePrice;		// 外部评估价值
	private Date lastEvaluateTime;		// 最后一次评估时间
	private String innerEvaluatePrice;		// 内部评估价值
	private Double mortgageLoanAmount;		// 担保对应借款余额
	private String mortgageRate;		// 抵质押率
	private String isSafe;		// 是否保险
	private String isInsuranceValid;		// 保险是否在有效期内
	private String isInsuranceIncom;		// 保险权益是否在我公司
	private String isInsuranceLicValid;		// 抵质押人营业执照是否在有效期内
	private String mortgagedPersonWish;		// 抵质押人意愿
	private String checkResult;		// 抵质押情况综合检查结论
	private String checkAdvice;		// 审批意见
	
	
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckAdvice() {
		return checkAdvice;
	}

	public void setCheckAdvice(String checkAdvice) {
		this.checkAdvice = checkAdvice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MortgageEvaluateInfo() {
		super();
	}

	public MortgageEvaluateInfo(String id){
		super(id);
	}
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Length(min=0, max=1, message="是否登记长度必须介于 0 和 1 之间")
	public String getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}
	
	@Length(min=0, max=32, message="登记机关长度必须介于 0 和 32 之间")
	public String getRegisterAuthority() {
		return registerAuthority;
	}

	public void setRegisterAuthority(String registerAuthority) {
		this.registerAuthority = registerAuthority;
	}
	
	@Length(min=0, max=1, message="是否进行抵质押评估长度必须介于 0 和 1 之间")
	public String getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}
	
	@Length(min=0, max=300, message="评估机构名称长度必须介于 0 和 300 之间")
	public String getEvaluateOrgName() {
		return evaluateOrgName;
	}

	public void setEvaluateOrgName(String evaluateOrgName) {
		this.evaluateOrgName = evaluateOrgName;
	}
	
	public String getOuterEvaluatePrice() {
		return outerEvaluatePrice;
	}

	public void setOuterEvaluatePrice(String outerEvaluatePrice) {
		this.outerEvaluatePrice = outerEvaluatePrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastEvaluateTime() {
		return lastEvaluateTime;
	}

	public void setLastEvaluateTime(Date lastEvaluateTime) {
		this.lastEvaluateTime = lastEvaluateTime;
	}
	
	public String getInnerEvaluatePrice() {
		return innerEvaluatePrice;
	}

	public void setInnerEvaluatePrice(String innerEvaluatePrice) {
		this.innerEvaluatePrice = innerEvaluatePrice;
	}
	
	public Double getMortgageLoanAmount() {
		return mortgageLoanAmount;
	}

	public void setMortgageLoanAmount(Double mortgageLoanAmount) {
		this.mortgageLoanAmount = mortgageLoanAmount;
	}
	
	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}
	
	@Length(min=0, max=1, message="是否保险长度必须介于 0 和 1 之间")
	public String getIsSafe() {
		return isSafe;
	}

	public void setIsSafe(String isSafe) {
		this.isSafe = isSafe;
	}
	
	@Length(min=0, max=1, message="保险是否在有效期内长度必须介于 0 和 1 之间")
	public String getIsInsuranceValid() {
		return isInsuranceValid;
	}

	public void setIsInsuranceValid(String isInsuranceValid) {
		this.isInsuranceValid = isInsuranceValid;
	}
	
	@Length(min=0, max=1, message="保险权益是否在我公司长度必须介于 0 和 1 之间")
	public String getIsInsuranceIncom() {
		return isInsuranceIncom;
	}

	public void setIsInsuranceIncom(String isInsuranceIncom) {
		this.isInsuranceIncom = isInsuranceIncom;
	}
	
	@Length(min=0, max=1, message="抵质押人营业执照是否在有效期内长度必须介于 0 和 1 之间")
	public String getIsInsuranceLicValid() {
		return isInsuranceLicValid;
	}

	public void setIsInsuranceLicValid(String isInsuranceLicValid) {
		this.isInsuranceLicValid = isInsuranceLicValid;
	}
	
	@Length(min=0, max=10, message="抵质押人意愿长度必须介于 0 和 10 之间")
	public String getMortgagedPersonWish() {
		return mortgagedPersonWish;
	}

	public void setMortgagedPersonWish(String mortgagedPersonWish) {
		this.mortgagedPersonWish = mortgagedPersonWish;
	}

	
}