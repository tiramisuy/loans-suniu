package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.service.StoreService;
import com.rongdu.loans.basic.vo.StoreVO;
import com.rongdu.loans.common.IdUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.helper.LoanApplyHelper;
import com.rongdu.loans.cust.entity.Blacklist;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.BlacklistManager;
import com.rongdu.loans.cust.manager.CustUserInfoManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.*;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.loan.dao.LoanApplyDAO;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.*;
import com.rongdu.loans.loan.option.xjbk.ApplyThirdOP;
import com.rongdu.loans.loan.option.xjbk.ApproveFeedbackOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.risk.service.RiskService;
import com.rongdu.loans.risk.vo.HitRuleVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.*;

/**
 * 产品信息Service接口实现类
 *
 * @author likang
 * @version 2017-06-22
 */
@Service("loanApplyService")
public class LoanApplyServiceImpl extends BaseService implements LoanApplyService {

    private static String CONTRACT_PREFIX = "HT";

    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private LoanRepayPlanManager loanRepayPlanManager;
    @Autowired
    private LoanProductManager loanProductManager;
    @Autowired
    private BlacklistManager blacklistManager;
    @Autowired
    private PromotionCaseManager promotionCaseManager;
    @Autowired
    private CustUserManager custUserManager;
    @Autowired
    private ContractManager contractManager;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private OperationLogManager operationLogManager;
    @Autowired
    private BorrowInfoManager borrowInfoManager;
    @Autowired
    private ContactHistoryManager contactHistoryManager;
    @Autowired
    private UserInfoHistoryManager userInfoHistoryManager;
    @Autowired
    private RefuseReasonManager refuseReasonManager;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private RiskService riskService;
    @Autowired
    private LoanProductService loanProductService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private OperationLogLaterManager operationLogLaterManager;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ApplyTripartiteService applyTripartiteService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AreaManager areaManager;
    @Autowired
    private BorrowInfoService borrowInfoService;
    @Autowired
    private KDDepositService kdDepositService;
    @Autowired
    private KDPayService kdPayService;
    @Autowired
    private LoanApplyDAO loanApplyDAO;
    @Autowired
    private CustUserInfoManager custUserInfoManager;

    /*
     * 现金贷
     */
    @Override
    public LoanApplySimpleVO getCurrentLoanApplyInfo(String userId) {
        // 设置返回参数
        LoanApplySimpleVO vo = new LoanApplySimpleVO();
        String productSwitch = Global.getConfig("xjd.product.switch");
        vo.setProductSwitch(productSwitch);
        vo.setProductCloseTip(Global.getConfig("xjd.product.close.tip"));

        // 根据产品代码获取产品信息
        /*
         * LoanProductVO proVo =
         * loanProductService.getLoanProductDetail(LoanProduct.DEF_PRODUCT_ID);
         * vo.setMaxAmt(proVo != null ? proVo.getMaxAmt() : new
         * BigDecimal(5000));
         */
        vo.setMaxAmt(new BigDecimal(5000));

        // 获取未完成贷款申请详情
        LoanApply loanApply = loanApplyManager.getInitUnFinishLoanApplyByUserId(userId);
        if (null == loanApply) {
            // 基础认证状态
            Integer authStatus = operationLogService.getAuthStatus(userId, LoanProduct.DEF_PRODUCT_ID);
            if (authStatus == AuthenticationStatusVO.NO) {
                vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
                return vo;
            }
            // 更多认证状态
            Integer moreAuthStatus = operationLogService.getMoreAuthStatus(userId, LoanProduct.DEF_PRODUCT_ID);
            if (AuthenticationStatusVO.YES.equals(moreAuthStatus)) {
                vo.setMoreAuthStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
            } else {
                vo.setMoreAuthStatus(LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
            }

            // 查询最近一个月申请次数
            CountApplyOP countApplyOP = new CountApplyOP();
            countApplyOP.setUserId(userId);
            countApplyOP.setBeginDate(DateUtils.getYYYYMMDD2Int(DateUtils.addMonth(new Date(), -1)));
            countApplyOP.setEndDate(DateUtils.getYYYYMMDD2Int());
            int applyCount = loanApplyManager.countApplyNearMonth(countApplyOP);
            if (applyCount >= Global.MAX_APPLY_COUNT_MONTH) {
                vo.setPermitApplyAgain(LoanApplySimpleVO.NO);
                vo.setApplyLimitTip(Global.getConfig("permit.apply.tip1"));
            } else {
                vo.setPermitApplyAgain(LoanApplySimpleVO.YES);
            }
            // 查询最近一笔完结申请记录
            LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
            if (null != lastApply) {
                if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                        || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                    // 上一笔申请被拒情况
                    vo.setIsHaveLoan(LoanApplySimpleVO.NO);
                    vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_REJECT);
                    // 判断上次拒绝更新的时间距现在的天数是否超限
                    Date lastUpdateTime = lastApply.getUpdateTime();
                    long pastDays = DateUtils.pastDays(lastUpdateTime);
                    if (pastDays < Global.MIN_XJD_APPLY_DAY) {
                        vo.setPermitApplyAgain(LoanApplySimpleVO.NO);
                        vo.setApplyLimitTip("申请次数限制，请稍后再试");
                    } else {
                        vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
                    }
                } else {
                    // 上一笔申请正常完结情况
                    vo.setIsHaveLoan(LoanApplySimpleVO.NO);
                    vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
                }
            } else {
                // 当前是否有贷款
                // Integer authStatus =
                // operationLogService.getAuthStatus(userId,
                // LoanProduct.DEF_PRODUCT_ID);
                if (authStatus == AuthenticationStatusVO.NO) {
                    vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
                } else {
                    vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
                }
            }
        } else {
            if (LoanProductEnum.CCD.getId().equals(loanApply.getProductId())
                    && loanApply.getStatus() > XjdLifeCycle.LC_APPLY_0) {
                vo.setPermitApplyAgain(LoanApplySimpleVO.NO);
                vo.setApplyLimitTip("您有未结清的其它贷款产品，请联系客服");
            } else {
                vo.setServFeeRate(loanApply.getServFeeRate());
                setLoanApply(vo, loanApply);
            }
        }
        return vo;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<ApplyListVO> getLoanApplyList(Page page, ApplyListOP applyListOP) {
        List<ApplyListVO> voList = loanApplyManager.getLoanApplyList(page, applyListOP);
        if (CollectionUtils.isEmpty(voList)) {
            page.setList(Collections.emptyList());
            return page;
        }
        setBlacklistParam(applyListOP, voList);
        page.setList(voList);
        return page;
    }

    @Override
    public Page<ApproveFeedbackOP> getLoanApplyThirdList(Page page, ApplyThirdOP applyThirdOP) {
        List<ApproveFeedbackOP> voList = loanApplyManager.getLoanApplyThirdList(page, applyThirdOP);
        if (CollectionUtils.isEmpty(voList)) {
            page.setList(Collections.emptyList());
            return page;
        }
        // setBlacklistParam(applyListOP, voList);
        page.setList(voList);
        return page;
    }

    private void setBlacklistParam(ApplyListOP applyListOP, List<ApplyListVO> voList) {
        /** 已否决列表需要查询是否在黑名单 */
        if (applyListOP.getStage().equals(3)) {
            List<String> userIdList = new ArrayList<>();
            for (ApplyListVO vo : voList) {
                userIdList.add(vo.getUserId());
            }
            List<Blacklist> blacklistList = blacklistManager.getBlacklistByUserIdList(userIdList);
            if (CollectionUtils.isNotEmpty(blacklistList)) {
                for (ApplyListVO vo : voList) {
                    for (Blacklist black : blacklistList) {
                        if (StringUtils.equals(vo.getUserId(), black.getUserId())) {
                            vo.setBlacklist(1);
                        }
                    }
                }
            }
        }
    }

    /*
     * 仅聚宝钱包调用
     */
    @Override
    public LoanApplySimpleVO getUnFinishLoanApplyInfo(String userId) {
        // 设置返回参数
        LoanApplySimpleVO vo = new LoanApplySimpleVO();
        // 参数判断
        if (StringUtils.isBlank(userId)) {
            logger.error(" 参数不合法");
            return vo;
        }
        // 获取未完成贷款申请详情
        LoanApply loanApply = loanApplyManager.getUnFinishLoanApplyByUserId(userId);
        if (null != loanApply) {
            if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
                    && !LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
                    && !LoanProductEnum.JDQ.getId().equals(loanApply.getProductId())
                    && loanApply.getStatus() > XjdLifeCycle.LC_APPLY_0) {
                return vo;
            }
            setLoanApply(vo, loanApply);
        }
        return vo;
    }

    @Override
    public boolean isExistUnFinishLoanApply(String userId) {
        // 参数判断
        if (StringUtils.isBlank(userId)) {
            logger.error(" 参数不合法");
            return false;
        }
        // 获取未完成贷款申请详情
        LoanApply loanApply = loanApplyManager.getUnFinishLoanApplyByUserId(userId);
        if (null == loanApply || StringUtils.isBlank(loanApply.getId())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<LoansApplySummaryVO> getLoanApplyListByUserId(String userId) {
        return loanApplyManager.getLoanApplyListByUserId(userId);
    }

    @Override
    public LoansApplyFinishedVO getFinishedLoansApplyFromUser(LoanApplyCustOP op) {
        // 构造返回对象
        LoansApplyFinishedVO rz = new LoansApplyFinishedVO();
        if (null == op) {
            logger.error("参数不合法");
            rz.setTotalCount(0);
            rz.setTotalTerm(0);
        } else {
            // 过滤审核拒绝的情况的记录
            op.setProcessStage(XjdLifeCycle.LC_LENDERS);
            // 获取已完结贷款申请列表
            List<LoansApplySummaryVO> list = loanApplyManager.getLoanApplySummaryFromUser(op);
            int listSize = list.size();
            if (null != list && listSize > 0) {
                // 返回值设置
                rz.setLoansApplySummaryList(list);
                // 统计有效记录条数
                int totalCount = 0;
                // 合计记录借款天数
                int totalTerm = 0;
                for (int i = 0; i < listSize; i++) {
                    LoansApplySummaryVO temp = list.get(i);
                    if (null != temp && null != temp.getApproveTerm()) {
                        // 统计有效记录条数
                        totalCount = totalCount + 1;
                        // 合计记录借款天数
                        totalTerm = totalTerm + temp.getApproveTerm();
                    }
                }
                rz.setTotalCount(totalCount);
                rz.setTotalTerm(totalTerm);
            } else {
                // 返回值设置
                rz.setTotalCount(0);
                rz.setTotalTerm(0);
            }
        }
        return rz;
    }

    @Override
    public LoanApplySimpleVO getLoanApplyById(String applyId) {
        // 设置返回参数
        LoanApplySimpleVO vo = null;
        // 参数判断
        if (StringUtils.isBlank(applyId)) {
            logger.error(" 参数不合法");
        } else {
            // 贷款信息
            LoanApply entity = loanApplyManager.getLoanApplyById(applyId);
            vo = new LoanApplySimpleVO();
            if (null == entity) {
                vo.setIsHaveLoan(LoanApplySimpleVO.NO);
            } else {
                vo.setUserId(entity.getUserId());
                vo.setProductId(entity.getProductId());
                vo.setTerm(entity.getTerm());
                vo.setServFeeRate(entity.getServFeeRate());
                vo.setCallCount(entity.getCallCount());
                vo.setLoanSuccCount(entity.getLoanSuccCount());
                setLoanApply(vo, entity);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("贷款详情：", JsonMapper.toJsonString(vo));
        }
        return vo;
    }

    /**
     * 贷款信息设置公用代码
     *
     * @param vo     LoanApplySimpleVO
     * @param entity LoanApply
     */
    private void setLoanApply(LoanApplySimpleVO vo, LoanApply entity) {
        // 设置贷款申请情况
        vo.setIsHaveLoan(LoanApplySimpleVO.YES);
        // 设置申请状态
        // 判断是否完成信息录入
        Integer status = entity.getStatus();
        // 从缓存中获取
        CustUserVO custUserVO = (CustUserVO) JedisUtils.getObject(Global.USER_CACHE_PREFIX + entity.getUserId());
        if (null == custUserVO) {
            custUserVO = custUserService.getCustUserById(entity.getUserId());
        }

        /*
         * 有未完结的订单不判断认证状态 Integer authStatus =
         * operationLogService.getAuthStatus(entity.getUserId(),
         * entity.getProductId()); if (authStatus == AuthenticationStatusVO.NO)
         * { vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
         * if (status >= XjdLifeCycle.LC_CASH_4 &&
         * ApplyStatusEnum.UNFINISH.getValue().equals(entity.getApplyStatus()))
         * { vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_PASS); } }
         * else
         */
        if (status == XjdLifeCycle.LC_AUTO_AUDIT_2 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2) {
            vo.setIsHaveLoan(LoanApplySimpleVO.NO);
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_REJECT);
        } else if (status == XjdLifeCycle.LC_APPLY_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_0
                || status == XjdLifeCycle.LC_AUTO_AUDIT_3 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0
                || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3) {
            // 真正审核中
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_AUDITING);
        } else if (status == XjdLifeCycle.LC_RAISE_0 || status == XjdLifeCycle.LC_RAISE_1
                || status == XjdLifeCycle.LC_LENDERS_0) {
            // 放款中
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_PAY);
        } else if (status > XjdLifeCycle.LC_APPLY_1 && status < XjdLifeCycle.LC_LENDERS_1) {
            // 审核成功募集资金中
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_AUDITING);
        } else if (status >= XjdLifeCycle.LC_CASH_3 && status <= XjdLifeCycle.LC_OVERDUE_1) {
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_PASS);
        } else if (status == XjdLifeCycle.LC_CASH_2) {
            // 待提现
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_WITHDRAW);
        } else {
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
        }

        // 需购买加急卷-现金贷14天单期-先支付
        // ytodo 0303 加急券 version
        /*
         * if ("2".equals(entity.getSource()) || "3".equals(entity.getSource()))
         * { // Android用户 if
         * (LoanProductEnum.XJD.getId().equals(entity.getProductId()) && status
         * == XjdLifeCycle.LC_RAISE_0 && entity.getTerm() == 1) {
         * vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_URGENT);
         * vo.setLoanApplyPayType(LoanApplySimpleVO.APPLY_PAY_TYPE_0);// 先支付
         * vo.setUrgentFee(entity.getServFee()); vo.setWaitPayNum("10000+");
         * vo.setCurToltalRepayAmt(entity.getApproveAmt().add(entity.getInterest
         * ())); if ("S".equals(entity.getUrgentPayed()) &&
         * WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue().toString().equals(
         * entity.getPayChannel())) { // 乐视放款渠道，购买加急券后，直接确认放款
         * vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_PAY); } else if
         * ("S".equals(entity.getUrgentPayed()) &&
         * WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().
         * equals(entity.getPayChannel())) { // 口袋放款渠道，购买加急券后，去开户
         * vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_URGENT_S); }
         * else if ("S".equals(entity.getUrgentPayed()) &&
         * WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(
         * entity.getPayChannel())) { if (custUserVO.getHjsAccountId() != null
         * && custUserVO.getHjsAccountId() != 0) { // 汉金所放款渠道，购买加急券且已开过户，直接确认放款
         * vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_PAY); } else {
         * // 汉金所放款渠道，购买加急券后，去开户
         * vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_URGENT_S); } } }
         * }else {
         */
        // 需购确认借款-现金贷14/15天单期-砍头息
/*		if (LoanProductEnum.XJD.getId().equals(entity.getProductId()) && status == XjdLifeCycle.LC_RAISE_0
				&& entity.getTerm().intValue() == 1 && !isBuyShoppingGold(entity.getId())) {
			vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_SHOPPING);// APP更新后替换为APPLY_STATUS_SHOPPING_2
			vo.setLoanApplyPayType(LoanApplySimpleVO.APPLY_PAY_TYPE_1);// 后代扣
			vo.setUrgentFee(entity.getServFee());// 支付费用
		}*/
        // }
        // 需购买旅游券-现金贷90天分期-后代扣
