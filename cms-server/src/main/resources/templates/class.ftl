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
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">
    <!--Ion Icons [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="static/plugins/treegrid/jquery.treegrid.css" rel="stylesheet"/>
    <link href="static/css/style.css" rel="stylesheet"/>
    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <script src="static/plugins/bootstrap-treeview/bootstrap-treeview.js"></script>
    <script src="static/plugins/treegrid/jquery.treegrid.min.js"></script>
    <script src="static/plugins/treegrid/jquery.treegrid.bootstrap3.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.min.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>
    <!--fileinput文件上传  -->
    <link type="text/css" href="static/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
    <script type="text/javascript" src="static/plugins/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript" src="static/plugins/bootstrap-fileinput/js/zh.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/comm.js"></script>
    <script src="static/js/sysmanage/classes.js"></script>
    <script src="static/js/pageextend.js"></script>
</head>
<body>
<!--Page Title-->
<div id="page-title">
    <h1 class="page-header text-overflow">分类管理</h1>
</div>
<!--End page title-->

<!--Breadcrumb-->
<ul class="breadcrumb">
    <li><a href="static/#">首页</a></li>
    <li>资源管理</li>
    <li class="active">分类管理</li>
</ul>
<!--End breadcrumb-->

<!--Page content-->
<div id="page-content">
    <div class="panel panel-body">
        <div class="bootstrap-table">
            <div class=" fixed-table-toolbar">
                <div class="bars pull-left">
                    <div id="toolbar" class="table-toolbar-left">
                        <button onclick="classModal(0)" class="btn btn-primary"><i class="icon-plus"></i> 新增</button>
                        <button onclick="classModal(1)" class="btn btn-mint"><i class="icon-edit"></i> 修改</button>
                        <button class="btn btn-danger" onclick="delClass()"><i class="icon-remove"></i> 删除</button>
                        <input type="hidden" class="form-control" id="optype">
                    </div>
                </div>
            </div>
        </div>
        <div class="fixed-table-container" style="padding-bottom: 0px;">
            <div class="fixed-table-body">
                <table id="classTb"></table>
            </div>
        </div>
    </div>
</div>
<!--End page content-->
<!--Default Bootstrap Modal-->
<div class="modal fade" id="classModal" role="dialog" tabindex="-1" data-backdrop="static" aria-labelledby="classModal"
     aria-hidden="true">
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
                        <label class="col-lg-3 control-label">父级分类：</label>
                        <div class="col-lg-7">
                            <input name="parentclassid" id="classSelect" type="text" class="form-control"
                                   placeholder="父分类编号"/>
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">分类名称：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="classname" placeholder="分类名称" id="className"/>
                            <input type="hidden" class="form-control" name="classid" id="classId"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">分类图片：</label>
                        <div class="col-lg-7">
                            <input multiple type="file" data-show-caption="true" class="form-control" name="file"
                                   placeholder="上传分类图片" id="file"/>
                            <input type="hidden" class="form-control" name="classimg" id="classImg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">分类简介：</label>
                        <div class="col-lg-7">
                            <textarea class="form-control" name="classdescription" placeholder="分类简介"
                                      id="classDescription"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">排序：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="sort" placeholder="排序" id="sort"/>
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
</body>
</html>

