package com.rongdu.loans.compute.helper;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.compute.AverageCapitalPlusInterestUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.FirstInterestAfterPrincipalUtils;
import com.rongdu.loans.compute.PrincipalInterestDayUtils;
import com.rongdu.loans.compute.PrincipalInterestUtils;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.option.PromotionCaseOP;

/**
 * Created by liuzhuang on 2017/7/26.
 */
public class LoanApplyHelper {
	/**
	 * 日志对象
	 */
	public static final Logger logger = LoggerFactory.getLogger(LoanApplyHelper.class);

	/**
	 * 分期产品设置(保存订单)
	 */
	public static void setFenqiInfoForSave(LoanApply source) {
		// 默认到期还本付息，此处只计算分期产品相关信息
		int term = source.getTerm();// 贷款期数（默认1期）
		int days = source.getApplyTerm();// 贷款期限（默认30天）
		BigDecimal interest = source.getInterest();// 利息（默认按天计算利息）

		// 现金贷小额分期90天
		if (LoanProductEnum.XJD.getId().equals(source.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_90) {
			source.setTerm(3);
			source.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			source.setRepayFreq("D");
			source.setRepayUnit(new BigDecimal(10));
		} else if (LoanProductEnum.XJD.getId().equals(source.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_28) {
			source.setTerm(4);
			source.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			source.setRepayFreq("D");
			source.setRepayUnit(new BigDecimal(7));
		} else if (LoanProductEnum.JDQ.getId().equals(source.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_28) {
			source.setTerm(4);
			source.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			source.setRepayFreq("D");
			source.setRepayUnit(new BigDecimal(7));
		}

		RepayMethodEnum repayMethod = RepayMethodEnum.get(source.getRepayMethod());// 还款方式
		if (repayMethod == null) {
			logger.error("setFenqiInfoForSave error : 未知的还款方式");
			throw new RuntimeException("未知的还款方式");
		}
		switch (repayMethod) {
		case INTEREST:
			days = getDaysForMonth(term);
			interest = AverageCapitalPlusInterestUtils.getInterestCount(source.getApproveAmt(), source.getActualRate(),
					source.getTerm());
			break;
		case EXPIRE:
			days = getDaysForMonth(term);
			interest = FirstInterestAfterPrincipalUtils.getInterestCount(source.getApproveAmt(),
					source.getActualRate(), source.getTerm());
			break;
		case INTEREST_DAY:
			days = getDaysForCCD(term, source.getRepayUnit());
			interest = AverageCapitalPlusInterestUtils.getInterestCount(source.getApproveAmt(), source.getActualRate(),
					source.getTerm() / 2);
			break;
		case PRINCIPAL_INTEREST:
			days = getDaysForMonth(term);
			interest = PrincipalInterestUtils.getInterestCount(source.getApproveAmt(), source.getActualRate(),
					source.getTerm());
			break;
		case PRINCIPAL_INTEREST_DAY:// 现金贷小额分期专用
			interest = PrincipalInterestDayUtils.getInterestCount(source.getApproveAmt(), source.getActualRate(),
					source.getRepayUnit().intValue(), source.getTerm());
			break;
		default:
			break;
		}
		source.setApplyTerm(days);
		source.setApproveTerm(days);
		source.setInterest(interest);

		setServFee(source);
	}

	/**
	 * 分期产品设置(审核订单)
	 */
	public static void setFenqiInfoForApprove(LoanApply source, LoanApply update) {
		int days = update.getApproveTerm();// 审批期限
		BigDecimal interest = source.getInterest();// 申请时计算的利息

		// 现金贷小额分期90天
		if (LoanProductEnum.XJD.getId().equals(update.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_90) {
			update.setTerm(3);
			update.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			update.setRepayFreq("D");
			update.setRepayUnit(new BigDecimal(10));
		}
		// 28天
		else if (LoanProductEnum.XJD.getId().equals(update.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_28) {
			update.setTerm(4);
			update.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			update.setRepayFreq("D");
			update.setRepayUnit(new BigDecimal(7));
		} else if (LoanProductEnum.XJD.getId().equals(update.getProductId()) && days < Global.XJD_AUTO_FQ_DAY_28) {
			update.setTerm(1);
			update.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
			update.setRepayFreq("D");
			update.setRepayUnit(new BigDecimal(0));
		} else if (LoanProductEnum.JDQ.getId().equals(update.getProductId()) && days == Global.XJD_AUTO_FQ_DAY_28) {
			update.setTerm(4);
			update.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
			update.setRepayFreq("D");
			update.setRepayUnit(new BigDecimal(7));
		}

		RepayMethodEnum repayMethod = RepayMethodEnum.get(update.getRepayMethod());// 还款方式
		switch (repayMethod) {
		case INTEREST:
			setXJDFQTerm(update, 99);// 设置临时还款期数
			setPromotionCase(update);
			setXJDFQTerm(update, source.getTerm());// 恢复源还款期数

			days = getDaysForMonth(update.getTerm());
			interest = AverageCapitalPlusInterestUtils.getInterestCount(update.getApproveAmt(), update.getActualRate(),
					update.getTerm());
			break;
		case EXPIRE:
			setPromotionCase(update);
			days = getDaysForMonth(update.getTerm());
			interest = FirstInterestAfterPrincipalUtils.getInterestCount(update.getApproveAmt(),
					update.getActualRate(), update.getTerm());
			break;
		case INTEREST_DAY:
			setPromotionCase(update);
			days = getDaysForCCD(update.getTerm(), update.getRepayUnit());
			interest = AverageCapitalPlusInterestUtils.getInterestCount(update.getApproveAmt(), update.getActualRate(),
					update.getTerm() / 2);
			break;
		case PRINCIPAL_INTEREST:
			setPromotionCase(update);
			days = getDaysForMonth(update.getTerm());
			interest = PrincipalInterestUtils.getInterestCount(update.getApproveAmt(), update.getActualRate(),
					update.getTerm());
			break;
		case PRINCIPAL_INTEREST_DAY:// 现金贷小额分期专用
			setPromotionCase(update);
			interest = PrincipalInterestDayUtils.getInterestCount(update.getApproveAmt(), update.getActualRate(),
					update.getRepayUnit().intValue(), update.getTerm());
			break;
		case ONE_TIME:
			// ytodo 复贷减免
			//if (!(update.getTerm() == 1 && update.getApproveTerm() == Global.XJD_DQ_DAY_15
			//		&& update.getServFee().compareTo(BigDecimal.ZERO) == 0)) {
				setPromotionCase(update);
			//}
			interest = CostUtils
					.calCurInterest(update.getApproveAmt(), update.getActualRate(), update.getApproveTerm());
			break;
		default:
			break;
		}
		update.setApproveTerm(days);
		update.setInterest(interest);

		setServFee(update);
	}

	private static int getDaysForCCD(int term, BigDecimal repayUnit) {
		Date now = new Date();
		Date startDate = DateUtils.addDay(now, -1);
		Date endDate = RepayPlanHelper.getCCDRepayDate(now, term, repayUnit);
		return DateUtils.daysBetween(startDate, endDate);
	}

	private static int getDaysForMonth(int term) {
		Date startDate = DateUtils.addDay(new Date(), -1);
		Date endDate = DateUtils.addMonth(startDate, term);
		return DateUtils.daysBetween(startDate, endDate);
	}

	private static void setPromotionCase(LoanApply apply) {
		PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
		promotionCaseOP.setApplyAmt(apply.getApproveAmt());
		promotionCaseOP.setProductId(apply.getProductId());
		promotionCaseOP.setApplyTerm(apply.getApproveTerm());
		promotionCaseOP.setChannelId(apply.getChannelId());
		/*LoanProductEnum productEnum = LoanProductEnum.get(apply.getProductId());
		switch (productEnum) {
		case CCD:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.CHENGDAI.getCode());
			break;
		case ZJD:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.CHENGDAI.getCode());
			break;
		case TYD:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.CHENGDAI.getCode());
			break;
		case TFL:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.TOUFULI.getCode());
			break;
		case LYFQ:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.LYFQAPP.getCode());
			break;
		case XJDFQ:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
			break;
		case JDQ:
			promotionCaseOP.setApplyTerm(apply.getTerm());
			promotionCaseOP.setChannelId(ChannelEnum.JIEDIANQIAN.getCode());
			break;
		default:
			promotionCaseOP.setApplyTerm(apply.getApproveTerm());
			promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
			break;
		}*/
		PromotionCase promotionCase = SpringContextHolder.getBean(PromotionCaseManager.class).getByApplyInfo(
				promotionCaseOP);
		if (promotionCase != null) {
			apply.setBasicRate(promotionCase.getRatePerYear());
			apply.setActualRate(promotionCase.getRatePerYear());
			apply.setServFeeRate(promotionCase.getServFeeRate());
			apply.setPromotionCaseId(promotionCase.getId());
		} else {
			logger.error("setFenqiInfoForApprove error : 找不到营销方案");
			throw new RuntimeException("找不到营销方案");
		}
	}

	private static void setXJDFQTerm(LoanApply update, int term) {
		if (LoanProductEnum.XJDFQ.getId().equals(update.getProductId())
				&& update.getServFeeRate().compareTo(BigDecimal.ZERO) == 0) {
			update.setTerm(term);
		}
	}

	private static void setServFee(LoanApply loanApply) {
		BigDecimal servFee = loanApply.getServFee();
		LoanProductEnum product = LoanProductEnum.get(loanApply.getProductId());
		switch (product) {
		case CCD:
			servFee = loanApply.getServFeeRate().multiply(loanApply.getApproveAmt())
					.setScale(0, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(loanApply.getTerm()));
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
			BigDecimal termServFee = amt1.add(amt2).subtract(amt3).setScale(0, BigDecimal.ROUND_HALF_UP);
			servFee = termServFee.multiply(new BigDecimal(loanApply.getTerm()));
			break;
		default:
			servFee = CostUtils.calServFee(loanApply.getServFeeRate(), loanApply.getApproveAmt());
			break;
		}
		loanApply.setServFee(servFee);
	}
}
