package com.rongdu.loans.tongdun.test;


import cn.fraudmetrix.riskservice.RuleDetailClient;
import cn.fraudmetrix.riskservice.RuleDetailResult;
import cn.fraudmetrix.riskservice.object.Environment;
import cn.fraudmetrix.riskservice.ruledetail.BlackListDetail;
import cn.fraudmetrix.riskservice.ruledetail.BlackListHit;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.tongdun.common.TongdunConfig;
import com.rongdu.loans.tongdun.vo.FraudApiVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FraudApiInvoker {

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String apiUrl = "https://apitest.tongdun.cn/riskService/v1.1";
//    private static final String apiUrl = "https://api.tongdun.cn/riskService/v1.1";
    private PoolingHttpClientConnectionManager connMgr;
    private RequestConfig requestConfig;

    public FraudApiInvoker() {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(50);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(500);
        // 设置读取超时
        configBuilder.setSocketTimeout(500);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(500);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public FraudApiVO invoke(Map<String, Object> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        HttpEntity entity;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.warn("[FraudApiInvoker] invoke failed, response status: " + statusCode);
                return null;
            }
            entity = response.getEntity();
            if (entity == null) {
                log.warn("[FraudApiInvoker] invoke failed, response output is null!");
                return null;
            }
            String result = EntityUtils.toString(entity, "utf-8");
            log.warn("[FraudApiInvoker] response："+result);
            return (FraudApiVO) JsonMapper.fromJsonString(result, FraudApiVO.class);
        } catch (Exception e) {
            log.error("[FraudApiInvoker] invoke throw exception, details: ", e);
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return null;
    }

    private SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage(), e);
        }
        return sslsf;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        //公共参数
		// 此处值填写您的合作方标识
		params.put("partner_code", TongdunConfig.partner_code);
		// 此处填写对应app密钥
		params.put("secret_key", TongdunConfig.ios_secret_key);
		// 此处填写策略集上的事件标识
		params.put("event_id", TongdunConfig.ios_event_id);
//		//此处填写移动端sdk采集到的信息black_box
		params.put("black_box", "eyJlcnJvck1zZyI6IkVycm9yIERvbWFpbj1jb2xsZWN0aW9uIHVuZmluaXNoZWQgQ29kZT0wIFwiKG51bGwpXCIiLCJvcyI6ImlPUyIsImJsYWNrQm94IjoiYkdwT1dsdlhKdUZuKzVsWVdKcGFHNWxTMkFZNlNRbEJtR0EzbGM4WW9tOHhmSXRcL00zVlhLS3JDdE9QMmxcL3BCNHB4cVpqQ3Vod1gwRm5tUzZjTzU3RWhKYmtydVJlWVpzOHhkV0x4Rno1R005cFwvZ1wvUTJTTzdMYnUrdXVBc0t6VmZtRFwvOVJYc0VLdFN5NHlWTzB4ZE9MUlczZE9weFVmelp5b0NMdEs1b1VPeitVQ202bHF2ek1rYm83b2FwRGczaEpLd3F3bHFNem16dmtLNXlVMWN1YW90VjNWQkJudXhqNmxXWjRRbW1FZG5kdURFRklRWGdMOU00YTc4ekxaQlwvckEySnRzRFpHbFV3ZXowRE1hSUJCeTl2T2t0N0xaYWFsZlBOWVc2TTRrczJCcUQxOW41M1dvT25cL1VwTUxpdHJJUGVOR0dlSnJOTFJZZWVVdkI1Z3Z4aEtwcHhydGJFODArTnozaUt2MGtaaEFtem8rNzJ1M21iK0JxUUszUVoxQm9EaFwvT0I0V2ZkdlFpREFkV2ZhbnlHMjg2RXJRVUo0M3B4cE1TN0hxY0xEaFBPVnlDMmpPN3VCdzdyNnZlZCtcL2VaVGFXd2pFYk1YeUc5YTlZZ1h1M2FZazZpcE9IMGN6YkRNZjhxaEk4dDZJd2FjMzNpWWdpV0dyV3llZXhmUHZEc28xQVhHYVdMYzcxa3ROS1hRVXY0SVZnZzcxRyszc3lFYjl6RkNcL1wvc2owSzloZDFSXC81TjNjWTdVU2s0TlpxSk53WHZhMTJXNEk2Vmh5emtzUXlia1U5cGpmd0Z2NkV5ek9SbjVqc2xMaFRxWUFMTDlIUW5cLzNITkdMdnNkNCs2WnE1VFJqK0R0b3dKa2Z5NGEyaCtvQlwvd0lNRFNcL2VnaG9ISmZxU0hrbW1CRSt6MmxnVTh6c0dYUE5NdkFZYjZDQ2FCbGxKbFhESHV3OGtlU0ZVaGVqQjB2c3g2N2hsenRhNjRuT2dpZnBWOVkraGJ3YXpcLzMrOVwvcEdQbDFVUmkrNDI0NG1RREx3NFpoeDJPSmdSNm53blwvenNrMFVnUmk2RUUzMGhtXC84QkQ2YXJWSkNkUkIzTnR1S201UkhLOGtCZ0FPMnZxVDk2YmNkQ1F2bGxReHlUdmx2ODJ5aGpvbEZHWWlOcW5qcFFkeld4ZFJyWkEyTzJKSlZnOEdcL0JaZXNcLzU0S0lCeXFidDczckRwQW5idEY2Z0VwSlIycEwrbk1LY2VCRlJXUHFZZUFXS0pqTm5pNHVcLzZRcmNrZEg0ZFowb3dBOXBvK3pJOExIVXlDSUxFVnJIV1wvUUI1bmhER0NlM3RqekMyTUpJUDBaRVpQNW5pOXpjWFdnWWsxIiwidmVyc2lvbiI6IjMuMC41In0=");
