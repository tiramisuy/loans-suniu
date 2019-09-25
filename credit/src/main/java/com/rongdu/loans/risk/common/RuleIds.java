package com.rongdu.loans.risk.common;

/**
 * 自有风控规则的ID
 *
 * @author sunda
 * @version 2017-08-14
 */
public class RuleIds {

    /**
     * 贷款申请信息完整性核查
     */
    public static String R1001 = "1001";
    /**
     * 黑名单核查
     */
    public static String R1002 = "1002";
    /**
     * 自有风控规则核查
     */
    public static String R1003 = "1003";
    /**
     * 各类信用评分核查
     */
    public static String R1004 = "1004";
    /**
     * 宜信致诚-阿福共享平台风险名单
     */
    public static String R1005 = "1005";
    /**
     * 宜信致诚-福网风险名单
     */
    public static String R1006 = "1006";
    /**
     * 芝麻信用行业关注名单
     */
    public static String R1007 = "1007";
    /**
     * 腾讯反欺诈服务风险名单
     */
    public static String R1008 = "1008";
    /**
     * 百融特殊名单核查
     */
    public static String R1009 = "1009";
    /**
     * 百融当日多次申请
     */
    public static String R1010 = "1010";
    /**
     * 百融多次申请月度版
     */
    public static String R1011 = "1011";
    /**
     * 白骑士反欺诈规则引擎
     */
    public static String R1012 = "1012";
    /**
     * 同盾反欺诈规则引擎
     */
    public static String R1013 = "1013";
    /**
     * 白骑士资信报告黑名单
     */
    public static String R1014 = "1014";
    /**
     * 新颜
     */
    public static String R1017 = "1017";
    /**
     * 通联
     */
    public static String R1022 = "1022";

    public static String R10220001 = "10220001";
    /**
     * 口袋黑名单核查
     */
    public static String R10150001 = "10150001";
    /**
     * 三方征信1黑名单核查
     */
    public static String R10160001 = "10160001";
    /**
     * 三方征信2黑名单核查
     */
    public static String R10160002 = "10160002";
    /**
     * 新颜-负面拉黑
     */
    public static String R10170001 = "10170001";
    /**
     * 新颜-共债档案-近5个月最大的共贷机构数过多
     */
    public static String R10170002 = "10170002";
    /**
     * 新颜-逾期档案-近5个月最小的共贷订单数过多
     */
    public static String R10170003 = "10170003";
    /**
     * 新颜-逾期机构过多
     */
    public static String R10170004 = "10170004";
    /**
     * 新颜-逾期订单数过多
     */
    public static String R10170005 = "10170005";
    /**
     * 新颜-申请客户存在逾期天数为M1的订单
     */
    public static String R10170006 = "10170006";
    /**
     * 新颜-申请客户当前未结清的逾期订单数过多
     */
    public static String R10170007 = "10170007";
    /**
     * 新颜-申请客户存在逾期金额过高（新颜）
     */
    public static String R10170008 = "10170008";
    /**
     * 新颜-负面拉黑-融都钱包-逾期七天以上
     */
    public static String R10170009 = "10170009";
    /**
     * 新颜-全景雷达聚宝袋得分
     */
    public static String R10170010 = "10170010";
    /**
     * 魔蝎-逾期未还金额较多
     */
    public static String R10180001 = "10180001";
    /**
     * 魔蝎-延滞期数较多
     */
    public static String R10180002 = "10180002";
    /**
     * 魔蝎-延滞金卡片数较多
     */
    public static String R10180003 = "10180003";
    /**
     * 白骑士和同盾风险决策皆为通过
     */
    public static String R10000001 = "10000001";
    /**
     * 白骑士建议通过（复贷）
     */
    public static String R10000002 = "10000002";
    /**
     * 贷款申请信息不完整
     */
    public static String R10010001 = "10010001";
    /**
     * 融都钱包自有黑名单
     */
    public static String R10020001 = "10020001";
    /**
     * 申请人年龄不符
     */
    public static String R10030001 = "10030001";
    /**
     * 手机号未实名
     */
    public static String R10030002 = "10030002";
    /**
     * 手机号实名与申请人身份证名字不一致
     */
    public static String R10030003 = "10030003";
    /**
     * 设备通讯录手机号码较少
     */
    public static String R10030004 = "10030004";
    /**
     * 设备通讯录联系人号码重复比例较大
     */
    public static String R10030005 = "10030005";
    /**
     * 运营商手机号入网时长较短
     */
    public static String R10030006 = "10030006";
    /**
     * 运营商手机号使用时长较短
     */
    public static String R10030007 = "10030007";
    /**
     * 近半年常用联系人累计通话时长较短
     */
    public static String R10030008 = "10030008";
    /**
     * 申请人所在地通话呼入次数比例较低
     */
    public static String R10030009 = "10030009";
    /**
     * 申请人所在地通话呼出次数比例较低
     */
    public static String R10030010 = "10030010";
    /**
     * 近半年较少使用此手机号码
     */
    public static String R10030011 = "10030011";
    /**
     * 填写的常用联系人存疑
     */
    public static String R10030012 = "10030012";
    /**
     * 资金饥渴急需贷款，或者贷款逾期
     */
    public static String R10030013 = "10030013";
    /**
     * 平时较少主动与外界通话
     */
    public static String R10030014 = "10030014";
    /**
     * 紧急联系人都不在申请人归属地
     */
    public static String R10030015 = "10030015";
    /**
     * 日常联系人未保存在设备通讯录中
     */
    public static String R10030016 = "10030016";
    /**
     * 设备通讯录联系含有敏感词
     */
    public static String R10030017 = "10030017";
    /**
     * 申请人工作单位所在地不合要求
     */
    public static String R10030018 = "10030018";
    /**
     * 申请人手机号归属所在地不合要求
     */
    public static String R10030019 = "10030019";
    /**
     * 单位名称含有敏感词
     */
    public static String R10030020 = "10030020";
    /**
     * 工作城市与居住城市不一致
     */
    public static String R10030021 = "10030021";
    /**
     * 申请人IP与GPS城市不一致
     */
    public static String R10030022 = "10030022";
    /**
     * 申请人与紧急联系人沟通较少
     */
    public static String R10030023 = "10030023";
    /**
     * 存在伪造联系人的嫌疑
     */
    public static String R10030024 = "10030024";
    /**
     * 近半年内某一个月主叫较少
     */
    public static String R10030025 = "10030025";
    /**
     * 运营商近半年内某一个消费金额较少
     */
    public static String R10030026 = "10030026";
    /**
     * 运营商近半年内某一个月被叫次数较少
     */
    public static String R10030027 = "10030027";
    /**
     * 在本平台存在15天以上逾期
     */
    public static String R10030028 = "10030028";
    /**
     * 在本平台尚有借款未结清
     */
    public static String R10030029 = "10030029";
    /**
     * Android设备安装有可疑的应用程序
     */
    public static String R10030030 = "10030030";
    /**
     * 在阿福共享平台存在1笔贷款当前逾期M1
     */
    public static String R10030031 = "10030031";
    /**
     * 芝麻账户不存在
     */
    public static String R10030032 = "10030032";
    /**
     * 申请人最近未与紧急联系人沟通
     */
    public static String R10030033 = "10030033";
    /**
     * 白骑士建议拒绝
     */
    public static String R10030034 = "10030034";
    /**
     * 同盾建议拒绝
     */
    public static String R10030035 = "10030035";

