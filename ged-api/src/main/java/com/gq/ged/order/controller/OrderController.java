package com.gq.ged.order.controller;

import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.controller.req.*;
import com.gq.ged.order.controller.res.*;
import com.gq.ged.order.service.*;
import com.gq.ged.user.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wrh on 2018/1/19.
 */
@RestController
@RequestMapping(value = "/gedOrder", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "订单", description = "订单")
public class OrderController extends BaseController {
  @Resource
  OrderService orderService;
  @Resource
  OrderNewService orderNewService;
  @Resource
  OrderDetailService orderDetailService;
  @Resource
  OrderPlanService orderPlanService;
  @Resource
  OrderGuarantLoanService orderGuarantLoanService;
  @Resource
  OrderVerifyService orderVerifyService;
  @Resource
  OrderDataMigrationService orderDataMigrationService;


  @ApiOperation(value = "插入申请单信息并推送借款系统(api)", notes = "插入申请单信息并推送借款系统")
  @RequestMapping(value = "/createGedOrder", method = RequestMethod.POST)
  public ResponseEntity<?> createGedOrder(@RequestBody GedOrderReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    System.out.println("======================" + userInfo.getId());
    GedOrderIdResForm gedOrderIdResForm = orderService.createOrder(reqForm, userInfo);
    return ResponseEntityUtil.success(gedOrderIdResForm);
  }

