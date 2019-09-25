package com.rongdu.loans.loan.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.dao.OperationLogDAO;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.vo.AuthenticationStatusVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志本服务地接口
 * 
 * @author likang
 * 
 */
@Service("operationLogManager")
public class OperationLogManager {

	@Autowired
	private OperationLogDAO operationLogDao;

	@Autowired
	private CustUserManager custUserManager;

	/**
	 * 根据申请编号查询操作记录
	 * 
	 * @param applyId
	 * @return
	 */
	public List<OperationLog> getByApplyId(String applyId) {
		return operationLogDao.getByApplyId(applyId);
	}

	/**
	 * 根据userId查询操作记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<OperationLog> getByUserId(String userId) {
		return operationLogDao.getByUserId(userId);
	}

	/**
	 * 根据userId查询核阶段前的操作记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<OperationLog> getUnAuditbyUserId(String userId) {
		return operationLogDao.getUnAuditbyUserId(userId);
	}

	/**
	 * 保存操作记录
	 * 
	 * @param entity
	 * @return
	 */
	public int saveOperationLog(OperationLog entity) {
		return operationLogDao.insert(entity);
	}

	/**
	 * 根据userId汇总审核阶段前的操作记录
	 * 
	 * @param userId
	 * @return
	 */
	public Map<Integer, Integer> getOperationLogSummary(String userId, String productId) {
		// 根据申请编号获取操作记录
		List<OperationLog> list = (List<OperationLog>) JedisUtils.getObject(Global.USER_AUTH_PREFIX + userId);
		if (list == null) {
			list = getUnAuditbyUserId(userId);
			JedisUtils.setObject(Global.USER_AUTH_PREFIX + userId, list, 60 * 60 * 24);
		}
		String accountId = null;
		String bindId = null;
		CustUserVO custUserVO = (CustUserVO) JedisUtils.getObject(Global.USER_CACHE_PREFIX + userId);
		if (null != custUserVO) {
			accountId = custUserVO.getAccountId();
			bindId = custUserVO.getBindId();
		} else {
			// 从数据库获取
			CustUser user = custUserManager.getById(userId);
			accountId = user.getAccountId();
			bindId = user.getBindId();
		}

		// 构造返回对象
		Map<Integer, Integer> rz = new HashMap<Integer, Integer>();
		// 遍历列表
		if (null != list && list.size() > 0) {
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				OperationLog temp = list.get(i);
				if (null != temp && null != temp.getStage()) {
					if (temp.getStage() == XjdLifeCycle.LC_XUEXIN_1) {
						rz.put(temp.getStage(), AuthenticationStatusVO.YES);
					} else if (temp.getStage() == XjdLifeCycle.LC_TAOBAO_1) {
						rz.put(temp.getStage(), AuthenticationStatusVO.YES);
					} else if (temp.getStage() == XjdLifeCycle.LC_TFL_1) {
						rz.put(temp.getStage(), AuthenticationStatusVO.YES);
					} else if (temp.getStage() == XjdLifeCycle.LC_DEPOSIT_1) {
						rz.put(temp.getStage(), AuthenticationStatusVO.YES);
					} else if (temp.getStage() == XjdLifeCycle.LC_CREDIT_1) {
						rz.put(temp.getStage(), AuthenticationStatusVO.YES);
					} else {
						rz.put(temp.getStage(), (temp.getStatus() - temp.getStage() * 10));
					}
					// 过滤处理规则
					filterRule(rz, temp, productId, accountId, bindId);
				}
			}
		}
		return rz;
	}

	/**
	 * 过滤状态规则
	 * 
	 * @param rz
	 * @param operationLog
	 */
	private void filterRule(Map<Integer, Integer> map, OperationLog operationLog, String productId, String accountId,
			String bindId) {
		if (operationLog.getStatus().equals(XjdLifeCycle.LC_TEL_1)) {
			int pastDate = DateUtils.daysBetween(operationLog.getUpdateTime(), new Date());
			int effective = Global.OPERATOR_SESAME_EFFECTIVE_DAY;
			if (pastDate > effective) {
				map.put(operationLog.getStage(), 0);
			}
		}
	}

	public int insertBatch(List<OperationLog> operationLogList) {
		return operationLogDao.insertBatch(operationLogList);
	}

	public int delAuthByStatus(String userId, Integer status) {
		return operationLogDao.delAuthByStatus(userId, status);
	}
}
