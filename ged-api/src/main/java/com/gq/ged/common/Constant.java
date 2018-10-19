package com.gq.ged.common;

public class Constant {
  /**
   * 还款（充值）期数
   */
  public static final  String REPEYMENT_PERIOD = "repayment_period";

  /**
   * cookie-->key
   */
  public static final String CURR_USER_INFO_TOKEN = "ticketCookie";


  /**
   * redis system_area 系统地区表
   */
  public static final String SESSION_LOCAL_SYSTEM_AREA_KEY = "redis:local:system_area:";

  /**
   * redis repayment_contractno_period 还款合同号、还款期数
   */
  public static final String REPAYMENT_CONTRACTNO_PERIOD  = "repayment_contractno_period";


  /**
   * redis repayment_amount 用户还款金额
   */
  public static final String REPAYMENT_AMOUNT  = "repayment_amount";

  /**
   * 通用0
   */
  public static final byte COMMONE_ZERO = 0;

  /**
   * 通用1
   */
  public static final byte COMMONE_ONE = 1;

  /**
   * 手机验证码前缀
   */
  public static final String VALID_MSG = "VALID_MSG:PRIFIX";
  /**
   * 密码错误
   */
  public static final String LOGIN_ERROR = "LOGIN:ERROR:";
  /**
   * 图形验证码
   */
  public static final String PICTURE_CODE = "PICTURE_CODE:";
  public static final String PAYMENT = "PAYMENT:";


  public static final String MOVIES_TO_IMG_1 = "?vframe/jpg/offset/1";

  public static final  String OVDUE_MANAGE_DAYS = "7";

  public static  final  String REPAY_FAIL_MSG="网络异常，请稍后重试。客服热线：400-116-5088";

  //第三方API方法名称
  public static final  String R360_API_OPERATOR_SOURCE_DATA = "wd.api.mobilephone.getdatav2";
  public static final  String R360_API_OPERATOR_REPORTE_DATA = "tianji.api.tianjireport.detail";
  //第三方API表
  public static final  String CASH_TAB_CRAWL = "cash_third_crawl_detail";
  public static final  String CASH_TAB_CRAWL_CUSTOME = "cash_third_crawl_detail_custom";

  public static final  String CASH_TAB_REPORT_CRAWL = "cash_third_crawl_report";
  //第三方API结果
  public static final  String R360_API_CRAWL_RESULT = "wd_api_mobilephone_getdatav2_response";
  public static final  String R360_API_CRAWL_REPORT_RESULT = "tianji_api_tianjireport_detail_response";

  //MQ队列名称
  public static final  String MQ_GED_USER_UPDATE_CUSTID = "MQ_GED_USER_UPDATE_CUSTID";
//  支付成功后缴费队列
  public static final  String MQ_GED_COMPENSATION = "MQ_GED_COMPENSATION";
//  支付成功后推送借款端队列
  public static final  String MQ_GED_PUSH_TO_BORROW = "MQ_GED_PUSH-BORROW";
//  支付成功后回调队列
  public static final  String MQ_GED_CALL_BACK_GET = "MQ_GED_CALL_BACK_GET";
//  还款支付成功后回调队列
  public static final  String MQ_GED_REPAYMENT_CALL_BACK_GET = "MQ_GED_REPAYMENT_CALL_BACK_GET";
//  还款充值成功后推送借款端
  public static final  String MQ_GED_REPAYMENT_BORROW = "MQ_GED_REPAYMENT_BORROW";
  public static final  String CONTRACT_NO = "MQ_GED_CONTRACT_NO";
//借款系统推送过来的线下信息
  public static final  String MQ_GED_UNDER_INFOS = "MQ_GED_UNDER_INFOS";

  //V2.0--------------------
  //企业开户成功后推送借款系统
  public static final  String MQ_GED_ENTERPRISE_ACCOUNT_TO_LOAN = "MQ_GED_ENTERPRISE_ACCOUNT_TO_LOAN";
  //企业开户成功后推送冠e通
  public static final  String MQ_GED_ENTERPRISE_ACCOUNT_TO_GET = "MQ_GED_ENTERPRISE_ACCOUNT_TO_GET";
  //个人开户成功后推送借款系统
  public static final  String MQ_GED_PERSONAL_ACCOUNT_TO_LOAN = "MQ_GED_PERSONAL_ACCOUNT_TO_LOAN";
  //个人开户成功后推送冠e通
  public static final  String MQ_GED_PERSONAL_ACCOUNT_TO_GET = "MQ_GED_PERSONAL_ACCOUNT_TO_GET";
  //个人开户成功后推送借款系统
  public static final  String MQ_GED_WITHDRAW_ORDER_STATUS_TO_LOAN = "MQ_GED_WITHDRAW_ORDER_STATUS_TO_LOAN";
  //个人开户成功后推送冠e通
  public static final  String MQ_GED_WITHDRAW_FLAG_TO_LOAN = "MQ_GED_WITHDRAW_FLAG_TO_LOAN";
  // 还款充值成功后推送借款端
  public static final  String MQ_GED_REPAYMENT_RECHARGE_BORROW = "MQ_GED_REPAYMENT_RECHARGE_BORROW";
  //还款充值成功后缴纳分期服务费和分期账户管理费
  public static final  String MQ_GED_REPAYMENT_DEDUCT= "MQ_GED_REPAYMENT_DEDUCT";

  //分页数据
  public static final  Integer PAGE_NUM = 0;
  public static final  Integer PAGE_SIZE = 1;

  public static final String RES_CODE_SUCCESS = "0000";

}
