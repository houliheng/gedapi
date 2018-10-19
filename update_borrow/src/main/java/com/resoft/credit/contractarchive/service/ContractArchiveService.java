/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.contractarchive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.contractarchive.dao.ContractArchiveDao;
import com.resoft.credit.contractarchive.entity.ContractArchive;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同归档信息表Service
 * @author lirongchao
 * @version 2016-01-20
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class ContractArchiveService extends CrudService<ContractArchiveDao, ContractArchive> {

	private static final Logger logger = LoggerFactory.getLogger(ContractArchiveService.class);

	@Autowired
	private ContractArchiveDao contractArchiveDao;

	public ContractArchive get(String id) {
		return super.get(id);
	}

	public List<ContractArchive> findList(ContractArchive contractArchive) {
		return super.findList(contractArchive);
	}

	public Page<ContractArchive> findPage(Page<ContractArchive> page, ContractArchive contractArchive) {
		return super.findPage(page, contractArchive);
	}

	@Transactional(value="CRE",readOnly = false)
	public void save(ContractArchive contractArchive) {
		super.save(contractArchive);
	}

	/**
	 * 根据合同编号查询合同归档信息
	 * @param params
	 * @return
	 */
	public List<ContractArchive> findListByContractNo(Map<String, Object> params){
		return super.dao.findListByContractNo(params);
	}

	public ContractArchive findByContractNo(String contractNo){
		return super.dao.findByContractNo(contractNo);
	}

	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_recipitent
	 * @date-author:2016年01月25日-lirongchao:保存合同归档信息
	 */
	@Transactional(value="CRE",readOnly = false)
	public String archiveSave(ContractArchive contractArchive,String borrowingTime) {
		String returnValue = "";
		try {
			ContractArchive contractArchiveInfo = super.get(contractArchive.getId());
			contractArchiveInfo.setContractLocation(contractArchive.getContractLocation());
			contractArchiveInfo.setIsBorrowing(contractArchive.getIsBorrowing());
			contractArchiveInfo.setBorrowingName(contractArchive.getBorrowingName());
			if(!StringUtils.isEmpty(borrowingTime)){
				contractArchiveInfo.setBorrowingTime(DateUtils.parseDate(borrowingTime, "yyyy-MM-dd"));
			}
			else{
				contractArchiveInfo.setBorrowingTime(null);
			}
			contractArchiveInfo.setBorrowingReason(contractArchive.getBorrowingReason());
			super.save(contractArchiveInfo);
			returnValue = "success";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return returnValue;
	}
	/**
	 * @reqno: H1601150171
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_Sender
	 * @date-author:2016年01月25日-lirongchao:保存合同归档发送信息,并将状态改为已发送
	 */
	@Transactional(value="CRE",readOnly = false)
	public String sendSave(ContractArchive contractArchive,String senderTime) {
		String returnValue = "";
		try {
			ContractArchive contractArchiveInfo = super.get(contractArchive.getId());
			contractArchiveInfo.setSenderName(contractArchive.getSenderName());
			contractArchiveInfo.setIsSender("1");
			contractArchiveInfo.setExpressCompany(contractArchive.getExpressCompany());
			contractArchiveInfo.setExpressNo(contractArchive.getExpressNo());
			if(!StringUtils.isEmpty(senderTime)){
				contractArchiveInfo.setSenderTime(DateUtils.parseDate(senderTime, "yyyy-MM-dd"));
			}
			else{
				contractArchiveInfo.setBorrowingTime(null);
			}
			super.save(contractArchiveInfo);
			returnValue = "success";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return returnValue ;
	}
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_recipitent
	 * @date-author:2016年01月25日-lirongchao:将多条信息的id以一定格式传给后台，后台再进行批量更新
	 */
	@Transactional(value="CRE",readOnly = false)
	public void recipitent(String ids) {
		List<String> idList = new ArrayList<String>();
		if(!StringUtils.isEmpty(ids)){
			for (String id : ids.split(",")){
				if(!StringUtils.isEmpty(id)){
					idList.add(id);
				}
			}
			String recipientName = UserUtils.getUser().getName();
			Map map =new HashMap();
			map.put("idList", idList);
			map.put("recipientName", recipientName);
			contractArchiveDao.recipitent(map);
		}
	}
	/**
	 * 联合受信的批量插入合同归档信息
	 * @param params
	 * @return
	 */
	public void insertContractArchiveLst( List<ContractArchive> contractArchivelst) {
		this.dao.insertContractArchiveLst(contractArchivelst);

	}
	/**
	 * 联合受信查询归档信息
	 * @param params
	 * @return
	 */
	public List<ContractArchive> getArchiveLstByApplyNo(String applyNo) {
		return this.dao.getArchiveLstByApplyNo(applyNo);
	}

	public Long getExpressNoCount(Map<String, Object> parMap) {
		return this.dao.getExpressNoCount(parMap);
	}

	public List<Map<String, Object>> getDataList(Map<String, Object> parMap) {
		return this.dao.getDataList(parMap);
	}

	public List<Map<String, Object>> getSubDataList(Map<String, Object> parMap) {
		return this.dao.getSubDataList(parMap);
	}

}