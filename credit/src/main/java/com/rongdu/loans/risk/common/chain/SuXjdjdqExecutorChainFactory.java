package com.rongdu.loans.risk.common.chain;

import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.common.RiskContants;
import com.rongdu.loans.risk.executor.*;
import com.rongdu.loans.risk.executor.jdq.R10010001Executor;
import com.rongdu.loans.risk.executor.jdq.R10020001Executor;
import com.rongdu.loans.risk.executor.jdq.R10030001Executor;
import com.rongdu.loans.risk.executor.jdq.R10030002Executor;
import com.rongdu.loans.risk.executor.jdq.R10030003Executor;
import com.rongdu.loans.risk.executor.jdq.R10030004Executor;
import com.rongdu.loans.risk.executor.jdq.R10030005Executor;
import com.rongdu.loans.risk.executor.jdq.R10030006Executor;
import com.rongdu.loans.risk.executor.jdq.R10030007Executor;
import com.rongdu.loans.risk.executor.jdq.R10030008Executor;
import com.rongdu.loans.risk.executor.jdq.R10030009Executor;
import com.rongdu.loans.risk.executor.jdq.R10030010Executor;
import com.rongdu.loans.risk.executor.jdq.R10030011Executor;
import com.rongdu.loans.risk.executor.jdq.R10030012Executor;
import com.rongdu.loans.risk.executor.jdq.R10030014Executor;
import com.rongdu.loans.risk.executor.jdq.R10030015Executor;
import com.rongdu.loans.risk.executor.jdq.R10030017Executor;
import com.rongdu.loans.risk.executor.jdq.R10030019Executor;
import com.rongdu.loans.risk.executor.jdq.R10030024Executor;
import com.rongdu.loans.risk.executor.jdq.R10030028Executor;
import com.rongdu.loans.risk.executor.jdq.R10030033Executor;
import com.rongdu.loans.risk.executor.jdq.R10030043Executor;
import com.rongdu.loans.risk.executor.jdq.R10030045Executor;
import com.rongdu.loans.risk.executor.jdq.R10030046Executor;
import com.rongdu.loans.risk.executor.jdq.R10030047Executor;
import com.rongdu.loans.risk.executor.jdq.R10030052Executor;
import com.rongdu.loans.risk.executor.jdq.R10030053Executor;
import com.rongdu.loans.risk.executor.jdq.R10030057Executor;
import com.rongdu.loans.risk.executor.jdq.R10030071Executor;
import com.rongdu.loans.risk.executor.jdq.R10030072Executor;
import com.rongdu.loans.risk.executor.jdq.R1005Executor;
import com.rongdu.loans.risk.executor.jdq.R1009Executor;
import com.rongdu.loans.risk.executor.jdq.*;


public class SuXjdjdqExecutorChainFactory {
    public static ExecutorChain createExecutorChain(AutoApproveContext context) {
        ExecutorChain chains = null;
        UserInfoVO userInfo = context.getUserInfo();
        if (userInfo != null && userInfo.getLoanSuccCount() != null && userInfo.getLoanSuccCount() > 0) {
            // 复贷
            chains = createReloanExecutorChain();
            context.put(RiskContants.KEY_BAIQISHI_EVENT_TYPE, BaiqishiConfig.decision_event_type_reloan);
        } else {
            // 默认
            chains = createFirstExecutorChain();
            context.put(RiskContants.KEY_BAIQISHI_EVENT_TYPE, BaiqishiConfig.decision_event_type_default);
        }
        return chains;
    }

