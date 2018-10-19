package com.resoft.postloan.check25.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.check25.dao.CheckTwentyFiveDao;
import com.resoft.postloan.check25.entity.CheckTwentyFive;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 25日复核Service
 * @author yanwanmei
 * @version 2016-05-25
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class CheckTwentyFiveService extends CrudService<CheckTwentyFiveDao, CheckTwentyFive> {

	public CheckTwentyFive get(String id) {
		return super.get(id);
	}
	
	public List<CheckTwentyFive> findList(CheckTwentyFive checkTwentyFive) {
		return super.findList(checkTwentyFive);
	}
	
	public Page<CheckTwentyFive> findPage(Page<CheckTwentyFive> page, CheckTwentyFive checkTwentyFive) {
		return super.findPage(page, checkTwentyFive);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(CheckTwentyFive checkTwentyFive) {
		super.save(checkTwentyFive);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(CheckTwentyFive checkTwentyFive) {
		super.delete(checkTwentyFive);
	}
	
	//任务下发
	public Page<MyMap> getCheckUserInfo(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(this.dao.getCheckUserInfo(paramMap));
		return page;
	}
	
	public CheckTwentyFive getTwentyFiveByAllocateId(String allocateId){
		return super.dao.getTwentyFiveByAllocateId(allocateId);
	}
	
	
}