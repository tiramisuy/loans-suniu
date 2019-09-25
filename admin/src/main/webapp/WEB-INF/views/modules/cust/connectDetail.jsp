<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>

<form id="connectDetail" class="layui-form" action="">
    <fieldset class="layui-elem-field">
        <legend>联系人通话详情</legend>
        <table style="display:inline;" class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="125">
                <col width="125">
                <col width="125">
                <col width="125">
                <col width="125">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>通话号码</th>
                <th>通话类型</th>
                <th>通话时间</th>
                <th>通话地点</th>
                <th>通话时长（秒）</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${connectDetail.data1}" var="calls" varStatus="xh">
	            <tr>
	                <td style="text-align: center">${xh.count}</td>
                    <td style="text-align: center">${calls.otherCellPhone}</td>
                    <td style="text-align: center">${calls.initType}</td>
                    <td style="text-align: center">${calls.startTime}</td>
                    <td style="text-align: center">${calls.place}</td>
                    <td style="text-align: center">${calls.useTime}</td>
	            </tr>
            </c:forEach>
            </tbody>
        </table>

        <table style="display:inline;margin-left:50px" class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="125">
                <col width="125">
                <col width="125">
                <col width="125">
                <col width="125">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>通话号码</th>
                <th>通话类型</th>
                <th>通话时间</th>
                <th>通话地点</th>
                <th>通话时长（秒）</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${connectDetail.data2}" var="calls" varStatus="xh">
                <tr>
                    <td style="text-align: center">${xh.count}</td>
                    <td style="text-align: center">${calls.otherCellPhone}</td>
                    <td style="text-align: center">${calls.initType}</td>
                    <td style="text-align: center">${calls.startTime}</td>
                    <td style="text-align: center">${calls.place}</td>
                    <td style="text-align: center">${calls.useTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </fieldset>
</form>