<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />  
<meta http-equiv="Expires" content="-1" />  
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>pay</title>
<style>
.box{ width:450px; height:240px; margin:0 auto; position:absolute; top:50%; left:50%; margin-top:-99px; margin-left:-225px; text-align:center;}
</style>
</head>
<body>
        <div class="box"><img src="../images/jump_pic01.png" /></div>
       
              

 <form name="pay" method="post" action="https://pay.fuiou.com/smpGate.do" id = "form">
<input type="hidden" value = '<%=request.getAttribute("md5")%>' name="md5"/>
<input type="hidden" value = '<%=request.getAttribute("mchnt_cd")%>' name="mchnt_cd"/>
<input type="hidden" value = '<%=request.getAttribute("order_id")%>' name="order_id"/>
<input type="hidden" value = '<%=request.getAttribute("order_amt")%>' name="order_amt"/>
<input type="hidden" value = '<%=request.getAttribute("order_pay_type")%>' name="order_pay_type"/>
<input type="hidden" value = '<%=request.getAttribute("page_notify_url")%>' name="page_notify_url"/>
<input type="hidden" value = '<%=request.getAttribute("back_notify_url")%>' name="back_notify_url"/>
<input type="hidden" value = '<%=request.getAttribute("order_valid_time")%>' name="order_valid_time"/>
<input type="hidden" value = '<%=request.getAttribute("iss_ins_cd")%>' name="iss_ins_cd"/>
<input type="hidden" value = '<%=request.getAttribute("goods_name")%>' name="goods_name"/>
<input type="hidden" value = '<%=request.getAttribute("goods_display_url")%>' name="goods_display_url"/>
<input type="hidden" value = '<%=request.getAttribute("rem")%>' name="rem"/>
<input type="hidden" value = '<%=request.getAttribute("ver")%>' name="ver"/>
</form>              
        
        
        
</body>
</html>
 <script language="javascript" type="text/javascript">
 window.onload=function(){
 document.getElementById("form").submit();
 }
  </script>
