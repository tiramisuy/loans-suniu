package com.rongdu.loans.loan.service.impl;

import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.loan.vo.LoanPromotionCaseVO;
import com.rongdu.loans.loan.entity.PromotionCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.service.PromotionCaseService;
import com.rongdu.loans.loan.vo.CostingResultVO;
/**
 * 营销方案服务类
 * @author likang
 *
 */
@Service("promotionCaseService")
public class PromotionCaseServiceImpl implements PromotionCaseService {

	
	@Autowired
	private PromotionCaseManager promotionCaseManager;
	
	@Override
	public CostingResultVO Costing(PromotionCaseOP promotionCaseOP) {
		return promotionCaseManager.Costing(promotionCaseOP);
	}

	@Override
	public String checkByChannel(String channel) {
		return promotionCaseManager.checkByChannel(channel);
	}

	@Override
	public List<Map<String, String>> findAllChannel() {
		String cache = "API_CACHE_LIST_BY_CHANNEl";
		List<Map<String, String>> list = (List<Map<String, String>>) JedisUtils.getObject(cache);
		if (list == null) {
			list = promotionCaseManager.findAllChannel();
			JedisUtils.setObject(cache, list, 60 * 60 * 24);
		}
		return list;
	}

    @Override
    public LoanPromotionCaseVO getById(String id) {
		PromotionCase promotionCase = promotionCaseManager.getById(id);
		if (promotionCase == null) {
			return null;
		}
		return BeanMapper.map(promotionCase,LoanPromotionCaseVO.class);
    }
}
