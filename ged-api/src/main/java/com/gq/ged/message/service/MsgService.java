package com.gq.ged.message.service;

import com.gq.ged.common.enums.MsgType;
import com.gq.ged.message.tmodel.MsgTypeEnum;

/**
 * Created by wyq_tomorrow on 2018/1/17.
 */
public interface MsgService {

  /**
   * 发送验证码
   *
   * @param mobile
   * @param type
   */
  void sendCheckCodeMessage(String mobile, MsgTypeEnum type);

  /**
   *
   * @param mobile
   * @param type
   */
  void sendCheckCodeMessage(String mobile, MsgType type);

  /**
   * 发送验证码  需要图形验证码作为参数
   * @param mobile
   * @param code
   */
  void sendCheckCodeMessagePc(String mobile,String code);

  /**
   * 统一发送短信
   *
   * @param template
   * @param mobile
   * @param type
   * @param param
   */
  void sendMessage(String template, String mobile, String type, String... param);

  /**
   * 发送短信
   * 
   * @param mobile
   * @param param
   */
  void sendMessageByLoanSysRegister(String mobile, String... param) throws Exception;

  /**
   * 审核拒绝发送短信
   * 
   * @param mobile
   * @param param
   */
  void sendMessageByLoanSysApplyRefuse(String mobile, String... param) throws Exception;

  /**
   * 按类型检查验证码是否存在
   *
   * @param mobile
   * @param code
   * @return
   */
  boolean checkCodeExists(String mobile, String code, String type);

  public boolean checkCodeExistsCommon(String mobile, String code, String constantType);
}
