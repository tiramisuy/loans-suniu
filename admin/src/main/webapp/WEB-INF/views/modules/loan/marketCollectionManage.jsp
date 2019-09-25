<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<meta name="decorator" content="default" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/applycount/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/applycount/css/jquery.date_input.pack.css" />
<script type="text/javascript"
	src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/applycount/js/jquery.date_input.pack.js"></script>

<style type="text/css">
.store-table {
	margin: 0 auto;
	width: 90%;
	text-align: center;
	border-top: 1px solid #cccccc;
	border-bottom: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
}

.store-table th, .store-table td {
	line-height: 40px;
	border-bottom: 1px solid #cccccc;
	border-right: 1px solid #cccccc;
}

label{
	margin-right:15px;
}

.input-medium {
    width: 15%;
    margin-right: 5%;
} 


</style>
</head>

<body>
	<%-- <c:set var="companyList" value="${fns:getCompanyList()}" /> --%>
	<ul class="nav nav-tabs">
		<li class="active">营销分配管理</li>
	</ul>




	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/market/marketCollectionManage" method="post"
			class="breadcrumb form-search">
			<div class="operation-search-time">
				<label>姓名</label>
				<input id="userName" name="userName" class="input-medium" type="text" value="${applyOP.userName}" maxlength="20">
				<label>分配状态</label>
				<form:select path="allotFlag" class="input-medium" >
					<form:option value="">全部</form:option>
					<form:option value="1"  >可分配</form:option>
					<form:option value="0" >不可分配</form:option>
					
				</form:select>
				

				<div class="operSearch-btn">
					<a href="javascript:void(0);" id="operSearch-btn"
						class="border-radius3">查询</a>
				</div>
				<input id="checkStart" name="checkStart" type="hidden"
					value="${applyOP.checkStart}" />
			</div>
		</form:form>
	</div>
	<!--搜索条件  e-->

	<table class="store-table">
		<colgroup>
			<col width="200">
			<col width="200">
			<col width="150">
		</colgroup>
		<thead>
			<tr style="color: #000000">
				<th>客服</th>
				<th>分配状态${user.allotFlag}</th>
				<th>客服营销权限</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="xh">
				<tr style="color: #000000; text-align: center;">
					<td>${user.name}</td>
					<td data-uid="${user.id}">
						<c:if test="${user.allotFlag == 1}">
							可分配
						</c:if>
						<c:if test="${user.allotFlag == 0}">
							不可分配
						</c:if>
					
					</td>
					<td>
						<input id="${user.id}" class="check_switch" type="checkbox" 
						
							<c:if test="${user.allotFlag == 1}">
								checked 
							</c:if>
						
							name="s" />
					</td>
				</tr>
			</c:forEach>
			
		</tbody> 
	</table>

	<script type="text/javascript">
		/* $(function() {
			loadAuditor();
		}); */

		$("#operSearch-btn").click(function() {
			$("#searchForm").submit();
		});

		//初审权限切换点击事件
		$(".check_switch").click(function() {
			var _this = this;
			var userId = this.id;
			var mess;
			if (this.checked) {
				mess = "确认要开启分配催收任务权限吗？"
			} else {
				mess = "确认要关闭催收任务权限吗？"
			}
			top.layer.confirm(mess, {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				//do something
				resetTip(); //loading();
				$.ajax({
					url : "${ctx}/loan/collection/allotCollectionAuthorization",
					data : {
						'userId' : userId
					},
					type : "Post",
					dataType : "json",
					success : function(data) {
						if (data.code == "1") {
							top.layer.msg(data.msg, {
								icon : 1
							});
							if (_this.checked) {
								$("td[data-uid=" + userId + "]").html("不可分配");
								_this.checked = false;
							} else {
								$("td[data-uid=" + userId + "]").html("可分配");
								_this.checked = true;
							}

						} else {
							top.layer.msg(data.msg, {
								icon : 2
							});
						}
					},
					error : function(data) {
						$.messager.alert('错误', data.msg);
					}
				});
				top.layer.close(index);
			});
			return false;
		});

		/* //级联查询审核人
		function loadAuditor() {
			$.ajax({
				type : "get",
				url : "${ctx}/loan/apply/getAuditorByCompanyId",
				cache : false,
				async : true,
				data : "companyId=" + $("#companyId").val(),
				dataType : "json",
				success : function(datas) {
					$("#auditor").empty();
					$("#auditor").append("<option value=''>全部</option>");
					if (datas.data != null && datas.data.length > 0) {
						for (var i = 0; i < datas.data.length; i++) {
							//初始化选中
							if ("${applyOP.auditor}" == datas.data[i].id) {
								$("#auditor").append(
										"<option value='"+datas.data[i].id+"' selected='selected'>"
												+ datas.data[i].name
												+ "</option>");
							} else {
								$("#auditor").append(
										"<option value='"+datas.data[i].id+"'>"
												+ datas.data[i].name
												+ "</option>");
							}
						}
					}
				},
				error : function() {
					alert("operation failed!");
				}
			});
		}

		//触发级联
		$("#companyId").change(function() {
			loadAuditor();
		}) */
	</script>

</body>
</html>