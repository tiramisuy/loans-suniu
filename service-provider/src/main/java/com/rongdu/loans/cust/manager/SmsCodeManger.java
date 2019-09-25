package com.rongdu.loans.cust.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.CrudService;
import com.rongdu.loans.cust.dao.SmsCodeDao;
import com.rongdu.loans.cust.option.QuerySmsOP;
import com.rongdu.loans.cust.vo.SmsCodeVo;
import com.rongdu.loans.loan.vo.ApplyListVO;

@Service("smsCodeManger")
public class SmsCodeManger extends CrudService<SmsCodeDao, SmsCodeVo, String>{
	
	@Autowired
	private SmsCodeDao smsCodeDao;
	
	public List<SmsCodeVo> getSmsCode(Page page, QuerySmsOP op) {
		return smsCodeDao.getSmsCode(page, op);
	}
	
	public List<SmsCodeVo> exportSms(QuerySmsOP op) {
		return smsCodeDao.exportSms(op);
	}
}
