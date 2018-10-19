package com.resoft.postloan.mortgageEvaluateInfo.dao;

import java.util.List;

import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateItems;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 抵质押物检查项目DAO接口
 * @author 赵华奎
 * @version 2016-05-31
 */
@MyBatisDao
public interface MortgageEvaluateItemsDao extends CrudDao<MortgageEvaluateItems> {

	List<MortgageEvaluateItems> findItemsList(String infoId);

	void saveEdit(MortgageEvaluateItems mortgageEvaluateItems);

	MortgageEvaluateItems showEdit(String id);

	void insertCheck(MyMap paramCheckMap);

	List<String> getcheckItems();


}