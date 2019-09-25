package com.rongdu.loans.pay.service.impl;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.quickpay.*;
import com.allinpay.demo.xstruct.trans.TransExt;
import com.allinpay.demo.xstruct.trans.TransRet;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.allinpay.demo.xstruct.trans.qry.TransQueryReq;
import com.rongdu.common.config.Global;
import com.rongdu.common.exception.BizException;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.HttpUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.HandlerTypeEnum;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.PayChannelEnum;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.mq.MessageProductor;
import com.rongdu.loans.pay.entity.TltPayEnum;
import com.rongdu.loans.pay.op.*;
import com.rongdu.loans.pay.service.SecService;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.tonglian.vo.TonglianIndependentLoanResultVo;
import com.rongdu.loans.pay.tonglian.vo.TonglianIndependentRspEnum;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.utils.LoanConfig;
import com.rongdu.loans.pay.utils.TltConfig;
import com.rongdu.loans.pay.utils.TonglianErrInfo;
import com.rongdu.loans.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 通联支付协议
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/3
 */
@Slf4j
@Service("tltAgreementPayService")
public class TltAgreementPayServiceImpl implements TltAgreementPayService {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private CustUserService custUserService;

    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private MessageProductor messageProductor;


    /**
     * 通联代扣
     * <p>
     * 1.调用通联代扣接口
     * 2.如果调用代扣接口结果未知，主动查询结果，
     * 3.如果代扣成功，调用代付接口；如果结果未知，返回未知失败状态
     *
     * @param param
     * @param payType code y0524
     * @return
     */
    @Override
    public TlAgreementPayResultVO withhold(WithholdOP param, Integer payType) {
        TlAgreementPayResultVO vo = null;
        try {
            vo = transaction(param);
        } catch (Exception e) {
//            TlWithholdQueryResultVO queryResultVO = queryResult(param);
//            vo.setTradeNo(queryResultVO.getReqNo());
            e.printStackTrace();
        }
        /** 保存代扣记录 */
        RepayLogVO repayLogVO = saveRepayLog(vo, param, payType);
        if (repayLogVO != null && "I".equals(repayLogVO.getStatus())) {
            messageProductor.sendToRepayIngQueue(repayLogVO);
        }
        return vo;
    }


