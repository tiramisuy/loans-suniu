package com.rongdu.loans.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.sys.dao.ChannelDAO;

@Service
public class ChannelService extends BaseService{
	public static final String ADMIN_CACHE_LIST_BY_CHANNEl = "ADMIN_CACHE_LIST_BY_CHANNEl";
	
	@Autowired
	private ChannelDAO channelDao;

	@SuppressWarnings("unchecked")
	public List<ChannelVO> findAllChannel() {
		List<ChannelVO> list =  (List<ChannelVO>)JedisUtils.getObject(ADMIN_CACHE_LIST_BY_CHANNEl);
		if (list == null){
			list = channelDao.findAllChannel();
			JedisUtils.setObject(ADMIN_CACHE_LIST_BY_CHANNEl,
					list,
					60*60*24);
		}
		return list;
	}
}
