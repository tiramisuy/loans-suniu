package com.rongdu.loans.loan.service;

import java.util.Date;
import java.util.List;

import com.rongdu.loans.loan.option.CarefreeCounterfoilDataOP;
import com.rongdu.loans.loan.option.GeneratorEquitableAssignmentDataOP;
import com.rongdu.loans.loan.vo.PactRecord;

/**
 * 无忧存证 生成接口
 * 
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
public interface CarefreeCounterfoilService {

	/**
	 * 生成无忧存证
	 * 
	 * @param carefreeCounterfoilIOP
	 * @return
	 */
	PactRecord generateCardfreeCounterfoil(CarefreeCounterfoilDataOP carefreeCounterfoilIOP);

	/**
	 * 调用无忧存证
	 * 
	 * @param userId
	 *            用户id
	 * @param applyId
	 *            申请订单号
	 * @param payNo
	 *            提现订单号
	 * @return
	 */
	PactRecord generate(String userId, String applyId, String payNo, Date payDate);

	String generateEquitableAssignment(List<GeneratorEquitableAssignmentDataOP> list);
}
