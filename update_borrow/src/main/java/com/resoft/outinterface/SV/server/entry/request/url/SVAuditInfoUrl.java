package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVAuditInfoUrl {
	@XmlElement(required=true,name="SIGNED_AUDIT")
	private List<SVRequestBaseData> signedAudit;
	@XmlElement(required=true,name="CUST_NOTIFY")
	private List<SVRequestBaseData> custNotify;
	@XmlElement(required=true,name="OTHER_FORM")
	private List<SVRequestBaseData> otherForm;
	public List<SVRequestBaseData> getSignedAudit() {
		return signedAudit;
	}
	public void setSignedAudit(List<SVRequestBaseData> signedAudit) {
		this.signedAudit = signedAudit;
	}
	public List<SVRequestBaseData> getCustNotify() {
		return custNotify;
	}
	public void setCustNotify(List<SVRequestBaseData> custNotify) {
		this.custNotify = custNotify;
	}
	public List<SVRequestBaseData> getOtherForm() {
		return otherForm;
	}
	public void setOtherForm(List<SVRequestBaseData> otherForm) {
		this.otherForm = otherForm;
	}
}
