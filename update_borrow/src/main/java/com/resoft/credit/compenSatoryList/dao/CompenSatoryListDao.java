package com.resoft.credit.compenSatoryList.dao;

import com.resoft.credit.compenSatoryList.entity.CompenSatoryList;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CompenSatoryListDao  extends CrudDao<CompenSatoryList> {

	//public StockTaskReceive getReceiveByApplyNoAndGrade(@Param("applyNo")String applyNo, @Param("grade")String grade);

    public  CompenSatoryList  findsumRemainAmount(CompenSatoryList compenSatoryList);

}
