$(function(){
	$(".pn-ltbody tr").hover(
		function () {$(this).addClass("pn-lhover");},
		function () {$(this).removeClass("pn-lhover"); }
	); 
	$(".pn-ltbody tr").click(function () {
		$(this).addClass("pn-lselected");
		$(this).siblings().removeClass("pn-lselected");
	}); 
	//$("#searchForm").submit(function(){
	//	loadmask();
		 
	//});
	
})

function loadmask() {
	$("html").mask("正在加载...");
}

function confirmDelete(){
	return confirm('您确定删除吗？');	
}

function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#searchForm").submit();
}

function gotoPage(pageNo,totalPages) {
	var exp = "^[1-9]\\d*$";
	if( new String(pageNo).match(exp)){
		var no =parseInt(pageNo);
		var total =parseInt(totalPages);
		if(no>=1&&no<=total){
			jumpPage(no);
		}else{
			Dialog.alert("您输入的页码超出范围，请重新输入");			
		}
	}else{
		Dialog.alert("请输入整数");
	}
}

//工具对象
Utils = {
		version : '1.0',
		name:'YuchengTech JavaScript Utils'
	};
/**
 * 切换复选框的选中状态
 * @param name 复选框的名称
 */
Utils.toggleCheckState = function(name) {
	$("input[type=checkbox][name=" + name + "]").each(function() {
		if($(this).prop("checked")){
			$(this).attr("checked", false);
		}else{
			$(this).prop("checked", true);
		}
	});
}
/**
 * 被选中的复选框的个数
 * @param name 复选框的名称
 */
Utils.checkedCount = function(name) {
 	var count = 0;
	$("input[type=checkbox][name=" + name + "]").each(function() {
 		if($(this).prop("checked")){
			count++;
		}
	});
	return count;
}
/**
 * 跳转到指定资源
 * @param name 资源路径
 */
Utils.forward =function(url,info){
	if(info!=null){
		Dialog.confirm(info,function(){
			window.location.href=url;	
			loadmask();
		});
		}else{
			window.location.href=url;
			loadmask();
		}
}
/**
 * 下载指定资源
 * @param name 资源路径
 */
Utils.download =function(url){
	window.location.href=url;	
}
/**
 * 切换到菜单
 */
Utils.lmenu = function(id) {
	var m = $('#' + id);
	m.addClass('lmenu');
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("lmenu-focus");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("lmenu-focus");
			}
			m.focusElement=this;
		});
	});
}



Utils.batch =function (url,info1,info2,checkboxName){
	if(checkboxName==""||typeof(checkboxName)=="undefined"){
		checkboxName = "ids";
	}
	var num = Utils.checkedCount(checkboxName);
	if(num>0){
		Dialog.confirm("您是否确认批量"+info1+"这"+num+"条数据？",function(){
			$("#dataListForm").attr("action",url);
			$("#dataListForm").submit();
		});
	}else{
		Dialog.alert("请选待要"+info1+"的"+info2+"!");
	}
}

Utils.doDelete =function (url,msg){
	if(msg==""||typeof(msg)=="undefined"){
 		msg = "您是否确认删除该条数据？";
	}
	Dialog.confirm(msg,function(){
 		window.location.href=url;	
	});
}

Utils.confirm =function (url,msg){
	Dialog.confirm(msg,function(){
		window.location.href=url;
	});
}


Utils.doUpdate =function (action,info){
 	if($("#form").valid()){
  		Dialog.confirm("您是否确认"+info+"该条数据？",function(){
 			$(form).attr("action",action);
			$(form).submit();		
			Utils.loading();
 			});
	}
	
}
 

Utils.openFormDialog =function (action,title,width,height){
	var diag = new Dialog();
	diag.Width = width;
	diag.Height = height;
	diag.Title = title;
	diag.URL = action;
	diag.OKEvent = function(){
		var doc = diag.innerFrame.contentWindow.document;
		//doc.getElementById("submit").click();
		//$(doc).find(":submit").click();
		$(doc).find("form").submit();
		//var errorNum = $(doc).find(".error").not("label").size();
		//alert(errorNum);
		if(1==1){
			//$(doc).find("#form").submit();
			//diag.close();
			//window.location.reload();
		}
	};//点击确定后调用的方法
	diag.show();
}

Utils.doOpenWindow =function (action,title){
	var diag = new Dialog();
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = title;
	diag.URL = action;
	diag.show();
}
 
Utils.doCloseWindow =function (action,msg){
	if($("#form").valid()){
		$(form).attr("action",action);
		$(form).submit();
		var result = new Object();
		result.msg=msg;
		window.returnValue = result;
		window.close();
	}
}

Utils.doSearch = function(action){
	var selector = "#searchForm";
	var old = $(selector).attr("action");
	$(selector).attr("action",action);
	loadmask();
 	setTimeout(function(){$(selector).submit();}, 50); 
	$(selector).attr("action",old);
}

Utils.doExport = function(action){
	var selector = "#searchForm";
	var old = $(selector).attr("action");
	$(selector).attr("action",action);
	$(selector).submit();				
	$(selector).attr("action",old);
}

Utils.doImport = function(action){
	var selector = "#searchForm";
	var old = $(selector).attr("action");
	$(selector).attr("action",action);
  	 $(selector).submit(); 
	$(selector).attr("action",old);
}

Utils.loading=function(){
	$("#content").mask("请稍后...");
};
Utils.goback=function(){

	history.back(-1);
	return false;
};