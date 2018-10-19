package com.resoft.credit.report.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.report.dao.StaReportSecondDao;
import com.resoft.credit.report.entity.StaReportFirst;
import com.resoft.credit.report.entity.StaReportSecond;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 统计报表2Service
 * @author lixing
 * @version 2016-12-09
 */
@Service
@Transactional(readOnly = true)
public class StaReportSecondService extends CrudService<StaReportSecondDao, StaReportSecond> {

	public StaReportSecond get(String id) {
		return super.get(id);
	}
	
	public List<StaReportSecond> findList(StaReportSecond staReportSecond) {
		return super.findList(staReportSecond);
	}
	
	public Page<StaReportSecond> findPage(Page<StaReportSecond> page, StaReportSecond staReportSecond) {
		return super.findPage(page, staReportSecond);
	}
	
	@Transactional(readOnly = false)
	public void save(StaReportSecond staReportSecond) {
		super.save(staReportSecond);
	}
	
	@Transactional(readOnly = false)
	public void delete(StaReportSecond staReportSecond) {
		super.delete(staReportSecond);
	}
	
	/**
	 * 统计报表1
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListFirst(Map<String, Object> params) {
		return super.dao.findListFirst(params);
	}
	/**
	 * 统计报表2
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListSecond(Map<String, Object> params) {
		return super.dao.findListSecond(params);
	}
	
	/**
	 * 统计报表4(行业趋势)
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListFourth(Map<String, Object> params){
		return super.dao.findListFourth(params);
	}
	
	public List<StaReportFirst> findOfficeLevelTwoList() {
		return super.dao.findOfficeLevelTwoList();
	}
	
	public Page<MyMap> findPageSecond(Page<MyMap> page, MyMap myMap) {
		myMap.setPage(page);
		page.setList(dao.findPageSecond(myMap));
		return page;
	}
}