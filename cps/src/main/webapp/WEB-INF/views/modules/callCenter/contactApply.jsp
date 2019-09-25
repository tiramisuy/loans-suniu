<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

 <!-- 查重信息 -->
<div role="tabpanel" class="tab-pane" id="matterInfo">
                <div class="box">
                    <div class="info importInfo">
                        <div class="title"></div>
                        <table>
                            <thead>
                                <tr>
                                    <th>关系</th>
                                    <th>姓名</th>
                                    <th>手机号码</th>
                                    <th></th>
                                    <th>订单号</th>
                                    <th>进件时间</th>
                                    <th>关系</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                </tr>
                            </thead>
                            <tbody>
                             {{#each data.list}}
        <tr>
            <td>{{sourceRelationship}}</td>
            <td>{{sourceUserName}}</td>
            <td>{{sourceMobile}}</td>
            <td class="red">匹配到---></td>
            {{#equal ../data/flag '0'}}
            <td>{{applyId}}</td>
            {{else}}
            <td>{{applyId}}</td>
            {{/equal}}
            <td>{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{relationshipStr}}</td>
            <td>{{name}}</td>
            <td>{{mobile}}</td>
        </tr>
        {{/each}}
                            </tbody>
                        </table>
                    </div>                   
                </div>
            </div>
