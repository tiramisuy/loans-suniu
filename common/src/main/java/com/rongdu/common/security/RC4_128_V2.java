package com.rongdu.common.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhangxiaolong on 2017/8/22.
 */
public class RC4_128_V2 {
    private static final Logger logger = LoggerFactory.getLogger(RC4_128_V2.class);
    private static final String RC4 = "RC4";
    private static final String UTF8 = "UTF-8";

    /**
     * RC4
     * @param plainText
     * @param rc4Key
     * @return
     */
    public static final String encode( final String plainText, final String rc4Key )
    {
        try
        {
            final Cipher c1 = Cipher.getInstance(RC4);
            c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
            return new String( Base64.encodeBase64(c1.doFinal(plainText.getBytes(UTF8))) );
        }
        catch(Throwable t)
        {
            logger.error("", t);
            return null;
        }
    }

    /**
     * RC4解密
     * @param encodedText
     * @param rc4Key
     * @return
     */
    public static final String decode( final String encodedText, final String rc4Key )
    {
        try
        {
            final Cipher c1 = Cipher.getInstance(RC4);
            c1.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
            return new String( c1.doFinal(Base64.decodeBase64(encodedText.getBytes())), UTF8 );
        }
        catch( Throwable t )
        {
            logger.error("", t);
            return null;
        }
    }


    public static void main(String[] args)
    {
        final String rc4Key = "1234567890012345";
        System.out.println("RC4 KEY [" + rc4Key + "]");

        final String plainTextSeed = "sdfsgdgdgsfsa2342fwe";
        final StringBuilder plainText = new StringBuilder();
        final int loopNumber = 5;
        for( int i=0; i<loopNumber; i++ )
        {
            plainText.append(plainTextSeed);
        }
        System.out.println("plainText [" + plainText.toString() + "]");
        System.out.println("plainText.length [" + plainText.toString().length() + "]");

        final String encodedText = RC4_128_V2.encode(plainText.toString(), rc4Key);
        System.out.println("encodedText [" + encodedText + "]");
        System.out.println("encodedText.length [" + encodedText.length() + "]");

        final String decodedText = RC4_128_V2.decode(encodedText, rc4Key);
        System.out.println("decodedText [" + decodedText + "]");

        System.out.println();

        if( plainText.toString().equals( decodedText ) )
            System.out.println("plainText  =  decodedText");
        else
            System.out.println("plainText  <> decodedText");
    }
}
