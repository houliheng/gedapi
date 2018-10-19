package com.resoft.outinterface.SV.client2;

import java.util.List;

public class WorkOrderNewField {

    private String app_no;

    private List<PersonalWaterField> personalWaterInformation;	// 个⼈人流⽔水信息

    private List<PersonalCreditField> personalCreditInformation;// 个⼈人征信信息

    private List<WorkField> workInformation;//工作信息

    private  List<ResidenceField> residenceInformation;	// 居住地信息

    private List<OtherField>	otherInformation;		// 其他信息

    private List<OpinionFeedbackField> opinionFeedback;//意⻅见反馈信息

    private List<CompanyCreditField> companyCreditInformation;// 企业征信信息

    private List<EnterpriseFinancialField> enterpriseFinancialInformation;// 企业财务信息

    public String getApp_no() {
        return app_no;
    }

    public void setApp_no(String app_no) {
        this.app_no = app_no;
    }

    public List<PersonalWaterField> getPersonalWaterInformation() {
        return personalWaterInformation;
    }

    public void setPersonalWaterInformation(List<PersonalWaterField> personalWaterInformation) {
        this.personalWaterInformation = personalWaterInformation;
    }

    public List<PersonalCreditField> getPersonalCreditInformation() {
        return personalCreditInformation;
    }

    public void setPersonalCreditInformation(List<PersonalCreditField> personalCreditInformation) {
        this.personalCreditInformation = personalCreditInformation;
    }

    public List<WorkField> getWorkInformation() {
        return workInformation;
    }

    public void setWorkInformation(List<WorkField> workInformation) {
        this.workInformation = workInformation;
    }

    public List<ResidenceField> getResidenceInformation() {
        return residenceInformation;
    }

    public void setResidenceInformation(List<ResidenceField> residenceInformation) {
        this.residenceInformation = residenceInformation;
    }

    public List<OtherField> getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(List<OtherField> otherInformation) {
        this.otherInformation = otherInformation;
    }

    public List<CompanyCreditField> getCompanyCreditInformation() {
        return companyCreditInformation;
    }

    public void setCompanyCreditInformation(List<CompanyCreditField> companyCreditInformation) {
        this.companyCreditInformation = companyCreditInformation;
    }

    public List<EnterpriseFinancialField> getEnterpriseFinancialInformation() {
        return enterpriseFinancialInformation;
    }

    public void setEnterpriseFinancialInformation(List<EnterpriseFinancialField> enterpriseFinancialInformation) {
        this.enterpriseFinancialInformation = enterpriseFinancialInformation;
    }

    public List<OpinionFeedbackField> getOpinionFeedback() {
        return opinionFeedback;
    }

    public void setOpinionFeedback(List<OpinionFeedbackField> opinionFeedback) {
        this.opinionFeedback = opinionFeedback;
    }
}
