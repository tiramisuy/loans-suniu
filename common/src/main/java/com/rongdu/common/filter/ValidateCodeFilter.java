package com.rongdu.common.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rongdu.common.utils.StringUtils;

/**
 * 负责授权码的生成与验证
 * 
 * @author sunda
 */
public class ValidateCodeFilter implements Filter {
	
	//验证失败后跳转的页面
	private String failureUrl;
	//验证码参数名
	private String paramName;	
	//图片的扩展名
	private String imgExtName = ".jpg";
	//使用ConcurrentHashMap的本地缓存策略.
	public Cache<String, String> cache;
	//验证码属性
	private int minLength = 4;
	private int maxLength =4;
//	private int fontSize = 20;
	private int imageWidth = 70;
	private int imageHeight = 26;

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
		  * 使用ConcurrentHashMap的本地缓存策略.		
		  * 基于Google Collection实现:		  
		  * 1.加大并发锁数量.
		  * 2.每个放入的对象在固定时间后过期.  
		  */		  
		cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (StringUtils.endsWith(request.getRequestURI(),imgExtName)) {
			genernateImage(request, response);
		}else {
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
		if (null!=verifyCode) {
			validate = verifyCode.equalsIgnoreCase(cache.getIfPresent(sessionID));
		}
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
	private void genernateImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		// 在内存中创建图象
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 生成背景
		createBackground(g);
		// 取随机产生的认证码
		String validateCode = createCharacter(g);
		String sessionID = request.getSession().getId();
		// 将认证码存入Cache
		cache.put(sessionID, validateCode);	
		ServletOutputStream out = response.getOutputStream();
		g.dispose();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", out);
		//关闭输入流
		IOUtils.closeQuietly(out);
	}
	
	private Color getRandColor(int fc,int bc) { 
		int f = fc;
		int b = bc;
		Random random=new Random();
        if(f>255) {
        	f=255; 
        }
        if(b>255) {
        	b=255; 
        }
        return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f)); 
	}
	
	private void createBackground(Graphics g) {
		// 填充背景
		g.setColor(getRandColor(220,250)); 
		g.fillRect(0, 0, imageWidth, imageHeight);
		// 加入干扰线条
		for (int i = 0; i < 8; i++) {
			g.setColor(getRandColor(40,150));
			Random random = new Random();
			int x = random.nextInt(imageWidth);
			int y = random.nextInt(imageHeight);
			int x1 = random.nextInt(imageWidth);
			int y1 = random.nextInt(imageHeight);
			g.drawLine(x, y, x1, y1);
		}
	}
	
	private String createCharacter(Graphics g) {
		//生成随机类
		Random random = new Random();	
		//产生验证码
		char[] digits = {'0','1','2','3','4','5','6','7','8','9'};
		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','j','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int length = minLength+random.nextInt(maxLength-minLength+1);
		int digitsLen = digits.length;
		int lettersLen = letters.length;
		
		String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"}; 
		StringBuilder builder = new StringBuilder();
		char c = 0;
		for (int i = 0; i < length; i++) {
			switch (random.nextInt(2)) {
			case 0:
				c=digits[random.nextInt(digitsLen)];
				break;
			case 1:
				c=letters[random.nextInt(lettersLen)];
				break;
			default:
				break;
			}
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,26)); 
			g.drawString(String.valueOf(c), 15 * i + 5, 19 + random.nextInt(8));
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * 给定范围获得随机颜色
	 * @param random 
	 * @param start
	 * @param end
	 * @return
	 */
//	private Color getRandomColor(Random random, int start,int end){
//	    if(start>255) start=255;
//	    if(end>255) end=255;
//	    int d= end-start;
//	    int r=start+random.nextInt(d);
//	    int g=start+random.nextInt(d);
//	    int b=start+random.nextInt(d);
//	    return new Color(r,g,b);
//	}

	@Override
	public void destroy() {
		cache.cleanUp();
		cache = null;
	}
	
}
