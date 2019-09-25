/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:41:03
 *
 *******************************************************************************/
package com.rongdu.loans.entity.account;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="sys_dept")
public class Dept  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  部门编号
      */
 	private String deptNo;
	/**
 	  *  部门名称
      */
 	private String deptName;
	/**
 	  *  上级部门ID
      */
 	private Long parentId;
	/**
 	  *  状态
      */
 	private String status;
	/**
 	  *  备注
      */
 	private String remark;

    public Dept() {
    }
	
    public Dept(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }
    public Dept(String deptNo, String deptName, Long parentId, String status, String remark) {
       this.deptNo = deptNo;
       this.deptName = deptName;
       this.parentId = parentId;
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
     *访问<部门编号>属性
     */
    @Column(name="dept_no", nullable=false, length=20)
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
     *访问<部门名称>属性
     */
    @Column(name="dept_name", nullable=false, length=20)
    public String getDeptName() {
        return this.deptName;
    }	    
    /**     
     *设置<部门名称>属性
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
   /**     
     *访问<上级部门ID>属性
     */
    @Column(name="parent_id")
    public Long getParentId() {
        return this.parentId;
    }	    
    /**     
     *设置<上级部门ID>属性
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
