package com.rongdu.loans.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/14
 * @since 1.0.0
 */
public enum PayChannelEnum {

    BAOFOO("BAOFOO","宝付支付","yyyy-MM-dd HH:mm:ss"),
    XIANFENG("XIANFENG","先锋支付","yyyyMMddHHmmss"),
    TONGLIAN("TONGLIAN","通联支付","yyyy-MM-dd HH:mm:ss");

    PayChannelEnum(String channelCode,String channelName,String timePattern){
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.timePattern = timePattern;
    }

    public static PayChannelEnum get(String channelCode) {
        for (PayChannelEnum p : PayChannelEnum.values()) {
            if (p.getChannelCode().equals(channelCode)) {
                return p;
            }
        }
        return null;
    }

    private String channelCode;
    private String channelName;
    private String timePattern;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }
}
