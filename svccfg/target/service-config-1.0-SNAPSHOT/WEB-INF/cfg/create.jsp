<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link rel="stylesheet" href="../../static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../../static/sweetalert/sweet-alert.css">
    <link rel="stylesheet" href="../../static/css/validation.css">
</head>
<body>
<div class="text-center"><h1>配置服务描述文件</h1></div>
<form id="cfgCreateForm" class="form-horizontal" style="margin-top: 60px;" role="form" action="save" method="post">
    <div class="content-section-a">
        <!-- line 1 -->
        <div class="form-group">
            <label for="component" class="col-sm-offset-1 col-sm-1 control-label">服务组件</label>
            <div class="col-sm-2">
                <select class="form-control" id="component" name="component">
                    <option value="">请选择！</option>
                    <c:forEach var="provider" items="${providers}">
                        <option value="${provider.abb}">${provider.name}</option>
                    </c:forEach>
                </select>
            </div>

            <label for="category" class="col-sm-1 control-label">服务分类</label>
            <div class="col-sm-2">
                <select class="form-control" id="category" name="category">
                    <option value="">请选择！</option>
                    <option value="组件服务">组件服务</option>
                    <option value="代理服务">代理服务</option>
                    <option value="聚合服务">聚合服务</option>
                </select>
            </div>
            <div class="col-sm-offset-1 col-sm-1">
                <button type="button" class="btn btn-large btn-primary btn-block" id="preview">预览</button>
            </div>
            <div class="col-sm-1">
                <button type="submit" class="btn btn-large btn-success btn-block" >提交</button>
            </div>
        </div>
        <!-- line 2 -->
        <div class="form-group">
            <label for="code" class="col-sm-offset-1 col-sm-1 control-label">服务码</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="code" name="code" maxlength="9"
                       placeholder="GW0000001">
            </div>
            <label for="svcname" class="col-sm-1 control-label">服务名称</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="svcname" name="svcname" maxlength="50"
                       placeholder="产品查询服务">
            </div>
            <label for="alias" class="col-sm-1 control-label">别名</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="alias" name="alias" maxlength="100"
                       placeholder="聚合中的namespace建议使用英文:uesrInfo">
            </div>
        </div>
        <!-- line 3 -->
        <div class="form-group">
            <label for="protocol" class="col-sm-offset-1 col-sm-1 control-label">所用协议</label>
            <div class="col-sm-2">
                <select class="form-control" id="protocol" name="protocol">
                    <option value="http" selected>http</option>
                    <option value="https">https</option>
                </select>
            </div>
            <label for="path" class="col-sm-1 control-label">路径</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="path" name="path" maxlength="80"
                       placeholder="GW/path1">
            </div>
            <label for="version" class="col-sm-1 control-label">版本号</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="version" name="version" maxlength="5"
                       placeholder="1.0或v1">
            </div>
        </div>

    </div>
    <div class="form-group">
        <!-- row 1 -->
        <H2 class="col-sm-offset-1 col-sm-3 text-center"><strong>请求报文</strong></H2>
        <H2 class="col-sm-3 text-center"><strong>响应报文</strong></H2>
        <H2 class="col-sm-3 text-center"><strong>完整服务定义</strong></H2>
    </div>
    <!-- row 2 -->
    <div class="form-group" style="text-align: right;">
        <!-- row 1 -->
        <div class="col-sm-offset-1 col-sm-3"><textarea rows="25" cols="60" style="resize: none;"
               id="requestMsg" name="requestMsg"   placeholder='{"requestMsg":[
        {
            "fieldName":"userName",
            "type":"simple",
            "required":"true"
        },
        {
            "fieldName":"address",
            "type":"complex",
            "required":"true",
            "fields":[
                {
                    "fieldName":"add",
                    "type":"simple",
                    "required":"true"
                }
            ]
        }
    ]}'></textarea></div>
        <div class="col-sm-3"><textarea rows="25" cols="60" style="resize: none;"
             id="responseMsg" name="responseMsg" placeholder='{"responseMsg":[
        {
            "fieldName":"address",
            "type":"complex",
            "required":"true",
            "fields":[
                {
                    "fieldName":"add",
                    "type":"simple",
                    "required":"true"
                }
            ]
        }
    ]}'></textarea></div>
        <div class="col-sm-3"><textarea rows="25" cols="60" style="resize: none;" disabled
             id="description" name="description"  placeholder='' ></textarea></div>
    </div>

</form>

<script type="text/javascript" src="../../static/jquery/jquery-3.0.0.js"></script>
<script type="text/javascript" src="../../static/jquery/jquery.validate1.12.0.js"></script>
<script type="text/javascript" src="../../static/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="../../static/sweetalert/sweet-alert.min.js"></script>
<script type="text/javascript" src="../../static/js/configuration.js"></script>
</body>
</html>