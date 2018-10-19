package com.resoft.credit.GedImportGedOrder.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;

import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.common.utils.Constants;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.GedImportGedOrder.entity.CreGedImportGetOrder;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.GedImportGedOrder.dao.CreGedImportGetOrderDao;





/**
 * 冠e通线下数据导入给冠e贷Service
 * @author lb
 * @version 2018-07-18
 */
@Service
@Transactional(readOnly = true)
public class CreGedImportGetOrderService extends CrudService<CreGedImportGetOrderDao, CreGedImportGetOrder> {

	private Logger logger = Logger.getLogger(CreGedImportGetOrderService.class);

	@Autowired
	private AccContractDao accContractDao;
	@Autowired
	private ApplyRegisterService applyRegisterService;

	public CreGedImportGetOrder get(String id) {
		return super.get(id);
	}

	public List<CreGedImportGetOrder> findList(CreGedImportGetOrder creGedImportGetOrder) {
		return super.findList(creGedImportGetOrder);
	}

	public Page<CreGedImportGetOrder> findPage(Page<CreGedImportGetOrder> page, CreGedImportGetOrder creGedImportGetOrder) {
		return super.findPage(page, creGedImportGetOrder);
	}

	@Transactional(readOnly = false)
	public void save(CreGedImportGetOrder creGedImportGetOrder) {
		super.save(creGedImportGetOrder);
	}

	@Transactional(readOnly = false)
	public void delete(CreGedImportGetOrder creGedImportGetOrder) {
		super.delete(creGedImportGetOrder);
	}

	/**
	 * 合同是否导入
	 *
	 * @param order
	 * @return
	 */
	public Boolean dealContractData(CreGedImportGetOrder order) {

		Boolean flag = true;
		CreGedImportGetOrder getOrder = findCreGedImportByContractNo(order.getContractNo());
		if (null != getOrder) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据合同号查询合同数据
	 *
	 * @return
	 */
	public CreGedImportGetOrder findCreGedImportByContractNo(String contractNo) {
		return this.dao.findCreGedImportByContractNo(contractNo);
	}

	/**
	 * 插入数据
	 *
	 * @param
	 * @return
	 */
	@Transactional(value = "ACC", readOnly = false)
	public void importContract(CreGedImportGetOrder getOrder) {
		getOrder.setId(AccFinancialPlatformUtils.makeSeqNo());
		this.dao.importContract(getOrder);
	}

	/**
	 * 修改数据
	 *
	 * @param
	 * @return
	 */
	@Transactional(value = "ACC", readOnly = false)
	public void updateImportedContract(CreGedImportGetOrder getOrder) {
		this.dao.updateImportedContract(getOrder);
	}


	/**
	 * 导入线下合同信息
	 * @param orderList
	 * @return
	 */
	@Transactional(value = "ACC", readOnly = false)
	public boolean importCregedOrder(List<CreGedImportGetOrder> orderList){
    	boolean orderFlag = true;
    	if(orderList != null && orderList.size()>0){
    		for(int i = 0;i<orderList.size();i++){
    			 CreGedImportGetOrder order = orderList.get(i);
    			 try {
					 boolean flag = dealContractData(order);
					 if (flag) {
						 this.dao.insert(order);
						 ApplyRegister applyRegisterQuery = applyRegisterService.getApplyRegisterByAccontractNo(order.getContractNo());
							if (applyRegisterQuery == null) {
								ApplyRegister applyRegister = new ApplyRegister();
								applyRegister.setApplyNo(order.getOrderCode());
								applyRegister.setSendGED("1");
								applyRegister.setApplyStatus("4");
								applyRegister.setProcDefKey(Constants.PROC_DEF_KEY_GR);
								applyRegisterService.save(applyRegister);
							}
						 //根据合同号增加申请编号
						 String contractNo = order.getContractNo();
						 String orderNo = order.getOrderCode();
						 Map<String,Object> paramMap = new HashMap<>();
						 paramMap.put("applyNo",orderNo);
						 paramMap.put("contractNo",contractNo);
						 accContractDao.updateContactByContractNo(paramMap);
					 }
				 }catch (Exception e){
    			 	e.printStackTrace();
    			 	 orderFlag = false;
				 }
			}
		}
    	return orderFlag;
	}


}