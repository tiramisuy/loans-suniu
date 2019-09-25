<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <meta name = "format-detection" content = "telephone=no">
    <title>聚宝钱包 - 注册</title>
   



<style>
        * {
            margin: 0;
            padding: 0;
        }

        html, body {
            font-size: .3rem;
            font-weight: normal;
        }

        .container {
            width: 100vw;
            height: 100vh;
            background: url("/static/xiaoheiyu/images/bg.png") no-repeat;
            -webkit-background-size: 100%;
            background-size: 100%;
            position: relative;
        }

        .options {
            width: 100%;
            position: absolute;
            top: 7rem;
        }

        .options > div {
            margin: 0 auto;
            text-align: center;
        }

        .options .tips {
            font-size: .35rem;
            margin-bottom: .4rem;

        }

        .options .input {
            width: 5rem;
            height: 1rem;
            border: 3px solid #ffffff;
            -webkit-border-radius: 5rem;
            -moz-border-radius: 5rem;
            border-radius: 5rem;
            margin-bottom: .45rem;
            color: #e64408;
            font-size: .35rem;
            line-height: 1rem;
            font-weight: bold;
        }

        .options .psw {
            font-size: .28rem;
            margin-bottom: .15rem;
        }

        .options .button {
            width: 4rem;
            height: 1.3rem;
            -webkit-border-radius: 4rem;
            -moz-border-radius: 4rem;
            border-radius: 4rem;
            background: url("/static/xiaoheiyu/images/btn.png") no-repeat;
            -webkit-background-size: 100%;
            background-size: 100%;
        }
    </style>

  </head>


 <body>
   
   <div class="container">
    <div class="options">
        <div class="tips">请使用下面的手机号直接登录借款：</div>
        <div class="input">${param.phone}</div>
        <c:if test="${param.password != null}"> 
        	<div class="psw">初始密码：${param.password}</div>
        </c:if>
        <div class="button" onclick="return downloadApp()"></div>
    </div>
</div>
</body>


<script type="text/javascript" src="/static/xiaoheiyu/js/jquery.js"></script>
<script type="text/javascript" src="/static/xiaoheiyu/js/rem.js"></script>

<script type="text/javascript">
function isAndroid(){
	var u = navigator.userAgent;
	return u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	//var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
}


function downloadApp(){
	

	if(isAndroid()){
		window.location.href="https://juqianbao.oss-cn-hangzhou.aliyuncs.com/apk/2.1.0/app-jvbaodai-release.apk";
	}else{
		window.location.href="https://itunes.apple.com/cn/app/%E8%81%9A%E5%AE%9D%E5%88%86%E6%9C%9F/id1438698705?mt=8";
	}
	
	return false;
}

</script>

   
  </body>
 
</html>
