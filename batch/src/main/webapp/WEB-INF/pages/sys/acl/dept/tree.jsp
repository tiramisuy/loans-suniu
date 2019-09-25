<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
<link href="${ctx}/res/widget/dtree/dtree.css" type="text/css" />
<script src="${ctx}/res/widget/dtree/dtree.js" type="text/javascript"></script>
<script src="${ctx}/res/common/js/jquery.min.js" type="text/javascript"></script>
<div class="box">
	<div class="dtree">
	<script type="text/javascript">
		<!--
		var url = "${ctx }/acl/emp.action?filter_LIKES_deptNo=";
		var d = new dTree('d');
		d.config.target = "empFrame";
<s:iterator value="list">d.add('${id}','${parentId}','${deptName}',url+'${deptNo}');
</s:iterator>
		document.write(d);	
		
	var height = $(window).height();
	
	$(window).scroll(function(){
		var curHeight = $(".dtree").height()+10;
		curHeight = height<curHeight?curHeight:height;
		$(".box").height(curHeight-20);
	}); 	
	$(document).click(function(){
		var curHeight = $(".dtree").height()+10;
		curHeight = height<curHeight?curHeight:height;
		$(".box").height(curHeight-20);
	}); 	
		//-->
	</script>
	</div>
</div>
<style>
<!--
.box{margin: 5px;padding: 5px 0 0 5px;border:1px solid #B4CFCF;background:#F9FBFD;position: absolute;height: 90%;width: 90%;}
-->
</style>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>