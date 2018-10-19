package com.resoft.outinterface.testEntry;

import java.io.Serializable;

public class Classroom  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
    private int id;  
    private String name;  
    private int grade;  
    private Goods goods;
    public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getGrade() {  
        return grade;  
    }  
  
    public void setGrade(int grade) {  
        this.grade = grade;  
    }  
  
    public Classroom(int id, String name, int grade) {  
        super();  
        this.id = id;  
        this.name = name;  
        this.grade = grade;  
    }  
  
    public Classroom() {  
        super();  
    }

	@Override
	public String toString() {
		return "Classroom [id=" + id + ", name=" + name + ", grade=" + grade
				+ ", goods=" + goods.toString() + "]";
	}  
  
}  
