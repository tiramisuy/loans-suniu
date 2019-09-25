package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/7/6.
 */
public enum ApplyStatusLifeCycleEnum {
    /** 申请 */
    APPLY_SUCCESS(171,17,"提交申请"),
    /** 审核 */
    WAITING_AOTUCHECK(210,21,"待审核"),
    AOTUCHECK_PASS(211,21,"审批通过"),
    AOTUCHECK_NO_PASS(212,21,"未通过"),
    MANUAL_CHECK(213,21,"人工审批"),
    WAITING_MANUALCHECK(220,22,"待审核"),
    MANUALCHECK_PASS(221,22,"审批通过"),
    MANUALCHECK_NO_PASS(222,22,"未通过"),
    MANUAL_RECHECK(223,22,"人工复审"),
    
    /** 签订 */
    WAITING_SIGN(310,31,"待签订"),
    SIGNED(311,31,"已签订"),

    /** 募集 */
    WAITING_PUSH(410,41,"待推送"),
    PUSH_SUCCESS(411,41,"募集中"),//推送成功
    PUSH_FAIL(412,41,"推送失败"),

    /** 取消*/
    CANCAL(420,42,"已取消"),

    /** 放款 */
    WAITING_LENDING(510,51,"待放款"),
    HAS_BEEN_LENDING(511,51,"已放款"),
    WAITING_WITHDRAWAL(512,51,"待提现"),
    CASH_WITHDRAWAL(513,51,"提现处理中"),
    WITHDRAWAL_SUCCESS(514,51,"提现成功"),
    WITHDRAWAL_FAIL(515,51,"提现失败"),

    /** 还款 */
    WAITING_REPAY(610,61,"待还款"),
    BRING_FORWARD_REPAY(611,61,"提前还款"),
    REPAY(612,61,"已还款"),//正常还款

    /** 逾期 */
    OVERDUE_WAITING_REPAY(710,71,"待还款"),
    OVERDUE_REPAY(711,71,"逾期还款"),

    /** 核销 */
    WAITING_VERIFICATION(810,81,"待核销"),
    VERIFICATION(811,81,"已核销")

    ;
    private Integer value;
    private Integer stage;
    private String desc;

    ApplyStatusLifeCycleEnum(Integer value,Integer stage, String desc) {
        this.value = value;
        this.stage = stage;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (ApplyStatusLifeCycleEnum applyStatusLifeCycleEnum : ApplyStatusLifeCycleEnum.values()) {
            if (applyStatusLifeCycleEnum.getValue().equals(id)) {
                return applyStatusLifeCycleEnum.getDesc();
            }
        }
        return null;
    }

    public static Integer getStage(Integer id) {
        for (ApplyStatusLifeCycleEnum applyStatusLifeCycleEnum : ApplyStatusLifeCycleEnum.values()) {
            if (applyStatusLifeCycleEnum.getValue().equals(id)) {
                return applyStatusLifeCycleEnum.getStage();
            }
        }
        return null;
    }

    public static ApplyStatusLifeCycleEnum get(Integer id) {
        for (ApplyStatusLifeCycleEnum applyStatusLifeCycleEnum : ApplyStatusLifeCycleEnum.values()) {
            if (applyStatusLifeCycleEnum.getValue().equals(id)) {
                return applyStatusLifeCycleEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getStage() {
        return stage;
    }

    public String getDesc() {
        return desc;
    }
}
