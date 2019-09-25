package com.rongdu.loans.common;

import java.util.Date;

import com.rongdu.loans.basic.service.AlarmService;
import org.dozer.DozerBeanMapper;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.task.TaskResult;
import com.rongdu.core.utils.IDGeneratorUtil;
import com.rongdu.core.utils.encode.JsonBinder;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.core.utils.spring.SpringContextHolder;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.batch.ScheduleTask;
import com.rongdu.loans.entity.batch.ScheduleTaskLog;
import com.rongdu.loans.service.batch.ScheduleTaskLogManager;
import com.rongdu.loans.service.batch.ScheduleTaskService;
import com.rongdu.loans.utils.DateUtil;

/**
 * 抽象任务工厂
 * @author sunda
 * @version 2017-07-14
 */
public abstract class AbstractJobFactory  implements Job{
	
	//任务状态：初始（需要加入计划，删除）
	public static String TASK_STATUS_DEFAULT = "DEFAULT";
	//任务状态：暂停（可以恢复启动，移除）
	public static String TASK_STATUS_PAUSE = "PAUSE";
	//任务状态：启动（可以暂停）
	public static String TASK_STATUS_ON = "ON";
	//任务状态：移除（可以重新加入计划，删除）
	public static String TASK_STATUS_OFF = "OFF";
	
	//任务执行结果：成功
	public static String TASK_RESULT_SUCCESS = "SUCCESS";
	//任务执行结果：失败
	public static String TASK_RESULT_FAILURE = "FAILURE";
	
	//失败处理策略：报警
	public static String FAIL_STRATEGY_ALARM = "ALARM";
	//失败处理策略：重试
	public static String FAIL_STRATEGY_RETRY = "RETRY";
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected DozerBeanMapper beanMapper = new DozerBeanMapper();
	
	protected JsonBinder jsonMapper = JsonBinder.buildNormalBinder();
	
	
	/**
	 * 调用scheduleTask中定义的任务方法
	 * 是否是在重新执行，默认为false
	 * @param task 待执行的任务
	 * @param
	 */
	public void executeTask(ScheduleTask task) {
		 String alarmMsg="";
		 String message="";
		 ScheduleTaskLog log = new ScheduleTaskLog();
		 Date start = new Date();
		 log.setStartTime(start);
		 task.setPreviousTime(start);
		try {			
			//调用各个业务系统定时任务逻辑	
			TaskResult result = invokeMethod(task,log);
			log.setStatus(TASK_RESULT_SUCCESS);
			logger.info("任务调度平台：任务执行完毕，应答消息：{}",jsonMapper.toJson(result));
		} catch (Exception e) {
			log.setStatus(TASK_RESULT_FAILURE);
			
			//失败处理策略（默认）：ALARM(报警)
			alarmMsg = new StringBuilder().append("任务调度平台提示：")
					.append(DateUtil.format())
					.append("，")
					.append(task.getDescription())
					.append(task.getTaskName())
					.append("，在执行时遇到异常任务(").append(e.getMessage()).append(")").toString();
			logger.info(alarmMsg);	
			
			//失败处理策略：RETRY(报警+重试)
			if (FAIL_STRATEGY_RETRY.equals(task.getFailStrategy())) {
				//原任务失败后才可能需要重试，重试任务失败后无需递归重试
				if (task.isRetryTask()) {
					logger.error("任务调度平台提示：任务{}处理失败，不需要重试。",task.getTaskName());
				}else {
					logger.error("任务调度平台提示：任务{}处理失败，需要重试{}次。",task.getTaskName(),task.getRetryTimes());	
					reExecuteTask(task);				
				}
			}
			sendAlarmSms(alarmMsg);
			e.printStackTrace();
		} finally{
			Date end = new Date();
			Long costTime = end.getTime()-start.getTime();
			String batchNo = DateUtil.format("yyMMddHHmmss");
			log.setId(IDGeneratorUtil.uuid());
			log.setTaskId(task.getId());
			log.setDescription(task.getDescription());	
			log.setEndTime(end);
			log.setCostTime(costTime);	
			log.setBatchNo(batchNo);
			log.setTaskName(task.getTaskName());
			log.setGroupName(task.getGroupName());
			saveTaskLog(log);
			//原任务需要持久化，重试任务无需持久化
			if (!task.isRetryTask()) {
				updateTask(task);				
			}
		}

	}
	
	/**
	 * 遇到异常时，重新执行任务
	 * @param task
	 */
	private void reExecuteTask(final ScheduleTask task) {
		int retryTimes = task.getRetryTimes();
		int times = 0;
		for (int i = 0; i < retryTimes; i++) {
			times = i+1;
			ScheduleTask retryTask = new ScheduleTask();
			beanMapper.map(task, retryTask);
			//注明这是一个“重试任务”
			retryTask.setRetryTask(true);
			retryTask.setDescription("(重试"+times+")"+task.getDescription());
			executeTask(retryTask);
		}
		
	}

	/**
	 * 更新任务执行记录
	 * @param task
	 */
	private void updateTask(ScheduleTask task) {
		ScheduleTaskService scheduleTaskService = (ScheduleTaskService) SpringContextHolder.getBean("scheduleTaskService");
		scheduleTaskService.update(task);
	}
	
	/**
	 * 记录任务执行日志
	 * @param log
	 */
	private void saveTaskLog(ScheduleTaskLog log) {
		ScheduleTaskLogManager scheduleTaskLogManager = (ScheduleTaskLogManager) SpringContextHolder.getBean("scheduleTaskLogManager");
		scheduleTaskLogManager.save(log);
	}

	/**
	 * 调用目标业务方法
	 * @param task
	 * @param log
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	private TaskResult invokeMethod(ScheduleTask task, ScheduleTaskLog log)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, Exception {
		TaskResult  result = null;
		Object targetObject = null;
		if (task.isSpringBean()) {
			targetObject = SpringContextHolder.getBean(task.getTargetObject());
		}else {
			 String className = task.getClazz();			        
			 Class clazz = Class.forName(className);
			 targetObject = clazz.newInstance();
		}
		Object returnObject = Reflections.invokeMethod(targetObject, task.getTargetMethod());
		if (returnObject!=null) {
			result = (TaskResult)returnObject;
		}
		//解析任务处理结果
		if (result!=null) {
			int totalNum = result.getSuccNum()+result.getFailNum();
			log.setTotalNum(totalNum);
			log.setSuccNum(result.getSuccNum());
			log.setFailNum(result.getFailNum());
			if (!result.isSuccess()) {
				throw new Exception(result.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 发送报警短信
	 * @param message
	 */
	public  void sendAlarmSms(String message){
		AlarmService alarmService = (AlarmService) SpringContextHolder.getBean("alarmService");
		alarmService.sendAlarmSms(Constants.ALARM_MOBILES, message);
	}

}