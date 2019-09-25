package com.rongdu.loans.loan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.dao.LoanTripProductDAO;
import com.rongdu.loans.loan.dao.LoanTripProductDetailDAO;
import com.rongdu.loans.loan.dao.LoanTripTicketDAO;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanTripProduct;
import com.rongdu.loans.loan.entity.LoanTripProductDetail;
import com.rongdu.loans.loan.entity.LoanTripTicket;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanTripProductDetailManager;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.option.LoanTripProductListOP;
import com.rongdu.loans.loan.service.LoanTripProductService;
import com.rongdu.loans.loan.vo.LoanTripProductDetailVO;
import com.rongdu.loans.loan.vo.LoanTripProductListVO;
import com.rongdu.loans.loan.vo.LoanTripProductVO;

/**
 * 
* @Description:  旅游产品信息服务实现类
* @author: 饶文彪
* @date 2018年7月12日
 */
@Service("loanTripProductService")
public class LoanTripProductServiceImpl extends BaseService implements LoanTripProductService {

	@Autowired
	private LoanTripProductDAO loanTripProductDAO;

	@Autowired
	private LoanTripProductDetailDAO loanTripProductDetailDAO;
	
	@Autowired
	private LoanTripTicketDAO loanTripTicketDAO;

	@Autowired
	private LoanTripProductDetailManager tripProductDetailManager;
	
	@Autowired
	private LoanApplyManager loanApplyManager;
	
	@Override
	public List<LoanTripProductVO> findAllProduct() {

		String cacheKey = "LoanTripProduct_findAllProduct";
		List<LoanTripProductVO> resultList = (List<LoanTripProductVO>) JedisUtils.getObject(cacheKey);

		if (null == resultList) {
			List<LoanTripProduct> products = loanTripProductDAO.findAllList();

			if (CollectionUtils.isNotEmpty(products)) {
				resultList = BeanMapper.mapList(products, LoanTripProductVO.class);

				// 结果保存到缓存
				JedisUtils.setObject(cacheKey, resultList, Global.ONE_DAY_CACHESECONDS);
			}
		}

		return resultList;
	}

	@Override
	public List<LoanTripProductDetailVO> findCustProduct(LoanTripProductDetailOP op) {
		// TODO Auto-generated method stub
//		String cacheKey = "LoanTripProduct_findCustProduct";
//		
//		if(StringUtils.isNoneBlank(op.getCustId())){//客户旅游券缓存
//			cacheKey += op.getCustId();
//		}
//		if(StringUtils.isNoneBlank(op.getApplyId())){//订单旅游券缓存
//			cacheKey += op.getApplyId();
//		}
//		
//		List<LoanTripProductDetailVO> resultList = (List<LoanTripProductDetailVO>) JedisUtils.getObject(cacheKey);
//
//		if (resultList == null) {
//			resultList = loanTripProductDetailDAO.findCustProduct(op);
//			if (StringUtils.isNoneBlank(op.getCustId())) {
//				// 结果保存到缓存
//				JedisUtils.setObject(cacheKey, resultList, Global.TWO_MINUTES_CACHESECONDS);
//			}
//		}
		return loanTripProductDetailDAO.findCustProduct(op);
	}

	@Override
	public String saveCustProduct(LoanTripProductDetailOP op) {
		// TODO Auto-generated method stub

		LoanTripProductDetail detail = new LoanTripProductDetail();
		detail.setApplyId(op.getApplyId());
		detail.setCustId(op.getCustId());
		detail.setProductId(op.getProductId());
		if (StringUtils.isNoneBlank(op.getOverdueTime())) {
			detail.setOverdueTime(DateUtils.parse(op.getOverdueTime()));
		}

		detail.preInsert();

		if (loanTripProductDetailDAO.insert(detail) == 1) {
			//JedisUtils.batchDel("LoanTripProduct_findCustProduct",3);
			return detail.getId();
		}

		return null;
	}

	@Override
	public Boolean updateCustProductAndTicket(String applyId,String updateBy) {
		LoanTripProductDetail productDetail = loanTripProductDetailDAO.findByApplyId(applyId);
		
		//如果根据申请ID在旅游产品明细表没有查询到数据就对该表做插入操作
		if(productDetail == null){
			List<LoanTripTicket> ticketList = loanTripTicketDAO.findAvailableByProductId(Global.DEFAULT_TRIP_PRODUCT_ID,100);
			if(ticketList.size() == 0){
				logger.error("无可用旅游票");
				return false;
			}
			LoanTripProductDetail detail = new LoanTripProductDetail();
			LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);	
				detail.setApplyId(applyId);
				detail.setCustId(loanApply.getUserId());
				detail.setProductId(Global.DEFAULT_TRIP_PRODUCT_ID);
				//随机选取可用的旅游票
				LoanTripTicket ticket = ticketList.get(new Random().nextInt(ticketList.size()));
				detail.setCardNo(ticket.getCardNo());
				detail.setUpdateTime(new Date());
				detail.setOverdueTime(ticket.getOverdueTime());
				detail.setUpdateBy(updateBy);
				detail.setCreateBy(updateBy);
				detail.preInsert();
				if (loanTripProductDetailDAO.insert(detail) == 1) {
					//插入成功了，修改券状态
					ticket.setStatus("1");
					ticket.setUpdateBy(updateBy);
					ticket.setUpdateTime(new Date());
					loanTripTicketDAO.update(ticket);
					return true;
				}else{
					logger.error("插入旅游产品明细失败！");
					return false;
				}
		}
		List<LoanTripTicket> ticketList = loanTripTicketDAO.findAvailableByProductId(productDetail.getProductId(),100);
		if(ticketList.size() == 0){
			logger.error("无可用旅游票");
			return false;
		}
		//随机选取可用的旅游票
		LoanTripTicket ticket = ticketList.get(new Random().nextInt(ticketList.size()));
		
		productDetail.setCardNo(ticket.getCardNo());
		productDetail.setUpdateBy(updateBy);
		productDetail.setUpdateTime(new Date());
		productDetail.setOverdueTime(ticket.getOverdueTime());
		ticket.setStatus("1");
		ticket.setUpdateBy(updateBy);
		ticket.setUpdateTime(new Date());
		loanTripProductDetailDAO.update(productDetail);
		loanTripTicketDAO.update(ticket);
		
		return true;
	}

	
	@Override
	public Page<LoanTripProductListVO> getLoanTripList(LoanTripProductListOP op) {
		  Page<LoanTripProductListVO> voPage = new Page<LoanTripProductListVO>(op.getPageNo(),op.getPageSize());
		List<LoanTripProductListVO> voList = tripProductDetailManager.getLoanTripList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}	
}
