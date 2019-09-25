<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">

    <fieldset class="layui-elem-field">
        <legend>个人小额信贷交易征信报告</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            </thead>
            <tbody>
                <tr>
                    <td>姓名</td>
                    <td>{{data.title.customerName}}</td>
                </tr>
                <tr>
                    <td>身份证号</td>
                    <td> {{data.title.paperNumber}}</td>
                </tr>
                <tr>
                    <td>报告编号</td>
                    <td> {{data.title.queryNumber}}</td>
                </tr>
                <tr>
                    <td>报告生成时间</td>
                    <td> {{data.title.reportTime}}</td>
                </tr>
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>信贷交易统计概况</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
                <tr>
                    <th colspan="5" style="text-align: center">借款申请记录</th>
                    <th colspan="3" style="text-align: center">正常还款账户</th>
                    <th colspan="3" style="text-align: center">异常还款账户</th>
                    <th rowspan="2" style="text-align: center">行业不良记录</th>
                    <th colspan="3" style="text-align: center">查询记录</th>
                </tr>
            </thead>
            <tbody align="center" valign="center">
                <tr>
                    <td>待审核</td>
                    <td>审批通过</td>
                    <td>审批拒绝</td>
                    <td>客户取消</td>
                    <td>小计</td>
                    <td>未结清</td>
                    <td>已结清</td>
                    <td>小计</td>
                    <td>未结清</td>
                    <td>已结清</td>
                    <td>小计</td>
                    <td>小计</td>
                    <td>3个月内</td>
                    <td>6个月内</td>
                    <td>总数</td>
                </tr>
                <tr>
                    <td>{{data.title.applyingCount}}</td>
                    <td>{{data.title.applyPassedCount}}</td>
                    <td>{{data.title.applyRejectCount}}</td>
                    <td>{{data.title.applyCancelCount}}</td>
                    <td>{{data.title.applyTotalCount}}</td>
                    <td>{{data.title.wjqCount}}</td>
                    <td>{{data.title.jqCount}}</td>
                    <td>{{data.title.totalCount}}</td>
                    <td>{{data.title.ewjqCount}}</td>
                    <td>{{data.title.ejqCount}}</td>
                    <td>{{data.title.etotalCount}}</td>
                    <td>{{data.title.blackCount}}</td>
                    <td rowspan="2">{{data.title.queryCount3Month}}</td>
                    <td rowspan="2">{{data.title.queryCount6Month}}</td>
                    <td rowspan="2">{{data.title.queryCount2Year}}</td>
                </tr>
                <tr>
                    <td>{{data.title.applyingSumMoney}}</td>
                    <td>{{data.title.applyPassedSumMoney}}</td>
                    <td>{{data.title.applyRejectSumMoney}}</td>
                    <td>{{data.title.applyCancelSumMoney}}</td>
                    <td>{{data.title.applyTotalSumMoney}}</td>
                    <td>{{data.title.wjqSumMoney}}</td>
                    <td>{{data.title.jqSumMoney}}</td>
                    <td>{{data.title.totalSumMoney}}</td>
                    <td>{{data.title.ewjqSumMoney}}</td>
                    <td>{{data.title.ejqSumMoney}}</td>
                    <td>{{data.title.etotalSumMoney}}</td>
                    <td>{{data.title.blackSumMoney}}</td>
                </tr>
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>借款申请记录明细</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
                <tr>
                    <th style="text-align: center">申请日期</th>
                    <th style="text-align: center">会员类型</th>
                    <th style="text-align: center">申请地点</th>
                    <th style="text-align: center">申请金额</th>
                    <th style="text-align: center">审批结果</th>
                    <th style="text-align: center">备注</th>
                </tr>
            </thead>
            <tbody align="center" valign="center">
                {{#each data.applyDetail}}
                <tr>
                    <td>{{applyDate}}</td>
                    <td>{{#equal memberType 01}}
                            P2P企业
                        {{/equal}}
                        {{#equal memberType 02}}
                            小贷公司
                        {{/equal}}
                        {{#equal memberType 03}}
                            担保公司
                        {{/equal}}
                        {{#equal memberType 04}}
                            财务公司
                        {{/equal}}
                        {{#equal memberType 05}}
                            消费金融
                        {{/equal}}
                        {{#equal memberType 06}}
                            典当公司
                        {{/equal}}
                        {{#equal memberType 07}}
                            民间借贷
                        {{/equal}}
                        {{#equal memberType 08}}
                            保险机构
                        {{/equal}}
                        {{#equal memberType 09}}
                            融资租赁
                        {{/equal}}
                        {{#equal memberType 99}}
                            其他
                        {{/equal}}
                    </td>
                    <td>{{creditAddress}}</td>
                    <td>{{loanMoney}}</td>
                    <td>
                        {{#equal applyResult 01}}
                        通过
                        {{/equal}}
                        {{#equal applyResult 02}}
                        拒绝
                        {{/equal}}
                        {{#equal applyResult 04}}
                        重新审批
                        {{/equal}}
                        {{#equal applyResult 05}}
                        客户取消
                        {{/equal}}
                    </td>
                    <td>{{remark}}</td>
                </tr>
                {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>正常还款账户明细</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th style="text-align: center">借款编号</th>
                <th style="text-align: center">会员类型</th>
                <th style="text-align: center">借款日期</th>
                <th style="text-align: center">到期日期</th>
                <th style="text-align: center">借款地点</th>
                <th style="text-align: center">担保方式</th>
                <th style="text-align: center">合同金额</th>
                <th style="text-align: center">还款期数</th>
                <th style="text-align: center">备注</th>
            </tr>
            </thead>
            <tbody align="center" valign="center">
            {{#each data.normalCreditDetails}}
            <tr>
                <td>{{num}}</td>
                <td>{{#equal memberType 01}}
                    P2P企业
                    {{/equal}}
                    {{#equal memberType 02}}
                    小贷公司
                    {{/equal}}
                    {{#equal memberType 03}}
                    担保公司
                    {{/equal}}
                    {{#equal memberType 04}}
                    财务公司
                    {{/equal}}
                    {{#equal memberType 05}}
                    消费金融
                    {{/equal}}
                    {{#equal memberType 06}}
                    典当公司
                    {{/equal}}
                    {{#equal memberType 07}}
                    民间借贷
                    {{/equal}}
                    {{#equal memberType 08}}
                    保险机构
                    {{/equal}}
                    {{#equal memberType 09}}
                    融资租赁
                    {{/equal}}
                    {{#equal memberType 99}}
                    其他
                    {{/equal}}
                </td>
                <td>{{creditStartDate}}</td>
                <td>{{creditEndDate}}</td>
                <td>{{creditAddress}}</td>
                <td>
                    {{#equal assureType A}}
                    抵押
                    {{/equal}}
                    {{#equal assureType B}}
                    质押
                    {{/equal}}
                    {{#equal assureType C}}
                    担保
                    {{/equal}}
                    {{#equal assureType D}}
                    信用
                    {{/equal}}
                    {{#equal assureType E}}
                    保证
                    {{/equal}}
                    {{#equal assureType Y}}
                    其他
                    {{/equal}}</td>
                <td>{{loanMoney}}</td>
                <td>{{loanPeriods}}</td>
                <td>{{remark}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>异常还款记录明细</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th style="text-align: center">借款编号</th>
                <th style="text-align: center">会员类型</th>
                <th style="text-align: center">借款日期</th>
                <th style="text-align: center">到期日期</th>
                <th style="text-align: center">担保方式</th>
                <th style="text-align: center">合同金额</th>
                <th style="text-align: center">还款期数</th>
            </tr>
            </thead>
            <tbody align="center" valign="center">
            {{#each data.abnormalCreditDetails}}
                <tr>
                    <td>{{num}}</td>
                    <td>{{#equal memberType 01}}
                        P2P企业
                        {{/equal}}
                        {{#equal memberType 02}}
                        小贷公司
                        {{/equal}}
                        {{#equal memberType 03}}
                        担保公司
                        {{/equal}}
                        {{#equal memberType 04}}
                        财务公司
                        {{/equal}}
                        {{#equal memberType 05}}
                        消费金融
                        {{/equal}}
                        {{#equal memberType 06}}
                        典当公司
                        {{/equal}}
                        {{#equal memberType 07}}
                        民间借贷
                        {{/equal}}
                        {{#equal memberType 08}}
                        保险机构
                        {{/equal}}
                        {{#equal memberType 09}}
                        融资租赁
                        {{/equal}}
                        {{#equal memberType 99}}
                        其他
                        {{/equal}}
                    </td>
                    <td>{{creditStartDate}}</td>
                    <td>{{creditEndDate}}</td>
                    <td>
                        {{#equal assureType 'A'}}
                        抵押
                        {{/equal}}
                        {{#equal assureType 'B'}}
                        质押
                        {{/equal}}
                        {{#equal assureType 'C'}}
                        担保
                        {{/equal}}
                        {{#equal assureType 'D'}}
                        信用
                        {{/equal}}
                        {{#equal assureType 'E'}}
                        保证
                        {{/equal}}
                        {{#equal assureType 'Y'}}
                        其他
                        {{/equal}}</td>
                    <td>{{loanMoney}}</td>
                    <td>{{loanPeriods}}</td>
                </tr>
            {{/each}}
            </tbody>
        </table>
        <legend>逾期记录明细</legend>
        <table class="layui-table right" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th style="text-align: center">借款编号</th>
                <th style="text-align: center">逾期日期</th>
                <th style="text-align: center">逾期时长</th>
                <th style="text-align: center">逾期金额</th>
                <th style="text-align: center">状态</th>
                <th style="text-align: center">更新日期</th>
                <th style="text-align: center">备注</th>
            </tr>
            </thead>
            <tbody align="center" valign="center">
            {{#each data.abnormalCreditDetails}}
                {{#each overdues}}
                    <tr>
                        <td>{{../num}}</td>
                        <td>{{checkOverdueDate}}</td>
                        <td>{{#equal overdueDays 01}}
                            小于30天
                            {{/equal}}
                            {{#equal overdueDays 02}}
                            大于等于30天且小于60天
                            {{/equal}}
                            {{#equal overdueDays 03}}
                            大于等于60天且小于90天
                            {{/equal}}
                            {{#equal overdueDays 04}}
                            大于等于90天且小于120天
                            {{/equal}}
                            {{#equal overdueDays 05}}
                            大于等于120天且小于150天
                            {{/equal}}
                            {{#equal overdueDays 06}}
                            大于等于150天且小于180天
                            {{/equal}}
                            {{#equal overdueDays 07}}
                            大于等于180天且小于210天
                            {{/equal}}
                            {{#equal overdueDays 08}}
                            大于等于210天且小于240天
                            {{/equal}}
                            {{#equal overdueDays 09}}
                            大于等于240天且小于270天
                            {{/equal}}
                            {{#equal overdueDays 10}}
                            大于等于270天且小于300天
                            {{/equal}}
                            {{#equal overdueDays 11}}
                            大于等于300天且小于330天
                            {{/equal}}
                            {{#equal overdueDays 12}}
                            大于等于330天且小于360天
                            {{/equal}}
                            {{#equal overdueDays 13}}
                            大于等于360天
                            {{/equal}}
                            {{#equal overdueDays 99}}
                            至今
                            {{/equal}}</td>
                        <td>{{nbMoney}}</td>
                        <td>
                            {{#equal overdueState 01}}
                            逾期中
                            {{/equal}}
                            {{#equal overdueState 03}}
                            恢复正常
                            {{/equal}}
                            {{#equal overdueState 04}}
                            核销
                            {{/equal}}
                            {{#equal overdueState 05}}
                            结清
                            {{/equal}}</td>
                        <td>{{operTime}}</td>
                        <td>{{remark}}</td>
                    </tr>
                {{/each}}
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>查询记录明细</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th style="text-align: center">查询日期</th>
                <th style="text-align: center">会员类型</th>
                <th style="text-align: center">查询类别</th>
                <th style="text-align: center">备注</th>
            </tr>
            </thead>
            <tbody align="center" valign="center">
            {{#each data.queryDetails}}
            <tr>
                <td>{{queryDate}}</td>
                <td>{{#equal memberType 01}}
                    P2P企业
                    {{/equal}}
                    {{#equal memberType 02}}
                    小贷公司
                    {{/equal}}
                    {{#equal memberType 03}}
                    担保公司
                    {{/equal}}
                    {{#equal memberType 04}}
                    财务公司
                    {{/equal}}
                    {{#equal memberType 05}}
                    消费金融
                    {{/equal}}
                    {{#equal memberType 06}}
                    典当公司
                    {{/equal}}
                    {{#equal memberType 07}}
                    民间借贷
                    {{/equal}}
                    {{#equal memberType 08}}
                    保险机构
                    {{/equal}}
                    {{#equal memberType 09}}
                    融资租赁
                    {{/equal}}
                    {{#equal memberType 99}}
                    其他
                    {{/equal}}
                </td>
                <td>{{queryType}}</td>
                <td>{{remark}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>行业不良信息</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th style="text-align: center">会员类型</th>
                <th style="text-align: center">报送/公开日期</th>
                <th style="text-align: center">最近逾期开始日期</th>
                <th style="text-align: center">借款地点</th>
                <th style="text-align: center">欠款总额</th>
                <th style="text-align: center">电话</th>
                <th style="text-align: center">邮箱</th>
                <th style="text-align: center">户籍地址</th>
                <th style="text-align: center">现居地址</th>
                <th style="text-align: center">状态</th>
                <th style="text-align: center">信息来源</th>
            </tr>
            </thead>
            <tbody align="center" valign="center">
            {{#each data.blackDatas}}
            <tr>
                <td>{{#equal memberType 01}}
                    P2P企业
                    {{/equal}}
                    {{#equal memberType 02}}
                    小贷公司
                    {{/equal}}
                    {{#equal memberType 03}}
                    担保公司
                    {{/equal}}
                    {{#equal memberType 04}}
                    财务公司
                    {{/equal}}
                    {{#equal memberType 05}}
                    消费金融
                    {{/equal}}
                    {{#equal memberType 06}}
                    典当公司
                    {{/equal}}
                    {{#equal memberType 07}}
                    民间借贷
                    {{/equal}}
                    {{#equal memberType 08}}
                    保险机构
                    {{/equal}}
                    {{#equal memberType 09}}
                    融资租赁
                    {{/equal}}
                    {{#equal memberType 99}}
                    其他
                    {{/equal}}
                </td>
                <td>{{createDate}}</td>
                <td>{{lastOverdueDate}}</td>
                <td>{{creditAddress}}</td>
                <td>{{arrears}}</td>
                <td>{{phone}}</td>
                <td>{{email}}</td>
                <td>{{residenceAddress}}</td>
                <td>{{currentAddress}}</td>
                <td>{{status}}</td>
                <td>
                    {{#equal source 'A'}}
                    会员机构
                    {{/equal}}
                    {{#equal source 'B'}}
                    合作机构
                    {{/equal}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <fieldset class="layui-elem-field">
        <legend>风险速查报告</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>被一家机构加入MSP黑名单信息</td>
                <td>{{#equal data.quickRisk.riskRule.A001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.A001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.A001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>被两家以上加入MSP黑名单</td>
                <td>{{#equal data.quickRisk.riskRule.A002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.A002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.A002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在申请信息</td>
                <td>{{#equal data.quickRisk.riskRule.B001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>近6个月申请次数达到3次或以上</td>
                <td>{{#equal data.quickRisk.riskRule.B002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>近6个月申请机构数达到3或以上</td>
                <td>{{#equal data.quickRisk.riskRule.B003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在申请拒绝信息</td>
                <td>{{#equal data.quickRisk.riskRule.B004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>近6个月内存在2次或以上申请拒绝信息</td>
                <td>{{#equal data.quickRisk.riskRule.B005 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B005 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.B005 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在借贷合同</td>
                <td>{{#equal data.quickRisk.riskRule.C001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在2笔或以上借贷合同</td>
                <td>{{#equal data.quickRisk.riskRule.C002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在未到期借贷合同</td>
                <td>{{#equal data.quickRisk.riskRule.C003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.C003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在逾期信息</td>
                <td>{{#equal data.quickRisk.riskRule.D001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在单笔合同2次或以上逾期信息</td>
                <td>{{#equal data.quickRisk.riskRule.D002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在2笔或以上不同合同逾期信息</td>
                <td>{{#equal data.quickRisk.riskRule.D003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在M3逾期信息</td>
                <td>{{#equal data.quickRisk.riskRule.D004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.D004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>手机号与历史记录不一致</td>
                <td>{{#equal data.quickRisk.riskRule.E001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>邮箱与历史记录不一致</td>
                <td>{{#equal data.quickRisk.riskRule.E002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>工作单位与半年内历史记录不一致</td>
                <td>{{#equal data.quickRisk.riskRule.E003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>常用住址与半年内历史记录不一致</td>
                <td>{{#equal data.quickRisk.riskRule.E004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.E004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>手机号关联黑名单逾期等风险信息</td>
                <td>{{#equal data.quickRisk.riskRule.F001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>邮箱关联黑名单逾期等风险信息</td>
                <td>{{#equal data.quickRisk.riskRule.F002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>工作单位关联黑名单逾期等风险信息</td>
                <td>{{#equal data.quickRisk.riskRule.F003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>常用住址关联黑名单逾期等风险信息</td>
                <td>{{#equal data.quickRisk.riskRule.F004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.F004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>身份证号关联其他身份验证一致申请人</td>
                <td>{{#equal data.quickRisk.riskRule.G001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>手机号与其他申请人共用</td>
                <td>{{#equal data.quickRisk.riskRule.G002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>邮箱与其他申请人共用</td>
                <td>{{#equal data.quickRisk.riskRule.G003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>半年内同一工作单位多人申请借贷</td>
                <td>{{#equal data.quickRisk.riskRule.G004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>半年内同一常用住址多人申请借贷</td>
                <td>{{#equal data.quickRisk.riskRule.G005 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G005 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.G005 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在司法失信信息</td>
                <td>{{#equal data.quickRisk.riskRule.H001 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H001 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H001 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在2条或以上司法失信信息</td>
                <td>{{#equal data.quickRisk.riskRule.H002 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H002 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H002 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在司法执行信息</td>
                <td>{{#equal data.quickRisk.riskRule.H003 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H003 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H003 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            <tr>
                <td>存在2条或以上司法执行信息</td>
                <td>{{#equal data.quickRisk.riskRule.H004 1}}
                    命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H004 0}}
                    没有命中
                    {{/equal}}
                    {{#equal data.quickRisk.riskRule.H004 '-'}}
                    未做命中规则比较
                    {{/equal}}
                </td>
            </tr>
            </tbody>
        </table>
    </fieldset>
</form>
