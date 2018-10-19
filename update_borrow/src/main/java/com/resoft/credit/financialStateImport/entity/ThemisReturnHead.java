package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis返回头Entity
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReturnHead extends DataEntity<ThemisReturnHead> {
	
	private static final long serialVersionUID = 1L;
	private String companycode;		// 企业编号
	private String returncode;		// 错误代码(0000成功9999失败)
	private String returnmessage;		// 错误说明
	private String applyNo;			//进件号

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public ThemisReturnHead() {
		super();
	}

	public ThemisReturnHead(String id){
		super(id);
	}

	@Length(min=1, max=255, message="企业编号长度必须介于 1 和 255 之间")
	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	@Length(min=0, max=255, message="错误代码(0000成功9999失败)长度必须介于 0 和 255 之间")
	public String getReturncode() {
		return returncode;
	}

	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	
	@Length(min=0, max=255, message="错误说明长度必须介于 0 和 255 之间")
	public String getReturnmessage() {
		return returnmessage;
	}

	public void setReturnmessage(String returnmessage) {
		this.returnmessage = returnmessage;
	}
	
}