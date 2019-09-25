/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.manager.impl;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.basic.dao.FileInfoDao;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.FileInfoManager;

/**
 * 影像资料-实体管理实现类
 * @author sunda
 * @version 2017-07-05
 */
@Service("fileInfoManager")
public class FileInfoManagerImpl extends BaseManager<FileInfoDao, FileInfo, String> implements FileInfoManager{
	
}