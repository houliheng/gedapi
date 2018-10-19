package com.resoft.outinterface.SV.dao;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.server.entry.request.SVApplyContact;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * sv回盘联系人DAO接口
 * @author admin
 * @version 2016-04-21
 */
@MyBatisDao
public interface SVApplyContactDao {
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SVApplyContact get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public SVApplyContact get(SVApplyContact entity);
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<SVApplyContact>());
	 * @param entity
	 * @return
	 */
	public List<SVApplyContact> findList(SVApplyContact entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<SVApplyContact> findAllList(SVApplyContact entity);
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(Map<String, Object> baseInfo);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(SVApplyContact entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(SVApplyContact entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(SVApplyContact entity);
	/**
	 * @reqno:H1507220015
	 * @date-designer:20150724-chenshaojia
	 * @date-author:20150724-chenshaojia:优化Mybatis分页插件的查询总数的sql语句，增加查询数据总数的接口方法
	 */
	/**
	 * 查询数据总数
	 * @param entity
	 * @return
	 */
	public long getCount(SVApplyContact entity);
}