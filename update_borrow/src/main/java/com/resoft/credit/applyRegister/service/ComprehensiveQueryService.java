package com.resoft.credit.applyRegister.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRegister.dao.ComprehensiveQueryDao;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegisterVO;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 综合查询Service
 * 
 * @author admin
 * @version 2016-03-01
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ComprehensiveQueryService extends CrudService<ComprehensiveQueryDao, ApplyRegisterVO> {

	public ApplyRegisterVO get(String id) {
		return super.get(id);
	}

	public List<ApplyRegisterVO> findList(ApplyRegisterVO applyRegisterVO) {
		return super.findList(applyRegisterVO);
	}

	public Page<ApplyRegisterVO> findPage(Page<ApplyRegisterVO> page, ApplyRegisterVO applyRegisterVO) {
		return super.findPage(page, applyRegisterVO);
	}

	/**
	 * 根据进件号查询流程
	 * 
	 * @param applyNo
	 * @return
	 */
	public ActTaskParam getActTaskParamByApplyNo(String applyNo) {
		return super.dao.getActTaskParamByApplyNo(applyNo);
	}

	public List<Map<String, Object>> findHQList(Map<String, Object> params) {
		return this.dao.findHQList(params);
	}

	public Long findHQListCount(Map<String, Object> parMap) {
		return this.dao.findHQListCount(parMap);
	}

	/**
	 * 根据进件ID查新信息
	 * 
	 * @param applyNo
	 * @return
	 */
	public HashMap<String, String> showDown(String applyRegisterVOId) {
		return super.dao.showDown(applyRegisterVOId);
	}

	public List<Map<String, Object>> findComprehensiveQueryList(Map<String, Object> parMap) {
		return this.dao.findComprehensiveQueryList(parMap);
	}

	public List<Map<String, Object>> findComprehensiveQuerySubList(Map<String, Object> parMap) {
		return this.dao.findComprehensiveQuerySubList(parMap);
	}

	public Long findComprehensiveQueryListCount(Map<String, Object> parMap) {
		return this.dao.findComprehensiveQueryListCount(parMap);
	}
}