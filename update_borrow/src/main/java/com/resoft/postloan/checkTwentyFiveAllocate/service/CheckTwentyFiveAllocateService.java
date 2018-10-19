package com.resoft.postloan.checkTwentyFiveAllocate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.checkTwentyFiveAllocate.dao.CheckTwentyFiveAllocateDao;
import com.resoft.postloan.checkTwentyFiveAllocate.entity.CheckTwentyFiveAllocate;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 借后25日复核任务分配表Service
 * @author yanwanmei
 * @version 2016-06-01
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class CheckTwentyFiveAllocateService extends CrudService<CheckTwentyFiveAllocateDao, CheckTwentyFiveAllocate> {

	public CheckTwentyFiveAllocate get(String id) {
		return super.get(id);
	}
	
	public List<CheckTwentyFiveAllocate> findList(CheckTwentyFiveAllocate checkTwentyFiveAllocate) {
		return super.findList(checkTwentyFiveAllocate);
	}
	
	public Page<CheckTwentyFiveAllocate> findPage(Page<CheckTwentyFiveAllocate> page, CheckTwentyFiveAllocate checkTwentyFiveAllocate) {
		return super.findPage(page, checkTwentyFiveAllocate);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(CheckTwentyFiveAllocate checkTwentyFiveAllocate) {
		super.save(checkTwentyFiveAllocate);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(CheckTwentyFiveAllocate checkTwentyFiveAllocate) {
		super.delete(checkTwentyFiveAllocate);
	}
	
	//下发任务后，将此条数据插入到借后25日复核任务分配表
	@Transactional(value="PL",readOnly = false)
	public void saveList(List<CheckTwentyFiveAllocate> checkTwentyFiveAllocates){
		super.dao.saveList(checkTwentyFiveAllocates);
	}
	
	//查询借后经理已下发任务
	public List<String> getContractNos(Map<String,Object> paraMap){
		return super.dao.getContractNos(paraMap);
	}
	
	@Transactional(value="PL",readOnly= false)
	public void updateCheckedTypeByContractNo(Map<String,String> param){
		super.dao.updateCheckedTypeByContractNo(param);
	}
	
	//待分配
	public Page<MyMap> getToAllocatedPLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getToAllocatedPLContract(paramMap));
		return page;
	}
	//已分配
	public Page<MyMap> getHasAllocatedPLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getHasAllocatedPLContract(paramMap));
		return page;
	}
	//已复核(查询当前机构及以下机构)
	public Page<MyMap> getAllHasAllocatedPLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getAllHasAllocatedPLContract(paramMap));
		return page;
	}
	
	/**
	 * 查询符合日常检查条件的合同编号
	 * 
	 * @param params
	 * @return
	 */
	public List<String> findContractNosForDaily(Map<String, Object> params) {
		return super.dao.findContractNosForDaily(params);
	}
	
	/**
	 * 重新复核
	 */
	@Transactional(value="PL",readOnly= false)
	public void checkedAgain(String contractNo){
		this.dao.checkedAgain(contractNo);
	}
	
	public CheckTwentyFiveAllocate getTwentyFiveByContractNo(Map<String, Object> params){
		return super.dao.getTwentyFiveByContractNo(params);
	}

	/**
	 * 查询符合日常检查条件的731
	 * 
	 * @param params
	 * @return
	 */
	public Page getSevenPLContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.getSevenPLContract(paramMap));
		return page;
	}
	
}