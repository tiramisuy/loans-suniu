<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/position/basic.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css" media="all">
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.form.js"></script>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style>
.cust-btn-bottom {text-align:center;margin-top:15px;}
.cust-btn-bottom input{width:120px;height:35px;display:inline-block;margin-right:15px;}

.select_line {
    display: block!important;
    width: 170px;
    height: 30px;
    border: 1px solid #eee;
}

.layui-form-label {
    width: 100px;
}


</style>

<script type="text/javascript">

function update(){
	
	var id=$("#id").val();
	var historyId = $("#historyId").val();
	var degree = $("#degree").val();
	if(degree == null || degree == ''  ){
		alert("请选择学历！");
		return;
	}
	/* var house = $("#house").val();
	var houseLoan= $("#houseLoan").val();
	var car =$("#car").val();
	var carLoan = $("#carLoan").val(); */
	var industry = $("#industry").val();
	/* var jobCompanyType= $("#jobCompanyType").val();
	var jobCompanyScale = $("#jobCompanyScale").val(); */
	var workPosition = $("#workPosition").val();
	var jobYears = $("#jobYears").val();
	var jobSalary= $("#jobSalary").val();
	
	$.ajax({
		url: "${ctx}/sys/custUser/updateCustuser",
	    type: 'post',
	    data: {
	    	id:id,
	    	applyId:historyId,
	    	degree:degree,
	    	/* house:house,
	    	houseLoan:houseLoan,
	    	car:car,
	    	carLoan:carLoan, */
	    	industry:industry,
	    	/* jobCompanyType:jobCompanyType,
	    	jobCompanyScale:jobCompanyScale, */
	    	workPosition:workPosition,
	    	workYear:jobYears,
	    	indivMonthIncome:jobSalary
	    
	    },
	    error: function (request) {
	         alert("系统繁忙,请稍后再试");
	    },
	    success: function (data) {
	    	if(data.code == 1){
	    		alert("处理成功！");
		    	parent.freshCust();
		    	var index = parent.layer.getFrameIndex(window.name);
		    	parent.layer.close(index);
	    	}else{
	    		alert(data.msg);
	    	}
	    	
	    	

	    },
	 });
}

function returnParent(){
	parent.freshCust();
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}


</script>


