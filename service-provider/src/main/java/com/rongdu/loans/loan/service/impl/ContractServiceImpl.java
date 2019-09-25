package com.rongdu.loans.loan.service.impl;

import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.manager.AppBankLimitManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.pay.exception.OrderProcessingException;
import com.rongdu.loans.pay.op.XfWithdrawOP;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.service.TRBaofooWithdrawService;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.service.XianFengWithdrawService;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;
import com.rongdu.loans.pay.vo.XfWithdrawResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.*;

/**
 * 借款合同-业务逻辑实现类
 *
 * @author likang
 * @version 2017-07-11
 */
@Service("contractService")
public class ContractServiceImpl extends BaseService implements ContractService {

    // 拼接最近三天放款成功记录的缓存key
    public static final String MAKE_LOAN_RECORDS_KEY = "make_loan_records";

    // 拼接最近三天放款成功记录的缓存key
    public static final String MAKE_LOAN_RECORDS_STR_KEY = "make_loan_records_str";

    // 最近三天放款成功记录缓存时间（2小时）
    private static final int CACHESECONDS = 2 * 60 * 60;

    @Autowired
    private ContractManager contractManager;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private LoanRepayPlanManager loanRepayPlanManager;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private BorrowInfoManager borrowInfoManager;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private CustUserManager custUserManager;
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private AppBankLimitManager appBankLimitManager;
    @Autowired
    private OverdueManager overdueManager;
    @Autowired
    private BaofooWithdrawService baofooWithdrawService;
    @Autowired
    private TltAgreementPayService tltAgreementPayService;
    @Autowired
    private TRBaofooWithdrawService trBaofooWithdrawService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private RepayLogService repayLogService;

