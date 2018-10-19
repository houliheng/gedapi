package com.resoft.credit.compenSatoryList.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.compenSatoryList.dao.CompenSatoryListDao;
import com.resoft.credit.compenSatoryList.entity.CompenSatoryList;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class CompenSatoryListService extends CrudService<CompenSatoryListDao, CompenSatoryList> {
	
	public CompenSatoryList get(String id) {
		return super.get(id);
	}
	
	public List<CompenSatoryList> findList(CompenSatoryList compenSatoryList) {
		return super.findList(compenSatoryList);
	}
	
	public Page<CompenSatoryList> findPage(Page<CompenSatoryList> page, CompenSatoryList compenSatoryList) {
		return super.findPage(page, compenSatoryList);
	}
	
	@Transactional(readOnly = false)
	public void save(CompenSatoryList compenSatoryList) {
		super.save(compenSatoryList);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompenSatoryList compenSatoryList) {
		super.delete(compenSatoryList);
	}

	public  CompenSatoryList  findsumRemainAmount(CompenSatoryList compenSatoryList){

		return super.dao.findsumRemainAmount(compenSatoryList);
	}


}
