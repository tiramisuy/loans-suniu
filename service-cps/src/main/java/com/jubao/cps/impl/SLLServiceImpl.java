package com.jubao.cps.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jubao.cps.common.HttpClient;
import com.jubao.cps.entity.ApplyTripartiteSll;
import com.jubao.cps.manager.ApplyTripartiteSllManager;
import com.jubao.cps.utils.CpsUtil;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.security.MD5Utils;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.sll.SLLUtil;
import com.rongdu.loans.cust.option.BaseInfoOP;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.fileserver.service.FileserverService;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.option.SLL.*;
import com.rongdu.loans.loan.option.jdq.*;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.FileUploadResult;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.loan.vo.sll.*;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lee on 2018/12/10.
 */
@Slf4j
@Service("sllService")
public class SLLServiceImpl implements SLLService {
    private static Logger logger = LoggerFactory.getLogger(SLLServiceImpl.class);
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private FileserverService fileserverService;
    @Autowired
    private ApplyTripartiteSllManager applyTripartiteSllManager;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private OverdueService overdueService;
    @Autowired
    private AppBankLimitService appBankLimitService;
    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;
    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private PromotionCaseService promotionCaseService;
    @Autowired
    private SLLStatusFeedBackService sllStatusFeedBackService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;


    private FileServerClient fileServerClient = new FileServerClient();

    private void sendMsg(String password, String userId, String mobile) {
        SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
        sendShortMsgOP.setIp("127.0.0.1");
        sendShortMsgOP.setMobile(mobile);
        sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
        sendShortMsgOP.setUserId(userId);
        sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
        sendShortMsgOP.setSource(SourceEnum.API.getCode());
        sendShortMsgOP.setChannelId("SLLAPI");
        shortMsgService.sendMsg(sendShortMsgOP);
    }

