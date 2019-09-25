package com.rongdu.common.filter;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.VerifyCodeUtil;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.sys.security.FormAuthenticationFilter;

/**
 * 负责授权码的生成与验证
 * 
 * @author sunda
 */
public class ValidateCodeFilter implements Filter {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	// 是否验证码登录
	private String isValidateCodeLogin="isValidateCodeLogin";
	// 验证失败后跳转的页面
	private String failureUrl;
	// 验证码参数名
	private String paramName;
	// 图片的扩展名
	private String imgExtName = ".jpg";
	// 使用ConcurrentHashMap的本地缓存策略.
	public Cache<String, String> cache;

	@Override
	public void init(FilterConfig config) throws ServletException {
		failureUrl = config.getInitParameter("failureUrl");
		if (StringUtils.isBlank(failureUrl)) {
			throw new IllegalArgumentException("ValidateCodeFilter缺少failureUrl参数");
		}
		paramName = config.getInitParameter("paramName");
		if (StringUtils.isBlank(paramName)) {
			throw new IllegalArgumentException("ValidateCodeFilter缺少paramName参数");
		}
		/**
		 * 使用ConcurrentHashMap的本地缓存策略. 基于Google Collection实现: 1.加大并发锁数量.
		 * 2.每个放入的对象在固定时间后过期.
		 */
		cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (StringUtils.endsWith(request.getRequestURI(), imgExtName)) {
			generateImage(request, response);
		} else {
			String method = request.getMethod();
			if (method.equalsIgnoreCase("post")) {
				boolean validated = validate(request);
				if (validated) {
					chain.doFilter(request, response);
				} else {
					request.setAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "验证码错误");
					request.getRequestDispatcher(failureUrl).forward(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * 验证验证码.
	 */
	private boolean validate(final HttpServletRequest request) {
		boolean isValidateCodeLogin = Boolean.valueOf(request.getParameter(this.isValidateCodeLogin));
		logger.debug("管理后台>是否验证码登录：{}", isValidateCodeLogin);
		if (!isValidateCodeLogin)
			return true;
		String sessionID = request.getSession().getId();
		String validateCodeInput = request.getParameter(paramName);
		boolean validate = false;
		String validateCode = cache.getIfPresent(sessionID);
		if (null != validateCodeInput) {
			validate = validateCodeInput.equalsIgnoreCase(validateCode);
		}
		logger.debug("管理后台>用户登录>图片验证码：{} vs {}，是否匹配{}", validateCodeInput, validateCode, validate);
		return validate;
	}

	/**
	 * 跳转到失败页面.
	 * 
	 * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception.
	 */
	protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.sendRedirect(request.getContextPath() + failureUrl);
	}

	/**
	 * 生成验证码图片.
	 */
	private void generateImage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);

		// 取随机产生的认证码
		String sessionID = request.getSession().getId();
		// 将认证码存入Cache
		cache.put(sessionID, verifyCode);
		String ip = Servlets.getIpAddress(request);
		logger.debug("管理后台>用户登录>图片验证码：{}，来源于{}", verifyCode, ip);
		ServletOutputStream out = response.getOutputStream();
		// 输出图象到页面
		VerifyCodeUtil.outputImage(60, 30, out, verifyCode);
		// 关闭输入流
		IOUtils.closeQuietly(out);
	}

	@Override
	public void destroy() {
		cache.cleanUp();
		cache = null;
	}

}
