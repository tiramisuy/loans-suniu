package com.rongdu.loans.loan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.ShopOrderVO;
import com.rongdu.loans.loan.dao.LoanTrafficDAO;
import com.rongdu.loans.loan.dao.LoanTrafficStatisticsDao;
import com.rongdu.loans.loan.entity.LoanTraffic;
import com.rongdu.loans.loan.entity.LoanTrafficStatistics;
import com.rongdu.loans.loan.manager.LoanTrafficManager;
import com.rongdu.loans.loan.manager.LoanTrafficStatisticsManager;
import com.rongdu.loans.loan.option.LoanTrafficOP;
import com.rongdu.loans.loan.option.LoanTrafficStatisticsOP;
import com.rongdu.loans.loan.service.LoanTrafficService;
import com.rongdu.loans.loan.vo.LoanTrafficStatisticsVO;
import com.rongdu.loans.loan.vo.LoanTrafficVO;

/**
 * 产品信息服务实现类
 * @author likang
 *
 */
@Service("loanTrafficService")
public class LoanTrafficServiceImpl extends BaseService implements LoanTrafficService {

	@Autowired
	private LoanTrafficManager loanTrafficManager;

	@Autowired
	private LoanTrafficStatisticsDao loanTrafficStatisticsDao;

	@Override
	public List<Map<String, Object>> getExtensionPlatformListByCondition(HashMap<String, Object> condition) {
		List<Map<String, Object>> list = loanTrafficManager.getExtensionPlatformListByCondition(condition);
		return list;
	}

	@Override
	@Transactional
	public void addHitLoanTraffic(String id) {
		loanTrafficManager.addHitLoanTraffic(id);

		// 每日统计
		Integer statsDate = DateUtils.getYYYYMMDD2Int();
		int rtn = loanTrafficStatisticsDao.addHits(id, statsDate);
		if (rtn < 1) {
			LoanTraffic loanTraffic = loanTrafficManager.get(id);
			if (loanTraffic != null) {
				LoanTrafficStatistics statistics = new LoanTrafficStatistics();
				statistics.setTrafficId(id);
				statistics.setTrafficName(loanTraffic.getName());
				statistics.setHits(1);
				statistics.setStatsDate(statsDate);
				statistics.setStatus("1");
				statistics.setCreateTime(new Date());
				statistics.setUpdateTime(statistics.getCreateTime());
				statistics.setDel(0);
				loanTrafficStatisticsDao.insert(statistics);
			}
		}
	}

	@Override
	public LoanTrafficVO get(String id) {
		// TODO Auto-generated method stub
		return BeanMapper.map(loanTrafficManager.get(id), LoanTrafficVO.class);
	}

	@Override
	public Page<LoanTrafficVO> findTrafficPage(Page<LoanTrafficVO> rtnPage, LoanTrafficOP op) {

		Page<LoanTraffic> page = new Page<LoanTraffic>(rtnPage.getPageNo(), rtnPage.getPageSize());
		page.setOrderBy(" scort asc");
		page = loanTrafficManager.findPage(page);
		rtnPage = new Page<LoanTrafficVO>(page.getPageNo(), page.getPageSize(),page.getCount());
		rtnPage.setList(BeanMapper.mapList(page.getList(), LoanTrafficVO.class));
		return rtnPage;
	}

	@Override
	public Integer saveOrUpdate(LoanTrafficVO loanTrafficVO) {
		// TODO Auto-generated method stub		
		if(StringUtils.isBlank(loanTrafficVO.getId())){
			loanTrafficVO.setHits(0);
			return loanTrafficManager.insert(BeanMapper.map(loanTrafficVO,LoanTraffic.class));
		}else {
			return loanTrafficManager.update(BeanMapper.map(loanTrafficVO,LoanTraffic.class));
		}
	}
	
	/**
 	* 贷超导流统计-实体管理接口
 	*/
	@Autowired
	private LoanTrafficStatisticsManager loanTrafficStatisticsManager;

	@Override
	public Page<LoanTrafficStatisticsVO> findTrafficStatisticsPage(Page<LoanTrafficStatisticsVO> rtnPage, LoanTrafficStatisticsOP op) {
		Page<LoanTrafficStatistics> page = new Page<LoanTrafficStatistics>(rtnPage.getPageNo(), rtnPage.getPageSize());
		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("del", 0));
		
		if(StringUtils.isNotEmpty(op.getTrafficId())){
			criteria1.and(Criterion.eq("traffic_id", op.getTrafficId()));
		}
		
		if(op.getBeginStatsDate() != null){
			criteria1.and(Criterion.ge("stats_date", op.getBeginStatsDate()));
		}
		
		if(op.getEndStatsDate() != null){
			criteria1.and(Criterion.le("stats_date", op.getEndStatsDate()));
		}
		
		
		criteriaList.add(criteria1);
		
		page.setOrderBy("stats_date desc");
		
		page = loanTrafficStatisticsManager.findPageByCriteriaList(page,criteriaList);
		
		rtnPage = new Page<LoanTrafficStatisticsVO>(page.getPageNo(), page.getPageSize(),page.getCount());
		rtnPage.setList(BeanMapper.mapList(page.getList(), LoanTrafficStatisticsVO.class));
		return rtnPage;
	}	

}
