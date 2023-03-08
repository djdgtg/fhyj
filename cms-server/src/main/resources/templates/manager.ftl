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
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <!--Bootstrap Select [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">

    <!--JAVASCRIPT-->
    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <script src="static/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.min.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>
    <!-- WdatePicker日期时间 -->
    <script src="static/plugins/My97DatePicker/WdatePicker.js"></script>

    <!--Bootstrap Select [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-select/bootstrap-select.js"></script>

    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/comm.js"></script>
    <script src="static/js/sysmanage/managers.js"></script>
</head>
<body>
<div id="wrapper">
    <!--Page Title-->
    <div id="page-title">
        <h1 class="page-header text-overflow">管理员管理</h1>
    </div>
    <!--End page title&ndash;&gt;

    <!--Breadcrumb-->
    <ul class="breadcrumb">
        <li><a href="#">首页</a></li>
        <li>系统管理</li>
        <li class="active">管理员管理</li>
    </ul>
    <!--End breadcrumb-->

    <!--Page content-->
    <div id="page-content">
        <div class="panel">
            <div id="searchDiv" class="panel-body form-horizontal">
                <form id="searchForm">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名：</label>
                                <div class="col-sm-9">
                                    <input class="form-control" id="_username" name="userName"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">真实姓名：</label>
                                <div class="col-sm-9">
                                    <input class="form-control" id="_realname" name="realName"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号码：</label>
                                <div class="col-sm-9">
                                    <input class="form-control" id="_phone" name="phone"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色：</label>
                                <div class="col-sm-9">
                                    <select class="selectpicker" id="_roleid" name="roleid">
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-9">
                                <button class="btn btn-default" onclick="searchManager()"><i class="icon-search"></i>查询
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div id="toolbar" class="table-toolbar-left">
                <button class="btn btn-primary" onclick="managerModal(0)"><i class="icon-plus"></i> 新增</button>
                <button class="btn btn-mint" onclick="managerModal(1)"><i class="icon-edit"></i> 修改</button>
                <button class="btn btn-danger" onclick="delManager()"><i class="icon-remove"></i> 删除</button>
                <button class="btn" id="resetPwdBtn" onclick="resetPasswd()"><i class="icon-refresh"></i> 重置密码</button>
                <button class="btn" id="lockUnBtn" onclick="lockUn(0)"><i class="icon-lock"></i> 锁定</button>
                <button class="btn" id="lockUnBtn" onclick="lockUn(1)"><i class="icon-unlock"></i> 解锁</button>
                <input type="hidden" class="form-control" id="optype">
            </div>
            <div class="panel-body">
                <table id="managerTb" class="demo-add-niftycheck">
                </table>
            </div>
        </div>
    </div>
    <!--End page content-->
    <!--Default Bootstrap Modal-->
    <div class="modal fade" id="managerModal" role="dialog" tabindex="-1" aria-labelledby="managerModal"
         aria-hidden="true">
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
                            <label class="col-lg-3 control-label">管理员名称：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="username" placeholder="管理员名称由字母、数字、下划线组成"
                                       id="userName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">角色：</label>
                            <div class="col-lg-7">
                                <select class="selectpicker" name="roleIdsStr" id="roleSelect">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">真实姓名：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="realname" id="realName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">联系电话：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="phone" placeholder="请输入手机号或座机号"
                                       id="phone">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">邮箱：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="email" id="email">
                                <input type="hidden" name="userid" id="userId">
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
</div>
</body>
</html>

