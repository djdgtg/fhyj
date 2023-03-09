var oTable = null;
var dictype = null;
var faIcon = {
    valid: 'fa fa-check-circle fa-lg text-success',
    invalid: 'fa fa-times-circle fa-lg',
    validating: 'fa fa-refresh'
}

$(window).on('load', function () {
    //加载字典类型数据
    loadDicType();

    //字典类型切换
    $('#dicTypeSelect').on('change', function () {
        oTable.bootstrapTable("refresh");
    });

    validDicTypeForm();
    validDicForm();

});


//获取字典类型
function loadDicType() {
    $("#dicTypeSelect").children().remove();
    $.ajax({
        url: "dic/list",
        dataType: "json",
        data: {"isDicType": 1},
        success: function (data) {
            if (data.data != null) {
                $.each(data.data, function (index, dic) {
                    $("#dicTypeSelect").append("<option value='" + dic.type + "'>" + dic.typeName + "</option>");
                });
                $("#dicTypeSelect").selectpicker("refresh");
                $("#dicTypeSelect").selectpicker("show");
                dictype = data.data[0].type;
                $("#dicTypeSelect").selectpicker("val", dictype);
                $("#name").val(dictype);
                loadDic();
            }
        },

        error: function (XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert('加载失败，请稍后重试');
        }
    });
}


//加载字典数据
function loadDic() {
    oTable = $("#dicTb").bootstrapTable({
        method: "post",  //使用get请求到服务器获取数据
        contentType: "application/x-www-form-urlencoded",
        url: "dic/list", //获取数据的Servlet地址
        queryParams: function () {
            return {'isDicType': 0, 'type': $('#dicTypeSelect').val()};
        },
        striped: true,  //表格显示条纹  
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数  
        pageNumber: 1, //当前第几页
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        sortable: false,                     //是否启用排序
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "client", //表示服务端请求  
        locale: 'zh-CN',
        clickToSelect: true,    //是否启用点击选中行
        columns: [{
            checkbox: true
        }, {
            field: 'did',
            visible: false,
        }, {
            field: 'name',
            title: '字典名称',
            align: 'center',
            valign: 'middle',
        }, {
            field: 'isDicType',
            visible: false,
        }, {
            field: 'value',
            title: '字典值',
        }, {
            field: 'type',
            title: '字典类型',
        }],
        toolbar: "#dicToolbar",
    });
}

/**
 * 字典类型modal
 */
function dicTypeModal() {
    clearFormData("#addDicTypeForm");
    $("#isDicType").val(1);
    $("#addDicTypeModal").modal('show');
}

/**
 * 字典modal
 */
function dicModal(opType) {
    clearFormData("#addDicForm");
    $("#isDicType").val(0);
    $("#typeName").val($('#dicTypeSelect').val());
    $("#opType").val(opType);

    if (opType === 1) {
        $("#modalTitle")[0].innerText = "修改字典";
        var selDic = $("#dicTb").bootstrapTable('getSelections');
        if (selDic.length === 1) {
            var dicId = selDic[0].id;
            var dicValue = selDic[0].value;
            var dicName = selDic[0].name;

            $("#name").val(dicName);
            $("#value").val(dicValue);
            $("#id").val(dicId);

        } else {
            bootbox.alert('请选择1条字典信息', function () {
                return;
            });
            return;
        }
    } else {
        $("#modalTitle")[0].innerText = "新增字典";
    }
    $("#addDicModal").modal('show');
}


function validDicTypeForm() {
    //字典类型form验证
    $('#addDicTypeForm').bootstrapValidator({
        excluded: [':disabled'],
        feedbackIcons: faIcon,
        fields: {
            'baseDic.type': {
                validators: {
                    notEmpty: {
                        message: '字典类型不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '字段类型长度必须在1~20位之间'
                    },
                }
            },
            'baseDic.typeName': {
                validators: {
                    notEmpty: {
                        message: '字典类型中文名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '字段类型中文名长度必须在1~50位之间'
                    },
                }
            },
        }
    }).on('success.field.bv', function (e, data) {
        var $parent = data.element.parents('.form-group');
        $parent.removeClass('has-success');
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();

        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        // Use Ajax to submit form data
        addDic("#addDicTypeForm", "#addDicTypeModal");
    });
}

