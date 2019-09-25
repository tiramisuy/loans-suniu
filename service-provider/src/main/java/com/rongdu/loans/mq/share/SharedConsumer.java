package com.rongdu.loans.mq.share;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.loan.option.share.CustInfo;
import com.rongdu.loans.loan.service.SharedService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service("sharedConsumer")
public class SharedConsumer implements ChannelAwareMessageListener {
    @Autowired
    private SharedService sharedService;


    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            CommonMessage<CustInfo> msg = parseMessage(message);
            log.debug("【共享服务-进件用户信息推送】消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            CustInfo custInfo = msg.getMessage();
            boolean flag = sharedService.pushCustInfo(custInfo);
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
            log.error("【共享服务-进件用户信息推送】处理失败",  e);
        }
    }


    /**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<CustInfo> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<CustInfo>> reference = new TypeReference<CommonMessage<CustInfo>>() {
        };
        CommonMessage<CustInfo> msg =
                (CommonMessage<CustInfo>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }

}