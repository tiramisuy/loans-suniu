package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/9/26.
 */
public enum UrgentRecallResult {

    RESULT_1(1, "承诺还款"),
    RESULT_2(2, "拖延还款"),
    RESULT_3(3, "拒绝还款"),
    RESULT_4(4, "他人代偿"),
    RESULT_5(5, "无人接听"),
    RESULT_6(6, "停机"),
    RESULT_7(7, "空号"),
    RESULT_8(8, "换新机主"),
    RESULT_9(9, "占线"),
    RESULT_10(10, "拒绝接听"),
    RESULT_11(11, "来电提醒"),
    RESULT_12(12, "他人转告"),
    RESULT_13(13, "无法转告"),
    RESULT_14(14, "减免"),
    RESULT_15(15, "死亡"),
    RESULT_16(16, "争议"),
    RESULT_17(17, "其他"),
    
    RESULT_99(99, "暂停催收");

    private Integer value;
    private String desc;

    UrgentRecallResult(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UrgentRecallResult get(Integer value) {
        for (UrgentRecallResult urgentRecallResultEnum : UrgentRecallResult.values()) {
            if (urgentRecallResultEnum.getValue().equals(value)) {
                return urgentRecallResultEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
    public String getDesc() {
        return desc;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
