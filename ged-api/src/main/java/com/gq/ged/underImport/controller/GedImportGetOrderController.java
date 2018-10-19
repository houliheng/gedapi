package com.gq.ged.underImport.controller;

import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.order.pc.req.OrderFeesDetailReqForm;
import com.gq.ged.underImport.controller.req.GedImportGetOrderJsonReq;
import com.gq.ged.underImport.service.GedImportGetOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Levi on 2018/7/18.
 */

@Api(value = "PC处理线下订单业务", description = "PC处理线下订单业务")
@RestController
@RequestMapping(value = "/pc/offline", produces = MediaType.APPLICATION_JSON_VALUE)
public class GedImportGetOrderController {

    @Autowired
    GedImportGetOrderService gedImportGetOrderService;




    @ApiOperation(value = "借款系统存储线下订单信息  ", notes = "借款系统存储线下订单信息")
    @RequestMapping(value = "/savePushUnderInfos", method = RequestMethod.POST)
    public ResponseEntity<?> savePushUnderInfos(HttpServletRequest request,
                                                  @RequestBody GedImportGetOrderJsonReq req) throws Exception {
        gedImportGetOrderService.savePushInfos(req.getJsonRes());
        return ResponseEntityUtil.success();
    }


    @ApiOperation(value = "test  ", notes = "test")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> test(HttpServletRequest request
                                              ) throws Exception {
        gedImportGetOrderService.dealUnderInfos();
        return ResponseEntityUtil.success();
    }



}
