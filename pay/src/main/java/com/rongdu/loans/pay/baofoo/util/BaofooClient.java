package com.rongdu.loans.pay.baofoo.util;

import com.rongdu.loans.pay.baofoo.domain.RequestParams;
import com.rongdu.loans.pay.baofoo.http.HttpMethod;
import com.rongdu.loans.pay.baofoo.http.HttpSendModel;
import com.rongdu.loans.pay.baofoo.http.SimpleHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 项目名称：baofoo-fopay-sdk-java
 * 类名称：请求客户端
 * 类描述：
 * 创建人：陈少杰
 * 创建时间：2014-10-22 下午2:58:22
 * 修改人：陈少杰
 * 修改时间：2014-10-22 下午2:58:22
 * @version
 */
public class BaofooClient {
	
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(BaofooClient.class);
	
	/**
	 * 宝付接口请求
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static SimpleHttpResponse doRequest(RequestParams params) throws Exception {
		// 请求解密工具
		String postData = "member_id=%s&terminal_id=%s&data_type=%s&data_content=%s&version=%s";
		postData = String.format(postData, params.getMemberId(),
				params.getTerminalId(), params.getDataType(), params.getDataContent(),params.getVersion());
		HttpSendModel httpSendModel = new HttpSendModel(params.getRequestUrl() + "?" + postData);
		logger.debug("宝付代付请求：" + params.getRequestUrl() + "?" + postData);
		httpSendModel.setMethod(HttpMethod.POST);
		return HttpUtil.doRequest(httpSendModel, "utf-8");
	}
}
