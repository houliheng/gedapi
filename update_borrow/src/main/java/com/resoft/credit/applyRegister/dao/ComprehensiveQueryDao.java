package com.resoft.credit.applyRegister.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegisterVO;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 综合查询DAO接口
 * @version 2016-03-01
 */
@MyBatisDao
public interface ComprehensiveQueryDao extends CrudDao<ApplyRegisterVO> {
	
	/**
	 * 根据进件号查询流程
	 * @param applyNo
	 * @return
	 */
	public ActTaskParam getActTaskParamByApplyNo(String applyNo);
	public List<Map<String, Object>> findHQList(Map<String, Object> params);
	public Long findHQListCount(Map<String, Object> params);
	public HashMap<String, String> showDown(String applyRegisterVOId);
	/**
	 * 查询综合信息多级表格 主表格数据
	 */
	public List<Map<String, Object>> findComprehensiveQueryList(Map<String, Object> parMap);
	/**
	 * 查询综合信息多级表格 子表格数据
	 */
	public List<Map<String, Object>> findComprehensiveQuerySubList(Map<String, Object> parMap);
	/**
	 *	查询综合信息多级表格 主表格数据总数
	 */
	public Long findComprehensiveQueryListCount(Map<String, Object> parMap);

}