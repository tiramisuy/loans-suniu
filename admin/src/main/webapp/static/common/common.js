/*!
 *Copyright 2014-2017 聚宝钱包 All rights reserved. 
 * 
 * 通用公共方法
 * @author sunda
 * @version 2014-4-29
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select").select2();
	}catch(e){
		// blank
	}
	
	//为数据列表添加选中或者划过效果
	$(".table tbody>tr").hover(function() {
		$(this).addClass("row-hover");
	}, function() {
		$(this).removeClass("row-hover");
	});
	$(".table tbody>tr").click(function() {
		$(this).addClass("row-selected");
		$(this).siblings().removeClass("row-selected");
	}); 
	
	//BEGIN BACK TO TOP
	$(window).scroll(function(){
	    if ($(this).scrollTop() < 200) {
	        $('#totop') .fadeOut();
	    } else {
	        $('#totop') .fadeIn();
	    }
	});
	$('#totop').on('click', function(){
	    $('html, body').animate({scrollTop:0}, 'fast');
	    return false;
	});
	//END BACK TO TOP
	
	//为数据列表全选/反选，添加监听事件
	$("input[type=checkbox][name='batchOptControl']").click(function(){
		$("input[type=checkbox][name='batchOptIds']").each(function() {
			if($(this).attr("checked")){
				$(this).attr("checked", false);
			}else{
				$(this).attr("checked", true);
			}
		});		
	});

		
});


// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

//恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	
	top.layer.confirm(mess, {icon: 3, title:'系统提示'}, function(index){
	    //do something
		if (typeof href == 'function') {
			href();
		}else{
			resetTip(); //loading();
			location = href;
		}
	    top.layer.close(index);
	});
	
	return false;
}

// 提示输入对话框
function promptx(title,  href){

	 var index = top.layer.prompt({title: title, formType: 2}, function(text){
		 if (typeof href == 'function') {
				href();
			}else{
				resetTip(); //loading();
				location = href + encodeURIComponent(text);
			}
		 
		 top.layer.close(index);
		  });
	return false;
}


// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

//打开对话框(添加修改)
function openFormDialog(title,url,width,height,target){
	
	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
		width='auto';
		height='auto';
	}else{//如果是PC端，根据用户设置的width和height显示。
	
	}	
	top.layer.open({
	    type: 2,  
	    area: [width, height],
	    title: title,
        maxmin: true, //开启最大化最小化按钮
	    content: url ,
	    btn: ['确定', '关闭'],
	    yes: function(index, layero){
	    	 var body = top.layer.getChildFrame('body', index);
	         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	         var inputForm = body.find('#inputForm');
	         var top_iframe;
	         if(target){
	        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
	         }else{
	        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
	         }
	         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
	        if(iframeWin.contentWindow.doSubmit() ){
	        	// top.layer.close(index);//关闭对话框。
	        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
	         }
			
		  },
		  cancel: function(index){ 
	       }
	}); 
	
}

//打开对话框(查看)
function openViewDialog(title,url,width,height,openMaxmin){
	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
		width='auto';
		height='auto';
	}else{//如果是PC端，根据用户设置的width和height显示。
	
	}
	if(typeof(openMaxmin) == "undefined"){
		openMaxmin = true;
	}
	
	top.layer.open({
	    type: 2,  
	    area: [width, height],
	    title: title,
        maxmin: openMaxmin, //开启最大化最小化按钮
	    content: url ,
	    btn: ['关闭'],
	    cancel: function(index){ 
	       }
	}); 
	
}

//启动加载效果，缓解用户等待的焦虑
function loadLayer(target){
	//加载层，加载的风格，支持0-2，0.1透明度的白色背景
	if(target&&target.layer){
		target.layer.load(0, { shade: [0.2,'#fff']});
	}
}

function page(n,s){
	if(n) $("#pageNo").val(n);
	if(s) $("#pageSize").val(s);
	$("#searchForm").submit();
	loadLayer(window);
	return false;
}

//导出excel
function exportExcel(ecportaction,listaction){
	var flag=0;
	var param={
		ecportaction:ecportaction
	};
	$.ajax({
        type: "post",
        url: "/a/loan/export/check",
        data: param,
        dataType: "json",
        async:false,
        error: function (request) {
            top.layer.alert("系统繁忙,请稍后再试", {
                icon : 2,
                title : '系统提示'
            });
        },
        success: function (data, textStatus) {
            if (data.code == "1") {
                flag=1;
            } else {
                top.layer.alert(data.msg, {
                    icon : 7,
                    title : '系统提示'
                });
            }
        }

    });
    if(flag==1){
    	top.layer.confirm("确认要导出用户数据吗？", {icon: 3, title:'系统提示'}, function(index){
			$("#searchForm").attr("action",ecportaction);
			$("#searchForm").submit();
			top.layer.close(index);
			$("#searchForm").attr('action',listaction); 
			//2s后自动关闭
			top.layer.msg("加载中，请您耐心等待...", {time: 2000});
		});
    }
	
	return false;
}

Utils = {
		version : '1.0',
		name:'YourCompany JavaScript Utils'
	};
Utils.checkedCount = function(name) {
	var count = 0;
	$("input[type=checkbox][name=" + name + "]").each(function() {
		if($(this).attr("checked")){
			count++;
		}
	});
	return count;
}
Utils.forward =function(url){
	loadLayer(window);
	window.location.href=url;
}
Utils.batch = function(action,opt,type){
	if(Utils.checkedCount("batchOptIds")>0){
		var  index = top.layer.confirm("您是否确认"+opt+"这"+Utils.checkedCount("batchOptIds")+"条"+type+"？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				//将待批量处理的记录ID添加到表单中
				$("input[type=checkbox][name='batchOptIds']:checked").each(function() {
					$("#searchForm").append($(this));
				});
				//将查询表单的action替换成批量处理组件的action
				$("#searchForm").attr("action",action);
				//提交表单
				$("#searchForm").submit();
				//加载Loading效果
				loadLayer(window);
				//关闭确认框
				top.layer.close(index);
			});
		
	}else{
		top.layer.alert("请选择待"+opt+"的"+type+"!");
	}
}