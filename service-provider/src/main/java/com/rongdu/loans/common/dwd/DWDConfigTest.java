package com.rongdu.loans.common.dwd;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈大王贷配置属性获取服务--测试环境〉
 *
 * @author yuanxianchu
 * @create 2018/11/27
 * @since 1.0.0
 */
@Component("dwdConfig")
@Profile("test")
public class DWDConfigTest implements DWDConfig {
    @Override
    public String getPartnerPublicKey() {
        String partnerPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqJbzaS3AQhVGfbr9MX/pnJOFj\n" +
                "5mFSdQpWyZqUl+Bu1Nllh4stvj4DD8A4efur/U1XQ5R9SecRU5P6qxbdClhVQvMC\n" +
                "1wSpYNi5tUtP6RAyzKcPfp0YPE6m2ErYyU92h6NMW53IODvLzoRhvVhsCaeGka35\n" +
                "Oi4QkdkCk/nz98gkJQIDAQAB";

        return partnerPublicKey;
    }

    @Override
    public String getPartnerPrivateKey() {
        String partnerPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOolvNpLcBCFUZ9u\n" +
                "v0xf+mck4WPmYVJ1ClbJmpSX4G7U2WWHiy2+PgMPwDh5+6v9TVdDlH1J5xFTk/qr\n" +
                "Ft0KWFVC8wLXBKlg2Lm1S0/pEDLMpw9+nRg8TqbYStjJT3aHo0xbncg4O8vOhGG9\n" +
                "WGwJp4aRrfk6LhCR2QKT+fP3yCQlAgMBAAECgYAyudY+HJW8noVeWunKrAhdjBng\n" +
                "QKrY6E5DGw4IHxcaK7alTkYStOJOW30zPwoM6qV0wElNE1Oh509pwOMFJBaah/+F\n" +
                "HoOZrnBwi9cQi9Fh1EwSYX54U2mecN42gWAX7+KtoY0vhE4wkKOKqLL1/F6UVVrE\n" +
                "zzaY+ZjIgQpG3NpFSQJBAPURuN0wbNbZg8P31q61OgNMHXC8D8HsaUnceOBGCk82\n" +
                "/uX6fquDYMc7YNM20OCMV4ZRatMfJuwROUgqoGygLMcCQQD0l06OGcJl+Ck1PJqI\n" +
                "ZMA2QzRzfBm3sGyblO+DqEkiDctYhYRyeRQbAkOEAx8xiMw8vu3JDD2hkMp0ClMX\n" +
                "KYOzAkEAvOZQLI6rFmDLZD6hSEGA2hHNj5wmOUBVWA2NTrIuOCkYCYUwJlw6ONfY\n" +
                "JnZWQv9qZ6pSQTH9+nC8Spgw2AoO+QJAYdVbwI8hKPRYV0kEbuYN1CaRhlfvWYAi\n" +
                "ESFDpJ/dkNd/rMrLLZasqhj/tIAjLAcR2oJsTmytAkEOGdg/YBZk7QJAOuXq3kVW\n" +
                "idibRbDoVLhidf1B/wHHC9ZVjknf7p2DytZJM2fqcjICLxYgMcQy1xkMWkIUxT/f\n" +
                "CXbdLVmLCn1PxA==";

        return partnerPrivateKey;
    }

    @Override
    public String getDWDPublicKey() {
        String dwdPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCH8pjCJ8Kfi0KWWPJNlQ9yY1wTgwcp7w/6gxFxGxp7fgJ1EXjTe6ZzvmA08sBabAuHkWwgpvYcSz2IoRBC+kcJ0dX0BFt7tKUS+HqCXfyeQhOZvmyvdC4L0tWub5s3eQ4t4OR8hvZt4f1Jg5OcLapm0TqNDhwSmw8s/BYssH1VIwIDAQAB";
        return dwdPublicKey;
    }

    @Override
    public String getAppId(DwdUtil.ChannelParse channelParse) {
        String appId;
        switch (channelParse){
            case CYQB:
                appId = "T1000026";
                break;
            case CYQBIOS:
                appId = "T1000026";
                break;
            case DAWANGDAI:
                appId = "T1000026";
                break;
            default:
                appId = "T1000026";
        }
        return appId;
    }

    @Override
    public String getGateWay() {
        //String gateWay = "http://k8s-imgs-dev.sinaif.com:8444/platform/callback";
        //String gateWay = "https://pre-callback.sinawallent.com/platform/callback";
        String gateWay = "http://imgstest.sinawallent.com:8444/cxplatform/platform/callback";
        return gateWay;
    }

    @Override
    public String getQueryWay() {
        //String queryWay = "http://k8s-imgs-dev.sinaif.com:8444/platformweb/query";
        //String queryWay = "https://pre-callback.sinawallent.com/platformweb/query";
        String queryWay = "http://imgstest.sinawallent.com:8444/cxplatformweb/platformweb/query";
        return queryWay;
    }
}
