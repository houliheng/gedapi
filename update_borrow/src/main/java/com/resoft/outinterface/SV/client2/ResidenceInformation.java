package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 居住地信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResidenceInformation {
	@XmlElement(name = "work_id")
	private String work_id;// 子工单编号
	@XmlElement(name = "p_residenceAddress")
	private String p_residenceAddress;// 居住地址区
	@XmlElement(name = "p_residenceDateilAddress")
	private String p_residenceDateilAddress;// 25、居住详细地址

	public String getWorkId() {
		return work_id;
	}
	public void setWorkId(String workId) {
		this.work_id = workId;
	}
	public String getResidenceAddress() {
		return p_residenceAddress;
	}
	public void setResidenceAddress(String residenceAddress) {
		this.p_residenceAddress = residenceAddress;
	}
	public String getResidenceDateilAddress() {
		return p_residenceDateilAddress;
	}
	public void setResidenceDateilAddress(String residenceDateilAddress) {
		this.p_residenceDateilAddress = residenceDateilAddress;
	}

}
