package com.rongdu.loans.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Service;

/**
 * 消息确认机制：确认是否收到消息
 * ReturnCallBack使用时需要通过RabbitTemplate 的setMandatory方法设置变量mandatoryExpression的值，
 * 该值可以是一个表达式或一个Boolean值。
 * 当为TRUE时，如果消息无法发送到指定的消息队列那么ReturnCallBack回调方法会被调用。
 */
@Service("returnCallBackListener")
public class ReturnCallBackListener implements ReturnCallback {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    	logger.info("ReturnCallback:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
	}
}