    @Override
    public boolean saveBaseInfo(BaseData orderBaseInfo) {
        boolean flag = false;
        try {
            String userPhone = orderBaseInfo.getOrderinfo().getUserMobile();
            String channelId = "SLLAPI";
            String productId = orderBaseInfo.getOrderinfo().getProductId();
            if (productId == Global.SLL_JQB_PRODUCTID) {
                channelId = "SLLAPI";
            } else if (productId == Global.SLL_JHH_PRODUCTID) {
                channelId = "SLLAPIJHH";
            }
            String userId = registerOrReturnUserId(userPhone, channelId);
            FileInfoVO fileInfoVO = custUserService.getLastSLLBaseByOrderSn(orderBaseInfo.getOrderinfo().getOrderNo());
            if (fileInfoVO == null) {
                String res = CpsUtil.uploadBaseData(orderBaseInfo, FileBizCode.SLL_BASE_DATA.getBizCode(), userId,
                        orderBaseInfo.getOrderinfo().getOrderNo());
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveAdditionInfo(AddData orderAppendInfo) {
        boolean flag = false;
        try {
            String orderSn = orderAppendInfo.getOrderNo();
            FileInfoVO fileInfoVO = custUserService.getLastSLLAdditionalByOrderSn(orderSn);
            if (fileInfoVO == null) {
                String res = CpsUtil.uploadBaseData(orderAppendInfo, FileBizCode.SLL_ADDITIONAL_DATA.getBizCode(),
                        "sllAdditional", orderSn);
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                    Map<String, String> map = Maps.newHashMap();
                    map.put(orderSn, String.valueOf(System.currentTimeMillis()));
                    JedisUtils.mapPut(Global.SLL_THIRD_KEY, map);
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getOrderNo(String applyId) {
        String orderNo = "";
        if (applyId == null) {
            applyId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        ApplyTripartiteSll applyTripartiteSll = applyTripartiteSllManager.getByCriteria(criteria);
        if (applyTripartiteSll != null) {
            orderNo = applyTripartiteSll.getTripartiteNo();
        }
        return orderNo;
    }

    @Override
    public String getApplyId(String orderNo) {
        String applyId = "";
        if (orderNo == null) {
            orderNo = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("tripartite_no", orderNo));
        ApplyTripartiteSll applyTripartiteSll = applyTripartiteSllManager.getByCriteria(criteria);
        if (applyTripartiteSll != null) {
            applyId = applyTripartiteSll.getApplyId();
        }
        return applyId;
    }

    @Override
    public List<String> findThirdIdsByApplyIds(List<String> applyIds) {
        return applyTripartiteSllManager.findThirdIdsByApplyIds(applyIds);
    }

    @Override
    public SLLResp orderQuickLoan(QuickLoanOP quickLoanOP) {
        SLLResp sllResp = new SLLResp();
        String userName = quickLoanOP.getUserName();
        String userMobile = quickLoanOP.getUserMobile();
        String userIdCard = quickLoanOP.getIdCard();
        userIdCard = userIdCard.replace("*", "_");
        userMobile = userMobile.replace("*", "_");
        String key = MD5Utils.MD5(userMobile + userIdCard);
        SLLResp sllRespCache = getIsUserAcceptCache(key);
        if (sllRespCache != null) {
            return sllRespCache;
        }

        String md5 = quickLoanOP.getMd5();
        if (StringUtils.isBlank(md5)){
            sllResp.setCode(SLLResp.FAILURE);// code=400 代表不可申请
            sllResp.setReason("C002");// C002：不符合机构要求
            return sllResp;
        }
        String applyLock = JedisUtils.get("SLL:APPLY_LOCK_" + md5);
        if (applyLock != null) {
            sllResp.setCode(SLLResp.FAILURE);// code=400 代表不可申请
            sllResp.setReason("C001");// 	C001：已经在对方有进行中的贷款
            setIsUserAcceptCache(sllResp, key);
            return sllResp;
        }
        String sll_limit_day = "";
        int sllLimitDay = StringUtils.isBlank(sll_limit_day) ? 60 : Integer.parseInt(sll_limit_day);
        CustUserVO custUserVO = custUserService.isRegister(userName, userMobile, userIdCard);
        if (custUserVO != null) {
            String userId = custUserVO.getId();
            // 聚宝自有黑名单
            boolean isBlackUser = riskBlacklistService.inBlackList(userName, userMobile, userIdCard);
            if (isBlackUser) {
                sllResp.setCode(SLLResp.FAILURE);// code=400 代表不可申请
                sllResp.setReason("C002");// C002：在对方有不良贷款记录
                setIsUserAcceptCache(sllResp, key);
                return sllResp;
            }

            // 未完成工单
            boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
            if (isExist) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setReason("C001");// C001：已经在对方有进行中的贷款
                setIsUserAcceptCache(sllResp, key);
                return sllResp;
            }

            // 在本平台有15天以上逾期记录
            int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
            if (maxOverdueDays > 15) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setReason("C002");// C002：在对方有不良贷款记录
                setIsUserAcceptCache(sllResp, key);
                return sllResp;
            }

            // 被拒日期距今不满60天
            // LoanApply lastApply =
            // loanApplyManager.getLastFinishApplyByUserId(userId);
            ApplyListVO lastApply = loanApplyService.getLastFinishApplyByUserId(userId);
            if (lastApply != null
                    && (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(lastApply.getStatus())
                    || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(lastApply.getStatus()))) {
                Date lastUpdateTime = lastApply.getUpdateTime();
                long pastDays = DateUtils.pastDays(lastUpdateTime);
                if (pastDays < sllLimitDay) {
                    sllResp.setCode(SLLResp.FAILURE);
                    sllResp.setReason("C003");// C003：30 天内被机构审批拒绝过
                    setIsUserAcceptCache(sllResp, key);
                    return sllResp;
                }
            }
        }
        sllResp.setCode(SLLResp.SUCCESS);
        sllResp.setMsg("SUCCESS");
        QuickLoanVO vo = new QuickLoanVO();
        vo.setIsReloan("0");
        sllResp.setData(vo);
        setIsUserAcceptCache(sllResp, key);
        return sllResp;
    }

    @Override
    public SLLResp cardBind(CardBindOP cardBindOP) {
        SLLResp sllResp = new SLLResp();
        CardBindVO cardBindVO = new CardBindVO();
        String applyId = this.getApplyId(cardBindOP.getOrderNo());
        // LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        if (loanApply == null || StringUtils.isBlank(loanApply.getUserId())) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("用户数据异常");
            cardBindVO.setNeedConfirm("0");// 0=不发送验证码
            // cardBindVO.setDealResult("0");// 0-绑卡失败
            sllResp.setData(cardBindVO);
            log.error("【奇虎360-用户绑定银行卡接口】异常-用户订单不存在orderNo={}", cardBindOP.getOrderNo());
            return sllResp;
        }
        String userId = loanApply.getUserId();
        try {
            // 验证银行是否开通
            boolean isOpen = appBankLimitService.isOpen(cardBindOP.getOpenBank());
            if (!isOpen) {
                // log.error("银行卡暂不支持绑定:{},{}", userId,
                // custUserVO.getBankCode());
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
                cardBindVO.setNeedConfirm("0");// 0=不发送验证码
                // cardBindVO.setDealResult("0");// 0-绑卡失败
                sllResp.setData(cardBindVO);
                return sllResp;
            }

            DirectBindCardOP bindCardOp = new DirectBindCardOP();
            bindCardOp.setCardNo(cardBindOP.getBankCard());
            bindCardOp.setIdNo(cardBindOP.getIdNumber());
            bindCardOp.setMobile(cardBindOP.getUserMobile());
            bindCardOp.setRealName(cardBindOP.getUserName());
            bindCardOp.setUserId(userId);
            bindCardOp.setBankCode(cardBindOP.getOpenBank());
            bindCardOp.setSource("4");
            bindCardOp.setIpAddr("127.0.0.1");
            // 宝付协议支付预绑卡银行发验证码
            BindCardResultVO bindCardResult = baofooAgreementPayService.agreementPreBind(bindCardOp);
            if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getBindId())) {
                // logger.error("[{}]协议支付预绑卡失败", custUserVO.getMobile());
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg(bindCardResult.getMsg());
                cardBindVO.setNeedConfirm("0");// 0=不发送验证码
                // cardBindVO.setDealResult("0");// 0-绑卡失败
                sllResp.setData(cardBindVO);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("bindId", bindCardResult.getBindId());
                map.put("orderNo", bindCardResult.getOrderNo());
                JedisUtils.setMap("SLL:bankVerify_" + userId, map, 60 * 3);
                sllResp.setCode(SLLResp.SUCCESS);
                sllResp.setMsg("SUCCESS");
                cardBindVO.setNeedConfirm("1");// 1=发送验证码
                sllResp.setData(cardBindVO);
            }
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("绑卡异常");
            cardBindVO.setNeedConfirm("0");// 0=不发送验证码
            // cardBindVO.setDealResult("0");// 0-绑卡失败
            sllResp.setData(cardBindVO);
            log.error("【奇虎360-用户绑定银行卡接口】异常orderNo={}", cardBindOP.getOrderNo(), e);
        }
        return sllResp;
    }

    @Override
    public SLLResp cardBindConfirm(CardBindOP cardBindOP) {
        SLLResp sllResp = new SLLResp();
        // CardBindVO cardBindVO = new CardBindVO();
        String applyId = this.getApplyId(cardBindOP.getOrderNo());
        // LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        String userId = loanApply.getUserId();

        try {
            Map<String, String> map = JedisUtils.getMap("SLL:bankVerify_" + userId);
            String orderNo = map == null ? "" : map.get("orderNo");
            String bindId = map == null ? "" : map.get("bindId");
            ConfirmBindCardOP bindCardOp = new ConfirmBindCardOP();
            bindCardOp.setMsgVerCode(cardBindOP.getVerifyCode());
            bindCardOp.setOrderNo(orderNo);
            bindCardOp.setBindId(bindId);
            bindCardOp.setUserId(userId);
            bindCardOp.setSource("4");
            bindCardOp.setType(1);
            // 宝付协议确认绑卡
            BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(bindCardOp);
            if (!bindCardResult.isSuccess()) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg(bindCardResult.getMsg());
                // cardBindVO.setDealResult("0");// 0-绑卡失败
                // sllResp.setData(cardBindVO);
                return sllResp;
            }
            if (StringUtils.isBlank(bindCardResult.getBindId())) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("银行卡信息有误，请重试或换卡");
                // cardBindVO.setDealResult("0");// 0-绑卡失败
                // sllResp.setData(bankVerifyVO);
                return sllResp;
            }
            BindCardVO bindInfo = bindCardService.findByOrderNo(orderNo);
            if (bindInfo == null) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("银行卡信息有误，请重试或换卡");
                // bankVerifyVO.setDealResult("0");// 0-绑卡失败
                // sllResp.setData(bankVerifyVO);
                return sllResp;
            }
            bindInfo.setBindId(bindCardResult.getBindId());
            bindInfo.setStatus(bindCardResult.getCode());
            bindInfo.setRemark(bindCardResult.getMsg());
            bindInfo.setChlName("宝付确认绑卡");
            bindInfo.setSource("4");
            bindInfo.setIp("127.0.0.1");

            int saveBc = bindCardService.update(bindInfo);
            if (saveBc == 0) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("银行卡信息有误，请重试或换卡");
                // bankVerifyVO.setDealResult("0");// 0-绑卡失败
                // sllResp.setData(bankVerifyVO);
                return sllResp;
            }

            IdentityInfoOP identityInfoOP = new IdentityInfoOP();
            identityInfoOP.setTrueName(bindInfo.getName());
            identityInfoOP.setCardNo(bindInfo.getCardNo());
            identityInfoOP.setIdNo(bindInfo.getIdNo());
            identityInfoOP.setProtocolNo(bindCardResult.getBindId());
            identityInfoOP.setUserId(userId);
            identityInfoOP.setSource("4");
            identityInfoOP.setBankCode(bindCardResult.getBankCode());
            identityInfoOP.setProductId("XJD");
            identityInfoOP.setAccount(bindInfo.getMobile());
            identityInfoOP.setBankMobile(bindInfo.getMobile());

