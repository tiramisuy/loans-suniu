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
                        <th>还款方式</th>
                        <th>还款期数</th>
                        <th>申请时间</th>
                        <th>订单状态</th>
                        <th>渠道</th>
                        <th>来源</th>
                    </tr>
                </thead>
                <tbody>
                    
                    
                    <tr>
                        
                        <td>${apply.id }</td>
                        <td>${apply.approveAmt+ apply.interest}</td>
                        <td>${apply.approveAmt }</td>
                        <td>${apply.interest}</td>
                        <td>${apply.approveTerm }</td>
                        <td>${apply.repayMethodStr }</td>
                        <td>${apply.term }</td>
                        <td><fmt:formatDate value="${apply.applyTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${apply.statusStr }</td>
                        <td>${apply.channelId }</td>
                        <td>${apply.source }</td>
                        
                        
                    </tr>
                                
                </tbody>
            </table>
        </div>
        <!-- 还款计划明细 -->
        <div class="box">
            <div class="title">还款计划明细</div>
            <table>
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>应还金额（元）</th>
                        <th>借款本金（元）</th>
                        <th>利息（元）</th>
                         <th>逾期管理费（元）</th>
                         <th>实际还款金额（元）</th>
                      
                        <th>期数</th>
                        <th>应还日期</th>
                        
                        <th>是否已经结清</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="applyItem" items="${repayPlanItemList}" varStatus="stat">
                
                    <tr>
                        <th>${stat['index']+1}</th>
                        <td>${applyItem.totalAmount}</td>
                        <td>${applyItem.principal }</td>
                        <td>${applyItem.interest}</td>
                        <td>${applyItem.overdueFee}</td>
                         <td>${applyItem.actualRepayAmt}</td>
                        
                        <td>${applyItem.thisTerm }</td>
                        <td>${applyItem.repayDateStr}</td>
                        
                        <td>${applyItem.statusStr}</td>
                    </tr>
                   
                     </c:forEach>
                </tbody>
            </table>
        </div>



    </div>

</body>

</html>