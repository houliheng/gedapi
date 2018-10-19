package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVContractSignUrl {
	@XmlElement(required=true,name="BOR_CONTRACT")
	private List<SVRequestBaseData> borContract;
	@XmlElement(required=true,name="INSURE_CONTRACT")
	private List<SVRequestBaseData> insureContract;
	@XmlElement(required=true,name="SERVICE_CONTRACT")
	private List<SVRequestBaseData> serviceContract;
	@XmlElement(required=true,name="REFUND_ADD_CON")
	private List<SVRequestBaseData> refundInsureCon;
	@XmlElement(required=true,name="COLLECTION_CONFIRM")
	private List<SVRequestBaseData> collectionConfirm;
	@XmlElement(required=true,name="MORT_CONTRACT")
	private List<SVRequestBaseData> mortContract;
	@XmlElement(required=true,name="PLEDGE_CONTRACT")
	private List<SVRequestBaseData> pledgeContract;
	@XmlElement(required=true,name="HOUSE_BUS_CONCORD")
	private List<SVRequestBaseData> houseConcord;
	@XmlElement(required=true,name="OLD_CAR_BUS_CONTRACT")
	private List<SVRequestBaseData> oldCarContract;
	@XmlElement(required=true,name="MECH_BUS")
	private List<SVRequestBaseData> mechBus;
	@XmlElement(required=true,name="PUR_SALE_CONTRACT")
	private List<SVRequestBaseData> purSaleContract;
	@XmlElement(required=true,name="HOUSE_RENT_CONTRACT")
	private List<SVRequestBaseData> houseRentCon;
	@XmlElement(required=true,name="FACTORY_RENT_CONTRACT")
	private List<SVRequestBaseData> facRentCon;
	@XmlElement(required=true,name="LAND_RENT_CONTRACT")
	private List<SVRequestBaseData> landRentCon;
	@XmlElement(required=true,name="STOCK_ASSIGN")
	private List<SVRequestBaseData> stockAssign;
	@XmlElement(required=true,name="STOCK_MORT")
	private List<SVRequestBaseData> stockMort;
	@XmlElement(required=true,name="WAREHOUSE_SUPVI")
	private List<SVRequestBaseData> warehouseSupvi;
	@XmlElement(required=true,name="WAREHOUSE_RENT")
	private List<SVRequestBaseData> warehouseRent;
	@XmlElement(required=true,name="MANAGE_TRANSFER")
	private List<SVRequestBaseData> mangeTransfer;
	@XmlElement(required=true,name="RENT_INCOME_ASSIGN")
	private List<SVRequestBaseData> rentInAssign;
	@XmlElement(required=true,name="INCOME_ASSIGN_NOTICE")
	private List<SVRequestBaseData> inAssignNotice;
	@XmlElement(required=true,name="LAND_USE_ASS_CONTRACT")
	private List<SVRequestBaseData> landUseAssContract;
	@XmlElement(required=true,name="MORT_APP_DEAL")
	private List<SVRequestBaseData> mortAppDeal;
	@XmlElement(required=true,name="THIRD_MORT_APP_DEAL")
	private List<SVRequestBaseData> mort3AppDeal;
	@XmlElement(required=true,name="GOODS_PUR_SALE_CON")
	private List<SVRequestBaseData> goodsPurSaleCon;
	@XmlElement(required=true,name="SHAREHOLDER_RESOLUTION")
	private List<SVRequestBaseData> shareholderResln;
	@XmlElement(required=true,name="HOUSE_HE")
	private List<SVRequestBaseData> houseHe;
	@XmlElement(required=true,name="HOUSE_INVEST_REPORT")
	private List<SVRequestBaseData> houseInvestReport;
	@XmlElement(required=true,name="DRIVE_BOOK")
	private List<SVRequestBaseData> driveBook;
	@XmlElement(required=true,name="VEHICLE_REGIST")
	private List<SVRequestBaseData> vehicleRegist;
	@XmlElement(required=true,name="VEHICLE_QUALIFIED")
	private List<SVRequestBaseData> vehicleQualified;
	@XmlElement(required=true,name="VE_ADMIN_MORT_REGIST")
	private List<SVRequestBaseData> veAdminMortRegist;
	public List<SVRequestBaseData> getBorContract() {
		return borContract;
	}
	public void setBorContract(List<SVRequestBaseData> borContract) {
		this.borContract = borContract;
	}
	public List<SVRequestBaseData> getInsureContract() {
		return insureContract;
	}
	public void setInsureContract(List<SVRequestBaseData> insureContract) {
		this.insureContract = insureContract;
	}
	public List<SVRequestBaseData> getServiceContract() {
		return serviceContract;
	}
	public void setServiceContract(List<SVRequestBaseData> serviceContract) {
		this.serviceContract = serviceContract;
	}
	public List<SVRequestBaseData> getRefundInsureCon() {
		return refundInsureCon;
	}
	public void setRefundInsureCon(List<SVRequestBaseData> refundInsureCon) {
		this.refundInsureCon = refundInsureCon;
	}
	public List<SVRequestBaseData> getCollectionConfirm() {
		return collectionConfirm;
	}
	public void setCollectionConfirm(List<SVRequestBaseData> collectionConfirm) {
		this.collectionConfirm = collectionConfirm;
	}
	public List<SVRequestBaseData> getMortContract() {
		return mortContract;
	}
	public void setMortContract(List<SVRequestBaseData> mortContract) {
		this.mortContract = mortContract;
	}
	public List<SVRequestBaseData> getPledgeContract() {
		return pledgeContract;
	}
	public void setPledgeContract(List<SVRequestBaseData> pledgeContract) {
		this.pledgeContract = pledgeContract;
	}
	public List<SVRequestBaseData> getHouseConcord() {
		return houseConcord;
	}
	public void setHouseConcord(List<SVRequestBaseData> houseConcord) {
		this.houseConcord = houseConcord;
	}
	public List<SVRequestBaseData> getOldCarContract() {
		return oldCarContract;
	}
	public void setOldCarContract(List<SVRequestBaseData> oldCarContract) {
		this.oldCarContract = oldCarContract;
	}
	public List<SVRequestBaseData> getMechBus() {
		return mechBus;
	}
	public void setMechBus(List<SVRequestBaseData> mechBus) {
		this.mechBus = mechBus;
	}
	public List<SVRequestBaseData> getPurSaleContract() {
		return purSaleContract;
	}
	public void setPurSaleContract(List<SVRequestBaseData> purSaleContract) {
		this.purSaleContract = purSaleContract;
	}
	public List<SVRequestBaseData> getHouseRentCon() {
		return houseRentCon;
	}
	public void setHouseRentCon(List<SVRequestBaseData> houseRentCon) {
		this.houseRentCon = houseRentCon;
	}
	public List<SVRequestBaseData> getFacRentCon() {
		return facRentCon;
	}
	public void setFacRentCon(List<SVRequestBaseData> facRentCon) {
		this.facRentCon = facRentCon;
	}
	public List<SVRequestBaseData> getLandRentCon() {
		return landRentCon;
	}
	public void setLandRentCon(List<SVRequestBaseData> landRentCon) {
		this.landRentCon = landRentCon;
	}
	public List<SVRequestBaseData> getStockAssign() {
		return stockAssign;
	}
	public void setStockAssign(List<SVRequestBaseData> stockAssign) {
		this.stockAssign = stockAssign;
	}
	public List<SVRequestBaseData> getStockMort() {
		return stockMort;
	}
	public void setStockMort(List<SVRequestBaseData> stockMort) {
		this.stockMort = stockMort;
	}
	public List<SVRequestBaseData> getWarehouseSupvi() {
		return warehouseSupvi;
	}
	public void setWarehouseSupvi(List<SVRequestBaseData> warehouseSupvi) {
		this.warehouseSupvi = warehouseSupvi;
	}
	public List<SVRequestBaseData> getWarehouseRent() {
		return warehouseRent;
	}
	public void setWarehouseRent(List<SVRequestBaseData> warehouseRent) {
		this.warehouseRent = warehouseRent;
	}
	public List<SVRequestBaseData> getMangeTransfer() {
		return mangeTransfer;
	}
	public void setMangeTransfer(List<SVRequestBaseData> mangeTransfer) {
		this.mangeTransfer = mangeTransfer;
	}
	public List<SVRequestBaseData> getRentInAssign() {
		return rentInAssign;
	}
	public void setRentInAssign(List<SVRequestBaseData> rentInAssign) {
		this.rentInAssign = rentInAssign;
	}
	public List<SVRequestBaseData> getInAssignNotice() {
		return inAssignNotice;
	}
	public void setInAssignNotice(List<SVRequestBaseData> inAssignNotice) {
		this.inAssignNotice = inAssignNotice;
	}
	public List<SVRequestBaseData> getLandUseAssContract() {
		return landUseAssContract;
	}
	public void setLandUseAssContract(List<SVRequestBaseData> landUseAssContract) {
		this.landUseAssContract = landUseAssContract;
	}
	public List<SVRequestBaseData> getMortAppDeal() {
		return mortAppDeal;
	}
	public void setMortAppDeal(List<SVRequestBaseData> mortAppDeal) {
		this.mortAppDeal = mortAppDeal;
	}
	public List<SVRequestBaseData> getMort3AppDeal() {
		return mort3AppDeal;
	}
	public void setMort3AppDeal(List<SVRequestBaseData> mort3AppDeal) {
		this.mort3AppDeal = mort3AppDeal;
	}
	public List<SVRequestBaseData> getGoodsPurSaleCon() {
		return goodsPurSaleCon;
	}
	public void setGoodsPurSaleCon(List<SVRequestBaseData> goodsPurSaleCon) {
		this.goodsPurSaleCon = goodsPurSaleCon;
	}
	public List<SVRequestBaseData> getShareholderResln() {
		return shareholderResln;
	}
	public void setShareholderResln(List<SVRequestBaseData> shareholderResln) {
		this.shareholderResln = shareholderResln;
	}
	public List<SVRequestBaseData> getHouseHe() {
		return houseHe;
	}
	public void setHouseHe(List<SVRequestBaseData> houseHe) {
		this.houseHe = houseHe;
	}
	public List<SVRequestBaseData> getHouseInvestReport() {
		return houseInvestReport;
	}
	public void setHouseInvestReport(List<SVRequestBaseData> houseInvestReport) {
		this.houseInvestReport = houseInvestReport;
	}
	public List<SVRequestBaseData> getDriveBook() {
		return driveBook;
	}
	public void setDriveBook(List<SVRequestBaseData> driveBook) {
		this.driveBook = driveBook;
	}
	public List<SVRequestBaseData> getVehicleRegist() {
		return vehicleRegist;
	}
	public void setVehicleRegist(List<SVRequestBaseData> vehicleRegist) {
		this.vehicleRegist = vehicleRegist;
	}
	public List<SVRequestBaseData> getVehicleQualified() {
		return vehicleQualified;
	}
	public void setVehicleQualified(List<SVRequestBaseData> vehicleQualified) {
		this.vehicleQualified = vehicleQualified;
	}
	public List<SVRequestBaseData> getVeAdminMortRegist() {
		return veAdminMortRegist;
	}
	public void setVeAdminMortRegist(List<SVRequestBaseData> veAdminMortRegist) {
		this.veAdminMortRegist = veAdminMortRegist;
	}
}
