/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.service.FileInfoService;
/**
 * 影像资料-业务逻辑实现类
 * @author sunda
 * @version 2017-07-05
 */
@Service("fileInfoService")
public class FileInfoServiceImpl  extends BaseService implements  FileInfoService{
	
	@Autowired
	private FileInfoManager fileInfoManager;
	
	/**
	 * 保存影像资料信息
	 * @param entity
	 * @return
	 */
	public long  insert(FileInfo entity){
		return fileInfoManager.insert(entity);
	}
	
}