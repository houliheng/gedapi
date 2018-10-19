package com.resoft.credit.videoDir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.videoDir.dao.VideoDirDao;
import com.resoft.credit.videoDir.entity.VideoDir;

/**
 * 影像目录管理Service
 * @author wanghong
 * @version 2016-11-01
 */
@Service
@Transactional(readOnly = true)
public class VideoDirService extends CrudService<VideoDirDao, VideoDir> {

	@Autowired
	private VideoDirDao videoDirDao;
	public VideoDir get(String id) {
		return super.get(id);
	}
	
	public List<VideoDir> findList(VideoDir videoDir) {
		return super.findList(videoDir);
	}
	
	public List<VideoDir> findAllList(VideoDir videoDir) {
		return videoDirDao.findAllList(videoDir);
	}
	
	public List<VideoDir> findByParentIdsLike(VideoDir videoDir) {
		return videoDirDao.findByParentIdsLike(videoDir);
	}
	
	public Page<VideoDir> findPage(Page<VideoDir> page, VideoDir videoDir) {
		return super.findPage(page, videoDir);
	}
	
	@Transactional(readOnly = false)
	public void save(VideoDir videoDir) {
		super.save(videoDir);
	}
	
	@Transactional(readOnly = false)
	public void delete(VideoDir videoDir) {
		super.delete(videoDir);
	}
	
	@Transactional(readOnly = true)
	public List<VideoDir> findListByVideoId(String parentId){
		VideoDir videoDir = dao.get(parentId);
		videoDir.setParentIds(videoDir.getParentIds()+parentId+",%");
		return dao.findVideoByParentIdsLike(videoDir);
	}
	
}