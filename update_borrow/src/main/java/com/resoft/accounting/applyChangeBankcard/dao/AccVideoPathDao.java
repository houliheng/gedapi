package com.resoft.accounting.applyChangeBankcard.dao;

import java.util.List;

import com.resoft.accounting.applyChangeBankcard.entity.AccVideoPath;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @version 2016-03-01
 */
@MyBatisDao
public interface AccVideoPathDao extends CrudDao<AccVideoPath> {

	public List<AccVideoPath> findAccVideoPathQueryList(String applyNo);

	public void saveAccVideoPath(AccVideoPath accVideoPath);
	
	public List<String> findPictureAddressQueryList(String applyNo);
	
	public void deleteAccVideoPath(String applyNo);
	
}