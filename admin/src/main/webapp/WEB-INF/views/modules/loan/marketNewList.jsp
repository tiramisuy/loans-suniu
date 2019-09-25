<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>营销待分配</title>
<meta name="decorator" content="default" />

<link rel="stylesheet"
	href="${ctxStatic}/layui-master/build/css/layui.css">
<link href="${ctxStatic}/position/basic.css" rel="stylesheet" />
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet" />

<%--<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>--%>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"
	type="text/javascript"></script>
 <style type="text/css">
        .form-search .ul-form li .error {
            width: auto !important;
        }
        #collection{
            width: 1051px;
            height: 791px;
            border: 1px solid rgba(221,221,221,1);
            position: relative;
        }
        #collections{
            height: 37px;
            line-height: 37px;
            background: rgba(242,242,242,1);
        }
        .collection{
            margin: 100px auto 0;
            width:867px;
            height:377px;
            border: 1px solid #cccccc;
            overflow: hidden;
            position: relative;
        }
        .collections{
            width:839px;
            height:330px;
            background-color:rgba(236, 246, 251, 1);
            margin-top: 23px;
            margin-left: 13px;
        }
        .clears:after{
            content: "";
            display: block;
            clear: both;
        }
        .collections-top{
            height: 36px;
            line-height: 36px;
            font-size: 14px;
            width: 820px;
            margin: 0 auto;
            border-bottom: 1px solid #cccccc;
        }
        .collections-top div,.collections-con div{
            float: left;
            width: 200px;
        }
        .collections-con{
            width: 800px;
            height: 278px;
            overflow-y: auto;
        }
        .collections-top em{
            color: #FF0000;
        }
        .collections-con div{
            font-size: 12px;
            color: #666;
            text-indent: 10px;
            height: 40px;
            line-height: 40px;
        }
        .collections-con div input{
            margin-right: 15px;
            margin-top: 3px;
        }
        .collections-con .collections-cons{
            width: 500px;
        }
        .collection select{
            width: 152px;
            height: 22px;
            font-size: 12px;
        }
        .collections-name,.collections-time{
            position: absolute;
            left: 422px;
            top: 68px;
        }
        .collections-time{
            left: 621px;
        }
        .collection-bot{
            width: 328px;
            margin: 28px auto 0;
        }
        .collection-bot-left,.collection-bot-right{
            width: 164px;
            height: 45px;
            text-align: center;
            line-height: 45px;
            cursor: pointer;
            color: #fff;
            background: rgba(25, 158, 216, 1);
            border-radius: 6px;
            float: left;
        }
        .collection-bot-right{
            background: #ffffff;
            color: #333;
        }
        a{
    		color: #2fa4e7;
    		text-decoration: none;
		}
		
		#menu{  
            font-size: 18px;  
            font-weight: bold;  
        }  
        #menu li{  
            text-decoration: none;  
            list-style: none;  
            display: inline-block;
            float: left;
            padding-left: 10px;
        }  
    </style>


