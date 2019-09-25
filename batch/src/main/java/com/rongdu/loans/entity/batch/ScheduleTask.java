/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-16 0:07:36
 *
 *******************************************************************************/
package com.rongdu.loans.entity.batch;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rongdu.core.utils.encode.JsonBinder;
import com.rongdu.loans.common.AbstractJobFactory;

/**
 * 定时任务实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="batch_schedule_task")
public class ScheduleTask  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3835136591457077836L;
	/**
 	  *  ID
      */
 	private String id;
	/**
 	  *  任务名称
      */
 	private String taskName;
	/**
 	  *  任务分组
      */
 	private String groupName;
	/**
 	  *  任务说明
      */
 	private String description;
	/**
 	  *  是否支持并发（0-禁用 1-启用）
      */
 	private boolean concurrent;
	/**
 	  *  开始时间
      */
 	private Date startTime;
	/**
 	  *  最近执行时间
      */
 	private Date previousTime;
	/**
 	  *  下次执行时间
      */
 	private Date nextTime;
	/**
 	  *  Cron表达式
      */
 	private String cronExpression;
	/**
 	  *  目标对象Bean
      */
 	private String targetObject;
	/**
 	  *  执行方法
      */
 	private String targetMethod;
	/**
 	  *  是否为SpringBean(0-否，1-是)
      */
 	private boolean springBean = true;
	/**
 	  *  Java类名，如果不是Spring中的Bean需要配置全类名用于反射
      */
 	private String clazz;
	/**
 	  *  失败处理策略(ALARM-警报，RETRY-重试）
      */
 	private String failStrategy = AbstractJobFactory.FAIL_STRATEGY_ALARM;
	/**
 	  *  任务执行失败之后，重试次数
      */
 	private int retryTimes = 1;
	/**
 	  *  状态
      */
 	private String status = AbstractJobFactory.TASK_STATUS_DEFAULT;
	/**
 	  *  备注信息
      */
 	private String remark;
	/**
 	  *  创建者
      */
 	private String createBy;
	/**
 	  *  创建时间
      */
 	private Date createTime;
	/**
 	  *  最后修改者
      */
 	private String updateBy;
	/**
 	  *  最后修改时间
      */
 	private Date updateTime;
	/**
 	  *  删除标记：0-正常，1-已经删除
      */
 	private boolean del;
 	
	/**
	  *  是否为失败后重新执行的任务，此信息无需持久化
     */
	private boolean retryTask = false;

    public ScheduleTask() {
    }
	
    public ScheduleTask(String id, String taskName, String groupName, String description, boolean concurrent, String cronExpression, String targetObject, String targetMethod, boolean springBean, String failStrategy, int retryTimes, String status, String createBy, String updateBy, boolean del) {
        this.id = id;
        this.taskName = taskName;
        this.groupName = groupName;
        this.description = description;
        this.concurrent = concurrent;
        this.cronExpression = cronExpression;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.springBean = springBean;
        this.failStrategy = failStrategy;
        this.retryTimes = retryTimes;
        this.status = status;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.del = del;
    }
    public ScheduleTask(String id, String taskName, String groupName, String description, boolean concurrent, Date startTime, Date previousTime, Date nextTime, String cronExpression, String targetObject, String targetMethod, boolean springBean, String clazz, String failStrategy, int retryTimes, String status, String remark, String createBy, Date createTime, String updateBy, Date updateTime, boolean del) {
       this.id = id;
       this.taskName = taskName;
       this.groupName = groupName;
       this.description = description;
       this.concurrent = concurrent;
       this.startTime = startTime;
       this.previousTime = previousTime;
       this.nextTime = nextTime;
       this.cronExpression = cronExpression;
       this.targetObject = targetObject;
       this.targetMethod = targetMethod;
       this.springBean = springBean;
       this.clazz = clazz;
       this.failStrategy = failStrategy;
       this.retryTimes = retryTimes;
       this.status = status;
       this.remark = remark;
       this.createBy = createBy;
       this.createTime = createTime;
       this.updateBy = updateBy;
       this.updateTime = updateTime;
       this.del = del;
    }
   
   /**     
     *访问<ID>属性
     */
     @Id 
    @Column(name="id", unique=true, nullable=false, length=50)
    public String getId() {
        return this.id;
    }	    
    /**     
     *设置<ID>属性
     */
    public void setId(String id) {
        this.id = id;
    }
   /**     
     *访问<任务名称>属性
     */
    @Column(name="task_name", nullable=false, length=20)
    public String getTaskName() {
        return this.taskName;
    }	    
    /**     
     *设置<任务名称>属性
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
   /**     
     *访问<任务分组>属性
     */
    @Column(name="group_name", nullable=false, length=20)
    public String getGroupName() {
        return this.groupName;
    }	    
    /**     
     *设置<任务分组>属性
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
   /**     
     *访问<任务说明>属性
     */
    @Column(name="description", nullable=false, length=50)
    public String getDescription() {
        return this.description;
    }	    
    /**     
     *设置<任务说明>属性
     */
    public void setDescription(String description) {
        this.description = description;
    }
   /**     
     *访问<是否支持并发（0-禁用 1-启用）>属性
     */
    @Column(name="concurrent", nullable=false)
    public boolean isConcurrent() {
        return this.concurrent;
    }	    
    /**     
     *设置<是否支持并发（0-禁用 1-启用）>属性
     */
    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }
   /**     
     *访问<开始时间>属性
     */
    @Column(name="start_time", length=0)
    public Date getStartTime() {
        return this.startTime;
    }	    
    /**     
     *设置<开始时间>属性
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
   /**     
     *访问<最近执行时间>属性
     */
    @Column(name="previous_time", length=0)
    public Date getPreviousTime() {
        return this.previousTime;
    }	    
    /**     
     *设置<最近执行时间>属性
     */
    public void setPreviousTime(Date previousTime) {
        this.previousTime = previousTime;
    }
   /**     
     *访问<下次执行时间>属性
     */
    @Temporal(TemporalType.TIMESTAMP)@Column(name="next_time", length=0)
    public Date getNextTime() {
        return this.nextTime;
    }	    
    /**     
     *设置<下次执行时间>属性
     */
    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }
   /**     
     *访问<Cron表达式>属性
     */
    @Column(name="cron_expression", nullable=false, length=50)
    public String getCronExpression() {
        return this.cronExpression;
    }	    
    /**     
     *设置<Cron表达式>属性
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
   /**     
     *访问<目标对象Bean>属性
     */
    @Column(name="target_object", nullable=false, length=20)
    public String getTargetObject() {
        return this.targetObject;
    }	    
    /**     
     *设置<目标对象Bean>属性
     */
    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }
   /**     
     *访问<执行方法>属性
     */
    @Column(name="target_method", nullable=false, length=20)
    public String getTargetMethod() {
        return this.targetMethod;
    }	    
    /**     
     *设置<执行方法>属性
     */
    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
   /**     
     *访问<是否为SpringBean(0-否，1-是)>属性
     */
    @Column(name="spring_bean", nullable=false)
    public boolean isSpringBean() {
        return this.springBean;
    }	    
    /**     
     *设置<是否为SpringBean(0-否，1-是)>属性
     */
    public void setSpringBean(boolean springBean) {
        this.springBean = springBean;
    }
   /**     
     *访问<Java类名，如果不是Spring中的Bean需要配置全类名用于反射>属性
     */
    @Column(name="clazz", length=50)
    public String getClazz() {
        return this.clazz;
    }	    
    /**     
     *设置<Java类名，如果不是Spring中的Bean需要配置全类名用于反射>属性
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
   /**     
     *访问<失败处理策略(ALARM-警报，RETRY-重试）>属性
     */
    @Column(name="fail_strategy", nullable=false, length=20)
    public String getFailStrategy() {
        return this.failStrategy;
    }	    
    /**     
     *设置<失败处理策略(ALARM-警报，RETRY-重试）>属性
     */
    public void setFailStrategy(String failStrategy) {
        this.failStrategy = failStrategy;
    }
   /**     
     *访问<任务执行失败之后，重试次数>属性
     */
    @Column(name="retry_times", nullable=false)
    public int getRetryTimes() {
        return this.retryTimes;
    }	    
    /**     
     *设置<任务执行失败之后，重试次数>属性
     */
    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }
   /**     
     *访问<状态>属性
     */
    @Column(name="status", nullable=false, length=20)
    public String getStatus() {
        return this.status;
    }	    
    /**     
     *设置<状态>属性
     */
    public void setStatus(String status) {
        this.status = status;
    }
   /**     
     *访问<备注信息>属性
     */
    @Column(name="remark", length=100)
    public String getRemark() {
        return this.remark;
    }	    
    /**     
     *设置<备注信息>属性
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
   /**     
     *访问<创建者>属性
     */
    @Column(name="create_by", nullable=false, length=50)
    public String getCreateBy() {
        return this.createBy;
    }	    
    /**     
     *设置<创建者>属性
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
   /**     
     *访问<创建时间>属性
     */
   @Column(name="create_time", length=0)
    public Date getCreateTime() {
        return this.createTime;
    }	    
    /**     
     *设置<创建时间>属性
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   /**     
     *访问<最后修改者>属性
     */
    @Column(name="update_by", nullable=false, length=50)
    public String getUpdateBy() {
        return this.updateBy;
    }	    
    /**     
     *设置<最后修改者>属性
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
   /**     
     *访问<最后修改时间>属性
     */
    @Column(name="update_time", length=0)
    public Date getUpdateTime() {
        return this.updateTime;
    }	    
    /**     
     *设置<最后修改时间>属性
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
   /**     
     *访问<删除标记：0-正常，1-已经删除>属性
     */
    @Column(name="del", nullable=false)
    public boolean isDel() {
        return this.del;
    }	    
    /**     
     *设置<删除标记：0-正常，1-已经删除>属性
     */
    public void setDel(boolean del) {
        this.del = del;
    }

    @Transient
    public boolean isRetryTask() {
		return retryTask;
	}

	public void setRetryTask(boolean retryTask) {
		this.retryTask = retryTask;
	}

	@Override
    public String toString() {
    	return JsonBinder.buildNormalBinder().toJson(this);
    }
}
