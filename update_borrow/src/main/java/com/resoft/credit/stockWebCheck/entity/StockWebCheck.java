package com.resoft.credit.stockWebCheck.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 一次网查（企查查）Entity
 * @author jml
 * @version 2017-09-07
 */
public class StockWebCheck extends DataEntity<StockWebCheck> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String isException;		// 标志位(0.正常1.异常)
	private String description;		// 描述
	
	public StockWebCheck() {
		super();
	}

	public StockWebCheck(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getIsException() {
		return isException;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}
	
	@Length(min=0, max=1000, message="描述长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}