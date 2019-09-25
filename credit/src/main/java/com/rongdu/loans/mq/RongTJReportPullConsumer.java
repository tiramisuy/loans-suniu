package com.rongdu.loans.mq;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import lombok.extern.slf4j.Slf4j;

/**  
* @Title: RongTJReportPullConsumer.java  
* @Package com.rongdu.loans.mq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年9月27日  
* @version V1.0  
*/
@Slf4j
@Service("rongTJReportPullConsumer")
public class RongTJReportPullConsumer implements ChannelAwareMessageListener {
	
	@Autowired
	private CreditDataInvokeService creditDataInvokeService;
	@Autowired
	private LoanApplyService loanApplyService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
            //控制接口调用频率
            log.debug("----------进入【请求生成】融天机运营商报告消费队列----------");
            CommonMessage<Map<String, String>> msg = parseMessage(message);
            log.debug("请求生成-融天机运营商报告消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            Map<String,String> params = msg.getMessage();
            String orderNo = params.get("orderNo");
            String userId = params.get("userId");
            String applyId = params.get("applyId");
            creditDataInvokeService.crawlRongCarrierReport(orderNo, userId, applyId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}

	}
	
	/**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<Map<String, String>> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<Map<String, String>>> reference = new TypeReference<CommonMessage<Map<String, String>>>() {
        };
        CommonMessage<Map<String, String>> msg =
                (CommonMessage<Map<String, String>>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }

}
