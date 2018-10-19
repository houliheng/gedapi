package com.resoft.postloan.overdueHandle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.overdueHandle.dao.OverdueHandleDao;
import com.resoft.postloan.overdueHandle.entity.OverdueHandle;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 贷后逾期处理Service
 * @author lixing
 * @version 2017-01-04
 */
@Service
@Transactional(readOnly = true)
public class OverdueHandleService extends CrudService<OverdueHandleDao, OverdueHandle> {

	public OverdueHandle get(String id) {
		return super.get(id);
	}
	
	public List<OverdueHandle> findList(OverdueHandle overdueHandle) {
		return super.findList(overdueHandle);
	}
	
	public Page<OverdueHandle> findPage(Page<OverdueHandle> page, OverdueHandle overdueHandle) {
		return super.findPage(page, overdueHandle);
	}
	
	@Transactional(readOnly = false)
	public void save(OverdueHandle overdueHandle) {
		super.save(overdueHandle);
	}
	
	@Transactional(readOnly = false)
	public void delete(OverdueHandle overdueHandle) {
		super.delete(overdueHandle);
	}
	
	//总部下发待办
	public Page<MyMap> findZBXFList(Page<MyMap> page,  MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.findZBXFList(myMap));
		return page;
	}
	//总部下发已办
	public Page<MyMap> findZBXFDoneList(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.findZBXFDoneList(myMap));
		return page;
	}
	
	//管理员查询待办
	public Page<MyMap> todoListAdmin(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.todoListAdmin(myMap));
		return page;
	}
	//非管理员查询待办
	public Page<MyMap> todoList(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.todoList(myMap));
		return page;
	}
	//任务下发查询人员列表
	public Page<MyMap> findZBXFAssignList(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.findZBXFAssignList(myMap));
		return page;
	}
	//查询已办
	public Page<MyMap> doneList(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.doneList(myMap));
		return page;
	}
	//根据逾期天数获取已启动流程的数据
	public List<MyMap> findListByOverdueDays(MyMap params) {
		return super.dao.findListByOverdueDays(params);
	}
	//根据逾期天数获取未启动流程的数据
	public List<MyMap> findListUnstarted(MyMap params) {
		return super.dao.findListUnstarted(params);
	}
}