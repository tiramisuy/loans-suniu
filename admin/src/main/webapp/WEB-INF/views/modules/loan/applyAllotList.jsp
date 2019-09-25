<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <c:choose>
        <c:when test="${applyOP.stage == 1}">
            <title>待审核项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 2}">
            <title>已过审项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 3}">
            <title>已否决项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 4}">
            <title>办理中项目</title>
        </c:when>
    </c:choose>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
        function openTabPage(url) {
            if(cookie('tabmode')=='1'){
                addTabPage('借款人详情',url,null);
            }else{
                window.location.href=url;
            }
        };
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/loan/apply/list?stage=${applyOP.stage}">
	                    审核管理
        </a>
    </li>


</ul>

<form:form id="searchForm" modelAttribute="applyOP" action="${ctx}/loan/apply/allotList" method="get" class="breadcrumb form-search">
	<button class="btn right btn-white btn-sm" data-toggle="tooltip" data-placement="right" onclick="">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<i class="fa fa-plus"></i> 刷新
	</button><br>
	</br><p><font size="2" color="red">上次刷新时间：${refresh}</font></p>
</form:form>
<%-- <sys:message content="上次刷新时间：${refresh} "/> --%>

<table id="contentTable" class="table">
    <colgroup>
        <col width="5%">
        <col width="10%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
    </colgroup>
    <thead>
    <tr>
        <th style="text-align: center;">序号</th>
        <th style="text-align: center;">审核人员</th>
        <th style="text-align: center;">当日已处理订单</th>
        <th style="text-align: center;">当日已通过订单</th>
        <th style="text-align: center;">当日已拒绝订单</th>
<!--         <th style="text-align: center;">超时效案件数</th> -->
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="apply" varStatus="xh">
        <tr>
            <td>
                  ${ xh.index + 1}
            </td>
            <td style="text-align: center;">
                    ${apply.name}
            </td>
            <td style="text-align: center;">
                    ${apply.total}
            </td>
            <td style="text-align: center;">
                    ${apply.apprv}
            </td>
            <td style="text-align: center;">
                    ${apply.notapprv}
            </td>
<!--             <td style="text-align: center;"> -->
<%--                     ${apply.timeout} --%>
<!--             </td> -->
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">${page}</div>
</body>
</html>