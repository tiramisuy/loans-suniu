package com.rongdu.loans.loan.manager;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.compute.helper.ContractHelper;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.dao.ContractDAO;
import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.vo.PrePayCostingVO;

/**
 * 借款合同-实体管理接口
 * 
 * @author likang
 * @version 2017-07-11
 */
@Service("contractManager")
public class ContractManager {

	@Autowired
	private PromotionCaseManager promotionCaseManager;
	@Autowired
	private ContractDAO contractDAO;

	/**
	 * 根据申请编号查询到账信息
	 * 
	 * @param applyId
	 * @return
	 */
	public Contract getByApplyId(String applyId) {
		return contractDAO.getByApplyId(applyId);
	}

	/**
	 * 最近三天申请放款记录
	 * 
	 * @return
	 */
	public List<Contract> getRecentThreeDaysRecords() {
		return contractDAO.getRecentThreeDaysRecords();
	}

	/**
	 * 根据用户id查询未结清合同
	 * 
	 * @param userId
	 * @return
	 */
	public Contract getUnFinishContractByUserId(String userId) {
		return contractDAO.getUnFinishContractByUserId(userId);
	}

	/**
	 * 清算合同
	 * 
	 * @param contract
	 * @return
	 */
	public int OverContract(Contract contract) {
		if (null != contract) {
			contract.setStatus(Global.REPAY_PLAN_STATUS_OVER);
			contract.preUpdate();
			return contractDAO.updateStatus(contract);
		}
		return 0;
	}

	/**
	 * 根据贷款申请单生成合同
	 * 
	 * @param loanApply
	 * @param payTime
	 *            放款时间
	 * @return
	 */
	public Contract insert(LoanApply loanApply, Date payTime) {
		Contract contract = BeanMapper.map(loanApply, Contract.class);
		contract.preInsert();
		contract.setId(loanApply.getContNo());
		contract.setApplyId(loanApply.getId());
		contract.setLoanStartDate(payTime);
		contract.setLoanEndDate(ContractHelper.getLoanEndDate(loanApply, contract.getLoanStartDate()));
		contract.setPrincipal(loanApply.getApproveAmt());
		contract.setTotalTerm(loanApply.getTerm());
		contract.setOverdueFee(loanApply.getOverdueFee());
		contract.setOverdueRate(loanApply.getOverdueRate());
		contract.setStatus(Global.REPAY_PLAN_STATUS_UNOVER);
		contract.setContTime(payTime);
		contract.setPayTime(payTime);
		contract.setLoanDays(loanApply.getApproveTerm());
		contract.setRepayMethod(Integer.valueOf(loanApply.getRepayMethod()));
		contract.setGraceType(Global.DEFAULT_GRACE_TYPE);
		contract.setGraceDay(0);
		contract.setFixPenaltyInt(Integer.valueOf(Global.YES));
		contract.setCompInt(Integer.valueOf(Global.NO));
		contract.setTotalAmount(contract.getPrincipal().add(contract.getInterest()));
		contract.setRemark(null);
		// 一次性还本付息的时候计算提前还款费用
		if (RepayMethodEnum.ONE_TIME.getValue().equals(contract.getRepayMethod())) {
			PrePayCostingVO prePayCostingVO = promotionCaseManager.calPrepayFee(loanApply.getId());
			if (prePayCostingVO != null && prePayCostingVO.getActualPrepayFee() != null) {
				contract.setPrepayFee(prePayCostingVO.getActualPrepayFee());
			}
		}
		//y:合同
		contractDAO.insert(contract);
		return contract;
	}

	public List<Contract> findByIdList(List<String> idList) {
		return contractDAO.findByIdList(idList);
	}

	public int updateForDelay(Contract contract) {
		return contractDAO.updateForDelay(contract);
	}
	
	public int updateforRepayTime(Contract contract){
		return contractDAO.updateforRepayTime(contract);
	}
	
	public int delByApplyId(String applyId){
		return contractDAO.delByApplyId(applyId);
	} 

}