/*		if (LoanProductEnum.XJD.getId().equals(entity.getProductId()) && status == XjdLifeCycle.LC_RAISE_0
				&& entity.getTerm().intValue() > 1 && !isBuyShoppingGold(entity.getId())) {
			vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_SHOPPING);
			vo.setLoanApplyPayType(LoanApplySimpleVO.APPLY_PAY_TYPE_1);// 后代扣
			vo.setUrgentFee(entity.getServFee());// 支付费用
		}*/
        // 需购买购物金-现金贷信用分期-后代扣
        // 借点钱分期 设置为购物金状态
        if (LoanProductEnum.JDQ.getId().equals(entity.getProductId()) && status == XjdLifeCycle.LC_RAISE_0
                && entity.getTerm().intValue() > 1) {
            vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_SHOPPING);
        }
        // 代扣购物金失败
        // if (LoanProductEnum.XJD.getId().equals(entity.getProductId())
        // && status == XjdLifeCycle.LC_RAISE_1
        // && entity.getTerm().intValue() > 1) {
        // ShopWithhold withHold =
        // shopWithholdManager.findByApplyId(entity.getId());
        // if (withHold != null && withHold.getWithholdNumber().intValue() >= 4
        // && withHold.getWithholdStatus().intValue() == 1) {
        // vo.setLoanApplyStatus(LoanApplySimpleVO.APPLY_STATUS_SHOPPING_FAIL);
        // }
        // }
        // 更多认证状态
/*		Integer moreAuthStatus = operationLogService.getMoreAuthStatus(entity.getUserId(), entity.getProductId());
		if (AuthenticationStatusVO.YES.equals(moreAuthStatus)) {
			vo.setMoreAuthStatus(LoanApplySimpleVO.APPLY_STATUS_UNOVER);
		} else {
			vo.setMoreAuthStatus(LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
		}*/
        // 审核通过后，显示应还总额，每期应还，还款期数
        if (status == XjdLifeCycle.LC_RAISE_0) {
            setApplyRepayPlan(vo, entity);
        }
        // 是否允许延期
