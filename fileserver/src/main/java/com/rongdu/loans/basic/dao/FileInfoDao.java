/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.FileInfo;

/**
 * 影像资料-数据访问接口
 * @author sunda
 * @version 2017-07-05
 */
@MyBatisDao
public interface FileInfoDao extends BaseDao<FileInfo,String> {
	
}