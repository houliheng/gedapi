package com.resoft.credit.dynamicSet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.dynamicSet.dao.CreDataGroupTableDao;
import com.resoft.credit.dynamicSet.dao.CreProFromColumnDao;
import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:表单数据项分类配置表 Service
 */
@Service("CreDataGroupTableService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreDataGroupTableService extends CrudService<CreDataGroupTableDao, CreDataGroupTable>{
	@Autowired
	private CreProFromColumnDao creProFromColumnDao;
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息_动态配置数据项展现
	 *		根据显示位置查询表单数据项分类配置表所有数据，同时将表单数据项分类配置表实体类中的对应的产品动态表单数据项配置表List数据填充完成
	 *@param showPosition显示位置：1贷款申请信息2客户信息
	 *@param productType产品类型
	 */
	public List<CreDataGroupTable> findDataSets(String showPosition,String productType){
		//根据显示位置查询表单数据项分类配置表所有数据
		CreDataGroupTable creDataGroupTable =new CreDataGroupTable();
		creDataGroupTable.setShowPosition(showPosition);
		/**
		 * @reqno:H1510080093
		 * @date-designer:2015年10月19日-songmin
		 * @date-author:2015年10月19日-songmin:需求中加入动态表单栏配置支持多法人
		 * 		多法人的支持：即在获取动态表单栏配置的时候，在查询时要根据用户所在的 根公司 ID来进行区分
		 * 		根公司在获取直接通过parentIDs来获得，根据系统设定，parentsIds[1]为根公司ID，这里考虑到
		 * 		根公司下的用户，当parentsIds[1]不存在时，直接获取器所在公司ID，一般状况下该情形不该出现
		 */
		User user = UserUtils.getUser();
		Office company = user.getCompany();
		String rootCompanyId = null;
		String parentIds = company.getParentIds();
		if(StringUtils.isNotEmpty(parentIds)){
			String[] parentIdsArr = parentIds.split(",");
			if(parentIdsArr.length>1){
				rootCompanyId = parentIdsArr[1];
			}else{
				rootCompanyId = company.getId();
			}
		}
		creDataGroupTable.setCompanyId(rootCompanyId);
		List<CreDataGroupTable> list_tables = super.dao.findDataSets(creDataGroupTable);
		
		//表单配置项配置存在的情况下//以下为填充产品动态表单数据项配置表List
		if(null!=list_tables && list_tables.size()>0){
			//根据产品类型和显示位置   查询产品动态表单数据项配置表记录信息
			CreProFromColumn creProFromColumn = new CreProFromColumn();
			creProFromColumn.setShowPosition(showPosition);
			creProFromColumn.setProductTypeCode(productType);//产品动态表单数据项配置查询需要根据产品分类的限定条件
			/**
			 * @reqno:H1510080094
			 * @date-designer:2015年10月19日-songmin
			 * @date-author:2015年10月19日-songmin:加入根公司的限定条件，避免在多法人的情况下查询出多余的数据
			 */
			creProFromColumn.setCompanyId(rootCompanyId);//加入根公司的限定条件，避免在多法人的情况下查询出多余的数据
			List<CreProFromColumn> prolist = creProFromColumnDao.findDataColumnSets(creProFromColumn);
			
			if(null!=prolist && prolist.size()>0){
				//以Map<DataGroup,List<CreProFromColumn>>的形式分类存储产品动态表单数据项配置列表
				Map<String,List<CreProFromColumn>> creProFromColumnMap = new HashMap<String, List<CreProFromColumn>>();
				if(null!=prolist){
					for (CreProFromColumn proFromColumn : prolist) {
						List<CreProFromColumn> prolist_temp = creProFromColumnMap.get(proFromColumn.getDataGroupId());
						if(null==prolist_temp){
							prolist_temp = new ArrayList<CreProFromColumn>();
							creProFromColumnMap.put(proFromColumn.getDataGroupId(), prolist_temp);
						}
						prolist_temp.add(proFromColumn);
					}
				}
				//填充产品动态表单数据项配置到表单数据项分类配置实体类中
				for (CreDataGroupTable dataGroupTable : list_tables) {
					List<CreProFromColumn> prolistCache = creProFromColumnMap.get(dataGroupTable.getId());
					if(null!=prolistCache){
						dataGroupTable.setProFromColumnList(prolistCache);
					}
				}
			}
		}
		return list_tables;
	}
	/**
	 * @reqno: H1511100160
	 * @date-designer:20151118-lirongchao

	 * @date-author:20151118-lirongchao: 1.点击列表操作中的“客户详情”，弹出窗口，跨系统访问信贷审批系统，查看客户贷款申请信息、客户信息；
											2.点击列表操作中的“客户详情”，弹出窗口,窗口名称“客户详情”；
											3.页面布局：以tab页签形式显示，包括：贷款申请信息、客户信息；默认只加载“贷款申请信息”页签，其它页签只有在点击时才做加载；
											两页面显示的元素与信贷审批系统中申请录入保持不变；页面所有的表单均为只读，保存、新增、删除、修改按钮隐藏不显示；
											当前环节-因为会有跨系统访问，所以重构了一下动态表单，将获取用户机构的方式通过参数传递

	 */	
	public List<CreDataGroupTable> findDataSets(String showPosition,String productType,String rootCompanyId){
		//根据显示位置查询表单数据项分类配置表所有数据
		CreDataGroupTable creDataGroupTable =new CreDataGroupTable();
		creDataGroupTable.setShowPosition(showPosition);
//		/**
//		 * @reqno:H1510080093
//		 * @date-designer:2015年10月19日-songmin
//		 * @date-author:2015年10月19日-songmin:需求中加入动态表单栏配置支持多法人
//		 * 		多法人的支持：即在获取动态表单栏配置的时候，在查询时要根据用户所在的 根公司 ID来进行区分
//		 * 		根公司在获取直接通过parentIDs来获得，根据系统设定，parentsIds[1]为根公司ID，这里考虑到
//		 * 		根公司下的用户，当parentsIds[1]不存在时，直接获取器所在公司ID，一般状况下该情形不该出现
//		 */
//		User user = UserUtils.getUser();
//		Office company = user.getCompany();
//		String rootCompanyId = null;
//		String parentIds = company.getParentIds();
//		if(StringUtils.isNotEmpty(parentIds)){
//			String[] parentIdsArr = parentIds.split(",");
//			if(parentIdsArr.length>1){
//				rootCompanyId = parentIdsArr[1];
//			}else{
//				rootCompanyId = company.getId();
//			}
//		}
		creDataGroupTable.setCompanyId(rootCompanyId);
		List<CreDataGroupTable> list_tables = super.dao.findDataSets(creDataGroupTable);
		
		//表单配置项配置存在的情况下//以下为填充产品动态表单数据项配置表List
		if(null!=list_tables && list_tables.size()>0){
			//根据产品类型和显示位置   查询产品动态表单数据项配置表记录信息
			CreProFromColumn creProFromColumn = new CreProFromColumn();
			creProFromColumn.setShowPosition(showPosition);
			creProFromColumn.setProductTypeCode(productType);//产品动态表单数据项配置查询需要根据产品分类的限定条件
			/**
			 * @reqno:H1510080094
			 * @date-designer:2015年10月19日-songmin
			 * @date-author:2015年10月19日-songmin:加入根公司的限定条件，避免在多法人的情况下查询出多余的数据
			 */
			creProFromColumn.setCompanyId(rootCompanyId);//加入根公司的限定条件，避免在多法人的情况下查询出多余的数据
			List<CreProFromColumn> prolist = creProFromColumnDao.findDataColumnSets(creProFromColumn);
			
			if(null!=prolist && prolist.size()>0){
				//以Map<DataGroup,List<CreProFromColumn>>的形式分类存储产品动态表单数据项配置列表
				Map<String,List<CreProFromColumn>> creProFromColumnMap = new HashMap<String, List<CreProFromColumn>>();
				if(null!=prolist){
					for (CreProFromColumn proFromColumn : prolist) {
						List<CreProFromColumn> prolist_temp = creProFromColumnMap.get(proFromColumn.getDataGroupId());
						if(null==prolist_temp){
							prolist_temp = new ArrayList<CreProFromColumn>();
							creProFromColumnMap.put(proFromColumn.getDataGroupId(), prolist_temp);
						}
						prolist_temp.add(proFromColumn);
					}
				}
				//填充产品动态表单数据项配置到表单数据项分类配置实体类中
				for (CreDataGroupTable dataGroupTable : list_tables) {
					List<CreProFromColumn> prolistCache = creProFromColumnMap.get(dataGroupTable.getId());
					if(null!=prolistCache){
						dataGroupTable.setProFromColumnList(prolistCache);
					}
				}
			}
		}
		return list_tables;
	}
	
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:动态查询动态表单存储的数据
	 * 		根据动态表单配置项中的配置的表名和传入的applyId查询动态表单配置项存储的表单数据
	 */
	public void dynaResultQurey(String applyId,List<CreDataGroupTable> creDataGroupTableList){
		if(null!=creDataGroupTableList && creDataGroupTableList.size()>0){
			for (CreDataGroupTable creDataGroupTable : creDataGroupTableList) {
				//有产品动态表单配置项的时候才查询数据
				if(null!=creDataGroupTable.getProFromColumnList()&&creDataGroupTable.getProFromColumnList().size()>0){
					Map paramMap = new HashMap();
					paramMap.put("table", creDataGroupTable.getDataTableName());
					paramMap.put("applyId", applyId);
					Map resultMap =  super.dao.dynaResultQurey(paramMap);
					creDataGroupTable.setResultMap(resultMap);
				}
			}
		}
	}
	
	/**
	 * @reqno:H1510210079
	 * @date-designer:2015年11月3日-songmin
	 * @date-author:2015年11月3日-songmin:CRE_信贷审批_总监审批_集成展示：贷款申请信息、客户信息、内匹配信息、征信信息
	 * 		判断产品动态配置表配置的动态表中是否存在相同ApplyID的数据，该查询用于首次保存贷款申请信息时，判断同一批次的保存操作是否有指向同一张表的情况
	 */
	public Map dynaFormQuery(String tableName,String applyId){
		Map map = new HashMap();
		map.put("table", tableName);
		map.put("applyId", applyId);
		return super.dao.dynaResultQurey(map);
	}
	
	/**
	 * @reqno:H1510080094
	 * @date-designer:2015年10月19日-songmin
	 * @date-author:2015年10月19日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_动态表单栏数据
	 *		动态表单配置项数据录入
	 */
	@Transactional(value="CRE",readOnly=false)
	public void dynaResultInsert(String tableName,Map entryMap){
		//必需确保key和value的长度一致
		if(null!=entryMap &&entryMap.size()>0){
			Map map = new HashMap();
			map.put("table", tableName);
			map.put("entryMap", entryMap);
			super.dao.dynaResultInsert(map);
		}
	}
	/**
	 * @reqno:H1510080094
	 * @date-designer:2015年10月19日-songmin
	 * @date-author:2015年10月19日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_动态表单栏数据
	 *		动态表单配置项数据修改
	 */
	@Transactional(value="CRE",readOnly=false)
	public void dynaResultUpdate(String tableName,
			Map entryMap,String id){
		//必需确保key和value的长度一致
		if(null!=entryMap && entryMap.size()>0){
			Map map = new HashMap();
			map.put("table", tableName);
			map.put("entryMap", entryMap);
			map.put("id", id);
			super.dao.dynaResultUpdate(map);
		}
	}
}
