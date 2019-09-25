package com.rongdu.loans.compute.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.AverageCapitalPlusInterestUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.FirstInterestAfterPrincipalUtils;
import com.rongdu.loans.compute.JbqbInterestUtils;
import com.rongdu.loans.compute.PrincipalInterestDayUtils;
import com.rongdu.loans.compute.PrincipalInterestUtils;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.dto.OverdueItemCalcDTO;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;

/**
 * Created by liuzhuang on 2017/7/26.
 */
public class RepayPlanHelper {

    /**
     * 还款计划明细更新之后需要修改还款计划总表，调用该方法，根据入参返回一个用于更新的LoanRepayPlan实体
     * 根据返回对象中的id或者applyId,更新其他字段值
     * <p>
     * 例如： LoanRepayPlan destination =
     * RepayPlanHelper.summaryLoanRepayPlan(source, itemList);
     * loanRepayPlanDAO.update(destination);
     *
     * @param source   原始的还款计划总表
     * @param itemList 更新后的还款计划明细
     * @return
     * @throws ParseException
     */
    public static LoanRepayPlan summaryLoanRepayPlan(LoanRepayPlan source, List<RepayPlanItem> itemList) {
        // 当期实际还款日期
        Date curRealRepayDate = null;
        // 下一个还款日
        String nextRepayDate = null;
        // 应还本息
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 应还本金
        BigDecimal principal = BigDecimal.ZERO;
        // 应还利息
        BigDecimal interest = BigDecimal.ZERO;
        // 提前还款手续费
        BigDecimal prepayFee = BigDecimal.ZERO;
        // 逾期罚息
        BigDecimal penalty = BigDecimal.ZERO;
        // 逾期管理费
        BigDecimal overdueFee = BigDecimal.ZERO;
        // 分期服务费
        BigDecimal servFee = BigDecimal.ZERO;
        // 减免费用
        BigDecimal deduction = BigDecimal.ZERO;
        // 已还本金
        BigDecimal payedPrincipal = BigDecimal.ZERO;
        // 已还利息
        BigDecimal payedInterest = BigDecimal.ZERO;
        // 已还期数
        int payedTerm = 0;
        // 是否结清
        int status = Global.REPAY_PLAN_STATUS_OVER;

        for (RepayPlanItem item : itemList) {
            // 延期还款计划明细不统计
            if (Global.REPAY_TYPE_MANDELAY.equals(item.getRepayType())
                    || Global.REPAY_TYPE_MANUALDELAY.equals(item.getRepayType())) {
                continue;
            }
            boolean settlement = item.getStatus().equals(Global.REPAY_PLAN_STATUS_OVER);
            if (!settlement) {
                // 只要有一笔未结清，整个还款计划就是未结清状态
                status = Global.REPAY_PLAN_STATUS_UNOVER;
                // 将第一笔未结清的还款就是下一期的还款
                if (nextRepayDate == null) {
                    nextRepayDate = DateUtils.formatDate(item.getRepayDate());
                }
            }
            // 依次将实际还款日期赋值给curRealRepayDate，最终获得最后一个实际还款日期
            if (item.getActualRepayTime() != null) {
                curRealRepayDate = item.getActualRepayTime();
            }
            // 如果已经结清，计算已还本金 、已还利息 、已还期数
            if (settlement) {
                payedPrincipal = payedPrincipal.add(item.getPayedPrincipal());
                payedInterest = payedInterest.add(item.getPayedInterest());
                payedTerm++;
            }
            totalAmount = totalAmount.add(item.getTotalAmount());
            principal = principal.add(item.getPrincipal());
            interest = interest.add(item.getInterest());
            overdueFee = overdueFee.add(item.getOverdueFee());
            penalty = penalty.add(item.getPenalty());
            prepayFee = prepayFee.add(item.getPrepayFee());
            servFee = servFee.add(item.getServFee());
            deduction = deduction.add(item.getDeduction());
        }

        LoanRepayPlan destination = BeanMapper.map(source, LoanRepayPlan.class);
        destination.setCurRealRepayDate(curRealRepayDate);
        if (status == Global.REPAY_PLAN_STATUS_UNOVER) {
            destination.setNextRepayDate(nextRepayDate);
        }
        destination.setTotalAmount(totalAmount);
        destination.setPrincipal(principal);
        destination.setInterest(interest);
        destination.setOverdueFee(overdueFee);
        destination.setPenalty(penalty);
        destination.setPrepayFee(prepayFee);
        destination.setServFee(servFee);
        destination.setDeduction(deduction);
        destination.setPayedPrincipal(payedPrincipal);
        destination.setUnpayPrincipal(principal.subtract(payedPrincipal));
        destination.setPayedInterest(payedInterest);
        destination.setUnpayInterest(interest.subtract(payedInterest));
        destination.setPayedTerm(payedTerm);
        destination.setUnpayTerm(source.getTotalTerm() - payedTerm);
        destination.setCurrentTerm(payedTerm < source.getTotalTerm() ? payedTerm + 1 : payedTerm);
        destination.setStatus(status);
        destination.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        destination.setUpdateTime(new Date());
        return destination;
    }

