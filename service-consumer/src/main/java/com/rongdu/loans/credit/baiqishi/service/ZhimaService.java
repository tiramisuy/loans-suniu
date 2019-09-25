package com.rongdu.loans.credit.baiqishi.service;

import com.rongdu.loans.credit.baiqishi.vo.AuthorizeOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultVO;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreOP;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListOP;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListVO;

/**
 * 芝麻信用-接口实现
 * @author sunda
 * @version 2017-07-20
 */
public interface ZhimaService {
	
	/**
	 * 芝麻信用授权
	 * @return
	 */
	public abstract AuthorizeVO authorize(AuthorizeOP op);

	/**
	 * 查询芝麻信用授权结果
	 * @return
	 */
	public abstract AuthorizeResultVO getAuthorizeResult(AuthorizeResultOP op);

	/**
	 * 查询芝麻信用分
	 * @return
	 */
	public abstract ZmScoreVO getZmScore(ZmScoreOP op);

	/**
	 * 查询芝麻信用行业关注名单
	 * @return
	 */
	public abstract ZmWatchListVO getZmWatchList(ZmWatchListOP op);
	
}
