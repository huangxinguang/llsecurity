<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>后台管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
    <link rel="stylesheet" href="static/css/main.css" media="all" />
</head>
<body class="childrenBody">
<div class="panel_box row">
    <div class="panel col">
        <a href="javascript:;" data-url="photograph/handlingList.do">
            <div class="panel_icon">
                <i class="layui-icon" data-icon="&#xe64a;">&#xe64a;</i>
            </div>
            <div class="panel_word photograph">
                <span></span>
                <cite>待处理摄影</cite>
            </div>
        </a>
    </div>

    <div class="panel col">
        <a href="javascript:;" data-url="user/list.do">
            <div class="panel_icon" style="background-color:#009688;">
                <i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
            </div>
            <div class="panel_word userAll">
                <span></span>
                <cite>注册用户总数</cite>
            </div>
        </a>
    </div>
    <div class="panel col max_panel">
        <a href="javascript:;" data-url="article/list.do">
            <div class="panel_icon" style="background-color:#2F4056;">
                <i class="iconfont icon-text" data-icon="icon-text"></i>
            </div>
            <div class="panel_word articleAll">
                <span></span>
                <cite>文章总数</cite>
            </div>
        </a>
    </div>
</div>

<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/main.js"></script>
</body>
</html>