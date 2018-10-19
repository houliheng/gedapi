package com.resoft.outinterface.SV.dao;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.server.entry.request.SVCustWorkInfo;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * SV回盘客户工作信息DAO接口
 * 
 * @author admin
 * @version 2016-04-21
 */
@MyBatisDao
public interface SVCustWorkInfoDao {
	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public SVCustWorkInfo get(String id);

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public SVCustWorkInfo get(SVCustWorkInfo entity);

	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<SVCustWorkInfo>());
	 * 
	 * @param entity
	 * @return
	 */
	public List<SVCustWorkInfo> findList(SVCustWorkInfo entity);

	/**
	 * 查询所有数据列表
	 * 
	 * @param entity
	 * @return
	 */
	public List<SVCustWorkInfo> findAllList(SVCustWorkInfo entity);

	/**
	 * 查询所有数据列表
	 * 
	 * @see public List<SVCustWorkInfo> findAllList(SVCustWorkInfo entity)
	 * @return
	 */
	@Deprecated
	public List<SVCustWorkInfo> findAllList();

	/**
	 * 插入数据
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(Map<String, Object> baseInfo);

	/**
	 * 更新数据
	 * 
	 * @param entity
	 * @return
	 */
	public int update(SVCustWorkInfo entity);

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param id
	 * @see public int delete(SVCustWorkInfo entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param entity
	 * @return
	 */
	public int delete(SVCustWorkInfo entity);

	/**
	 * @reqno:H1507220015
	 * @date-designer:20150724-chenshaojia
	 * @date-author:20150724-chenshaojia:优化Mybatis分页插件的查询总数的sql语句，增加查询数据总数的接口方法
	 */
	/**
	 * 查询数据总数
	 * 
	 * @param entity
	 * @return
	 */
	public long getCount(SVCustWorkInfo entity);
}