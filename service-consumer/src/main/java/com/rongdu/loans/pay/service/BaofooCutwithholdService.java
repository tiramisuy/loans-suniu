package com.rongdu.loans.pay.service;

import com.rongdu.loans.pay.op.WithholdOP;
import com.rongdu.loans.pay.op.WithholdQueryOP;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;

/**
 * 宝付分账代扣接口
 * 
 * @author likang
 */
public interface BaofooCutwithholdService {

	/**
	 * 宝付代扣
	 * 
	 * @param param
	 * @param payType
	 *            code y0524
	 * @return
	 */
	WithholdResultVO withhold(WithholdOP param, Integer payType);

	/**
	 * 查询宝付代扣交易
	 * 
	 * @param param
	 * @return
	 */
	public WithholdQueryResultVO queryResult(WithholdOP param);

	/**
	 * 查询宝付代扣交易
	 * 
	 * @param op
	 * @return
	 */
	public WithholdQueryResultVO queryWithholdResult(WithholdQueryOP op);

}
