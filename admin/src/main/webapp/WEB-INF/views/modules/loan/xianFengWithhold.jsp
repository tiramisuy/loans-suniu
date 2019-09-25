<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>手动代扣</title>
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

.l {
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
		<li class="active"><a href="${ctx}/loan/repay/warnList">手动代扣</a>
		</li>


	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">运营管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;手动代扣
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form class="breadcrumb  form-search" id="breadcrumb">
		<legend>手动代扣</legend>

		<div class="control-group">
			<ul class="ul-form">
				<li>
					<label>代扣渠道</label>
					<select name="channel" id="channel" onchange="showCode()">
						<option value="tonglian">通联代扣</option>
					</select>
				</li>
			</ul>
		</div>


		<div class="control-group" style="display: block;" id="bDefault" >
			<ul class="ul-form">
			 <li>
				<label class="control-label">默认代扣人：</label>
				<select name="dName" id="dName" onchange="showDefault();" style="width:150px">
					<option value="0">选择默认代扣人 </option>
					<option value="1">谢轩-招商银行</option>
					<%--<option value="GDB">廖明成-广发银行</option>--%>
					<%--<option value="CCB">廖明成-建设银行</option>--%>
					<%--<option value="4">胡小曼-建设银行</option>--%>
					<%--<option value="5">程梦娇-建设银行</option>--%>
					<%--<option value="6">蓝丽明-建设银行</option>--%>
					<%--<option value="7">张传波-建设银行</option>--%>
					<%--<option value="8">刘壮-建设银行</option>--%>
					<%--<option value="9">谭书楷-工商银行</option>--%>
					<%--<option value="10">李丛丛-建设银行</option>--%>
					<%--<option value="11">邓学军-建设银行</option>--%>
					<%--<option value="12">谭晓祥-建设银行</option>--%>
					<%--<option value="13">覃贵兰-建设银行</option>--%>
					<%--<option value="14">林小琪-建设银行</option>--%>
				</select>
			</li>
			</ul>
			</div>
		</div>




		 <div class="control-group">
            <ul class="ul-form">
                <li><label>姓名</label>
                    <input type="text" id="userName" name="userName" />
                </li>
            </ul>
        </div>

         <div class="control-group">
            <ul class="ul-form">
                <li><label>身份证</label>
                    <input type="text" id="idNo" name="idNo"/>
                </li>
            </ul>
        </div>

         <div class="control-group">
            <ul class="ul-form">
                <li><label>电话</label>
                    <input type="text" id="mobile" name="mobile"/>
                </li>
            </ul>
        </div>

        <div class="control-group">
            <ul class="ul-form">
                <li><label>银行卡号</label>
                    <input type="text" id="bankCard" name="bankCard"/>
                </li>
            </ul>
        </div>

		<div class="control-group" style="display: block;" id="bCode">
			<ul class="ul-form">
			 <li>
				<label class="control-label">银行编码：</label>
				<select name="bankCode" id="bankCode" style="width: 200px;">
					<option value="ICBC">中国工商银行</option>
					<option value="ABC">中国农业银行</option>
					<option value="CCB">中国建设银行</option>
					<option value="BOC">中国银行</option>
				    <option value="BCOM">中国交通银行</option>
	     			<option value="CIB">兴业银行</option>
	      			<option value="CITIC">中信银行</option>
			       	<option value="CEB">中国光大银行</option>
			        <option value="PAB">平安银行</option>
			        <option value="PSBC">中国邮政储蓄银行</option>
			        <option value="SHB">上海银行</option>
			        <option value="SPDB">浦东发展银行</option>
			        <option value="CMBC">民生银行</option>
			        <option value="CMB">招商银行</option>
			        <option value="GDB">广发银行</option>
					<option value="HXB">华夏银行</option>
					<option value="HZB">杭州银行</option>
					<option value="BOB">北京银行</option>
					<option value="NBCB">宁波银行</option>
					<option value="JSB">江苏银行</option>
					<option value="ZSB">浙商银行</option>
				</select>
			</li>
			</ul>
			</div>
		</div>


		 <div class="control-group">
            <ul class="ul-form">
                <li><label>代扣金额</label>
                    <input type="text" id="withholdAmt" name="withholdAmt" />
                </li>
            </ul>
        </div>

		<div class="control-group">
			<ul class="ul-form">
				<li><label>备注</label>
				   <input type="text" id="remark" name="remark" value="${remark}"/>
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

		function showCode(){
			var channel = $("#channel option:selected").val();
			if("2"==channel){
				$("#bCode").css('display','block');
				$("#bDefault").css('display','block');
			}
			if("1"==channel){
				$("#bCode").css('display','none');
				$("#bDefault").css('display','none');

				$("#userName").val("");
				$("#idNo").val("");
				$("#mobile").val("");
				$("#bankCard").val("");
			}
		}

		function showDefault(){
			var dName = $("#dName option:selected").val();
			if("0"==dName){
				$("#breadcrumb")[0].reset();
			} else if("1"==dName) {
                $("#userName").val("谢轩");
                $("#idNo").val("430102198310101013");
                $("#mobile").val("18611807455");
                $("#bankCard").val("6214850101931852");
                $("#bankCode").val("CMB").trigger("change");
            }

            /*
            else if("4"==dName) {
                $("#userName").val("胡小曼");
                $("#idNo").val("421125199308157964");
                $("#mobile").val("13545283228");
                $("#bankCard").val("6217002870076194784");
                $("#bankCode").val("CCB").trigger("change");
            } else if("5"==dName) {
                $("#userName").val("程梦娇");
                $("#idNo").val("420923199103062523");
                $("#mobile").val("18565758368");
                $("#bankCard").val("6236682870008549037");
                $("#bankCode").val("CCB").trigger("change");
            } else if("6"==dName) {
                $("#userName").val("蓝丽明");
                $("#idNo").val("42102319940816002X");
                $("#mobile").val("13164104815");
                $("#bankCard").val("6236682870005844282");
                $("#bankCode").val("CCB").trigger("change");
            }else if("7"==dName) {
                $("#userName").val("张传波");
                $("#idNo").val("411524198709063212");
                $("#mobile").val("15926324431");
                $("#bankCard").val("6217002870031371584");
                $("#bankCode").val("CCB").trigger("change");
            }else if("8"==dName) {
                $("#userName").val("刘壮");
                $("#idNo").val("420114198604291231");
                $("#mobile").val("13986066955");
                $("#bankCard").val("6236682870005881706");
                $("#bankCode").val("CCB").trigger("change");
            }else if("9"==dName) {
                $("#userName").val("谭书楷");
                $("#idNo").val("421124199009052090");
                $("#mobile").val("13545147778");
                $("#bankCard").val("6215593202021966638");
                $("#bankCode").val("ICBC").trigger("change");
            }else if("10"==dName) {
                $("#userName").val("李丛丛");
                $("#idNo").val("420621199509065725");
                $("#mobile").val("15827091627");
                $("#bankCard").val("6236682870008795994");
                $("#bankCode").val("CCB").trigger("change");
            }else if("11"==dName) {
                $("#userName").val("邓学军");
                $("#idNo").val("420324199008030033");
                $("#mobile").val("18672378026");
                $("#bankCard").val("6217002870078317870");
                $("#bankCode").val("CCB").trigger("change");
            }else if("12"==dName) {
                $("#userName").val("谭晓祥");
                $("#idNo").val("422802199210066815");
                $("#mobile").val("17600296815");
                $("#bankCard").val("6217002870067231181");
                $("#bankCode").val("CCB").trigger("change");
            }else if("13"==dName) {
                $("#userName").val("覃贵兰");
                $("#idNo").val("420528199412302826");
                $("#mobile").val("15997528549");
                $("#bankCard").val("6217002830004965978");
                $("#bankCode").val("CCB").trigger("change");
            }else if("14"==dName) {
                $("#userName").val("林小琪");
                $("#idNo").val("420704199211150029");
                $("#mobile").val("15972004577");
                $("#bankCard").val("6214662871558744");
                $("#bankCode").val("CCB").trigger("change");
            }else {
                $("#userName").val("廖明成");
                $("#idNo").val("420624198901041810");
                $("#mobile").val("18971600623");
                if("SPDB"==dName){
                    $("#bankCard").val("6217922102470284");
                    //$("#bankCode").val("SPDB");
                    //$("#bankCode").attr("value","SPDB");
                    //$("#bankCode option[value='" + "SPDB" + "']").attr("selected", true);
                    $("#bankCode").val("SPDB").trigger("change");
                }else if("GDB"==dName){
                    $("#bankCard").val("6214624021000801426");
                    $("#bankCode").val("GDB").trigger("change");
                }else if("CCB"==dName){
                    $("#bankCard").val("6236682870001535504");
                    $("#bankCode").val("CCB").trigger("change");
                }
            }
            */

}


function xfWithHold() {
var userName = $("#userName").val();
var mobile = $("#mobile").val();
var bankCard = $("#bankCard").val();
var withholdAmt =$("#withholdAmt").val();
var remark = $("#remark").val();
var idNo = $("#idNo").val();
var channel = $("#channel option:selected").val();
var bankCode ="";
var bankName="";
if(""==channel){
    layer.alert('请选择代扣渠道', {
        icon : 7
    });
    return false;
}
if("2"==channel){
    bankCode = $("#bankCode option:selected").val();
    bankName = $("#bankCode option:selected").text();
    if(""==bankCode){
        layer.alert('银行编码不能为空', {
            icon : 7
        });
        return false;
    }
}

if ("" == userName) {
    layer.alert('客户姓名不能为空', {
        icon : 7
    });
    return false;
}

if ("" == idNo) {
    layer.alert('客户身份证不能为空', {
        icon : 7
    });
    return false;
}

if ("" == mobile) {
    layer.alert('客户手机号不能为空', {
        icon : 7
    });
    return false;
}


if ("" == bankCard) {
    layer.alert('客户银行卡号不能为空', {
        icon : 7
    });
    return false;
}


if ("" == withholdAmt) {
    layer.alert('代扣金额不能为空', {
        icon : 7
    });
    return false;
}


if ("" == remark) {
    layer.alert('备注不能为空', {
        icon : 7
    });
    return false;
}


if(remark.length > 20){
    layer.alert('备注字数不能大于20', {
        icon : 7
    });
    return false;
}


//$(".layui-layer-btn0")这个是确定按钮
$(".layui-btn").addClass("layui-btn-disabled");
$(".layui-btn").attr('disabled',true);
//  layer.msg('处理中...',{shade:[0.8, '#393D49'],time:60*60*1000});

var param = {
        userName : userName,
        mobile : mobile,
        bankCard : bankCard,
        withholdAmt : withholdAmt,
        remark : remark,
        idNo : idNo,
        channel:channel,
        bankCode:bankCode,
        bankName:bankName

};
$.ajax({
    type : "post",
    url : "${ctx}/loan/shopWithHold/handXianFengWithHold",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						alert(data.msg);
						 	$("#userName").val("");
							$("#mobile").val("");
							 $("#bankCard").val("");
							$("#withholdAmt").val("");
							 $("#remark").val("");
							 $("#idNo").val("");
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