<form id="userInfo" class="layui-form" action="${ctx}/sys/custUser/updateCustuser">

	<input type="hidden"  id="id" name="id"  value="${custUser.id }"  /> 
	<input type="hidden"  id="historyId" name="historyId"  value="${historyId }"  /> 
   	<fieldset class="layui-elem-field">
        <legend>身份信息</legend>
        <div class="layui-field-box">
            <div  class="layui-form-item"  style="position: relative" >
                <div class="layui-inline">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-inline">
                    	<span style="color:red;padding: 9px 15px;display: inline-block;" >${custUser.userName}</span>
                    </div>
                </div>
                <div class="layui-inline">
                     <label class="layui-form-label">手机号码</label>
                    <div class="layui-input-inline">
                    	<span style="color:red;padding: 9px 15px;display: inline-block;" >${custUser.mobile}</span>
                    </div>
                </div>
                
                <div class="layui-inline">
                    <label class="layui-form-label">证件类型</label>
                    <div class="layui-input-inline">
                    	<span style="color:red;padding: 9px 15px;display: inline-block;" >
                    		<c:forEach var="item" items = "${idTypelist}">
								 <c:if test="${item.id == custUser.idType }">
								 	${item.desc}  
								 </c:if> 		
	                    	</c:forEach> 
                    	</span>
                    </div>
                </div>
                 <div class="layui-inline">
                    <label class="layui-form-label">证件号码</label>
                    <div class="layui-input-inline">
                    	<span style="color:red;padding: 9px 15px;display: inline-block;" >${custUser.idNo}</span>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">最高学历</label>
					<div class="layui-input-inline">
	                    <select id="degree" name="degree" class="select_line">
	                    	<option value="">请选择</option>
	                    	<c:forEach var="item" items = "${education}">
								  <option value="${item.key}" <c:if test="${item.key == custUser.degree }">selected</c:if> >${item.value} </option>       		
	                    	</c:forEach> 
	                    </select>
                    </div>
                </div>
                <%-- <div class="layui-inline">
                    <label class="layui-form-label">是否有房</label>
                    <div class="layui-input-inline">
                    	<select id="house" name="house" class="select_line" >
                    	 	<option value="">请选择</option>
                    		<c:forEach var="item" items = "${haveHouse}">
								  <option value="${item.key}" <c:if test="${item.key == house }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach> 
                    	</select>
                      
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有房贷</label>
                   <div class="layui-input-inline">
                    	<select id="houseLoan" name="houseLoan" class="select_line" >
                    		 <option value="">请选择</option>
                    		 <c:forEach var="item" items = "${houseLoan }">
								  <option value="${item.key}" <c:if test="${item.key == cust_houseLoan }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach>
                    	</select>
                        
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有车</label>
                    <div class="layui-input-inline">
                    	<select id="car" name="car" class="select_line" >
                    		<option value="">请选择</option>
                    		<c:forEach var="item" items = "${haveCar }" >
								  <option value="${item.key}" <c:if test="${item.key == car }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach>
                    	</select>
                   </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有车贷</label>
                     <div class="layui-input-inline">
                    	<select id="carLoan" name="carLoan" class="select_line" >
                    		<option value="">请选择</option>
                    		<c:forEach var="item" items = "${carLoan }" >
								  <option value="${item.key}" <c:if test="${item.key == cust_carLoan }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach>
                    	</select>
                      </div>  
                </div> --%>
            </div>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>单位信息</legend>
        <div class="layui-field-box">
			<div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">公司行业</label>
 					<div class="layui-input-inline">
                    	<select id="industry" name="industry" class="select_line" >
                    		<option value="">请选择</option>
                    		<c:forEach var="item" items = "${jobIndustry }" >
								  <option value="${item.key}" <c:if test="${item.key == custUser.industry }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach>
                    	</select>
                     </div>
               </div>
                <%-- <div class="layui-inline">
                    <label class="layui-form-label">公司类型</label>
					<div class="layui-input-inline">
					<select id="jobCompanyType" name="jobCompanyType" class="select_line" >
						<option value="" >请选择</option>
                    		<c:forEach var="item" items = "${jobCompanyType }" >
								  <option value="${item.key}" <c:if test="${item.key == workCategory }">selected</c:if> >${item.value} </option>       		
                    		</c:forEach>
                    	</select>
                   </div>
               </div> 
               <div class="layui-inline">
                    <label class="layui-form-label">公司规模</label>
					<div class="layui-input-inline">
					<select id="jobCompanyScale"  name="jobCompanyScale"  class="select_line">
						<option value="">请选择</option>
                    	<c:forEach var="item" items = "${jobCompanyScale }" >
							<option value="${item.key}" <c:if test="${item.key == workSize }">selected</c:if> >${item.value} </option>       		
                    	</c:forEach>
                    </select>
                    </div>
                 </div> --%>
                <div class="layui-inline">  
                    <label class="layui-form-label">工作岗位</label>
					<div class="layui-input-inline">
                        <input type="text" class="select_line" id="workPosition" name="workPosition" value="${custUser.workPosition}"
                               class="layui-input"  maxlength="15">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作时间</label>
					<div class="layui-input-inline">
					<select id="jobYears"  name="jobYears" class="select_line" >
					 	<option value="">请选择</option>
                    	<c:forEach var="item" items = "${jobYears }">
							  <option value="${item.key}" <c:if test="${item.key == custUser.workYear }">selected</c:if> >${item.value} </option>       		
                    	</c:forEach> 
                    </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作收入</label>
					<div class="layui-input-inline">
					<select id="jobSalary"  name="jobSalary" class="select_line" >
					 	<option value="">请选择</option>
                    	<c:forEach var="item" items = "${jobSalary }">
							<option value="${item.key}" <c:if test="${item.key == custUser.indivMonthIncome }">selected</c:if> >${item.value} </option>       		
                    	</c:forEach>
                    </select>
                   	</div>
                </div>
			</div>
        </div>
        </fieldset>
        <div class="cust-btn-bottom">
        	<input type="button" id="submit"  class="layui-btn layui-btn-info"  onclick="update(); "  value="确定" >
        	<input type="button" id="return"  class="layui-btn layui-btn-info"  onclick="returnParent();"  value="取消" >
        </div>
        
</form>

