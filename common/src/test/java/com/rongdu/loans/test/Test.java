package com.rongdu.loans.test;

import com.rongdu.common.security.Digests;


public class Test {

	public static void main(String[] args) {
//		System.out.println(StringUtils.printJarFilePath(org.apache.commons.lang3.StringUtils.class));
		
		System.out.println(Digests.sha1("a123456"));
	}
}
