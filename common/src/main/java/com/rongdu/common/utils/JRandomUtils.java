package com.rongdu.common.utils;

import org.apache.commons.lang3.RandomUtils;

public class JRandomUtils {

	public static String getRandomNumStr(int len) {
		String s = StringUtils.leftPad("0", len, '0');
		String max = "";
		for (int i = 0; i < len; i++) {
			max = max + "9";
		}
		try {
			Long random = RandomUtils.nextLong(1, Long.parseLong(max));
			s = StringUtils.leftPad(String.valueOf(random), len, '0');
		} catch (Exception e) {
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.leftPad("0", 8, '0'));
		System.out.println(getRandomNumStr(8));
	}
}
