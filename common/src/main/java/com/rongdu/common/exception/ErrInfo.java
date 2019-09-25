package com.rongdu.common.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum ErrInfo {

    SUCCESS("SUCCESS", "处理成功"),
    ERROR("ERROR", "处理失败"),
    WAITING("WAITING", "处理中"),
    INVALID_TOKEN("INVALID_TOKEN", "令牌无效"),
    SIGN_ERROR("SIGN_ERROR", "签名错误"),
    REG_ERROR("FAIL", "注册失败"),
    MOB_ERROR("FAIL", "手机号格式不正确"),
    REG_USER_EXISTS("FAIL", "您的账户或已经存在，请直接登录"),
    REG_USER_UNEXISTS("FAIL", "账户未注册，请先完成注册"),
    MSG_ERROR("FAIL", "短信验证码不正确"),
    MSG_TYPE_ERROR("FAIL", "请输入有效的短信验证类型"),
    MSG_SEND_ERROR("FAIL", "发送短信验证码失败"),
    MSG_FREQUENTLY("FAIL", "短信验证发送请求过于频繁"),
    MSG_BL("FAIL", "IP黑名单"),
    UPDATE_TOKEN("FAIL", "非正常流程操作修改密码"),
    DO_OVERRUN("FAIL", "操作次数超限,请明天再试"),
    PROCESS_ERROR("FAIL", "流程异常，未查到申请记录"),
    APPLYNO_ERROR("FAIL", "申请编号生成或查询异常"),

    CARD4_VERIFY_FAIL("FAIL", "银行卡信息有误，请重试或换卡"),
    UN_CG_AUTH_CODE("FAIL", "非法请求，请先业务授权"),
    CG_OPEN_FAIL("FAIL", "恒丰银行存管电子账户开通失败"),
    SAVE_IDENTITYINFO_FAIL("FAIL", "保存身份认证失败"),
    UNFIND_CUST("FAIL", "未查询到用户信息"),
    UN_PASS_CARD4_VERIFY("FAIL", "四要素验证未通过，请重新身份认证"),
    REPEAT_PAY("FAIL", "重复还款或还款计划明细id错误，请确认后再试"),
    PREPAY_FAIL("FAIL", "预支付失败"),
    PAY_TIMEOUT("FAIL", "支付超时"),
    PAY_SETTLEMENT_FAIL("FAIL", "还款结算失败"),
    UNFIND_TX_RECORD("FAIL", "暂无交易记录"),

    TENCENT_GET_TICKET_FAIL("FAIL", "获取腾讯NONCE ticket失败"),
    TENCENT_DO_FAIL("FAIL", "腾讯接口调用失败"),
    OCR_IDCARD_FRONT_FAIL("FAIL", "身份证正面识别未成功，请稍后再重试"),
    OCR_IDCARD_BACK_FAIL("FAIL", "身份证反面识别未成功，请稍后再重试"),
    OCR_STA_UPDATE_FAIL("FAIL", "OCR认证状态更新或日志记录失败"),
    FACE_VIDEO_UPLOAD_FAIL("FAIL", "人脸识别视频上载失败"),
    FACE_PHOTO_UPLOAD_FAIL("FAIL", "人脸识别失败，请稍后再重试"),
    OCR_FRONT_PHOTO_UPLOAD_FAIL("FAIL", "身份证正面上载失败，请稍后再重试"),
    OCR_BACK_PHOTO_UPLOAD_FAIL("FAIL", "身份证反面上载失败，请稍后再重试"),
    OCR_IDCARD_EXISTS("FAIL", "身份证已被绑定认证"),

    SESAME_DO_FAIL("FAIL", "芝麻信用接口调用失败"),
    SESAME_AUTH_FAIL("FAIL", "芝麻信用授权失败"),
    SESAME_STA_UPDATE_FAIL("FAIL", "芝麻信用认证状态更新或日志记录失败"),
    OPERATOR_STA_UPDATE_FAIL("FAIL", "运行商认证状态更新或日志记录失败"),

    NOTFIND_VERSION("FAIL", "该app类型版本数据正在维护中···"),
    NOTFIND_MAKELOAN_RECORDS("FAIL", "暂无放款记录"),
    NOTFIND_LOAN_RECORDS("FAIL", "暂无贷款记录"),
    NOTFIND_UNFINISH_LOAN_RECORDS("FAIL", "暂无募集成功且未完结贷款记录"),
    NOTFIND_AUTH_RECORDS("FAIL", "暂无认证日志记录"),
    NOTFIND_CUST_USER("FAIL", "用户未注册"),
    NOTFIND_LOAN_PRODUCT("FAIL", "输入产品编号有误"),
    NOTFIND_CUST_USERINFO("FAIL", "基本信息为空"),
    USERINFO_INCOMPLETE("FAIL", "身份信息不存在或录入不完整"),
    TERMSAUTH_SMSCODE_TIMEOUT("FAIL", "授权短信验证码超时"),
    ACCOUNTID_ERROR("FAIL", "电子账户为空，暂无法授权"),
    TERMSAUTH_FAIL("FAIL", "授权失败"),

    BASIC_SAVE_FAIL("FAIL", "保存基础信息失败，或者更新相关申请状态失败"),
    REPEAT_APPLY("FAIL", "您有未完结申请单，请勿重复提交"),
    APPLY_SAVE_FAIL("FAIL", "保存申请信息失败，或者更新相关申请状态失败"),
    PWD_UPDATE_FAIL("FAIL", "修改密码失败"),
    DEL_CACHE_FAIL("FAIL", "key值未匹配到对应缓存清理"),
    PWDERROR_FREQUENTLY("FAIL", "密码输错次数超限，请稍后再试"),
    USER_LOCK("FAIL", "您的账户已冻结，请联系客服4001622772"),
    NOAUTH_FAIL("FAIL", "认证未完成"),

    BAD_REQUEST("400", "请求参数无效"),
    FORBIDDEN("403", "会话超时或者尚未认证，请重新登录"),
    SERVER_ERROR("500", "网络异常"),

    LIMIT_PAY("FAIL", "暂时无法线上还款,请联系客服线下还款4001622772"),

    RONG_CHECK("FAIL", "请前往融360APP选购商品借款!");

    private String code;
    private String msg;

    ErrInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
