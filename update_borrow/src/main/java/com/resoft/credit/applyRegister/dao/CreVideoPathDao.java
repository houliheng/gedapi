package com.resoft.credit.applyRegister.dao;

import java.util.List;

import com.resoft.credit.applyRegister.entity.CreVideoPath;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 综合查询DAO接口
 * @version 2016-03-01
 */
@MyBatisDao
public interface CreVideoPathDao extends CrudDao<CreVideoPath> {



	/**
	 * CRE_信贷审批_综合查询
	 */
	public List<CreVideoPath> findCreVideoPathQueryList(String applyNo,String taskDefKey);

	public List<CreVideoPath> findPostCreVideoPathList(String applyNo);
	
	public List<CreVideoPath> downloadPic(String applyNo,String taskDefKey, String fileDir);

	/**
	 * CRE_信贷审批_综合查询
	 */
	public void saveCreVideoPath(CreVideoPath creVideoPath,String taskDefKey);
	public void deleteByVideoPath(String videoPath);
	public List<CreVideoPath> findCreVideoPathDbgsList(String applyNo,String taskDefKey);
	public Integer countNum(String unSocCreditNo,String uploadType);
	
}