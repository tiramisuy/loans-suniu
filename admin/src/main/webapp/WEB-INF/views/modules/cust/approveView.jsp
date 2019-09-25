<%@ page contentType="text/html;charset=UTF-8" %>
<%--借款信息start--%>
    <fieldset class="layui-elem-field">
    <legend>当前申请订单</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
        <colgroup>
            <col width="200">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="200">
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>订单号</th>
            <th>订单状态</th>
            <th>借款产品</th>
            <th>借款本金（元）</th>
            <th>借款期限</th>
            <th>借款利率（年化）</th>
            <th>服务费</th>
            <th>借款用途</th>
            <th>还款方式</th>
            <th>还款期数</th>
            <th>申请时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{#each repayItemDetailList}}
        {{#equal status 171}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}
        {{#equal status 220}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}
        {{#equal status 223}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}

        {{/each}}
        </tbody>
    </table>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>历史借款记录</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
        <colgroup>
            <col width="200">
            <col width="200">
            <col width="150">
            <col width="100">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="200">
            <col width="200">
            <col width="200">
            <col width="200">
            <col width="200">
            <col width="200">
            <col width="150">
            <col width="300">
        </colgroup>
        <thead>
        <tr>
            <th>订单号</th>
            <th>合同号</th>
            <th>订单状态</th>
            <th>是否逾期</th>
            <th>借款产品</th>
            <th>借款本金（元）</th>
            <th>借款期限</th>
            <th>借款利率（年化）</th>
            <th>借款用途</th>
            <th>借款日期</th>
            <th>到期日期</th>
            <th>还款方式</th>
            <th>还款期数</th>
            <th>最高逾期天数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{#each repayItemDetailList}}
        {{#notEqual status 171 220}}
        	{{#notEqual status 171 223}}
				<tr>
		            <td><a class="text-blue"
		                   href="{{../../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
		                   target="_blank">{{id}}</a></td>
		            <td>{{contNo}}</td>
		            <td>{{statusStr}}</td>
		            <td>
		                {{#equal number 0}}
		                否
		                {{else}}
		                是
		                {{/equal}}
		            </td>
		            <td>{{productName}}</td>
		            <td>{{approveAmt}}</td>
		            <td>{{approveTerm}}天</td>
		            <td>{{actualRate}}%</td>
		            <td>{{purpose}}</td>
		            <td>{{formatDate startDate 'yyyy-MM-dd HH:mm:ss'}}</td>
		            <td>{{formatDate endDate 'yyyy-MM-dd HH:mm:ss'}}</td>
		            <td>{{repayMethod}}</td>
		            <td>{{term}}</td>
		            <td>{{number}}</td>
		            <td><a href="javascript:void(0)" class="text-blue"
		                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')" style="padding-left:10px;">详情</a>
		            </td>
		        </tr>
        	 {{/notEqual}}
        {{/notEqual}}
        {{/each}}
        </tbody>
    </table>
</fieldset>
<%--借款信息end--%>

<%--查重start--%>
<fieldset class="layui-elem-field">
    <legend>查重</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
        <colgroup>
            <col width="100">
            <col width="200">
            <col width="200">
            <col width="100">
            <col width="200">
            <col width="200">
            <col width="100">
            <col width="200">
            <col width="200">
        </colgroup>
        <thead>
        <tr>
            <th>关系</th>
            <th>姓名</th>
            <th>手机号码</th>
            <th></th>
            <th>订单号</th>
            <th>进件时间</th>
            <th>关系</th>
            <th>姓名</th>
            <th>手机号码</th>
        </tr>
        </thead>
        <tbody>
        {{#each contactHistoryList}}
        <tr>
            <td>{{sourceRelationship}}</td>
            <td>{{sourceUserName}}</td>
            <td>{{sourceMobile}}</td>
            <td class="red">匹配到---></td>
            <td><a class="text-blue" href="{{../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{applyId}}&flag=0" target="_blank">{{applyId}}</a></td>
            <td>{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{relationshipStr}}</td>
            <td>{{name}}</td>
            <td>{{mobile}}</td>
        </tr>
        {{/each}}
        </tbody>
    </table>
</fieldset>
<%--查重end--%>