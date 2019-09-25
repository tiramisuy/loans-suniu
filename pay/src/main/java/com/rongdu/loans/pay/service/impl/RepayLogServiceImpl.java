/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.PayChannelEnum;
import com.rongdu.loans.pay.op.AuthPayOP;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.option.FlowListOP;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogListVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.entity.RepayLog;
import com.rongdu.loans.pay.manager.RepayLogManager;

/**
 * 充值/还款-业务逻辑实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-11
 */
@Service("repayLogService")
public class RepayLogServiceImpl extends BaseService implements RepayLogService {

	/**
	 * 充值/还款-实体管理接口
	 */
	@Autowired
	private RepayLogManager repayLogManager;
	@Autowired
	private CustUserService custUserService;

	// @Autowired
	// private SettlementManager settlementManager;
	//
	// @Autowired
	// private LoanRepayPlanManager loanRepayPlanManager;
	// /**
	// * 合同管理接口
	// */
	// @Autowired
	// private ContractManager contractManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<RepayLogListVO> getRepayLogList(FlowListOP op) {
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		List<RepayLogListVO> voList = repayLogManager.getRepayLogList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	public List<RepayLogListVO> getRepayLogListForExport(FlowListOP op) {
		List<RepayLogListVO> voList = repayLogManager.getRepayLogList(null, op);
		return voList;
	}

	// /**
	// * 保存还款记录
	// * 流程： 1、保存支付记录
	// * 2、账单结算 (支付失败不结算)
	// */
	// @Override
	// public ConfirmPayResultVO saveRepayLog(RePayOP rePayOP) {
	// if(null == rePayOP) {
	// logger.error("error, the param is null");
	// return null;
	// }
	// // // 构造参数对象
	// // RepayLog entity = new RepayLog();
	// // // 用户id
	// String userId = rePayOP.getUserId();
	// // entity.setUserId(userId);
	// // 根据用户id查询未结清合同
	// Contract contract =
	// contractManager.getUnFinishContractByUserId(userId);
	// if(null != contract) {
	// rePayOP.setApplyId(contract.getApplyId());
	// // entity.setApplyId(contract.getApplyId());
	// // // 合同编号
	// // entity.setContractId(contract.getId());
	// } else {
	// logger.error("[{}], not find contract", userId);
	// return null;
	// }
	// // // 交易金额
	// // entity.setTxAmt(new BigDecimal(rePayOP.getTxAmt()));
	// // Date txTime =
	// // DateUtils.parse(rePayOP.getPaySuccTime(),
	// // Global.BAOFOO_TIEM_PATTERN);
	// // entity.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
	// // entity.setTxTime(txTime);
	// // entity.setTxType(rePayOP.getTxType());
	// // // TODO 交易服务费暂时未0
	// // entity.setTxFee(new BigDecimal(0));
	// // entity.setBindId(rePayOP.getBindId());
	// // entity.setBankCode(rePayOP.getBankCode());
	// // entity.setCardNo(rePayOP.getCardNo());
	// // entity.setBankName(rePayOP.getBankName());
	// // entity.setChlCode(Global.BAOFOO_CHANNEL_CODE);
	// // entity.setChlName(Global.BAOFOO_CHANNEL_NAME);
	// // entity.setChlOrderNo(rePayOP.getPayComOrderNo());
	// // entity.setSuccAmt(
	// // new BigDecimal(rePayOP.getPaySuccAmt()));
	// // entity.setSuccTime(txTime);
	// // entity.setOrderInfo(rePayOP.getOrderInfo());
	// // entity.setTerminal(rePayOP.getTerminal());
	// // entity.setTerminalId(rePayOP.getTerminal());
	// // entity.setGoodsId(Global.PAY_GOODS_ID);
	// // entity.setGoodsName(Global.PAY_GOODS_NAME);
	// // entity.setGoodsNum(1);
	// // entity.setId(rePayOP.getPayComOrderNo());
	// // entity.setIp(rePayOP.getIp());
	// // entity.setStatus(rePayOP.getPayStatus());
	// // entity.setNewRecord(true);
	// // entity.preInsert();
	// // int rz = repayLogManager.insert(entity);
	//
	// // 账单结算
	// ConfirmPayResultVO vo = settlementManager.Settlement(rePayOP);
	// return vo;
	// }
	//
	// @Override
	// public StatementVO getStatementByItemId(String repayPlanItemId) {
	// if(StringUtils.isNotBlank(repayPlanItemId)) {
	// return loanRepayPlanManager.getStatementByItemId(repayPlanItemId);
	// } else {
	// logger.error("the param repayPlanItemId is null");
	// }
	// return null;
	// }

