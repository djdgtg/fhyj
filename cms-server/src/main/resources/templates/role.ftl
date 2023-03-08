<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>知识服务平台管理</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="static/css/nifty.min.css" rel="stylesheet">

    <!--DataTables [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="static/plugins/bootstrap-treeview/bootstrap-treeview.css" rel="stylesheet">
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.min.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <script src="static/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="static/plugins/bootstrap-treeview/bootstrap-treeview.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>

    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/comm.js"></script>
    <script src="static/js/sysmanage/roles.js"></script>
</head>
<body>
<!--Page Title-->
<div id="page-title">
    <h1 class="page-header text-overflow">角色管理</h1>
</div>
<!--End page title-->

<!--Breadcrumb-->
<ul class="breadcrumb">
    <li><a href="#">首页</a></li>
    <li>系统管理</li>
    <li class="active">角色管理</li>
</ul>
<!--End breadcrumb-->

<!--Page content-->
<div id="page-content">
    <div class="panel">
        <div id="toolbar" class="table-toolbar-left">
            <button class="btn btn-primary" onclick="roleModal(0)"><i class="icon-plus"></i> 新增</button>
            <button class="btn btn-mint" onclick="roleModal(1)"><i class="icon-edit"></i> 修改</button>
            <button class="btn btn-danger" onclick="delRole()"><i class="icon-remove"></i> 删除</button>
            <button onclick="menuModal()" class="btn"><i class="icon-cog"></i> 配置菜单</button>
            <input type="hidden" class="form-control" id="optype">
        </div>
        <div class="panel-body">
            <table class="demo-add-niftycheck" id="roleTb">
            </table>
        </div>
    </div>
</div>
<!--End page content-->
<!--Default Bootstrap Modal-->
<div class="modal fade" id="roleModal" role="dialog" tabindex="-1" aria-labelledby="roleModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title" id="modalTitle"></h4>
            </div>
            <form id="addForm" method="post" class="form-horizontal">
                <!--Modal body-->
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">角色名称：</label>
                        <div class="col-lg-7">
                            <input type="hidden" name="roleid" id="roleId">
                            <input type="text" class="form-control" name="rolename" placeholder="角色名称" id="roleName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">角色描述：</label>
                        <div class="col-lg-7">
                            <textarea rows="3" class="form-control" name="description" placeholder="角色描述"
                                      id="roleDes"></textarea>
                        </div>
                    </div>
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                    <button class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--End Default Bootstrap Modal-->
<!--Default Bootstrap Modal-->
<div class="modal fade" id="menuModal" role="dialog" tabindex="-1" aria-labelledby="menuModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">配置菜单</h4>
            </div>
            <!--Modal body-->
            <div class="modal-body">
                <div class="form-group">
                    <div id="menuSelect"></div>
                </div>
            </div>

            <!--Modal footer-->
            <div class="modal-footer">
                <input type="hidden" id="roleIdForMenu">
                <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                <button class="btn btn-primary" onclick="saveRoleMenu()">保存</button>
            </div>
        </div>
    </div>
</div>
<!--End Default Bootstrap Modal-->
</body>
</html>

