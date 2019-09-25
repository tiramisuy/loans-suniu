package com.rongdu.loans.loan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.OperationLogManager;
import com.rongdu.loans.loan.service.OperationLogService;
import com.rongdu.loans.loan.vo.AuthenticationStatusVO;
import com.rongdu.loans.loan.vo.OperationLogVO;

/**
 * 操作日志Service接口实现类
 * 
 * @author likang
 * @version 2017-07-04
 */
@Service("operationLogService")
public class OperationLogServiceImpl extends BaseService implements OperationLogService {

	@Autowired
	private OperationLogManager operationLogLocalService;

	@Autowired
	private LoanApplyManager loanApplyManager;

	@Autowired
	private CustUserManager custUserManager;

	@Override
	public AuthenticationStatusVO getAuthenticationStatus(String userId, String productId) {
		// 构造返回对象
		AuthenticationStatusVO rz = new AuthenticationStatusVO();
		// 获取操作日志记录
		Map<Integer, Integer> operationLogMap = operationLogLocalService.getOperationLogSummary(userId, productId);
		if (null != operationLogMap && operationLogMap.size() > 0) {
			// 身份认证识别状态
			if (null == operationLogMap.get(XjdLifeCycle.LC_OCR)) {
				rz.setOcrStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setOcrStatus(operationLogMap.get(XjdLifeCycle.LC_OCR));
			}
			// 人脸认证
			if (null == operationLogMap.get(XjdLifeCycle.LC_FACE)) {
				rz.setFaceStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setFaceStatus(operationLogMap.get(XjdLifeCycle.LC_FACE));
			}

			// 身份认证结果
			if ((rz.getOcrStatus() + rz.getFaceStatus()) == 2) {
				rz.setIdentityStatus(AuthenticationStatusVO.YES);
			} else {
				rz.setIdentityStatus(AuthenticationStatusVO.NO);
			}

			// 基本信息
			if (null == operationLogMap.get(XjdLifeCycle.LC_BASE)) {
				rz.setBaseInfoStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setBaseInfoStatus(operationLogMap.get(XjdLifeCycle.LC_BASE));
			}

			// 授权认证
			// 运营商认证
			if (null == operationLogMap.get(XjdLifeCycle.LC_TEL)) {
				rz.setTelOperatorStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setTelOperatorStatus(operationLogMap.get(XjdLifeCycle.LC_TEL));
			}

			// 芝麻信用认证
			rz.setSesameCreditStatus(AuthenticationStatusVO.YES); // 芝麻初始化为已认证

			// 学信认证
			if (null == operationLogMap.get(XjdLifeCycle.LC_XUEXIN_1)) {
				rz.setXueXinStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setXueXinStatus(AuthenticationStatusVO.YES);
			}
			// 淘宝认证
			if (null == operationLogMap.get(XjdLifeCycle.LC_TAOBAO_1)) {
				rz.setTaoBaoStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setTaoBaoStatus(AuthenticationStatusVO.YES);
			}

			// 授权认证
			// if((rz.getTelOperatorStatus() + rz.getSesameCreditStatus()) == 2)
			// {
			if (AuthenticationStatusVO.YES.equals(rz.getTelOperatorStatus())) {
				rz.setAuthorizationStatus(AuthenticationStatusVO.YES);
			} else {
				rz.setAuthorizationStatus(AuthenticationStatusVO.NO);
			}

			// 开电子账户状态
			if (null == operationLogMap.get(XjdLifeCycle.LC_ID)) {
				rz.setEleAccountStatus(AuthenticationStatusVO.NO);
			} else {
				rz.setEleAccountStatus(operationLogMap.get(XjdLifeCycle.LC_ID));
			}

			// 信用卡
			String cacheKey = "CREDIT_CARD_OPERATION_" + userId;
			String creditCardOperationValue = JedisUtils.get(cacheKey);
			if (creditCardOperationValue != null) {
				rz.setCreditCardStatus(AuthenticationStatusVO.ING);
			} else {
				if (null == operationLogMap.get(XjdLifeCycle.LC_CREDIT_1)) {
					rz.setCreditCardStatus(AuthenticationStatusVO.NO);
				} else {
					rz.setCreditCardStatus(AuthenticationStatusVO.YES);
				}
			}

			/*
			 * if(LoanProductEnum.CCD.getId().equals(productId)) { //投复利代扣授权
			 * if(null == operationLogMap.get(XjdLifeCycle.LC_DEPOSIT_1)) {
			 * rz.setDepositStatus(AuthenticationStatusVO.NO); } else {
			 * rz.setDepositStatus(AuthenticationStatusVO.YES); } //最终状态
			 * if(rz.getDepositStatus() + rz.getEleAccountStatus() == 2) {
			 * rz.setBindCardStatus(AuthenticationStatusVO.YES); } else {
			 * rz.setBindCardStatus(AuthenticationStatusVO.NO); } }
			 */
		}
		return rz;
	}

	@Override
	public int getAuthStatus(String userId, String productId) {
		AuthenticationStatusVO rz = this.getAuthenticationStatus(userId, productId);
		if (rz != null && AuthenticationStatusVO.YES.equals(rz.getIdentityStatus())
				&& AuthenticationStatusVO.YES.equals(rz.getBaseInfoStatus())
				&& AuthenticationStatusVO.YES.equals(rz.getAuthorizationStatus())
				&& AuthenticationStatusVO.YES.equals(rz.getEleAccountStatus())) {
			return AuthenticationStatusVO.YES;
		}
		return AuthenticationStatusVO.NO;
	}

	public int getMoreAuthStatus(String userId, String productId) {
		AuthenticationStatusVO rz = this.getAuthenticationStatus(userId, productId);
		if (rz != null && AuthenticationStatusVO.YES.equals(rz.getCreditCardStatus())) {
			return AuthenticationStatusVO.YES;
		}
		return AuthenticationStatusVO.NO;
	}

	public List<OperationLogVO> getByUserId(String userId) {
		List<OperationLogVO> voList = new ArrayList<OperationLogVO>();
		List<OperationLog> list = operationLogLocalService.getByUserId(userId);
		if (list != null && list.size() > 0) {
			for (OperationLog log : list) {
				OperationLogVO vo = new OperationLogVO();
				vo.setUserId(log.getUserId());
				vo.setStage(log.getStage());
				vo.setStatus(log.getStatus());
				vo.setTime(log.getTime());
				voList.add(vo);
			}
		}
		return voList;
	}

	public int delAuthByStatus(String userId, Integer status) {
		int rz = operationLogLocalService.delAuthByStatus(userId, status);
		if (rz > 0) {
			if (StringUtils.isNotBlank(userId)) {
				// 删除缓存用户认证信息
				JedisUtils.delObject(Global.USER_AUTH_PREFIX + userId);
			} else {
				logger.error("用户id为空");
			}
		}
		return rz;
	}
}
