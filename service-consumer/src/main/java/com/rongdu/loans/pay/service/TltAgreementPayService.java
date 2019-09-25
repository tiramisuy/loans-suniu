package com.rongdu.loans.pay.service;

import com.rongdu.loans.common.HandlerTypeEnum;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.op.*;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.TlAgreementPayResultVO;
import com.rongdu.loans.pay.vo.TonglianQueryResultVo;

/**
 * 通联协议支付商户接口
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/3
 */
public interface TltAgreementPayService {

    /**
     * 交易结果查询
     *
     * @param param
     * @return
     */
    Object query(TlWithholdQueryOP param);

    /**
     * 协议支付签约短信触发(310001)
     *
     * @param fagra
     * @return
     */
    BindCardResultVO agreementPayMsgSend(DirectBindCardOP bindCardOP, HandlerTypeEnum handlerTypeEnum) throws Exception;


    /**
     * 协议支付签约(310002)
     *
     * @param fagrcext
     * @return
     */
    BindCardResultVO agreementPaySign(ConfirmBindCardOP param) throws Exception;

    /**
     * 通联支付
     *
     * @param withholdOP
     * @param payType
     * @return
     */
    TlAgreementPayResultVO withhold(WithholdOP withholdOP, Integer payType);

    /**
     * 协议支付(310011)
     *
     * @param fasttrx
     * @return
     */
    TlAgreementPayResultVO agreementPay(TlAgreementPayOP payOP);

    ConfirmAuthPayVO pay(AuthPayOP payOP);

    /**
     * 前置执行
     *
     * @param pw
     * @return
     */
    boolean prePayment(PayLogVO pw);

    /**
     * 通联独立代付
     *
     * @param payLogVO
     * @return
     */
    boolean tonghuaLoan(PayLogVO payLogVO);

    /**
     * 通联独立代付交易结果查询
     *
     * @param param
     * @return
     */
    TonglianQueryResultVo tonghuaLoanQuery(TlWithholdQueryOP param);


    /**
     * 单笔实时代付(出金/提现)(100014)
     *
     * @param payLogVO
     * @return
     */
    boolean signOrderPayroll(PayLogVO payLogVO) throws Exception;

}
