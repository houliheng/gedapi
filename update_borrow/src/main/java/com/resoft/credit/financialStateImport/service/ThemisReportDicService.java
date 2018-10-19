package com.resoft.credit.financialStateImport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.financialStateImport.dao.ThemisReportDicDao;
import com.resoft.credit.financialStateImport.entity.ThemisReportDic;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * Themis报表字典表Service
 * @author wuxi01
 * @version 2016-03-23
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class ThemisReportDicService extends CrudService<ThemisReportDicDao, ThemisReportDic> {

	public ThemisReportDic get(String id) {
		return super.get(id);
	}
	
	public List<ThemisReportDic> findList(ThemisReportDic themisReportDic) {
		return super.findList(themisReportDic);
	}
	
	public Page<ThemisReportDic> findPage(Page<ThemisReportDic> page, ThemisReportDic themisReportDic) {
		return super.findPage(page, themisReportDic);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(ThemisReportDic themisReportDic) {
		super.save(themisReportDic);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(ThemisReportDic themisReportDic) {
		super.delete(themisReportDic);
	}
	
}