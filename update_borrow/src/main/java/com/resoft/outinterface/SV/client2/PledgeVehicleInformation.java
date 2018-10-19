package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 质押车辆信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PledgeVehicleInformation {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "c_entrustedPersonContactWay")
	private String c_entrustedPersonContactWay;// 12、委托办理人联系方式
	@XmlElement(name = "c_productAttributes")
	private String c_productAttributes; // 1、产品属性
	@XmlElement(name = "c_propertyShareName")
	private String c_propertyShareName;// 3、产权共有人姓名
	@XmlElement(name = "c_propertyOwnerName")
	private String c_propertyOwnerName;// 2、产权所有人姓名
	@XmlElement(name = "c_purchaseTime")
	private String c_purchaseTime;// 4、购置时间
	@XmlElement(name = "c_vehicleBrand")
	private String c_vehicleBrand;// 5、车辆品牌
	@XmlElement(name = "c_vehicleType")
	private String c_vehicleType;// 6、车辆型号
	@XmlElement(name = "c_otherInformation")
	private String c_otherInformation; // 7、其他信息
	@XmlElement(name = "c_carBusinessAddress")
	private String c_carBusinessAddress;// 经营地址区
	@XmlElement(name = "c_carBusinessDetailAddress")
	private String c_carBusinessDetailAddress;// 9、经营详细地址
	@XmlElement(name = "c_legalRepresentative")
	private String c_legalRepresentative;// 10、法定代表人
	@XmlElement(name = "c_entrustedHandlePerson")
	private String c_entrustedHandlePerson;// 11、委托办理人
	@XmlElement(name = "c_entrustedRelationship")
	private String c_entrustedRelationship;// 13、委托办理人与公司关系

	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getEntrustedPersonContactWay() {
		return c_entrustedPersonContactWay;
	}

	public void setEntrustedPersonContactWay(String entrustedPersonContactWay) {
		this.c_entrustedPersonContactWay = entrustedPersonContactWay;
	}

	public String getProductAttributes() {
		return c_productAttributes;
	}

	public void setProductAttributes(String productAttributes) {
		this.c_productAttributes = productAttributes;
	}

	public String getPropertyShareName() {
		return c_propertyShareName;
	}

	public void setPropertyShareName(String propertyShareName) {
		this.c_propertyShareName = propertyShareName;
	}

	public String getPropertyOwnerName() {
		return c_propertyOwnerName;
	}

	public void setPropertyOwnerName(String propertyOwnerName) {
		this.c_propertyOwnerName = propertyOwnerName;
	}

	public String getPurchaseTime() {
		return c_purchaseTime;
	}

	public void setPurchaseTime(String purchaseTime) {
		this.c_purchaseTime = purchaseTime;
	}

	public String getVehicleBrand() {
		return c_vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.c_vehicleBrand = vehicleBrand;
	}

	public String getVehicleType() {
		return c_vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.c_vehicleType = vehicleType;
	}

	public String getOtherInformation() {
		return c_otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.c_otherInformation = otherInformation;
	}

	public String getCarBusinessAddress() {
		return c_carBusinessAddress;
	}

	public void setCarBusinessAddress(String carBusinessAddress) {
		this.c_carBusinessAddress = carBusinessAddress;
	}

	public String getCarBusinessDetailAddress() {
		return c_carBusinessDetailAddress;
	}

	public void setCarBusinessDetailAddress(String carBusinessDetailAddress) {
		this.c_carBusinessDetailAddress = carBusinessDetailAddress;
	}

	public String getLegalRepresentative() {
		return c_legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.c_legalRepresentative = legalRepresentative;
	}

	public String getEntrustedHandlePerson() {
		return c_entrustedHandlePerson;
	}

	public void setEntrustedHandlePerson(String entrustedHandlePerson) {
		this.c_entrustedHandlePerson = entrustedHandlePerson;
	}

	public String getEntrustedRelationship() {
		return c_entrustedRelationship;
	}

	public void setEntrustedRelationship(String entrustedRelationship) {
		this.c_entrustedRelationship = entrustedRelationship;
	}

}
