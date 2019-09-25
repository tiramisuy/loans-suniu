<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息表管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

	<link rel="stylesheet" href="${ctxStatic}/layui_2/css/layui.css">
	<script src="${ctxStatic}/layui_2/layui.js"></script>
    <script type="text/javascript"src="${ctxStatic}/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">




        $(document).ready(function() {




			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});


            layui.use('upload', function(){
                var upload = layui.upload;

                //执行实例
                var uploadInst = upload.render({
                    elem: '#goodsPic_btn' //绑定元素
                    ,exts:'jpg|png|gif|bmp|jpeg'
                    ,url: '/a/upload/logo' //上传接口
                    ,done: function(res){
                        if(res.url && res.url !="null"){
                            $("[name='goodsPic']").val(res.url);
                            alert("上传成功");
                        }else{
                            alert("上传失败");
                        }
                    }
                    ,error: function(){
                        //请求异常回调
                        alert("上传失败");
                    }
                });
            });



        /*    layui.use('upload', function(){
                var upload = layui.upload;

                //执行实例
                var uploadInst = upload.render({
                    elem: '#picBanner_btn' //绑定元素
                    ,exts:'jpg|png|gif|bmp|jpeg'
                    ,url: '/a/upload/logo' //上传接口
                    ,multiple: true
                    ,done: function(res){
                        if(res.url && res.url !="null"){
                            var lastVal = $("[name='picBanner']").val() ? $("[name='picBanner']").val() + "$" : '';
                            $("[name='picBanner']").val(lastVal +res.url);
                        }else{
                            alert("上传失败");
                        }
                    }
                    ,error: function(){
                        //请求异常回调
                        alert("上传失败");
                    }
                });
            });*/


            layui.use('upload', function(){
                var upload = layui.upload;
                //执行实例
                var uploadInst = upload.render({
                    elem: '#picBanner_btn' //绑定元素
                    ,exts:'jpg|png|gif|bmp|jpeg'
                    ,url: '${ctx}/upload/logo' //上传接口
                    ,multiple: true
                    ,choose: function(obj) {
                        $("[name='picBanner']").val('');
                    }
                    ,done: function(res){
                        if(res.url && res.url !="null"){
                            var lastVal = $("[name='picBanner']").val();
                            if(lastVal) {
                                try{
                                    lastVal = JSON.parse(lastVal);
                                }
                                catch(e) {
                                    console.log('error：' + e);
                                    lastVal = {};
                                }
                            }
                            else {
                                lastVal = {};
                            }
                            var valArray = [res.url];
                            for (var key in lastVal) {
                                valArray.push(lastVal[key]);
                            }
                            var newVal = {}
                            for (var i = 0; i < valArray.length; i++) {
                                newVal['pic' + i] = valArray[i];
                            }
                            $("[name='picBanner']").val(JSON.stringify(newVal));

                            //alert("上传成功");
                        }else{
                            alert("上传失败");
                        }
                    }
                    ,error: function(){
                        //请求异常回调
                        alert("上传失败");
                    }
                });


            });


            layui.use('upload', function(){
                var upload = layui.upload;
                //$("[name='picDetail']").val("");

                //执行实例
                var uploadInst = upload.render({
                    elem: '#picDetail_btn' //绑定元素
                    ,exts:'jpg|png|gif|bmp|jpeg'
                    ,url: '${ctx}/upload/logo' //上传接口
                    ,multiple: true
                    ,choose: function(obj) {
                        $("[name='picDetail']").val('');
                    }
                    ,done: function(res){
                        if(res.url && res.url !="null"){
                        	var lastVal = $("[name='picDetail']").val();
                        	if(lastVal) {
                        		try{
                        			lastVal = JSON.parse(lastVal);
                        		}
                        		catch(e) {
                        			console.log('error：' + e);
                        			lastVal = {};
                        		}
                        	}
                        	else {
                        		lastVal = {};
                        	}
                        	var valArray = [res.url];
                        	for (var key in lastVal) {
								valArray.push(lastVal[key]);
							}
                        	var newVal = {}
                        	for (var i = 0; i < valArray.length; i++) {
                        		newVal['pic' + i] = valArray[i];
							}
                            $("[name='picDetail']").val(JSON.stringify(newVal));

                            //alert("上传成功");
                        }else{
                            alert("上传失败");
                        }
                    }
                    ,error: function(){
                        //请求异常回调
                        alert("上传失败");
                    }
                });


            });


		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/loan/goodsList/">商品信息表列表</a></li>
		<li class="active"><a href="${ctx}/loan/goodsList/form?id=${goodsList.id}">商品信息表
			<%--<shiro:hasPermission name="loan:goodsList:edit">--%>
				${not empty goodsList.id?'修改':'添加'}
			<%--</shiro:hasPermission>--%>
			<%--<shiro:lacksPermission name="loan:goodsList:edit">查看</shiro:lacksPermission>--%>
            </a></li>
        </ul><br/>
        <form:form id="inputForm" modelAttribute="goodsList" action="${ctx}/loan/goodsList/save" method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <sys:message content="${message}"/>
            <div class="control-group">
                <label class="control-label">商品名称：</label>
                <div class="controls">
                    <form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
                </div>
            </div>
			<div class="control-group">
				<label class="control-label">商品简称：</label>
				<div class="controls">
					<form:input path="simpleName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
            <div class="control-group">
                <label class="control-label">商品单价：</label>
                <div class="controls">
                    <form:input path="goodsPrice" lay-verify="goodsPrice" maxlength="14" htmlEscape="false" class="input-xlarge  number"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">市场价：</label>
                <div class="controls">
                    <form:input path="marketPrice" htmlEscape="false"  maxlength="14" class="input-xlarge  number"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">商品主图：</label>
				<div class="controls">
					<form:input path="goodsPic" htmlEscape="false" maxlength="100" class="input-xlarge " readonly="true"/>

					<button type="button" class="layui-btn" id="goodsPic_btn">
						<i class="layui-icon">&#xe67c;</i>上传图片
					</button>
				</div>
            </div>


          <%--  <div class="control-group">
                <label class="control-label">销量：</label>
                <div class="controls">
                    <form:input path="salesVolume" htmlEscape="false" maxlength="255" class="input-xlarge "/>
                </div>
            </div>--%>
            <div class="control-group">
                <label class="control-label">商品展示图：</label>


				<div class="controls">
					<form:input path="picBanner" htmlEscape="false"  class="input-xlarge " readonly="true"/>

					<button type="button" class="layui-btn" id="picBanner_btn">
						<i class="layui-icon">&#xe67c;</i>上传图片(多图上传)
					</button>
				</div>
            </div>

            <div class="control-group">
                <label class="control-label">购物券最大使用金额：</label>
                <div class="controls">
                    <form:input path="maxCoupon" htmlEscape="false" class="input-xlarge number"/>
                </div>
            </div>
			<div class="control-group">
				<label class="control-label">是否上架：</label>
				<div class="controls">

					<form:select path="status" htmlEscape="false" maxlength="10" class="input-xlarge required">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>

				</div>
			</div>



            <div class="control-group">
                <label class="control-label">商品详情：</label>


                <div class="controls">
                    <form:input path="picDetail" htmlEscape="false"  class="input-xlarge " readonly="true"/>

                    <button type="button" class="layui-btn" id="picDetail_btn">
                        <i class="layui-icon">&#xe67c;</i>上传图片(多图上传)
                    </button>
                </div>
            </div>
            <div class="form-actions">
                <%--<shiro:hasPermission name="loan:goodsList:edit">--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>