package com.resoft.credit.checkPhone.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 电话核查Entity
 * @author yanwanmei
 * @version 2016-02-27
 */
public class CheckPhone extends DataEntity<CheckPhone> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 核查对象
	private String custId;		// 客户ID（主借人ID,共借人ID,担保人ID，配偶ID等）
	private String custName;		// 姓名
	private String mobileNum;		// 移动电话
	private Date dialTime;		// 拨打时间
	private String phoneSrc;		// 电话来源（字典类型：PHONE_SRC）
	private String dialResc;		// 拨打情况
	private String listenerType;		// 接听者身份
	private String riskPoint;		// 异常风险点
	private String is114Check;		// 企业电话是否经过114网查
	private String description;		// 电核详情
	private String checkUserId;		// 检查人ID
	private String checkUserName;		// 拨打人
	private String visitCount;		// 拨打人
	
	public CheckPhone() {
		super();
	}

	public CheckPhone(String id){
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
	
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=15, message="移动电话长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="拨打时间不能为空")
	public Date getDialTime() {
		return dialTime;
	}

	public void setDialTime(Date dialTime) {
		this.dialTime = dialTime;
	}
	
	@Length(min=0, max=1000, message="电话来源（字典类型：PHONE_SRC）长度必须介于 0 和 1000 之间")
	public String getPhoneSrc() {
		return phoneSrc;
	}

	public void setPhoneSrc(String phoneSrc) {
		this.phoneSrc = phoneSrc;
	}
	
	@Length(min=0, max=10, message="拨打情况长度必须介于 0 和 10 之间")
	public String getDialResc() {
		return dialResc;
	}

	public void setDialResc(String dialResc) {
		this.dialResc = dialResc;
	}
	
	@Length(min=0, max=10, message="接听者身份长度必须介于 0 和 10 之间")
	public String getListenerType() {
		return listenerType;
	}

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}
	
	@Length(min=0, max=300, message="异常风险点长度必须介于 0 和 300 之间")
	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}
	
	@Length(min=0, max=1, message="企业电话是否经过114网查长度必须介于 0 和 1 之间")
	public String getIs114Check() {
		return is114Check;
	}

	public void setIs114Check(String is114Check) {
		this.is114Check = is114Check;
	}
	
	@Length(min=0, max=1000, message="电核详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=32, message="检查人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	@Length(min=0, max=20, message="拨打人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}
	
	
	
}