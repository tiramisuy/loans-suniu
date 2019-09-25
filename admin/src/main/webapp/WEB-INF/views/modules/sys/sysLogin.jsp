<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.rongdu.loans.sys.security.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${fns:getConfig('productName')} -管理员登录</title>
<link href="${ctxStatic}/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>

<!--当前页面JS和样式-->
<link href="${ctxStatic}/modules/login/css/login.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/modules/login/js/login.js" type="text/javascript"></script>
<link href="${ctxStatic}/modules/login/css/layui.css" rel="stylesheet" type="text/css" />



</head>
<body style="background:url('${ctxStatic}/modules/login/images/about.png') no-repeat;overflow:hidden;">

	<div class="beg-login-box box">
            <header>
                <h1 style="color:#FFFFFF">业务管理平台</h1>
            </header>
            <div class="beg-login-main">
            	<form method="post" action="${ctx}/login" name="loginForm" id="loginForm"> 
                    <div class="layui-form-item">
                        <input type="text" name="username" id="username"  autocomplete="off" placeholder="请输入登录名" class="layui-input" maxlength="20">
                    </div>
                    <div class="layui-form-item">
                        <input id="password" type="password"  name="password"  autocomplete="off" placeholder="请输入密码" class="layui-input" maxlength="20">
                    </div>
                    
                   	<c:if test="${isValidateCodeLogin}">
                    	 <div class="layui-form-item">
            				 	<input type="hidden" id="isValidateCodeLogin" name="isValidateCodeLogin"  value="${isValidateCodeLogin}" class="layui-input" />
            				 	<input type="text" id="validateCode" name="validateCode" placeholder="图形验证码" maxlength="5" class="layui-input required" style="margin-bottom:0;width: 200px;display:  inline-block;">
            				 	<a href="javascript:" onclick="$('.validateCode').attr('src','/a/validateCode.jpg?'+new Date().getTime());" class="mid validateCodeRefresh" style="">
									<img src="/a/validateCode.jpg" onclick="$('.validateCodeRefresh').click();" class="mid validateCode" style="">
								</a>
            				 	
            				 	<%-- <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;width:150px;" /> --%>
                        
                    	</div>
            			
            		</c:if>
                    
                  	<div  class="code-item">
            			<%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);%>
						<label class="input-label" for="errorMsg" style="display:block;"></label>
						<span class="error" id="errorMsg" >
							<%=error==null?"":error%>								
						</span>
            		</div>	
                    
                    <div class="layui-form-item">
                        <div>
                            <button id="loginButton" class="layui-btn layui-btn-primary" style="display: block;width:180px;margin:0 auto;height: 35px;" >登录</button>
                        </div>
                        <div class="beg-clear"></div>
                    </div>
                </form>
            </div>
        </div>

			
    <span id="copyright">
    	<span>Copyright &copy; <a href="${fns:getConfig('domainName')}" target="_blank">${fns:getConfig('domainName')}</a><strong></strong> All Rights Reserved.</span>
    </span>	

</body>
</html>