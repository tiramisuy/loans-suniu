/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface EchoLoanRecordDao extends BaseDao<EchoLoanRecord,String> {
	
}