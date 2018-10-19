package com.resoft.credit.dynamicSet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.dynamicSet.dao.CreProFromColumnDao;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:产品动态表单数据项配置表 service
 */
@Service("CreProFromColumnService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreProFromColumnService extends CrudService<CreProFromColumnDao, CreProFromColumn>{
//	/**
//	 * @reqno:H1510080092
//	 * @date-designer:2015年10月15日-songmin
//	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息_动态配置数据项展现
//	 * 		根据dataGroup、showPosition[1贷款申请信息2客户信息]来查询所有的动态表单配置项列表数据，并将结果组装成Map<DATA_GROUP，LIST<CreDataGroupTable>>的形式返回
//	 */
//	public List<CreProFromColumn> findDataColumnSets(CreProFromColumn creProFromColumn){
//		List<CreProFromColumn> list = super.dao.findDataColumnSets(creProFromColumn);
//		Map<String,List<CreProFromColumn>> creProFromColumnMap = new HashMap<String, List<CreProFromColumn>>();
//		if(null!=list){
//			for (CreProFromColumn proFromColumn : list) {
//				List<CreProFromColumn> list_temp = creProFromColumnMap.get(proFromColumn.getDataGroup());
//				if(null==list_temp){
//					list_temp = new ArrayList<CreProFromColumn>();
//					creProFromColumnMap.put(proFromColumn.getDataGroup(), list_temp);
//				}
//				list_temp.add(proFromColumn);
//			}
//		}
//		return null;
//	}
}
