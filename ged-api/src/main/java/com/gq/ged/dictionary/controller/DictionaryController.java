package com.gq.ged.dictionary.controller;

import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.dictionary.controller.res.BankInfo;
import com.gq.ged.dictionary.controller.res.LoanListResForm;
import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.user.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/28.
 */
@Api(value = "字典信息", description = "字典信息")
@RestController
@RequestMapping(value = "/api/dictionary", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictionaryController {
  @Resource
  DictionaryService dictionaryService;

  @Resource
  FuyouAreaService fuyouAreaService;

  @ApiOperation(value = "获取首页金额", notes = "获取首页金额")
  @RequestMapping(value = "/getPortalAmount", method = RequestMethod.GET)
  public ResponseEntity<String> getPortalAmount(HttpServletRequest request) throws Exception {
    String amount = dictionaryService.getPortalAmount();
    return ResponseEntityUtil.success(amount);
  }

  @ApiOperation(value = "获取借钱List", notes = "获取借钱List")
  @RequestMapping(value = "/getLoanList", method = RequestMethod.GET)
  public ResponseEntity<List<LoanListResForm>> getLoanList(HttpServletRequest request)
      throws Exception {
    List<LoanListResForm> list = dictionaryService.getLoanList();
    return ResponseEntityUtil.success(list);
  }

  @ApiOperation(value = "获取bankList", notes = "获取bankList")
  @RequestMapping(value = "/getBankList", method = RequestMethod.GET)
  public ResponseEntity<List<BankInfo>> getBankList(HttpServletRequest request) throws Exception {
    List<BankInfo> list = dictionaryService.getBanks("BANK_TYPE");
    return ResponseEntityUtil.success(list);
  }


  @ApiOperation(value = "获取富友B2CbankList", notes = "获取bankList")
  @RequestMapping(value = "/getB2cBankList", method = RequestMethod.GET)
  public ResponseEntity<List<BankInfo>> getB2cBankList(HttpServletRequest request)
      throws Exception {
    List<BankInfo> list = dictionaryService.getBanks("BANK_TYPE_FUYOU_B2C");
    return ResponseEntityUtil.success(list);
  }

  @ApiOperation(value = "获取富友B2BbankList", notes = "获取bankList")
  @RequestMapping(value = "/getB2bBankList", method = RequestMethod.GET)
  public ResponseEntity<List<BankInfo>> getB2bBankList(HttpServletRequest request)
      throws Exception {
    List<BankInfo> list = dictionaryService.getBanks("BANK_TYPE_FUYOU_B2B");
    return ResponseEntityUtil.success(list);
  }

  @ApiOperation(value = "获取省列表", notes = "获取bankList")
  @RequestMapping(value = "/getProvinceList", method = RequestMethod.GET)
  public ResponseEntity<List<FuiouArea>> getProvinceList(HttpServletRequest request)
      throws Exception {
    List<FuiouArea> list = fuyouAreaService.selectProvinceList();
    return ResponseEntityUtil.success(list);
  }

  @ApiOperation(value = "获取市列表", notes = "获取bankList")
  @RequestMapping(value = "/getCityList/{provinceId}", method = RequestMethod.GET)
  public ResponseEntity<List<FuiouArea>> getCityList(@PathVariable("provinceId") Long provinceId,
      HttpServletRequest request) throws Exception {
    List<FuiouArea> list = fuyouAreaService.selectCityList(provinceId);
    return ResponseEntityUtil.success(list);
  }
}
