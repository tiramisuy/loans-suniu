<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
   
    <%--定位信息start--%>
    <fieldset class="layui-elem-field">
        <legend>定位统计（按时间排序）</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="150">
                <col width="150">
                <col width="150">
                <col width="150">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>省</th>
                <th>市</th>
                <th>区</th>
                <th>地址</th>
                <th>时间</th>
            </tr>
            </thead>
            <tbody>
            {{#each data}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{province}}</td>
                <td>{{city}}</td>
                <td>{{district}}</td>
                <td ><a href='http://api.map.baidu.com/marker?location={{latitude}},{{longitude}}&title=客户位置&content={{address}}&output=html' target='_blank' class="text-blue">{{address}}</a></td>
                <td>{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>


    </fieldset>
    <%--通讯录联系人通讯end--%>
</form>
