package com.rongdu.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class ImageUtil {
	private static final Logger logger = Logger.getLogger(ImageUtil.class);
	private static final int unitLen = 256 * 1024;
	private static final int buffSize = 4 * 1024 * 1024;
	private static final int maxImageSize = 16 * 1024 * 1024;

	public static String getURLImage(String imageUrl) throws Exception {
		String base64Image = "";
		ByteBuffer bb = ByteBuffer.allocate(maxImageSize);
		InputStream inputStream = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setConnectTimeout(5 * 1000);
			inputStream = httpURLConnection.getInputStream();
			int len;
			byte[] buff = new byte[buffSize];
			while ((len = read(inputStream, buff)) > 0) {
				bb.put(buff, 0, len);
			}
			bb.flip();
			byte[] content = new byte[bb.limit()];
			bb.get(content);
			base64Image = byteToBase64(content);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====获取图片异常====", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return base64Image;
	}

	private static String byteToBase64(byte[] imgData) {
		return Base64.encodeBase64String(imgData);
	}

	private static int read(InputStream is, byte[] buff) {
		int len = 0;
		int off = 0;
		try {
			while ((len = is.read(buff, off, unitLen)) != -1) {
				off += len;
				if (unitLen > (buff.length - off))
					break;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return off;
	}

}