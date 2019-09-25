package com.rongdu.loans.hanjs.api.service;

import com.rongdu.loans.hanjs.op.HanJSOrderOP;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.op.HanJSWithdrawOP;
import com.rongdu.loans.hanjs.op.QueryOrderStateOP;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;

public interface HanJSApiUserService {

	HanJSResultVO openAccount(HanJSUserOP op);

	HanJSResultVO withdraw(HanJSWithdrawOP op);

	HanJSResultVO pushBid(HanJSOrderOP op);

	HanJSResultVO queryOrderState(QueryOrderStateOP op);
}
