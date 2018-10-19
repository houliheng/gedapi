package com.gq.ged.order.pc;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gq.ged.user.dao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gq.ged.account.controller.res.v2.CapitalResForm;
import com.gq.ged.account.controller.res.v2.OnlineBankingRecharge;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.service.OrderPlanService;
import com.gq.ged.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zhaozb on 2018/4/13.
 */

@Api(value = "V2.0PC端订单服务", description = "V2.0PC端订单服务")
@Controller
@RequestMapping(value = "v2/pc/page", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderControllerNewPage extends BaseController {
  @Resource
  OrderService orderService;
  @Resource
  OrderPlanService orderPlanService;
  @Resource
  AccountOpenService openService;

  @Value("${gq.ged.h5.url}")
  String gedh5url;
  @Value("${gq.ged.local.url}")
  String gedLocalurl;

  static Logger logger = LoggerFactory.getLogger(OrderControllerNewPage.class);


  //
  // @RequestMapping("/payCallBack")
  // public String payCallBack(Model model , @RequestBody PayCallBackReqForm reqForm){
  // model.addAttribute("reqForm",reqForm);
  // return "index";
  // }

  @RequestMapping(value = "/payCallBack", method = RequestMethod.GET)
  public String payCallBack(@ModelAttribute CapitalResForm resForm, Model model)
      throws Exception {
    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    OnlineBankingRecharge rechargeData = mapper.readValue(URLDecoder.decode(data, "UTF-8"),OnlineBankingRecharge.class);

    //调用充值(缴费)回调接口
    //openService.paychargeCallBack(resForm);

    model.addAttribute("type", 1);
    model.addAttribute("resForm", resForm);
    model.addAttribute("data", rechargeData);
    //resForm.setAm ((Integer.parseInt(rechargeData.getAmount()) / 100) + "");
    //model.addAttribute("gedh5url", gedh5url);
    model.addAttribute("gedh5url", gedLocalurl);
    logger.info("=====================================支付结果=======================================");
    logger.info(JSONObject.toJSONString(resForm));
    return "index";
  }

    @RequestMapping(value = "/guarantorPayCallBack", method = RequestMethod.GET)
    public String guarantorPayCallBack(@ModelAttribute CapitalResForm resForm, Model model)
            throws Exception {
        String data = resForm.getData();
        ObjectMapper mapper = new ObjectMapper();
        OnlineBankingRecharge rechargeData = mapper.readValue(URLDecoder.decode(data, "UTF-8"),OnlineBankingRecharge.class);

        model.addAttribute("type", 1);
        model.addAttribute("resForm", resForm);
        model.addAttribute("data", rechargeData);
        //resForm.setAm ((Integer.parseInt(rechargeData.getAmount()) / 100) + "");
        //model.addAttribute("gedh5url", gedh5url);
        model.addAttribute("gedh5url", gedLocalurl);
        logger.info("=====================================支付结果=======================================");
        logger.info(JSONObject.toJSONString(resForm));
        return "guarantor_index";
    }

  @ApiOperation(value = "第三方支付成功后保  回调的接口", notes = "第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/saveResParasAfterPay", method = RequestMethod.POST)
  public String saveResParasAfterPay(@ModelAttribute PayCallBackReqForm res) throws Exception {
    orderService.saveResParasAfterPay(res);
    return "index";
  }

  @RequestMapping(value = "/payCallBackForPayment", method = RequestMethod.GET)
  public String payCallBackForPayment(@ModelAttribute CapitalResForm resForm, Model model)
      throws Exception {
    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    OnlineBankingRecharge rechargeData = mapper.readValue(URLDecoder.decode(data, "UTF-8"),OnlineBankingRecharge.class);
    //充值(还款)回调
    openService.repaymentRechargeCallBack(resForm);

    model.addAttribute("type", 1);
    model.addAttribute("resForm", resForm);
    model.addAttribute("data", rechargeData);
    //resForm.setAm ((Integer.parseInt(rechargeData.getAmount()) / 100) + "");
    //model.addAttribute("gedh5url", gedh5url);
    model.addAttribute("gedh5url", gedLocalurl);
    logger.info(
            "===========================================支付回调结果==============================================");
    logger.info(JSONObject.toJSONString(resForm));
    return "repayment_suc_err";
  }

  @ApiOperation(value = "还款支付第三方支付成功后保  回调的接口", notes = "还款支付第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/saveResParasAfterPayRepayment", method = RequestMethod.POST)
  public String saveResParasAfterPayForRepayment(@ModelAttribute PayCallBackReqForm res)
      throws Exception {
//    orderService.pushToBorrowRepayment(
//        res.getMchnt_txn_ssn().substring(0, res.getMchnt_txn_ssn().length() - 9));
      res.setAmt(Integer.parseInt(res.getAmt()) / 100 + "");
      orderService.userRechargeCallBackForRepayment(
        res.getMchnt_txn_ssn().substring(0, res.getMchnt_txn_ssn().length() - 9),res.getAmt() , res);
    return "repayment_suc_err";
  }

}
