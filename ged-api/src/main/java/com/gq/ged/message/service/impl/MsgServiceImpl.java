package com.gq.ged.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.MsgType;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.RandomUtil;
import com.gq.ged.message.api.res.MsgRedisEntity;
import com.gq.ged.message.service.MsgService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyq_tomorrow on 2018/1/17.
 */
@Service
public class MsgServiceImpl implements MsgService {
  static Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);

  @Value("${gq.cash.msm.template.url}")
  String templateUrl;
  @Value("${gq.cash.msm.template.sysCode}")
  String systemCode;
  @Value("${gq.cash.msm.template.register.code}")
  String registerCode;
  @Value("${gq.cash.msm.template.forget.code}")
  String forgetCode;
  @Value("${gq.cash.msm.template.loansysregister.code}")
  String loanSysRegister;
  @Value("${gq.cash.msm.template.loanrefuse.code}")
  String loanrefuseCode;
  @Value("${gq.cash.msm.template.bindMobile.code}")
  String bindMobileCode;

  @Resource
  RedissonClient redissonClient;

  @Resource
  UserService userService;

  @Override
  public void sendCheckCodeMessage(String mobile, MsgTypeEnum type) {
    // 判断手机号是否是系统用户
    List<UserLogin> users = userService.getUserByMobile(mobile);
    /*
     * if (users == null || users.size() == 0) throw new
     * BusinessException(Errors.USER_VALIDE_CODE_ERROR,"手机号:"+mobile + "不是系统用户！");
     */
    String code = RandomUtil.generateMixNumber(6);
    String template = "";
    if (type == MsgTypeEnum.MSG_TYPE_REGISTER) {
      // 判断是否注册
      List<UserLogin> list = userService.getUserByMobile(mobile);
      if (list.size() > 0) {
        throw new BusinessException(Errors.USER_MOBILE_EXISTS_ERROR);
      }
      template = registerCode;
    }
    if (type == MsgTypeEnum.MSG_TYPE_FORGET) {
      template = forgetCode;
    }
    sendMessage(template, mobile, type.toString(), code);
  }

  @Override
  public void sendCheckCodeMessage(String mobile, MsgType type) {
    String code = RandomUtil.generateMixNumber(6);
    String template = "";
    if (type == MsgType.BIND_MOBILE) {
      template = bindMobileCode;
    }
    sendMessage(template, mobile, type.toString(), code);
  }



  @Override
  public void sendCheckCodeMessagePc(String mobile, String code) {
    // 判断手机号是否是系统用户
    List<UserLogin> users = userService.getUserByMobile(mobile);
    if (users == null || users.size() == 0) {
        throw new BusinessException(Errors.USER_VALIDE_CODE_ERROR, "手机号:" + mobile + "不是系统用户！");
    }
    // 校验图形验证码
    boolean f = this.checkCodeExistsCommon(mobile, code, Constant.PICTURE_CODE);
    if (!f) { // 校验图形验证码
      throw new BusinessException(Errors.USER_MOBILE_EXISTS_ERROR, "图形验证码错误");
    }
    this.sendCheckCodeMessage(mobile, MsgTypeEnum.MSG_TYPE_FORGET);
  }

  @Override
  public void sendMessage(String template, String mobile, String msgType, String... param) {
    // 检查是否超过规定次数(3分钟内不能重发，1天内发5次)
    RBucket rBucket =
        redissonClient.getBucket(Constant.VALID_MSG + ":" + msgType + ":" + mobile);
    beforCheckSendCode(rBucket);
    try {
      String message = MsgUtils.getMessageJsonStr(systemCode, mobile, template, param);
      logger.info("param:" + param);
      // afterCheckSendCode(rBucket, msgType.toString(), mobile, param.clone());
      // afterCheckSendCode(rBucket, msgType.toString(), mobile, param.clone());
      JSONObject jsonObject = HttpUtils.doPost(templateUrl, message);
      String retCode = String.valueOf(jsonObject.get("retCode"));
      if ("Success".equals(retCode)) {
        logger.info("发送短信成功");
        // 后置检查8
        afterCheckSendCode(rBucket, msgType, mobile, param.clone());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendMessageByLoanSysRegister(String mobile, String... param) throws Exception {
    String message = MsgUtils.getMessageJsonStr(systemCode, mobile, loanSysRegister, param);
    JSONObject jsonObject = HttpUtils.doPost(templateUrl, message);
  }

  @Override
  public void sendMessageByLoanSysApplyRefuse(String mobile, String... param) throws Exception {
    String message = MsgUtils.getMessageJsonStr(systemCode, mobile, loanrefuseCode, param);
    JSONObject jsonObject = HttpUtils.doPost(templateUrl, message);
  }


  private void beforCheckSendCode(RBucket rBucket) {
    if (rBucket.isExists()) {
      MsgRedisEntity me = (MsgRedisEntity) rBucket.get();
      // 1、检查三分钟
      boolean flag = DateUtils.isSameDay(me.getCurrentDate(), new Date());
      if (flag) {
        // 2、检查一天
        if (me.getCount() == 5) {
          throw new BusinessException(Errors.MSG_FIVE_SEND_WRONG_ERROR);
        }
      }
    }
  }


  private void afterCheckSendCode(RBucket rBucket, String type, String mobile, String[] param) {
    // 检查缓存是否存在内容，存在在当前次数加1
    if (rBucket.isExists()) {
      MsgRedisEntity me = (MsgRedisEntity) rBucket.get();
      boolean flag = DateUtils.isSameDay(me.getCurrentDate(), new Date());
      // 日期相同
      if (flag) {
        me.setCount(me.getCount() + 1);
      } else {
        me.setCount(1);
      }
      me.setParam(param);
      me.setCurrentDate(new Date());
      rBucket.set(me, 180, TimeUnit.SECONDS);
    } else {
      MsgRedisEntity me = new MsgRedisEntity();
      me.setParam(param);
      me.setCount(1);
      me.setCurrentDate(new Date());
      me.setMobile(mobile);
      rBucket.set(me, 180, TimeUnit.SECONDS);
    }
  }


  @Override
  public boolean checkCodeExists(String mobile, String code, String type) {
    RBucket rBucket = redissonClient.getBucket(Constant.VALID_MSG + ":" + type + ":" + mobile);
    if (!rBucket.isExists()) {
      return false;
    }
    MsgRedisEntity me = (MsgRedisEntity) rBucket.get();
    String rCode = me.getParam()[0];
    if (!rCode.equals(code)) {
      return false;
    }
    return true;
  }

  @Override
  public boolean checkCodeExistsCommon(String mobile, String code, String constantType) {
    RBucket rBucket = redissonClient.getBucket(constantType + mobile);
    if (!rBucket.isExists()) {
      return false;
    }
    MsgRedisEntity me = (MsgRedisEntity) rBucket.get();
    String rCode = me.getParam()[0];
    if (!rCode.equals(code)) {
      return false;
    }
    return true;
  }
}
