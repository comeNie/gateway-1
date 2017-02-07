$(function () {
    $('#preview').on('click', doPreview);
    $('#selectCfgBtn').on('click', selectCfg);
    $('#selectCfgRelationBtn').on('click', selectCfgRelation);
    $('#creatCfgBtn').on('click', function () {
        window.location.href = "/service-config/gw/cfg/create";
    });
    $('#category').on('change', checkCategory);
    $.validator.addMethod(
        'jsonStyle', function (value, element) {
            var length = value.length;
            if (parseInt(length) > 2) {
                try {
                    JSON.parse('{"22":22,' + value.trim().substring(1, value.lastIndexOf("}")) + "}");
                    return true;
                } catch (e) {
                    return false;
                }
            }
            return true;
        }, '非法的json格式'
    );
    $('#cfgCreateForm').validate({
        errorElement: 'span',
        rules: {
            component: {
                required: true
            },
            category: {
                required: true
            },
            code: {
                required: true,
                remote: "checkCodeExist"
            },
            svcname: {
                required: true
            },
            alias: {
                required: true,
                remote: "checkAliasExist"
            },
            version: {
                required: true
            },
            protocol: {
                required: true
            },
            path: {
                required: true
            },
            description: {
                required: true
            },
            requestMsg: {
                jsonStyle: true
            },
            responseMsg: {
                jsonStyle: true
            }
        },
        messages: {
            component: {
                required: "请选择服务组件"
            },
            category: {
                required: "请选择服务分类"
            },
            code: {
                required: "请输入服务码",
                remote: "此服务码已存在"
            },
            svcname: {
                required: "请输入服务名称"
            },
            alias: {
                required: "请输入服务别名",
                remote: "此别名已经存在"
            },
            version: {
                required: "请输入版本号"
            },
            protocol: {
                required: "请选择协议"
            },
            path: {
                required: "请输入路径"
            },
            description: {
                required: "请输入输入报文"
            },
            requestMsg: {
                jsonStyle: "JSON格式不正确"
            },
            responseMsg: {
                jsonStyle: "JSON格式不正确"
            }
        },
        success: function (error, element) {
            $(element).next('span').removeClass('hide');
            error.remove();
        },
        errorPlacement: function (error, element) {
            element.next('span').addClass('hide');
            element.parent().append(error);
        },
        submitHandler: function (form) {
            $('#description').removeAttr('disabled');
            $('#description').attr('readonly', 'readonly');
            form.submit();
        }
    });

    $('#cfgEditForm').validate({
        errorElement: 'span',
        rules: {
            component: {
                required: true
            },
            category: {
                required: true
            },
            code: {
                required: true,
                remote: "checkCodeExist?id=" + $('#id').val()
            },
            svcname: {
                required: true
            },
            alias: {
                required: true,
                remote: "checkAliasExist?id=" + $('#id').val()
            },
            version: {
                required: true
            },
            protocol: {
                required: true
            },
            path: {
                required: true
            },
            description: {
                required: true
            },
            requestMsg: {
                jsonStyle: true
            },
            responseMsg: {
                jsonStyle: true
            }
        },
        messages: {
            component: {
                required: "请选择服务组件"
            },
            category: {
                required: "请选择服务分类"
            },
            code: {
                required: "请输入服务码",
                remote: "此服务码已存在"
            },
            svcname: {
                required: "请输入服务名称"
            },
            alias: {
                required: "请输入服务别名",
                remote: "此别名已经存在"
            },
            version: {
                required: "请输入版本号"
            },
            protocol: {
                required: "请选择协议"
            },
            path: {
                required: "请输入路径"
            },
            description: {
                required: "请输入输入报文"
            },
            requestMsg: {
                jsonStyle: "JSON格式不正确"
            },
            responseMsg: {
                jsonStyle: "JSON格式不正确"
            }
        },
        success: function (error, element) {
            $(element).next('span').removeClass('hide');
            error.remove();
        },
        errorPlacement: function (error, element) {
            element.next('span').addClass('hide');
            element.parent().append(error);
        },
        submitHandler: function (form) {
            $('#description').removeAttr('disabled');
            $('#description').attr('readonly', 'readonly');
            form.submit();
        }
    });
});

