package com.resoft.postloan.checkTwentyFiveInfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 25日复核信息Entity
 * @author admin
 * @version 2016-05-25
 */
public class MainBorrower extends DataEntity<CheckTwentyFiveInfoVO> {
	private static final long serialVersionUID = -5788104741906582891L;
	private CheckTwentyFiveInfo CheckTwentyFiveInfo;

	public CheckTwentyFiveInfo getCheckTwentyFiveInfo() {
		return CheckTwentyFiveInfo;
	}

	public void setCheckTwentyFiveInfo(CheckTwentyFiveInfo checkTwentyFiveInfo) {
		CheckTwentyFiveInfo = checkTwentyFiveInfo;
	}
	
}