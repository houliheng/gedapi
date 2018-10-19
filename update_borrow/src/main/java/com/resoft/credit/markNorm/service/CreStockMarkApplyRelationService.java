package com.resoft.credit.markNorm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.markNorm.dao.CreStockMarkApplyRelationDao;
import com.resoft.credit.markNorm.entity.CreStockMarkApplyRelation;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 股权信息加减分关系Service
 * @author zcl
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class CreStockMarkApplyRelationService extends CrudService<CreStockMarkApplyRelationDao, CreStockMarkApplyRelation> {
	public CreStockMarkApplyRelation get(String id) {
		return super.get(id);
	}
	
	public List<CreStockMarkApplyRelation> findList(CreStockMarkApplyRelation creStockMarkApplyRelation) {
		return super.findList(creStockMarkApplyRelation);
	}
	
	public Page<CreStockMarkApplyRelation> findPage(Page<CreStockMarkApplyRelation> page, CreStockMarkApplyRelation creStockMarkApplyRelation) {
		return super.findPage(page, creStockMarkApplyRelation);
	}
	
	/**
	 * 通过申请编号，验证加减分项是否填全
	 * @param applyNo
	 * @return Map<String,String> key:isOk   value:fail或success    key:message   value:描述
	 */
	public Map<String,String> verifyMarkIsCompleteByApplyNo(String applyNo){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(applyNo)){
			List<CreStockMarkApplyRelation> addList = this.dao.findRelationByApplyNo(applyNo,Constants.stock_mark_type_add);
			if(addList.size()==0){
				map.put("isOk", "fail");
				map.put("message", "加分项没有填写!");
				return map;
			}
			List<CreStockMarkApplyRelation> minusList = this.dao.findRelationByApplyNo(applyNo,Constants.stock_mark_type_minus);
			if(minusList.size()==0){
				map.put("isOk", "fail");
				map.put("message", "减分项没有填写!");
				return map;
			}
		}else{
			map.put("isOk", "fail");
			map.put("message", "申请编号为空!");
			return map;
		}
		map.put("isOk", "success");
		return map;
	}
	
	@Transactional(readOnly = false)
	public void save(CreStockMarkApplyRelation creStockMarkApplyRelation) {
		super.save(creStockMarkApplyRelation);
	}
	
	@Transactional(readOnly = false)
	public void updateNewRelation(String applyNo,String markType, List<CreStockMarkApplyRelation> list) {
		this.dao.deleteRelationByApplyNo(applyNo,markType);
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CreStockMarkApplyRelation creStockMarkApplyRelation) {
		super.delete(creStockMarkApplyRelation);
	}
	
}