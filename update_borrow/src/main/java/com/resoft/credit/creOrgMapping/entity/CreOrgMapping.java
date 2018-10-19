package com.resoft.credit.creOrgMapping.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 机构关系映射
 * @author guoshaohua
 * @version 2017-11-01
 */
public class CreOrgMapping extends DataEntity<CreOrgMapping> {
		
	private static final long serialVersionUID = 1L;
	private String id;	    //机构编码
	private String name;   //机构名称
	private String orgValue;//对应码值
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgValue() {
		return orgValue;
	}
	public void setOrgValue(String orgValue) {
		this.orgValue = orgValue;
	}
	
	
	
	
	
	
}
