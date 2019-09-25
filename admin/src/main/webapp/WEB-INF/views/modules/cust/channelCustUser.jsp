<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>借款人分类列表</title>
    <style type="text/css">
    	.upload-popBg{ display:none; position:fixed; z-index:9919891016; left:0; top:0; right:0; bottom:0; width:100%; height:100%; opacity:0.5; filter:alpha(opacity=50); -moz-opacity: .5; background:#000;}
		.upload-popBg img{ display: block; z-index:9919891019; position:fixed; left:50%; top:50%; -webkit-transform:translate(-50%,-50%); -moz-transform:translate(-50%,-50%); -o-transform:translate(-50%,-50%); -ms-transform:translate(-50%,-50%); transform:translate(-50%,-50%); overflow: hidden; }
    	.upDateBtn{ display: inline-block; padding:0 12px; line-height: 30px; color:#ffffff; background: #3daae9; border:1px solid #ccc; cursor:pointer;}
    </style>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

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
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/custUser/channelCustUser">借款人分类列表</a></li>
</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
            <%--<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">机构用户</a></li>--%>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;借款人分类列表</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="borrowerOP" action="${ctx}/sys/custUser/channelCustUser" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>姓名</label>
            <form:input path="realName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>手机号码</label>
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
        </li>
        <li><label>身份证号</label>
            <form:input path="idNo" htmlEscape="false" maxlength="25" class="input-medium"/>
        </li>
 <%--        <c:if test="${fns:haveRole('jbqb')}">
        <li><label>渠道</label>
            <form:select path="channel" class="input-medium">
           		<form:option value="">全部</form:option>
                <c:forEach items="${channel}" var="detail">
                      <form:option value="${detail.cid}">${detail.cName}</form:option>
                </c:forEach>
            </form:select>
        </li>
        </c:if> --%>
   <%--      <c:if test="${fns:haveRole('ccd')}">
        <li><label>门店</label>
            <form:select path="remark" class="input-medium">
           		<form:option value="">全部</form:option>
                <c:forEach items="${companyList}" var="detail">
                      <form:option value="${detail.companyId}">${detail.name}</form:option>
                </c:forEach>
            </form:select>
        </li>
        </c:if> --%>
   <%--      <li><label>是否开户</label>

            <form:select path="openAccount" class="input-medium">
                <c:forEach items="${enums}" var="detail">
                    <c:choose>
                        <c:when test="${borrowerOP.openAccount == detail.value}">
                            <form:option value="${detail.value}" selected="selected">${detail.desc}</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="${detail.value}">${detail.desc}</form:option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>

        </li> --%>
        
      <%--   <li><label>是否黑名单</label>
	            <form:select path="isBlack" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="1">是</form:option>
	                <form:option value="2">否</form:option>
	            </form:select>
	        </li>
         --%>
        
        
        <li class="time_li"><label>注册时间</label>
            <form:input path="registerStart" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${borrowerOP.registerStart}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
        
            <h>-</h>
            <form:input path="registerEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${borrowerOP.registerEnd}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>渠道</th>
      <!--   <th>所属门店</th> -->
        <!-- <th>是否设置交易密码</th> -->
      <!--   <th>是否开通存管账号</th> -->
        <th>银行名称</th>
        <th>银行卡号</th>
        <!-- <th>投复利账户id</th> -->
        <!-- <th>可用余额（元）</th>
        <th>冻结金额（元）</th> -->
      <!--   <th>是否黑名单</th> -->
        <th>创建时间</th>
      <!--   <th>操作</th> -->
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="custUser" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
            <td>
                <a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable('${custUser.id}');">${custUser.realName}
            </td>
            <td>
                    ${custUser.sexStr}
            </td>
            <td>
                ${custUser.mobile}
            </td>
            <td>
                    ${custUser.idNo}
            </td>
            <td>
            	<c:forEach items="${channel}" var="detail">
	           		<c:if test="${custUser.channel == detail.cid}">
	             		   ${detail.cName}
	            	</c:if>
                </c:forEach>
            </td>
        <%--     <td>
            	<c:forEach items="${companyList}" var="detail">
	           		<c:if test="${custUser.remark == detail.companyId}">
	             		   ${detail.name}
	            	</c:if>
                </c:forEach>
            </td>	 --%>
<%--             <td>
                <c:if test="${custUser.paypwd == '是'}"><div >${custUser.paypwd}</div></c:if>
                <c:if test="${custUser.paypwd == '否'}"><div class="required">${custUser.paypwd}</div></c:if>

            </td> --%>
          <%--   <td>
                 ${custUser.accountId != null ? "已开通" : "未开通"}
            </td> --%>
            <td>${custUser.bankName}</td>
            <td>${custUser.cardNo}</td>
            <%-- <td>
                    ${custUser.accountId}
            </td> --%>
            <%-- <td>
                    ${custUser.availableBalance}
            </td>
            <td>
                    ${custUser.frozenBalance}
            </td> --%>
           <%--  <td>
                    ${custUser.blacklist == 1 ? "是" : "否"}
            </td> --%>
            <td>
                <fmt:formatDate value="${custUser.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
          <%--   <td>
                <c:if test="${(custUser.sexStr != null and custUser.sexStr != '') and (custUser.remark == storeId or (custUser.remark == null and fns:haveRole('jbqb') == true) or fns:haveRole('superRole')==true) and fns:haveRole('kefu')==false}">
                    <a href="javascript:void(0)" onclick="openTabPage('${ctx}/sys/custUser/custuserDetail?id=${custUser.id}')">详情</a>
                </c:if>
                <c:if test="${fns:haveRole('black_admin')==true}">
                 	<a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left: 5px;" onclick="insertBlickList('${custUser.id }')">加入黑名单</a>
                </c:if>
                
            </td> --%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div class="upload-popBg"><img src="${ctxStatic}/images/upload.gif" /></div>
<script id="tpl_custApplyList" type="text/x-handlebars-template">
    <jsp:include page="custApplyList.jsp"></jsp:include>
</script>
<script id="upDateFile" type="text/x-handlebars-template">
    <%--阿里云文件更新--%>
    <jsp:include page="upDateFile.jsp"></jsp:include>
</script>
<script>


function insertBlickList(userId) {
	layer.confirm('您确定要加入黑名单吗？', {
		btn : [ '提交', '取消' ]
	}, function(index) {
		insertConfirm(userId);
		layer.close(index);
	});
}

function insertConfirm(userId) {
	var param = {
			userId : userId
	};
	$.ajax({
		type : "post",
		url : "${ctx}/sys/custUser/insertBlickList",
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







function contentTable(id){
    var param = {
    	id:id
    };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/custApplyList",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {	
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_custApplyList").html());
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
    
    function upDateFile(applyId) {
    	var param = {
            	applyId: applyId,
            	type:'1'
            };
    	var myTemplate = Handlebars.compile($("#upDateFile").html());
        var html = myTemplate(param);
        layer.open({
            type: 1,
            title: '上传附件',
            area: ['420px', '240px'], //宽高
            content: html
        });
        $('#layui-layer1').css("top","200px");
    };
    
    function upDateChangeFile(applyId) {
        var param = {
        	applyId: applyId,
        	type:'2'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/checkLoanStatus",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "OK") {
                	var myTemplate = Handlebars.compile($("#upDateFile").html());
                    var html = myTemplate(param);
                    layer.open({
                        type: 1,
                        title: '重新上传附件',
                        area: ['420px', '240px'], //宽高
                        content: html
                    });
                    $('#layui-layer1').css("top","200px");
                } else {
                    layer.alert(data.msg, {icon: 7});
                }
            },

        });
    };
    
    function upDateALIYUNFile(applyId){
    	var formData = new FormData();
    	var file = document.getElementById('upDateALIYUNfile').files; 
    	if(file == null || file.length == 0){
    		alert("请选择上传文件");
    		return false;
    	}
        formData.append("myfile", document.getElementById("upDateALIYUNfile").files[0]);
        $(".upload-popBg").show();
        $.ajax({
            url: "${ctx}/loan/apply/uploadFile/" + applyId,
            type: "POST",
            data: formData,
            /**
            *必须false才会自动加上正确的Content-Type
            */
            contentType: false,
            /**
            * 必须false才会避开jQuery对 formdata 的默认处理
            * XMLHttpRequest会对 formdata 进行正确的处理
            */
            processData: false,
            success: function (data) {
            	$(".upload-popBg").hide();
                if (data.code == "OK") {
                    alert("上传成功！");
                    $("#enSureBtn").removeAttr("disabled");
                    $("#enSureBtn").removeAttr("style");
                }
                if (data.code == "NOK") {
                    alert(data.msg);
                }
            },
            error: function () {
            	$(".upload-popBg").hide();
                alert("上传失败！");
            }
        });
    };
    
    function enSureUpload(applyId) {
    	$("#enSureBtn").attr("disabled","disabled");
        var param = {
        	applyId: applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/enSureUpload",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "OK") {
                	alert(data.msg);
                	$('#layui-layer2').hide();
                	$('#layui-layer-shade2').hide();
                } else {
                	$("#enSureBtn").removeAttr("disabled");
                    alert(data.msg);
                }
            },

        });
    };
</script>
</body>



</html>



