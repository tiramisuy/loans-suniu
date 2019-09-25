package com.rongdu.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CharsetUtils {

    private CharsetUtils() {
    }

    public static String toCharset(final String str, final String charset, int judgeCharsetLength) {
        try {
            String oldCharset = getEncoding(str, judgeCharsetLength);
            return new String(str.getBytes(oldCharset), charset);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return str;
        }
    }

    public static String getEncoding(final String str, int judgeCharsetLength) {
        String encode = CharsetUtils.DEFAULT_CHARSET;
        for (String charset : SUPPORT_CHARSET) {
            if (isCharset(str, charset, judgeCharsetLength)) {
                encode = charset;
                break;
            }
        }
        return encode;
    }

    public static boolean isCharset(final String str, final String charset, int judgeCharsetLength) {
        try {
            String temp = str.length() > judgeCharsetLength ? str.substring(0, judgeCharsetLength) : str;
            return temp.equals(new String(temp.getBytes(charset), charset));
        } catch (Throwable e) {
            return false;
        }
    }
    
    /**
     * 尝试转化编码格式，用于明确字符的编码
     * @param str
     * @return
     */
	public static String tryCharset(String str){
		String result = null;
		String from = null,to = null;
		for (int i = 0; i < SUPPORT_CHARSET.size(); i++) {
			for (int j = 0; j < SUPPORT_CHARSET.size(); j++) {
				from = SUPPORT_CHARSET.get(i);
				to = SUPPORT_CHARSET.get(j);
				try {
					result = new String(str.getBytes(from), to);
					System.out.println("---------------------------"+from+"—>"+to+"---------------------------");
					System.out.println(result);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final List<String> SUPPORT_CHARSET = new ArrayList<String>();

    static {
        SUPPORT_CHARSET.add("ISO-8859-1");

        SUPPORT_CHARSET.add("GB2312");
        SUPPORT_CHARSET.add("GBK");
        SUPPORT_CHARSET.add("GB18030");

        SUPPORT_CHARSET.add("US-ASCII");
        SUPPORT_CHARSET.add("ASCII");

        SUPPORT_CHARSET.add("ISO-2022-KR");

        SUPPORT_CHARSET.add("ISO-8859-2");

        SUPPORT_CHARSET.add("ISO-2022-JP");
        SUPPORT_CHARSET.add("ISO-2022-JP-2");

        SUPPORT_CHARSET.add("UTF-8");
    }
    
}
