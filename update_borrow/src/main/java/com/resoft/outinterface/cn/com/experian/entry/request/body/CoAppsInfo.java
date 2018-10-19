package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class CoAppsInfo {
	@XmlElement(name = "CoApp", required = true)
    protected List<CoAppInfo> coAppInfo;

	public List<CoAppInfo> getCoAppInfo() {
		return coAppInfo;
	}

	public void setCoAppInfo(List<CoAppInfo> coAppInfo) {
		this.coAppInfo = coAppInfo;
	}
}
