<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">

    <fieldset class="layui-elem-field">
        <legend>基本信息</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>登记姓名</td>
                <td>{{data.report.basic.real_name}}</td>
            </tr>
            <tr>
                <td>登记身份证号</td>
                <td> {{data.report.basic.idcard}}</td>
            </tr>
            <tr>
                <td>生肖</td>
                <td> {{data.shengxiao}}</td>
            </tr>
            <tr>
                <td>本机号码</td>
                <td> {{data.report.basic.cell_phone}}</td>
            </tr>
            <tr>
                <td>手机号码归属地</td>
                <td> {{data.phoneLoc}}</td>
            </tr>
            <tr>
                <td>入网时间</td>
                <td> {{data.report.basic.reg_time}}</td>
            </tr>
            <tr>
                <td>入网时长</td>
                <td> {{data.regLong}}</td>
            </tr>
            <tr>
                <td>数据获取时间</td>
                <td> {{data.report.basic.update_time}}</td>
            </tr>
            <tr>
                <td>通话详单</td>
                <td style="color: #0e90d2" onclick="getConnectDetail('{{data.applyId}}')">通话详单</td>
            </tr>
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>工作信息</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="400">
                <col width="400">
                <col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>居住地址</th>
                <th>公司名称</th>
                <th>工作地址</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{data.livingAddress}}</td>
                <td>{{data.companyName}}</td>
                <td>{{data.companyAddress}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>紧急联系人</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>关系</th>
                <th>电话</th>
                <th>归属地</th>
                <th>呼入次数</th>
                <th>呼入时长</th>
                <th>首次呼入</th>
                <th>末次呼入</th>
                <th>呼出次数</th>
                <th>呼出时长</th>
                <th>首次呼出</th>
                <th>末次呼出</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.report.urgentContact}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{name}}</td>
                <td>{{relation}}</td>
                <td>{{mobile}}</td>
                <td>{{phone_num_loc}}</td>
                <td>{{call_in_cnt}}</td>
                <td>{{call_in_len}}</td>
                <td>{{firstCallIn}}</td>
                <td>{{lastCallIn}}</td>
                <td>{{call_out_cnt}}</td>
                <td>{{call_out_len}}</td>
                <td>{{firstCallOut}}</td>
                <td>{{lastCallOut}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <%-- 用户信息检测 --%>
    <fieldset class="layui-elem-field">
        <legend>用户信息检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>查询过该用户的相关企业数量</td>
                <td>{{data.userInfoCheck.check_search_info.searched_org_cnt}}</td>
            </tr>
            <tr>
                <td>查询过该用户的相关企业类型</td>
                <td>{{data.userInfoCheck.check_search_info.searched_org_type}}</td>
            </tr>
            <tr>
                <td>身份证组合过的其他姓名</td>
                <td>{{data.userInfoCheck.check_search_info.idcard_with_other_names}}</td>
            </tr>
            <tr>
                <td>身份证组合过其他电话</td>
                <td>{{data.userInfoCheck.check_search_info.idcard_with_other_phones}}</td>
            </tr>
            <tr>
                <td>电话号码组合过其他姓名</td>
                <td>{{data.userInfoCheck.check_search_info.phone_with_other_names}}</td>
            </tr>
            <tr>
                <td>电话号码组合过其他身份证</td>
                <td>{{data.userInfoCheck.check_search_info.phone_with_other_idcards}}</td>
            </tr>
            <tr>
                <td>电话号码注册过的相关企业数量</td>
                <td>{{data.userInfoCheck.check_search_info.register_org_cnt}}</td>
            </tr>
            <tr>
                <td>电话号码注册过的相关企业类型</td>
                <td>{{data.userInfoCheck.check_search_info.register_org_type}}</td>
            </tr>
            <tr>
                <td>电话号码出现过的公开网站</td>
                <td>{{data.userInfoCheck.check_search_info.arised_open_web}}</td>
            </tr>
            <tr>
                <td>用户号码联系黑中介分数</td>
                <td>{{data.userInfoCheck.check_black_info.phone_gray_score}}</td>
            </tr>
            <tr>
                <td>直接联系人中黑名单人数</td>
                <td>{{data.userInfoCheck.check_black_info.contacts_class1_blacklist_cnt}}</td>
            </tr>
            <tr>
                <td>间接联系人中黑名单人数</td>
                <td>{{data.userInfoCheck.check_black_info.contacts_class2_blacklist_cnt}}</td>
            </tr>
            <tr>
                <td>直接联系人人数</td>
                <td>{{data.userInfoCheck.check_black_info.contacts_class1_cnt}}</td>
            </tr>
            <tr>
                <td>引起黑名单的直接联系人数量</td>
                <td>{{data.userInfoCheck.check_black_info.contacts_router_cnt}}</td>
            </tr>
            <tr>
                <td>直接联系人中引起间接黑名单占比</td>
                <td>{{data.userInfoCheck.check_black_info.contacts_router_ratio}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>

    <%-- 聚信立-用户行为检测 --%>
    <fieldset class="layui-elem-field">
        <legend>聚信立-用户行为检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>检查结果</th>
                <th>说明</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.behaviorCheckJXL}}
            <tr data-id="{{id}}">
                <td>{{check_point_cn}}</td>
                <td>{{result}}</td>
                <td>{{evidence}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <%-- 聚信立-运营商数据 --%>
    <fieldset class="layui-elem-field">
        <legend>聚信立-运营商数据</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>运营商（中文）</th>
                <th>号码</th>
                <th>归属地</th>
                <th>月份</th>
                <th>呼叫次数</th>
                <th>主叫次数</th>
                <th>主叫时间(分)</th>
                <th>被叫次数</th>
                <th>被叫时间(分)</th>
                <th>流量(MB)</th>
                <th>短信数目</th>
                <th>话费消费(元)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.cellBehavior}}
            <tr data-id="{{id}}">
                <td>{{cell_operator_zh}}</td>
                <td>{{cell_phone_num}}</td>
                <td>{{cell_loc}}</td>
                <td>{{cell_mth}}</td>
                <td>{{call_cnt}}</td>
                <td>{{call_out_cnt}}</td>
                <td>{{call_out_time}}</td>
                <td>{{call_in_cnt}}</td>
                <td>{{call_in_time}}</td>
                <td>{{net_flow}}</td>
                <td>{{sms_cnt}}</td>
                <td>{{total_amount}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <%-- 聚信立-出行数据--%>
    <fieldset class="layui-elem-field">
        <legend>聚信立-出行数据</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>出发地</th>
                <th>目的地</th>
                <th>出行时间类型</th>
                <th>出行开始时间</th>
                <th>出行结束时间</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.tripInfo}}
            <tr data-id="{{id}}">
                <td>{{trip_leave}}</td>
                <td>{{trip_dest}}</td>
                <td>{{trip_type}}</td>
                <td>{{trip_start_time}}</td>
                <td>{{trip_end_time}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <%-- 宜信阿福数据 --%>
    <fieldset class="layui-elem-field">
        <legend>宜信阿福</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>提供数据的机构代号</th>
                <th>被查询借款人姓名</th>
                <th>借款时间</th>
                <th>期数</th>
                <th>借款金额</th>
                <th>借款类型码</th>
                <th>审批结果码</th>
                <th>还款状态码</th>
                <th>逾期金额</th>
                <th>逾期情况</th>
                <th>历史逾期M3+次数</th>
                <th>历史逾期M6+次数</th>
                <th>历史逾期总次</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.zhiChengList}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{orgName}}</td>
                <td>{{name}}</td>
                <td>{{loanDate}}</td>
                <td>{{periods}}</td>
                <td>{{loanAmount}}</td>
                {{#equal loanTypeCode 21}}
                <td>信用</td>
                {{/equal}}
                {{#equal loanTypeCode 22}}
                <td>抵押</td>
                {{/equal}}
                {{#equal loanTypeCode 23}}
                <td>担保</td>
                {{/equal}}
                {{#equal approvalStatusCode 201}}
                <td>审批中</td>
                {{/equal}}
                {{#equal approvalStatusCode 202}}
                <td>批贷已放款</td>
                {{/equal}}
                {{#equal approvalStatusCode 203}}
                <td>拒贷</td>
                {{/equal}}
                {{#equal approvalStatusCode 204}}
                <td>客户放弃</td>
                {{/equal}}
                {{#equal loanStatusCode 301}}
                <td>正常</td>
                {{/equal}}
                {{#equal loanStatusCode 302}}
                <td>逾期</td>
                {{/equal}}
                {{#equal loanStatusCode 303}}
                <td>结清</td>
                {{/equal}}
                <td>{{overdueAmount}}</td>
                <td>{{overdueStatus}}</td>
                <td>{{overdueM3}}</td>
                <td>{{overdueM6}}</td>
                <td>{{overdueTotal}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>宜信阿福综合决策分</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <tbody>
            <tr>
                <td>综合评分(分值范围300-650)</td>
                <td>{{data.zhiChengDecision.compositeScore}}</td>
            </tr>
            <tr>
                <td>决策建议</td>
                {{#equal data.zhiChengDecision.decisionSuggest 0}}
                <td>数据不足，未得出决策建议</td>
                {{/equal}}
                {{#equal data.zhiChengDecision.decisionSuggest 1}}
                <td>资质良好，通过</td>
                {{/equal}}
                {{#equal data.zhiChengDecision.decisionSuggest 2}}
                <td>资质尚可，审核后无风险通过</td>
                {{/equal}}
                {{#equal data.zhiChengDecision.decisionSuggest 3}}
                <td>资质一般，正常审核</td>
                {{/equal}}
                {{#equal data.zhiChengDecision.decisionSuggest 4}}
                <td>存在一定风险，严格审核</td>
                {{/equal}}
                {{#equal data.zhiChengDecision.decisionSuggest 5}}
                <td>资质较差，拒绝</td>
                {{/equal}}
            </tr>
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>宜信阿福欺诈甄别分</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <tbody>
            <tr>
                <td>综合评分(分值范围1-100，未命中数据时不返回分数)</td>
                <td>{{data.zhiChengFraudScreen.fraudScore}}</td>
            </tr>
            <tr>
                <td>欺诈等级(从一级至五级，资质逐渐降低，风险逐渐升高)</td>
                <td>{{data.zhiChengFraudScreen.fraudLevel}}级</td>
            </tr>
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>宜信阿福社会关系网</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <tbody>
            <tr>
                <td>主叫联系人黑名单数</td>
                <td>{{data.zhiChengFraudScreenSocial.activeCallBlackCnt}}</td>
            </tr>
            <tr>
                <td>主叫联系人数</td>
                <td>{{data.zhiChengFraudScreenSocial.activeCallCnt}}</td>
            </tr>
            <tr>
                <td>主叫联系人逾期数</td>
                <td>{{data.zhiChengFraudScreenSocial.activeCallOverdueCnt}}</td>
            </tr>
            <tr>
                <td>年龄</td>
                <td>{{data.zhiChengFraudScreenSocial.age}}</td>
            </tr>
            <tr>
                <td>与法院通话次数</td>
                <td>{{data.zhiChengFraudScreenSocial.courtCallNum}}</td>
            </tr>
            <tr>
                <td>与虚拟号码通话人数</td>
                <td>{{data.zhiChengFraudScreenSocial.fictionCallCnt}}</td>
            </tr>
            <tr>
                <td>与虚拟号码通话次数</td>
                <td>{{data.zhiChengFraudScreenSocial.fictionCallNum}}</td>
            </tr>
            <tr>
                <td>与虚拟号码通话秒数</td>
                <td>{{data.zhiChengFraudScreenSocial.fictionCallSeconds}}</td>
            </tr>
            <tr>
                <td>一阶联系人黑名单个数</td>
                <td>{{data.zhiChengFraudScreenSocial.firstOrderBlackCnt}}</td>
            </tr>
            <tr>
                <td>一阶联系人黑名单占比</td>
                <td>{{data.zhiChengFraudScreenSocial.firstOrderBlackRate}}</td>
            </tr>
            <tr>
                <td>一阶联系人逾期 M3+个数</td>
                <td>{{data.zhiChengFraudScreenSocial.firstOrderM3Cnt}}</td>
            </tr>
            <tr>
                <td>一阶联系人逾期个数</td>
                <td>{{data.zhiChengFraudScreenSocial.firstOrderOverdueCnt}}</td>
            </tr>
            <tr>
                <td>一阶联系人逾期占比</td>
                <td>{{data.zhiChengFraudScreenSocial.firstOrderOverdueRate}}</td>
            </tr>
            <tr>
                <td>与律师通话总次数</td>
                <td>{{data.zhiChengFraudScreenSocial.lawyerCallNum}}</td>
            </tr>
            <tr>
                <td>与银行或同行通话总次数</td>
                <td>{{data.zhiChengFraudScreenSocial.loanCallNum}}</td>
            </tr>
            <tr>
                <td>与银行或同行通话总秒数</td>
                <td>{{data.zhiChengFraudScreenSocial.loanCallSeconds}}</td>
            </tr>
            <tr>
                <td>与银行或同行通话总人数</td>
                <td>{{data.zhiChengFraudScreenSocial.loanCallCnt}}</td>
            </tr>
            <tr>
                <td>与澳门通话人数</td>
                <td>{{data.zhiChengFraudScreenSocial.macaoCallCnt}}</td>
            </tr>
            <tr>
                <td>与澳门通话次数</td>
                <td>{{data.zhiChengFraudScreenSocial.macaoCallNum}}</td>
            </tr>
            <tr>
                <td>与澳门通话秒数</td>
                <td>{{data.zhiChengFraudScreenSocial.macaoCallSeconds}}</td>
            </tr>
            <tr>
                <td>夜间通话人数</td>
                <td>{{data.zhiChengFraudScreenSocial.nightCallCnt}}</td>
            </tr>
            <tr>
                <td>夜间通话次数</td>
                <td>{{data.zhiChengFraudScreenSocial.nightCallNum}}</td>
            </tr>
            <tr>
                <td>夜间通话秒数</td>
                <td>{{data.zhiChengFraudScreenSocial.nightCallSeconds}}</td>
            </tr>
            <tr>
                <td>异地通话人数</td>
                <td>{{data.zhiChengFraudScreenSocial.remoteCallCnt}}</td>
            </tr>
            <tr>
                <td>异地通话次数</td>
                <td>{{data.zhiChengFraudScreenSocial.remoteCallNum}}</td>
            </tr>
            <tr>
                <td>异地通话秒数</td>
                <td>{{data.zhiChengFraudScreenSocial.remoteCallSeconds}}</td>
            </tr>
            <tr>
                <td>二阶联系人黑名单个数</td>
                <td>{{data.zhiChengFraudScreenSocial.secondOrderBlackCnt}}</td>
            </tr>
            <tr>
                <td>二阶联系人逾期 M3+个数</td>
                <td>{{data.zhiChengFraudScreenSocial.secondOrderM3Cnt}}</td>
            </tr>
            <tr>
                <td>二阶联系人逾期个数</td>
                <td>{{data.zhiChengFraudScreenSocial.secondOrderOverdueCnt}}</td>
            </tr>

            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>宜信阿福风险名单</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>名单类型</th>
                <th>风险明细</th>
                <th>风险命中项</th>
                <th>风险命中内容</th>
                <th>风险最近时间</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.zhiChengFraudScreenRiskResult}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                {{#equal dataType 'CREDITEASE'}}
                <td>网贷</td>
                {{/equal}}
                {{#equal dataType 'COURT_LITIGATION'}}
                <td>法院诉讼</td>
                {{/equal}}
                {{#equal dataType 'OTHER'}}
                <td>其他</td>
                {{/equal}}
                {{#equal dataType 'NETWORK'}}
                <td>网络</td>
                {{/equal}}

                {{#equal riskDetail 'ARREARS'}}
                <td>长期拖欠</td>
                {{/equal}}
                {{#equal riskDetail 'FAKE_INFO'}}
                <td>申请信息虚假</td>
                {{/equal}}
                {{#equal riskDetail 'FAKE_DATA'}}
                <td>资料虚假</td>
                {{/equal}}
                {{#equal riskDetail 'PSEUDO'}}
                <td>伪冒</td>
                {{/equal}}
                {{#equal riskDetail 'LOSS_PAYMENT'}}
                <td>丧失还款能力</td>
                {{/equal}}
                {{#equal riskDetail 'RISK_USEAGE'}}
                <td>用途风险</td>
                {{/equal}}
                {{#equal riskDetail 'AGENCY'}}
                <td>同行中介</td>
                {{/equal}}
                {{#equal riskDetail 'COURT_PROMISE_BREAKING'}}
                <td>法院-失信</td>
                {{/equal}}
                {{#equal riskDetail 'COURT_ EXECUTED'}}
                <td>法院-被执行</td>
                {{/equal}}
                {{#equal riskDetail 'RECENT_EXIST_MULTI_APPLY_RISK'}}
                <td>近期存在多头申请风险</td>
                {{/equal}}
                {{#equal riskDetail 'MODEL_EVALUATION_LOW_QUALIFICATION'}}
                <td>模型评估低资质</td>
                {{/equal}}
                {{#equal riskDetail 'SMALL_BUSINESS_ARREARS'}}
                <td>小额业务拖欠</td>
                {{/equal}}
                {{#equal riskDetail 'FIRST_OVER_SUSPECTED_FRAUD'}}
                <td>首逾 M3</td>
                {{/equal}}
                {{#equal riskDetail 'DIFFICULTIES_OBTAIN_SMALL_BUSINESS'}}
                <td>小额业务获批困难</td>
                {{/equal}}

                {{#equal riskItemType 'ID_NO'}}
                <td>本人身份证号</td>
                {{/equal}}
                {{#equal riskItemType 'CONTACT_ID_NO'}}
                <td>联系人身份证号</td>
                {{/equal}}
                {{#equal riskItemType 'MOBILE'}}
                <td>本人手机号码</td>
                {{/equal}}
                {{#equal riskItemType 'CONTACT_MOBILE_NO'}}
                <td>联系人手机号码</td>
                {{/equal}}
                {{#equal riskItemType 'FAMILY_TEL'}}
                <td>家庭固话</td>
                {{/equal}}
                {{#equal riskItemType 'FAMILY_ADDR'}}
                <td>家庭地址</td>
                {{/equal}}
                {{#equal riskItemType 'BANK_NO'}}
                <td>银行卡号</td>
                {{/equal}}
                {{#equal riskItemType 'QQ'}}
                <td>QQ号</td>
                {{/equal}}
                {{#equal riskItemType 'EMAIL'}}
                <td>电子邮箱</td>
                {{/equal}}
                {{#equal riskItemType 'CORP_NAME'}}
                <td>单位名称</td>
                {{/equal}}
                {{#equal riskItemType 'CORP_TEL'}}
                <td>单位固话</td>
                {{/equal}}
                {{#equal riskItemType 'CORP_ADDR'}}
                <td>单位地址</td>
                {{/equal}}

                <td>{{riskItemValue}}</td>
                <td>{{riskTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>呼入前50</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>匹配通讯录联系人</th>
                <th>通话号码</th>
                <th>归属地</th>
                <th>最早呼入时间</th>
                <th>最近呼入时间</th>
                <th>呼入次数</th>
                <th>呼入时长(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.report.calInCntListV}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{firstCall}}</td>
                <td>{{lastCall}}</td>
                <td>{{terminatingCallCount}}</td>
                <td>{{terminatingTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>呼出前50</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>匹配通讯录联系人</th>
                <th>通话号码</th>
                <th>归属地</th>
                <th>最早呼出时间</th>
                <th>最近呼出时间</th>
                <th>呼出次数</th>
                <th>呼出时长(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.report.callOutCntListV}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{firstCall}}</td>
                <td>{{lastCall}}</td>
                <td>{{originatingCallCount}}</td>
                <td>{{originatingTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>用户行为检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>检查结果</th>
                <th>说明</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.behaviorCheckArrayList}}
            <tr data-id="{{id}}">
                <td>{{check_point_cn}}</td>
                <td>{{result}}</td>
                <td>{{evidence}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>运营商账单</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>账单月份</th>
                <th>总费用（元）</th>
                <th>实际缴费金额（分）</th>
                <th>套餐及固定费（元）</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.report.transactions}}
            <tr data-id="{{id}}">
                <td>{{bill_cycle}}</td>
                <td>{{total_amt}}</td>
                <td>{{pay_amt}}</td>
                <td>{{plan_amt}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


</form>
