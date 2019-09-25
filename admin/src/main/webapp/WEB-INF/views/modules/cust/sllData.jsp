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
                <td>本机号码</td>
                <td> {{data.report.basic.cell_phone}}</td>
            </tr>
            <tr>
                <td>入网时间</td>
                <td> {{data.report.basic.reg_time}}</td>
            </tr>
            <tr>
                <td>数据获取时间</td>
                <td> {{data.report.basic.update_time}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>


    <%--<fieldset class="layui-elem-field">--%>
        <%--<legend>定位信息</legend>--%>
        <%--<table class="layui-table" lay-even="" lay-skin="row">--%>
            <%--<colgroup>--%>
                <%--<col width="400">--%>
                <%--<col width="400">--%>
            <%--</colgroup>--%>
            <%--<thead>--%>
            <%--<tr>--%>
                <%--<th>地址</th>--%>
                <%--<th>时间</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--<tr>--%>
                <%--<td>{{data.gpsAddress1.gpsAddress}}</td>--%>
                <%--<td>{{data.gpsAddress1.gpsAddressTime}}</td>--%>
            <%--</tr>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</fieldset>--%>


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
                <th>说明</th>
                <%--<th>建议决策</th>--%>
            </tr>
            </thead>
            <tbody>
            {{#each data.behaviorCheckArrayList}}
            <tr data-id="{{id}}">
                <td>{{check_point_cn}}</td>
                <td>{{result}}</td>
                <td>{{evidence}}</td>
                <%--<td>--%>
                    <%--{{#equal score 0}}--%>
                    <%--<span style="color:#000800;">无数据</span>--%>
                    <%--{{/equal}}--%>
                    <%--{{#equal score 1}}--%>
                    <%--<span style="color:#40b725;">通过</span>--%>
                    <%--{{/equal}}--%>
                    <%--{{#equal score 2}}--%>
                    <%--<span style="color:red;">不通过</span>--%>
                    <%--{{/equal}}--%>
                <%--</td>--%>
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
                <th>实际缴费金额（元）</th>
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
