package com.resoft.outinterface.themis.entry.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisRequestBody {
	@XmlElement(name="NEW_FINANCE_BS")
	private List<NewFinanceBS> newFinanceBS;
	@XmlElement(name="NEW_FINANCE_PL")
	private List<NewFinancePL> newFinancePL;
	public ThemisRequestBody() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<NewFinanceBS> getNewFinanceBS() {
		return newFinanceBS;
	}

	public void setNewFinanceBS(List<NewFinanceBS> newFinanceBS) {
		this.newFinanceBS = newFinanceBS;
	}

	public List<NewFinancePL> getNewFinancePL() {
		return newFinancePL;
	}

	public void setNewFinancePL(List<NewFinancePL> newFinancePL) {
		this.newFinancePL = newFinancePL;
	}

	@Override
	public String toString() {
		return "Body [newFinanceBS=" + newFinanceBS.toString() + ", newFinancePL="
				+ newFinancePL.toString() + "]";
	}
	
}
