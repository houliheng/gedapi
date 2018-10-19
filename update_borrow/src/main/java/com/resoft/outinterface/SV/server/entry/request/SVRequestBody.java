package com.resoft.outinterface.SV.server.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.outinterface.SV.server.entry.request.url.SVRequestBodyUrl;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequestBody {
//	@XmlElement(required=false,name="BODY_INFO")
//	private SVRequestBodyInfo bodyInfo;
	@XmlElement(required=false,name="BODY_URL")
	private SVRequestBodyUrl bodyUrl;
//	public SVRequestBodyInfo getBodyInfo() {
//		return bodyInfo;
//	}
//	public void setBodyInfo(SVRequestBodyInfo bodyInfo) {
//		this.bodyInfo = bodyInfo;
//	}
	public SVRequestBodyUrl getBodyUrl() {
		return bodyUrl;
	}
	public void setBodyUrl(SVRequestBodyUrl bodyUrl) {
		this.bodyUrl = bodyUrl;
	}
}
