package com.rongdu.loans.tongrong.api.service;

import com.rongdu.loans.tongrong.op.TRPayOP;
import com.rongdu.loans.tongrong.vo.TRPayVO;

public interface TRPayApiService {

	public TRPayVO pay(TRPayOP op);
}
