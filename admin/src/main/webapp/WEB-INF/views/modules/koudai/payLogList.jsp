<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>口袋放款明细</title>
    <meta name="decorator" content="default"/>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/koudai/payLog/list">口袋放款明细</a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">放款管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>口袋放款明细</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="payLogListOP" action="${ctx}/koudai/payLog/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>姓名</label>
            <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>手机号码</label>
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
        </li>
        <li><label>放款状态</label>
            <form:select path="payStatus" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="0">成功</form:option>
                <form:option value="1">失败</form:option>
                <form:option value="2">募集中</form:option>
                <form:option value="3">已取消</form:option>
            </form:select>
        </li>
        
         <%--<li><label>创建订单状态</label>
            <form:select path="kdCreateCode" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="0">成功</form:option>
                <form:option value="1">失败</form:option>
                <form:option value="2">未推送</form:option>
            </form:select>
        </li> --%>
        
        <li><label>存管提现状态</label>
            <form:select path="withdrawStatus" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="0">未提现</form:option>
                <form:option value="1">已提现</form:option>
                
            </form:select>
        </li>
        
        		
 <li class="time_li"><label>放款时间</label> 
	<form:input id='expectStart' path="expectStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});" />
			<h>-</h>
	<form:input id='expectEnd' path="expectEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});" />
 </li>
			
        
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
    </ul>
</form:form>


<!-- <div class="row">
			<div class="col-sm-12 pull-left">
				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return validateExport()">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			</div>
		</div> -->


