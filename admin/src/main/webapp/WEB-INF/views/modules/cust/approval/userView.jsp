<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客户信息</title>
    <link rel="stylesheet" href="${ctxStatic}/modules/cust/approval/css/tableStyle.css">
    <link rel="stylesheet" href="${ctxStatic}/modules/cust/approval/css/audit.css?t=<%=System.currentTimeMillis()%>">
    
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>   
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
</head>

<body>
    <div class="box">
        <!-- Nav tabs -->
        <ul id="myTabs" class="nav">
            <li class="active" onclick="userInfo()">客户信息</li>
            <li onclick="contactConectInfo()">通讯录</li>
        </ul>
        <!-- Tab panes -->
        <div id="con" class="tab-content">
             <div id="tabDiv"></div>
            
            
            <!--   审批 -->
                    <div class="info accraditation">
                        <div class="title">审批:</div>
                        <form id="checkForm" class="layui-form"	action="/loan/apply/check" method="post" onsubmit="return false;">
                            <div>
                                <span>审批金额:</span>
                                <input class="accItem" name="approveAmt" id="approveAmt" type="text" readonly="readonly" value="${applyInfo.approveAmt}"><label>元</label>
                            </div>
                            <div>
                             <span>审批期限:</span>
                               <input class="accItem" name="approveTerm"  type="text" readonly="readonly" value="${applyInfo.approveTerm}">
                                    <span>天</span>
                            </div>
                            <div>
                             <span>服务费率:</span>
                                <input class="accItem" name="servFeeRate"  type="text" readonly="readonly" value="${applyInfo.servFeeRate}">
                                    <span>%</span>
                            </div>
                            <div class="accIdea">审批意见:<textarea name="remark"></textarea></div>
                            
                            <input type="hidden" name="operatorId" value="${operatorId}">
                        <input id="applyId" name="applyId" type="hidden" value="${applyId}" />
                        
                        <input type="hidden" id="checkResult" name="checkResult" value="">
                        
                            <button class="accbtn btnPass" onClick="applyCheck(this.form,1)">通过</button>
                            <button class="accbtn btnReject" onClick="applyCheck(this.form,2)">拒绝</button>
                            
                        </form>
                    </div>
        </div>
    </div>

</body>

<script id="tpl_7" type="text/x-handlebars-template">
    <%--客户信息--%>
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>
    <script id="tpl_8" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="contactConnectInfo.jsp"></jsp:include>
</script>


<script type="text/javascript">
        var userId = '${userId}';
        var applyId = '${applyId}';
        var operatorId = "${operatorId}";
        var mobile = '${mobile}';
        var productId = '${productId}';
        var processStatus='${applyInfo.processStatus}';
        var approveAmt='${applyInfo.approveAmt}';
        var approveTerm='${applyInfo.approveTerm}';

        $(function () {
            //$('#con').children().eq(0).show().siblings().hide();
            $("#myTabs").on("click", "li", function (event) {
                var i = $(this).index();
                $(this).addClass('active').siblings().removeClass('active');
                //$('#con').children().eq(i).show().siblings().hide();
            })
            
        })
    </script>




<script type="text/javascript">





//客户信息
      function userInfo() {
          var param = {
              userId: userId,
              applyId: applyId,
              sign: "check",
              operatorId: operatorId
          };
          $.ajax({
              type: "post",
              url: "${ctx}/loan/apply/approvalUserDetail",
              data: param,
              dataType: "json",
              error: function (request) {
                  alert("系统繁忙,请稍后再试");
              },
              success: function (data, textStatus) {
                  if (data.code == "1") {
                      var myTemplate = Handlebars.compile($("#tpl_7").html());
                      data.data.removeDuplicateQueryVO = data.removeDuplicateQueryVO;
                      var html = myTemplate(data.data);
                      document.getElementById("tabDiv").innerHTML = html;
                  } else {
                      alert(data.msg);
                  }

              },

          });
      }

      //通讯录
      function contactConectInfo() {
          var param = {
              applyId: applyId,
              userId: userId
          };
          $.ajax({
              type: "post",
              url: "${ctx}/loan/apply/approvalContactConnectInfo",
              data: param,
              dataType: "json",
              error: function (request) {
                  alert("系统繁忙,请稍后再试");
              },
              success: function (data, textStatus) {
                  if (data.code == "1") {
                      var myTemplate = Handlebars.compile($("#tpl_8").html());
                      var html = myTemplate(data.data);
                      document.getElementById("tabDiv").innerHTML = html;
                  } else {
                      alert(data.msg);
                  }
              },

          });
      }

      function applyCheck(form,checkResult) {
      	
      	 $("#checkResult").val(checkResult);
      	
      	
      	
         
  		if(productId=='XJD'){
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
          	if(form.checkResult.value == '1' && (form.approveTerm.value == '14' || form.approveTerm.value == '15') && form.approveAmt.value>'1500'){
          		alert("14/15天产品审批金额不能超过1500");
              	return false;
          	}
          	if(form.checkResult.value == '1' && form.approveTerm.value == '28' && form.approveAmt.value!='3000'){
          		alert("28天产品审批金额只能为3000");
              	return false;
          	}
          	if(form.checkResult.value == '1' && form.approveTerm.value == '90' && form.approveAmt.value!='2000'){
          		alert("90天产品审批金额只能为2000");
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
          
         
          
          $.ajax({
				cache : true,
				type : "POST",
				url : "${ctx}/loan/apply/approve",
				data : $('#checkForm').serialize(),
				async : false,
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data) {
					if (data.code == "1") {
						alert("提交成功");
						window.location.href="${ctx}/loan/apply/approvalNext";
					} else {
						alert(data.msg)
					} 
				}
			});
          
          
          return true;
      }
      
      
      
      function submitSave() {
  		
  		var checkResu = $('input[name="checkResult"]:checked').val();
  		var servFee = $("#servFeeRate option:selected").val();
  		
  		if (servFee== '1' || servFee== '') {
          	alert("请选择服务费率");
          	return false;
      	}
  		if (checkResu != '3'){
          	alert("审批结果请选择未接电话");
          	return false;
      	}
  		/* if ($.trim($("#remark").val()) == "") {
  			  alert("审批意见不能为空");
  	            return false;
  		}  */else {
  			$.ajax({
  					cache : true,
  					type : "POST",
  					url : "/a/loan/apply/saveCheck",
  					data : $('#checkForm').serialize(),
  					async : false,
  					error : function(request) {
  						alert("系统繁忙,请稍后再试");
  					},
  					success : function(data) {
  						if (data.code == "1") {
  							alert("提交成功");
  							window.location.reload(); 
  						} else {
  							alert(data.msg)
  						} 
  					}
  				});
  		}
  	}
      
      $(function (){

                                          
          if(productId=='XJDFQ'){
  			$("#approveAmt").val('5000');
  		}else if(productId=='XJD'){
  			if(processStatus==223){
  				$("#approveAmt").val(approveAmt);
  			}else{
  				if(approveTerm==14 || approveTerm==15){
  					$("#approveAmt").val('1500');
  				}else if(approveTerm==28){
  					$("#approveAmt").val('3000');
  				}else{
  					$("#approveAmt").val('2000');
  				}
  			}    			   			    			
  		}
          
          userInfo('${userId}', '${applyId}');
      })

</script>

</html>