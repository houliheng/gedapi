package com.resoft.credit.videoDir.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.videoDir.entity.VideoDir;

/**
 * 影像目录管理DAO接口
 * @author wanghong
 * @version 2016-11-01
 */
@MyBatisDao
public interface VideoDirDao extends CrudDao<VideoDir> {
	public List<VideoDir> findVideoByParentIdsLike(VideoDir videoDir);
	public List<VideoDir> findByParentIdsLike(VideoDir videoDir);
}