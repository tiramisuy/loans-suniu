package com.rongdu.loans.loan.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.loan.dao.PromotionCaseDAO;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.vo.CostingResultVO;
import com.rongdu.loans.loan.vo.FeeCeseVO;
import com.rongdu.loans.loan.vo.PrePayCostingVO;

/**
 * 营销方案本地服务接口
 * @author likang
 *
 */
@Service("promotionCaseManager")
public class PromotionCaseManager extends BaseManager<PromotionCaseDAO, PromotionCase, String> {

	// 营销方案列表缓存时间
	private static final int CACHESECONDS = 24*60*60;

	@Autowired
	private PromotionCaseDAO promotionCaseDAO;

	/**
	 * 根据申请信息获取营销方案列表
	 * @param promotionCaseOP
	 * @return
	 */
	public List<PromotionCase> getByListApplyInfo(
			PromotionCaseOP promotionCaseOP) {
		// 获取营销方案列表
		List<PromotionCase> list = new ArrayList<PromotionCase>();
		// 拼接缓存key
		StringBuilder sb = new StringBuilder();
		sb.append(Global.PROMOTION_CASE_CACHE_PREFIX)
				.append(promotionCaseOP.getChannelId())
				.append("_")
				.append(promotionCaseOP.getProductId());
		// 从缓存中获取营销方案
		list = (List<PromotionCase>) JedisUtils.getObjectList(sb.toString());
		if(null == list || list.size() == 0 || null == list.get(0)) {
			list = promotionCaseDAO.getByApplyInfo(promotionCaseOP);
			JedisUtils.setObjectList(sb.toString(), list, CACHESECONDS);
		}
		return list;
	}
	

	/**
	 * 根据申请信息获取营销方案
	 * @param promotionCaseOP
	 * @return
	 */
	public PromotionCase getByApplyInfo(PromotionCaseOP promotionCaseOP) {
		// 构造返回参数
		PromotionCase rz = null;
		if(null != promotionCaseOP
				&& null != promotionCaseOP.getApplyAmt()
				&& null != promotionCaseOP.getApplyTerm()) {
			// 获取营销方案列表
			List<PromotionCase> list = getByListApplyInfo(promotionCaseOP);
			// 过滤营销方案列表
			if(null != list && list.size() > 0) {
				int size = list.size();
				for(int i = 0; i < size; i++) {
					PromotionCase temp = list.get(i);
					if(null != temp) {
						if(temp.getAmtBegin() <= promotionCaseOP.getApplyAmt().intValue()
								&& temp.getAmtEnd() >= promotionCaseOP.getApplyAmt().intValue()
								&& temp.getTermBegin() <= promotionCaseOP.getApplyTerm()
								&& temp.getTermEnd() >= promotionCaseOP.getApplyTerm()) {
							rz = temp;
							break;
						}
					}
				}
			}
		}
		return rz;
	}

	/**
	 * 费用计算
	 * @return
	 */
	public CostingResultVO Costing(PromotionCaseOP promotionCaseOP) {
		CostingResultVO rzVo = new CostingResultVO();
		PromotionCase promotionCase = getByApplyInfo(promotionCaseOP);
		if(null != promotionCase
				&& null != promotionCase.getRatePerDay()
				&& null != promotionCase.getServFeeRate()) {
			// 日利息（实际）
			BigDecimal dayInterest =
					CostUtils.calActualDayInterest(
							promotionCase.getRatePerYear().divide(BigDecimal.valueOf(Global.YEAR_DAYS_FENQI), BigDecimal.ROUND_HALF_UP),
							promotionCaseOP.getApplyAmt(),
							promotionCase.getDiscountValue());
			// 总利息
			BigDecimal totalInterest =
					CostUtils.calTotalInterest(
							dayInterest, promotionCaseOP.getApplyTerm());

			// 服务费
			BigDecimal servFee = CostUtils.calServFee(
					promotionCase.getServFeeRate(),
					promotionCaseOP.getApplyAmt());
			// 利率（日化）
			rzVo.setRatePerDay(CostUtils.calActualDayRate(
					promotionCase.getRatePerDay(),
					promotionCase.getDiscountValue()));
			rzVo.setServFeeRate(promotionCase.getServFeeRate());
			rzVo.setDayInterest(dayInterest);
			rzVo.setServFee(servFee);
			rzVo.setTotalInterest(totalInterest);
			rzVo.setToAccountAmt(CostUtils.calToAccountAmt(
					promotionCaseOP.getApplyAmt(), servFee));
			rzVo.setRealRepayAmt(CostUtils.calRealRepayAmt(
					promotionCaseOP.getApplyAmt(), totalInterest));
		}
		return rzVo;
	}

