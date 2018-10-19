package com.gq.ged.account.controller;

import com.gq.ged.account.controller.req.*;
import com.gq.ged.account.controller.res.AccountInitResForm;
import com.gq.ged.account.controller.res.SignResForm;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.dao.model.AccountCompanyVo;
import com.gq.ged.account.dao.model.AccountVo;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.account.service.SignService;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.utils.copy.BeanCopyTools;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.user.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@Api(value = "账户信息", description = "账户信息")
@RestController
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController extends BaseController {
  @Resource
  AccountService accountService;

  @Resource
  SignService signService;

  @ApiOperation(value = "企业账户基本信息", notes = "补充账户基本信息")
  @RequestMapping(value = "/callAccountBaseInfo", method = RequestMethod.POST)
  public ResponseEntity<Long> callAccountBaseInfo(@RequestBody AccountBaseReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    Long id = accountService.callAccountBaseInfo(reqForm, user.getId());
    return ResponseEntityUtil.success(id);
  }

  @ApiOperation(value = "企业完善账户信息", notes = "完善账户信息")
  @RequestMapping(value = "/callAccountInfo", method = RequestMethod.POST)
  public ResponseEntity<Void> callAccountInfo(@RequestBody AccountBankReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    accountService.callAccountInfo(reqForm, user.getId());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "企业补充账户扩展信息", notes = "补充账户扩展信息")
  @RequestMapping(value = "/callAccountExtPicInfo", method = RequestMethod.POST)
  public ResponseEntity<Void> callAccountInfo(@RequestBody AccountExtListForm list,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    accountService.callAccountExtPicInfo(list, user.getId());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "查询企业账户信息", notes = "查询企业账户信息")
  @RequestMapping(value = "/queryCompanyAccountInfo", method = RequestMethod.GET)
  public ResponseEntity<AccountCompany> queryCompanyAccountInfoById(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    AccountCompany accountCompany = accountService.queryCompanyAccountInfoById(user.getId());
    return ResponseEntityUtil.success(accountCompany);
  }

  @ApiOperation(value = "查询银行卡列表", notes = "查询银行卡列表")
  @RequestMapping(value = "/queryAccountList", method = RequestMethod.GET)
  public ResponseEntity<List<Account>> queryAccountList(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    List<Account> list = accountService.getAccountInfo(user.getId());
    return ResponseEntityUtil.success(list);
  }

  @ApiOperation(value = "查询个人开户信息", notes = "查询个人开户信息")
  @RequestMapping(value = "/queryAccountByIdCardNo/{idCardNo}", method = RequestMethod.POST)
  public ResponseEntity<Account> queryAccountByIdCardNo(@PathVariable("idCardNo") String idCardNo,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    Account account = accountService.getAccountByIdCardNo(idCardNo, user.getId());
    return ResponseEntityUtil.success(account);
  }

  @ApiOperation(value = "存量创建个人开户信息", notes = "存量创建个人开户信息")
  @RequestMapping(value = "/createAccountByCopyInfo", method = RequestMethod.POST)
  public ResponseEntity<Void> createAccountByCopyInfo(@RequestBody CorporationReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    accountService.createAccountByCopyInfo(reqForm, user);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "增量创建个人开户信息", notes = "增量创建个人开户信息")
  @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
  public ResponseEntity<Void> createAccount(@RequestBody CorporationReqForm reqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    accountService.createCorporationAccount(reqForm, user);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "签约", notes = "签约")
  @RequestMapping(value = "/signed", method = RequestMethod.POST)
  public ResponseEntity<?> signed(HttpServletRequest req) throws Exception {
    User userInfo = this.getUserInfo(req);
    SignResForm signResForm = signService.sign(userInfo.getId());
    return ResponseEntityUtil.success(signResForm);
  }

  @ApiOperation(value = "签约成功", notes = "签约成功")
  @RequestMapping(value = "/signedSuccess", method = RequestMethod.POST)
  public ResponseEntity<?> signedSuccess(@RequestBody @Valid SignSuccessReqForm reqForm)
      throws Exception {
    Integer res = signService.signSuccess(reqForm);
    return ResponseEntityUtil.success(res);
  }

  @ApiOperation(value = "初始化账户信息", notes = "初始化账户信息")
  @RequestMapping(value = "/initAccountInfo", method = RequestMethod.GET)
  public ResponseEntity<AccountInitResForm> initAccountInfo(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    AccountInitResForm resForm = accountService.initAccountInfo(user.getId());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "查询用户开户标识", notes = "查询用户开户标识")
  @RequestMapping(value = "/queryUserAccountFlag", method = RequestMethod.GET)
  public ResponseEntity<AccountFlagResForm> queryUserAccountFlag(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    AccountFlagResForm resForm = accountService.queryUserAccountFlag(user.getId());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "根据不同标识返回账户信息", notes = "根据不同标识返回账户信息")
  @RequestMapping(value = "/queryAccountInfoByFlag/{flag}", method = RequestMethod.GET)
  public ResponseEntity<?> queryAccountInfoByFlag(@PathVariable("flag") Integer flag,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);

    if (flag == 0) {
      AccountCompany accountCompany = accountService.queryCompanyAccountInfoById(user.getId());
      AccountCompanyVo accountCompanyVo = new AccountCompanyVo();

      if (accountCompany != null && StringUtils.isNotBlank(accountCompany.getAccountsPermitsUrl())
          && StringUtils.isNotBlank(accountCompany.getBusinessLicenceUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardBackUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardFaceUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardHoldUrl())) {

        BeanCopyTools.copyProperties(accountCompany, accountCompanyVo);
        accountCompanyVo.setAccountFlag(1);// 1表示已开户
      } else {
        accountCompanyVo.setAccountFlag(0);// 1表示已开户
      }

      return ResponseEntityUtil.success(accountCompanyVo);
    } else if (flag == 1) {

      Account account = accountService.getAccountByUserId(user.getId());
      AccountVo accountVo = new AccountVo();

      if (account != null && null != account.getStatus()) {
        BeanCopyTools.copyProperties(account, accountVo);
        accountVo.setAccountFlag(1);// 1表示已开户
        accountVo.setStatus(account.getStatus());
      } else {
        accountVo.setAccountFlag(0);// 0表示未开户
        accountVo.setStatus(0);
      }
      return ResponseEntityUtil.success(accountVo);
    } else {
      throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR);
    }
  }
}
