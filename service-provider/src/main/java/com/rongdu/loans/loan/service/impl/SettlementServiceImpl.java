package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.LoginUtils;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.UserInfoHistory;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanTripProductService;
import com.rongdu.loans.loan.service.SettlementService;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.StatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 
 * @author likang
 * 
 */
@Service("settlementService")
public class SettlementServiceImpl extends BaseService implements SettlementService {

	@Autowired
	private SettlementManager settlementManager;

	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;

	@Autowired
	private ContractManager contractManager;
	
	@Autowired
	private CustCouponManager custCouponManager;
	
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	
	@Autowired
	private UserInfoHistoryManager userInfoHistoryManager;
	
	@Autowired
	private ContractService contractService;

	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private LoanTripProductService loanTripProductService;

	@Autowired
	private OverdueManager overdueManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private CustUserService custUserService;

	@Override
	public StatementVO getStatementByItemId(String repayPlanItemId) {
		if (StringUtils.isNotBlank(repayPlanItemId)) {
			return loanRepayPlanManager.getStatementByItemId(repayPlanItemId);
		} else {
			logger.error("the param repayPlanItemId is null");
		}
		return null;
	}

	@Override
	public ConfirmPayResultVO settlement(RePayOP rePayOP) {
		//userId,applyId,prePayFlag,RepayPlanItemId,ChlCode,PaySuccTime,PaySuccAmt
		//TxType,Source,Ip
		// 参数判断
		if (null == rePayOP) {
			logger.error("error, the param is null");
			return null;
		}
		// 用户id
		String userId = rePayOP.getUserId();

		// 根据用户id查询未结清合同
		Contract contract = contractManager.getUnFinishContractByUserId(userId);
		if (null != contract) {
			rePayOP.setApplyId(contract.getApplyId());
		} else {
			logger.error("[{}], not find contract", userId);
			return null;
		}
		// 账单结算
		//y:app还款
		ConfirmPayResultVO vo = settlementManager.Settlement(rePayOP);
		return vo;
	}

	@Override
	public ConfirmPayResultVO delay(RePayOP rePayOP) {
		// 参数判断
		if (null == rePayOP) {
			logger.error("error, the param is null");
			return null;
		}
		// 用户id
		String userId = rePayOP.getUserId();

		// 根据用户id查询未结清合同
		Contract contract = contractManager.getUnFinishContractByUserId(userId);
		if (null != contract) {
			rePayOP.setApplyId(contract.getApplyId());
		} else {
			logger.error("[{}], not find contract", userId);
			return null;
		}
		// 延期
		int ret = contractService.processManualDelay(rePayOP.getRepayPlanItemId());
		ConfirmPayResultVO rzVo = new ConfirmPayResultVO();
		rzVo.setPrePayFlag("2");
		rzVo.setResult(ret);
		return rzVo;
	}

	@Override
	public ConfirmPayResultVO urgent(RePayOP rePayOP) {
		// 参数判断
		if (null == rePayOP) {
			logger.error("error, the param is null");
			return null;
		}
		// 加急
		int ret = 0;
		// LoanApplySimpleVO vo =
		// loanApplyService.getLoanApplyById(rePayOP.getApplyId());
		// if (vo == null) {
		// logger.error("error, the loanApply is null");
		// return null;
		// }
		// if (vo.getTerm() > 1) {
		// boolean flag =
		// loanApplyService.saveShopedBorrowInfo(rePayOP.getApplyId());
		// if (flag)
		// ret = 1;
		// } else {
		ret = loanApplyService.updateApplyStatus(rePayOP.getApplyId(), XjdLifeCycle.LC_LENDERS_0);
		if (ret > 0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			contractService.processAdminLendPay(rePayOP.getApplyId(), new Date(), true);
		}
		// }
		ConfirmPayResultVO rzVo = new ConfirmPayResultVO();
		rzVo.setPrePayFlag("2");
		rzVo.setResult(ret);
		return rzVo;
	}

