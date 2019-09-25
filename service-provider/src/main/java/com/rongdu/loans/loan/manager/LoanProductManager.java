package com.rongdu.loans.loan.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.compute.AverageCapitalPlusInterestUtils;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanLimitEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.LoanPurposeEnum;
import com.rongdu.loans.enums.LoanTermEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.dao.LoanProductDAO;
import com.rongdu.loans.loan.dao.PromotionCaseDAO;
import com.rongdu.loans.loan.entity.LoanProduct;
import com.rongdu.loans.loan.option.LoanProductOP;
import com.rongdu.loans.loan.option.PromotionOP;
import com.rongdu.loans.loan.vo.LoanProductVO;
import com.rongdu.loans.loan.vo.PromotionCaseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("loanProductManager")
public class LoanProductManager extends BaseManager<LoanProductDAO, LoanProduct, String> {

	@Autowired
	private LoanProductDAO loanProductDao;

	@Autowired
	private LoanProductTermManager loanProductTermLocalService;

	@Autowired
	private PromotionCaseDAO promotionCaseDAO;

	// 申请编号缓存时间（3天）
	private static final int CACHESECONDS = 3 * 24 * 60 * 60;

	/**
	 * 插入数据
	 * 
	 * @param op
	 * @return
	 */
	public int saveLoanProduct(LoanProductOP op) {
		LoanProduct loanProduct = new LoanProduct();
		toLoanProduct(op, loanProduct);
		loanProduct.setCreateBy("system");
		loanProduct.setUpdateBy("system");
		loanProduct.setCreateTime(new Date());
		loanProduct.setUpdateTime(new Date());
		loanProduct.setId(op.getProductId());
		return loanProductDao.insert(loanProduct);
	}

	/**
	 * 更新数据
	 * 
	 * @param op
	 * @return
	 */
	public int updateLoanProduct(LoanProductOP op) {
		LoanProduct loanProduct = new LoanProduct();

		// 清理产品信息缓存
		JedisUtils.delObject(op.getProductId() + Global.LOAN_PRO_SUFFIX);

		toLoanProduct(op, loanProduct);
		loanProduct.setCreateBy("system");
		loanProduct.setUpdateBy("system");
		loanProduct.preUpdate();
		return loanProductDao.update(loanProduct);
	}

	/**
	 * 查询产品信息
	 * 
	 * @param id
	 * @return
	 */
	public LoanProduct getById(String id) {
		return loanProductDao.getById(id);
	}

	/**
	 * LoanProduct转换LoanProductOP
	 * 
	 * @return
	 */
	private void toLoanProduct(LoanProductOP op, LoanProduct rz) {
		if (null != op) {
			if (null == rz) {
				rz = new LoanProduct();
			}
			rz.setId(op.getProductId());
			rz.setName(op.getName());
			rz.setType(op.getType());
			rz.setDescription(op.getDescription());
			rz.setMinIncrAmt(op.getMinIncrAmt());
			rz.setMinAmt(op.getMinAmt());
			rz.setMaxAmt(op.getMaxAmt());
			rz.setRepayMethod(op.getRepayMethod());
			rz.setPrepay(op.getPrepay());
			rz.setMinLoanDay(op.getMinLoanDay());
			rz.setStartInterest(op.getStartInterest());
			rz.setGraceDay(op.getGraceDay());
			rz.setStatus(op.getStatus());
		}
	}