	@Override
	public RepayLogVO get(String id) {
		RepayLog entity = repayLogManager.get(id);
		RepayLogVO vo = null;
		if (entity != null) {
			vo = BeanMapper.map(entity, RepayLogVO.class);
		}
		return vo;
	}

	@Override
	public int save(RepayLogVO vo) {
		RepayLog entity = BeanMapper.map(vo, RepayLog.class);
		return repayLogManager.insert(entity);
	}

	@Override
	public int update(RepayLogVO vo) {
		RepayLog entity = BeanMapper.map(vo, RepayLog.class);
		return repayLogManager.update(entity);
	}

	@Override
	public RepayLogVO findByChlOrderNo(String orderNo) {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("chl_order_no", orderNo));
		RepayLog entity = repayLogManager.getByCriteria(criteria);
		RepayLogVO vo = BeanMapper.map(entity, RepayLogVO.class);
		return vo;
	}
	
	@Override
	public int updateRepayResult(RepayLogVO vo) {
		RepayLog entity = BeanMapper.map(vo, RepayLog.class);
		return repayLogManager.update(entity);
	}

	/**
	 * 代付成功后，更新原支付订单的“payStatus”状态
	 * 
	 * @param origOrderNo
	 * @param payStatus
	 */
	public int updatePayStatus(String origOrderNo, String payStatus) {
		return repayLogManager.updatePayStatus(origOrderNo, payStatus);
	}

	@Override
	public Long countPayingByRepayPlanItemId(String repayPlanItemId) {
		return repayLogManager.countPayingByRepayPlanItemId(repayPlanItemId);
	}

	public Long countPayingByApplyId(String applyId) {
		return repayLogManager.countPayingByApplyId(applyId);
	}

	public List<RepayLogVO> findUnsolvedOrders() {
		return repayLogManager.findUnsolvedOrders();
	}

	@Override
	public RepayLogVO findByRepayPlanItemId(String repayPlanItemId) {
		RepayLog entity = repayLogManager.findByRepayPlanItemId(repayPlanItemId);
		RepayLogVO vo = BeanMapper.map(entity, RepayLogVO.class);
		return vo;
	}

	@Override
	public Long countPayingByIdNo(String idNo) {
		return repayLogManager.countPayingByIdNo(idNo);
	}

	@Override
	public RepayLogVO saveRepayLog(ConfirmAuthPayVO result, AuthPayOP op) {
		CustUserVO user = custUserService.getCustUserById(op.getUserId());
		RepayLogVO order = new RepayLogVO();
		order.setId(result.getReqNo());
		order.setNewRecord(true);
		order.setUserId(op.getUserId());
		order.setApplyId(op.getApplyId());
		order.setContractId(op.getContractId());
		order.setRepayPlanItemId(op.getRepayPlanItemId());

		order.setUserName(user.getRealName());
		order.setIdNo(user.getIdNo());
		order.setMobile(user.getMobile());
		order.setBankCode(user.getBankCode());
		order.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		order.setCardNo(user.getCardNo());
		order.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());

		if (Global.REPAY_TYPE_AUTO.equals(op.getTxType())) {// 后台代扣
			order.setTxType("WITHHOLD");
		} else {
			order.setTxType("AUTH_PAY");
		}
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMdd")));
		order.setTxTime(new Date());
		order.setTxAmt(new BigDecimal(result.getSuccAmt()));
		order.setTxFee(BigDecimal.ZERO);

		order.setTerminal(op.getSource());
		order.setIp(op.getIp());
		order.setChlOrderNo(result.getOrderNo());
		PayChannelEnum payChannel = PayChannelEnum.get(op.getPayChannel());
		order.setChlCode(payChannel.getChannelCode());
		order.setChlName(payChannel.getChannelName());
		order.setGoodsName("聚宝钱包还款");
		order.setGoodsNum(1);
        /*if (StringUtils.isNotBlank(op.getGoodsId())) {
            order.setGoodsId(op.getGoodsId());
        }*/

		order.setStatus(result.getStatus());
		order.setRemark(result.getMsg());
		order.setPayType(op.getPayType());
		if (result.isSuccess()) {
			order.setSuccAmt(new BigDecimal(result.getSuccAmt()));
			order.setSuccTime(DateUtils.parse(result.getSuccTime()));
			//order.setStatus(ErrInfo.SUCCESS.getCode());
		}

		//order.setCouponId(op.getCouponId());//优惠券ID
		logger.debug("协议支付-正在保存支付订单：{}，{}元，{}", user.getRealName(), result.getSuccAmt(), result.getReqNo());
		int rz = this.save(order);
		if (rz == 0){
			return null;
		}
		return order;
	}
}