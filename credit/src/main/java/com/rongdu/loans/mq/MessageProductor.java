package com.rongdu.loans.mq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.credit.entity.ApiInvokeLog;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportNotifyVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportNotifyVO;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJReportReq;

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

	/**
	 * 向队列推送消息
	 * 
	 * @param obj
	 * @param obj
	 */
	public void sendDataToQueue(ApiInvokeLog obj) {
		CommonMessage<ApiInvokeLog> message = new CommonMessage<ApiInvokeLog>();
		String queueName = QueueConfig.API_INVOKE_LOG.getName();
		String type = QueueConfig.API_INVOKE_LOG.getType();
		String source = "rms";
		String bizId = obj.getId();
		String id = String.format("%s_%s", type, bizId);
		message.setQueueName(queueName);
		message.setType(type);
		message.setSource(source);
		message.setId(id);
		message.setBizId(bizId);
		message.setMessage(obj);
		logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
		amqpTemplate.convertAndSend(queueName, message);
	}

	/**
	 * 准备自动审批
	 */
//	public void sendToPreAutoApproveQueue(String applyId) {
//		CommonMessage<String> message = new CommonMessage<String>();
//		String queueName = QueueConfig.PREPARE_AUTO_APPROVE.getName();
//		String type = QueueConfig.PREPARE_AUTO_APPROVE.getType();
//		String source = "rms";
//		String bizId = applyId;
//		String id = String.format("%s_%s", type, bizId);
//		message.setQueueName(queueName);
//		message.setType(type);
//		message.setSource(source);
//		message.setId(id);
//		message.setBizId(bizId);
//		message.setMessage(applyId);
//		logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
//		amqpTemplate.convertAndSend(queueName, message);
//	}

	/**
	 * 自动审批
	 */
//	public void sendToAutoApproveQueue(String applyId) {
//		CommonMessage<String> message = new CommonMessage<String>();
//		String queueName = QueueConfig.AUTO_APPROVE.getName();
//		String type = QueueConfig.AUTO_APPROVE.getType();
//		String source = "rms";
//		String bizId = applyId;
//		String id = String.format("%s_%s", type, bizId);
//		message.setQueueName(queueName);
//		message.setType(type);
//		message.setSource(source);
//		message.setId(id);
//		message.setBizId(bizId);
//		message.setMessage(applyId);
//		logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
//		amqpTemplate.convertAndSend(queueName, message);
//	}

	/**
	 * 魔蝎信用卡邮箱报告通知
	 */
	public void sendToMoxieEmailReportQueue(EmailReportNotifyVO vo) {
		logger.debug("魔蝎信用卡邮箱报告通知消息生产者：{}", vo);
		amqpTemplate.convertAndSend("moxieEmailReportQueue", vo);
	}

	/**
	 * 魔蝎网银报告通知
	 */
	public void sendToMoxieBankReportQueue(BankReportNotifyVO vo) {
		logger.debug("魔蝎网银报告通知消息生产者：{}", vo);
		amqpTemplate.convertAndSend("moxieBankReportQueue", vo);
	}
	
	/**
	 * code y0706
	 * 接收处理融天机运营商报告队列
	 */
	public void sendRongTJReportDetailQuene(RongTJReportReq rTjReportReq) {
    	logger.debug("----------进入融天机运营商报告处理队列----------");
        CommonMessage<RongTJReportReq> message = new CommonMessage<RongTJReportReq>();
        String queueName = QueueConfig.PROCESS_RONGTJ_REPORTDETAIL.getName();
        String type = QueueConfig.PROCESS_RONGTJ_REPORTDETAIL.getType();
        String source = "rong360";
        String bizId = rTjReportReq.getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(rTjReportReq);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }
	
	/**
	 * 请求生成融天机运营商报告队列
	 */
	public void sendRongTJReportQuene(String orderNo,String userId,String applyId) {
    	logger.debug("----------进入融天机运营商报告拉取队列----------");
    	Map<String, String> tianjiReport = new HashMap<>();
        CommonMessage<Map<String, String>> message = new CommonMessage<Map<String, String>>();
    	tianjiReport.put("orderNo", orderNo);
    	tianjiReport.put("userId", userId);
    	tianjiReport.put("applyId", applyId);
        String queueName = QueueConfig.PULL_RONGTJ_REPORT.getName();
        String type = QueueConfig.PULL_RONGTJ_REPORT.getType();
        String source = "rong360";
        String bizId = orderNo;
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(tianjiReport);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }
	
}
