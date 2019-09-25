package com.rongdu.loans.common.dwd;

/**
 * 〈一句话功能简述〉<br>
 * 〈大王贷配置属性获取接口〉
 *
 * @author yuanxianchu
 * @create 2018/11/27
 * @since 1.0.0
 */
public interface DWDConfig {

    String getPartnerPublicKey();
    String getPartnerPrivateKey();
    String getDWDPublicKey();
    String getAppId(DwdUtil.ChannelParse channelCode);
    String getGateWay();
    String getQueryWay();
}