function doPreview() {
    var reqCheck = false;
    var respCheck = false;
    var reqRespAlert = "以下格式未满足要求：";
    var component = $('#component').val();
    var category = $('#category').val();
    var code = $('#code').val();
    var svcname = $('#svcname').val();
    var version = $('#version').val();
    var requestMsg = $('#requestMsg').val();
    var responseMsg = $('#responseMsg').val();
    var path = $('#path').val();

    var json = '{"code":"' + code +
        '","svcname":"' + svcname +
        '","version":"' + version +
        '","component":"' + component +
        '","path":"' + path +
        '","category":"' + category;
    if('聚合服务' == category){
        json += '","serviceList":[]';
    }else if('代理服务' == category){
        json += '","proxiedService":""';
    }else{
        json += '"';
    }
    if(requestMsg.trim().length > 0){
        var reqJson = "," + requestMsg.trim().substring(1, requestMsg.lastIndexOf("}"));
        if (isJsonFormat(json + reqJson + "}")) {
            json += reqJson;
            reqCheck = true;
        } else {
            reqRespAlert += "请求报文";
        }
    }else{
        reqCheck = true;
    }
    if(responseMsg.trim().length > 0){
        var respJson = "," + responseMsg.trim().substring(1, responseMsg.lastIndexOf("}"));
        if (isJsonFormat(json + respJson + "}")) {
            json += respJson;
            respCheck = true;
        } else {
            reqRespAlert += "  响应报文";
        }
    }else{
        respCheck = true;
    }

    json += '}';

    $('#description').val(JSON.stringify(JSON.parse(json), null, "\t"));
    if (!reqCheck || !respCheck) {
        swal("请检查", reqRespAlert, "error");
    }
}

function isJsonFormat(str) {
    try {
        $.parseJSON(str);
    } catch (e) {
        return false;
    }
    return true;
}

function selectCfg() {
    $('#pageNo').val(1);
    getCfgList(1);
}

function selectCfgRelation() {
    $('#pageNo').val(1);
    getCfgRelationList(1);
}

function getCfgList(pageNo) {
    var totalPage = $('#totalPage').val();
    var component = $('#component').val();
    var category = $('#category').val();
    var code = $('#code').val();
    var svcname = $('#svcname').val();
    var version = $('#version').val();
    var protocol = $('#protocol').val();
    var path = $('#path').val();
    var alias = $('#alias').val();

    $("#cfgListTable tr:not(:first)").empty();
    $.ajax({
        url: "/service-config/gw/cfg/getCfgList",
        type: 'POST',
        dataType: 'json',
        data: {
            component: component,
            category: category,
            code: code,
            svcname: svcname,
            version: version,
            protocol: protocol,
            path: path,
            pageNo: pageNo,
            alias: alias
        },
        success: function (data) {
            $('#pageNo').val(data['pageNo']);
            $('#totalPage').val(data['totalPage']);
            $('#PageCount').val(data['totalRecord']);
            $('#PageSize').val(data['pageSize']);
            $('#countindex').val(data['pageNo'] * data['pageSize']);
            loadpage();
            data = data['results'];
            if (data && data.length > 0) {
                var colors = ["success", "warning"];
                var trs = "";
                for (var i in data) {
                    trs += '<tr class=' + colors[i % 2] + '><td>' +
                        (eval(i) + 1) + '</td><td>' +
                        data[i].code + '</td><td>' +
                        data[i].alias + '</td><td>' +
                        data[i].svcname + '</td><td>' +
                        data[i].component + '</td><td>' +
                        data[i].category + '</td><td>' +
                        data[i].version + '</td><td>' +
                        data[i].protocol + '</td><td>' +
                        data[i].path + '</td><td style="text-align: left;width: 300px;padding-left: 50px;"><div>' +
                        '<button class="btn btn-warning cfgEditBtn" type="button" value="' + data[i].id + '">修改</button>' +
                        '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger cfgDelBtn" type="button" value="' + data[i].id + "&" + data[i].code + '" >删除</button>';
                    if ('GW' == data[i].component && "代理服务" == data[i].category) {
                        trs += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-info cfgRelation" type="button" value="' + data[i].id + '" >代理配置</button>'
                    }else if ('GW' == data[i].component && "聚合服务" == data[i].category) {
                        trs += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-primary cfgRelation" type="button" value="' + data[i].id + '" >聚合配置</button>'
                    }
                    trs += '</div></td></tr>';
                }
                $('#cfgListTable').append(trs);
                $('.cfgEditBtn').on('click', editCfg);
                $('.cfgDelBtn').on('click', delCfg);
                $('.cfgRelation').on('click', cfgRelation);
            }
        }
    });
}
function editCfg() {
    var id = $(this).val();
    window.location.href = "/service-config/gw/cfg/edit?id=" + id;
}
function cfgRelation() {
    var id = $(this).val();
    window.location.href = "/service-config/gw/cfg/relation?id=" + id;
}
function delCfg() {
    var value = $(this).val().split('&');
    swal({
            title: "请确认",
            text: "确定要删除" + value[1] + "吗？",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    url: "/service-config/gw/cfg/del?id=" + value[0],
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        swal("已删除", value[1] + "已经被删除", "success");
                        getCfgList();
                    },
                    error: function () {
                        swal("取消", value[1] + "删除失败", "error");
                    }
                });
            } else {
                swal("取消", "已经取消删除" + value[1], "error");
            }
        });
}

