<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-4-16
  Time: 上午 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/main.css">
    <link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
    <link rel="stylesheet" href="${APP_PATH }/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 项目标签</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <%@include file="/WEB-INF/jsp/common/userinfo.jsp" %>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <%@include file="/WEB-INF/jsp/common/menu.jsp" %>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 项目标签列表</div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH }/script/docs.min.js"></script>
<script src="${APP_PATH }/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

        var setting = {
            view: {
                selectedMulti: false,
                addDiyDom: function(treeId, treeNode){
                    var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                    if ( treeNode.icon ) {
                        icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
                    }
                    //获取当前节点的a标签，设置target属性值为空，避免跳转
                    $("#"+treeNode.tId+"_a").prop("target","");
                },
                addHoverDom: function(treeId, treeNode){
                    var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                    aObj.attr("href", "javascript:;");
                    if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
                    var s = '<span id="btnGroup'+treeNode.tId+'">';
                    if ( treeNode.level == 0 ) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if ( treeNode.level == 1 ) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        if (treeNode.children.length == 0) {
                            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                        }
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if ( treeNode.level == 2 ) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }

                    s += '</span>';
                    aObj.after(s);
                },
                removeHoverDom: function(treeId, treeNode){
                    $("#btnGroup"+treeNode.tId).remove();
                }
            },
            async: {
                enable: true,
                url:"tree.txt",
                autoParam:["id", "name=n", "level=lv"]
            },
            callback: {
                onClick : function(event, treeId, json) {

                }
            }
        };
        //$.fn.zTree.init($("#treeDemo"), setting); //异步访问数据

        var d = [
            {"id":1,"pid":0,"seqno":0,"name":"众筹平台项目标签","url":null,"icon":"glyphicon glyphicon-tags","open":true,"checked":false,"children":[
                    {"id":2,"pid":1,"seqno":0,"name":"手机","url":"dashboard.htm","icon":"glyphicon glyphicon-th-large","open":true,"checked":false,"children":[
                            {"id":8,"pid":7,"seqno":1,"name":"手机","url":"user/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":9,"pid":7,"seqno":1,"name":"快充","url":"role/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":10,"pid":7,"seqno":1,"name":"超级本","url":"permission/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]}
                        ]},
                    {"id":6,"pid":1,"seqno":1,"name":"数码","url":"message/index.htm","icon":"glyphicon glyphicon-th-large","open":true,"checked":false,"children":[
                            {"id":8,"pid":7,"seqno":1,"name":"电脑","url":"user/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":9,"pid":7,"seqno":1,"name":"旋转屏幕","url":"role/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":10,"pid":7,"seqno":1,"name":"超级本","url":"permission/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]}
                        ]},
                    {"id":7,"pid":1,"seqno":1,"name":"电脑","url":"","icon":"glyphicon glyphicon-th-large","open":true,"checked":false,"children":[
                            {"id":8,"pid":7,"seqno":1,"name":"电脑","url":"user/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":9,"pid":7,"seqno":1,"name":"旋转屏幕","url":"role/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]},
                            {"id":10,"pid":7,"seqno":1,"name":"超级本","url":"permission/index.htm","icon":"glyphicon glyphicon-tag","open":true,"checked":false,"children":[]}
                        ]}
                ]}
        ];
        $.fn.zTree.init($("#treeDemo"), setting, d);
    });
</script>
</body>
</html>
