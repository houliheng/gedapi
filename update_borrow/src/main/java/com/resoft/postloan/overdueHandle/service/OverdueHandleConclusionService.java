package com.resoft.postloan.overdueHandle.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.overdueHandle.entity.OverdueHandleConclusion;
import com.resoft.postloan.overdueHandle.dao.OverdueHandleConclusionDao;

/**
 * 逾期处理流程意见Service
 * @author lixing
 * @version 2017-01-06
 */
@Service
@Transactional(readOnly = true)
public class OverdueHandleConclusionService extends CrudService<OverdueHandleConclusionDao, OverdueHandleConclusion> {

	public OverdueHandleConclusion get(String id) {
		return super.get(id);
	}
	
	public List<OverdueHandleConclusion> findList(OverdueHandleConclusion overdueHandleConclusion) {
		return super.findList(overdueHandleConclusion);
	}
	
	public Page<OverdueHandleConclusion> findPage(Page<OverdueHandleConclusion> page, OverdueHandleConclusion overdueHandleConclusion) {
		return super.findPage(page, overdueHandleConclusion);
	}
	
	@Transactional(readOnly = false)
	public void save(OverdueHandleConclusion overdueHandleConclusion) {
		super.save(overdueHandleConclusion);
	}
	
	@Transactional(readOnly = false)
	public void delete(OverdueHandleConclusion overdueHandleConclusion) {
		super.delete(overdueHandleConclusion);
	}
	
	public OverdueHandleConclusion findConclusionByParams(Map<String, Object> params){
		return super.dao.findConclusionByParams(params);
	}
}