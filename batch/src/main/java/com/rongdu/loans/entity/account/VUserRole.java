package com.rongdu.loans.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 角色.
 * 
 * 注释见{@link User}.
 */
@Entity
@Table(name="V_USER_ROLE")
public class VUserRole  implements java.io.Serializable {
	private static final long serialVersionUID = -2793368684299281058L;
	/**
 	  *  id
      */

 	private Long userId;
	/**
 	  *  用户名
      */
 	private String username;
	/**
 	  *  姓名
      */
 	private String name;
	/**
 	  *  部门编号
      */
 	private String deptCode;
	/**
	  *  部门名称
     */
	private String deptName;
	/**
	  *  角色ID
    */
	private Long roleId;
	/**
	  *  角色名称
   */
	private String roleName;

    public VUserRole() {
    }
	


	public VUserRole(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    public VUserRole(Long userId, String name, String ideptCode, String deptName,Long roleId) {
       this.userId = userId;
       this.name = name;
       this.deptCode = ideptCode;
       this.deptName = deptName;
       this.roleId = roleId;
    }
   
   /**     
     *访问<id>属性
     */
     @Id 
    @Column(name="USER_ID", unique=true, nullable=false, scale=0)
    public Long getUserId() {
        return this.userId;
    }	    
    /**     
     *设置<id>属性
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     *访问<username>属性
     */
    @Column(name="USERNAME")
	public String getUsername() {
		return username;
	}
    /**     
     *设置<username>属性
     */
	public void setUsername(String username) {
		this.username = username;
	}
	   /**     
     *访问<deptCode>属性
     */
	 @Column(name="DEPT_NO")
	public String getDeptCode() {
		return deptCode;
	}
    /**     
     *设置<deptCode>属性
     */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	   /**     
     *访问<deptCode>属性
     */
	 @Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}
    /**     
     *设置<deptName>属性
     */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    /**     
    * 访问<roleId>属性
    */
	 @Column(name="ROLE_ID")
    public Long getRoleId() {
		return roleId;
	}
    /**     
     *设置<roleId>属性
     */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	 @Column(name="ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}