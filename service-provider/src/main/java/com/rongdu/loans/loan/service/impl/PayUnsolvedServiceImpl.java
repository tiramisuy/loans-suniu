package com.rongdu.loans.loan.service.impl;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.PayUnsolvedService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.service.XianFengWithdrawService;
import com.rongdu.loans.pay.vo.XfWithdrawResultVO;

/**  
 * code y0621
* @Title: PayUnsolvedServiceImpl.java  
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
@Service(value="payUnsolvedService")
public class PayUnsolvedServiceImpl extends BaseService implements PayUnsolvedService {
	
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private XianFengWithdrawService xianFengWithdrawService;

	@Override
	public TaskResult processPayUnsolvedOrders() {
		logger.info("==========定时查询【先锋代付】交易处理中订单更新数据start==========");
		List<PayLogVO> list = payLogService.findPayUnsolvedOrders();
		
		int succNum = 0;
		int failNum = 0;
		int totalNum = list.size();
		String merchantNo = null;
		XfWithdrawResultVO vo = null;
		for (PayLogVO payLogVO : list) {
			try {
				merchantNo = payLogVO.getId();
				vo = xianFengWithdrawService.xfWithdrawQuery(merchantNo);
				if (vo.isSuccess()) {
					payLogVO.setStatus(WITHDRAWAL_SUCCESS.getValue().toString());
					payLogVO.setRemark(vo.getResMessage());
					payLogVO.setSuccTime(DateUtils.parseDatetime(vo.getTradeTime()));
					payLogVO.setSuccAmt(new BigDecimal(vo.getAmountYuan()));
					payLogService.update(payLogVO);
				} else if (vo.isFail()) {
					payLogVO.setStatus(WITHDRAWAL_FAIL.getValue().toString());
					payLogVO.setRemark(vo.getResMessage());
					payLogService.update(payLogVO);
				}
				succNum++;
			} catch (Exception e) {
				failNum++;
				logger.error("查询先锋代付订单状态，更新订单失败： " + JSONObject.toJSONString(payLogVO), e);
			}
		}
		logger.info("==========定时查询【先锋代付】交易处理中订单更新数据end,toalNum={},succNum={},failNum={}==========", totalNum,succNum, failNum);
		return new TaskResult(succNum, failNum);
	}

}
