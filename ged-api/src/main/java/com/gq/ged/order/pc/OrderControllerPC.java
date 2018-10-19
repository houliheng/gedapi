package com.gq.ged.order.pc;

import com.gq.ged.account.service.SignService;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.pc.req.*;
import com.gq.ged.order.pc.res.CheckServiceFeeResForm;
import com.gq.ged.order.pc.res.GeneratePayParas;
import com.gq.ged.order.pc.res.RepayPlanResForm;
import com.gq.ged.order.pc.res.WithDrawViewResForm;
import com.gq.ged.order.service.OrderPlanService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "PC端订单服务", description = "PC端订单服务")
@RestController
@RequestMapping(value = "/pc/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderControllerPC extends BaseController {
  @Resource
  OrderService orderService;
  @Resource
  OrderPlanService orderPlanService;
  @Resource
  SignService signService;

  @Resource
  GedOrderMapper gedOrderMapper;

  @Resource
  PayService payService;

  @ApiOperation(value = "资金管理页面跳转选择", notes = "资金管理页面跳转选择")
  @RequestMapping(value = "/moneyManage", method = RequestMethod.GET)
  public ResponseEntity<OrderFeesDetailReqForm> moneyManage(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil.success(orderService.orderManage(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "费用支付页面详情", notes = "费用支付页面详情")
  @RequestMapping(value = "/payFeeInfos", method = RequestMethod.GET)
  public ResponseEntity<?> payFeeInfos(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderService.payFeeInfos(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "订单查看页面详情", notes = "订单查看页面详情")
  @RequestMapping(value = "/orderView", method = RequestMethod.GET)
  public ResponseEntity<?> orderView(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderService.orderView(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "根据订单编号生成充值时第三方的请求参数", notes = "根据订单编号生成充值时第三方的请求参数")
  @RequestMapping(
      value = "/generatePayParas/{orderNo}/{loginName}/{seqNum}/{orderPayType}/{issinscd}",
      method = RequestMethod.GET)
  public ResponseEntity<?> generatePayParas(@PathVariable("orderNo") String orderNo,
      @PathVariable("loginName") String loginName, @PathVariable("seqNum") String seqNum,
      @PathVariable("orderPayType") String orderPayType, @PathVariable("issinscd") String issinscd,
      HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderService.generatePayParas(seqNum, loginName, orderNo,
        this.getUserInfo(request).getId(), orderPayType, issinscd));
  }


  @ApiOperation(value = "第三方支付成功后保  回调的接口", notes = "第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/saveResParasAfterPay", method = RequestMethod.POST)
  public ResponseEntity<?> saveResParasAfterPay(HttpServletRequest request,
      @RequestBody PayCallBackReqForm res) throws Exception {
    return ResponseEntityUtil.success(orderService.saveResParasAfterPay(res));
  }

  @ApiOperation(value = "用户支付", notes = "用户支付")
  @RequestMapping(value = "/userRecharge/{bankId}", method = RequestMethod.GET)
  public ResponseEntity<?> userRecharge(HttpServletRequest request,
      @PathVariable("bankId") String bankId) throws Exception {
    return ResponseEntityUtil
        .success(orderService.userRecharge(this.getUserInfo(request).getId(), bankId));
  }


  @ApiOperation(value = "用户充值成功回调资金端", notes = "用户充值成功回调资金端")
  @RequestMapping(value = "/userRechargeCallBack", method = RequestMethod.GET)
  public ResponseEntity<?> userRechargeCallBack(HttpServletRequest request) throws Exception {
    orderService.userRechargeCallBack(this.getUserInfo(request).getId());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "用户缴费", notes = "用户缴费")
  @RequestMapping(value = "/userCompensation", method = RequestMethod.GET)
  public ResponseEntity<?> userCompensation(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil
        .success(orderService.userCompensation(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "修改充值中状态", notes = "修改充值中状态")
  @RequestMapping(value = "/paying/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<?> paying(HttpServletRequest request,
      @PathVariable("orderNo") String orderNo) throws Exception {
    orderService.paying(this.getUserInfo(request).getId(), orderNo);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "查询缴费状态", notes = "查询缴费状态")
  @RequestMapping(value = "/checkServiceStatus", method = RequestMethod.GET)
  public ResponseEntity<CheckServiceFeeResForm> checkServiceStatus(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil
        .success(orderService.CheckServiceFeeStatus(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "保存指定订单费用支付时选择的bankid", notes = "保存指定订单费用支付时选择的bankid")
  @RequestMapping(value = "/saveBankId/{orderNo}/{bankId}", method = RequestMethod.GET)
  public ResponseEntity<?> saveBankId(HttpServletRequest request,
      @PathVariable("orderNo") String orderNo, @PathVariable("bankId") String bankId)
      throws Exception {
    return ResponseEntityUtil
        .success(orderService.saveBankId(orderNo, bankId, this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "提现", notes = "提现")
  @RequestMapping(value = "/withDr/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<?> withDr(HttpServletRequest request,
      @PathVariable("orderNo") String orderNo) throws Exception {
    return ResponseEntityUtil
        .success(orderService.confirWithDrawCash(this.getUserInfo(request), orderNo));
  }


 @ApiOperation(value = "第三方支付成功后保  回调的接口", notes = "第三方支付成功后保  回调的接口")
  @RequestMapping(value = "/updateFinishRepay", method = RequestMethod.POST)
  public ResponseEntity<?> updateFinishRepay(@RequestBody UpdateFinishRepayReqForm res)
      throws Exception {
    orderService.updateFinishRepay(res.getOrderNo());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "提现详情页面", notes = "提现详情页面")
  @RequestMapping(value = "/beforeWithDr/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<WithDrawViewResForm> beforeWithDr(HttpServletRequest request,
      @PathVariable("orderNo") String orderNo) throws Exception {
    return ResponseEntityUtil
        .success(orderService.beforeWithDr(orderNo, this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "第三方支付(还款)成功后  回调的接口", notes = "第三方支付(还款)成功后  回调的接口")
  @RequestMapping(value = "/saveRechargeAfterPay", method = RequestMethod.POST)
  public ResponseEntity<?> saveRechargeAfterPay(HttpServletRequest request,
      @RequestBody ParasAfterPayReqForm res) throws Exception {
    return ResponseEntityUtil.success(orderService.saveRepaymentAfterPay(res));
  }

  @ApiOperation(value = "借款系统回调接口 -- 根据订单号修改serviceFlag", notes = "借款系统回调接口 -- 根据订单号修改serviceFlag")
  @RequestMapping(value = "/updateServiceFlagByOrderNo", method = RequestMethod.POST)
  public ResponseEntity<?> updateServiceFlagByOrderNo(HttpServletRequest request,
      @RequestBody RpdateServiceFlagByOrderNoReqForm req) throws Exception {
    return ResponseEntityUtil.success(orderService.updateServiceFlagByOrderNo(req));
  }

 /* @ApiOperation(value = "test", notes = "test")
  @RequestMapping(value = "/test/{orderCode}", method = RequestMethod.GET)
  public ResponseEntity<?> test(HttpServletRequest request,
      @PathVariable("orderCode") String orderCode) throws Exception {
    return ResponseEntityUtil.success(orderService.getContractUrlByOrderNo(orderCode));
  }*/
//  @ApiOperation(value = "test", notes = "test")
//  @RequestMapping(value = "/test/{pageNum}/{pageSize}", method = RequestMethod.GET)
//  public ResponseEntity<?> test(HttpServletRequest request,
//      @PathVariable("pageNum") Integer pageNum , @PathVariable("pageSize") Integer pageSize) throws Exception {
//
//    pageNum = pageNum == null ? 1 : pageNum;
//    pageSize = pageSize == null ? 10 : pageSize;
////    PageHelper.startPage(pageNum, pageSize);
////    List<GedOrder> list = this.gedOrderMapper.selectByExample(new GedOrderExample());
//    GedOrder order =  new GedOrder();
//    order.setUserId(11l);
//
//    PageInfo<GedOrder> p = orderService.getOrdersPageByCondition(order,pageNum,pageSize);
//    return ResponseEntityUtil.success( p);
//  }



  @ApiOperation(value = "还款计划", notes = "还款计划")
  @RequestMapping(value = "/repaymentPlan", method = RequestMethod.GET)
  public ResponseEntity<RepayPlanResForm> repaymentPlan(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil
        .success(orderService.repaymentPlan(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "获取还款状态", notes = "获取还款状态")
  @RequestMapping(value = "/getRepayStatus", method = RequestMethod.GET)
  public ResponseEntity<?> getRepayStatus(HttpServletRequest request) throws Exception {
    return ResponseEntityUtil
        .success(orderService.getRepayStatus(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "还款充值 / 提前结清充值", notes = "还款充值 / 提前结清充值")
  @RequestMapping(value = "/userRechargeForRepayment/{bankId}/{amt}/{periodNum}/{type}",
      method = RequestMethod.GET)
  public ResponseEntity<?> userRecharge(HttpServletRequest request,
      @PathVariable("bankId") String bankId, @PathVariable("amt") String amt,
      @PathVariable("periodNum") String periodNum , @PathVariable("type") Integer type) throws Exception {
    return ResponseEntityUtil.success(orderService
        .userRechargeForRepayment(this.getUserInfo(request).getId(), bankId, amt, periodNum , type) );
  }

  @ApiOperation(value = "根据订单编号生成充值时第三方的请求参数", notes = "根据订单编号生成充值时第三方的请求参数")
  @RequestMapping(
      value = "/generatePayParasForRepayment/{orderNo}/{loginName}/{seqNum}/{orderPayType}/{issinscd}/{amt}",
      method = RequestMethod.GET)
  public ResponseEntity<?> generatePayParasForRepayment(@PathVariable("orderNo") String orderNo,
      @PathVariable("loginName") String loginName, @PathVariable("seqNum") String seqNum,
      @PathVariable("orderPayType") String orderPayType, @PathVariable("issinscd") String issinscd,
      @PathVariable("amt") String amt, HttpServletRequest request) throws Exception {
    return ResponseEntityUtil.success(orderService.generatePayParasForRepayment(seqNum, loginName,
        orderNo, this.getUserInfo(request).getId(), orderPayType, issinscd, amt));
  }

}
