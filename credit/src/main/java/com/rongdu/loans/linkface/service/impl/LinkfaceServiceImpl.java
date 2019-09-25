package com.rongdu.loans.linkface.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.CharsetUtils;
import com.rongdu.common.utils.FileUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.linkface.common.LinkfaceConfig;
import com.rongdu.loans.linkface.service.LinkfaceService;
import com.rongdu.loans.linkface.vo.IdnumberVerificationOP;
import com.rongdu.loans.linkface.vo.IdnumberVerificationVO;

@Service
public class LinkfaceServiceImpl extends PartnerApiService implements LinkfaceService {

	@Override
	public IdnumberVerificationVO idnumberVerification(IdnumberVerificationOP op) {
		IdnumberVerificationVO vo = null;

		CloseableHttpResponse response = null;
		HttpPost post = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			LogParam log = new LogParam();
			log.setPartnerId(LinkfaceConfig.partner_id);
			log.setPartnerName(LinkfaceConfig.partner_name);
			log.setBizCode(LinkfaceConfig.selfie_idnumber_verification_biz_code);
			log.setBizName(LinkfaceConfig.selfie_idnumber_verification_biz_name);

			post = new HttpPost(LinkfaceConfig.selfie_idnumber_verification_url);

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

			multipartEntityBuilder.addTextBody("api_id", LinkfaceConfig.app_id);
			multipartEntityBuilder.addTextBody("api_secret",LinkfaceConfig.api_secret);
			multipartEntityBuilder.addTextBody("name", op.getName(),ContentType.create("text/plain", Consts.UTF_8));
			multipartEntityBuilder.addTextBody("id_number", op.getId_number());
			multipartEntityBuilder.addBinaryBody("selfie_file",FileUtils.downLoadFromUrl(op.getSelfie_url()));
			//multipartEntityBuilder.addBinaryBody("selfie_file",new File("D:/tmp/test3.jpg"));
			

			post.setEntity(multipartEntityBuilder.build());

			logger.debug("{}-{}-请求地址：{}", op.getName(), op.getId_number(),
					LinkfaceConfig.selfie_idnumber_verification_url);

			long start = System.currentTimeMillis();
			String responseString = "";
			response = httpclient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				// HttpEntity entitys = response.getEntity();
				// BufferedReader reader = new BufferedReader(new
				// InputStreamReader(entitys.getContent()));
				// responseString = reader.readLine();

				responseString = EntityUtils.toString(response.getEntity(), CharsetUtils.DEFAULT_CHARSET);

				vo = (IdnumberVerificationVO) JsonMapper.fromJsonString(responseString, IdnumberVerificationVO.class);

			} else {
				// HttpEntity r_entity = response.getEntity();
				// responseString = EntityUtils.toString(r_entity);
				responseString = EntityUtils.toString(response.getEntity(), CharsetUtils.DEFAULT_CHARSET);

				vo = (IdnumberVerificationVO) JsonMapper.fromJsonString(responseString, IdnumberVerificationVO.class);

				logger.error("错误码是：" + response.getStatusLine().getStatusCode() + "  "
						+ response.getStatusLine().getReasonPhrase());
				logger.error("出错原因是：" + responseString);
			}

			if (responseString != null && responseString.length() > 1000) {
				logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
						StringUtils.substring(responseString, 0, 1000));
			} else {
				logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
			}

			long end = System.currentTimeMillis();
			long costTime = end - start;
			log.setCostTime(costTime);
			log.setInvokeTime(new java.sql.Date(start));
			log.setSuccess(vo.isSuccess());
			log.setCode(vo.getCode());
			log.setMsg(vo.getMsg());
			log.setUrl(LinkfaceConfig.selfie_idnumber_verification_url);
			log.setSyncRespContent(responseString);
			saveApiInvokeLog(log);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				if (null != post) {
					post.releaseConnection();
				}
				if (null != httpclient) {
					httpclient.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		}

		return vo;
	}

}
