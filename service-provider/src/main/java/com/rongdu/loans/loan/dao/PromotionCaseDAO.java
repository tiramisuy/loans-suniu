package com.rongdu.loans.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.PromotionOP;
import com.rongdu.loans.loan.vo.PrePayCostingVO;
import com.rongdu.loans.loan.vo.PromotionCaseVO;

/**
 * 贷款产品营销专案-数据访问接口
 * @author likang
 * @version 2017-07-06
 */
@MyBatisDao
public interface PromotionCaseDAO extends BaseDao<PromotionCase, String> {
	
	/**
	 * 根据申请信息获取营销方案
	 * @param promotionCaseOP
	 * @return
	 */
	List<PromotionCase> getByApplyInfo(PromotionCaseOP promotionCaseOP);
	
	
	/**
	 * 根据申请编号获取提前还款相关参数
	 * @param applyId
	 * @return
	 */
	PrePayCostingVO getPrePayInfoByApplyId(String applyId);
	
	/**
	 * 根据渠道ID来获取服务费率,日利率等参数
	 */
	List<PromotionCaseVO> getByChannelID(String channelId);
	
	/**
	 * 根据产品ID来获取服务费率,日利率等参数
	 */
	List<PromotionCaseVO> getByProductIDAndChannelId(@Param(value ="productId")String productId,@Param(value ="channelId")String channelId);

	
	List<PromotionCaseVO> getByProductIDAndChannelIdForPage(@Param(value = "page")Page<PromotionOP> page,
			@Param(value = "op") PromotionOP op);
	
	/**
	 * 查看是否特定渠道
	 * @param channel
	 * @return
	 */
	String checkByChannel(String channel);
	
	
	public int updatePromotion(@Param(value = "id")String id);
	
	public List<Map<String, String>> findAllChannel();
}
