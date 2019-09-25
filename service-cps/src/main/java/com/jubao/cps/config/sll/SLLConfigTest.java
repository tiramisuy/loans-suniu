package com.jubao.cps.config.sll;

import com.rongdu.loans.common.sll.SLLConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈奇虎360配置属性获取服务--测试环境〉
 *
 * @author yuanxianchu
 * @create 2018/12/13
 * @since 1.0.0
 */
@Component("sllConfig")
@Profile("test")
public class SLLConfigTest implements SLLConfig {
    @Override
    public String getPartnerPublicKey() {
        String partnerPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0gXqDS0UHfrX47hxY5U4Q1Bh3egTBSbwhU+Jin" +
                "/pflwVIgigYVSID9FOZrV1oW2Bo5kgOhj/V1AYldHLYTcpTvTy6iYRny5a6lIhLzJy7uGadck/IAfWRK1OQSq+wNx2RjeIA9Yfz7tARt//bnldx+HyXxExa+PRi8fT5r/S6xwIDAQAB";
        return partnerPublicKey;
    }

    @Override
    public String getPartnerPrivateKey() {
        String partnerPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMBIfz6ZPcoiIHtu\n" +
                "Taq16AsN9FFktlrKWW+/Hq2gaLlezCwEmlQv1WpiwB8XwvbrmHg8dWnu1VjT8ruv\n" +
                "gHPB817k5Vy/mfofkQrCFnS1T5/IcxQ4m7xD9pZi0uV/0xYgjmPOHGPvBo7Iy1+F\n" +
                "NMLDc9r/HdOezmVyAJXo3LkXiXchAgMBAAECgYBEC2O8K06KcXk9NNOXTbhH8TA8\n" +
                "fX9qsaDkwqWAm/tzXfCyww46LJNBiqCiYC5GYykZo4uJaVNmk9qaQIkcbc5JcXIJ\n" +
                "umy46pBrsEsMsBkjGjh5WW5bFP3cNdbsITZuPb1nHv9H/qfa3mGhtvk+F8sixZ99\n" +
                "5VSDinxxyexbHXfAAQJBAOO9aq1obA4G8qWn7LxowUv3rJz4jffv/OQXVs1a2g1t\n" +
                "I8h8LA4p2gEvgaBNmMiSt9dufIeyEkSpBqjZx6ArgGECQQDYJLtFmt4U9/duCmzT\n" +
                "1lIKytdLXsgtjLk+lnJwCPmItrLyvvXCfIYVzG8mvzGQGoITcBLz8CmB8IwWyv1e\n" +
                "FG7BAkB0kJbxeukTpOq9b130cYm+YF6xWWcQ6H0AIhIDueSxypLuIuBJv5Id2Tr3\n" +
                "2b/BqZb/ZUXIDpTbH8iQ1CchDCMBAkEAmZzZX6mmJj7pIdLU72UFX9gzVMSi/gRi\n" +
                "b0HbSRaHygsWeQEQhs2bOgjWAjKl1eWRBqGDRs0rosbDuTtBAMkCwQJBALK5s7su\n" +
                "F+X6evNNuuptvuVGpkhzKU6c0TMWYXlLPVjo70ufqpzYC68DZlxc0jz5fBu3T2bk\n" +
                "+xvPvtQKY76XlR8=";
        return partnerPrivateKey;
    }

    @Override
    public String getSLLPublicKey() {
        return null;
    }

    @Override
    public String getAppId() {
        String appId = "140";
        return appId;
    }

    @Override
    public String getGateWay() {
        String gateWay = "http://demo.t.360.cn/xdpt/openapi/proxy/do";
        return gateWay;
    }

    @Override
    public String getJHHAppId() {
        String jhhappId = "184";
        return jhhappId;
    }

}
