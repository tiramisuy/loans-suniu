<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<frameset cols="18%,82%" frameborder="0" border="0" framespacing="0" name="empMainFrameset"  id="empMainFrameset">
	<frame src="${ctx }/acl/dept!tree.action" name="deptTreeFrame"  id="deptTreeFrame" noresize="noresize"/>
	<frame name="empFrame" id="empFrame" />
</frameset>

