package com.resoft.credit.checkFace.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkFace.dao.CheckFaceDao;
import com.resoft.credit.checkFace.entity.CheckFace;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 面审信息Service
 * @author yanwanmei
 * @version 2016-02-25
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckFaceService extends CrudService<CheckFaceDao, CheckFace> {

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
		
	public CheckFace get(String id) {
		return super.get(id);
	}
	
	public List<CheckFace> findList(CheckFace checkFace) {
		return super.findList(checkFace);
	}
	
	public Page<CheckFace> findPage(Page<CheckFace> page, CheckFace checkFace) {
		return super.findPage(page, checkFace);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CheckFace checkFace) {
		super.save(checkFace);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CheckFace checkFace) {
		super.delete(checkFace);
	}
	@Transactional(value="CRE",readOnly = false)
	public void saveCheckFace(CheckFace checkFace,ActTaskParam actTaskParam,Map<String, String> processMap,Model model) throws Exception{
		this.save(checkFace);
		processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap,model);
	}
	@Transactional(value="CRE",readOnly = false)
	public CheckFace getCheckFaceByApplyNo(String applyNo){
		return super.dao.getCheckFaceByApplyNo(applyNo);
		
	}
	
	}