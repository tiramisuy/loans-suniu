package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.vo.MessageVO;

import java.util.List;

/**
 *  消息DAO接口
 *  @author likang
 *  @version 2017-06-30
 */
@MyBatisDao
public interface MessageDAO extends BaseDao<Message, String>{

	/**
	 * 根据用户id查询消息列表
	 * @param userId
	 * @return
	 */
	List<MessageVO> getByUserId(String userId);
	
	/**
	 * 统计用户未读信息条数
	 * @param userId
	 * @return
	 */
	int countUnReadMsg(String userId);
	
	/**
	 * 更新查看状态
	 * @param message
	 * @return
	 */
	int updateStatus(Message message);
}
