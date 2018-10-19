package com.resoft.postloan.mortgageEvaluateInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.checkRemove.dao.CheckRemoveDao;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateItems;
import com.resoft.postloan.mortgageEvaluateInfo.dao.MortgageEvaluateInfoDao;

/**
 * 资产管理Service
 * @author zhaohuakui
 * @version 2016-05-25
 */
@Service("plMortgageEvaluateInfoService")
@Transactional(readOnly = true)
public class MortgageEvaluateInfoService extends CrudService<MortgageEvaluateInfoDao, MortgageEvaluateInfo> {

	@Autowired
	private MortgageEvaluateInfoDao mortgageEvaluateInfoDao;
	/**
	 * 查询抵质押物检查信息 
	 * @author zhaohuakui
	 * @version 2016-06-17
	 */
	public MortgageEvaluateInfo findMortgageByInfoId(String infoId) {
		return this.dao.findMortgageByInfoId(infoId);
	}
}