    //获取验证码
    //获取url中的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    }  
    var channel =  GetQueryString('channel');
	var channelStr = channel.indexOf("sudai");
	var channelStr2 = channel.indexOf("rongyilai");
    var channelStr3 = channel.indexOf("qihu360");
    
    var countdown=60; 
        function settime(obj) { 
            var data = {
                account: $("[name='username']").val(),
                msgType:1,
                channel:channel,
                source:1
            };
            var flag = true, timer;
            var username= $("[name='username']");
            if(username.val()=='') {
                username.parents('li').next(".Tips").html("&times&nbsp;请输入手机号！");
                username.focus();
                return false;
            }else{
                username.parents('li').next(".Tips").html("");
                $.ajax({
                    type: "POST",
                    url:'http://192.168.10.254:9000/api/anon/sendMsgVerCode',
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        //console.log(data.code);
                        if (data.code == "SUCCESS") {
                            $("#btn").parents('li').next(".Tips").html('&times&nbsp;请输入验证码！');
                            flag = false;
                            var num = 59;
                            timer = setInterval(function () {
                                num--;
                                var html = '重新发送'+num + "s";
                                $("#btn").attr("disabled", true);
                                $("#btn").val(html);
                                if (num == 0) {
                                    clearInterval(timer);
                                    $("#btn").removeAttr("disabled");
                                    $("#btn").val("获取验证码");
                                    flag = true;
                                }
        
                            }, 1000);

                        }else{
                            $("#btn").parents('li').next(".Tips").html(data.msg);
                        }
                    }
                });
            }
        } 

        function check() {
            var inputcode=$("[name='inputcode']");
            var username=$("[name='username']");
            var password=$("[name='password']");
            $(".Tips").html("");
            if(username.val()=='') {
                username.parents('li').next(".Tips").html("&times&nbsp;请输入手机号！");
                username.focus();
                return false;
            }
            if(!/^1[3578]\d{9}$/.test(username.val())){
                username.parents('li').next(".Tips").html("&times&nbsp;您输入的手机号不正确！");
                username.focus();
                return false;
            }
            if(inputcode.val()=='') {
                inputcode.parents('li').next(".Tips").html("&times&nbsp;请输入验证码！");
                inputcode.focus();
                return false;
            }
            if(password.val()==''){
                password.parents('li').next(".Tips").html("&times&nbsp;请输入密码！");
                password.focus();
                return false;
            }
            if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/.test(password.val())){
                password.parents('li').next(".Tips").html("&times&nbsp;请输入正确的密码！");
                password.focus();
                return false;
            } else {
                var data={
                    account:$("[name='username']").val(),
                    password:$("[name='password']").val(),
                    msgVerCode:$("[name='inputcode']").val(),
                    channel:channel,
                    source:3
                }
                $.ajax({
                    type: "POST",
                    url: "http://api.jubaoqiandai.com/api/anon/register",
                    data: data,
                    dataType: 'json',
                    success: function (datas) {
                        if (datas.code == "SUCCESS") {
                            $(".reg-resultbg").show();
                            $(".reg-result").show();
                        }else{
                            password.parents('li').next(".Tips").html(datas.msg);
                        }
                    }
                });
                return true;
            }
            
            
        }

        function apply(){
            var mobile=$("[name='mobile']");
            $(".Tips").html("");
            if(mobile.val()=='') {
                mobile.parents('li').next(".Tips").html("&times&nbsp;请输入手机号！");
                mobile.focus();
                return false;
            }
        }

    // 点解关闭弹框
	$(".reg-close").on("click",function(){
		$(".reg-result").hide();
        $(".reg-resultbg").hide();		
	});