    /**
     * 近半年较少使用此手机号码2
     */
    public static String R10030036 = "10030036";

    /**
     * Android设备安装有可疑的应用程序2
     */
    public static String R10030037 = "10030037";

    /**
     * 在阿福共享平台存在2笔贷款当前逾期M1
     */
    public static String R10030038 = "10030038";
    /**
     * 在阿福共享平台存在3笔及以上贷款当前逾期
     */
    public static String R10030039 = "10030039";
    /**
     * 在阿福共享平台存在贷款当前逾期M2+
     */
    public static String R10030040 = "10030040";
    /**
     * 在阿福共享平台存在贷款历史逾期M3+
     */
    public static String R10030041 = "10030041";
    /**
     * 在阿福共享平台存在贷款历史逾期M6+
     */
    public static String R10030042 = "10030042";
    /**
     * 互通电话号码较低
     */
    public static String R10030043 = "10030043";
    /**
     * 互通电话号码较低2
     */
    public static String R10030044 = "10030044";
    /**
     * 申请借贷手机号码过多
     */
    public static String R10030045 = "10030045";
    /**
     * 手机号码关联过多身份证
     */
    public static String R10030046 = "10030046";
    /**
     * 手机号码关联过多身份证2
     */
    public static String R10030047 = "10030047";
    /**
     * 手机号星网模型过多
     */
    public static String R10030048 = "10030048";
    /**
     * 短号联系过多
     */
    public static String R10030049 = "10030049";
    /**
     * 催收类号码通话过多
     */
    public static String R10030050 = "10030050";
    /**
     * 疑似催收类号码通话过多
     */
    public static String R10030051 = "10030051";
    /**
     * 联系人通话次数不符合要求
     */
    public static String R10030052 = "10030052";
    /**
     * 联系人通话时间不符合要求
     */
    public static String R10030053 = "10030053";
    /**
     * 近半年常用联系人平均通话时长较短
     */
    public static String R10030054 = "10030054";
    /**
     * 申请人手机号段不合要求
     */
    public static String R10030055 = "10030055";
    /**
     * 申请人手机号月平均消费不合要求
     */
    public static String R10030056 = "10030056";
    /**
     * 日常联系人未保存在设备通讯录中2
     */
    public static String R10030057 = "10030057";
    /**
     * 日常联系人未保存在设备通讯录中3
     */
    public static String R10030058 = "10030058";
    /**
     * 人脸识别失败
     */
    public static String R10030059 = "10030059";
    /**
     * 配偶有未完结订单
     */
    public static String R10030060 = "10030060";
    /**
     * 运营商账单<4个月
     */
    public static String R10030061 = "10030061";
    /**
     * 连续3天关机次数>=2次
     */
    public static String R10030062 = "10030062";
    /**
     * 紧急联系人在通话详单中的个数<1个
     */
    public static String R10030063 = "10030063";
    /**
     * 非活跃总天数>90
     */
    public static String R10030064 = "10030064";
    /**
     * 贷款类通话次数>=22
     */
    public static String R10030065 = "10030065";
    /**
     * 110通话次数
     */
    public static String R10030066 = "10030066";
    /**
     * 平均月消费>400
     */
    public static String R10030067 = "10030067";
    /**
     * 夜间活动情况>20%
     */
    public static String R10030068 = "10030068";

