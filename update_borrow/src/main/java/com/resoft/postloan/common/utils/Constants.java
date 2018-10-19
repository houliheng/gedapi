package com.resoft.postloan.common.utils;

public class Constants {
	/** 角色名称 */
	public static final String ROLE_NAME = "ROLE_NAME";
	/**工作流流程标识--抵押物处置 */
	public static final String WORKFLOW_COLLATERAL_DISPOSAL = "gqcc_collateral_disposal";
	/** 角色名称-借后日常检查人员 */
	public static final String ROLE_NAME_JHRCJCJL = "借后日常检查经理";
	/** 角色名称-借后日常检查人员 */
	public static final String ROLE_NAME_JHRCJCZY = "借后日常检查专员";
	/** 角色类型 */
	public static final String ROLE_TYPE = "ROLE_TYPE";
	/** 联系人 */
	public static final String ROLE_TYPE_CONTACT = "7";
	/** 担保企业 */
	public static final String ROLE_TYPE_DBQY = "6";
	/** 主借企业 */
	public static final String ROLE_TYPE_ZJQY = "5";
	/** 配偶 */
	public static final String ROLE_TYPE_MATE = "4";
	/** 担保人 */
	public static final String ROLE_TYPE_DBR = "3";
	/** 共借人 */
	public static final String ROLE_TYPE_GJR = "2";
	/** 主借人 */
	public static final String ROLE_TYPE_ZJR = "1";
	/** 25日复核-合规性 */
	public static final String ROLE_TYPE_HGX = "8";
	/** 日常检查后进入下一流程 */
	public static final String CHECK_DAILY_PROC = "CHECK_DAILY_PROC";
	/** 日常检查后进入下一流程 保存 */
	public static final String CHECK_DAILY_PROC_BC = "1";
	/** 日常检查后进入下一流程 催收中 */
	public static final String CHECK_DAILY_PROC_CSZ = "2";
	/** 日常检查后进入下一流程 催收结束 */
	public static final String CHECK_DAILY_PROC_CSJS = "12";
	/** 日常检查后进入下一流程 法务 */
	public static final String CHECK_DAILY_PROC_FW = "3";
	/** 日常检查后进入下一流程 展期申请中 */
	public static final String CHECK_DAILY_PROC_ZQSQZ = "4";
	/** 日常检查后进入下一流程 展期通过 */
	public static final String CHECK_DAILY_PROC_ZQTG = "5";
	/** 日常检查后进入下一流程 展期拒绝 */
	public static final String CHECK_DAILY_PROC_ZQJJ = "6";
	/** 日常检查后进入下一流程 借新还旧申请中 */
	public static final String CHECK_DAILY_PROC_JXHJSHZ = "7";
	/** 日常检查后进入下一流程 借新还旧通过 */
	public static final String CHECK_DAILY_PROC_JXHJTG = "8";
	/** 日常检查后进入下一流程 借新还旧拒绝 */
	public static final String CHECK_DAILY_PROC_JXHJJJ = "9";
	/** 日常检查后进入下一流程 特殊情况处理 */
	public static final String CHECK_DAILY_PROC_TSQKCL = "10";
	/** 日常检查后进入下一流程 签署保证合同 */
	public static final String CHECK_DAILY_PROC_QSBZHT = "11";
	/** 工作流流程标识--合同展期申请 */
	public static String WORKFLOW_CONTRACT_EXTEND = "gqch_contract_extend";
	/** 催收管理--催收方式--未执行过催收 */
	public static String DEBT_COLLECTION_NONE = "1";
	/** 催收管理--催收方式--电话催收 */
	public static String DEBT_COLLECTION_PHONE = "2";
	/** 催收管理--催收方式--上门催收 */
	public static String DEBT_COLLECTION_FACE = "3";
	/** 催收管理--催收方式--外包催收 */
	public static String DEBT_COLLECTION_OUT = "4";
	/** 催收管理--催收方式--法务催收 */
	public static String DEBT_COLLECTION_LEGAL = "5";
	/** 25日复核--菜单-借后专员待复核 */
	public static String CHECK_TWENTY_FIVE_TOCHECK = "1";
	/** 25日复核--菜单-借后专员已复核 */
	public static String CHECK_TWENTY_FIVE_HASCHECKED = "2";
	/** 25日复核--菜单-借后经理待办 */
	public static String CHECK_TWENTY_FIVE_TOALLOCATE = "3";
	/** 25日复核--菜单-借后经理已办 */
	public static String CHECK_TWENTY_FIVE_HASALLOCATE = "4";
	/** 25日复核--菜单-签署保证合同 */
	public static String HAS_SIGN_GUARANTY_CONTRACT = "5";
	/** 25日复核--菜单-待清收 */
	public static String TO_ACCOUNT_CLEAN = "7";
	/** 25日复核--菜单-已清收 */
	public static String HAS_ACCOUNT_CLEAN = "8";
	/** 25日复核--菜单-清收待分配 */
	public static String TO_ACCOUNT_CLEAN_ALLOCATE = "9";
	/** 25日复核--菜单-清收已分配 */
	public static String HAS_ACCOUNT_CLEAN_ALLOCATE = "10";
	/** 25日复核--查询当前机构及以下机构已复核数据 */
	public static String ALL_CHECK_TWENTY_FIVE_DATA = "11";
	/** 25日复核--查询当前机构及以下机构已复核数据 */
	public static String ALL_CHECK_SENVEN_ONE_DATA = "12";
	/** 待复核 */
	public static String TO_BE_ALLOCATED = "1";
	/** 已复核 */
	public static String HAS_BEEN_ALLOCATED = "2";
	/** 25日复核--已复核（签署保证合同） */
	public static String SIGN_GUARANTY_CONTRACT = "3";
	/** 25日复核--清收 */
	public static String ACCOUNT_CLEAN_APPROVE = "4";
	/** 25日复核--清收打回 */
	public static String ACCOUNT_CLEAN_BEAT_BACK = "6";
	/** 25日复核-清收-待审核 */
	public static String TO_BE_CHECKED = "0";
	/** 25日复核-清收-通过 */
	public static String PASS_THROUGH = "1";
	/** 25日复核-清收-打回 */
	public static String BEAT_BACK = "2";
	/** 25日复核--是否锁定 */
	public static String IS_LOCK_NO = "0";
	/** 25日复核--总部待审核标志 */
	public static String DQ_STATUS = "check";
	/** 25日复核--操作结果 */
	public static String DEDUCT_RESULT_PART = "1";
	/** 25日复核--清收结束 */
	public static String ACCOUNT_CLEAN_OVER = "3";
	/** 日常检查 待检查 */
	public static String ALLOCATE_TYPE_TODO = "1";
	/** 日常检查 已检查 */
	public static String ALLOCATE_TYPE_DONE = "2";
	/** 催收管理--催收任务状态--转办待审核 */
	public static String DEBT_COLLECTION_STATUS_ZBDSH = "0";
	/** 催收管理--催收任务状态--待分配 */
	public static String DEBT_COLLECTION_STATUS_DFP = "1";
	/** 催收管理--催收任务状态--已分配待催收 */
	public static String DEBT_COLLECTION_STATUS_YFPDCS = "2";
	/** 催收管理--催收任务状态--催收结束 */
	public static String DEBT_COLLECTION_STATUS_CSJS = "3";
	/** 催收管理--催收任务状态--催收结束(法务催收转清收) */
	public static String DEBT_COLLECTION_STATUS_FWZQS = "4";
	/** 催收管理--催收操作--转办 */
	public static String DEBT_COLLECTION__ZB = "turn";
	/** 催收管理--催收操作--设置风险级别 */
	public static String DEBT_COLLECTION__SZFXJB = "riskLelve";
	/** 催收管理--待分配来源--逾期自动 */
	public static String DEBT_COLLECTION__YQZD = "1";
	/** 催收管理--待分配来源--电话转办 */
	public static String DEBT_COLLECTION__DHZB = "2";
	/** 催收管理--待分配来源--上门转办 */
	public static String DEBT_COLLECTION__SMZB = "3";
	/** 催收管理--待分配来源--外包转办 */
	public static String DEBT_COLLECTION__WBZB = "4";
	/** 催收管理--待分配来源--日常检查 */
	public static String DEBT_COLLECTION__RCJC = "5";
	/** 催收管理--待分配来源--25日复核 */
	public static String DEBT_COLLECTION__25FH = "6";
	/** 催收管理--转办通过 */
	public static String DEBT_COLLECTION_TURN_SUCCESS = "1";
	/** 催收管理--转办打回 */
	public static String DEBT_COLLECTION_TURN_FAIL = "2";
	/** 签署保证合同--保证合同类型 */
	public static String CONTRACT_TEMPLATE_TYPE = "22";

