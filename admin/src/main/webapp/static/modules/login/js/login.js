$(function() {
	
	registerFormSubmitEvent();
	
	//registerFormValidateEvent();
	if($("#isValidateCodeLogin").val()!=null && $("#isValidateCodeLogin").val() != ''){
		$(".box").height("330px");
	}
	
	/*document.onkeydown =cdk; 
	function cdk(){ 
	    if(event.keyCode ==13){
	    	var username = $("#username").val();
	    	var password = $("#password").val();
	    	var validateCode = $("#validateCode").val();
	    	if(username==null||username==""||$.trim(username).length==0 ){
	    		$("#errorMsg").html("请输入用户名");
	    		return;
	    	}
	    	var regex =  /[a-zA-Z0-9][a-zA-Z0-9_]{4,19}$/;
	    	if(!regex.test(username) ){
	    		$("#errorMsg").html("请输入正确的用户名");
	    		return;
	    	}
	    	if(password==null||password==""||$.trim(password).length==0 ){
	    		$("#errorMsg").html("请输入登录密码");
	    		return;
	    	}
	    	var isValidateCodeLogin='${isValidateCodeLogin}';
	    	if(isValidateCodeLogin=='true'){
	    		if(validateCode==null||validateCode==""||$.trim(validateCode).length==0 ){
	    			$("#errorMsg").html("请输入图形验证码");
	    			return;
	    		}
	    	}
	    	$("#loginForm").submit();
	    }
	}*/
	
});

function registerFormSubmitEvent() {
	$("#loginButton").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var validateCode = $("#validateCode").val();
		if(username==null||username==""||$.trim(username).length==0 ){
			$("#errorMsg").html("请输入用户名");
			return;
		}
		var regex =  /[a-zA-Z0-9][a-zA-Z0-9_]{4,19}$/;
		if(!regex.test(username) ){
			$("#errorMsg").html("请输入正确的用户名");
			return;
		}
		if(password==null||password==""||$.trim(password).length==0 ){
			$("#errorMsg").html("请输入登录密码");
			return;
		}
		var isValidateCodeLogin='${isValidateCodeLogin}';
		if(isValidateCodeLogin=='true'){
			if(validateCode==null||validateCode==""||$.trim(validateCode).length==0 ){
				$("#errorMsg").html("请输入图形验证码");
				return;
			}
		}
		$("#loginForm").submit();
	});
}

function registerFormValidateEvent() {
	$("#form1").validate({
		rules: {
			username: {
				required: true,
				username: true
			},
			password: {
				required: true
			},
			validateCode: {
				required: true,
				remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"
			}
		},
		messages: {
			username: {
				required: "请填写用户名",
				username:"请输入正确的用户名"
			},
			password: {
				required: "请填写密码"
			},
			validateCode: {
				remote: "验证码不正确", 
				required: "请填写验证码"
			}
		},
		errorLabelContainer: "#errorMsg",
		errorPlacement: function(error, element) {
			return false;
			//error.appendTo($("#messageBox"));
		} 
	});
}