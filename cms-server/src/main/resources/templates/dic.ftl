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

    <!--Bootstrap Select [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
    <!--DataTables [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.min.js"></script>
    <!--Bootstrap Select [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-select/bootstrap-select.min.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <script src="static/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>

    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/comm.js"></script>
    <script src="static/js/sysmanage/dic.js"></script>
</head>
<body>
<!--Page Title-->
<div id="page-title">
    <h1 class="page-header text-overflow">字典管理</h1>
</div>
<!--End page title-->

<!--Breadcrumb-->
<ul class="breadcrumb">
    <li><a href="static/#">首页</a></li>
    <li>系统管理</li>
    <li class="active">字典管理</li>
</ul>
<!--End breadcrumb-->

<!--Page content-->
<div id="page-content">
    <div class="panel">
        <div id="dicToolbar" class="table-toolbar-left">
            <div style="float:left;">
                <span style="font-size:16px;">字典类型：</span>
                <select class="selectpicker" id="dicTypeSelect"></select>
            </div>
            <div style="float:left;margin-left:10px;">
                <button onclick="dicTypeModal()" class="btn btn-primary"><i class="icon-plus"></i> 新增字典类型</button>
                <button class="btn btn-danger" onclick="delDicTypeBtn()"><i class="icon-remove"></i> 删除字典类型</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button onclick="dicModal(0)" class="btn btn-primary"><i class="icon-plus"></i> 新增字典</button>
                <button class="btn btn-mint" onclick="dicModal(1)"><i class="icon-edit"></i> 修改字典</button>
                <button class="btn btn-danger" onclick="delDic()"><i class="icon-remove"></i> 删除字典</button>
                <input type="hidden" id="opType">
            </div>
        </div>
        <div class="panel-body">
            <table class="demo-add-niftycheck" id="dicTb">
            </table>
        </div>
    </div>
</div>
<!--End page content-->

<!--addDicTypeModal-->
<div class="modal fade" id="addDicTypeModal" role="dialog" tabindex="-1" aria-labelledby="addDicModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">新增字典类型</h4>
            </div>
            <!--Modal body-->
            <form id="addDicTypeForm" method="post" class="form-horizontal">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字典类型：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="type">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字典类型中文名：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="typeName">
                            <input type="hidden" value="1" name="isDicType" id="isDicType"/>
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
<!--addDicTypeModal-->


<!--addDicModal-->
<div class="modal fade" id="addDicModal" role="dialog" tabindex="-1" aria-labelledby="addDicModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title" id="modalTitle"></h4>
            </div>

            <form id="addDicForm" method="post" class="form-horizontal">
                <!--Modal body-->
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字典名：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="name" placeholder="字典名" id="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字典值：</label>
                        <div class="col-lg-7">
                            <input type="hidden" value="0" name="isDicType" id="isDicType"/>
                            <input type="hidden" name="type" id="name"/>
                            <input type="hidden" class="form-control" name="id" id="id">
                            <input type="text" class="form-control" name="value" placeholder="字典值" id="value">
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
<!--addDicModal-->
</body>
</html>

