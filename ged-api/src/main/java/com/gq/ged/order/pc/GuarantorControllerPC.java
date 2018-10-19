package com.gq.ged.order.pc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gq.ged.order.controller.req.GuarantConfirmReqForm;
import com.gq.ged.order.pc.req.ContractRepayPlanDetail;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.order.service.OrderGuarantLoanService;
import com.gq.ged.user.dao.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.service.OrderBorrowerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "PC端担保人服务", description = "PC端担保人服务")
@RestController
@RequestMapping(value = "/pc/guarantor", produces = MediaType.APPLICATION_JSON_VALUE)
public class GuarantorControllerPC extends BaseController {

  @Resource
  OrderGuarantLoanService guarantLoanService;

  @ApiOperation(value = "担保人首页", notes = "担保人首页")
  @RequestMapping(value = "/repaymentsList", method = RequestMethod.GET)
  public ResponseEntity<GuarantorIndexResForm> repaymentsList(HttpServletRequest request)
      throws Exception {
    return ResponseEntityUtil.success(guarantLoanService.index(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "担保人确认列表", notes = "担保人确认列表")
  @RequestMapping(value = "/confirmGuaranteeList", method = RequestMethod.GET)
  public ResponseEntity<List<ConfirmGuaranteeListResForm>> confirmGuaranteeList(
      HttpServletRequest request) throws Exception {
    return ResponseEntityUtil
        .success(guarantLoanService.confirmGuaranteeList(this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "担保人记录列表", notes = "担保人记录列表")
  @RequestMapping(value = "/guaranteeRecordList/{pageNum}/{pageSize}", method = RequestMethod.GET)
  public ResponseEntity<PageInfo<GuaranteeRecordListResForm>> guaranteeRecordList(
      HttpServletRequest request, @PathVariable("pageNum") Integer pageNum,
      @PathVariable("pageSize") Integer pageSize) throws Exception {
    return ResponseEntityUtil.success(guarantLoanService
        .guaranteeRecordList(this.getUserInfo(request).getId(), pageNum, pageSize));
  }

  @ApiOperation(value = "担保人确认详情页", notes = "担保人确认详情页")
  @RequestMapping(value = "/confirmGuaranteeDetail/{guaranteeId}", method = RequestMethod.GET)
  public ResponseEntity<ConfirmGuaranteeDetailResForm> confirmGuaranteeDetail(
      HttpServletRequest request, @PathVariable("guaranteeId") Long guaranteeId) throws Exception {
    return ResponseEntityUtil.success(
        guarantLoanService.confirmGuaranteeDetail(guaranteeId, this.getUserInfo(request).getId()));
  }

  @ApiOperation(value = "担保人记录详情页", notes = "担保人记录详情页")
  @RequestMapping(value = "/guaranteeRecordDetail/{guaranteeId}", method = RequestMethod.GET)
  public ResponseEntity<GuaranteeRecordDetailResForm> guaranteeRecordDetail(
      HttpServletRequest request, @PathVariable("guaranteeId") Long guaranteeId) throws Exception {
    return ResponseEntityUtil.success(
        guarantLoanService.guaranteeRecordDetail(guaranteeId, this.getUserInfo(request).getId()));
  }

    @ApiOperation(value = "担保确认(api)按钮", notes = "担保确认(api)按钮")
    @RequestMapping(value = "/ConfirmGuarantee", method = RequestMethod.POST)
    public ResponseEntity<Void> ConfirmGuarantee(HttpServletRequest request,@RequestBody GuarantConfirmReqForm reqForm) throws Exception {
        User userInfo = this.getUserInfo(request);
        guarantLoanService.ConfirmGuarantee(userInfo.getId(),reqForm);
        return ResponseEntityUtil.success();
    }

}
