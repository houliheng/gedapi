package com.resoft.credit.videoDir.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.videoDir.entity.VideoDir;

/**
 * 影像目录管理DAO接口
 * @author wanghong
 * @version 2016-11-01
 */
@MyBatisDao
public interface VideoUploadDao extends CrudDao<VideoDir> {
	public String getIdByName(VideoDir videoDir);
	public int getNumByName(VideoDir videoDir);
	public String getFileNameById(VideoDir videoDir);
	public List<VideoDir> findCustList(VideoDir videoDir);
	public VideoDir isExist(VideoDir videoDir);
	public void insertColumn(@Param("videoDirList")List<VideoDir> videoDirList);
	
	/**
	 * 根据applyNo和taskDefKey给影像信息加锁
	 */
	public void lockVideoMessageByApplyNoAndTaskDefKey(Map<String, Object> params);
}