<script type="text/javascript">
	$(document).ready(function() {

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
		<li class="active"><a
			href="${ctx}/loan/market/marketNewList">新用户列表</a></li>
	</ul>

	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">客服管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;营销待分配
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="expCustuserOP"
		action="${ctx}/loan/market/marketNewList" method="post"
		class="breadcrumb form-search layui-form">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
	
		<ul class="ul-form">
			<li><label>姓名</label> <form:input path="userName"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>手机号 </label> <form:input path="mobile"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>分配人员</label>
            <form:select path="allotUserId" class="input-medium" >
                <c:if test="${fns:haveRole('kefu_dianxiao') == false}">
                    <form:option value="">全部</form:option>
                </c:if>
                <c:forEach items="${userList}" var="detail">
                    <c:choose>
                        <c:when test="${loginUser == detail.id}">
                            <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                        </c:when>
                        <c:when test="${loginUser != detail.id}">
                            <form:option value="${detail.id}">${detail.name}</form:option>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
        </li>
          <li><label>拨打情况</label>
	            <form:select path="isPush" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="1">永久拒绝营销</form:option>
	                <form:option value="2">无人接听</form:option>
	                <form:option value="3">考虑</form:option>
	                <form:option value="4">需要</form:option>
	                <form:option value="5">不需要</form:option>
	                <form:option value="6">第三方接听</form:option>
	            </form:select>
	        </li>	
			 <li class="time_li"><label>注册时间</label>
                <form:input id='checkStart' path="checkStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${checkStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            
                <h>-</h>
                <form:input id='checkEnd' path="checkEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${checkEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
			 <li class="time_li"><label>分配时间</label>
                <form:input id='updateStart' path="updateStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${updateStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            
                <h>-</h>
                <form:input id='updateEnd' path="updateEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${updateEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
			<li><label>客户类型</label>
	            <form:select path="approveResult" class="input-medium">
	                <form:option value="1">新用户</form:option>
	                <form:option value="2">老用户</form:option>
	            </form:select>
	        </li>
	        
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>

	</form:form>
<c:if test="${fns:haveRole('kefu_dianxiao') != true}">
	<div class="row">
		<div class="col-sm-12 pull-left">
<!-- 			<button id="btnExport" class="btn btn-white btn-sm" 
				data-toggle="tooltip" data-placement="left"
				onclick="return expCustUser();">
				<i class="fa fa-file-excel-o"></i> 导出
			</button> -->
			 
			
			<input id="csSubmit" class="btn btn-primary" type="button"
					onclick="marketAllot()" value="订单分配" />
			
		</div>
	</div>
 </c:if>
 
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th><input type="checkbox" name="batchOptControl"></th>
       			<th>序号</th>
       			<th>分配人</th>
       			<th>分配时间</th>
				<th>客户姓名</th>
				<th>手机号码</th>
				<th>注册时间</th>
				<th>拨打时间</th>
				<th>拨打情况</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<tr>
		        	<td><input class="check" type="checkbox" value="${item.id}"
								username="${item.userName}" companyId="${item.mobile}"
								name="batchOptIds">
					</td>
		            <td>
		                    ${xh.count}
		            </td>
		            <td>${item.allotName}</td>
		            <td>
		            	<fmt:formatDate value="${item.allotDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		            </td>
					<td>${item.userName}</td>
					<td>${item.mobile}</td>
					<td><fmt:formatDate value="${item.approveTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.warnTime}</td>
					<td>
								<c:if test="${item.isPush ==1 }">
				          			永久拒绝营销
				          		</c:if>
				          		<c:if test="${item.isPush ==2 }">
				          			无人接听
				          		</c:if>
				          		<c:if test="${item.isPush ==3 }">
				          			考虑
				          		</c:if>
				          		<c:if test="${item.isPush ==4 }">
				          			需要
				          		</c:if>
				          		<c:if test="${item.isPush ==5 }">
				          			不需要
				          		</c:if>
				          		<c:if test="${item.isPush ==6 }">
				          			第三方接听
				          		</c:if>
					</td>
		<td>
			<c:if test="${fns:haveRole('kefu_dianxiao') == true}">
				<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="newMarketResult('${item.allotId }','${ctx}')" style="padding-left: 5px;">拨打情况</a>	
			</c:if>	
		</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	<script id="market_allot" type="text/x-handlebars-template">
   		 <jsp:include page="marketAllot.jsp"></jsp:include>
	</script>
	<script id="loan_market_new_result" type="text/x-handlebars-template">
   		 <jsp:include page="marketNewResult.jsp"></jsp:include>
	</script>
	
	
	<script type="text/javascript">
	
		function expCustUser(){
			
			var startTime = $("#checkStart").val();
			var endTime = $("#checkEnd").val();
			var flag1 = false;
			if ("" == startTime && "" == endTime) {
				flag1 = true;
			}
			;
			;
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
			return exportExcel('${ctx}/loan/market/exportMarketNewList','${ctx}/loan/market/marketNewList');
			
		}
	
		//弹出订单分配页面
		function marketAllot() {
			var list = [];
			var param = {
				list : list
			};

			if (!$(".check").is(":checked")) {
				alert("请勾选选项");
				return false;
			} else {
				$.each($(".check"), function() {
					if ($(this).is(":checked")) {
						var obj = {
							id : "",
							username : "",
							companyId : ""
						};
						obj.id = $(this).attr("value");
						obj.username = $(this).attr("username");
						obj.companyId = $(this).attr("companyId");
						list.push(obj);
					}
				});
				var myTemplate = Handlebars.compile($("#market_allot").html());
				var html = myTemplate(param);
				layer.open({
					type : 1,
					title : '订单分配',
					area : [ '60%', '80%' ], //宽高
					content : html
				})
				$.ajax({
					type : "post",
					url : "${ctx}/loan/market/getAllCsUser",
					data : "",
					dataType : "json",
					error : function(request) {
						alert("系统繁忙,请稍后再试");
					},
					success : function(data, textStatus) {
						if (data.code == "1") {
							var collectionsSelecter = $("#collections-name");
							var str = "";
							for (var i = 0; i < data.data.length; i++) {
								/* str += '<option value="'+data.data[i].id+'-'+data.data[i].name+'">'
										+ data.data[i].name + '</option>';
										 */
								if(i != 0){
									str += '<div style="padding-top:10px;">';
								}else{
									str += '<div>';
								}
								
							str += '<input type="checkbox" data-id="'+data.data[i].id+'-'+data.data[i].name+'"/><span>'+ data.data[i].name + '</span></div>';
										
							}
							collectionsSelecter.append(str);
						} else {
							alert(data.msg);
						}

					},

				});
			}
		};
				
				

		//提交订单粉分配信息
		function warnBot1() {
			if ($("#collections-name").val() == "--选择提醒人员--") {
				alert("请选择分配的提醒人员");
				return false;
			}
			var arrId = "";
			$.each($(".collections-con input"), function(i) {
				if ($(this).is(":checked")) {
					arrId += $(this).attr("data-id") + "|";
				}
			});
			
			var arrUId = "";//存储多个催收员的id
			$.each($(".collections-name input"), function(i) {
				if ($(this).is(":checked")) {
					arrUId += $(this).attr("data-id") + "|";
				}
			});
			
			
			var datas = {
				ids : arrId,
				companyId : arrUId,
			}
			$.ajax({
				url : '${ctx}/loan/market/custMarketAllot',
				type : 'post',
				data : datas,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						layer.close(layer.index);
						location.reload(true);
					} else {
						alert(data.msg)
					}
		
				},
			});
		};
		
		
		
		
		
		
		//弹出拨打情况页面
		function newMarketResult(marketId,ctx) {
			var param = {
				allotId : marketId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/market/toMarketNewResult",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#loan_market_new_result")
								.html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '拨打结果',
							area : [ '35%', '28%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}
		
				},
		
			});
		};
		
		
		

		function confimMarketAllotResult(marketId, resultType) {
			var markResult = $("input[name='markResult']:checked").val();
			if(markResult==undefined || markResult==""){
				alert("请选择拨打情况");
				return;
			}
			var param = {
				allotId : marketId,
				resultType : markResult
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/market/confimAllotMarketResult",
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
		
		
		
		
		
		
		
	</script>
	
</body>
</html>