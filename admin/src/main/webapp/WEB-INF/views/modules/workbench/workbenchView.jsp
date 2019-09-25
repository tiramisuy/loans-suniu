<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>我的工作台</title>
  <meta name="decorator" content="default"/>
  <link href="${ctxStatic}/position/basic.css" rel="stylesheet">
  <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css"  media="all">
  <link href="${ctxStatic}/position/card_type.css" rel="stylesheet" />

  <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
  <script src="${ctxStatic}/layui-master/src/layui.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {

    });

  </script>
</head>
<body class="pd-20">

<ul class="nav nav-tabs">
  <li class="active"><a href="${ctx}/workbench">我的工作台</a></li>
</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
  <div class="pull-left">
    <ol class="breadcrumb page-breadcrumb">
      <li><i class="fa fa-home"></i>&nbsp;<a href="#">我的工作台</a></li>
      <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;我的工作台</li>
    </ol>
  </div>
  <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->


<!--
<blockquote class="layui-elem-quote">
  运营数据
</blockquote>
<fieldset class="layui-elem-field hidden">
  <div class="card-type-1">
    <div class="pd-20 card-width5  hidden">
      <p class="title">注册用户数</p>
      <dl>
      
        <dt class="red">${vo.registerCurrent}个</dt>
        <dd>当日</dd>
      </dl>
      <dl>
        <dt class="c-blue">${vo.h5Total}个</dt>
        <dd>H5</dd>
      </dl>
      <dl>
        <dt class="c-blue">${vo.androidTotal}个</dt>
        <dd>Android</dd>
      </dl>
      <dl>
        <dt class="c-blue">${vo.iosTotal}个</dt>
        <dd>ios</dd>
      </dl>
      <dl>
        <dt class="c-blue">${vo.registerTotal}个</dt>
        <dd>历史</dd>
      </dl>
    </div>
  </div>
  <div class="card-type-1">
    <div class="pd-20 hidden" style="padding-bottom:140px;">
      <p class="title">申请用户数</p>
      <dl>
        <dt class="red">${vo.applyCurrent}个</dt>
        <dd>当日</dd>
      </dl>
      <dl>
        <dt class="c-blue">${vo.applyTotal}个</dt>
        <dd>历史</dd>
      </dl>
    </div>
  </div><div class="card-type-1">
  <div class="pd-20 hidden" style="padding-bottom:140px;">
    <p class="title">放款用户数</p>
    <dl>
      <dt class="red">${vo.borrowerCurrent}个</dt>
      <dd>当日</dd>
    </dl>
    <dl>
      <dt class="c-blue">${vo.borrowerTotal}个</dt>
      <dd>历史</dd>
    </dl>
  </div>
</div><div class="card-type-1">
  <div class="pd-20 hidden" style="padding-bottom:140px;">
    <p class="title">放款金额</p>
    <dl>
      <dt class="red">${vo.loanCurrent}元</dt>
      <dd>当日</dd>
    </dl>
    <dl>
      <dt class="c-blue">${vo.loanTotal}元</dt>
      <dd>历史</dd>
    </dl>
  </div>
</div>
</fieldset>
<blockquote class="layui-elem-quote mgt-30">
  平台数据
</blockquote>
<fieldset class="layui-elem-field hidden">
  <div class="card-type-1">
    <div class="pd-20 hidden">
      <p class="title">待办审批任务总数</p>
      <dl class="full">
        <dt><a class="red" href="${ctx}/loan/apply/list?stage=1"><u>${vo.taskNumber}个</u></a></dt>
      </dl>
    </div>
  </div>
  <div class="card-type-1">
    <div class="pd-20 hidden">
      <p class="title">昨日还款失败总笔数</p>
      <dl class="full">
        <dt class="red">${vo.failNumber}个</dt>
      </dl>

    </div>
  </div><div class="card-type-1">
  <div class="pd-20 hidden">
    <p class="title">昨日还款成功笔数</p>
    <dl class="full">
      <dt class="red">${vo.succesNumber}个</dt>
    </dl>

  </div>
</div><div class="card-type-1">
  <div class="pd-20 hidden">
    <p class="title">还款失败总笔数</p>
    <dl class="full">
      <dt class="red">${vo.failTotalNumber}个</dt>
    </dl>

  </div>
</div>
</fieldset>
-->

<%--<blockquote class="layui-elem-quote mgt-30">--%>
  <%--图标数据--%>
<%--</blockquote>--%>
<%--<fieldset class="layui-elem-field hidden pd-20">--%>
  <%--<div class="card-50">--%>
    <%--sadsadf--%>
  <%--</div>--%>
  <%--<div class="card-50">--%>
    <%--sadsadf--%>
  <%--</div>--%>
<%--</fieldset>--%>



</body>
</html>