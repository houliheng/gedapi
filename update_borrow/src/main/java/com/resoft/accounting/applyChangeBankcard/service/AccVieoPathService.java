package com.resoft.accounting.applyChangeBankcard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.applyChangeBankcard.dao.AccVideoPathDao;
import com.resoft.accounting.applyChangeBankcard.entity.AccVideoPath;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;


@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class AccVieoPathService extends CrudService<AccVideoPathDao, AccVideoPath> {

	public List<AccVideoPath> findAccVideoPathList(String applyNo) {
		return super.dao.findAccVideoPathQueryList(applyNo);
	}
	
	public List<String> findPictureAddressQueryList(String applyNo) {
		return super.dao.findPictureAddressQueryList(applyNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveCreVideoPath(AccVideoPath creVideoPath) {
		super.dao.saveAccVideoPath(creVideoPath);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void deleteAccVideoPath(String applyNo){
		this.dao.deleteAccVideoPath(applyNo);
	}

	public Page<MyMap> findDoneProcessPage(Page<MyMap> page, List<MyMap> list) {
		Page<MyMap> result = page;
		if (page.getList() == null)
			page.setList(new ArrayList<MyMap>());
		else {
			page.getList().clear();
		}
		if (list.size() > 0) {
			result.setCount(12);
			result.setDefaultCount(false);
			result.getList().addAll(list);
		}
		return result;
	}
}