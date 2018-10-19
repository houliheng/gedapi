package com.resoft.credit.loanApply.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1510080098
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
 * 客户房产信息表  实体类  Table：CRE_CUST_HOUSING_INFO
 */
public class CreCustHousingInfo extends DataEntity<CreCustHousingInfo>{
	private String id;// varchar(32) default null comment 'id', 
	private String applyId;// varchar(32) default null comment '申请id', 
	private String houseType;// varchar(10) default null comment '住房类型（字典类型：house_type）', 
	private BigDecimal houseAmount;// decimal(18,2) default null comment '房贷金额/租金', 
	private Date housePurTime;// timestamp null default null comment '购房时间', 
	private BigDecimal housePrice;// decimal(18,2) default null comment '购房价格', 
	private BigDecimal houseArea;// decimal(18,2) default null comment '住房面积', 
	private BigDecimal proPercent;// decimal(18,2) default null comment '产权占比', 
	private Date liveBeginDate;// timestamp null default null comment '居住起始日期', 
	private Integer loadPeriod;// int(10) default null comment '贷款期限（购房的贷款期限）', 
	private BigDecimal monthPrice;// decimal(18,2) default null comment '月供', 
	private BigDecimal balance;// decimal(18,2) default null comment '贷款余额', 
	private String hsProvince;// varchar(32) default null comment '地址省', 
	private String hsCity;// varchar(32) default null comment '地址市', 
	private String hsDistinct;// varchar(32) default null comment '地址区', 
	private String hsDetails;// varchar(200) default null comment '地址-详细', 
	private String addrSync;// varchar(10) default null comment '房屋地址与现居地址是否一致（字典类型：yes_no）' 
	
	//扩展属性
	private String houseTypeLabel;//住房类型
	private String addrSyncLabel;//房屋地址与现居地址是否一致
	/**
	 * @return the houseTypeLabel
	 */
	public String getHouseTypeLabel() {
		return houseTypeLabel;
	}
	/**
	 * @param houseTypeLabel the houseTypeLabel to set
	 */
	public void setHouseTypeLabel(String houseTypeLabel) {
		this.houseTypeLabel = houseTypeLabel;
	}
	/**
	 * @return the addrSyncLabel
	 */
	public String getAddrSyncLabel() {
		return addrSyncLabel;
	}
	/**
	 * @param addrSyncLabel the addrSyncLabel to set
	 */
	public void setAddrSyncLabel(String addrSyncLabel) {
		this.addrSyncLabel = addrSyncLabel;
	}
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
	 * @return the houseType
	 */
	public String getHouseType() {
		return houseType;
	}
	/**
	 * @param houseType the houseType to set
	 */
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	/**
	 * @return the houseAmount
	 */
	public BigDecimal getHouseAmount() {
		return houseAmount;
	}
	/**
	 * @param houseAmount the houseAmount to set
	 */
	public void setHouseAmount(BigDecimal houseAmount) {
		this.houseAmount = houseAmount;
	}
	/**
	 * @return the housePurTime
	 */
	public Date getHousePurTime() {
		return housePurTime;
	}
	/**
	 * @param housePurTime the housePurTime to set
	 */
	public void setHousePurTime(Date housePurTime) {
		this.housePurTime = housePurTime;
	}
	/**
	 * @return the housePrice
	 */
	public BigDecimal getHousePrice() {
		return housePrice;
	}
	/**
	 * @param housePrice the housePrice to set
	 */
	public void setHousePrice(BigDecimal housePrice) {
		this.housePrice = housePrice;
	}
	/**
	 * @return the houseArea
	 */
	public BigDecimal getHouseArea() {
		return houseArea;
	}
	/**
	 * @param houseArea the houseArea to set
	 */
	public void setHouseArea(BigDecimal houseArea) {
		this.houseArea = houseArea;
	}
	/**
	 * @return the proPercent
	 */
	public BigDecimal getProPercent() {
		return proPercent;
	}
	/**
	 * @param proPercent the proPercent to set
	 */
	public void setProPercent(BigDecimal proPercent) {
		this.proPercent = proPercent;
	}
	/**
	 * @return the liveBeginDate
	 */
	public Date getLiveBeginDate() {
		return liveBeginDate;
	}
	/**
	 * @param liveBeginDate the liveBeginDate to set
	 */
	public void setLiveBeginDate(Date liveBeginDate) {
		this.liveBeginDate = liveBeginDate;
	}
	/**
	 * @return the loadPeriod
	 */
	public Integer getLoadPeriod() {
		return loadPeriod;
	}
	/**
	 * @param loadPeriod the loadPeriod to set
	 */
	public void setLoadPeriod(Integer loadPeriod) {
		this.loadPeriod = loadPeriod;
	}
	/**
	 * @return the monthPrice
	 */
	public BigDecimal getMonthPrice() {
		return monthPrice;
	}
	/**
	 * @param monthPrice the monthPrice to set
	 */
	public void setMonthPrice(BigDecimal monthPrice) {
		this.monthPrice = monthPrice;
	}
	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	/**
	 * @return the hsProvince
	 */
	public String getHsProvince() {
		return hsProvince;
	}
	/**
	 * @param hsProvince the hsProvince to set
	 */
	public void setHsProvince(String hsProvince) {
		this.hsProvince = hsProvince;
	}
	/**
	 * @return the hsCity
	 */
	public String getHsCity() {
		return hsCity;
	}
	/**
	 * @param hsCity the hsCity to set
	 */
	public void setHsCity(String hsCity) {
		this.hsCity = hsCity;
	}
	/**
	 * @return the hsDistinct
	 */
	public String getHsDistinct() {
		return hsDistinct;
	}
	/**
	 * @param hsDistinct the hsDistinct to set
	 */
	public void setHsDistinct(String hsDistinct) {
		this.hsDistinct = hsDistinct;
	}
	/**
	 * @return the hsDetails
	 */
	public String getHsDetails() {
		return hsDetails;
	}
	/**
	 * @param hsDetails the hsDetails to set
	 */
	public void setHsDetails(String hsDetails) {
		this.hsDetails = hsDetails;
	}
	/**
	 * @return the addrSync
	 */
	public String getAddrSync() {
		return addrSync;
	}
	/**
	 * @param addrSync the addrSync to set
	 */
	public void setAddrSync(String addrSync) {
		this.addrSync = addrSync;
	}
}
