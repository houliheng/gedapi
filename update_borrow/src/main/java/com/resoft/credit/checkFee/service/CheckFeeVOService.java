package com.resoft.credit.checkFee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.checkFee.dao.CheckFeeVODao;
import com.resoft.credit.checkFee.entity.CheckFeeVO;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 外访费返还Service
 * @author yanwanmei
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckFeeVOService extends CrudService<CheckFeeVODao, CheckFeeVO> {

	public List<CheckFeeVO> findList(CheckFeeVO checkFeeVO) {
		return super.findList(checkFeeVO);
	}
	
	public Page<CheckFeeVO> findPage(Page<CheckFeeVO> page, CheckFeeVO checkFeeVO) {
		return super.findPage(page, checkFeeVO);
	}
	
	//系统管理员查询所有用户的数据
	public Page<MyMap> findAllPage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(this.dao.findAllUserList(paramMap));
		return page;
	}
}