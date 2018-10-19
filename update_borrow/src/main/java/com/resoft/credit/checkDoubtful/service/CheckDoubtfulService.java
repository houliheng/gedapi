package com.resoft.credit.checkDoubtful.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkDoubtful.dao.CheckDoubtfulDao;
import com.resoft.credit.checkDoubtful.entity.CheckDoubtful;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

/**
 * 借前外访Service
 * 
 * @author yanwanmei
 * @version 2016-03-01
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CheckDoubtfulService extends CrudService<CheckDoubtfulDao, CheckDoubtful> {

	private static final Logger logger = LoggerFactory.getLogger(CheckDoubtfulService.class);
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private VideoUploadDao videoUploadDao;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	public CheckDoubtful get(String id) {
		return super.get(id);
	}

	public List<CheckDoubtful> findList(CheckDoubtful checkDoubtful) {
		return super.findList(checkDoubtful);
	}

	public Page<CheckDoubtful> findPage(Page<CheckDoubtful> page, CheckDoubtful checkDoubtful) {
		return super.findPage(page, checkDoubtful);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(CheckDoubtful checkDoubtful) {
		super.save(checkDoubtful);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(CheckDoubtful checkDoubtful) {
		super.delete(checkDoubtful);
	}

	public List<CheckDoubtful> getPageByApplyNo(String applyNo) {
		return super.dao.getPageByApplyNo(applyNo);
	}

	/**
	 * 查询full_message
	 * 
	 */
	public Object getFullMsg(String hitaskid) {
		return super.dao.getFullMsg(hitaskid);
	}

	@Transactional(value = "CRE", readOnly = false)
	public AjaxView saveResult(ProcessSuggestionInfo processSuggestionInfo, ActTaskParam actTaskParam) {
		AjaxView ajaxView = new AjaxView();
		try {
			if ("yes".equals(processSuggestionInfo.getPassFlag())) {// 提交
				// 将applyNo和suggestion保存到processSuggestionInfo中，主要是为了利用之前做抵质押物评估中的方法。
				processSuggestionInfoService.saveVisit(processSuggestionInfo);
				actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + processSuggestionInfo.getSuggestionDesc(), "提交", null);
				Map<String, Object> params = Maps.newHashMap();
				params.put("applyNo", actTaskParam.getApplyNo());
				params.put("taskDefKey", actTaskParam.getTaskDefKey());
				params.put("lockFlag", "1");
				videoUploadDao.lockVideoMessageByApplyNoAndTaskDefKey(params);
				processSuggestionInfoService.insertFlag(processSuggestionInfo, actTaskParam.getTaskDefKey());
				ajaxView.setSuccess().setMessage("提交成功！");
			} else {
				actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + processSuggestionInfo.getSuggestionDesc());
				processSuggestionInfoService.saveVisit(processSuggestionInfo);
				if ("no".equals(processSuggestionInfo.getPassFlag())) {
					processSuggestionInfo.setPassFlag("back");
				}
				processSuggestionInfoService.insertFlag(processSuggestionInfo, actTaskParam.getTaskDefKey());
				ajaxView.setSuccess().setMessage("已打回！");
			}
		} catch (Exception e) {
			logger.error("借前外访-外访意见-录入异常：", e);
			return ajaxView.setFailed().setMessage("提交失败！");
		}
		return ajaxView;

	}
}