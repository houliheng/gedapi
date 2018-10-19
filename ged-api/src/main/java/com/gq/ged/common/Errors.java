package com.gq.ged.common;

/**
 * 错误码
 */
public enum Errors {
  // 0-200 系统级
  //
  SYSTEM_NOT_ACCESS_ERROR(-1, "系统错误", "无权限登录"),
  //
  SUCCESS(0, "系统错误", "操作成功"),
  //
  SYSTEM_ERROR(1, "系统错误", "系统错误"),
  //
  SYSTEM_DATA_ERROR(2, "系统错误", "数据异常"),
  //
  SYSTEM_DATA_NOT_FOUND(3, "系统错误", "数据不存在"),

  //
  SYSTEM_UPDATE_ERROR(4, "系统错误", "数据更新失败"),
  //
  SYSTEM_NO_ACCESS(5, "系统错误", "无权限访问"),
  //
  SYSTEM_REQUEST_PARAM_ERROR(6, "系统错误", "请求参数错误"),
  //
  SYSTEM_BUSINESS_ERROR(7, "系统错误", "系统繁忙,请您稍后再试"),
  //
  SYSTEM_DATE_TRANS_ERROR(8, "系统错误", "日期转换错误"),
  //
  SYSTEM_TOKEN_ERROR(999, "系统错误", "请登录"),
  //
  USER_MOBILE_EXISTS_ERROR(101, "用户注册", "该手机号已经注册"),
  //
  USER_MOBILE_NOT_EXISTS_ERROR(102, "用户登录", "该手机号不存在"),
  USER_COMPANY_NOT_EXISTS_ERROR(102, "用户登录", "该社会统一代码不存在"),
  //
  BUSI_PASSWORD_WRONG_TOO_MANY_ERROR(103, "用户登录", "账号因密码错误次数过多，请10分钟后再试"),
  //
  BUSI_PASSWORD_CONFIRM_WRONG_ERROR(104, "忘记密码", "两次密码输入不一致"),
  //
  BUSI_PASSWORD_WRONG_ERROR(105, "用户登录", "密码错误，还有%s次机会"),
  //
  USER_VALIDE_CODE_NOT_EXISTS_ERROR(106, "用户登录", "验证码错误"),
  //
  MSG_FIVE_SEND_WRONG_ERROR(107, "验证码", "一天内只能发送5次验证码"),
  //
  USER_VALIDE_CODE_ERROR(108, "用户登录", "验证码错误"),
  //
  UPLOAD_FILE_NOT_EXISTS_ERROR(109, "上传文件错误", "上传文件不能为空"),
  //
  USER_COMPANY_EXISTS_ERROR(110, "用户注册", "该企业已经注册"),

  USER_SHTYDM_NOT_EXISTS_ERROR(1111, "用户注册", "社会统一代码不能为空"),

  USER_TYPE_NOT_EXISTS_ERROR(1110, "用户注册", "用户类型不能为空"),

  USER_COMPANY_MOBILE_EXISTS_ERROR(118, "用户注册", "该企业手机号已经注册"),
  //
  USER_CREATE_ACCOUNT_ERROR(111, "个人开户", "个人开户失败"),

  ENTERPRISE_CREATE_ACCOUNT_ERROR(112, "企业开户", "企业开户失败"),

  ONLINE_BANKING_RECHARGE_ERROR(113, "网银充值", "网银充值失败"),

  WITHDRAW_ERROR(114, "提现", "提现失败"),

  USER_REGISTER_ERROR(115, "用户激活", "用户激活失败"),

  USER_GUARANTOR_COMPANY_EXISTS_ERROR(116, "用户注册", "该担保人已经注册"),

  USER_OLD_PASSWORD_ERROR(117, "用户注册", "老密码错误"),

  //
  USER_NEW_SAME_PASSWORD_ERROR(118, "用户注册", "新密码和老密码不能一致"),
  //
  USER_BIND_MOBILE_ERROR(119, "用户注册", "该手机号已经被绑定"),
  //
  ORDER_NOT_EXISTS_ERROR(201, "订单", "订单不存在"),
  //
  ACCOUNT_NOT_EXISTS_ERROR(202, "开户", "未开户"),
  //
  NOTE_UNCOMMIT_ERROR(205, "申请单", "您有审核中的订单"),

  PUSH_ERROR(206, "申请单", "推送数据失败"),

  WITH_DRAW_ERROR(207, "申请单", "提现失败"),

  NOTSERVICE_ERROR(208, "申请单", "请去交服务费"),

  OPEN_ACCOUNT_ERROR(209, "开户", "该用户已经开户"),

  WITHDRAWING_ERROR(208, "申请单", "您的提现正在处理中"),

