package com.resoft.credit.applyRegister.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRegister.dao.CreVideoPathDao;
import com.resoft.credit.applyRegister.entity.CreVideoPath;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 综合查询Service
 * @author admin
 * @version 2016-03-01
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreVieoPathService extends CrudService<CreVideoPathDao, CreVideoPath> {

	@Autowired
	private CreVideoPathDao creVideoPathDao;
	public List<CreVideoPath> findCreVideoPathList(String applyNo,String taskDefKey) {
		return super.dao.findCreVideoPathQueryList(applyNo,taskDefKey);
	}
	public List<CreVideoPath> findPostCreVideoPathList(String applyNo) {
		return super.dao.findPostCreVideoPathList(applyNo);
	}
	public List<CreVideoPath> findCreVideoPathList(String applyNo,String taskDefKey,String fileDir) {
		return creVideoPathDao.downloadPic(applyNo,taskDefKey,fileDir);
	}
	@Transactional(value="CRE",readOnly = false)
	public void saveCreVideoPath(CreVideoPath creVideoPath,String taskDefKey) {
		if(creVideoPath.getFileStoragePath()!=null){
			super.dao.saveCreVideoPath(creVideoPath,taskDefKey);
		}
	}
	@Transactional(value="CRE",readOnly = false)
	public void deleteByVideoPath(String videoPath) {
		creVideoPathDao.deleteByVideoPath(videoPath);
	}

    public Page findDoneProcessPage(Page<MyMap> page, List list){
    	Page result = page;
    	if (page.getList() == null)
	        page.setList(new ArrayList<MyMap>());
	    else {
	        page.getList().clear();
	    }
	    if(list.size()>0){
	        result.setCount(12);
	        result.setDefaultCount(false);
	        result.getList().addAll(list);
	    }
	    return result;
    }
    
    public List<CreVideoPath> findCreVideoPathDbgsList(String applyNo,String taskDefKey) {
		return super.dao.findCreVideoPathDbgsList(applyNo,taskDefKey);
	}
    
    
    public Integer countNum(String unSocCreditNo,String uploadType){
    	return creVideoPathDao.countNum(unSocCreditNo,uploadType);
    }
    
}