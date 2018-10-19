package com.resoft.credit.applyRegister.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRegister.dao.RelatedPieceDao;
import com.resoft.credit.applyRegister.entity.RelatedPiece;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 关联进件Service
 * 
 * @author wangguodong
 * @version 2016-03-24
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class RelatedPieceService extends CrudService<RelatedPieceDao, RelatedPiece> {

	
	public RelatedPiece get(String id) {
		return super.get(id);
	}

	public List<RelatedPiece> findList(RelatedPiece RelatedPiece) {
		return super.findList(RelatedPiece);
	}

	public Page<RelatedPiece> findPage(Page<RelatedPiece> page,
			RelatedPiece RelatedPiece) {
		return super.findPage(page, RelatedPiece);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(RelatedPiece RelatedPiece) {
		super.save(RelatedPiece);
	}


}
