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
public class MessageProductor {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 消息对接模板
	 */
	@Autowired
	private AmqpTemplate amqpTemplate;

	public void sendToRepayIngQueue(Object obj) {
		logger.debug("【代扣结果查询】-消息生产者：{}", obj);
		amqpTemplate.convertAndSend("repayIngQueue", obj);
	}

	public void sendToPayIngQueue(Object obj) {
		logger.debug("【代付结果查询】-消息生产者：{}", obj);
		amqpTemplate.convertAndSend("payIngQueue", obj);
	}
}
