package com.resoft.outinterface.hr.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Persons")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persons {
	@XmlElement(name="Person" ,required=true)
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Persons [person=" + person.toString() + "]";
	}
}
