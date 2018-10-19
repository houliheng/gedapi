/**   
 * Copyright © 2017 Innodev. All rights reserved.
 * 
 * @Title ExceptionHandle.java 
 * @Package com.zyd.business.handle
 * @author <a href="mailto:yadong.zhang0415@gmail.com">yadong.zhang</a> 
 * @date 2017年6月7日 下午4:55:59 
 * @version V1.0   
 */
package com.gq.ged.common.exception.handle;

import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 统一异常处理类<br>
 * 捕获程序所有异常，针对不同异常，采取不同的处理方式
 * 
 * @date 2017年6月7日 下午4:55:59
 * @version V1.0
 * @since JDK ： 1.7
 */
@ControllerAdvice
@ResponseBody
@ApiIgnore
public class ExceptionHandle {
	static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
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
		logger.error("异常信息", ex);
		ResponseEntity re = new ResponseEntity();
		re.setCode(String.valueOf(ex.getCode()));
		re.setException(ex.getMessage());
		return re;
	}
}
