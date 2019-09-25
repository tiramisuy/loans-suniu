package com.rongdu.loans.api.common;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;


/**
 * Jcaptcha单例模式
 * @author zcb
 *
 */
public class CaptchaServiceSingleton {

	private CaptchaServiceSingleton() {
	}

	private static ImageCaptchaService instance = null;

	// 传入样式类
	static {
		instance = new DefaultManageableImageCaptchaService(
				new FastHashMapCaptchaStore(), new ImageCaptchaEngineExtend(),
				180, 100000, 75000);
	}

	public static ImageCaptchaService getInstance() {
		return instance;
	}

}