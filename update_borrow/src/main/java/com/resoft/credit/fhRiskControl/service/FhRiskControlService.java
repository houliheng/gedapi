package com.resoft.credit.fhRiskControl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.credit.fhRiskControl.dao.FhRiskControlDao;
import com.resoft.credit.fhRiskControl.entity.FhRiskControl;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 法海风控信息Service
 * 
 * @author wangguodong
 * @version 2017-02-17
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class FhRiskControlService extends CrudService<FhRiskControlDao, FhRiskControl> {

	public FhRiskControl get(String id) {
		return super.get(id);
	}

	public List<FhRiskControl> findRiskControlYcByApplyNo(String applyNo){
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		return dao.findRiskControlYcByApplyNo(params);
	}
	
	public List<FhRiskControl> findList(FhRiskControl fhRiskControl) {
		return super.findList(fhRiskControl);
	}

	public Page<FhRiskControl> findPage(Page<FhRiskControl> page, FhRiskControl fhRiskControl) {
		return super.findPage(page, fhRiskControl);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(FhRiskControl fhRiskControl) {
		super.save(fhRiskControl);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(FhRiskControl fhRiskControl) {
		super.delete(fhRiskControl);
	}

	/**
	 * 根据applyNo和custId统计查询次数
	 */
	public Long getCountByApplyNoAndCustId(Map<String, Object> params) {
		return this.dao.getCountByApplyNoAndCustId(params);
	}

	/**
	 * 根据applyNo和custId改变报告状态
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateReportStatusAndFilePathById(String realFilePath, String fileName, String filePath, String reportStatus, String applyNo, String custId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("reportStatus", reportStatus);
		params.put("applyNo", applyNo);
		params.put("custId", custId);
		params.put("filePath", filePath);
		params.put("fileName", fileName);
		params.put("realFilePath", realFilePath);
		params.put("updateDate", new Date());
		this.dao.updateReportStatusAndFilePathById(params);
	}

	/**
	 * 根据applyNo和custId获取报告表中记录
	 */
	public List<FhRiskControl> findFhRiskControlReportByApplyNoAndCustId(String applyNo, String custId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("custId", custId);
		return this.dao.findFhRiskControlReportByApplyNoAndCustId(params);
	}

	/**
	 * 新增报告生成记录
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void savefhRiskControlReport(FhRiskControl fhRiskControl) {
		this.dao.savefhRiskControlReport(fhRiskControl);
	}

}