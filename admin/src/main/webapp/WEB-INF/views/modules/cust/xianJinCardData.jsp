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
                <td> {{data.all.user_verify.operator_verify.basic.real_name}}</td>
            </tr>
            <tr>
                <td>登记身份证号</td>
                <td> {{data.all.user_verify.operator_verify.basic.idcard}}</td>
            </tr>
            <tr>
                <td>本机号码</td>
                <td> {{data.all.user_verify.operator_verify.basic.cell_phone}}</td>
            </tr>
            <tr>
                <td>入网时间</td>
                <td> {{data.all.user_verify.operator_verify.basic.reg_time}}</td>
            </tr>
            <tr>
                <td>数据获取时间</td>
                <td> {{data.all.user_verify.operator_verify.basic.update_time}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>定位信息</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="400">
                <col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>地址</th>
                <th>时间</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{data.gpsAddress1.gpsAddress}}</td>
                <td>{{data.gpsAddress1.gpsAddressTime}}</td>
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
                <th>最近一周联系次数</th>
                <th>最近一月联系次数</th>
                <th>最近三月联系次数</th>
                <th>近半年联系次数</th>
                <th>近半年联系时长(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.urgentContact}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{name}}</td>
                <td>{{relation}}</td>
                <td>{{mobile}}</td>
                <td>{{phone_num_loc}}</td>
                <td>{{contact_1w}}</td>
                <td>{{contact_1m}}</td>
                <td>{{contact_3m}}</td>
                <td>{{call_cnt}}</td>
                <td>{{call_len}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>呼入前10</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>匹配通讯录联系人</th>
                <th>通话号码</th>
                <th>归属地</th>
                <th>最近一周联系次数</th>
                <th>最近一月联系次数</th>
                <th>最近三月联系次数</th>
                <th>三个月以上联系次数</th>
                <th>通话次数/通话时间</th>
                <th>呼入次数/时间(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.calInCntListV}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{contact_1w}}</td>
                <td>{{contact_1m}}</td>
                <td>{{contact_3m}}</td>
                <td>{{contact_3m_plus}}</td>
                <td>{{call_cnt}}/{{call_len}}</td>
                <td>{{terminatingCallCount}}/{{terminatingTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>呼出前10</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>匹配通讯录联系人</th>
                <th>通话号码</th>
                <th>归属地</th>
                <th>最近一周联系次数</th>
                <th>最近一月联系次数</th>
                <th>最近三月联系次数</th>
                <th>三个月以上联系次数</th>
                <th>通话次数/通话时间</th>
                <th>呼出次数/时间(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.callOutCntListV}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{contact_1w}}</td>
                <td>{{contact_1m}}</td>
                <td>{{contact_3m}}</td>
                <td>{{contact_3m_plus}}</td>
                <td>{{call_cnt}}/{{call_len}}</td>
                <td>{{originatingCallCount}}/{{originatingTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>

    <%--<fieldset class="layui-elem-field">--%>
        <%--<legend>反欺诈云分析</legend>--%>
        <%--<table class="layui-table" lay-even="" lay-skin="row">--%>
            <%--<colgroup>--%>
                <%--<col width="400">--%>
                <%--<col width="400">--%>
            <%--</colgroup>--%>
            <%--<thead>--%>
            <%--<tr>--%>
                <%--<th>检查项目</th>--%>
                <%--<th>时间维度</th>--%>
                <%--<th>结果</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--{{#each data.baiRongApply}}--%>
            <%--<tr>--%>
                <%--<td>多头申请</td>--%>
                <%--<td>{{ruleName}}</td>--%>
                <%--<td>{{value}}</td>--%>
            <%--</tr>--%>
            <%--{{/each}}--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</fieldset>--%>

    <fieldset class="layui-elem-field">
        <legend>用户行为检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>检查结果</th>
                <th>证据</th>
                <th>建议决策</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.all.user_verify.operator_report_verify.behavior_check}}
            <tr data-id="{{id}}">
                <td>{{check_point_cn}}</td>
                <td>{{result}}</td>
                <td>{{evidence}}</td>
                <td>
                    {{#equal score 0}}
                    <span style="color:#000800;">无数据</span>
                    {{/equal}}
                    {{#equal score 1}}
                    <span style="color:#40b725;">通过</span>
                    {{/equal}}
                    {{#equal score 2}}
                    <span style="color:red;">不通过</span>
                    {{/equal}}
                </td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


    <fieldset class="layui-elem-field">
        <legend>联系人区域数量排名</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>地区名称</th>
                <th>号码数量</th>
                <th>呼入次数</th>
                <th>呼出次数</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.contactRegionsV}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{region_loc}}</td>
                <td>{{region_uniq_num_cnt}}</td>
                <td>{{region_call_in_cnt}}</td>
                <td>{{region_call_out_cnt}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>


</form>
