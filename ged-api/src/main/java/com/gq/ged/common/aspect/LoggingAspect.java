package com.gq.ged.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2017/12/5.
 */
@Aspect
@Component
public class LoggingAspect {
  static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  /*
   * 环绕通知需要携带 ProceedingJoinPoint 类型参数 环绕通知类似于动态代理的全过程 ProceedingJoinPoint 类型的参数可以决定是否执行目标方法 环绕通知
   * 必须有返回值 且返回值就是方法的返回值
   */
  @Around("execution(* com.future.core.**.controller.*.*(..))")
  public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String methodName = proceedingJoinPoint.getSignature().getName();
    Object result = null;
    List<Object> args = Arrays.asList(proceedingJoinPoint.getArgs());
    List<Object> list = new ArrayList<Object>();
    String remoteIp = "";
    for (Object obj : args) {
      if (obj instanceof HttpServletRequest) {
        HttpServletRequest request = (HttpServletRequest) obj;
        remoteIp = getRemoteIP(request);
        continue;
      }
      if (obj instanceof HttpServletResponse) {
        continue;
      }
      list.add(obj);
    }

    // 前置通知JSONObject.toJSONString(args)
    logger.info("远程访问ip - " + remoteIp);
    //logger.info("Method - " + methodName + "  begins with" + JSONObject.toJSONString(list));
    // 执行目标方法
    result = proceedingJoinPoint.proceed();
    // 上报信息id 方法名 入参

    return result;
  }

  public static String getRemoteIP(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
