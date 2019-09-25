package com.rongdu.loans.mq;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Service
public class MessageProductorService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    /**
     * 向队列推送消息
     * @param quene
     * @param obj
     */
    public void sendDataToQueue(String quene, Object obj) {
//    	logger.info("消息生产者：{}",obj);
        amqpTemplate.convertAndSend(quene, obj);
    }

    /**
     * 准备自动审批
     */
//    public void sendToPreAutoApproveQueue(String applyId) {
//        CommonMessage<String> message = new CommonMessage<String>();
//        String queueName = QueueConfig.PREPARE_AUTO_APPROVE.getName();
//        String type = QueueConfig.PREPARE_AUTO_APPROVE.getType();
//        String source = "api";
//        String bizId = applyId;
//        String id = String.format("%s_%s",type,bizId);
//        message.setQueueName(queueName);
//        message.setType(type);
//        message.setSource(source);
//        message.setId(id);
//        message.setBizId(bizId);
//        message.setMessage(applyId);
//        logger.debug("消息生产者：{}，{}，{}",source,type,bizId);
////        amqpTemplate.convertAndSend(queueName, message);
//
//    }

}
