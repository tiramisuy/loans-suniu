package com.rongdu.core.utils.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.rongdu.core.utils.encode.Encodes;
import com.rongdu.core.utils.encode.JsonBinder;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.utils.StringUtil;



public class ServletUtils {

	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	public static final String AUTHENTICATION_HEADER = "Authorization";

	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
	
	public static JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
	
	private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	
	public static void setDisableCacheHeader(HttpServletResponse response) {
		
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + Encodes.base64Encode(encode.getBytes());
	}
	
	public static String get(HttpServletRequest request,String paramName){
		String value = request.getParameter(paramName);
		return value;
	}
	
	public static String getString(HttpServletRequest request,String paramName){
		return get(request,paramName);
	}
	
	public static Integer getInteger(HttpServletRequest request,String paramName){
		Integer value = null;
		String str = get(request,paramName);
		if (StringUtils.isNotBlank(str)) {
			value = Integer.parseInt(str);
		}
		return value;
	}
	
	public static Long getLong(HttpServletRequest request,String paramName){
		Long value = null;
		String str = get(request,paramName);
		if (StringUtils.isNotBlank(str)) {
			value = Long.parseLong(str);
		}
		return value;
	}
	
	public static Float getFloat(HttpServletRequest request,String paramName){
		Float value = null;
		String str = get(request,paramName);
		if (StringUtils.isNotBlank(str)) {
			value = Float.parseFloat(str);
		}
		return value;
	}
	
	public static Double getDouble(HttpServletRequest request,String paramName){
		Double value = null;
		String str = get(request,paramName);
		if (StringUtils.isNotBlank(str)) {
			value = Double.parseDouble(str);
		}
		return value;
	}
	
	public static Date getDate(HttpServletRequest request,String paramName,String format){
		Date date = null;
		String str = get(request,paramName);
		if (StringUtils.isNotBlank(str)) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		return date;
	}
	
	
	public  static  <T> T encapsulate(HttpServletRequest request,Class<T> clazz){
		Assert.notNull(request, "Request must not be null");
		String prefix = StringUtil.getShortname(clazz.getSimpleName())+".";
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String paramName = null;
		String fieldName =  null;
		String value = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields) {
			fieldName = field.getName();
			paramName = prefix+fieldName;
			value = request.getParameter(paramName);
			if (StringUtils.isNotBlank(value)) {
				value = Encodes.urlDecode(value);
				Reflections.setFieldValue(obj, fieldName, value);
			}
		}
		return obj;
	}
	
	public  static  <T> T bindRequestData(HttpServletRequest request,Class<T> clazz){
		Assert.notNull(request, "Request must not be null");
		T bean = null;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ServletRequestDataBinder binder = new ServletRequestDataBinder(bean);
		binder.bind(request);
		return bean;
	}
	
	
	public  static  <T> T bindRequestMultiData(HttpServletRequest request,Class<T> clazz){
		Assert.notNull(request, "Request must not be null");
		T bean = null;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ServletRequestDataBinder binder = new ServletRequestDataBinder(bean);
 		
		CommonsMultipartResolver comResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest comRequest = comResolver.resolveMultipart(request);
		binder.bind(comRequest);
		
		return bean;
	}
	
	
	public static <T> T encapsulate(HttpServletRequest request,Class<T> clazz,String prefix){
		Assert.notNull(request, "Request must not be null");
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String paramName = null;
		String fieldName = null;
		String value = null;
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields) {
			fieldName = field.getName();
			if (prefix==null) {
				paramName = fieldName;
			}else{
				paramName = prefix+fieldName;
			}			
			value = request.getParameter(paramName);
			if (StringUtils.isNotBlank(value)) {
				value = Encodes.urlDecode(value);
				Reflections.setFieldValue(obj, fieldName, value);
			}
		}
		return obj;
	}
	
	public static void renderJson(HttpServletResponse response,Object obj) {	
		response.setContentType("application/json;charset=UTF-8");   
		response.setHeader("Cache-Control","no-cache"); 
		PrintWriter out = null;	
		String json = jsonBinder.toJson(obj);
		logger.debug("响应给客户端的结果："+ json);
		try {
			out = response.getWriter();
			out.write(json);	
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();			
		}
	}
	
	public static String getRemoteAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getRemoteAddr();  
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                //根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
                ipAddress= inet.getHostAddress();  
            }  
        }  
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
            if(ipAddress.indexOf(",")>0){  
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
            }  
        }  
        return ipAddress;   
	}
	
	public static void printAllPramaters(HttpServletRequest request){
		System.out.println("----------------------------------打印所有请求参数----------------------------------");
		Enumeration<String> names = request.getParameterNames();
		String name = null;
		String[]  values = null;
		while(names.hasMoreElements()){
			name = names.nextElement();
			values = request.getParameterValues(name);
			System.out.println(name+" : "+StringUtils.join(values, ','));
		}
	}

}