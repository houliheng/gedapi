/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.contractarchive.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.contractarchive.entity.ContractArchive;

/**
 * 合同归档信息表DAO接口
 * @author lirongchao
 * @version 2016-01-20
 */
@MyBatisDao
public interface ContractArchiveDao extends CrudDao<ContractArchive> {
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_recipitent
	 * @date-author:2016年01月25日-lirongchao:批量更新签收状态
	 */
	public void recipitent(Map map);

	/**
	 * 根据合同编号查询合同归档信息
	 * @param params
	 * @return
	 */
	public List<ContractArchive> findListByContractNo(Map<String, Object> params);
	public ContractArchive findByContractNo(String contractNo);

	public void insertContractArchiveLst(@Param("contractArchivelst") List<ContractArchive> contractArchivelst);
	/**
	 * 根据合同编号查询联合受信合同归档信息
	 * @param params
	 * @return
	 */
	public List<ContractArchive> insertContractArchiveLst(String applyNo);

	public List<ContractArchive> getArchiveLstByApplyNo(String applyNo);

	public Long getExpressNoCount(Map<String, Object> parMap);

	public List<Map<String, Object>> getDataList(Map<String, Object> parMap);
	public List<Map<String, Object>> getSubDataList(Map<String, Object> parMap);
}