    /**
     * 首贷的规则及顺序
     *
     * @return
     */
    private static ExecutorChain createFirstExecutorChain() {
        ExecutorChain chains = new ExecutorChain();

        //1、10010001 申请单完整性检查
        chains.addExecutor(new R10010001Executor());
        //2、10020001 金牛分期黑名单
        chains.addExecutor(new R10020001Executor());

        //3、10190018 身份证验证不一致 数据来源魔蝎
        chains.addExecutor(new R10190018Executor());//运营商报告手机号与客户身份证验证不一致
        //4、10190017 客户信息不一致, 数据来源魔蝎
        chains.addExecutor(new R10190017Executor());//运营商报告手机号与客户身份信息手机号不一致
        //5、10190014 110号码通话次数较多,110号码通话次数>1 数据来源魔蝎
        chains.addExecutor(new R10190014Executor());
        //6、 10190013 改-> 10030063 紧急联系人在通话详单中的个数<1个
        // 10030063 -> 紧急联系人在通话详单中的个数小于等于5个，数据来源于：借点钱分析报告
        chains.addExecutor(new R10030063Executor());//紧急联系人在通话详单中的个数小于等于5个
        //7、10190011 近6月（最近0-180天）通话次数 贷款类通话次数>20  数据来源魔蝎
        chains.addExecutor(new R10190011Executor());//贷款类通话次数较多
        //8、10190010 近6月， 催收类通话次数>11 , 数据来源魔蝎
        chains.addExecutor(new R10190010Executor());//催收类通话次数较多
        //9、10190007 近3个月连续3天以上关机次数不符合要求	近3个月连续3天以上关机次数>=1 数据来源魔蝎
        chains.addExecutor(new R10190007Executor());//近3个月连续3天以上关机次数不符合要求
        //10、10190019 近6个月连续3天以上关机次数不符合要求	近6个月连续3天以上关机次数>=3 数据来源魔蝎
        chains.addExecutor(new R10190019Executor());
        //11、10190006 每月平均消费>400元,或者每月平均消费<30元 , 数据来源魔蝎
        chains.addExecutor(new R10190006Executor());
//        //12、10190005 非活跃总天数>=26
//        chains.addExecutor(new R10190005Executor());
        //13、10190004 活跃总间隔<=149
        //chains.addExecutor(new R10190004Executor());//活跃总间隔<=149
        //14、10190001 联系人互通电话的号码数量不符合要求(近6个月),互通电话的号码数量>120
        chains.addExecutor(new R10190001Executor());//联系人互通电话的号码数量不符合要求(近6个月)

        //15、1012 白骑士 白骑士反欺诈规则引擎 TODO 排查
        chains.addExecutor(new R1012Executor());

        //16、10030071 呼入呼出第一名联系次数都小于30拒绝
        chains.addExecutor(new R10030071Executor());

        //17、10030069 该身份证绑定其他手机号有未完成订单
        chains.addExecutor(new R10030069Executor());//该身份证绑定其他手机号有未完成订单

        //18、1009 百融,查库中规则集, TODO 排查
        chains.addExecutor(new R1009Executor());

        //19、10030064 非活跃总天数>90，数据来源于：现金白卡
        chains.addExecutor(new R10030064Executor());//非活跃总天数>90

        //20、10030061  运营商账单<4个月，数据来源于：分析报告
        chains.addExecutor(new R10030061Executor());//运营商账单<4个月

        //21、10030060 配偶有未完结订单，数据来源于：平台数据
        chains.addExecutor(new R10030060Executor());//配偶有未完结订单

        //22、10030057 设备通讯录号码与运营商近6个月的常用通话记录一致数>=0并且<10，数据来源于：本地设备通讯录
        chains.addExecutor(new R10030057Executor());
        //23、10030055 手机号码为170号段拒绝
        chains.addExecutor(new R10030055Executor());

        //24、10030053 申请人填写的所有联系人，近6个月通话时间都小于20分钟
        chains.addExecutor(new R10030053Executor());
        //25、10030052 申请人填写的所有联系人，近6个月通话次数都小于10次,查通话记录
        chains.addExecutor(new R10030052Executor());

        //26、10030049 近6个月通话记录Top10里电话号码位数小于8的个数≥3.
        chains.addExecutor(new R10030049Executor());


        //27、10030046 身份证组合过其它电话个数≥2，数据来源于：魔蝎
        chains.addExecutor(new R10030046Executor());//身份证关联过多手机号码

        //28、10030047 身份证组合过其它电话个数=1，数据来源于：魔蝎
        chains.addExecutor(new R10030047Executor());//身份证关联手机号个数=1


        //29、10030045 手机号组合过其它身份证个数≥1，数据来源于：魔蝎
        chains.addExecutor(new R10030045Executor());//手机号码关联过多身份证

        //30、10030043 互通电话的号码数量<=20 切 > 12，（近6个月）数据来源于：魔蝎 ,
        chains.addExecutor(new R10030043Executor());//近3个月互通电话号码较低
        //31、10030072 互通号码较少（近3个月）	互通电话的号码数量≤小于等于6，数据来源于：魔蝎
        chains.addExecutor(new R10030072Executor());//近3个月互通电话号码较低


        //32、10030037 Android设备安装有可疑的应用程序（大于等于20个）
//        chains.addExecutor(new R10030037Executor());

        //33、10030036 近半年较少使用此手机号码2
        //近6个月关机天数不符合要求	近6个月用户关机>=12天
        //近3个月关机天数不符合要求	近3个月用户关机>=5天
//        chains.addExecutor(new R10030036Executor());


        //34、10030034 白骑士建议拒绝 根据白骑士反欺诈风控引擎进行决策，建议拒绝 ,
        chains.addExecutor(new R10030034Executor());

        //35、10030033 申请人过去未与常用联系人沟通 申请人填写的配偶和父母联系人近半年内最近一次通话联系时间距申请日≥60天
        chains.addExecutor(new R10030033Executor());

        //36、10030030 Android设备安装有可疑的应用程序（大于等于5个，小于20个）
//        chains.addExecutor(new R10030030Executor());

        //37、10030029 上笔借款未结清，数据来源于：贷后数据
        chains.addExecutor(new R10030029Executor());

        //38、10030028 还款记录出现5天及以上的逾期，数据来源于：贷后数据
        chains.addExecutor(new R10030028Executor());

        //39、10030027 运营商近半年内某一个月被叫次数为0，数据来源于：魔蝎
        chains.addExecutor(new R10030027Executor());

        //40、10030026 运营商近半年内某一个消费金额为≤10，数据来源于：魔蝎
        chains.addExecutor(new R10030026Executor());

        //41、10030025 运营商近半年内某一个月主叫次数为0，数据来源于：魔蝎
        chains.addExecutor(new R10030025Executor());

        //42、10030024 申请人婚姻状况为未婚，填写的父母近半年内最早联系时间距申请日≤150天，且＞120天（父母手机号码后4位与“常用联系电话（近6个月）”手机号码后4位进行匹配不成功）
        chains.addExecutor(new R10030024Executor());

        //43、10030023 近半年通话详单中没有与父母、配偶进行的通话记录
        chains.addExecutor(new R10030023Executor());

        //44、10030021 工作地址“省市”与居住地址“省市”不一致，数据来源于：申请表
        chains.addExecutor(new R10030021Executor());


        //10030020 单位名称含“金融”“贷款”“小贷”关键字，数据来源于：申请数据
        chains.addExecutor(new R10030020Executor());

        //10030019 申请人手机号归属地“西藏”“新疆”“宁德”，数据来源于：借点钱基本数据
        chains.addExecutor(new R10030019Executor());//申请人手机号归属所在地不合要求

        //10030018 申请人工作单位所在地“西藏”“新疆”“宁德”，数据来源于：OCR
        chains.addExecutor(new R10030018Executor());

        //10030017 设备通讯录联系名字含“骗贷”“伪造”“催收”“催钱”“黑户”贷款中介”“口子”“网贷中介”“小贷中介”关键字，数据来源于：借点钱基础补充数据
        chains.addExecutor(new R10030017Executor());//设备通讯录联系含有敏感词

        //10030016 设备通讯录号码与运营商近6个月的常用通话记录（通话次数）TOP20中一致数＜7，数据来源于：借点钱基础数据魔蝎分析报告
        chains.addExecutor(new R10030016Executor());

        //10030015 申请人填写的常用联系人手机号码归属地没有一位在申请人工作所在地，数据来源于：借点钱魔蝎报告数据
        chains.addExecutor(new R10030015Executor());

        //10030014 运营商 拨出电话号码个数＜50(近6个月)，数据来源于：魔蝎
        chains.addExecutor(new R10030014Executor());//平时较少主动与外界通话

        //10030013 运营商 贷款类号码通话使用情况＞20，数据来源于：魔蝎分析报告
        //  chains.addExecutor(new R10030013Executor()); 与10190011 重复

        //10030012 申请人填写的常用联系人与运营商近6个月的常用通话记录TOP20中一致数＜1，数据来源于：魔蝎通话报告数据
        chains.addExecutor(new R10030012Executor());

        //10030011 运营商近半年全天未使用通话和短信功能的累计天数＞30且=<50，数据来源于：借点钱魔蝎报告数据
        chains.addExecutor(new R10030011Executor());

        //10030010 手机运营商为非电信，申请人工作所在地对应地区运营商通话呼入次数百分比＜30%，数据来源于：借点钱魔蝎报告数据
        chains.addExecutor(new R10030010Executor());

        //10030009 手机运营商为非电信，申请人工作所在地对应地区运营商通话呼入次数百分比＜30%，数据来源于：借点钱魔蝎报告数据
        chains.addExecutor(new R10030009Executor());

        //10030008 运营商前5名最常用联系人近半年累计通话时长有一人≤20分钟，数据来源于：借点钱报告
        chains.addExecutor(new R10030008Executor());

        //10030007 运营商手机号使用时长≤4个月（120天），数据来源于：借点钱基础数据魔蝎分析报告
        chains.addExecutor(new R10030007Executor());//运营商手机号使用时长较短

        //10030006 运营商手机号入网时长≤6个月（180天），数据来源于：借点钱基础数据
        chains.addExecutor(new R10030006Executor());//运营商手机号入网时长较短

        //10030005 设备通讯录联系人重复号码比例＞20%，数据来源于：借点钱报告数据
        chains.addExecutor(new R10030005Executor());

        //10030004 设备通讯录11位手机号码联系人且不重复的人数大于0且小于等于10人，数据来源于：借点钱基础补充数据
        chains.addExecutor(new R10030004Executor());// 设备通讯录手机号码较少

        //10030003 手机号实名与申请人身份证名字不一致,数据来源于：OCR、魔蝎
        chains.addExecutor(new R10030003Executor());// 手机号实名与申请人身份证名字不一致

        //10030002 手机号未实名，数据来源于：魔蝎
        chains.addExecutor(new R10030002Executor());// 手机号码是否经过实名认证

        //10030001 申请人年龄＜22或年龄＞46，数据来源于：OCR 数据来源于：自有解析数据
        chains.addExecutor(new R10030001Executor());// 申请人年龄＜=19或年龄＞=46


        //10030074 申请人所填写的紧急联系人手机号近3小时出现在他人紧急联系人中的次数≥2 数据来源于：自有解析数据
        chains.addExecutor(new R10030074Executor());//紧急联系人近3小时关联其他申请人较多

        //10030075 申请人所填写的紧急联系人手机号近72小时出现在他人紧急联系人中的次数≥3 数据来源于：自有解析数据
        chains.addExecutor(new R10030075Executor());//紧急联系人近72小时关联其他申请人较多

        //10030076 申请人所填写的紧急联系人手机号近1个月内出现在他人紧急联系人中的次数≥4  数据来源于：自有解析数据
        chains.addExecutor(new R10030076Executor());//紧急联系人近一个月关联其他申请人较多

        //10190003 申请人命中魔蝎黑名单 关注名单综合评分小于40，数据来源于：魔蝎
        chains.addExecutor(new R10190003Executor());//申请人命中魔蝎黑名单


        //10170004 逾期机构过多（新颜） 申请客户6个月内逾期机构数>=4 数据来源:5-新颜:逾期档案数据
        chains.addExecutor(new R10170004Executor());

        //10170005 逾期订单数过多（新颜） 申请客户6个月内逾期订单数>=7  数据来源:5-新颜:逾期档案数据
        chains.addExecutor(new R10170005Executor());

        //10170006 申请客户存在逾期天数为M1的订单（新颜） 申请客户6个月内存在逾期天数为M1的订单 数据来源:5-新颜:逾期档案数据
        chains.addExecutor(new R10170006Executor());

        //10170007 申请客户当前未结清的逾期订单数过多（新颜） 申请客户6个月内当前未结清的逾期订单数>=2 数据来源:5-新颜:逾期档案数据
        chains.addExecutor(new R10170007Executor());

        //10170008 申请客户存在逾期金额过高（新颜）申请客户6个月内存在逾期金额为2000以上的订单 数据来源:5-新颜:逾期档案数据
        chains.addExecutor(new R10170008Executor());

        //10030073 地域限制 申请人身份证归属省份及城市不符 数据来源:3-自有解析数据
        chains.addExecutor(new R10030073Executor());//申请人身份证归属省份及城市不符

        // 10030133 进件单位不符合
//        chains.addExecutor(new R10030133Executor());

        // 借款记录审批结果为批贷已放款且还款状态为逾期 申请人身份证是否触发宜信阿福的风险名单

        // 宜信阿福
        chains.addExecutor(new R1005Executor());

        // 通联网贷黑名单
        chains.addExecutor(new R10220001Executor());

        return chains;
    }

    /**
     * 复贷的规则及顺序
     *
     * @return
     */
    private static ExecutorChain createReloanExecutorChain() {
        ExecutorChain chains = new ExecutorChain();
        // 黑名单
        chains.addExecutor(new R10020001Executor());
        // 口袋黑名单
        // chains.addExecutor(new R10150001Executor());
        // 三方征信1黑名单
        // chains.addExecutor(new R10160001Executor());
        // chains.addExecutor(new R10160002Executor());
        // 自有规则
        chains.addExecutor(new R10030001Executor());
        chains.addExecutor(new R10030028Executor());

        //如果白骑士风险决策为通过，那么该笔贷款申请自动审批通过（复贷）标注为删除
//        chains.addExecutor(new R10000002Executor());

        // 白骑士
//        chains.addExecutor(new R10030034Executor());
//        chains.addExecutor(new R10040003Executor());
//        chains.addExecutor(new R1012Executor());
        // 百融
//        chains.addExecutor(new R1009Executor());
//        chains.addExecutor(new R1011Executor());
        // 新颜
//        chains.addExecutor(new R10170001Executor());// R10170009Executor替代
        // chains.addExecutor(new R10170009Executor());
        return chains;
    }

}