function getCfgRelationList(pageNo) {
    var totalPage = $('#totalPage').val();
    var component = $('#component').val();
    var category = $('#category').val();
    var code = $('#code').val();
    var svcname = $('#svcname').val();
    var version = $('#version').val();
    var protocol = $('#protocol').val();
    var path = $('#path').val();
    var alias = $('#alias').val();

    $("#cfgListTable tr:not(:first)").empty();
    $.ajax({
        url: "/service-config/gw/cfg/getCfgRelationList",
        type: 'POST',
        dataType: 'json',
        data: {
            component: component,
            category: category,
            code: code,
            svcname: svcname,
            version: version,
            protocol: protocol,
            path: path,
            pageNo: pageNo,
            alias: alias
        },
        success: function (data) {
            $('#pageNo').val(data['pageNo']);
            $('#totalPage').val(data['totalPage']);
            $('#PageCount').val(data['totalRecord']);
            $('#PageSize').val(data['pageSize']);
            $('#countindex').val(data['pageNo'] * data['pageSize']);
            loadpage();
            data = data['results'];
            var category =  $('#category_').val();
            if (data && data.length > 0) {
                var colors = ["success", "warning"];
                var trs = "";
                for (var i in data) {
                    trs += '<tr class=' + colors[i % 2] + '><td>' +
                        (eval(i) + 1) + '</td><td>' +
                        data[i].code + '</td><td>' +
                        data[i].alias + '</td><td>' +
                        data[i].svcname + '</td><td>' +
                        data[i].component + '</td><td>' +
                        data[i].category + '</td><td>' +
                        data[i].version + '</td><td>' +
                        data[i].protocol + '</td><td>' +
                        data[i].path + '</td><td>';
                    if("代理服务" == category){
                        trs += '<button class="btn btn-info setRelation" type="button" value="' + data[i].code + "&代理" +'">设为代理</button>';
                    }
                    if("聚合服务" == category){
                        trs += '<button class="btn btn-info setRelation" type="button" value="' + data[i].code + "&聚合" +'">添加聚合</button>';
                    }
                    trs += '</td></tr>';
                }
                $('#cfgListTable').append(trs);
                $('.setRelation').on('click', setRelation);
                initSetRelation();
            }
        }
    });
}
function initSetRelation(){
    $.ajax({
        url: "/service-config/gw/cfg/getRelations",
        type: 'POST',
        dataType: 'json',
        data:{
            svcCodeMaster:$('#code_').val()
        },
        success: function (data) {
            if (data) {
                $('.setRelation').each(function () {
                    for (var i in data) {
                        if(data[i].svcCodeBranch == $(this).val().split('&')[0]){
                            setDelBtn($(this));
                        }
                    }
                });
            }
        },
        error: function () {
        }
    });
}
function formatJson(){
    $('#description_').val(JSON.stringify(JSON.parse($('#description_').val()), null, "\t"));
}
function setRelation(){
    var btn = $(this);
    var value = $(this).val().split('&');
    var message = '';
    var type = "";
    var proxiedService_ = $('#proxiedService_').val();
    if('代理' == value[1]){
        type = "1";
        if(proxiedService_.trim().length > 0){
            message = "确认将被代理服务码替换为：" + value[0] + "吗?";
        }else{
            message = "确认添加被代理服务码为：" + value[0] + "吗?";
        }
    }
    var svcCodeMaster = $('#code_').val();
    var svcCodeBranch = value[0];
    var url = "";
    if('代理' == value[1] && proxiedService_.trim().length > 0){
        url = "/service-config/gw/cfg/updateRelation";
    }else{
        url = "/service-config/gw/cfg/setRelation";
    }
    if('聚合' == value[1]){
        message = "确认添加被聚合服务码：" + value[0] + "吗?";
        type = "2";
        var degrade = false;
        swal({
            title: "是否可降级?",
            text: "降级说明：。。。。。",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "true",
            cancelButtonText: "false",
            closeOnConfirm: false,
            closeOnCancel: false},
            function(isConfirm){
                if (isConfirm) {
                    degrade = true;
                }
                swal({
                        title: "请确认",
                        text: message,
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认",
                        cancelButtonText: "取消",
                        closeOnConfirm: false,
                        closeOnCancel: true
                    },
                    function (isConfirm) {
                        if(isConfirm){
                            $.ajax({
                                url: url,
                                type: 'POST',
                                dataType: 'json',
                                data:{
                                    svcCodeMaster:svcCodeMaster,
                                    svcCodeBranch:svcCodeBranch,
                                    type:type,
                                    degrade:degrade
                                },
                                success: function (data) {
                                    var proxiedService_ = $('#proxiedService_').val();
                                    if(proxiedService_.trim().length > 0){
                                        proxiedService_ += "**  " + svcCodeBranch + "\n";
                                        $('#proxiedService_').val('');
                                        $('#proxiedService_').val(proxiedService_);
                                    }else{
                                        $('#proxiedService_').val("**  " + svcCodeBranch + "\n");
                                    }
                                    providerScollTop();
                                    updateDescription(svcCodeMaster);
                                    setDelBtn(btn);
                                    swal("已更新", "服务定义已更新", "success");
                                },
                                error: function () {
                                    swal("取消", "添加失败", "error");
                                }
                            });
                        }

                    });
            });
    }else{
        swal({
                title: "请确认",
                text: message,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                closeOnConfirm: false,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data:{
                            svcCodeMaster:svcCodeMaster,
                            svcCodeBranch:svcCodeBranch,
                            type:type
                        },
                        success: function (data) {
                            if('代理' == value[1]){
                                $('#proxiedService_').val(svcCodeBranch);
                                $('.setRelation').removeAttr('disabled');
                            }
                            if('聚合' == value[1]){
                                var proxiedService_ = $('#proxiedService_').val();
                                if(proxiedService_.trim().length > 0){
                                    proxiedService_ += "**  " + svcCodeBranch + "\n";
                                    $('#proxiedService_').val('');
                                    $('#proxiedService_').val(proxiedService_);
                                }else{
                                    $('#proxiedService_').val("**  " + svcCodeBranch + "\n");
                                }
                                providerScollTop();
                            }
                            updateDescription(svcCodeMaster);
                            setDelBtn(btn);
                            swal("已更新", "服务定义已更新", "success");
                        },
                        error: function () {
                            swal("取消", "添加失败", "error");
                        }
                    });
                }
            });
    }
}

