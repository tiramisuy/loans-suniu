package com.rongdu.loans.qhzx.demo;

import com.rongdu.loans.qhzx.common.QhzxConfig;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class DataSecurityUtil {
	public static void main(String[] args) {
		try {
			// String s = signData("abc");
			String s = "HhegzMMlIYKAth5hZS+SMBM2Vz7iuAeypLNuQRzrQaisCKVbR0RgyWBt4LfFcaVJAUekXXfWVZPX\ngODeFw3GpRdg+zZSSNLI4HLaECt5bg6ykwEfGhpk4LktjfzmtTrPlvvtGyzhfiuNQl4Vjo+EQB8W\nNnNJvzODHGVDkLxQjqZFgAbCW2008PjRptAwqxtYGKdg6r7lp2YNLjB1+NjnD09QdZ49on9B5LRw\nLs4RJXYYDsS1luX7ezvCjWlHRVxcKhQ1+56y0I+T1R0bzvzNDsyaAe8Ic/m1iiMB0hrCR9211rgr\nMh80eEoUbn4nmI/qh1ToYURdPHXMmgHvCHP5tYrpH8kBM03ytda4KzIfNHhKFG5+J5iP6n/nqCvm\nidXHzJoB7whz+bW7akfWSxHfpwBKkUi7Yez6j6snDa8/ebiQCX1hGzHFKzB1mqr00euHMpZBU08o\nh9FpnAWOBf+FJJiLaqhKse33";
			verifyData(
					s,
					"BuOIaou42Wov81X6KwvMK3C3HiYb2gfptMWo7RbsE0t2/t1FQWQ5n44awN34WHim1kSROetpqBWh\natb5RcF2NHA81A9HTDXXaymQWjFn4z9hfqyhz33dEMEv0XRRUNvI2TqBEzc/IPL65svImD2zbevU\nLOpqrh8gvOEB2+lGlxw=\n");
			// System.out.println("验签OK");
			// System.out.println(decrypt("Kn7F+lvQqP/59bltknRDBA==",
			// "123456781234567812345678"));
			System.out.println("k62@dfqlRE" + "_"
					+ digest("k62@dfqlRE".getBytes()));
			// System.out.println(getPublicKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String digest(byte[] oriByte) throws Exception {
		MessageDigest md = null;
		String strDes = null;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(oriByte);
			strDes = CommonUtil.bytes2Hex(md.digest());
		} catch (Exception e) {
			throw new Exception("E000016", e);
		}
		return strDes;
	}

	public static String signData(String data) throws Exception {
		try {
			PrivateKey key = getPrivateKey();
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(key);
			sig.update(data.getBytes("utf-8"));
			byte[] sigBytes = sig.sign();
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encodeBuffer(sigBytes);
		} catch (Exception e) {
			throw new Exception("E000012", e);
		}
	}

	public static void verifyData(String data, String signValue)
			throws Exception {
		try {
			PublicKey key = getPublicKey();
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(key);
			sig.update(data.getBytes("utf-8"));
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] signValueByte = decoder.decodeBuffer(signValue);
			if (!sig.verify(signValueByte)) {
				throw new Exception("E000013");
			}
			System.out.println("验签OK！");
		} catch (Exception e) {
			throw new Exception("E000014", e);
		}
	}

	private static PublicKey getPublicKey() throws Exception {
		InputStream is = null;
		try {
	       //公钥
	        String  cerpath = DataSecurityUtil.class.getResource(QhzxConfig.pubkey_path).getPath();      
			is = new FileInputStream(cerpath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
			return cert.getPublicKey();
		} catch (FileNotFoundException e) {
			throw new Exception("E000029");
		} catch (CertificateException e) {
			throw new Exception("E000030");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static PrivateKey getPrivateKey() throws Exception {
		char[] storePwdArr;
		int i;
		BufferedInputStream bis = null;
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
		    //私钥
	        String  pfxpath = DataSecurityUtil.class.getResource(QhzxConfig.prikey_path).getPath();
			FileInputStream fis = new FileInputStream(pfxpath);
			bis = new BufferedInputStream(fis);
			String storePassword = QhzxConfig.prikey_password;
			String storeAlias = "signKey";
			storePwdArr = new char[storePassword.length()];// store password
			for (i = 0; i < storePassword.length(); i++) {
				storePwdArr[i] = storePassword.charAt(i);
			}
			ks.load(bis, storePwdArr);
			PrivateKey priv = (PrivateKey) ks.getKey(storeAlias, storePwdArr);
			return priv;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			throw new Exception("E000033");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("E000031");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("E000032");
		} catch (CertificateException e) {
			e.printStackTrace();
			throw new Exception("E000030");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("E000033");
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			throw new Exception("E000033");
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String decrypt(String sealTxt, String keyStr)
			throws Exception {
		try {
			Cipher cipher = null;
			byte[] byteFina = null;
			SecretKey key = getKey(keyStr);
			try {
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] sealByte = decoder.decodeBuffer(sealTxt);
				byteFina = cipher.doFinal(sealByte);
				return new String(byteFina, "utf-8");
			} catch (Exception e) {
				throw new Exception("E000034", e);
			} finally {
				cipher = null;
			}
		} catch (Exception ee) {
			throw new Exception(ee);
		}
	}

	public static String encrypt(byte[] oriByte, String keyStr)
			throws Exception {
		try {
			byte[] sealTxt = null;
			SecretKey key = getKey(keyStr);
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				sealTxt = cipher.doFinal(oriByte);
				BASE64Encoder encoder = new BASE64Encoder();
				String ret = encoder.encode(sealTxt);
				return ret;
			} catch (Exception e) {
				throw new Exception("E000035", e);
			} finally {
				cipher = null;
			}
		} catch (Exception ee) {
			throw new Exception(ee);
		}
	}

	private static SecretKey getKey(String key) throws Exception {
		try {
			// 实例化DESede密钥
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("utf-8"));
			// 实例化密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			// 生成密钥
			SecretKey secretKey = keyFactory.generateSecret(dks);
			return secretKey;
		} catch (Exception e) {
			throw new Exception("E000036", e);
		}
	}
}
