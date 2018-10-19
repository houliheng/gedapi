package com.resoft.credit.rateCapital.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.rateCapital.entity.RateCapital;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao("creRateCapitalDao")
public interface RateCapitalDao extends CrudDao<RateCapital> {
	public void saveAll(Map<String, Object> param);
	public void banchDelete(Map<String, Object> param);
	public List<RateCapital> findListAll(RateCapital rateCapital);
	public List<RateCapital> selectCapitalEdit(RateCapital rateCapital);
}
