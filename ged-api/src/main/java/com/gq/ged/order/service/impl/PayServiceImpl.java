package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.utils.SecurityUtils;
import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.dao.mapper.UserRechargeRecordMapper;
import com.gq.ged.order.dao.model.UserRechargeRecord;
import com.gq.ged.order.dao.model.UserRechargeRecordExample;
import com.gq.ged.order.pc.req.RechargeCallBackForm;
import com.gq.ged.order.pc.req.RechargeForm;
import com.gq.ged.order.pc.res.GeneratePayParas;
import com.gq.ged.order.pc.res.RechargeReForm;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.service.PayService;
import com.gq.ged.order.service.RechargeRecordService;
import com.gq.ged.order.service.RechargeUserRecordService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Levi on 2018/4/26.
 */
@Service
public class PayServiceImpl implements PayService {
  Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

  @Resource
  OrderService orderService;
  @Resource
  AccountService accountService;
  @Resource
  UserService userService;

  @Resource
  RechargeUserRecordService userRechargeRecordService;
  @Value("${ged.pay.mchn}")
  String gedPayMchn;
  @Value("${gq.ged.h5.url}")
  String gedh5url;
  @Value("${gq.ged.api.url}")
  String gedapiUrl;
  @Value("${gq.ged.get.mchn}")
  String mchn;
  @Value("${gq.ged.get.rechargeTradeType}")
  String rechargeTradeType;
  @Value("${gq.ged.get.rechargeCallBackTradeType}")
  String rechargeCallBackTradeType;



