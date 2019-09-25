<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客户信息</title>
    <link rel="stylesheet" href="/static/callCenter/css/clientInfo.css">
	
</head>


<body>

    <div class="container">
        <div class="userInfo">
            <div class="title">客户信息</div>
            <ul>
                <c:if test="${custUser != null}">


                    <li>姓名: <span>${custUser.realName}</span></li>
                    <li>手机号: <span>${custUser.mobile}</span></li>
                    <li>身份证号: <span>${custUser.idNo}</span></li>
                    <li>姓别: <span>${custUser.sexStr}</span></li>
                    <li>年龄: <span>${custUser.age}</span></li>
                    <li>工作岗位: <span>${custUser.workPosition}</span></li>
                    <li>银行名称: <span>${custUser.bankName}</span></li>
                    <li>银行卡号: <span>${custUser.cardNo}</span></li>

                </c:if>
            </ul>

        </div>
        <!-- <div>
            <div class="title">申请订单信息</div>
            <ul>
            <c:if test="${apply != null}"> 
        	
        	<li>订单号: <span>${apply.id}</span></li>
                <!-- <li>借贷产品: <span>${apply.productName}</span></li> -->

        <!-- <li>应还金额: <span>${apply.approveAmt+apply.interest}</span></li>
                <li>借款本金: <span>${apply.approveAmt}元</span></li>
                <li>利息: <span>${apply.interest}元</span></li>
                
                <li>借款期限: <span>${apply.approveTerm}天</span></li> -->
        <!-- <li>借款利率(年化): <span><fmt:formatNumber type="number" value="${apply.actualRate*100}" pattern="0.00" maxFractionDigits="2"/>%</span></li> -->

        <!-- 
                <li>借款服务费率: <span><fmt:formatNumber type="number" value="${apply.servFeeRate*100}" pattern="0.00" maxFractionDigits="2"/>%</span></li>
                <li>服务费: <span>${apply.servFee}元</span></li>
                <li>借款用途: <span>${apply.purpose}</span></li>
                
                 -->
        <!--   <li>还款方式: <span>${apply.repayMethodStr}</span></li>
                <li>还款期数: <span>${apply.term}期</span></li>
                <li>申请时间: <span><fmt:formatDate value="${apply.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
                <li>订单状态: <span>${apply.statusStr}</span></li>
  			 </c:if>
                
            </ul>
        </div> -->
        <!-- 申请订单信息 -->
        <div class="box ordRes">
            <div class="title">申请订单信息</div>
            <table>
                <thead>
                    <tr>
                        <th>订单号</th>
                        <th>应还金额（元）</th>
                        <th>借款本金（元）</th>
                        <th>利息（元）</th>
                        <th>借款期限</th>                       
                        <th>还款期数</th>
                        <th>贷款开始日期</th>
                        <th>订单状态</th>
                    </tr>
                </thead>
                <tbody>
                    
                    
                    <tr>
                        <td>${loanRepayPlan.applyId }</td>
                        <td>${loanRepayPlan.totalAmount}</td>
                        <td>${loanRepayPlan.principal }</td>
                        <td>${loanRepayPlan.interest}</td>
                        <td>${loanRepayPlan.totalTerm }</td>
                        <td>${loanRepayPlan.currentTerm }</td>
                        <td><fmt:formatDate value="${loanRepayPlan.loanStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${loanRepayPlan.status ==1?"已结清":"未结清"}</td>
                    </tr>
                                
                </tbody>
            </table>
        </div>
        <!-- 历史订单信息 -->
        <div class="box">
            <div class="title">历史订单信息</div>
            <table>
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单号</th>
                        <th>应还金额（元）</th>
                        <th>借款本金（元）</th>
                        <th>利息（元）</th>
                        <th>借款期限</th>
                        <th>还款期数</th>
                        <th>申请时间</th>
                        <th>订单状态</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="applyItem" items="${applyList}" varStatus="stat">
                <c:if test="${applyItem.applyStatus == 1 && applyItem.id != applyId}">
                    <tr>
                        <th>${stat['index']+1}</th>
                        <td>${applyItem.id }</td>
                        <td>${applyItem.approveAmt+ applyItem.interest}</td>
                        <td>${applyItem.approveAmt }</td>
                        <td>${applyItem.interest}</td>
                        <td>${applyItem.approveTerm }</td>
                        <td>${applyItem.term }</td>
                        <td><fmt:formatDate value="${applyItem.applyTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${applyItem.statusStr }</td>
                    </tr>
                    </c:if>
                     </c:forEach>
                </tbody>
            </table>
        </div>



    </div>

</body>

</html>