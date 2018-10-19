package com.resoft.outinterface.SV.client2;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkOrderTransferVO {
	@XmlElement(name = "app_no")
	private String appNo;// 工单集编号
	@XmlElement(name = "affiliatedEnterpriseData")
	private List<AffiliatedEnterpriseData> affiliatedEnterpriseData;
	@XmlElement(name = "companyBasicInformation")
	private List<CompanyBasicInformation> companyBasicInformation;
	@XmlElement(name = "personalBasicData")
	private List<PersonalBasicData> personalBasicData;
	@XmlElement(name = "personalFamilyData")
	private List<PersonalFamilyData> personalFamilyData;
	@XmlElement(name = "pledgeHouseData")
	private List<PledgeHouseData> pledgeHouseData;
	@XmlElement(name = "pledgeVehicleInformation")
	private List<PledgeVehicleInformation> pledgeVehicleInformation;
	@XmlElement(name = "residenceInformation")
	private List<ResidenceInformation> residenceInformation;

	public String getApplyNo() {
		return appNo;
	}

	public void setApplyNo(String applyNo) {
		this.appNo = applyNo;
	}

	public List<AffiliatedEnterpriseData> getAffiliatedEnterpriseData() {
		return affiliatedEnterpriseData;
	}

	public void setAffiliatedEnterpriseData(List<AffiliatedEnterpriseData> affiliatedEnterpriseData) {
		this.affiliatedEnterpriseData = affiliatedEnterpriseData;
	}

	public List<CompanyBasicInformation> getCompanyBasicInformation() {
		return companyBasicInformation;
	}

	public void setCompanyBasicInformation(List<CompanyBasicInformation> companyBasicInformation) {
		this.companyBasicInformation = companyBasicInformation;
	}

	public List<PersonalBasicData> getPersonalBasicData() {
		return personalBasicData;
	}

	public void setPersonalBasicData(List<PersonalBasicData> personalBasicData) {
		this.personalBasicData = personalBasicData;
	}

	public List<PersonalFamilyData> getPersonalFamilyData() {
		return personalFamilyData;
	}

	public void setPersonalFamilyData(List<PersonalFamilyData> personalFamilyData) {
		this.personalFamilyData = personalFamilyData;
	}

	public List<PledgeHouseData> getPledgeHouseData() {
		return pledgeHouseData;
	}

	public void setPledgeHouseData(List<PledgeHouseData> pledgeHouseData) {
		this.pledgeHouseData = pledgeHouseData;
	}

	public List<PledgeVehicleInformation> getPledgeVehicleInformation() {
		return pledgeVehicleInformation;
	}

	public void setPledgeVehicleInformation(List<PledgeVehicleInformation> pledgeVehicleInformation) {
		this.pledgeVehicleInformation = pledgeVehicleInformation;
	}

	public List<ResidenceInformation> getResidenceInformation() {
		return residenceInformation;
	}

	public void setResidenceInformation(List<ResidenceInformation> residenceInformation) {
		this.residenceInformation = residenceInformation;
	}

}
