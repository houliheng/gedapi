package com.gq.ged.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by wyq_tomorrow on 2018/1/12.
 */
@ControllerAdvice(basePackages = "com.future.core")
public class RequestObjectAdvice implements RequestBodyAdvice {
  static Logger logger = LoggerFactory.getLogger(RequestObjectAdvice.class);

  @Override
  public boolean supports(MethodParameter methodParameter, Type type,
      Class<? extends HttpMessageConverter<?>> aClass) {
    return methodParameter.hasParameterAnnotation(RequestBody.class);
  }

  @Override
  public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage,
      MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
    logger.info("5");
    return null;
  }

  @Override
  public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage,
      MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass)
      throws IOException {
    logger.info("3");
    return httpInputMessage;
  }

  @Override
  public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage,
      MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
    logger.info("4");
    return o;
  }
}
