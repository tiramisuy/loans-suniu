package com.rongdu.loans.mq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportNotifyVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportOP;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.moxie.service.MoxieEmailService;
import com.rongdu.loans.moxie.service.MoxieNotifyService;

/**
 * 魔蝎-信用卡邮箱报告通知消费者
 * 
 * @author liuzhuang
 */
@Service("moxieEmailReportConsumer")
public class MoxieEmailReportConsumer implements ChannelAwareMessageListener {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private FileServerClient fileServerClient = new FileServerClient();

	@Autowired
	private MoxieNotifyService moxieNotifyService;
	@Autowired
	private MoxieEmailService moxieEmailService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private CustUserService custUserService;

	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			String body = new String(message.getBody(), "UTF-8");
			logger.debug("魔蝎信用卡邮箱报告通知消息消费者：{}", body);
			// 保存通知记录
			EmailReportNotifyVO vo = JsonMapper.getInstance().fromJson(body, EmailReportNotifyVO.class);
			moxieNotifyService.saveEmailReportNotify(vo);

			// 删除信用卡认证中缓存
			String cacheKey = "CREDIT_CARD_OPERATION_" + vo.getUser_id();
			JedisUtils.del(cacheKey);

			boolean result = Boolean.valueOf(vo.getResult());
			if (result) {
				// 更新信用卡认证状态
				int logRz = loanApplyService.updateAuthStatus(vo.getUser_id(), XjdLifeCycle.LC_CREDIT_1);
				if (logRz == 0) {
					logger.error("更新信用卡认证状态失败,{}", vo.getUser_id());
				}
				// 获取报告数据
				EmailReportOP op = new EmailReportOP();
				op.setEmailId(vo.getEmail_id());
				op.setTaskId(vo.getTask_id());
				List<EmailReportVO> report = moxieEmailService.getReportData(op);
				// 上传报告
				if (report != null) {
					uploadFile(vo.getUser_id(), report, FileBizCode.MOXIE_EMAIL_REPORT.getBizCode(), "txt");
				}
			}
			// 更新用户邮箱
			custUserService.updateEmail(vo.getUser_id(), vo.getEmail());
		} catch (Exception e) {
			// 被拒绝的消息是否重新入队列
			// boolean requeue = true;
			// channel.basicNack(deliveryTag, multiple, requeue);
			e.printStackTrace();
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
		}
	}

	private void uploadFile(String userId, Object vo, String fileBizCode, String fileExt) {
		// 公共参数
		UploadParams params = new UploadParams();
		// 注意获取用户端的IP地址
		String clientIp = "127.0.0.1";
		// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
		params.setUserId(userId);
		params.setApplyId("");
		params.setIp(clientIp);
		params.setSource("5");
		params.setBizCode(fileBizCode);
		String fileBodyText = JsonMapper.toJsonString(vo);
		fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
	}
}