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
	<c:set var="companyList" value="${fns:getCompanyList()}" />
	<ul class="nav nav-tabs">
		<li class="active">审核统计</li>
	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/newAllotManage" method="post"
			class="breadcrumb form-search">
			<div class="operation-search-time">
				<label>门店</label>
				<form:select path="companyId" class="input-medium" disabled="true">
					<form:option value="">全部</form:option>
					<form:option value="XJD" selected="selected">聚宝钱包</form:option>
					<c:forEach items="${companyList}" var="detail">
						<form:option value="${detail.companyId}">${detail.name}</form:option>
					</c:forEach>
				</form:select>

				<label>审核人</label>
				<form:select path="auditor" class="input-medium">
					<form:option value="">全部</form:option>
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
			<col width="200">
			<col width="150">
		</colgroup>
		<thead>
			<tr style="color: #000000">
				<th>信审人</th>
				<th>归属门店</th>
				<th>是否初审人员</th>
				<th>开启初审权限</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${primaryUsers}" var="primaryUser" varStatus="xh">
				<tr style="color: #000000; text-align: center;">
					<td>${primaryUser.name}</td>
					<td><c:choose>
							<c:when test="${primaryUser.companyId == null}">
	                            	聚宝钱包
	                        </c:when>
							<c:otherwise>
								<c:forEach items="${companyList}" var="detail">
									<c:if test="${detail.companyId == primaryUser.companyId}">${detail.name}</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
					<td data-uid="${primaryUser.id}">是</td>
					<td><input id="${primaryUser.id}" class="check_switch"
						type="checkbox" checked name="s" /></td>
				</tr>
			</c:forEach>
			<c:forEach items="${finalUsers}" var="finalUser" varStatus="xh">
				<tr style="color: #000000; text-align: center;">
					<td>${finalUser.name}</td>
					<td><c:choose>
							<c:when test="${finalUser.companyId == null}">
	                            	聚宝钱包
	                        </c:when>
							<c:otherwise>
								<c:forEach items="${companyList}" var="detail">
									<c:if test="${detail.companyId == finalUser.companyId}">${detail.name}</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
					<td data-uid="${finalUser.id}">否</td>
					<td><input id="${finalUser.id}" class="check_switch"
						type="checkbox" name="s" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		$(function() {
			loadAuditor();
		});

		$("#operSearch-btn").click(function() {
			$("#searchForm").submit();
		});

		//初审权限切换点击事件
		$(".check_switch").click(function() {
			var _this = this;
			var userId = this.id;
			var mess;
			if (this.checked) {
				mess = "确认要开启初审权限吗？"
			} else {
				mess = "确认要关闭初审权限吗？"
			}
			top.layer.confirm(mess, {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				//do something
				resetTip(); //loading();
				$.ajax({
					url : "${ctx}/loan/apply/switchAllotManage",
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
								$("td[data-uid=" + userId + "]").html("否");
								_this.checked = false;
							} else {
								$("td[data-uid=" + userId + "]").html("是");
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

		//级联查询审核人
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
		})
	</script>

</body>
</html>