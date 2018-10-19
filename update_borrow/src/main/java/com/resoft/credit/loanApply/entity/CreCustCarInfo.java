package com.resoft.credit.loanApply.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1510080100
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_车辆信息【默认数据项】展现
 *  客户车辆信息表      实体类   Table:CRE_CUST_CAR_INFO
 */
public class CreCustCarInfo extends DataEntity<CreCustCarInfo>{
	private static final long serialVersionUID = -9147720041138682582L;
	
	private String id;// varchar(32) default null comment 'id', 
	private String applyId;// varchar(32) default null comment '申请id', 
	private String carBrand;// varchar(100) default null comment '车辆品牌', 
	private String carType;// varchar(10) default null comment '车辆类型（字典类型：car_type）', 
	private String carplateNum;// varchar(20) default null comment '车牌号码', 
	private String carengineNum;// varchar(20) default null comment '发动机号', 
	private String carFrameNum;// varchar(20) default null comment '车架号', 
	private String carcolor;// varchar(20) default null comment '车身颜色', 
	private Integer usePeriod;// int(10) default null comment '已使用年限(个月)', 
	private Integer carPeriod;// int(10) default null comment '规定年限（字典类型：car_period）', 
	private BigDecimal mileage;// decimal(18,2) default null comment '累计行驶里程(万公里)', 
	private BigDecimal buyPrice;// decimal(18,2) default null comment '购入原价（元）', 
	private String tendingState;// varchar(10) default null comment '维护保养情况（字典类型：tending_state）', 
	private String workNature;// varchar(10) default null comment '工作性质（字典类型：work_nature）' 
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the applyId
	 */
	public String getApplyNo() {
		return applyId;
	}
	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyNo(String applyId) {
		this.applyId = applyId;
	}
	/**
	 * @return the carBrand
	 */
	public String getCarBrand() {
		return carBrand;
	}
	/**
	 * @param carBrand the carBrand to set
	 */
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	/**
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}
	/**
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}
	/**
	 * @return the carplateNum
	 */
	public String getCarplateNum() {
		return carplateNum;
	}
	/**
	 * @param carplateNum the carplateNum to set
	 */
	public void setCarplateNum(String carplateNum) {
		this.carplateNum = carplateNum;
	}
	/**
	 * @return the carengineNum
	 */
	public String getCarengineNum() {
		return carengineNum;
	}
	/**
	 * @param carengineNum the carengineNum to set
	 */
	public void setCarengineNum(String carengineNum) {
		this.carengineNum = carengineNum;
	}
	/**
	 * @return the carFrameNum
	 */
	public String getCarFrameNum() {
		return carFrameNum;
	}
	/**
	 * @param carFrameNum the carFrameNum to set
	 */
	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}
	/**
	 * @return the carcolor
	 */
	public String getCarcolor() {
		return carcolor;
	}
	/**
	 * @param carcolor the carcolor to set
	 */
	public void setCarcolor(String carcolor) {
		this.carcolor = carcolor;
	}
	/**
	 * @return the usePeriod
	 */
	public Integer getUsePeriod() {
		return usePeriod;
	}
	/**
	 * @param usePeriod the usePeriod to set
	 */
	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}
	/**
	 * @return the carPeriod
	 */
	public Integer getCarPeriod() {
		return carPeriod;
	}
	/**
	 * @param carPeriod the carPeriod to set
	 */
	public void setCarPeriod(Integer carPeriod) {
		this.carPeriod = carPeriod;
	}
	/**
	 * @return the mileage
	 */
	public BigDecimal getMileage() {
		return mileage;
	}
	/**
	 * @param mileage the mileage to set
	 */
	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}
	/**
	 * @return the buyPrice
	 */
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	/**
	 * @param buyPrice the buyPrice to set
	 */
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	/**
	 * @return the tendingState
	 */
	public String getTendingState() {
		return tendingState;
	}
	/**
	 * @param tendingState the tendingState to set
	 */
	public void setTendingState(String tendingState) {
		this.tendingState = tendingState;
	}
	/**
	 * @return the workNature
	 */
	public String getWorkNature() {
		return workNature;
	}
	/**
	 * @param workNature the workNature to set
	 */
	public void setWorkNature(String workNature) {
		this.workNature = workNature;
	}
}
