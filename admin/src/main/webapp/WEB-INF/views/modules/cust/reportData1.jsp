<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
   
    <%--资信云报告1--%>
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
                <th>第一次联系时间</th>
                <th>最近联系时间</th>
                <th>被叫次数/时间(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.ccmList1}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{beginTime}}</td>
                <td>{{endTime}}</td>
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
                <th>第一次联系时间</th>
                <th>最近联系时间</th>
                <th>主叫次数/时间(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.ccmList2}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{contactName}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{beginTime}}</td>
                <td>{{endTime}}</td>
                <td>{{originatingCallCount}}/{{originatingTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>本人通话活动地区</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>地区</th>
                <th>通话次数</th>
                <th>呼入次数/呼入时间(秒)</th>
                <th>呼出次数/呼出时间(秒)</th>
                <th>呼入次数占比</th>
                <th>呼出次数占比</th>
                <th>呼入时间占比</th>
                <th>呼出时间占比</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.mnoCommonlyConnectAreas}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{area}}</td>
                <td>{{numberCount}}</td>
                <td>{{terminatingCallCount}}/{{terminatingCallTime}}</td>
                <td>{{originatingCallCount}}/{{originatingCallTime}}</td>
                <td>{{callInCountPercentage}}</td>
                <td>{{callOutCountPercentage}}</td>
                <td>{{callInTimePercentage}}</td>
                <td>{{callOutTimePercentage}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>联系人通话活动地区</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>地区</th>
                <th>通话次数</th>
                <th>呼入次数/呼入时间(秒)</th>
                <th>呼出次数/呼出时间(秒)</th>
                <th>呼入次数占比</th>
                <th>呼出次数占比</th>
                <th>呼入时间占比</th>
                <th>呼出时间占比</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.mnoContactsCommonlyConnectAreas}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{area}}</td>
                <td>{{numberCount}}</td>
                <td>{{terminatingCallCount}}/{{terminatingCallTime}}</td>
                <td>{{originatingCallCount}}/{{originatingCallTime}}</td>
                <td>{{callInCountPercentage}}</td>
                <td>{{callOutCountPercentage}}</td>
                <td>{{callInTimePercentage}}</td>
                <td>{{callOutTimePercentage}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>分时间段统计</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            </colgroup>
            <thead>
            <tr>
                <th>时间段</th>
                <th>活动次数</th>
                <th>占比</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.mnoPeriodUsedInfos}}
            <tr data-id="{{id}}">
                <td>{{periodType}}</td>
                <td>{{count}}</td>
                <td>{{percentage}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
</form>
