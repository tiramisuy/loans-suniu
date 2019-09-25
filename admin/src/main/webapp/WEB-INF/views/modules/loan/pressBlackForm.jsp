<%@ page contentType="text/html;charset=UTF-8" %>
<legend>黑名单信息</legend>
<table class="table">
    <thead>
    <tr>
        <th>借款人姓名</th>
        <th>手机号码</th>
        <th>证件号码</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>{{userRealName}}</td>
        <td>{{userMobile}}</td>
        <td>{{userIdNo}}</td>
    </tr>
</table>

<legend>逾期记录</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
      <colgroup>
      </colgroup>
      <thead>
      <tr>
        <th>合同编号</th>
        <th>借款期限</th>
        <th>应还本金（元）</th>
        <th>应还利息（元）</th>
        <th>已借期限</th>
        <th>逾期天数</th>
        <th>逾期利息（元）</th>
        <th>逾期管理费</th>
        <th>分期服务费</th>
        <th>减免金额（元）</th>
        <th>应还总额（元）</th>
        <th>实还总额（元）</th>
        <th>差额（元）</th>
        <th>借款日期</th>
        <th>应还日期</th>
        <th>实还日期</th>
        <!--
        <th>历史逾期次数</th>
        <th>历史最长逾期天数</th>
        -->
        <th>还款状态</th>
        <th>标签</th>
      </tr>
      </thead>
      <tbody>
      <%--<c:forEach var="item" items="${detailList}" varStatus="xh">--%>
      {{#each collectionDetailVo}}
        <tr>
          <td>{{contNo}}</td>
          <td>{{approveTerm}}天</td>
          <td>{{principal}}</td>
          <td>{{interest}}</td>
          <td>{{borrow}}天</td>
          <td>{{overdue}}天</td>
          <td>{{penalty}}</td>
          <td>{{overdueFee}}</td>
          <td>{{termServFee}}</td>
          <td>{{deduction}}</td>
          <td>{{totalAmount}}</td>
          <td>{{actualRepayAmt}}</td>
          <td>{{subAmt}}</td>
          <td>{{formatDate loanStartDate "yyyy-MM-dd HH:mm:ss"}}</td>
          <td>{{formatDate repayDate "yyyy-MM-dd"}}</td>
          <td>{{formatDate actualRepayTime "yyyy-MM-dd HH:mm:ss"}}</td>
          <!--
          <td>{{overdueTimes}}</td>
          <td>{{maxOverdueDays}}</td>
          -->
          <td>
            {{#equal status 1}}
            <div class="text-blue">已还款</div>
            {{/equal}}
            {{#equal status 0}}
            <div class="required">待还款</div>
            {{/equal}}
          </td>
          <td>
            {{#equal sign 0}}
            <div>正常</div>
            {{/equal}}
            {{#equal sign 1}}
            <div class="text-blue">提前</div>
            {{/equal}}
            {{#equal sign 2}}
            <div class="required">逾期</div>
            {{/equal}}
          </td>
        </tr>
      {{/each}}
      <%--</c:forEach>--%>
      </tbody>
    </table>
    
    
<form class="breadcrumb  form-search" id="myBlackForm">
    <input id="userId" name="userId" type="hidden" value="{{userId}}"/>
    <input id="applyId" name="applyId" type="hidden" value="{{applyId}}"/>
     <input id="itemId" name="itemId" type="hidden" value="{{itemId}}"/>
    
	<legend>加黑名单办理</legend>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>拉黑理由</label></li>
            <div class="layui-input-block">
                <textarea placeholder="50字以内" class="layui-textarea" name="remark" id="remark" maxlength="50"></textarea>
            </div>
        </ul>
    </div>
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block">
            <button type="button"  class="layui-btn" onclick="submitBlackList();">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.close(layer.index);">取消</button>
        </div>
    </div>
</form>
    
    