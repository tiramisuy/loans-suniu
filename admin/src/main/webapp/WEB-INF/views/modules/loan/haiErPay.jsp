<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>海尔代付</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/layui-master/build/css/layui.css">
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

<style type="text/css">
a {
	color: #2fa4e7;
	text-decoration: none;
}

<
style type ="text/css">.l {
	float: left;
}

.r {
	float: right;
}

.form-search .ul-form li .error {
	width: 500px; !important;
}

#collection {
	width: 1051px;
	height: 791px;
	border: 1px solid rgba(221, 221, 221, 1);
	position: relative;
}

#collections {
	height: 37px;
	line-height: 37px;
	background: rgba(242, 242, 242, 1);
}

.collection {
	margin: 100px auto 0;
	width: 867px;
	height: 377px;
	border: 1px solid #cccccc;
	overflow: hidden;
	position: relative;
}

.collections {
	width: 839px;
	height: 330px;
	background-color: rgba(236, 246, 251, 1);
	margin-top: 23px;
	margin-left: 13px;
}

.clears:after {
	content: "";
	display: block;
	clear: both;
}

.collections-top {
	height: 36px;
	line-height: 36px;
	font-size: 14px;
	width: 820px;
	margin: 0 auto;
	border-bottom: 1px solid #cccccc;
}

.collections-top div {
	float: left;
	width: 200px;
}

.collections-con {
	width: 800px;
	height: 278px;
	overflow-y: auto;
}

.collections-top em {
	color: #FF0000;
}

.collections-con div {
	font-size: 12px;
	color: #666;
	text-indent: 10px;
	height: 40px;
	line-height: 40px;
}

.collections-con div input {
	margin-right: 15px;
	margin-top: 3px;
}

.collections-con .collections-cons {
	width: 500px;
}

.collection select {
	width: 152px;
	/*height: 22px;
            font-size: 12px;*/
}
/* .collections-name,.collections-time{
            position: absolute;
            left: 422px;
            top: 68px;
        } */
.collections-time {
	left: 621px;
}

.collection-bot {
	width: 328px;
	margin: 28px auto 0;
}

.collection-bot-left,.collection-bot-right {
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

.collection-bot-right {
	background: #ffffff;
	color: #333;
}

.collections-top .wAuto {
	display: inline-block;
	width: auto;
	margin-right: 18px;
}

a {
	color: #2fa4e7;
	text-decoration: none;
}
</style>



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
		<li class="active"><a href="${ctx}/loan/repay/warnList">海尔代付</a>
		</li>


	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">运营管理</a></li>
			<!-- 	<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">催收管理</a></li> -->
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;海尔代付
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form class="breadcrumb  form-search">
		<legend>海尔代付</legend>
		
			
		<div class="control-group">
			<ul class="ul-form">
				<li>
					<label>代付渠道</label>
					<select name="channel" id="channel" onchange="showCode()">
						<option value="1">海尔代付</option>
						<option value="2">先锋代付</option>
					</select>
				</li>
			</ul>
		</div>
		
		
		 <div class="control-group">
            <ul class="ul-form">
                <li><label>姓名</label>
                    <input type="text" id="realName" name="realName" />
                </li>
            </ul>
        </div>
        
       
        
        
        <div class="control-group">
            <ul class="ul-form">
                <li><label>银行卡号</label>
                    <input type="text" id="cardNo" name="cardNo"/>
                </li>
            </ul>
        </div>
        
         <div class="control-group" id="bankCodeCont" style="display: block;">
            <ul class="ul-form">
                <li><label>银行</label>                    
                    <select id="bankCode" name="bankCode" style="width: 210px;">
                    	<option value="">全部</option>
		                <c:forEach items="${bankData}" var="item">
		                <option value="${item.key}">${item.value}</option>		                
		                </c:forEach>
                    </select> 
                    <br>                         
                </li>
            </ul>
        </div>
        
		
		
		
		 <div class="control-group">
            <ul class="ul-form">
                <li><label>金额</label>
                    <input type="text" id="amount" name="amount" />
                </li>
            </ul>
        </div>
		
		
		<div class="layui-form-item mgt-30">
			<div class="layui-input-block">
				<button type="button" class="layui-btn"
					onclick="xfWithHold()">提交</button>
				<button type="reset" class="layui-btn layui-btn-primary"
					onclick="layer.close(layer.index);">取消</button>
			</div>
		</div>
	</form>


	<script>


		function xfWithHold() {
			
			var channel = $("#channel option:selected").val();
			var realName = $("#realName").val();
			var cardNo = $("#cardNo").val();
			var bankCode = $("#bankCode").val();
			var amount =$("#amount").val();
						

			if ("" == channel) {
				layer.alert('请选择代付渠道', {
					icon : 7
				});
				return false;
			}
			if ("" == realName) {
				layer.alert('客户姓名不能为空', {
					icon : 7
				});
				return false;
			}

			if ("" == cardNo) {
				layer.alert('客户银行卡号不能为空', {
					icon : 7
				});
				return false;
			}
			if ("" == amount) {
				layer.alert('金额不能为空', {
					icon : 7
				});
				return false;
			}
			if ("" == bankCode) {
				layer.alert('银行不能为空', {
					icon : 7
				});
				return false;
			}
		
			
			//$(".layui-layer-btn0")这个是确定按钮
            $(".layui-btn").addClass("layui-btn-disabled");
            $(".layui-btn").attr('disabled',true);
          //  layer.msg('处理中...',{shade:[0.8, '#393D49'],time:60*60*1000});
			
			var param = {
					cardNo : cardNo,
					realName : realName,
					bankCode : bankCode,
					amount : amount,	
					channel:channel
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopWithHold/handHaiErPay",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						alert(data.msg);
					 	$("#cardNo").val("");
						$("#realName").val("");
						 $("#bankCode").val("");
						$("#amount").val("");
						$(".select2-chosen").html("全部");	 
						 $(".layui-btn").removeClass("layui-btn-disabled");
						 $(".layui-btn").attr('disabled',false);
						 //location.reload(true);
					} else {
						alert(data.msg)
					}
				},

			});
			
		};
		
		
	</script>
</body>
</html>