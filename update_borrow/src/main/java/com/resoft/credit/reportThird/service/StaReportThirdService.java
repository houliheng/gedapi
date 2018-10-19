package com.resoft.credit.reportThird.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.reportThird.dao.StaReportThirdDao;
import com.resoft.credit.reportThird.entity.StaReportThird;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class StaReportThirdService extends CrudService<StaReportThirdDao, StaReportThird> {
	public List<StaReportThird> findListThird(Map<String, Object> reviewedRule) {
		return this.dao.findListThird(reviewedRule);
	}
}
