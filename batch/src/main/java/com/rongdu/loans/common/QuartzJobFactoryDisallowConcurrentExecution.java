package com.rongdu.loans.common;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.loans.entity.batch.ScheduleTask;


/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @author sunda
 * @version 2017-07-14
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution extends AbstractJobFactory {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {	
		
	  	try {
	  		ScheduleTask task = (ScheduleTask) context.getMergedJobDataMap().get("ScheduleTask");
	  		logger.error("按计划执行定时任务（无状态）：{}-{}，",task.getDescription(),task.getTaskName());  
	   		executeTask(task);	   
	   	} catch (Exception e) {
	   		logger.error("按计划执行定时任务，错误：{}",e.getMessage());
	   		e.printStackTrace();
	  	}
	}
	
}
