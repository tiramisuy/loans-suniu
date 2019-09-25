package com.rongdu.loans.mq;

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
}
