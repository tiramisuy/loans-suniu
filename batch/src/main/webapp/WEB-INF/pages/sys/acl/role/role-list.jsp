<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/jscsslist.jsp" %>
<div class="role-list">
<ul id="adminTabs" class="adminTabs">
<s:iterator value="list">
<a href="${ctx }/acl/assign-authority!assignAuthority.action?roleId=${id }" target="assginAuthorityFrame"><li>${name }</li></a>
</s:iterator>
</ul>
</div>
<style type="text/css">
ul,li{
margin:0;
padding: 0;
}
.adminTabs{
list-style: none;
margin-left: 15%;
margin-top: 10px;
}
.adminTabs li {
    background: url("../res/sys/img/admin/tabs-li-bg.jpg") no-repeat scroll center center transparent;
    cursor: pointer;
    height: 28px;
    line-height: 28px;
    margin: 5px 0;
	text-align: center;
    width: 106px;
}
.adminTabs li.current {
    background: url("../res/sys/img/admin/bg-current.jpg") no-repeat scroll center top #FFFFFF;
    color: #FFFFFF;
}
.adminTabs li.current a{color: #FFFFFF;}
.adminTabs  a:visited{color: #000000;}
.role-list{margin: 5px 0 0 5px;padding:5px;border:1px solid #B4CFCF;background:#F9FBFD;position: absolute;height: 96%;width: 90%;}
</style>
<script type="text/javascript">
	$(function(){
		$("#adminTabs li").click(function(){
			if(!$(this).hasClass("current")){
				$("#adminTabs li").removeClass("current");
				$(this).addClass("current");
			}
		});
	});
</script>