package com.gq.ged.user.pc;

import com.gq.ged.common.enums.ChanelStyle;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.utils.ValidateCode;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.user.api.res.UserResForm;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.pc.req.LoginReqForm;
import com.gq.ged.user.pc.req.UserBindForm;
import com.gq.ged.user.pc.req.UserPassForm;
import com.gq.ged.user.pc.req.UserRetrieveForm;
import com.gq.ged.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "PC端用户服务", description = "PC端用户服务")
@RestController
@RequestMapping(value = "/pc/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerPC extends BaseController {
  @Resource
  UserService userService;

  @Resource
  RedissonClient redissonClient;

  @ApiOperation(value = "用户登录", notes = "用户登录")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<UserResForm> queryUserTitle(@RequestBody LoginReqForm reqForm)
      throws Exception {
    UserResForm resForm =
        userService.loginUser(reqForm.getUserName(), reqForm.getPwd(), ChanelStyle.PC.getValue());
    return ResponseEntityUtil.success(resForm);
  }

  @ApiOperation(value = "找回密码", notes = "找回密码")
  @RequestMapping(value = "/retrievePasswd", method = RequestMethod.POST)
  public ResponseEntity<?> retrievePasswd(@RequestBody UserRetrieveForm reqForm) throws Exception {
    userService.retrievePasswdPC(reqForm);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "修改密码", notes = "修改密码")
  @RequestMapping(value = "/updatePasswd", method = RequestMethod.POST)
  public ResponseEntity<?> updatePasswd(@RequestBody UserPassForm reqForm,
      HttpServletRequest request) throws Exception {
    User user = this.getUserInfo(request);
    userService.updatePassword(reqForm, user);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "绑定手机号", notes = "绑定手机号")
  @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
  public ResponseEntity<?> bindMobile(@RequestBody UserBindForm reqForm, HttpServletRequest request)
      throws Exception {
    User user = this.getUserInfo(request);
    userService.bindMobile(reqForm, user);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "生成图片验证码", notes = "生成图片验证码")
  @RequestMapping(value = "/validateCode/{mobile}", method = RequestMethod.GET)
  public ResponseEntity<?> validateCode(@PathVariable("mobile") String mobile,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 设置响应的类型格式为图片格式
    response.setContentType("image/jpeg");
    // 禁止图像缓存。
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    ValidateCode vCode = ValidateCode.getInstance();
    redissonClient.getBucket("validateCode:" + mobile).set(vCode.getCode(), 3, TimeUnit.MINUTES);
    /*
     * HttpSession session = request.getSession();
     * 
     * 
     * session.setAttribute("code",vCode.getCode() );
     */
    vCode.write(response.getOutputStream());
    return null;
  }




}
