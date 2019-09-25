package com.rongdu.loans.cust.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.cust.option.MessageOP;
import com.rongdu.loans.cust.service.MessageService;
import com.rongdu.loans.cust.vo.MessageVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息Service实现
 * @author likang
 * @version 2017-06-30
 */
@Service("messageService")
public class MessageServiceImpl extends BaseService implements MessageService {

    @Autowired
    private MessageManager messageManager;


    public List<MessageVO> getMsgByUserId(String userId) {
        List<MessageVO> rz = new ArrayList<MessageVO>();
        if(StringUtils.isBlank(userId)) {
            logger.error("param is error!");
            return rz;
        }
        // 调用本地服务
        return messageManager.getMsgByUserId(userId);
    }


    public int countUnReadMsg(String userId) {
        if(StringUtils.isBlank(userId)) {
            logger.error("param is error!");
            return 0;
        }
        // 调用本地服务
        return messageManager.countUnReadMsg(userId);
    }


    public int updateMsgViewStatus(MessageOP messageOp) {
        if(null == messageOp) {
            logger.error("param is error!");
            return 0;
        }
        int rz = 0;
        try {
	        // 构造参数对象
	        Message message = new Message();
	        message.setId(messageOp.getMsgId());
	        message.setUserId(messageOp.getUserId());
	        message.setViewSource(
	        		Integer.parseInt(messageOp.getViewSource()));
	        message.setViewStatus(messageOp.getViewStatus());
	        message.setViewTime(new Date());
	        message.setIp(messageOp.getIp());
	        message.preUpdate();
	        rz = messageManager.updateMsgViewStatus(message);
        } catch(Exception e) {
        	logger.error("error :[{}]", e);
        }
        return rz;
    }
}
