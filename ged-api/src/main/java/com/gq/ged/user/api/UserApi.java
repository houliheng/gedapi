package com.gq.ged.user.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gq.ged.annotation.THttpService;
import com.gq.ged.common.enums.ChanelStyle;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.utils.StringUtil;
import com.gq.ged.user.api.res.UserResForm;
import com.gq.ged.user.service.UserService;
import com.gq.ged.user.tmodel.UserLoginThriftForm;
import com.gq.ged.user.tmodel.UserRegisterThriftForm;
import com.gq.ged.user.tmodel.UserRetrieveThriftForm;
import com.gq.ged.user.tmodel.UserTService;
import org.apache.thrift.TException;

import javax.annotation.Resource;

/**
 * Created by wyq_tomorrow on 2018/1/16.
 */
@THttpService("/api/userTService")
public class UserApi implements UserTService.Iface {

  @Resource
  UserService userService;

  @Override
  public String loginUser(UserLoginThriftForm loginUser) throws TException {
    UserResForm resForm = userService.loginUser(loginUser.mobile, loginUser.password, ChanelStyle.MOBILE.getValue());
    return JSONObject.toJSONString(ResponseEntityUtil.success(resForm),
        SerializerFeature.WriteNullStringAsEmpty);
  }

  @Override
  public String registerUser(UserRegisterThriftForm registerUser) {
    UserResForm userResForm = userService.registerUser(registerUser);
    return JSONObject.toJSONString(ResponseEntityUtil.success(userResForm));
  }

  @Override
  public String retrievePasswd(UserRetrieveThriftForm retrieveForm) throws TException {
    UserResForm userResForm = userService.retrievePasswd(retrieveForm);
    return JSONObject.toJSONString(ResponseEntityUtil.success(userResForm));
  }

  @Override
  public String queryUser(String mobile) throws TException {
    if (StringUtil.isMoblie(mobile)) {
      userService.queryUserByMobile(mobile);
    }else{
      userService.SocialCreditCodeByUser(mobile);
    }
    return JSONObject.toJSONString(ResponseEntityUtil.success());
  }

  @Override
  public String checkValidCode(String mobile, String code) throws TException {
    userService.checkValidCode(mobile, code);
    return JSONObject.toJSONString(ResponseEntityUtil.success());
  }
}
