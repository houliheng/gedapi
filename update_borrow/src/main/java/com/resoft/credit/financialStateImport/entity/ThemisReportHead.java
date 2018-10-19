package com.resoft.credit.financialStateImport.entity;


import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * Themis报表头Entity
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReportHead extends DataEntity<ThemisReportHead> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;
	private User user;
	private String depcode;		// 部门编码
	private String companycode;		// 企业编码
	private String companyChnName;		// 企业中文名称
	private String companyEngName;		// 企业英文名称
	private String buildDate;		// 企业成立年月(格式：YYYYMM)
	private String companynature;		// 企业性质(全民所有制企业 01 集体所有制企业 02联营企业03 三资企业 04 私营企业05 其它企业06)
	private String mainIndustryCode;		// 主行业代码
	private String gbIndu1;		// 国标一级
	private String gbIndu2;		// 国标二级
	private String marketClass;		// 是否上市(上市  1,非上市 2)
	private String postcode;		// 邮政编码
	private String isMerger;		// 是否合并(非合并报表1，合并报表2)
	private String reportType;		// 报表类型(年报、季报)
	private String reportUnit;		// 报表单位(报表单位为&ldquo;元&rdquo; 1报表单位为&ldquo;千元&rdquo;2报表单位为&ldquo;万元&rdquo;3报表单位为&ldquo;千万元&rdquo;4报表单位为&ldquo;亿&rdquo;5)
	
	
	
	
	public ThemisReportHead(String companycode, String companyChnName,
			String companyEngName, String bulidDate, String companynature,
			String gbIndu1, String gbIndu2, String marketClass,
			String postcode, String isMerger, String reportType,
			String reportUnit) {
		super();
		this.companycode = companycode;
		this.companyChnName = companyChnName;
		this.companyEngName = companyEngName;
		this.buildDate = bulidDate;
		this.companynature = companynature;
		this.gbIndu1 = gbIndu1;
		this.gbIndu2 = gbIndu2;
		this.marketClass = marketClass;
		this.postcode = postcode;
		this.isMerger = isMerger;
		this.reportType = reportType;
		this.reportUnit = reportUnit;
	}

	public ThemisReportHead() {
		super();
	}

	public ThemisReportHead(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="部门编码长度必须介于 0 和 255 之间")
	public String getDepcode() {
		return depcode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}
	
	@Length(min=1, max=255, message="企业编码长度必须介于 1 和 255 之间")
	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	@Length(min=0, max=255, message="企业中文名称长度必须介于 0 和 255 之间")
	public String getCompanyChnName() {
		return companyChnName;
	}

	public void setCompanyChnName(String companyChnName) {
		this.companyChnName = companyChnName;
	}
	
	@Length(min=0, max=255, message="企业英文名称长度必须介于 0 和 255 之间")
	public String getCompanyEngName() {
		return companyEngName;
	}

	public void setCompanyEngName(String companyEngName) {
		this.companyEngName = companyEngName;
	}
	
	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	@Length(min=0, max=255, message="企业性质(全民所有制企业 01 集体所有制企业 02联营企业03 三资企业 04 私营企业05 其它企业06)长度必须介于 0 和 255 之间")
	public String getCompanynature() {
		return companynature;
	}

	public void setCompanynature(String companynature) {
		this.companynature = companynature;
	}
	
	@Length(min=0, max=255, message="主行业代码长度必须介于 0 和 255 之间")
	public String getMainIndustryCode() {
		return mainIndustryCode;
	}

	public void setMainIndustryCode(String mainIndustryCode) {
		this.mainIndustryCode = mainIndustryCode;
	}
	
	@Length(min=0, max=255, message="国标一级长度必须介于 0 和 255 之间")
	public String getGbIndu1() {
		return gbIndu1;
	}

	public void setGbIndu1(String gbIndu1) {
		this.gbIndu1 = gbIndu1;
	}
	
	@Length(min=0, max=255, message="国标二级长度必须介于 0 和 255 之间")
	public String getGbIndu2() {
		return gbIndu2;
	}

	public void setGbIndu2(String gbIndu2) {
		this.gbIndu2 = gbIndu2;
	}
	
	@Length(min=0, max=255, message="是否上市(上市  1,非上市 2)长度必须介于 0 和 255 之间")
	public String getMarketClass() {
		return marketClass;
	}

	public void setMarketClass(String marketClass) {
		this.marketClass = marketClass;
	}
	
	@Length(min=0, max=255, message="邮政编码长度必须介于 0 和 255 之间")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Length(min=0, max=255, message="是否合并(非合并报表1，合并报表2)长度必须介于 0 和 255 之间")
	public String getIsMerger() {
		return isMerger;
	}

	public void setIsMerger(String isMerger) {
		this.isMerger = isMerger;
	}
	
	@Length(min=0, max=255, message="报表类型(年报、季报)长度必须介于 0 和 255 之间")
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	@Length(min=0, max=255, message="报表单位(报表单位为&ldquo;元&rdquo; 1报表单位为&ldquo;千元&rdquo;2报表单位为&ldquo;万元&rdquo;3报表单位为&ldquo;千万元&rdquo;4报表单位为&ldquo;亿&rdquo;5)长度必须介于 0 和 255 之间")
	public String getReportUnit() {
		return reportUnit;
	}

	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
}