package com.resoft.multds.credit.plCustinfo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.credit.plCustinfo.dao.PLCustInfoDao;
import com.resoft.multds.credit.plCustinfo.entity.PLCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 借后客户信息Service
 * 
 * @author wuxi01
 * 
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class PLCustInfoService extends CrudService<PLCustInfoDao, PLCustInfo> {

}