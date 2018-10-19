package com.resoft.outinterface.rest.SVFinancialToThemis.entity;

import java.io.Serializable;
import java.util.List;

import com.resoft.credit.financialStateImport.entity.ThemisReportHead;
import com.resoft.credit.financialStateImport.entity.ThemisReportInfo;
/*
 * description:外访系统财报导入json字符串转化的实体类
 * */
public class SVFinancialToThemisRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<ThemisReportInfo>  triList1;
	private List<ThemisReportInfo>  triList2;
	private ThemisReportHead trh;
	public List<ThemisReportInfo> getTriList1() {
		return triList1;
	}
	public void setTriList1(List<ThemisReportInfo> triList1) {
		this.triList1 = triList1;
	}
	public List<ThemisReportInfo> getTriList2() {
		return triList2;
	}
	public void setTriList2(List<ThemisReportInfo> triList2) {
		this.triList2 = triList2;
	}
	public ThemisReportHead getTrh() {
		return trh;
	}
	public void setTrh(ThemisReportHead trh) {
		this.trh = trh;
	}
	
}
