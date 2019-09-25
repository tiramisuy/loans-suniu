/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.ShopWithholdDao;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.ShopWithhold;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.ShopWithholdOP;
import com.rongdu.loans.loan.vo.ShopWithholdVO;

/**
 * 购物款代扣-实体管理实现类
 * @author liuliang
 * @version 2018-05-31
 */
@Service("shopWithholdManager")
public class ShopWithholdManager extends BaseManager<ShopWithholdDao, ShopWithhold, String> {
	public ShopWithhold findByApplyId(String applyId){
		return dao.findByApplyId(applyId);
	}
	
	public int updateByApplyId(ShopWithhold withHoldOP){
		return dao.updateByApplyId(withHoldOP);
	}
	
	
	public int updateShopWithHold(ShopWithhold withHoldOP){
		return dao.updateShopWithHold(withHoldOP);
	}
	
	/**
	 * 查询三次代扣都失败的代扣信息显示列表上
	 * @param page
	 * @param op
	 * @return
	 */
	public List<ShopWithholdVO> selectShopWithHoldList(Page<ShopWithholdVO> page,ShopWithholdOP op){
		return dao.selectShopWithHoldList(page,op);
	}
	
	
	public List<LoanApply> getLoanApply(ShopWithholdOP op){
		return dao.getLoanApply(op);
	}
	
	
	/*
	 * 查询首次扣款失败的用于二、三次代扣
	 */
	public List<ShopWithholdVO> selectFaildShopWithHold(){
		return dao.selectFaildShopWithHold();
	}
}