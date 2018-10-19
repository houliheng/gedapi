package com.resoft.accounting.common.utils;

public class Constants {
	
	/**工作流流程标识--违约金罚息减免*/
	public static String WORKFLOW_APPLY_MARGIN_REPAY  = "gqcc_penalty_fine_exempt";
	/**工作流流程标识--保证金退还*/
	public static String WORKFLOW_MARGIN_REPAY  = "gqch_margin_repay";
	/**工作流流程标识--全款提前还款*/
	public static String WORKFLOW_ADVANCE_REPAY  = "gqch_advance_repay";
	/**保证金退还--退还状态*/
	public static String MARGIN_AMOUNT_STATUS = "MARGIN_AMOUNT_STATUS";
	/**银行卡变更--申请流程状态*/
	public static String FLOW_STATE = "FLOW_STATE";
	/**合同状态*/
	public static String CONTRACT_STATE = "CONTRACT_STATE";
	/**期状态*/
	public static String PERIOD_STATE = "PERIOD_STATE";
	/**期状态--逾期结清*/
	public static String PERIOD_STATE_YQJQ = "0100";
	/**期状态--待还款*/
	public static String PERIOD_STATE_DHK = "0200";
	/**期状态--逾期*/
	public static String PERIOD_STATE_YQ = "0300";
	/**期状态--正常结清*/
	public static String PERIOD_STATE_ZCJQ = "0400";
	/**期状态--提前还款*/
	public static String PERIOD_STATE_TQHK = "0500";
	/**期状态--提前结清*/
	public static String PERIOD_STATE_TQJQ = "0600";
	/**银行卡变更--申请流程状态--申请中*/
	public static String FLOW_STATE_SQZ = "2";
	/**银行卡变更--申请流程状态--审核通过*/
	public static String FLOW_STATE_SHTG = "4";
	/**银行卡变更--申请流程状态--审核未通过*/
	public static String FLOW_STATE_SHWTG= "5";
	/**保证金退还--退还状态--未申请*/
	public static String MARGIN_AMOUNT_STATUS_WSQ = "1";
	/**保证金退还--退还状态--申请中*/
	public static String MARGIN_AMOUNT_STATUS_SQZ = "2";
	/**保证金退还--退还状态--退还中*/
	public static String MARGIN_AMOUNT_STATUS_THZ = "3";
	/**保证金退还--退还状态--已退还*/
	public static String MARGIN_AMOUNT_STATUS_YTH = "4";
	/**保证金退还--退还状态--放弃*/
	public static String MARGIN_AMOUNT_STATUS_FQ = "5";
	/**保证金退还--申请*/
	public static String MARGIN_AMOUNT_SQ = "1";
	/**保证金退还--结算中心*/
	public static String MARGIN_AMOUNT_JSZXSH = "2";
	/**保证金退还--分公司*/
	public static String MARGIN_AMOUNT_FGSQR = "3";
	/**违约金减免状态--申请中*/
	public static String APPLY_STATUS_SQ = "0";
	/**违约金减免状态--审核通过*/
	public static String APPLY_STATUS_SHTG = "1";
	/**违约金减免状态--审核打回*/
	public static String APPLY_STATUS_SHDH = "2";
	/**机构层级--大区*/
	public static String OFFICE_LEVEL_DQ = "1";
	/**机构层级--区域*/
	public static String OFFICE_LEVEL_QY = "2";
	/**机构层级--门店*/
	public static String OFFICE_LEVEL_MD = "3";
	/**合同锁标记--账务调整*/
	public static String CONTRACT_LOCK_FLAG_ZWTZ = "1";
	/**合同锁标记--重新匹配*/
	public static String CONTRACT_LOCK_FLAG_CXPP = "2";
	/**合同锁标记--冲正流水*/
	public static String CONTRACT_LOCK_FLAG_CZLS = "3";
	/**提前还款--还款状态--已申请待批复*/
	public static String ADVANCE_DEDUCT_STATUS_SQDPF = "1";
	/**提前还款--还款状态--已批复待确认*/
	public static String ADVANCE_DEDUCT_STATUS_PFDQR = "2";
	/**提前还款--还款状态--已划扣待调账*/
	public static String ADVANCE_DEDUCT_STATUS_HKDTZ = "3";
	/**提前还款--还款状态--调账完成*/
	public static String ADVANCE_DEDUCT_STATUS_TZWC = "4";
	/**提前还款--还款状态--放弃*/
	public static String ADVANCE_DEDUCT_STATUS_FQ = "5";
	/**提前还款--工作流节点--结算中心审核*/
	public static String ADVANCE_DEDUCT_UTASK_JSZXSH = "utask_jszxsh";
	/**提前还款--工作流节点--分公司确认*/
	public static String ADVANCE_DEDUCT_UTASK_FGSQR = "utask_fgsqr";
	/**提前还款--工作流节点--结算中心确认*/
	public static String ADVANCE_DEDUCT_UTASK_JSZXQR = "utask_jszxqr";
	/**划扣类型--直接划扣*/
	public static String DEDUCT_TYPE_ZJHK = "10180001";
	/**划扣类型--代划扣*/
	public static String DEDUCT_TYPE_DHK = "10180002";
	//查账--大区人员申请查账
	public static String CHECK_SQCZ = "check_sqcz";
	//查账--确认匹配入账
	public static String CHECK_QRPPRZ = "check_qrpprz";
	//查账--划拨成功
	public static String CHECK_HBCG = "check_hbcg";
}
