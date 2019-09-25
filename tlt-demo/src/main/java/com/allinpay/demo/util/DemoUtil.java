package com.allinpay.demo.util;

import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.xstruct.common.InfoReq;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class DemoUtil {
    private static Provider prvd = null;

    static {
        prvd = new BouncyCastleProvider();
    }

    public static InfoReq makeReq(String trxcod,String merchantid,String username,String userpass) {
        InfoReq info = new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(merchantid + String.format("-%016d", System.currentTimeMillis()));
        info.setUSER_NAME(username);
        info.setUSER_PASS(userpass);
        info.setLEVEL("5");
        info.setDATA_TYPE("2");
        info.setVERSION("05");
        info.setMERCHANT_ID(merchantid);
        return info;
    }

    public static String getNow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    /**
     * @param xmlMsg 待签名报文
     * @return 签名后的报文
     * @throws AIPGException
     */
    public static String buildSignedXml(String xmlMsg,String pfxpass, String... keyFile) throws AIPGException {
        if (xmlMsg == null) {
            throw new AIPGException("传入的加签报文为空");
        }
        String IDD_STR = "<SIGNED_MSG></SIGNED_MSG>";
        if (xmlMsg.indexOf(IDD_STR) == -1) {
            throw new AIPGException("找不到签名信息字段");
        }
        String strMsg = xmlMsg.replaceAll(IDD_STR, "");
        AIPGSignature signature = new AIPGSignature(prvd);
        System.out.println("加签内容" + strMsg);
        String newKeyFile = null;
        if (keyFile != null) {
            newKeyFile = keyFile[0];
        }
        String signedStr = signature.signMsg(strMsg, newKeyFile, pfxpass);
        String strRnt = xmlMsg.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedStr + "</SIGNED_MSG>");
        return strRnt;
    }

    /**
     * @param xmlMsg  返回报文
     * @param pathCer 通联公钥
     * @return
     * @throws AIPGException310001
     */
    public static boolean verifyXml(String xmlMsg, String... pathcer) throws AIPGException {
        if (xmlMsg == null) {
            throw new AIPGException("传入的验签报文为空");
        }
        int pre = xmlMsg.indexOf("<SIGNED_MSG>");
        int suf = xmlMsg.indexOf("</SIGNED_MSG>");
        if (pre == -1 || suf == -1 || pre >= suf) {
            throw new AIPGException("找不到签名信息");
        }
        String signedStr = xmlMsg.substring(pre + 12, suf);
        String msgStr = xmlMsg.substring(0, pre) + xmlMsg.substring(suf + 13);
        AIPGSignature signature = new AIPGSignature(prvd);
        String newPathcer = null;
        if (pathcer.length > 0) {
            newPathcer = pathcer[0];
        }
        return signature.verifyMsg(signedStr, msgStr, newPathcer);
    }
}