  GUARANT_ERROR(208, "申请单", "请联系客服确认已交所需费用"),

  LOANSYSTEM_ERROR(210, "申请单", "还款计划失败"),

  INVOK_ERROR(211, "申请单", "调用失败"),

  VALIDECODE_EXPIRE_ERROR(212, "验证码", "验证码过期"),
  REPAYMENT_IS_DEALING(310, "您的还款正在处理中", "您的还款正在处理中"),

  BORROWER_EXCEPTION(400 , "借款人信息错误" ,"借款人信息错误"),
  ACCOUNT_ENNOAML(600 , "账户状态异常" ,"账户状态异常"),

  VALIDECODE_ERROR(213, "验证码", "验证码错误"),

  VALIDATE_INPUT_ERROR(215,"验证码错误","您输入的验证码不正确，请重新获取后输入"),
  VALIDECODE_ISNULL_ERROR(214, "验证码", "验证码为空，请您输入验证码"),
  SIGNERNAME_ISNULL_ERROR(217, "签约代表人姓名", "签约代表人姓名不能为空"),
  NOT_CONTRACT_CODE(216, "没有合同编号", "获取合同编号为空，无法获取账户管理费分期支付表"),

  GUARANT_ORDER_ERROR(220 , "没有担保账号信息" ,"没有担保账号信息"),

    BADNMOBILE_ERROR(219, "用户登录", "请先绑定手机号"),


    CCOUNT_NOT_EXISTS(601 , "账户信息" ,"个人账户不存在"),

  MOBILE_IS_NULL_ERROR(603 , "手机号验证" ,"请先填写手机号"),
  REQ_ERROR(605 , "立即签署" ,"立即签署失败,生成合同时出现错误"),
  FETCH_MANAGER_SERVICE_FREE_ERROR(606 , "获取账户管理费分期支付表失败" ,"查询还款计划失败，获取账户管理费分期支付表失败"),
  MOBILE_IS_ERROR(604 , "手机号验证" ,"请填写正确的手机号"),
  ORDER_EXISTS(602 , "申请单" ,"申请单已存在"),
  WITHDRAW_LACK_BALANCE(701 , "提现" ,"账户余额不足"),
  WITHDRAW_UNPAID_GUARANTEE_DEPOSIT_FEE(702 , "提现" ,"请联系客服确认已交所需费用"),
  ALREADY_RECHARGED(703 , "充值" ,"已充值过"),
  ALREADY_DECUCTED(704 , "缴费" ,"已缴费");




  // WITHDRAW


  public int code;
  public String categoryLable;
  public String label;
  public Object data;

  private Errors(int code, String categoryLable, String label,Object data) {
    this.code = code;
    this.categoryLable = categoryLable;
    this.label = label;
    this.data = data;
  }
  private Errors(int code, String categoryLable, String label) {
    this.code = code;
    this.categoryLable = categoryLable;
    this.label = label;
  }

  /**
   * @param code
   * @return
   */
  public static String getLabel(int code) {
    String result = "";
    for (Errors status : Errors.values()) {
      if (status.code == code) {
        result = status.label;
      }
    }
    return result;
  }

  public static Errors resolveObject(int code) {
    switch (code) {
      case 0:
        return SUCCESS;
      case 1:
        return SYSTEM_ERROR;
      case 2:
        return SYSTEM_DATA_ERROR;
      case 3:
        return SYSTEM_DATA_NOT_FOUND;
      case 4:
        return SYSTEM_UPDATE_ERROR;
      case 5:
        return SYSTEM_NO_ACCESS;
      case 6:
        return SYSTEM_REQUEST_PARAM_ERROR;
      case 7:
        return SYSTEM_BUSINESS_ERROR;
      case 8:
        return SYSTEM_DATE_TRANS_ERROR;
      case 101:
        return USER_MOBILE_EXISTS_ERROR;
      case 102:
        return USER_MOBILE_NOT_EXISTS_ERROR;
      case 103:
        return BUSI_PASSWORD_WRONG_TOO_MANY_ERROR;
      case 104:
        return BUSI_PASSWORD_CONFIRM_WRONG_ERROR;
      case 105:
        return BUSI_PASSWORD_WRONG_ERROR;
      case 106:
        return USER_VALIDE_CODE_NOT_EXISTS_ERROR;
      case 107:
        return MSG_FIVE_SEND_WRONG_ERROR;
      case 108:
        return USER_VALIDE_CODE_ERROR;
      case 109:
        return UPLOAD_FILE_NOT_EXISTS_ERROR;
    }
    return Errors.SYSTEM_ERROR;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getCategoryLable() {
    return categoryLable;
  }

  public void setCategoryLable(String categoryLable) {
    this.categoryLable = categoryLable;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
