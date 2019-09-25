<%@ page contentType="text/html;charset=UTF-8" %>
<h4 class="header blue">逾期记录</h4>
<table class="table">
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
        <th>借款时间</th>
        <th>应还日期</th>
        <th>实还时间</th>
        <!--
        <th>历史逾期次数</th>
        <th>历史逾期最长天数</th>
        -->
        <th>还款状态</th>
        <th>标签</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>{{repayDetailListVO.contNo}}</td>
        <td>{{repayDetailListVO.approveTerm}}天</td>
        <td>{{repayDetailListVO.principal}}</td>
        <td>{{repayDetailListVO.interest}}</td>
        <td>
            {{#if repayDetailListVO.borrow}}
            {{repayDetailListVO.borrow}}天
            {{/if}}
        </td>
        <td>
            {{#if repayDetailListVO.overdue}}
            {{repayDetailListVO.overdue}}天
            {{/if}}
        </td>
        <td>{{repayDetailListVO.penalty}}</td>
        <%--<td>{{overdueFee*overdue}}</td>--%>
        <td>{{repayDetailListVO.overdueFee}}</td>
        <td>{{repayDetailListVO.termServFee}}</td>
        <td>{{repayDetailListVO.deduction}}</td>
        <td>{{repayDetailListVO.totalAmount}}</td>
        <td>{{repayDetailListVO.actualRepayAmt}}</td>
        <td>{{repayDetailListVO.subAmt}}</td>
        <td>{{formatDate repayDetailListVO.loanStartDate "yyyy-MM-dd HH:mm:ss"}}</td>
        <td>{{formatDate repayDetailListVO.repayDate "yyyy-MM-dd"}}</td>
        <td>{{formatDate repayDetailListVO.actualRepayTime "yyyy-MM-dd HH:mm:ss"}}</td>
        <!--
        <td>{{repayDetailListVO.overdueTimes}}</td>
        <td>{{repayDetailListVO.maxOverdueDays}}</td>
        -->
        <td>
            {{#equal repayDetailListVO.status 1}}
            <div class="text-blue">已还款</div>
            {{/equal}}
            {{#equal repayDetailListVO.status 0}}
            <div class="required">待还款</div>
            {{/equal}}
        </td>
        <td>
            {{#equal repayDetailListVO.sign 0}}
            <div>正常</div>
            {{/equal}}
            {{#equal repayDetailListVO.sign 1}}
            <div class="text-blue">提前</div>
            {{/equal}}
            {{#equal repayDetailListVO.sign 2}}
            <div class="required">逾期</div>
            {{/equal}}
        </td>
    </tr>
</table>
{{#equal source 1}}
<form class="breadcrumb  form-search" id="myform">
    <input id="repayPlanItemId" name="repayPlanItemId" type="hidden" value="{{repayDetailListVO.id}}"/>
    <h4 class="header blue">减免办理（限时减免， 有效期仅到当日24：00前）</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>减免金额</label>
                <input type="text" id="deduction" name="deduction" class="{required:true,number:true,min:0.01}" style="width:50%;"/>
            </li>
        </ul>
    </div>

    <div class="control-group">
        <ul class="ul-form">
            <li style=" width: 10%;"><label>减免理由</label></li>
            <div class="layui-input-block" style="margin-left: 112px;">
                <textarea placeholder="200字以内" class="layui-textarea" name="remark" maxlength="200"></textarea>
            </div>
        </ul>
    </div>
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block">
            <button type="submit"  class="layui-btn">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.close(layer.index);">取消</button>
        </div>
    </div>
</form>
{{/equal}}

<h4 class="header blue">减免记录</h4>
<table class="table">
    <thead>
    <tr>
        <th>编号</th>
        <th>提交时间</th>
        <th>提交人员</th>
        <th>减免金额</th>
        <th>减免理由</th>
        <th>审核时间</th>
        <th>审核人员</th>
        <th>审核状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    {{#each list}}
    <tr>
        <td>{{id}}</td>
        <td>{{formatDate createTime "yyyy-MM-dd HH:mm:ss"}}</td>
        <td>{{createBy}}</td>
        <td>{{deduction}}</td>
        <td>{{remark}}</td>
        <td>{{formatDate approveTime "yyyy-MM-dd HH:mm:ss"}}</td>
        <td>{{approverName}}</td>
        <td>
            {{#equal status 0}}
            <div>待审核</div>
            {{/equal}}
            {{#equal status 1}}
            <div>通过</div>
            {{/equal}}
            {{#equal status 2}}
            <div>不通过</div>
            {{/equal}}
        </td>
        <td>
            <%--{{#equal source 2}}--%>
            {{#equal status 0}}
            <a href="javascript:void(0)" class="text-blue" onclick="approve('{{id}}','{{repayPlanItemId}}','1')">通过</a>
            <a href="javascript:void(0)" class="text-blue" onclick="approve('{{id}}','{{repayPlanItemId}}','2')">不通过</a>
            {{/equal}}
            <%--{{/equal}}--%>
        </td>

    </tr>
    {{/each}}
</table>