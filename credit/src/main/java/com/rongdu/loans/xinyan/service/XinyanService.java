/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.service;

import com.rongdu.loans.xinyan.vo.BlackOP;
import com.rongdu.loans.xinyan.vo.BlackVO;
import com.rongdu.loans.xinyan.vo.JbqbBlackOP;
import com.rongdu.loans.xinyan.vo.JbqbBlackVO;
import com.rongdu.loans.xinyan.vo.OverdueOP;
import com.rongdu.loans.xinyan.vo.OverdueVO;
import com.rongdu.loans.xinyan.vo.RadarOP;
import com.rongdu.loans.xinyan.vo.RadarVO;
import com.rongdu.loans.xinyan.vo.TotaldebtOP;
import com.rongdu.loans.xinyan.vo.TotaldebtVO;

/**
 * 新颜
 * 
 * @author liuzhuang
 * @version 2018-05-18
 */
public interface XinyanService {

	/**
	 * 负面拉黑
	 */
	public BlackVO black(BlackOP op);

	/**
	 * 共债档案
	 */
	public TotaldebtVO totaldebt(TotaldebtOP op);

	/**
	 * 逾期档案
	 */
	public OverdueVO overdue(OverdueOP op);

	/**
	 * 负面拉黑-聚宝钱包
	 */
	public JbqbBlackVO blackJbqb(JbqbBlackOP op);

	/**
	 * 全景雷达
	 */
	public RadarVO radar(RadarOP op);

}