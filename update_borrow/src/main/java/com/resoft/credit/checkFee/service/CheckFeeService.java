package com.resoft.credit.checkFee.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.checkFee.entity.CheckFeeVO;
import com.resoft.credit.checkFee.dao.CheckFeeDao;

/**
 * 外访费登记Service
 * @author yanwanmei
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckFeeService extends CrudService<CheckFeeDao, CheckFee> {

	public CheckFee get(String id) {
		return super.get(id);
	}
	
	public List<CheckFee> findList(CheckFee checkFee) {
		return super.findList(checkFee);
	}
	
	public Page<CheckFee> findPage(Page<CheckFee> page, CheckFee checkFee) {
		return super.findPage(page, checkFee);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CheckFee checkFee) {
		super.save(checkFee);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CheckFee checkFee) {
		super.delete(checkFee);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public CheckFee findByApplyNo(String applyNo){
		return this.dao.findByApplyNo(applyNo);
		
	}
	
	/**
	 * 查询外访费返还列表
	 * @param params
	 * @return
	 */
	public List<CheckFeeVO> findCheckFeeVOList(Map<String, String> params){
		return super.dao.findCheckFeeVOList(params);
	};
	
}