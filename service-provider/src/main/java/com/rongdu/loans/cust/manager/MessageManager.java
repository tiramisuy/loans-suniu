package com.rongdu.loans.cust.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.cust.dao.MessageDAO;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.vo.MessageVO;

/**
 * 
 * liuzhuang on 2018/6/11.
 */
@Service("messageManager")
public class MessageManager {
	public static final String CUST_MESSAGE_LIST_CACHE_PREFIX = "CUST_MESSAGE_LIST_";
	public static final String CUST_MESSAGE_COUNT_CACHE_PREFIX = "CUST_MESSAGE_COUNT_";

	@Autowired
	private MessageDAO messageDao;

	/**
	 * 根据用户id查询消息列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<MessageVO> getMsgByUserId(String userId) {
		String cacheKey = CUST_MESSAGE_LIST_CACHE_PREFIX + userId;
		List<MessageVO> list = (List<MessageVO>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = messageDao.getByUserId(userId);
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return list;
	}

	/**
	 * 统计用户未读信息条数
	 * 
	 * @param userId
	 * @return
	 */
	public int countUnReadMsg(String userId) {
		String cacheKey = CUST_MESSAGE_COUNT_CACHE_PREFIX + userId;
		String count = (String) JedisUtils.get(cacheKey);
		int n = 0;
		if (count == null) {
			n = messageDao.countUnReadMsg(userId);
			JedisUtils.set(cacheKey, String.valueOf(n), Global.ONE_DAY_CACHESECONDS);
		} else {
			n = Integer.parseInt(count);
		}
		return n;
	}

	/**
	 * 更新查看状态
	 * 
	 * @param message
	 * @return
	 */
	public int updateMsgViewStatus(Message message) {
		int rz = messageDao.updateStatus(message);
		if (rz > 0) {
			cleanCache(message.getUserId());
		}
		return rz;
	}

	/**
	 * 新增
	 * 
	 * @param message
	 * @return
	 */
	public int insert(Message message) {
		int rz = messageDao.insert(message);
		if (rz > 0) {
			cleanCache(message.getUserId());
		}
		return rz;
	}

	/**
	 * 批量新增
	 * 
	 * @param list
	 * @return
	 */
	public int insertBatch(List<Message> list) {
		int rz = messageDao.insertBatch(list);
		if (rz > 0) {
			for (Message m : list) {
				cleanCache(m.getUserId());
			}
		}
		return rz;
	}

	private void cleanCache(String userId) {
		String cacheKey1 = CUST_MESSAGE_LIST_CACHE_PREFIX + userId;
		String cacheKey2 = CUST_MESSAGE_COUNT_CACHE_PREFIX + userId;
		JedisUtils.delObject(cacheKey1);
		JedisUtils.del(cacheKey2);
	}
}
