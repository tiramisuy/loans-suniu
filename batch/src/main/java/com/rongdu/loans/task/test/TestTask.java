package com.rongdu.loans.task.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.utils.DateUtil;


/**
 * 定时任务业务处理逻辑
 * @author sunda
 * @version 2017-07-14
 */
@Service
public class TestTask{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public TaskResult testMethod(){
		//成功数量
		int succNum = 0;
		//失败数量
		int failNum = 0;
		//成功，可以返回执行情况；失败，必须返回失败原因
		String message = "";
		
		//执行定时任务业务逻辑
		logger.info("正在执行定时任务：{}",DateUtil.format());
		
		//执行时需要统计成功数量和失败数量
		succNum = 10;
		failNum = 0;
		
		
		
		TaskResult result = new TaskResult(succNum, failNum,message);
		return result;
	}
}
