package com.resoft.credit.mappingInfo.dao;

import com.resoft.credit.mappingInfo.entity.MappingInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同号映射表DAO接口
 * @author 合同号映射表
 * @version 2016-07-29
 */
@MyBatisDao
public interface MappingInfoDao extends CrudDao<MappingInfo> {
}