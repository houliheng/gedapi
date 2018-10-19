package com.resoft.common.utils;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.utils.DataScopeUtils;

public class DataScopeFitter extends DataScopeUtils {
	public static void companyDataScopeFilter(BaseEntity<?> entity) {
		baseDataScopeFilter(entity);
		entity.getSqlMap().put("ResoftFramework_userSelfColumnName","a.create_by");
		entity.getSqlMap().put("ResoftFramework_officeColumnName", "a.ORG_ID");
		entity.getSqlMap().put("ResoftFramework_companyColumnName", "a.ORG_ID");
		entity.getSqlMap().put("ResoftFramework_officeSelfColumnName", "a.ORG_ID");
	}
	public static void companyDataScopeFilterToContractArchive(BaseEntity<?> entity) {
		baseDataScopeFilter(entity);
		entity.getSqlMap().put("ResoftFramework_userSelfColumnName","a.create_by");
		entity.getSqlMap().put("ResoftFramework_officeColumnName", "a.org_num");
		entity.getSqlMap().put("ResoftFramework_companyColumnName", "a.org_num");
		entity.getSqlMap().put("ResoftFramework_officeSelfColumnName", "a.org_num");
	}
}