	/**
	 * 查询产品明细
	 * 
	 * @param productId
	 * @return
	 */
	public LoanProductVO getLoanProductDetail(String productId) {
		// 设置返回参数
		LoanProductVO rz = null;
		// 参数判断
		if (StringUtils.isBlank(productId)) {
			logger.info("参数不合法");
		} else {
			// 从缓存获取结果
			String cacheKey = productId + Global.LOAN_PRO_SUFFIX;
			rz = (LoanProductVO) JedisUtils.getObject(cacheKey);
			if (null == rz) {
				// 获取产品信息
				rz = new LoanProductVO();
				LoanProduct loanProduct = getById(productId);
				if (null != loanProduct) {
					if (LoanProductEnum.TFL.getId().equals(productId) || LoanProductEnum.LYFQ.getId().equals(productId)) {
						LinkedHashMap<String, String> termMap = new LinkedHashMap<String, String>();
						for (LoanTermEnum p : LoanTermEnum.values()) {
							termMap.put(p.getId(), p.getName());
						}

						// 借款用途
						LinkedHashMap<String, String> pMap = new LinkedHashMap<String, String>();
						for (LoanPurposeEnum p : LoanPurposeEnum.values()) {
							pMap.put(p.getId(), p.getName());
						}

						// 还款方式
						LinkedHashMap<Integer, String> repayMap = new LinkedHashMap<Integer, String>();

						LoanProductEnum productEnum = LoanProductEnum.get(productId);
						switch (productEnum) {
						case TFL:
							repayMap.put(RepayMethodEnum.INTEREST.getValue(), RepayMethodEnum.INTEREST.getDesc());
							repayMap.put(RepayMethodEnum.EXPIRE.getValue(), RepayMethodEnum.EXPIRE.getDesc());
							break;
						case LYFQ:
							repayMap.put(RepayMethodEnum.PRINCIPAL_INTEREST.getValue(),
									RepayMethodEnum.PRINCIPAL_INTEREST.getDesc());
							break;
						default:
							repayMap.put(RepayMethodEnum.INTEREST.getValue(), RepayMethodEnum.INTEREST.getDesc());
							repayMap.put(RepayMethodEnum.EXPIRE.getValue(), RepayMethodEnum.EXPIRE.getDesc());
							repayMap.put(RepayMethodEnum.PRINCIPAL_INTEREST.getValue(),
									RepayMethodEnum.PRINCIPAL_INTEREST.getDesc());
							break;
						}

						rz.setName(loanProduct.getName());
						rz.setMinAmt(loanProduct.getMinAmt());
						rz.setPersonAmt(new BigDecimal(LoanLimitEnum.MIN.getValue()));
						rz.setMaxAmt(loanProduct.getMaxAmt());
						rz.setLoanProductTerm(termMap);
						rz.setLoanPurpose(pMap);
						rz.setRepayMethods(repayMap);

						rz.setRepayMethod(loanProduct.getRepayMethod());
						rz.setRepayFreq(loanProduct.getRepayFreq());
						rz.setRepayUnit(loanProduct.getRepayUnit());
						rz.setGraceDay(loanProduct.getGraceDay());
					} else {
						// 获取产品周期信息
						/*
						 * List<LoanProductTermVO> list =
						 * loanProductTermLocalService
						 * .getByProductId(productId);
						 */
						// 获取产品费率信息
						List<PromotionCaseVO> rateList = promotionCaseDAO.getByProductIDAndChannelId(productId,
								LoanProductEnum.CCD.getId().equals(productId) ? ChannelEnum.CHENGDAI.getCode()
										: ChannelEnum.JUQIANBAO.getCode());
						// 设置返回值
						fromLoanProduct(loanProduct, rz);
						/* rz.setLoanProductTerm(list); */
						rz.setLoanProductRate(rateList);
					}
					// 结果保存到缓存
					JedisUtils.setObject(cacheKey, rz, CACHESECONDS);
				}
			}
		}
		return rz;
	}

	/**
	 * LoanProduct对象投射到LoanProductVO
	 * 
	 * @return
	 */
	private void fromLoanProduct(LoanProduct entity, LoanProductVO rz) {
		if (null != entity) {
			if (null == rz) {
				rz = new LoanProductVO();
			}
			rz.setProductId(entity.getId());
			rz.setName(entity.getName());
			rz.setType(entity.getType());
			rz.setDescription(entity.getDescription());
			rz.setMinIncrAmt(entity.getMinIncrAmt());
			rz.setMinAmt(entity.getMinAmt());
			rz.setMaxAmt(entity.getMaxAmt());
			rz.setRepayFreq(entity.getRepayFreq());
			rz.setRepayUnit(entity.getRepayUnit());
			rz.setRepayMethod(entity.getRepayMethod());
			rz.setPrepay(entity.getPrepay());
			rz.setMinLoanDay(entity.getMinLoanDay());
			rz.setStartInterest(entity.getStartInterest());
			rz.setGraceDay(entity.getGraceDay());
			rz.setStatus(entity.getStatus());
		}
	}

