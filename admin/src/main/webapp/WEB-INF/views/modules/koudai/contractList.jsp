<%@ page contentType="text/html;charset=UTF-8" %>
<table class="table">
    <thead>
    <tr>
        <th  style="text-align: center;">查看合同</th>
    </tr>
    </thead>
    <tbody>
    {{#each this}}
    <tr>
        <td style="text-align: center;"><a href="{{this}}" target="view_window">合同{{addOne @index}}</td>
    </tr>
    {{/each}}
    </tbody>
</table>