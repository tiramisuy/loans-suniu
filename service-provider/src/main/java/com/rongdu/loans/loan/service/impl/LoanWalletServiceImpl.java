package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.security.MD5Utils;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.XianJinCardUtils;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.app.vo.AppBanksVO;
import com.rongdu.loans.common.HandlerTypeEnum;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.entity.TonglianErrInfo;
import com.rongdu.loans.loan.manager.ApplyTripartiteDkqbManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.dkqb.LoanWalletVO;
import com.rongdu.loans.loan.option.loanWallet.*;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.TlWithholdQueryOP;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("loanWalletService")
public class LoanWalletServiceImpl implements LoanWalletService {

    Logger logger = LoggerFactory.getLogger(JDQServiceImpl.class);

    @Autowired
    private CustUserService custUserService;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private OverdueService overdueService;
    @Autowired
    private PromotionCaseManager promotionCaseManager;
    @Autowired
    private ApplyTripartiteDkqbManager applyTripartiteDkqbManager;
    @Autowired
    private AppBankLimitService appBankLimitService;
    @Autowired
    private TltAgreementPayService tltAgreementPayService;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;

    @Override
    public LoanWalletVO pushUser(JSONObject data) {
        return null;
    }

    @Override
    public boolean saveIntoOrder(String jsonData, String type) {
        return false;
    }

    @Override
    public LoanWalletVO isUserAccept(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        LWCheckUserOP lwCheckUserOP = JSONObject.parseObject(data.toJSONString(), LWCheckUserOP.class);
        String realName = lwCheckUserOP.getRealName();
        String mobile = lwCheckUserOP.getMobile().replace("*", "");
        String key = lwCheckUserOP.getMd5();
        LoanWalletVO loanWalletVOCache = getIsUserAcceptCache(key);
        if (loanWalletVOCache != null) {
            return loanWalletVOCache;
        }
        CustUserVO custUserVO = custUserService.isRegister(realName, mobile, null);
        if (null != custUserVO) {
            String userId = custUserVO.getId();
            // 自有黑名单
            boolean isBlackUser = riskBlacklistService.inBlackList(realName, mobile, null);
            if (isBlackUser) {
                result.put("acceptStatus", 210);
                result.put("remark", "该用户不可借款");

                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                loanWalletVO.setResult(result);
                setIsUserAcceptCache(loanWalletVO, key);
                return loanWalletVO;
            }

            // 未完成工单
            boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
            if (isExist) {
                result.put("acceptStatus", 210);
                result.put("remark", "该用户有未完成工单");

                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                loanWalletVO.setResult(result);
                setIsUserAcceptCache(loanWalletVO, key);
                return loanWalletVO;
            }

            // 在本平台有15天以上逾期记录
            int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
            if (maxOverdueDays > 15) {
                result.put("acceptStatus", 210);
                result.put("remark", "该用户存在逾期记录");

                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                loanWalletVO.setResult(result);
                setIsUserAcceptCache(loanWalletVO, key);
                return loanWalletVO;
            }

            // 被拒日期距今不满60天
            LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
            if (lastApply != null
                    && (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(lastApply.getStatus()) || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS
                    .getValue().equals(lastApply.getStatus()))) {
                Date lastUpdateTime = lastApply.getUpdateTime();
                long pastDays = DateUtils.pastDays(lastUpdateTime);
                if (pastDays < 60) {
                    result.put("acceptStatus", 210);
                    result.put("remark", "该用户上笔订单被拒未满60天");

                    loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                    loanWalletVO.setMessage("请求成功");
                    loanWalletVO.setResult(result);
                    setIsUserAcceptCache(loanWalletVO, key);
                    return loanWalletVO;
                }
            }
        }
        result.put("acceptStatus", 200);
        result.put("remark", "该用户可以借款");

        loanWalletVO.setMessage("请求成功");
        loanWalletVO.setResult(result);
        setIsUserAcceptCache(loanWalletVO, key);
        logger.debug("【贷款钱包】用户检测接口phone={},name={},响应结果={}", lwCheckUserOP.getMobile(), realName,
                JSONObject.toJSONString(loanWalletVO));
        return loanWalletVO;
    }

