<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户管理</title>
    <style type="text/css">
    
    	#detail_table{width:100%!important;}
    
    	#collection_content{width:80%;float:left}
    
    	#detail .collection_div{margin-bottom:10px ;width: 100%; height: 40px;}
    
    	#ConnectionInfo .layui-form-item .layui-input-inline {float: none;!important}
    
    	#xianjinContactConnectionInfo .layui-elem-field{height:458px!important}
    	
    	#ConnectionInfo .layui-elem-field{height:458px!important}
    
    	#detail .layui-elem-field{width:40%!important;}
    
    	#collectionRecord .layui-elem-field{width:100%!important}
    
        .text-blue {
            color: blue;
        }

        .required {
            color: red;
        }
        #promiseDateStr,#nextContactTimeStr{
            width: 170px;
            height: 30px;
            padding: 4px 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-top: 3px;
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
        tr td span{
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
        
         #returnBtn{
			float:right;
			height:30px;
			line-height:30px;
			border-radius:5px;
			margin-right:10px;
		}
		#btnBefore{
			float:right;
			height:30px;
			line-height:30px;
			border-radius:5px;
			margin-right:10px;
		}
		#btnAfter{
			float:right;
			height:30px;
			line-height:30px;
			border-radius:5px;
			margin-right:10px;
		}
        
        
    </style>
    <link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css" media="all">
    <link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

    <link href="${ctxStatic}/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>

<body>
<span class="layui-breadcrumb">
  <a href="${ctx}/loan/repay/pressList?stage=1">贷后管理</a>
  <a href="${ctx}/loan/repay/pressList?stage=1">催收管理</a>
  <a href="${ctx}/loan/repay/pressList?stage=1">逾期催收明细</a>
  <a><cite>催收详情</cite></a>
  	<c:if test="${hasAfter == 1}">
  		<input id="btnAfter" type="button" class="layui-btn layui-btn-info"
			onclick="goNext('${resAfter.afterItemId}','${resAfter.afterUserId}','${resAfter.afterApplyId}','${page}','1');"  value="下一个" ></input>
	</c:if>
	<c:if test="${hasBefore == 1}">
		<input id="btnBefore" type="button" class="layui-btn layui-btn-info"
			onclick="goNext('${resBefore.beforeItemId}','${resBefore.beforeUserId}','${resBefore.beforeApplyId}','${page}','-1');"  value="上一个" ></input>	
	</c:if>			
    <input class="layui-btn layui-btn-info"  type="button" id="returnBtn" name="returnBtn"  onclick="returnParent();" value="返回" />
</span>

<!-- 用于保存列表页面的条件参数 -->
<form  id="checkForm"  action="">   
	<input id="parentId" name="parentId" type="hidden" value="${parentId}"/>
	<input id="beforeOrAfter" name="beforeOrAfter" type="hidden" value="${beforeOrAfter}"/>
	<input id="index" name="index" type="hidden" value="${index}" />
	<input id="pageNo" name="pageNo" type="hidden" value="${pressOP.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${pressOP.pageSize}"/>
    <input id="status" name="status" type="hidden" value="${pressOP.status}"/>
    <input id="userName" name="userName" type="hidden" value="${pressOP.userName}"/>
    <input id="mobile" name="mobile" type="hidden" value="${pressOP.mobile}"/>
    <input id="idNo" name="idNo" type="hidden" value="${pressOP.idNo}"/>
    <input id="operatorId" name="operatorId" type="hidden" value="${pressOP.operatorId}"/>
    <input id="result" name="result" type="hidden" value="${pressOP.result}"/>
    <input id="content" name="content" type="hidden" value="${pressOP.content}"/>
    <input id="productId" name="productId" type="hidden" value="${pressOP.productId}"/>
    <div id="overdueDiv">
     <c:forEach items="${pressOP.overdue}" var="item" varStatus="status">
     	<input id="overdue[${status.index }]" name="overdue" type="hidden" value="${item}"/>
	 </c:forEach>
	</div>
	<input id="borrowStart" name="borrowStart" type="hidden" value="${pressOP.borrowStart}"/>
    <input id="borrowEnd" name="borrowEnd" type="hidden" value="${pressOP.borrowEnd}"/>
    <input id="expectStart" name="expectStart" type="hidden" value="${pressOP.expectStart}"/>
    <input id="expectEnd" name="expectEnd" type="hidden" value="${pressOP.expectEnd}"/>
    <input id="actualStart" name="actualStart" type="hidden" value="${pressOP.actualStart}"/>
    <input id="actualEnd" name="actualEnd" type="hidden" value="${pressOP.actualEnd}"/>
