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


public class PreloanAuditApiInvoker {

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String apiUrl = "https://apitest.tongdun.cn/preloan/apply/v5";
    private PoolingHttpClientConnectionManager connMgr;
    private RequestConfig requestConfig;

    public PreloanAuditApiInvoker() {
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
		params.put("partner_key",TongdunConfig.partner_key);
		// 此处填写策略集上的事件标识
		params.put("app_name", TongdunConfig.android_app_name);
//		//此处填写移动端sdk采集到的信息black_box
//		params.put("black_box", "your_black_box_to_send_to_api");
//		//以下填写其他要传的参数，比如系统字段，扩展字段
//		params.put("account_login", "your_login_name");
//		//终端IP地址
//		params.put("ip_address", "your_login_ip");
		
		String name = "王五";
		String idNo = "451110190501015626";
		String mobile = "18600000000";
		// 业务参数
		params.put("name", name);
		params.put("id_number", idNo);
		params.put("mobile", mobile);

        
        FraudApiVO apiResp = new PreloanAuditApiInvoker().invoke(params);
        System.out.println(apiResp.getSuccess());
        System.out.println(apiResp.getReason_code());
        String sequenceId = apiResp.getSeq_id();
        queryRuleDetail(sequenceId);
    }
    
    private static void queryRuleDetail(String sequenceId) {
		// 填写参数
        String partnerCode = TongdunConfig.partner_code;
        String partnerKey = TongdunConfig.partner_key;
        Environment env = Environment.SANDBOX; // Environment.PRODUCT表示调用生产环境, 测试环境请修改为Environment.SANDBOX

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
        final PreloanAuditApiInvoker invoker = new PreloanAuditApiInvoker();
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