/*		if (LoanProductEnum.XJD.getId().equals(entity.getProductId()) && entity.getTerm().intValue() == 1) {
			vo.setDelayLimit("1");
		} else {
			vo.setDelayLimit("0");
		}*/

        vo.setProductId(entity.getProductId());
        // 申请编号
        vo.setApplyId(entity.getId());
        // 申请时间
        vo.setApplyTime(DateUtils.formatDateTime(entity.getApplyTime()));
        // 审批金额
        vo.setApproveAmt(entity.getApproveAmt());
        // 审批日期
        vo.setApproveTerm(entity.getApproveTerm());
        // 申请天数
        vo.setApplyTerm(entity.getApplyTerm());
        // 应还利息
        vo.setInterest(entity.getInterest());
        // 用户姓名
        vo.setUserName(entity.getUserName());
        // 用户手机
        vo.setMobile(entity.getMobile());
        // 用户证件号
        vo.setIdNo(entity.getIdNo());
        // 申请流程阶段
        vo.setProcessStage(entity.getStage());
        // 申请流程阶段的状态
        vo.setProcessStatus(entity.getStatus());
        // 贷款申请状态(用于复审回显)
        vo.setApproveResult(entity.getApproveResult());
        vo.setSource(entity.getSource());
        vo.setExtInfo(entity.getExtInfo());
        vo.setIp(entity.getIp());
        // 渠道码
        vo.setChannelId(entity.getChannelId());
        // 放款渠道
        vo.setPayChannel(entity.getPayChannel());
        // 放款成功次数
        vo.setLoanSuccCount(entity.getLoanSuccCount());
        // 加急券购买状态
        vo.setUrgentPayed(entity.getUrgentPayed());

        // 直接从数据库中查最新数据
        // CustUserVO custUserVO =
        // custUserService.getCustUserById(entity.getUserId());

        // String protocolNo = custUserVO.getProtocolNo();
        // String bankCode = custUserVO.getBankCode();
        String needBindCard = "0";
        // if (StringUtils.isBlank(protocolNo)) {
        // needBindCard = "1";
        // } else if (!StringUtils.startsWith(protocolNo, "1")
        // && (BankCodeEnum.ICBC_BANK.getbName().equals(bankCode) ||
        // BankCodeEnum.HXB_BANK.getbName().equals(
        // bankCode))) {
        // needBindCard = "1";
        // }
        vo.setNeedBindCard(needBindCard);
        vo.setBankCard(custUserVO.getCardNo());
        // 是否需要开户 0-不需要 1-需要
        // vo.setNeedOpenAccount(StringUtils.isNotBlank(custUserVO.getAccountId())
        // ? "0" : "1");
        vo.setNeedOpenAccount("0");
        // 14天单期产品用口袋放款，不需要开户
        if (entity.getApproveTerm() == Global.XJD_DQ_DAY_14) {
            vo.setNeedOpenAccount("0");
        }
        // 口袋存管放款必须开户
        // int payChannel =
        // Integer.parseInt(configService.getValue("day_15_pay_channel"));
        // if (status == XjdLifeCycle.LC_RAISE_0 &&
        // !isBuyShoppingGold(entity.getId())
        // && payChannel == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
        // && entity.getApproveTerm() == Global.XJD_DQ_DAY_15) {
        // vo.setNeedOpenAccount("1");
        // }

        // 将银行编码转成银行名称
        vo.setBankCode(BankCodeEnum.getName(custUserVO.getBankCode()));
        vo.setEmail(custUserVO.getEmail());
        // 贷款用途
        vo.setPurpose(entity.getPurpose());
        vo.setPurposeDesc(LoanPurposeEnum.getDesc(entity.getPurpose()));

        vo.setFirstFeeDesc(Global.FIRST_FEE_DESC);
        vo.setSecondFeeDesc(Global.SECOND_FEE_DESC);

        if (StringUtils.isNotBlank(custUserVO.getBankCityId()) && !"(null)".equals(custUserVO.getBankCityId())) {
            vo.setCityId(custUserVO.getBankCityId());
            String cityName = cityService.getById(Integer.valueOf(custUserVO.getBankCityId()).intValue());
            vo.setCityName(cityName);
        }
        if (null != vo.getProcessStage()) {
            switch (vo.getProcessStage()) {
                case XjdLifeCycle.LC_LENDERS:
                    // 放款后
                    if (status > XjdLifeCycle.LC_LENDERS_0) {
                        // 获取还款计划信息-- 放款后
                        getLoanRepayPlanInfo(vo, isOverLoan(entity.getApplyStatus()));
                        // 获取提现情况
                        if (status != XjdLifeCycle.LC_CASH_4) {
                            // 提现状态
                            vo.setCashState(LoanApplySimpleVO.YES);
                            // 可提现金额
                            vo.setCashAmt(entity.getApproveAmt());
                        }
                    }
                    getCashInfo(vo);
                    break;
                case XjdLifeCycle.LC_REPAY:
                    // 获取还款计划信息 - 还款阶段
                    getLoanRepayPlanInfo(vo, isOverLoan(entity.getApplyStatus()));
                    break;
                case XjdLifeCycle.LC_OVERDUE:
                    // 获取还款计划信息 - 逾期阶段
                    // 获取还款计划信息 - 还款阶段
                    getLoanRepayPlanInfo(vo, isOverLoan(entity.getApplyStatus()));
                    break;
                case XjdLifeCycle.LC_WRITE_OFF:
                    // 获取还款计划信息 - 核销阶段
                    // 获取还款计划信息 - 还款阶段
                    getLoanRepayPlanInfo(vo, isOverLoan(entity.getApplyStatus()));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取还款计划信息
     *
     * @param vo
     * @param isOverLoan 是否已结清贷款
     */
    private void getLoanRepayPlanInfo(LoanApplySimpleVO vo, boolean isOverLoan) {
        // 获取还款计划信息
        LoanRepayDetailVO loanRepayPlan = loanRepayPlanManager.getLoanRepayDetail(vo.getApplyId(),
                isOverLoan ? Global.REPAY_PLAN_STATUS_OVER : Global.REPAY_PLAN_STATUS_UNOVER);
        if (null != loanRepayPlan) {
            // 还款计划明细id
            vo.setRepayPlanItemId(loanRepayPlan.getRepayPlanItemId());
            // 应还款日期
            vo.setRepayDate(DateUtils.formatDate(loanRepayPlan.getCurRepayDate()));
            // 放款日期
            vo.setLoanDate(DateUtils.formatDateTime(loanRepayPlan.getLoanStartDate()));
            // 当前利息(默认到期情况的利息)
            vo.setInterest(loanRepayPlan.getInterest());
            // 减免费用
            vo.setDeduction(loanRepayPlan.getDeduction());
            // 应还本息
            BigDecimal curActualRepayAmt = loanRepayPlan.getCurActualRepayAmt();
            BigDecimal totalAmount = curActualRepayAmt == null ? loanRepayPlan.getTotalAmount()
                    : loanRepayPlan.getTotalAmount().subtract(curActualRepayAmt);
            vo.setCurToltalRepayAmt(totalAmount);
            // 当前期数
            vo.setTerm(loanRepayPlan.getThisTerm());
            // 当前本金
            vo.setPrincipal(loanRepayPlan.getPrincipal());
            // 总期数
            if (LoanProductEnum.XJD.getId().equals(vo.getProductId()) && loanRepayPlan.getTotalTerm().intValue() > 1
                    && vo.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_90) {
                // 小额分期
                vo.setTotalTerm(loanRepayPlan.getTotalTerm() + 2);
                // if (loanRepayPlan.getThisTerm() ==
                // loanRepayPlan.getTotalTerm()) {
                // vo.setPreferential(LoanApplySimpleVO.YES);
                // }
            } else {
                vo.setTotalTerm(loanRepayPlan.getTotalTerm());
            }
            // 贷款已申请天数
            // Integer pastDays =
            // DateUtils.daysBetween(loanRepayPlan.getCurStartDate(), new
            // Date()) + 1;
            Integer pastDays = DateUtils.daysBetween(loanRepayPlan.getLoanStartDate(), new Date()) + 1;
            // 逾期天数
            Integer overdueDays = 0;
            if (isOverLoan) {
                // 实际还款日期
                vo.setRepayDate(DateUtils.formatDate(loanRepayPlan.getCurRealRepayDate()));
                overdueDays = DateUtils.daysBetween(loanRepayPlan.getCurRepayDate(),
                        loanRepayPlan.getCurRealRepayDate());
            } else {
                overdueDays = DateUtils.daysBetween(loanRepayPlan.getCurRepayDate(), new Date());
            }
            if (overdueDays > 0) {
                // 逾期天数
                vo.setOverdueDays(overdueDays);
                // 逾期罚息
                vo.setPenalty(loanRepayPlan.getPenalty());
                // 逾期管理费
                vo.setOverdueFee(loanRepayPlan.getOverdueFee());
                // 近7天应还金额
                vo.setUnPayAmtNear7(loanRepayPlan.getTotalAmount());
            } else if (overdueDays == 0) {
                // 逾期天数
                vo.setOverdueDays(0);
                // 提前天数
                vo.setPrepayDays(0);
                // 近7天应还金额
                vo.setUnPayAmtNear7(loanRepayPlan.getTotalAmount());
            } else {
                // 逾期天数
                vo.setOverdueDays(0);
                // 提前天数
                vo.setPrepayDays(-overdueDays);
                if (vo.getPrepayDays() == 0) {
                    vo.setUnPayAmtNear7(loanRepayPlan.getTotalAmount());
                }
                // 提前还款违约金
                vo.setPrepayFee(loanRepayPlan.getPrepayFee());
            }
            // 计算延期金额
            // Contract contract =
            // contractManager.getByApplyId(vo.getApplyId());
            BigDecimal overdueFee = new BigDecimal(0);
            BigDecimal deduction = new BigDecimal(0);
            if (overdueDays > 0) {
                overdueFee = loanRepayPlan.getOverdueFee();
                deduction = loanRepayPlan.getDeduction();
            }
            BigDecimal curDelayRepayAmt = vo.getApproveAmt().multiply(new BigDecimal(Global.DELAY_RATE)).add(overdueFee)
                    .subtract(deduction).setScale(2, BigDecimal.ROUND_HALF_UP);
            vo.setCurDelayRepayAmt(curDelayRepayAmt);
            // 是否允许提前还款
            int minLoanDay = 1;
            if (pastDays > minLoanDay) {
                vo.setPermitPrePay(LoanApplySimpleVO.YES);
                if (LoanProductEnum.XJD.getId().equals(vo.getProductId())
                        && loanRepayPlan.getTotalTerm().intValue() == 1) {
                    vo.setDelayLimit("1");
                }
            } else {
                vo.setPermitPrePay(LoanApplySimpleVO.NO);
                vo.setDelayLimit("0");
            }

            /*
             * // 提前还款情况 if (vo.getPrepayDays() > 0 && !isOverLoan) {
             * //提前还款相关费用结算 PrePayCostingVO prePayVO =
             * promotionCaseManager.calPrepayFee(vo.getApplyId()); if (null !=
             * prePayVO) { // 提前还款违约金
             * vo.setPrepayFee(prePayVO.getActualPrepayFee());
             *
             * // 提前还款节约付利息 BigDecimal cutInterest = new BigDecimal(0.00); //
             * 提交还款节省利息 cutInterest =
             * CostUtils.calPrePayInterest(loanRepayPlan.getInterest(),
             * vo.getApproveTerm(), (vo.getPrepayDays()));
             *
             * // 当前利息 未到期情况 BigDecimal curInterest =
             * CostUtils.sub(loanRepayPlan.getInterest(), cutInterest); //
             * 当前利息（未到期情况） vo.setInterest(curInterest); // 应还本息 BigDecimal
             * actualTotalAmount = CostUtils.add(prePayVO.getActualPrepayFee(),
             * CostUtils.sub(loanRepayPlan.getTotalAmount(), cutInterest));
             * vo.setCurToltalRepayAmt(actualTotalAmount); if
             * (vo.getPrepayDays() < 8) { // 近7天应还金额（不含提前还款手续费）
             * vo.setUnPayAmtNear7(CostUtils.sub(loanRepayPlan.getTotalAmount(),
             * cutInterest)); } } //获取产品信息判断是否可以提前还款 LoanProductVO productVO =
             * loanProductManager
             * .getLoanProductDetail(LoanProduct.DEF_PRODUCT_ID); if (null !=
             * productVO) { // 判断是否允许提前还款 Integer minLoanDay =
             * productVO.getMinLoanDay(); if (null == minLoanDay) { minLoanDay =
             * 0; } if (pastDays > minLoanDay) {
             * vo.setPermitPrePay(LoanApplySimpleVO.YES); } else {
             * vo.setPermitPrePay(LoanApplySimpleVO.NO); } } } else {
             * vo.setPermitPrePay(LoanApplySimpleVO.NO); }
             */
        }
    }

    /**
     * 申请单是否完结
     *
     * @param param
     * @return
     */
    private boolean isOverLoan(Integer param) {
        return (null != param && param == ApplyStatusEnum.FINISHED.getValue());
    }

    /**
     * 获取提现情况
     *
     * @param vo
     */
    private void getCashInfo(LoanApplySimpleVO vo) {
        // 调用提现接口
        Contract contract = contractManager.getByApplyId(vo.getApplyId());
        if (null != contract) {
            // 提现状态
            // vo.setCashAmt(contract.getPrincipal().doubleValue());
            vo.setToAccounttime(DateUtils.formatDateTime(contract.getPayTime()));
        }
    }

    @Override
    @Transactional
    public SaveApplyResultVO saveLoanApply(LoanApplyOP loanApplyOP) {
        // 构造返回对象
        SaveApplyResultVO saveRzVO = new SaveApplyResultVO();
        logger.info("LoanApplyService saveLoanApply userId:{}", loanApplyOP.getUserId());
        // 构造入参
        LoanApply loanApply = new LoanApply();
        // 申请编号 (订单号)
        _setApplyId(loanApplyOP, loanApply);
        // 个人信息 (用户ID，身份证号，手机号，用户名称，渠道码，门店ID )
        boolean flag = _setUserInfo(loanApplyOP, loanApply);
        if (!flag) {
            logger.error("用户信息不存在");
            saveRzVO.setMessage("用户信息不存在");
            return saveRzVO;
        }
        // 产品信息 (产品ID，产品名，还款方式，还款间隔单位，还款间隔)
        flag = _setProductInfo(loanApplyOP, loanApply);
        if (!flag) {
            logger.error("无产品信息1");
            saveRzVO.setMessage("无产品信息");
            return saveRzVO;
        }
        // 营销方案 (营销方案ID，年化利率，实际利率，实际总利息：按日利率计算，服务费，罚息，逾期管理费率，折扣)
        flag = _setPromotionCaseInfo(loanApplyOP, loanApply);
        if (!flag) {
            logger.error("无产品信息2");
            saveRzVO.setMessage("无产品信息");
            return saveRzVO;
        }
        // 申请信息 (申请金额，申请期限，借款用途，ip，来源，申请时间，订单状态)
        // 分期设置：还款方式，还款间隔单位，还款间隔，实际总利息：按年利率/360计算，服务费
        _setApplyInfo(loanApplyOP, loanApply);
        // 入库
        _save2DB(loanApplyOP, loanApply, saveRzVO);
        return saveRzVO;
    }

    private void _setApplyId(LoanApplyOP loanApplyOP, LoanApply loanApply) {
        LoanProductEnum productEnum = LoanProductEnum.get(loanApplyOP.getProductId());
        switch (productEnum) {
            case TFL:
                loanApply.setId(IdUtils.getApplyId(loanApplyOP.getProductType()));
                break;
            case LYFQ:
                loanApply.setId(IdUtils.getApplyId(loanApplyOP.getProductType()));
                break;
            default:
                // if (StringUtils.isBlank(loanApplyOP.getApplyId())) {
                // ApplyIdVO applyIdVO =
                // loanApplyManager.getApplyId(loanApplyOP.getUserId());
                // loanApply.setId(applyIdVO.getApplyId());
                // } else {
                // loanApply.setId(loanApplyOP.getApplyId());
                // }
                loanApply.setId(IdUtils.getApplyId(loanApplyOP.getProductType()));
                break;
        }
    }

    private boolean _setUserInfo(LoanApplyOP loanApplyOP, LoanApply loanApply) {
        boolean flag = false;
        String userId = loanApplyOP.getUserId();
        if (null != userId) {
            // 从缓存中获取
            CustUserVO custUserVO = (CustUserVO) JedisUtils.getObject(Global.USER_CACHE_PREFIX + userId);
            if (null == custUserVO) {
                // 从数据库获取
                custUserVO = custUserService.getCustUserById(userId);
            }

            // 缓存中获取个人信息
            if (null != custUserVO) {
                // 客户编号
                loanApply.setUserId(userId);
                // 身份证号
                loanApply.setIdNo(custUserVO.getIdNo());
                // 手机号
                loanApply.setMobile(custUserVO.getMobile());
                // 客户名称
                loanApply.setUserName(custUserVO.getRealName());
                // 渠道码
                loanApply.setChannelId(
                        custUserVO.getChannel() != null ? custUserVO.getChannel() : loanApplyOP.getChannelId());
                // 设置门店id
                loanApply.setCompanyId(custUserVO.getRemark());
                flag = true;
            }
        }
        return flag;
    }

    private boolean _setProductInfo(LoanApplyOP loanApplyOP, LoanApply loanApply) {
        boolean flag = false;
        // 产品信息
        String productId = loanApplyOP.getProductId();
        // 产品id
        loanApply.setProductId(productId);
        // 产品类型
        loanApply.setProductType(loanApplyOP.getProductType());

        // 获取产品信息
        LoanProductVO proinfo = loanProductManager.getLoanProductDetail(productId);
        if (proinfo == null)
            return flag;
        // 产品名称
        loanApply.setProductName(proinfo.getName());
        // 产品逾期可宽限天数
        loanApply.setGraceDay(proinfo.getGraceDay());
        // 还款方式（1按月等额本息，2按月等额本金，3次性还本付息，4按月付息、到期还本，5按等额本息）
        loanApply.setRepayMethod(proinfo.getRepayMethod());
        // 还款间隔单位（M-月、Q-季、Y-年、D-天）,多期情况下有值
        loanApply.setRepayFreq(proinfo.getRepayFreq());
        // 还款间隔（1）,多期情况下有值
        loanApply.setRepayUnit(proinfo.getRepayUnit());
        flag = true;
        return flag;
    }

    private boolean _setPromotionCaseInfo(LoanApplyOP loanApplyOP, LoanApply loanApply) {
        boolean flag = false;
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        promotionCaseOP.setApplyAmt(loanApplyOP.getApplyAmt());
        promotionCaseOP.setApplyTerm(loanApplyOP.getApplyTerm());
        promotionCaseOP.setProductId(loanApplyOP.getProductId());

        promotionCaseOP.setChannelId(loanApplyOP.getChannelId());

        PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
        if (null != promotionCase && "1".equals(promotionCase.getStatus())) {
            loanApply.setPromotionCaseId(promotionCase.getId());
            // 利率（年化）
            BigDecimal ratePerYear = promotionCase.getRatePerYear();
            loanApply.setBasicRate(ratePerYear);
            // 折扣贴息比例
            BigDecimal discountVal = promotionCase.getDiscountValue();
            // 实际利率=基础利率*（1-折扣贴现率）
            loanApply.setActualRate(CostUtils.calActualYearRate(ratePerYear, discountVal));
            // 实际利息（按天）
            BigDecimal dayInterest = CostUtils.calActualDayInterest(promotionCase.getRatePerDay(),
                    loanApplyOP.getApplyAmt(), discountVal);
            // 实际利息(总)
            loanApply.setInterest(CostUtils.calTotalInterest(dayInterest, loanApplyOP.getApplyTerm()));
            // 服务费费率
            loanApply.setServFeeRate(promotionCase.getServFeeRate());
            // 服务费
            BigDecimal servFee = CostUtils.calServFee(promotionCase.getServFeeRate(), loanApplyOP.getApplyAmt());
            loanApply.setServFee(servFee);
            // 罚息利率
            loanApply.setOverdueRate(promotionCase.getOverdueValue());
            // 逾期管理费/天
            loanApply.setOverdueFee(promotionCase.getOverdueFee());
            // 还款期数
            loanApply.setTerm(LoanApply.DEF_REPAY_TERM);
            loanApply.setDiscount(promotionCase.getDiscount());
            // 折扣金额
            BigDecimal discountAmt = CostUtils.calDiscountAmt(promotionCase.getRatePerYear(), discountVal,
                    loanApplyOP.getApplyTerm());
            loanApply.setDiscountAmt(discountAmt);
            // 折扣比率
            loanApply.setDiscountRate(discountVal);
            // 折扣原因
            loanApply.setDiscountReason(promotionCase.getRemark());
            flag = true;
        }
        return flag;
    }

    private void _setApplyInfo(LoanApplyOP loanApplyOP, LoanApply loanApply) {
        // 扩展信息处理
        Map<String, String> extInfo = new HashMap<String, String>();
        // 白骑士设备id bqsTokenKey
        extInfo.put(Global.BQS_KEY, loanApplyOP.getBqsTokenKey());
        // 同盾设备id tdBlackBox
        extInfo.put(Global.TD_KEY, loanApplyOP.getTdBlackBox());
        // 设置扩展信息
        loanApply.setExtInfo(JsonMapper.getInstance().toJson(extInfo));
        // 申请金额
        loanApply.setApplyAmt(loanApplyOP.getApplyAmt());
        // 申请期限
        loanApply.setApplyTerm(loanApplyOP.getApplyTerm());
        // 核批金额
        loanApply.setApproveAmt(loanApplyOP.getApplyAmt());
        // 还款期数
        loanApply.setTerm(loanApplyOP.getTerm());
        // 机器自动通过时开启
        // if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
        // loanApply.setApproveAmt(new BigDecimal(1000));// 现金贷默认核批金额为1000
        // }
        // 核批期限
        loanApply.setApproveTerm(loanApplyOP.getApplyTerm());
        // 借款用途
        loanApply.setPurpose(loanApplyOP.getPurpose());
        // ip
        loanApply.setIp(loanApplyOP.getIp());
        // 来源
        loanApply.setSource(loanApplyOP.getSource());
        // 渠道码
        // loanApply.setChannelId(loanApplyOP.getChannelId());
        // 流程阶段
        loanApply.setStage(XjdLifeCycle.LC_APPLY);
        // 流程阶段状态
        loanApply.setStatus(XjdLifeCycle.LC_APPLY_1);
        // 申请日期
        loanApply.setApplyDate(DateUtils.getYYYYMMDD2Int(new Date()));
        // 申请时间
        loanApply.setApplyTime(new Date());
        // 申请单(订单)状态
        loanApply.setApplyStatus(ApplyStatusEnum.UNFINISH.getValue());
        // 分期产品设置
        LoanApplyHelper.setFenqiInfoForSave(loanApply);
    }

    private void _save2DB(LoanApplyOP loanApplyOP, LoanApply loanApply, SaveApplyResultVO saveRzVO) {
        String userId = loanApplyOP.getUserId();

        // 查询至此历史申请贷款次数
        int applyCount = loanApplyManager.countApply(userId);
        // 历史放款成功并还款完结的次数
        int loanSuccCount = loanApplyManager.countOverLoanByRepay(userId);

        int applyResult = 0;
        // 判断当前申请编号是否有记录
        int count = loanApplyManager.countById(loanApply.getId());
        loanApply.setLoanSuccCount(loanSuccCount);
        if (count == 0) {
            // 保存
            loanApply.setNewRecord(true);
            loanApply.preInsert();
            applyResult = loanApplyManager.saveLoanApplyInfo(loanApply);
        } else {
            // 更新申请单数据
            loanApply.preUpdate();
            applyResult = loanApplyManager.updateLoanApplyInfo(loanApply);
        }

        // 生成用户信息快照
        // 统计该申请单快照情况
        int infosnapCount = userInfoHistoryManager.countByApplyId(loanApply.getId());
        int snapRz = 0;
        if (infosnapCount == 0) {
            // 保存快照信息
            snapRz = userInfoHistoryManager.saveUserInfoSnap(loanApply.getId(), userId, applyCount, loanSuccCount);
            if (snapRz == 0) {
                logger.error("保存用户信息快照失败");
                saveRzVO.setMessage("保存用户信息快照失败");
                return;
            }
        } else {
            logger.warn("申请单[{}]，重复提交保存快照。", loanApply.getId());
        }

        // 生成紧急联系人快照
        // 过滤同一申请编号多次提交
        contactHistoryManager.delContactSnap(loanApply.getId());
        // 保存快照信息
        snapRz = contactHistoryManager.saveContactSnap(loanApply.getId(), userId);
        if (snapRz == 0) {
            logger.error("保存紧急联系人快照失败");
            saveRzVO.setMessage("保存紧急联系人快照失败");
            return;
        }
        if (applyResult == 1) {
            saveRzVO.setSuccess(true);
            saveRzVO.setApplyId(loanApply.getId());
            return;
        } else {
            logger.error("loanApplyManager 保存申请信息失败");
            saveRzVO.setMessage("保存申请信息失败");
            return;
        }
    }

    @Override
    public ContentTableVO contentTable(ContentTableOP op) {
        /** 贷款申请单 */
        LoanApply loanApply = loanApplyManager.getLoanApplyById(op.getApplyId());
        if (loanApply == null) {
            return new ContentTableVO();
        }
        /** 合同 */
        Contract contract = contractManager.getByApplyId(op.getApplyId());
        /** 产品 */
        Product product = productManager.getById(op.getProductId());
        /** 营销方案 */
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());
        List<RepayDetailListVO> voList = null;
        if (contract != null) {
            /** 还款计划 */
            Page voPage = new Page(1, Integer.MAX_VALUE);
            RepayDetailListOP repayDetailListOP = new RepayDetailListOP();
            repayDetailListOP.setContNo(op.getContNo());
            voList = repayPlanItemManager.repayDetailList(voPage, repayDetailListOP);
        }

        return assembleContentTableVO(loanApply, contract, product, promotionCase, voList);
    }

    /**
     * 贷款单审核接口
     * <p/>
     * 操作入口上分为系统自动审核和人工审核； 审核结果上分为通过和不通过。
     * <p/>
     * 业务逻辑： 1.修改贷款单状态 2.增加操作日志 3.发送短信通知 4.借款标的推送
     *
     * @param op
     * @return
     */
    @Transactional
    public Boolean approve(LoanCheckOP op) {
        String loanApproveingLockCacheKey = "loan_approveing_lock_" + op.getApplyId();
        synchronized (LoanApplyServiceImpl.class) {
            String loanApproveingLock = JedisUtils.get(loanApproveingLockCacheKey);
            if (loanApproveingLock == null) {
                // 加锁，防止并发
                JedisUtils.set(loanApproveingLockCacheKey, "locked", 30 * 60);
            } else {
                logger.warn("贷款单审核接口调用中，applyId= {}", op.getApplyId());
                return Boolean.FALSE;
            }
        }
        try {
            Date now = new Date();
            Boolean auto;

            BigDecimal approveAmt = op.getApproveAmt();
            Integer approveTerm = op.getApproveTerm();
            Integer repayTerm = op.getRepayTerm();
            Integer borrowType = op.getBorrowType();
            BigDecimal servFeeRate = op.getServFeeRate();
            String productId = op.getProductId();

            String operatorId = StringUtils.isNotBlank(op.getOperatorId()) ? op.getOperatorId()
                    : Global.DEFAULT_OPERATOR_ID;
            String operatorName = StringUtils.isNotBlank(op.getOperatorName()) ? op.getOperatorName()
                    : Global.DEFAULT_OPERATOR_NAME;

            if (approveAmt == null || approveAmt.compareTo(new BigDecimal(0)) <= 0) {
                logger.error("调用贷款单审核接口，审批金额<=0，applyId= {}，approveAmt= {}", op.getApplyId(), approveAmt);
                return Boolean.FALSE;
            }
            if (approveTerm == null || approveTerm <= 0) {
                logger.error("调用贷款单审核接口，审批期限<=0，applyId= {}，approveTerm= {}", op.getApplyId(), approveTerm);
                return Boolean.FALSE;
            }
            if (repayTerm == null || repayTerm <= 0) {
                logger.error("调用贷款单审核接口，还款期数<=0，applyId= {}，repayTerm= {}", op.getApplyId(), repayTerm);
                return Boolean.FALSE;
            }
            if (borrowType == null || borrowType <= 0) {
                logger.error("调用贷款单审核接口，标的类型<=0，applyId= {}，borrowType= {}", op.getApplyId(), borrowType);
                return Boolean.FALSE;
            }
            if (servFeeRate == null || servFeeRate.compareTo(new BigDecimal(0)) < 0) {
                logger.error("调用贷款单审核接口，服务费率<0，applyId= {}，servFeeRate= {}", op.getApplyId(), servFeeRate);
                return Boolean.FALSE;
            }
            if (StringUtils.isBlank(productId)) {
                logger.error("调用贷款单审核接口，产品为空，applyId= {}，productId= {}", op.getApplyId(), productId);
                return Boolean.FALSE;
            }
            LoanApply loanApply = loanApplyManager.getLoanApplyById(op.getApplyId());
            if (loanApply == null) {
                logger.error("调用贷款单审核接口，数据不存在，applyId= {}", op.getApplyId());
                return Boolean.FALSE;
            }
            LoanApply updateEntity = new LoanApply();
            updateEntity = BeanMapper.map(loanApply, LoanApply.class);
            updateEntity.setUserCreditLine(op.getAutoApproveStatus2());

            Integer status = loanApply.getStatus();
            // String remark = StringUtils.isNotBlank(loanApply.getRemark()) ?
            // loanApply.getRemark() : "";
            // remark = StringUtils.isNotBlank(op.getRemark()) ? remark + ";" +
            // op.getRemark() : remark;

            // 人工审核通过时，根据是否上传企业执照修改用户类型为企业
            if (MANUAL_CHECK.getValue().equals(status) || WAITING_MANUALCHECK.getValue().equals(status)) {
                if (op.getCheckResult().equals(1)) {
                    CustUserVO userVO = custUserService.getCustUserById(loanApply.getUserId());
                    if (userVO == null) {
                        logger.error("调用贷款单审核接口，用户不存在，userId= {}", loanApply.getUserId());
                        return Boolean.FALSE;
                    }
/*					if (StringUtils.isBlank(userVO.getType())
							|| UserTypeEnum.personal.getId().equals(userVO.getType())) {
						FileInfoVO file = custUserService.getEnterpriseLicenseFileInfo(loanApply.getUserId());
						if (file != null) {
							userVO.setType(UserTypeEnum.enterprise.getId());
							custUserService.updateCustUser(userVO);
						}
					}*/
                }
                JedisUtils.del(Global.UPLOAD_ENTERPRISE_LICENSE_PREFIX + loanApply.getUserId());
            }

            updateEntity.setApplyStatus(op.getCheckResult().equals(2) ? ApplyStatusEnum.FINISHED.getValue()
                    : ApplyStatusEnum.UNFINISH.getValue());
            // 自动审核
            if (WAITING_AOTUCHECK.getValue().equals(status) || APPLY_SUCCESS.getValue().equals(status)) {
                auto = true;
                updateEntity.setOperatorId(operatorId);
                updateEntity.setOperatorName(operatorName);
                updateEntity.setApproverId(operatorId);
                updateEntity.setApproverName(operatorName);
                status = op.getCheckResult().equals(1) ? WAITING_PUSH.getValue()
                        : op.getCheckResult().equals(2) ? AOTUCHECK_NO_PASS.getValue() : WAITING_MANUALCHECK.getValue();
                updateEntity.setStatus(status);
                // updateEntity.setRemark(remark);
                updateEntity.setUpdateBy(operatorName);
                if (!op.getCheckResult().equals(3)) {
                    updateEntity.setApproveResult(op.getCheckResult().equals(1) ? 1 : 2);
                    updateEntity.setApproveTime(now);
                }

                if (op.getCheckResult().equals(3)) {
                    /* 机审通过发送短信 */
                    sendSMS(updateEntity);
                }

                // 人工审核
            } else if (MANUAL_CHECK.getValue().equals(status) || WAITING_MANUALCHECK.getValue().equals(status)
                    || MANUAL_RECHECK.getValue().equals(status)) {
                auto = false;
                updateEntity.setApproveResult(op.getCheckResult().equals(1) ? 3 : 4);

/*				if (op.isLevel()) { // 初级
					status = op.getCheckResult().equals(1) ? MANUAL_RECHECK.getValue() : MANUALCHECK_NO_PASS.getValue();
					updateEntity.setOperatorId(operatorId);
					updateEntity.setOperatorName(operatorName);
					op.setCheckResult(4);
				} else {
				}*/
                status = op.getCheckResult().equals(1) ? WAITING_PUSH.getValue() : MANUALCHECK_NO_PASS.getValue();
                updateEntity.setApproverId(operatorId);
                updateEntity.setApproverName(operatorName);

                updateEntity.setStatus(status);
                // updateEntity.setRemark(remark);
                updateEntity.setUpdateBy(operatorName);
                updateEntity.setApproveTime(now);

            } else {
                logger.error("调用贷款单审核接口，数据状态不正确，applyId = " + op.getApplyId() + " , status = " + status);
                return Boolean.FALSE;
            }
            updateEntity.setStage(ApplyStatusLifeCycleEnum.getStage(status));
            if (op.getCheckResult().equals(1)) {
                // 合同编号生成
                String contractNo = CONTRACT_PREFIX + updateEntity.getId();
                updateEntity.setContNo(contractNo);
				/*if (!LoanProductEnum.XJD.getId().equals(loanApply.getProductId())
						&& !LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
					String ccdContractNo = getContractNo(loanApply.getCompanyId());
					updateEntity.setContNo(StringUtils.isNotBlank(ccdContractNo) ? ccdContractNo : contractNo);
				}*/
            }

            // ytodo 复贷减免
            /*
             * if (op.getDerate() != null && op.getDerate() == 1) {
             * //单期1500产品每天有20个名额服务费为0，审批金额1200，年化利率0.24 BigDecimal basicRate =
             * new BigDecimal(0.24); approveAmt = new BigDecimal(1200);
             * servFeeRate = BigDecimal.ZERO;
             * updateEntity.setBasicRate(basicRate);
             * updateEntity.setActualRate(CostUtils.calActualYearRate(basicRate,
             * updateEntity.getDiscountRate())); }
             */
            updateEntity.setProductId(productId);
            updateEntity.setApproveAmt(approveAmt);
            updateEntity.setApproveTerm(approveTerm);
            updateEntity.setTerm(repayTerm);
            updateEntity.setUpdateTime(now);
            // 修改服务费和费率
            BigDecimal servFee = CostUtils.calServFee(servFeeRate, approveAmt);
            updateEntity.setServFee(servFee);
            updateEntity.setServFeeRate(servFeeRate);
            // 分期产品设置
            LoanApplyHelper.setFenqiInfoForApprove(loanApply, updateEntity);

            int success = loanApplyManager.updateLoanApplyInfo(updateEntity);
            if (success == 0) {
                return Boolean.FALSE;
            }
            logger.info("审核订单 : {},{},{}", loanApply.getId(), loanApply.getStatus(), updateEntity.getStatus());
            loanApply.setApproveResult(updateEntity.getApproveResult());
            if (op.getCheckResult().equals(1)) {
                /** 借款标的推送 */
/*				if ((LoanProductEnum.CCD.getId().equals(updateEntity.getProductId())
						|| LoanProductEnum.ZJD.getId().equals(updateEntity.getProductId())
						|| LoanProductEnum.TYD.getId().equals(updateEntity.getProductId())
						|| LoanProductEnum.LYFQ.getId().equals(updateEntity.getProductId())
						|| LoanProductEnum.TFL.getId().equals(updateEntity.getProductId()))
						&& DateUtils.isAfter(updateEntity.getCreateTime(),
								DateUtils.parse("2018-03-25", "yyyy-MM-dd"))) {
					// saveBorrowInfo(updateEntity, borrowType);
				}*/
                Integer tempStatus = auto ? AOTUCHECK_PASS.getValue() : MANUALCHECK_PASS.getValue();
                /** 审核通过，需要增加审核操作日志 */
                saveApproveLog(op, now, auto, operatorId, operatorName, loanApply, tempStatus);// 从待审核变为审核通过
/*				if (!applyTripartiteService.isExistApplyId(op.getApplyId())
						&& !applyTripartiteRong360Service.isExistApplyId(op.getApplyId())) {
					sendSMS(updateEntity);
				}*/
                /** app消息 */
                /*				saveMessage(updateEntity, true);*/
            } else if (op.getCheckResult().equals(2)) {
                if (!reject2rengong(updateEntity)) {
                    if (StringUtils.isNotBlank(op.getRefuseId())) {
                        /** 不通过时记录数据 */
                        refuseReasonManager.record(op.getRefuseId());
                    }
                    /** 消息 */
                    saveMessage(loanApply, false);
                    saveApproveLog(op, now, auto, operatorId, operatorName, loanApply, status);
                }
            } else if (op.getCheckResult().equals(4)) {
                saveApproveLog(op, now, auto, operatorId, operatorName, loanApply, status);
            }
            return Boolean.TRUE;
        } finally {
            // 移除锁
            JedisUtils.del(loanApproveingLockCacheKey);
        }
    }

    /**
     * 分期拒绝订单转单期（人工/通过）
     *
     * @return
     */
    private boolean reject2rengong(LoanApply updateEntity) {
        if (!LoanProductEnum.XJDFQ.getId().equals(updateEntity.getProductId())) {
            return false;
        }
        String applyId = updateEntity.getId();

        List<HitRuleVO> hitRuleList = riskService.getHitRuleList(applyId, null);
        for (HitRuleVO vo : hitRuleList) {
            if ("10020001".equals(vo.getRuleId()) || "10030028".equals(vo.getRuleId())) {
                return false;
            }
        }

        boolean isConvert = false;
        int status = XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0;// 默认人工待审批
        BigDecimal approveAmt = new BigDecimal(1300);
        int approveTerm = 14;

        UserInfoHistory userInfoHistory = userInfoHistoryManager.getByApplyId(applyId);
        int loanSuccCount = userInfoHistory == null ? 0 : userInfoHistory.getLoanSuccCount();
        if (loanSuccCount > 0) {
            isConvert = true;
            status = XjdLifeCycle.LC_RAISE_0;// 审核通过订单状态变为待推送
            LoanApply lastApply = loanApplyManager.getLastRepayedByUserId(updateEntity.getUserId(),
                    LoanProductEnum.XJD.getId());
            if (lastApply != null) {
                approveAmt = lastApply.getApproveAmt();
                approveTerm = lastApply.getApplyTerm();
            }
            int maxTerm = 14;
            BigDecimal maxAmt = new BigDecimal(1500);
            if (approveTerm > maxTerm) {
                approveTerm = maxTerm;
            }
            if (approveAmt.compareTo(maxAmt) > 0) {
                approveAmt = maxAmt;
            }
        } else {
            for (HitRuleVO vo : hitRuleList) {
                if (RiskSourceEnum.MOXIE.getId().equals(vo.getSource())) {
                    isConvert = true;
                    break;
                }
            }
        }
        if (isConvert) {
            PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
            promotionCaseOP.setApplyAmt(approveAmt);
            promotionCaseOP.setApplyTerm(approveTerm);
            promotionCaseOP.setProductId(LoanProductEnum.XJD.getId());
            promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
            PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
            if (null != promotionCase) {
                updateEntity.setPromotionCaseId(promotionCase.getId());
                // 利率（年化）
                BigDecimal ratePerYear = promotionCase.getRatePerYear();
                updateEntity.setBasicRate(ratePerYear);
                // 折扣贴息比例
                BigDecimal discountVal = promotionCase.getDiscountValue();
                // 实际利率=基础利率*（1-折扣贴现率）
                updateEntity.setActualRate(CostUtils.calActualYearRate(ratePerYear, discountVal));
                // 实际利息（按天）
                BigDecimal dayInterest = CostUtils.calActualDayInterest(promotionCase.getRatePerDay(), approveAmt,
                        discountVal);
                // 实际利息(总)
                updateEntity.setInterest(CostUtils.calTotalInterest(dayInterest, approveTerm));
                // 服务费费率
                updateEntity.setServFeeRate(promotionCase.getServFeeRate());
                // 服务费
                BigDecimal servFee = CostUtils.calServFee(promotionCase.getServFeeRate(), approveAmt);
                updateEntity.setServFee(servFee);
                // 罚息利率
                updateEntity.setOverdueRate(promotionCase.getOverdueValue());
                // 逾期管理费/天
                updateEntity.setOverdueFee(promotionCase.getOverdueFee());
                // 还款期数
                updateEntity.setTerm(LoanApply.DEF_REPAY_TERM);
                updateEntity.setDiscount(promotionCase.getDiscount());
                // 折扣金额
                BigDecimal discountAmt = CostUtils.calDiscountAmt(promotionCase.getRatePerDay(), discountVal,
                        approveTerm);
                updateEntity.setDiscountAmt(discountAmt);
                // 折扣比率
                updateEntity.setDiscountRate(discountVal);
                // 折扣原因
                updateEntity.setDiscountReason(promotionCase.getRemark());

                // 订单状态
                updateEntity.setStage(ApplyStatusLifeCycleEnum.getStage(status));
                updateEntity.setStatus(status);
                updateEntity.setApplyStatus(ApplyStatusEnum.UNFINISH.getValue());
                updateEntity.setApproveResult(1);
                updateEntity.setProductId(LoanProductEnum.XJD.getId());
                updateEntity.setProductName(LoanProductEnum.XJD.getName());
                updateEntity.setProductType("0");
                updateEntity.setApproveAmt(approveAmt);
                updateEntity.setApproveTerm(approveTerm);
                updateEntity.setApplyAmt(new BigDecimal(3000));
                updateEntity.setApplyTerm(14);
                updateEntity.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
                updateEntity.setRepayFreq("");
                updateEntity.setRepayUnit(BigDecimal.ZERO);
                // 合同编号生成
                String contractNo = CONTRACT_PREFIX + updateEntity.getId();
                updateEntity.setContNo(contractNo);
                if (!LoanProductEnum.XJD.getId().equals(updateEntity.getProductId())
                        && !LoanProductEnum.XJDFQ.getId().equals(updateEntity.getProductId())) {
                    String ccdContractNo = getContractNo(updateEntity.getCompanyId());
                    updateEntity.setContNo(StringUtils.isNotBlank(ccdContractNo) ? ccdContractNo : contractNo);
                }
                int success = loanApplyManager.updateLoanApplyInfo(updateEntity);
                if (success == 0) {
                    return false;
                }
                OperationLogLater operationLog = new OperationLogLater();
                if (XjdLifeCycle.LC_RAISE_0 == status) {
                    Integer tmpStatus = AOTUCHECK_PASS.getValue();// 自动审批通过
                    operationLog.setStatus(tmpStatus);
                    operationLog.setStage(ApplyStatusLifeCycleEnum.getStage(tmpStatus));
                    /** 发送短信 */
                    if (!applyTripartiteService.isExistApplyId(applyId)) {
                        sendSMS(updateEntity);
                    }
                    /** 消息 */
                    saveMessage(updateEntity, true);
                } else {
                    operationLog.setStatus(status);
                    operationLog.setStage(ApplyStatusLifeCycleEnum.getStage(status));
                }
                operationLog.setPreviousStage(updateEntity.getStage());
                operationLog.setPreviousStatus(updateEntity.getStatus());
                operationLog.setApplyId(updateEntity.getId());
                operationLog.setUserId(updateEntity.getUserId());
                operationLog.setRemark("现金贷分期被拒订单自动转急速钱包");
                operationLog.setIp("127.0.0.1");
                operationLog.setSource(Global.SOURCE_SYSTEM);
                operationLog.preInsert();
                operationLog.setTime(new Date());
                operationLog.setOperatorId("system");
                operationLog.setOperatorName("自动审批系统");
                operationLog.setCreateBy("system");
                operationLog.setUpdateBy("自动审批系统");
                operationLogLaterManager.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    /**
     * 更新贷款申请进件的状态,保存日志
     *
     * @param applyId
     */
    public int updateApplyStatus(String applyId, int status) {
        int updateRow = 0;
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        loanApply.setStatus(status);
        loanApply.setStage(ApplyStatusLifeCycleEnum.getStage(status));
        updateRow = loanApplyManager.updateStatusAndSaveLog(loanApply);
        return updateRow;
    }

    /**
     * 保存移动端通知
     *
     * @param loanApply
     * @param pass
     */
    private void saveMessage(LoanApply loanApply, boolean pass) {
        try {
            BigDecimal interestPerDay = loanApply.getApproveAmt().multiply(loanApply.getActualRate())
                    .divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_HALF_UP);
            String content = "";
            if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId()) && loanApply.getTerm().intValue() > 1) {
                content = pass
                        ? String.format(ShortMsgTemplate.MSG_TEMP_APPROVE, loanApply.getUserName(),
                        loanApply.getApproveAmt(), interestPerDay)
                        : ShortMsgTemplate.MSG_TEMP_APPROVE_FAIL;
            } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
                content = pass
                        ? String.format(ShortMsgTemplate.MSG_TEMP_APPROVE3, loanApply.getUserName(),
                        loanApply.getApproveAmt(), loanApply.getTerm())
                        : ShortMsgTemplate.MSG_TEMP_APPROVE_FAIL;
            } else {
                content = pass
                        ? String.format(ShortMsgTemplate.MSG_TEMP_APPROVE, loanApply.getUserName(),
                        loanApply.getApproveAmt(), interestPerDay)
                        : ShortMsgTemplate.MSG_TEMP_APPROVE_FAIL;
            }

            Message message = new Message();
            message.preInsert();
            message.setUserId(loanApply.getUserId());
            message.setTitle(
                    pass ? ShortMsgTemplate.MSG_TEMP_APPROVE_TITLE : ShortMsgTemplate.MSG_TEMP_APPROVE_FAIL_TITLE);
            message.setContent(content);
            message.setType(Global.CUST_MESSAGE_TYPE_SYS);
            message.setNotifyTime(new Date());
            message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
            message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
            message.setStatus(1);
            message.setDel(0);
            messageManager.insert(message);
        } catch (Exception e) {
            logger.error("发送APP借款审批通知失败,applyId = " + loanApply.getId() + ", pass = " + pass, e);
        }

    }

    /**
     * 发送审核短信
     *
     * @param loanApply
     */
    private void sendSMS(LoanApply loanApply) {
        try {
            SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
            sendShortMsgOP.setMsgType(1);// 初审通过
            sendShortMsgOP.setUserName(loanApply.getUserName());
            sendShortMsgOP.setMobile(loanApply.getMobile());
            sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
            sendShortMsgOP.setUserId(loanApply.getUserId());
            sendShortMsgOP.setChannelId(loanApply.getChannelId());
            sendShortMsgOP.setProductId(loanApply.getProductId());
            shortMsgService.sendShortMsg(sendShortMsgOP);
        } catch (Exception e) {
            logger.error("发送审核短信失败，userId=" + loanApply.getUserId() + ", mobile=" + loanApply.getMobile(), e);
        }

    }

    /**
     * 保存标的
     *
     * @param loanApply
     */
    private void saveBorrowInfo(LoanApply loanApply, Integer borrowType, Integer loanPayType) {
        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());

        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(loanApply.getId());
        if (borrowInfo == null) {
            int term = loanApply.getTerm();// 分期期数
            String purpose = loanApply.getPurpose();// 借款用途
/*			if (LoanProductEnum.CCD.getId().equals(loanApply.getProductId())) {
				term = loanApply.getTerm() / 2;
				purpose = LoanPurposeEnum.P1.getId();
			}
			if (LoanProductEnum.ZJD.getId().equals(loanApply.getProductId())
					|| LoanProductEnum.TYD.getId().equals(loanApply.getProductId())) {
				purpose = LoanPurposeEnum.P1.getId();
			}*/
            BigDecimal rate = getBorrowInfoRate(term);

            borrowInfo = new BorrowInfo();
            borrowInfo.setApplyId(loanApply.getId());
            borrowInfo.setUserId(loanApply.getUserId());
            borrowInfo.setUserName(loanApply.getUserName());
/*			if (LoanProductEnum.LYFQ.getId().equals(loanApply.getProductId())) {
				borrowInfo.setTitle(Global.DEFAULT_LYFQ_TITLE);
			} else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
					|| LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
				borrowInfo.setTitle(configService.getValue("xjdfq_borrow_title"));
			} else {
			}*/
            borrowInfo.setTitle(Global.DEFAULT_TITLE);
            borrowInfo.setBorrowAmt(loanApply.getApproveAmt());
            borrowInfo.setBorrowDate(DateUtils.getDate());
            borrowInfo.setAccountId(custUser.getAccountId());
            borrowInfo.setContNo(loanApply.getContNo());
            borrowInfo.setActualRate(rate);
            // 先支付服务费设置为零
            if (loanPayType.intValue() == LoanApplySimpleVO.APPLY_PAY_TYPE_0.intValue()) {
                borrowInfo.setServFeeRate(BigDecimal.ZERO);
            } else {
                borrowInfo.setServFeeRate(loanApply.getServFeeRate());
            }
            borrowInfo.setOverdueRate(loanApply.getOverdueRate());
            borrowInfo.setOverdueFee(loanApply.getOverdueFee());
            borrowInfo.setPrepayValue(promotionCase.getPrepayValue());
            borrowInfo.setPurpose(purpose);
            borrowInfo.setPeriodUnit("D");
            borrowInfo.setPeriod(term);
            borrowInfo.setRepayMethod(loanApply.getRepayMethod());
            borrowInfo.setPartnerId(loanApply.getProductId());
            borrowInfo.setPartnerName(ChannelEnum.getDescByCode(loanApply.getChannelId()));
            borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue());
            borrowInfo.setBorrowType(borrowType);
            borrowInfo.setRemark(String.valueOf(loanPayType));
            if (StringUtils.isBlank(loanApply.getPayChannel())) {
                borrowInfo.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue());
                updatePayChannel(loanApply.getId(), WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue());
            } else {
                borrowInfo.setPayChannel(
                        WithdrawalSourceEnum.getByValue(Integer.parseInt(loanApply.getPayChannel())).getValue());
            }

            // 单期复贷减免活动由 乐视放款
            // ytodo 复贷减免
            /*
             * if (loanApply.getTerm() == 1 && loanApply.getApproveTerm() ==
             * Global.XJD_DQ_DAY_15 &&
             * loanApply.getServFee().compareTo(BigDecimal.ZERO) == 0) {
             * borrowInfo.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_LESHI.
             * getValue()); }
             */
            borrowInfoManager.insert(borrowInfo);
        } else {
            logger.warn("标的已存在，请勿重复提交，参数：" + JsonMapper.getInstance().toJson(loanApply));
        }
    }

    /**
     * 计算推标利率
     */
    private BigDecimal getBorrowInfoRate(int term) {
        BigDecimal rate = new BigDecimal(0);
        if (term >= 1 && term <= 4) {
            rate = new BigDecimal(0.14);
        } else if (term >= 5 && term <= 8) {
            rate = new BigDecimal(0.15);
        } else if (term >= 9 && term <= 24) {
            rate = new BigDecimal(0.17);
        } else if (term >= 25 && term <= 36) {
            rate = new BigDecimal(0.175);
        }
        return rate;
    }

    /**
     * 保存审批日志
     *
     * @param op
     * @param now
     * @param auto
     * @param operatorId
     * @param operatorName
     * @param loanApply
     * @param status
     */
    private void saveApproveLog(LoanCheckOP op, Date now, Boolean auto, String operatorId, String operatorName,
                                LoanApply loanApply, Integer status) {
        try {
            if (auto) {
                return;
            }
            OperationLogLater operationLog = new OperationLogLater();
            operationLog.setPreviousStage(loanApply.getStage());
            operationLog.setPreviousStatus(loanApply.getStatus());
            operationLog.setApplyId(loanApply.getId());
            operationLog.setUserId(loanApply.getUserId());
            operationLog.setStatus(status);
            operationLog.setStage(ApplyStatusLifeCycleEnum.getStage(status));
            if (4 == loanApply.getApproveResult() || 2 == loanApply.getApproveResult()) {
                operationLog.setRefuseId(op.getRefuseId());
            }
            operationLog.setRemark(StringUtils.isNotBlank(op.getRemark()) ? op.getRemark() : "");
            operationLog.setIp(op.getIp());
            operationLog.setSource(auto ? Global.SOURCE_SYSTEM : Global.SOURCE_WEB);
            operationLog.preInsert();
            operationLog.setTime(now);
            operationLog.setOperatorId(operatorId);
            operationLog.setOperatorName(operatorName);
            operationLog.setCreateBy(operatorId);
            operationLog.setUpdateBy(operatorName);
            operationLogLaterManager.insert(operationLog);
        } catch (Exception e) {
            logger.error("保存审核操作日志失败，参数：" + JsonMapper.getInstance().toJson(op), e);
        }
    }

    /**
     * 组装ContentTableVO
     *
     * @param loanApply
     * @param contract
     * @param product
     * @param promotionCase
     * @param voList
     * @return
     */
    private ContentTableVO assembleContentTableVO(LoanApply loanApply, Contract contract, Product product,
                                                  PromotionCase promotionCase, List<RepayDetailListVO> voList) {
        ContentTableVO vo = BeanMapper.map(loanApply, ContentTableVO.class);
        if (contract != null) {
            vo.setGraceType(contract.getGraceType());
            vo.setGraceDay(contract.getGraceDay());
            vo.setLoanEndDate(contract.getLoanEndDate());
            vo.setOverdueFee(contract.getOverdueFee());
        }
        if (product != null) {
            vo.setPrepay(product.getPrepay());
            vo.setMinLoanDay(product.getMinLoanDay());
            vo.setStartInterest(product.getStartInterest());
        }
        if (promotionCase != null) {
            vo.setPrepayFeeType(promotionCase.getPrepayFeeType());
            vo.setPrepayValue(promotionCase.getPrepayValue());
            vo.setOverdueFeeType(promotionCase.getOverdueFeeType());
            vo.setOverdueValue(promotionCase.getOverdueValue());
        }
        if (CollectionUtils.isNotEmpty(voList)) {
            vo.setList(voList);
        }
        if (StringUtils.isNotBlank(vo.getRepayMethod())) {
            vo.setRepayMethod(RepayMethodEnum.getDesc(Integer.valueOf(vo.getRepayMethod())));
        }
        return vo;
    }

    @Override
    public String getCurApplyIdByUserId(String userId) {
        // 参数判断
        if (StringUtils.isBlank(userId)) {
            logger.error(" 参数不合法");
            return null;
        }
        return loanApplyManager.getCurApplyIdByUserId(userId);
    }

    @Override
    public ApplyIdVO getApplyId(String userId) {
        // 参数判断
        if (StringUtils.isBlank(userId)) {
            logger.error(" 参数不合法");
            return null;
        }
        return loanApplyManager.getApplyId(userId);
    }

    @Override
    public AgreementVO getAgreementFactor(AgreementOP agreementOP) {
        // 参数判断
        if (null == agreementOP || null == agreementOP.getUserId()) {
            logger.error("param agreementOP is null!");
        }
        // 返回对象初始化
        AgreementVO rzVo = new AgreementVO();
        // 贷款金额
        rzVo.setLoanAmt(String.valueOf(agreementOP.getApplyAmt().setScale(2)));
        // 贷款金额中文大写
        rzVo.setLoanAmtChinese(MoneyUtils.convert(rzVo.getLoanAmt()));
        // 贷款期限
        rzVo.setLoanTerm(agreementOP.getApplyTerm());
        // 获取申请编号
        String applyId = agreementOP.getApplyId();
        if (StringUtils.isBlank(applyId) && null != agreementOP.getUserId()) {
            ApplyIdVO applyIdVO = getApplyId(agreementOP.getUserId());
            if (null != applyIdVO) {
                applyId = applyIdVO.getApplyId();
            }
        }
        if (StringUtils.isNotBlank(applyId)) {
            // 协议编号（XY+申请编号）
            rzVo.setAgreementNo(Global.AGREEMENT_NO_PREFIX + applyId);
        } else {
            logger.error("the applyId is null!");
        }
        // 获取基本信息
        CustUser info = custUserManager.getById(agreementOP.getUserId());
        if (null != info) {
            // 姓名
            rzVo.setTrueName(info.getRealName());
            // 身份证号码
            rzVo.setIdNo(info.getIdNo());
            // 手机号
            rzVo.setMobile(info.getMobile());
            // 银行代码
            // rzVo.setBankCode(info.getBankCode());
            // 绑定银行卡号
            rzVo.setCardNo(info.getCardNo());
            // 存管电子账户
            rzVo.setAccountId(info.getAccountId());
            // 快捷支付绑定id
            rzVo.setBindId(StringUtils.isNotBlank(info.getBindId()) ? info.getBindId() : info.getProtocolNo());
        }
        // 获取营销信息
        PromotionCaseOP op = new PromotionCaseOP();
        op.setApplyAmt(agreementOP.getApplyAmt());
        op.setApplyTerm(agreementOP.getApplyTerm());
        op.setChannelId(agreementOP.getChannelId());
        op.setProductId(agreementOP.getProductId());
        PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(op);
        if (null != promotionCase) {
            // 总利息
            BigDecimal interest = BigDecimal.ZERO;
            // 本息总额
            BigDecimal totalPI = BigDecimal.ZERO;
            String loanStartDate = "";
            String loanEndDate = "";
            LoanProductEnum productEnum = LoanProductEnum.get(agreementOP.getProductId());
            /*
             * switch (productEnum) { case XJD: interest =
             * CostUtils.calCurInterest(agreementOP.getApplyAmt(),
             * promotionCase.getRatePerYear(), agreementOP.getApplyTerm());
             * totalPI = CostUtils.add(agreementOP.getApplyAmt(), interest);
             * break; default:
             */
            RepayPlanOP repayPlanOP = new RepayPlanOP();
            repayPlanOP.setApplyAmt(agreementOP.getApplyAmt());
            repayPlanOP.setChannelId(agreementOP.getChannelId());
            repayPlanOP.setProductId(agreementOP.getProductId());
            repayPlanOP.setRepayMethod(agreementOP.getRepayMethod());
            repayPlanOP.setRepayTerm(agreementOP.getApplyTerm());
            Map<String, Object> repayPlan = loanRepayPlanService.getRepayPlan(repayPlanOP);
            interest = repayPlan.containsKey("totalInterest")
                    ? new BigDecimal(String.valueOf(repayPlan.get("totalInterest")))
                    : BigDecimal.ZERO;
            totalPI = repayPlan.containsKey("totalAmt") ? new BigDecimal(String.valueOf(repayPlan.get("totalAmt")))
                    : BigDecimal.ZERO;
            loanStartDate = repayPlan.containsKey("loanStartDate") ? repayPlan.get("loanStartDate").toString() : "";
            loanEndDate = repayPlan.containsKey("loanEndDate") ? repayPlan.get("loanEndDate").toString() : "";
            /*
             * break; }
             */
            // 总利息
            rzVo.setInterest(String.valueOf(interest));
            // 本息总额
            rzVo.setTotalPI(String.valueOf(totalPI));
            // 服务费率
            rzVo.setServFeeRate(String.valueOf(promotionCase.getServFeeRate()));
            // 提前还款违约费率
            rzVo.setPrepayFeeRate(String.valueOf(promotionCase.getPrepayValue()));
            // 逾期管理费
            rzVo.setOverdueFee(String.valueOf(promotionCase.getOverdueFee()));
            // 逾期违约罚息费率
            rzVo.setDefaultFeeRate(String.valueOf(promotionCase.getOverdueValue()));
            rzVo.setLoanStartDate(loanStartDate);
            rzVo.setLoanEndDate(loanEndDate);
        }
        // 获取费用方案列表
        List<FeeCeseVO> feeCeseList = promotionCaseManager.getFeeCeseList(op);
        rzVo.setFeeCeseList(feeCeseList);
        // 还款方式
        rzVo.setRepayMethodStr(RepayMethodEnum.get(agreementOP.getRepayMethod()).getDesc());
        return rzVo;
    }

    /**
     * 阿福获取用户记录
     *
     * @param trueName
     * @param idNo
     * @return
     */
    @Override
    public UserRecordVO getUserRecord(String trueName, String idNo) {
        UserRecordVO userRecordVO = new UserRecordVO();
        /** 查询用户是否存在 */
        List<CustUser> userList = custUserManager.findByNameAndIdNoByYixin(trueName, idNo);
        CustUser user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
        }
        if (user == null) {
            return userRecordVO;
        }
        /** 查询用户的贷款申请单*/
        List<LoanApply> loanApplyList = loanApplyManager.getLoanApplyByUserId(user.getId());
        if (CollectionUtils.isEmpty(loanApplyList)) {
            return userRecordVO;
        }
        /** 查询用户的还款计划 */
        List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanManager.findAllByUserId(user.getId());
        /** 查询用户的还款计划明细 */
        List<RepayPlanItem> repayPlanItemList = null;
        if (CollectionUtils.isNotEmpty(loanRepayPlanList)) {
            repayPlanItemList = repayPlanItemManager.getByUserId(user.getId());
        }

        /** 组装loanRecordVOList */
        List<LoanRecordVO> loanRecordVOList = new ArrayList<>();
        for (LoanApply apply : loanApplyList) {
            LoanRecordVO loanRecordVO = new LoanRecordVO();
            loanRecordVO.setName(user.getRealName());
            loanRecordVO.setCertNo(user.getIdNo());
            setParams(loanRecordVO, apply, loanRepayPlanList, repayPlanItemList);
            calcOverdue(loanRecordVO, apply, repayPlanItemList);
            loanRecordVOList.add(loanRecordVO);
        }
        userRecordVO.setLoanRecords(loanRecordVOList);

        /** 组装riskResultVOList */
