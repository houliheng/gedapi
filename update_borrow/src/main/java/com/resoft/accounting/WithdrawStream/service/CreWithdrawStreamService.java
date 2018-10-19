package com.resoft.accounting.WithdrawStream.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.WithdrawStream.dao.CreWithdrawStreamDao;
import com.resoft.accounting.WithdrawStream.entity.CreWithdrawStream;
import com.resoft.outinterface.rest.ged.entity.GedServiceFee;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 冠E贷充值流水记录Service
 * @author gsh
 * @version 2018-04-02
 */
@Service
@Transactional(readOnly = true)
public class CreWithdrawStreamService extends CrudService<CreWithdrawStreamDao, CreWithdrawStream> {

	public CreWithdrawStream get(String id) {
		return super.get(id);
	}
	
	public List<CreWithdrawStream> findList(CreWithdrawStream creWithdrawStream) {
		return super.findList(creWithdrawStream);
	}
	
	public Page<CreWithdrawStream> findPage(Page<CreWithdrawStream> page, CreWithdrawStream creWithdrawStream) {
		return super.findPage(page, creWithdrawStream);
	}
	
	@Transactional(readOnly = false)
	public void save(CreWithdrawStream creWithdrawStream) {
		super.save(creWithdrawStream);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreWithdrawStream creWithdrawStream) {
		super.delete(creWithdrawStream);
	}
	
	
	public void saveStream(GedServiceFee gedServiceFee){
		CreWithdrawStream withdrawStream = new CreWithdrawStream();
		if(StringUtils.isNotBlank(gedServiceFee.getContractNo())){
			withdrawStream.setContractNo(gedServiceFee.getContractNo());
		}
		if (StringUtils.isNotBlank(gedServiceFee.getApplyNo())) {
			withdrawStream.setApplyNo(gedServiceFee.getApplyNo());
		}
		if (StringUtils.isNotBlank(gedServiceFee.getMchn())) {
			withdrawStream.setMchn(gedServiceFee.getMchn());
		}
		if (StringUtils.isNotBlank(gedServiceFee.getSeqNo())) {
			withdrawStream.setSeqno(gedServiceFee.getSeqNo());
		}
		if (StringUtils.isNotBlank(gedServiceFee.getWithdrawAmount())) {
			withdrawStream.setWithdrawAmount(gedServiceFee.getWithdrawAmount());
		}
		if(withdrawStream != null){
			save(withdrawStream);
		}
	}
}