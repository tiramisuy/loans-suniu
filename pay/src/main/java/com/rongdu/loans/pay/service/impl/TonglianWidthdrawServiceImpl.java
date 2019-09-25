package com.rongdu.loans.pay.service.impl;

import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.rongdu.common.config.Global;
import com.google.common.collect.Maps;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.op.TlWithholdQueryOP;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.service.TonglianWithdrawService;
import com.rongdu.loans.pay.tonglian.vo.TonglianIndependentRspEnum;
import com.rongdu.loans.pay.utils.TonglianErrInfo;
import com.rongdu.loans.pay.utils.WithdrawErrInfo;
import com.rongdu.loans.pay.vo.TonglianQueryResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Slf4j
@Service("tonglianWithdrawService")
public class TonglianWidthdrawServiceImpl implements TonglianWithdrawService {
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private TltAgreementPayService tltAgreementPayService;
    @Autowired
    private CustUserService userService;
    @Autowired
    private ContractService contractService;


    public static final List<String> TONGLIAN_PAY_UNSOLVED_STATUS = new ArrayList<String>();
    public static final List<String> TONGLIAN_LOAN_UNSOLVED_STATUS = new ArrayList<String>();
    public static final List<String> BAOFOO_PAY_SUCCESS_STATUS = new ArrayList<String>();
    public static final Map<String, String> middleStateMap = new HashMap<String, String>() {{
        put("1000", "无此交易,中间状态");
        put("1002", "无此交易,中间状态");
        put("2000", "批次中存在处理中的交易,中间状态");
        put("2001", "批次中存在处理中的交易,中间状态");
        put("2003", "批次中存在处理中的交易,中间状态");
        put("2005", "批次中存在处理中的交易,中间状态");
        put("2007", "批次中存在处理中的交易,中间状态");
        put("2008", "批次中存在处理中的交易,中间状态");
    }};

    static {
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.I.getCode());
//        BAOFOO_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.F.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.E0.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.E2000.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.E2003.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.E2007.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.E2008.getCode());
        TONGLIAN_PAY_UNSOLVED_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_3));


        TONGLIAN_LOAN_UNSOLVED_STATUS.add(TonglianErrInfo.I.getCode());
