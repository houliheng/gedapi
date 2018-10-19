package com.resoft.outinterface.themis.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisResponse {
	@XmlElement(name="HEAD")
	private ThemisResponseHead head;
	@XmlElement(name="BODY")
	private ThemisResponseBody body;
	
	@Override
	public String toString() {
		return "ThemisResponse [head=" + head.toString() + ", body=" + body.toString() + "]";
	}
	public ThemisResponseHead getHead() {
		return head;
	}
	public void setHead(ThemisResponseHead head) {
		this.head = head;
	}
	public ThemisResponseBody getBody() {
		return body;
	}
	public void setBody(ThemisResponseBody body) {
		this.body = body;
	}
	public ThemisResponse() {
		super();
	}
}
