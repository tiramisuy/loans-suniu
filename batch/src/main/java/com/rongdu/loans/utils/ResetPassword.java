package com.rongdu.loans.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class ResetPassword {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(SecurityUtil.shaPassword("admin",  DigestUtils.md5Hex("admin"+808)));

	}

}
