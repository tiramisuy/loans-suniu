<%@ page contentType="text/html;charset=UTF-8" %>

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

        {{#each data.list}}
        <tr>
            <td>{{sourceRelationship}}</td>
            <td>{{sourceUserName}}</td>
            <td>{{sourceMobile}}</td>
            <td class="red">匹配到---></td>
<%--            {{#equal ../data/flag '0'}}
            <td>{{applyId}}</td>
            {{else}}
            {{/equal}}--%>
            <td><a class="text-blue" href="{{../data/ctx}}/loan/apply/checkApplyFrom?id={{userId}}&sign=detail&applyId={{applyId}}&flag=0" target="_blank">{{applyId}}</a></td>
            <td>{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{relationshipStr}}</td>
            <td>{{name}}</td>
            <td>{{mobile}}</td>
        </tr>
        {{/each}}
        </tbody>
    </table>