</form>

<!--  -->



<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
    	 <li <c:if test="${page == 2}">class="layui-this"</c:if> onclick="detail1('${userId}','${applyId}',1,'${itemId}')">催收详情</li>
        <li <c:if test="${page == 1}">class="layui-this" </c:if> onclick="userInfo('${userId}','${applyId}', '1','${overday}')">客户信息</li>
        <c:if test="${overday > 1 }">
        <li onclick="contactConectInfo('${applyId}','${userId}')">手机通讯录</li>
        </c:if>
<%--         <c:if test="${succCount > 0 and overday > 14}">
        <li onclick="contactConectInfo('${applyId}','${userId}')">手机通讯录</li>
        </c:if> --%>
        <%--<li onclick="getReportPage('${userIdNo}','${userRealName}','${userMobile}')">现金白卡报告</li>--%>
       <%--  <li <c:if test="${page == 2}">class="layui-this"</c:if> onclick="detail('${userId}','${applyId}',1,'${itemId}')">催收详情</li>
       		<li onclick="record()">催收记录</li>
          --%>


        <%--<li onclick="xianJinCardData('${applyId}')">现金白卡报告</li>
--%>
    </ul>

    <div class="layui-tab-content" style="">
        <div id="tabDiv" class="layui-tab-item layui-show"/>
    </div>
</div>

</body>

<script id="tpl_userinfo" type="text/x-handlebars-template">
    <%--客户信息--%>
    <jsp:include page="../cust/xianJinCardUserInfo.jsp"></jsp:include>
</script>
<%-- <script id="tpl_detail" type="text/x-handlebars-template">
    催收详情
    <jsp:include page="collectionDetail.jsp"></jsp:include>
</script> --%>
<script id="tpl_8" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="../cust/xianJinCardConnectInfo.jsp"></jsp:include>
</script>

<script id="tpl_detail_all" type="text/x-handlebars-template">
    <%--催收详情--%>
    <jsp:include page="collectionAllDetail.jsp"></jsp:include>
</script>

 <%--
<script id="tpl_record" type="text/x-handlebars-template">
    催收记录
    <jsp:include page="collectionRecord.jsp"></jsp:include>
</script>
--%>
<script id="tpl_contactConnectInfo" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="../cust/contactConnectInfo.jsp"></jsp:include>

</script>

<script id="tpl_16" type="text/x-handlebars-template">
    <%--现金白卡报告--%>
    <jsp:include page="../cust/xianJinCardData.jsp"></jsp:include>
</script>

