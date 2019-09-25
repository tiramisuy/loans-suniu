$(function() {
	registerFormValidateEvent();
	setRetryTimesStatus();
	$("#failStrategy").change(function(){
		setRetryTimesStatus();
	});
	$("#cron").change(function(){
		$("#cronExpression").val($("#cron").val());
	});
	
});

function setRetryTimesStatus(){
	var failStrategy = $("#failStrategy").find("option:selected").val();
	if('ALARM'==failStrategy){
		$("#retryTimes").attr("readonly","readonly");
		$("#retryTimes").val(0);
	}
	if('RETRY'==failStrategy){
		$("#retryTimes").removeAttr("readonly");
	}
}

function registerFormValidateEvent() {
	$("#form").validate({
		rules: {
			taskName: {
				required: true,
				abc:true
			},
			groupName: {
				required: true,
				abc:true
			},
			description: {
				required: true
			},
			targetObject: {
				required: true,
				abc:true
			},
			targetMethod: {
				required: true,
				abc:true
			},
			retryTimes: {
				required: true,
				digits:true
			},
			cronExpression: {
				required: true
			},
			remark: {
				maxlength: 100
			}
		},
		messages: {
			taskName: {
				required: "请填写任务名称"
			},
			groupName: {
				required: "请填写任务分组名称"
			},
			description: {
				required: "请填写任务说明"
			},
			targetObject: {
				required: "请填写目标对象"
			},
			targetMethod: {
				required: "请填写执行方法"
			},
			retryTimes: {
				required: "请填写重试次数",
				digits: "请填写整数"
			},
			cronExpression: {
				required: "请填写Cron表达式"
			},
			remark: {
				required: "备注信息长度应控制在100个字符"
			}
		}
	});
}