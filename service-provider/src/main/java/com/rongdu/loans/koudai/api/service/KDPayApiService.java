package com.rongdu.loans.koudai.api.service;

import com.rongdu.loans.koudai.op.pay.KDPayOP;
import com.rongdu.loans.koudai.op.pay.KDPayQueryOP;
import com.rongdu.loans.koudai.vo.pay.KDPayQueryVO;
import com.rongdu.loans.koudai.vo.pay.KDPayVO;

public interface KDPayApiService {

	public KDPayVO pay(KDPayOP op);

	public KDPayQueryVO query(KDPayQueryOP op);
}
