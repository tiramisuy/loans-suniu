<%@ page contentType="text/html;charset=UTF-8" %>

<style>
.layui-layer-page .layui-layer-content input{width:175px;}
</style>

<form class="breadcrumb  form-search">
    <h4 class="header blue">身份信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>姓名</label>
                <input type="text" name="realName" value="{{realName}}" class="medium" readonly="readonly">
            </li>
            <li><label>手机号码</label>
                <input type="text" name="mobile" value="{{mobile}}" class="medium" readonly="readonly">
            </li>
            <li><label>证件类型</label>
                <input type="text" name="idType" value="{{idType}}" class="medium" readonly="readonly">
            </li>
            <li><label>证件号码</label>
                <input type="text" name="idNo" value="{{idNo}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>性别</label>
                <input type="text" name="sexStr" value="{{sexStr}}" class="medium" readonly="readonly">
            </li>
            <li><label>年龄</label>
                <input type="text" name="age" value="{{age}}" class="medium" readonly="readonly">
            </li>
            <li><label>发证机关</label>
                <input type="text" name="idRegOrg" value="{{idRegOrg}}" class="medium" readonly="readonly">
            </li>
            <li class="time_li"><label>证件有效期</label>
                <input type="text" name="idTermBegin" value="{{idTermBegin}}" class="input-small Wdate"
                       readonly="readonly">
                <h>-</h>
                <input type="text" name="idTermEnd" value="{{idTermEnd}}" class="input-small Wdate" readonly="readonly">
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>证件地址</label>
                <input type="text" name="regAddr" value="{{regAddr}}" class="medium" readonly="readonly">
            </li>
            <li><label>最高学历</label>
                <input type="text" name="degree" value="{{degree}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>

    <h4 class="header blue">单位信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>单位名称</label>
                <input type="text" name="comName" value="{{comName}}" class="medium" readonly="readonly">
            </li>
            <li><label>工作地址</label>
                <input type="text" name="workAddr" value="{{workAddr}}" class="medium" readonly="readonly">
            </li>

        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>工作岗位</label>
                <input type="text" name="workPosition" value="{{workPosition}}" class="medium" readonly="readonly">
            </li>
            <li class="tel_li"><label>办公电话</label>
                <input type="text" name="comTelZone" value="{{comTelZone}}" class="medium" readonly="readonly">
                <h>-</h>
                <input type="text" name="comTelNo" value="{{comTelNo}}" class="medium" readonly="readonly">
                <h>-</h>
                <input type="text" name="comTelExt" value="{{comTelExt}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>
    <h4 class="header blue">家庭信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>居住地址</label>
                <input type="text" name="resideAddr" value="{{resideAddr}}" class="medium" readonly="readonly">
            </li>
            <li><label>居住时长</label>
                <input type="text" name="resideYear" value="{{resideYear}}" class="medium" readonly="readonly">
            </li>
            <li><label>婚姻状况</label>
                <input type="text" name="marital" value="{{marital}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>
</form>
<h4 class="header blue">联系人信息</h4>
<table class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>手机号码</th>
        <th>与本人关系</th>
    </tr>
    </thead>
    <tbody>
    {{#each contactList}}
    <tr>
        <td>{{addOne @index}}</td>
        <td>{{name}}</td>
        <td>{{mobile}}</td>
        <td>{{relationshipStr}}</td>
    </tr>
    {{/each}}
    </tbody>
</table>

<form class="breadcrumb  form-search">
    <h4 class="header blue">运营商信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>运营商</label>
                <input type="text" name="" value="" class="medium" readonly="readonly">
            </li>
            <li><label>手机号码</label>
                <input type="text" name="mobile" value="{{mobile}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>
    <h4 class="header blue">其他信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>QQ号码</label>
                <input type="text" name="qq" value="{{qq}}" class="medium" readonly="readonly">
            </li>
        </ul>
    </div>
    <h4>账户信息</h4>

    <div class="control-group">
        <ul class="ul-form">
            <li><label>创建时间</label>
                <input type="text" name="createTime" value="{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}"
                       class="medium"
                       readonly="readonly">
            </li>
            <li><label>更新时间</label>
                <input type="text" name="updateTime" value="{{formatDate updateTime 'yyyy-MM-dd HH:mm:ss'}}"
                       class="medium"
                       readonly="readonly">
            </li>
            <li><label>是否冻结</label>
                <input type="text" name="status" value="{{#equal status 0}}是{{/equal}}{{#equal status 1}}否{{/equal}}"
                       class="medium" readonly="readonly">
            </li>
            <li><label>是否黑名单</label>
                <input type="text" name="blacklist"
                       value="{{#equal blacklist 1}}是{{/equal}}{{#equal blacklist 0}}否{{/equal}}" class="medium"
                       readonly="readonly">
            </li>
        </ul>
    </div>
</form>