            int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
            if (saveRz == 0) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("系统异常");
                // bankVerifyVO.setDealResult("0");// 0-绑卡失败
                // sllResp.setData(bankVerifyVO);
                return sllResp;
            }
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            // bankVerifyVO.setDealResult("1");
            // sllResp.setData(bankVerifyVO);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            // bankVerifyVO.setDealResult("0");// 0-绑卡失败
            // sllResp.setData(bankVerifyVO);
            log.error("【奇虎360-用户验证银行卡接口】异常orderNo={}", cardBindOP.getOrderNo(), e);
        }
        return sllResp;
    }

    @Override
    public ConclusionPullVO conclusionPull(String orderNo) {
        return sllStatusFeedBackService.pullConclusion(orderNo);
    }

    @Override
    public SLLResp conclusionConfirm(ConclusionConfirmOP conclusionConfirmOP) {
        SLLResp sllResp = new SLLResp();
        try {
            String applyId = this.getApplyId(conclusionConfirmOP.getOrderNo());
            ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(loanApply.getMobile());
            if (custUserVO.getCardNo() == null) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("未绑卡");
                return sllResp;
            }
            Integer status = loanApply.getStatus();
            if (!status.equals(XjdLifeCycle.LC_CASH_4) && !status.equals(XjdLifeCycle.LC_CHANNEL_0)) {
                boolean flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
                if (flag) {
                    sllResp.setCode(SLLResp.SUCCESS);
                    sllResp.setMsg("SUCCESS");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
        }
        return sllResp;
    }

    @Override
    public OrderTrialVO orderTrial(OrderTrialOP orderTrialOP) {
        OrderTrialVO vo = new OrderTrialVO();

        String applyId = this.getApplyId(orderTrialOP.getOrderNo());
        // LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        if (loanApply == null) {
            throw new RuntimeException("用户数据异常，订单尚未生成");
        }

        LoanPromotionCaseVO promotionCase = promotionCaseService.getById(loanApply.getPromotionCaseId());
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        promotionCaseOP.setApplyAmt(loanApply.getApplyAmt());
        promotionCaseOP.setApplyTerm(loanApply.getApplyTerm());
        promotionCaseOP.setProductId(loanApply.getProductId());
        promotionCaseOP.setChannelId(promotionCase.getChannelId());

        // CostingResultVO costingResultVO =
        // promotionCaseService.Costing(promotionCaseOP);
        // vo.setReceiveAmount(costingResultVO.getToAccountAmt());
        BigDecimal interest = CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
                loanApply.getApproveTerm());
        vo.setReceiveAmount(CostUtils.sub(loanApply.getApproveAmt(),loanApply.getServFee()));
        vo.setServiceFee(loanApply.getServFee());
        vo.setPayAmount(CostUtils.add(loanApply.getApproveAmt(), interest));

        String feeDesc = "是有%s元本金，%s元利息";
        vo.setFeeDesc(String.format(feeDesc, loanApply.getApproveAmt(), interest));
        vo.setConfirmDesc("");
        if (loanApply.getTerm() > 1) {
            // 多期产品
            List<BigDecimal> perioAmountList = new ArrayList<>();
            RepayPlanOP repayPlanOP = new RepayPlanOP();
            repayPlanOP.setApplyAmt(promotionCaseOP.getApplyAmt());
            repayPlanOP.setProductId(promotionCaseOP.getProductId());
            repayPlanOP.setRepayTerm(promotionCaseOP.getApplyTerm());
            repayPlanOP.setChannelId(promotionCaseOP.getChannelId());
            repayPlanOP.setRepayMethod(loanApply.getRepayMethod());
            Map<String, Object> repayPlanMap = loanRepayPlanService.getRepayPlan(repayPlanOP);
            Date repayDate = new Date();
            if (!repayPlanMap.isEmpty()) {
                List<Map<String, Object>> repayPlanDetails = (List<Map<String, Object>>) repayPlanMap.get("list");
                for (int i = 0; i < repayPlanDetails.size(); i++) {
                    Map<String, Object> repayPlanDetailMap = repayPlanDetails.get(i);
                    perioAmountList.add((BigDecimal) repayPlanDetailMap.get("repayAmt"));
                }
            }
            vo.setPeriodAmount(perioAmountList);
        }

        return vo;
    }

    @Override
    public RepaymentPlanPullVO repaymentPlanPull(String orderNo) {
        return sllStatusFeedBackService.pullRepaymentPlan(orderNo);
    }

    @Override
    public List<OrderRepayplanDetailVO> repayplanDetail(RepayplanDetailOP repayplanDetailOP) {
        String periodNos = repayplanDetailOP.getPeriodNos();
        if (StringUtils.isBlank(periodNos)) {
            return null;
        }
        String[] split = periodNos.split(",");
        return sllStatusFeedBackService.pullPaymentPlanDetail(repayplanDetailOP.getOrderNO(), Arrays.asList(split));
    }

    @Override
    public SLLResp orderRepay(OrderRepayOP orderRepayOP) {
        SLLResp sllResp = new SLLResp();
        // PaymentReqVO paymentReqVO = new PaymentReqVO();
        String orderNo = orderRepayOP.getOrderNo();
        String lockKey = Global.JBD_PAY_LOCK + orderNo;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            String applyId = this.getApplyId(orderNo);
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            String repayPlanItemId = loanApplySimpleVO.getRepayPlanItemId();
            // 根据orderId防并发加锁
            boolean lock = JedisUtils.setLock(lockKey, requestId, 60);
            if (!lock) {
                log.warn("【奇虎360】协议直接支付接口调用中，orderNo= {}", orderNo);
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("还款处理中，请勿重复操作");
                // paymentReqVO.setNeedConfirm("0");// 是否会发送验证码 0=不发送验证码,1=发送验证码
                // paymentReqVO.setDealResult("0");// 还款申请结果1-申请成功，0-申请失败
                // paymentReqVO.setReason("还款处理中，请勿重复操作");
                // paymentReqVO.setTransactionid(repayPlanItemId);
                // sllResp.setData(paymentReqVO);
                return sllResp;
            }

            Long payCount = repayLogService.countPayingByRepayPlanItemId(repayPlanItemId);
            if (payCount != 0) {
                log.error("代扣数据异常，订单正在处理中：{}", repayPlanItemId);
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("还款处理中，请勿重复操作");
                // paymentReqVO.setNeedConfirm("0");// 是否会发送验证码 0=不发送验证码,1=发送验证码
                // paymentReqVO.setDealResult("0");// 还款申请结果1-申请成功，0-申请失败
                // paymentReqVO.setReason("还款处理中，请勿重复操作");
                // paymentReqVO.setTransactionid(repayPlanItemId);
                // sllResp.setData(paymentReqVO);
                return sllResp;
            }

            RePayOP rePayOP = new RePayOP();
            rePayOP.setApplyId(applyId);
            rePayOP.setUserId(loanApplySimpleVO.getUserId());
            rePayOP.setRepayPlanItemId(repayPlanItemId);
            rePayOP.setTxAmt(loanApplySimpleVO.getCurToltalRepayAmt().toString());
            rePayOP.setPayType(1);
            rePayOP.setPrePayFlag(RePayOP.PREPAY_FLAG_NO);
            rePayOP.setSource(loanApplySimpleVO.getSource());
            rePayOP.setIp(loanApplySimpleVO.getIp());
            ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPay(rePayOP);
            if (confirmAuthPayVO.isSuccess()) {
                sllResp.setCode(SLLResp.SUCCESS);
                sllResp.setMsg("还款成功");
                /*
                 * paymentReqVO.setNeedConfirm("0");
				 * paymentReqVO.setDealResult("1");
				 * paymentReqVO.setTransactionid(repayPlanItemId);
				 * sllResp.setData(paymentReqVO);
				 */
            } else if ("I".equals(confirmAuthPayVO.getStatus())) {
                sllResp.setCode(SLLResp.SUCCESS);
                sllResp.setMsg("还款处理中，请稍后查询");
                /*
                 * paymentReqVO.setNeedConfirm("0");
				 * paymentReqVO.setDealResult("1");
				 * paymentReqVO.setTransactionid(repayPlanItemId);
				 * sllResp.setData(paymentReqVO);
				 */
            } else {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg(confirmAuthPayVO.getMsg());
                /*
                 * paymentReqVO.setNeedConfirm("0");
				 * paymentReqVO.setDealResult("1");
				 * paymentReqVO.setTransactionid(repayPlanItemId);
				 * sllResp.setData(paymentReqVO);
				 */
            }
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("还款异常");
            sllResp.setData("");
            log.error("【奇虎360-用户还款接口】异常orderNo={}", orderRepayOP.getOrderNo(), e);
        } finally {
            // 解除orderId并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
        return sllResp;
    }

    @Override
    public OrderStatusPullVO orderStatusPull(String orderNo) {
        return sllStatusFeedBackService.pullOrderStatus(orderNo);
    }

    private String registerOrReturnUserId(String userPhone, String channelId) {
        String userId = "";
        if (!custUserService.isRegister(userPhone)) {
            RegisterOP registerOP = new RegisterOP();
            registerOP.setAccount(userPhone);
            String password = XianJinCardUtils.setData(4);
            registerOP.setPassword(XianJinCardUtils.pwdToSHA1(String.valueOf(password)));
            registerOP.setChannel(channelId);
            userId = custUserService.saveRegister(registerOP);
            // sendMsg(password, userId, userPhone);
        } else {
            CustUserVO custUserVO = custUserService.getCustUserByMobile(userPhone);
            userId = custUserVO.getId();
        }
        return userId;
    }

    private SLLResp getIsUserAcceptCache(String key) {
        SLLResp sllResp = new SLLResp();
        if (StringUtils.isNotBlank(key)) {
            return (SLLResp) JedisUtils.getObject("SLL:IsUserAccept_" + key);
        }
        return sllResp;
    }

    private SLLResp setIsUserAcceptCache(SLLResp sllResp, String key) {
        if (null != sllResp) {
            JedisUtils.setObject("SLL:IsUserAccept_" + key, sllResp, 30);
        }
        return sllResp;
    }

    public TaskResult saveUserAndApplyInfo() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int failNum = 0;
        Map<String, String> thirdKey = JedisUtils.getMap(Global.SLL_THIRD_KEY);
        if (thirdKey != null) {
            List<Map.Entry<String, String>> thirdKeyList = new ArrayList<>(thirdKey.entrySet());
            Collections.sort(thirdKeyList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    return map1.getValue().compareTo(map2.getValue());
                }
            });
            int limit = thirdKeyList.size() >= 200 ? 200 : thirdKeyList.size();
            List<Map.Entry<String, String>> thirdListLimit = thirdKeyList.subList(0, limit);
            for (Map.Entry<String, String> map : thirdListLimit) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String orderSn = map.getKey();
                if (Long.parseLong(map.getValue()) <= (System.currentTimeMillis() - 1000 * 60 * 2)
                        && Long.parseLong(map.getValue()) >= (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    BaseData base = getPushBaseData(orderSn);
                    AddData additional = getPushAdditionalData(orderSn);
                    if (base != null && additional != null) {
                        try {
                            String userPhone = base.getOrderinfo().getUserMobile();
                            String ip = "127.0.0.1";
                            String channelId = "SLLAPI";
                            String productId = base.getOrderinfo().getProductId();
                            if (Global.SLL_JQB_PRODUCTID.equals(productId)) {
                                channelId = "SLLAPI";
                            } else if (Global.SLL_JHH_PRODUCTID.equals(productId)) {
                                channelId = "SLLAPIJHH";
                            }
                            String userId = registerOrReturnUserId(userPhone,channelId);
                            if (!loanApplyService.isExistUnFinishLoanApply(userId)) {
                                int data = fileserverService.updateUserIdByOrderSn(userId, orderSn,
                                        "sll_addition_data");
                                saveDoOcr(base, additional, userId);
                                saveBaseInfo(base, additional, userId, channelId);
                                saveRz(userId, ip);
                                saveReportData(additional, base);
                                saveLoanApply(additional, base, userId,channelId);
                            }
                            JedisUtils.mapRemove(Global.SLL_THIRD_KEY, orderSn);
                            succNum++;
                        } catch (Exception e) {
                            JedisUtils.mapRemove(Global.SLL_THIRD_KEY, orderSn);
                            failNum++;
                            logger.error("SLL三方工单转化失败:{} 手工处理", orderSn);
                            e.printStackTrace();
                        }
                    } else {
                        Map<String, String> rePush = Maps.newHashMap();
                        rePush.put(orderSn, String.valueOf(System.currentTimeMillis()));
                        JedisUtils.mapPut(Global.SLL_THIRD_KEY, rePush);
                    }
                } else if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    // JedisUtils.mapRemove(Global.XJBK_THIRD_KEY, orderSn);
                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(failNum);
        return taskResult;
    }

    @Override
    public BaseData getPushBaseData(String orderSn) {
        String cacheKey = "SLL:PUSH_BASE_" + orderSn;
        BaseData vo = (BaseData) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastSLLBaseByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "SLL", "从文件获取用户基础信息", fileInfoVO.getUrl());
                    vo = (BaseData) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), BaseData.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "SLL", "从文件获取用户基础信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public AddData getPushAdditionalData(String orderSn) {
        String cacheKey = "SLL:PUSH_ADDITIONAL_" + orderSn;
        AddData vo = (AddData) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
               FileInfoVO fileInfoVO = custUserService.getLastSLLAdditionalByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "SLL", "从文件获取用户附加信息", fileInfoVO.getUrl());
                    vo = (AddData) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), AddData.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "SLL", "从文件获取用户附加信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    private void saveDoOcr(BaseData base, AddData appendInfo, String userId) {
        try {
            String homeAreas = appendInfo.getIdAddressOcr();
            String faceRecognitionPicture = appendInfo.getPhotoAssay().get(0);
            String zPicture = appendInfo.getIdPositive().get(0);
            String fPicture = null;
            if (appendInfo.getIdNegative() != null) {
                fPicture = appendInfo.getIdNegative().get(0);
            }
            String ocrName = appendInfo.getNameOcr();
            String ocrAddress = appendInfo.getIdAddressOcr();
            String ocrIdNumber = appendInfo.getIdNumberOcr();
            String ocrIssuedBy = appendInfo.getIdIssueOrgOcr();
            String ocrSex = appendInfo.getIdSexOcr();
            String ocrRace = appendInfo.getIdEthnicOcr();
            String ocrEndTime = appendInfo.getIdDueTimeOcr();
            Date date;
            if (ocrEndTime.equals("长期")) {
                date = DateUtils.parse("20991212", "yyyyMMdd");
            } else {
                date = DateUtils.parse(ocrEndTime, "yyyyMMdd");
            }
            ocrEndTime = DateUtils.formatDate(date, "yyyy-MM-dd");
            String ocrStartTime = "2018-10-24";

            String faceRecognitionPictureBase64 = ImageUtil.getURLImage(faceRecognitionPicture);
            String zPictureBase64 = ImageUtil.getURLImage(zPicture);
            String fPictureBase64 = ImageUtil.getURLImage(fPicture);
            UploadParams faceImage = new UploadParams();
            faceImage.setUserId(userId);
            faceImage.setIp("127.0.0.1");
            faceImage.setSource("4");
            faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
            uploadBase64Image(faceRecognitionPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
            uploadBase64Image(zPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
            uploadBase64Image(fPictureBase64, faceImage);
            OcrOP ocrOP = new OcrOP();
            ocrOP.setName(ocrName);
            ocrOP.setAddress(ocrAddress);
            ocrOP.setIdcard(ocrIdNumber);
            ocrOP.setAuthority(ocrIssuedBy);
            ocrOP.setSex(ocrSex);
            ocrOP.setNation(ocrRace);
            ocrOP.setValidDate(ocrStartTime.replace("-", ".") + "-" + ocrEndTime.replace("-", "."));
            ocrOP.setUserId(userId);
            ocrOP.setSource("4");
            int saveDoOcrResult = custUserService.saveDoOcr(ocrOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBaseInfo(BaseData base, AddData additional, String userId, String channelId) {
        String userPhone = base.getApplydetail().getPhoneNumberHouse();
        String userName = base.getApplydetail().getBureauUserName();
        String userIdcard = base.getApplydetail().getUserId();
        String degree = RongUtils.convertDegree(base.getApplydetail().getUserEducation());
        String homeAddress = additional.getIdAddressOcr();

        String relation = additional.getContact1aRelationship();
        String mobile = additional.getContact1aNumber();
        String name = filterEmoji(additional.getContact1aName());
        String relation1 = RongUtils.convertARelation(relation);
        String inRelation = relation1 + "," + name + "," + mobile;

        String relationSpare = additional.getEmergencyContactPersonaRelationship();
        String nameSpare = filterEmoji(additional.getEmergencyContactPersonaName());
        String mobileSpare = additional.getEmergencyContactPersonaPhone();
        String relationSpare1 = RongUtils.convertBRelation(relationSpare);
        String inRelationSpare = relationSpare1 + "," + nameSpare + "," + mobileSpare;

        String qq = "";
        BaseInfoOP baseInfoOP = new BaseInfoOP();
        String companyName = additional.getCompanyName();
        String address = additional.getCompanyAddrDetail();
        if (address.length() >= 180) {
            address = address.substring(0, 180);
        }
        String tel = additional.getCompanyNumber();
        String[] telSplit = tel.split("-");
        baseInfoOP.setUserName(userName);
        baseInfoOP.setMobile(userPhone);
        baseInfoOP.setIdNo(userIdcard);
        baseInfoOP.setIdType("0");
        baseInfoOP.setUserId(userId);
        baseInfoOP.setComName(companyName);
        baseInfoOP.setWorkAddr(address);
        baseInfoOP.setComTelZone(telSplit.length >= 1 ? telSplit[0] : "");
        baseInfoOP.setComTelNo(telSplit.length >= 2 ? telSplit[1] : "");
        baseInfoOP.setComTelExt(telSplit.length >= 3 ? telSplit[2] : "");
        baseInfoOP.setDegree(degree);
        baseInfoOP.setResideAddr(homeAddress);
        baseInfoOP.setContactParent(inRelation);
        baseInfoOP.setContactFriend(inRelationSpare);
        baseInfoOP.setQq(qq);
        baseInfoOP.setChannelId(channelId);
        int saveRz = custUserService.saveBaseInfo(baseInfoOP);
    }

    private void saveRz(String userId, String ip) {
        loanApplyService.saveOperationLog(userId, ip);
    }

    private SaveApplyResultVO saveLoanApply(AddData additional, BaseData base, String userId,String channelId) {
        String orderSn = additional.getOrderNo();

        String flag = configService.getValue(Global.SLL_PRODUCT_FLAG);
        /** 贷款成功次数 */
        int loanSuccCount = loanApplyService.countOverLoanByRepay(userId);
        int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
        if (loanSuccCount > 0 && maxOverdueDays <= 5){
            // 复贷用户审批28天4期
            flag = "1";
        }
        PromotionCaseOP promotionCaseOP = CpsUtil.getPromotionCase(flag);
        LoanApplyOP loanApplyOP = new LoanApplyOP();
        loanApplyOP.setProductId(promotionCaseOP.getProductId());
        loanApplyOP.setUserId(userId);
        loanApplyOP.setApplyAmt(promotionCaseOP.getApplyAmt());
        loanApplyOP.setApplyTerm(promotionCaseOP.getApplyTerm());
        loanApplyOP.setSource("4");
        if ("XJD".equals(promotionCaseOP.getProductId())) {
            loanApplyOP.setProductType("0");
        } else if ("XJDFQ".equals(promotionCaseOP.getProductId())) {
            loanApplyOP.setProductType("4");
        }
        cleanCustUserInfoCache(userId);
        SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
        if (!isExistApplyId(rz.getApplyId())) {
            int r = loanApplyService.updateChannel(rz.getApplyId(), channelId);
            int saveTripartiteOrderResult = insertTripartiteOrder(rz.getApplyId(), orderSn);
        }
        return rz;
    }

    public static String filterEmoji(String source) {
        if (!StringUtils.isEmpty(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        } else {
            return source;
        }
    }

    public static FileVO uploadBase64Image(String base64Image, UploadParams params) {
        // 图片上传
        FileServerClient fileServerClient = new FileServerClient();
        String rz = fileServerClient.uploadBase64Image(base64Image, params);
        FileResult obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null == obj || !StringUtils.equals(obj.getCode(), "SUCCESS")) {
            rz = fileServerClient.uploadBase64Image(base64Image, params);
        }
        obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null != obj && StringUtils.equals(obj.getCode(), "SUCCESS") && null != obj.getData()) {
            return obj.getData();
        }
        return null;
    }

    public static long cleanCustUserInfoCache(String userId) {
        long result = 0;
        if (StringUtils.isNotBlank(userId)) {
            // 缓存用户信息
            result = JedisUtils.delObject(Global.USER_CACHE_PREFIX + userId);
        } else {

        }
        return result;
    }

    @Override
    public boolean isExistApplyId(String applyId) {
        if (applyId == null) {
            applyId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        long count = applyTripartiteSllManager.countByCriteria(criteria);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int insertTripartiteOrder(String applyId, String orderSn) {
        ApplyTripartiteSll applyTripartiteJdq = new ApplyTripartiteSll();
        applyTripartiteJdq.setApplyId(applyId);
        applyTripartiteJdq.setTripartiteNo(orderSn);
        return applyTripartiteSllManager.insert(applyTripartiteJdq);
    }

    @Override
    public JDQReport getReportData(String orderSn) {
        String cacheKey = "SLL:PUSH_REPORT_" + orderSn;
        JDQReport vo = (JDQReport) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastSLLReportByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "SLL", "从文件获取用户报告信息", fileInfoVO.getUrl());
                    vo = (JDQReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), JDQReport.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户报告信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public void saveReportData(AddData addData, BaseData baseData) {
        Map<String, Object> result = new HashMap<>();
        Mobile mobile = baseData.getAddinfo().getMobile();
        User user = mobile.getUser();
        Basic basic = new Basic();
        basic.setCellPhone(user.getPhone());
        basic.setIdcard(user.getIdCard());
        basic.setRealName(user.getRealName());
        basic.setRegTime(user.getRegTime());

        List<Bill> billList = mobile.getBill();
        List<Transactions> transactions = new ArrayList<>();
        if (billList != null) {
            for (Bill bill : billList) {
                Transactions transactions1 = new Transactions();
                transactions1.setBillCycle(bill.getMonth());
                transactions1.setPayAmt(bill.getCallPay() / 100);
                transactions1.setPlanAmt(bill.getPackageFee() / 100);
                transactions1.setTotalAmt(bill.getCallPay() / 100);
                transactions.add(transactions1);
            }
        }
        if (transactions != null) {
            Transactions sum = new Transactions();
            for (Transactions t : transactions) {
                sum.setPayAmt(t.getPayAmt() + sum.getPayAmt());
                sum.setPlanAmt(t.getPlanAmt() + sum.getPlanAmt());
                sum.setTotalAmt(t.getTotalAmt() + sum.getTotalAmt());
            }
            Transactions avg = new Transactions();
            int size = transactions.size() == 0 ? 1 : transactions.size();
            avg.setPayAmt(sum.getPayAmt() / size);
            avg.setTotalAmt(sum.getTotalAmt() / size);
            avg.setPlanAmt(sum.getPlanAmt() / size);
            avg.setBillCycle("平均");
            transactions.add(avg);
            Collections.sort(transactions, new Comparator<com.rongdu.loans.loan.option.jdq.Transactions>() {
                @Override
                public int compare(com.rongdu.loans.loan.option.jdq.Transactions t1,
                                   com.rongdu.loans.loan.option.jdq.Transactions t2) {
                    return t1.getBillCycle().compareTo(t2.getBillCycle());
                }
            });
        }

        List<PhoneList> phoneList = addData.getContacts().getPhoneList();
        List<AddressBook> addressBookList = new ArrayList<>();
        if (phoneList != null) {
            for (PhoneList phoneList1 : phoneList) {
                AddressBook addressBook = new AddressBook();
                addressBook.setMobile(phoneList1.getPhone());
                addressBook.setName(phoneList1.getName());
                addressBookList.add(addressBook);
            }
        }

        List<Teldata> teldataList = mobile.getTel().getTeldata();
        List<Calls> callsList = new ArrayList<>();
        for (Teldata teldata : teldataList) {
            Calls calls = new Calls();
            calls.setOtherCellPhone(teldata.getReceivePhone());
            calls.setCellPhone(baseData.getOrderinfo().getUserMobile());
            calls.setInitType("1".equals(teldata.getCallType()) ? "主叫" : "被叫");
            calls.setUseTime(teldata.getTradeTime());
            calls.setStartTime(teldata.getCallTime());
            calls.setPlace(teldata.getTradeAddr());
            callsList.add(calls);
        }

        Map<String, Count> zj = Maps.newHashMap();
        Map<String, Count> bj = Maps.newHashMap();
        int callAtNight = 0;
        int callAtDay = 0;
        int call110 = 0;
        List<String> stringList = Lists.newArrayList();
        for (com.rongdu.loans.loan.option.jdq.Calls calls : callsList) {
            stringList.add(calls.getStartTime());
            int hour = Integer.parseInt(calls.getStartTime().substring(11, 13));
            if (hour >= 23 || hour < 6) {
                callAtNight++;
            }
            if (hour < 23 && hour > 6) {
                callAtDay++;
            }
            if (calls.getOtherCellPhone().equals("110")) {
                call110++;
            }
            bj = CpsUtil.CallCount("被叫", calls, bj);
            zj = CpsUtil.CallCount("主叫", calls, zj);
        }
        BigDecimal count = new BigDecimal(callAtNight + callAtDay);
        BigDecimal bl = new BigDecimal(callAtNight).divide(count, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));
        Set<String> bjSet = bj.keySet();
        Set<String> zjSet = zj.keySet();
        Set<String> countSet = Sets.newHashSet();
        countSet.addAll(bjSet);
        countSet.retainAll(zjSet);
        int countCall = countSet.size();
        countSet.clear();
        countSet.addAll(bjSet);
        countSet.addAll(zjSet);
        int countAll = countSet.size();
        BigDecimal bigDecimal = new BigDecimal(countCall).divide(new BigDecimal(countAll), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));
        int days1 = 0;
        int days3 = 0;
        List<String> listNew = new ArrayList<>(new TreeSet<>(stringList));
        for (int i = 0; i < listNew.size() - 1; i++) {
            Date start = DateUtils.parse(listNew.get(i));
            Date end = DateUtils.parse(listNew.get(i + 1));
            int days = DateUtils.daysBetween(start, end) - 1;
            if (days == 1) {
                days1++;
            }
            if (days > 2) {
                days3++;
            }
        }
        Date start = DateUtils.parse(listNew.get(0));
        Date end = DateUtils.parse(listNew.get(listNew.size() - 1));
        int days = DateUtils.daysBetween(start, end);
        List<ContactList> listbj = Lists.newArrayList();
        for (String k : bj.keySet()) {
            ContactList contactList = new ContactList();
            contactList.setCallInCnt(bj.get(k).getCount());
            contactList.setCallInLen(bj.get(k).getSum());
            contactList.setPhoneNum(k);
            contactList.setPhoneNumLoc(bj.get(k).getGsd());
            contactList.setLastCall(bj.get(k).getLastCall());
            contactList.setFirstCall(bj.get(k).getFirstCall());
            listbj.add(contactList);
        }
        List<ContactList> listzj = Lists.newArrayList();
        for (String k : zj.keySet()) {
            ContactList contactList = new ContactList();
            contactList.setCallOutCnt(zj.get(k).getCount());
            contactList.setCallOutLen(zj.get(k).getSum());
            contactList.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(zj.get(k).getSum()).longValue()));
            contactList.setPhoneNum(k);
            contactList.setPhoneNumLoc(zj.get(k).getGsd());
            contactList.setLastCall(zj.get(k).getLastCall());
            contactList.setFirstCall(zj.get(k).getFirstCall());
            listzj.add(contactList);
        }
        List<ContactList> calInCntList = CpsUtil.getTopCalInCntList(listbj, 10);
        List<ContactList> callOutCntList = CpsUtil.getTopCallOutCntList(listzj, 10);
        List calInCntListV = CpsUtil.jdqContactMatch(calInCntList, addressBookList);
        List callOutCntListV = CpsUtil.jdqContactMatch(callOutCntList, addressBookList);
        UserContact contactInfo = new UserContact();

        contactInfo.setName(addData.getContact1aName());
        contactInfo.setMobile(addData.getContact1aNumber());
        contactInfo.setRelation(addData.getContact1aRelationship());

        contactInfo.setNameSpare(addData.getEmergencyContactPersonaName());
        contactInfo.setMobileSpare(addData.getEmergencyContactPersonaPhone());
        contactInfo.setRelationSpare(addData.getEmergencyContactPersonaRelationship());

        List<JDQUrgentContact> urgentContactArrayList = new ArrayList<>();
        JDQUrgentContact urgentContact = new JDQUrgentContact();
        urgentContact.setMobile(contactInfo.getMobile());
        urgentContact.setName(contactInfo.getName());
        urgentContact.setRelation(SLLUtil.Relation(contactInfo.getRelation()));
        for (ContactList contactList1 : listbj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobile())) {
                urgentContact.setFirstCallIn(contactList1.getFirstCall());
                urgentContact.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
                urgentContact.setLastCallIn(contactList1.getLastCall());
                urgentContact.setCallInCnt(contactList1.getCallInCnt());
                urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        for (ContactList contactList1 : listzj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobile())) {
                urgentContact.setFirstCallOut(contactList1.getFirstCall());
                urgentContact.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
                urgentContact.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
                urgentContact.setLastCallOut(contactList1.getLastCall());
                urgentContact.setCallOutCnt(contactList1.getCallOutCnt());
                urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact);
        JDQUrgentContact urgentContact1 = new JDQUrgentContact();
        urgentContact1.setMobile(contactInfo.getMobileSpare());
        urgentContact1.setName(contactInfo.getNameSpare());
        urgentContact1.setRelation(SLLUtil.Relation(contactInfo.getRelationSpare()));
        for (ContactList contactList1 : listbj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobileSpare())) {
                urgentContact1.setFirstCallIn(contactList1.getFirstCall());
                urgentContact1.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
                urgentContact1.setLastCallIn(contactList1.getLastCall());
                urgentContact1.setCallInCnt(contactList1.getCallInCnt());
                urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        for (ContactList contactList1 : listzj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobileSpare())) {
                urgentContact1.setFirstCallOut(contactList1.getFirstCall());
                urgentContact1.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
                urgentContact1.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
                urgentContact1.setLastCallOut(contactList1.getLastCall());
                urgentContact1.setCallOutCnt(contactList1.getCallOutCnt());
                urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact1);
        String regTime = basic.getRegTime();
        Date date = DateUtils.parseYYMMdd(regTime);
        int month = DateUtils.getMonth(date, new Date());

        List<ContactCheck> contactChecks = new ArrayList<>();
        Map<String, Count> countMap = Maps.newHashMap();
        for (com.rongdu.loans.loan.option.jdq.Calls calls : callsList) {
            Count object = countMap.get(calls.getOtherCellPhone());
            if (object == null) {
                Count count1 = new Count();
                count1.setCount(1);
                count1.setSum(calls.getUseTime());
                countMap.put(calls.getOtherCellPhone(), count1);
            } else {
                object.setCount(object.getCount() + 1);
                object.setSum(object.getSum() + calls.getUseTime());
                countMap.put(calls.getOtherCellPhone(), object);
            }
        }
        for (AddressBook addressBook : addressBookList) {
            ContactCheck contactCheck = new ContactCheck();
            String contactMobile = addressBook.getMobile();
            String contactName = addressBook.getName();
            contactCheck.setMobile(contactMobile);
            contactCheck.setName(contactName);
            contactCheck.setCallCnt(0);
            contactCheck.setCallLen(0);
            for (String k : countMap.keySet()) {
                if (addressBook.getMobile().equals(k)) {
                    contactCheck.setCallCnt(countMap.get(k).getCount());
                    contactCheck.setCallLen(countMap.get(k).getSum());
                }
            }
            contactChecks.add(contactCheck);
        }
        Collections.sort(contactChecks);

        JDQReport jdqReport = new JDQReport();
        jdqReport.setCallatnight(callAtNight);
        jdqReport.setBl(bl);
        jdqReport.setDays(days);
        jdqReport.setDays1(days1);
        jdqReport.setDays3(days3);
        jdqReport.setCall110(call110);
        jdqReport.setCountcall(countCall);
        jdqReport.setBigdecimal(bigDecimal);
        jdqReport.setBjsize(listbj.size());
        jdqReport.setZjsize(listzj.size());
        jdqReport.setMonth(month);
        jdqReport.setCalincntlistv(calInCntListV);
        jdqReport.setCalloutcntlistv(callOutCntListV);
        jdqReport.setUrgentcontact(urgentContactArrayList);
        jdqReport.setTransactions(transactions);
        jdqReport.setBasic(basic);
        jdqReport.setContactCheckList(contactChecks);
        saveReport(jdqReport, baseData);
    }

    public boolean saveReport(JDQReport jdqReport, BaseData intoOrder) {
        boolean flag = false;
        try {
            String userPhone = intoOrder.getOrderinfo().getUserMobile();
            String channelId = "SLLAPI";
            String productId = intoOrder.getOrderinfo().getProductId();
            if (productId == Global.SLL_JQB_PRODUCTID) {
                channelId = "SLLAPI";
            } else if (productId == Global.SLL_JHH_PRODUCTID) {
                channelId = "SLLAPIJHH";
            }
            String userId = registerOrReturnUserId(userPhone,channelId);
            FileInfoVO fileInfoVO = custUserService.getLastJDQReportByOrderSn(intoOrder.getOrderinfo().getOrderNo());
            if (fileInfoVO == null) {
                String res = CpsUtil.uploadBaseData(jdqReport, FileBizCode.SLL_REPORT_DATA.getBizCode(), userId,
                        intoOrder.getOrderinfo().getOrderNo());
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public TaskResult approveFeedbackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> sllCallBackMap = JedisUtils.getMap(Global.SLL_APPROVE_FEEDBACK);
            if (sllCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(sllCallBackMap.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(2000);
                        if (count >= 200) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderNo = this.getOrderNo(applyId);
                        if (orderNo != null) {
                            log.info("----------【奇虎360-审批结论，订单状态推送】定时任务applyId={},orderNo={}----------", applyId,
                                    orderNo);
                            boolean result = sllStatusFeedBackService.approveFeedBack(applyId);
                            if (result) {
                                JedisUtils.mapRemove(Global.SLL_APPROVE_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.SLL_APPROVE_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("----------【奇虎360-审批结论，订单状态推送】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult lendFeedbackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> sllCallBackMap = JedisUtils.getMap(Global.SLL_LEND_FEEDBACK);
            if (sllCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(sllCallBackMap.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(2000);
                        if (count >= 200) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderNo = this.getOrderNo(applyId);
                        if (orderNo != null) {
                            log.info("----------【奇虎360-订单状态，还款计划推送】定时任务applyId={},orderNo={}----------", applyId,
                                    orderNo);
                            boolean result = sllStatusFeedBackService.lendFeedBack(applyId);
                            if (result) {
                                JedisUtils.mapRemove(Global.SLL_LEND_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.SLL_LEND_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("----------【奇虎360-订单状态，还款计划推送】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult settlementFeedbackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> dwdCallBackMap = JedisUtils.getMap(Global.SLL_SETTLEMENT_FEEDBACK);
            if (dwdCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(dwdCallBackMap.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(2000);
                        if (count >= 200) {
                            break;
                        }
                        count++;
                        String repayPlanItemId = map.getKey();
                        String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
                        String orderNo = this.getOrderNo(applyId);
                        if (orderNo != null) {
                            log.info("----------【奇虎360-订单状态，还款状态，还款计划推送】定时任务applyId={},orderNo={},"
                                    + "repayPlanItemId={}----------", applyId, orderNo, repayPlanItemId);
                            boolean result = sllStatusFeedBackService.settlementFeedBack(applyId, repayPlanItemId);
                            if (result) {
                                JedisUtils.mapRemove(Global.SLL_SETTLEMENT_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.SLL_SETTLEMENT_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("----------【奇虎360-订单状态，还款状态，还款计划推送】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult orderStatusFeedbackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> sllCallBackMap = JedisUtils.getMap(Global.SLL_ORDERSTATUS_FEEDBACK);
            if (sllCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(sllCallBackMap.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(2000);
                        if (count >= 200) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderNo = this.getOrderNo(applyId);
                        if (orderNo != null) {
                            log.info("----------【奇虎360-订单状态推送】定时任务applyId={},orderNo={}----------", applyId, orderNo);
                            boolean result = sllStatusFeedBackService.orderfeedback(orderNo, true);
                            if (result) {
                                JedisUtils.mapRemove(Global.SLL_ORDERSTATUS_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.SLL_ORDERSTATUS_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("----------【奇虎360-订单状态推送】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public String resetImage(String orderNo) {
        try {
            logger.info("===============开始================{}", orderNo);
            BaseData base = getPushBaseData(orderNo);
            AddData additional = getPushAdditionalData(orderNo);
            String userPhone = base.getOrderinfo().getUserMobile();
            String channelId = "SLLAPI";
            String productId = base.getOrderinfo().getProductId();
            if (productId == Global.SLL_JQB_PRODUCTID) {
                channelId = "SLLAPI";
            } else if (productId == Global.SLL_JHH_PRODUCTID) {
                channelId = "SLLAPIJHH";
            }
            String userId = registerOrReturnUserId(userPhone,channelId);
            saveDoOcr(base, additional, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "修复失败";
        }
        logger.info("==============结束================{}", orderNo);
        return "修复成功";
    }

    @Override
    public String createAccount(String url, Map<String, String> params, Map<String, String> headerMap, String payChannel) {
        String result = HttpClient.postForPair(url, params, headerMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String msg = null;
        if (jsonObject != null && jsonObject.getString("msg") != null) {
            msg = jsonObject.getString("msg");
        }
        String openUrl = "http://api.jubaoqiandai.com/img/tip1.html";
        if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().equals(Integer.parseInt(payChannel))) {
            openUrl = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>聚宝钱包</title>\n" +
                    "</head>\n" +
                    "<style>\n" +
                    "    .container{\n" +
                    "        width: 100%;\n" +
                    "        height: 100%;\n" +
                    "    }\n" +
                    "    .tip{\n" +
                    "        width: 200px;\n" +
                    "        height: 200px;\n" +
                    "        background-color: #eee;\n" +
                    "        margin: 80px auto;\n" +
                    "        padding:20px 10px 0;\n" +
                    "        border-radius: 20px;\n" +
                    "        box-sizing: border-box;\n" +
                    "    }\n" +
                    "    .tipImg{\n" +
                    "        width: 80px;\n" +
                    "        height: 80px;\n" +
                    "        margin:0 auto;\n" +
                    "        background:url(http://api.jubaoqiandai.com/img/lingdang.png) no-repeat;\n" +
                    "        background-size: 100%;\n" +
                    "    }\n" +
                    "    .text{\n" +
                    "        margin-top:20px;\n" +
                    "        text-align: center;\n" +
                    "        font-size: 12px;\n" +
                    "        color:#333;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"tip\">\n" +
                    "            <div class=\"tipImg\"></div>\n" +
                    "            <div class=\"text\">请等待银行放款...</div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
        }
        if (jsonObject != null && jsonObject.getJSONObject("data") != null) {
            openUrl = jsonObject.getJSONObject("data").getString("url");
        }
        return openUrl;
    }

    @Override
    public String withdraw(String url, Map<String, String> params, Map<String, String> headerMap, String
            payChannel) {
        String result = HttpClient.postForPair(url, params, headerMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String msg = null;
        if (jsonObject != null && jsonObject.getString("msg") != null) {
            msg = jsonObject.getString("msg");
        }
        String openUrl = "http://api.jubaoqiandai.com/img/tip.html";
        if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().equals(Integer.parseInt(payChannel))) {
            openUrl = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>聚宝钱包</title>\n" +
                    "</head>\n" +
                    "<style>\n" +
                    "    .container{\n" +
                    "        width: 100%;\n" +
                    "        height: 100%;\n" +
                    "    }\n" +
                    "    .tip{\n" +
                    "        width: 200px;\n" +
                    "        height: 200px;\n" +
                    "        background-color: #eee;\n" +
                    "        margin: 80px auto;\n" +
                    "        padding:20px 10px 0;\n" +
                    "        border-radius: 20px;\n" +
                    "        box-sizing: border-box;\n" +
                    "    }\n" +
                    "    .tipImg{\n" +
                    "        width: 80px;\n" +
                    "        height: 80px;\n" +
                    "        margin:0 auto;\n" +
                    "        background:url(http://api.jubaoqiandai.com/img/lingdang.png) no-repeat;\n" +
                    "        background-size: 100%;\n" +
                    "    }\n" +
                    "    .text{\n" +
                    "        margin-top:20px;\n" +
                    "        text-align: center;\n" +
                    "        font-size: 12px;\n" +
                    "        color:#333;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"tip\">\n" +
                    "            <div class=\"tipImg\"></div>\n" +
                    "            <div class=\"text\">如已提现请查看银行卡；<br>如未提现请等待银行放款后提现</div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
        }
        if (jsonObject != null && jsonObject.getString("url") != null) {
            openUrl = jsonObject.getString("url");
        }
        return openUrl;
    }

}