	public List<LoanProductVO> getXJDLoanProductDetail(String productId) {
		// 设置返回参数
		List<LoanProductVO> resultList = null;
		// 从缓存获取结果
		String cacheKey = productId + Global.LOAN_PRO_SUFFIX + "_NEW";
		resultList = (List<LoanProductVO>) JedisUtils.getObject(cacheKey);
		if (null == resultList) {
			// 获取产品信息
			resultList = new ArrayList<LoanProductVO>();
			List<LoanProduct> loanProductList = loanProductDao.getLoanProductLikeById(productId);
			for (LoanProduct loanProduct : loanProductList) {
				LoanProductVO loanProductVO = new LoanProductVO();
				loanProductVO.setProductId(loanProduct.getId());
				loanProductVO.setName(loanProduct.getName());
				loanProductVO.setType(loanProduct.getType());
				loanProductVO.setMinIncrAmt(loanProduct.getMinIncrAmt());
				loanProductVO.setMinAmt(loanProduct.getMinAmt());
				loanProductVO.setMaxAmt(loanProduct.getMaxAmt());
				loanProductVO.setRepayMethod(loanProduct.getRepayMethod());
				loanProductVO.setStatus(loanProduct.getStatus());
				loanProductVO.setRepayFreq(loanProduct.getRepayFreq());
				loanProductVO.setDescription(loanProduct.getDescription());
				loanProductVO.setRemark(loanProduct.getRemark());
				loanProductVO.setLoanPeriod(loanProduct.getLoanPeriod());
				loanProductVO.setImgUrl(loanProduct.getImgUrl());
				List<PromotionCaseVO> rateList = null;
				if ("4".equals(loanProduct.getType())) {  //分期
					// 获取产品费率信息
					rateList = promotionCaseDAO.getByProductIDAndChannelId(LoanProductEnum.XJDFQ.getId(),
							ChannelEnum.JUQIANBAO.getCode());
					PromotionCaseVO temp = null;
					for (PromotionCaseVO c : rateList) {
						if(c.getPeriod().intValue() == 99) { //99期特殊处理
							temp = c;
						}
						c.setYearInterest(new BigDecimal(0.15));
					}
					rateList.remove(temp);
				} else if ("0".equals(loanProduct.getType())) { //单期
					// 获取产品费率信息
					rateList = promotionCaseDAO.getByProductIDAndChannelId(LoanProductEnum.XJD.getId(),
							ChannelEnum.JUQIANBAO.getCode());
					// 单期的服务费设为0
					if (rateList != null && rateList.size() > 0) {
						for (PromotionCaseVO pvo : rateList) {
							pvo.setServFee(new BigDecimal(0));
						}
					}
				}
				loanProductVO.setLoanProductRate(rateList);
				// 借款用途
				LinkedHashMap<String, String> pMap = new LinkedHashMap<String, String>();
				/*for (LoanPurposeEnum p : LoanPurposeEnum.values()) {
					pMap.put(p.getName(), p.getId());
				}*/
				pMap.put(LoanPurposeEnum.getDesc("10"), "10"); //购物分期
				loanProductVO.setLoanPurpose(pMap);
/*				LinkedHashMap<Integer, String> repayMap = new LinkedHashMap<Integer, String>();
				repayMap.put(RepayMethodEnum.INTEREST.getValue(), RepayMethodEnum.INTEREST.getDesc());
				loanProductVO.setRepayMethods(repayMap);*/
				resultList.add(loanProductVO);
			}
			// 结果保存到缓存
			JedisUtils.setObject(cacheKey, resultList, CACHESECONDS);
		}
		return resultList;
	}
	
	
	
	public List<PromotionCaseVO> getLoanProductDetail(Page page,PromotionOP op){
		return promotionCaseDAO.getByProductIDAndChannelIdForPage(page,op);
	}
	
	public int updatePromotion(String id){
		return promotionCaseDAO.updatePromotion(id);
	}
	
}
