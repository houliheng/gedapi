package com.resoft.postloan.collateralDisposalConclusion.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.google.common.collect.Maps;
import com.resoft.postloan.collateralDisposal.dao.CollateralDisposalDao;
import com.resoft.postloan.collateralDisposalConclusion.entity.CollateralDisposalConclusion;
import com.resoft.postloan.collateralDisposalConclusion.dao.CollateralDisposalConclusionDao;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;

/**
 * 抵押物处置流程意见Service
 * @author wangguodong
 * @version 2017-01-09
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class CollateralDisposalConclusionService extends CrudService<CollateralDisposalConclusionDao, CollateralDisposalConclusion> {

	@Autowired
	ActTaskService actTaskService;
	
	
	@Autowired
	CollateralDisposalDao collateralDisposalDao;
	
	
	public CollateralDisposalConclusion get(String id) {
		return super.get(id);
	}
	
	public List<CollateralDisposalConclusion> findList(CollateralDisposalConclusion collateralDisposalConclusion) {
		return super.findList(collateralDisposalConclusion);
	}
	
	public Page<CollateralDisposalConclusion> findPage(Page<CollateralDisposalConclusion> page, CollateralDisposalConclusion collateralDisposalConclusion) {
		return super.findPage(page, collateralDisposalConclusion);
	}
	
	@Transactional(value = "PL",readOnly = false)
	public void save(CollateralDisposalConclusion collateralDisposalConclusion) {
		super.save(collateralDisposalConclusion);
	}
	
	@Transactional(value = "PL",readOnly = false)
	public void saveSuggestion(ActTaskParam actTaskParam, CollateralDisposalConclusion collateralDisposalConclusion,String passFlag ) {
		save(collateralDisposalConclusion);
		if("yes".equals(passFlag)){
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + collateralDisposalConclusion.getConclusionDesc(), "提交", null);
			if("utask_zbrwcz".equals(actTaskParam.getTaskDefKey())){
				Map<String, Object> params = Maps.newHashMap();
				params.put("contractNo", collateralDisposalConclusion.getContractNo());
				params.put("periodNum", collateralDisposalConclusion.getPeriodNum());
				collateralDisposalDao.updateDisposalStatusByContractNoAndPeriodNum(params);
			}
		}else if("back".equals(passFlag)){
			actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + collateralDisposalConclusion.getConclusionDesc());
		}else if("yesToFinish".equals(passFlag)){
			//提交并结束流程
			actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【结束】" + collateralDisposalConclusion.getConclusionDesc());
			Map<String, Object> params = Maps.newHashMap();
			params.put("contractNo", collateralDisposalConclusion.getContractNo());
			params.put("periodNum", collateralDisposalConclusion.getPeriodNum());
			collateralDisposalDao.updateDisposalStatusByContractNoAndPeriodNum(params);
		}else{
			
		}
	}
	
	/**
	 * 根据合同号和期数查询意见信息
	 */
	@Transactional(value = "PL",readOnly = false)
	public List<CollateralDisposalConclusion> findCollateralDisposalConclusionListByContractNoAndPeriodNum(Map<String, Object> params){
		return this.dao.findCollateralDisposalConclusionListByContractNoAndPeriodNum(params);
	}
	
}