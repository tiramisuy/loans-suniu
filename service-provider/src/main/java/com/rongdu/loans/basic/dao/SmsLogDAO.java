package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.SmsLog;
import org.apache.ibatis.annotations.Param;

/**
 * 短信日志DAO接口
 * @author sunda
 * @version 2017-07-25
 */
@MyBatisDao
public interface SmsLogDAO  extends BaseDao<SmsLog, String>{

	int countRegBlackList(@Param("ip")String ip);
}
