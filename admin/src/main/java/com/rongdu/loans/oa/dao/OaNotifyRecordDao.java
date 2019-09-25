/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.oa.dao;

import java.util.List;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.oa.entity.OaNotifyRecord;

/**
 * 通知通告记录DAO接口
 * @author sunda
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaNotifyId(String oaNotifyId);
	
}