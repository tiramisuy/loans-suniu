package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.op.TlWithholdQueryOP;
import com.rongdu.loans.pay.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import com.rongdu.loans.borrow.option.HelpApplyOP;
import com.rongdu.loans.borrow.service.HelpApplyService;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.enums.MsgEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.enums.RepayDeductionTypeEnum;
import com.rongdu.loans.loan.manager.GoodsOrderManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.AuthPayQueryOP;
import com.rongdu.loans.pay.op.WithholdQueryOP;
import com.rongdu.loans.pay.vo.AuthPayQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

@Slf4j
@Service("repayUnsolvedService")
public class RepayUnsolvedServiceImpl extends BaseService implements RepayUnsolvedService {
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private SettlementService settlementService;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private LoanRepayPlanManager loanRepayPlanManager;
    @Autowired
    private BaofooWithholdService baofooWithholdService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private BaofooAuthPayService baofooAuthPayService;
    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private ShopWithholdService shopWithholdService;
    @Autowired
    private XianFengAgreementPayService xianFengAgreementPayService;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private RongPointCutService rongPointCutService;
    @Autowired
    private GoodsOrderManager goodsOrderManager;
    @Autowired
    private ShopService shopService;
    @Autowired
    private HelpApplyService helpApplyService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private KjtpayService kjtpayService;
    @Autowired
    private CustCouponManager custCouponManager;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private TltAgreementPayService tltAgreementPayService;

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

