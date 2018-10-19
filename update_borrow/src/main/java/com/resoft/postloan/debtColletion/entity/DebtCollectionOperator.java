package com.resoft.postloan.debtColletion.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同操作人员统计Entity
 * @author wangguodong
 * @version 2016-06-02
 */
public class DebtCollectionOperator extends DataEntity<DebtCollectionOperator> {

	private static final long serialVersionUID = 1L;
	private String loginName; // 登陆名
	private String operatorName; // 姓名
	private String operatorOffice; // 归属部门
	private String operatorCompany; // 归属机构

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorOffice() {
		return operatorOffice;
	}

	public void setOperatorOffice(String operatorOffice) {
		this.operatorOffice = operatorOffice;
	}

	public String getOperatorCompany() {
		return operatorCompany;
	}

	public void setOperatorCompany(String operatorCompany) {
		this.operatorCompany = operatorCompany;
	}

}
