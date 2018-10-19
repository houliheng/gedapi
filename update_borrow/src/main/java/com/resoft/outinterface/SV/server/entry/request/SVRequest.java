package com.resoft.outinterface.SV.server.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequest {
	@XmlElement(required=false,name="HEAD")
	private SVRequestHead header;
	@XmlElement(required=false,name="BODY")
	private SVRequestBody body;
	public SVRequestHead getHeader() {
		return header;
	}
	public void setHeader(SVRequestHead header) {
		this.header = header;
	}
	public SVRequestBody getBody() {
		return body;
	}
	public void setBody(SVRequestBody body) {
		this.body = body;
	}
}
