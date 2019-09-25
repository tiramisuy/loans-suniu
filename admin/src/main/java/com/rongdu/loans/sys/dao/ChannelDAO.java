package com.rongdu.loans.sys.dao;

import java.util.List;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.vo.ChannelVO;

@MyBatisDao
public interface ChannelDAO extends CrudDao<ChannelVO> {
	/**
	 * 查询所有的渠道
	 */
	public List<ChannelVO> findAllChannel();
}
