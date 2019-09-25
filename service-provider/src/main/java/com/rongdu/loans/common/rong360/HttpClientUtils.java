package com.rongdu.loans.common.rong360;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.utils.CharsetUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Title: HttpUtils.java
 * @Package com.rongdu.loans.common.rong360
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yuanxianchu
 * @date 2018年7月9日
 * @version V1.0
 */
public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private final static int BUFFER_SIZE = 4096;

	private static final String DEFAULT_CHARSET = CharsetUtils.DEFAULT_CHARSET;

	private static PoolingHttpClientConnectionManager cm = null;

	private static final RequestConfig REQUEST_CONFIG_TIME_OUT = RequestConfig.custom().setSocketTimeout(60000)
			.setConnectTimeout(60000).setConnectionRequestTimeout(60000).build();
	
	private static CloseableHttpClient client = null;
	
	private final static HttpClientUtils dataCaller = new HttpClientUtils();
	
	public HttpClientUtils() {
		init();
	}

//	@PostConstruct
	/**
	 * 初始化
	 */
	public void init() {

		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sslcontext.init(null, new TrustManager[] { tm }, null);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		SSLConnectionSocketFactory sslConnectionSocketFactory =
				new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslConnectionSocketFactory)
				.build();
		// 初始化HttpClient链接池
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 设置连接池最大链接数
		cm.setMaxTotal(300);
		// 设置链接主机的最大链接数（实际起作用的是DefaultMaxPerRoute而非MaxTotal,MaxTotal表示链接池大小，仅标识能存入多少链接
		// 而DefaultMaxPerRoute则代表同一时间最多能有多少链接访问主机）
		cm.setDefaultMaxPerRoute(150);
		client = getClient(true);
	}

	/**
	 * @param isPooled
	 *            是否使用连接池
	 */
	public static CloseableHttpClient getClient(boolean isPooled) {
		if (isPooled) {
			if (client == null) {
				/**
				 * 发生异常重试机制
				 */
				HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
					@Override
					public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
						logger.debug("异常重试机制【重试次数】" + executionCount);
						if (executionCount >= 3) { // 重试三次后停止
							return false;
						}
						if (exception instanceof NoHttpResponseException) { // NoHttpResponseException异常替换为exception异常
							return true;
						} else if (exception instanceof ClientProtocolException) { // ClientProtocolException异常替换为exception异常
							return true;
						} else if (exception instanceof SocketTimeoutException) { // SocketTimeoutException异常替换为exception异常
							return true;
						}
						return false;
					}
				};
				
				/**
				 * 根据返回结果重试策略
				 */
				ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new ServiceUnavailableRetryStrategy() {

					@Override
					public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
						logger.debug("返回结果重试策略【重试次数】" + executionCount);
						if (executionCount >= 4)
							return false;
						String respString = null;
						try {
							HttpEntity entity = response.getEntity();
							if (entity != null) {
								//重复读取Http实体的内容
								entity = new BufferedHttpEntity(entity);
								response.setEntity(entity);
							}
							if (entity.getContentType().getValue().equals("image/jpeg")) {
								return false;
							}
							respString = EntityUtils.toString(entity, Charset.forName(DEFAULT_CHARSET));
							JSONObject object = JSONObject.parseObject(respString);
							
							if (object == null
									|| !("200".equals(object.getString("error")) || "0".equals(object.getString("code"))
											|| "200".equals(object.getString("code")) || "SUCCESS".equals(object.getString("code")))) {
								return true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					}

					@Override
					public long getRetryInterval() {
						//重试间隔
						return 6000;
					}
				};
				client = HttpClients.custom().setConnectionManager(cm).setRetryHandler(retryHandler)
						.setServiceUnavailableRetryStrategy(serviceUnavailableRetryStrategy).build();
			}
			return client;
		}
		return HttpClients.createDefault();

	}
	
	public static String postForJson(String serviceUrl, Map<String, String> headerMap, String jsonString){
		String respString = null;
		CloseableHttpResponse httpResponse = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(serviceUrl);
			httpPost.addHeader("Connection", "keep-alive");
			httpPost.addHeader("content-type", "application/json");

			if (headerMap != null && !headerMap.isEmpty()) {
				for (String str : headerMap.keySet()) {
					httpPost.addHeader(str, headerMap.get(str));
				}
			}
			StringEntity se = new StringEntity(jsonString, "utf-8");
			httpPost.setEntity(se);
			httpResponse = client.execute(httpPost);
			respString = getContent(httpResponse.getEntity());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (httpPost != null) {
					httpPost.releaseConnection();
				}
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return respString;
	}
	
	public static String postForPair(String url , Map<String, String> params){
		String respString = postForPair(url, params, null);
		return respString;
	}
	
	public static String postForPair(String url, Map<String, String> params, Map<String, String> headers) {
//		System.out.println("postForJson");
		String respString = null;
		HttpPost httpPost = null;
		httpPost = new HttpPost(url);
		//设置超时时间
		httpPost.setConfig(REQUEST_CONFIG_TIME_OUT);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
		if (headers != null) {
			for (Entry<String, String> set : headers.entrySet()) {
				httpPost.setHeader(set.getKey(), set.getValue());
			}
		}
		// 创建参数列表
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// url格式编码
		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(list, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(uefEntity);
		respString = execute(httpPost);
		return respString;
	}
	
	private static String execute(HttpPost httpPost) {
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).build();
//		HttpClient httpClient = MySSLSocketFactory.getNewHttpClient();
		String respString = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 执行请求
			httpResponse = client.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			if (entity.getContentType().getValue().equals("image/jpeg")) {
				respString = inputStreamToBase64(entity.getContent());
				return respString;
			}
			respString = EntityUtils.toString(entity, Charset.forName(DEFAULT_CHARSET));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpPost != null) {
					httpPost.releaseConnection();
				}
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return respString;
	}
	
	
	private static String getContent(HttpEntity entity) throws Exception {
		return inputStreamTOString(entity.getContent());
	}
	
	private static String inputStreamTOString(InputStream in) throws Exception {
		byte[] data = readInputStream(in);
		return new String(data, DEFAULT_CHARSET);
	}
	
	private static String inputStreamToBase64(InputStream in) throws IOException {
		byte[] data = readInputStream(in);
		BASE64Encoder encode = new BASE64Encoder();
        String s = encode.encode(data);
        return s;
	}
	
	 private static byte[] readInputStream(InputStream in) throws IOException {
		 ByteArrayOutputStream outStream = null;
			byte[] bb;
			try {
				outStream = new ByteArrayOutputStream();
				byte[] data = new byte[BUFFER_SIZE];
				int count;
				while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
					outStream.write(data, 0, count);
				bb = outStream.toByteArray();
			} finally {
				try {
					if (null != outStream) {
						outStream.close();
					}
				} catch (Exception e) {
					logger.error("readInputStream关闭outStream异常");
				}
			}
			return bb;
	 }
	 
}
