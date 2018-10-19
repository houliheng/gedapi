package com.resoft.postloan.mortgageEvaluateInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.mortgageEvaluateInfo.dao.MortgageEvaluateItemsDao;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateItems;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 抵质押物检查项目Service
 * @author 赵华奎
 * @version 2016-05-31
 */
@Service
@Transactional(readOnly = true)
public class MortgageEvaluateItemsService extends CrudService<MortgageEvaluateItemsDao, MortgageEvaluateItems> {

	public List<MortgageEvaluateItems> findItemsList(String infoId) {
		return super.dao.findItemsList(infoId);
	}

	public void saveEdit(MortgageEvaluateItems mortgageEvaluateItems) {
		 super.dao.saveEdit(mortgageEvaluateItems);
	}

	public MortgageEvaluateItems showEdit(String id) {
		return super.dao.showEdit(id);
	}

	public void insertCheck(MyMap paramCheckMap) {
		 super.dao.insertCheck(paramCheckMap);
	}

	public List<String> getcheckItems() {
		return super.dao.getcheckItems();
	}
	
}