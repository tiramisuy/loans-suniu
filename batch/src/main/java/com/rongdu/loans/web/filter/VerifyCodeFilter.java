package com.rongdu.loans.web.filter;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.MapMaker;
import com.rongdu.loans.utils.VerifyCodeUtil;

/**
 * 负责授权码的生成与验证
 * 
 * @author sunda
 */
public class VerifyCodeFilter implements Filter {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	// 验证失败后跳转的页面
	private String failureUrl;
	// 验证码参数名
	private String paramName;
	// 图片的扩展名
	private String imgExtName = ".jpg";
	// 使用ConcurrentHashMap的本地缓存策略.
	public ConcurrentMap<String, String> cache;

	@Override
	public void init(FilterConfig config) throws ServletException {
		failureUrl = config.getInitParameter("failureUrl");
		if (StringUtils.isBlank(failureUrl)) {
			throw new IllegalArgumentException("VerifyCodeFilter缺少failureUrl参数");
		}
		paramName = config.getInitParameter("paramName");
		if (StringUtils.isBlank(paramName)) {
			throw new IllegalArgumentException("VerifyCodeFilter缺少paramName参数");
		}
		/**
		 * 使用ConcurrentHashMap的本地缓存策略. 基于Google Collection实现: 1.加大并发锁数量.
		 * 2.每个放入的对象在固定时间后过期.
		 */
		cache = new MapMaker().concurrencyLevel(32)
				.expiration(3, TimeUnit.MINUTES).makeMap();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (StringUtils.endsWith(request.getRequestURI(), imgExtName)) {
			genernateImage(request, response);
		} else {
			boolean validated = validate(request);
			if (validated) {
				chain.doFilter(request, response);
			} else {
				redirectFailureUrl(request, response);
			}
		}
	}

	/**
	 * 验证验证码.
	 */
	private boolean validate(final HttpServletRequest request) {
		String sessionID = request.getSession().getId();
		String verifyCode = request.getParameter(paramName);
		boolean validate = false;
		if (null != verifyCode) {
			validate = verifyCode.equalsIgnoreCase(cache.get(sessionID));
		}
		return validate;
	}

	/**
	 * 跳转到失败页面.
	 * 
	 * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception.
	 */
	protected void redirectFailureUrl(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",
				new Exception("验证码错误"));
		response.sendRedirect(request.getContextPath() + failureUrl);
	}

	/**
	 * 生成验证码图片.
	 */
	private void genernateImage(final HttpServletRequest request,
			final HttpServletResponse response) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ServletOutputStream out = null;
		try {
			String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
			logger.info("验证码 : " + verifyCode);
			String sessionID = request.getSession().getId();
			// 将认证码存入Cache
			cache.put(sessionID, verifyCode);
			out = response.getOutputStream();
			// 输出图象到页面
			VerifyCodeUtil.outputImage(60, 30, out, verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("生成验证码图片错误 : ", e);
		} finally {
			// 关闭输入流
			IOUtils.closeQuietly(out);
		}
	}

	@Override
	public void destroy() {
		cache.clear();
		cache = null;
	}

}
