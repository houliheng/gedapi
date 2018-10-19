package com.gq.ged.message.pc;

import javax.annotation.Resource;

import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.MsgType;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.message.service.MsgService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.user.api.res.UserResForm;
import com.gq.ged.user.pc.req.LoginReqForm;
import com.gq.ged.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Levi on 2018/3/5.
 */

@Api(value = "PC端短信服务", description = "PC端短信服务")
@RestController
@RequestMapping(value = "/pc/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageControllerPC extends BaseController {

  @Resource
  MsgService msgService;

  @Resource
  RedissonClient redissonClient;

  @ApiOperation(value = "找回密码发送短信验证码 -- 需要短信验证码", notes = "找回密码发送短信验证码 -- 需要短信验证码")
  @RequestMapping(value = "/sendMessage/{mobile}/{code}", method = RequestMethod.GET)
  public ResponseEntity<?> sendMessage(@PathVariable("mobile") String mobile,
      @PathVariable("code") String code) throws Exception {
    RBucket rBucket = redissonClient.getBucket("validateCode:" + mobile);
    if (!rBucket.isExists()) {
      throw new BusinessException(Errors.VALIDECODE_EXPIRE_ERROR);
    }
    String rCode = (String) rBucket.get();
    if (!rCode.toLowerCase().equals(code.toLowerCase())) {
      throw new BusinessException(Errors.VALIDECODE_ERROR);
    }
    msgService.sendCheckCodeMessage(mobile, MsgTypeEnum.MSG_TYPE_FORGET);
    return ResponseEntityUtil.success();
  }

  @ApiOperation(value = "绑定手机号发送短信验证码", notes = "绑定手机号发送短信验证码")
  @RequestMapping(value = "/bindMobileMessage/{mobile}", method = RequestMethod.GET)
  public ResponseEntity<?> bindMobileMessage(@PathVariable("mobile") String mobile) throws Exception {
    msgService.sendCheckCodeMessage(mobile, MsgType.BIND_MOBILE);
    return ResponseEntityUtil.success();
  }


}
