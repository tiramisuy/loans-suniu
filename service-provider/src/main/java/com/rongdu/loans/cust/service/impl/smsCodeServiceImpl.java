package com.rongdu.loans.cust.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.cust.manager.SmsCodeManger;
import com.rongdu.loans.cust.option.QuerySmsOP;
import com.rongdu.loans.cust.service.SmsCodeService;
import com.rongdu.loans.cust.vo.SmsCodeVo;

@Service("smsCodeService")
public class smsCodeServiceImpl extends BaseService implements SmsCodeService{

	@Autowired
	private SmsCodeManger smsCodeManger;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<SmsCodeVo> getSmsCode(Page page, QuerySmsOP op) {
		List<SmsCodeVo> list = smsCodeManger.getSmsCode(page, op);
		if (CollectionUtils.isEmpty(list)) {
			page.setList(Collections.emptyList());
			return page;
		}
		page.setList(list);
		return page;
	}

	@Override
	public List<SmsCodeVo> exportSms(QuerySmsOP op) {
		return smsCodeManger.exportSms(op);
	}

}