	/** 借后-借新还旧标记 */
	public static final String PL_BORROW_NEW_FLAG = "PL_BORROW_NEW_FLAG";
	/** 借后-借新还旧标记-是 */
	public static final String PL_BORROW_NEW_FLAG_Y = "1";
	/** 借后-借新还旧标记-不是 */
	public static final String PL_BORROW_NEW_FLAG_N = "0";

	/** 登记保存 */
	public static final String APPLY_STATUS_SAVE = "1";
	/** 提交审批中 */
	public static final String APPLY_STATUS_SUBMIT = "2";
	/** 拒绝 */
	public static final String APPLY_STATUS_REFUSED = "3";
	/** 放款成功 */
	public static final String APPLY_STATUS_FKCG = "4";
	/** 提前还款 */
	public static final String APPLY_STATUS_TQHK = "5";
	/** 正常结清 */
	public static final String APPLY_STATUS_ZCJQ = "6";
	/** 逾期 */
	public static final String APPLY_STATUS_YQ = "7";
	/** 展期 */
	public static final String APPLY_STATUS_ZQ = "8";
	/** 坏账 */
	public static final String APPLY_STATUS_HZ = "9";
	/** 流标 */
	public static final String APPLY_STATUS_LB = "10";
	/** 借新还旧登记保存 */
	public static final String APPLY_STATUS_JXHJDJBC = "11";

