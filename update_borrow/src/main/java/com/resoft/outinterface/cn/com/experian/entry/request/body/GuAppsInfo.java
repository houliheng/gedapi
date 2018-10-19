package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class GuAppsInfo {
	 @XmlElement(name = "GuApp", required = true)
	 protected List<GuAppInfo> guAppInfo;

	public List<GuAppInfo> getGuAppInfo() {
		return guAppInfo;
	}

	public void setGuAppInfo(List<GuAppInfo> guAppInfo) {
		this.guAppInfo = guAppInfo;
	}
	 
}
