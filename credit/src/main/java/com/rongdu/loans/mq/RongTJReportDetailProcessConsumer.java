package com.rongdu.loans.mq;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJReportReq;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.loan.service.RongService;

/**  
* @Title: RongTJReportDetailConsumer.java  
* @Package com.rongdu.loans.mq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Service("rongTJReportDetailConsumer")
public class RongTJReportDetailProcessConsumer implements ChannelAwareMessageListener {
	@Autowired
	private RongService rongService;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.debug("----------进入【接收处理】融天机运营商报告消费队列----------");
            CommonMessage<RongTJReportReq> msg = parseMessage(message);
            logger.debug("接收处理-融天机运营商报告消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            RongTJReportReq rTjReportReq = msg.getMessage();
            String orderNo = rTjReportReq.getOrderNo();
            String searchId = rTjReportReq.getSearch_id();
            String userId = rTjReportReq.getUserId();
            String applyId = rTjReportReq.getApplyId();
            String state = rTjReportReq.getState();
            boolean flag = rongService.rongReportDetail(orderNo, searchId, userId, applyId, state);
            if (!flag) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                        message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBody());
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                    message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBody());
            e.printStackTrace();
        }
    }
	
	/**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<RongTJReportReq> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<RongTJReportReq>> reference = new TypeReference<CommonMessage<RongTJReportReq>>() {
        };
        CommonMessage<RongTJReportReq> msg =
                (CommonMessage<RongTJReportReq>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }

}