    /**
     * 该身份证绑定其他手机号有未完成订单
     */
    public static String R10030069 = "10030069";

    /**
     * 融都钱包自有白名单
     */
    public static String R10030070 = "10030070";
    /**
     * 呼入呼出第一名,次数都小于30拒绝
     */
    public static String R10030071 = "10030071";
    /**
     * 申请人身份证归属省份及城市不符
     */
    public static String R10030072 = "10030072";
    /**
     * 申请人身份证签发机关城市不符
     */
    public static String R10030073 = "10030073";

    /**
     * 紧急联系人近3个小时关联其他申请人较多
     */
    public static String R10030074 = "10030074";
    /**
     * 紧急联系人近72个小时关联其他申请人较多
     */
    public static String R10030075 = "10030075";
    /**
     * 紧急联系人近1个月关联其他申请人较多
     */
    public static String R10030076 = "10030076";

    /**
     * 致诚信用分不佳
     */
    public static String R10040001 = "10040001";
    /**
     * 芝麻信用分不佳
     */
    public static String R10040002 = "10040002";
    /**
     * 白骑士反欺诈分较高
     */
    public static String R10040003 = "10040003";
    /**
     * 同盾反欺诈分较高
     */
    public static String R10040004 = "10040004";

    /**
     * 互通电话的号码数量不符合要求
     */
    public static String R10190001 = "10190001";

    /**
     * 天机系统被查询次数不符合要求
     */
    public static String R10190002 = "10190002";

    /**
     * 命中天机黑名单
     */
    public static String R10190003 = "10190003";

    /**
     * 活跃总间隔天数较少
     */
    public static String R10190004 = "10190004";

    /**
     * 非活跃总天数较多
     */
    public static String R10190005 = "10190005";

    /**
     * 每月平均消费不符合要求
     */
    public static String R10190006 = "10190006";
    /**
     * 连续3天关机次数
     */
    public static String R10190007 = "10190007";
    /**
     * 夜间通话活动
     */
    public static String R10190008 = "10190008";
    /**
     * 夜间短信活动
     */
    public static String R10190009 = "10190009";
    /**
     * 催收类通话次数
     */
    public static String R10190010 = "10190010";
    /**
     * 贷款类通话次数
     */
    public static String R10190011 = "10190011";
    /**
     * 赌博类通话次数
     */
    public static String R10190012 = "10190012";
    /**
     * 紧急联系人在通话详单中的个数
     */
    public static String R10190013 = "10190013";
    /**
     * 110号码通话情况
     */
    public static String R10190014 = "10190014";
    /**
     * P2P 、银行类，投资类通话次数
     */
    public static String R10190015 = "10190015";
    /**
     * 当前账户余额太少
     */
    public static String R10190016 = "10190016";
    /**
     * 运营商报告手机号与客户身份信息手机号不一致
     */
    public static String R10190017 = "10190017";
    /**
     * 运营商报告身份证验证
     */
    public static String R10190018 = "10190018";
    /**
     * 运营商报告 近6个月连续3天以上关机次数
     */
    public static String R10190019 = "10190019";
    /**
     * 近6个月关机天数不符合要求
     */
    public static String R10190020 = "10190020";
    /**
     * 近3个月关机天数不符合要求
     */
    public static String R10190021 = "10190021";

