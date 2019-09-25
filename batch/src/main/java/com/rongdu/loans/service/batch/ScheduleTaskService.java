package com.rongdu.loans.service.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.loans.common.QuartzJobFactory;
import com.rongdu.loans.common.QuartzJobFactoryDisallowConcurrentExecution;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.entity.batch.ScheduleTask;
import com.rongdu.loans.utils.DateUtil;

/**
 * 定时任务业务逻辑处理类
 * 
 * @author sunda
 * @version 2017-07-13
 */
@Service
public class ScheduleTaskService implements
		ApplicationListener<ApplicationEvent> {

	@Autowired
	private ScheduleTaskManager scheduleJobManager;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	/**
	 * 查询所有启用的定时任务
	 * 
	 * @return
	 */
	public List<ScheduleTask> findAvailableTask() {
		return scheduleJobManager.findBy("status", "ON");
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 新增任务
	 */
	@Transactional
	public boolean insertTask(ScheduleTask task) throws SchedulerException {
		logger.info("新增任务：{}", task);
		scheduleJobManager.save(task);
		return true;
	}

	/**
	 * 获取上一次执行时间
	 */
	public Date getPreviousFireTime(ScheduleTask task) {
		Date date = null;
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(),
					task.getGroupName());
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(triggerKey);
			date = trigger.getPreviousFireTime();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		logger.info("获取上一次执行时间：{}", DateUtil.format(date));
		return date;
	}

	/**
	 * 获取下一次执行时间
	 */
	public Date getNextFireTime(ScheduleTask task) {
		Date date = null;
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(),
					task.getGroupName());
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(triggerKey);
			if (trigger != null) {
				date = trigger.getNextFireTime();
			} else {
				return null;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		logger.info("获取下一次执行时间：{}", DateUtil.format(date));
		return date;
	}

	public static boolean checkExists(String jobName, String jobGroup)
			throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return schedulerFactory.getScheduler().checkExists(triggerKey);
	}

	/**
	 * 加入到任务计划池，并更新到数据库
	 */
	@Transactional
	public boolean addTask(ScheduleTask task) throws SchedulerException {
		logger.info("正在向计划中添加任务：{}", task);
		task.setStatus("ON");
		addTask(null, task);
		update(task);
		return true;
	}

	/**
	 * 加入到任务计划池
	 */
	public boolean addTask(Scheduler scheduler, ScheduleTask task)
			throws SchedulerException {
		logger.info("正在向计划中添加任务：{}", task);
		if (scheduler == null) {
			scheduler = schedulerFactory.getScheduler();
		}
		Class clazz = task.isConcurrent() ? QuartzJobFactory.class
				: QuartzJobFactoryDisallowConcurrentExecution.class;
		// 任务名，任务组，任务执行类
		JobDetail jobDetail = JobBuilder.newJob(clazz)
				.withIdentity(task.getTaskName(), task.getGroupName()).build();
		jobDetail.getJobDataMap().put("ScheduleTask", task);
		// 触发器
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		// 触发器名,触发器组
		triggerBuilder.withIdentity(task.getTaskName(), task.getGroupName());
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(task.getCronExpression());
		// 触发器时间设定
		triggerBuilder.withSchedule(scheduleBuilder);
		// 创建Trigger对象
		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
		// 调度容器设置JobDetail和Trigger
		scheduler.scheduleJob(jobDetail, trigger);
		// 立即启动
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
		return true;
	}

	/**
	 * 立即运行任务
	 */
	public boolean runTask(ScheduleTask task) throws SchedulerException {
		logger.info("立即运行任务：{}", task);
		Scheduler scheduler = schedulerFactory.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(),
				task.getGroupName());
		// 创建Trigger对象
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = task.isConcurrent() ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(task.getTaskName(), task.getGroupName())
					.build();
			jobDetail.getJobDataMap().put("ScheduleTask", task);
			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder
					.newTrigger();
			// 触发器名,触发器组
			triggerBuilder
					.withIdentity(task.getTaskName(), task.getGroupName());
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(task.getCronExpression());
			// 触发器时间设定
			triggerBuilder.withSchedule(scheduleBuilder);
			triggerBuilder.startNow();
			// 调度容器设置JobDetail和Trigger
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(task.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			// scheduler.rescheduleJob(triggerKey, trigger);
		}
		JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getGroupName());
		scheduler.triggerJob(jobKey);
		return true;
	}

	/**
	 * 更新任务
	 */
	public boolean updateTask(ScheduleTask task) throws SchedulerException {
		logger.info("立即运行任务：{}", task);
		Scheduler scheduler = schedulerFactory.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(),
				task.getGroupName());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(task.getCronExpression());
		// 按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
				.withSchedule(scheduleBuilder).build();
		// 按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
		// update(task);
		return true;
	}

	/**
	 * 暂停任务
	 * 
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public boolean pauseTask(ScheduleTask task) throws SchedulerException {
		logger.info("正在暂停任务：{}", task);
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getGroupName());
		scheduler.pauseJob(jobKey);
		task.setStatus("PAUSE");
		update(task);
		return true;
	}

	/**
	 * 恢复任务
	 */
	public boolean resumeTask(ScheduleTask task) throws SchedulerException {
		logger.info("正在恢复任务：{}", task);
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getGroupName());
		scheduler.resumeJob(jobKey);

		// TriggerKey : name + group
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(),
				task.getGroupName());
		if (!checkExists(task.getTaskName(), task.getGroupName())) {
			addTask(scheduler, task);
		}
		if (checkExists(task.getTaskName(), task.getGroupName())) {
			scheduler.resumeTrigger(triggerKey);
			logger.info(">>>>>>>>>>> resumeJob success, triggerKey:{}",
					triggerKey);
		} else {
			logger.info(">>>>>>>>>>> resumeJob fail, triggerKey:{}", triggerKey);
		}

		task.setStatus("ON");
		update(task);
		return true;
	}

	/**
	 * 暂停所有的任务
	 * 
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public boolean pauseAllTask() throws SchedulerException {
		logger.info("正在暂停所有的任务");
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.pauseAll();
		return true;
	}

	/**
	 * 恢复所有的任务
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public boolean resumeAllTask() throws SchedulerException {
		logger.info("正在恢复所有的任务");
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.resumeAll();
		return true;
	}

	/**
	 * 移除任务
	 */
	public boolean deleteTask(ScheduleTask task) throws SchedulerException {
		logger.info("正在移除任务：{}", task);
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getGroupName());
		scheduler.deleteJob(jobKey);
		task.setStatus("OFF");
		update(task);
		return true;
	}

	/**
	 * 获取运行中的任务
	 */
	public List<ScheduleTask> getRunningTask() throws SchedulerException {
		logger.info("获取运行中的任务");
		Scheduler scheduler = schedulerFactory.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler
				.getCurrentlyExecutingJobs();
		List<ScheduleTask> jobList = new ArrayList<ScheduleTask>(
				executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleTask job = new ScheduleTask();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setTaskName(jobKey.getName());
			job.setGroupName(jobKey.getGroup());
			job.setRemark("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler
					.getTriggerState(trigger.getKey());
			job.setStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 获取计划中的任务 指那些已经添加到quartz调度器的任务
	 */
	public List<ScheduleTask> getScheduleTask() throws SchedulerException {
		logger.info("获取计划中的任务");
		Scheduler scheduler = schedulerFactory.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleTask> jobList = new ArrayList<ScheduleTask>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler
					.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleTask job = new ScheduleTask();
				job.setTaskName(jobKey.getName());
				job.setGroupName(jobKey.getGroup());
				job.setRemark("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler
						.getTriggerState(trigger.getKey());
				job.setStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 系统初始化定时任务列表
	 * 
	 * @throws Exception
	 */
	public void initTaskList() throws Exception {
		logger.info("正在初始化任务列表");
		Scheduler scheduler = schedulerFactory.getScheduler();
		// 可执行的任务列表
		Collection<ScheduleTask> taskList = findAvailableTask();
		for (ScheduleTask task : taskList) {
			addTask(scheduler, task);
		}
	}

	public ScheduleTask get(String id) {
		return scheduleJobManager.get(id);
	}

	@Transactional
	public void insert(ScheduleTask task) {
		scheduleJobManager.save(task);
	}

	@Transactional
	public void update(ScheduleTask task) {
		Date nextTime = getNextFireTime(task);
		task.setNextTime(nextTime);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		VUser user = null;
		if (authentication != null) {
			user = (VUser) authentication.getPrincipal();
		}
		if (user != null) {
			task.setUpdateBy(String.valueOf(user.getId()));
			task.setUpdateTime(new Date());
		} else {
			task.setUpdateBy("system");
			task.setUpdateTime(new Date());
		}
		scheduleJobManager.update(task);
	}

	public void delete(String id) {
		scheduleJobManager.delete(id);
	}

	public Page<ScheduleTask> findPage(Page<ScheduleTask> page,
			List<PropertyFilter> filters) {
		return scheduleJobManager.findPage(page, filters);
	}

	@Transactional
	public void save(ScheduleTask entity) {
		scheduleJobManager.save(entity);
	}

	@Transactional
	public int batchDelete(String[] ids) {
		// TODO Auto-generated method stub
		return scheduleJobManager.batchDelete(ids);
	}

	public boolean isUnique(String propertyName, String value) {
		// TODO Auto-generated method stub
		return scheduleJobManager.isUnique(propertyName, value);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {

		// 只在初始化“根上下文”的时候执行
		if (applicationEvent.getSource() instanceof XmlWebApplicationContext) {
			if (((XmlWebApplicationContext) applicationEvent.getSource())
					.getDisplayName().equals("Root WebApplicationContext")) {
				try {
					initTaskList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
