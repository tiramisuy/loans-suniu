<%@ page contentType="text/html;charset=UTF-8" %>
<%--催收记录--%>
  <form class="layui-form" action="">
    <fieldset class="layui-elem-field">
      <legend>催收记录</legend>
      <table class="layui-table mgt-20">
        <colgroup>
          <col width="3%">
          <col width="10%">
          <col width="7%">
          <col width="7%">
          <col width="8%">
          <col width="7%">
          <col width="8%">
          <col width="7%">
          <col width="18%">
          <col width="5%">
          <col width="10%">
          <col width="10%">
        </colgroup>
        <thead>
        <tr>
          <th>编号</th>
          <th>创建时间</th>
          <th>催收人员</th>
          <th>催收方式</th>
          <th>与本人关系</th>
          <th>联系人姓名</th>
          <th>联系人电话</th>
          <th>催收结果</th>
          <th>催收内容</th>
          <th>是否承诺付款</th>
          <th>承诺付款时间</th>
          <th>下次跟进时间</th>
        </tr>
        </thead>
        <tbody>
        {{#each list}}
        <tr>
          <td>{{addOne @index}}</td>
          <td>{{formatDate createTime "yyyy-MM-dd HH:mm:ss"}}</td>
          <td>{{operatorName}}</td>
          <td>
            {{#equal type 1}}
            电话
            {{/equal}}
            {{#equal type 2}}
            短信
            {{/equal}}
            {{#equal type 3}}
            其他
            {{/equal}}
          </td>
          <td>{{relationshipStr}}</td>
          <td>{{contactName}}</td>
          <td>{{contactMobile}}</td>
          <td>{{resultStr}}</td>
          <td>{{content}}</td>
          <td>
            {{#equal promise 0}}
            否
            {{/equal}}
            {{#equal promise 1}}
            是
            {{/equal}}
          </td>
          <td>{{formatDate promiseDate "yyyy-MM-dd"}}</td>
          <td>{{formatDate nextContactTime "yyyy-MM-dd HH:mm:ss"}}</td>
        </tr>
        {{/each}}

        </tbody>
      </table>
    </fieldset>
  </form>