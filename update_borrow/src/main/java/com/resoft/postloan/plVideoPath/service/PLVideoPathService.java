package com.resoft.postloan.plVideoPath.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.plVideoPath.dao.PLVideoPathDao;
import com.resoft.postloan.plVideoPath.entity.PLVideoPath;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 综合查询Service
 * @author admin
 * @version 2016-03-01
 */
@Service @DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class PLVideoPathService extends CrudService<PLVideoPathDao, PLVideoPath> {

	@Autowired
	private PLVideoPathDao creVideoPathDao;
	public List<PLVideoPath> findCreVideoPathList(String applyNo,String taskDefKey) {
		return super.dao.findCreVideoPathQueryList(applyNo,taskDefKey);
	}
	@Transactional(value="PL",readOnly = false)
	public void saveCreVideoPath(PLVideoPath creVideoPath,String taskDefKey) {
		if(creVideoPath.getFileStoragePath()!=null){
			super.dao.saveCreVideoPath(creVideoPath,taskDefKey);
		}
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
    
    @Transactional(value="PL",readOnly = false)
	public void deleteByVideoPath(String videoPath) {
    	creVideoPathDao.deleteByVideoPath(videoPath);
	}
}