package com.gq.ged.common.web;

import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.ChanelStyle;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.utils.TokenInfo;
import com.gq.ged.common.utils.TokenUtil;
import com.gq.ged.user.dao.model.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
public class BaseController {
  static Logger logger = LoggerFactory.getLogger(BaseController.class);

  @Resource
  RedissonClient redissonClient;




/*   public User getUserInfo(HttpServletRequest request) throws Exception { User user = new User();
   user.setId(999379l);
   user.setCreateTime(new Date());
   user.setMobile("18512345678");
   user.setUsername("冠易贷");
   return user;
   }*/

  public User getUserInfo(HttpServletRequest request) throws Exception {
    String tokenId = CookieUtils.getCookieTokenId(request);
    TokenInfo tokenInfo = TokenUtil.decodeToken(tokenId);
    String chanelStyle = "";
    if (JudgeIsMoblie(request)) {
      logger.info("手机端........!");
      chanelStyle = ChanelStyle.MOBILE.getValue();
    } else {
      logger.info("PC端...........!");
      chanelStyle = ChanelStyle.PC.getValue();
    }
    User user = (User) redissonClient.getBucket(Constant.CURR_USER_INFO_TOKEN + ":" + chanelStyle
        + ":" + tokenInfo.getUid() + ":" + tokenId).get();
    if (user == null) {
      logger.info("获取用户信息失败!");
      throw new BusinessException(Errors.SYSTEM_TOKEN_ERROR);
    }
    return user;
  }

  public void setUserInfo(HttpServletRequest request, User user) throws Exception {
    String tokenId = CookieUtils.getCookieTokenId(request);
    TokenInfo tokenInfo = TokenUtil.decodeToken(tokenId);
    String chanelStyle = "";
    if (JudgeIsMoblie(request)) {
      logger.info("冠e贷2.0 >>> 手机端........!");
      chanelStyle = ChanelStyle.MOBILE.getValue();
    } else {
      logger.info("冠e贷2.0 >>> PC端...........!");
      chanelStyle = ChanelStyle.PC.getValue();
    }
    redissonClient.getBucket(Constant.CURR_USER_INFO_TOKEN + ":" + chanelStyle + ":"
        + tokenInfo.getUid() + ":" + tokenId).set(user);
  }



  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> exceptionHandler(Exception ex) {
    logger.error("异常信息", ex);
    ResponseEntity re = new ResponseEntity();
    re.setException(ex.getMessage());
    return re;
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Void> runtimeExceptionHandler(RuntimeException ex) {
    logger.error("异常信息", ex);
    ResponseEntity re = new ResponseEntity();
    re.setException(ex.getMessage());
    return re;
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Void> confirmationExceptionHandler(BusinessException ex) {
    logger.error("error--->" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "异常信息",
        ex);
    ResponseEntity re = new ResponseEntity();
    re.setCode(String.valueOf(ex.getCode()));
    re.setException(ex.getMessage());
    re.setData(ex.getData());
    return re;
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<Void> bindExceptionHandler(BindException ex) {
    ResponseEntity re = new ResponseEntity();
    re.setCode("-20");
    re.setException(ex.getMessage());
    return re;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<Void> validExceptionHandler(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    JSONObject jsonObject = new JSONObject();
    for (FieldError error : fieldErrors) {
      jsonObject.put(error.getField(), error.getDefaultMessage());
    }

    ResponseEntity re = new ResponseEntity();
    re.setCode("-30");
    re.setException(jsonObject.toString());
    return re;
  }

  // 判断是否为手机浏览器
  public static boolean JudgeIsMoblie(HttpServletRequest request) {
    boolean isMoblie = false;
    String[] mobileAgents = {"iphone", "android","ipad", "phone", "mobile", "wap", "netfront",
        "java", "opera mobi", "opera mini", "ucweb", "windows ce", "symbian", "series", "webos",
        "sony", "blackberry", "dopod", "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu",
        "midp", "cldc", "motorola", "foma", "docomo", "up.browser", "up.link", "blazer", "helio",
        "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith", "palmsource", "alcatel",
        "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",
        "smartphone", "iemobile", "spice", "bird", "zte-", "longcos", "pantech", "gionee",
        "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320", "240x320",
        "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
        "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq",
        "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
        "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper",
        "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany",
        "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony",
        "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v", "voda",
        "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
        "Googlebot-Mobile"};
    if (request.getHeader("User-Agent") != null) {
      String agent = request.getHeader("User-Agent");
      logger.info("User-Agent===========" + agent);
      for (String mobileAgent : mobileAgents) {
        if (agent.toLowerCase().indexOf(mobileAgent) >= 0 && agent.toLowerCase().indexOf("macintosh") <= 0) {
          isMoblie = true;
          break;
        }
      }
    }
    return isMoblie;
  }
}
