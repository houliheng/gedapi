package com.resoft.credit.custRemoveBind.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

public class CustRemoveBind extends DataEntity<CustRemoveBind> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户Id
	private String custName;		// 客户名称
	private String idType;		// 证件类型(个人字典类型：CUSTOMER_P_ID_TYPE,对公字典类型：CUSTOMER_C_ID_TYPE)
	private String idNum;		// 证件号
	private String mobileNum;  //手机号
	private User user;		// 客户经理ID
	private Office company;    //所属机构
	private String isBind;		// 是否绑定(字典类型:BIND_STATUS)
	private String [] ids;		// 用于批量绑定
	private String userType;      //标志当前是管理访问还是客户经理访问
	
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public CustRemoveBind() {
		super();
	}

	public CustRemoveBind(String id){
		super(id);
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Length(min=0, max=200, message="客户名称长度必须介于 0 和 200 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=10, message="证件类型(个人字典类型：CUSTOMER_P_ID_TYPE,对公字典类型：CUSTOMER_C_ID_TYPE)长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=200, message="证件号长度必须介于 0 和 200 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=10, message="是否绑定(字典类型：IS_BIND)长度必须介于 0 和 10 之间")
	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}