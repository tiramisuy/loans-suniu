package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.cust.entity.CustCoupon;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.option.ShopOrderOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.enums.GoodsStatusEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.dao.GoodsOrderDao;
import com.rongdu.loans.loan.entity.GoodsAddress;
import com.rongdu.loans.loan.entity.GoodsList;
import com.rongdu.loans.loan.entity.GoodsOrder;
import com.rongdu.loans.loan.manager.GoodsAddressManager;
import com.rongdu.loans.loan.manager.GoodsListManager;
import com.rongdu.loans.loan.manager.GoodsOrderManager;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.option.GoodsListOp;
import com.rongdu.loans.loan.option.GoodsOrderOP;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.service.ShopService;
import com.rongdu.loans.loan.vo.GoodsAddressVO;
import com.rongdu.loans.loan.vo.GoodsListVO;
import com.rongdu.loans.loan.vo.GoodsOrderVO;
import com.rongdu.loans.loan.vo.LoanGoodsAdreessVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.XfAgreementPayOP;
import com.rongdu.loans.pay.service.XianFengAgreementPayService;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * Created by lee on 2018/8/28.
 */
@Service("shopService")
public class ShopServiceImpl extends BaseService implements ShopService {

	private static final String SHOP_COUPON_CACHE_PREFIX = "SHOP_COUPON_CACHE_";
	@Autowired
	private GoodsListManager goodsListManager;
	@Autowired
	private GoodsOrderManager goodsOrderManager;
	@Autowired
	private XianFengAgreementPayService xianFengAgreementPayService;
	@Autowired
	private CustUserService userService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private GoodsOrderDao goodsOrderDao;
	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private CustCouponManager custCouponManager;
	@Autowired
	private GoodsAddressManager goodsAddressManager;
	@Autowired
	private AreaManager areaManager;

