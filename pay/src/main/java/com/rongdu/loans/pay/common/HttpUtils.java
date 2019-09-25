/**
 *Copyright 2014-2017 www.jubaoqiandai.com  All rights reserved.
 */
package com.rongdu.loans.pay.common;

import com.rongdu.common.utils.CharsetUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http请求工具类
 *  @author sunda
 *  @version 2017-07-04
 */
public class HttpUtils{
	
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String DEFAULT_CHARSET =CharsetUtils.DEFAULT_CHARSET;
		
	private CloseableHttpClient httpClient = null;
	
	
	public String postForJson(String url , Map<String, String> params){
		 String respString = postForJson(url, params, null);
		return respString;
	}
	
	public String getForJson(String url , Map<String, String> params){
		 String respString = getForJson(url, params, null);
		return respString;
	}
		
	/**
	 * raw方式Post请求
	 * @param url
	 * @param jsonParamBody json请求报文
	 * @return json响应报文
	 */
	public String postForJson(String url , String jsonParamBody){
		 HttpPost httpRequest = new HttpPost(url);;
		 httpRequest.setHeader("Content-Type","application/json;charset="+DEFAULT_CHARSET);
		 httpRequest.setEntity(new StringEntity(jsonParamBody,Charset.forName(DEFAULT_CHARSET)));
		 String respString =  execute(httpRequest);
		return respString;
	}
	
	/**
	 * 执行HttpPost请求
	 * @param httpPost
	 * @return
	 */
	public String execute(HttpPost httpRequest){
		 String respString = null;
		 CloseableHttpClient httpClient = createHttpClient();
		 CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.createDefault() ;		
          //执行请求
          httpResponse = httpClient.execute(httpRequest);
          HttpEntity entity = httpResponse.getEntity();
          respString = EntityUtils.toString(entity,Charset.forName(DEFAULT_CHARSET));  
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpRequest != null) {
					httpRequest.releaseConnection();;
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
      }
		return respString;
	}
	
	/**
	 * 执行HttpGet请求
	 * @param httpPost
	 * @return
	 */
	public String execute(HttpGet httpRequest){
		 String respString = null;
		 CloseableHttpClient httpClient = createHttpClient();
		 CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.createDefault() ;		
          //执行请求
          httpResponse = httpClient.execute(httpRequest);
          HttpEntity entity = httpResponse.getEntity();
          respString = EntityUtils.toString(entity,Charset.forName(DEFAULT_CHARSET)); 
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpRequest != null) {
					httpRequest.releaseConnection();;
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
      }
		return respString;
	}
		
	/**
	 * x-www-form-urlencoded
	 * Post请求
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public String postForJson(String url , Map<String, String> params , Map<String, String> headers){
		 String respString = null;
		 HttpPost httpRequest = null;
		 //创建参数列表
		 List<NameValuePair> list = new ArrayList<NameValuePair>();
		 if (params!=null) {
			 for( Entry<String, String> entry : params.entrySet()){
				 list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			 }			
		 }
		 httpRequest = new HttpPost(url);
		 if (headers!=null) {
			 for ( Entry<String, String> set : headers.entrySet()) {
				 httpRequest.setHeader(set.getKey(), set.getValue());	
			 }				
		 }
		 httpRequest.setHeader("Content-Type","application/x-www-form-urlencoded");
		 //url格式编码
		 UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(list,DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 httpRequest.setEntity(uefEntity);
		 respString =  execute(httpRequest);
		return respString;
	}
	
	/**
	 * x-www-form-urlencoded
	 * Get请求
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public String getForJson(String url , Map<String, String> params , Map<String, String> headers){
		 String respString = null;
		 HttpGet httpRequest = null;
		 //创建参数列表
		 url = makeQueryString(url, params);
		 httpRequest = new HttpGet(url);
		 if (headers!=null) {
			 for ( Entry<String, String> set : headers.entrySet()) {
				 httpRequest.setHeader(set.getKey(), set.getValue());	
			 }				
		 }
		 httpRequest.setHeader("Content-Type","application/x-www-form-urlencoded");
		 //执行请求
		 respString =  execute(httpRequest);
		return respString;
	}

	private CloseableHttpClient createHttpClient() {
		if (httpClient==null) {
			 logger.debug("HttpUtils-正在创建HttpClient");
			httpClient = HttpClients.createDefault();
		}
		return httpClient;
	}
	
	
   /**
    * 组装请求字符串
    * @param args
    * @param charset
    * @return
    * @throws UnsupportedEncodingException
    */
    public static String makeQueryString(String url,Map<String, String> urlParams, String charset) throws UnsupportedEncodingException{
        url = (url==null)?"":url;
        for (Map.Entry<String, String> entry : urlParams.entrySet()){
            url += entry.getKey() + "=" + (charset == null ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";
        }
        return url.substring(0, url.length()-1);
    }
    
    /**
     * 组装请求字符串
     * @param args
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String makeQueryString(Map<String, String> urlParams) {
        try {
			return makeQueryString(null, urlParams, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
         return null;
     }
     
     /**
      * 组装请求字URL
      * @param url
      * @param args
      * @return
      * @throws UnsupportedEncodingException
      */
      public static String makeQueryString(String url,Map<String, String> urlParams){
    	 String target = url;
         try {
        	 String queryString = makeQueryString(null, urlParams, CharsetUtils.DEFAULT_CHARSET);
        	 if(0 != queryString.length()) {
        		 String linkOperator = target.contains("?") ? "&" : "?";
        		 target += linkOperator + queryString;
        	 }
			return target;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
          return target;
      }

}