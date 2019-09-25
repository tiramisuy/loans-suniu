package com.rongdu.loans.moxie.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportNotifyVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportNotifyVO;
import com.rongdu.loans.mq.MessageProductor;

@RestController
@RequestMapping(value = "/moxie/api/v1")
public class WebHookController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebHookController.class);

	private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

	private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

	private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";

	private static ObjectMapper objectMapper = new ObjectMapper();

	// @Value("${moxie.signature.secret}")
	// private String secret;

	@Autowired
	private MessageProductor messageProductor;

	/**
	 * 回调接口, moxie通过此endpoint通知账单更新和任务状态更新
	 */
	@RequestMapping(value = "/notifications", name = "魔蝎回调", method = RequestMethod.POST)
	public void notifyUpdateBill(@RequestBody String body, ServletRequest request, ServletResponse response) {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// 事件类型：task or bill
		String eventName = httpServletRequest.getHeader(HEADER_MOXIE_EVENT);

		// 业务类型：email、bank、carrier 等
		String eventType = httpServletRequest.getHeader(HEADER_MOXIE_TYPE);

		// body签名
		String signature = httpServletRequest.getHeader(HEADER_MOXIE_SIGNATURE);

		LOGGER.info("request body:" + body);

		if (Strings.isNullOrEmpty(eventName)) {
			writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:"
					+ HEADER_MOXIE_EVENT);
			return;
		}

		if (Strings.isNullOrEmpty(eventType)) {
			writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:"
					+ HEADER_MOXIE_TYPE);
			return;
		}

		if (Strings.isNullOrEmpty(signature)) {
			writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:"
					+ HEADER_MOXIE_SIGNATURE);
			return;
		}

		if (Strings.isNullOrEmpty(body)) {
			writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "request body is empty");
			return;
		}

		// 验签，判断body是否被篡改
		// if (!SignatureUtils.base64Hmac256(secret, body).equals(signature)) {
		// writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST,
		// "signature mismatch");
		// return;
		// }

		// 任务提交
		if (StringUtils.equals(eventName.toLowerCase(), "task.submit")) {
			// 通知状态变更为 '认证中'
			// noticeHttp..
			try {
				Map<String, ?> map = objectMapper.readValue(body, Map.class);
				if (map.containsKey("user_id")) {
					String userId = map.get("user_id").toString();
					String cacheKey = "CREDIT_CARD_OPERATION_" + userId;
					JedisUtils.set(cacheKey, "ing", 30);
				}
			} catch (Exception e) {
				LOGGER.error("body convert to object error", e);
			}
		}

		// 登录结果
		// {"mobile":"15368098198","timestamp":1476084445670,"result":false,"message":"[CALO-22001-10]-服务密码错误，请确认正确后输入。","user_id":"374791","task_id":"fdda6b30-8eba-11e6-b7e9-00163e10b2cd"}
		if (StringUtils.equals(eventName.toLowerCase(), "task")) {
			try {
				Map<String, ?> map = objectMapper.readValue(body, Map.class);
				if (map.containsKey("result")) {
					String result = map.get("result").toString();
					if (StringUtils.equals(result, "false")) {
						if (map.containsKey("message")) {
							String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
							// 通知状态变更为 '认证失败'
							// noticeHttp..
							LOGGER.info("task event. result={}, message={}", result, message);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("body convert to object error", e);
			}
		}

		// 任务过程中的失败
		// 运营商的格式{"mobile":"13429801680","timestamp":1474641874728,"result":false,"message":"系统繁忙，请稍后再试","user_id":"1111","task_id":"3e9ff350-819c-11e6-b7fe-00163e004a23"}
		if (StringUtils.equals(eventName.toLowerCase(), "task.fail")) {
			try {
				Map<String, ?> map = objectMapper.readValue(body, Map.class);
				if (map.containsKey("result") && map.containsKey("message")) {
					String result = map.get("result").toString();
					String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
					if (StringUtils.equals(result, "false")) {

						// 通知状态变更为 '认证失败'
						// noticeHttp..
						LOGGER.info("task fail event. result={}, message={}", result, message);
					}
				}
			} catch (Exception e) {
				LOGGER.error("body convert to object error", e);
			}
		}

		LOGGER.info("event name:" + eventName.toLowerCase());
		// 任务完成的通知处理，其中qq联系人的通知为sns，其它的都为bill
		if (StringUtils.equals(eventName.toLowerCase(), "bill")
				|| StringUtils.equals(eventName.toLowerCase(), "allbill")
				|| StringUtils.equals(eventName.toLowerCase(), "sns")) {

			// 通知状态变更为 '认证完成'
			// noticeHttp..
		}

		if (StringUtils.equals(eventName.toLowerCase(), "report")) {
			if (StringUtils.equals(eventType.toLowerCase(), "email")) {
				EmailReportNotifyVO vo = (EmailReportNotifyVO) JsonMapper.fromJsonString(body,
						EmailReportNotifyVO.class);
				messageProductor.sendToMoxieEmailReportQueue(vo);
			} else if (StringUtils.equals(eventType.toLowerCase(), "bank")) {
				BankReportNotifyVO vo = (BankReportNotifyVO) JsonMapper.fromJsonString(body, BankReportNotifyVO.class);
				messageProductor.sendToMoxieBankReportQueue(vo);
			}
		}

		writeMessage(httpServletResponse, HttpServletResponse.SC_CREATED, "default eventtype");
	}

	private void writeMessage(HttpServletResponse response, int status, String content) {
		response.setStatus(status);
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.write(content);
		} catch (IOException ignored) {
		}
	}
}