<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>借款订单号</th>
        <!--<th>放款订单号</th>-->
        <th>借款人姓名</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>银行</th>
        <th>卡号</th>
        <th>放款金额</th>
        <th>放款时间</th>
        <th>到账时间</th>
        <!-- <th>放款失败次数</th> -->
        <th>放款状态</th>
        <th>放款结果</th>
        <!-- <th>创建订单结果</th> -->
        <th>提现状态</th>
        <th  style="min-width: 80px;">备注</th>
        <th style="min-width: 200px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
            <td>
                    ${item.applyId}
            </td>
            <!--
            <td>
                    ${item.payOrderId}
            </td>
            -->
            <td>
               ${item.userName}
            </td>
            <td>
                    ${item.mobile}
            </td>
            <td>
                    ${fn:substring(item.idNo,0,6)}****${fn:substring(item.idNo,fn:length(item.idNo)-4,fn:length(item.idNo))}
            </td>
            <td>
                    ${item.bankName}
            </td>
            <td>
                    ${fn:substring(item.cardNo,0,4)}****${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}
            </td>
            <td>
                    ${item.payAmt}
            </td>
            <td>
                <fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${item.paySuccTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <%-- <td>
                    ${item.payFailCount}
            </td> --%>
            <td>
                <c:choose>
                    <c:when test="${item.payStatus == '0'}">
                        <div class="text-blue">成功</div>
                    </c:when>
                    <c:when test="${item.payStatus == '1'}">
                        <div class="required">失败</div>
                    </c:when>
                     <c:when test="${item.payStatus == '3'}">
                        <div class="required">已取消</div>
                    </c:when>
                    <c:otherwise>
                        <span style="color:orange;">募集中</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${item.kdPayMsg}
            </td>
             
             <%--<td>
                <c:choose>
                    <c:when test="${item.kdCreateCode == '0'}">
                        <div class="text-blue">提交成功</div>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </td> --%>
            
            <td>
                <c:choose>
                    <c:when test="${item.withdrawStatus == 0}">
                        <div class="required">未提现</div>
                    </c:when>
                    <c:when test="${item.withdrawStatus == 1}">
                        <div class="text-blue">已提现</div>
                    </c:when>                   
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </td>
            
            <td>
                    ${item.remark2}
            </td>
            
            <td class="text-blue">
            	<c:if test="${not empty item.payOrderId}">
            		<a href='${ctx}/loan/withdraw/exportContract?contNo=${item.applyId}&type=koudai' style="padding-left:5px;">借款协议</a>
            	</c:if>
            	<c:if test="${empty item.payOrderId}">
            		<a href="javascript:void(0)" onclick="queryStatus('${item.applyId}')" style="padding-left: 10px;">查询状态</a>
            		<a href="javascript:void(0)" onclick="queryAccount('${item.applyId}')" style="padding-left: 10px;">查询开户信息</a>
            		<c:if test="${item.payStatus == '0' }">
            			<a href="javascript:void(0)" onclick="getContract('${item.applyId}')" style="padding-left: 10px;">查询合同</a>
            		</c:if>
            	</c:if>
					
				<%--					
				<c:if test="${item.payStatus == 1}">
					<a href="javascript:void(0)" onclick="adminPay('${item.id}')" style="padding-left: 10px;">放款</a>
					<a href="javascript:void(0)" onclick="adminCancel('${item.id}')" style="padding-left: 10px;">取消订单</a>
				</c:if>
				--%>
				
				<%--	
				<c:if test="${item.kdCreateCode != null && item.kdCreateCode != '0'}">
					<a href="javascript:void(0)" onclick="adminCreate('${item.id}')" style="padding-left: 10px;">创建订单</a>
				</c:if>
				--%>
					
				<!--转渠道放款 ：放款失败  -->
				<c:if test="${item.payStatus == 1 && !fn:contains(item.remark,'_TO_')}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="showChangePaychannel('${item.id}')" style="padding-left:5px;">修改放款渠道</a>
                </c:if> 
                <!--转渠道放款 ：放款成功，24小时未提现  -->
                <c:if test="${item.payStatus == 0 && item.withdrawStatus == 0 && fns:pastMinutes(item.paySuccTime)>1440 && !fn:contains(item.remark,'_TO_')}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="showChangePaychannel('${item.id}')" style="padding-left:5px;">修改放款渠道</a>
                </c:if> 
				<!--取消订单 ：放款失败，或者放款成功24小时未提现  -->
            	<c:if test="${(item.payStatus==1 && !fn:contains(item.remark,'_TO_')) || (item.payStatus == 0 && item.withdrawStatus == 0 && fns:pastMinutes(item.paySuccTime)>1440 && !fn:contains(item.remark,'_TO_'))}">
					<a href="javascript:void(0)" onclick="adminCancel('${item.id}')" style="padding-left: 10px;">取消订单</a>
				</c:if>
					
				<%--
				<c:if test="${fns:haveRole('system') == true}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="changeRepayTime('${item.applyId}')" style="padding-left:5px;">修改账单日</a>
                </c:if>  
                --%>
                
                <!--放款成功,上传还款计划 -->
                <c:if test="${item.payStatus == 0}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="pushAssetRepaymentPeriod('${item.applyId}')" style="padding-left:5px;">上传还款计划</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>



<script id="repayTimeDialog" type="text/x-handlebars-template">
    <jsp:include page="/WEB-INF/views/include/repayDateDialog.jsp"></jsp:include>
</script>

<script id="contractList" type="text/x-handlebars-template">
    <jsp:include page="contractList.jsp"></jsp:include>
</script>

<script id="accountList" type="text/x-handlebars-template">
    <jsp:include page="accountList.jsp"></jsp:include>
</script>

<script id="changePaychannel" type="text/x-handlebars-template">
    <jsp:include page="/WEB-INF/views/include/changePaychannel.jsp"></jsp:include>
</script>

<script>


function dateFtt(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 

function changeRepayTime(applyId) {
	var d = new Date();
	var str = dateFtt('yyyy-MM-dd hh:mm:ss',d);
	//var str = d.getFullYear().toString() + '-' + addzero(d.getMonth() + 1) + '-' + addzero(d.getDate()) + ' ' + addzero(d.getHours()) + ':' + addzero(d.getMinutes()) + ':' + addzero(d.getSeconds());
	var param = { 
		applyId : applyId,
		date : str
	};
	var myTemplate = Handlebars.compile($("#repayTimeDialog").html());
	var html = myTemplate(param);
	layer.open({
		type : 1,
		title : '修改账单日',
		area : [ '500px', '240px' ], //宽高
		content : html
	});
};


function changeRepayDate(applyId) {
	var loanTime = $("#loantime").val();
	changeRepayDateConfirm(applyId, loanTime);
	layer.close(index);
}



function changeRepayDateConfirm(applyId, loanTime) {
	var param = {
		applyId : applyId,
		loanTime : loanTime
	};
	$.ajax({
		type : "post",
		url : "${ctx}/loan/withdraw/changeRepayDate",
		data : param,
		dataType : "json",
		error : function(request) {
			alert("系统繁忙,请稍后再试");
		},
		success : function(data, textStatus) {
			if (data.code == "1") {
				location.reload(true);
			} else {
				alert(data.msg)
			}

		},
	});
};





function validateExport() {
	 var startTime = $("#expectStart").val();
	var endTime = $("#expectEnd").val();
	var flag1 = false;
	if ("" == startTime && "" == endTime) {
		flag1 = true;
	};
	if (("" == startTime && "" != endTime) || ("" != startTime && "" == endTime)) {
		layer.alert('请选择时间段', {
			icon : 7
		});
		return false;
	};
	if (flag1) {
		layer.alert('请选择导出时间', {
			icon : 7
		});
		return false;
	}
	var startTimestamp = new Date(startTime).getTime();
	var endTimestamp = new Date(endTime).getTime();
	if (startTimestamp > endTimestamp) {
		layer.alert('起始日期应在结束日期之前', {
			icon : 7
		});
		return false;
	}
	
	if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
		layer.alert('时间段最长为一个月', {
			icon : 7
		});
		return false;
	} 
	return exportExcel(
			'${ctx}/koudai/payLog/exportList',
			'${ctx}/koudai/payLog/list');
};

	function adminPay(id) {
			layer.confirm('您确定要放款吗？', {
			   btn : [ '提交', '取消' ]
		    }, function(index) {
			   adminPayConfirm(id);
			   layer.close(index);
		    });
		};
 	function adminPayConfirm(id) {
			var param = {
				id : id
			};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/adminPay",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						location.reload(true);
					} else {
						alert(data.msg)
					}
				},

			});
		};
		
		
		
		
		function adminCreate(id) {
			layer.confirm('您确定要创建订单吗？', {
			   btn : [ '提交', '取消' ]
		    }, function(index) {
		    	adminCreateConfirm(id);
			   layer.close(index);
		    });
		};
 	function adminCreateConfirm(id) {
			var param = {
				id : id
			};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/adminCreate",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						location.reload(true);
					} else {
						alert(data.msg)
					}
				},

			});
		};
		
		
		
		function adminCancel(id) {
			layer.confirm('您确定要取消订单吗？', {
			   btn : [ '提交', '取消' ]
		    }, function(index) {
		    	adminCancelConfirm(id);
			   layer.close(index);
		    });
		};
 	function adminCancelConfirm(id) {
			var param = {
				id : id
			};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/adminCancel",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						location.reload(true);
					} else {
						alert(data.msg)
					}
				},

			});
		};
		
		function getContract(applyId){
			var param = {
					applyId : applyId
				};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/getContract",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#contractList").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '合同列表',
							area : [ '300px', '240px' ], //宽高
							content : html
						});
					} else {
						alert(data.msg)
					}
				},

			});
		};
		
		function queryStatus(applyId){
			var param = {
					applyId : applyId
				};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/queryStatus",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						layer.alert(data.data);
					} else {
						alert(data.msg)
					}
				},

			});
		};


		function queryAccount(applyId){
			var param = {
					applyId : applyId
				};
			$.ajax({
				type : "post",
				url : "${ctx}/koudai/queryAccount",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
                        var myTemplate = Handlebars.compile($("#accountList").html());
                        var html = myTemplate(data.data);
                        layer.open({
                            type : 1,
                            title : '开户信息',
                            area : [ '450px', '240px' ], //宽高
                            content : html
                        });
					} else {
						alert(data.msg)
					}
				},

			});
		};
		
		
		
