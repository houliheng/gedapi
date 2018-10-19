package com.resoft.postloan.plProFromColumn.dao;

import java.util.List;

import com.resoft.postloan.plProFromColumn.entity.PLProFromColumn;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:产品动态表单数据项配置表 DAO
 */
@MyBatisDao("plProFromColumnDao")
public interface PLProFromColumnDao extends CrudDao<PLProFromColumn>{
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息_动态配置数据项展现
	 * 		根据dataGroup、showPosition[1贷款申请信息2客户信息]来查询所有的动态表单配置项列表数据，查询的结果在service组装成Map<DATA_GROUP，LIST<CreDataGroupTable>>的形式
	 */
	public List<PLProFromColumn> findDataColumnSets(PLProFromColumn creProFromColumn);
}