	@Override
	@Transactional
	public ConfirmPayResultVO urgentPushingLoan(RePayOP rePayOP) {
		// 参数判断
		if (null == rePayOP) {
			logger.error("error, the param is null");
			return null;
		}
		CustUserVO custUser = LoginUtils.getCustUserInfo(rePayOP.getUserId());
		if (null == custUser) {
			// 从数据库获取
			custUser = custUserService.getCustUserById(rePayOP.getUserId());
		}
		ConfirmPayResultVO rzVo = new ConfirmPayResultVO();

		String applyId = rePayOP.getApplyId();
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		// 更新订单表加急券支付状态
		boolean result = loanApplyService.updateUrgentPayed(applyId, "S");
		if (WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue().toString().equals(loanApply.getPayChannel())
				|| (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(loanApply.getPayChannel()) && custUser.getHjsAccountId() != null
				&& custUser.getHjsAccountId() != 0)) {
			// 乐视放款渠道，购买加急券后，直接确认放款
			// 汉金所放款渠道，购买加急券且已开过户，直接确认放款
			result = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_0);
		}
		if (!result){
			logger.error("加急券购买状态更新异常，请联系管理员！applyId={}", applyId);
			throw new RuntimeException("加急券购买状态更新异常，请联系管理员！");
		}
		rzVo.setPrePayFlag("2");//提前还款标识（2-非提前还清）
		rzVo.setResult(1);//处理结果（ 1-处理成功）
		return rzVo;
	}

	public ConfirmPayResultVO trip(RePayOP rePayOP) {
		// 参数判断
		if (null == rePayOP) {
			logger.error("error, the param is null");
			return null;
		}
		// 保存用户选择旅游产品
		try {
			LoanTripProductDetailOP productDetailOP = new LoanTripProductDetailOP();
			productDetailOP.setApplyId(rePayOP.getApplyId());
			productDetailOP.setCustId(rePayOP.getUserId());
			productDetailOP.setProductId(rePayOP.getTripProductId());
			loanTripProductService.saveCustProduct(productDetailOP);
		} catch (Exception e) {
			logger.error("保存旅游产品失败：", e);
		}

		// 旅游券
		boolean flag = loanApplyService.saveShopedBorrowInfo(rePayOP.getApplyId(), LoanApplySimpleVO.APPLY_PAY_TYPE_0);
		ConfirmPayResultVO rzVo = new ConfirmPayResultVO();
		rzVo.setPrePayFlag("2");
		rzVo.setResult(flag ? 1 : 0);
		return rzVo;
	}
	
	/**
	 * 是否可进行购物券抵扣
	 * @param repayPlanItemId
	 */
	@Override
	public boolean isEnableCouponDeduction(String repayPlanItemId){
		
		RepayPlanItem repayPlanItem = repayPlanItemManager.get(repayPlanItemId);
		if(repayPlanItem == null){
			return false;
		}
		//查询贷款历史信息
		UserInfoHistory allpyHistory = userInfoHistoryManager.getByApplyId(repayPlanItem.getApplyId());
		//正常还款两次，第三次续借成功才能抵扣
		if(allpyHistory == null || allpyHistory.getLoanSuccCount() < 2){
			return false;
		}
		
		//统计用户二三期逾期的订单数
		//int overdueTimes = repayPlanItemManager.countOverdueInTwoOrThreeTerm(repayPlanItem.getUserId() ,repayPlanItem.getApplyId());

		//改查loan_overdut表
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("apply_id", repayPlanItem.getApplyId()));
		int overdueTimes= (int)overdueManager.countByCriteria(criteria1);
		//单期客户、多期客户前二三未逾期的客户当天还款可以使用购物券抵扣
		if( repayPlanItem.getTotalTerm() == 1 || ( repayPlanItem.getThisTerm()
				== repayPlanItem.getTotalTerm() && overdueTimes == 0) ){
			return true;
		}

		return false;
	}
	
	
	
}
