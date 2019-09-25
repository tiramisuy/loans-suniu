/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.CrudService;
import com.rongdu.test.dao.SysEmpDao;
import com.rongdu.test.entity.SysEmp;
import com.rongdu.test.service.SysEmpService;
/**
 * 鍛樺伐淇℃伅Service
 * @author sunda
 * @version 2016-11-20
 */
@Service("sysEmpService")
@Transactional(readOnly = true)
public class SysEmpServiceImpl  extends CrudService<SysEmpDao, SysEmp> implements  SysEmpService{

	/**
 	* 鏌ヨ鍛樺伐淇℃伅
 	* @author sunda
 	*/	
	public SysEmp get(String id) {
		return super.get(id);
	}

	/**
 	* 鏌ヨ绗﹀悎鏉′欢鐨勫憳宸ヤ俊鎭� 	* @author sunda
 	*/	
	public List<SysEmp> findList(SysEmp sysEmp) {
		return super.findList(sysEmp);
	}
	
	/**
 	* 鍒嗛〉鏌ヨ鍛樺伐淇℃伅
 	* @author sunda
 	*/		
	public Page<SysEmp> findPage(Page<SysEmp> page, SysEmp sysEmp) {
		return super.findPage(page, sysEmp);
	}
	
	/**
 	* 淇濆瓨鍛樺伐淇℃伅
 	* @author sunda
 	*/
	@Transactional(readOnly = false)
	public void save(SysEmp sysEmp) {
		super.save(sysEmp);
	}
	
	/**
 	* 鍒犻櫎鍛樺伐淇℃伅
 	* @author sunda
 	*/
	@Transactional(readOnly = false)
	public void delete(SysEmp sysEmp) {
		super.delete(sysEmp);
	}
	
}