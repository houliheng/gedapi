package com.resoft.outinterface.SV.server.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequestHead {
	@XmlElement(required=false,name="APPLY_NO")
	private String applyNo;
	@XmlElement(required=false,name="SEQ_NO")
	private String seqNo;
//	@XmlElement(required=false,name="CONTRACT_NO")
//	private String contractNo;
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
//	public String getContractNo() {
//		return contractNo;
//	}
//	public void setContractNo(String contractNo) {
//		this.contractNo = contractNo;
//	}
}