    /**
     * 每日计算逾期的还款计划各字段的值,返回一个用于更新的RepayPlanItem实体
     *
     * @param source
     * @param type   1=XJD 2=TFL
     * @return
     */
    public static RepayPlanItem calcOverdueItem(OverdueItemCalcDTO source, int type) throws ParseException {
        Date now = new Date();
        // 逾期天数
        int overdueDays = DateUtils.daysBetween(source.getRepayDate(), now);
        if (type == 2)
            overdueDays++;
        // 当前利息
        int repayUnit = source.getRepayUnit() == null ? 0 : source.getRepayUnit().intValue();
        BigDecimal interest = source.getInterest();
        BigDecimal penalty = BigDecimal.ZERO;
        BigDecimal overdueFee = BigDecimal.ZERO;
        if (!(WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue() == Integer.parseInt(source.getPayChannel()))) {
            interest = getCurrInterest(source.getRepayMethod(), source.getApproveAmt(), source.getApproveTerm(),
                    source.getTotalTerm(), source.getThisTerm(), source.getActualRate(), overdueDays, repayUnit,
                    source.getPayChannel(), source.getApplyServFee());
            // 罚息
            penalty = CostUtils.calPenalty(source.getOverdueRate(), source.getApproveAmt(), overdueDays);
        }
        // 逾期管理费 不超过本金的50%
        // BigDecimal overdueFee = CostUtils.calOverFee(source.getOverdueFee(),
        // overdueDays, source.getApproveAmt());
        // if (overdueFee.compareTo(source.getPrincipal().multiply(new
        // BigDecimal(0.5))) >= 0) {
        // overdueFee = source.getPrincipal().multiply(new BigDecimal(0.5));
        // }
        BigDecimal overdueFeeLimit = new BigDecimal("3000");
        if (ChannelEnum.JIEDIANQIAN.getCode().equals(source.getChannelId())
                || ChannelEnum.JIEDIANQIAN2.getCode().equals(source.getChannelId())){
            // 逾期管理费不超过本金
            overdueFeeLimit = source.getApproveAmt().divide(new BigDecimal(source.getTotalTerm()),2,
                    BigDecimal.ROUND_HALF_UP);
        }else if (ChannelEnum.DAWANGDAI.getCode().equals(source.getChannelId())
                || ChannelEnum._51JDQ.getCode().equals(source.getChannelId())
                || ChannelEnum.YBQB.getCode().equals(source.getChannelId())
                || ChannelEnum.CYQB.getCode().equals(source.getChannelId())
                || ChannelEnum.CYQBIOS.getCode().equals(source.getChannelId())){
            // 逾期管理费不超过本金
            overdueFeeLimit = source.getApproveAmt().divide(new BigDecimal(source.getTotalTerm()),2,
                    BigDecimal.ROUND_HALF_UP);
        }


        overdueFee =
                CostUtils.calOverFee(source.getOverdueFee(), overdueDays , source.getApproveAmt());
        overdueFee = overdueFeeLimit.compareTo(overdueFee) >= 1? overdueFee: overdueFeeLimit;

        RepayPlanItem destination = new RepayPlanItem();
        destination.setId(source.getId());
        destination.setInterest(interest);
        destination.setUnpayInterest(interest);
        destination.setOverdueFee(overdueFee);
        destination.setPenalty(penalty);
        // 减免有效期仅到当日24：00前,清空减免费
        destination.setDeduction(BigDecimal.ZERO);

        // 应还本息 = 应还本金+应还利息+逾期管理费+罚息+提前还款手续费+分期服务费-减免费用
        BigDecimal totalAmount = CostUtils.calTotalAmount(source.getPrincipal(), interest, overdueFee, penalty,
                BigDecimal.ZERO, source.getServFee(), destination.getDeduction());
        destination.setTotalAmount(totalAmount);
        destination.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        destination.setUpdateTime(now);
        return destination;
    }

