package com.resoft.credit.noEntry.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 机构禁件表Entity
 * @author lirongchao
 * @version 2016-01-08
 */
public class OrgNoEntry extends DataEntity<OrgNoEntry> {
	/**
	 * @reqno: H1512260021
	 * @date-designer:2016年01月13日-lirongchao
	 * @date-author:2016年01月13日-lirongchao:机构禁件表Entity
	 */
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构ID
	private Office office; 		//机构
	
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public OrgNoEntry() {
		super();
	}

	public OrgNoEntry(String id){
		super(id);
	}

	@Length(min=0, max=32, message="机构ID长度必须介于 0 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}