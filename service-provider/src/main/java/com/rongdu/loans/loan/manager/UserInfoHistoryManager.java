package com.rongdu.loans.loan.manager;

import com.rongdu.loans.loan.dao.UserInfoHistoryDAO;
import com.rongdu.loans.loan.entity.UserInfoHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息快照本地接口
 * @author likang
 *
 */
@Service("userInfoHistoryManager")
public class UserInfoHistoryManager {

	@Autowired
	private UserInfoHistoryDAO userInfoHistoryDAO;

	/**
	 * 保存当前基本信息快照
	 * @param
	 * @return
	 */
	public int saveUserInfoSnap(
			String applyId, String userId, int applyCount, int loanSuccCount) {
		// 获取当前用户本信息
		UserInfoHistory userInfoHistory =
				userInfoHistoryDAO.getCurUserInfo(userId);
		// 补充申请编号
		userInfoHistory.setApplyId(applyId);
		userInfoHistory.setApplyCount(applyCount);
		userInfoHistory.setLoanSuccCount(loanSuccCount);
		userInfoHistory.preInsert();
		return userInfoHistoryDAO.insert(userInfoHistory);
	}

	public UserInfoHistory getByApplyId(String applyId) {
		return userInfoHistoryDAO.getByApplyId(applyId);
	}

	/**
	 * 根据申请编号统计
	 * @param applyId
	 * @return
	 */
	public int countByApplyId(String applyId) {
		return userInfoHistoryDAO.countByApplyId(applyId);
	}


}
