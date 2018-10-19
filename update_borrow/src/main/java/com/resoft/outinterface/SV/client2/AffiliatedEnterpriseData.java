package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 关联企业列表
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AffiliatedEnterpriseData {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "c_enterpriseOrganizationCode")
	private String c_enterpriseOrganizationCode;// 41、组织机构代码
	@XmlElement(name = "c_businessRegistrationName")
	private String c_businessRegistrationName; // 40、企业工商登记名称


	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getEnterpriseOrganizationCode() {
		return c_enterpriseOrganizationCode;
	}

	public void setEnterpriseOrganizationCode(String enterpriseOrganizationCode) {
		this.c_enterpriseOrganizationCode = enterpriseOrganizationCode;
	}

	public String getBusinessRegistrationName() {
		return c_businessRegistrationName;
	}

	public void setBusinessRegistrationName(String businessRegistrationName) {
		this.c_businessRegistrationName = businessRegistrationName;
	}

}
