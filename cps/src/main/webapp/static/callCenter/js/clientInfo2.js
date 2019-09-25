


//客户信息
        function userInfo() {
            var param = {
                userId: userId,
                applyId: applyId,
                sign: "check",
                operatorId: operatorId
            };
            $.ajax({
                type: "post",
                url: "/call/center/userDetail",
                data: param,
                dataType: "json",
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        var myTemplate = Handlebars.compile($("#tpl_7").html());
                        data.data.removeDuplicateQueryVO = data.removeDuplicateQueryVO;
                        var html = myTemplate(data.data);
                        document.getElementById("tabDiv").innerHTML = html;
                    } else {
                        alert(data.msg);
                    }

                },

            });
        }

        //通讯录
        function contactConectInfo() {
            var param = {
                applyId: applyId,
                userId: userId
            };
            $.ajax({
                type: "post",
                url: "/call/center/getContactConnectInfo",
                data: param,
                dataType: "json",
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        var myTemplate = Handlebars.compile($("#tpl_8").html());
                        var html = myTemplate(data.data);
                        document.getElementById("tabDiv").innerHTML = html;
                    } else {
                        alert(data.msg);
                    }
                },

            });
        }

        //资信云
        function reportData(type) {
            var param = {
                applyId: applyId,
                type:type
            };
            $.ajax({
                type: "post",
                url: "/call/center/reportData",
                data: param,
                dataType: "json",
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                    	var myTemplate;
                    	if(type==1){
                    		myTemplate=Handlebars.compile($("#tpl_11").html());
                    	}else{
                    		myTemplate=Handlebars.compile($("#tpl_12").html());
                    	}
                        var html = myTemplate(data);
                        document.getElementById("tabDiv").innerHTML = html;
                    } else {
                        alert(data.msg);
                    }
                },

            });
        };
        
        //查重信息
        function contactApply() {
            var param = {
                applyId: applyId,
                userId: userId,
                mobile: mobile
            };
            $.ajax({
                type: "post",
                url: "/call/center/removeDuplicateQuery",
                data: param,
                dataType: "json",
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        var myTemplate = Handlebars.compile($("#tpl_6").html());
                        var html = myTemplate(data);
                        document.getElementById("tabDiv").innerHTML = html;
                    } else {
                        alert(data.msg);
                    }
                },

            });
        }
        
        
        function applyCheck(form,checkResult) {
        	
        	 $("#checkResult").val(checkResult);
        	
        	
        	
           
    		if(productId=='XJD'){
            	if (form.checkResult.value == '1' && form.approveAmt.value == '') {
                	alert("请填写审批金额");
                	return false;
            	}
            	//var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
            	var regAmt = /^[1-9]\d*00$/;
         		if (form.checkResult.value == '1' && (!regAmt.test(form.approveAmt.value) || form.approveAmt.value == '0')) {
              		alert("请填写正确的审批金额");
              		return false;
         		}
         		if (form.checkResult.value == '1' && (form.approveAmt.value<500 || form.approveAmt.value>3000)) {
              		alert("审批金额为500-3000");
              		return false;
         		}
         		if (form.checkResult.value == '1' && form.approveTerm.value == '') {
                	alert("请选择审批期限");
                	return false;
            	}
            	if (form.servFeeRate.value == '1' && form.servFeeRate.value == '') {
                	alert("请选择服务费率");
                	return false;
            	}
            	if(form.checkResult.value == '1' && (form.approveTerm.value == '14' || form.approveTerm.value == '15') && form.approveAmt.value>'1500'){
            		alert("14/15天产品审批金额不能超过1500");
                	return false;
            	}
            	if(form.checkResult.value == '1' && form.approveTerm.value == '28' && form.approveAmt.value!='3000'){
            		alert("28天产品审批金额只能为3000");
                	return false;
            	}
            	if(form.checkResult.value == '1' && form.approveTerm.value == '90' && form.approveAmt.value!='2000'){
            		alert("90天产品审批金额只能为2000");
                	return false;
            	}
            }
            if(productId=='XXX'){
            	var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
         		if (form.checkResult.value == '1' && (!regAmt.test(form.approveAmt.value) || form.approveAmt.value == '0')) {
              		alert("请填写正确的审批金额");
              		return false;
         		}
            	var regTerm = /^[0-9]{1,3}$/;
         		if (form.checkResult.value == '1' && (!regTerm.test(form.approveTerm.value) || form.approveTerm.value=='0')) {
              		alert("请填写正确的审批期限");
              		return false;
         		}
            }
            if (form.remark.value == '') {
                alert("请填写审批意见");
                return false;
            }
            
           
            
            $.ajax({
				cache : true,
				type : "POST",
				url : "/call/center/approve",
				data : $('#checkForm').serialize(),
				async : false,
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data) {
					if (data.code == "1") {
						alert("提交成功");
						window.location.reload(); 
					} else {
						alert(data.msg)
					} 
				}
			});
            
            
            return true;
        }
        
        
        
        function submitSave() {
    		
    		var checkResu = $('input[name="checkResult"]:checked').val();
    		var servFee = $("#servFeeRate option:selected").val();
    		
    		if (servFee== '1' || servFee== '') {
            	alert("请选择服务费率");
            	return false;
        	}
    		if (checkResu != '3'){
            	alert("审批结果请选择未接电话");
            	return false;
        	}
    		/* if ($.trim($("#remark").val()) == "") {
    			  alert("审批意见不能为空");
    	            return false;
    		}  */else {
    			$.ajax({
    					cache : true,
    					type : "POST",
    					url : "/call/center/saveCheck",
    					data : $('#checkForm').serialize(),
    					async : false,
    					error : function(request) {
    						alert("系统繁忙,请稍后再试");
    					},
    					success : function(data) {
    						if (data.code == "1") {
    							alert("提交成功");
    							window.location.reload(); 
    						} else {
    							alert(data.msg)
    						} 
    					}
    				});
    		}
    	}
        
        $(function (){
//            $("input[name='checkResult']").on('change',function(){	
//                if($(this).val() == 2){
//                    $(this).parent().parent().children().eq(1).removeClass("transparent_class");
//                }else{
//                    $(this).parent().parent().children().eq(1).addClass("transparent_class");
//                }
//            });
//            
//            $('#showAll').on('click',function(){
//                if(!$(this).hasClass('all')){
//                    $('.ruleDate').show();
//                    $('.snh').hide();
//                    $(this).addClass('all').text('收起<');
//                }else{
//                    $('.ruleDate').hide();
//                    $('.snh').show();
//                    $(this).removeClass('all').text('展开>');
//                }
//            })
            
                                    
            if(productId=='XJDFQ'){
    			$("#approveAmt").val('5000');
    		}else if(productId=='XJD'){
    			if(processStatus==223){
    				$("#approveAmt").val(approveAmt);
    			}else{
    				if(approveTerm==14 || approveTerm==15){
    					$("#approveAmt").val('1500');
    				}else if(approveTerm==28){
    					$("#approveAmt").val('3000');
    				}else{
    					$("#approveAmt").val('2000');
    				}
    			}    			   			    			
    		}
            
            userInfo('${userId}', '${applyId}');
        })