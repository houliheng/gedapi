package com.resoft.credit.interfaceinfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;

/**
 * 接口日志记录DAO接口
 * @author wanghong
 * @version 2016-10-31
 */
@MyBatisDao
public interface InterfaceInfoDao extends CrudDao<InterfaceInfo> {

	InterfaceInfo findSVtoThemisResult(InterfaceInfo interfaceInfo);

	
}