    public static Date getRepayDate(LoanApply loanApply, Contract contract, int currTerm) {
        Date repayDate = null;
        Date startDate = DateUtils.addDay(contract.getLoanStartDate(), -1);
        RepayMethodEnum repayMethod = RepayMethodEnum.get(Integer.valueOf(loanApply.getRepayMethod()));// 放款方式
        switch (repayMethod) {
            case INTEREST:
                repayDate = DateUtils.addMonth(startDate, currTerm);
                break;
            case EXPIRE:
                repayDate = DateUtils.addMonth(startDate, currTerm);
                break;
            case ONE_TIME:
                repayDate = DateUtils.addDay(startDate, loanApply.getApproveTerm() * currTerm);
                break;
            case INTEREST_DAY:
                repayDate = getCCDRepayDate(contract.getLoanStartDate(), currTerm, loanApply.getRepayUnit());
                break;
            case PRINCIPAL_INTEREST:
                repayDate = DateUtils.addMonth(startDate, currTerm);
                break;
            case PRINCIPAL_INTEREST_DAY:
                repayDate = DateUtils.addDay(startDate,
                        loanApply.getRepayUnit().multiply(new BigDecimal(currTerm)).intValue());
                break;
            default:
                break;
        }
        return repayDate;
    }

    public static String getRepayDateStr(LoanApply loanApply, Contract contract, int currTerm) {
        Date repayDate = getRepayDate(loanApply, contract, currTerm);
        return DateUtils.formatDate(repayDate);
    }

    public static Date getStartDate(LoanApply loanApply, Contract contract, int currTerm) {
        Date repayDate = getRepayDate(loanApply, contract, currTerm - 1);// 上一期的还款时间
        return DateUtils.addDay(repayDate, 1);
    }

    public static BigDecimal getTermPrincipal(LoanApply loanApply, LoanRepayPlan loanRepayPlan, int thisTerm) {
        BigDecimal principal = loanRepayPlan.getPrincipal();// 默认一次性还本付息
        RepayMethodEnum repayMethod = RepayMethodEnum.get(Integer.valueOf(loanApply.getRepayMethod()));// 放款方式
        switch (repayMethod) {
            case INTEREST:
                principal = AverageCapitalPlusInterestUtils.getMonthPrincipal(loanApply.getApproveAmt(),
                        loanApply.getActualRate(), loanRepayPlan.getTotalTerm()).get(thisTerm);
                break;
            case EXPIRE:
                principal = FirstInterestAfterPrincipalUtils
                        .getMonthPrincipal(loanApply.getApproveAmt(), loanRepayPlan.getTotalTerm()).get(thisTerm);
                break;
            case ONE_TIME:
                principal = loanApply.getApproveAmt();
                break;
            case INTEREST_DAY:
                // 月还款本息
                BigDecimal monthPrincipalInterest = AverageCapitalPlusInterestUtils
                        .getMonthPrincipalInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
                                loanRepayPlan.getTotalTerm() / 2)
                        .divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP);
                // 月还款利息
                BigDecimal monthInterest = getTermInterest(loanApply, loanRepayPlan, thisTerm);

