/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.baiqishi.dao.MnoBillRecordDao;
import com.rongdu.loans.baiqishi.entity.MnoBillRecord;
import org.springframework.stereotype.Service;

/**
 * 白骑士-运营商账单记录-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("mnoBillRecordManager")
public class MnoBillRecordManager extends BaseManager<MnoBillRecordDao, MnoBillRecord, String>{
	
}