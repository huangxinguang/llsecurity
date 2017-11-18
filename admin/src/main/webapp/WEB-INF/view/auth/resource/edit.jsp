<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>资源编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="static/css/common.css" media="all" />

    <link rel="stylesheet" href="static/btree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;">
    <input type="hidden" name="id" value="${resource.id}">

    <div class="layui-form-item">
        <label class="layui-form-label">资源名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${resource.name}"  name="name" lay-verify="required">
        </div>

    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">资源编码</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="code" value="${resource.code}" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">父节点</label>
        <div class="layui-input-block">
            <ul id="roleTree" class="ztree"></ul>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">url</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="url" value="${resource.url}"  lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-inline">
            <select name="type" lay-filter="type" lay-verify="required">
                <option value=""></option>
                <c:choose>
                    <c:when test="${resource.type==1}">
                        <option value="1" selected>菜单</option>
                        <option value="2">按钮</option>
                    </c:when>
                    <c:otherwise>
                        <option value="1">菜单</option>
                        <option value="2" selected>按钮</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="sort" value="${resource.sort}" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图标</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="icon" value="${resource.icon}"  lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="" lay-filter="addUser">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript">
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;


        form.on('submit(addUser)',function(data){
            var form = new FormData($("#data-form")[0]);

            var parentId = onCheck();
            $.ajax({
                url: "resource/saveOrUpdate.do?parentId="+parentId,
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("添加成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
            return false;
        });

    });
</script>

<script type="text/javascript">
    var setting = {
        view: {
            addHoverDom: false,
            removeHoverDom: false,
            selectedMulti: true
        },
        check: {
            enable: true,
            chkStyle: "radio",  //单选框
            radioType: "all",
            chkboxType:  { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true,
                idKey:"id",
                pidKey:"pid",
            }
        },
        edit: {
            enable: false
        }
    };
    var zNodes;
    $(document).ready(function(){
        $.ajax({
            async : false,
            cache : false,
            type : 'get',
            data : {"id":${resource.id}},
            dataType : 'json',
            url : "resource/selectedResourceTree.do",
            error : function() {
                layer.msg('亲，请求失败！');
            },
            success : function(data) {
                zNodes = data;
                $.fn.zTree.init($("#roleTree"), setting, zNodes);
            }
        });
    });

    function onCheck() {
        var treeObj = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes.length == 0) {
            return -1;
        }
        return nodes[0].id;
    }

</script>
</body>
</html>