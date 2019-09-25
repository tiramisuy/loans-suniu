package com.rongdu.loans.tencent.common;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.DecoderException;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 腾讯签名工具类
 * @author sunda
 * @version 2017-07-04
 */
public class SignUtils {
	
	
	private static final String SHA1 = "SHA-1";
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	//腾讯提供示例，依赖于com.google.common包
	@Deprecated
	public static String sign(List<String> values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		values.removeAll(Collections.singleton(null)); // remove null
		Collections.sort(values);
		StringBuilder sb = new StringBuilder();
		for (String s : values) {
			sb.append(s);
		}
		return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
	}
	
	/**
	 * 采用sha1方式对请求参数进行签名
	 * @param values
	 * @return
	 */
	public static String sha1Sign(List<String> values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		//remove null
		values.removeAll(Collections.singleton(null)); 
		Collections.sort(values);
		StringBuilder sb = new StringBuilder();
		for (String s : values) {
			sb.append(s);
		}
		Charset charset = Charset.forName("UTF-8");
		byte[] input = sha1(sb.toString().getBytes(charset));
		char[] chars = encodeHex(input);
		String sha1String = new String(chars);
		return sha1String.toUpperCase();
	}
	
	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}
	
	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    public static char[] encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }
    
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toLowerCase
     *            {@code true} converts to lowercase, {@code false} to uppercase
     * @return A char[] containing hexadecimal characters
     * @since 1.4
     */
    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }
    
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toDigits
     *            the output alphabet
     * @return A char[] containing hexadecimal characters
     * @since 1.4
     */
    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
    
    /**
     * Converts a hexadecimal character to an integer.
     *
     * @param ch
     *            A character to convert to an integer digit
     * @param index
     *            The index of the character in the source
     * @return An integer
     * @throws DecoderException
     *             Thrown if ch is an illegal hex character
     */
    protected static int toDigit(final char ch, final int index) throws Exception {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }
	
	public static void main(String[] args) throws Exception {		
		String appId = "TIDA0001";
		String userId = "userID19959248596551";
		String nonceStr = "kHoSxvLZGxSoFsjxlbzEoUzh5PAnTU7T";
		String version = "1.0.0";
		String ticket = "XO99Qfxlti9iTVgHAjwvJdAZKN3nMuUhrsPdPlPVKlcyS50N6tlLnfuFBPIucaMS";
		//以上参数计算得到的签名应为：4AE72E6FBC2E9E1282922B013D1B4C2CBD38C4BD
		
		List<String> values = new ArrayList<>();
		values.add(appId);
		values.add(userId);
		values.add(nonceStr);
		values.add(version);
		values.add(ticket);

		System.out.println(SignUtils.sign(values));
		System.out.println(SignUtils.sha1Sign(values));

	}

}
