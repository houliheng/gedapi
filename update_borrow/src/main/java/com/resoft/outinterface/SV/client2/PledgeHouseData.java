package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 抵押房产信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PledgeHouseData {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "c_propertyRight")
	private String c_propertyRight; // 1、产权属性
	@XmlElement(name = "c_houseOwnerName")
	private String c_houseOwnerName;// 2、产权所有人姓名
	@XmlElement(name = "c_housePropertyUseNature")
	private String c_housePropertyUseNature;// 3、房产使用性质
	@XmlElement(name = "c_planningPurposes")
	private String c_planningPurposes; // 4、规划用途
	@XmlElement(name = "c_propertyAddress")
	private String c_propertyAddress; // 房产地址区
	@XmlElement(name = "c_propertyDetailAddress")
	private String c_propertyDetailAddress; // 6、房产详细地址
	@XmlElement(name = "c_buildingArea")
	private String c_buildingArea;// 7、建筑面积
	@XmlElement(name = "c_useArea")
	private String c_useArea; // 8、使用面积
	@XmlElement(name = "c_hotelFloor")
	private String c_hotelFloor; // 9、所在楼层
	@XmlElement(name = "c_propertyTypes")
	private String c_propertyTypes; // 房产种类

	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getPropertyRight() {
		return c_propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.c_propertyRight = propertyRight;
	}

	public String getHouseOwnerName() {
		return c_houseOwnerName;
	}

	public void setHouseOwnerName(String houseOwnerName) {
		this.c_houseOwnerName = houseOwnerName;
	}

	public String getHousePropertyUseNature() {
		return c_housePropertyUseNature;
	}

	public void setHousePropertyUseNature(String housePropertyUseNature) {
		this.c_housePropertyUseNature = housePropertyUseNature;
	}

	public String getPlanningPurposes() {
		return c_planningPurposes;
	}

	public void setPlanningPurposes(String planningPurposes) {
		this.c_planningPurposes = planningPurposes;
	}

	public String getPropertyAddress() {
		return c_propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.c_propertyAddress = propertyAddress;
	}

	public String getPropertyDetailAddress() {
		return c_propertyDetailAddress;
	}

	public void setPropertyDetailAddress(String propertyDetailAddress) {
		this.c_propertyDetailAddress = propertyDetailAddress;
	}

	public String getBuildingArea() {
		return c_buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.c_buildingArea = buildingArea;
	}

	public String getUseArea() {
		return c_useArea;
	}

	public void setUseArea(String useArea) {
		this.c_useArea = useArea;
	}

	public String getHotelFloor() {
		return c_hotelFloor;
	}

	public void setHotelFloor(String hotelFloor) {
		this.c_hotelFloor = hotelFloor;
	}

	public String getPropertyTypes() {
		return c_propertyTypes;
	}

	public void setPropertyTypes(String propertyTypes) {
		this.c_propertyTypes = propertyTypes;
	}

}
