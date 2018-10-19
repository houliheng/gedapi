package com.resoft.credit.creditViewBook.service.creditOtherInfo;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;
import com.resoft.credit.creditViewBook.dao.creditOtherInfo.CreditOtherInfoDao;

/**
 * 征信意见书其他信息Service
 * @author wuxi01
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditOtherInfoService extends CrudService<CreditOtherInfoDao, CreditOtherInfo> {

	public CreditOtherInfo get(String id) {
		return super.get(id);
	}
	
	public List<CreditOtherInfo> findList(CreditOtherInfo creditOtherInfo) {
		return super.findList(creditOtherInfo);
	}
	
	public Page<CreditOtherInfo> findPage(Page<CreditOtherInfo> page, CreditOtherInfo creditOtherInfo) {
		return super.findPage(page, creditOtherInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditOtherInfo creditOtherInfo) {
		super.save(creditOtherInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditOtherInfo creditOtherInfo) {
		super.delete(creditOtherInfo);
	}
	
	public CreditOtherInfo getCreditOtherInfoByApplyNo(String applyNo){
		return super.dao.getCreditOtherInfoByApplyNo(applyNo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void updateByApplyNo(CreditOtherInfo creditOtherInfo) {
		 super.dao.updateByApplyNo(creditOtherInfo);
	}
	
}