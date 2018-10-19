package com.resoft.credit.repayPlanUnion.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanResponse;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqForm;
import com.resoft.credit.repayPlanUnion.dao.RepayPlanUnionDao;

/**
 * 还款计划授信Service
 * @author wangguodong
 * @version 2016-12-14
 */
@Service
@Transactional(readOnly = true)
public class RepayPlanUnionService extends CrudService<RepayPlanUnionDao, RepayPlanUnion> {

	public RepayPlanUnion get(String id) {
		return super.get(id);
	}
	
	public List<RepayPlanUnion> findList(RepayPlanUnion repayPlanUnion) {
		return super.findList(repayPlanUnion);
	}
	
	public Page<RepayPlanUnion> findPage(Page<RepayPlanUnion> page, RepayPlanUnion repayPlanUnion) {
		return super.findPage(page, repayPlanUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(RepayPlanUnion repayPlanUnion) {
		super.save(repayPlanUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(RepayPlanUnion repayPlanUnion) {
		super.delete(repayPlanUnion);
	}
	
	public List<RepayPlanUnion> getRepayPlanByParam(Map<String,String> param){
		return super.dao.getRepayPlanByParam(param);
	}

	public List<GedRepayPlanResponse> getByApplyTaskAppro(Map<String, String> param) {
		// TODO Auto-generated method stub
		return dao.getByApplyTaskAppro(param);
	}

	public OrderContractReqForm getRepayAndContract(String applyNo, String taskDefKey, String approId) {
		return dao.getRepayAndContract( applyNo,  taskDefKey,  approId);

	}


}