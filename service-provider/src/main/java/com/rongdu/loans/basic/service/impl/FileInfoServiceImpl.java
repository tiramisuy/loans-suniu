/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 影像资料-业务逻辑实现类
 * @author zhangxiaolong
 * @version 2017-07-13
 */
@Service("fileInfoService")
public class FileInfoServiceImpl  extends BaseService implements  FileInfoService{
	
	/**
 	* 影像资料-实体管理接口
 	*/
	@Autowired
	private FileInfoManager fileInfoManager;

}