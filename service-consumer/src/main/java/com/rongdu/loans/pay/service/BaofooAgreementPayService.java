package com.rongdu.loans.pay.service;

import com.rongdu.loans.pay.op.AuthPayQueryOP;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.PreAuthPayOP;
import com.rongdu.loans.pay.vo.AuthPayQueryResultVO;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;

/**
 * 宝付协议支付商户接口
 * Created by lee on 2018/4/12.
 */
public interface BaofooAgreementPayService {

    ConfirmAuthPayVO agreementPay(PreAuthPayOP param) throws Exception;

    AuthPayQueryResultVO queryAgreementPayResult(AuthPayQueryOP op);

    BindCardResultVO agreementPreBind(DirectBindCardOP param) throws Exception;

    BindCardResultVO agreementConfirmBind(ConfirmBindCardOP param) throws Exception;
	
    boolean unBind(String userId, String bindId) throws Exception;
}
