package com.resoft.credit.creditAndLine.service.creditLineBank;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.creditAndLine.dao.creditLineBank.CreditLineBankDetailDao;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBankDetail;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 征信银行卡流水明细Service
 * 
 * @author wuxi01
 * @version 2016-02-27
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CreditLineBankDetailService extends CrudService<CreditLineBankDetailDao, CreditLineBankDetail> {

	private static final Logger logger = LoggerFactory.getLogger(CreditLineBankDetailService.class);

	@Autowired
	private CustInfoService custInfoService;

	public CreditLineBankDetail get(String id) {
		CreditLineBankDetail creditLineBankDetail = super.get(id);
		return creditLineBankDetail;
	}

	public List<CreditLineBankDetail> findList(CreditLineBankDetail creditLineBankDetail) {
		return super.findList(creditLineBankDetail);
	}

	public Page<CreditLineBankDetail> findPage(Page<CreditLineBankDetail> page, CreditLineBankDetail creditLineBankDetail) {
		return super.findPage(page, creditLineBankDetail);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(CreditLineBankDetail creditLineBankDetail) {
		super.save(creditLineBankDetail);
	}

	/**
	 * 保存银行卡流水明细 同时对银行卡流水表中的总额、平均额进行更新
	 * 
	 * @param creditLineBankDetail
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveDetail(CreditLineBankDetail creditLineBankDetail) throws Exception {
		super.save(creditLineBankDetail);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(CreditLineBankDetail creditLineBankDetail) {
		super.delete(creditLineBankDetail);
	}

	/**
	 * 根据银行卡ID计算得到进项总额、出项总额、月均有效流水
	 * 
	 * @param Map
	 *            <String, String>
	 * @return Map<String, String>
	 */
	public Map<String, String> getAvgAndSum(Map<String, String> param) {
		return super.dao.getAvgAndSum(param);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void banchDelete(String ids) {
		if (ids != null) {
			String idArray[] = ids.split(",");
			List<String> idList = Arrays.asList(idArray);
			Map<String, Object> param = new HashMap<String, Object>();
			if (idList != null && idList.size() > 0) {
				param.put("idList", idList);
				super.dao.banchDelete(param);
			}

		}
	}

	/**
	 * 根绝银行卡ID，流水月份查询流水信息
	 * 
	 * @param params
	 * @return
	 */
	public List<CreditLineBankDetail> findLineBankDetailByLineMonth(Map<String, String> params) {
		return super.dao.findLineBankDetailByLineMonth(params);
	}

	/**
	 * 查询单张银行卡流水是否满足要求
	 * 
	 * @param params
	 * @param ajaxView
	 * @return
	 */
	public boolean isBankDetailCompleted(Map<String, String> params, AjaxView ajaxView) {
		try {
			String applyNo = params.get("applyNo");
			String minNum = "";
			try {
				minNum = DictUtils.getDictList("CREDIT_LINE_BANK_DETAIL_NUM").get(0).getValue();
			} catch (Exception e) {
				logger.error("查询字典表失败！", e);
			}
			params.put("minNum", minNum);
			Integer minNumInt = Integer.parseInt(minNum);
			// 常量List，存入连续的6-1个1
			String defaultValueList = "";
			for (int i = 0; i < minNumInt - 1; i++) {
				defaultValueList += "1,";
			}
			// 主借人
			List<CustInfo> mainCust = custInfoService.findMainBorrowerByApplyNo(applyNo);
			if (mainCust == null || mainCust.size() != 1 || mainCust.get(0) == null) {
				ajaxView.setFailed().setMessage("校验失败，原因为主借人信息数据错误，请联系管理员！");
				return false;
			}
			String custId = mainCust.get(0).getId();
			params.put("custId", custId);
			List<String> lineBankList = super.dao.findLineMonthList(params);
			if (lineBankList == null || lineBankList.size() == 0) {
				ajaxView.setFailed().setMessage("征信及流水页面，主借人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
				return false;
			}
			for (int i = 0; i < lineBankList.size(); i++) {
				String diffValueList = "";
				List<String> lineMonthList = Arrays.asList(lineBankList.get(i).split(","));
				Collections.sort(lineMonthList);
				if (lineMonthList == null || lineMonthList.size() < minNumInt) {
					ajaxView.setFailed().setMessage("征信及流水页面，主借人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
					return false;
				}
				for (int j = 0; j < lineMonthList.size() - 1; j++) {
					diffValueList += DateUtils.getMonthsBetween(lineMonthList.get(j + 1) + "01", lineMonthList.get(j) + "01") + ",";
				}
				if (diffValueList.contains(defaultValueList)) {
					break;
				}
				if (i == lineBankList.size() - 1 && !diffValueList.contains(defaultValueList)) {
					ajaxView.setFailed().setMessage("征信及流水页面，主借人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
					return false;
				}
			}

			// 配偶
			/*ApplyRelation mateRelation = new ApplyRelation();
			mateRelation.setApplyNo(applyNo);
			mateRelation.setRoleType(Constants.ROLE_TYPE_MATE);
			List<ApplyRelation> mateRelationList = applyRelationService.findList(mateRelation);
			if (mateRelationList != null && mateRelationList.size() == 1 && mateRelationList.get(0) != null) {
				// 配偶存在情况下，校验配偶银行卡信息
				params.put("custId", mateRelationList.get(0).getCustId());
				List<String> mateLineBankList = super.dao.findLineMonthList(params);
				if (mateLineBankList == null || mateLineBankList.size() == 0) {
					ajaxView.setFailed().setMessage("征信及流水页面，主借人配偶单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
					return false;
				}
				for (int i = 0; i < mateLineBankList.size(); i++) {
					String diffValueList = "";
					List<String> lineMonthList = Arrays.asList(mateLineBankList.get(i).split(","));
					Collections.sort(lineMonthList);
					if (lineMonthList == null || lineMonthList.size() < minNumInt) {
						ajaxView.setFailed().setMessage("征信及流水页面，主借人配偶单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
						return false;
					}
					for (int j = 0; j < lineMonthList.size() - 1; j++) {
						diffValueList += DateUtils.getMonthsBetween(lineMonthList.get(j + 1) + "01", lineMonthList.get(j) + "01") + ",";
					}
					if (diffValueList.contains(defaultValueList)) {
						break;
					}
					if (i == lineBankList.size() - 1 && !diffValueList.contains(defaultValueList)) {
						ajaxView.setFailed().setMessage("征信及流水页面，主借人配偶单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
						return false;
					}
				}
			}
			// 担保人
			ApplyRelation gurranRelation = new ApplyRelation();
			gurranRelation.setApplyNo(applyNo);
			gurranRelation.setRoleType(Constants.ROLE_TYPE_DBR);
			List<ApplyRelation> gurranRelationList = applyRelationService.findList(gurranRelation);
			if (gurranRelationList != null && gurranRelationList.size() > 0) {
				// 担保人存在情况下，校验担保人银行卡信息
				for (int i = 0; i < gurranRelationList.size(); i++) {
					if (gurranRelationList.get(i) != null) {
						params.put("custId", gurranRelationList.get(i).getCustId());
						List<String> gurrLineBankList = super.dao.findLineMonthList(params);
						if (gurrLineBankList == null || gurrLineBankList.size() == 0) {
							ajaxView.setFailed().setMessage("征信及流水页面，每个担保人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
							return false;
						}
						for (int k = 0; k < gurrLineBankList.size(); k++) {
							String diffValueList = "";
							List<String> lineMonthList = Arrays.asList(gurrLineBankList.get(k).split(","));
							Collections.sort(lineMonthList);
							if (lineMonthList == null || lineMonthList.size() < minNumInt) {
								ajaxView.setFailed().setMessage("征信及流水页面，每个担保人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
								return false;
							}
							for (int j = 0; j < lineMonthList.size() - 1; j++) {
								diffValueList += DateUtils.getMonthsBetween(lineMonthList.get(j + 1) + "01", lineMonthList.get(j) + "01") + ",";
							}
							if (diffValueList.contains(defaultValueList)) {
								break;
							}
							if (i == lineBankList.size() - 1 && !diffValueList.contains(defaultValueList)) {
								ajaxView.setFailed().setMessage("征信及流水页面，每个担保人单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
								return false;
							}
						}
					}
				}
			}

			// 主借企业
			ApplyRelation comRelation = new ApplyRelation();
			comRelation.setApplyNo(applyNo);
			comRelation.setRoleType(Constants.ROLE_TYPE_ZJQY);
			List<ApplyRelation> comRelationList = applyRelationService.findList(comRelation);
			if (comRelationList != null && comRelationList.size() == 1) {
				// 主借企业存在情况下，校验主借企业银行卡信息
				params.put("custId", comRelationList.get(0).getCustId());
				List<String> comLineBankList = super.dao.findLineMonthList(params);
				if (comLineBankList == null || comLineBankList.size() == 0) {
					ajaxView.setFailed().setMessage("征信及流水页面，主借人企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
					return false;
				}
				for (int i = 0; i < comLineBankList.size(); i++) {
					String diffValueList = "";
					List<String> lineMonthList = Arrays.asList(comLineBankList.get(i).split(","));
					Collections.sort(lineMonthList);
					if (lineMonthList == null || lineMonthList.size() < minNumInt) {
						ajaxView.setFailed().setMessage("征信及流水页面，主借人企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
						return false;
					}
					for (int j = 0; j < lineMonthList.size() - 1; j++) {
						diffValueList += DateUtils.getMonthsBetween(lineMonthList.get(j + 1) + "01", lineMonthList.get(j) + "01") + ",";
					}
					if (diffValueList.contains(defaultValueList)) {
						break;
					}
					if (i == lineBankList.size() - 1 && !diffValueList.contains(defaultValueList)) {
						ajaxView.setFailed().setMessage("征信及流水页面，主借人企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
						return false;
					}
				}
			}

			// 担保企业
			ApplyRelation dbqyRelation = new ApplyRelation();
			dbqyRelation.setApplyNo(applyNo);
			dbqyRelation.setRoleType(Constants.ROLE_TYPE_DBQY);
			List<ApplyRelation> dbqyRelationList = applyRelationService.findList(dbqyRelation);
			if (dbqyRelationList != null && dbqyRelationList.size() > 0) {
				// 担保企业存在情况下，校验担保企业银行卡信息
				for (int i = 0; i < dbqyRelationList.size(); i++) {
					if (dbqyRelationList.get(i) != null) {
						params.put("custId", dbqyRelationList.get(i).getCustId());
						List<String> gurrComLineBankList = super.dao.findLineMonthList(params);
						if (gurrComLineBankList == null || gurrComLineBankList.size() == 0) {
							ajaxView.setFailed().setMessage("征信及流水页面，每个担保企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
							return false;
						}
						for (int k = 0; k < gurrComLineBankList.size(); k++) {
							String diffValueList = "";
							List<String> lineMonthList = Arrays.asList(gurrComLineBankList.get(k).split(","));
							Collections.sort(lineMonthList);
							if (lineMonthList == null || lineMonthList.size() < minNumInt) {
								ajaxView.setFailed().setMessage("征信及流水页面，每个担保企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
								return false;
							}
							for (int j = 0; j < lineMonthList.size() - 1; j++) {
								diffValueList += DateUtils.getMonthsBetween(lineMonthList.get(j + 1) + "01", lineMonthList.get(j) + "01") + ",";
							}
							if (diffValueList.contains(defaultValueList)) {
								break;
							}
							if (i == lineBankList.size() - 1 && !diffValueList.contains(defaultValueList)) {
								ajaxView.setFailed().setMessage("征信及流水页面，每个担保企业单个银行卡流水明细需为连续" + minNum + "个月的明细，请完善后再进行操作！");
								return false;
							}
						}
					}
				}
			}*/

			return true;
		} catch (NumberFormatException e) {
			logger.error("校验失败！", e);
			ajaxView.setFailed().setMessage("校验银行卡明细出现异常，请联系管理员！");
			return false;
		}
	};
}