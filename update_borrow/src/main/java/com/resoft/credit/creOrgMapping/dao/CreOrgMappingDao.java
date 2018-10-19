package com.resoft.credit.creOrgMapping.dao;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.creOrgMapping.entity.CreOrgMapping;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
/**
 * 机构映射关系
 * @author guoshaohua
 *
 */
@MyBatisDao
public interface CreOrgMappingDao extends CrudDao<CreOrgMapping>{
	
	
	/**
	 * 查询机构映射表中已有的最大机构码值
	 * @return
	 */
	public Integer findMaxOrgValue();
	
	public CreOrgMapping findCreOrgMappingByCode(@Param("code") String code);
}
