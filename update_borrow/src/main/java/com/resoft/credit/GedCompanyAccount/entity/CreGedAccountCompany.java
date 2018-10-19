package com.resoft.credit.GedCompanyAccount.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.restlet.util.StringReadingListener;

import java.util.Date;

/**
 * 冠E贷企业账户信息Entity
 * @author gsh
 * @version 2018-03-31
 */
/**
 * @author JML
 *
 */
public class CreGedAccountCompany extends DataEntity<CreGedAccountCompany> {
	
	private static final long serialVersionUID = 1L;
	private Long userId;		// 客户ID
	private String companyName;		// 公司名称(企业名称)
	private String companyCardType;		// company_card_type
	private String socialCreditCode;		// 统一社会信用代码
	private String businessLicence;		// business_licence
	private String organizationCode;		// organization_code
	private String taxCode;		// tax_code
	private String legalName;		// legal_name
	private String legalMobile;		// legal_mobile
	private String legalCardType;		// legal_card_type
	private String legalCardNumber;		// legal_card_number
	private String companyContact;		// company_contact
	private String contactMobile;		// contact_mobile
	private String companyBankOfDeposit;		// 开户行编码
	private String companyBankOfDepositValue;		// 开户行名称
	private String companyAccount;		// 对公银行卡号
	private String companyBankBranchName;		// company_bank_branch_name
	private String provinceCode;		// province_code
	private String cityCode;		// city_code
	private String areaCode;		// area_code
	private String idCardFaceUrl;		// id_card_face_url
	private String idCardBackUrl;		// id_card_back_url
	private String idCardHoldUrl;		// id_card_hold_url
	private String businessLicenceUrl;		// business_licence_url
	private String accountsPermitsUrl;		// accounts_permits_url
	private Long createId;		// create_id
	private Date createTime;		// create_time
	private Integer status;
	private String address;
	
	private String accountVerifyInfo;
	private Integer returnTime;
	private String custId;
	private String companyId;
	public CreGedAccountCompany() {
		super();
	}

	public CreGedAccountCompany(String id){
		super(id);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=100, message="公司名称(企业名称)长度必须介于 0 和 100 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=20, message="company_card_type长度必须介于 0 和 20 之间")
	public String getCompanyCardType() {
		return companyCardType;
	}

	public void setCompanyCardType(String companyCardType) {
		this.companyCardType = companyCardType;
	}
	
	@Length(min=0, max=100, message="统一社会信用代码长度必须介于 0 和 100 之间")
	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}
	
	@Length(min=0, max=100, message="business_licence长度必须介于 0 和 100 之间")
	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	
	@Length(min=0, max=100, message="organization_code长度必须介于 0 和 100 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=100, message="tax_code长度必须介于 0 和 100 之间")
	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	@Length(min=0, max=100, message="legal_name长度必须介于 0 和 100 之间")
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	@Length(min=0, max=20, message="legal_mobile长度必须介于 0 和 20 之间")
	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	
	@Length(min=0, max=1, message="legal_card_type长度必须介于 0 和 1 之间")
	public String getLegalCardType() {
		return legalCardType;
	}

	public void setLegalCardType(String legalCardType) {
		this.legalCardType = legalCardType;
	}
	
	@Length(min=0, max=20, message="legal_card_number长度必须介于 0 和 20 之间")
	public String getLegalCardNumber() {
		return legalCardNumber;
	}

	public void setLegalCardNumber(String legalCardNumber) {
		this.legalCardNumber = legalCardNumber;
	}
	
	@Length(min=0, max=100, message="company_contact长度必须介于 0 和 100 之间")
	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}
	
	@Length(min=0, max=20, message="contact_mobile长度必须介于 0 和 20 之间")
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	@Length(min=0, max=100, message="开户行编码长度必须介于 0 和 100 之间")
	public String getCompanyBankOfDeposit() {
		return companyBankOfDeposit;
	}

	public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
		this.companyBankOfDeposit = companyBankOfDeposit;
	}
	
	@Length(min=0, max=100, message="开户行名称长度必须介于 0 和 100 之间")
	public String getCompanyBankOfDepositValue() {
		return companyBankOfDepositValue;
	}

	public void setCompanyBankOfDepositValue(String companyBankOfDepositValue) {
		this.companyBankOfDepositValue = companyBankOfDepositValue;
	}
	
	@Length(min=0, max=100, message="对公银行卡号长度必须介于 0 和 100 之间")
	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	
	@Length(min=0, max=100, message="company_bank_branch_name长度必须介于 0 和 100 之间")
	public String getCompanyBankBranchName() {
		return companyBankBranchName;
	}

	public void setCompanyBankBranchName(String companyBankBranchName) {
		this.companyBankBranchName = companyBankBranchName;
	}
	
	@Length(min=0, max=10, message="province_code长度必须介于 0 和 10 之间")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	@Length(min=0, max=10, message="city_code长度必须介于 0 和 10 之间")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Length(min=0, max=10, message="area_code长度必须介于 0 和 10 之间")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Length(min=0, max=100, message="id_card_face_url长度必须介于 0 和 100 之间")
	public String getIdCardFaceUrl() {
		return idCardFaceUrl;
	}

	public void setIdCardFaceUrl(String idCardFaceUrl) {
		this.idCardFaceUrl = idCardFaceUrl;
	}
	
	@Length(min=0, max=100, message="id_card_back_url长度必须介于 0 和 100 之间")
	public String getIdCardBackUrl() {
		return idCardBackUrl;
	}

	public void setIdCardBackUrl(String idCardBackUrl) {
		this.idCardBackUrl = idCardBackUrl;
	}
	
	@Length(min=0, max=100, message="id_card_hold_url长度必须介于 0 和 100 之间")
	public String getIdCardHoldUrl() {
		return idCardHoldUrl;
	}

	public void setIdCardHoldUrl(String idCardHoldUrl) {
		this.idCardHoldUrl = idCardHoldUrl;
	}
	
	@Length(min=0, max=100, message="business_licence_url长度必须介于 0 和 100 之间")
	public String getBusinessLicenceUrl() {
		return businessLicenceUrl;
	}

	public void setBusinessLicenceUrl(String businessLicenceUrl) {
		this.businessLicenceUrl = businessLicenceUrl;
	}
	
	@Length(min=0, max=100, message="accounts_permits_url长度必须介于 0 和 100 之间")
	public String getAccountsPermitsUrl() {
		return accountsPermitsUrl;
	}

	public void setAccountsPermitsUrl(String accountsPermitsUrl) {
		this.accountsPermitsUrl = accountsPermitsUrl;
	}
	
	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccountVerifyInfo() {
		return accountVerifyInfo;
	}

	public void setAccountVerifyInfo(String accountVerifyInfo) {
		this.accountVerifyInfo = accountVerifyInfo;
	}

	public Integer getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Integer returnTime) {
		this.returnTime = returnTime;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}