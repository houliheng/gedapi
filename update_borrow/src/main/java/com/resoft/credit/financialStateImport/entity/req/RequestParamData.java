package com.resoft.credit.financialStateImport.entity.req;

import com.resoft.credit.financialStateImport.entity.ThemisReportHead;

import java.io.Serializable;

public class RequestParamData implements Serializable {

    private CompanyInfo companyInfo;
    private ReportData reportData;

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public ReportData getReportData() {
        return reportData;
    }

    public void setReportData(ReportData reportData) {
        this.reportData = reportData;
    }
    /*private CompanyInfo companyInfo;
    private ReportData reportData;

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public ReportData getReportData() {
        return reportData;
    }

    public void setReportData(ReportData reportData) {
        this.reportData = reportData;
    }*/
}
