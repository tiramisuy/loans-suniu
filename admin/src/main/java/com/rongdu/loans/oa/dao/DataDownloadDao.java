/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.oa.dao;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.oa.entity.DataDownload;

/**
 * 数据下载表-数据访问接口
 * @author zhuchangbing
 * @version 2018-12-24
 */
@MyBatisDao
public interface DataDownloadDao extends CrudDao<DataDownload> {
	
}