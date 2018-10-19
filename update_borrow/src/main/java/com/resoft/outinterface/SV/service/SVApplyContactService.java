package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVApplyContactDao;
import com.resoft.outinterface.SV.server.entry.request.SVApplyContact;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * sv回盘联系人Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVApplyContactService {

	@Autowired
	private SVApplyContactDao svApplyContactDao;

	public SVApplyContact get(String id) {
		return svApplyContactDao.get(id);
	}

	public List<SVApplyContact> findList(SVApplyContact sVApplyContact) {
		return svApplyContactDao.findList(sVApplyContact);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVApplyContact> contactList) {
		baseInfo.put("list", contactList);
		svApplyContactDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVApplyContact sVApplyContact) {
		svApplyContactDao.delete(sVApplyContact);
	}

}