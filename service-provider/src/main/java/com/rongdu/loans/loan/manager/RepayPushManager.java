/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;


import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.RepayPushDAO;
import com.rongdu.loans.loan.entity.RepayPush;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款推送结果记录-实体管理实现类
 * @author likang
 * @version 2017-09-08
 */
@Service("repayPushManager")
public class RepayPushManager extends BaseManager<RepayPushDAO, RepayPush, String> {

	/**
	 * 保存推送记录
	 * @param repayPush
	 * @return
	 */
	public int saveRepayPush(RepayPush repayPush) {
		if(null != repayPush) {
			String assetId = repayPush.getAssetId();
			if(null != assetId){
				if(countByAssetId(assetId) == 0) {
					return dao.insert(repayPush);
				} else {
					if(repayPush.getPushResult() == RepayPush.RESULT_SUCCESS) {
						// 资产id有记录情况，删除以往记录，再重新插入
						dao.deleteAssetId(repayPush.getAssetId());
						return dao.insert(repayPush);
					} else {
						logger.error("assetId:[{}] 已经存在推送记录", assetId);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 根据资产id更新推送结果
	 * @param repayPush
	 * @return
	 */
	public int updatePushResultByAssetId(RepayPush repayPush) {
		return dao.updatePushResultByAssetId(repayPush);
	}

	/**
	 * 根据资产id统计记录条数
	 * @param assetId
	 * @return
	 */
	public int countByAssetId(String assetId) {
		return dao.countByAssetId(assetId);
	}

	/**
	 * 根据推送类型查询还款推送失败的数据
	 * @param pushType
	 * @return
	 */
	public List<RepayPush> getPushFailByType(Integer pushType) {
		return dao.getPushFailByType(pushType);
	}

	/**
	 * 查询还款到账状态
	 * @param payComOrderNo
	 * @return
	 */
	public String getPayStatus(String payComOrderNo) {
		return dao.getPayStatus(payComOrderNo);
	}

}