function showChangePaychannel(id) {
	
	var param = { 
		id : id
	};
	var myTemplate = Handlebars.compile($("#changePaychannel").html());
	var html = myTemplate(param);
	layer.open({
		type : 1,
		title : '修改放款渠道',
		area : [ '500px', '240px' ], //宽高
		content : html
	});
};

function changePaychannel(id,applyId) {
	var cgPaychannel = $("#cgPaychannel").val();
	changePaychannelConfirm(id, cgPaychannel);
	layer.close(layer.index);
}



function changePaychannelConfirm(id, cgPaychannel) {
	var param = {
		id : id,
		paychannel : cgPaychannel
	};
	$.ajax({
		type : "post",
		url : "${ctx}/koudai/changePaychannel",
		data : param,
		dataType : "json",
		error : function(request) {
			alert("系统繁忙,请稍后再试");
		},
		success : function(data, textStatus) {
			if (data.code == "1") {
				location.reload(true);
			} else {
				alert(data.msg)
			}

		},
	});
};


function pushAssetRepaymentPeriod(applyId) {
	var param = {
		applyId : applyId
	};
	$.ajax({
		type : "post",
		url : "${ctx}/koudai/pushAssetRepaymentPeriod",
		data : param,
		dataType : "json",
		error : function(request) {
			alert("系统繁忙,请稍后再试");
		},
		success : function(data, textStatus) {
			if (data.code == "1") {
				alert("提交成功")
				location.reload(true);
			} else {
				alert(data.msg)
			}

		},
	});
};
</script>
</body>
</html>