  @ApiOperation(value = "借款系统推送冠E贷新增(借款系统)", notes = "借款系统推送冠E贷新增")
  @RequestMapping(value = "/loanPushCreateOrder", method = RequestMethod.POST)
  public ResponseEntity<?> loanPushCreateOrder(@RequestBody PushOrderListForm reqForm)
      throws Exception {
    orderService.pushLoanCreateOrder(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "查询订单状态(api)", notes = "查询订单状态")
  @RequestMapping(value = "/selectGedOrderStatus", method = RequestMethod.POST)
  public ResponseEntity<?> selectGedOrderStatus(HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    return ResponseEntityUtil.success(orderNewService.selectOrderStatus(userInfo));
  }

  @ApiOperation(value = "订单状态反馈接口(借款系统)", notes = "订单状态反馈接口")
  @RequestMapping(value = "/OrderStateFeedBack", method = RequestMethod.POST)
  public ResponseEntity<Void> OrderStateFeedBack(@RequestBody GedOrderStatusReqForm reqForm)
      throws Exception {
    orderService.OrderStateFeedBack(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "推送冠E通更新期限和金额(借款系统)", notes = "推送冠E通更新期限和金额")
  @RequestMapping(value = "/updateLoanTeAmo", method = RequestMethod.POST)
  public ResponseEntity<?> updateLoanTeAmo(@RequestBody GedOrderUpdateReqForm reqForm)
      throws Exception {
    orderService.updateLoanTeAmo(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "借款记录(api)", notes = "借款记录")
  @RequestMapping(value = "/selectLoanRecord", method = RequestMethod.POST)
  public ResponseEntity<?> selectLoanRecord(HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    List<GedLoanRecordResForm> gedLoanRecordlist = orderService.selectLoanRecord(userInfo);
    return ResponseEntityUtil.success(gedLoanRecordlist);
  }

  @ApiOperation(value = "提现回调(资金端)", notes = "提现回调(资金端)")
  @RequestMapping(value = "/callBackwithDraw", method = RequestMethod.POST)
  public Map<String,Object> callBackwithDraw(@RequestBody CallBackWithDrawReqForm reqForm) throws Exception{
    return orderService.callBackwithDraw(reqForm);
  }

  @ApiOperation(value = "还款计划查询(api)", notes = "还款计划查询")
  @RequestMapping(value = "/selectRepayment", method = RequestMethod.POST)
  public ResponseEntity<?> selectRepayment(@RequestBody GedOrderNoReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    return ResponseEntityUtil.success(orderService.selectRepayment(userInfo, reqForm));
  }

  @ApiOperation(value = "判断是否开户回显(api)", notes = "判断是否开户回显(api)")
  @RequestMapping(value = "/selectWheAccount", method = RequestMethod.POST)
  public ResponseEntity<?> selectWheAccount(HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    return ResponseEntityUtil.success(orderService.selectWheAccount(userInfo));
  }

  @ApiOperation(value = "审核未通过 我知道了(api)", notes = "审核未通过 我知道了")
  @RequestMapping(value = "/auditFail", method = RequestMethod.POST)
  public ResponseEntity<Void> auditFail(@RequestBody GedOrderNoReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    orderService.auditFail(userInfo, reqForm);
    return ResponseEntityUtil.success();
  }


  @ApiOperation(value = "查询借款详情(api)", notes = "查询借款详情(api)")
  @RequestMapping(value = "/queryOrderByCode/{orderNo}", method = RequestMethod.GET)
  public ResponseEntity<OrderDetailResForm> queryOrderByCode(
      @PathVariable("orderNo") String orderNo, HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    OrderDetailResForm resForm = orderDetailService.getOrderDetailInfo(orderNo,userInfo.getId());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "更新实名认证信息(api)", notes = "更新实名认证信息")
  @RequestMapping(value = "/updateCertification", method = RequestMethod.POST)
  public ResponseEntity<?> updateCertification(@RequestBody CertificationReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    orderService.updateCertification(userInfo, reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "提现(Api)", notes = "提现")
  @RequestMapping(value = "/withDrawCash", method = RequestMethod.POST)
  public ResponseEntity<?> withDrawCash(HttpServletRequest request,
      @RequestBody GedOrderNoReqForm reqForm) throws Exception {
    User userInfo = this.getUserInfo(request);
    String orderNo = reqForm.getOrderNo();
    return ResponseEntityUtil.success(orderService.withDraw(userInfo, orderNo));
  }

  @ApiOperation(value = "确认提现(Api)", notes = "确认提现")
  @RequestMapping(value = "/confirWithDrawCash", method = RequestMethod.POST)
  public ResponseEntity<?> confirWithDrawCash(@RequestBody GedOrderNoReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User userInfo = this.getUserInfo(request);
    String orderNo = reqForm.getOrderNo();
    orderService.confirWithDrawCash(userInfo, orderNo);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "账户余额(Api)", notes = "账户余额")
  @RequestMapping(value = "/accountBalance", method = RequestMethod.POST)
  public ResponseEntity<?> accountBalance(HttpServletRequest request,
      @RequestBody GedOrderNoReqForm reqForm) throws Exception {
      User userInfo = this.getUserInfo(request);
    String orderNo = reqForm.getOrderNo();
    return ResponseEntityUtil.success(orderService.accountBalance(orderNo,userInfo.getId()));
  }

  @ApiOperation(value = "担保借款(首页)", notes = "担保借款(首页)")
  @RequestMapping(value = "/guaranteeLoan", method = RequestMethod.POST)
  public ResponseEntity<?> guaranteeLoan(HttpServletRequest request)
          throws Exception {
       User userInfo=this.getUserInfo(request);
    return ResponseEntityUtil.success(orderGuarantLoanService.getOrderGuarantLoanInfo(userInfo.getId()));
  }

  @ApiOperation(value = "担保记录(api)", notes = "担保记录")
  @RequestMapping(value = "/selectGuaranteeRecord", method = RequestMethod.POST)
  public ResponseEntity<?> selectGuaranteeRecord(HttpServletRequest request,@RequestBody PageReqForm reqForm) throws Exception {
      User userInfo = this.getUserInfo(request);
    List<GuaranteeConfirmResForm> gedLoanRecordlist = orderGuarantLoanService.selectGuaranteeRecord(userInfo.getId(),reqForm);
    return ResponseEntityUtil.success(gedLoanRecordlist);
  }

  @ApiOperation(value = "担保确认(api)列表", notes = "担保确认(api)列表")
  @RequestMapping(value = "/selectGuaranteeConfirm", method = RequestMethod.POST)
  public ResponseEntity<?> selectGuaranteeConfirm(HttpServletRequest request,@RequestBody PageReqForm reqForm) throws Exception {
       User userInfo = this.getUserInfo(request);
    List<GuaranteeConfirmResForm> gedLoanConfirmlist = orderGuarantLoanService.selectGuaranteeConfirm(userInfo.getId(),reqForm);
    return ResponseEntityUtil.success(gedLoanConfirmlist);
  }

  @ApiOperation(value = "担保确认(api)按钮", notes = "担保确认(api)按钮")
  @RequestMapping(value = "/ConfirmGuarantee", method = RequestMethod.POST)
  public ResponseEntity<Void> ConfirmGuarantee(HttpServletRequest request,@RequestBody GuarantConfirmReqForm reqForm) throws Exception {
    User userInfo = this.getUserInfo(request);
    orderGuarantLoanService.ConfirmGuarantee(userInfo.getId(),reqForm);
    return ResponseEntityUtil.success();
  }

    @ApiOperation(value = "推送担保记录信息(借款系统)", notes = "推送担保记录信息(借款系统)")
    @RequestMapping(value = "/pushGuaranteeRecord", method = RequestMethod.POST)
    public ResponseEntity<Void> pushGuaranteeRecord(@RequestBody GuaranteeForm reqForm) throws Exception {
        orderGuarantLoanService.pushGuaranteeRecord(reqForm);
        return ResponseEntityUtil.success();
    }

  @ApiOperation(value = "推送还款计划", notes = "推送还款计划")
  @RequestMapping(value = "/pushRepaymentPlan", method = RequestMethod.POST)
  public ResponseEntity<Void> pushRepaymentPlan(@RequestBody PaymentPlanReqForm reqForm)
      throws Exception {
    orderPlanService.generatorOrderPlan(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "新增信审信息(借款系统)", notes = "新增信审信息(借款系统)")
  @RequestMapping(value = "/pushorderVerify", method = RequestMethod.POST)
  public ResponseEntity<Void> pushorderVerify(@RequestBody CreditInfoToRequest reqForm)
          throws Exception {
    orderVerifyService.insertOrderVerify(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "借款系统打回更新冠E贷状态(借款系统)", notes = "借款系统打回更新冠E贷状态")
  @RequestMapping(value = "/OrderStateNotBack", method = RequestMethod.POST)
  public ResponseEntity<Void> OrderStateNotBack(@RequestBody GedOrderNotStatusReqForm reqForm)
          throws Exception {
    orderService.OrderStateNotBack(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "数据迁移(借款系统)", notes = "数据迁移")
  @RequestMapping(value="/pushDataMigration",method = RequestMethod.POST)
  public ResponseEntity<Void> pushDataMigration(@RequestBody OrderDataMigrationReqForm reqForm) throws Exception{
    orderDataMigrationService.pushDataMigration(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "更新合同号信息(借款系统)", notes = "更新合同号信息")
  @RequestMapping(value="/updateContract",method = RequestMethod.POST)
  public ResponseEntity<Void> updateContract(@RequestBody OrderConTractListForm reqForm) throws Exception{
    orderService.updateContract(reqForm);
    return ResponseEntityUtil.success();
  }

    @ApiOperation(value = "借款项目", notes = "借款项目")
    @RequestMapping(value="/loanProgram",method = RequestMethod.POST)
    public ResponseEntity<?> loanProgram(HttpServletRequest request) throws Exception{
         User userInfo=this.getUserInfo(request);
       List<OrderLoanProgramResForm> loanList= orderService.loanProgram(userInfo.getId());
        return ResponseEntityUtil.success(loanList);
    }

  @ApiOperation(value = "合同签署返回PDF和标题", notes = "合同签署返回PDF和标题")
  @RequestMapping(value="/contractSign",method = RequestMethod.POST)
  public ResponseEntity<?> contractSign(@RequestBody GedOrderNoReqForm reqForm) throws Exception{
    return ResponseEntityUtil.success(orderDataMigrationService.contractSign(reqForm.getOrderNo()));
  }

  @ApiOperation(value = "数据迁移(冠E通录入)", notes = "数据迁移(冠E通录入)")
  @RequestMapping(value="/dataMigration",method = RequestMethod.POST)
  public ResponseEntity<Void> dataMigration() throws Exception{
    orderDataMigrationService.dataMigration();
    return ResponseEntityUtil.success();
  }
}
