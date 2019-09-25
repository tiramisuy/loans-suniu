package com.rongdu.loans.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.Assert;

public class SecurityUtil {

	public static String shaPassword(String rawPass, String salt) {
		Assert.notNull(rawPass, "密码不能为空");
		Assert.notNull(salt, "salt不能为空");
		PasswordEncoder encoder = new ShaPasswordEncoder();
		String shaPassword = encoder.encodePassword(rawPass, salt);
		return shaPassword;
	}

	public static void main(String[] args) {
		String username = "admin";
		String password = "123qweQWE";
		String salt = DigestUtils.md5Hex(username + 808);
		String pswd = SecurityUtil.shaPassword(password, salt);
		System.out.println(pswd);
	}

}
