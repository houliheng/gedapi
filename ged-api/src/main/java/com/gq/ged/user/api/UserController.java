package com.gq.ged.user.api;

import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.ChanelStyle;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.user.api.req.PwdReqForm;
import com.gq.ged.user.api.req.UserLoginReqForm;
import com.gq.ged.user.api.req.UserQueryReqForm;
import com.gq.ged.user.api.req.UserRegisterReqForm;
import com.gq.ged.user.api.res.*;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.ContractService;
import com.gq.ged.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.thrift.TException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wyq_tomorrow on 2018/1/22.
 */
@Api(value = "用户信息", description = "用户信息")
@RestController
@RequestMapping(value = "/iface/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {
  @Resource
  UserService userService;

  @ApiOperation(value = "登录", notes = "登录")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<UserResForm> loginUser(@RequestBody UserLoginReqForm loginUser)
      throws TException {
    UserResForm resForm =
        userService.loginUser(loginUser.mobile, loginUser.password, ChanelStyle.MOBILE.getValue());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "查询用户是否注册(借款系统)", notes = "查询用户是否注册(借款系统)")
  @RequestMapping(value = "/queryUserByLoanSys", method = RequestMethod.POST)
  public ResponseEntity<Object> queryUserByLoanSys(@RequestBody UserQueryReqForm reqForm)
      throws Exception {
    UserQueryResForm resForm = userService.queryUserByLoanSys(reqForm);
    if (resForm.getResultCode() == 0) {
      return ResponseEntityUtil.success(resForm);
    } else {
      if (reqForm.getUserRole() == 1) {
        return ResponseEntityUtil.fail(Errors.USER_GUARANTOR_COMPANY_EXISTS_ERROR, resForm);
      } else {
        return ResponseEntityUtil.fail(Errors.USER_COMPANY_EXISTS_ERROR, resForm);
      }
    }

  }

  @ApiOperation(value = "发送注册请求(借款系统)", notes = "发送注册请求(借款系统)")
  @RequestMapping(value = "/registerUserByLoanSys", method = RequestMethod.POST)
  public ResponseEntity<Void> registerUserByLoanSys(
      @RequestBody UserRegisterReqForm reqForm) throws Exception {
    userService.registerUserByLoanSys(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
  @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
  public ResponseEntity<Void> queryUserInfo(HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    userService.queryUserInfoById(user.getId());
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "我的-查询用户姓名", notes = "我的-查询用户姓名")
  @RequestMapping(value = "/queryUserTitle", method = RequestMethod.POST)
  public ResponseEntity<UserTitleResForm> queryUserTitle(HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    UserTitleResForm resForm = userService.queryUserTitle(user.getId());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "更新用户密码", notes = "更新用户密码")
  @RequestMapping(value = "/updateUserPwd", method = RequestMethod.POST)
  public ResponseEntity<UserResForm> updateUserPwd(@RequestBody PwdReqForm pwdReqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    UserResForm userResForm =
        userService.updateUserPwd(pwdReqForm, user, pwdReqForm.getChanelStyle().getValue());
    return ResponseEntityUtil.success(userResForm);
  }
}
