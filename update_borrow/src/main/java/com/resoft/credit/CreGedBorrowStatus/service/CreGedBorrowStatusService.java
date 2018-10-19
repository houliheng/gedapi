package com.resoft.credit.CreGedBorrowStatus.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.accContract.AccContract;
import com.resoft.credit.CreGedBorrowStatus.dao.CreGedBorrowStatusDao;
import com.resoft.credit.CreGedBorrowStatus.entity.CreGedBorrowStatus;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 冠E贷同步状态Service
 * @author zcl
 * @version 2018-03-13
 */
@Service @DbType("cre.dbType")
@Transactional(readOnly = true)
public class CreGedBorrowStatusService extends CrudService<CreGedBorrowStatusDao, CreGedBorrowStatus> {

	public CreGedBorrowStatus get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据申请编号保存状态
	 * @param applyNo
	 * @param status
	 */
	@Transactional(value="CRE",readOnly = false)
	public void saveGedBorrowStatusByApplyNo(String applyNo,String applyIdChild,  Integer status,Integer isUnion,AccContract contract){
		Facade.facade.gedOrderStateFeedBack(applyNo,applyIdChild, status,contract);
		CreGedBorrowStatus creGedBorrowStatus = new CreGedBorrowStatus();
		creGedBorrowStatus.setApplyNo(applyNo);
		List<CreGedBorrowStatus> glist = findList(creGedBorrowStatus);
		Date date = new Date();
		if(glist.size() > 0){
			creGedBorrowStatus = glist.get(0);
			creGedBorrowStatus.setStatus(status);
			creGedBorrowStatus.setUpdateDate(date);
			creGedBorrowStatus.setIsUnion(isUnion);



		}else{
			creGedBorrowStatus.setStatus(status);
			creGedBorrowStatus.setCreateDate(date);
			creGedBorrowStatus.setUpdateDate(date);
			creGedBorrowStatus.setIsUnion(isUnion);
		}
		save(creGedBorrowStatus);
	}
	
	public List<CreGedBorrowStatus> findList(CreGedBorrowStatus creGedBorrowStatus) {
		return super.findList(creGedBorrowStatus);
	}
	
	public Page<CreGedBorrowStatus> findPage(Page<CreGedBorrowStatus> page, CreGedBorrowStatus creGedBorrowStatus) {
		return super.findPage(page, creGedBorrowStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(CreGedBorrowStatus creGedBorrowStatus) {
		super.save(creGedBorrowStatus);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreGedBorrowStatus creGedBorrowStatus) {
		super.delete(creGedBorrowStatus);
	}
	
	/**
	 * 冠易贷订单状态反馈状态更新
	 * @param applyNo
	 * @param loanStatus
	 */
	@Transactional(readOnly = false)
	public boolean updateDrawStatus(String applyNo , String loanStatus){
		return this.dao.updateDrawStatus(applyNo,loanStatus);
	}
	
}