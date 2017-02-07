<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../static/sweetalert/sweet-alert.css">
    <style type="text/css">
        .success-btn {
            color: #fff;
            background-color: #2ECC71;
            border-color: #2ECC71;
        }
    </style>
</head>
<body>
<div style="text-align: center;margin-top: 260px;">
    <c:if test="${result.flg == 'success'}">
        <div><img src="../static/img/sign-check-icon.png" width="128" height="128"/></div>
    </c:if>
    <c:if test="${result.flg == 'failed'}">
        <img src="../static/img/sign-error-icon.png" width="128" height="128"/>
    </c:if>
    <div>${result.msg}</div>
    <div style="margin-top: 30px;">
        <button type="button" class="btn btn-primary btn-lg">

            <a href="${result.url}" style="color: #fff;">确定</a></button>
    </div>
</div>
<script type="text/javascript" src="../static/jquery/jquery-3.0.0.js"></script>
<script type="text/javascript" src="../static/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="../static/sweetalert/sweet-alert.min.js"></script>
</body>
</html>