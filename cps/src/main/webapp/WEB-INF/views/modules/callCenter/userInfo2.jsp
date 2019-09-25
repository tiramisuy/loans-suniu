<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客户信息</title>
    <link rel="stylesheet" href="/static/callCenter/css/tableStyle.css">
    <link rel="stylesheet" href="/static/callCenter/css/audit.css?t=<%=System.currentTimeMillis()%>">
    
    <script src="/static/callCenter/js/jquery.min.js"></script>    
    <script src="/static/js/handlebars/handlebars-v3.0.3.js"></script>
    <script src="/static/js/handlebars/helper.js"></script>
</head>

<body>
    <div class="box">
        <!-- Nav tabs -->
        <ul id="myTabs" class="nav">
            <li class="active" onclick="userInfo()">客户信息</li>
            <li onclick="contactConectInfo()">通讯录</li>
        </ul>
        <!-- Tab panes -->
        <div id="con" class="tab-content">
             <div id="tabDiv"></div>
            
            
            <!--   审批 -->
                    <div class="info accraditation">
                        <div class="title">审批:</div>
                        <form id="checkForm" class="layui-form"	action="/loan/apply/check" method="post" onsubmit="return false;">
                            <div>
                                <span>审批金额:</span>
                                <input class="accItem" name="approveAmt" id="approveAmt" type="text" readonly="readonly" value="${applyInfo.approveAmt}"><label>元</label>
                            </div>
                            <div>
                             <span>审批期限:</span>
                               <input class="accItem" name="approveTerm"  type="text" readonly="readonly" value="${applyInfo.approveTerm}">
                                    <span>天</span>
                            </div>
                            <div>
                             <span>服务费率:</span>
                                <input class="accItem" name="servFeeRate"  type="text" readonly="readonly" value="${applyInfo.servFeeRate}">
                                    <span>%</span>
                            </div>
                            <div class="accIdea">审批意见:<textarea name="remark"></textarea></div>
                            
                            <input type="hidden" name="operatorId" value="${operatorId}">
                        <input id="applyId" name="applyId" type="hidden" value="${applyId}" />
                        
                        <input type="hidden" id="checkResult" name="checkResult" value="">
                        
                            <button class="accbtn btnPass" onClick="applyCheck(this.form,1)">通过</button>
                            <button class="accbtn btnReject" onClick="applyCheck(this.form,2)">拒绝</button>
                        </form>
                    </div>
        </div>
    </div>

</body>

<script id="tpl_7" type="text/x-handlebars-template">
    <%--客户信息--%>
    <jsp:include page="userInfo_user.jsp"></jsp:include>
</script>
    <script id="tpl_8" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="contactConnectInfo.jsp"></jsp:include>
</script>


<script type="text/javascript">
        var userId = '${userId}';
        var applyId = '${applyId}';
        var operatorId = "${operatorId}";
        var mobile = '${mobile}';
        var productId = '${productId}';
        var processStatus='${applyInfo.processStatus}';
        var approveAmt='${applyInfo.approveAmt}';
        var approveTerm='${applyInfo.approveTerm}';

        $(function () {
            //$('#con').children().eq(0).show().siblings().hide();
            $("#myTabs").on("click", "li", function (event) {
                var i = $(this).index();
                $(this).addClass('active').siblings().removeClass('active');
                //$('#con').children().eq(i).show().siblings().hide();
            })
            
        })
    </script>



<script src="/static/callCenter/js/clientInfo2.js?t=<%=System.currentTimeMillis()%>"></script>

</html>