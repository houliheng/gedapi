package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;


/**
 * 财报导入信息表Entity
 * @author caoyinglong
 * @version 2016-03-10
 */
public class FinancialStateImport  extends DataEntity<FinancialStateImport>{

	private static final long serialVersionUID = 1L;
	private String companyName;
	public FinancialStateImport() {
		super();
	}
	@Length(min=0, max=300, message="单位名称长度必须介于 0 和 300 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}

