package com.rongdu.loans.loan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.loan.dao.ShoppedCouponDAO;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.option.ShoppedCouponOP;
import com.rongdu.loans.loan.service.ShoppedCouponService;
import com.rongdu.loans.loan.vo.ShoppedCouponVO;

@Service("shoppedCouponService")
public class ShoppedCouponServiceImpl extends BaseService implements ShoppedCouponService {

	@Autowired
	private ShoppedCouponDAO shoppedCouponDAO;
	@Autowired
	private CustCouponManager custCouponManager;
	@Autowired
	private LoanApplyManager loanApplyManager;

	@Override
	public Page<ShoppedCouponVO> getShoppedCouponList(ShoppedCouponOP op) {
		Page page = new Page(op.getPageNo(), op.getPageSize());
		List<ShoppedCouponVO> list = shoppedCouponDAO.getShoppedCouponList(page, op);
		page.setList(list);
		return page;
	}

	@Override
	public void generateCoupon(String applyId,String userName) {
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
/*		// 融360不放券
		if (loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15 && "4".equals(loanApply.getSource())
				&& "RONG".equals(loanApply.getChannelId())) {
			return;
		}
		// 奇虎360不放券
		if ((loanApply.getApproveTerm() == Global.XJD_DQ_DAY_15 || loanApply.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28) &&
						"4".equals(loanApply.getSource()) && "SLLAPIJHH".equals(loanApply.getChannelId())) {
					isGenerateCustCoupon = false;
				}
		*/
		// 同一笔订单不重复发券
		ShoppedCouponOP op = new ShoppedCouponOP();
		op.setApplyId(applyId);
		int num = shoppedCouponDAO.countShoppedCoupon(op);
		if (num > 0) {
			return;
		}
		loanApply.setUpdateBy(userName);
		loanApply.setUpdateTime(new Date());
		custCouponManager.generateCustCoupon(loanApply);
	}

}
