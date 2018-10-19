package com.resoft.accounting.staOverdueStatus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.staOverdueStatus.dao.StaOverdueStatusDao;
import com.resoft.accounting.staOverdueStatus.entity.StaOverdueStatus;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
/**
 * 逾期信息Service
 * 
 * @author wangguodong
 * 
 */
@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class StaOverdueStatusService extends CrudService<StaOverdueStatusDao, StaOverdueStatus> {

	@Transactional(value = "ACC", readOnly = false)
	public List<StaOverdueStatus> findStaOverdueStatusByContractNo(String contractNo) {
		return this.dao.findStaOverdueStatusByContractNo(contractNo);
	}
}
