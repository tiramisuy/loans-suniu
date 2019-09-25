/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.baiqishi.dao.MnoCallRecordDao;
import com.rongdu.loans.baiqishi.entity.MnoCallRecord;
import org.springframework.stereotype.Service;

/**
 * 白骑士-运营商通话记录-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("mnoCallRecordManager")
public class MnoCallRecordManager extends BaseManager<MnoCallRecordDao, MnoCallRecord, String>{
	
}