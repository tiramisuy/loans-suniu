<%@ page contentType="text/html;charset=UTF-8" %>
<div id="layer-photos">
<table class="table">
    <!--证件信息-->
    <div class="layui-field-box pd-10 hidden">
        <dl class="zj-dl">
            <dt>证件信息</dt>
            <dt>姓名：{{data.realName}}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp身份证号码：{{data.idNo}}</dt>
            <dd class="hand">

                {{#each data.list}}
                {{#equal bizCode 'front_idcard'}}
                <img src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
                {{/equal}}
                {{/each}}

            </dd>
        </dl>
        <dl class="zj-dl">
            <dt>&nbsp</dt>
            <dt>&nbsp</dt>
            <dd class="hand">
                {{#each data.list}}
                {{#equal bizCode 'back_idcard'}}
                <img src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
                {{/equal}}
                {{/each}}
            </dd>
        </dl>
    </div>
    <!-- 营业执照  -->
    {{#each data.list}}
    {{#equal bizCode 'enterprise_license'}}
    <div class="layui-field-box pd-10 hidden">
        <dl class="zj-dl">
            <dt>营业执照 </dt>
            <dd>
                <img src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
            </dd>
        </dl>
    </div>
    {{/equal}}
    {{/each}}
    
<!--     <div class="layui-field-box pd-10 hidden">
        <dl class="zj-dl">
            <dt>人脸识别</dt>
            {{#each data.list}}
            {{#equal bizCode 'face_verify'}}
            <dt>视频上传时间：{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</dt>
            <dd>
                <video controls="controls" preload="auto">
                    <source src="{{url}}" title="{{bizName}}" type="video/mp4"/>
                </video>
            </dd>
            {{/equal}}
            {{/each}}
        </dl>
    </div> -->

    {{#each data.list}}
    {{#equal bizCode 'face_verify'}}
	<div class="layui-field-box pd-10 hidden">
        <dl style="width:480px;height:600px;">
            <dt>人脸识别照 </dt>
            <dd>
                <img src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
            </dd>
        </dl>
    </div>
    {{/equal}}
    {{/each}}

</table>
</div>