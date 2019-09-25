package com.rongdu.loans.cust.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.cust.option.QuerySmsOP;
import com.rongdu.loans.cust.vo.SmsCodeVo;

@Service
public interface SmsCodeService{
	
	Page<SmsCodeVo> getSmsCode(@NotNull(message = "分页参数不能为空") Page page,
			@NotNull(message = "参数不能为空") QuerySmsOP op);
	
	List<SmsCodeVo> exportSms(QuerySmsOP op);
}
