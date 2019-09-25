package com.rongdu.loans.constant;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
//		String sn = "204b57876674a68aec724128facb811e33413590";
//		String apppwd = "12345678901234567890123456789012";
//		String dest = base64(sha1(sn+apppwd));
//		System.out.println(dest);
		System.out.println(isHighterVersion("1.6.1","1.51"));
	}
	
	private static boolean isHighterVersion(String currentVer, String lastedVer) {
		if (StringUtils.isBlank(lastedVer)) {
			return true;
		}
		String[] curArray = StringUtils.split(currentVer, '.');
		String[] lastedArray = StringUtils.split(lastedVer, '.');
		int curArrayLen = curArray.length;
		int lastedArrayLen = lastedArray.length;
		int len = curArrayLen<lastedArrayLen?curArrayLen:lastedArrayLen;
		int currentInt = 0;
		int lastetInt = 0;
		for (int i = 0; i < len; i++) {
			currentInt = Integer.parseInt(curArray[i]);
			lastetInt = Integer.parseInt(lastedArray[i]);
			if (currentInt>lastetInt) {
				return true;
			}else if (currentInt<lastetInt){
				return false;
			}
			//如果相等，继续比较下一位
		}
		return false;
	}
	
	/**
	 * 用SHA1算法计算散列值
	 * @param str
	 * @return
	 */
	public static byte[] sha1(String str) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return messageDigest.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将字节数据转为base64字符串
	 * @param input
	 * @return
	 */
	public static String base64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}
	
}
