/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:40:47
 *
 *******************************************************************************/
package com.rongdu.loans.entity.account;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * 员工实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="sys_emp", uniqueConstraints = @UniqueConstraint(columnNames="dept_no") )
public class Emp  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  工号
      */
 	private String empNo;
	/**
 	  *  员工姓名
      */
 	private String empName;
	/**
 	  *  部门编号
      */
 	private String deptNo;
	/**
 	  *  岗位
      */
 	private String job;
	/**
 	  *  入职日期
      */
 	private Date hireDate;
	/**
 	  *  状态
      */
 	private String status;
	/**
 	  *  备注
      */
 	private String remark;

    public Emp() {
    }
	
    public Emp(String empNo, String empName, String job) {
        this.empNo = empNo;
        this.empName = empName;
        this.job = job;
    }
    public Emp(String empNo, String empName, String deptNo, String job, Date hireDate, String status, String remark) {
       this.empNo = empNo;
       this.empName = empName;
       this.deptNo = deptNo;
       this.job = job;
       this.hireDate = hireDate;
       this.status = status;
       this.remark = remark;
    }
   
   /**     
     *访问<ID>属性
     */
     @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }	    
    /**     
     *设置<ID>属性
     */
    public void setId(Long id) {
        this.id = id;
    }
   /**     
     *访问<工号>属性
     */
    @Column(name="emp_no", nullable=false, length=20)
    public String getEmpNo() {
        return this.empNo;
    }	    
    /**     
     *设置<工号>属性
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
   /**     
     *访问<员工姓名>属性
     */
    @Column(name="emp_name", nullable=false, length=20)
    public String getEmpName() {
        return this.empName;
    }	    
    /**     
     *设置<员工姓名>属性
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }
   /**     
     *访问<部门编号>属性
     */
    @Column(name="dept_no", unique=true, length=20)
    public String getDeptNo() {
        return this.deptNo;
    }	    
    /**     
     *设置<部门编号>属性
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
   /**     
     *访问<岗位>属性
     */
    @Column(name="job", nullable=false, length=20)
    public String getJob() {
        return this.job;
    }	    
    /**     
     *设置<岗位>属性
     */
    public void setJob(String job) {
        this.job = job;
    }
   /**     
     *访问<入职日期>属性
     */
    @Temporal(TemporalType.DATE)@Column(name="hire_date", length=0)
    public Date getHireDate() {
        return this.hireDate;
    }	    
    /**     
     *设置<入职日期>属性
     */
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
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
     *访问<备注>属性
     */
    @Column(name="remark", length=200)
    public String getRemark() {
        return this.remark;
    }	    
    /**     
     *设置<备注>属性
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


}