	/** 已绑定 */
	public static final String BIND_STATUS_YBD = "1";
	/** 未绑定 */
	public static final String BIND_STATUS_WBD = "0";
	/** 合同展期--待申请 */
	public static String EXTEND_APPLY_DSQ = "0";
	/** 合同展期--已申请 */
	public static String EXTEND_APPLY_YSQ = "1";
	/** 合同展期--申请状态--保存未提交 */
	public static String EXTEND_APPLY_STATUS_BCWFJ = "1";
	/** 合同展期--申请状态--提交待审批 */
	public static String EXTEND_APPLY_STATUS_TJDSP = "2";
	/** 合同展期--申请状态--审核通过 */
	public static String EXTEND_APPLY_STATUS_SHTG = "3";
	/** 合同展期--申请状态--审核拒绝 */
	public static String EXTEND_APPLY_STATUS_SHJJ = "4";
	/** 合同展期审批-- 总部数据中心确认 */
	public static String EXTEND_APPROVE_ZBSJZXQR = "utask_zbsjzxqr";

	/** 借新还旧申请状态 */
	public static String BORROW_NEW_STATUS = "BORROW_NEW_STATUS";
	/** 借新还旧申请状态-提交待审核 */
	public static String BORROW_NEW_STATUS_TOCHECK = "0";
	/** 借新还旧申请状态-大区审核通过 */
	public static String BORROW_NEW_STATUS_DQSHTG = "1";
	/** 借新还旧申请状态-大区审核打回 */
	public static String BORROW_NEW_STATUS_DQSHDH = "2";
	/** 借新还旧申请状态-总部审核通过 */
	public static String BORROW_NEW_STATUS_ZBSHTG = "3";
	/** 借新还旧申请状态-总部审核打回 */
	public static String BORROW_NEW_STATUS_ZBSHDH = "4";

