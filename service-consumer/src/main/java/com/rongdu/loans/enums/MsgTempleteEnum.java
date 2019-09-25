package com.rongdu.loans.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈短信模板枚举类〉
 *
 * @author yuanxianchu
 * @create 2019/5/8
 * @since 1.0.0
 */
public enum MsgTempleteEnum {

    AutoApprove(1,"156816","#name#=%s","【金牛钱包】#name#，您的申请已通过初审，我们将在1小时内致电复核，请保持电话畅通，祝您生活愉快。"),
    ManApprove(2,"156817","#name#=%s&#app#=%s&#p#=%s","【金牛钱包】#name#，您在#app#APP上申请的#p#借款已经终审通过，请您到#app#APP上提现即可到账。"),
    Repay(3,"156820","#n#=%s&#app#=%s&#p#=%s&#t#=%s&#a#=%s","【金牛钱包】#n#，您在#app#APP上申请的#p#明天是#t#还款日，金额#a#元，请您提前做好资金安排。");

    private Integer msgType;// 1:初审通过，2：终审通过，3：还款提醒
    private String tplId;
    private String tplValue;
    private String desc;

    MsgTempleteEnum(Integer msgType, String tplId, String tplValue, String desc) {
        this.msgType = msgType;
        this.tplId = tplId;
        this.tplValue = tplValue;
        this.desc = desc;
    }

    public static MsgTempleteEnum get(Integer msgType) {
        for (MsgTempleteEnum p : MsgTempleteEnum.values()) {
            if (p.getMsgType().intValue() == msgType.intValue()) {
                return p;
            }
        }
        return null;
    }


    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTplValue() {
        return tplValue;
    }

    public void setTplValue(String tplValue) {
        this.tplValue = tplValue;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }
}