function setAggBtn(btn){
    btn.attr('class', 'btn btn-info setRelation');
    btn.text('添加聚合');
    $('.setRelation').on('click', setRelation);
}

function setDelBtn(btn){
    btn.attr('class', 'btn btn-danger delRelation');
    btn.text('解除聚合');
    $('.delRelation').on('click', delRelation);
}

function delRelation() {
    var btn = $(this);
    var value = $(this).val().split('&');
    var svcCodeMaster = $('#code_').val();
    var svcCodeBranch = value[0];
    swal({
            title: "请确认",
            text: "将" + svcCodeBranch + "从聚合中解除？",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    url: "/service-config/gw/cfg/delRelation",
                    type: 'POST',
                    dataType: 'json',
                    data:{
                        svcCodeMaster:svcCodeMaster,
                        svcCodeBranch:svcCodeBranch
                    },
                    success: function (data) {
                        //恢复添加按钮
                        //更新被聚合服务码列表
                        //更新服务定义
                        refreshAggregationList(svcCodeMaster, updateDescription(svcCodeMaster, setAggBtn(btn)));
                        swal("已解除", "服务定义已更新", "success");
                    },
                    error: function () {
                        swal("取消", "解除失败", "error");
                    }
                });
            }
        });
}

function providerScollTop(){
    var scrollTop = $("#proxiedService_")[0].scrollHeight;
    $("#proxiedService_").scrollTop(scrollTop);
}

function updateDescription(code, callback){
    $.ajax({
        url: "/service-config/gw/cfg/getDescriptionByCode",
        type: 'POST',
        dataType: 'json',
        data:{
            code:code
        },
        success: function (data) {
            $('#description_').val(JSON.stringify(data, null, "\t"));
            callback();
        },
        error: function () {

        }
    }, 'string');
}
function editJsonFormat(){
    $('#requestMsg').val(JSON.stringify(JSON.parse($('#requestMsg').val()), null, "\t"));
    $('#responseMsg').val(JSON.stringify(JSON.parse($('#responseMsg').val()), null, "\t"));
    $('#description').val(JSON.stringify(JSON.parse($('#description').val()), null, "\t"));
}

function checkCategory(){
    var cate = $(this).val();
    if('聚合服务' == cate){
        $('#requestMsg').attr('disabled', 'disabled');
        $('#responseMsg').attr('disabled', 'disabled');
    }else{
        $('#requestMsg').removeAttr('disabled');
        $('#responseMsg').removeAttr('disabled');
    }
}
//刷新被聚合列表
function refreshAggregationList(svcCodeMaster, callback){
    $.ajax({
        url: "/service-config/gw/cfg/getRelations",
        type: 'POST',
        dataType: 'json',
        data:{
            svcCodeMaster:svcCodeMaster
        },
        success: function (data) {
            $('#proxiedService_').val("");
            var listVal = "";
            for (var i in data) {
                listVal += (eval(i)+1) + "  " + data[i].svcCodeBranch +"\n";
            }
            $('#proxiedService_').val(listVal);
            callback();
        },
        error: function () {
        }
    });
}