    @Autowired
    private ShopWithholdService shopWithholdService;
    @Autowired
    private XianFengWithdrawService xianFengWithdrawService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private BorrowInfoService borrowInfoService;
    @Autowired
    private RongPointCutService rongPointCutService;
    @Autowired
    private ShopWithholdManager shopWithholdManager;
    @Autowired
    private CustCouponManager custCouponManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<MakeLoanRecordVO> getRecentThreeDaysRecords() {
        // 优先从缓存获取
        List<MakeLoanRecordVO> rzList = (List<MakeLoanRecordVO>) JedisUtils.getObjectList(MAKE_LOAN_RECORDS_KEY);
        if (null == rzList || rzList.size() == 0) {
            // 构造返回对象
            rzList = new ArrayList<MakeLoanRecordVO>();
            // 从持久层查询最近三天申请放款成功的记录
            List<Contract> recordList = contractManager.getRecentThreeDaysRecords();
            MakeLoanRecordVO makeLoanRecordVO = null;
            if (null != recordList && recordList.size() > 0) {
                int size = recordList.size();
                for (int i = 0; i < size; i++) {

                    Contract temp = recordList.get(i);
                    if (null != temp && null != temp.getMobile() && null != temp.getPrincipal()) {
                        makeLoanRecordVO = new MakeLoanRecordVO();
                        String mobile = temp.getMobile();
                        makeLoanRecordVO.setIdNoLastFour(mobile.substring((mobile.length() - 4), mobile.length()));
                        makeLoanRecordVO.setLoanAmt(temp.getPrincipal());
                        rzList.add(makeLoanRecordVO);
                    }
                }
            }
            // 新获取结果存入缓存
            if (null != rzList && rzList.size() > 0) {
                JedisUtils.setObjectList(MAKE_LOAN_RECORDS_KEY, rzList, CACHESECONDS);
            }
        }
        return rzList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getRecentThreeDaysRecordsStr() {
        // 优先从缓存获取
        List<String> rzList = (List<String>) JedisUtils.getObjectList(MAKE_LOAN_RECORDS_STR_KEY);
        if (null == rzList || rzList.size() == 0) {
            // 构造返回对象
            rzList = new ArrayList<String>();
            // 从持久层查询最近三天申请放款成功的记录
            List<Contract> recordList = contractManager.getRecentThreeDaysRecords();
            StringBuilder sb = null;
            if (null != recordList && recordList.size() > 0) {
                int size = recordList.size();
                for (int i = 0; i < size; i++) {
                    Contract temp = recordList.get(i);
                    if (null != temp && null != temp.getIdNo() && null != temp.getPrincipal()) {
                        sb = new StringBuilder();
                        String mobile = temp.getMobile();
                        sb.append(Global.MAKE_LOAN_TIPS_1)
                                .append(mobile.substring((mobile.length() - 4), mobile.length()))
                                .append(Global.MAKE_LOAN_TIPS_2).append(temp.getPrincipal())
                                .append(Global.MAKE_LOAN_TIPS_3);
                        rzList.add(sb.toString());
                    }
                }
            }
            // 新获取结果存入缓存
            if (null != rzList && rzList.size() > 0) {
                JedisUtils.setObjectList(MAKE_LOAN_RECORDS_STR_KEY, rzList, CACHESECONDS);
            }
        }
        return rzList;
    }

    /**
     * 推标回调
     */
    @Override
    @Transactional
    public boolean process(String outsideSerialNo, Date payTime, String notifyType) throws RuntimeException {
        if (StringUtils.isBlank(outsideSerialNo) || payTime == null) {
            logger.error("参数异常, outsideSerialNo = {} , payTime = {}", outsideSerialNo, payTime);
            throw new RuntimeException("参数校验不通过");
        }
        if (StringUtils.isBlank(notifyType)) {
            logger.error("通知类型为空，推送到门店系统，outsideSerialNo={}", outsideSerialNo);
            return false;
        }
        BorrowInfo borrowInfo = borrowInfoManager.getByOutsideSerialNo(outsideSerialNo);
        if (borrowInfo == null) {
            String date = DateUtils.formatDateTime(payTime);
            logger.error("标的不存在，outsideSerialNo = {}, payTime = {}", outsideSerialNo, date);
            return false;
        }
        LoanApply loanApply = loanApplyManager.getLoanApplyById(borrowInfo.getApplyId());
        if (loanApply == null) {
            logger.error("贷款申请单不存在，outsideSerialNo = {}，applyId = {}", outsideSerialNo, borrowInfo.getApplyId());
            return false;
        }
        if (!LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
                && !LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("推送到门店系统，outsideSerialNo = {}，applyId = {}", outsideSerialNo, borrowInfo.getApplyId());
            return false;
        }
        if (!PUSH_SUCCESS.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + borrowInfo.getApplyId() + ", status = "
                    + getDesc(loanApply.getStatus()));
            throw new RuntimeException("贷款申请单的状态不正确");
        }
        logger.info("p2p平台满标提现回调,notifyType={},applyId={}", notifyType, loanApply.getId());
        // 满标回调
        if ("1".equals(notifyType)) {
            return true;
        }
        // 旅游券598回调
        else if ("2".equals(notifyType)) {
            /** 现金贷分期,推标回调代扣购物金 ,代扣异常情况不影响流程 */
            boolean isShopWithhold = false;
            if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
                isShopWithhold = true;
            } else if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
                    && StringUtils.isNotBlank(borrowInfo.getRemark())
                    && LoanApplySimpleVO.APPLY_PAY_TYPE_1.intValue() == Integer.parseInt(borrowInfo.getRemark())) {
                isShopWithhold = true;
            }
            if (isShopWithhold) {
                // 防止598重复扣款
                ShopWithhold withHold = shopWithholdManager.findByApplyId(loanApply.getId());
                if (withHold != null && withHold.getWithholdStatus().intValue() == 0) {
                    logger.warn("598已扣款成功,请勿重复通知:{},{}", outsideSerialNo, loanApply.getId());
                    // 已代扣成功，补发通知到p2p平台
                    borrowInfoService.whithholdServFeeSuccessNotify(loanApply.getId());
                    return true;
                }
                try {
                    XfAgreementPayResultVO vo = shopWithholdService.doShopWithhold(loanApply.getId(),
                            loanApply.getServFee().toString());
                    if (vo.isSuccess()) {
                        // 代扣服务费成功通知到p2p平台
                        borrowInfoService.whithholdServFeeSuccessNotify(loanApply.getId());
                    }
                    if (!vo.getSuccess()) {
                        logger.error("放款回调，代扣购物金失败，applyId = {}，withHoldAmt = {},custUserId = {} ", loanApply.getId(),
                                loanApply.getServFee().toString(), loanApply.getUserId());
                    }
                } catch (Exception e) {
                    logger.error("放款代扣异常");
                }
            } else {
                // 先支付则直接通知，无需代扣
                // 代扣服务费成功通知到p2p平台
                borrowInfoService.whithholdServFeeSuccessNotify(loanApply.getId());
            }
            return true;
        }
        // 1402回调
        else if ("3".equals(notifyType)) {
            /** 更新贷款申请单状态 */
            updateLoanApplyInfo(loanApply);
            /** 签订合同 */
            Contract contract = contractManager.insert(loanApply, payTime);
            /** 保存签订日志 */
            saveContraclog(loanApply);
            /** 生成还款计划 */
            LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                    WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getValue());
            /** 生成还款计划明细 */
            List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
            /** 汇总还款计划明细，更新还款计划 */
            loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
            loanRepayPlanManager.update(loanRepayPlan);
            /** 免密提现，修改贷款单状态，增加提现操作日志，保存paylog流水 */
            agreeWithdraw(loanApply, true);

            /** 移动端通知 */
            saveMessage2(loanApply, payTime);

            // 发放旅游券
            try {
                // 融360不放券
                boolean isGenerateCustCoupon = true;
                if (loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15 && "4".equals(loanApply.getSource())
                        && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                        && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                    isGenerateCustCoupon = false;
                }
                // 奇虎360不放券
                if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                        || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                        && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                    isGenerateCustCoupon = false;
                }
                if (isGenerateCustCoupon) {
                    custCouponManager.generateCustCoupon(loanApply);
                }
            } catch (Exception e) {
                logger.error("发券失败，applyId = {}", loanApply.getId());
            }
            return true;
        } else {
            logger.error("未知通知类型，applyId = {}", loanApply.getId());
            return false;
        }
    }

    /**
     * 后台手动放款
     *
     * @return
     */
    @Transactional
    public void processAdminLendPay(String applyId, Date payTime, boolean isPayment) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())
                && !WAITING_LENDING.getValue().equals(loanApply.getStatus())
                && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        if (StringUtils.isBlank(custUser.getCardNo())) {
            logger.error("未绑卡不能放款, applyId = " + applyId);
            throw new RuntimeException("未绑卡不能放款, applyId = " + applyId);
        }
        if (loanApplyService.isBuyShoppingGold(applyId)) {
            logger.error("已推标，无法后台放款, applyId = " + applyId);
            throw new RuntimeException("已推标，无法后台放款, applyId = " + applyId);
        }
        Integer sourceStatus = loanApply.getStatus();
        /** 更新贷款申请单状态 */
        // y:后台手动/app放款
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_OFFLINE.getValue());
        if (WAITING_PUSH.getValue().equals(sourceStatus)
                && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            Map<String, Object> extInfo = new HashMap<String, Object>();
            extInfo.put("lendType", 1);// 放款方式 1=扣除后放款
            loanRepayPlan.setRemark(JsonMapper.getInstance().toJson(extInfo));
            loanRepayPlanManager.update(loanRepayPlan);
        }
        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);
        /** 免密提现，修改贷款单状态，增加提现操作日志，保存paylog流水 */
        agreeWithdraw(loanApply, false);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);

        /** 聚宝贷自有资金放款宝付代付 */
        if (isPayment && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
                && loanApply.getApproveAmt().compareTo(new BigDecimal(4000)) < 0) {
            try {
                String payChannelCode = configService.getValue("pay_channel_code");
                if (Global.BAOFOO_CHANNEL_CODE.equals(payChannelCode)) {
                    // 宝付代付
                    payment(loanApply, sourceStatus);
                } else if (Global.XIANFENG_CHANNEL_CODE.equals(payChannelCode)) {
                    // 先锋代付 code y0621
                    xfWithdraw(loanApply, sourceStatus);
                }
            } catch (Exception e) {
                logger.error("放款失败，applyId={}，error={}", applyId, e.getMessage());
            }
        }
    }

    /**
     * 口袋放款
     *
     * @return
     */
    @Transactional
    public void processKoudaiLendPay(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }

        if (!PUSH_SUCCESS.getValue().equals(loanApply.getStatus())
                && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }

        /** 更新贷款申请单状态 */
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_KOUDAI.getValue());
        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);

        /** 修改申请单状态为待还款514 */
        loanApply.setStage(WITHDRAWAL_SUCCESS.getStage());
        loanApply.setStatus(WITHDRAWAL_SUCCESS.getValue());

        if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG
                .getValue() == Integer.parseInt(loanApply.getPayChannel())) {// 口袋存管
            /** 修改申请单状态为待提现512 */
            loanApply.setStatus(ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue());
        }

        loanApplyManager.updateStageOrStatus(loanApply);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);

        // 发放现金抵扣券
        try {
            // 融360不放券
            boolean isGenerateCustCoupon = true;
            if (loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15 && "4".equals(loanApply.getSource())
                    && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                    && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                isGenerateCustCoupon = false;
            }
            // 奇虎360不放券
            if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                    || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                    && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                isGenerateCustCoupon = false;
            }
            if (isGenerateCustCoupon) {
                custCouponManager.generateCustCoupon(loanApply);
            }
        } catch (Exception e) {
            logger.error("发券失败，applyId = {}", loanApply.getId());
        }
    }

    /**
     * 口袋存管放款
     *
     * @return
     */
    @Transactional
    public void processKDDepositLendPay(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        // if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
        // logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = "
        // + loanApply.getProductId());
        // throw new RuntimeException("贷款申请单的产品ID不正确, applyId = " + applyId + ",
        // productId = "
        // + loanApply.getProductId());
        // }

        // if (!PUSH_SUCCESS.getValue().equals(loanApply.getStatus())
        // && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
        // logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " +
        // getDesc(loanApply.getStatus()));
        // throw new RuntimeException("贷款申请单的状态不正确, applyId = " + applyId + ",
        // status = "
        // + getDesc(loanApply.getStatus()));
        // }

        /** 更新贷款申请单状态 */
        // updateLoanApplyInfo(loanApply);

        // LoanApply loanApply = new LoanApply();
        // loanApply.setId(applyId);
        loanApply.setPayChannel(String.valueOf(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()));
        loanApply.setPayTime(new Date());
        loanApply.setStage(HAS_BEEN_LENDING.getStage());
        loanApply.setStatus(HAS_BEEN_LENDING.getValue());
        loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        loanApply.setUpdateTime(new Date());
        loanApplyManager.updatePayChannel(loanApply);

        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue());
        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);

        /** 修改申请单状态为待还款514 */
        loanApply.setStage(WITHDRAWAL_SUCCESS.getStage());
        loanApply.setStatus(WITHDRAWAL_SUCCESS.getValue());

        if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG
                .getValue() == Integer.parseInt(loanApply.getPayChannel())) {// 口袋存管
            /** 修改申请单状态为待提现512 */
            loanApply.setStatus(ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue());
        }

        loanApplyManager.updateStageOrStatus(loanApply);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);

        // 发送短信
        sendMsg(loanApply.getUserId(), loanApply.getMobile(), String.format(ShortMsgTemplate.WITHDRAW_NOTICE,
                loanApply.getUserName(), loanApply.getApproveAmt().toString()), loanApply.getChannelId());
        // 发放现金抵扣券
        try {
            // 融360不放券
            boolean isGenerateCustCoupon = true;
            if (loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15 && "4".equals(loanApply.getSource())
                    && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                    && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                isGenerateCustCoupon = false;
            }
            // 奇虎360不放券
            if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                    || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                    && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                isGenerateCustCoupon = false;
            }
            if (isGenerateCustCoupon) {
                custCouponManager.generateCustCoupon(loanApply);
            }
        } catch (Exception e) {
            logger.error("发券失败，applyId = {}", loanApply.getId());
        }
    }

    private void sendMsg(String userId, String mobile, String msg, String channelId) {
        SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
        sendShortMsgOP.setIp("127.0.0.1");
        sendShortMsgOP.setMobile(mobile);
        // sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD,
        // password));
        sendShortMsgOP.setMessage(msg);
        sendShortMsgOP.setUserId(userId);
        sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
        sendShortMsgOP.setSource(SourceEnum.API.getCode());
        sendShortMsgOP.setChannelId(channelId);
        shortMsgService.sendMsg(sendShortMsgOP);
    }

    /**
     * 乐视放款
     *
     * @return
     */
    @Transactional
    public void processLeshiLendPay(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        Integer sourceStatus = loanApply.getStatus();
        /** 更新贷款申请单状态 */
        // y:后台手动/app放款
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue());
        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);
        /** 免密提现，修改贷款单状态，增加提现操作日志，保存paylog流水 */
        agreeWithdraw(loanApply, false);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);

        /** 聚宝贷自有资金放款宝付代付 */
        try {
            // 宝付代付
            payment(loanApply, sourceStatus);
        } catch (Exception e) {
            logger.error("放款失败，applyId={}，error={}", applyId, e.getMessage());
        }

        /** 发放购物券 */
        try {
            // 融360不放券
            boolean isGenerateCustCoupon = true;
            if ("4".equals(loanApply.getSource())
                    && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                    && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                isGenerateCustCoupon = false;
            }
            // 奇虎360不放券
            if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                    || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                    && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                isGenerateCustCoupon = false;
            }
            if (isGenerateCustCoupon) {
                custCouponManager.generateCustCoupon(loanApply);
            }
        } catch (Exception e) {
            logger.error("购物券发放失败，applyId={}，error={}", applyId, e.getMessage());
        }
    }

    /**
     * 通联放款
     *
     * @return
     */
    @Override
    public void processTltLendPay(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        /** 通联代付放款 */
        try {
            // 通联代付
            paymentForTonglian(loanApply);
        } catch (Exception e) {
            logger.error("放款失败，applyId={}，error={}", applyId, e.getMessage());
        }

    }

    /**
     * 后台推标放款
     *
     * @return
     */
    @Transactional
    public void processBorrowLendPay(String applyId, Date payTime, boolean isPayment) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())
                && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        if (loanApply.getTerm().intValue() <= 1) {
            logger.error("贷款申请单的期数不正确, applyId = " + applyId + ", term = " + loanApply.getTerm());
            throw new RuntimeException("贷款申请单的期数不正确, applyId = " + applyId + ", term = " + loanApply.getTerm());
        }
        if (loanApplyService.isBuyShoppingGold(loanApply.getId())) {
            logger.error("已推标，请勿重复提交, applyId = " + applyId);
            throw new RuntimeException("已推标，请勿重复提交, applyId = " + applyId);
        }
        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        if (StringUtils.isBlank(custUser.getCardNo())) {
            logger.error("未绑卡, applyId = " + applyId);
            throw new RuntimeException("未绑卡, applyId = " + applyId);
        }
        if (StringUtils.isBlank(custUser.getAccountId())) {
            logger.error("未开存管账号, applyId = " + applyId);
            throw new RuntimeException("未开存管账号, applyId = " + applyId);
        }
        if (isPayment) {
            // 插入推标表
            boolean flag = loanApplyService.saveShopedBorrowInfo(loanApply.getId(), LoanApplySimpleVO.APPLY_PAY_TYPE_1);
        }
    }

    /**
     * 后台手动延期
     *
     * @param repayPlanItemId 还款计划明细Id
     */
    @Transactional
    public void processAdminDelay(String repayPlanItemId) {
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        String applyId = item.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }
        if (DateUtils.isBefore(loanApply.getApproveTime(), DateUtils.parseYYMMdd("2017-12-29"))) {
            logger.error("该贷款申请单不能延期, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "该贷款申请单不能延期, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }
        if (WITHDRAWAL_SUCCESS.getValue().equals(loanApply.getStatus())
                || WAITING_REPAY.getValue().equals(loanApply.getStatus())
                || OVERDUE_WAITING_REPAY.getValue().equals(loanApply.getStatus())) {
        } else {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        processDelay(repayPlanItemId, 1);
    }

    /**
     * 主动延期
     *
     * @param applyId         订单号
     * @param repayPlanItemId 还款计划明细Id
     */
    @Transactional
    public int processManualDelay(String repayPlanItemId) {
        // y:app延期
        processDelay(repayPlanItemId, 2);

        rongPointCutService.delayPoint(repayPlanItemId);
        return 1;
    }

    /**
     * 延期
     *
     * @param repayPlanItemId
     * @param delayType       1=后台手动延期，2=app主动支付延期
     * @return
     */
    private int processDelay(String repayPlanItemId, int delayType) {
        RepayPlanItem sourceItem = repayPlanItemManager.get(repayPlanItemId);
        String applyId = sourceItem.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(applyId);
        Contract contract = contractManager.getByApplyId(applyId);
        List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(applyId);

        /** 生成新的还款计划明细 **/
        RepayPlanItem insertItem = BeanMapper.map(sourceItem, RepayPlanItem.class);
        int insertIdIndex = itemList.size() + 1;
        String insertId = insertIdIndex < 10 ? applyId + "0" + insertIdIndex : applyId + insertIdIndex;
        insertItem.setNewRecord(true);
        insertItem.preInsert();
        insertItem.setId(insertId);
        // 延期开始时间
        // 逾期情况，延期开始时间=当前时间
        // 未逾期情况，延期开始时间=还款时间
        Date delayStartDate = DateUtils.parseYYMMdd(DateUtils.getDate());
        int overdueDays = DateUtils.daysBetween(insertItem.getRepayDate(), new Date());// 逾期天数
        if (overdueDays < 0) {
            delayStartDate = insertItem.getRepayDate();
            overdueDays = 0;
        }
        // 延期天数
        int delayDays = loanApply.getApplyTerm();
        // 逾期管理费
        BigDecimal overdueFee = insertItem.getOverdueFee() == null ? BigDecimal.ZERO : insertItem.getOverdueFee();
        // 减免
        BigDecimal deduction = insertItem.getDeduction() == null ? BigDecimal.ZERO : insertItem.getDeduction();
        // 延期利息
        BigDecimal delayInterest = CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
                delayDays);
        // 延期金额
        BigDecimal delayAmt = contract.getPrincipal().multiply(new BigDecimal(Global.DELAY_RATE)).add(overdueFee)
                .subtract(deduction).setScale(2, BigDecimal.ROUND_HALF_UP);
        @SuppressWarnings("unchecked")
        Map<String, Object> extInfo = JsonMapper.getInstance().fromJson(sourceItem.getRemark(), Map.class);
        if (extInfo == null) {
            extInfo = new HashMap<String, Object>();
        }
        Object tmpDelayAmt = extInfo.get("delayAmt");
        BigDecimal currDelayAmt = (tmpDelayAmt != null) ? new BigDecimal(String.valueOf(tmpDelayAmt)) : BigDecimal.ZERO;
        extInfo.put("delayAmt", currDelayAmt.add(delayAmt));

        insertItem.setRepayDate(DateUtils.addDay(delayStartDate, delayDays));
        insertItem.setTotalAmount(insertItem.getTotalAmount().add(delayInterest).subtract(overdueFee).add(deduction));
        insertItem.setInterest(insertItem.getInterest().add(delayInterest));
        insertItem.setUnpayInterest(insertItem.getUnpayInterest().add(delayInterest));
        insertItem.setOverdueFee(BigDecimal.ZERO);
        insertItem.setDeduction(BigDecimal.ZERO);
        insertItem.setRemark(JsonMapper.getInstance().toJson(extInfo));
        repayPlanItemManager.insert(insertItem);

        /** 修改还款计划 */
        repayPlan.setLoanEndDate(DateUtils.addDay(delayStartDate, delayDays));

        String sNextRepayDate = DateUtils.formatDate(DateUtils.addDay(delayStartDate, delayDays), "yyyy-MM-dd");
        repayPlan.setNextRepayDate(sNextRepayDate);

        repayPlan.setTotalAmount(repayPlan.getTotalAmount().add(delayInterest).subtract(overdueFee).add(deduction));
        repayPlan.setInterest(repayPlan.getInterest().add(delayInterest));
        repayPlan.setUnpayInterest(repayPlan.getUnpayInterest().add(delayInterest));
        repayPlan.setOverdueFee(repayPlan.getOverdueFee().subtract(overdueFee));
        repayPlan.setDeduction(repayPlan.getDeduction().subtract(deduction));
        loanRepayPlanManager.update(repayPlan);

        /** 修改合同 */
        contract.setLoanEndDate(DateUtils.addDay(delayStartDate, delayDays));
        contract.setLoanDays(contract.getLoanDays() + overdueDays + delayDays);
        contractManager.updateForDelay(contract);

        /** 修改申请表 */
        loanApply.setApproveTerm(loanApply.getApproveTerm() + overdueDays + delayDays);
        loanApplyManager.updateForDelay(loanApply);

        /**
         * 原还款计划明细改为结清
         */
        Date repayTime = DateUtils.parse(DateUtils.getDateTime(), "yyyy-MM-dd HH:mm:ss");
        String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
        sourceItem.setActualRepayTime(repayTime);
        sourceItem.setActualRepayDate(repayDate);
        sourceItem.setActualRepayAmt(delayAmt);
        sourceItem.setStatus(ApplyStatusEnum.FINISHED.getValue());
        if (delayType == 1)
            sourceItem.setRepayType(Global.REPAY_TYPE_MANDELAY);
        else
            sourceItem.setRepayType(Global.REPAY_TYPE_MANUALDELAY);
        repayPlanItemManager.update(sourceItem);

        // 逾期结清
        Overdue overdue = overdueManager.get(sourceItem.getId());
        if (overdue != null) {
            overdue.setActualRepayAmt(delayAmt);
            overdue.setActualRepayDate(DateUtils.formatDate(repayTime, "yyyy-MM-dd"));
            overdue.setActualRepayTime(repayTime);
            overdue.setOverdueEndDate(repayTime);
            overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
            overdueManager.update(overdue);
        }
        // 线下延期记录流水
        if (delayType == 1) {
            // 线下延期功能详见delayDeal方法
            /*
             * saveDelayRepayLog(true, sourceItem.getUserId(), applyId,
             * sourceItem.getContNo(), repayPlanItemId, DateUtils.getDateTime(),
             * delayAmt, PayTypeEnum.DELAY);
             */
        }
        return 1;
    }

    /**
     * 提现
     *
     * @param applyId
     */
    public void withdraw(String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        agreeWithdraw(loanApply, true);
    }

    /**
     * 保存移动端通知
     *
     * @param loanApply
     */
    private void saveMessage(LoanApply loanApply, Date payTime) {
        try {
            BigDecimal interestPerDay = loanApply.getInterest().divide(BigDecimal.valueOf(loanApply.getApproveTerm()),
                    BigDecimal.ROUND_HALF_UP);
            String content = String.format(ShortMsgTemplate.APP_MESSAGE_HAS_BEEN_LENDING, loanApply.getApproveAmt(),
                    loanApply.getServFee(), loanApply.getApproveTerm(), interestPerDay);
            Message message = new Message();
            message.preInsert();
            message.setUserId(loanApply.getUserId());
            message.setTitle(ShortMsgTemplate.APP_MESSAGE_HAS_BEEN_LENDING_TITLE);
            message.setContent(content);
            message.setType(Global.CUST_MESSAGE_TYPE_SYS);
            message.setNotifyTime(payTime);
            message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
            message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
            message.setStatus(1);
            message.setDel(0);
            messageManager.insert(message);
        } catch (Exception e) {
            logger.error("发送APP到账通知失败,applyId = " + loanApply.getId(), e);
        }

    }

    /**
     * 保存移动端通知
     *
     * @param loanApply
     */
    private void saveMessage2(LoanApply loanApply, Date payTime) {
        try {
            String content = String.format(ShortMsgTemplate.APP_MESSAGE_HAS_BEEN_LENDING2, loanApply.getApproveAmt());
            Message message = new Message();
            message.preInsert();
            message.setUserId(loanApply.getUserId());
            message.setTitle(ShortMsgTemplate.APP_MESSAGE_HAS_BEEN_LENDING_TITLE);
            message.setContent(content);
            message.setType(Global.CUST_MESSAGE_TYPE_SYS);
            message.setNotifyTime(payTime);
            message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
            message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
            message.setStatus(1);
            message.setDel(0);
            messageManager.insert(message);
        } catch (Exception e) {
            logger.error("发送APP到账通知失败,applyId = " + loanApply.getId(), e);
        }
    }

    private void sendSMS(BigDecimal principal, String realName, String mobile, String userId, String channelId) {
        try {
            String message = String.format(ShortMsgTemplate.MSG_TEMP_HAS_BEEN_LENDING, realName, principal);
            SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
            sendShortMsgOP.setMessage(message);
            sendShortMsgOP.setMobile(mobile);
            sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
            sendShortMsgOP.setUserId(userId);
            sendShortMsgOP.setChannelId(channelId);
            shortMsgService.sendMsg(sendShortMsgOP);
        } catch (Exception e) {
            logger.error("发送放款短信失败，userId=" + userId + ", mobile=" + mobile, e);
        }

    }

    private int updateLoanApplyInfo(LoanApply before) {
        LoanApply loanApply = new LoanApply();
        loanApply.setId(before.getId());
        loanApply.setStage(HAS_BEEN_LENDING.getStage());
        loanApply.setStatus(HAS_BEEN_LENDING.getValue());
        loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        loanApply.setUpdateTime(new Date());
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    private void saveContraclog(LoanApply loanApply) {
        logger.info("合同日志：{},{},{},{},{}", loanApply.getId(), loanApply.getStage(), loanApply.getStatus(),
                SIGNED.getStage(), SIGNED.getValue());
        /*
         * try { OperationLog operationLog = new OperationLog();
         * operationLog.preInsert();
         * operationLog.setUserId(loanApply.getUserId());
         * operationLog.setPreviousStage(loanApply.getStage());
         * operationLog.setPreviousStatus(loanApply.getStatus());
         * operationLog.setApplyId(loanApply.getId());
         * operationLog.setStatus(SIGNED.getValue());
         * operationLog.setStage(SIGNED.getStage());
         * operationLog.setSource(Global.SOURCE_SYSTEM);
         * operationLog.setTime(new Date());
         * operationLog.setOperatorId(Global.DEFAULT_OPERATOR_ID);
         * operationLog.setOperatorName(Global.DEFAULT_OPERATOR_NAME);
         * operationLog.setRemark("放款成功，签订合同，生成还款计划");
         * operationLogManager.saveOperationLog(operationLog); } catch
         * (Exception e) { logger.error("保存签订合同操作日志失败，applyId = " +
         * loanApply.getId(), e); }
         */
    }

    /**
     * 提现
     *
     * @param loanApply
     */
    @Transactional
    void agreeWithdraw(LoanApply loanApply, boolean isPayLog) {
        try {
            // 提现成功的情况
            loanApply.setStage(WITHDRAWAL_SUCCESS.getStage());
            loanApply.setStatus(WITHDRAWAL_SUCCESS.getValue());
            loanApply.setRemark(WITHDRAWAL_SUCCESS.getDesc());
            // 提现金额
            BigDecimal txAmount = CostUtils.sub(loanApply.getApproveAmt(), loanApply.getServFee());
            // 更新申请表状态插入变更记录
            loanApplyManager.updateStatusAndSaveLog(loanApply);
            CustUser custUser = null;
            if (StringUtils.isNotBlank(loanApply.getUserId())) {
                custUser = custUserManager.getById(loanApply.getUserId());
                if (null != custUser && txAmount != null) {
                    // 保存提现paylog
                    if (isPayLog) {
                        savePayLog(loanApply, custUser, txAmount.toString());
                    }
                    if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
                        sendSMS(loanApply.getApproveAmt(), loanApply.getUserName(), loanApply.getMobile(),
                                loanApply.getUserId(), loanApply.getChannelId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("投复利放款免密提现，applyId=[{}]:[{}]", loanApply.getId(), e);
        }
    }

    private int savePayLog(LoanApply loanApply, CustUser custUser, String txAmount) {
        Date now = new Date();
        PayLogVO payLog = new PayLogVO();
        payLog.setApplyId(loanApply.getId());
        payLog.setContractNo(loanApply.getContNo());
        payLog.setUserId(custUser.getId());
        payLog.setUserName(custUser.getRealName());
        payLog.setTxType("auto");
        payLog.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
        payLog.setTxTime(now);
        payLog.setTxAmt(new BigDecimal(txAmount));
        payLog.setTxFee(BigDecimal.ONE);
        payLog.setToAccName(custUser.getRealName());
        payLog.setToAccNo(custUser.getCardNo());
        payLog.setToIdno(custUser.getIdNo());
        payLog.setToMobile(custUser.getMobile());
        payLog.setToBankName((appBankLimitManager.getBankName(custUser.getBankCode())));
        if (StringUtils.isNotBlank(txAmount)) {
            payLog.setSuccAmt(BigDecimal.valueOf(Double.valueOf(txAmount)));
            payLog.setSuccTime(now);
            payLog.setStatus(WITHDRAWAL_SUCCESS.getValue().toString());
        } else {
            payLog.setSuccAmt(BigDecimal.ZERO);
            payLog.setStatus(WITHDRAWAL_FAIL.getValue().toString());
        }
        return payLogService.save(payLog);
    }

    @Override
    public ContractVO getUnFinishContractByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("the param userId is null");
            return null;
        }
        // 获取合同信息
        Contract contract = contractManager.getUnFinishContractByUserId(userId);
        if (null != contract) {
            ContractVO contractVO = new ContractVO();
            contractVO.setContractNo(contract.getId());
            contractVO.setApplyId(contract.getApplyId());
            return contractVO;
        }
        return null;
    }

    /**
     * 手动延期金额查询
     */
    @Override
    public Map<String, Object> getDelayAmount(String repayPlanItemId, String delayDate) {
        RepayPlanItem sourceItem = repayPlanItemManager.get(repayPlanItemId);
        String applyId = sourceItem.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        Contract contract = contractManager.getByApplyId(applyId);
        Date delayStartDate = DateUtils.parseYYMMdd(delayDate);
        int overdueDays = DateUtils.daysBetween(sourceItem.getRepayDate(), delayStartDate);// 逾期天数
        if (overdueDays < 0) {
            overdueDays = 0;
        }
        // 逾期管理费
        BigDecimal overdueFee = CostUtils.calOverFee(loanApply.getOverdueFee(), overdueDays, loanApply.getApproveAmt());
        // 减免
        BigDecimal deduction = sourceItem.getDeduction() == null ? BigDecimal.ZERO : sourceItem.getDeduction();
        // 减免金额不能大于逾期管理费
        if (deduction.compareTo(overdueFee.multiply(new BigDecimal(0.5))) > 0)
            deduction = overdueFee.multiply(new BigDecimal(0.5));
        // 延期金额
        BigDecimal delayAmt = contract.getPrincipal().multiply(new BigDecimal(Global.DELAY_RATE)).add(overdueFee)
                .subtract(deduction).setScale(2, BigDecimal.ROUND_HALF_UP);
        Map<String, Object> map = Maps.newHashMap();
        map.put("delayAmt", delayAmt);
        map.put("repayPlanItemId", repayPlanItemId);
        return map;
    }

    /*
     * 手动延期
     */
    @Override
    @Transactional
    public void delayDeal(String repayPlanItemId, int delayType, String delayDate, String repayType,
                          String repayTypeName) {
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", repayPlanItemId);
            throw new RuntimeException("还款明细不存在或已结清");
        }
        String applyId = item.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }
        if (DateUtils.isBefore(loanApply.getApproveTime(), DateUtils.parseYYMMdd("2017-12-29"))) {
            logger.error("该贷款申请单不能延期, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "该贷款申请单不能延期, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }
        if (WITHDRAWAL_SUCCESS.getValue().equals(loanApply.getStatus())
                || WAITING_REPAY.getValue().equals(loanApply.getStatus())
                || OVERDUE_WAITING_REPAY.getValue().equals(loanApply.getStatus())) {
        } else {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        // code y0524 统计已经提交支付，正在处理中的订单，如果有，就不能手动延期
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            throw new OrderProcessingException("99", "订单正在处理中，请稍后再试");
        }
        delaySubmit(repayPlanItemId, delayType, delayDate, repayType, repayTypeName);

        rongPointCutService.delayPoint(repayPlanItemId);// 用作Rong360订单延期时，切面通知的切入点标记
    }

    private int delaySubmit(String repayPlanItemId, int delayType, String delayDate, String repayType,
                            String repayTypeName) {
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        String applyId = item.getApplyId();
        LoanApply apply = loanApplyManager.getLoanApplyById(applyId);
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(applyId);
        Contract contract = contractManager.getByApplyId(applyId);
        List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(applyId);

        /** 生成新的还款计划明细 **/
        RepayPlanItem insertItem = BeanMapper.map(item, RepayPlanItem.class);
        int insertIdIndex = itemList.size() + 1;
        String insertId = insertIdIndex < 10 ? applyId + "0" + insertIdIndex : applyId + insertIdIndex;
        insertItem.setNewRecord(true);
        insertItem.preInsert();
        insertItem.setId(insertId);
        insertItem.setStatus(ApplyStatusEnum.UNFINISH.getValue());
        // 延期开始时间
        // 逾期情况，延期开始时间=延期时间
        // 未逾期情况，延期开始时间=还款时间
        Date delayStartDate = DateUtils.parseYYMMdd(delayDate);
        int overdueDays = DateUtils.daysBetween(insertItem.getRepayDate(), delayStartDate);
        if (overdueDays < 0) {
            delayStartDate = insertItem.getRepayDate();
            overdueDays = 0;
        }
        // 延期天数
        int delayDays = apply.getApplyTerm();
        // 延期利息
        BigDecimal delayInterest = CostUtils.calCurInterest(apply.getApproveAmt(), apply.getActualRate(), delayDays);

        BigDecimal principal = item.getPrincipal();
        BigDecimal interest = BigDecimal.ZERO;
        BigDecimal overdueFee = BigDecimal.ZERO;
        BigDecimal penalty = BigDecimal.ZERO;
        BigDecimal prepayFee = item.getPrepayFee();
        BigDecimal servFee = item.getServFee();
        BigDecimal deduction = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (overdueDays > 0) {
            // 当前利息
            int repayUnit = apply.getRepayUnit() == null ? 0 : apply.getRepayUnit().intValue();
            interest = RepayPlanHelper.getCurrInterest(apply.getRepayMethod(), apply.getApproveAmt(),
                    apply.getApproveTerm(), item.getTotalTerm(), item.getThisTerm(), apply.getActualRate(), overdueDays,
                    repayUnit, apply.getPayChannel(), apply.getServFee());
            // 逾期管理费
            overdueFee = CostUtils.calOverFee(apply.getOverdueFee(), overdueDays, apply.getApproveAmt());
            // 罚息
            penalty = CostUtils.calPenalty(apply.getOverdueRate(), apply.getApproveAmt(), overdueDays);
            // 减免金额不能大于逾期管理费
            if (item.getDeduction().compareTo(overdueFee.multiply(new BigDecimal(0.5))) > 0) {
                deduction = overdueFee.multiply(new BigDecimal(0.5));
            } else {
                deduction = item.getDeduction();
            }
            // 应还本息 = 应还本金+应还利息+逾期管理费+罚息+提前还款手续费-减免费用
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        } else {
            interest = item.getInterest();
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        }

        // 延期金额
        BigDecimal delayAmt = contract.getPrincipal().multiply(new BigDecimal(Global.DELAY_RATE)).add(overdueFee)
                .subtract(deduction).setScale(2, BigDecimal.ROUND_HALF_UP);
        Map<String, Object> extInfo = JsonMapper.getInstance().fromJson(item.getRemark(), Map.class);
        if (extInfo == null) {
            extInfo = new HashMap<String, Object>();
        }
        Object tmpDelayAmt = extInfo.get("delayAmt");
        BigDecimal currDelayAmt = (tmpDelayAmt != null) ? new BigDecimal(String.valueOf(tmpDelayAmt)) : BigDecimal.ZERO;
        extInfo.put("delayAmt", currDelayAmt.add(delayAmt));

        insertItem.setRepayDate(DateUtils.addDay(delayStartDate, delayDays));
        insertItem.setTotalAmount(CostUtils
                .calTotalAmount(principal, interest, BigDecimal.ZERO, penalty, prepayFee, servFee, BigDecimal.ZERO)
                .add(delayInterest));
        insertItem.setInterest(interest.add(delayInterest));
        insertItem.setUnpayInterest(interest.add(delayInterest));
        insertItem.setOverdueFee(BigDecimal.ZERO);
        insertItem.setPenalty(penalty);
        insertItem.setDeduction(BigDecimal.ZERO);
        insertItem.setRemark(JsonMapper.getInstance().toJson(extInfo));
        insertItem.setActualRepayAmt(null);
        insertItem.setActualRepayDate(null);
        insertItem.setActualRepayTime(null);
        repayPlanItemManager.insert(insertItem);

        /** 修改还款计划 */
        List<RepayPlanItem> itemList2 = new ArrayList<RepayPlanItem>();
        itemList2.add(insertItem);
        repayPlan = RepayPlanHelper.summaryLoanRepayPlan(repayPlan, itemList2);
        repayPlan.setLoanEndDate(DateUtils.addDay(delayStartDate, delayDays));
        loanRepayPlanManager.update(repayPlan);

        /** 修改合同 */
        contract.setLoanEndDate(DateUtils.addDay(delayStartDate, delayDays));
        contract.setLoanDays(apply.getApproveTerm() + overdueDays + delayDays);
        contractManager.updateForDelay(contract);

        /** 修改申请表 */
        apply.setApproveTerm(apply.getApproveTerm() + overdueDays + delayDays);
        loanApplyManager.updateForDelay(apply);

        /**
         * 原还款计划明细改为结清
         */
        Date repayTime = DateUtils.parse(delayDate, "yyyy-MM-dd HH:mm:ss");
        String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
        item.setActualRepayTime(repayTime);
        item.setActualRepayDate(repayDate);
        item.setActualRepayAmt(delayAmt);
        item.setTotalAmount(totalAmount);
        item.setInterest(interest);
        item.setUnpayInterest(interest);
        item.setOverdueFee(overdueFee);
        item.setPenalty(penalty);
        item.setDeduction(deduction);
        item.setRepayType(Global.REPAY_TYPE_MANDELAY);
        /*
         * if (ApplyStatusEnum.PROCESSING.getValue().equals(item.getStatus())) {
         * item.setRepayType(Global.REPAY_TYPE_AUTODELAY); }
         */
        item.setStatus(ApplyStatusEnum.FINISHED.getValue());
        repayPlanItemManager.update(item);

        // 逾期结清
        Overdue overdue = overdueManager.get(item.getId());
        if (overdue != null) {
            if (overdueDays > 0) {
                overdue.setActualRepayAmt(delayAmt);
                overdue.setActualRepayDate(DateUtils.formatDate(repayTime, "yyyy-MM-dd"));
                overdue.setActualRepayTime(repayTime);
                overdue.setOverdueEndDate(repayTime);
                overdue.setOverdueDays(overdueDays);
                overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
                overdueManager.update(overdue);
            } else {
                // 没逾期删除记录
                overdueManager.deleteTruely(item.getId());
            }
        }
        if (delayType == 1) {
            // 线下延期记录流水
            saveDelayRepayLog(true, item.getUserId(), applyId, item.getContNo(), repayPlanItemId, delayDate, delayAmt,
                    PayTypeEnum.DELAY, repayType, repayTypeName);
        }
        return 1;
    }

    /**
     * 乐视宝付代付
     */
    public boolean payment(LoanApply loanApply, Integer sourceStatus) {
        BigDecimal payAmt = CostUtils.sub(loanApply.getApproveAmt(), loanApply.getServFee());
        // ytodo 0303 加急券
        if ("S".equals(loanApply.getUrgentPayed())) {
            // payAmt = loanApply.getApproveAmt();
        }
        // if (WAITING_LENDING.getValue().equals(sourceStatus)) {
        // payAmt = loanApply.getApproveAmt();
        // }
        CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
        if (null != custUserVO && payAmt != null) {
            PayLogVO pw = new PayLogVO();
            pw.setUserId(loanApply.getUserId());
            pw.setApplyId(loanApply.getId());
            pw.setUserName(custUserVO.getRealName());
            pw.setToAccName(custUserVO.getRealName());
            pw.setToAccNo(custUserVO.getCardNo());
            pw.setToIdno(custUserVO.getIdNo());
            pw.setToMobile(StringUtils.isNotBlank(custUserVO.getBankMobile()) ? custUserVO.getBankMobile()
                    : custUserVO.getMobile());
            pw.setTxAmt(payAmt);
            pw.setContractNo(loanApply.getContNo());
            pw.setToBankName(appBankLimitManager.getBankName(custUserVO.getBankCode()));
            pw.setChlCode(Global.BAOFOO_CHANNEL_CODE);
            pw.setChlName(Global.BAOFOO_CHANNEL_NAME);
            return baofooWithdrawService.payment(pw);
        }
        return Boolean.FALSE;
    }

    /**
     * 通联代付
     */
    public boolean paymentForTonglian(LoanApply loanApply) {
        BigDecimal payAmt = CostUtils.sub(loanApply.getApproveAmt(), loanApply.getServFee());
        CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
        if (null != custUserVO && payAmt != null) {
            PayLogVO pw = new PayLogVO();
            pw.setUserId(loanApply.getUserId());
            pw.setApplyId(loanApply.getId());
            pw.setUserName(custUserVO.getRealName());
            pw.setToAccName(custUserVO.getRealName());
            pw.setToAccNo(custUserVO.getCardNo());
            pw.setToIdno(custUserVO.getIdNo());
            pw.setToMobile(StringUtils.isNotBlank(custUserVO.getBankMobile()) ? custUserVO.getBankMobile()
                    : custUserVO.getMobile());
            pw.setTxAmt(payAmt);
            pw.setContractNo(loanApply.getContNo());
            pw.setToBankName(appBankLimitManager.getBankName(custUserVO.getBankCode()));
            pw.setToBankCode(custUserVO.getBankCode());
            pw.setChlCode(Global.TONGLIAN_CHANNEL_CODE);
            pw.setChlName(Global.TONGLIAN_CHANNEL_NAME);
            return tltAgreementPayService.prePayment(pw);
        }
        return Boolean.FALSE;
    }

    /**
     * 通融宝付代付
     */
    public boolean paymentForTongrong(LoanApply loanApply, Integer sourceStatus) {
        BigDecimal payAmt = CostUtils.sub(loanApply.getApproveAmt(), loanApply.getServFee());
        // ytodo 0303 加急券
        if ("S".equals(loanApply.getUrgentPayed())) {
            // payAmt = loanApply.getApproveAmt();
        }
        // if (WAITING_LENDING.getValue().equals(sourceStatus)) {
        // payAmt = loanApply.getApproveAmt();
        // }
        CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
        if (null != custUserVO && payAmt != null) {
            PayLogVO pw = new PayLogVO();
            pw.setUserId(loanApply.getUserId());
            pw.setApplyId(loanApply.getId());
            pw.setUserName(custUserVO.getRealName());
            pw.setToAccName(custUserVO.getRealName());
            pw.setToAccNo(custUserVO.getCardNo());
            pw.setToIdno(custUserVO.getIdNo());
            pw.setToMobile(StringUtils.isNotBlank(custUserVO.getBankMobile()) ? custUserVO.getBankMobile()
                    : custUserVO.getMobile());
            pw.setTxAmt(payAmt);
            pw.setContractNo(loanApply.getContNo());
            pw.setToBankName(appBankLimitManager.getBankName(custUserVO.getBankCode()));
            pw.setChlCode(Global.TONGRONG_BAOFOO_CHANNEL_CODE);
            pw.setChlName(Global.TONGRONG_BAOFOO_CHANNEL_NAME);
            return trBaofooWithdrawService.payment(pw);
        }
        return Boolean.FALSE;
    }

    /**
     * code y0621 先锋代付
     */
    private XfWithdrawResultVO xfWithdraw(LoanApply loanApply, Integer sourceStatus) {
        XfWithdrawResultVO vo = null;
        int record = payLogService.findUnsolvedXfWithdrawRecord(loanApply.getId());
        if (record > 0) {
            logger.warn("先锋-代付，该笔代付申请订单已经提交成功，请勿重复付款：applyId={},name={},mobile={}", loanApply.getId(),
                    loanApply.getUserName(), loanApply.getMobile());
            return new XfWithdrawResultVO(ErrInfo.ERROR.getCode(), "订单已经提交成功，请勿重复付款");
        }
        BigDecimal payAmt = CostUtils.sub(loanApply.getApproveAmt(), loanApply.getServFee());
        if (WAITING_LENDING.getValue().equals(sourceStatus)) {
            payAmt = loanApply.getApproveAmt();
        }

        XfWithdrawOP op = new XfWithdrawOP();
        if (payAmt != null) {
            op.setAmount(payAmt.toString());
            op.setApplyId(loanApply.getId());
            op.setContractId(loanApply.getContNo());
            op.setUserId(loanApply.getUserId());
        }
        vo = xianFengWithdrawService.xfWithdraw(op);
        return vo;
    }

    /**
     * 保存线下延期还款记录
     *
     * @param vo
     * @return
     */
    private int saveDelayRepayLog(boolean isSuccess, String userId, String applyId, String contNo,
                                  String repayPlanItemId, String tradeDate, BigDecimal tradeAmt, PayTypeEnum payType, String repayType,
                                  String repayTypeName) {

        CustUserVO user = custUserService.getCustUserById(userId);

        Date now = new Date();
        RepayLogVO repayLog = new RepayLogVO();
        repayLog.setId(IdGen.uuid());
        repayLog.setNewRecord(true);
        repayLog.setApplyId(applyId);
        repayLog.setContractId(contNo);
        repayLog.setRepayPlanItemId(repayPlanItemId);
        repayLog.setUserId(user.getId());
        repayLog.setUserName(user.getRealName());
        repayLog.setIdNo(user.getIdNo());
        repayLog.setMobile(user.getMobile());
        repayLog.setTxType("MANPAY");
        Date dTradeDate = DateUtils.parse(tradeDate, "yyyy-MM-dd HH:mm:ss");
        repayLog.setTxDate(Long.parseLong(DateUtils.formatDate(dTradeDate, "yyyyMMdd")));
        if (StringUtils.isNotBlank(tradeDate)) {
            repayLog.setTxTime(dTradeDate);
        }
        repayLog.setTxAmt(tradeAmt);
        repayLog.setTxFee(BigDecimal.ZERO);
        repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
        repayLog.setChlOrderNo("");
        repayLog.setChlName(repayTypeName);
        repayLog.setChlCode(repayType);
        repayLog.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());
        repayLog.setBankCode(user.getBankCode());
        repayLog.setCardNo(user.getCardNo());
        repayLog.setBankName("");
        repayLog.setGoodsName("聚宝贷还款");
        repayLog.setGoodsNum(1);
        repayLog.setPayType(payType.getId());
        repayLog.setSuccAmt(tradeAmt);
        repayLog.setSuccTime(now);
        if (isSuccess) {
            repayLog.setStatus("SUCCESS");
            repayLog.setRemark("交易成功");
        } else {
            repayLog.setStatus("FAIL");
            repayLog.setRemark("交易失败");
        }
        int rz = repayLogService.save(repayLog);
        return rz;
    }

    @Override
    public int getServFeeWithholdResult(String lid) {
        BorrowInfo borrowInfo = borrowInfoManager.getByOutsideSerialNo(lid);
        if (borrowInfo == null) {
            logger.error("标的不存在，lid = {}", lid);
            return 0;
        }
        ShopWithhold withHold = shopWithholdManager.findByApplyId(borrowInfo.getApplyId());
        if (withHold == null || withHold.getWithholdStatus().intValue() != 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 通融放款
     *
     * @return
     */
    @Transactional
    public void processTongRongLendPay(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }

        /** 更新贷款申请单状态 */
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue());

        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);

        /** 修改申请单状态为待还款514 */
        loanApply.setStage(WITHDRAWAL_SUCCESS.getStage());
        loanApply.setStatus(WITHDRAWAL_SUCCESS.getValue());
        loanApplyManager.updateStageOrStatus(loanApply);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);

        // 发放现金抵扣券
        try {
            // 融360不放券
            boolean isGenerateCustCoupon = true;
            if ("4".equals(loanApply.getSource())
                    && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                    && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                isGenerateCustCoupon = false;
            }
            // 奇虎360不放券
            if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                    || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                    && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                isGenerateCustCoupon = false;
            }
            if (isGenerateCustCoupon) {
                custCouponManager.generateCustCoupon(loanApply);
            }
        } catch (Exception e) {
            logger.error("发券失败，applyId = {}", loanApply.getId());
        }
    }

    @Override
    @Transactional
    public void processHanJSLendPay(String applyId, Date payTime, String payAmount) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
            throw new RuntimeException(
                    "贷款申请单的产品ID不正确, applyId = " + applyId + ", productId = " + loanApply.getProductId());
        }

        if (!PUSH_SUCCESS.getValue().equals(loanApply.getStatus())
                && LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }

        /** 更新贷款申请单状态 */
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue());

        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);

        /** 修改申请单状态为待提现 */
        loanApply.setStage(WAITING_WITHDRAWAL.getStage());
        loanApply.setStatus(WAITING_WITHDRAWAL.getValue());
        // 汉金所更新放款时间
        loanApply.setPayTime(payTime);
        loanApplyManager.updateStageOrStatus(loanApply);
        /** 移动端通知 */
        saveMessage2(loanApply, payTime);
        /** 保存流水日志 */
        saveHanJSPayLog(loanApply, payAmount);
        /** 发送短信通知 */
        sendMsg(loanApply.getUserId(), loanApply.getMobile(), String.format(ShortMsgTemplate.WITHDRAW_NOTICE,
                loanApply.getUserName(), loanApply.getApproveAmt().toString()), loanApply.getChannelId());
        // 发放现金抵扣券
        try {
            // 融360不放券
            boolean isGenerateCustCoupon = true;
            if ("4".equals(loanApply.getSource())
                    && ("RONG".equals(loanApply.getChannelId()) || "RONGJHH".equals(loanApply.getChannelId()))
                    && JedisUtils.get("rong360_jubao_borrow_info_" + loanApply.getId()) == null) {
                isGenerateCustCoupon = false;
            }
            // 奇虎360不放券
            if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15
                    || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) && "4".equals(loanApply.getSource())
                    && "SLLAPIJHH".equals(loanApply.getChannelId())) {
                isGenerateCustCoupon = false;
            }
            if (isGenerateCustCoupon) {
                custCouponManager.generateCustCoupon(loanApply);
            }
        } catch (Exception e) {
            logger.error("发券失败，applyId = {}", loanApply.getId());
        }
    }

    @Override
    @Transactional
    public void createRepayPlan(String applyId, Date payTime) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        /** 更新贷款申请单状态 */
        updateLoanApplyInfo(loanApply);
        /** 签订合同 */
        Contract contract = contractManager.insert(loanApply, payTime);
        /** 保存签订日志 */
        saveContraclog(loanApply);
        /** 生成还款计划 */
        LoanRepayPlan loanRepayPlan = loanRepayPlanManager.insert(loanApply, contract,
                WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue());
        /** 生成还款计划明细 */
        List<RepayPlanItem> itemList = null;
        if (LoanProductEnum.JDQ.getId().equals(loanApply.getProductId())){
            itemList = repayPlanItemManager.insertTLRepayList(loanApply, contract, loanRepayPlan);
        }else if (LoanProductEnum.JNFQ.getId().equals(loanApply.getProductId())){
            itemList = repayPlanItemManager.insertTLRepayList2(loanApply, contract, loanRepayPlan);
        }else {
            itemList = repayPlanItemManager.insertBatch(loanApply, contract, loanRepayPlan);
        }
        /** 汇总还款计划明细，更新还款计划 */
        loanRepayPlan = RepayPlanHelper.summaryLoanRepayPlan(loanRepayPlan, itemList);
        loanRepayPlanManager.update(loanRepayPlan);

        /** 修改申请单状态为已放款511 */
        loanApply.setStage(ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getStage());
        loanApply.setStatus(ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue());

        loanApplyManager.updateStageOrStatus(loanApply);
    }

    private int saveHanJSPayLog(LoanApply loanApply, String payAmount) {
        CustUserVO custUser = custUserService.getCustUserById(loanApply.getUserId());
        Date now = new Date();
        PayLogVO payLog = new PayLogVO();
        payLog.setIsNewRecord(true);
        payLog.setId("HJS" + loanApply.getId());
        payLog.setApplyId(loanApply.getId());
        payLog.setContractNo(loanApply.getContNo());
        payLog.setChlCode("HANJS");
        payLog.setChlName("汉金所");
        payLog.setUserId(custUser.getId());
        payLog.setUserName(custUser.getRealName());
        payLog.setTxType("auto");
        payLog.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
        payLog.setTxTime(now);
        payLog.setTxAmt(new BigDecimal(payAmount));
        payLog.setTxFee(BigDecimal.ONE);
        payLog.setToAccName(custUser.getRealName());
        payLog.setToIdno(custUser.getIdNo());
        payLog.setToMobile(custUser.getMobile());
        payLog.setSuccAmt(BigDecimal.valueOf(Double.valueOf(payAmount)));
        payLog.setSuccTime(now);
        payLog.setStatus(WAITING_WITHDRAWAL.getValue().toString());
        return payLogService.save(payLog);
    }
}
