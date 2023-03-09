var menuDataList = null;
var faIcon = {
    valid: 'fa fa-check-circle fa-lg text-success',
    invalid: 'fa fa-times-circle fa-lg',
    validating: 'fa fa-refresh'
}
$(window).on('load', function () {
    //加载菜单数据
    loadMenus();
    validForm();

});

function loadMenus() {
    $("#menuTb").supertreegrid({
        url: 'menus/listCustom',
        method: 'post',
        columns: [
            {
                field: 'menuname',
                title: '菜单名称'
            },
//	    	{ field: 'pagemark', title: '菜单图标' },
            {
                field: 'path',
                title: '菜单路径',
                formatter: function (val, row) {
                    return val == null ? '' : val;
                }
            },
            {
                field: 'isoutlink',
                title: '外链',
                formatter: function (val, row) {
                    return val == 1 ? '√' : '×';
                }
            },
            {
                field: 'description',
                title: '菜单描述',
                formatter: function (val, row) {
                    return val == null ? '' : val;
                }
            },
            {
                field: 'sort',
                title: '菜单排序'
            },
        ],
        idfield: 'menuid',
        parentfield: 'parentmenuid',
    });
}

function menuModal(optype) {
    clearFormData("#addForm");
    $("#optype").val(optype);

    $('#parentMenuSelect').comboxtree({
        url: 'options/getMenuTreeOptionsByRole',
        data: { 'optionsSearchBean.userid': -1, 'optionsSearchBean.roleId': -1 },
        method:'post',
        isroot: true
    });
    if (optype == 1) {
        $("#modalTitle")[0].innerText = "修改菜单";
        var selMenus = $('#menuTb').data('selectedRow');
        if (selMenus == null) {
            bootbox.alert('请选择一个菜单', function () {
                return;
            });
            return;
        } else {
            $("#parentMenuSelect").val(selMenus.parentmenuid);
            $("#menuId").val(selMenus.menuid);
            $("#menuName").val(selMenus.menuname);
            $("#path").val(selMenus.path);
            $("#description").val(selMenus.description);
            $("#menuSort").val(selMenus.sort);
            var isOutLink = selMenus.isoutlink;
            if (isOutLink == 0) {
                $("#isOutLinkCheckbox").attr("checked", false);
            } else {
                $("#isOutLinkCheckbox").attr("checked", true);
            }
            if(selMenus.parentmenuid != 0){
                parentMenuName = selMenus.parentmenuname;
            }
            $(".formcomboxtree-handdiv").find('span').eq(0).text(parentMenuName);
        }
    } else {
        $("#modalTitle")[0].innerText = "新增菜单";
    }
    $("#menuModal").modal('show');
}

//表单验证
function validForm() {
    $('#addForm').bootstrapValidator({
        feedbackIcons: faIcon,
        fields: {
            'menuname': {
                validators: {
                    notEmpty: {
                        message: '菜单名称不能为空.'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '菜单名称长度必须在1~20位之间'
                    }
                }
            },
            'baseMenus.sort': {
                validators: {
                    notEmpty: {
                        message: '排序不能为空.'
                    },
                }
            },
            'description': {
                validators: {
                    stringLength: {
                        min: 0,
                        max: 200,
                        message: '分类名长度必须小于200个字符'
                    }
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
        var opType = $("#optype").val();
        ;
        if (opType == 1) {
            insertAndUpdateMenu("menus/update");
        } else {
            insertAndUpdateMenu("menus/add");
        }
    });
}

//添加更新菜单
function insertAndUpdateMenu(url) {
    if ($("#isOutLinkCheckbox").prop('checked')) {
        $("#isOutLink").val(1);
    } else {
        $("#isOutLink").val(0);
    }
    $("#parentMenuId").val($("#parentMenuSelect").val());
    $.ajax({
        type: "POST",
        url: url,
        data: $("#addForm").serialize(),
        success: function (data) {
            if (data.status == 200) {
                bootbox.alert(data.msg, function () {
                    //关闭对话框
                    $('#menuModal').modal('hide');
                    //清空表单数据
                    clearFormData("#addForm");
                    //重新加载父级菜单下拉框
                    //$('#parentMenuSelect').comboxtree('refresh');
                    //loadParMenu();
                    //重新加载表格数据
                    $("#menuTb").supertreegrid('refresh');
                });
            } else {
                bootbox.alert(data.msg);
            }

        }
    });
}


//删除菜单
function delMenu() {
    var selMenus = $('#menuTb').data('selectedRow');
    if (selMenus == null) {
        bootbox.alert('请选择一个菜单', function () {
            return;
        });
        return;
    } else {
        var menuId = selMenus.menuid;
        bootbox.confirm("确定删除?", function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "menus/del",
                    data: {'menuId': menuId},
                    success: function (data) {
                        if (data.status == 200) {
                            bootbox.alert(data.msg, function () {
                                //重新加载表格数据
                                $("#menuTb").supertreegrid('refresh');
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