                principal = monthPrincipalInterest.subtract(monthInterest);
                break;
            case PRINCIPAL_INTEREST:
                principal = PrincipalInterestUtils.getMonthPrincipal(loanApply.getApproveAmt(),
                        loanRepayPlan.getTotalTerm());
                break;
            case PRINCIPAL_INTEREST_DAY:
                principal = PrincipalInterestDayUtils.getTermPrincipal(loanApply.getApproveAmt(),
                        loanRepayPlan.getTotalTerm());
                break;
            default:
                break;
        }
        return principal;
    }

    public static BigDecimal getTermInterest(LoanApply loanApply, LoanRepayPlan loanRepayPlan, int thisTerm) {
        BigDecimal interest = loanRepayPlan.getInterest();// 默认一次性还本付息
        RepayMethodEnum repayMethod = RepayMethodEnum.get(Integer.valueOf(loanApply.getRepayMethod()));// 放款方式
        switch (repayMethod) {
            case INTEREST:
                interest = AverageCapitalPlusInterestUtils.getMonthInterest(loanApply.getApproveAmt(),
                        loanApply.getActualRate(), loanRepayPlan.getTotalTerm()).get(thisTerm);
                break;
            case EXPIRE:
                interest = FirstInterestAfterPrincipalUtils.getMonthInterest(loanApply.getApproveAmt(),
                        loanApply.getActualRate(), loanRepayPlan.getTotalTerm()).get(thisTerm);
                break;
            case ONE_TIME:
                if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_LESHI
                        .getValue() == Integer.parseInt(loanApply.getPayChannel())) {
                    interest = JbqbInterestUtils.getTermInterest(loanApply.getApproveAmt(), loanApply.getServFee(),
                            loanApply.getActualRate(), new BigDecimal(0.24), loanApply.getApproveTerm());
                } else {
                    interest = CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
                            loanApply.getApproveTerm());
                }
                break;
            case INTEREST_DAY:
                BigDecimal currTerm = new BigDecimal(thisTerm).divide(new BigDecimal(2), 0, BigDecimal.ROUND_UP);
                interest = AverageCapitalPlusInterestUtils.getMonthInterest(loanApply.getApproveAmt(),
                        loanApply.getActualRate(), loanRepayPlan.getTotalTerm() / 2).get(currTerm.intValue());
                interest = interest.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
                break;
            case PRINCIPAL_INTEREST:
                interest = PrincipalInterestUtils.getMonthInterest(loanApply.getApproveAmt(), loanApply.getActualRate());
                break;
            case PRINCIPAL_INTEREST_DAY:
                if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_LESHI
                        .getValue() == Integer.parseInt(loanApply.getPayChannel())) {
                    interest = JbqbInterestUtils.getTermInterest(loanApply.getApproveAmt(), loanApply.getServFee(),
                            loanApply.getActualRate(), new BigDecimal(0.24), loanApply.getRepayUnit().intValue());
                } else {
                    interest = PrincipalInterestDayUtils.getTermInterest(loanApply.getApproveAmt(),
                            loanApply.getActualRate(), loanApply.getRepayUnit().intValue());
                }
                break;
            default:
                break;
        }
        return interest;
    }

    // ytodo 0317
    public static BigDecimal getTermInterestTemp(LoanApply loanApply, LoanRepayPlan loanRepayPlan, int days) {
        BigDecimal interest = loanRepayPlan.getInterest();// 默认一次性还本付息
        RepayMethodEnum repayMethod = RepayMethodEnum.get(Integer.valueOf(loanApply.getRepayMethod()));// 放款方式
        switch (repayMethod) {
            case ONE_TIME:
                if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_LESHI
                        .getValue() == Integer.parseInt(loanApply.getPayChannel())) {
                    interest = JbqbInterestUtils.getTermInterest(loanApply.getApproveAmt(), loanApply.getServFee(),
                            loanApply.getActualRate(), new BigDecimal(0.24), days);
                } else {
                    interest = CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(), days);
                }
                break;
            case PRINCIPAL_INTEREST_DAY:
                if (StringUtils.isNotBlank(loanApply.getPayChannel()) && WithdrawalSourceEnum.WITHDRAWAL_LESHI
                        .getValue() == Integer.parseInt(loanApply.getPayChannel())) {
                    interest = JbqbInterestUtils.getTermInterest(loanApply.getApproveAmt(), loanApply.getServFee(),
                            loanApply.getActualRate(), new BigDecimal(0.24), days);
                } else {
                    interest = PrincipalInterestDayUtils.getTermInterest(loanApply.getApproveAmt(),
                            loanApply.getActualRate(), days);
                }
                break;
            default:
                break;
        }
        return interest;
    }

    public static BigDecimal getTotalServFee(LoanApply loanApply) {
        BigDecimal servFee = BigDecimal.ZERO;
        LoanProductEnum product = LoanProductEnum.get(loanApply.getProductId());
        switch (product) {
            case CCD:
            case ZJD:
            case TYD:
                servFee = getTermServFee(loanApply).multiply(new BigDecimal(loanApply.getTerm())).setScale(0,
                        BigDecimal.ROUND_HALF_UP);
                break;
            default:
                break;
        }
        return servFee;
    }

    public static BigDecimal getTermServFee(LoanApply loanApply) {
        BigDecimal servFee = BigDecimal.ZERO;
        LoanProductEnum product = LoanProductEnum.get(loanApply.getProductId());
        switch (product) {
            case CCD:
                servFee = loanApply.getServFeeRate().multiply(loanApply.getApproveAmt()).setScale(0,
                        BigDecimal.ROUND_HALF_UP);
                break;
            case ZJD:
            case TYD:
                // 等本等息
                BigDecimal amt1 = PrincipalInterestUtils.getMonthPrincipal(loanApply.getApproveAmt(), loanApply.getTerm());
                BigDecimal amt2 = PrincipalInterestUtils.getMonthInterest(loanApply.getApproveAmt(),
                        loanApply.getServFeeRate());
                // 等额本息
                BigDecimal amt3 = AverageCapitalPlusInterestUtils.getMonthPrincipalInterest(loanApply.getApproveAmt(),
                        loanApply.getActualRate(), loanApply.getTerm());
                servFee = amt1.add(amt2).subtract(amt3).setScale(0, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                break;
        }
        return servFee;
    }

    public static BigDecimal getCurrInterest(Integer repayMethodId, BigDecimal approveAmt, Integer approveTerm,
                                             Integer totalTerm, Integer thisTerm, BigDecimal actualRate, int overdueDays, int repayUnit,
                                             String payChannel, BigDecimal applyServFee) {
        BigDecimal overdueInterest = new BigDecimal(0);
        BigDecimal interest = new BigDecimal(0);
        RepayMethodEnum repayMethod = RepayMethodEnum.get(Integer.valueOf(repayMethodId));// 放款方式
        switch (repayMethod) {
            case INTEREST:
                overdueInterest = CostUtils.calFenqiCurrOverdueInterest(approveAmt, actualRate, overdueDays);
                interest = AverageCapitalPlusInterestUtils.getMonthInterest(approveAmt, actualRate, totalTerm)
                        .get(thisTerm);
                interest = interest.add(overdueInterest);
                break;
            case EXPIRE:
                overdueInterest = CostUtils.calFenqiCurrOverdueInterest(approveAmt, actualRate, overdueDays);
                interest = FirstInterestAfterPrincipalUtils.getMonthInterest(approveAmt, actualRate, totalTerm)
                        .get(thisTerm);
                interest = interest.add(overdueInterest);
                break;
            case INTEREST_DAY:
                if (actualRate.compareTo(new BigDecimal("0.3600")) == 0) {
                    interest = approveAmt.multiply(actualRate).divide(BigDecimal.valueOf(24), Global.DEFAULT_AMT_SCALE,
                            BigDecimal.ROUND_HALF_UP);
                } else {
                    BigDecimal currTerm = new BigDecimal(thisTerm).divide(new BigDecimal(2), 0, BigDecimal.ROUND_UP);
                    interest = AverageCapitalPlusInterestUtils.getMonthInterest(approveAmt, actualRate, totalTerm / 2)
                            .get(currTerm.intValue());
                    interest = interest.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
                }
                break;
            case PRINCIPAL_INTEREST:
                overdueInterest = CostUtils.calFenqiCurrOverdueInterest(approveAmt, actualRate, overdueDays);
                interest = PrincipalInterestUtils.getMonthInterest(approveAmt, actualRate);
                interest = interest.add(overdueInterest);
                break;
            case PRINCIPAL_INTEREST_DAY:
                if (StringUtils.isNotBlank(payChannel)
                        && WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue() == Integer.parseInt(payChannel)) {
                    overdueInterest = JbqbInterestUtils.getTermInterest(approveAmt, applyServFee, actualRate,
                            new BigDecimal(0.24), overdueDays);
                    interest = JbqbInterestUtils.getTermInterest(approveAmt, applyServFee, actualRate, new BigDecimal(0.24),
                            repayUnit);
                    interest = interest.add(overdueInterest);
                } else {
                    overdueInterest = CostUtils.calFenqiCurrOverdueInterest(approveAmt, actualRate, overdueDays);
                    interest = PrincipalInterestDayUtils.getTermInterest(approveAmt, actualRate, repayUnit);
                    interest = interest.add(overdueInterest);
                }
                break;
            case ONE_TIME:
                if (StringUtils.isNotBlank(payChannel)
                        && WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue() == Integer.parseInt(payChannel)) {
                    interest = JbqbInterestUtils.getTermInterest(approveAmt, applyServFee, actualRate, new BigDecimal(0.24),
                            approveTerm + overdueDays);
                } else {
                    interest = CostUtils.calCurInterest(approveAmt, actualRate, approveTerm + overdueDays);
                }
                break;
            default:
                break;
        }
        return interest;
    }

    // public static BigDecimal getDayInterest(Integer repayMethodId, BigDecimal
    // approveAmt, BigDecimal actualRate) {
    // BigDecimal everyDayInterest = new BigDecimal(0);
    // RepayMethodEnum repayMethod =
    // RepayMethodEnum.get(Integer.valueOf(repayMethodId));// 放款方式
    // switch (repayMethod) {
    // case INTEREST:
    // everyDayInterest = CostUtils.calFenqiDayInterest(approveAmt, actualRate);
    // break;
    // case EXPIRE:
    // everyDayInterest = CostUtils.calFenqiDayInterest(approveAmt, actualRate);
    // break;
    // case INTEREST_DAY:
    // everyDayInterest = CostUtils.calFenqiDayInterest(approveAmt, actualRate);
    // break;
    // case PRINCIPAL_INTEREST:
    // everyDayInterest = CostUtils.calFenqiDayInterest(approveAmt, actualRate);
    // break;
    // case PRINCIPAL_INTEREST_DAY:
    // everyDayInterest = CostUtils.calFenqiDayInterest(approveAmt, actualRate);
    // break;
    // case ONE_TIME:
    // everyDayInterest = CostUtils.calCurInterest(approveAmt, actualRate, 1);
    // break;
    // default:
    // break;
    // }
    // return everyDayInterest;
    // }

    public static Date getCCDRepayDate(Date loanStartDate, int currTerm, BigDecimal repayUnit) {
        repayUnit = new BigDecimal(15);
        Date repayDate = null;
        Date startDate = DateUtils.addDay(loanStartDate, -1);
        if (currTerm <= 0)
            return startDate;
        if (currTerm == 1) {
            if ("02-14".equals(DateUtils.formatDate(startDate, "MM-dd"))
                    || "02-15".equals(DateUtils.formatDate(startDate, "MM-dd"))) {
                repayDate = DateUtils.getLastDayInTheMonth(startDate);
            } else {
                repayDate = DateUtils.addDay(startDate, repayUnit.multiply(new BigDecimal(currTerm)).intValue());
            }
        } else if (currTerm == 2) {
            repayDate = DateUtils.addMonth(startDate, 1);
        } else if (currTerm >= 3) {
            if (currTerm % 2 == 1) {
                Date termDate = getCCDRepayDate(loanStartDate, 1, repayUnit);
                repayDate = DateUtils.addMonth(termDate, (currTerm - 1) / 2);
            } else {
                Date termDate = getCCDRepayDate(loanStartDate, 2, repayUnit);
                repayDate = DateUtils.addMonth(termDate, (currTerm - 2) / 2);
            }
        }
        if (currTerm > 0 && currTerm < 3 && "31".equals(DateUtils.formatDate(repayDate, "dd"))) {
            repayDate = DateUtils.addDay(repayDate, -1);
        }
        return repayDate;
    }

    public static void setOverRepayPlanItem(RepayPlanItem d, BigDecimal totalAmount, BigDecimal principal,
                                            BigDecimal interest, BigDecimal overdueFee, BigDecimal penalty, BigDecimal prepayFee, BigDecimal deduction,
                                            BigDecimal actualRepayAmt, String actualRepayTime, String repayType) {
        Date repayTime = DateUtils.parse(actualRepayTime, "yyyy-MM-dd HH:mm:ss");
        String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
        BigDecimal zero = BigDecimal.ZERO;
        d.setTotalAmount(totalAmount);
        d.setPrincipal(principal);
        d.setPayedPrincipal(principal);
        d.setUnpayPrincipal(zero);
        d.setInterest(interest);
        d.setPayedInterest(interest);
        d.setUnpayInterest(zero);
        d.setOverdueFee(overdueFee);
        d.setPenalty(penalty);
        d.setPrepayFee(prepayFee);
        d.setDeduction(deduction);
        d.setActualRepayTime(repayTime);
        d.setActualRepayDate(repayDate);
        d.setActualRepayAmt(actualRepayAmt);
        d.setStatus(ApplyStatusEnum.FINISHED.getValue());
        d.setRepayType(repayType);
    }
}
