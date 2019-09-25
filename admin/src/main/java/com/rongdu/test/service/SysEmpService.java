/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.service;
import java.util.List;

import com.rongdu.common.persistence.Page;
import com.rongdu.test.entity.SysEmp;
/**
 * 员工信息Service
 * @author sunda
 * @version 2016-11-20
 */
public interface SysEmpService  {
	
	/**
 	* 查询员工信息
 	* @author sunda
 	*/	
	public SysEmp get(String id);
	
	/**
 	* 查询符合条件的员工信息
 	* @author sunda
 	*/
	public List<SysEmp> findList(SysEmp sysEmp) ;

	/**
 	* 分页查询员工信息
 	* @author sunda
 	*/	
	public Page<SysEmp> findPage(Page<SysEmp> page, SysEmp sysEmp);
	
	/**
 	* 保存员工信息
 	* @author sunda
 	*/
	public void save(SysEmp sysEmp) ;
	
	/**
 	* 删除员工信息
 	* @author sunda
 	*/
	public void delete(SysEmp sysEmp) ;
	
}