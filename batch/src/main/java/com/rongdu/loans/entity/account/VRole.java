/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-18 20:03:59
 *
 *******************************************************************************/
package com.rongdu.loans.entity.account;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VRole实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="V_ROLE")
public class VRole  implements java.io.Serializable {
	
	private static final long serialVersionUID = -2672578779941913871L;
	/**
 	  *  id
      */
 	private Long id;
	/**
 	  *  name
      */
 	private String name;
	/**
 	  *  intro
      */
 	private String intro;
	/**
 	  *  userNum
      */
 	private Long userNum;

    public VRole() {
    }
	
    public VRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public VRole(Long id, String name, String intro, Long userNum) {
       this.id = id;
       this.name = name;
       this.intro = intro;
       this.userNum = userNum;
    }
   
   /**     
     *访问<id>属性
     */
     @Id 
    @Column(name="ID", unique=true, nullable=false, scale=0)
    public Long getId() {
        return this.id;
    }	    
    /**     
     *设置<id>属性
     */
    public void setId(Long id) {
        this.id = id;
    }
   /**     
     *访问<name>属性
     */
    @Column(name="NAME", nullable=false, length=510)
    public String getName() {
        return this.name;
    }	    
    /**     
     *设置<name>属性
     */
    public void setName(String name) {
        this.name = name;
    }
   /**     
     *访问<intro>属性
     */
    @Column(name="INTRO", length=100)
    public String getIntro() {
        return this.intro;
    }	    
    /**     
     *设置<intro>属性
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }
   /**     
     *访问<userNum>属性
     */
    @Column(name="USER_NUM", precision=22, scale=0)
    public Long getUserNum() {
        return this.userNum;
    }	    
    /**     
     *设置<userNum>属性
     */
    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

}
