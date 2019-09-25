<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="验证码输入框名称"%>
<%@ attribute name="inputCssStyle" type="java.lang.String" required="false" description="验证框样式"%>
<%@ attribute name="imageCssStyle" type="java.lang.String" required="false" description="验证码图片样式"%>
<%@ attribute name="buttonCssStyle" type="java.lang.String" required="false" description="看不清按钮样式"%>
<input type="text" id="${name}" name="${name}" placeholder="图形验证码" maxlength="5" class="required" style="${inputCssStyle}"/>
<a href="javascript:" onclick="$('.${name}').attr('src','${pageContext.request.contextPath}/validateCode.jpg?'+new Date().getTime());" class="mid ${name}Refresh" style="${buttonCssStyle}">
<img src="${pageContext.request.contextPath}/validateCode.jpg" onclick="$('.${name}Refresh').click();" class="mid ${name}" style="${imageCssStyle}"/>
</a> 
