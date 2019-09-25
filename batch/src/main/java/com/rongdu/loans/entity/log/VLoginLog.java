/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:36
 *
 *******************************************************************************/
package com.rongdu.loans.entity.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.rongdu.loans.utils.Excel;


/**
 * 登录日志实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="V_LOGIN_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VLoginLog  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1136201089859041726L;
	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  用户
      */
 	@Excel(fieldLabel="用户",fieldWidth=10)
 	private String username;
	/**
	  *  用户姓名
    */
	@Excel(fieldLabel="用户姓名",fieldWidth=10)
	private String empName;
	/**
	  *  部门编号
    */
	@Excel(fieldLabel="部门编号",fieldWidth=10)
	private String deptCode;
	/**
	  *  部门名称
    */
	@Excel(fieldLabel="部门名称",fieldWidth=15)
	private String deptName;
	/**
 	  *  操作
      */
 	@Excel(fieldLabel="操作",fieldWidth=10)
 	private String opt;
	/**
 	  *  登录时间
      */
 	@Excel(fieldLabel="登录时间",fieldWidth=20)
 	private String loginTime;
	/**
 	  *  IP地址
      */
 	@Excel(fieldLabel="IP地址",fieldWidth=20)
 	private String ip;
	/**
 	  *  状态
      */
 	@Excel(fieldLabel="状态",fieldWidth=15)
 	private String status;
 	/**
	  *  结果
     */
	@Excel(fieldLabel="结果",fieldWidth=15)
	private String result;
 	/**
	  *  操作次数
     */
	private String optNum;

	public VLoginLog() {
    }
    public VLoginLog(Long id, String username,String empName,String deptCode,String deptName, String opt, String loginTime, String ip, String status,String result) {
       this.id = id;
       this.username = username;
       this.empName = empName;
       this.deptCode = deptCode;
       this.deptName = deptName;
       this.opt = opt;
       this.loginTime = loginTime;
       this.ip = ip;
       this.status = status;
       this.result = result; 
    }
   
   public VLoginLog(Long loginId) {
		this.id = loginId;
	}
/**     
     *访问<ID>属性
     */
    @Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
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
     *访问<用户>属性
     */
    @Column(name="USERNAME", nullable=false, length=20)
    public String getUsername() {
        return this.username;
    }	    
    /**     
     *设置<用户>属性
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**     
     *访问<用户姓名>属性
     */
    @Column(name="NAME", nullable=false, length=15)
 	public String getEmpName() {
		return empName;
	}
    /**     
     *设置<用户姓名>属性
     */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
    /**     
     *访问<部门编号>属性
     */
    @Column(name="DEPT_NO", nullable=false, length=15)
	public String getDeptCode() {
		return deptCode;
	}
    /**     
     *设置<部门编号>属性
     */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
    /**     
     *访问<部门名称>属性
     */
    @Column(name="DEPT_NAME", nullable=false, length=25)
	public String getDeptName() {
		return deptName;
	}
    /**     
     *设置<部门名称>属性
     */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
   /**     
     *访问<操作>属性
     */
    @Column(name="OPT", nullable=false, length=40)
    public String getOpt() {
        return this.opt;
    }	    
    /**     
     *设置<操作>属性
     */
    public void setOpt(String opt) {
        this.opt = opt;
    }
   /**     
     *访问<登录时间>属性
     */
    @Column(name="LOGIN_TIME", nullable=false, length=20)
    public String getLoginTime() {
        return this.loginTime;
    }	    
    /**     
     *设置<登录时间>属性
     */
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
   /**     
     *访问<IP地址>属性
     */
    @Column(name="IP", nullable=false, length=20)
    public String getIp() {
        return this.ip;
    }	    
    /**     
     *设置<IP地址>属性
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
   /**     
     *访问<状态>属性
     */
    @Column(name="STATUS", nullable=false, length=10)
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
     *访问<结果>属性
     */
    @Column(name="RESULT", nullable=false, length=10)
    public String getResult() {
        return this.result;
    }	    
    /**     
     *设置<结果>属性
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**     
     *设置<操作次数>属性
     */
	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}
}