<script>

    $(document).ready(function () {
    	
    	
       /*  detail('${userId}','${applyId}','','${itemId}'); */
        detail1('${userId}','${applyId}','','${itemId}');

      //  collectionRecord();
    });


    function contactConectInfo(applyId, userId) {
    	
        var param = {
            applyId: applyId,
            userId: userId,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/getXianJinCardConnectInfo",
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



    function xianJinCardData(applyId) {
    	
        var param = {
            applyId: applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/xianJinCardData",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate;
                    myTemplate = Handlebars.compile($("#tpl_16").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },
        });
    }
    ;

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
    };



    function doSubmit(){
        var type = $('#contactType').val();
        var contactId = $('#contactId').val();
        var result = $('#collection_result').val();
        var content = $('#collection_content').val();
        var promise = $('#promise').val();
        var promiseDateStr = $('#promiseDateStr').val();
        var nextContactTimeStr = $('#nextContactTimeStr').val();
        if (content == '') {
            alert("请填写催收内容");
            return false;
        }
        if (content.length > 50) {
            alert("催收内容不得超过50字");
            return false;
        }
/*         if ( type == '') {
            alert("请选择催收方式");
            return false;
        }
        if (contactId == '') {
            alert("请选择与本人关系");
            return false;
        }
        if (result == '') {
            alert("请选择催收结果");
            return false;
        }


        if (promise == '') {
            alert("请选择是否承诺付款");
            return false;
        }
        if (promise == '1' && promiseDateStr == '') {
            alert("请选择承诺付款时间");
            return false;
        }
        if (nextContactTimeStr == '') {
            alert("请选择下次跟进时间");
            return false;
        } */
        var param = {
            itemId: '${itemId}',
            contactId: contactId,
            type: type,
            result: result,
            content: content,
            promise: promise,
            promiseDateStr: promiseDateStr,
            nextContactTimeStr: nextContactTimeStr
        };
        $(".layui-btn").attr({"disabled":"disabled"});
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/saveRecord",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
                $(".layui-btn").removeAttr("disabled");
            },
            success: function (data) {
                if (data.code == "1") {

                    alert(data.msg);
                    $(".layui-btn").removeAttr("disabled");
                  /*  	detail('${userId}','${applyId}',1); */

                   	detail1('${userId}','${applyId}',1);


                } else {
                	$(".layui-btn").removeAttr("disabled");
                    alert(data.msg)
                }
            }
        });
        return false;
    };

    function userInfo(id, applyId, sign,overday) {
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
            	data.data.overday = overday;
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_userinfo").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function detail(id, applyId,num,thisTermId) {
        if(num==1){
            location.replace(location.href)
            return false;
        }
        var param = {
            userId: id,
            applyId: applyId,
            thisTermId: thisTermId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/detail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html

                    timer();

                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function detail1(id, applyId,num,thisTermId) {
        if(num==1){
            location.replace(location.href)
            return false;
        }
        var param = {
            userId: id,
            applyId: applyId,
            thisTermId: thisTermId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/detail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail_all").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html

                    timer();

                } else {
                    alert(data.msg)
                }

            },

        });
    };


    function collectionRecord() {
        var param = {
            itemId: '${itemId}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/list",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail_all").html());
                    var html = myTemplate(data.data);
                   document.getElementById("collectionRecord").innerHTML = html
                   timer();
                } else {
                    alert(data.msg)
                }

            },

        });
    };






    function record() {
        var param = {
            itemId: '${itemId}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/list",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_record").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html
                } else {
                    alert(data.msg)
                }

            },

        });
    };
    var flag=true;
    function add() {
        if (flag) {
            var html ='';
            html +='<tr data-id="4"><td></td>';
            html +='<td><input  class="add_name" maxlength="11" type="text"></td>';
            html +='<td><input  class="add_phone" maxlength="11"  type="text"></td>'
            html +='<td><select  id="relation"><option>--请选择--</option><option>本人</option><option>父母</option><option>朋友</option><option>配偶</option><option>同事</option><option>其他联系人</option></select></td>';
            html +='<td><span onclick="preservation()">保存</span>';
            $("table").append(html);
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
        if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($("input.add_phone").val()))){
            alert("手机号不正确");
            return false;
        }
        if($("#relation").val()=="--请选择--"){
            alert("请选择关系")
            return false;
        }
        var data={
            userId:'${userId}',
            applyId:'${applyId}',
            name:$("input.add_name").val(),
            mobile:$("input.add_phone").val(),
            relation:$("#relation").val(),
            source:2
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
                    userInfo('${userId}','${applyId}', '1',1);
                } else {
                    alert(data.msg)
                }

              //  collectionRecord();	//保存成功刷新催收记录

            },
        });
    }

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
                    userInfo('${userId}','${applyId}', '1',1);
                } else {
                    alert(data.msg)
                }

            },
        });
    }

    function timer(){
        layui.use(['form', 'element'], function () {
            var $ = layui.jquery
                    , element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

            var form = layui.form();
            form.on('select(type)', function(data) {
                if(data.value == '1'){
                    $('#promiseDateDiv').show();
                }else{
                    $('#promiseDateDiv').hide();
                }
            });
        });
    }
    
    
 	function returnParent(){
    	var parentId = $("#parentId").val();
    	if(parentId != "" && parentId != "undefined"  ){
    		var info = this.parent.getCollectionPage(parentId);
	   	 	fillForm(info);
    		this.parent.closeTab();
 	    	this.parent.freshTab(parentId);
    	}	
		var searchFormUrl = $("#checkForm").serialize();
		var userNameEncode = "";
		if($("#userName").val() != null && $("#userName").val() != ""){
			userNameEncode = encodeURI(encodeURI($("#userName").val()));
		}
		var contentEncode = "";
		if($("#content").val() != null && $("#content").val() != ""){
			contentEncode = encodeURI(encodeURI($("#content").val()));
		}
    	searchFormUrl= decodeURIComponent(searchFormUrl,true);
    	var url = '${ctx}/loan/collection/pressList?'+searchFormUrl+'&userNameEncode='+userNameEncode+'&contentEncode='+contentEncode;
    	window.location = url ;
    	
    }
    
 	function fillForm(info){
    	var pageNo = info["pageNo"];
    	$("#pageNo").val(pageNo);
    	var pageSize = info["pageSize"];
    	$("#pageSize").val(pageSize);
    	var status = info["status"];
    	$("#status").val(status);
    	var userName = info["userName"];
    	$("#userName").val(userName);
    	var mobile = info["mobile"];
    	$("#mobile").val(mobile);
    	var idNo = info["idNo"];
    	$("#idNo").val(idNo);
    	var operatorId = info["operatorId"];
    	$("#operatorId").val(operatorId);
    	var result = info["result"];
    	$("#result").val(result);
    	var content = info["content"];
    	$("#content").val(content);
    	var productId = info["productId"];
    	$("#productId").val(productId);
    	var overdue = info["overdue"];
    	$("#overdueDiv").empty();
    	$.each(overdue,function(index,value){
    	     $("#overdueDiv").append("<input id='overdue["+index+"]' name='overdue' type='hidden' value='"+value+"'/>");
    	});
    	var borrowStart = info["borrowStart"];
    	$("#borrowStart").val(borrowStart);
		var borrowEnd = info["borrowEnd"];
		$("#borrowEnd").val(borrowEnd);    	
		var expectStart = info["expectStart"];
		$("#expectStart").val(expectStart);		
		var expectEnd = info["expectEnd"];
		$("#expectEnd").val(expectEnd);		
		var actualStart = info["actualStart"];
		$("#actualStart").val(actualStart);
		var actualEnd = info["actualEnd"];
		$("#actualEnd").val(actualEnd);		
    	
    }
 	
 	
 	
	function goNext(itemId,userId,applyId,page,beforeOrAfter){
		$("#beforeOrAfter").val(beforeOrAfter);
		var searchFormUrl = $("#checkForm").serialize();
		searchFormUrl= decodeURIComponent(searchFormUrl,true);
		var userNameEncode = "";
		if($("#userName").val() != null && $("#userName").val() != ""){
			userNameEncode = encodeURI(encodeURI($("#userName").val()));
		}
		var contentEncode = "";
		if($("#content").val() != null && $("#content").val() != ""){
			contentEncode = encodeURI(encodeURI($("#content").val()));
		}
		var url = '${ctx}/loan/collection/pressFrom?itemId='+itemId+'&userId='+userId+'&applyId='+applyId+'&page='+page+'&'+searchFormUrl+'&userNameEncode='+userNameEncode+'&contentEncode='+contentEncode;
		window.location = url ;
		
	}
    
    
</script>
</html>