	/**
	 * 提前还款手续费计算
	 * @return
	 */
	public PrePayCostingVO calPrepayFee(String applyId) {
		if(StringUtils.isBlank(applyId)) {
			return null;
		}
		PrePayCostingVO vo =
				promotionCaseDAO.getPrePayInfoByApplyId(applyId);
		if(null != vo
				&& null != vo.getPrepayFeeType()
				&& null != vo.getPrepayValue()
				&& null != vo.getLoanAmt()) {
			if(vo.getPrepayFeeType() == PromotionCase.FEE_TYPE_RATE) {
				BigDecimal prepayFee =
						vo.getLoanAmt().multiply(
								vo.getPrepayValue()).setScale(
								Global.DEFAULT_AMT_SCALE,
								BigDecimal.ROUND_HALF_UP);
				vo.setActualPrepayFee(prepayFee);
				return vo;
			}
		}
		return null;
	}

	/**
	 * 获取费用方案列表
	 * @return
	 */
	public List<FeeCeseVO> getFeeCeseList(PromotionCaseOP promotionCaseOP) {
		// 构造返回对象
		List<FeeCeseVO> rzList = new ArrayList<FeeCeseVO>();
		if(null != promotionCaseOP
				&& null != promotionCaseOP.getApplyAmt()
				&& null != promotionCaseOP.getApplyTerm()) {
			// 获取营销方案列表
			List<PromotionCase> list =
					getByListApplyInfo(promotionCaseOP);
			// 过滤营销方案列表
			if(null != list && list.size() > 0) {
				FeeCeseVO feeCeseVO = null;
				int size = list.size();
				for(int i = 0; i < size; i++) {
					PromotionCase temp = list.get(i);
					if(null != temp) {
						feeCeseVO = new FeeCeseVO();
						// 价格区间
						feeCeseVO.setLoanAmtRange(getRangeStr(
								temp.getAmtBegin(), temp.getAmtEnd()));
						// 期限区间
						feeCeseVO.setLoanTermRange(getRangeStr(
								temp.getTermBegin(), temp.getTermEnd()));
						// 逾期管理费
						feeCeseVO.setOverdueFee(
								String.valueOf(temp.getOverdueFee()));
						// 逾期费用费率
						feeCeseVO.setDefaultFeeRate(
								String.valueOf(temp.getOverdueValue()));
						// 服务费率
						feeCeseVO.setServFeeRate(
								String.valueOf(temp.getServFeeRate()));
						// 提前还款费率
						feeCeseVO.setPrepayFeeRate(
								String.valueOf(temp.getPrepayValue()));
						rzList.add(feeCeseVO);
					}
				}
			}
		}
		return rzList;
	}

	/**
	 * 区间值拼接
	 * @param begin
	 * @param end
	 * @return
	 */
	private String getRangeStr(Integer begin, Integer end) {
		String rangeStr = "-";
		if(null != begin && null != end) {
			StringBuilder bf = new StringBuilder();
			bf.append(begin).append(rangeStr).append(end);
			return bf.toString();
		}
		return rangeStr;
	}

	public String checkByChannel(String channel) {
		return promotionCaseDAO.checkByChannel(channel);
	}
	
	public List<Map<String, String>> findAllChannel() {
		return promotionCaseDAO.findAllChannel();
	}

	

}
