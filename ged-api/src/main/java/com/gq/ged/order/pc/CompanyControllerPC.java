package com.gq.ged.order.pc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gq.ged.order.controller.res.JoinCompanyInfo;
import com.gq.ged.order.controller.res.OrderGroupInfo;
import com.gq.ged.order.service.CompanyService;
import com.gq.ged.order.service.impl.CompanyServiceImpl;
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
import com.gq.ged.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "企业管理", description = "企业管理")
@RestController
@RequestMapping(value = "/pc/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyControllerPC extends BaseController {


    @Resource
    OrderBorrowerService borrowerService;
    @Resource
    OrderService orderService;
    @Resource
    CompanyService companyService;

    @ApiOperation(value = "首页企业管理列表", notes = "首页企业管理列表")
    @RequestMapping(value = "/indexCompanyList", method = RequestMethod.GET)
    public ResponseEntity< List<OrderGroupInfo> > indexCompanyList(HttpServletRequest request) throws Exception {
        return ResponseEntityUtil
                .success(orderService.selectJoinOrderList(this.getUserInfo(request).getId()));
    }
    @ApiOperation(value = "联合授信企业管理列表", notes = "联合授信企业管理列表")
    @RequestMapping(value = "/companyList/{masterCode}", method = RequestMethod.GET)
    public ResponseEntity< List<JoinCompanyInfo> > companyList(HttpServletRequest request , @PathVariable("masterCode") String masterCode) throws Exception {
        return ResponseEntityUtil
                .success(companyService.getJoinCompanyInfos(this.getUserInfo(request).getId() , masterCode));
    }
    @ApiOperation(value = "联合授信企业订单李彪", notes = "联合授信企业订单李彪")
    @RequestMapping(value = "/orderList/{masterCode}", method = RequestMethod.GET)
    public ResponseEntity< List<LoanRecordResForm> > orderList(HttpServletRequest request , @PathVariable("masterCode") String masterCode) throws Exception {
        return ResponseEntityUtil
                .success(companyService.getJoinCompanyOrderInfos(this.getUserInfo(request).getId() , masterCode));
    }

}