    /**
     * 中智诚神月
     */
    public static String R10200001 = "10200001";
    public static String R10200002 = "10200002";
    public static String R10200003 = "10200003";
    public static String R10200004 = "10200004";
    public static String R10200005 = "10200005";
    public static String R10200006 = "10200006";
    public static String R10200007 = "10200007";
    public static String R10200008 = "10200008";
    public static String R10200009 = "10200009";
    public static String R10200010 = "10200010";
    /**
     * 融360 天机西瓜分
     */
    public static String R10210001 = "10210001";

    /**
     * 借款记录审批结果为批贷已放款且还款状态为逾期
     */
    public static String R10050001 = "R10050001";
    /**
     * 风险名单风险明细为长期拖欠
     */
    public static String R10050002 = "R10050002";
    /**
     * 风险名单风险明细为丧失还款能力
     */
    public static String R10050003 = "R10050003";
    /**
     * 风险名单风险明细为法院-失信
     */
    public static String R10050004 = "R10050004";
    /**
     * 风险名单风险明细为法院-被执行
     */
    public static String R10050005 = "R10050005";
    /**
     * 风险类别为丧失还款能力类
     */
    public static String R10050006 = "R10050006";
    /**
     * 风险类别为伪冒类
     */
    public static String R10050007 = "R10050007";
    /**
     * 风险类别为资料虚假类
     */

    public static String R10050008 = "R10050008";
    /**
     * 风险类别为用途虚假类
     */
    public static String R10050009 = "R10050009";
    /**
     * 风险类别为其他
     */
    public static String R10050010 = "R10050010";


    public static String R10030077 = "10030077";
    public static String R10030078 = "10030078";
    public static String R10030079 = "10030079";
    public static String R10030080 = "10030080";
    public static String R10030081 = "10030081";
    public static String R10030082 = "10030082";
    public static String R10030083 = "10030083";
    public static String R10030084 = "10030084";
    public static String R10030085 = "10030085";
    public static String R10030086 = "10030086";
    public static String R10030087 = "10030087";
    public static String R10030088 = "10030088";
    public static String R10030089 = "10030089";
    public static String R10030090 = "10030090";
    public static String R10030091 = "10030091";
    public static String R10030092 = "10030092";
    public static String R10030093 = "10030093";
    public static String R10030094 = "10030094";
    public static String R10030095 = "10030095";
    public static String R10030096 = "10030096";
    public static String R10030097 = "10030097";
    public static String R10030098 = "10030098";
    public static String R10030099 = "10030099";
    public static String R10030100 = "10030100";
    public static String R10030101 = "10030101";
    public static String R10030102 = "10030102";
    public static String R10030103 = "10030103";
    public static String R10030104 = "10030104";
    public static String R10030105 = "10030105";
    public static String R10030106 = "10030106";
    public static String R10030107 = "10030107";
    public static String R10030108 = "10030108";
    public static String R10030109 = "10030109";
    public static String R10030110 = "10030110";
    public static String R10030111 = "10030111";
    public static String R10030112 = "10030112";
    public static String R10030113 = "10030113";
    public static String R10030114 = "10030114";
    public static String R10030115 = "10030115";
    public static String R10030116 = "10030116";
    public static String R10030117 = "10030117";
    public static String R10030118 = "10030118";
    public static String R10030119 = "10030119";
    public static String R10030120 = "10030120";
    public static String R10030121 = "10030121";
    public static String R10030122 = "10030122";
    public static String R10030123 = "10030123";
    public static String R10030124 = "10030124";
    public static String R10030125 = "10030125";
    public static String R10030126 = "10030126";
    public static String R10030127 = "10030127";
    public static String R10030128 = "10030128";
    public static String R10030129 = "10030129";
    public static String R10030130 = "10030130";
    public static String R10030131 = "10030131";
    public static String R10030132 = "10030132";
    public static String R10030133 = "10030133";

    public static String AT = "AT";
    public static String AT001 = "AT001";
    public static String AT002 = "AT002";
    public static String AT003 = "AT003";
    public static String AT004 = "AT004";
    public static String AT005 = "AT005";
    public static String AT006 = "AT006";
    public static String AT007 = "AT007";
    public static String AT008 = "AT008";
    public static String AT009 = "AT009";
    public static String AT010 = "AT010";
    public static String AT011 = "AT011";
    public static String AT012 = "AT012";
    public static String AT013 = "AT013";
    public static String AT014 = "AT014";
    public static String AT015 = "AT015";
    public static String AT016 = "AT016";
    public static String AT017 = "AT017";
    public static String AT018 = "AT018";
    public static String AT019 = "AT019";
    public static String AT020 = "AT020";
    public static String AT021 = "AT021";
    public static String AT022 = "AT022";
    public static String AT023 = "AT023";
    public static String AT024 = "AT024";
    public static String AT025 = "AT025";
    public static String AT026 = "AT026";
    public static String AT027 = "AT027";
    public static String AT028 = "AT028";
}
