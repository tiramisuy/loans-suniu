package com.rongdu.loans.mq.repay;

import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.service.TonglianWithdrawService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.service.RepayUnsolvedService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * 支付处理中订单消息处理
 * 
 * @author liuzhuang
 */
@Service("repayIngConsumer")
public class RepayIngConsumer implements ChannelAwareMessageListener {
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private RepayUnsolvedService repayUnsolvedService;
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void onMessage(Message message, Channel channel) throws Exception {
		String lockKey = "";
		String requestId = String.valueOf(System.nanoTime());// 请求标识
		String body = new String(message.getBody(), "UTF-8");
		try {
			logger.debug("【代扣结果查询】消息消费者：{}", body);

			RepayLogVO repayLogVO = JsonMapper.getInstance().fromJson(body, RepayLogVO.class);

			lockKey = Global.REPAYING_LOCK + repayLogVO.getId();
			boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 5);
			if (lock) {
				Thread.sleep(1000 * 5);
				if (isIng(repayLogVO.getStatus())){
					if (Global.TONGLIAN_CHANNEL_CODE.equals(repayLogVO.getChlCode())) {
						// 查询通联商户1代付结果
						repayUnsolvedService.queryRepayResultTL(repayLogVO);
					}
				}
			}
		} catch (Exception e) {
			// 被拒绝的消息是否重新入队列
			// boolean requeue = true;
			// channel.basicNack(deliveryTag, multiple, requeue);
			logger.debug("【代扣结果查询】消息消费异常：{}", body);
		} finally {
			// // 消息确认机制：RabbitMQ会等待消费者显式发回ack信号后才从内存(和磁盘，如果是持久化消息的话)中移去消息。
			// // 否则，RabbitMQ会在队列中消息被消费后立即删除它。
			// //
			// true-自动应答，false-关闭RabbitMQ的自动应答机制，改为手动应答ack，需要使用channel.ack、channel.nack、channel.basicReject
			// // 取消消息自动应答，进行手动应答
			// boolean autoAck = false;
			// // 接收到消息后，就会自动反馈一个消息给服务器
			// String queueName = msg.getQueueName();
			// QueueingConsumer callback = new QueueingConsumer(channel);
			// channel.basicConsume(queueName, autoAck, callback);
			// 该消息的投递标识
			long deliveryTag = message.getMessageProperties().getDeliveryTag();
			// multiple是否批量应答多个投递.true:将一次性拒绝所有小于deliveryTag的消息
			boolean multiple = false;
			channel.basicAck(deliveryTag, multiple);
			JedisUtils.releaseLock(lockKey, requestId);
		}
	}

	private boolean isIng(String status) {
		if (StringUtils.isBlank(status)) {
			return false;
		}
		String[] statusArr = { "I" };
		for (String str : statusArr) {
			if (status.equals(str)) {
				return true;
			}
		}
		return false;
	}

}