<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/jscsslist.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>菜单</title>
</head>
<body class="lbody">
<ul id="lmenu">
<s:iterator value="menu" status="status">
	<li><p style="display:none" onclick="toRightFrame('${ctx }${url }');" ></p><a href="javascript:void(0);" onclick="toRightFrame('${ctx }${url }');">${name }</a></li>
</s:iterator>
</ul>
</body>
</html>
<%@ include file="/page/jscsslist.jsp" %>
<script type="text/javascript">
$(function(){
	Utils.lmenu('lmenu');
	$("#lmenu p:first").trigger("click");
});
function toRightFrame(url){
	top.rightFrame.location=url;
//	alert($(top.rightFrame.document.body).html());
  	$(top.rightFrame.document.body).mask("正在加载...");
  	
}
</script>