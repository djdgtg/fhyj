<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>知识服务平台管理</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="../static/css/nifty.min.css" rel="stylesheet">
    <!--DataTables [ OPTIONAL ]-->
    <link href="../static/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <!--Font Awesome [ OPTIONAL ]-->
    <link rel="stylesheet" href="../static/plugins/font-awesome/css/font-awesome.min.css">
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="../static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <!--Bootstrap Select [ OPTIONAL ]-->
    <link href="../static/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
    <link href="../static/plugins/bootstrap-treeview/bootstrap-treeview.css" rel="stylesheet">

    <!--jQuery [ REQUIRED ]-->
    <script src="../static/js/jquery-2.2.4.min.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="../static/js/bootstrap.min.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="../static/plugins/bootbox/bootbox.min.js"></script>
    <script src="../static/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="../static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>
    <!--Bootstrap Select [ OPTIONAL ]-->
    <script src="../static/plugins/bootstrap-select/bootstrap-select.min.js"></script>
    <script src="../static/plugins/bootstrap-treeview/bootstrap-treeview.js"></script>
    <!--DataTables Sample [ PORTAL ]-->
    <script src="../static/js/comm.js"></script>
    <script src="../static/js/resourcemanage/moulds.js"></script>
</head>
<body>
<!--Page Title-->
<div id="page-title">
    <h1 class="page-header text-overflow">知识模型管理</h1>
</div>
<!--End page title-->

<!--Breadcrumb-->
<ul class="breadcrumb">
    <li><a href="#">首页</a></li>
    <li>知识组织</li>
    <li class="active">知识模型管理</li>
</ul>
<!--End breadcrumb-->

<!--Page content-->
<div id="page-content">
    <div class="panel">
        <div id="toolbar" class="table-toolbar-left">
            <button class="btn btn-primary" onclick="mouldModal(0)"><i class="icon-plus"></i> 新增</button>
            <button class="btn btn-mint" onclick="mouldModal(1)"><i class="icon-edit"></i> 修改</button>
            <button class="btn btn-danger" onclick="delMould()"><i class="icon-remove"></i> 删除</button>
            <button class="btn" onclick="setMetaData()"><i class="icon-cog"></i> 设定字段</button>
            <input type="hidden" id="optype">
        </div>
        <div class="panel-body">
            <table id="mouldTb" class="demo-add-niftycheck"></table>
        </div>
    </div>
</div>
<!--End page content-->
<!--Default Bootstrap Modal-->
<div class="modal fade" id="mouldModal" role="dialog" tabindex="-1" aria-labelledby="mouldModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title" id="mouldModalTitle"></h4>
            </div>
            <form id="addForm" method="post" class="form-horizontal">
                <!--Modal body-->
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">模型名称：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="mouldname" id="mouldName">
                            <input type="hidden" name="mouldid" id="mouldId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">模型描述：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="description" id="description">
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
<div class="modal fade" id="metadataListModal" role="dialog" tabindex="-1" aria-labelledby="metadataListModal"
     aria-hidden="true">
    <div class="modal-dialog" style="width:100%">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">字段设定</h4>
            </div>
            <!--Modal body-->
            <div class="modal-body">
                <div id="metadataToolbar" class="table-toolbar-left">
                    <button class="btn btn-primary" onclick="metadataMsgModal(0)" id="metaAdd"><i class="icon-plus"></i>
                        新增
                    </button>
                    <button class="btn btn-mint" onclick="metadataMsgModal(1)" id="metaUpdate"><i class="icon-edit"></i>
                        修改
                    </button>
                    <button class="btn btn-danger" onclick="delMetadata()" id="metaDel"><i class="icon-remove"></i> 删除
                    </button>
                    <input type="hidden" id="opMetadataType">
                </div>
                <div class="panel-body">
                    <table id="metadataTb" class="demo-add-niftycheck"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--End Default Bootstrap Modal-->


<div class="modal fade" id="metadataMsgModal" role="dialog" tabindex="-1" aria-labelledby="metadataMsgModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title" id="metaModalTitle"></h4>
            </div>
            <form id="metadataForm" method="post" class="form-horizontal">
                <!--Modal body-->
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字段名：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="columnname" id="columnName">
                            <input type="hidden" name="mouldid" id="metadatMouldId">
                            <input type="hidden" name="metadataid" id="metadataId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字段中文名：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="columncname" id="columnCname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字段类型：</label>
                        <div class="col-lg-7">
                            <select class="selectpicker" name="columntype" id="columnType"></select>
                        </div>
                    </div>
                    <div class="form-group" id="columnSourceDiv">
                        <label class="col-lg-3 control-label">数据来源：</label>
                        <div class="col-lg-7">
                            <select class="selectpicker" name="columnsource" id="columnSource"></select>
                        </div>
                    </div>
                    <div id="ruleDiv" class="form-group"></div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">字段长度：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="columnlength" id="columnLength">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">校验表达式：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="validexp" id="validExp">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">校验提示信息：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="validmsg" id="validMsg">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">默认值：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="defaultvalue" id="defaultValue">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">表单显示：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="formshow" value="0" id="formShowNo"><label
                                for="formShowNo">否</label>
                            <input type="radio" class="magic-radio" name="formshow" value="1" id="formShowYes"><label
                                for="formShowYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">列表显示：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="gridshow" value="0" id="gridShowNo"><label
                                for="gridShowNo">否</label>
                            <input type="radio" class="magic-radio" name="gridshow" value="1" id="gridShowYes"><label
                                for="gridShowYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">查询显示：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="searchshow" value="0" id="searchShowNo"><label
                                for="searchShowNo">否</label>
                            <input type="radio" class="magic-radio" name="searchshow" value="1"
                                   id="searchShowYes"><label for="searchShowYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">导入导出显示：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="importandexportshow" value="0"
                                   id="importandexportShowNo"><label for="importandexportShowNo">否</label>
                            <input type="radio" class="magic-radio" name="importandexportshow" value="1"
                                   id="importandexportShowYes"><label for="importandexportShowYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">是否必填项：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="required" value="0" id="requiredNo"><label
                                for="requiredNo">否</label>
                            <input type="radio" class="magic-radio" name="required" value="1" id="requiredYes"><label
                                for="requiredYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">是否可修改列：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="editable" value="0" id="editableNo"><label
                                for="editableNo">否</label>
                            <input type="radio" class="magic-radio" name="editable" value="1" id="editableYes"><label
                                for="editableYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">是否值唯一：</label>
                        <div class="col-lg-7">
                            <input type="radio" class="magic-radio" name="only" value="0" id="onlyNo"><label
                                for="onlyNo">否</label>
                            <input type="radio" class="magic-radio" name="only" value="1"
                                   id="onlyYes"><label for="onlyYes">是</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">排序：</label>
                        <div class="col-lg-7">
                            <input type="text" class="form-control" name="sorts" id="sorts">
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
<!--End page content-->
</body>
</html>

