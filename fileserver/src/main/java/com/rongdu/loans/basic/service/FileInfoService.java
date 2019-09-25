/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import com.rongdu.loans.basic.entity.FileInfo;

/**
 * 影像资料-业务逻辑接口
 * @author sunda
 * @version 2017-07-05
 */
public interface FileInfoService {
	
	/**
	 * 保存影像资料信息
	 * @param entity
	 * @return
	 */
	public long insert(FileInfo entity);
	
}