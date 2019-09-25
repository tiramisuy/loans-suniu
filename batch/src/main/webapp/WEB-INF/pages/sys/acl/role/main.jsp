<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<frameset cols="15%,85%" frameborder="0" border="0" framespacing="0" name="roleFrameset"  id="roleFrameset">
	<frame src="${ctx }/acl/assign-authority!roleList.action" name="roleListFrame"  id="roleListFrame" noresize="noresize"/>
	<frame   name="assginAuthorityFrame" id="assginAuthorityFrame" />
</frameset>
