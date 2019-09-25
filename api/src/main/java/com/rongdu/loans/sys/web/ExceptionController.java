package com.rongdu.loans.sys.web;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rongdu.common.exception.BizException;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.loans.api.common.BadRequestException;

/**
 * 统一处理Controller异常
 * 
 * @author sunda
 *
 */
@ControllerAdvice
public class ExceptionController {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Handles any Illegal State Exception from a controller method
	 * @param e The exception that was thrown
	 * @return JSON Map with error messages specific to the illegal state exception thrown
	 */
	@ExceptionHandler(IllegalStateException.class)
//	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody Map<String,Object> handleIOExcpetion(IllegalStateException e){
		Map<String,Object> result = new ApiResult(
				ErrInfo.FORBIDDEN.getCode(),
//				ErrInfo.FORBIDDEN.getMsg()+e.getMessage());
		        ErrInfo.FORBIDDEN.getMsg());
		logger.error("Error: " + e.getMessage());
		logger.error("Class? " + e.getClass());
		e.printStackTrace();
		return result;
	}
	
	@ExceptionHandler(BizException.class)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String,Object> handleBizExcpetion(BizException e){
		Map<String,Object> result = new ApiResult(e.getCode(), e.getMessage());
		logger.error("Error: " + e.getMessage());
		logger.error("Class? " + e.getClass());
		e.printStackTrace();
		return result;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BadRequestException.class)
	public @ResponseBody Map<String,Object> handleBadRequestException(BadRequestException e){
		ApiResult result = new ApiResult(e.getCode(), e.getMessage());
		Errors errors = e.getErrors();
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		List<FieldError> errorList = errors.getFieldErrors();
		int listSize = errorList.size();
		for(int i=0;i<listSize;i++) {
			FieldError err = errorList.get(i);
			logger.debug("请求参数校验未通过：{} ：{}",err.getField(),err.getDefaultMessage());
			data.put(err.getField(), err.getDefaultMessage());
			if(i == 0) {
			    result.setMsg(err.getDefaultMessage());
			}
			result.setData(data);
		}
//		e.printStackTrace();
		return result;
	}
	
	/**
	 * Catch all exception handler for any other exception thrown in the controller.
	 * @param e Exception that was thrown.
	 * @return A more generic error message that does not give away any implementation details
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String,Object> handleExcpetion( Exception e){
		Map<String,Object> result = new ApiResult(
				ErrInfo.SERVER_ERROR.getCode(),
//				ErrInfo.SERVER_ERROR.getMsg()+e.getMessage());
				ErrInfo.SERVER_ERROR.getMsg());
		logger.error("Error: " + e.getMessage());
		logger.error("Class? " + e.getClass());
		e.printStackTrace();
		return result;
	}
}
