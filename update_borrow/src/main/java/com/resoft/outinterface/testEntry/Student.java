package com.resoft.outinterface.testEntry;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;


@XmlRootElement  
@XmlType(name="Student",propOrder={"id","test","name","age","type","classroom"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
	@XmlAttribute(name="id")
	private int icd;
	private int id;  
	private String test;
    private String name;  
    private int age;  
    @XmlElement(required=false)
    private Type type;
    @XmlElement(required=true)
    private List<Classroom> classroom;  
    public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getIcd() {
		return icd;
	}

	public void setIcd(int icd) {
		this.icd = icd;
	}

	public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getTest() {
    	return test;
    }
    
    public void setTest(String test) {
    	this.test = test;
    }
    public List<Classroom> getClassroom() {
		return classroom;
	}

	public void setClassroom(List<Classroom> classroom) {
		this.classroom = classroom;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	
	//�޲ι��ź���һ����Ҫ������JXBContext�޷��������  
    public Student() {  
        super();  
    }

	@Override
	public String toString() {
		return "Student [ test=" + test + ", id=" + id + ", name="
				+ name + ", age=" + age + ", classroom=" + classroom.toString() + "]";
	}

    
}  