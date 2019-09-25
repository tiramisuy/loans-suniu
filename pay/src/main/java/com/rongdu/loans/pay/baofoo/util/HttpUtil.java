package com.rongdu.loans.pay.baofoo.util;

import com.rongdu.loans.pay.baofoo.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * 项目名称：baofoo-fopay-sdk-java
 * 类名称：http请求相关工具类
 * 类描述：
 * 创建人：陈少杰
 * 创建时间：2014-10-22 下午2:58:22
 * 修改人：陈少杰
 * 修改时间：2014-10-22 下午2:58:22
 * @version
 */
public class HttpUtil {

	/**
	 * @param httpSendModel
	 * @param getCharSet
	 * @return
	 * @throws Exception 
	 */
	public static SimpleHttpResponse doRequest(HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		// 创建默认的httpClient客户端端
		SimpleHttpClient simpleHttpclient = new SimpleHttpClient();

		try {
			return doRequest(simpleHttpclient, httpSendModel, getCharSet);
		} finally {
			simpleHttpclient.getHttpclient().getConnectionManager().shutdown();
		}

	}

	/**
	 * @param httpclient
	 * @param httpSendModel
	 * @param getCharSet
	 * @return
	 * @throws Exception 
	 */
	public static SimpleHttpResponse doRequest(
			SimpleHttpClient simpleHttpclient, HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		HttpRequestBase httpRequest = buildHttpRequest(httpSendModel);

		if (httpSendModel.getUrl().startsWith("https://")) {
			simpleHttpclient.enableSSL();
		}

		try {
			HttpResponse response = simpleHttpclient.getHttpclient().execute(
					httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			if (isRequestSuccess(statusCode)) {
				return new SimpleHttpResponse(statusCode, EntityUtils.toString(
						response.getEntity(), getCharSet), null);
			} else {
				return new SimpleHttpResponse(statusCode, null, response
						.getStatusLine().getReasonPhrase());
			}

		} catch (Exception e) {
			throw new Exception("http请求异常", e);
		}

	}

	/**
	 * @param httpSendModel
	 * @return
	 * @throws Exception 
	 */
	protected static HttpRequestBase buildHttpRequest(
			HttpSendModel httpSendModel) throws Exception {
		HttpRequestBase httpRequest;
		if (httpSendModel.getMethod() == null) {
			throw new Exception("请求方式未设定");
		} else if (httpSendModel.getMethod() == HttpMethod.POST) {

			String url = httpSendModel.getUrl();
			String sendCharSet = httpSendModel.getCharSet();
			List<HttpFormParameter> params = httpSendModel.getParams();

			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null && params.size() != 0) {

				for (HttpFormParameter param : params) {
					qparams.add(new BasicNameValuePair(param.getName(), param
							.getValue()));
				}

			}

			HttpPost httppost = new HttpPost(url);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(qparams,
						sendCharSet));
			} catch (UnsupportedEncodingException e) {
				throw new Exception("构建post请求参数失败", e);
			}

			httpRequest = httppost;
		} else if (httpSendModel.getMethod() == HttpMethod.GET) {
			HttpGet httpget = new HttpGet(httpSendModel.buildGetRequestUrl());

			httpRequest = httpget;
		} else {
			throw new Exception("请求方式不支持：" + httpSendModel.getMethod());
		}

		return httpRequest;
	}

	/**
	 * 请求是否成功
	 * 
	 * @param statusCode
	 * @return
	 */
	public static boolean isRequestSuccess(int statusCode) {
		return statusCode == 200;
	}
	
	public static String RequestForm(String Url,Map<String,String> Parms){		
		if(Parms.isEmpty()){
			return  "参数不能为空！";
		}
		String PostParms = "";
		int PostItemTotal = Parms.keySet().size();
		int Itemp=0;
		for (String key : Parms.keySet()){
			PostParms += key + "="+Parms.get(key);
			Itemp++;
			if(Itemp<PostItemTotal){
				PostParms +="&";
			}
		}
		HttpSendModel httpSendModel = new HttpSendModel(Url + "?" + PostParms);
		System.out.println("【向宝付请求】：" + Url + "?" + PostParms);
		httpSendModel.setMethod(HttpMethod.POST);
		SimpleHttpResponse response = null;
		try {
			response = doRequest(httpSendModel, "utf-8");
		} catch (Exception e) {
			return e.getMessage();
		}
		return response.getEntityString();

	}

	public static void main(String[] args) throws Exception {
		System.out.println(doRequest(
						new HttpSendModel(
								"http://paytest.baofoo.com/baofoo-fopay/pay/BF0040001.do?member_id=100000276&terminal_id=100000990&data_type=xml&data_content=35be841b6037aef3044a8765523e484d0018ef96406c22de1ef942013b673d1804c170803e7f357fff982e7602fc0fe71a9f80555797db75be83fd1ecfef30af752f32c9ddf7b7e14fcac4c1a846e2fe7e4fa6f8885f464b7b9fafe157a68a3d3f0eae746b2d2fcf43c139df247ebec21fa671cb06db7bd7ac3f763b9d1542659e7050b6566331fd52d4e1f16909406b86f432162bc74e4228accaba8592921009b9119e3c8677d9f6290fa9434237311d11bd81e8d0aecce71fa0d5f4d3f905c4477816e9ca494253fda39be5326d69065a3077a4d1d25c21b8997cd172feadc4b70e38df4e96a86159cb68736c1bf65eeebaa79af981cef7a219d4508641f3b1a997bebe5d4f0f113f784249cf314e985de76dca430685ac9362e93186aa64318f01342a4bc5eabeb359c84af075ec6a290d444dc80017c60492ef729c7a6a89c0e28a261f4c22a8f5283c11f91d8caa5d70f407f48e4088476067d0a2d0d9e212c9f25f6f111bfdc6706972b1b3cf796501c9d3dd4f73479343c91524e68ab2022cf84e173948459dde46bd61d40c4c729b66c1e35938fb7a2b85f88417698e0df5c300af9fdc44e8f22a77da2299165d67df53d91d45f66f7071e1b45fd48ac38f202cfce0ec5fe5be9eeb9e16a632e17108de5f30b94506aa9a8819e3cc45960300f2b44387541497fe96d5feec51da774bebfe64bf45a08c66c1bcaa4c8930b7e245498ae7f6d42d9b78a3981bb1adabe8850794b715488b435e6af02f84e8c8842080d27a929ea3e5b102c1db4c58f434ebeeba685e7ed52cfff245e32ec05763bc0d04223bc88d443a20afa69b1330c2bef66681e871e0311dd46788a96945bd9825aeff12b1dbf33d4fe1903d44c46719ad7ace1b86a1bb05da91c7&version=4.0.0"),
						"utf-8").getEntityString());
	}

}
