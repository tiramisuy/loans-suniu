package com.rongdu.loans.loan.service;

import java.util.Date;
import java.util.List;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.ShopWithholdOP;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.ShopWithholdVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * 购物款代扣
 * 
 * @author likang
 */
public interface ShopWithholdService {

	/**
	 * 代扣处理逻辑
	 * 
	 * @param applyId
	 * @param withHoldAmt
	 * @param custUserId
	 * @return
	 */
	public XfAgreementPayResultVO doShopWithhold(String applyId, String amount);

	/**
	 * 定时任务执行2、3次代扣
	 * 
	 * @param list
	 * @return
	 */
	public TaskResult doShopWithholdTask();

	/**
	 * 客服手动代扣
	 * 
	 * @param op
	 * @return
	 */
	public boolean handShopWithhold(String id);

	/**
	 * 
	 * @param applyId
	 * @param remark
	 * @param status
	 *            0=成功 1=失败
	 * @return
	 */
	public boolean updateShopWithhold(String applyId, String remark, Integer status);

	public Page<ShopWithholdVO> selectShopWithholdList(ShopWithholdOP op);

	public List<RepayDetailListVO> getLoanApply(ShopWithholdOP op);

	public int insertRepayLog(String applyId, String chlId, String chlName);

	/**
	 * 宝付代扣购物金
	 * 
	 * @param applyId
	 * @return 1=成功，2=失败，3=处理中
	 */
	public int baofooWithholdShopping(String applyId);

	Boolean isWithholdSuccess(String lid);

	/**
	 * 用于修改还款日 更改合同表放款时间，然后生成计划总表时间和计划明细表时间 做修改
	 * 
	 * @param loanApply
	 * @return
	 */
	public void updateRepayTime(String applyId, Date payTime);
	/**
	 * 修改放款渠道
	 */
	void changePaychannel(String id, String paychannel);
}
