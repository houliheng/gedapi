package com.resoft.credit.generalAudit.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 总公司总经理审核条件配置Entity
 * @author chenshaojia
 * @version 2016-04-08
 */
public class GeneralManagerAudit extends DataEntity<GeneralManagerAudit> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private Office office;		// office_id
	private String productTypeCode;		// product_type_code
	private String operatorCode;		// operator_code
	private BigDecimal contractAmount;		// contract_amount
	
	public GeneralManagerAudit() {
		super();
	}

	public GeneralManagerAudit(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	
}