package com.resoft.credit.dynamicSet.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:表单数据项分类配置表 DAO
 */
@MyBatisDao("CreDataGroupTableDao")
public interface CreDataGroupTableDao extends CrudDao<CreDataGroupTable>{
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息_动态配置数据项展现
	 *		根据显示位置查询表单数据项分类配置表所有数据
	 */
	/**
	 * @reqno:H1510080093
	 * @date-designer:2015年10月17日-songmin
	 * @date-author:2015年10月17日-songmin:CRE_信贷审批_申请录入_贷款申请信息_动态表单栏展现
	 * 	系统修改了表结构，加入了多法人的支持，这里进行数据查询的时候增加公司ID的判断条件
	 * @param showPosition：显示位置         companyId：公司ID
	 */
	//public List<CreDataGroupTable> findDataSets(String showPosition);
	public List<CreDataGroupTable> findDataSets(CreDataGroupTable creDataGroupTable);
	
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:动态查询动态表单存储的数据
	 * 	select * from ${table} where APPLY_ID=#{applyId}
	 */
	public Map dynaResultQurey(Map map);
	
	/**
	 * @reqno:H1510080094
	 * @date-designer:2015年10月19日-songmin
	 * @date-author:2015年10月19日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_动态表单栏数据
	 * 		动态表单数据录入
	 */
	public void dynaResultInsert(Map map);
	/**
	 * @reqno:H1510080094
	 * @date-designer:2015年10月19日-songmin
	 * @date-author:2015年10月19日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_动态表单栏数据
	 * 		动态表单数据修改
	 */
	public void dynaResultUpdate(Map map);
}
