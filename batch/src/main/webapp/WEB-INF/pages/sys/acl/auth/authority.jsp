<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<frameset cols="18%,82%" frameborder="0" border="0" framespacing="0" name="resFrameset"  id="resFrameset">
	<frame src="${ctx }/acl/authority!tree.action" name="treeFrameset"  id="treeFrameset" noresize="noresize"/>
	<frame   name="contentFrame" id=""contentFrame"" />
</frameset>
