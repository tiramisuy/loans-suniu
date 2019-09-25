<%@ page contentType="text/html;charset=UTF-8" %>
<table class="table">
    <thead>
    <tr>
        <th>操作时间</th>
        <th>分配前催收人</th>
        <th>分配后催收人</th>
        <th>操作人</th>
        <th>备注</th>

    </tr>
    </thead>
    <tbody>
    {{#each list}}
    <tr>
        <td>{{formatDate createTime "yyyy-MM-dd HH:mm:ss"}}</td>
        <td>{{fromOperatorName}}</td>
        <td>{{toOperatorName}}</td>
        <td>{{createBy}}</td>
        <td>{{remark}}</td>
    </tr>
    {{/each}}
</table>