//字典信息验证
function validDicForm() {
    $('#addDicForm').bootstrapValidator({
        feedbackIcons: faIcon,
        fields: {
            "baseDic.name": {
                validators: {
                    notEmpty: {
                        message: '字典名不能为空.'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '字典名长度必须在1~20位之间'
                    },
                }
            },
            "baseDic.value": {
                validators: {
                    notEmpty: {
                        message: '字典值不能为空.'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '字典值中文名长度必须在1~20位之间'
                    },
                }
            }
        }
    }).on('success.field.bv', function (e, data) {
        var $parent = data.element.parents('.form-group');
        $parent.removeClass('has-success');
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();

        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        // Use Ajax to submit form data

        var opType = $("#opType").val();
        if (opType === 1) {
            updateDicMsg();
        } else {
            addDic("#addDicForm", "#addDicModal");
        }
    });
}


//添加字典及字典类型
function addDic(formId, modalId) {
    $.ajax({
        type: "POST",
        url: "dic/add",
        data: $(formId).serialize(),
        success: function (data) {
            if (data.status === 200) {
                bootbox.alert(data.msg, function () {//清空表单数据
                    clearFormData(formId);

                    $(modalId).modal('hide');

                    if (modalId === "#addDicTypeModal") {
                        loadDicType();
                        $('#dicTypeSelect').selectpicker('val', $("#type").val());
                    } else {
                        $("#dicTb").bootstrapTable("refresh");
                    }
                });

            } else {
                bootbox.alert(data.msg);
            }
        }
    });
}


function updateDicMsg() {
    $.ajax({
        type: "POST",
        url: "dic/update",
        data: $("#addDicForm").serialize(),
        success: function (data) {
            if (data.status === 200) {
                bootbox.alert(data.msg, function () {
                    //关闭对话框
                    $('#addDicModal').modal('hide');
                    $("#opType").val(0);
                    //清空表单数据
                    clearFormData("#addDicForm");
                    //重新加载表格数据
                    $("#dicTb").bootstrapTable("refresh");
                });
            } else {
                bootbox.alert(data.msg);
            }
        }
    });
}


//删除字典类型
function delDicTypeBtn() {
    //var dicType = $("#dicType").val();
    var dicType = $("#dicTypeSelect").val();
    $.ajax({
        type: "POST",
        url: "dic/del",
        data: "type=" + dicType + "&isDicType=1",
        success: function (data) {
            if (data.status === 200) {
                bootbox.alert(data.msg, function () {
                    $("#typeSelect").selectpicker("render");
                    $("#typeSelect").selectpicker("refresh");
                    //重新加载表格数据
                    $("#dicTb").bootstrapTable("refresh");
                });
            } else {
                bootbox.alert(data.msg);
            }

        }
    });
}


//删除
function delDic() {
    var selDic = $("#dicTb").bootstrapTable('getSelections');
    if (selDic.length == 0) {
        bootbox.alert('至少选择一个字典！', function () {
            return;
        });
        return;
    } else {
        var dicIds = "";

        for (var i = 0; i < selDic.length; i++) {
            dicIds += selDic[i].id + ",";
        }
        dicIds += 0;
        bootbox.confirm("确定删除?", function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "dic/delBatch",
                    data: "dicIds=" + dicIds,
                    success: function (data) {
                        if (data.status === 200) {
                            bootbox.alert(data.msg, function () {
                                //重新加载表格数据
                                $("#dicTb").bootstrapTable("refresh");
                            });
                        } else {
                            bootbox.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}
