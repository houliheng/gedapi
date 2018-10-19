package com.gq.ged.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * Created by wyq_tomorrow on 2018/1/12.
 */
@ControllerAdvice(basePackages = "com.future.core")
public class ResponseObjectAdvice implements ResponseBodyAdvice {
  static Logger logger = LoggerFactory.getLogger(ResponseObjectAdvice.class);

  @Override
  public boolean supports(MethodParameter methodParameter, Class aClass) {
    return methodParameter.hasMethodAnnotation(SerializedField.class);
  }

  @Override
  public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
      Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
    logger.info("2");
    return o;
  }
}
