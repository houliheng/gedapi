package com.resoft.postloan.checkTwentyFiveInfo.entity;

import com.resoft.postloan.check25.entity.CheckTwentyFive;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 25日复核信息Entity
 * @author admin
 * @version 2016-05-25
 */
public class CheckTwentyFiveInfoVO extends DataEntity<CheckTwentyFiveInfoVO> {
	
	private static final long serialVersionUID = 1L;
	private String roleType;
	private CheckTwentyFive checkTwentyFive;
	private MainBorrower mainBorrower;
	private Guarantor guarantor;
	private MainBorrowerCompany mainBorrowerCompany;
	private GuarantorCompany guarantorCompany;
	private Compliance compliance;
	
	public CheckTwentyFiveInfoVO() {
		super();
	}

	public CheckTwentyFiveInfoVO(String id){
		super(id);
	}

	public CheckTwentyFive getCheckTwentyFive() {
		return checkTwentyFive;
	}

	public void setCheckTwentyFive(CheckTwentyFive checkTwentyFive) {
		this.checkTwentyFive = checkTwentyFive;
	}

	public MainBorrower getMainBorrower() {
		return mainBorrower;
	}

	public void setMainBorrower(MainBorrower mainBorrower) {
		this.mainBorrower = mainBorrower;
	}

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public MainBorrowerCompany getMainBorrowerCompany() {
		return mainBorrowerCompany;
	}

	public void setMainBorrowerCompany(MainBorrowerCompany mainBorrowerCompany) {
		this.mainBorrowerCompany = mainBorrowerCompany;
	}

	public GuarantorCompany getGuarantorCompany() {
		return guarantorCompany;
	}

	public void setGuarantorCompany(GuarantorCompany guarantorCompany) {
		this.guarantorCompany = guarantorCompany;
	}

	public Compliance getCompliance() {
		return compliance;
	}

	public void setCompliance(Compliance compliance) {
		this.compliance = compliance;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	
	
}