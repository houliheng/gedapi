package com.resoft.credit.videoDir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.resoft.credit.videoDir.entity.VideoDir;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 影像目录管理Service
 * @author wanghong
 * @version 2016-11-01
 */
@Service
@Transactional(readOnly = true)
public class VideoUploadService extends CrudService<VideoUploadDao, VideoDir> {

	@Autowired
	private VideoUploadDao videoUploadDao;
	public VideoDir get(String id) {
		return super.get(id);
	}

	public List<VideoDir> findList(VideoDir videoDir) {
		return super.findList(videoDir);
	}

	public List<VideoDir> findAllList(VideoDir videoDir) {
		return videoUploadDao.findAllList(videoDir);
	}

	public List<VideoDir> findCustList(VideoDir videoDir) {
		return videoUploadDao.findCustList(videoDir);
	}

	public String getIdByName(VideoDir videoDir) {
		return videoUploadDao.getIdByName(videoDir);
	}

	public int getNumByName(VideoDir videoDir) {
		return videoUploadDao.getNumByName(videoDir);
	}

	public String getFileNameById(VideoDir videoDir) {
		return videoUploadDao.getFileNameById(videoDir);
	}
	
	public VideoDir isExist(VideoDir videoDir) {
		return videoUploadDao.isExist(videoDir);
	}

	public Page<VideoDir> findPage(Page<VideoDir> page, VideoDir videoDir) {
		return super.findPage(page, videoDir);
	}

	@Transactional(readOnly = false)
	public void save(VideoDir videoDir) {
		super.save(videoDir);
	}

	@Transactional(readOnly = false)
	public void insert(List<VideoDir> videoDir) {
		videoUploadDao.insertColumn(videoDir);
	}

	@Transactional(readOnly = false)
	public void delete(VideoDir videoDir) {
		super.delete(videoDir);
	}



}