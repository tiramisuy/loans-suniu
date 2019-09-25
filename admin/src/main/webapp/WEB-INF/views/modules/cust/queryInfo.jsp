<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>投复利客户信息查询</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {

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
		<li class="active"><a href="${ctx}/sys/custUser/findCustUser">客户查询</a>
		</li>


	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;客户查询
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form id="searchForm" action="${ctx}/sys/custUser/findCustUser"
		class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>手机号码</label> <input name="mobile" value="${mobile}"
				htmlEscape="false" maxlength="11" class="input-medium" /></li>
			<li>&nbsp;&nbsp;&nbsp;<input id="btnSubmit"
				class="btn btn-primary" type="submit" value="查询" />
			</li>
		</ul>
	</form>
	<sys:message content="${message}" />

	<table id="table1" class="table">
		<thead>
			<tr>
				<c:if test="${none == 'none'}">
					<th>客户信息</th>
				</c:if>
				<th>存管账户</th>
				<th>客户姓名</th>
				<th>用户类型</th>
				<th>是否开通存管账号</th>
				<th>身份证号</th>
				<th>手机号</th>
				<th>银行卡号</th>
				<th>账户余额</th>
				<th>成功放款次数</th>
				<th>已推标数量</th>
				<th>是否结清</th>
				<th>详情</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${status == 'OK'}">
					<c:forEach items="${resultMap}" var="map" varStatus="xh">
						<td>${map.id}</td>
						<td><a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable('${map.mobile_no}');">${map.realname}</a></td>
						<td><c:if test="${map != null and (map.version == '0' or map.version == null)}">
								<div>理财用户</div>
							</c:if> 
							<c:if test="${map.version == '1'}">
								<div>借款用户</div>
							</c:if> 
							<c:if test="${map.version == '3'}">
								<div>小微宝</div>
							</c:if> 
							<c:if test="${map.version == '4'}">
								<div>诚诚普惠</div>
							</c:if> 
							<c:if test="${map.version == '5'}">
								<div>复利小贷</div>
							</c:if>
							<c:if test="${map.version == '6'}">
								<div>旅游分期</div>
							</c:if>
							<c:if test="${map.version == '7'}">
								<div>信用分期</div>
							</c:if>
						</td>
						<td><c:if test="${map.open_account == '1'}">
								<div>是</div>
							</c:if> <c:if test="${map.open_account == '0'}">
								<div>否</div>
							</c:if></td>
						<td>${map.certif_id}</td>
						<td>${map.mobile_no}</td>
						<td>${map.capAcntNo}</td>	
					</c:forEach>
					<td>${amount }</td>
					<td>${loanCount }</td>
					<td>${loan}</td>
					<td><c:choose>
							<c:when test="${loan != 0}">
								<!--如果 -->
								<c:if test="${isRepaying == 'true' }">
									<div>正在还款</div>
								</c:if>
								<c:if test="${isRepaying == 'false' }">
									<div>已经结清或正在筹标</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<!--否则 -->
								<div>无标的信息</div>
							</c:otherwise>
						</c:choose></td>
				</c:when>
				<c:when test="${status == 'NOK'}">
					<td>
						<c:choose>
							<c:when test="${vo.accountId != null }">
								${vo.accountId}
							</c:when>
							<c:otherwise>
								客户未在投复利开户
							</c:otherwise>
						</c:choose>
					</td>
					<td><a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable('${vo.mobile}');">${vo.realName}</a></td>
					<td>
						<c:choose>
							<c:when test="${vo.channel == 'CCDQB' }">
								诚诚普惠
							</c:when>
							<c:when test="${vo.channel == 'TFLAPP' }">
								复利小贷
							</c:when>
							<c:when test="${vo.channel == 'LYFQAPP' }">
								开心游
							</c:when>
							<c:otherwise>
								聚宝钱包
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${vo.accountId != null }">
								是
							</c:when>
							<c:otherwise>
								否
							</c:otherwise>
						</c:choose>
					</td>
					<td>${vo.idNo}</td>
					<td>${vo.mobile}</td>
					<td>${vo.cardNo}</td>
					<td>无</td>
					<td>${loanCount }</td>
					<td>无</td>
					<td>无</td>
					<td>
					<c:if test="${vo.realName != null}">
					<a href="javascript:void(0)" onclick="openTabPage('${ctx}/sys/custUser/custuserDetail?id=${vo.id}')">详情</a>
					</c:if>
					</td>
				</c:when>
			</c:choose>
			<c:if test="${none == 'none'}">
				<td>暂无客户信息</td>
			</c:if>

		</tbody>
	</table>
	<c:if test="${loan > 0}">
		<table id="table2" class="table">
			<thead>
				<tr>
					<th>标名</th>
					<th>发标时间</th>
					<th>是否满标</th>
					<th>金额</th>
					<th>合同</th>
					<th>是否已取消</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${loanList}" var="item" varStatus="xh">
					<tr>
						<td>${item.title }</td>
						<td>${item.tenderTime}</td>
						<td><c:if test="${item.isFull == true}">
								<div>是</div>
							</c:if> <c:if test="${item.isFull == false}">
								<div>否</div>
							</c:if></td>
						<td>${item.amount }</td>
						<td><a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="checkContract('${item.aremark}');">查看合同</td>
						<td>
							<c:choose>
								<c:when test="${item.isdel == true }">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
						</td>
						<td>
						<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="cancel('${item.id}');">取消标的
						<c:if test="${item.isFull == true}">
							<c:if test="${amount>0 and (amount >= 2000 or amount <= 600)}">
								<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="afreshPay('${item.id}','1');">放款598
							</c:if>
							<c:if test="${amount <= 1500 and amount > 1401}">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="afreshPay('${item.id}','2');">放款1402
							</c:if>
						</c:if>
						</td>
					</tr>
				</c:forEach>
		</table>
	</c:if>
	<script id="tpl_custUserApplyList" type="text/x-handlebars-template">
    	<jsp:include page="custUserApplyList.jsp"></jsp:include>
	</script>
	<script id="tpl_checkContract" type="text/x-handlebars-template">
    	<jsp:include page="checkContract.jsp"></jsp:include>
	</script>
	<script>
	function contentTable(mobile){
	    var param = {
	    	mobile:mobile
	    };
	        $.ajax({
	            type: "post",
	            url: "${ctx}/sys/custUser/applyListByMobile",
	            data: param,
	            dataType: "json",
	            error: function (request) {
	                alert("系统繁忙,请稍后再试");
	            },
	            success: function (data, textStatus) {	
	                if (data.code == "1") {
	                    var myTemplate = Handlebars.compile($("#tpl_custUserApplyList").html());
	                    var html = myTemplate(data);
	                    layer.open({
	                        type: 1,
	                        title: '客户订单',
	                        area: ['55%', '40%'], //宽高
	                        content: html
	                    })
	                } else {
	                    alert(data.msg)
	                }

	            },

	        });
	    };
	    
	    function checkContract(contracts){
		    	var contractList = new Array();
		    	contractList = contracts.split(",");
		    	var param = {
		    			data : contractList
		    	};
		    	var myTemplate = Handlebars.compile($("#tpl_checkContract").html());
	            var html = myTemplate(param);
	            layer.open({
	                type: 1,
	                title: '查看合同',
	                area: ['20%', '30%'], //宽高
	                content: html
	            });
		    };
		    
	    function afreshPay(lid,type){
	    	top.layer.confirm("确认要重新放款？", {icon: 3, title:'系统提示'}, function(index){
	    		var param = {
	    				lid:lid,
	    				type:type
	    		    };
   		        $.ajax({
   		            type: "post",
   		            url: "${ctx}/sys/custUser/afreshPay",
   		            data: param,
   		            dataType: "json",
   		            error: function (request) {
   		                alert("系统繁忙,请稍后再试");
   		            },
   		            success: function (data, textStatus) {	
   		                if (data.code == "OK") {
   		                	alert(data.msg)
   		                } else {
   		                    alert(data.msg)
   		                }

   		            },

   		        });
				top.layer.close(index);
			});
	    	
	    };
	    
	    function cancel(lid){
	    	top.layer.confirm("确认要取消标的？", {icon: 3, title:'系统提示'}, function(index){
	    		var param = {
	    				lid:lid
	    		    };
   		        $.ajax({
   		            type: "post",
   		            url: "${ctx}/sys/custUser/cancelLid",
   		            data: param,
   		            dataType: "json",
   		            error: function (request) {
   		                alert("系统繁忙,请稍后再试");
   		            },
   		            success: function (data, textStatus) {	
   		                if (data.code == "OK") {
   		                	alert("撤标成功")
   		                } else {
   		                    alert("撤标失败")
   		                }

   		            },

   		        });
				top.layer.close(index);
			});
	    	
	    };
	</script>
</body>
</html>