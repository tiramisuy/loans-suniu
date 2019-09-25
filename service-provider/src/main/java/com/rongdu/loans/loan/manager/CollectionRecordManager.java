/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.CollectionRecordDao;
import com.rongdu.loans.loan.entity.CollectionRecord;
import org.springframework.stereotype.Service;

/**
 * 催收记录-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-10-09
 */
@Service("collectionRecordManager")
public class CollectionRecordManager extends BaseManager<CollectionRecordDao, CollectionRecord, String> {
	
}