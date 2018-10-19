package com.resoft.outinterface.SV.server.entry.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequestBodyInfo {
	@XmlElement(required = false, name = "COMPANY")
	private List<SVCompany> company;
	@XmlElement(required = false, name = "PERSON")
	private List<SVPerson> person;
	@XmlElement(required = false, name = "MORTGAGE_CAR")
	private List<SVMortgageCar> mortgageCar;
	@XmlElement(required = false, name = "MORTGAGE_HOUSE")
	private List<SVMortgageHouse> mortgageHouse;

	public List<SVCompany> getCompany() {
		return company;
	}

	public void setCompany(List<SVCompany> company) {
		this.company = company;
	}

	public List<SVPerson> getPerson() {
		return person;
	}

	public void setPerson(List<SVPerson> person) {
		this.person = person;
	}

	public List<SVMortgageCar> getMortgageCar() {
		return mortgageCar;
	}

	public void setMortgageCar(List<SVMortgageCar> mortgageCar) {
		this.mortgageCar = mortgageCar;
	}

	public List<SVMortgageHouse> getMortgageHouse() {
		return mortgageHouse;
	}

	public void setMortgageHouse(List<SVMortgageHouse> mortgageHouse) {
		this.mortgageHouse = mortgageHouse;
	}
}
