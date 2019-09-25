
package com.rongdu.loans.cust.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.option.QuerySmsOP;
import com.rongdu.loans.cust.vo.SmsCodeVo;

@MyBatisDao
public interface SmsCodeDao extends BaseDao<SmsCodeVo, String>{
	
	List<SmsCodeVo> getSmsCode(@Param(value = "page") Page<SmsCodeVo> page,
			@Param(value = "op") QuerySmsOP op);
	
	List<SmsCodeVo> exportSms(@Param(value = "op") QuerySmsOP op);
}