	@Override
	public List<GoodsListVO> getGoodsList() {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("status", GoodsStatusEnum.ON_SHELVES.getValue()));
		criteria.and(Criterion.eq("del", 0));
		List<GoodsList> mapList = goodsListManager.findAllByCriteria(criteria);
		if (CollectionUtils.isEmpty(mapList)) {
			return Collections.emptyList();
		}
		return BeanMapper.mapList(mapList, GoodsListVO.class);
	}

	@Transactional
	@Override
	public ApiResultVO buyGoods(GoodsOrderOP goodsOrderOP) {
		ApiResultVO apiResultVO = new ApiResultVO();
		CustUser custUser = custUserManager.getById(goodsOrderOP.getAccountId());
		if (custUser.getCardNo() == null) {
			apiResultVO.setMsg("请去个人中心先绑卡");
			apiResultVO.setCode("FAIL");
			return apiResultVO;
		} else {
			goodsOrderOP.setBankName(BankCodeEnum.getName(custUser.getBankCode()));
		}
		if (JedisUtils.get(SHOP_COUPON_CACHE_PREFIX + goodsOrderOP.getCouponId()) != null) {
			apiResultVO.setMsg("订单已提交,请到我的订单查询");
			apiResultVO.setCode("FAIL");
			return apiResultVO;
		}
		if("9".equals(goodsOrderOP.getGoodsId())) {
			Criteria criteria1 = new Criteria();
			criteria1.add(Criterion.eq("goods_id", goodsOrderOP.getGoodsId())); // 商品为T恤
			criteria1.and(Criterion.eq("account_id", goodsOrderOP.getAccountId()));
			criteria1.and(Criterion.ne("status", 2));
			long tCount = goodsOrderManager.countByCriteria(criteria1);
			if (tCount > 1) {
				apiResultVO.setMsg("该商品每人限购2件");
				apiResultVO.setCode("FAIL");
				return apiResultVO;
			}
		}

		GoodsOrder goodsOrder = new GoodsOrder();
		BeanUtils.copyProperties(goodsOrderOP, goodsOrder);
		goodsOrder.setUserName(custUser.getRealName());
		goodsOrder.setUserPhone(custUser.getMobile());
		goodsOrder.setStatus("0");
		goodsOrder.preInsert();
		int result = goodsOrderDao.insert(goodsOrder);
		if (result == 1 && StringUtils.isNotBlank(goodsOrderOP.getCouponId())) {
			String [] couponIdArr=goodsOrderOP.getCouponId().split(",");
			List<String> couponIdList= Arrays.asList(couponIdArr);
			for(String couponId:couponIdList){
				Criteria criteria = new Criteria();
				//criteria.add(Criterion.eq("id", goodsOrderOP.getCouponId()));
				criteria.add(Criterion.eq("id", couponId));
				CustCoupon custCoupon = new CustCoupon();
				custCoupon.setStatus(2);
				custCouponManager.updateByCriteriaSelective(custCoupon, criteria);
				JedisUtils.set(SHOP_COUPON_CACHE_PREFIX + couponId, "1", Global.THREE_DAY_CACHESECONDS);
			}


		}
		goodsOrderOP.setId(goodsOrder.getId());
		apiResultVO.setData(goodsOrderOP);
		return apiResultVO;
	}

	@Override
	public void paySuccess(RepayLogVO rePayOP) {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("id", rePayOP.getApplyId()));
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setStatus("1");
		goodsOrder.setPayTime(new Date());
		int updateByCriteriaSelective = goodsOrderManager.updateByCriteriaSelective(goodsOrder, criteria);
		if (updateByCriteriaSelective == 1) {
			Criteria criteria1 = new Criteria();
			criteria1.and(Criterion.eq("id", rePayOP.getApplyId()));
			GoodsOrder goodsOrder1 = goodsOrderManager.getByCriteria(criteria1);
			if (goodsOrder1.getCouponId() != null) {
				String [] couponIdArr=goodsOrder1.getCouponId().split(",");
				List<String> couponIdList=Arrays.asList(couponIdArr);
				for(String couponId:couponIdList){
					Criteria criteria2 = new Criteria();
					criteria2.add(Criterion.eq("id", couponId));
					CustCoupon custCoupon = new CustCoupon();
					custCoupon.setStatus(1);
					custCouponManager.updateByCriteriaSelective(custCoupon, criteria2);
				}
			}
		}
	}

	@Override
	public ApiResultVO payGoods(String id, String userId) {
		ApiResultVO apiResultVO = new ApiResultVO();
		try {
			Long payCount = repayLogService.countPayingByApplyId(id);
			if (payCount != 0) {
				apiResultVO.setCode("FAIL");
				apiResultVO.setMsg("交易处理中,请稍后查询");
				return apiResultVO;
			}
			CustUserVO user = userService.getCustUserById(userId);
			if (user.getCardNo() == null) {
				apiResultVO.setMsg("请先绑卡");
				apiResultVO.setCode("FAIL");
				return apiResultVO;
			}
			XfAgreementPayOP op = new XfAgreementPayOP();
			Criteria criteria = new Criteria();
			criteria.add(Criterion.eq("id", id));
			criteria.and(Criterion.eq("account_id", userId));
			GoodsOrder goodsOrder = goodsOrderManager.getByCriteria(criteria);
			if (goodsOrder == null || !goodsOrder.getStatus().equals("0")) {
				apiResultVO.setMsg("未找到有效订单");
				apiResultVO.setCode("FAIL");
				return apiResultVO;
			}
			// 若为T恤且支付为0
			if ("9".equals(goodsOrder.getGoodsId()) && BigDecimal.ZERO.compareTo(goodsOrder.getPrice()) == 0) {
				Criteria orderCriteria1 = new Criteria();
				orderCriteria1.add(Criterion.eq("id", id));
				GoodsOrder order = new GoodsOrder();
				order.setStatus("1");
				order.setPayTime(new Date());
				int updateByCriteriaSelective = goodsOrderManager.updateByCriteriaSelective(order, orderCriteria1);
				if (updateByCriteriaSelective == 1) {
					if (goodsOrder.getCouponId() != null) {
						String [] couponIdArr=goodsOrder.getCouponId().split(",");
						List<String> couponIdList=Arrays.asList(couponIdArr);
						for(String couponId:couponIdList){
							Criteria criteria1 = new Criteria();
							criteria1.add(Criterion.eq("id", couponId));
							CustCoupon custCoupon = new CustCoupon();
							custCoupon.setStatus(1);
							custCouponManager.updateByCriteriaSelective(custCoupon, criteria1);
						}
					}
					apiResultVO.setMsg("购买成功！");
					apiResultVO.setCode("SUCCESS");
					return apiResultVO;
				}
			}

			op.setUserId(userId);
			op.setAmount(String.valueOf(goodsOrder.getPrice()));
			op.setApplyId(id);
			op.setPayType(PayTypeEnum.SHOPPINGMALL.getId());
			XfAgreementPayResultVO xfAgreementPayResultVO = xianFengAgreementPayService.agreementPay(op);

			Criteria criteria1 = new Criteria();
			criteria1.add(Criterion.eq("id", id));
			GoodsOrder goodsOrder1 = new GoodsOrder();
			goodsOrder1.setStatus("4");
			int updateByCriteriaSelective = goodsOrderManager.updateByCriteriaSelective(goodsOrder1, criteria1);
			apiResultVO.setMsg("交易处理中,请稍后查询");
			apiResultVO.setCode("SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResultVO;
	}

	@Override
	public List<GoodsOrderVO> findGoodsOrderByUserId(String userId) {
		List<Criteria> criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("account_id", userId));
		criteria1.and(Criterion.eq("del", 0));
		criteriaList.add(criteria1);
		return BeanMapper.mapList(goodsOrderManager.findAllByCriteriaList(criteriaList), GoodsOrderVO.class);
	}

	@Override
	public List<GoodsOrderVO> findGoodsOrder(GoodsOrderOP op) {
		List<Criteria> criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("a.account_id", op.getAccountId()));
		criteria1.and(Criterion.eq("a.del", 0));

		if (StringUtils.isNotBlank(op.getStatus())) {
			if (op.getStatus().contains(",")) {
				criteria1.and(Criterion.in("a.status", op.getStatus().split(",")));
			} else {
				criteria1.and(Criterion.eq("a.status", op.getStatus()));
			}
		}

		criteriaList.add(criteria1);
		return goodsOrderManager.findAllVOByCriteriaList(criteriaList);
	}

	@Transactional
	@Override
	public ApiResultVO cancelOrder(String id) {
		ApiResultVO apiResultVO = new ApiResultVO();
		Criteria criteria3 = new Criteria();
		criteria3.add(Criterion.eq("id", id));
		GoodsOrder goodsOrder3 = goodsOrderManager.getByCriteria(criteria3);
		if (!goodsOrder3.getStatus().equals("0")) {
			apiResultVO.setCode("FAIL");
			apiResultVO.setMsg("不能取消");
			return apiResultVO;
		}
		try {
			Criteria criteria = new Criteria();
			criteria.add(Criterion.eq("id", id));
			GoodsOrder goodsOrder = new GoodsOrder();
			goodsOrder.setStatus("2");
			int updateByCriteriaSelective = goodsOrderManager.updateByCriteriaSelective(goodsOrder, criteria);
			if (updateByCriteriaSelective == 1) {
				Criteria criteria1 = new Criteria();
				criteria1.add(Criterion.eq("id", id));
				GoodsOrder goodsOrder1 = goodsOrderManager.getByCriteria(criteria1);
				if (goodsOrder1.getCouponId() != null) {

					String [] couponIdArr=goodsOrder1.getCouponId().split(",");
					List<String> couponIdList=Arrays.asList(couponIdArr);
					for(String couponId:couponIdList){
						Criteria criteria2 = new Criteria();
						criteria2.add(Criterion.eq("id", couponId));
						CustCoupon custCoupon = new CustCoupon();
						custCoupon.setStatus(0);
						custCouponManager.updateByCriteriaSelective(custCoupon, criteria2);
						JedisUtils.del(SHOP_COUPON_CACHE_PREFIX + couponId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResultVO;
	}

	@Override
	public Page<GoodsOrderVO> goodsOrderList(ShopOrderOP op) {
		Page page = new Page(op.getPageNo(), op.getPageSize());
		List<GoodsOrderVO> voList = goodsOrderManager.goodsOrderList(page, op);
		page.setList(voList);
		return page;
	}

	@Override
	public int updateGoodsOrderDeliver(String shopOrderID,String expressNo,String updateBy) {
		GoodsOrder entity = new GoodsOrder();
		entity.setId(shopOrderID);
		entity.setStatus("3");
		entity.setDeliverTime(new Date());
		entity.setExpressNo(expressNo);
		entity.setUpdateBy(updateBy);
		int num = goodsOrderManager.updateByIdSelective(entity);
		return num;
	}

	@Override
	public ApiResultVO selectAdress(String userId) {
		ApiResultVO apiResultVO = new ApiResultVO();
		LoanGoodsAdreessVO loanGoodsResultVO = new LoanGoodsAdreessVO();
		try {
			GoodsAddress goodsAddress = goodsAddressManager.getNewAddress(userId);
			if (goodsAddress != null) {
				Map<String, String> areaMap = areaManager.getAreaCodeAndName();
				loanGoodsResultVO.setUserName(goodsAddress.getUserName());
				loanGoodsResultVO.setMobile(goodsAddress.getMobile());
				loanGoodsResultVO.setResideProvince(goodsAddress.getProvince());
				if (StringUtils.isNotBlank(goodsAddress.getProvince())) {
					loanGoodsResultVO.setResideProvinceName(areaMap.get(goodsAddress.getProvince()));
				}
				loanGoodsResultVO.setResideCity(goodsAddress.getCity());
				if (StringUtils.isNotBlank(goodsAddress.getCity())) {
					loanGoodsResultVO.setResideCityName(areaMap.get(goodsAddress.getCity()));
				}
				loanGoodsResultVO.setResideDistrict(goodsAddress.getDistrict());
				if (StringUtils.isNotBlank(goodsAddress.getDistrict())) {
					loanGoodsResultVO.setResideDistrictName(areaMap.get(goodsAddress.getDistrict()));
				}
				loanGoodsResultVO.setResideAddr(goodsAddress.getAddress());
				loanGoodsResultVO.setId(goodsAddress.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResultVO.setCode("FAIL");
			apiResultVO.setMsg("地址查询失败");
		}
		apiResultVO.setData(loanGoodsResultVO);
		return apiResultVO;
	}

	@Override
	public ApiResultVO updateAdress(GoodsAddressVO goodsAddressVO) {
		ApiResultVO apiResultVO = new ApiResultVO();
		try {
			GoodsAddress goodsAddress = goodsAddressManager.getNewAddress(goodsAddressVO.getUserId());
			if (goodsAddress == null) {
				GoodsAddress goodsAddress1 = new GoodsAddress();
				BeanUtils.copyProperties(goodsAddressVO, goodsAddress1);
				int result = goodsAddressManager.insert(goodsAddress1);
			} else {
				Criteria criteria = new Criteria();
				criteria.add(Criterion.eq("id", goodsAddress.getId()));
				BeanUtils.copyProperties(goodsAddressVO, goodsAddress);
				goodsAddressManager.updateByCriteriaSelective(goodsAddress, criteria);
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResultVO.setCode("FAIL");
			apiResultVO.setMsg("地址保存失败");
		}
		return apiResultVO;
	}

	@Override
	public List<GoodsOrderVO> goodsOrderExportList(ShopOrderOP shopOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean cancelDeliver(String shopOrderID,String updateBy) {
		boolean flag = false;
		GoodsOrder entity = new GoodsOrder();
		entity.setId(shopOrderID);
		entity.setStatus("2");
		entity.setDeliverTime(new Date());
		entity.setUpdateBy(updateBy);
		int num = goodsOrderManager.updateByIdSelective(entity);
		if (num == 1) {
			Criteria criteria = new Criteria();
			criteria.add(Criterion.eq("id", shopOrderID));
			GoodsOrder goodsOrder = goodsOrderManager.getByCriteria(criteria);
			if (goodsOrder.getCouponId() != null) {
				String [] couponIdArr=goodsOrder.getCouponId().split(",");
				List<String> couponIdList=Arrays.asList(couponIdArr);
				for(String couponId:couponIdList){
					Criteria criteria2 = new Criteria();
					criteria2.add(Criterion.eq("id", couponId));
					CustCoupon custCoupon = new CustCoupon();
					custCoupon.setStatus(0);
					custCoupon.setUpdateBy(updateBy);
					custCouponManager.updateByCriteriaSelective(custCoupon, criteria2);
				}
			}
			flag = true; 
		}
		return flag;
	}


	@Override
	public GoodsListVO get(String id) {
		return BeanMapper.map(goodsListManager.get(id), GoodsListVO.class);
	}

	@Override
	public Integer saveOrUpdate(GoodsListVO goodsListVO) {
		return goodsListManager.saveOrUpdate(goodsListVO);
	}

	@Override
	public Page<GoodsListVO> queryGoodsList(GoodsListOp op) {
		Page page = new Page(op.getPageNo(), op.getPageSize());
		List<GoodsListVO> voList = goodsListManager.queryGoodsList(page, op);
		page.setList(voList);
		return page;
	}

	@Override
	public  Integer delete(GoodsListVO goodsList) {
		GoodsList entity=new GoodsList();
		entity.setId(goodsList.getId());
		entity.setUpdateTime(new Date());
		return goodsListManager.delete(entity);
	}
}