    /**
     * 保存还款记录
     *
     * @param vo
     * @param param
     * @param payType code y0524
     * @return
     */
    private RepayLogVO saveRepayLog(TlAgreementPayResultVO vo, WithholdOP param, Integer payType) {

        CustUserVO user = custUserService.getCustUserById(param.getUserId());

        Date now = new Date();
        RepayLogVO repayLog = new RepayLogVO();
        repayLog.setId(vo.getTradeNo());
        repayLog.setNewRecord(true);
        repayLog.setApplyId(param.getApplyId());
        repayLog.setContractId(param.getContNo());
        repayLog.setRepayPlanItemId(param.getRepayPlanItemId());
        repayLog.setUserId(user.getId());
        repayLog.setUserName(user.getRealName());
        repayLog.setIdNo(user.getIdNo());
        repayLog.setMobile(user.getMobile());
        repayLog.setTxType("WITHHOLD");
        repayLog.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMdd")));
//        if (StringUtils.isNotBlank(vo.getTradeTime())) {
        repayLog.setTxTime(new Date());
//        }
        BigDecimal txAmt = new BigDecimal(param.getTxnAmt())
                .divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
        repayLog.setTxAmt(txAmt);
        repayLog.setTxFee(calWithholdFee(txAmt));
        repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
        repayLog.setChlOrderNo(vo.getTradeNo());
        repayLog.setChlName("通联支付");
        repayLog.setChlCode("TONGLIAN");
        repayLog.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());
        repayLog.setBankCode(user.getBankCode());
        repayLog.setCardNo(user.getCardNo());
        repayLog.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
//		repayLog.setGoodsName("聚宝钱包还款");
        repayLog.setGoodsName("还款");
        repayLog.setGoodsNum(1);
        repayLog.setStatus(vo.getStatus());
        repayLog.setRemark(vo.getResCode() + "," + vo.getResMessage());
        repayLog.setPayType(payType);
//        if (vo.getSuccess()) {
//            BigDecimal succAmt = new BigDecimal(vo.getAmount())
//                    .divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
//            repayLog.setSuccAmt(succAmt);
//            repayLog.setSuccTime(now);
//            repayLog.setStatus(ErrInfo.SUCCESS.getCode());
//        }
        repayLog.setCouponId(param.getCouponId());
        int num = repayLogService.save(repayLog);
        if (num == 0) {
            return null;
        }
        return repayLog;
    }


    /**
     * 计算每笔代扣交易费用
     * 千分之2.5
     *
     * @param fee 单位元
     * @return
     */
    private BigDecimal calWithholdFee(BigDecimal fee) {
        if (fee.compareTo(new BigDecimal(100000)) > 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(0);
    }

//    /**
//     * 查询代扣结果
//     *
//     * @param param
//     * @return
//     */
//    public TlWithholdQueryResultVO queryResult(WithholdOP param) {
//        TlWithholdQueryOP op = new TlWithholdQueryOP();
//        op.setReqSn(param.getTransId());
//        TlWithholdQueryResultVO queryResultVO = null;
//        try {
//            queryResultVO = query(op);
//        } catch (Exception e) {
//            log.error("通联-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(param));
//            return getUnknowStatusVO();
//        }
//        if (queryResultVO == null) {
//            log.error("通联-代扣：结果未知，param = {}", JsonMapper.getInstance().toJson(param));
//            return getUnknowStatusVO();
//        }
//        return queryResultVO;
//    }


    /**
     * 代扣查询
     *
     * @param param
     * @return
     */
    @Override
    public Object query(TlWithholdQueryOP param) {
        log.debug("========31-代扣交易状态查询类交易===========");
        TransQueryReq queryReq = new TransQueryReq();
        queryReq.setMERCHANT_ID(TltConfig.tlt_merchantid);
        //也就是原请求交易中的REQ_SN的值
        //查询交易的文件名
        queryReq.setQUERY_SN(param.getReqSn());

        //调用
        TlWithholdQueryResultVO vo = new TlWithholdQueryResultVO();
        AipgRsp aipgRsp = null;
        try {
            aipgRsp = tltCommonMethod(TltPayEnum.TX_200004, queryReq);
            InfoRsp infoRsp = aipgRsp.getINFO();
            log.info("响应结果码：{}", infoRsp.getRET_CODE());
            log.info("响应消息：{}", infoRsp.getERR_MSG());
            return aipgRsp;
//            if ("0000".equals(infoRsp.getRET_CODE()) || "4000".equals(infoRsp.getRET_CODE())) {
//                QTransRsp ret = (QTransRsp) aipgRsp.trxObj();
//                return ret;
//            } else {
//                return aipgRsp.trxObj();
//            }
        } catch (Exception e) {
            vo.setMsg("代扣查询交易异常....");
            vo.setSuccess(false);
            log.error("代扣查询交易查询异常....");
        }
        return null;
    }

    /**
     * 定义未知状态返回值
     *
     * @return
     */
    private TlWithholdQueryResultVO getUnknowStatusVO() {
        TlWithholdQueryResultVO queryResultVO = new TlWithholdQueryResultVO();
        queryResultVO.setCode("SYS_ERR");
        queryResultVO.setCode("SYS_ERR");
        queryResultVO.setMsg("系统异常，结果未知");
        return queryResultVO;
    }

    /**
     * 代扣交易
     *
     * @param param
     * @return
     */
    public TlAgreementPayResultVO transaction(WithholdOP param) throws Exception {
        log.debug("========13-代扣类交易===========");
        TlAgreementPayOP payOP = new TlAgreementPayOP();
        payOP.setBindId(param.getBindId());
        payOP.setAmount(param.getTxnAmt());
        payOP.setUserId(param.getUserId());
        payOP.setRealName(param.getRealName());
        TlAgreementPayResultVO vo = agreementPay(payOP);
        return vo;
    }


    /**
     * 快捷签约短信
     *
     * @param fagra
     * @return
     */
    @Override
    public BindCardResultVO agreementPayMsgSend(DirectBindCardOP bindCardOP, HandlerTypeEnum handlerTypeEnum) {
        FAGRA fagra = new FAGRA();
        //银行卡或存折上的所有人姓名
        fagra.setACCOUNT_NAME(bindCardOP.getRealName());
        //账号,借记卡或信用卡
        fagra.setACCOUNT_NO(bindCardOP.getCardNo());
        //帐号属性，	0私人，1公司。不填时，默认为私人0。
        fagra.setACCOUNT_PROP("0");
        //银行编码
        fagra.setBANK_CODE(bindCardOP.getBankNo());
        //手机号/小灵通
        fagra.setTEL(bindCardOP.getMobile());
        //证件号
        fagra.setID(bindCardOP.getIdNo());
        //开户证件类型,0身份证
        fagra.setID_TYPE("0");
        //商户代码
        fagra.setMERCHANT_ID(TltConfig.tlt_merchantid);
        //账号类型,00银行卡，01存折，02信用卡。不填默认为银行卡00。
        fagra.setACCOUNT_TYPE("00");
        //信用卡时必填
        fagra.setCVV2("");
        //有效期,信用卡时必填，格式MMYY(信用卡上的两位月两位年)
        fagra.setVAILDDATE("");
        //商户保留信息
        fagra.setMERREM("");
        //备注
        fagra.setREMARK("tl_pay_send_msg");

        //调用
        BindCardResultVO vo = new BindCardResultVO();
        AipgRsp aipgRsp = null;
        try {
            aipgRsp = tltCommonMethod(TltPayEnum.TX_310001, fagra);
            InfoRsp infoRsp = aipgRsp.getINFO();
            log.info("响应结果码：{}", infoRsp.getRET_CODE());
            log.info("响应消息：{}", infoRsp.getERR_MSG());
            if ("0000".equals(infoRsp.getRET_CODE())) {
                FAGRARET fr = (FAGRARET) aipgRsp.trxObj();
                log.info("响应结果码：{}", fr.getRET_CODE());
                log.info("响应消息：{}", fr.getERR_MSG());
                if ("0000".equals(fr.getRET_CODE())) {
                    vo.setSuccess(true);
                    vo.setMsg(fr.getERR_MSG());
                    vo.setCode(fr.getRET_CODE());
                    vo.setOrderNo(infoRsp.getREQ_SN());
                    vo.setBindId(infoRsp.getREQ_SN());
                    // 预绑卡后，保存绑卡信息,外部渠道的话则记录绑卡信息
                    if (!handlerTypeEnum.getCode().equals(HandlerTypeEnum.SYSTEM_HANDLER.getCode())) {
                        saveBindInfo(bindCardOP, infoRsp.getREQ_SN(), "", infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
                    }
                } else {
                    vo.setSuccess(false);
                    vo.setMsg(infoRsp.getERR_MSG());
                    vo.setCode(infoRsp.getRET_CODE());
                    log.error("通联协议支付-预确认支付协议短信异常：{}，{}，{}", bindCardOP.getMobile(), infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
                }
                log.error("通联协议支付-预确认支付协议短信：{}，{}，{}", bindCardOP.getMobile(), infoRsp.getRET_CODE(), infoRsp.getERR_MSG(), infoRsp.getREQ_SN());
            } else {
                vo.setSuccess(false);
                vo.setMsg(infoRsp.getERR_MSG());
                vo.setCode(infoRsp.getRET_CODE());
                log.error("通联协议支付-预确认支付协议短信异常：{}，{}，{}", bindCardOP.getMobile(), infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("通联协议支付-预确认支付协议短信异常");
//            e.printStackTrace();
        }
        return vo;
    }

    /**
     * 快捷签约验证
     *
     * @param fagrc
     * @return
     */
    @Override
    public BindCardResultVO agreementPaySign(ConfirmBindCardOP bindCardOP) throws Exception {
        // 数据
        FAGRC fagrc = new FAGRC();
        //原请求流水,对应申请 请求报文中的REQ_SN
        fagrc.setSRCREQSN(bindCardOP.getBindId());
        //商户号
        fagrc.setMERCHANT_ID(TltConfig.tlt_merchantid);
        //短信验证码
        fagrc.setVERCODE(bindCardOP.getMsgVerCode());

        BindCardResultVO vo = new BindCardResultVO();
        //调用
        AipgRsp aipgRsp = tltCommonMethod(TltPayEnum.TX_310002, fagrc);
        InfoRsp infoRsp = aipgRsp.getINFO();
        vo.setOrderNo(infoRsp.getREQ_SN());
//        log.debug("响应结果码：{}", infoRsp.getRET_CODE());
//        log.error("响应消息：{}", infoRsp.getERR_MSG());
        if ("0000".equals(infoRsp.getRET_CODE())) {
            FAGRCRET ft = (FAGRCRET) aipgRsp.trxObj();
//            FAGRARET fr = (FAGRARET) aipgRsp.trxObj();
            log.debug("响应结果码：{}", ft.getRET_CODE());
            log.error("响应消息：{}", ft.getERR_MSG());
            if ("0000".equals(ft.getRET_CODE())) {
                //绑定的协议号
                vo.setBindId(ft.getAGRMNO());
                vo.setSuccess(true);
                vo.setCode(ft.getRET_CODE());
                vo.setMsg(ft.getERR_MSG());
            } else {
                vo.setSuccess(false);
                vo.setCode(infoRsp.getRET_CODE());
                vo.setMsg(infoRsp.getERR_MSG());
            }
            log.info("通联支付-协议支付签约信息：{},{},{}", vo.getBindId(), vo.getCode(), vo.getMsg());
        } else {
            vo.setSuccess(false);
            vo.setCode(infoRsp.getRET_CODE());
            vo.setMsg(infoRsp.getERR_MSG());
            log.error("通联协议支付-支付协议签约异常：{}，{}，{}", bindCardOP.getOrderNo(), infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
//            throw new Exception("通联协议支付异常..");
        }

        return vo;

    }

//    /**
//     * 协议支付解约
//     *
//     * @param fagrcnl
//     * @return
//     */
//    @Override
//    public AipgRsp agreementPayBreachSign(FAGRCNL fagrcnl) throws Exception {
//        //demo数据
////        FAGRCNL fagrc = new FAGRCNL();
////        fagrc.setMERCHANT_ID(TltConfig.tlt_merchantid);
////        fagrc.setAGRMNO("AIP3989180418000001081");
//
//        //调用
//        AipgRsp aipgRsp = tltCommonMethod(TltPayEnum.TX_310003, fagrcnl);
//        InfoRsp infoRsp = aipgRsp.getINFO();
//        log.debug("响应结果码：{}", infoRsp.getRET_CODE());
//        log.error("响应消息：{}", infoRsp.getERR_MSG());
//        if ("0000".equals(infoRsp.getRET_CODE())) {
//            FAGRCNLRET fr = (FAGRCNLRET) aipgRsp.trxObj();
//            log.debug("响应结果码：{}", fr.getRET_CODE());
//            log.error("响应消息：{}", fr.getERR_MSG());
//        }
//
//        return null;
//    }

    /**
     * 协议支付
     *
     * @param fasttrx
     * @return
     */
    @Override
    public TlAgreementPayResultVO agreementPay(TlAgreementPayOP payOP) {
        //数据
        FASTTRX ft = new FASTTRX();
        ft.setMERCHANT_ID(TltConfig.tlt_merchantid);
        ft.setBUSINESS_CODE("14902");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
        ft.setSUBMIT_TIME(DemoUtil.getNow());

        //协议号,签约时返回的协议号
        ft.setAGRMNO(payOP.getBindId());
        //银行卡或存折上的所有人姓名
        ft.setACCOUNT_NAME(payOP.getRealName());
        //金额	整数，单位分,转换为元
        ft.setAMOUNT(payOP.getAmount());
        // ytodo
        /*if (new BigDecimal(payOP.getAmount()).compareTo(new BigDecimal(100000))==1){
            ft.setAMOUNT("99999");
        }*/
        //自定义用户号，	商户自定义的用户号，开发人员可当作备注字段使用
        ft.setCUST_USERID(payOP.getUserId());
        //备注
        ft.setREMARK("通联协议支付代扣");
        //摘要
        ft.setSUMMARY("通联协议支付代扣");

        TlAgreementPayResultVO vo = new TlAgreementPayResultVO();
        //调用
        AipgRsp aipgRsp = null;
        try {
            aipgRsp = tltCommonMethod(TltPayEnum.TX_310011, ft);
            InfoRsp infoRsp = aipgRsp.getINFO();
            //log.debug("响应结果码：{}", infoRsp.getRET_CODE());
            //log.error("响应消息：{}", infoRsp.getERR_MSG());
            vo.setTradeNo(infoRsp.getREQ_SN());
            if ("0000".equals(infoRsp.getRET_CODE()) || "2000".equals(infoRsp.getRET_CODE())) {
                FASTTRXRET ret = (FASTTRXRET) aipgRsp.trxObj();
                if ("0000".equals(ret.getRET_CODE())) {
                    vo.setResCode(ret.getRET_CODE());
                    vo.setResMessage(ret.getERR_MSG());
                    vo.setTradeTime(ret.getSETTLE_DAY());
                    vo.setAmount(payOP.getAmount());
                    vo.setStatus(TonglianErrInfo.I.getCode());
                    vo.setSuccess(true);
                    //处理中
                } else if (TonglianErrInfo.E2000.getCode().equals(ret.getRET_CODE()) || TonglianErrInfo.E2007.getCode().equals(ret.getRET_CODE()) ||
                        TonglianErrInfo.E2008.getCode().equals(ret.getRET_CODE())) {
                    vo.setResCode(ret.getRET_CODE());
                    vo.setResMessage(ret.getERR_MSG());
                    vo.setTradeTime(ret.getSETTLE_DAY());
                    vo.setAmount(payOP.getAmount());
                    vo.setStatus(TonglianErrInfo.I.getCode());
                } else {
                    vo.setResCode(ret.getRET_CODE());
                    vo.setResMessage(ret.getERR_MSG());
                    vo.setTradeTime(ret.getSETTLE_DAY());
                    vo.setAmount(payOP.getAmount());
                    vo.setStatus(TonglianErrInfo.F.getCode());
                }
                log.info("通联支付-协议支付结果：{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            } else if (TonglianErrInfo.E2007.getCode().equals(infoRsp.getRET_CODE())) {
                vo.setStatus(TonglianErrInfo.I.getCode());
                vo.setResCode(infoRsp.getRET_CODE());
                vo.setResMessage(infoRsp.getERR_MSG());
                log.info("通联支付-协议支付结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            } else if (TonglianErrInfo.E2008.getCode().equals(infoRsp.getRET_CODE())) {
                vo.setStatus(TonglianErrInfo.I.getCode());
                vo.setResCode(infoRsp.getRET_CODE());
                vo.setResMessage(infoRsp.getERR_MSG());
                log.info("通联支付-协议支付结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            } else if (TonglianErrInfo.E1108.getCode().equals(infoRsp.getRET_CODE())) {
                vo.setStatus(TonglianErrInfo.F.getCode());
                vo.setResCode(infoRsp.getRET_CODE());
                vo.setSuccess(false);
                vo.setResMessage(infoRsp.getERR_MSG());
                log.info("通联支付-协议支付结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            } else if (TonglianErrInfo.E1000.getCode().equals(infoRsp.getRET_CODE())) {
                vo.setStatus(TonglianErrInfo.F.getCode());
                vo.setResCode(infoRsp.getRET_CODE());
                vo.setSuccess(false);
                vo.setResMessage(infoRsp.getERR_MSG());
                log.info("通联支付-协议支付结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            } else {
                vo.setStatus(TonglianErrInfo.F.getCode());
                vo.setSuccess(false);
                vo.setResCode(infoRsp.getRET_CODE());
                vo.setResMessage(infoRsp.getERR_MSG());
                log.info("通联支付-协议支付结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
            }
        } catch (Exception e) {
//            e.printStackTrace();
            vo.setStatus(TonglianErrInfo.F.getCode());
            vo.setSuccess(false);
            log.info("通联支付-协议支付失败结果：{},{},{},{},{}", payOP.getBindId(), payOP.getRealName(), vo.getAmount(), vo.getResCode(), vo.getResMessage());
        }
        return vo;
    }

    @Override
    public ConfirmAuthPayVO pay(AuthPayOP op) {
        // 实际支付参数组装
        TlAgreementPayOP tlAgreementPayOP = BeanMapper.map(op, TlAgreementPayOP.class);
        tlAgreementPayOP.setAmount(MoneyUtils.yuan2fen(op.getAmount()));
        TlAgreementPayResultVO tlAgreementPayResultVO = this.agreementPay(tlAgreementPayOP);

        // 支付结果组装
        ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
        confirmAuthPayVO.setSuccess(tlAgreementPayResultVO.isSuccess());
        confirmAuthPayVO.setCode(tlAgreementPayResultVO.getResCode());
        confirmAuthPayVO.setMsg(tlAgreementPayResultVO.getResMessage());

        confirmAuthPayVO.setReqNo(tlAgreementPayResultVO.getTradeNo());
        confirmAuthPayVO.setOrderNo(tlAgreementPayResultVO.getTradeNo());
        confirmAuthPayVO.setSuccAmt(op.getAmount());
        confirmAuthPayVO.setSuccTime(DateUtils.getDateTime());
        confirmAuthPayVO.setStatus(tlAgreementPayResultVO.getStatus());

        op.setPayChannel(PayChannelEnum.TONGLIAN.getChannelCode());
        RepayLogVO repayLogVO = repayLogService.saveRepayLog(confirmAuthPayVO, op);
        if (repayLogVO != null && "I".equals(confirmAuthPayVO.getStatus())) {
            messageProductor.sendToRepayIngQueue(repayLogVO);
        }
        return confirmAuthPayVO;
    }

    /**
     * 通联支付前置执行方法
     *
     * @param pw
     * @return
     */
    @Override
    public boolean prePayment(PayLogVO pw) {
        pw.setId("TL" + DateUtils.getHHmmss() + (int) (new Random().nextInt(900) + 100));
        pw.setIsNewRecord(true);
        pw.setTxType("withdraw");
        pw.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
        pw.setTxTime(new Date());
        int record = payLogService.findPaymentRecord(pw.getApplyId());
        if (record > 0) {
            log.warn("通联-代付，该笔代付申请订单已经提交成功，请勿重复付款：{}，{}元，{}", pw.getToAccName(), pw.getTxAmt(), pw.getToAccNo());
            return Boolean.FALSE;
        }
        log.info("通联-代付，准备付款到账户：{}，{}元，{}", pw.getToAccName(), pw.getTxAmt(), pw.getToAccNo());

        if ("tonglian2".equals(TltConfig.is_merchant_profile)) {
            // 通联独立账户2放款
            return tonghuaLoan(pw);
        } else {
            // 向通联发送代付放款指令
            return signOrderPayroll(pw);
        }
    }


    /**
     * 通联账户2放款
     *
     * @param payLogVO
     */
    @Override
    public boolean tonghuaLoan(PayLogVO payLogVO) {
        log.info("=========================通联账户2放款=====================");
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("version", "1.0");
        //签名方法  01：非对称（RSA）
        reqMap.put("sign_method", "01");
        //交易类型,10：付款
        reqMap.put("trans_code", "10");
        //商户代码
        reqMap.put("mer_code", LoanConfig.loan_merchantid);
        //商户系统产生并上送，同一商户同一交易日内唯一
        reqMap.put("order_no", "ON" + String.valueOf(System.nanoTime()));
        //订单发送时间
        reqMap.put("txn_date", DateUtils.formatDate(payLogVO.getTxTime(), "yyyyMMddHHmmss"));
        //交易金额
        reqMap.put("amount", String.valueOf(payLogVO.getTxAmt().multiply(BigDecimal.valueOf(100)).intValue()));
        //交易币种
        reqMap.put("currency", "156");
        //卡号
        reqMap.put("card_no", payLogVO.getToAccNo());
        //姓名
        reqMap.put("name", payLogVO.getToAccName());
        String respJson = null;
        boolean flag;
        try {
            String pfxPath = this.getClass().getResource(LoanConfig.loan_key_path).getPath();
            // ytodo url
            pfxPath = new URI(pfxPath).getPath();
            SecService.signature(reqMap, pfxPath);
            respJson = HttpUtils.postForJson(LoanConfig.loan_url, reqMap);
            TonglianIndependentLoanResultVo resultVo = (TonglianIndependentLoanResultVo) JsonMapper.fromJsonString(respJson, TonglianIndependentLoanResultVo.class);
            payLogVO.setChlOrderNo(resultVo.getOrder_no());
            log.info("响应内容:{}", respJson);
            if (resultVo.getResp_code().equals(TonglianIndependentRspEnum.TR_00.getCode())) {
                flag = Boolean.TRUE;
                payLogVO.setStatus(TonglianErrInfo.I.getCode());
                payLogVO.setRemark(resultVo.getResp_code() + "," + resultVo.getResp_msg());
            } else if (resultVo.getResp_code().equals(TonglianIndependentRspEnum.TR_99.getCode()) || resultVo.getResp_code().equals(TonglianIndependentRspEnum.TR_18.getCode()) || resultVo.getResp_code().equals(TonglianIndependentRspEnum.TR_19.getCode())) {
                // 放款处理中，定时查询放款结果
                flag = Boolean.TRUE;
                payLogVO.setStatus(TonglianErrInfo.I.getCode());
                payLogVO.setRemark(resultVo.getResp_code() + "," + resultVo.getResp_msg());
            } else {
                // 放款失败，手动处理补偿放款或取消放款
                flag = Boolean.TRUE;
                payLogVO.setStatus(TonglianErrInfo.F.getCode());
                payLogVO.setRemark("放款失败：" + resultVo.getResp_code() + "," + resultVo.getResp_msg());
            }
        } catch (Exception e) {
            flag = Boolean.FALSE;
            log.error("放款异常,放款失败...");
            payLogVO.setStatus(TonglianErrInfo.F.getCode());
            String remarks = StringUtils.substring(e.getMessage(), 0, 99);
            payLogVO.setRemark(remarks);
        } finally {
            payLogVO.setChlCode(Global.TONGLIAN_LOAN_CHANNEL_CODE);
            payLogVO.setChlName(Global.TONGLIAN_LOAN_CHANNEL_NAME);
            int rz = payLogService.save(payLogVO);
            if (rz > 0 && "I".equals(payLogVO.getStatus())) {
                // 放款处理中立刻查询放款结果
                messageProductor.sendToPayIngQueue(payLogVO);
            }
        }
        return flag;
    }

    @Override
    public TonglianQueryResultVo tonghuaLoanQuery(TlWithholdQueryOP param) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("version", "1.0");
        //签名方法  01：非对称（RSA）
        reqMap.put("sign_method", "01");
        //交易类型, 90:查询
        reqMap.put("trans_code", "90");
        //商户代码
        reqMap.put("mer_code", LoanConfig.loan_merchantid);
        //商户系统产生并上送，同一商户同一交易日内唯一
        reqMap.put("order_no", param.getReqSn());
        //订单发送时间
        reqMap.put("txn_date", param.getTxnDate());
        try {
            String pfxPath = this.getClass().getResource(LoanConfig.loan_key_path).getPath();
            // ytodo url
            pfxPath = new URI(pfxPath).getPath();
            SecService.signature(reqMap, pfxPath);
            String respJson = HttpUtils.postForJson(LoanConfig.loan_url, reqMap);
            return (TonglianQueryResultVo) JsonMapper.fromJsonString(respJson, TonglianQueryResultVo.class);
        } catch (Exception e) {
            log.error("代扣查询交易查询异常....");
        }
        return new TonglianQueryResultVo();
    }

    /**
     * 通联账户1单笔实时代付(出金/提现)(100014)
     *
     * @param transExt
     * @return
     */
    @Override
    public boolean signOrderPayroll(PayLogVO payLogVO) {
        log.info("=========================通联账户1放款=====================");
        TransExt trans = new TransExt();
        //账号名,银行卡或存折上的持有人姓名。
        trans.setACCOUNT_NAME(payLogVO.getToAccName());
        //帐号,银行卡或存折号码
        trans.setACCOUNT_NO(payLogVO.getToAccNo());
        //账号属性 0私人，1公司。不填时，默认为私人0。
        trans.setACCOUNT_PROP("0");
        //金额,	整数
        trans.setAMOUNT(MoneyUtils.yuan2fen(payLogVO.getTxAmt().toString()));
        //银行代码, 4位或8位置，参见附录A.3银行代码
        trans.setBANK_CODE(payLogVO.getToBankCode());
        //手机号/小灵通
        trans.setTEL(payLogVO.getToMobile());
        //自定义用户号
        trans.setCUST_USERID(payLogVO.getToIdno());
        //货币类型,人民币：CNY, 港元：HKD，美元：USD。不填时，默认为人民币。
        trans.setCURRENCY("CNY");
        //必须使用业务人员提供的业务代码，否则返回“未开通业务类型”,,代付的业务代码为09900
        trans.setBUSINESS_CODE("09900");
        //设置商户
        trans.setMERCHANT_ID(TltConfig.tlt_merchantid);
        //设置提交时间
        trans.setSUBMIT_TIME(DemoUtil.getNow());
        //调用
        AipgRsp aipgRsp = null;
        Boolean falg;
        try {
            aipgRsp = tltCommonMethod(TltPayEnum.TX_100014, trans);
            if (aipgRsp != null) {
                InfoRsp infoRsp = aipgRsp.getINFO();
                log.debug("响应结果码：{}", infoRsp.getRET_CODE());
                log.error("响应消息：{}", infoRsp.getERR_MSG());
                payLogVO.setChlOrderNo(infoRsp.getREQ_SN());
                if ("0000".equals(infoRsp.getRET_CODE()) || "4000".equals(infoRsp.getRET_CODE())) {
                    TransRet ret = (TransRet) aipgRsp.trxObj();
                    log.debug("响应结果码：{}", ret.getRET_CODE());
                    log.debug("响应完成日期：{}", ret.getSETTLE_DAY());
                    log.error("响应消息：{}", ret.getERR_MSG());
                    if ("0000".equals(ret.getRET_CODE())) {
                        falg = Boolean.TRUE;
                        payLogVO.setStatus(TonglianErrInfo.I.getCode());
                        payLogVO.setRemark(ret.getERR_MSG());
                    } else {
                        falg = Boolean.FALSE;
                        payLogVO.setStatus(TonglianErrInfo.I.getCode());
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + "," + ret.getERR_MSG());
                    }
                } else {
                    falg = Boolean.FALSE;
                    payLogVO.setStatus(TonglianErrInfo.I.getCode());
                    if ("2007".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",提交银行处理中");
                    } else if ("2000".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",系统处理数据中");
                    } else if ("2001".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",等待商户审核");
                    } else if ("2003".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",等待受理");
                    } else if ("2005".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",等待符核");
                    } else if ("2008".equals(infoRsp.getRET_CODE())) {
                        payLogVO.setRemark("放款处理中：" + infoRsp.getRET_CODE() + ",实时交易超时");
                    } else {
                        payLogVO.setStatus(TonglianErrInfo.F.getCode());
                        payLogVO.setRemark("放款失败：" + infoRsp.getRET_CODE() + "," + infoRsp.getERR_MSG());
                    }
                }
            } else {
                throw new BizException("通联-代付：应答报文缺少报文头");
            }
        } catch (Exception e) {
            log.error("通联-代付-异常：{}", e.getMessage());
            falg = Boolean.FALSE;
            payLogVO.setStatus(TonglianErrInfo.F.getCode());
            String remarks = StringUtils.substring(e.getMessage(), 0, 99);
            payLogVO.setRemark(remarks);
        } finally {
            payLogVO.setChlCode(Global.TONGLIAN_CHANNEL_CODE);
            payLogVO.setChlName(Global.TONGLIAN_CHANNEL_NAME);
            int rz = payLogService.save(payLogVO);
            if (rz > 0 && "I".equals(payLogVO.getStatus())) {
                // 放款处理中立刻查询放款结果
                messageProductor.sendToPayIngQueue(payLogVO);
            }
        }
        return falg;
    }

    private String generateBindCardId(String idCard, String accNo, String mobile) {
        String input = idCard + accNo + mobile;
        return Digests.md5(input);
    }

    /**
     * 保存通联预绑卡信息
     *
     * @param param
     * @param transId
     * @param bindId
     * @param code
     * @param msg
     * @return
     */
    @Transactional
    BindCardVO saveBindInfo(DirectBindCardOP param, String transId, String bindId, String code, String msg) {
        BindCardVO bindInfo = null;
        String id = generateBindCardId(param.getIdNo(), param.getCardNo(), param.getMobile());
        bindInfo = bindCardService.get(id);
        // 预绑卡时，创建绑卡信息
        if (bindInfo == null) {
            bindInfo = new BindCardVO();
            bindInfo.setIsNewRecord(true);
            bindInfo.setId(id);
        }
        bindInfo.setUserId(param.getUserId());
        bindInfo.setCardNo(param.getCardNo());
        String bankCode = param.getBankCode();
        String bankName = BankLimitUtils.getNameByBankCode(bankCode);
        bindInfo.setBankCode(bankCode);
        bindInfo.setBankName(bankName);
        bindInfo.setSource(param.getSource());
        bindInfo.setIdNo(param.getIdNo());
        bindInfo.setName(param.getRealName());
        bindInfo.setMobile(param.getMobile());
        bindInfo.setTxTime(new Date());
        bindInfo.setTxType("agree");
        bindInfo.setChlCode("TONGLIAN");
        bindInfo.setChlName("通联确认协议支付");
        bindInfo.setTxDate(DateUtils.getDate("yyyyMMdd"));
        bindInfo.setIp(param.getIpAddr());
        bindInfo.setChlOrderNo(transId);
        bindInfo.setStatus(code);
        bindInfo.setRemark(msg);
        bindInfo.setBindId(bindId);
        // 预绑卡时，保存绑卡信息
        if (bindInfo.getIsNewRecord()) {
            bindCardService.save(bindInfo);
            log.info("协议支付-预绑卡-记录绑卡信息：{},{},{},{}", param.getUserId(), bindInfo.getBankName(), param.getCardNo(),
                    param.getMobile());
        } else {
            log.info("协议支付-预绑卡-更新绑卡信息：{},{},{},{},{}", msg, param.getUserId(), bindInfo.getBankName(),
                    param.getCardNo(), param.getMobile());
            bindCardService.update(bindInfo);
        }
        return bindInfo;
    }


    /**
     * 通联支付协议 comon方法
     *
     * @param tltPayEnum
     * @param obj[]
     * @return
     * @throws Exception
     */
    private AipgRsp tltCommonMethod(TltPayEnum tltPayEnum, Object... obj) throws Exception {
        log.info("==================通联支付协议,{}=========================", tltPayEnum.getDesc());
        InfoReq inforeq = DemoUtil.makeReq(tltPayEnum.getCode(), TltConfig.tlt_merchantid, TltConfig.tlt_username, TltConfig.tlt_userpass);
        AipgReq req = new AipgReq();
        req.setINFO(inforeq);
        for (Object o : obj) {
            req.addTrx(o);
        }
        try {
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String pfxPath = this.getClass().getResource(TltConfig.tlt_pathpfx).getPath();
            String pathcer = this.getClass().getResource(TltConfig.tlt_pathcer).getPath();
            // ytodo url
            pfxPath = new URI(pfxPath).getPath();
            pathcer = new URI(pathcer).getPath();
            String signedXml = DemoUtil.buildSignedXml(xml, TltConfig.tlt_pfxpass, pfxPath);
            //step3 发往通联
            String url = TltConfig.tlt_url + "?MERCHANT_ID=" + TltConfig.tlt_merchantid + "&REQ_SN=" + inforeq.getREQ_SN();
            log.debug("请求报文：{}", signedXml);
            log.debug("请求地址:{}", url);
            String respText = HttpUtil.post(signedXml, url);
            log.debug("响应报文：{}", respText);
            //step4 验签
            if (!DemoUtil.verifyXml(respText, pathcer)) {
                log.error("验签失败");
                throw new Exception("验签失败");
            }
            //step5 xml转对象
            AipgRsp rsp = XmlParser.parseRsp(respText);
            return rsp;
        } catch (AIPGException e) {
            e.printStackTrace();
        }
        return new AipgRsp();
    }


//    /**
//     * 协议支付并签约
//     *
//     * @param fasttrx
//     * @return
//     */
//    @Override
//    public AipgRsp agreementPayAndSign(FASTTRX fasttrx) throws Exception {
//        //demo 数据
////        FASTTRX ft = new FASTTRX();
////        ft.setMERCHANT_ID(TltConfig.tlt_merchantid);
////        ft.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
////        ft.setSUBMIT_TIME(TltUtils.getNow());
////        ft.setAGRMNO("AIP5988180829000002067");
////        ft.setACCOUNT_NAME("买单宝专用四");
////        ft.setAMOUNT("90");
////        ft.setVER_CODE("111111");
////        ft.setSRC_REQ_SN("200604000005095-0001536132935606");
////        ft.setCUST_USERID("哈哈哈哈");
////        ft.setREMARK("a发送到发斯蒂芬");
////        ft.setSUMMARY("asjdfasdfkasdf");
////
////        LedgerDtl dtl1 = new LedgerDtl();
////        dtl1.setAMOUNT("1");
////        dtl1.setMERCHANT_ID(TltConfig.tlt_merchantid);
////        dtl1.setSN("0");
////        dtl1.setTYPE("0");
////
////		/*Ledgers ledgers = new Ledgers();
////		ledgers.addTrx(dtl1);*/
////        AipgRsp aipgRsp = tltCommonMethod(TltPayEnum.TX_310010, fasttrx , ledgers);
//
//        //调用
//        AipgRsp aipgRsp = tltCommonMethod(TltPayEnum.TX_310010, fasttrx);
//        InfoRsp infoRsp = aipgRsp.getINFO();
//        log.debug("响应结果码：{}", infoRsp.getRET_CODE());
//        log.error("响应消息：{}", infoRsp.getERR_MSG());
//        if ("0000".equals(infoRsp.getRET_CODE())) {
//            FASTTRXRETC ret = (FASTTRXRETC) aipgRsp.trxObj();
//            log.debug("响应结果码：{}", ret.getRET_CODE());
//            log.debug("响应完成日期：{}", ret.getSETTLE_DAY());
//            log.error("响应消息：{}", ret.getERR_MSG());
//
//        }
//        return null;
//    }

//    /**
//     * 单笔实时代收(代扣)
//     *
//     * @param transExt
//     * @return
//     */
//    @Override
//    public AipgRsp signOrderCollection(TransExt transExt) throws Exception {
//        //demo 数据
////        TransExt trans = new TransExt();
////        trans.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
////        trans.setMERCHANT_ID(TltConfig.tlt_merchantid);
////        trans.setSUBMIT_TIME(TltUtils.getNow());
////        trans.setACCOUNT_NAME("银联000000");
////        trans.setACCOUNT_NO("6224243000000038");
////        trans.setACCOUNT_PROP("0");
////        trans.setAMOUNT("100");
////        trans.setBANK_CODE("0102");
////        trans.setCURRENCY("CNY");
////        trans.setTEL("");
////        trans.setCUST_USERID("410421197808247033");
//
//        //调用
//        AipgRsp aipgRsp = tltCommonMethod(TltPayEnum.TX_100011, transExt);
//        InfoRsp infoRsp = aipgRsp.getINFO();
//        log.debug("响应结果码：{}", infoRsp.getRET_CODE());
//        log.error("响应消息：{}", infoRsp.getERR_MSG());
//        if ("0000".equals(infoRsp.getRET_CODE())) {
//            TransRet ret = (TransRet) aipgRsp.trxObj();
//            log.debug("响应结果码：{}", ret.getRET_CODE());
//            log.debug("响应完成日期：{}", ret.getSETTLE_DAY());
//            log.error("响应消息：{}", ret.getERR_MSG());
//
//        }
//
//        return null;
//    }

}
