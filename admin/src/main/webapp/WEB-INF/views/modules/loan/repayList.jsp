<%@ page contentType="text/html;charset=UTF-8" %>
<table class="table">
    <thead>
    <tr>
        <th>待还ID</th>
        <th>合同编号</th>
        <th>借款人姓名</th>
        <th>期数</th>
        <th>应还本金（元）</th>
        <th>应还利息（元）</th>
        <!-- <th>每日利息（元）</th> -->
        <th>已借期限</th>
        <th>提前天数</th>
        <th>提前违约金（元）</th>
        <th>分期服务费（元）</th>
        <th>逾期天数</th>
        <th>逾期利息（元）</th>
        <th>逾期管理费</th>
        <th>减免金额（元）</th>
        <th>应还金额（元）</th>
        <th>实还金额（元）</th>
        <th>借款时间</th>
        <th>应还日期</th>
        <th>实还时间</th>
        <th>订单状态</th>
        <th>标签</th>
    </tr>
    </thead>
    <tbody>
    {{#each list}}
    <tr>
        <td>{{id}}</td>
        <td>{{contNo}}</td>
        <td>{{userName}}</td>
        <td>{{thisTerm}}/{{totalTerm}}</td>
        <td>{{principal}}</td>
        <td>{{interest}}</td>
        <!-- <td>{{everydayInterest}}</td> -->
        <td>
            {{#if borrow}}
            {{borrow}}天
            {{/if}}
        </td>
        <td>
            {{#if ahead}}
            {{ahead}}天
            {{/if}}
        </td>
        <td>{{prepayFee}}</td>
        <td>{{termServFee}}</td>
        <td>
            {{#if overdue}}
            {{overdue}}天
            {{/if}}
        </td>
        <td>{{penalty}}</td>
        <td>{{overdueFee}}</td>
        <td>{{deduction}}</td>
        <td>{{totalAmount}}</td>
        <td>{{actualRepayAmt}}</td>
        <td>{{formatDate loanStartDate "yyyy-MM-dd HH:mm:ss"}}</td>
        <td>{{formatDate repayDate "yyyy-MM-dd"}}</td>
        <td>{{formatDate actualRepayTime "yyyy-MM-dd HH:mm:ss"}}</td>
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
            {{#equal sign 3}}
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
</table>