package com.resoft.credit.checkWeb.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 网查Entity
 * @author yanwanmei
 * @version 2016-02-27
 */
public class CheckWeb extends DataEntity<CheckWeb> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 核查对象
	private String custId;		// 客户ID（主借人ID,共借人ID,担保人ID，配偶ID等）
	private String custName;		// 姓名
	private String idType;		// 证件类型
	private String idNum;		// 证件号
	private String mobileNum;		// 移动电话
	private String zhixingCourtGovFlg;		// 全国法院被执行人信息查询_标示
	private String zhixingCourtGovDesc;		// 全国法院被执行人信息查询_结论
	private String baiduFlg;		// 百度_标示
	private String baiduDesc;		// 百度_结论
	private String nacaoOrgFlg;		// 全国组织代码管理中心_标示
	private String nacaoOrgDesc;		// 全国组织代码管理中心_结论
	private String judgementsOnlineFlg;		// 中国裁判文书网_标示
	private String judgementsOnlineDesc;		// 中国裁判文书网_结论
	private String cfdaFlg;		// 国家食品药品监督管理总局数据查询_标示
	private String cfdaDesc;		// 国家食品药品监督管理总局数据查询_结论
	private String bjygscxFlg;		// 全国道路运输证件查询系统_标示
	private String bjygscxDesc;		// 全国道路运输证件查询系统_结论
	private String zhongdengwangOrgFlg;		// 中登网_标示
	private String zhongdengwangOrgDesc;		// 中登网_结论
	private String gacprcFlg;		// 海关总署进出口报关证查询_标示
	private String gacprcDesc;		// 海关总署进出口报关证查询_结论
	private String gsxtSaicGovFlg;		// 全国企业信用信息公示系统_标示
	private String gsxtSaicGovDesc;		// 全国企业信用信息公示系统_结论
	private Date checkDate;		// 核查时间
	private String checkUserId;		// 检查人ID
	private String checkUserName;		// 核查人
	private String nationalPromiseToBeExecutedFlag;
	private String nationalPromiseToBeExecutedDesc;
	public CheckWeb() {
		super();
	}

	public CheckWeb(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=4, message="核查对象长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=32, message="客户ID（主借人ID,共借人ID,担保人ID，配偶ID等）长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=30, message="姓名长度必须介于 0 和 30 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=10, message="证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=18, message="证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@Length(min=0, max=15, message="移动电话长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	@Length(min=0, max=1, message="全国法院被执行人信息查询_标示长度必须介于 0 和 1 之间")
	public String getZhixingCourtGovFlg() {
		return zhixingCourtGovFlg;
	}

	public void setZhixingCourtGovFlg(String zhixingCourtGovFlg) {
		this.zhixingCourtGovFlg = zhixingCourtGovFlg;
	}
	
	@Length(min=0, max=1000, message="全国法院被执行人信息查询_结论长度必须介于 0 和 1000 之间")
	public String getZhixingCourtGovDesc() {
		return zhixingCourtGovDesc;
	}

	public void setZhixingCourtGovDesc(String zhixingCourtGovDesc) {
		this.zhixingCourtGovDesc = zhixingCourtGovDesc;
	}
	
	@Length(min=0, max=1, message="百度_标示长度必须介于 0 和 1 之间")
	public String getBaiduFlg() {
		return baiduFlg;
	}

	public void setBaiduFlg(String baiduFlg) {
		this.baiduFlg = baiduFlg;
	}
	
	@Length(min=0, max=1000, message="百度_结论长度必须介于 0 和 1000 之间")
	public String getBaiduDesc() {
		return baiduDesc;
	}

	public void setBaiduDesc(String baiduDesc) {
		this.baiduDesc = baiduDesc;
	}
	
	@Length(min=0, max=1, message="全国组织代码管理中心_标示长度必须介于 0 和 1 之间")
	public String getNacaoOrgFlg() {
		return nacaoOrgFlg;
	}

	public void setNacaoOrgFlg(String nacaoOrgFlg) {
		this.nacaoOrgFlg = nacaoOrgFlg;
	}
	
	@Length(min=0, max=1000, message="全国组织代码管理中心_结论长度必须介于 0 和 1000 之间")
	public String getNacaoOrgDesc() {
		return nacaoOrgDesc;
	}

	public void setNacaoOrgDesc(String nacaoOrgDesc) {
		this.nacaoOrgDesc = nacaoOrgDesc;
	}
	
	@Length(min=0, max=1, message="中国裁判文书网_标示长度必须介于 0 和 1 之间")
	public String getJudgementsOnlineFlg() {
		return judgementsOnlineFlg;
	}

	public void setJudgementsOnlineFlg(String judgementsOnlineFlg) {
		this.judgementsOnlineFlg = judgementsOnlineFlg;
	}
	
	@Length(min=0, max=1000, message="中国裁判文书网_结论长度必须介于 0 和 1000 之间")
	public String getJudgementsOnlineDesc() {
		return judgementsOnlineDesc;
	}

	public void setJudgementsOnlineDesc(String judgementsOnlineDesc) {
		this.judgementsOnlineDesc = judgementsOnlineDesc;
	}
	
	@Length(min=0, max=1, message="国家食品药品监督管理总局数据查询_标示长度必须介于 0 和 1 之间")
	public String getCfdaFlg() {
		return cfdaFlg;
	}

	public void setCfdaFlg(String cfdaFlg) {
		this.cfdaFlg = cfdaFlg;
	}
	
	@Length(min=0, max=1000, message="国家食品药品监督管理总局数据查询_结论长度必须介于 0 和 1000 之间")
	public String getCfdaDesc() {
		return cfdaDesc;
	}

	public void setCfdaDesc(String cfdaDesc) {
		this.cfdaDesc = cfdaDesc;
	}
	
	@Length(min=0, max=1, message="全国道路运输证件查询系统_标示长度必须介于 0 和 1 之间")
	public String getBjygscxFlg() {
		return bjygscxFlg;
	}

	public void setBjygscxFlg(String bjygscxFlg) {
		this.bjygscxFlg = bjygscxFlg;
	}
	
	@Length(min=0, max=1000, message="全国道路运输证件查询系统_结论长度必须介于 0 和 1000 之间")
	public String getBjygscxDesc() {
		return bjygscxDesc;
	}

	public void setBjygscxDesc(String bjygscxDesc) {
		this.bjygscxDesc = bjygscxDesc;
	}
	
	@Length(min=0, max=1, message="中登网_标示长度必须介于 0 和 1 之间")
	public String getZhongdengwangOrgFlg() {
		return zhongdengwangOrgFlg;
	}

	public void setZhongdengwangOrgFlg(String zhongdengwangOrgFlg) {
		this.zhongdengwangOrgFlg = zhongdengwangOrgFlg;
	}
	
	@Length(min=0, max=1000, message="中登网_结论长度必须介于 0 和 1000 之间")
	public String getZhongdengwangOrgDesc() {
		return zhongdengwangOrgDesc;
	}

	public void setZhongdengwangOrgDesc(String zhongdengwangOrgDesc) {
		this.zhongdengwangOrgDesc = zhongdengwangOrgDesc;
	}
	
	@Length(min=0, max=1, message="海关总署进出口报关证查询_标示长度必须介于 0 和 1 之间")
	public String getGacprcFlg() {
		return gacprcFlg;
	}

	public void setGacprcFlg(String gacprcFlg) {
		this.gacprcFlg = gacprcFlg;
	}
	
	@Length(min=0, max=1000, message="海关总署进出口报关证查询_结论长度必须介于 0 和 1000 之间")
	public String getGacprcDesc() {
		return gacprcDesc;
	}

	public void setGacprcDesc(String gacprcDesc) {
		this.gacprcDesc = gacprcDesc;
	}
	
	@Length(min=0, max=1, message="全国企业信用信息公示系统_标示长度必须介于 0 和 1 之间")
	public String getGsxtSaicGovFlg() {
		return gsxtSaicGovFlg;
	}

	public void setGsxtSaicGovFlg(String gsxtSaicGovFlg) {
		this.gsxtSaicGovFlg = gsxtSaicGovFlg;
	}
	
	@Length(min=0, max=1000, message="全国企业信用信息公示系统_结论长度必须介于 0 和 1000 之间")
	public String getGsxtSaicGovDesc() {
		return gsxtSaicGovDesc;
	}

	public void setGsxtSaicGovDesc(String gsxtSaicGovDesc) {
		this.gsxtSaicGovDesc = gsxtSaicGovDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=32, message="检查人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	@Length(min=0, max=20, message="核查人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getNationalPromiseToBeExecutedFlag() {
		return nationalPromiseToBeExecutedFlag;
	}

	public void setNationalPromiseToBeExecutedFlag(String nationalPromiseToBeExecutedFlag) {
		this.nationalPromiseToBeExecutedFlag = nationalPromiseToBeExecutedFlag;
	}

	public String getNationalPromiseToBeExecutedDesc() {
		return nationalPromiseToBeExecutedDesc;
	}

	public void setNationalPromiseToBeExecutedDesc(String nationalPromiseToBeExecutedDesc) {
		this.nationalPromiseToBeExecutedDesc = nationalPromiseToBeExecutedDesc;
	}
	
}