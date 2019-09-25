package com.rongdu.loans.statistical.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 *
 * Created by zhangxiaolong on 2017/8/28.
 */
public class ZhimaCreditStatistics extends BaseEntity<ZhimaCreditStatistics> {

    /**
     * 数据统计日期 YYYY-MM-DD
     */
    private String biz_date;
    /**
     * 二级商户编号
     */
    private String linked_merchant_id;
    /**
     * 证件类型 0-身份证；
     */
    private String user_credentials_type = "0";
    /**
     * 证件号码
     */
    private String user_credentials_no;
    /**
     * 姓名
     */
    private String user_name;
    /**
     * 业务号
     */
    private String order_no;
    /**
     * 场景类型 1-资金借贷；
     */
    private String scene_type = "1";
    /**
     * 场景描述
     */
    private String scene_desc = "资金借贷";
    /**
     * 场景状态
     * "当“数据订正标识”为0或3时，不可置空；
     【业务履行阶段】
     0-履约（资金借贷场景下，贷款类业务应还资金已还，但分期业务尚未全部结清；类信用卡业务应还资金已还，但整个账户尚未销户；
     服务使用场景下，用户先享后付类业务整笔订单与费用相关部分应付费用已付，但多期业务尚未全部完结；
     相关约定场景下，应处理的相关业务事件已处理，但多个子事件可并存的业务尚未全部完结；）
     1-违约（资金借贷场景下，贷款类业务或类信用卡业务存在资金逾期未还款；
     服务使用场景下，用户先享后付类业务存在费用逾期未付款；
     物品借还场景下，借还类业务存在物品逾期未归还；
     相关约定场景下，相关业务事件存在违约未处理；）
     2-结清（资金借贷场景下，贷款类业务整笔贷款全部结清，或类信用卡业务整个账户销户；
     服务使用场景下，用户先享后付类业务整笔订单与费用相关部分全部完结；
     物品借还场景下，借还类业务物品完成归还；
     相关约定场景下，相关业务事件全部处理完结，特别是多个子事件可并存的业务中，所有相关子事件全部完结且不再会新增；）

     【业务申请阶段】
     3-用户放弃（在申请商家提供的信用服务流程中，用户主动放弃。比如没有填完申请资料。）
     4-审批拒绝（在申请商家提供的信用服务流程中，用户未通过审批，包括授信审批、放款审批等。）
     5-审批通过（在申请商家提供的信用服务流程中，用户通过审批，包括授信审批、放款审批等。）
     6-服务使用 （
     资金借贷场景下，表示放款；
     服务使用场景下，表示服务开始；
     物品借还场景下，表示物品借出；
     相关约定场景下，表示业务开始。
     ）

     同一业务号同一场景类型下，曾“结清”过的数据记录不应再次更新为“履约""或""违约""状态。
     同一业务号同一场景类型下，曾“用户放弃”过的数据记录不应再次更新为“履约""""违约""或""结清""状态。"

     */
    private String scene_status;
    /**
     * 放款金额
     */
    private String create_amt;
    /**
     * 业务阶段日期
     */
    private String installment_due_date;
    /**
     * 当前违约累计金额
     */
    private String overdue_amt;
    /**
     * 当前违约开始日期 YYYY-MM-DD
     */
    private String gmt_ovd_date;
    /**
     * 数据订正标识
     * "不可置空；
     0-正常数据，无需订正；
     1-一次性删除同一用户同一业务号同一场景类型下某一数据统计日期（含）之前的全部历史记录，其中数据统计日期、二级商户编号、证件类型、证件号码、业务号、场景类型、数据订正标识7个字段不可置空，其他字段置空；
     2-删除同一用户同一业务号同一场景类型下某条历史记录，由数据统计日期、二级商户编号、证件类型、证件号码、业务号、场景类型、分期应还日、数据订正标识8个字段定位到该条记录，其他字段置空；
     3-修改同一用户同一业务号同一场景类型下某条历史记录，由数据统计日期、二级商户编号、证件类型、证件号码、业务号、场景类型、分期应还日、数据订正标识8个字段定位到该条记录，其他字段信息可被订正。"

     */
    private String rectify_flag = "0";
    /**
     * 备注
     */
    private String memo;
    /**
     * 贷款申请id
     */
    private transient String apply_id;
    /**
     * 用户id
     */
    private transient String user_id;
    /**
     * 推送结果：0待推送，1推送成功，2推送失败
     */
    private transient String push_status;


    public String getBiz_date() {
        return biz_date;
    }

    public void setBiz_date(String biz_date) {
        this.biz_date = biz_date;
    }

    public String getLinked_merchant_id() {
        return linked_merchant_id;
    }

    public void setLinked_merchant_id(String linked_merchant_id) {
        this.linked_merchant_id = linked_merchant_id;
    }

    public String getUser_credentials_type() {
        return user_credentials_type;
    }

    public void setUser_credentials_type(String user_credentials_type) {
        this.user_credentials_type = user_credentials_type;
    }

    public String getUser_credentials_no() {
        return user_credentials_no;
    }

    public void setUser_credentials_no(String user_credentials_no) {
        this.user_credentials_no = user_credentials_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getScene_type() {
        return scene_type;
    }

    public void setScene_type(String scene_type) {
        this.scene_type = scene_type;
    }

    public String getScene_desc() {
        return scene_desc;
    }

    public void setScene_desc(String scene_desc) {
        this.scene_desc = scene_desc;
    }

    public String getScene_status() {
        return scene_status;
    }

    public void setScene_status(String scene_status) {
        this.scene_status = scene_status;
    }

    public String getCreate_amt() {
        return create_amt;
    }

    public void setCreate_amt(String create_amt) {
        this.create_amt = create_amt;
    }

    public String getInstallment_due_date() {
        return installment_due_date;
    }

    public void setInstallment_due_date(String installment_due_date) {
        this.installment_due_date = installment_due_date;
    }

    public String getOverdue_amt() {
        return overdue_amt;
    }

    public void setOverdue_amt(String overdue_amt) {
        this.overdue_amt = overdue_amt;
    }

    public String getGmt_ovd_date() {
        return gmt_ovd_date;
    }

    public void setGmt_ovd_date(String gmt_ovd_date) {
        this.gmt_ovd_date = gmt_ovd_date;
    }

    public String getRectify_flag() {
        return rectify_flag;
    }

    public void setRectify_flag(String rectify_flag) {
        this.rectify_flag = rectify_flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPush_status() {
        return push_status;
    }

    public void setPush_status(String push_status) {
        this.push_status = push_status;
    }
}