//        List<HitRuleVO> hitRuleVOList = riskService.getHitRuleList(null, user.getId());
        // 至简提供宜信上报接口修改
        List<HitRuleVO> hitRuleVOList = riskService.getHitRuleList(null, user.getId(), Arrays.asList("A", "B"));
        if (CollectionUtils.isNotEmpty(hitRuleVOList)) {
            List<RiskResultVO> riskResultVOList = new ArrayList<>();
            for (HitRuleVO vo : hitRuleVOList) {
                if (StringUtils.isNotBlank(vo.getRuleName())) {
                    RiskResultVO riskResultVO = new RiskResultVO();
                    riskResultVO.setRiskDetail(vo.getRuleName());
                    riskResultVO.setRiskItemTypeCode("101010");
                    riskResultVO.setRiskItemValue(user.getIdNo());
                    riskResultVO.setRiskTime(DateUtils.formatDate(vo.getCreateTime(), DateUtils.FORMAT_INT_DATE));
                    riskResultVOList.add(riskResultVO);
                }
            }
            userRecordVO.setRiskResults(riskResultVOList);
        }
        return userRecordVO;
    }

    /**
     * 审核状态
     *
     * @param status
     * @return
     */
    private void setStatusCode(LoanRecordVO loanRecordVO, Integer status) {
        if (ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)) {
            // 拒贷
            loanRecordVO.setApprovalStatusCode("203");
            return;
        }
        if (ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL.getValue().equals(status) || ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
            // 客户放弃
            loanRecordVO.setApprovalStatusCode("204");
            return;
        }
        if (status >= ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue()) {
            // 已放款
            loanRecordVO.setApprovalStatusCode("202");
            loanRecordVO.setLoanStatusCode("301");
            return;
        }
        // 审核中
        loanRecordVO.setApprovalStatusCode("201");
    }

    /**
     * 还款状态码
     *
     * @param apply
     * @param loanRepayPlanList
     * @param repayPlanItemList
     * @return
     */
    private void setParams(LoanRecordVO loanRecordVO, LoanApply apply, List<LoanRepayPlan> loanRepayPlanList,
                           List<RepayPlanItem> repayPlanItemList) {
        loanRecordVO.setLoanTypeCode("21");// 信用
        // 默认使用申请时间，如果已放款，使用放款时间（签订时间）
        loanRecordVO.setLoanDate(DateUtils.formatDate(apply.getApplyTime(), DateUtils.FORMAT_INT_DATE));
        // 致诚阿福要求期数固定填1
        loanRecordVO.setPeriods("1");
        loanRecordVO.setLoanAmount(apply.getApplyAmt());
        setStatusCode(loanRecordVO, apply.getStatus());
        /** 没有还款计划的时候，处于放款之前状态，无需还款，还款状态为正常 */
        if (CollectionUtils.isEmpty(loanRepayPlanList)) {
            return;
        }
        /** 找出当前申请单的还款计划 */
        LoanRepayPlan loanRepayPlan = null;
        for (LoanRepayPlan plan : loanRepayPlanList) {
            if (StringUtils.equals(apply.getId(), plan.getApplyId())) {
                loanRepayPlan = plan;
                break;
            }
        }

        if (loanRepayPlan == null) {
            return;
        }

        loanRecordVO.setLoanDate(DateUtils.formatDate(loanRepayPlan.getLoanStartDate(), DateUtils.FORMAT_INT_DATE));
        /** 结清 */
        if (ApplyStatusEnum.FINISHED.getValue().equals(loanRepayPlan.getStatus())) {
            loanRecordVO.setLoanStatusCode("303");
            return;
        }

        Integer currentTerm = loanRepayPlan.getCurrentTerm();
        currentTerm = 1;// 写死一期
        for (RepayPlanItem item : repayPlanItemList) {
            /** 如果往期存在逾期未还款，则返回逾期状态 */
            if (StringUtils.equals(apply.getId(), item.getApplyId()) && item.getThisTerm() < currentTerm
                    && ApplyStatusEnum.UNFINISH.getValue().equals(item.getStatus())) {
                loanRecordVO.setLoanStatusCode("302");
                return;
            }
            /** 如果往期都已还款，当期逾期，则返回逾期状态 */
            if (StringUtils.equals(apply.getId(), item.getApplyId()) && item.getThisTerm().equals(currentTerm)
                    && DateUtils.daysBetween(item.getRepayDate(), new Date()) > 0
                    && ApplyStatusEnum.UNFINISH.getValue().equals(item.getStatus())) {
                loanRecordVO.setLoanStatusCode("302");
                return;
            }

        }
        return;
    }

    /**
     * 计算逾期
     *
     * @param apply
     * @param repayPlanItemList
     * @return
     */
    private void calcOverdue(LoanRecordVO loanRecordVO, LoanApply apply, List<RepayPlanItem> repayPlanItemList) {
        if (CollectionUtils.isEmpty(repayPlanItemList)) {
            return;
        }

        // 逾期总金额
        BigDecimal overdueAmount = BigDecimal.ZERO;
        // 逾期总次数
        int overdueNum = 0;
        // m3次数
        int m3 = 0;
        // m6次数
        int m6 = 0;
        int overdueStatusNum = 0;
        // 应还天数数组
        List<Date> repayDate = new ArrayList<>();
        for (RepayPlanItem item : repayPlanItemList) {
            if (StringUtils.equals(apply.getId(), item.getApplyId())
                    && DateUtils.daysBetween(item.getRepayDate(), new Date()) > 0
                    && ApplyStatusEnum.UNFINISH.getValue().equals(item.getStatus())) {
                // 统计逾期金额
                overdueAmount = overdueAmount.add(item.getTotalAmount());
                // 统计逾期次数
                overdueNum++;
                // 计算本次逾期的跨度
                int termNum = calcTermNum(item.getStartDate(), item.getRepayDate());
                repayDate.add(item.getRepayDate());
                overdueStatusNum = termNum > overdueStatusNum ? termNum : overdueStatusNum;
                m3 = termNum > 3 ? m3 + 1 : m3;
                m6 = termNum > 6 ? m6 + 1 : m6;
            }
        }
        // 如果没有逾期，直接返回
        if (overdueAmount.equals(BigDecimal.ZERO)) {
            return;
        }

        int overDays = 0;
        for (Date date : repayDate) {
            int days = DateUtils.daysBetween(date, new Date());
            if (days > overDays) {
                overDays = days;
            }
        }
        // 逾期状态
        String overdueStatus = setOverdueStatus(overDays);

        loanRecordVO.setOverdueAmount(overdueAmount);
        loanRecordVO.setOverdueStatus(overdueStatus);
        // 致诚阿福要求逾期次数填1或者不填
        loanRecordVO.setOverdueTotal(overdueNum == 0 ? null : 1);
        if (m3 > 0) {
            // 致诚阿福要求填1或者不填
            loanRecordVO.setOverdueM3(1);
        }
        if (m6 > 0) {
            // 致诚阿福要求填1或者不填
            loanRecordVO.setOverdueM6(1);
        }
    }

    /**
     * 换算逾期状态
     *
     * @return
     */
    private String setOverdueStatus(int overdueDays) {
        if (overdueDays > 0 && overdueDays <= 30) {
            return "M1";
        } else if (overdueDays > 30 && overdueDays <= 60) {
            return "M2";
        } else if (overdueDays > 60 && overdueDays <= 90) {
            return "M3";
        } else if (overdueDays > 90) {
            return "M3+";
        }
        return null;
    }

    /**
     * 计算某一期逾期的期数
     *
     * @param start
     * @param end
     * @return
     */
    private int calcTermNum(Date start, Date end) {
        // 计算一期的天数
        int perTermDays = 30;
        // 计算逾期的天数
        int overdueDays = DateUtils.daysBetween(end, new Date());
        int termNum = overdueDays / perTermDays;
        if (overdueDays % perTermDays > 0) {
            termNum = termNum + 1;
        }
        return termNum;
    }

    /**
     * 统计查询审核管理列表
     *
     * @return
     */
    @Override
    public Map<String, Object> getCheckAllotListTotal(String startDate, String endDate, List<String> auditorId,
                                                      String type) {
        return loanApplyManager.getCheckAllotListTotal(startDate, endDate, auditorId, type);
    }

    /**
     * 申请是否受限判断
     */
    @Override
    public boolean isApplyLimit(String userId) {
        boolean rz = false;
        // 查询最近一笔完结申请记录
        LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
        if (null != lastApply) {
            if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                    || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                // 判断上次拒绝更新的时间距现在的天数是否超限
                Date lastUpdateTime = lastApply.getUpdateTime();
                long pastDays = DateUtils.pastDays(lastUpdateTime);
                int minDay = Global.MIN_XJD_APPLY_DAY;
                if (LoanProductEnum.CCD.getId().equals(lastApply.getProductId())) {
                    minDay = Global.MIN_AGAIN_APPLY_DAY;
                }
                if (pastDays < minDay) {
                    return true;
                }
            }
        }
        // 查询最近一个月申请次数
        CountApplyOP countApplyOP = new CountApplyOP();
        countApplyOP.setUserId(userId);
        countApplyOP.setBeginDate(DateUtils.getYYYYMMDD2Int(DateUtils.addMonth(new Date(), -1)));
        countApplyOP.setEndDate(DateUtils.getYYYYMMDD2Int());
        int applyCount = loanApplyManager.countApplyNearMonth(countApplyOP);
        if (applyCount >= Global.MAX_APPLY_COUNT_MONTH) {
            return true;
        }
        return rz;
    }

    /*
     * 获取当前贷款申请状态(新)
     */
    public Map<String, Object> getCurrentLoanStatusByPid(String userId, String productId) {
        // 设置返回参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("permitApplyAgain", LoanApplySimpleVO.YES);
        // LoanApply loanApply = loanApplyManager.getAuditLoanApply(userId);
        BigDecimal amt = loanApplyManager.getUnFinishLoanAmtByUserId(userId);
        if (amt != null) {
            map.put("creditAmt",
                    amt.compareTo(new BigDecimal(LoanLimitEnum.MAX.getValue())) < 0
                            ? new BigDecimal(LoanLimitEnum.MAX.getValue()).subtract(amt)
                            : new BigDecimal(0));
        }

        LoanApply loanApply = loanApplyManager.getUnFinishLoanApplyByUserId(userId);
        if (loanApply != null) {
            Integer status = loanApply.getStatus();
            if (!productId.equals(loanApply.getProductId()) && status > XjdLifeCycle.LC_APPLY_0) {
                map.put("permitApplyAgain", LoanApplySimpleVO.NO);
                map.put("applyLimitTip", "您有未结清的其它贷款产品，请联系客服");
                return map;
            }

            /*
             * Integer authStatus = operationLogService.getAuthStatus(userId,
             * productId); if (authStatus == AuthenticationStatusVO.NO) {
             * map.put("permitApplyAgain", LoanApplySimpleVO.NO);
             * map.put("loanApplyStatus",
             * LoanApplySimpleVO.APPLY_STATUS_UNIDENTITY);
             * map.put("applyLimitTip", "您有未完成认证,请先去认证"); return map; }
             */
            if (status == XjdLifeCycle.LC_APPLY_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_0
                    || status == XjdLifeCycle.LC_AUTO_AUDIT_3 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0
                    || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3) {
                if (LoanProductEnum.LYFQ.getId().equals(productId)) {
                    map.put("permitApplyAgain", LoanApplySimpleVO.YES);
                    map.put("loanApplyStatus", LoanApplySimpleVO.APPLY_STATUS_UNOVER);
                } else {
                    map.put("permitApplyAgain", LoanApplySimpleVO.NO);
                    map.put("loanApplyStatus", LoanApplySimpleVO.APPLY_STATUS_AUDITING);
                }
                map.put("applyAmt", loanApply.getApplyAmt());
                map.put("term", loanApply.getTerm() != null ? LoanTermEnum.getDesc(loanApply.getTerm()) : "");
                map.put("repayMethod", RepayMethodEnum.getDesc(loanApply.getRepayMethod()));
                map.put("applyLimitTip", "您的申请正在审核,请耐心等待,审核期间请保持手机通畅以便客服联系。审核通过将以短信和APP消息方式通知。");
            }
        }
        return map;
    }

    @Override
    public int updateAuthStatus(String userId, Integer status) {
        /** 保存操作记录数据 */
        OperationLog operationLog = new OperationLog();
        operationLog.setUserId(userId);
        operationLog.setStage(status);
        operationLog.setStatus(status);
        operationLog.setSource(Global.DEFAULT_SOURCE);
        operationLog.defOperatorIdAndName();
        operationLog.preInsert();
        return loanApplyManager.saveOperationLog(operationLog);
    }

    @Override
    public void updateCompanyId(String companyId, String id) {
        loanApplyManager.updateCompanyId(companyId, id);
    }

    public int countUnFinishLoanApplyByUserId(String userId) {
        return loanApplyManager.countUnFinishLoanApplyByUserId(userId);
    }

    public int countApprovingByUserId(String userId) {
        return loanApplyManager.countApprovingByUserId(userId);
    }

    public BigDecimal getUnFinishLoanAmtByUserId(String userId) {
        return loanApplyManager.getUnFinishLoanAmtByUserId(userId);
    }

    public long getLastRejectDaysByUserId(String userId) {
        LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
        if (null != lastApply) {
            if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                    || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                // 判断上次拒绝更新的时间距现在的天数是否超限
                Date lastUpdateTime = lastApply.getUpdateTime();
                long lastRejectDays = DateUtils.pastDays(lastUpdateTime);
                return lastRejectDays;
            }
        }
        return -1;
    }

    public int countRejectByUserid(String userId) {
        return loanApplyManager.countRejectByUserId(userId);
    }

    @Override
    public String getOutSideNumByApplyId(String applyId) {
        return borrowInfoManager.getOutSideNumByApplyId(applyId);
    }

    @Override
    public List<ApplyListVO> getLoanApplyListExport(Page page, ApplyListOP op) {
        return loanApplyManager.getLoanApplyList(page, op);
    }

    @Override
    public ApplyListVO getLastFinishApplyByUserId(String userId) {
        LoanApply loanApply = loanApplyManager.getLastFinishApplyByUserId(userId);
        if (loanApply == null) {
            return null;
        }
        return BeanMapper.map(loanApply, ApplyListVO.class);
    }

    @Override
    public ApplyListVO getBaseLoanApplyById(String applyId) {
        LoanApply loanapply = loanApplyManager.getLoanApplyById(applyId);
        if (loanapply == null) {
            return null;
        }
        return BeanMapper.map(loanapply, ApplyListVO.class);
    }

    public String getContractNo(String companyId) {
        StoreVO storeVO = storeService.getBycompayId(companyId);
        if (storeVO == null) {
            return "";
        }
        String code = storeVO.getCode();
        String monthStr = DateUtils.getDate("yyyyMM");

        String approveCount = JedisUtils.get(code + monthStr);
        if (StringUtils.isBlank(approveCount)) {
            approveCount = loanApplyManager.countApproveByCompanyId(companyId, monthStr) + 1 + "";
        } else {
            approveCount = Integer.parseInt(approveCount) + 1 + "";
        }
        JedisUtils.set(code + monthStr, approveCount, 32 * 24 * 60 * 60);
        int count = Integer.parseInt(approveCount);
        if (count > 9999)
            return "";
        int maxLen = 4;
        int len = String.valueOf(count).length();
        String s = "";
        for (int i = 0; i < maxLen - len; i++) {
            s += "0";
        }
        approveCount = s + count;
        return code + DateUtils.getDate("yyyyMMdd") + "-" + approveCount;
    }

    @Override
    public int updateResetCheck(String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        logger.info("撤销订单: {},{},{}", applyId, loanApply.getStatus(),
                ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());
        loanApply.setStage(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getStage());
        loanApply.setStatus(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());
        loanApply.setApplyStatus(ApplyStatusEnum.UNFINISH.getValue());
        loanApply.setApproveResult(0);
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    /**
     * 取消订单
     */
    public int updateCancel(String applyId, String operatorName) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (XjdLifeCycle.LC_RAISE_0 != loanApply.getStatus()
                && XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0 != loanApply.getStatus()) {
            return 0;
        }
        String lockKey = Global.BORROW_INFO_LOCK + loanApply.getId();
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            boolean lock = JedisUtils.setLock(lockKey, requestId, 5 * 60);
            if (!lock) {
                logger.error("当前贷款编号{},已被锁定.", loanApply.getId());
                return 0;
            }
            BorrowInfo borrow = borrowInfoManager.getByApplyId(loanApply.getId());
            logger.info("取消订单: {},{},{}", applyId, loanApply.getStatus(), ApplyStatusLifeCycleEnum.CANCAL.getValue());
            // 取消标的推送
            if (borrow != null && borrow.getPushStatus() != XjdLifeCycle.LC_RAISE_1) {
                // 已确认借款但未推送，设置推送成功
                borrow.setPushStatus(XjdLifeCycle.LC_RAISE_1);
                borrowInfoManager.update(borrow);
            }
            loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
            loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
            loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
            loanApply.setUpdateBy(operatorName);
            return loanApplyManager.updateLoanApplyInfo(loanApply);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 解除orderNo并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
        return 0;
    }

    @Override
    public int updateCountUserBindCardPv(String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if ("1".equals(loanApply.getCountUserBindCardPv())) {
            return 0;
        }
        loanApply.setCountUserBindCardPv("1");//设置为已访问
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    /**
     * 取消订单-代扣旅游券专用
     */
    public int updateCancelShopWithhold(String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (XjdLifeCycle.LC_RAISE_1 != loanApply.getStatus().intValue()) {
            return 100;
        }
        // 取消借款发送通知到p2p
        if (!borrowInfoService.cancelLoanNotify(applyId)) {
            return 101;
        }
        logger.info("取消订单: {},{},{}", applyId, loanApply.getStatus(), ApplyStatusLifeCycleEnum.CANCAL.getValue());
        loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
        loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
        loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    @Override
    public List<Map<String, Object>> custApplyList(String id) {
        return loanApplyManager.custApplyList(id);
    }

    public boolean hasOtherProductUnFinishApply(String userId, String productId) {
        List<LoanApply> loanApplyList = loanApplyManager.findUnFinishListByUserId(userId);
        for (LoanApply a : loanApplyList) {
            if (!a.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void setApplyRepayPlan(LoanApplySimpleVO vo, LoanApply entity) {
        RepayPlanOP op = new RepayPlanOP();
        op.setApplyAmt(entity.getApproveAmt());
        op.setProductId(entity.getProductId());
        op.setRepayMethod(entity.getRepayMethod());
        op.setRepayTerm(entity.getTerm());
        op.setApplyId(entity.getId());
        op.setChannelId(ChannelEnum.JUQIANBAO.getCode());
        Map<String, Object> repayPlan = loanRepayPlanService.getRepayPlan(op);
        if (!repayPlan.isEmpty()) {
            BigDecimal totalRepayAmount = new BigDecimal(String.valueOf(repayPlan.get("totalAmt")));
            List<Map<String, Object>> detail = (List<Map<String, Object>>) repayPlan.get("list");
            BigDecimal termRepayAmount = new BigDecimal(String.valueOf(detail.get(0).get("repayAmt")));

            vo.setTotalRepayAmount(totalRepayAmount);
            vo.setTermRepayAmount(termRepayAmount);
            vo.setRepayMethod(entity.getRepayMethod().toString());
            vo.setTotalRepayTerm(entity.getTerm());
        }
    }

    /**
     * 购买商品后插入标的
     */
    @Override
    @Transactional
    public boolean saveShopedBorrowInfo(String applyId, Integer loanPayType) {
        String shopedBorrowInfoLockCacheKey = "shoped_borrow_info_lock_" + applyId;
        synchronized (LoanApplyServiceImpl.class) {
            String shopedBorrowInfoLock = JedisUtils.get(shopedBorrowInfoLockCacheKey);
            if (shopedBorrowInfoLock == null) {
                // 加锁，防止并发
                JedisUtils.set(shopedBorrowInfoLock, "locked", 5 * 60);
            } else {
                logger.warn("协议确认记录接口调用中，applyId= {}", applyId);
                return false;
            }
        }
        BorrowInfo borrwo = borrowInfoManager.getByApplyId(applyId);
        if (borrwo != null) {
            logger.error("重复插入标的，applyId= {}", applyId);
            return false;
        }
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("订单状态不正确，applyId= {}", applyId);
            return false;
        }
        Integer borrowType = Global.DEFAULT_BORROW_TYPE;
/*		if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
			if (loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
					|| loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
					|| loanApply.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
				borrowType = 13;
			}
		} else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
			borrowType = 8;
		}*/
        // 插入标的表
        saveBorrowInfo(loanApply, borrowType, loanPayType);

        // 设置为已购买购物金
        String cacheKey = "BUY_SHOPPING_GOLD_" + applyId;
        JedisUtils.set(cacheKey, "1", 5 * 60);
        return true;
    }

    /**
     * 是否购买购物金
     */
    public boolean isBuyShoppingGold(String applyId) {
        boolean ret = false;
        String cacheKey = "BUY_SHOPPING_GOLD_" + applyId;
        String value = JedisUtils.get(cacheKey);
        if (value == null) {
            BorrowInfo borrwo = borrowInfoManager.getByApplyId(applyId);
            if (borrwo != null) {
                JedisUtils.set(cacheKey, "1", 5 * 60);
                ret = true;
            } else {
                JedisUtils.set(cacheKey, "0", 5 * 60);
                ret = false;
            }
        } else {
            if ("1".equals(value)) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public Integer getBorrowType(String applyId) {
        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
        if (null != borrowInfo) {
            return borrowInfo.getBorrowType();
        }
        return 0;
    }

    @Override
    public ApplyAllotVO getApplyById(String id) {
        return BeanMapper.map(loanApplyManager.getLoanApplyById(id), ApplyAllotVO.class);
    }

    @Override
    public List<Map<String, Object>> getReturnRate(String startDate, String endDate, String auditorId, String channel,
                                                   String productId, String termType) {
        return loanApplyManager.getReturnRate(startDate, endDate, auditorId, channel, productId, termType);
    }

    @Override
    public Map<String, Object> getContractDetail(String contNo, String type) {
        Map<String, Object> data = loanApplyManager.findContractInfo(contNo);
        if (null != data) {
            // 获取地区列表
            Map<String, String> areaMap = areaManager.getAreaCodeAndName();
            // 地址处理
            String address = null;
            if ((null != areaMap.get(data.get("resideProvince"))) && (null != areaMap.get(data.get("resideCity")))
                    && (null != areaMap.get(data.get("resideDistrict")))) {
                address = areaMap.get(data.get("resideProvince")) + areaMap.get(data.get("resideCity"))
                        + areaMap.get(data.get("resideDistrict")) + data.get("resideAddr");
            } else {
                address = (String) data.get("resideAddr");
            }
            data.put("contNo", contNo);
            data.put("resideAddr", address);
            data.put("purpose", LoanPurposeEnum.getDesc((String) data.get("purpose")));
            data.put("repayMethod", RepayMethodEnum.INTEREST.getDesc());
            data.put("bankCode", BankCodeEnum.getName((String) data.get("bankCode")));
            data.put("principalRMB", MoneyUtils.convert(data.get("principal").toString()));
            data.put("basicRate", BigDecimal.valueOf(100).multiply((BigDecimal) data.get("basicRate")));
            BigDecimal principaltotal = (BigDecimal) data.get("principal");
            BigDecimal servFee = (BigDecimal) data.get("servFee");
            BigDecimal interest1 = BigDecimal.ZERO;
            Integer approveTerm = (Integer) data.get("approveTerm");
            if ("xjd".equals(type)) {
                // 计算平台利息
                interest1 = principaltotal.subtract(servFee).multiply(BigDecimal.valueOf(approveTerm.longValue()))
                        .multiply(BigDecimal.valueOf(0.24))
                        .divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_HALF_UP);
            } else {
                // 计算口袋利息
                interest1 = principaltotal.subtract(servFee).multiply(BigDecimal.valueOf(approveTerm.longValue()))
                        .multiply(BigDecimal.valueOf(0.18))
                        .divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_HALF_UP);
            }
            data.put("interest", interest1);
            // 垫资费用
            BigDecimal advanceFee = servFee.multiply(BigDecimal.valueOf(approveTerm.longValue()))
                    .multiply(BigDecimal.valueOf(0.36)).divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_HALF_UP);
            data.put("approveTerm", data.get("approveTerm"));
            data.put("advanceFee", advanceFee);
            data.put("overplusAmount", principaltotal.subtract(servFee));
            // 获取分期明细数据
            List<Map<String, Object>> repayDetailData = repayPlanItemManager.getRepayDetailByContNo(contNo);
            for (Map<String, Object> map : repayDetailData) {
                BigDecimal principal = (BigDecimal) map.get("principal");
                BigDecimal interest = (BigDecimal) map.get("interest");
                map.put("totalAmount", principal.add(interest));
                map.put("repayDate", DateUtils.formatDate((Date) map.get("repayDate"), "yyyy年MM月dd日"));
            }
            data.put("repayDetailData", repayDetailData);
            for (Map<String, Object> map : repayDetailData) {
                if (null != (Date) map.get("loanStartDate")) {
                    data.put("loanStartDate", DateUtils.formatDate((Date) map.get("loanStartDate"), "yyyy年MM月dd日"));
                    data.put("loanEndDate", DateUtils.formatDate((Date) map.get("loanEndDate"), "yyyy年MM月dd日"));
                    break;
                }
            }
        }
        return data;
    }

    public int countUnFinishApplyByMobile(String mobile) {
        return loanApplyManager.countUnFinishApplyByMobile(mobile);
    }

    /**
     * 保存审核操作日志
     *
     * @param op
     * @param now
     * @param auto
     * @param operatorId
     * @param operatorName
     * @param applyId
     * @return
     */
    public int saveApproveLogLatter(LoanCheckOP op, Date now, Boolean auto, String operatorId, String operatorName,
                                    String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        loanApply.setApproveResult(3);
        saveApproveLog(op, now, auto, operatorId, operatorName, loanApply, loanApply.getStatus());
        return 1;
    }

    /**
     * 未接电话更新申请表
     *
     * @param applyId
     * @param callNum
     * @param remark
     * @return
     */
    public int updateNoAnswer(String applyId, Integer callNum, String remark) {
        return loanApplyManager.updateNoAnswer(applyId, callNum, remark);
    }

    /**
     * 历史放款成功并还款完结的次数
     */
    public int countOverLoanByRepay(String userId) {
        return loanApplyManager.countOverLoanByRepay(userId);
    }

    @Override
    public int countUnFinishByMobileAndIdNo(String idNo, String mobile) {
        return loanApplyManager.countUnFinishByMobileAndIdNo(idNo, mobile);
    }

    @Override
    /**
     * 口袋存管更新放款渠道
     */
    public int updateKDPayChannel(String applyId) {
        // LoanApply loanApply = new LoanApply();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        // 更新状态为410的订单(410更新为411)
        if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()) {
            loanApply.setPayChannel(String.valueOf(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()));
            // loanApply.setStage(ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getStage());//
            // 推送成功
            loanApply.setStatus(ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());// 推送成功
            loanApplyManager.updatePayChannel(loanApply);
            kdDepositService.saveOrUpdatePayLogStatus(applyId, null, 2);// 处理中
        }
        saveKDBorrowInfo(loanApply);
        return 1;
    }

    /**
     * 保存口袋存管标的
     *
     * @param loanApply
     */
    private void saveKDBorrowInfo(LoanApply loanApply) {
        Integer loanPayType = LoanApplySimpleVO.APPLY_PAY_TYPE_1;
        Integer borrowType = Global.DEFAULT_BORROW_TYPE;
        if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            if (loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                    || loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                    || loanApply.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
                borrowType = 13;
            }
        } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
            borrowType = 8;
        }

        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());

        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(loanApply.getId());
        if (borrowInfo == null) {
            int term = loanApply.getTerm();// 分期期数
            String purpose = loanApply.getPurpose();// 借款用途
            if (LoanProductEnum.CCD.getId().equals(loanApply.getProductId())) {
                term = loanApply.getTerm() / 2;
                purpose = LoanPurposeEnum.P1.getId();
            }
            if (LoanProductEnum.ZJD.getId().equals(loanApply.getProductId())
                    || LoanProductEnum.TYD.getId().equals(loanApply.getProductId())) {
                purpose = LoanPurposeEnum.P1.getId();
            }
            BigDecimal rate = getBorrowInfoRate(term);

            borrowInfo = new BorrowInfo();
            borrowInfo.setApplyId(loanApply.getId());
            borrowInfo.setUserId(loanApply.getUserId());
            borrowInfo.setUserName(loanApply.getUserName());
            if (LoanProductEnum.LYFQ.getId().equals(loanApply.getProductId())) {
                borrowInfo.setTitle(Global.DEFAULT_LYFQ_TITLE);
            } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
                    || LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
                borrowInfo.setTitle(configService.getValue("xjdfq_borrow_title"));
            } else {
                borrowInfo.setTitle(Global.DEFAULT_TITLE);
            }
            borrowInfo.setBorrowAmt(loanApply.getApproveAmt());
            borrowInfo.setBorrowDate(DateUtils.getDate());
            borrowInfo.setAccountId(custUser.getAccountId());
            borrowInfo.setContNo(loanApply.getContNo());
            borrowInfo.setActualRate(rate);

            borrowInfo.setServFeeRate(loanApply.getServFeeRate());
            borrowInfo.setOverdueRate(loanApply.getOverdueRate());
            borrowInfo.setOverdueFee(loanApply.getOverdueFee());
            borrowInfo.setPrepayValue(promotionCase.getPrepayValue());
            borrowInfo.setPurpose(purpose);
            borrowInfo.setPeriodUnit(Global.DEFAULT_PERIOD_UNIT);
            borrowInfo.setPeriod(term);
            borrowInfo.setRepayMethod(loanApply.getRepayMethod());
            borrowInfo.setPartnerId(loanApply.getProductId());
            borrowInfo.setPartnerName(ChannelEnum.getDescByCode(loanApply.getChannelId()));
            borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
            borrowInfo.setBorrowType(borrowType);
            // 口袋放款
            borrowInfo.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue());

            borrowInfo.setRemark(String.valueOf(loanPayType));

            borrowInfoManager.insert(borrowInfo);

            // 修改申请表放款渠道
            // updatePayChannel(loanApply.getId(),
            // WithdrawalSourceEnum.getByValue(borrowInfo.getPayChannel()).getValue());
        } else {
            // logger.warn("标的已存在，请勿重复提交，参数：" +
            // JsonMapper.getInstance().toJson(loanApply));
            borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
            // 口袋放款
            borrowInfo.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue());
            borrowInfoManager.update(borrowInfo);

        }
    }

    public int updatePayChannel(String applyId, int payChannel) {
        LoanApply loanApply = new LoanApply();
        loanApply.setId(applyId);
        loanApply.setPayChannel(String.valueOf(payChannel));
        loanApply.setPayTime(new Date());
        loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        loanApply.setUpdateTime(new Date());
        return loanApplyManager.updatePayChannel(loanApply);
    }

    public int updatePChannel(String applyId, int payChannel) {
        LoanApply apply = loanApplyManager.getLoanApplyById(applyId);
        // 验证口袋是否已经确认借款
        if (StringUtils.isNotBlank(apply.getPayChannel())
                && Integer.parseInt(apply.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()) {
            Map<String, Object> result = kdPayService.queryStatus(applyId);
            if (0 == (Integer) result.get("retCode")) {
                Map<String, Object> data = (Map<String, Object>) result.get("retData");
                if (null != data) {
                    Integer status = (Integer) data.get("status");
                    if (status != null) {
                        // 1: 放款中
                        // 2: 放款成功(钱放到电子账户)
                        // 4: 提现冲正
                        // 5: 放款成功(受托支付)
                        // 6: 提现成功(钱到银行卡)
                        // 7: 提现失败(可以再次发起提现)
                        // 8: 订单不存在
                        // 9: 提现失败(不需要再次发起提现 风控拒绝订单)
                        // 10:提现中
                        // 11:订单取消
                        // 100:开户问题
                        // 200:授权问题
                        // 300:借款合规页
                        // 400:等待二次确认
                        if (status.intValue() == 1 || status.intValue() == 2 || status.intValue() == 4
                                || status.intValue() == 5 || status.intValue() == 6 || status.intValue() == 7
                                || status.intValue() == 9 || status.intValue() == 10 || status.intValue() == 11) {
                            return 500;
                        }
                    }

                }
            }
        }
        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
        if (borrowInfo == null) {
            LoanApply loanApply = new LoanApply();
            loanApply.setId(applyId);
            loanApply.setPayChannel(String.valueOf(payChannel));
            loanApply.setPayTime(new Date());
            loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
            loanApply.setUpdateTime(new Date());
            return loanApplyManager.updatePayChannel(loanApply);
        } else {
            return 500;
        }
    }

    @Override
    public int updateChannel(String applyId, String sll) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("id", applyId));
        LoanApply loanApply = new LoanApply();
        loanApply.setChannelId(sll);
        return loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
    }

    @Override
    public void saveOperationLog(String userId, String ip) {
        OperationLog operationLog1 = new OperationLog();
        operationLog1.setUserId(userId);
        operationLog1.setStage(XjdLifeCycle.LC_FACE);
        operationLog1.setStatus(XjdLifeCycle.LC_FACE_1);
        operationLog1.setIp(ip);
        operationLog1.setSource(Global.DEFAULT_SOURCE);
        operationLog1.defOperatorIdAndName();
        operationLog1.preInsert();
        loanApplyManager.saveOperationLog(operationLog1);
    }

    @Override
    public String getPayChannelByUserId(String userId) {
        LoanApply apply = loanApplyManager.getInitUnFinishLoanApplyByUserId(userId);
        if (null != apply) {
            return apply.getPayChannel();
        }
        return null;
    }

    @Override
    public int updateHanJSPayChannel(String applyId) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        logger.info("{}-{}-{}", "HANJS", applyId, JsonMapper.toJsonString(loanApply));
        // 更新为汉金所放款渠道
        if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()) {
            loanApply.setPayChannel(String.valueOf(WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()));
            loanApplyManager.updatePayChannel(loanApply);
        }
        saveHanJSBorrowInfo(loanApply);
        return 1;
    }

    /**
     * 保存汉金所标的
     *
     * @param loanApply
     */
    private void saveHanJSBorrowInfo(LoanApply loanApply) {
        Integer loanPayType = LoanApplySimpleVO.APPLY_PAY_TYPE_1;
        Integer borrowType = Global.DEFAULT_BORROW_TYPE;
        if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            if (loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                    || loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                    || loanApply.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
                borrowType = 13;
            }
        } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
            borrowType = 8;
        }

        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());

        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(loanApply.getId());
        boolean flag = false;
        if (1 == custUser.getHjsAccountId()) {
            flag = true;
        }
        if (flag && borrowInfo == null) {
            int term = loanApply.getTerm();// 分期期数
            String purpose = loanApply.getPurpose();// 借款用途
            if (LoanProductEnum.CCD.getId().equals(loanApply.getProductId())) {
                term = loanApply.getTerm() / 2;
                purpose = LoanPurposeEnum.P1.getId();
            }
            if (LoanProductEnum.ZJD.getId().equals(loanApply.getProductId())
                    || LoanProductEnum.TYD.getId().equals(loanApply.getProductId())) {
                purpose = LoanPurposeEnum.P1.getId();
            }
            BigDecimal rate = getBorrowInfoRate(term);

            borrowInfo = new BorrowInfo();
            borrowInfo.setApplyId(loanApply.getId());
            borrowInfo.setUserId(loanApply.getUserId());
            borrowInfo.setUserName(loanApply.getUserName());
            if (LoanProductEnum.LYFQ.getId().equals(loanApply.getProductId())) {
                borrowInfo.setTitle(Global.DEFAULT_LYFQ_TITLE);
            } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
                    || LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
                borrowInfo.setTitle(configService.getValue("xjdfq_borrow_title"));
            } else {
                borrowInfo.setTitle(Global.DEFAULT_TITLE);
            }
            borrowInfo.setBorrowAmt(loanApply.getApproveAmt());
            borrowInfo.setBorrowDate(DateUtils.getDate());
            borrowInfo.setAccountId(custUser.getHjsAccountId().toString());
            borrowInfo.setContNo(loanApply.getContNo());
            borrowInfo.setActualRate(rate);

            borrowInfo.setServFeeRate(loanApply.getServFeeRate());
            if ("S".equals(loanApply.getUrgentPayed())) {
                // ytodo 0303 加急券
                //borrowInfo.setServFeeRate(BigDecimal.ZERO);
            }
            borrowInfo.setOverdueRate(loanApply.getOverdueRate());
            borrowInfo.setOverdueFee(loanApply.getOverdueFee());
            borrowInfo.setPrepayValue(promotionCase.getPrepayValue());
            borrowInfo.setPurpose(purpose);
            borrowInfo.setPeriodUnit(Global.DEFAULT_PERIOD_UNIT);
            borrowInfo.setPeriod(term);
            borrowInfo.setRepayMethod(loanApply.getRepayMethod());
            borrowInfo.setPartnerId(loanApply.getProductId());
            borrowInfo.setPartnerName(ChannelEnum.getDescByCode(loanApply.getChannelId()));
            borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue());
            borrowInfo.setBorrowType(borrowType);
            borrowInfo.setPayChannel(WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue());

            borrowInfo.setRemark(String.valueOf(loanPayType));

            borrowInfoManager.insert(borrowInfo);
        } else {
            logger.warn("标的已存在，请勿重复提交，参数：" + JsonMapper.getInstance().toJson(loanApply));
        }
    }

    /**
     * export-admin里实现
     */
    @Override
    public List<ApplyListCalloutVO> getLoanApplyListExportCallout(Page page, ApplyListOP op) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LoanApplyVO> getLoanApplyByUserId(String userId) {
        // TODO Auto-generated method stub
        return BeanMapper.mapList(loanApplyManager.getLoanApplyByUserId(userId), LoanApplyVO.class);
    }

    @Override
    public int rong360KoudaiBuildBorrowInfo(int n) {
        int count = 0;
        String prefix = "rong_create_account_";
        Set<String> keys = JedisUtils.getKeys(prefix, 2);
        if (keys != null) {
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                if (count > n)
                    break;
                String key = it.next();
                String applyId = key.replace(prefix, "");
                LoanApply apply = loanApplyManager.getLoanApplyById(applyId);
                if (apply != null && apply.getStatus() == XjdLifeCycle.LC_RAISE_0) {
                    BorrowInfo borrow = borrowInfoManager.getByApplyId(applyId);
                    if (borrow == null) {
                        apply.setPayChannel("4");// 乐视

                        saveBorrowInfo(apply, 13, LoanApplySimpleVO.APPLY_PAY_TYPE_1);

                        updatePayChannel(applyId, 4);

                        JedisUtils.del(key);
                        count++;
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    /**
     * 乐视-手动放款,
     *
     * @param applyId
     * @return 10-borrow_info中已经存在 11-贷款申请订单不存在 12-未绑卡 13-订单状态不正确(不等于410)
     */
    @Override
    public int handelExtend(String applyId) {
        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
        if (borrowInfo != null) {
            logger.error("borrow_info中存在，不能重复插入.applyId = {}", applyId);
            return 10;
        }
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            return 11;
        }
        if (!WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("订单状态不正确，applyId= {}", applyId);
            return 13;
        }
        CustUser custUser = custUserManager.getById(loanApply.getUserId());
        if (StringUtils.isBlank(custUser.getCardNo())) {
            logger.error("未绑卡不能放款, applyId = " + applyId);
            return 12;
        }
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());

        int term = loanApply.getTerm();// 分期期数
        String purpose = loanApply.getPurpose();// 借款用途
        if (LoanProductEnum.CCD.getId().equals(loanApply.getProductId())) {
            term = loanApply.getTerm() / 2;
            purpose = LoanPurposeEnum.P1.getId();
        }
        if (LoanProductEnum.ZJD.getId().equals(loanApply.getProductId())
                || LoanProductEnum.TYD.getId().equals(loanApply.getProductId())) {
            purpose = LoanPurposeEnum.P1.getId();
        }
        BigDecimal rate = getBorrowInfoRate(term);

        Integer borrowType = Global.DEFAULT_BORROW_TYPE;
        if (LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            if (loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                    || loanApply.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                    || loanApply.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
                borrowType = 13;
            }
        } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())) {
            borrowType = 8;
        }

        borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(loanApply.getId());
        borrowInfo.setUserId(loanApply.getUserId());
        borrowInfo.setUserName(loanApply.getUserName());
        if (LoanProductEnum.LYFQ.getId().equals(loanApply.getProductId())) {
            borrowInfo.setTitle(Global.DEFAULT_LYFQ_TITLE);
        } else if (LoanProductEnum.XJDFQ.getId().equals(loanApply.getProductId())
                || LoanProductEnum.XJD.getId().equals(loanApply.getProductId())) {
            borrowInfo.setTitle(configService.getValue("xjdfq_borrow_title"));
        } else {
            borrowInfo.setTitle(Global.DEFAULT_TITLE);
        }
        borrowInfo.setBorrowAmt(loanApply.getApproveAmt());
        borrowInfo.setBorrowDate(DateUtils.getDate());
        borrowInfo.setAccountId(custUser.getAccountId());
        borrowInfo.setContNo(loanApply.getContNo());
        borrowInfo.setActualRate(rate);
        // 先支付服务费设置为零
        if (1 == LoanApplySimpleVO.APPLY_PAY_TYPE_0.intValue()) {
            borrowInfo.setServFeeRate(BigDecimal.ZERO);
        } else {
            borrowInfo.setServFeeRate(loanApply.getServFeeRate());
        }
        borrowInfo.setOverdueRate(loanApply.getOverdueRate());
        borrowInfo.setOverdueFee(loanApply.getOverdueFee());
        borrowInfo.setPrepayValue(promotionCase.getPrepayValue());
        borrowInfo.setPurpose(purpose);
        borrowInfo.setPeriodUnit(Global.DEFAULT_PERIOD_UNIT);
        borrowInfo.setPeriod(term);
        borrowInfo.setRepayMethod(loanApply.getRepayMethod());
        borrowInfo.setPartnerId(loanApply.getProductId());
        borrowInfo.setPartnerName(ChannelEnum.getDescByCode(loanApply.getChannelId()));
        borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue());
        borrowInfo.setBorrowType(borrowType);
        borrowInfo.setRemark(String.valueOf(1));
        borrowInfo.setPayChannel(4);

        updatePayChannel(loanApply.getId(), 4);
        return borrowInfoManager.insert(borrowInfo);
    }

    @Override
    public boolean updateUrgentPayed(String applyId, String status) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("id", applyId));
        criteria.and(Criterion.eq("del", 0));
        LoanApply loanApply = new LoanApply();
        loanApply.setUrgentPayed(status);
        int n = loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
        return n > 0;
    }


    @Override
    public TaskResult cancelLoanApply() {
        logger.info("开始执行取消借点钱审核通过后客户未提现的订单任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        // 获取3天之前的日期
        Date date = DateUtils.addDay(new Date(), -3);
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("status", ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()));
        //只查询借点钱的数据
        criteria.and(Criterion.eq("product_id", LoanProductEnum.JDQ.getId()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss")));
        List<LoanApply> list = loanApplyManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("取消订单结束，暂无待取消数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        for (LoanApply loanApply : list) {
            if (XjdLifeCycle.LC_RAISE_0 != loanApply.getStatus().intValue()
                    && XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0 != loanApply.getStatus().intValue()) {
                fail++;
                logger.info("取消订单失败，该笔订单状态错误.订单ID{}", loanApply.getId());
                continue;
            }

            String lockKey = Global.BORROW_INFO_LOCK + loanApply.getId();
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            try {
                boolean lock = JedisUtils.setLock(lockKey, requestId, 5 * 60);
                if (!lock) {
                    logger.error("当前贷款编号{},已被锁定.", loanApply.getId());
                    continue;
                }
                // 确认借款后不能取消
                BorrowInfo borrow = borrowInfoManager.getByApplyId(loanApply.getId());
                if (borrow != null) {
                    fail++;
                    logger.info("取消订单失败，该笔订单已发起提现.订单ID{}", loanApply.getId());
                    continue;
                }
                logger.info("取消订单: {},{},{}", loanApply.getId(), loanApply.getStatus(), ApplyStatusLifeCycleEnum.CANCAL.getValue());
                loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
                loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
                loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
                loanApply.setUpdateBy("system");
                loanApplyManager.updateLoanApplyInfo(loanApply);
                success++;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 解除orderNo并发锁
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("取消订单结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }


    @Override
    public TaskResult cancelLoanApplyByDwd() {
        logger.info("开始执行取消大王贷审核通过后客户未提现的订单任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        // 获取3天之前的日期
        Date date = DateUtils.addDay(new Date(), -7);
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("status", ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()));
        //只查询借点钱的数据
        criteria.and(Criterion.eq("product_id", LoanProductEnum.JN.getId()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss")));
        List<LoanApply> list = loanApplyManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("取消订单结束，暂无待取消数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        for (LoanApply loanApply : list) {
            if (XjdLifeCycle.LC_RAISE_0 != loanApply.getStatus().intValue()
                    && XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0 != loanApply.getStatus().intValue()) {
                fail++;
                logger.info("取消订单失败，该笔订单状态错误.订单ID{}", loanApply.getId());
                continue;
            }

            String lockKey = Global.BORROW_INFO_LOCK + loanApply.getId();
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            try {
                boolean lock = JedisUtils.setLock(lockKey, requestId, 5 * 60);
                if (!lock) {
                    logger.error("当前贷款编号{},已被锁定.", loanApply.getId());
                    continue;
                }
                // 确认借款后不能取消
                BorrowInfo borrow = borrowInfoManager.getByApplyId(loanApply.getId());
                if (borrow != null) {
                    fail++;
                    logger.info("取消订单失败，该笔订单已发起提现.订单ID{}", loanApply.getId());
                    continue;
                }
                logger.info("取消订单: {},{},{}", loanApply.getId(), loanApply.getStatus(), ApplyStatusLifeCycleEnum.CANCAL.getValue());
                loanApply.setStage(ApplyStatusLifeCycleEnum.CANCAL.getStage());
                loanApply.setStatus(ApplyStatusLifeCycleEnum.CANCAL.getValue());
                loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
                loanApply.setUpdateBy("system");
                loanApplyManager.updateLoanApplyInfo(loanApply);
                success++;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 解除orderNo并发锁
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("取消订单结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<ApplyListVO> getLoanApplyByApi(Page page, ApplyListOP applyListOP) {
        List<ApplyListVO> voList = loanApplyDAO.getLoanApplyByApi(page, applyListOP);
        if (CollectionUtils.isEmpty(voList)) {
            page.setList(Collections.emptyList());
            return page;
        }
        // setBlacklistParam(applyListOP, voList);
        page.setList(voList);
        return page;
    }

    @Override
    public Map<String, String> handleApproveOrder(ShareVO shareVO, String type) {
        // 创建返回值
        Map<String, String> result = null;
        // 拿取数据
        LoanApply loanApply = loanApplyManager.getLoanApplyById(shareVO.getLoanId());
        if (null != loanApply && loanApply.getStatus().intValue() > ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().intValue()) {
            result = new HashedMap();
            result.put("customerName", shareVO.getCustomerName());
            result.put("paperNumber", shareVO.getPaperNumber());
            result.put("loanId", shareVO.getLoanId());
            result.put("loanTypeDesc", "信用借款");
            // 客户取消无审批时间取更新时间
            if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.CANCAL.getValue().intValue()){
                result.put("checkResultTime", DateUtils.formatDate(loanApply.getUpdateTime(), DateUtils.FORMAT_SHORT));
            } else{
                result.put("checkResultTime", DateUtils.formatDate(loanApply.getApproveTime(), DateUtils.FORMAT_SHORT));
            }
            Integer status = loanApply.getStatus();
            // 审批拒绝不用传输合同信息
            if (status.intValue() == ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().intValue()
                    || status.intValue() == ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().intValue()
                    || status.intValue() == ApplyStatusLifeCycleEnum.CANCAL.getValue().intValue()) {
                String statusStr = status.intValue() == 420 ? "05" : "02";
                result.put("checkResult", statusStr);
                return result;
            } else {
                CustUserInfo user = custUserInfoManager.getById(loanApply.getUserId());
                //审批通过 如果没放款生成还款计划 就先不传合同信息
                if (status.intValue() >= ApplyStatusLifeCycleEnum.WAITING_LENDING.getValue().intValue()){
                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(loanApply.getId());
                    result.put("loanMoney", loanApply.getApproveAmt().toString());
                    if ("D".equals(loanApply.getRepayFreq())) {
                        result.put("loanPeriods", "1");
                    } else {
                        result.put("loanPeriods", loanApply.getTerm().toString());
                    }
                    result.put("loanAssureType", "D");
                    // 此处因通过并不表示生成合同计划 所以计算日期传送
                    result.put("loanStartDate", DateUtils.formatDate(repayPlan.getLoanStartDate(), DateUtils.FORMAT_SHORT));
                    // 单期的加7天 多期的加28天
                    result.put("loanEndDate", DateUtils.formatDate(repayPlan.getLoanEndDate(), DateUtils.FORMAT_SHORT));
                    result.put("loanCreditCity", user.getRegCity());
                }
                result.put("checkResult", "01");
                result.put("phone", loanApply.getMobile());
            }
        }
        return result;
    }

    @Override
    public Map<String, String> handleOrder(ShareVO shareVO, String type) {
        // 创建返回值
        Map<String, String> result = null;
        // 拿取数据
        LoanApply loanApply = loanApplyManager.getLoanApplyById(shareVO.getLoanId());
        if (null != loanApply) {
            String statusStr = "";
            result = new HashedMap();
            if (type.endsWith("3")){
                // 共享逾期状态
                LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(loanApply.getId());
                if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue().intValue()) {
                    statusStr = "02";
                    result.put("nbMoney", repayPlan.getTotalAmount().toString());
                } else if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().intValue()) {
                    statusStr = "01";
                    result.put("nbMoney", repayPlan.getTotalAmount().toString());
                } else  {
                    return null;
                }
            } else {
                // 共享合同结清状态 因产品分期问题 所以只有最后一期逾期结清才传03 核销状态
                if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.BRING_FORWARD_REPAY.getValue().intValue()
                        || loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.REPAY.getValue().intValue()) {
                    statusStr = "04";
                } else if (loanApply.getStatus().intValue() == ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().intValue()) {
                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(loanApply.getId());
                    int i = DateUtils.compareDate(DateUtils.formatDate(repayPlan.getLoanEndDate()), DateUtils.getDateTime());
                    if (i < 0){
                        statusStr = "03";
                    } else {
                        statusStr = "04";
                    }
                } else {
                    return null;
                }
            }
            result.put("customerName", shareVO.getCustomerName());
            result.put("paperNumber", shareVO.getPaperNumber());
            result.put("loanId", shareVO.getLoanId());
            result.put("loanTypeDesc", "信用借款");
            result.put("state", statusStr);
            return result;
        }
        return result;
    }

    @Override
    public int updateCompositeScore(String applyId, String compositeScore) {
        LoanApply entity = new LoanApply();
        entity.setId(applyId);
        entity.setCompositeScore(compositeScore);
        return loanApplyManager.updateLoanApplyInfo(entity);
    }

    @Override
    public int updateZhiChengLoanRecord(String applyId, int loanRecordSize) {
        LoanApply entity = new LoanApply();
        entity.setId(applyId);
        entity.setLoanRecord(String.valueOf(loanRecordSize));
        return loanApplyManager.updateLoanApplyInfo(entity);
    }

}
