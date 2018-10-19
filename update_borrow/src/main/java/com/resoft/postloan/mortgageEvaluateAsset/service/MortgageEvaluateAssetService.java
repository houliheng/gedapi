package com.resoft.postloan.mortgageEvaluateAsset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.mortgageEvaluateAsset.entity.MortgageEvaluateAsset;
import com.resoft.postloan.mortgageEvaluateAsset.dao.MortgageEvaluateAssetDao;

/**
 * 资产评估Service
 * @author zhaohuakui
 * @version 2016-05-25
 */
@Service
@Transactional(readOnly = true)
public class MortgageEvaluateAssetService extends CrudService<MortgageEvaluateAssetDao, MortgageEvaluateAsset> {

	@Autowired
	private MortgageEvaluateAssetDao mortgageEvaluateAssetDao;

	public void saveRemark(MortgageEvaluateAsset mortgageEvaluateAsset) {
		mortgageEvaluateAssetDao.saveRemark(mortgageEvaluateAsset);
	}
	public void saveAsset(MortgageEvaluateAsset mortgageEvaluateAsset) {
		mortgageEvaluateAssetDao.saveAsset(mortgageEvaluateAsset);
	}

	public Page<MyMap> findpage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(mortgageEvaluateAssetDao.findToDoListByPage(paramMap));
		return page;
	}
	public void updateAsset(MortgageEvaluateAsset mortgageEvaluateAsset) {
		mortgageEvaluateAssetDao.updateAsset(mortgageEvaluateAsset);
		
	}
}