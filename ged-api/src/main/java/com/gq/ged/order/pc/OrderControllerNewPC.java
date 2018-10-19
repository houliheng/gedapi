package com.gq.ged.order.pc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.gq.ged.account.service.SignService;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.pc.req.OrderFeesDetailReqForm;
import com.gq.ged.order.pc.req.ParasAfterPayReqForm;
import com.gq.ged.order.pc.req.RpdateServiceFlagByOrderNoReqForm;
import com.gq.ged.order.pc.req.UpdateFinishRepayReqForm;
import com.gq.ged.order.pc.res.CheckServiceFeeResForm;
import com.gq.ged.order.pc.res.RepayPlanResForm;
import com.gq.ged.order.pc.res.WithDrawViewResForm;
import com.gq.ged.order.service.OrderNewService;
import com.gq.ged.order.service.OrderPlanService;
import com.gq.ged.user.dao.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "V2.0PC端订单服务", description = "V2.0PC端订单服务")
@RestController
@RequestMapping(value = "v2/pc/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderControllerNewPC extends BaseController {
  @Resource
  OrderNewService orderNewService;
  @Resource
  OrderPlanService orderPlanService;
  @Resource
  SignService signService;

  @ApiOperation(value = "资金管理页面跳转选择", notes = "资金管理页面跳转选择")
  @RequestMapping(value = "/moneyManage", method = RequestMethod.GET)
  public ResponseEntity<OrderFeesDetailReqForm> moneyManage(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    return ResponseEntityUtil.success(orderNewService.orderManage(user.getId()));
  }

  @ApiOperation(value = "费用支付页面详情", notes = "费用支付页面详情")
  @RequestMapping(value = "/payFeeInfos", method = RequestMethod.GET)
  public ResponseEntity<?> payFeeInfos(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderNewService.payFeeInfos(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "订单查看页面详情", notes = "订单查看页面详情")
  @RequestMapping(value = "/orderView", method = RequestMethod.GET)
  public ResponseEntity<?> orderView(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderNewService.orderView(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "根据订单编号生成充值时第三方的请求参数", notes = "根据订单编号生成充值时第三方的请求参数")
  @RequestMapping(
      value = "/generatePayParas/{orderNo}/{loginName}/{seqNum}/{orderPayType}/{issinscd}",
      method = RequestMethod.GET)
  public ResponseEntity<?> generatePayParas(@PathVariable("orderNo") String orderNo,
                                            @PathVariable("loginName") String loginName, @PathVariable("seqNum") String seqNum,
                                            @PathVariable("orderPayType") String orderPayType, @PathVariable("issinscd") String issinscd,
                                            HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderNewService.generatePayParas(seqNum, loginName, orderNo,
        this.getUserInfo(request).getId(), orderPayType, issinscd));
  }


  @ApiOperation(value = "第三方支付成功后保  回调的接口", notes = "第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/saveResParasAfterPay", method = RequestMethod.POST)
  public ResponseEntity<?> saveResParasAfterPay(HttpServletRequest request,
                                                @RequestBody PayCallBackReqForm res) throws Exception {
    return ResponseEntityUtil.success(orderNewService.saveResParasAfterPay(res));
  }

  @ApiOperation(value = "用户支付", notes = "用户支付")
  @RequestMapping(value = "/userRecharge/{bankId}", method = RequestMethod.GET)
  public ResponseEntity<?> userRecharge(HttpServletRequest request,
                                        @PathVariable("bankId") String bankId) throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.userRecharge(this.getUserInfo(request).getId(), bankId));
  }


  @ApiOperation(value = "用户充值成功回调资金端", notes = "用户充值成功回调资金端")
  @RequestMapping(value = "/userRechargeCallBack", method = RequestMethod.GET)
  public ResponseEntity<?> userRechargeCallBack(HttpServletRequest request) throws Exception {
    orderNewService.userRechargeCallBack(this.getUserInfo(request).getId());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "用户缴费", notes = "用户缴费")
  @RequestMapping(value = "/userCompensation", method = RequestMethod.GET)
  public ResponseEntity<?> userCompensation(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.userCompensation(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "修改充值中状态", notes = "修改充值中状态")
  @RequestMapping(value = "/paying/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<?> paying(HttpServletRequest request,
                                  @PathVariable("orderNo") String orderNo) throws Exception {
    orderNewService.paying(this.getUserInfo(request).getId(), orderNo);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "查询缴费状态", notes = "查询缴费状态")
  @RequestMapping(value = "/checkServiceStatus", method = RequestMethod.GET)
  public ResponseEntity<CheckServiceFeeResForm> checkServiceStatus(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.CheckServiceFeeStatus(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "保存指定订单费用支付时选择的bankid", notes = "保存指定订单费用支付时选择的bankid")
  @RequestMapping(value = "/saveBankId/{orderNo}/{bankId}", method = RequestMethod.GET)
  public ResponseEntity<?> saveBankId(HttpServletRequest request,
                                      @PathVariable("orderNo") String orderNo, @PathVariable("bankId") String bankId)
      throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.saveBankId(orderNo, bankId, this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "提现", notes = "提现")
  @RequestMapping(value = "/withDr/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<?> withDr(HttpServletRequest request,
                                  @PathVariable("orderNo") String orderNo) throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.confirWithDrawCash(this.getUserInfo(request), orderNo));
  }


  @ApiOperation(value = "提现详情页面", notes = "提现详情页面")
  @RequestMapping(value = "/beforeWithDr/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<WithDrawViewResForm> beforeWithDr(HttpServletRequest request,
                                                          @PathVariable("orderNo") String orderNo) throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.beforeWithDr(orderNo, this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "第三方支付(还款)成功后  回调的接口", notes = "第三方支付(还款)成功后  回调的接口")
  @RequestMapping(value = "/saveRechargeAfterPay", method = RequestMethod.POST)
  public ResponseEntity<?> saveRechargeAfterPay(HttpServletRequest request,
                                                @RequestBody ParasAfterPayReqForm res) throws Exception {
    return ResponseEntityUtil.success(orderNewService.saveRepaymentAfterPay(res));
  }

  @ApiOperation(value = "借款系统回调接口 -- 根据订单号修改serviceFlag", notes = "借款系统回调接口 -- 根据订单号修改serviceFlag")
  @RequestMapping(value = "/updateServiceFlagByOrderNo", method = RequestMethod.POST)
  public ResponseEntity<?> updateServiceFlagByOrderNo(HttpServletRequest request,
                                                      @RequestBody RpdateServiceFlagByOrderNoReqForm req) throws Exception {
    return ResponseEntityUtil.success(orderNewService.updateServiceFlagByOrderNo(req));
  }

  @ApiOperation(value = "test", notes = "test")
  @RequestMapping(value = "/test/{orderCode}", method = RequestMethod.GET)
  public ResponseEntity<?> test(HttpServletRequest request,
                                @PathVariable("orderCode") String orderCode) throws Exception {
    orderNewService.pushToBorrowRepayment(orderCode);
    System.out.println("====================推送借款端信息beigen=====================");
    System.out.println("orderCode:" + orderCode);
    // System.out.println("sysFlag:" + sysFlag);
    System.out.println("====================推送借款端信息end=====================");
    return ResponseEntityUtil.success();
  }
  // @ApiOperation(value = "test", notes = "test")
  // @RequestMapping(value = "/test/{orderCode}/{sysFlag}/{contractNo}", method = RequestMethod.GET)
  // public ResponseEntity<?> test(HttpServletRequest request,
  // @PathVariable("orderCode") String orderCode, @PathVariable("sysFlag") Integer sysFlag,
  // @PathVariable("contractNo") String contractNo) throws Exception {
  // orderService.pushToBrrow(orderCode, sysFlag, contractNo);
  // System.out.println("====================推送借款端信息beigen=====================");
  // System.out.println("orderCode:" + orderCode);
  // System.out.println("sysFlag:" + sysFlag);
  // System.out.println("====================推送借款端信息end=====================");
  // return ResponseEntityUtil.success();
  // }

  @ApiOperation(value = "还款计划", notes = "还款计划")
  @RequestMapping(value = "/repaymentPlan", method = RequestMethod.GET)
  public ResponseEntity<RepayPlanResForm> repaymentPlan(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.repaymentPlan(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "获取还款状态", notes = "获取还款状态")
  @RequestMapping(value = "/getRepayStatus", method = RequestMethod.GET)
  public ResponseEntity<?> getRepayStatus(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil
        .success(orderNewService.getRepayStatus(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "还款充值", notes = "还款充值")
  @RequestMapping(value = "/userRechargeForRepayment/{bankId}/{amt}/{periodNum}",
      method = RequestMethod.GET)
  public ResponseEntity<?> userRecharge(HttpServletRequest request,
                                        @PathVariable("bankId") String bankId, @PathVariable("amt") String amt,
                                        @PathVariable("periodNum") String periodNum) throws Exception {
    return ResponseEntityUtil.success(orderNewService
        .userRechargeForRepayment(this.getUserInfo(request).getId(), bankId, amt, periodNum));
  }

  @ApiOperation(value = "根据订单编号生成充值时第三方的请求参数", notes = "根据订单编号生成充值时第三方的请求参数")
  @RequestMapping(
      value = "/generatePayParasForRepayment/{orderNo}/{loginName}/{seqNum}/{orderPayType}/{issinscd}/{amt}",
      method = RequestMethod.GET)
  public ResponseEntity<?> generatePayParasForRepayment(@PathVariable("orderNo") String orderNo,
                                                        @PathVariable("loginName") String loginName, @PathVariable("seqNum") String seqNum,
                                                        @PathVariable("orderPayType") String orderPayType, @PathVariable("issinscd") String issinscd,
                                                        @PathVariable("amt") String amt, HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderNewService.generatePayParasForRepayment(seqNum, loginName,
        orderNo, this.getUserInfo(request).getId(), orderPayType, issinscd, amt));
  }


}
