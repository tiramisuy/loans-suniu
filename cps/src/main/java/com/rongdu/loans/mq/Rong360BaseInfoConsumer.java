package com.rongdu.loans.mq;

import java.io.UnsupportedEncodingException;

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
import com.rongdu.loans.loan.option.rong360Model.OrderBaseInfo;
import com.rongdu.loans.loan.service.RongService;

/**
 */
@Service("rong360BaseInfoConsumer")
public class Rong360BaseInfoConsumer implements ChannelAwareMessageListener {
    @Autowired
    private RongService rongService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            CommonMessage<OrderBaseInfo> msg = parseMessage(message);
            logger.debug("融360推送基本信息消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            OrderBaseInfo orderBaseInfo = msg.getMessage();
            boolean flag = rongService.saveUserBaseInfo(orderBaseInfo);
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
    private CommonMessage<OrderBaseInfo> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<OrderBaseInfo>> reference = new TypeReference<CommonMessage<OrderBaseInfo>>() {
        };
        CommonMessage<OrderBaseInfo> msg =
                (CommonMessage<OrderBaseInfo>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }

}