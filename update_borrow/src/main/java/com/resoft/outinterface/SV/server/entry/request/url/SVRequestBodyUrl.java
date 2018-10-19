package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequestBodyUrl {
	@XmlElement(required=true,name="MAIN_BORROWER_PIC")
	private List<SVMainBorrowerUrl> mainBorrowerPic;
	@XmlElement(required=true,name="CO_BORROWER_PIC")
	private List<SVCoBorrowerUrl> coBorrowerPic;
	@XmlElement(required=true,name="GU_BORROWER_PIC")
	private List<SVGuBorrowerUrl> guBorrowerPic;
	@XmlElement(required=true,name="GU_COMPANY_PIC")
	private List<SVGuCompanyUrl> guCompanyPic;
	@XmlElement(required=true,name="MAIN_COMPANY_PIC")
	private List<SVMainCompanyUrl> mainCompanyPic;
	@XmlElement(required=true,name="MORT_APPLICATION_PIC")
	private List<SVMortApplicationInfoUrl> mortApplicationPic;
	@XmlElement(required=true,name="AUDIT_INFO_PIC")
	private List<SVAuditInfoUrl> auditInfoPic;
	@XmlElement(required=true,name="GQ_GET")
	private List<SVRequestBaseData> GQGet;
	@XmlElement(required=true,name="CONTRACT_SIGN_PIC")
	private List<SVContractSignUrl> contractSignPic;
	@XmlElement(required=true,name="AFTER_BORROWER_PIC")
	private List<SVAfterBorrowerUrl> afterBorrowerPic;
	@XmlElement(required=true,name="PDF_PIC")
	private List<SVRequestBaseData> pdfPic;
	@XmlElement(required=true,name="FINANCIAL_STATE_EXCEL")
	private List<SVRequestBaseData> financialStateExcel;
	public List<SVMainBorrowerUrl> getMainBorrowerPic() {
		return mainBorrowerPic;
	}
	public void setMainBorrowerPic(List<SVMainBorrowerUrl> mainBorrowerPic) {
		this.mainBorrowerPic = mainBorrowerPic;
	}
	public List<SVCoBorrowerUrl> getCoBorrowerPic() {
		return coBorrowerPic;
	}
	public void setCoBorrowerPic(List<SVCoBorrowerUrl> coBorrowerPic) {
		this.coBorrowerPic = coBorrowerPic;
	}
	public List<SVGuBorrowerUrl> getGuBorrowerPic() {
		return guBorrowerPic;
	}
	public void setGuBorrowerPic(List<SVGuBorrowerUrl> guBorrowerPic) {
		this.guBorrowerPic = guBorrowerPic;
	}
	public List<SVGuCompanyUrl> getGuCompanyPic() {
		return guCompanyPic;
	}
	public void setGuCompanyPic(List<SVGuCompanyUrl> guCompanyPic) {
		this.guCompanyPic = guCompanyPic;
	}
	public List<SVMainCompanyUrl> getMainCompanyPic() {
		return mainCompanyPic;
	}
	public void setMainCompanyPic(List<SVMainCompanyUrl> mainCompanyPic) {
		this.mainCompanyPic = mainCompanyPic;
	}
	public List<SVMortApplicationInfoUrl> getMortApplicationPic() {
		return mortApplicationPic;
	}
	public void setMortApplicationPic(
			List<SVMortApplicationInfoUrl> mortApplicationPic) {
		this.mortApplicationPic = mortApplicationPic;
	}
	public List<SVAuditInfoUrl> getAuditInfoPic() {
		return auditInfoPic;
	}
	public void setAuditInfoPic(List<SVAuditInfoUrl> auditInfoPic) {
		this.auditInfoPic = auditInfoPic;
	}
	public List<SVRequestBaseData> getGQGet() {
		return GQGet;
	}
	public void setGQGet(List<SVRequestBaseData> gQGet) {
		GQGet = gQGet;
	}
	public List<SVContractSignUrl> getContractSignPic() {
		return contractSignPic;
	}
	public void setContractSignPic(List<SVContractSignUrl> contractSignPic) {
		this.contractSignPic = contractSignPic;
	}
	public List<SVAfterBorrowerUrl> getAfterBorrowerPic() {
		return afterBorrowerPic;
	}
	public void setAfterBorrowerPic(List<SVAfterBorrowerUrl> afterBorrowerPic) {
		this.afterBorrowerPic = afterBorrowerPic;
	}
	public List<SVRequestBaseData> getPdfPic() {
		return pdfPic;
	}
	public void setPdfPic(List<SVRequestBaseData> pdfPic) {
		this.pdfPic = pdfPic;
	}
	public List<SVRequestBaseData> getFinancialStateExcel() {
		return financialStateExcel;
	}
	public void setFinancialStateExcel(List<SVRequestBaseData> financialStateExcel) {
		this.financialStateExcel = financialStateExcel;
	}
}
