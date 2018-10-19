package com.resoft.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.mappingInfo.dao.MappingInfoDao;
import com.resoft.credit.mappingInfo.entity.MappingInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;

/**
 * 公共生成进件号和合同编号的Service
 * 
 * @author yanwanmei
 * @version 2016-03-02
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CreateNumberService extends CrudService<ContractDao, Contract> {

	private static final Logger logger = LoggerFactory.getLogger(CreateNumberService.class);
	@Autowired
	private ContractService contractService;
	@Autowired
	private MappingInfoDao mappingInfoDao;

	public Contract get(String id) {
		return super.get(id);
	}

	// 生成合同编号
	// loanKind:借款类型;operateOrgId:经办机构id;
	@Transactional(value = "CRE", readOnly = false)
	public List<String> createContractNo(String loanKind, String operateOrgId, List<Integer> flagList, String dateStr) {
		List<String> returnList = new ArrayList<String>();
		int flagFirst = 0;
		int flagMiddle = 0;
		int flagLast = 0;
		try {
			int flagRestrart = 0;
			// "G"表示冠群，J表示借款业务
			StringBuffer contractNo = new StringBuffer();
			contractNo.append("HG-GJ");

			// 借款类型：1.信用 2.抵押 3.债股结合 4.混合
			if (Constants.PRODUCT_TYPE_XY.equals(loanKind)) {
				contractNo.append("X");
			} else if (Constants.PRODUCT_TYPE_DY.equals(loanKind)) {
				contractNo.append("D");
			} else if (Constants.PRODUCT_TYPE_ZGJH.equals(loanKind)) {
				contractNo.append("Z");
			} else if (Constants.PRODUCT_TYPE_HH.equals(loanKind)) {
				contractNo.append("H");
			} else if (Constants.PRODUCT_TYPE_CG.equals(loanKind)) {
				contractNo.append("C");
			}else{
				contractNo.append("R");
			}
			
			// 添加分公司代码,需要根据经办机构id到sys_office中去查
			Map<String, Object> orgLevelMap = contractService.findLevelNumByOrgId(operateOrgId);
			contractNo.append(orgLevelMap.get("code"));

			// 添加当前日期
			String dateString = null;
			// 合同编号
			if (flagList.size() < 3) {
				dateString = DateUtils.formatDate(new Date(), "yyMMdd");
			} else {
				dateString = DateUtils.formatDate(new Date(), "yyMMddHHmmss");
			}
			contractNo.append(dateString);
			String currentDate = dateString.substring(0, 6);

			if (flagList.size() < 3) {
				flagFirst = (Integer) flagList.get(0);
				flagLast = (Integer) flagList.get(1);
				// 如果日期不一致，说明dateStr是昨天的日期，需要更改，标志位也需要重新跑
				if (!dateStr.equalsIgnoreCase(currentDate)) {
					dateStr = currentDate;
					flagFirst = 0;
					flagLast = 0;
				}

				// 根据flag添加序号（AA-ZZ）A:65
				char firstChar = 0;
				char lastChar = 0;
				firstChar = (char) (flagFirst + 65);
				lastChar = (char) (flagLast + 65);
				contractNo.append(firstChar);
				contractNo.append(lastChar);

				// 英文字母为26个，当flagLast大于25时，flagFirst进位
				flagLast++;
				if (flagLast > 25) {
					flagFirst++;
					flagLast = 0;
				}

				MappingInfo mappingInfo = new MappingInfo();
				mappingInfo.setId(operateOrgId);
				mappingInfo.setContractNoFirst(flagFirst + "");
				mappingInfo.setContractNoSecond(flagLast + "");
				mappingInfo.preUpdate();
				mappingInfoDao.update(mappingInfo);
			}

			if (flagList.size() == 3) {
				flagFirst = (Integer) flagList.get(0);
				flagMiddle = (Integer) flagList.get(1);
				flagLast = (Integer) flagList.get(2);
				// 如果日期不一致，说明dateStr是昨天的日期，需要更改，标志位也需要重新跑
				if (!dateStr.equals(currentDate)) {
					dateStr = currentDate;
					flagFirst = 0;
					flagMiddle = 0;
					flagLast = 0;
					flagRestrart = 1;// 重新计数
				}

				// 根据flag添加序号（AA-ZZ）A:65
				char firstChar = 0;
				char middleChar = 0;
				char lastChar = 0;
				firstChar = (char) (flagFirst + 65);
				middleChar = (char) (flagMiddle + 65);
				lastChar = (char) (flagLast + 65);
				contractNo.append(firstChar);
				contractNo.append(middleChar);
				contractNo.append(lastChar);
			}

			returnList.add(0, contractNo.toString());
			returnList.add(1, flagRestrart + "");
			returnList.add(2, dateStr);
			if (flagList.size() < 3) {
				returnList.add(3, flagFirst + "");
				returnList.add(4, flagLast + "");
			}
		} catch (Exception e) {
			logger.error("生成合同号或进件号失败", e);
		}

		return returnList;
	}

}
