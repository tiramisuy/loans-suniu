<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
<link href="../res/widget/dtree/dtree.css" type="text/css" />
<script src="../res/widget/dtree/dtree.js" type="text/javascript"></script>
<script src="${ctx}/res/common/js/jquery.min.js" type="text/javascript"></script>
<div class="box">
	<div class="dtree">
	<script type="text/javascript">
		<!--
		var d = new dTree('d');
		d.config.target = "contentFrame";
		<c:forEach items="${list}" var="e">d.add('${e.id}','${e.pid}','${e.name}','${ctx }/acl/authority!viewAuthority.action?eid=${e.id}');
		</c:forEach>		
		document.write(d);
		
		var height = $(window).height();
		$(document).click(function(){
			if($(".box").height()>$(".dtree").height()){
				$(".box").height(height-20);
			}else{
				$(".box").height($(".dtree").height()+10);			
			}
		}); 
		$(window).scroll(function(){
			if($(".box").height()>$(".dtree").height()){
				$(".box").height(height-20);
			}else{
				$(".box").height($(".dtree").height()+10);			
			}
		}); 	
		//-->
	</script>
	</div>
</div>
<style>
.box{margin: 5px 0 0 5px;padding: 5px 0 0 5px;border:1px solid #B4CFCF;background:#F9FBFD;position: absolute;height: 90%;width: 90%;}
</style>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>