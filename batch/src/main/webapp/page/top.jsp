<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>聚宝钱包 Administrator's Control Panel</title>
<script src="${ctx}/res/common/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/widget/loadmask/jquery.loadmask.min.js" type="text/javascript"></script>
<link href="${ctx}/res/widget/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="top">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="200">
	<table width="200" border="0" cellspacing="0" cellpadding="0">
      <tr  style="background: url('${ctx }/res/sys/img/admin/top_bg.jpg');">
        <td height="63" align="center" style="font-size:20px;color: #FCD209;font-weight: bolder;">聚宝钱包
        <span align="center" style="font-size:15px;color: #FFF;font-weight: bolder;">任务调度平台</span>
        </td>
      </tr>
      <tr>
        <td height="33" align="center" background="${ctx }/res/sys/img/admin/time_bg.jpg">
		<img src="${ctx }/res/sys/img/admin/ico3.jpg">&nbsp;
       <script language="javascript">
			var date=new Date();
			var weekday = "星期"+['日','一','二','三','四','五','六'][date.getDay()];		
			document.write(date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日 "+weekday);
	   </script>
       </td>
      </tr>
    </table></td>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15" height="54"><img src="${ctx }/res/sys/img/admin/top_bg.jpg"></td>
        <td background="${ctx }/res/sys/img/admin/top_bg.jpg"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="500" height="30">
            <a href="#" id="welcome">您好, <sec:authentication property="principal.name"/>[<sec:authentication property="principal.username"/>]</a>			
			<a href="#" id="refresh" onclick="refresh();">刷新</a>
			<a href="${ctx }/j_spring_security_logout" target="_top" id="logout" onclick="confirm('您确定注销吗？');">注销</a>
		   </td>
            <td align="right">
            </td>
            <td width="100"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img src="${ctx }/res/sys/img/admin/top_07.jpg"></td>
        <td background="${ctx }/res/sys/img/admin/nav_bg.jpg">
		   <ul class="menu">
		 	<s:iterator value="menu" status="status">
		 		<s:if test="#status.first"><li class="current"></s:if><s:else><li class="sep"></li><li></s:else><a href="#" onclick="toLeftFrame('${ctx }/frame!left.action?id=${authId }');">${name }</a></li>
		 	</s:iterator>
			</ul>
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</div>
</body>
</html>
<%@ include file="/page/jscssview.jsp" %>
<style type="text/css">
*{margin:0;padding:0}
html{height:100%;overflow:hidden;}
body{height:100%;}
#welcome{color:#FFF;padding-left:20px;margin-left:20px; background:url(${ctx }/res/sys/img/admin/welcome-icon.png) no-repeat;}
#refresh{color:#FFF;padding-left:20px; margin-left:20px;background:url(${ctx }/res/sys/img/admin/refresh-icon.png) no-repeat;}
#logout{color:#FFF;padding-left:20px; margin-left:20px;background:url(${ctx }/res/sys/img/admin/logout-icon.png) no-repeat;}
#view_index{color:#FFF;}

.menu{padding-left:1em;font-size:12px;font-weight:700;float:left;margin:4px 4px 0 0;list-style:none;}
.menu li{float:left;width:80px;}
.menu li.sep{float:left;height:35px;width:5px;background:url(${ctx }/res/sys/img/admin/sep.jpg) left 3px no-repeat;margin-left: 1px;}
.menu li a{display:block;height:35px;text-align:center;line-height:35px;padding:0 14px;color:#000;outline:none;hide-focus:expression(this.hideFocus=true);}
.menu li.current{background:url(${ctx }/res/sys/img/admin/nav_current.jpg) left top no-repeat;}
.menu li.current a{color:#fff;}

.undis{display:none;}
.dis{display:block;}
</style>
<script type="text/javascript">
$(function(){
	$(".menu li[class!='sep']").click(function(){
		$(".menu li[class!='sep'] ").removeClass();
		$(this).addClass("current");
	});
});
function toLeftFrame(url){
	top.leftFrame.location=url;
	//$("body").mask("正在加载...");
}
function refresh(){
	top.rightFrame.location.reload();
	//$("body").mask("正在加载...");
}
</script>