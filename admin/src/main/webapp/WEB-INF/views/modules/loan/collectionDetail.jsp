<%@ page contentType="text/html;charset=UTF-8" %>

<%--催收详情--%>

<form class="layui-form" action="">
  <%--联系人信息start--%>
  <fieldset class="layui-elem-field">
    <legend>逾期记录</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
      <colgroup>
        <col width="200">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="200">
        <col width="150">
        <col width="200">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="150">
        <col width="200">
        <col width="150">
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
        <th>历史逾期次数</th>
        <th>历史最长逾期天数</th>
        <th>还款状态</th>
        <th>标签</th>
      </tr>
      </thead>
      <tbody>
      <%--<c:forEach var="item" items="${detailList}" varStatus="xh">--%>
      {{#each detailList}}
        <tr>
          <td>{{contNo}}</td>
          <td>{{approveTerm}}天</td>
          <td>{{principal}}</td>
          <td>{{currentInterest}}</td>
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
          <td>{{overdueTimes}}</td>
          <td>{{maxOverdueDays}}</td>
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
  </fieldset>

  <fieldset class="layui-elem-field">
    <legend>催收办理</legend>
    <form id="submitForm"  class="layui-form" >

      <div class="layui-form-item">
        <div class="layui-inline" style="width:1150px">
          <div class="layui-form-item" >
            <label class="layui-form-label">催收方式</label>

            <div class="layui-input-inline">
              <select id="contactType">
                <option value="">--请选择--</option>
                <option value="1">电话</option>
                <option value="2">短信</option>
                <option value="3">其他</option>
              </select>
            </div>
            <label class="layui-form-label">与本人关系</label>

            <div class="layui-input-inline">
              <select id="contactId"  class="input-medium">
                <option value="">--请选择--</option>
                {{#each contactList}}
                  <option value="{{id}}">{{detail}}</option>
                {{/each}}
                  <option value="00">其他</option>

                <%--<c:forEach items="${contactList}" var="detail">--%>
                  <%--<option value="${detail.id}">${detail.detail}</option>--%>
                <%--</c:forEach>--%>
              </select>
            </div>
            <label class="layui-form-label">催收结果</label>

            <div class="layui-input-inline">
              <select id="result"  class="input-medium">
                <option value="">--请选择--</option>
                {{#each resultList}}
                <option value="{{value}}">{{value}}-{{desc}}</option>
                {{/each}}
                <%--<c:forEach items="${resultList}" var="detail">--%>
                  <%--<option value="${detail.value}">${detail.value}-${detail.desc}</option>--%>
                <%--</c:forEach>--%>
              </select>
            </div>
          </div>

          <div class="layui-form-item layui-form-text">
            <label class="layui-form-label"><em class="required">*</em>催收内容</label>

            <div class="layui-input-block">
              <textarea placeholder="请输入内容" class="layui-textarea" id="content"></textarea>
            </div>
          </div>
          <div class="layui-form-item" style="width: 100%">

            <label class="layui-form-label">是否承诺付款</label>

            <div class="layui-input-inline">
              <select id="promise" lay-filter="type">
                <option value="">--请选择--</option>
                <option value="1">是</option>
                <option value="0">否</option>
              </select>
            </div>
            <div id="promiseDateDiv">
              <label class="layui-form-label">承诺付款时间</label>

              <div class="layui-input-inline">
                <input id="promiseDateStr" type="text" readonly="readonly" maxlength="20"
                       class="input-middle Wdate"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
              </div>
            </div>
            <label class="layui-form-label">下次跟进时间</label>

            <div class="layui-input-inline">
              <input id="nextContactTimeStr" type="text" readonly="readonly" maxlength="20"
                     class="input-middle Wdate"
                     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </div>
            <button class="layui-btn" lay-submit="" lay-filter="demo1" onClick="return doSubmit()">提交</button>
          </div>
        </div>

      </div>
    </form>
  </fieldset>

</form>

