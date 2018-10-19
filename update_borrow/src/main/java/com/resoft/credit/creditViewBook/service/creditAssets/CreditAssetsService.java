package com.resoft.credit.creditViewBook.service.creditAssets;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditViewBook.dao.creditAssets.CreditAssetsDao;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 信审意见书资产清单Service
 * @author wuxi01
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditAssetsService extends CrudService<CreditAssetsDao, CreditAssets> {

	public CreditAssets get(String id) {
		return super.get(id);
	}
	
	public List<CreditAssets> findList(CreditAssets creditAssets) {
		return super.findList(creditAssets);
	}
	
	public Page<CreditAssets> findPage(Page<CreditAssets> page, CreditAssets creditAssets) {
		return super.findPage(page, creditAssets);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditAssets creditAssets) {
		super.save(creditAssets);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditAssets creditAssets) {
		super.delete(creditAssets);
	}
	
	@Transactional(value="CRE",readOnly=false)
	public void batchDelete(List<String> idList){
		/*if(null!=idList && idList.size()>0){
			Map<String,Object> param = new HashMap<String, Object>();*/
			/*param.put("idList", idList);*/
			super.dao.batchDelete(idList);
		//}
	}
	
	public List<CreditAssets> getCreditAssetsByApplyNo(String applyNo){
		return super.dao.getCreditAssetsByApplyNo(applyNo);
	}
	
}