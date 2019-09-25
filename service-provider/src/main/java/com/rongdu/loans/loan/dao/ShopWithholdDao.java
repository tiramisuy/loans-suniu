/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.ShopWithhold;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.ShopWithholdOP;
import com.rongdu.loans.loan.vo.ShopWithholdVO;

/**
 * 购物款代扣-数据访问接口
 * @author liuliang
 * @version 2018-05-31
 */
@MyBatisDao
public interface ShopWithholdDao extends BaseDao<ShopWithhold,String> {
	public ShopWithhold findByApplyId(@Param(value = "applyId")String applyId);
	
	public int updateByApplyId(@Param(value="entity")ShopWithhold withHoldOP);
	
	public List<ShopWithholdVO> selectShopWithHoldList(Page<ShopWithholdVO> page,@Param(value="op")ShopWithholdOP op);
	
	public List<ShopWithholdVO> selectFaildShopWithHold();
	
	public int updateShopWithHold(ShopWithhold withHoldOP);
	
	public List<LoanApply> getLoanApply(@Param(value="op")ShopWithholdOP op);
}