//		//以下填写其他要传的参数，比如系统字段，扩展字段
//		params.put("account_login", "your_login_name");
//		//终端IP地址
		params.put("ip_address", "117.136.81.138");
		
		String name = "李星";
		String idNo = "430921198909242210";
		String mobile = "13657238400";
		// 业务参数
		params.put("account_name", name);
		params.put("id_number", idNo);
		params.put("account_mobile", mobile);


        FraudApiVO apiResp = new FraudApiInvoker().invoke(params);
        System.out.println(apiResp.getSuccess());
        System.out.println(apiResp.getReason_code());
        String sequenceId = apiResp.getSeq_id();
        queryRuleDetail(sequenceId);
    }
    
    private static void queryRuleDetail(String sequenceId) {
		// 填写参数
        String partnerCode = TongdunConfig.partner_code;
        String partnerKey = TongdunConfig.partner_key;
        Environment env = Environment.PRODUCT; // Environment.PRODUCT表示调用生产环境, 测试环境请修改为Environment.SANDBOX

        // 调用接口
        RuleDetailClient client = RuleDetailClient.getInstance(partnerCode, env);
        RuleDetailResult result = client.execute(partnerKey, sequenceId);
        if (result == null) return;

        // 样例：获取风险名单命中的数据
        List<BlackListDetail> list = result.find(BlackListDetail.class);
        System.out.println(JsonMapper.toJsonString(list));
        for (BlackListDetail e : list) {
            List<BlackListHit> hits = e.getHits();
            for (BlackListHit hit : hits) {
                // hit中包含了命中风险名单的具体信息

            }
        }
	}

    public static void heartbeat() {
        final FraudApiInvoker invoker = new FraudApiInvoker();
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("partner_code", "rongdu");
        params.put("secret_key", "bd7fcb98e58f46179339439f3835227b");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        invoker.invoke(params);
                        Thread.sleep(60000);
                    } catch (InterruptedException | IOException e) {
                        break;
                    }
                }
            }
        }, "FraudApiInvoker Heartbeat Thread").start();
    }
}