//        BAOFOO_PAY_UNSOLVED_STATUS.add(TonglianErrInfo.F.getCode());
        TONGLIAN_LOAN_UNSOLVED_STATUS.add(TonglianIndependentRspEnum.TR_18.getCode());
        TONGLIAN_LOAN_UNSOLVED_STATUS.add(TonglianIndependentRspEnum.TR_19.getCode());
        TONGLIAN_LOAN_UNSOLVED_STATUS.add(TonglianIndependentRspEnum.TR_99.getCode());
        TONGLIAN_LOAN_UNSOLVED_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_3));

        BAOFOO_PAY_SUCCESS_STATUS.add(TonglianErrInfo.S.getCode());
        BAOFOO_PAY_SUCCESS_STATUS.add(TonglianErrInfo.E1.getCode());
        BAOFOO_PAY_SUCCESS_STATUS.add(TonglianErrInfo.E2.getCode());
        BAOFOO_PAY_SUCCESS_STATUS.add(TonglianErrInfo.E4000.getCode());
        BAOFOO_PAY_SUCCESS_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_4));
        BAOFOO_PAY_SUCCESS_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_2));
    }

    @Override
    public TaskResult processTlUnsolvedOrders() {
        Calendar calendar = Calendar.getInstance();
        TaskResult result = new TaskResult();
        List<PayLogVO> pwList = payLogService.findTonglianPayUnsolvedOrders(TONGLIAN_PAY_UNSOLVED_STATUS);
        log.debug("通联商户1-代付-检查付款结果：{}有【{}】条订单尚未处理完成", DateUtils.formatDateTime(calendar.getTime()), pwList.size());
        int succNum = 0;
        for (PayLogVO pw : pwList) {
            String lockKey = Global.PAYING_LOCK + pw.getId();
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            try {
                boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 5);
                if (!lock) {
                    continue;
                }
                if ("S".equals(pw.getStatus())){
                    continue;
                }
                // 通联商户1-查询代付结果
                PayLogVO payLogVO = queryLoanResultTL1(pw);
                if ("S".equals(payLogVO.getStatus())){
                    succNum++;
                }
            } finally {
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }
        result.setSuccNum(succNum);
        result.setFailNum(pwList.size() - succNum);
        return result;
    }

    public PayLogVO queryLoanResultTL1(PayLogVO vo){
        String orderNo = vo.getChlOrderNo();
        AipgRsp rspResult = null;
        try {
            Thread.sleep(500);
            rspResult = queryWithdrawResult(orderNo);
            // 通联返回结果非空
            if (rspResult != null && rspResult.getTrxData() != null && rspResult.getTrxData().size() > 0) {
                QTransRsp details = (QTransRsp) rspResult.trxObj();
                //details 通联响应报文中可能有多条响应记录,应该是有批量查询功能,这里是单条记录查询故只处理单条记录
                if (details != null && details.getDetails() != null && details.getDetails().size() == 1) {
                    QTDetail detail = (QTDetail) details.getDetails().get(0);
                    log.info("通联商户1-代付-检查付款结果：{}，{}元，{}，{}", vo.getToAccName(), detail.getAMOUNT(),
                            getOrderRemark(detail.getRET_CODE()), vo.getId());
                    // 状态为 0000或4000 批次已经处理完成
                    if (TonglianErrInfo.E0000.getCode().equalsIgnoreCase(detail.getRET_CODE()) || TonglianErrInfo.E4000.getCode().equalsIgnoreCase(detail.getRET_CODE())) {
                        vo.setStatus(TonglianErrInfo.S.getCode());
                        vo.setSuccTime(vo.getTxTime());
                        vo.setSuccAmt(vo.getTxAmt());
                        vo.setRemark(detail.getERR_MSG());
                        // 查询结果成功，更新订单状态，生成还款计划
                        try {
                            contractService.createRepayPlan(vo.getApplyId(), vo.getTxTime());
                        } catch (Exception e) {
                            log.error("放款成功，生成还款计划异常，请手动操作！applyId={}",vo.getApplyId(),e);
                        }
                        //succNum++;
                    } else {
                        if (middleStateMap.get(detail.getRET_CODE()) != null) {
                            vo.setStatus(TonglianErrInfo.I.getCode());
                            vo.setRemark(detail.getERR_MSG());
                        } else {
                            // 查询结果失败，更新代付日志
                            vo.setStatus(TonglianErrInfo.F.getCode());
                            vo.setRemark(detail.getERR_MSG());
                        }
                    }
                } else {
                    log.error("通联商户1-代付-检查付款结果,响应报文异常,无法解析...");
                }
            } else {
                vo.setStatus(TonglianErrInfo.F.getCode());
                vo.setRemark(rspResult.getINFO().getERR_MSG());
                log.error("通联商户1-代付-检查付款结果,无响应结果报文 chl_order_no:{}", orderNo);
            }
        } catch (Exception e) {
            log.error("通联商户1-代付-检查付款结果异常.", e);
        } finally {
            int updateRows = payLogService.updatePayResult(vo);
            log.info("通联商户1-代付-检查付款结果：{}更新本地订单状态", 1 == updateRows ? "已" : "未");
        }
        return vo;
    }

    @Override
    public TaskResult processTlLoanUnsolvedOrders() {
        Calendar calendar = Calendar.getInstance();
        TaskResult result = new TaskResult();
        List<PayLogVO> pwList = payLogService.findTonglianLoanPayUnsolvedOrders(TONGLIAN_LOAN_UNSOLVED_STATUS);
        log.debug("通联支付商户2独立-放款-检查放款结果：{}有【{}】条订单尚未处理完成", DateUtils.formatDateTime(calendar.getTime()), pwList.size());
        int succNum = 0;
        for (PayLogVO pw : pwList) {
            String lockKey = Global.PAYING_LOCK + pw.getId();
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            try {
                boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 5);
                if (!lock) {
                    continue;
                }
                if ("S".equals(pw.getStatus())){
                    continue;
                }
                // 通联商户2-查询代付结果
                PayLogVO payLogVO = queryLoanResultTL2(pw);
                if ("S".equals(payLogVO.getStatus())){
                    succNum++;
                }
            } finally {
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }
        result.setSuccNum(succNum);
        result.setFailNum(pwList.size() - succNum);
        return result;
    }

    @Override
    public PayLogVO queryLoanResultTL2(PayLogVO vo){
        String orderNo = vo.getChlOrderNo();
        TonglianQueryResultVo rspResult = null;
        try {
            Thread.sleep(500);
            rspResult = queryLoanResult(orderNo, DateUtils.formatDate(vo.getTxTime(),"yyyyMMddHHmmss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 通联返回结果非空
        if (rspResult != null) {
            log.info("通联支付商户2-放款-检查放款结果：{}，{}，{}", vo.getToAccName(), rspResult.getOrg_resp_code(), rspResult.getOrg_resp_msg());
            //表示成功
            if ("00".equals(rspResult.getOrg_resp_code())) {
                vo.setStatus(TonglianErrInfo.S.getCode());
                vo.setSuccTime(vo.getTxTime());
                vo.setSuccAmt(vo.getTxAmt());
                vo.setRemark("放款成功");
                // 查询结果成功，更新订单状态，生成还款计划
                try {
                    contractService.createRepayPlan(vo.getApplyId(), vo.getTxTime());
                } catch (Exception e) {
                    log.error("放款成功，生成还款计划异常，请手动操作！applyId={}",vo.getApplyId(),e);
                }
            } else {
                if ("18".equals(rspResult.getOrg_resp_code()) || "19".equals(rspResult.getOrg_resp_code()) || "99".equals(rspResult.getOrg_resp_code())) {
                    // 查询结果处理中，不做操作
                }else {
                    // 查询结果失败，更新代付日志
                    vo.setStatus(TonglianErrInfo.F.getCode());
                    vo.setRemark(rspResult.getOrg_resp_msg());
                }
            }
            int updateRows = payLogService.updatePayResult(vo);
            log.info("通联支付商户2-放款-检查放款结果响应报文：{}", rspResult.toString());
            log.info("通联支付商户2-放款-检查放款结果：{}更新本地订单状态", 1 == updateRows ? "已" : "未");
        }
        return vo;
    }


    /**
     * 重新付款
     *
     * @param pw
     */
    @Transactional
    public AdminWebResult reWithdraw(String payNo) {
        PayLogVO pw = payLogService.get(payNo);
        if (!TONGLIAN_PAY_UNSOLVED_STATUS.contains(pw.getStatus())
                && !TONGLIAN_LOAN_UNSOLVED_STATUS.contains(pw.getStatus())) {
            String userId = pw.getUserId();
            String applyId = pw.getApplyId();
            List<String> unsolvedAndSuccessStatus = new ArrayList<String>();
            unsolvedAndSuccessStatus.addAll(TONGLIAN_PAY_UNSOLVED_STATUS);
            unsolvedAndSuccessStatus.addAll(TONGLIAN_LOAN_UNSOLVED_STATUS);
            if (payLogService.countTonglianPayUnsolvedAndSuccess(applyId, unsolvedAndSuccessStatus) > 0) {
                return new AdminWebResult("99", "订单已放款成功或正在处理中");
            }
            CustUserVO custUserVO = userService.getCustUserById(userId);

            PayLogVO newPw = (PayLogVO) BeanMapper.map(pw, PayLogVO.class);
            newPw.setToAccName(custUserVO.getRealName());
            newPw.setToAccNo(custUserVO.getCardNo());
            newPw.setToIdno(custUserVO.getIdNo());
            newPw.setToMobile(StringUtils.isNotBlank(custUserVO.getBankMobile()) ? custUserVO.getBankMobile()
                    : custUserVO.getMobile());
            newPw.setToBankName(BankCodeEnum.getName(custUserVO.getBankCode()));
            newPw.setIsNewRecord(true);
            newPw.setTxType("withdraw");
            newPw.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
            newPw.setTxTime(new Date());
            /*newPw.setStatus(TonglianErrInfo.I.getCode());
            newPw.setRemark(TonglianErrInfo.I.getMsg());*/
            newPw.setId("TL" + DateUtils.getHHmmss() + (int) (new Random().nextInt(900) + 100));
            // 向通联发送代付指令
            tltAgreementPayService.tonghuaLoan(newPw);
            return new AdminWebResult("1", "重新放款成功");
        }
        return new AdminWebResult("99", "订单状态错误");
        // payLogService.delete(pw);
    }


    /**
     * 代付交易状态查证
     *
     * @param orderNo 代付请求流水号
     * @return
     */
    public AipgRsp queryWithdrawResult(String orderNo) {
        TlWithholdQueryOP queryOP = new TlWithholdQueryOP();
        queryOP.setReqSn(orderNo);
        return (AipgRsp) tltAgreementPayService.query(queryOP);
    }

    /**
     * 放款交易状态查证
     *
     * @param orderNo 代付请求流水号
     * @return
     */
    public TonglianQueryResultVo queryLoanResult(String orderNo, String txnDate) {
        TlWithholdQueryOP queryOP = new TlWithholdQueryOP();
        queryOP.setReqSn(orderNo);
        queryOP.setTxnDate(txnDate);
        return tltAgreementPayService.tonghuaLoanQuery(queryOP);
    }


    /**
     * 获取订单备注
     *
     * @param state
     * @return
     */
    public String getOrderRemark(String state) {
        for (TonglianErrInfo err : TonglianErrInfo.values()) {
            if (err.getCode().equals(state)) {
                return err.getMsg();
            }
        }
        return "未知";
    }
}
