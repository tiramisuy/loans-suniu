package com.rongdu.loans.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * 消息确认机制：确认是否发送消息
 */
@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements ConfirmCallback {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，
	 * 只要正确的到达exchange中，broker即可确认该消息返回给客户端ack
	 */
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    	 if (ack) {
             logger.info("消息发送成功");
         } else {
             //处理丢失的消息（nack）
        	 logger.info("消息发送失败：{}", cause);
         }
    }
}
