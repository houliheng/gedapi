package com.gq.ged.order.pc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gq.ged.order.service.OrderBorrowerNewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.pc.req.ContractRepayPlanDetail;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.order.service.OrderBorrowerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by zhaozb on 2018/6/5.
 */

@Api(value = "PC端借款人服务", description = "PC端借款人服务")
@RestController
@RequestMapping(value = "/v2/pc/borrower", produces = MediaType.APPLICATION_JSON_VALUE)
public class BorroweNewrControllerPC extends BaseController {


    @Resource
    OrderBorrowerNewService borrowerService;

    @ApiOperation(value = "2.0首页", notes = "2.0首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ResponseEntity<BorrowerIndexResForm> newIndex(HttpServletRequest request) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.index(this.getUserInfo(request)));
    }

    @ApiOperation(value = "借款记录", notes = "借款记录")
    @RequestMapping(value = "/loanRecords/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<PageInfo<LoanRecordResForm> > loanRecords(HttpServletRequest  request , @PathVariable("pageNum")Integer pageNum ,   @PathVariable("pageSize")Integer pageSize) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.getLoanRecordsPageByUserId(this.getUserInfo(request).getId() ,pageNum , pageSize ));
    }
    @ApiOperation(value = "还款列表", notes = "还款列表")
    @RequestMapping(value = "/repaymentsList", method = RequestMethod.GET)
    public ResponseEntity<List<ContractRepayPlanDetail>> repaymentsList(HttpServletRequest request) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.myRepayments(this.getUserInfo(request).getId()));
    }

    @ApiOperation(value = "借款详情", notes = "借款详情")
    @RequestMapping(value = "/orderDetail/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<OrderDetailResForm> orderDetail(HttpServletRequest request, @PathVariable("orderNo") String orderNo) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.orderDetail(orderNo));
    }

    @ApiOperation(value = "服务费详情页", notes = "服务费详情页")
    @RequestMapping(value = "/serviceFeeDetail/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<ServiceFeeDetailResForm> serviceFeeDetail(HttpServletRequest request, @PathVariable("orderNo") String orderNo) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.serviceFeeDetail(orderNo));
    }

    @ApiOperation(value = "提现详情页", notes = "提现详情页")
    @RequestMapping(value = "/withdrawDetail/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<WithdrawDetailResForm> withdrawDetail(HttpServletRequest request, @PathVariable("orderNo") String orderNo) throws Exception {
        return ResponseEntityUtil
                .success(borrowerService.withdrawDetail(orderNo));
    }
//    @ApiOperation(value = "test", notes = "test")
//    @RequestMapping(value = "/test/{userId}", method = RequestMethod.GET)
//    public ResponseEntity<?> test(HttpServletRequest request,  @PathVariable("userId") Long userId) throws Exception {
//        return ResponseEntityUtil
//                .success(borrowerService.planDetailByUserId(userId));
//    }
//    @ApiOperation(value = "test1", notes = "test1")
//    @RequestMapping(value = "/test1/{userId}", method = RequestMethod.GET)
//    public ResponseEntity<?> test1(HttpServletRequest request,  @PathVariable("userId") Long userId) throws Exception {
//        return ResponseEntityUtil
//                .success(borrowerService.borrowConditonByUserId(userId));
//    }

}
