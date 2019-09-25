package com.rongdu.loans.mq.anrong;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.anrong.vo.ShareVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Service
public class AnRongMessageService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送共享审批结果消息
     * @param vo
     */
    public void sendShareMessage(ShareVO vo,QueueConfig config,String type) {
        CommonMessage<ShareVO> message = new CommonMessage<>();
        String source = "rms" + type;
        String bizId = vo.getLoanId();
        String id = String.format("%s_%s", config.getType(), bizId);
        message.setQueueName(config.getName());
        message.setType(config.getType());
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(vo);
        logger.debug("消息生产者：{}，{}，{}", source, config.getType(), bizId);
        amqpTemplate.convertAndSend(config.getName(), message);
    }
}
