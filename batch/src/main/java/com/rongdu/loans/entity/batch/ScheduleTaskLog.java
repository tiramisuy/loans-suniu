/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-16 0:08:00
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

/**
 * 定时任务执行日志实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="batch_schedule_task_log")
public class ScheduleTaskLog  implements java.io.Serializable {

	private static final long serialVersionUID = -4970289171664937848L;
	/**
 	  *  日志ID
      */
 	private String id;
	/**
 	  *  任务ID
      */
 	private String taskId;
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
 	  *  批次编号
      */
 	private String batchNo;
	/**
 	  *  开始时间
      */
 	private Date startTime;
	/**
 	  *  结束时间
      */
 	private Date endTime;
	/**
 	  *  耗时（秒）
      */
 	private Long costTime;
	/**
 	  *  总计处理多少数据
      */
 	private Integer totalNum;
	/**
 	  *  成功数量
      */
 	private Integer succNum;
	/**
 	  *  失败数量
      */
 	private Integer failNum;
	/**
 	  *  状态
      */
 	private String status;
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
 	private boolean del = false;

    public ScheduleTaskLog() {
    }
	
    public ScheduleTaskLog(String id, String taskId, String taskName, String groupName, Date startTime, String createBy, String updateBy, boolean del) {
        this.id = id;
        this.taskId = taskId;
        this.taskName = taskName;
        this.groupName = groupName;
        this.startTime = startTime;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.del = del;
    }
    public ScheduleTaskLog(String id, String taskId, String taskName, String groupName, String description, String batchNo, Date startTime, Date endTime, Long costTime, Integer totalNum, Integer succNum, Integer failNum, String status, String remark, String createBy, Date createTime, String updateBy, Date updateTime, boolean del) {
       this.id = id;
       this.taskId = taskId;
       this.taskName = taskName;
       this.groupName = groupName;
       this.description = description;
       this.batchNo = batchNo;
       this.startTime = startTime;
       this.endTime = endTime;
       this.costTime = costTime;
       this.totalNum = totalNum;
       this.succNum = succNum;
       this.failNum = failNum;
       this.status = status;
       this.remark = remark;
       this.createBy = createBy;
       this.createTime = createTime;
       this.updateBy = updateBy;
       this.updateTime = updateTime;
       this.del = del;
    }
   
   /**     
     *访问<日志ID>属性
     */
     @Id 
    @Column(name="id", unique=true, nullable=false, length=50)
    public String getId() {
        return this.id;
    }	    
    /**     
     *设置<日志ID>属性
     */
    public void setId(String id) {
        this.id = id;
    }
   /**     
     *访问<任务ID>属性
     */
    @Column(name="task_id", nullable=false, length=50)
    public String getTaskId() {
        return this.taskId;
    }	    
    /**     
     *设置<任务ID>属性
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
    @Column(name="description", length=50)
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
     *访问<批次编号>属性
     */
    @Column(name="batch_no", length=20)
    public String getBatchNo() {
        return this.batchNo;
    }	    
    /**     
     *设置<批次编号>属性
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
   /**     
     *访问<开始时间>属性
     */
    @Temporal(TemporalType.TIMESTAMP)@Column(name="start_time", nullable=false, length=0)
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
     *访问<结束时间>属性
     */
    @Temporal(TemporalType.TIMESTAMP)@Column(name="end_time", length=0)
    public Date getEndTime() {
        return this.endTime;
    }	    
    /**     
     *设置<结束时间>属性
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
   /**     
     *访问<耗时（秒）>属性
     */
    @Column(name="cost_time")
    public Long getCostTime() {
        return this.costTime;
    }	    
    /**     
     *设置<耗时（秒）>属性
     */
    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
   /**     
     *访问<总计处理多少数据>属性
     */
    @Column(name="total_num")
    public Integer getTotalNum() {
        return this.totalNum;
    }	    
    /**     
     *设置<总计处理多少数据>属性
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
   /**     
     *访问<成功数量>属性
     */
    @Column(name="succ_num")
    public Integer getSuccNum() {
        return this.succNum;
    }	    
    /**     
     *设置<成功数量>属性
     */
    public void setSuccNum(Integer succNum) {
        this.succNum = succNum;
    }
   /**     
     *访问<失败数量>属性
     */
    @Column(name="fail_num")
    public Integer getFailNum() {
        return this.failNum;
    }	    
    /**     
     *设置<失败数量>属性
     */
    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }
   /**     
     *访问<状态>属性
     */
    @Column(name="status", length=10)
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
    @Temporal(TemporalType.TIMESTAMP)@Column(name="create_time", length=0)
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
    @Temporal(TemporalType.TIMESTAMP)@Column(name="update_time", length=0)
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


}