    @Override
    public LoanWalletVO loanCalculate(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        LWCalculateOP lwCalculateOP = JSONObject.parseObject(data.toJSONString(), LWCalculateOP.class);
        BigDecimal applyAmt = BigDecimal.valueOf(lwCalculateOP.getLoanAmount()).divide(BigDecimal.valueOf(100));
        Integer term = lwCalculateOP.getLoanTerm();
        // 查询营销方案
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        promotionCaseOP.setApplyAmt(applyAmt);
        promotionCaseOP.setApplyTerm(term);
        promotionCaseOP.setProductId(LoanProductEnum.JDQ.getId());
        promotionCaseOP.setChannelId(ChannelEnum.LoanWallet.getCode());
        PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);

        if (null != promotionCase) {
            // 服务费 (单位:分)
            BigDecimal serviceFee = applyAmt.multiply(promotionCase.getServFeeRate());
            // 实际到账金额 (单位:分)
            BigDecimal receiveAmount = applyAmt.subtract(serviceFee).multiply(BigDecimal.valueOf(100));
            // 利息 (单位:分)
            BigDecimal interestFee = applyAmt.multiply(promotionCase.getRatePerDay()).setScale(0, BigDecimal.ROUND_UP).multiply(BigDecimal.valueOf(term));
            // 应还总额 (单位:分)
            BigDecimal repayAmount = applyAmt.add(interestFee);

            result.put("repayAmount", repayAmount.intValue());
            result.put("receiveAmount", receiveAmount.intValue());
            result.put("interestFee", interestFee.intValue());
            result.put("serviceFee", serviceFee.intValue());
            result.put("serviceFeeDesc", "服务费");
            loanWalletVO.setMessage("请求成功");
        } else {
            result.put("repayAmount", 0);
            result.put("receiveAmount", 0);
            result.put("interestFee", 0);
            result.put("serviceFee", 0);
            result.put("serviceFeeDesc", "0");
            loanWalletVO.setMessage("未查询到产品");
        }
        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
        loanWalletVO.setResult(result);
        logger.debug("【贷款钱包】借款试算接口data={},响应结果={}", data, JSONObject.toJSONString(loanWalletVO));
        return loanWalletVO;
    }

    @Override
    public LoanWalletVO getContractList(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        List<JSONObject> list = new ArrayList<>();

        // 获取查询参数
        Map map = JSONObject.parseObject(data.toJSONString(), Map.class);
        String lwOrderId = (String) map.get("orderId");
        if (StringUtils.isNotBlank(lwOrderId)) {
            String applyId = applyTripartiteDkqbManager.getApplyId(lwOrderId);
            if (StringUtils.isBlank(applyId)) {
                loanWalletVO.setCode(LoanWalletVO.CODE_ERROR);
                loanWalletVO.setMessage("数据异常，用户工单尚未生成！");
                logger.error("【贷款钱包】合同接口异常-用户工单尚未生成！OrderId={}", lwOrderId);
                return loanWalletVO;
            }
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            if (custUserVO == null) {
                loanWalletVO.setCode(LoanWalletVO.CODE_ERROR);
                loanWalletVO.setMessage("数据异常，用户尚未注册！");
                logger.error("【贷款钱包】合同接口异常-用户尚未注册OrderId={}", lwOrderId);
                return loanWalletVO;
            }
            // 目前先不传合同

            result.put("name", "贷款合同");
            result.put("link", "");
            list.add(result);
        }
        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
        loanWalletVO.setMessage("请求成功");
        loanWalletVO.setResult(JSONObject.parseObject(JSONObject.toJSONString(list)));
        logger.debug("【贷款钱包】合同接口OrderId={},响应结果={}", lwOrderId, JSONObject.toJSONString(loanWalletVO));
        return loanWalletVO;
    }

    @Override
    public LoanWalletVO getBankList(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = null;
        List<JSONObject> list = new ArrayList<>();

        // 查询可用的银行
        List<AppBanksVO> banks = appBankLimitService.getBanks();
        for (AppBanksVO bank : banks) {
            result = new JSONObject();
            result.put("bankCode", bank.getBankNo());
            result.put("bankName", bank.getBankName());
            list.add(result);
        }
        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
        loanWalletVO.setMessage("请求成功");
        loanWalletVO.setResult(JSONObject.parseObject(JSONObject.toJSONString(list)));
        logger.debug("【贷款钱包】获取银行接口,响应结果={}", JSONObject.toJSONString(loanWalletVO));
        return loanWalletVO;
    }

    @Override
    @Transactional
    public LoanWalletVO bindCard(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        // 先查询缓存是否发送过验证码 没有调用通联短信接口
        LWBindCardOP lwBindCardOP = JSONObject.parseObject(data.toJSONString(), LWBindCardOP.class);
        String mobile = lwBindCardOP.getMobile();
        String idCard = lwBindCardOP.getIdCard();
        String key = MD5Utils.MD5(mobile + idCard);
        String bindSMSCache = getBindSMSCache(key);
        try {
            if (StringUtils.isBlank(bindSMSCache)) {
                DirectBindCardOP bindCardOp = new DirectBindCardOP();
                bindCardOp.setCardNo(lwBindCardOP.getCardNo());
                bindCardOp.setIdNo(idCard);
                bindCardOp.setMobile(mobile);
                bindCardOp.setRealName(lwBindCardOP.getRealName());
                // 调用通联短信接口
                BindCardResultVO bindCardResult = tltAgreementPayService.agreementPayMsgSend(bindCardOp, HandlerTypeEnum.SANS_HANDLER);
                if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getOrderNo())) {
                    result.put("bindStatus", "210");
                    result.put("remark", "验证码发送失败请重新发送");
                } else {
                    result.put("bindStatus", "100");
                    result.put("remark", "输入短信验证码");
                    JedisUtils.setObject("LoanWallet:BindSMS_" + key, mobile, 60 * 2);
                }
                loanWalletVO.setResult(result);
                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                logger.debug("【贷款钱包】协议支付预绑卡发短信,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                return loanWalletVO;
            } else {
                if (StringUtils.isNotBlank(lwBindCardOP.getVerifyCode())) {
                    // 绑卡
                    BindCardVO bindCard = bindCardService.getBindCard(mobile, idCard, lwBindCardOP.getCardNo(), lwBindCardOP.getRealName());
                    if (null != bindCard && StringUtils.isNotBlank(bindCard.getChlOrderNo())) {
                        ConfirmBindCardOP param = new ConfirmBindCardOP();
                        param.setMsgVerCode(lwBindCardOP.getVerifyCode());
                        param.setBindId(bindCard.getChlOrderNo());

                        // op校验需要 绑卡不需要 随机参数
                        param.setOrderNo(String.valueOf(System.currentTimeMillis()));
                        param.setSource("4");
                        param.setType(1);
                        BindCardResultVO bindCardResult = tltAgreementPayService.agreementPaySign(param);
                        logger.info("【贷款钱包】通联协议确认绑卡响应:{},{}", param.getUserId(), JSONObject.toJSON(bindCardResult));
                        if (!bindCardResult.isSuccess()) {
                            result.put("bindStatus", "210");
                            result.put("remark", "绑卡失败请重新绑卡");
                            // 删除短信缓存 重新发送短信绑卡
                            JedisUtils.del("LoanWallet:BindSMS_" + key);
                            loanWalletVO.setResult(result);
                            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                            loanWalletVO.setMessage("请求成功");
                            logger.debug("【贷款钱包】协议支付绑卡,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                            return loanWalletVO;
                        }
                        BindCardVO bindInfo = bindCardService.findByOrderNo(bindCardResult.getOrderNo());
                        CustUserVO user = custUserService.getCustUserByMobile(mobile);
                        if (StringUtils.isBlank(bindCardResult.getBindId()) || null == bindInfo || null == user) {
                            result.put("bindStatus", "210");
                            result.put("remark", "银行卡信息有误，请重试或换卡");
                            // 删除短信缓存 重新发送短信绑卡
                            JedisUtils.del("LoanWallet:BindSMS_" + key);
                            loanWalletVO.setResult(result);
                            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                            loanWalletVO.setMessage("请求成功");
                            logger.debug("【贷款钱包】协议支付绑卡,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                            return loanWalletVO;
                        }

                        // 保存绑卡信息

                        IdentityInfoOP identityInfoOP = new IdentityInfoOP();
                        identityInfoOP.setTrueName(bindInfo.getName());
                        identityInfoOP.setCardNo(bindInfo.getCardNo());
                        identityInfoOP.setIdNo(bindInfo.getIdNo());
                        identityInfoOP.setProtocolNo(bindCardResult.getBindId());
                        identityInfoOP.setUserId(user.getId());
                        identityInfoOP.setSource(param.getSource());
                        identityInfoOP.setBankMobile(mobile);
                        identityInfoOP.setBankCode(bindInfo.getBankCode());
                        identityInfoOP.setProductId(LoanProductEnum.JDQ.getId());
                        identityInfoOP.setAccount(bindInfo.getMobile());
                        identityInfoOP.setBindId(bindCardResult.getBindId());

                        bindInfo.setBindId(bindCardResult.getBindId());
                        bindInfo.setStatus(bindCardResult.getCode());
                        bindInfo.setRemark(bindCardResult.getMsg());
                        bindInfo.setBankCode(bindInfo.getBankCode());
                        bindInfo.setBankName(bindInfo.getBankName());
                        bindInfo.setChlName("通联确认协议支付绑卡");
                        bindInfo.setSource(param.getSource());

                        int saveBc = bindCardService.update(bindInfo);
                        if (saveBc == 0) {
                            result.put("bindStatus", "210");
                            result.put("remark", "处理异常,请重新绑卡");
                            // 删除短信缓存 重新发送短信绑卡
                            JedisUtils.del("LoanWallet:BindSMS_" + key);
                            loanWalletVO.setResult(result);
                            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                            loanWalletVO.setMessage("请求成功");
                            logger.debug("【贷款钱包】协议支付绑卡,更新BindCard响应结果={}", JSONObject.toJSONString(loanWalletVO));
                            return loanWalletVO;
                        }
                        int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
                        if (saveRz == 0) {
                            result.put("bindStatus", "210");
                            result.put("remark", "处理异常,请重新绑卡");
                            // 删除短信缓存 重新发送短信绑卡
                            JedisUtils.del("LoanWallet:BindSMS_" + key);
                            loanWalletVO.setResult(result);
                            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                            loanWalletVO.setMessage("请求成功");
                            logger.debug("【贷款钱包】协议支付绑卡,更新CustUser响应结果={}", JSONObject.toJSONString(loanWalletVO));
                            return loanWalletVO;
                        }

                        result.put("bindStatus", "200");
                        result.put("remark", "绑卡成功");
                        loanWalletVO.setResult(result);
                        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                        loanWalletVO.setMessage("请求成功");
                        logger.debug("【贷款钱包】协议支付绑卡,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                        return loanWalletVO;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            loanWalletVO.setCode(LoanWalletVO.CODE_ERROR);
            loanWalletVO.setMessage("请求失败");
            logger.error("【贷款钱包】用户绑卡异常phone={}", mobile, e);
        }
        loanWalletVO.setCode(LoanWalletVO.CODE_ERROR);
        loanWalletVO.setMessage("请求失败");
        return loanWalletVO;
    }

    @Override
    public LoanWalletVO submitLoan(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        LWWithdrawOP lwWithdrawOP = JSONObject.parseObject(data.toJSONString(), LWWithdrawOP.class);
        String applyId = applyTripartiteDkqbManager.getApplyId(lwWithdrawOP.getOrderId());

        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null || StringUtils.isBlank(loanApply.getId())) {
            result.put("loanStatus", "210");
            result.put("remark", "该用户尚未进件！");
            loanWalletVO.setResult(result);
            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
            loanWalletVO.setMessage("请求成功");
            logger.debug("【贷款钱包】用户请求提现,响应结果={}", JSONObject.toJSONString(loanWalletVO));
            return loanWalletVO;
        }

        CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
        if (custUserVO == null || custUserVO.getCardNo() == null) {
            result.put("loanStatus", "210");
            result.put("remark", "该用户尚未绑卡！");
            loanWalletVO.setResult(result);
            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
            loanWalletVO.setMessage("请求成功");
            logger.debug("【贷款钱包】用户请求提现,响应结果={}", JSONObject.toJSONString(loanWalletVO));
            return loanWalletVO;
        }

        LoanApplySimpleVO applyVo = loanApplyService.getUnFinishLoanApplyInfo(custUserVO.getId());
        logger.info("LoanApplyStatus:{}", applyVo.getLoanApplyStatus().intValue());
        if (LoanApplySimpleVO.APPLY_STATUS_SHOPPING.intValue() != applyVo.getLoanApplyStatus().intValue()) {
            result.put("loanStatus", "210");
            result.put("remark", "重复提现");
            loanWalletVO.setResult(result);
            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
            loanWalletVO.setMessage("请求成功");
            logger.debug("【贷款钱包】用户请求提现,响应结果={}", JSONObject.toJSONString(loanWalletVO));
            return loanWalletVO;
        } else {
            boolean flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
            logger.info("flag:{}", flag);
            if (!flag) {
                result.put("loanStatus", "210");
                result.put("remark", "【贷款钱包】用户请求提现系统异常！");
                loanWalletVO.setResult(result);
                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                logger.debug("【贷款钱包】用户请求提现,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                return loanWalletVO;
            } else {
                result.put("loanStatus", "200");
                result.put("remark", "用户请求提现成功！");
                loanWalletVO.setResult(result);
                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                logger.debug("【贷款钱包】用户请求提现,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                return loanWalletVO;
            }
        }
    }

    @Override
    public LoanWalletVO repayment(JSONObject data) {
        // 定义返回结果
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        JSONObject result = new JSONObject();

        LWRepaymentOP lwRepaymentOP = JSONObject.parseObject(data.toJSONString(), LWRepaymentOP.class);

        String orderId = lwRepaymentOP.getOrderId();
        String lockKey = "LoanWallet:pay_lock_" + orderId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        // 根据orderId防并发加锁
        boolean lock = JedisUtils.setLock(lockKey, requestId, 60);
        if (!lock) {
            logger.warn("【贷款钱包】协议代扣接口调用中，loanWalletOrderId= {}", orderId);
            result.put("repayStatus", "210");
            result.put("remark", "还款处理中，请勿重复操作");
            loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
            loanWalletVO.setMessage("请求成功");
            loanWalletVO.setResult(result);
            return loanWalletVO;
        }
        try {
            String applyId = applyTripartiteDkqbManager.getApplyId(lwRepaymentOP.getOrderId());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);

            // 截取repayPlanItemId最后一位 比对期数是否正确
            String repayPlanItemId = loanApplySimpleVO.getRepayPlanItemId();
            String term = StringUtils.substring(loanApplySimpleVO.getRepayPlanItemId(), repayPlanItemId.length() - 1);
            if (!term.equals(lwRepaymentOP.getStageNo())){
                result.put("repayStatus", "210");
                result.put("remark", "还款期数错误");
                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                loanWalletVO.setResult(result);
                logger.debug("【贷款钱包】代扣结果,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                return loanWalletVO;
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

            ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementTonglianPay(rePayOP);
            if ("I".equals(confirmAuthPayVO.getStatus())) {
                // 线程休息2s 查询代扣结果
                Thread.sleep(2000);
                boolean flag = queryRepaymentResult(confirmAuthPayVO.getOrderNo());
                if (flag) {
                    result.put("repayStatus", "200");
                    result.put("remark", "还款成功");
                    loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                    loanWalletVO.setMessage("请求成功");
                    loanWalletVO.setResult(result);
                    logger.debug("【贷款钱包】首次执行查询代扣结果,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                    return loanWalletVO;
                } else {
                    Thread.sleep(2000);
                    boolean flag1 = queryRepaymentResult(confirmAuthPayVO.getOrderNo());
                    if (flag1) {
                        result.put("repayStatus", "200");
                        result.put("remark", "还款成功");
                        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                        loanWalletVO.setMessage("请求成功");
                        loanWalletVO.setResult(result);
                        logger.debug("【贷款钱包】第二次执行查询代扣结果,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                        return loanWalletVO;
                    } else {
                        result.put("repayStatus", "210");
                        result.put("remark", "还款处理中");
                        loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                        loanWalletVO.setMessage("请求成功");
                        loanWalletVO.setResult(result);
                        logger.debug("【贷款钱包】代扣结果,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                        return loanWalletVO;
                    }
                }
            } else {
                result.put("repayStatus", "210");
                result.put("remark", "还款失败");
                loanWalletVO.setCode(LoanWalletVO.CODE_SUCCESS);
                loanWalletVO.setMessage("请求成功");
                loanWalletVO.setResult(result);
                logger.debug("【贷款钱包】代扣结果,响应结果={}", JSONObject.toJSONString(loanWalletVO));
                return loanWalletVO;
            }
        } catch (Exception e) {
            logger.error("【贷款钱包】主动还款接口异常! OrderId={}", lwRepaymentOP.getOrderId(), e);
            result.put("repayStatus", "210");
            result.put("remark", "还款失败");
            loanWalletVO.setCode(LoanWalletVO.CODE_ERROR);
            loanWalletVO.setMessage("系统异常");
            loanWalletVO.setResult(result);
        } finally {
            // 解除orderId并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
        return loanWalletVO;
    }

    @Transactional
    public boolean queryRepaymentResult(String orderNo) {
        TlWithholdQueryOP queryOP = new TlWithholdQueryOP();
        queryOP.setReqSn(orderNo);
        QTransRsp rspResult = (QTransRsp) tltAgreementPayService.query(queryOP);
        Boolean allSolved = false;
        // 通联返回结果非空
        if (rspResult != null && rspResult.getDetails().size() > 0) {
            Double txAmt = 0D;
            allSolved = true;
            Boolean doing = false;// 处理中
            List<String> remarks = new ArrayList<String>();
            String remark = null;
            String succDate = null;
            List<QTDetail> details = rspResult.getDetails();
            for (QTDetail resp : details) {
                if (StringUtils.isNotBlank(resp.getAMOUNT())) {
                    txAmt += Double.parseDouble(resp.getAMOUNT());
                }
                allSolved = (allSolved) && (TonglianErrInfo.E0000.getCode().equals(resp.getRET_CODE()) || TonglianErrInfo.E4000.getCode().equals(resp.getRET_CODE()));
                remark = resp.getAMOUNT() + "元," + resp.getREMARK();
                remarks.add(remark);
                succDate = resp.getFINTIME();
                doing = (!TonglianErrInfo.E0000.getCode().equals(resp.getRET_CODE()) && !TonglianErrInfo.E4000.getCode().equals(resp.getRET_CODE()));
                if (doing) {
                    allSolved = false;
                    break;
                }
            }
            if (allSolved) {
                WithholdQueryResultVO retVo = new WithholdQueryResultVO();
                retVo.setOrigTradeDate(succDate);
                retVo.setSuccAmt(txAmt.toString());
                RepayLogVO pw = repayLogService.get(orderNo);
                // 还款
                if (PayTypeEnum.SETTLEMENT.getId().equals(pw.getPayType())) {
                    withholdService.updateOrderInfo(pw.getRepayPlanItemId(), retVo);
                } else {
                    logger.error("查询支付订单状态，错误的支付类型： payType=" + pw.getPayType());
                }
                pw.setStatus(ErrInfo.SUCCESS.getCode());
                pw.setSuccTime(new Date());
                pw.setSuccAmt(new BigDecimal(retVo.getSuccAmt()));
                pw.setRemark("扣款成功");
                repayLogService.updateRepayResult(pw);
            }

        }
        return allSolved;
    }

    public static LoanWalletVO setIsUserAcceptCache(LoanWalletVO loanWalletVO, String key) {
        if (null != loanWalletVO) {
            JedisUtils.setObject("LoanWallet:IsUserAccept_" + key, loanWalletVO, 30);
        }
        return loanWalletVO;
    }

    public static LoanWalletVO getIsUserAcceptCache(String key) {
        LoanWalletVO loanWalletVO = new LoanWalletVO();
        if (StringUtils.isNotBlank(key)) {
            return (LoanWalletVO) JedisUtils.getObject("LoanWallet:IsUserAccept_" + key);
        }
        return loanWalletVO;
    }

    public static String getBindSMSCache(String key) {
        if (StringUtils.isNotBlank(key)) {
            return (String) JedisUtils.getObject("LoanWallet:BindSMS_" + key);
        }
        return null;
    }
}
