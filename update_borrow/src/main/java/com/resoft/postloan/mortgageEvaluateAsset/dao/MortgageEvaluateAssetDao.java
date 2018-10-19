package com.resoft.postloan.mortgageEvaluateAsset.dao;

import java.util.List;

import com.resoft.postloan.mortgageEvaluateAsset.entity.MortgageEvaluateAsset;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 资产评估DAO接口
 * @author zhaohuakui
 * @version 2016-05-25
 */
@MyBatisDao
public interface MortgageEvaluateAssetDao extends CrudDao<MortgageEvaluateAsset> {

	List<MyMap> findToDoListByPage(MyMap paramMap);

	void saveRemark(MortgageEvaluateAsset mortgageEvaluateAsset);

	void saveAsset(MortgageEvaluateAsset mortgageEvaluateAsset);

	void updateAsset(MortgageEvaluateAsset mortgageEvaluateAsset);
	
	
}