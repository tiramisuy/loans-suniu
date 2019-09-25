package com.rongdu.loans.cust.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.loans.cust.option.MessageOP;
import com.rongdu.loans.cust.vo.MessageVO;

/**
 * 消息Service
 * @author likang
 * @version 2017-06-15
 */
@Service
public interface MessageService {

	/**
	 * 根据用户id查询消息列表
	 * @param userId
	 * @return
	 */
	List<MessageVO> getMsgByUserId(String userId);
	
	/**
	 * 统计用户未读信息条数
	 * @param userId
	 * @return
	 */
	int countUnReadMsg(String userId);
	
	/**
	 * 更新查看状态
	 * @param messageOp
	 * @return
	 */
	int updateMsgViewStatus(MessageOP messageOp);
}
