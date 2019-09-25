<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>借款人详情</title>
    <style type="text/css">
        .text-blue {
            color: blue;
        }

        .required {
            color: red;
        }

        .hand {
            cursor: hand;
        }
        .ruleDate {
            display: none;
        }
        .snh {
        }
        #showAll{
            cursor: pointer;
        }
        .add{
            width:59px;
            height: 28px;
            line-height: 28px;
            color: #fff;
            background: rgba(25, 158, 216, 1);
            text-align: center;
            font-size: 14px;
            cursor: pointer;
        }
        
        tr td input{
            width: 144px;
            height: 25px;
        }
        tr td select{
            width: 110px;
            height: 29px;

        }
        #contactSub{
            display: inline-block;
            width: 50px;
            height: 20px;
            line-height: 20px;
            text-align: center;
            background: rgba(25, 158, 216, 1);
            cursor: pointer;
            color: #fff;
            font-size: 12px;
        }
        #relation{
            display: block;
        }
		
		.upload-popBg{ display:none; position:fixed; z-index:9919891016; left:0; top:0; right:0; bottom:0; width:100%; height:100%; opacity:0.5; filter:alpha(opacity=50); -moz-opacity: .5; background:#000;}
		.upload-popBg img{ display: block; z-index:9919891019; position:fixed; left:50%; top:50%; -webkit-transform:translate(-50%,-50%); -moz-transform:translate(-50%,-50%); -o-transform:translate(-50%,-50%); -ms-transform:translate(-50%,-50%); transform:translate(-50%,-50%); overflow: hidden; }
    	.upDateBtn{ display: inline-block; padding:0 12px; line-height: 30px; color:#ffffff; background: #3daae9; border:1px solid #ccc; cursor:pointer;}
    </style>
</head>
<link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css" media="all">
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.form.js"></script>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

<body>
<span class="layui-breadcrumb">
  <a href="${ctx}/sys/custUser/custuserList">客户管理</a>
  <a href="${ctx}/sys/custUser/custuserList">借款人列表</a>
  <a><cite>详情</cite></a>
</span>

<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this" onclick="userInfo('${vo.userInfoVO.id}','${applyId}', '${sign}')">客户信息</li>
        <c:if test="${sign == 'check' or sign == 'detail' }">
            <li onclick="contactConectInfo('${applyId}','${vo.userInfoVO.id}')">
                	通讯录
            </li>
        </c:if>
        <c:if test="${sign == 'check' or sign == 'detail' }">
        	<li onclick="reportData('${applyId}','1')">资信云一</li>
        	<li onclick="reportData('${applyId}','2')">资信云二</li>
        </c:if>
        <c:if test="${(sign == 'check' or sign == 'detail') and productId=='XJDFQ' }">
        	<li onclick="getCreditcardReport('${vo.userInfoVO.id}')">信用卡报告</li>
        </c:if>
        <li onclick="locationInfo('${vo.userInfoVO.id}')">定位信息</li>
     <%--    <c:if test="${sign == 'check'}">
        	<li onclick="approveView('${applyId}','${vo.userInfoVO.id}','${vo.userInfoVO.realName}','${vo.userInfoVO.mobile}')">审批</li>
        </c:if> --%>
        <c:if test="${sign == 'user' or sign == 'check' or sign == 'detail'}">
            <li onclick="cardinfoView('${vo.userInfoVO.id}')">银行卡信息</li>
        </c:if>
        <c:if test="${sign == 'check' or sign == 'detail' }">
            <li onclick="getReportPage('${vo.userInfoVO.idNo}','${vo.userInfoVO.realName}','${vo.userInfoVO.mobile}')">
                资信云报告
            </li>
        </c:if>
        <c:if test="${sign == 'check' or sign == 'detail' }">
            <li onclick="contactApply('${applyId}','${vo.userInfoVO.id}','${vo.userInfoVO.realName}','${vo.userInfoVO.mobile}')">
                查重信息
            </li>
        </c:if>
        <li onclick="authInfo('${vo.userInfoVO.id}')">认证记录</li>
        <c:if test="${fns:haveRole('xinshen') == true and sign == 'check'}">
        <li onclick="uploadCuishou('${vo.userInfoVO.idNo}','${vo.userInfoVO.realName}','${vo.userInfoVO.mobile}','${applyId}')">开通催收指标</li>
        </c:if>
    </ul>

    <div class="layui-tab-content" style="">
        <div id="tabDiv" class="layui-tab-item layui-show"/>
    </div>
</div>

<%-- <c:if test="${sign == 'check' or sign == 'detail'}"> --%>
    <!--大数据审批结果-->
    <blockquote class="layui-elem-quote mgt-20">大数据审批结果</blockquote>
    <c:if test="${!empty vo.juqianbao}">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="100">
                <col width="300">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th colspan="5">进件大纲</th>
            </tr>
            <tr>
                <th>征信厂商</th>
                <th>编号</th>
                <th>风险等级</th>
                <th>规则</th>
                <th>不符说明</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${vo.juqianbao}" varStatus="xh">
            <tr>
                <td>${item.name}</td>
                <td>${item.ruleCode}</td>
                <c:choose> 
     				<c:when test="${item.riskRank == '高'}">
     					<td style="color:red;">${item.riskRank}</td>
 					</c:when>
 					<c:when test="${item.riskRank == '审批通过'}">
     					<td style="color:green;">${item.riskRank}</td>
 					</c:when>
     				<c:otherwise>
     					<td>${item.riskRank}</td>
  					</c:otherwise> 
				</c:choose>
                <td>${item.ruleName}</td>
                <td>${item.remark}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${!empty vo.juqianbaoBlackList}">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="100">
                <col width="300">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th colspan="5">黑名单</th>
            </tr>
            <tr>
                <th>征信厂商</th>
                <th>编号</th>
                <th>风险等级</th>
                <th>规则</th>
                <th>不符说明</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${vo.juqianbaoBlackList}" varStatus="xh">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.ruleCode}</td>
                    <c:choose> 
     					<c:when test="${item.riskRank == '高'}">
     						<td style="color:red;">${item.riskRank}</td>
 						</c:when>
     					<c:otherwise>
     						<td>${item.riskRank}</td>
  						</c:otherwise> 
					</c:choose>
                    <td>${item.ruleName}</td>
                    <td>${item.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${!empty vo.thirdBlackList}">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="100">
                <col width="300">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th colspan="5">三方黑名单</th>

            </tr>
            <tr>
                <th>征信厂商</th>
                <th>编号</th>
                <th>风险等级</th>
                <th>规则</th>
                <th>不符说明</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${vo.thirdBlackList}" varStatus="xh">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.ruleCode}</td>
                    <c:choose> 
     					<c:when test="${item.riskRank == '高'}">
     						<td style="color:red;">${item.riskRank}</td>
 						</c:when>
     					<c:otherwise>
     						<td>${item.riskRank}</td>
  						</c:otherwise> 
					</c:choose>
                    <td>${item.ruleName}</td>
                    <td>${item.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${!empty vo.third}">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="100">
                <col width="300">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th colspan="5">三方数据</th>
            </tr>
            <tr>
                <th>征信厂商</th>
                <th>编号</th>
                <th>风险等级</th>
                <th>规则</th>
                <th>不符说明</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${vo.third}" varStatus="xh">
                <tr <c:if test="${item.sort > 3 or item.sort < 0}">class="ruleDate"</c:if>>
                    <td>${item.name}</td>
                    <td>${item.ruleCode}</td>
                    <c:choose> 
     					<c:when test="${item.riskRank == '高'}">
     						<td style="color:red;">${item.riskRank}</td>
 						</c:when>
     					<c:otherwise>
     						<td>${item.riskRank}</td>
  						</c:otherwise> 
					</c:choose>
                    <td>${item.ruleName}</td>
                    <td>${item.remark}</td>
                </tr>

                <c:if test="${item.sort == -1}">
                    <tr class="snh">
                        <td>...</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
                <c:if test="${vo.more == 1 and xh.last}">
                    <tr>
                        <td colspan="5" class="text-blue" style="text-align:center" id="showAll">展开></td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </c:if>


<div class="upload-popBg"><img src="${ctxStatic}/images/upload.gif" /></div>

</body>
<script id="tpl_1" type="text/x-handlebars-template">
    <%--弹窗1--%>
    <jsp:include page="repayList.jsp"></jsp:include>
</script>
<script id="tpl_2" type="text/x-handlebars-template">
    <%--影像信息--%>
    <jsp:include page="fileinfo.jsp"></jsp:include>
</script>
<script id="tpl_3" type="text/x-handlebars-template">
    <%--银行卡信息--%>
    <jsp:include page="cardinfo.jsp"></jsp:include>
</script>
<script id="tpl_4" type="text/x-handlebars-template">
    <%--借款信息--%>
    <jsp:include page="borrowlist.jsp"></jsp:include>
</script>
<script id="tpl_5" type="text/x-handlebars-template">
    <%--资金账户--%>
    <jsp:include page="account.jsp"></jsp:include>
</script>
<script id="tpl_6" type="text/x-handlebars-template">
    <%--查重信息--%>
    <jsp:include page="contactApplyFor.jsp"></jsp:include>
</script>
<script id="tpl_7" type="text/x-handlebars-template">
    <%--客户信息--%>
    <jsp:include page="userApplyInfo.jsp"></jsp:include>
</script>
<script id="tpl_8" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="contactConnectInfo.jsp"></jsp:include>
</script>
<script id="tpl_9" type="text/x-handlebars-template">
    <%--定位信息--%>
    <jsp:include page="locationInfo.jsp"></jsp:include>
</script>
<script id="tpl_10" type="text/x-handlebars-template">
    <%--认证信息--%>
    <jsp:include page="authInfo.jsp"></jsp:include>
</script>
<script id="upDateFile" type="text/x-handlebars-template">
    <%--阿里云文件更新--%>
    <jsp:include page="upDateFile.jsp"></jsp:include>
</script>
<script id="tpl_11" type="text/x-handlebars-template">
    <%--资信云1--%>
    <jsp:include page="reportData1.jsp"></jsp:include>
</script>
<script id="tpl_12" type="text/x-handlebars-template">
    <%--资信云2--%>
    <jsp:include page="reportData2.jsp"></jsp:include>
</script>
<script id="tpl_13" type="text/x-handlebars-template">
    <%--审批预览--%>
    <jsp:include page="approveView.jsp"></jsp:include>
</script>
<script id="tpl_14" type="text/x-handlebars-template">
    <%--信用卡邮箱报告--%>
    <jsp:include page="emailReportData.jsp"></jsp:include>
</script>
<script id="tpl_15" type="text/x-handlebars-template">
    <%--网银报告--%>
    <jsp:include page="bankReportData.jsp"></jsp:include>
</script>
<script>
    $(document).ready(function () {
        userInfo('${vo.userInfoVO.id}', '${applyId}', '${sign}');
        
   		$("#checkForm").submit(function() {
   			$("#btnCheckSubmit").addClass("layui-btn-disabled"); 
   			$("#btnCheckSubmit").attr("disabled",'disabled');
		});
		
		var processStatus='${applyInfo.processStatus}';
		var productId='${productId}';
		var approveAmt='${applyInfo.approveAmt}';
		approveAmt=parseFloat(approveAmt).toFixed(0);
		if(productId=='TFL' || productId=='LYFQ'){
			$("#approveAmt").val(approveAmt);
			$("#repayTerm").val('${applyInfo.term}');
			
			$("#approveAmt").blur(function(){
				var bigAmt=smalltoBIG($("#approveAmt").val());
				$("#bigAmt").text(bigAmt);
			});
		}else if(productId=='CCD'){
			$("#approveAmt").val(approveAmt);
			$("#repayTerm").val('${applyInfo.term}');
		}else if(productId=='XJDFQ'){
			$("#approveAmt").val(approveAmt);
			$("#repayTerm").val('${applyInfo.term}');
			var servFeeRate='${applyInfo.servFeeRate}';
			$("#servFeeRate").val(parseFloat(servFeeRate).toFixed(2));
		}else if(productId=='XJD'){
			if(processStatus==223){
				$("#approveAmt").val(approveAmt);
			}else{
				$("#approveAmt").val('1300');
			}
			$("#approveTerm").val('${applyInfo.approveTerm}');
			
			var servFeeRate='${applyInfo.servFeeRate}';
			$("#servFeeRate").val(parseFloat(servFeeRate).toFixed(2));
		}
    });

    $(function () {
        $("#upload").click(function () {
        	var formData = new FormData();
        	var file = document.getElementById('file1').files; 
        	if(file == null || file.length == 0){
        		alert("请选择上传文件");
        		return false;
        	}
            formData.append("myfile", document.getElementById("file1").files[0]);
            $(".upload-popBg").show();
            $("#upload").attr("disabled",'disabled');
            $.ajax({
                url: "${ctx}/loan/apply/uploadFile/" + $("#applyId").val(),
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
                	$("#upload").removeAttr("disabled");
                	$(".upload-popBg").hide();
                    if (data.code == "OK") {
                        alert("上传成功！");
                    }
                    if (data.status == "NOK") {
                        alert(data.msg);
                    }
                },
                error: function () {
                	$("#upload").removeAttr("disabled");
                	$(".upload-popBg").hide();
                    alert("上传失败！");
                }
            });
        });
    });
    
    function uploadContactFile(userId){
    	var file = document.getElementById('ContactFile').files; 
    	if(file == null || file.length == 0){
    		alert("请选择上传文件");
    		return false;
    	}
    	var formData = new FormData();
    	for(i=0;i<file.length;i++){      
    	 formData.append("file["+i+"]", file[i]);
    	}   
        $(".upload-popBg").show();
        $("#uploadContactFile").attr("disabled",'disabled');
        $.ajax({
            url: "${ctx}/loan/apply/uploadContactFile/" + userId,
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
            	$("#uploadContactFile").removeAttr("disabled");
            	$(".upload-popBg").hide();
                if (data.code == "OK") {
                    alert("上传成功");
                    location.assign(location);
                }
                if (data.code == "NOK") {
                    alert(data.msg);
                }
            },
            error: function () {
            	$("#uploadContactFile").removeAttr("disabled");
            	$(".upload-popBg").hide();
                alert("上传失败！");
            }
        });
    };
    
    function deleteContactFile(id){
    	var param = {
            id: id
       	};
        $(".upload-popBg").show();
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/deleteContactFile",
            data: param,
            dataType: "json",
            error: function (request) {
            	$(".upload-popBg").hide();
                alert("系统繁忙,请稍后再试");
            },
            success: function (data) {
            	$(".upload-popBg").hide();
                if (data.code == "OK") {
                    alert("删除成功");
                    location.assign(location);
                }
                if (data.code == "NOK") {
                    alert(data.msg);
                }
            },

        });
    };
    
    function applyCheck(form,productId) {
        if (form.checkResult.value == '0' && form.refuseId.value == '') {
            alert("请选择拒绝原因");
            return false;
        }
		if(productId=='TFL' || productId=='LYFQ'){
			if (form.checkResult.value == '1' && form.approveAmt.value == '') {
            	alert("请填写审批金额");
            	return false;
        	}
        	var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
     		if (form.checkResult.value == '1' && (!regAmt.test(form.approveAmt.value) || form.approveAmt.value == '0')) {
          		alert("请填写正确的审批金额");
          		return false;
     		}
			if (form.checkResult.value == '1' && form.repayTerm.value == '') {
            	alert("请选择审批期数");
            	return false;
        	}
        	if (form.checkResult.value == '1' && form.borrowType.value == '') {
            	alert("请选择标的类型");
            	return false;
        	}
		}else if(productId=='CCD'){
			if (form.checkResult.value == '1' && form.approveAmt.value == '') {
            	alert("请选择审批金额");
            	return false;
        	}
			if (form.checkResult.value == '1' && form.repayTerm.value == '') {
            	alert("请选择审批期数");
            	return false;
        	}
        	if (form.checkResult.value == '1' && form.borrowType.value == '') {
            	alert("请选择标的类型");
            	return false;
        	}
		}else if(productId=='XJD'){
        	if (form.checkResult.value == '1' && form.approveAmt.value == '') {
            	alert("请填写审批金额");
            	return false;
        	}
        	//var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
        	var regAmt = /^[1-9]\d*00$/;
     		if (form.checkResult.value == '1' && (!regAmt.test(form.approveAmt.value) || form.approveAmt.value == '0')) {
          		alert("请填写正确的审批金额");
          		return false;
     		}
     		if (form.checkResult.value == '1' && (form.approveAmt.value<500 || form.approveAmt.value>3000)) {
          		alert("审批金额为500-3000");
          		return false;
     		}
     		if (form.checkResult.value == '1' && form.approveTerm.value == '') {
            	alert("请选择审批期限");
            	return false;
        	}
        	if (form.servFeeRate.value == '1' && form.servFeeRate.value == '') {
            	alert("请选择服务费率");
            	return false;
        	}
        }
        if(productId=='XXX'){
        	var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
     		if (form.checkResult.value == '1' && (!regAmt.test(form.approveAmt.value) || form.approveAmt.value == '0')) {
          		alert("请填写正确的审批金额");
          		return false;
     		}
        	var regTerm = /^[0-9]{1,3}$/;
     		if (form.checkResult.value == '1' && (!regTerm.test(form.approveTerm.value) || form.approveTerm.value=='0')) {
          		alert("请填写正确的审批期限");
          		return false;
     		}
        }
        if (form.remark.value == '') {
            alert("请填写审批意见");
            return false;
        }
        return true;
    }

    function userInfo(id, applyId, sign) {
        var param = {
            id: id,
            applyId: applyId,
            sign: sign
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/userDetail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_7").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }

            },

        });
    }

    function contentTable_1(contNo, applyId, productId) {
        var param = {
            contNo: contNo,
            applyId: applyId,
            productId: productId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/contentTable",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_1").html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        area: ['95%', '80%'], //宽高
                        content: html,
                        offset: '100px'
                    })
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;
    
    function upDateFile(applyId) {
        var param = {
        	applyId: applyId
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
                	$('#layui-layer1').hide();
                	$('#layui-layer-shade1').hide();
                } else {
                    alert(data.msg);
                }
            },

        });
    };
    
    function getReportPage(idNo, name, mobile) {
        var param = {
            idNo: idNo,
            name: name,
            mobile: mobile
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/getReportPage",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    window.open(data.data);
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;

    function fileinfoView(userId, idNo, realName) {
        var param = {
            userId: userId,
            idNo: idNo,
            realName: realName
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/fileinfo",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_2").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }

            },

        });
    }
    ;

    function cardinfoView(userId) {
        var param = {
            userId: userId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/cardinfo",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_3").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;

    function borrowView(userId) {
        var param = {
            userId: userId,
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/borrowlist",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_4").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;

    function accountView(accountId) {
        var param = {
            accountId: accountId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/account",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_5").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;

    function contactApply(applyId, userId, userName, mobile) {
        var param = {
            applyId: applyId,
            userId: userId,
            userName: userName,
            mobile: mobile,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/removeDuplicateQuery",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_6").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;
    
    function contactConectInfo(applyId, userId) {
        var param = {
            applyId: applyId,
            userId: userId,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/getContactConnectInfo",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_8").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;
    
    function locationInfo(userId) {
        var param = {
        	userId: userId,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/area/getLocationInfo",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_9").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;
    
    function authInfo(userId) {
        var param = {
        	userId: userId,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/getAuthList",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_10").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    };
    
    function uploadCuishou(idNo,name,mobile,applyId){
		var result = confirm('您确定开通催收指标吗？');  
    	if(result){  
        	uploadCuishouConfirm(idNo,name,mobile,applyId);
    	}
	}
    function uploadCuishouConfirm(idNo,name,mobile,applyId) {
        var param = {
        	idNo: idNo,
            name: name,
            mobile: mobile,
            applyId:applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/uploadCuishou",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                     alert(data.msg);
                } else {
                    alert(data.msg);
                }
            },

        });
    };

    $('#showAll').on('click',function(){
        if(!$(this).hasClass('all')){
            $('.ruleDate').show();
            $('.snh').hide();
            $(this).addClass('all').text('收起<');
        }else{
            $('.ruleDate').hide();
            $('.snh').show();
            $(this).removeClass('all').text('展开>');
        }
    })

    layui.use(['form', 'element'], function () {
        var $ = layui.jquery
                , element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

        var form = layui.form();
    });
    
    /** 数字金额大写转换(可以处理整数,小数,负数) */    
    function smalltoBIG(n)     
    {    
        var fraction = ['角', '分'];    
        var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
        var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
        var head = n < 0? '欠': '';    
        n = Math.abs(n);    
      
        var s = '';    
      
        for (var i = 0; i < fraction.length; i++)     
        {    
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
        }    
        s = s || '整';    
        n = Math.floor(n);    
      
        for (var i = 0; i < unit[0].length && n > 0; i++)     
        {    
            var p = '';    
            for (var j = 0; j < unit[1].length && n > 0; j++)     
            {    
                p = digit[n % 10] + unit[1][j] + p;    
                n = Math.floor(n / 10);    
            }    
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
        }    
        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
    } 
    
    function delAuth(userId,status){
		layer.confirm('您确定删除该认证记录？', {
			btn: ['提交','取消']
		}, function(index){
			delAuthConfirm(userId,status);
			layer.close(index);
		});
	};
	function delAuthConfirm(userId,status){
	    var param = {
	    	userId:userId,
	    	status:status
	    };
	    $.ajax({
	        type: "post",
	        url: "${ctx}/sys/custUser/delAuthByStatus",
	        data: param,
	        dataType: "json",
	        error: function (request) {
	            alert("系统繁忙,请稍后再试");
	        },
	        success: function (data, textStatus) {
	            if (data.code == "1") {
	                location.reload(true);
	            } else {
	                alert(data.msg);
	            }
	        },
	
	    });
	};
    
    function reportData(applyId, type) {
        var param = {
            applyId: applyId,
            type:type
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/reportData",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                	var myTemplate;
                	if(type==1){
                		myTemplate=Handlebars.compile($("#tpl_11").html());
                	}else{
                		myTemplate=Handlebars.compile($("#tpl_12").html());
                	}
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    };
    
    function approveView(applyId, userId, userName, mobile) {
        var param = {
        	applyId:applyId,
            userId: userId,
            userName:userName,
            mobile:mobile
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/approveView",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                	var myTemplate=Handlebars.compile($("#tpl_13").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    
    var flag=true;
    function add() {
        if (flag) {
            var html ='';
            html +='<tr data-id="4"><td></td>';
            html +='<td><input  class="add_name" maxlength="11" type="text"></td>';
            html +='<td><input  class="add_phone" maxlength="11" type="text"></td>'
            html +='<td><select  id="relation"><option>--请选择--</option><option>本人</option><option>父母</option><option>朋友</option><option>配偶</option><option>同事</option><option>其他联系人</option></select></td>';
            html +='<td></td><td></td><td></td>'
            html +='<td><span id="contactSub" onclick="preservation()">保存</span>';
            $("#contactTable").append(html);
            flag=false;
        }else{
            alert("您有未保存的添加记录哦")
        }
    }

    //保存
    function preservation(){
        if($("input.add_name").val()==""){
            alert("请填写联系人姓名");
            return false;
        }
        if($("input.add_phone").val()==""){
            alert("请填写联系人联系方式");
            return false;
        }
        if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test($("input.add_phone").val()))){ 
            alert("手机号不正确");
            return false; 
        } 
        if($("#relation").val()=="--请选择--"){
            alert("请选择关系")
            return false;
        }
        var data={
            userId:'${vo.userInfoVO.id}',
            applyId:'${applyId}',
            name:$("input.add_name").val(),
            mobile:$("input.add_phone").val(),
            relation:$("#relation").val(),
            source:3
        }
        console.log(data)
        $.ajax({
            url: "${ctx}/sys/custUser/saveCollectionContact",
            type: 'post',
            data: data,
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    flag=true;
                    userInfo('${vo.userInfoVO.id}','${applyId}', '${sign}');
                } else {
                    alert(data.msg)
                }

            },
        });
    };
    
    //删除
    function remove(e){
        var id=$(e).parents("tr").attr("data-id");
        $.ajax({
            url: "${ctx}/sys/custUser/deleteCollectionContact",
            type: 'post',
            data: {id:id},
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    userInfo('${vo.userInfoVO.id}','${applyId}', '${sign}');
                } else {
                    alert(data.msg)
                }

            },
        });
    }
    
    //信用卡报告
    function getCreditcardReport(userId) {
        var param = {
            userId: userId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/getCreditcardReport",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                   	var myTemplate;
                   	var html;
                	if(data.data.type==1){
                		myTemplate=Handlebars.compile($("#tpl_14").html());
                		html = myTemplate(data.data);
                	}else{
                		myTemplate=Handlebars.compile($("#tpl_15").html());
                		html = myTemplate(data.data.reportData.creditcard);
                	}
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    };

</script>
</html>