    @Override
    public TaskResult processRepayUnsolvedOrders() {
        logger.info("定时查询支付交易处理中订单更新数据start");
        int success = 0;
        int fail = 0;
        List<RepayLogVO> repayLogList = repayLogService.findUnsolvedOrders();
        for (RepayLogVO repayLogVO : repayLogList) {
            String cacheKey = "REPAY_ING_LOCK_" + repayLogVO.getId();
            if (JedisUtils.get(cacheKey) != null) {
                continue;
            }
            JedisUtils.set(cacheKey, "1", 60 * 5);
            try {
                Thread.sleep(500);

                RepayLogVO tmp = repayLogService.get(repayLogVO.getId());
                if ("SUCCESS".equals(tmp.getStatus())) {
                    continue;
                }
                // 还款和延期属于贷后支付，需验证还款计划。
                // 加急,代扣购物金，旅游券为贷前支付，不需要验证还款计划
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())
                        || PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    RepayPlanItem repayPlanItem = repayPlanItemManager.get(repayLogVO.getRepayPlanItemId());
                    if (repayPlanItem == null) {
                        logger.error("repayPlanItem为空, itemId : {}", repayLogVO.getRepayPlanItemId());
                        throw new RuntimeException("repayPlanItem为空");
                    }
                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(repayPlanItem.getApplyId());
                    int currTerm = (repayPlanItem.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                    if (currTerm != repayPlanItem.getThisTerm()) {
                        logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItem.getId(),
                                currTerm, repayPlanItem.getThisTerm());
                        throw new RuntimeException("还款期数错误, itemId = " + repayPlanItem.getId() + " ,currTerm ="
                                + currTerm + " ,thisTerm =" + repayPlanItem.getThisTerm());
                    }
                    if (repayPlanItem.getStatus() != null && repayPlanItem.getStatus() == 1) {
                        logger.info("还款计划已结清,repayPlanItemId={}", repayLogVO.getRepayPlanItemId());
                        repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                        repayLogVO.setRemark("已还款,重复提交");
                        repayLogService.update(repayLogVO);
                        success++;
                        continue;
                    }
                }
                // 加急验证订单状态,防止后台操作员点扣除后放款，对已放款订单状态重置为510
                if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
                    LoanApply apply = loanApplyManager.getLoanApplyById(repayLogVO.getApplyId());
                    if (apply == null) {
                        logger.error("订单为空, applyId={}", repayLogVO.getApplyId());
                        throw new RuntimeException("订单为空");
                    }
                    if (apply.getStatus() != null && apply.getStatus().intValue() >= XjdLifeCycle.LC_LENDERS_0) {
                        logger.info("订单已购买加急券,applyId={},status={}", repayLogVO.getApplyId(), apply.getStatus());
                        repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                        repayLogVO.setRemark("已购买加急券,重复提交");
                        repayLogService.update(repayLogVO);
                        success++;
                        continue;
                    }
                }
                if (repayLogVO.getRemark().startsWith("预支付")) {
                    continue;
                }
                logger.info("支付交易处理中订单信息,userName={},mobile={},applyId={},itemId={},payType={},txType={}",
                        repayLogVO.getUserName(), repayLogVO.getMobile(), repayLogVO.getApplyId(),
                        repayLogVO.getRepayPlanItemId(), repayLogVO.getPayType(), repayLogVO.getTxType());
                if ("BAOFOO".equals(repayLogVO.getChlCode())) {
                    updateProcessOrderBaoFoo(repayLogVO);
                } else if ("XIANFENG".equals(repayLogVO.getChlCode())) {
                    updateProcessOrderXianFeng(repayLogVO);
                } else if ("KJTPAY".equals(repayLogVO.getChlCode())) {// 海尔支付
                    kjtpayService.updateProcessOrder(repayLogVO);
                }
                success++;
            } catch (Exception e) {
                fail++;
                logger.error("查询支付订单状态，更新订单失败： " + JsonMapper.getInstance().toJson(repayLogVO), e);
            }
        }
        logger.info("定时查询支付交易处理中订单更新数据end,success={},fail={}", success, fail);
        return new TaskResult(success, fail);
    }

    @Override
    public TaskResult processTLRepayUnsolvedOrders() {
        Calendar calendar = Calendar.getInstance();
        TaskResult result = new TaskResult();
        List<RepayLogVO> repayLogList = repayLogService.findUnsolvedOrders();
        logger.debug("通联商户1-代扣-检查付款结果：{}有【{}】条订单尚未处理完成", DateUtils.formatDateTime(calendar.getTime()), repayLogList.size());
        AipgRsp rspResult = null;
        String orderNo = null;
        String succCode = ErrInfo.SUCCESS.getCode();
        int succNum = 0;
        for (RepayLogVO pw : repayLogList) {
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
                // 还款和延期属于贷后支付，需验证还款计划。
                // 加急,代扣购物金，旅游券为贷前支付，不需要验证还款计划
                if (PayTypeEnum.SETTLEMENT.getId().equals(pw.getPayType())) {
                    RepayPlanItem repayPlanItem = repayPlanItemManager.get(pw.getRepayPlanItemId());
                    if (repayPlanItem.getStatus() != null && repayPlanItem.getStatus() == 1) {
                        logger.info("还款计划已结清,repayPlanItemId={}", pw.getRepayPlanItemId());
                        pw.setStatus(TonglianErrInfo.S.getCode());
                        pw.setRemark("已还款,重复提交");
                        repayLogService.update(pw);
                        succNum++;
                        continue;
                    }
                }
                // 通联商户1-查询代扣结果
                RepayLogVO repayLogVO = queryRepayResultTL(pw);
                if ("S".equals(repayLogVO.getStatus())){
                    succNum++;
                }
            } finally {
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }
        result.setSuccNum(succNum);
        result.setFailNum(repayLogList.size() - succNum);
        return result;
    }

    @Override
    public RepayLogVO queryRepayResultTL(RepayLogVO vo){
        String orderNo = vo.getChlOrderNo();
        AipgRsp rspResult = null;
        try {
            Thread.sleep(500);
            // 还款和延期属于贷后支付，需验证还款计划。
            // 加急,代扣购物金，旅游券为贷前支付，不需要验证还款计划
            if (PayTypeEnum.SETTLEMENT.getId().equals(vo.getPayType())
                    || PayTypeEnum.DELAY.getId().equals(vo.getPayType())) {
                RepayPlanItem repayPlanItem = repayPlanItemManager.get(vo.getRepayPlanItemId());
                if (repayPlanItem == null) {
                    logger.error("repayPlanItem为空, itemId : {}", vo.getRepayPlanItemId());
                    throw new RuntimeException("repayPlanItem为空");
                }
                LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(repayPlanItem.getApplyId());
                int currTerm = (repayPlanItem.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                if (currTerm != repayPlanItem.getThisTerm()) {
                    logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItem.getId(),
                            currTerm, repayPlanItem.getThisTerm());
                    throw new RuntimeException("还款期数错误, itemId = " + repayPlanItem.getId() + " ,currTerm ="
                            + currTerm + " ,thisTerm =" + repayPlanItem.getThisTerm());
                }
                /*if (repayPlanItem.getStatus() != null && repayPlanItem.getStatus() == 1) {
                    logger.info("还款计划已结清,repayPlanItemId={}", pw.getRepayPlanItemId());
                    pw.setStatus(ErrInfo.SUCCESS.getCode());
                    pw.setRemark("已还款,重复提交");
                    repayLogService.update(pw);
                    succNum++;
                    continue;
                }*/
            }


            rspResult = queryWithdrawResult(orderNo);

            // 通联返回结果非空
            if (rspResult != null && rspResult.getTrxData() != null && rspResult.getTrxData().size() > 0) {
                QTransRsp details = (QTransRsp) rspResult.trxObj();
                //details 通联响应报文中可能有多条响应记录,应该是有批量查询功能,这里是单条记录查询故只处理单条记录
                if (details != null && details.getDetails() != null && details.getDetails().size() == 1) {
                    QTDetail detail = (QTDetail) details.getDetails().get(0);
                    logger.info("通联商户1-代扣-检查付款结果：{}，{}元，{}，{}", vo.getUserName(), vo.getTxAmt(),
                            getOrderRemark(detail.getRET_CODE()), vo.getId());

                    // 状态为 0000或4000 批次已经处理完成
                    if (TonglianErrInfo.E0000.getCode().equalsIgnoreCase(detail.getRET_CODE()) || TonglianErrInfo.E4000.getCode().equalsIgnoreCase(detail.getRET_CODE())) {
                        vo.setStatus(TonglianErrInfo.S.getCode());
                        vo.setSuccTime(vo.getTxTime());
                        vo.setSuccAmt(vo.getTxAmt());
                        vo.setRemark(detail.getERR_MSG());

                        WithholdQueryResultVO retVo = new WithholdQueryResultVO();
                        retVo.setOrigTradeDate(DateUtils.formatDateTime(vo.getTxTime()));
                        retVo.setSuccAmt(MoneyUtils.yuan2fen(vo.getTxAmt().toString()));
                        // 还款
                        if (PayTypeEnum.SETTLEMENT.getId().equals(vo.getPayType())) {
                            if ("WITHHOLD".equals(vo.getTxType())){
                                retVo.setPayType(Global.REPAY_TYPE_AUTO);
                            }else if ("AUTH_PAY".equals(vo.getTxType())){
                                retVo.setPayType(Global.REPAY_TYPE_MANUAL);
                            }
                            withholdService.updateOrderInfo(vo.getRepayPlanItemId(), retVo);
                            rongPointCutService.settlementPoint(vo.getRepayPlanItemId(), true);// 用作Rong360
                            // 订单逾期，切面通知的切入点标记
                            //XianJinCardUtils.setJDQRepayPlanFeedbackToRedis(vo.getApplyId());
                        } else if (PayTypeEnum.PARTWITHHOLD.getId().equals(vo.getPayType())) {
                            // 当前实际已还
                            repayPlanItemService.updateForPartWithhold(vo.getRepayPlanItemId(), vo.getSuccAmt().toString(),
                                    retVo.getOrigTradeDate());
                        } else {
                            logger.error("查询支付订单状态，错误的支付类型： payType=" + vo.getPayType());
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
                    log.error("通联商户1-代扣-检查付款结果,响应报文异常,无法解析...");
                }
            } else {
                vo.setStatus(TonglianErrInfo.F.getCode());
                vo.setRemark(rspResult.getINFO().getERR_MSG());
                log.error("通联商户1-代扣-检查付款结果,无响应结果报文 chl_order_no:{}", orderNo);
            }
        } catch (Exception e) {
            logger.error("查询支付订单状态，更新订单失败： " + JsonMapper.getInstance().toJson(vo), e);
            e.printStackTrace();
        } finally {
            int updateRows = repayLogService.updateRepayResult(vo);
            logger.info("通联支付-代扣-检查扣款结果：{}更新本地订单状态", 1 == updateRows ? "已" : "未");
        }
        return vo;
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
    /****************************************************************************/
    /*************************** 宝付处理中订单处理 ***************************************/
    /****************************************************************************/
    private void updateProcessOrderBaoFoo(RepayLogVO repayLogVO) throws ParseException {
        // 认证支付-主动还款
        if ("AUTH_PAY".equals(repayLogVO.getTxType())) {
            // 主动还款查询
            AuthPayQueryOP op = new AuthPayQueryOP();
            op.setOrigTransId(repayLogVO.getId());
            op.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyyMMddHHmmss"));
            AuthPayQueryResultVO queryResult = baofooAuthPayService.queryAuthPayResult(op);
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(queryResult));
            if (queryResult == null)
                throw new RuntimeException("主动还款查询结果为空");
            if (isBaofooRespSuccess(queryResult.getCode())) {
                if (StringUtils.isBlank(queryResult.getSuccAmt()))
                    throw new RuntimeException("主动还款查询成功金额为空");

                String succAmtYuan = MoneyUtils.fen2yuan(queryResult.getSuccAmt());

                RePayOP rePayOP = new RePayOP();
                rePayOP.setPayStatus(queryResult.getCode());
                rePayOP.setOrderInfo(queryResult.getMsg());
                rePayOP.setPaySuccAmt(succAmtYuan);
                rePayOP.setPaySuccTime(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyy-MM-dd HH:mm:ss"));
                rePayOP.setUserId(repayLogVO.getUserId());
                rePayOP.setRepayPlanItemId(repayLogVO.getRepayPlanItemId());
                rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
                rePayOP.setIp(repayLogVO.getIp());
                rePayOP.setSource(repayLogVO.getTerminal());
                rePayOP.setApplyId(repayLogVO.getApplyId());

                ConfirmPayResultVO rz = null;
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.settlement(rePayOP);
                    XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_SUCCESS);
                }
                // 延期
                else if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.delay(rePayOP);
                }
                // 购买加急券
                else if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					/*if ("2".equals(rePayOP.getSource()) || "3".equals(rePayOP.getSource())){
						// Android用户
						rz = settlementService.urgentPushingLoan(rePayOP);
					}else {*/
                    rz = settlementService.urgent(rePayOP);
                    //}
                }
                // 购买旅游券
                else if (PayTypeEnum.TRIP.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.trip(rePayOP);
                } else {
                    logger.error("查询支付订单状态，错误的支付类型： payType=" + repayLogVO.getPayType());
                }
                if (rz != null) {
                    repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                    repayLogVO.setRemark(queryResult.getMsg());
                    repayLogVO.setSuccTime(repayLogVO.getTxTime());
                    repayLogVO.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
                    int updateRows = repayLogService.updateRepayResult(repayLogVO);
                }
            } else if (!isBaofooRespUnsolved(queryResult.getCode())) {
                repayLogVO.setStatus(queryResult.getCode());
                repayLogVO.setRemark(queryResult.getMsg());
                repayLogService.update(repayLogVO);

                // 购买加急券
				/*if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					loanApplyService.updateUrgentPayed(repayLogVO.getApplyId(),"F");
				}*/
                // 还款失败
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    // 用作Rong360订单代扣失败，切面通知的切入点标记
                    rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), false);
                }
                // 延期失败
                if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    rongPointCutService.repayProcessFailPoint(repayLogVO.getRepayPlanItemId());
                }
            }
        }
        // 系统代扣
        else if ("WITHHOLD".equals(repayLogVO.getTxType())) {
            /***** BAOFOO *****/
            WithholdQueryOP op = new WithholdQueryOP();
            op.setOrigTransId(repayLogVO.getId());
            op.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyyMMddHHmmss"));
            WithholdQueryResultVO retVo = baofooWithholdService.queryWithholdResult(op);
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("系统代扣查询结果为空");
            if ("SYS_ERR".equals(retVo.getCode()))
                throw new RuntimeException("系统代扣查询异常，结果未知");
            // 查询结果为交易成功，更新还款记录
            if (isBaofooRespSuccess(retVo.getCode())) {
                String succAmtYuan = MoneyUtils.fen2yuan(retVo.getSuccAmt());
                if (StringUtils.isBlank(retVo.getSuccAmt()))
                    throw new RuntimeException("系统代扣查询成功金额为空");
                if (StringUtils.isBlank(retVo.getOrigTradeDate()))
                    throw new RuntimeException("系统代扣查询原订单时间为空");
                if (StringUtils.isNotBlank(repayLogVO.getCouponId())) {
                    retVo.setCouponId(repayLogVO.getCouponId());
                }
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    withholdService.updateOrderInfo(repayLogVO.getRepayPlanItemId(), retVo);
                    rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), true);// 用作Rong360订单逾期，切面通知的切入点标记
                    XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_SUCCESS);
                }
                // 延期费
                else if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    contractService.delayDeal(repayLogVO.getRepayPlanItemId(), 2,
                            DateUtils.formatDateTime(retVo.getOrigTradeDate()), null, null);
                }
                // 购物金
                else if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
                    shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getMsg(), 0);
                }
                // 部分代扣
                else if (PayTypeEnum.PARTWITHHOLD.getId().equals(repayLogVO.getPayType())) {
                    repayPlanItemService.updateForPartWithhold(repayLogVO.getRepayPlanItemId(), succAmtYuan,
                            retVo.getOrigTradeDate());
                } else {
                    logger.error("查询支付订单状态，错误的支付类型： payType=" + repayLogVO.getPayType());
                }

                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getMsg());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
                int updateRows = repayLogService.updateRepayResult(repayLogVO);
            } else if (!isBaofooRespUnsolved(retVo.getCode())) {
                if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
                    shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getMsg(), 1);
                }
                repayLogVO.setStatus(retVo.getCode());
                repayLogVO.setRemark(retVo.getMsg());
                repayLogService.update(repayLogVO);

                // 购买加急券
				/*if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					loanApplyService.updateUrgentPayed(repayLogVO.getApplyId(),"F");
				}*/
                // 还款失败
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    // 用作Rong360订单代扣失败，切面通知的切入点标记
                    rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), false);
                }
                // 延期失败
                if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    rongPointCutService.repayProcessFailPoint(repayLogVO.getRepayPlanItemId());
                }
                XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_FAIL);
            }
        }
        // 协议支付-直接还款
        else if ("AM_PAY".equals(repayLogVO.getTxType())) {
            AuthPayQueryOP op = new AuthPayQueryOP();
            op.setOrigTransId(repayLogVO.getId());
            op.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyy-MM-dd HH:mm:ss"));
            AuthPayQueryResultVO queryResult = baofooAgreementPayService.queryAgreementPayResult(op);
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(queryResult));
            if (queryResult == null)
                throw new RuntimeException("协议支付主动还款查询结果为空");
            if (isBaofooRespSuccess(queryResult.getCode())) {
                if (StringUtils.isBlank(queryResult.getSuccAmt()))
                    throw new RuntimeException("协议支付主动还款查询成功金额为空");

                String succAmtYuan = MoneyUtils.fen2yuan(queryResult.getSuccAmt());

                RePayOP rePayOP = new RePayOP();
                rePayOP.setPayStatus(queryResult.getCode());
                rePayOP.setOrderInfo(queryResult.getMsg());
                rePayOP.setPaySuccAmt(succAmtYuan);
                rePayOP.setPaySuccTime(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyy-MM-dd HH:mm:ss"));
                rePayOP.setUserId(repayLogVO.getUserId());
                rePayOP.setRepayPlanItemId(repayLogVO.getRepayPlanItemId());
                rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
                rePayOP.setIp(repayLogVO.getIp());
                rePayOP.setSource(repayLogVO.getTerminal());
                rePayOP.setApplyId(repayLogVO.getApplyId());

                ConfirmPayResultVO rz = null;
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.settlement(rePayOP);
                }
                // 延期
                else if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.delay(rePayOP);
                }
                // 购买加急券
                else if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					/*if ("2".equals(rePayOP.getSource()) || "3".equals(rePayOP.getSource())){
						// Android用户
						rz = settlementService.urgentPushingLoan(rePayOP);
					}else {*/
                    rz = settlementService.urgent(rePayOP);
                    //}
                }
                // 购买旅游券
                else if (PayTypeEnum.TRIP.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.trip(rePayOP);
                } else {
                    logger.error("查询支付订单状态，错误的支付类型： payType=" + repayLogVO.getPayType());
                }
                if (rz != null) {
                    repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                    repayLogVO.setRemark(queryResult.getMsg());
                    repayLogVO.setSuccTime(repayLogVO.getTxTime());
                    repayLogVO.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
                    int updateRows = repayLogService.updateRepayResult(repayLogVO);
                }
            } else if (!isBaofooRespUnsolved(queryResult.getCode())) {
                repayLogVO.setStatus(queryResult.getCode());
                repayLogVO.setRemark(queryResult.getMsg());
                repayLogService.update(repayLogVO);

                // 购买加急券
				/*if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					loanApplyService.updateUrgentPayed(repayLogVO.getApplyId(),"F");
				}*/
                // 还款失败
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    // 用作Rong360订单代扣失败，切面通知的切入点标记
                    rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), false);
                }
                // 延期失败
                if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    rongPointCutService.repayProcessFailPoint(repayLogVO.getRepayPlanItemId());
                }
            }
        }
        // 后台手动代扣
        else if ("WH_ADMIN".equals(repayLogVO.getTxType())) {
            WithholdQueryOP op = new WithholdQueryOP();
            op.setOrigTransId(repayLogVO.getId());
            op.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyyMMddHHmmss"));
            WithholdQueryResultVO retVo = baofooWithholdService.queryWithholdResult(op);
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("系统代扣查询结果为空");
            if ("SYS_ERR".equals(retVo.getCode()))
                throw new RuntimeException("系统代扣查询异常，结果未知");
            // 查询结果为交易成功，更新还款记录
            if (isBaofooRespSuccess(retVo.getCode())) {
                String succAmtYuan = MoneyUtils.fen2yuan(retVo.getSuccAmt());
                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getMsg());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
                int updateRows = repayLogService.updateRepayResult(repayLogVO);
            } else if (!isBaofooRespUnsolved(retVo.getCode())) {
                repayLogVO.setStatus(retVo.getCode());
                repayLogVO.setRemark(retVo.getMsg());
                repayLogService.update(repayLogVO);
            }
        }
    }

    public static boolean isBaofooRespSuccess(String status) {
        return StringUtils.isNotBlank(status) && (status.equals("0000") || status.equals("BF00114"));
    }

    public static boolean isBaofooRespUnsolved(String status) {
        if (StringUtils.isBlank(status)) {
            return false;
        }
        String[] statusArr = {"BF00100", "BF00112", "BF00113", "BF00115", "BF00144", "BF00202"};
        for (String str : statusArr) {
            if (status.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /****************************************************************************/
    /*************************** 先锋查询中订单处理 ***************************************/
    /**
     * @throws ParseException
     **************************************************************************/
    public void updateProcessOrderXianFeng(RepayLogVO repayLogVO) throws ParseException {
        // 协议支付
        if ("AUTH_PAY".equals(repayLogVO.getTxType())) {
            XfAgreementPayResultVO retVo = xianFengAgreementPayService.agreementPayQuery(repayLogVO.getId());
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("查询结果为空");
            // 查询结果为交易成功，更新还款记录
            if (retVo.isSuccess()) {
                RePayOP rePayOP = new RePayOP();
                rePayOP.setPayStatus(retVo.getResCode());
                rePayOP.setOrderInfo(retVo.getResMessage());
                rePayOP.setPaySuccAmt(repayLogVO.getTxAmt().toString());
                rePayOP.setPaySuccTime(retVo.getTradeTime());
                rePayOP.setUserId(repayLogVO.getUserId());
                rePayOP.setApplyId(repayLogVO.getApplyId());
                rePayOP.setRepayPlanItemId(repayLogVO.getRepayPlanItemId());
                rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
                rePayOP.setIp(repayLogVO.getIp());
                rePayOP.setSource(repayLogVO.getTerminal());
                rePayOP.setChlCode(Global.XIANFENG_CHANNEL_CODE);
                if (StringUtils.isNotBlank(repayLogVO.getCouponId())) {
                    rePayOP.setCouponId(repayLogVO.getCouponId());// 优惠券ID
                    rePayOP.setIsDeduction(RepayDeductionTypeEnum.YES.getValue());
                    // 设置减免金额
                    String[] couponIds = repayLogVO.getCouponId().split(",");
                    String deductionAmt = custCouponManager.sumCouponAmtByIds(Arrays.asList(couponIds));
                    rePayOP.setDeductionAmt(deductionAmt);
                }

                ConfirmPayResultVO rz = null;
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    rz = settlementService.settlement(rePayOP);
                    XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_SUCCESS);
                    XianJinCardUtils.setRepayPlanFeedbackToRedis(repayLogVO.getApplyId());
                }
                // 延期
                else if (PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    JedisUtils.set(Global.DELAY_SUCCESS_FLAG_PREFIX + repayLogVO.getUserId(), "1",
                            Global.ONE_DAY_CACHESECONDS);
                    rz = settlementService.delay(rePayOP);
                }
                // 购买加急券
                else if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
                    // ytodo 0303 加急券
					/*if ("2".equals(rePayOP.getSource()) || "3".equals(rePayOP.getSource())){
						// Android用户
						rz = settlementService.urgentPushingLoan(rePayOP);
					}else {*/
                    rz = settlementService.urgent(rePayOP);
                    //}
                }
                // 购物金
                else if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
                    shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getResMessage(), 0);
                }
                // 购买旅游券
                else if (PayTypeEnum.TRIP.getId().equals(repayLogVO.getPayType())) {
                    rePayOP.setTripProductId(repayLogVO.getGoodsId());
                    rz = settlementService.trip(rePayOP);
                }
                // 商城购物
                else if (PayTypeEnum.SHOPPINGMALL.getId().equals(repayLogVO.getPayType())) {
                    shopService.paySuccess(repayLogVO);

                } else {
                    logger.error("查询支付订单状态，错误的支付类型： payType=" + repayLogVO.getPayType());
                }

                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(repayLogVO.getTxAmt());
                repayLogService.updateRepayResult(repayLogVO);
            } else if (!"I".equals(retVo.getStatus())) {
                // if
                // (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType()))
                // {
                // // 先锋代扣失败后，接着宝付代扣
                // int withhodResult =
                // shopWithholdService.baofooWithholdShopping(repayLogVO.getApplyId());
                // if (withhodResult == 0) {
                // shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(),
                // "交易成功", 0);
                // } else if (withhodResult == 1) {
                // shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(),
                // retVo.getResMessage(), 1);
                // }
                // }
                // 购买加急券
				/*if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())) {
					loanApplyService.updateUrgentPayed(repayLogVO.getApplyId(),"F");
				}*/
                if (PayTypeEnum.SHOPPING.getId().equals(repayLogVO.getPayType())) {
                    shopWithholdService.updateShopWithhold(repayLogVO.getApplyId(), retVo.getResMessage(), 1);
                }
                if (PayTypeEnum.SHOPPINGMALL.getId().equals(repayLogVO.getPayType())) {
                    Criteria criteria = new Criteria();
                    criteria.and(Criterion.eq("id", repayLogVO.getApplyId()));
                    GoodsOrder goodsOrder = new GoodsOrder();
                    goodsOrder.setStatus("0");
                    goodsOrder.setPayTime(new Date());
                    int updateByCriteriaSelective = goodsOrderManager.updateByCriteriaSelective(goodsOrder, criteria);
                }

                // 支付失败发站内信
                if (isXfFail(retVo.getResCode())) {
                    String title = "支付失败";
                    String content = String.format("支付金额:%s元,失败原因:%s", repayLogVO.getTxAmt(), retVo.getResMessage());
                    sendMessage(repayLogVO.getUserId(), title, content);
                }

                repayLogVO.setStatus("F");
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogService.update(repayLogVO);
            }
            // 2分钟后解除app前端锁定
            if (retVo.isSuccess() || !"I".equals(retVo.getStatus())) {
                String lockId = null;
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())
                        || PayTypeEnum.DELAY.getId().equals(repayLogVO.getPayType())) {
                    lockId = repayLogVO.getRepayPlanItemId();
                } else if (PayTypeEnum.URGENT.getId().equals(repayLogVO.getPayType())
                        || PayTypeEnum.TRIP.getId().equals(repayLogVO.getPayType())) {
                    lockId = repayLogVO.getApplyId();
                }
                if (lockId != null) {
                    String userAgreementPayLockCacheKey = "user_agreementPay_lock_" + lockId;
                    JedisUtils.set(userAgreementPayLockCacheKey, "locked", 120);
                }
            }
        }
        // 后台手动代扣
        else if ("WH_ADMIN".equals(repayLogVO.getTxType())) {
            XfAgreementPayResultVO retVo = xianFengAgreementPayService.agreementPayQuery(repayLogVO.getId());
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("查询结果为空");
            if (retVo.isSuccess()) {
                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(repayLogVO.getTxAmt());
                repayLogService.update(repayLogVO);
            } else if (!"I".equals(retVo.getStatus())) {
                repayLogVO.setStatus("F");
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogService.update(repayLogVO);
            }
        }
        // 主动支付
        else if ("AUTO_PAY".equals(repayLogVO.getTxType())) {
            XfAgreementPayResultVO retVo = xianFengAgreementPayService.agreementPayQuery(repayLogVO.getId());
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("查询结果为空");
            if (retVo.isSuccess()) {
                if (PayTypeEnum.BORROWHELP.getId().equals(repayLogVO.getPayType())) {
                    HelpApplyOP helpApplyOP = new HelpApplyOP();
                    helpApplyOP.setId(repayLogVO.getApplyId());
                    helpApplyOP.setRetCode(retVo.getResCode());
                    helpApplyOP.setRetMsg(retVo.getResMessage());
                    helpApplyOP.setMobile(repayLogVO.getMobile());
                    helpApplyOP.setStatus(1);
                    if (!StringUtils.isBlank(retVo.getTradeTime())) {
                        helpApplyOP.setPayTime(DateUtils.parse(DateUtils.formatDateTime(retVo.getTradeTime())));
                        helpApplyOP.setPayDate(Integer.valueOf(retVo.getTradeTime().substring(0, 8)));
                    }
                    helpApplyService.borrowHelpPaid(helpApplyOP);
                }
                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(repayLogVO.getTxAmt());
                repayLogService.update(repayLogVO);
            } else if (!"I".equals(retVo.getStatus())) {
                if (PayTypeEnum.BORROWHELP.getId().equals(repayLogVO.getPayType())) {
                    HelpApplyOP helpApplyOP = new HelpApplyOP();
                    helpApplyOP.setId(repayLogVO.getApplyId());
                    helpApplyOP.setRetCode(retVo.getResCode());
                    helpApplyOP.setRetMsg(retVo.getResMessage());
                    helpApplyOP.setMobile(repayLogVO.getMobile());
                    helpApplyOP.setStatus(0);
                    if (!StringUtils.isBlank(retVo.getTradeTime())) {
                        helpApplyOP.setPayTime(DateUtils.parse(DateUtils.formatDateTime(retVo.getTradeTime())));
                        helpApplyOP.setPayDate(Integer.valueOf(retVo.getTradeTime().substring(0, 8)));
                    }
                    helpApplyService.borrowHelpPaid(helpApplyOP);
                }
                repayLogVO.setStatus("F");
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogService.update(repayLogVO);
            }
        }
        // 系统代扣
        else if ("WITHHOLD".equals(repayLogVO.getTxType())) {
            XfAgreementPayResultVO retVo = xianFengAgreementPayService.agreementPayQuery(repayLogVO.getId());
            logger.info("支付交易处理中订单查询结果={}", JsonMapper.toJsonString(retVo));
            if (retVo == null)
                throw new RuntimeException("查询结果为空");
            if (retVo.isSuccess()) {
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(repayLogVO.getPayType())) {
                    WithholdQueryResultVO withholdResultVO = new WithholdQueryResultVO();
                    withholdResultVO.setSuccAmt(MoneyUtils.yuan2fen(repayLogVO.getTxAmt().toString()));
                    withholdResultVO.setOrigTradeDate(DateUtils.formatDate(repayLogVO.getTxTime(), "yyyyMMddHHmmss"));

                    withholdService.updateOrderInfo(repayLogVO.getRepayPlanItemId(), withholdResultVO);
                    rongPointCutService.settlementPoint(repayLogVO.getRepayPlanItemId(), true);// 用作Rong360订单逾期，切面通知的切入点标记
                    XianJinCardUtils.setRepayStatusFeedbackToRedis(repayLogVO.getApplyId(), Global.XJBK_SUCCESS);
                }
                repayLogVO.setStatus(ErrInfo.SUCCESS.getCode());
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogVO.setSuccTime(repayLogVO.getTxTime());
                repayLogVO.setSuccAmt(repayLogVO.getTxAmt());
                repayLogService.update(repayLogVO);
            } else if (!"I".equals(retVo.getStatus())) {
                repayLogVO.setStatus("F");
                repayLogVO.setRemark(retVo.getResMessage());
                repayLogService.update(repayLogVO);
            }
        }
    }

    private boolean isXfFail(String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        // 10010=余额不足
        // 10025=金额超限
        // 10049=交易失败，详情请咨询发卡行
        // 10024=姓名、身份证、银行卡或手机号信息不一致
        String[] codeArr = {"10010", "10025", "10049", "10024"};
        for (String c : codeArr) {
            if (code.trim().equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 发送站内信通知
     *
     * @param userId
     * @param paySuccAmt
     * @return
     */
    private int sendMessage(String userId, String title, String content) {
        // 初始化内部信参数对象
        Message message = new Message();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);
        message.setType(Global.CUST_MESSAGE_TYPE_SYS);
        message.setNotifyTime(new Date());
        message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
        message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
        message.setStatus(MsgEnum.SEND_SUCCSS.getValue());
        message.setDel(0);
        message.preInsert();
        return messageManager.insert(message);
    }
}
