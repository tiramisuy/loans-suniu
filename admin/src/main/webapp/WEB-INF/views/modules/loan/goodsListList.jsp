<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }


        function updateStatus(goodsId,status) {
            layer.confirm('确认修改商品上下架状态吗？', {
                btn : [ '提交', '取消' ]
            }, function(index) {
                issueConfirm(goodsId,status);
                layer.close(index);
            });
        }
        function issueConfirm(goodsId,status) {
            var param = {
                goodsId : goodsId,
				status:status
            };
            $.ajax({
                type : "post",
                url : "${ctx}/loan/goodsList/updateStatus",
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loan/goodsList/">商品信息表列表</a></li>
		<%--<shiro:hasPermission name="loan:goodsList:edit">--%>
			<li><a href="${ctx}/loan/goodsList/form">商品信息表添加</a></li>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsList" action="${ctx}/loan/goodsList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>商品名称</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否上架</label>
				<form:select path="status" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="0">否</form:option>
					<form:option value="1">是</form:option>
				</form:select>
			</li>


			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>单价(元)</th>
				<th>市场价(元)</th>
				<th>销量</th>
				<th>是否上架</th>
				<th>创建时间</th>



				<%--<shiro:hasPermission name="loan:goodsList:edit">--%>
					<th>操作</th>
				<%--</shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsList">
			<tr>

				<td>
					${goodsList.goodsName}
				</td>
				<td>
						${goodsList.goodsPrice}
				</td>
				<td>
						${goodsList.marketPrice}
				</td>
				<td>
						${goodsList.salesVolume}
				</td>
				<td>
                        ${goodsList.status ==0?"否":goodsList.status ==1?"是":goodsList.status}
				</td>

				<td>
					<fmt:formatDate value="${goodsList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>




				<%--<shiro:hasPermission name="loan:goodsList:edit">--%>
					<td>
					<a href="${ctx}/loan/goodsList/form?id=${goodsList.id}" ; '800px', '480px','mainFrame');">修改</a>


						<a href="javascript:void(0)" onclick="updateStatus('${goodsList.id}','${goodsList.status}')" style="padding-left:5px;">${goodsList.status ==0?"上架":goodsList.status ==1?"下架":goodsList.status}</a>
					<a href="${ctx}/loan/goodsList/delete?id=${goodsList.id}" onclick="return confirmx('确认要删除该商品信息表吗？', this.href)">删除</a>
				</td>
				<%--</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>