  @Override
  public RechargeReForm commonUserRecharge(Long userId, String bankId, String amt, String period)
      throws Exception {
    logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓普通充值调get开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓userid=" + userId);
    logger.info("userRechargeForRepayment请求参数为bankId=" + bankId + "------amt=" + amt);
    User userInfo = userService.getUserById(userId);
    /*
     * if (userInfo.getCheckAccountFlag() == null || userInfo.getCheckAccountFlag() != 4){ throw new
     * BusinessException(Errors.ACCOUNT_ENNOAML, "账户信息异常，请先开户！" ); }
     */
    if (userInfo.getGetCustId() == null) {
      throw new BusinessException(Errors.ACCOUNT_ENNOAML, "账户信息异常，请先开户！");
    }
    RechargeForm paras = new RechargeForm();
    paras.setAmt(amt);
    paras.setBank_id(bankId);
    paras.setCust_no(userInfo.getGetCustId());
    paras.setMchn(mchn);
    // SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
    String seq = System.currentTimeMillis() + "";
    paras.setSeq_no(seq);
    paras.setTrade_type(rechargeTradeType);

    JSONObject res = orderService.recharge(paras);
    logger.info("充值操作==========用户id=" + userId + "  结果为：");
    logger.info(res.toJSONString());
    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑普通充值调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    if (res != null && Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))) {
      RechargeReForm r = new RechargeReForm();
      r.setUserName(res.getString("username"));
      r.setOrderNo(res.getString("order_no"));
      r.setSeqNum(res.getString("seq_no"));

      UserRechargeRecord userRechargeRecord = new UserRechargeRecord();
      // userRechargeRecord.setStatus();
      userRechargeRecord.setUserId(userId);
      userRechargeRecord.setSeqNo(seq);
      userRechargeRecord.setBankId(bankId);
      userRechargeRecord.setCreateTime(new Date());
      userRechargeRecord.setCustNo(userInfo.getGetCustId());
      userRechargeRecord.setMobileNo(userInfo.getMobile());
      userRechargeRecord.setModifyTime(new Date());
      userRechargeRecord.setOrderNo(r.getOrderNo());
      userRechargeRecord.setRechargeAmount(new BigDecimal(amt));
      userRechargeRecord.setRespCode(res.getString("resp_code"));
      userRechargeRecord.setTradeDate(new Date());
      // userRechargeRecord.setType();
      userRechargeRecordService.insertUserRechargeRecorde(userRechargeRecord);

      return r;
    }
    throw new BusinessException(Errors.SYSTEM_ERROR,
        "普通充值前获取流水号失败 原因：" + res.getString("resp_code"));
  }


  @Override
  public GeneratePayParas commonGeneratePayParas(String seqNum, String loginName, String orderNo,
      Long userId, String orderPayType, String issinscd, String amt) {
    // GedOrder order = orderService.selectOrderByOrderNo(seqNum.substring(0, seqNum.length() - 9));
    // if (order == null)
    // throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
    // "生成第三方支付参数失败，原因订单查询失败orderNo=" + seqNum.substring(0, seqNum.length() - 9));
    // User user = userService.getUserById(userId);
    Account account = accountService.getAccountByUserId(userId);
    if (account == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "生成第三方支付参数失败，原因账户查询失败userId=" + userId);
    }

    GeneratePayParas payParas = new GeneratePayParas();
    payParas.setAmt(new BigDecimal(amt).multiply(new BigDecimal(100)).intValue() + "");
    payParas.setLogin_id(loginName);
    payParas.setMchnt_cd(gedPayMchn);
    // SimpleDateFormat df = new SimpleDateFormat("HHmmssSSS");
    payParas.setMchnt_txn_ssn(orderNo);
    // payParas.setMchnt_txn_ssn(orderNo + df.format(new Date()));
    payParas.setBack_notify_url(gedapiUrl + "/pc/page/commonUserRechargeCallBack");
    payParas.setPage_notify_url(gedapiUrl + "/pc/page/commonPayCallBack");
    payParas.setIss_ins_cd(issinscd);
    payParas.setOrder_pay_type(orderPayType);

    String si = payParas.getAmt() + "|" + payParas.getBack_notify_url() + "|" + issinscd + "|"
        + payParas.getLogin_id() + "|" + payParas.getMchnt_cd() + "|" + payParas.getMchnt_txn_ssn()
        + "|" + orderPayType + "|" + payParas.getPage_notify_url();
    String ssiInfo = SecurityUtils.sign(si);
    logger.info("普通充值generatePayParas明文签名为：" + si);
    logger.info("普通充值generatePayParas生成的签名为：" + ssiInfo);
    payParas.setSignature(ssiInfo);

    return payParas;
  }

  @Override
  public void commonUserRechargeCallBack(String ssn, String amt,
      PayCallBackReqForm payCallBackReqForm) throws Exception {
    logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓普通充值成功后回调get开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓第三方流水号=" + ssn);
    if (!Constant.RES_CODE_SUCCESS.equals(payCallBackReqForm.getResp_code())) {
      throw new BusinessException(Errors.SYSTEM_ERROR, "普通充值富有充值回调失败："
          + payCallBackReqForm.getResp_code() + "-" + payCallBackReqForm.getResp_desc());
    }
    // 1、通过返回资金流水号获得充值记录
    UserRechargeRecord record = userRechargeRecordService.getGedUserRechargeRecordOrderNo(ssn);
    if (record == null) {
      throw new BusinessException(Errors.SYSTEM_ERROR, "普通充值前获取充值记录失败 ");
    }
    User userInfo = userService.getUserById(record.getUserId());
    RechargeCallBackForm rechargeCallBackForm = new RechargeCallBackForm();
    rechargeCallBackForm.setTrade_type(rechargeCallBackTradeType);// 交易类型
    rechargeCallBackForm.setSeq_no(record.getOrderCode());// 交易流水号
    rechargeCallBackForm.setCust_no(userInfo.getGetCustId());// 客户编号
    rechargeCallBackForm.setOrder_no(ssn);// 第三方交易流水号
    rechargeCallBackForm.setRespCode(payCallBackReqForm.getResp_code());// 第三方交易返回码
    rechargeCallBackForm.setMobile_no(record.getMobileNo());// 第三方交易返回手机号
    rechargeCallBackForm.setAmt(new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 充值金额
    rechargeCallBackForm.setBank_id(record.getBankId());// 银行类型


    // 2、调用资金入账
    logger.info("普通充值完成后调get入账的start");
    logger.info(JSONObject.toJSONString(rechargeCallBackForm));
    JSONObject res = orderService.rechargeCallBack(rechargeCallBackForm);
    logger.info("普通充值完成后调get入账的end");
    logger.info(res.toJSONString());
    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑普通充值成功后回调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    // 3、更新用户充值记录为成功
    record.setStatus(1);
    userRechargeRecordService.updateUserRechargeRecordById(record);
  }


}
