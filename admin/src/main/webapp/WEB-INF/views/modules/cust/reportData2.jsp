<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
   
    <%--资信云报告2--%>
    <fieldset class="layui-elem-field">
        <legend>个人信息概况</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="100">
            </colgroup>
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>姓名</td>
                <td>{{data.petitioner.name}}<span style="padding-left:20px;">{{data.petitioner.gender}}/{{data.petitioner.age}}/{{data.petitioner.birthAddress}}</span></td>
            </tr>
            <tr>
                <td>身份证</td>
                <td>{{data.petitioner.certNo}}</td>
            </tr>
            <tr>
                <td>手机号</td>
                <td>{{data.petitioner.mobile}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>运营商</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="200">
            </colgroup>
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>手机号</td>
                <td>{{data.mnoBaseInfo.mobile}}/{{data.mnoBaseInfo.monType}}/{{data.mnoBaseInfo.belongTo}}
                	{{#equal data.passRealName true}}
                	<span style="color:green;padding-left:200px;">已实名认证，{{data.crossValidationOpenTime}}</span>
                	{{/equal}}
                	{{#equal data.passRealName false}}
                	<span style="color:red;padding-left:200px;">未实名认证，{{data.crossValidationOpenTime}}</span>
                	{{/equal}}
                </td>
            </tr>
            <tr>
                <td>实名信息</td>
                <td>{{data.mnoBaseInfo.boundName}}
                	{{#equal data.equalToPetitioner true}}
                	<span style="color:green;padding-left:342px;">匹配成功(与用户提供的身份证、姓名匹配成功)</span>
                	{{/equal}}
                	{{#equal data.equalToPetitioner false}}
                	<span style="color:red;padding-left:342px;">匹配失败(与用户提供的身份证、姓名匹配不成功)</span>
                	{{/equal}}
                </td>
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
                <th>最近联系时间</th>
                <th>最早联系时间</th>
                <th>近半年联系次数</th>
                <th>近半年联系时长(秒)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.emergencyContacts}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{name}}</td>
                <td>{{relation}}</td>
                <td>{{mobile}}</td>
                <td>{{belongTo}}</td>
                <td>{{latestConnectTime}}</td>
                <td>{{firstConnectTime}}</td>
                <td>{{connectCount}}</td>
                <td>{{connectDuration}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>反欺诈云分析</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="400">
            	<col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>时间跨度</th>
                <th>结果</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>多头申请</td>
                <td>90天之内</td>
                <td>{{data.bqsAntiFraudCloud.partnerCount}}</td>
            </tr>
            <tr>
                <td>手机号关联身份证个数</td>
                <td>90天之内</td>
                <td>{{data.bqsAntiFraudCloud.idcCount}}</td>
            </tr>
            <tr>
                <td>身份证关联手机号个数</td>
                <td>90天之内</td>
                <td>{{data.bqsAntiFraudCloud.phoneCount}}</td>
            </tr>
            <tr>
                <td>手机号星网模型大小</td>
                <td>180天之内</td>
                <td>{{data.bqsAntiFraudCloud.starnetCount}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>常用行为检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="400">
            	<col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>结果</th>
                <th>证据</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{data.crossValidation.callActiveArea.inspectionItems}}</td>
                <td>{{data.crossValidation.callActiveArea.result}}</td>
                <td>{{data.crossValidation.callActiveArea.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.contactsActiveArea.inspectionItems}}</td>
                <td>{{data.crossValidation.contactsActiveArea.result}}</td>
                <td>{{data.crossValidation.contactsActiveArea.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.contactsSize.inspectionItems}}</td>
                <td>{{data.crossValidation.contactsSize.result}}</td>
                <td>{{data.crossValidation.contactsSize.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.exchangeCallMobileCount.inspectionItems}}</td>
                <td>{{data.crossValidation.exchangeCallMobileCount.result}}</td>
                <td>{{data.crossValidation.exchangeCallMobileCount.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.contactsActiveDegree.inspectionItems}}</td>
                <td>{{data.crossValidation.contactsActiveDegree.result}}</td>
                <td>{{data.crossValidation.contactsActiveDegree.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.notCallAndSmsDayCount.inspectionItems}}</td>
                <td>{{data.crossValidation.notCallAndSmsDayCount.result}}</td>
                <td>{{data.crossValidation.notCallAndSmsDayCount.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.numberUsedLong.inspectionItems}}</td>
                <td>{{data.crossValidation.numberUsedLong.result}}</td>
                <td>{{data.crossValidation.numberUsedLong.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.nightCallCount.inspectionItems}}</td>
                <td>{{data.crossValidation.nightCallCount.result}}</td>
                <td>{{data.crossValidation.nightCallCount.evidence}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>特殊通话行为检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="400">
            	<col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>结果</th>
                <th>证据</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.crossValidation.exceptionalConnectInfoList}}
            <tr data-id="{{id}}">
                <td>{{inspectionItems}}</td>
                <td>{{result}}</td>
                <td>{{evidence}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>运营商数据检测</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
            	<col width="400">
            	<col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>检查项目</th>
                <th>结果</th>
                <th>证据</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{data.crossValidation.terminatingCallCount.inspectionItems}}</td>
                <td>{{data.crossValidation.terminatingCallCount.result}}</td>
                <td>{{data.crossValidation.terminatingCallCount.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.originatingMobileCount.inspectionItems}}</td>
                <td>{{data.crossValidation.originatingMobileCount.result}}</td>
                <td>{{data.crossValidation.originatingMobileCount.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.callDurationLess1minSize.inspectionItems}}</td>
                <td>{{data.crossValidation.callDurationLess1minSize.result}}</td>
                <td>{{data.crossValidation.callDurationLess1minSize.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.callDuration5to10minSize.inspectionItems}}</td>
                <td>{{data.crossValidation.callDuration5to10minSize.result}}</td>
                <td>{{data.crossValidation.callDuration5to10minSize.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.callDurationBigger10minSize.inspectionItems}}</td>
                <td>{{data.crossValidation.callDurationBigger10minSize.result}}</td>
                <td>{{data.crossValidation.callDurationBigger10minSize.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.terminatingCallDurationMax.inspectionItems}}</td>
                <td>{{data.crossValidation.terminatingCallDurationMax.result}}</td>
                <td>{{data.crossValidation.terminatingCallDurationMax.evidence}}</td>
            </tr>
            <tr>
                <td>{{data.crossValidation.originatingCallDurationMax.inspectionItems}}</td>
                <td>{{data.crossValidation.originatingCallDurationMax.result}}</td>
                <td>{{data.crossValidation.originatingCallDurationMax.evidence}}</td>
            </tr>
            </tbody>
        </table>
    </fieldset>
</form>
