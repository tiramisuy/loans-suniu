/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-19 16:51:16
 *
 *******************************************************************************/
package com.rongdu.loans.entity.account;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VRoleAuthority实体类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Entity
@Table(name="V_ROLE_AUTHORITY")
public class VRoleAuthority  implements java.io.Serializable {

	private static final long serialVersionUID = 7244196878040275751L;
	/**
 	  *  roleId
      */
 	private Long roleId;
	/**
 	  *  authId
      */
 	private Long authId;
	/**
 	  *  pid
      */
 	private Long pid;
 	/**
	  *  code
     */
	private String code;
	/**
 	  *  name
      */
 	private String name;
	/**
 	  *  rank
      */
 	private Long rank;
	/**
 	  *  type
      */
 	private String type;
	/**
 	  *  url
      */
 	private String url;

    public VRoleAuthority() {
    }
	
    public VRoleAuthority(Long roleId) {
        this.roleId = roleId;
    }
    
    public VRoleAuthority(Long authId, Long pid,String name,String url) {
        this.authId = authId;
        this.pid = pid;
        this.name = name;
        this.url = url;
     }
    
    public VRoleAuthority(Long roleId, Long authId, Long pid, String code,String name, Long rank, String type, String url) {
       this.roleId = roleId;
       this.authId = authId;
       this.pid = pid;
       this.code = code;
       this.name = name;
       this.rank = rank;
       this.type = type;
       this.url = url;
    }
   
   /**     
     *访问<roleId>属性
     */
     @Id 
    @Column(name="ROLE_ID", unique=true, nullable=false, scale=0)
    public Long getRoleId() {
        return this.roleId;
    }	    
    /**     
     *设置<roleId>属性
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
   /**     
     *访问<authId>属性
     */
    @Id 
    @Column(name="AUTH_ID", scale=0)
    public Long getAuthId() {
        return this.authId;
    }	    
    /**     
     *设置<authId>属性
     */
    public void setAuthId(Long authId) {
        this.authId = authId;
    }
   /**     
     *访问<pid>属性
     */
    @Column(name="PID", scale=0)
    public Long getPid() {
        return this.pid;
    }	    
    /**     
     *设置<pid>属性
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
   /**     
     *访问<name>属性
     */
    @Column(name="NAME", length=40)
    public String getName() {
        return this.name;
    }	    
    /**     
     *设置<name>属性
     */
    public void setName(String name) {
        this.name = name;
    }
   public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

/**     
     *访问<rank>属性
     */
    @Column(name="RANK", precision=11, scale=0)
    public Long getRank() {
        return this.rank;
    }	    
    /**     
     *设置<rank>属性
     */
    public void setRank(Long rank) {
        this.rank = rank;
    }
   /**     
     *访问<type>属性
     */
    @Column(name="TYPE", length=10)
    public String getType() {
        return this.type;
    }	    
    /**     
     *设置<type>属性
     */
    public void setType(String type) {
        this.type = type;
    }
   /**     
     *访问<url>属性
     */
    @Column(name="URL", length=100)
    public String getUrl() {
        return this.url;
    }	    
    /**     
     *设置<url>属性
     */
    public void setUrl(String url) {
        this.url = url;
    }


}
