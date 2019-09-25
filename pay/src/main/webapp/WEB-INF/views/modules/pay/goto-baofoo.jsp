<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>聚宝钱包-支付网关</title>
<style>
.box {
	width: 450px;
	height: 240px;
	margin: 0 auto;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -99px;
	margin-left: -225px;
	text-align: center;
}
</style>
</head>
<body>
	<div class="box">
		<img src="${pageContext.request.contextPath}/static/images/pay/goto_pay.png" />
	</div>

	<form name="pay" method="post" action="${payUrl}" id="form">
		<input type="hidden" name="MemberID" value="${MemberID }"/>
		<input type="hidden" name="TerminalID" value="${TerminalID }"/>
		<input type="hidden" name="InterfaceVersion" value="${InterfaceVersion }"/>
		<input type="hidden" name="KeyType" value="${KeyType }"/>
		<input type="hidden" name="PayID" value="${PayID }"/>
		<input type="hidden" name="TradeDate" value="${TradeDate }"/>
		<input type="hidden" name="TransID" value="${TransID }"/>
		<input type="hidden" name="OrderMoney" value="${OrderMoney }"/>
		<input type="hidden" name="ProductName" value="${ProductName }"/>
		<input type="hidden" name="Amount" value="${Amount }"/>
		<input type="hidden" name="Username" value="${Username }"/>
		<input type="hidden" name="AdditionalInfo" value="${AdditionalInfo }"/>
		<input type="hidden" name="PageUrl" value="${PageUrl }"/>
		<input type="hidden" name="ReturnUrl" value="${ReturnUrl }"/>
		<input type="hidden" name="Signature" value="${Signature }"/>
		<input type="hidden" name="NoticeType" value="${NoticeType }"/>
	</form>

</body>
</html>
<script language="javascript" type="text/javascript">
 window.onload=function(){
 	document.getElementById("form").submit();
 }
</script>
