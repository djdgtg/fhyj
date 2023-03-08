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
    <link href="static/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">

    <!--DataTables [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-treeview/bootstrap-treeview.css" rel="stylesheet">
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <link href="static/plugins/treegrid/jquery.treegrid.css" rel="stylesheet"/>
    <link href="static/css/style.css" rel="stylesheet"/>
    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <script src="static/plugins/bootstrap-treeview/bootstrap-treeview.js"></script>
    <script src="static/plugins/treegrid/jquery.treegrid.min.js"></script>
    <script src="static/plugins/treegrid/jquery.treegrid.bootstrap3.js"></script>
    <script src="static/js/comm.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.min.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>
    <script src="static/plugins/bootstrap-select/bootstrap-select.min.js"></script>

    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/sysmanage/menus.js"></script>
    <script src="static/js/pageextend.js"></script>
</head>
<body>
<!-- WRAPPER -->
<div id="wrapper">
    <!--Page Title-->
    <div id="page-title">
        <h1 class="page-header text-overflow">菜单管理</h1>
    </div>
    <!--End page title-->

    <!--Breadcrumb-->
    <ul class="breadcrumb">
        <li><a href="#">首页</a></li>
        <li>系统管理</li>
        <li class="active">菜单管理</li>
    </ul>
    <!--End breadcrumb-->

    <!--Page content-->
    <div id="page-content">
        <div class="panel panel-body">
            <div class="bootstrap-table">
                <div class=" fixed-table-toolbar">
                    <div class="bars pull-left">
                        <div id="toolbar" class="table-toolbar-left">
                            <button class="btn btn-primary" onclick="menuModal(0)"><i class="icon-plus"></i> 新增</button>
                            <button id="update-btn" class="btn btn-mint" onclick="menuModal(1)"><i
                                    class="icon-edit"></i> 修改
                            </button>
                            <button id="delete-btn" class="btn btn-danger" onclick="delMenu()"><i
                                    class="icon-remove"></i> 删除
                            </button>
                            <input type="hidden" class="form-control" id="optype">
                        </div>
                    </div>
                </div>
            </div>
            <div class="fixed-table-container" style="padding-bottom: 0px;">
                <div class="fixed-table-body">
                    <table id="menuTb"></table>
                </div>
            </div>
        </div>
    </div>
    <!--End page content-->
    <!--Default Bootstrap Modal-->
    <div class="modal fade" id="menuModal" role="dialog" tabindex="-1" aria-labelledby="menuModal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i>
                    </button>
                    <h4 class="modal-title" id="modalTitle"></h4>
                </div>
                <form id="addForm" method="post" class="form-horizontal">
                    <!--Modal body-->
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">父级菜单：</label>
                            <div class="col-lg-7">
                                <input name="parentmenuid" id="parentMenuSelect" type="text" class="form-control" placeholder="顶级菜单" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">菜单名称：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="menuname" id="menuName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">路径：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="path" id="path">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">排序：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="sort" id="menuSort">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">外链：</label>
                            <div class="col-lg-7">
                                <input type="checkbox" name="isoutlink" id="isOutLinkCheckbox">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">菜单描述：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="description" id="description">
                                <input type="hidden" class="form-control" name="menuid" id="menuId">
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
</div>
</body>
</html>

