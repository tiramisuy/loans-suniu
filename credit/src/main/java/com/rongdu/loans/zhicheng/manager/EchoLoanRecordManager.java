/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.zhicheng.dao.EchoLoanRecordDao;
import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;
import org.springframework.stereotype.Service;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("echoLoanRecordManager")
public class EchoLoanRecordManager extends BaseManager<EchoLoanRecordDao, EchoLoanRecord, String>{
	
}