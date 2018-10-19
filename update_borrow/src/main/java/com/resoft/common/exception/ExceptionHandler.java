package com.resoft.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) {
		if(e instanceof MaxUploadSizeExceededException){
			/**
			 * @reqno:H1512250043
			 * @date-designer:20151229-lirongchao
			 * @date-author:20151229-lirongchao:监测指标报送-数据填报-指标导入功能，导入文件大于10M时，页面报错
			 * Any spring mvc配置异常统一处理，上传文件过大，spring mvc异常报错MaxUploadSizeExceededException
			 * Ans spring mvc统一配置异常MaxUploadSizeExceededException处理，提示用户导入文件过大
			*/
			ModelAndView mv = new ModelAndView("app/credit/sys/template/uploadError");
			mv.addObject("error", "上传失败！上传文件不能大于10M！");
			return mv;
		}
		return null;
	}

}
