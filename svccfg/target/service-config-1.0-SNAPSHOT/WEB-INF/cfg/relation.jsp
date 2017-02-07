<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link rel="stylesheet" href="../../static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../../static/sweetalert/sweet-alert.css">
    <style type="text/css">
        .th-text-center th {
            text-align: center;
        }
    </style>

</head>
<body onload="formatJson();providerScollTop();">
<div class="content-section-a">

    <h1 style="text-align: center;">代理或聚合关系配置</h1>
    <div class="row">
    <div class="table-bordered col-sm-4">
        <div class="form-horizontal">
            <div class="form-group">
                <label for="component_" class="col-sm-offset-3 col-sm-3 control-label">服务组件</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="component_" name="component" value="${svcDescInfo.component}" readonly>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="code_" class="col-sm-offset-3 col-sm-3 control-label">服务码</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="code_" name="code" value="${svcDescInfo.code}" readonly>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="code_" class="col-sm-offset-3 col-sm-3 control-label">服务分类</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="category_" name="category" value="${svcDescInfo.category}" readonly>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="svcname_" class="col-sm-offset-3 col-sm-3 control-label">服务名称</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="svcname_" name="svcname" value="${svcDescInfo.svcname}" readonly>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="version_" class="col-sm-offset-3 col-sm-3 control-label">版本号</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="version_" name="version" value="${svcDescInfo.version}" readonly>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="proxiedService_" class="col-sm-offset-3 col-sm-3 control-label">
                    <c:if test="${svcDescInfo.category == '代理服务'}">
                        被代理服务码
                    </c:if>
                    <c:if test="${svcDescInfo.category == '聚合服务'}">
                        被聚合服务码
                    </c:if>
                </label>
                <div class="col-sm-6">
                    <c:if test="${svcDescInfo.category == '代理服务'}">
                        <input type="text" class="form-control" id="proxiedService_" name="proxiedService" value="<c:forEach var="svcRelation" items="${svcRelations}">${svcRelation.svcCodeBranch}</c:forEach>" readonly>
                    </c:if>
                    <c:if test="${svcDescInfo.category == '聚合服务'}">
                        <textarea rows="5" style="width:300px;resize: none;" disabled
                                  id="proxiedService_" name="proxiedService" ><c:forEach var="svcRelation" items="${svcRelations}" varStatus="status">${status.index+1}:&nbsp;&nbsp;&nbsp;${svcRelation.svcCodeBranch}&#13;&#10;</c:forEach></textarea>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="form-horizontal">
            <div class="form-group">
                <label for="description_" class="col-sm-offset-3 col-sm-3 control-label">服务定义</label>
                <div class="col-sm-6">
                <textarea rows="25" wrap="off" style="width:300px;resize: none;overflow:scroll;" disabled
                          id="description_" name="description" >${svcDescInfo.description}</textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="table-bordered col-sm-8">
        <div class="form-horizontal">
            <!-- line 1 -->
            <div class="form-group">
                <label for="component" class="col-sm-offset-1 col-sm-1 control-label">服务组件</label>
                <div class="col-sm-2">
                    <select class="form-control" id="component" name="component">
                        <option value="">全部</option>
                        <c:forEach var="provider" items="${providers}">
                            <c:if test="${provider.abb != 'GW'}">
                                <option value="${provider.abb}">${provider.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <label for="category" class="col-sm-1 control-label">服务分类</label>
                <div class="col-sm-2">
                    <select class="form-control" id="category" name="category">
                        <%--<option value="">全部</option>--%>
                        <option value="组件服务" selected>组件服务</option>
                        <%--<option value="代理服务">代理服务</option>
                        <option value="聚合服务">聚合服务</option>--%>
                    </select>
                </div>
                <div class="col-sm-offset-1 col-sm-1">
                    <button type="button" class="btn btn-large btn-primary btn-block" id="selectCfgRelationBtn">查询</button>
                </div>

                <div class="col-sm-1">
                    <button type="button" class="btn btn-large btn-success btn-block" id="creatCfgBtn">新增</button>
                </div>

            </div>
            <!-- line 2 -->
            <div class="form-group">
                <label for="code" class="col-sm-offset-1 col-sm-1 control-label">服务码</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" id="code" name="code"
                           placeholder="GW0000001">
                </div>
                <label for="svcname" class="col-sm-1 control-label">服务名称</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" id="svcname" name="svcname"
                           placeholder="产品查询服务">
                </div>
                <label for="version" class="col-sm-1 control-label">版本号</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" id="version" name="version"
                           placeholder="1.0或v1">
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
                    <input type="text" class="form-control" id="path" name="path"
                           placeholder="GW/path1">
                </div>
            </div>
        </div>
        <table id="cfgListTable" class="table" style="text-align: center;margin-top: 60px;" >
            <tr class="info th-text-center">
                <th>序号</th><th>服务码</th><th>别名</th><th>服务名称</th><th>服务组件</th><th>服务分类</th><th>版本号</th><th>所用协议</th><th>路径</th><th>操作</th>
            </tr>
        </table>
        <div style="text-align: center;">
            <ul class="pagination" id="pagination">
            </ul>
            <!-- 页码 默认1-->
            <input type="hidden" id="pageNo" value="1"/>
            <!-- 总页数 默认1-->
            <input type="hidden" id="totalPage" value="1" />
            <!-- 总记录数 -->
            <input type="hidden" id="PageCount" runat="server" value="1" />
            <!-- 每页记录数 -->
            <input type="hidden" id="PageSize" runat="server" />
            <input type="hidden" id="countindex" runat="server" />
            <!--设置最多显示的页码数 可以手动设置 默认为5-->
            <input type="hidden" id="visiblePages" runat="server" value="5" />
        </div>
    </div>
    </div>
</div>
<script type="text/javascript" src="../../static/jquery/jquery-3.0.0.js"></script>
<script type="text/javascript" src="../../static/jquery/jquery.validate1.12.0.js"></script>
<script type="text/javascript" src="../../static/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="../../static/page/jqPaginator.min.js"></script>
<script type="text/javascript" src="../../static/page/myPage.js"></script>
<script type="text/javascript" src="../../static/sweetalert/sweet-alert.min.js"></script>
<script type="text/javascript" src="../../static/js/configuration.js"></script>
<script type=text/javascript>
    getCfgRelationList(1);
</script>
</body>
</html>