	/** 借新还旧申请结果 */
	public static String BORROW_NEW_RESULT = "BORROW_NEW_RESULT";
	/** 借新还旧申请结果-审核通过 */
	public static String BORROW_NEW_RESULT_PASS = "1";
	/** 借新还旧申请结果-审核打回 */
	public static String BORROW_NEW_RESULT_REFUSE = "2";
	/** 催收管理--法务催收--全部回款 */
	public static String DEBT_COLLECTION_LEGAL_RESULT_QBHK = "1";
	/** 催收管理--法务催收--核销 */
	public static String DEBT_COLLECTION_LEGAL_RESULT_HX = "2";
	/** 核销--核销状态--未申请 */
	public static String CHECK_REMOVE_STATUS_WSQ = "0";
	/** 核销--核销状态--申请未审核 */
	public static String CHECK_REMOVE_STATUS_SQWSH = "1";
	/** 核销--核销状态--大区审核通过 */
	public static String CHECK_REMOVE_STATUS_DQSHTG = "2";
	/** 核销--核销状态--大区审核打回 */
	public static String CHECK_REMOVE_STATUS_DQSHDH = "3";
	/** 核销--核销状态--总部审核通过 */
	public static String CHECK_REMOVE_STATUS_ZBSHTG = "4";
	/** 核销--核销状态--总部审核打回 */
	public static String CHECK_REMOVE_STATUS_ZBSHDH = "5";
	/** 核销--核销结果--审核通过 */
	public static String CHECK_REMOVE_RESULT_SHTG = "yes";
	/** 核销--核销结果--审核打回 */
	public static String CHECK_REMOVE_RESULT_SHDH = "no";

	/** 账务系统合同还款执行统计表中合同状态 */
	public static String CONTRACT_STATE = "CONTRACT_STATE";
	/** 账务系统合同还款执行统计表中合同状态-预约 */
	public static String CONTRACT_STATE_YY = "0100";
	/** 账务系统合同还款执行统计表中合同状态-面签 */
	public static String CONTRACT_STATE_MQ = "0200";
	/** 账务系统合同还款执行统计表中合同状态-签约完成 */
	public static String CONTRACT_STATE_QYWC = "0300";
	/** 账务系统合同还款执行统计表中合同状态-作废 */
	public static String CONTRACT_STATE_ZF = "0400";
	/** 账务系统合同还款执行统计表中合同状态-放款成功 */
	public static String CONTRACT_STATE_FKCG = "0500";
	/** 账务系统合同还款执行统计表中合同状态-无逾期-还款中 */
	public static String CONTRACT_STATE_WYQ_HKZ = "0600";
	/** 账务系统合同还款执行统计表中合同状态-无逾期-已还完 */
	public static String CONTRACT_STATE_WYQ_YHW = "0700";
	/** 账务系统合同还款执行统计表中合同状态-有逾期-还款中 */
	public static String CONTRACT_STATE_YYQ_HKZ = "0800";
	/** 账务系统合同还款执行统计表中合同状态-有逾期-已还完 */
	public static String CONTRACT_STATE_YYQ_YHW = "0900";
	/** 账务系统合同还款执行统计表中合同状态-有逾期-提前还款 */
	public static String CONTRACT_STATE_YYQ_TQHK = "1000";
	/** 账务系统合同还款执行统计表中合同状态-无逾期-提前还款 */
	public static String CONTRACT_STATE_WYQ_TQHK = "1100";
	/** 账务系统合同还款执行统计表中合同状态-清收未结清 */
	public static String CONTRACT_STATE_QSWJQ = "1200";
	/** 账务系统合同还款执行统计表中合同状态-异常终止-法务 */
	public static String CONTRACT_STATE_YCZZ_FW = "1300";
	/** 账务系统合同还款执行统计表中合同状态-清收已结清 */
	public static String CONTRACT_STATE_QSYJQ = "1400";
	/** 账务系统合同还款执行统计表中合同状态-异常终止-核销 */
	public static String CONTRACT_STATE_YCZZ_HX = "1500";
	/** 25日复核--清收未结清 */
	public static String CONTRACT_ACCOUNT_CLEAN = "1200";
	/** 25日复核--清收已结清 */
	public static String CONTRACT_ACCOUNT_ALL_CLEAN = "1400";

	/** 核销--总部审批 */
	public static String CHECK_REMOVE_ZBSP = "zb";
	/** 核销--大区审批 */
	public static String CHECK_REMOVE_DQSP = "dq";
	
	/**
	 * 系统管理员id
	 */
	public static String ADMIN_USER_ID = "1";

}
