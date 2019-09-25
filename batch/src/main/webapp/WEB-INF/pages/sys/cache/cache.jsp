<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="ps-btn-opt" style="text-align: center;">
			<input type="button" class="opt-btn" value="刷新缓存"  onclick="Utils.forward('${ctx }/sys/cache!flush.action');"/>
			</div>
		</div>	
		<div style="text-align: center;margin: 10px;">
				${msg }			 
		</div>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>
<style>
<!--
li{margin:0; padding:0; list-sytle:none}
-->
</style>