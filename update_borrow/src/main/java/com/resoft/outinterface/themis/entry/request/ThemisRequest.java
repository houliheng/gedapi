package com.resoft.outinterface.themis.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisRequest{
	@XmlElement(name="HEAD")
	private ThemisRequestHead head;
	@XmlElement(name="BODY")
	private ThemisRequestBody body;
	
	
	@Override
	public String toString() {
		return "ThemisRequest [head=" + head.toString() + ", body=" + body.toString() + "]";
	}
	public ThemisRequestHead getHead() {
		return head;
	}
	public void setHead(ThemisRequestHead head) {
		this.head = head;
	}
	public ThemisRequestBody getBody() {
		return body;
	}
	public void setBody(ThemisRequestBody body) {
		this.body = body;
	}
	public ThemisRequest() {
		super();
	}
}
