package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkField {
    @JsonProperty(value = "P_unitName")
     private String punitName   ; //单位名称
    @JsonProperty(value = "P_unitType")
    private String punitType    ;//单位性质
    @JsonProperty(value = "P_jobCategory")
    private String pjobCategory    ; //职位类别
    @JsonProperty(value = "P_jobLevel")
    private String pjobLevel    ; //职位级别
    @JsonProperty(value = "P_jobName")
    private String pjobName    ; //职位名称
    @JsonProperty(value = "P_departmentName")
    private String pdepartmentName    ; //部⻔门名称
    @JsonProperty(value = "P_unitTelephone")
    private String punitTelephone    ; //单位电话
    @JsonProperty(value = "P_unitEntryDate")
    private String punitEntryDate    ;//本单位⼊入职⽇日期
    @JsonProperty(value = "P_wagePaymentMethod")
    private String pwagePaymentMethod    ;//⼯工资发放⽅方式
    @JsonProperty(value = "P_basicMonthlySalary")
    private String pbasicMonthlySalary    ; //每⽉月基本薪⾦金金
    @JsonProperty(value = "P_otherMonthlyIncome")
    private String potherMonthlyIncome    ;  //其他⽉月收⼊入
    @JsonProperty(value = "P_payrollDay")
    private String ppayrollDay    ; //⼯工资发放⽇日
    @JsonProperty(value = "P_unitAddress")
    private String punitAddress    ; //单位地址
    @JsonProperty(value = "P_unitDetailAddress")
    private String punitDetailAddress    ; //详细地址
    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getPunitName() {
        return punitName;
    }

    public void setPunitName(String punitName) {
        this.punitName = punitName;
    }

    public String getPunitType() {
        return punitType;
    }

    public void setPunitType(String punitType) {
        this.punitType = punitType;
    }

    public String getPjobCategory() {
        return pjobCategory;
    }

    public void setPjobCategory(String pjobCategory) {
        this.pjobCategory = pjobCategory;
    }

    public String getPjobLevel() {
        return pjobLevel;
    }

    public void setPjobLevel(String pjobLevel) {
        this.pjobLevel = pjobLevel;
    }

    public String getPjobName() {
        return pjobName;
    }

    public void setPjobName(String pjobName) {
        this.pjobName = pjobName;
    }

    public String getPdepartmentName() {
        return pdepartmentName;
    }

    public void setPdepartmentName(String pdepartmentName) {
        this.pdepartmentName = pdepartmentName;
    }

    public String getPunitTelephone() {
        return punitTelephone;
    }

    public void setPunitTelephone(String punitTelephone) {
        this.punitTelephone = punitTelephone;
    }

    public String getPunitEntryDate() {
        return punitEntryDate;
    }

    public void setPunitEntryDate(String punitEntryDate) {
        this.punitEntryDate = punitEntryDate;
    }

    public String getPwagePaymentMethod() {
        return pwagePaymentMethod;
    }

    public void setPwagePaymentMethod(String pwagePaymentMethod) {
        this.pwagePaymentMethod = pwagePaymentMethod;
    }

    public String getPbasicMonthlySalary() {
        return pbasicMonthlySalary;
    }

    public void setPbasicMonthlySalary(String pbasicMonthlySalary) {
        this.pbasicMonthlySalary = pbasicMonthlySalary;
    }

    public String getPotherMonthlyIncome() {
        return potherMonthlyIncome;
    }

    public void setPotherMonthlyIncome(String potherMonthlyIncome) {
        this.potherMonthlyIncome = potherMonthlyIncome;
    }

    public String getPpayrollDay() {
        return ppayrollDay;
    }

    public void setPpayrollDay(String ppayrollDay) {
        this.ppayrollDay = ppayrollDay;
    }

    public String getPunitAddress() {
        return punitAddress;
    }

    public void setPunitAddress(String punitAddress) {
        this.punitAddress = punitAddress;
    }

    public String getPunitDetailAddress() {
        return punitDetailAddress;
    }

    public void setPunitDetailAddress(String punitDetailAddress) {
        this.punitDetailAddress = punitDetailAddress;
    }
}
