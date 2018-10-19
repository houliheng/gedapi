package com.resoft.accounting.deductApply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.deductApply.dao.DeductApplyVODao;
import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 划扣查询Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class DeductApplyVOService extends CrudService<DeductApplyVODao, DeductApplyVO> {

	public DeductApplyVO get(String id) {
		return super.get(id);
	}

	public List<DeductApplyVO> findList(DeductApplyVO deductApplyVO) {
		return super.findList(deductApplyVO);
	}

	public Page<DeductApplyVO> findPage(Page<DeductApplyVO> page, DeductApplyVO deductApplyVO) {
		return super.findPage(page, deductApplyVO);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(DeductApplyVO deductApplyVO) {
		super.save(deductApplyVO);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(DeductApplyVO deductApplyVO) {
		super.delete(deductApplyVO);
	}
	
	/**
	 * 根据划扣申请编号获取数据
	 */
	@Transactional(value = "ACC", readOnly = false)
	public DeductApplyVO getDeductApplyVOByDeductApplyNo(String deductApplyNo) {
		return this.dao.getDeductApplyVOByDeductApplyNo(deductApplyNo);
	}


}