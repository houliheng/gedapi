package com.resoft.accounting.applyMarginRepay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.applyMarginRepay.dao.ApplyMarginRepayVODao;
import com.resoft.accounting.applyMarginRepay.entity.ApplyMarginRepayVO;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 保证金退还申请Service
 * @author wuxi01
 * @version 2016-03-04
 */
@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class ApplyMarginRepayVOService extends CrudService<ApplyMarginRepayVODao, ApplyMarginRepayVO> {

	public ApplyMarginRepayVO get(String id) {
		return super.get(id);
	}
	
	public List<ApplyMarginRepayVO> findList(ApplyMarginRepayVO applyMarginRepayVO) {
		return super.findList(applyMarginRepayVO);
	}
	
	public Page<ApplyMarginRepayVO> findPage(Page<ApplyMarginRepayVO> page, ApplyMarginRepayVO applyMarginRepayVO) {
		return super.findPage(page, applyMarginRepayVO);
	}
	
	
}