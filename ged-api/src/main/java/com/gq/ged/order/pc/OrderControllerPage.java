package com.gq.ged.order.pc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.service.PayService;
import com.gq.ged.order.service.RechargeRepaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.pc.req.OrderFeesDetailReqForm;
import com.gq.ged.order.pc.req.ParasAfterPayReqForm;
import com.gq.ged.order.pc.req.RechargeReqForm;
import com.gq.ged.order.pc.req.RpdateServiceFlagByOrderNoReqForm;
import com.gq.ged.order.pc.res.RechargeReForm;
import com.gq.ged.order.pc.res.RepayPlanResForm;
import com.gq.ged.order.pc.res.WithDrawViewResForm;
import com.gq.ged.order.service.OrderPlanService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "PC端订单服务", description = "PC端订单服务")
@Controller
@RequestMapping(value = "/pc/page", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderControllerPage extends BaseController {
  @Resource
  OrderService orderService;
  @Resource
  OrderPlanService orderPlanService;
  @Resource
  RechargeRepaymentRecordService repaymentRecordService;
  @Resource
  PayService payService;

  @Value("${gq.ged.h5.url}")
  String gedh5url;

  static Logger logger = LoggerFactory.getLogger(OrderControllerPage.class);


  //
  // @RequestMapping("/payCallBack")
  // public String payCallBack(Model model , @RequestBody PayCallBackReqForm reqForm){
  // model.addAttribute("reqForm",reqForm);
  // return "index";
  // }

  @RequestMapping(value = "/payCallBack", method = RequestMethod.POST)
  public String payCallBack(@ModelAttribute PayCallBackReqForm reqForm, Model model)
      throws Exception {
    model.addAttribute("type", 1);
    String amt = new BigDecimal(reqForm.getAmt()).divide(new BigDecimal(100)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN) + "";
    reqForm.setAmt(amt);
    model.addAttribute("reqForm", reqForm);
    // reqForm.setAmt((Integer.parseInt(reqForm.getAmt()) / 100) + "");
    model.addAttribute("gedh5url", gedh5url);
    logger.info(
        "===========================================支付回调结果==============================================");
    logger.info(JSONObject.toJSONString(reqForm));
    return "index";
  }

  @ApiOperation(value = "缴费充值回调接口", notes = "缴费充值回调接口")
  @RequestMapping(value = "/saveResParasAfterPay", method = RequestMethod.POST)
  public String saveResParasAfterPay(@ModelAttribute PayCallBackReqForm res) throws Exception {
    orderService.saveResParasAfterPay(res);
    return "index";
  }

  @ApiOperation(value = "还款充值回调接口", notes = "还款充值回调接口")
  @RequestMapping(value = "/saveResParasAfterPayRepayment", method = RequestMethod.POST)
  public String saveResParasAfterPayForRepayment(@ModelAttribute PayCallBackReqForm res)
      throws Exception {
    String amt = new BigDecimal(res.getAmt()).divide(new BigDecimal(100)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN) + "";
    res.setAmt(amt);
    orderService.userRechargeCallBackForRepayment(res.getMchnt_txn_ssn(), res.getAmt(), res);
    return "repayment_suc_err";
  }

  @ApiOperation(value = "普通支付回调的接口", notes = "普通支付回调的接口")
  @RequestMapping(value = "/commonUserRechargeCallBack", method = RequestMethod.POST)
  public String commonUserRechargeCallBack(@ModelAttribute PayCallBackReqForm res)
      throws Exception {
    String amt = new BigDecimal(res.getAmt()).divide(new BigDecimal(100)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN) + "";
    res.setAmt(amt);
    payService.commonUserRechargeCallBack(res.getMchnt_txn_ssn(), res.getAmt(), res);
    return "index";
  }

  @ApiOperation(value = "还款充值回调接口", notes = "还款充值回调接口")
  @RequestMapping(value = "/payCallBackForPayment", method = RequestMethod.POST)
  public String payCallBackForPayment(@ModelAttribute PayCallBackReqForm reqForm, Model model)
      throws Exception {
    model.addAttribute("type", 2);
    model.addAttribute("optype", 0);
    GedRepaymentRecord gedRepaymentRecord =
        repaymentRecordService.getGedRepaymentRecordByZjStreamNo(reqForm.getMchnt_txn_ssn());
    model.addAttribute("orderNo", gedRepaymentRecord == null ? ""
        : gedRepaymentRecord.getSeqNo() == null ? "" : gedRepaymentRecord.getSeqNo());
    model.addAttribute("gedh5url", gedh5url);
    String amt = new BigDecimal(reqForm.getAmt()).divide(new BigDecimal(100)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN) + "";
    reqForm.setAmt(amt);
    model.addAttribute("reqForm", reqForm);
    logger.info(
        "===========================================还款支付回调结果==============================================");
    logger.info(JSONObject.toJSONString(reqForm));
    return "repayment_suc_err";
  }



  @ApiOperation(value = "普通支付 第三方支付成功后保  回调的接口", notes = "普通支付 第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/commonPayCallBack", method = RequestMethod.POST)
  public String commonPayCallBack(@ModelAttribute PayCallBackReqForm reqForm, Model model)
      throws Exception {
    model.addAttribute("type", 3);

    model.addAttribute("gedh5url", gedh5url);
    String amt = new BigDecimal(reqForm.getAmt()).divide(new BigDecimal(100)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN) + "";
    reqForm.setAmt(amt);
    model.addAttribute("reqForm", reqForm);
    model.addAttribute("optype", 1);
    logger.info(
        "===========================================普通支付回调结果==============================================");
    logger.info(JSONObject.toJSONString(reqForm));
    return "index";
  }

}
