package com.resoft.postloan.plVideoPath.dao;

import java.util.List;

import com.resoft.postloan.plVideoPath.entity.PLVideoPath;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 综合查询DAO接口
 * @version 2016-03-01
 */
@MyBatisDao
public interface PLVideoPathDao extends CrudDao<PLVideoPath> {
	
	
	
	/**
	 * CRE_信贷审批_综合查询
	 */
	public List<PLVideoPath> findCreVideoPathQueryList(String applyNo,String taskDefKey);

	/**
	 * CRE_信贷审批_综合查询
	 */
	public void saveCreVideoPath(PLVideoPath creVideoPath,String taskDefKey);
	public void deleteByVideoPath(String videoPath);

}