var oTable = null;
var faIcon = {
		valid: 'fa fa-check-circle fa-lg text-success',
		invalid: 'fa fa-times-circle fa-lg',
		validating: 'fa fa-refresh'
	}
var dboptiondata = null; //数据库选项

var nexusList = null; //数据库选项

var nexusDataMsg = null;

$(window).on('load', function() {
	loadModalSelect("#mouldSel",'',null,null,true);
	loadDbLibrays();

	//资源模型切换
    $('#dbMouldSelect').on('change',function(){
    	var mouldId = $('#dbMouldSelect').val();
    	loadLibraryNexus(mouldId,null);
    	loadDbCol(mouldId,null);
    });
    
    //关系字段选项值切换
    $('#dataStepTypeSel').on('change',function(){
    	stepSetDbcol();
    });
    
    //关系资源库切换
    $('#sdbNameSel').on('change',function(){
    	var databasename = $('#sdbNameSel').val();
    	loadDbMeta(databasename,null);
    });
    //关系字段选项名切换
    $('#sdbNameFieldSel').on('change',function(){
    	loadSdbSql();
    });

    //关系字段选项值切换
    $('#sdbValueSel').on('change',function(){
    	loadSdbSql();
    });
    validDbForm();
    validNexusForm();
    $('#dbLibModal').on('hidden.bs.modal', function() {
	    $("#dbLibModal").data('bootstrapValidator').destroy();
	    $('#dbLibModal').data('bootstrapValidator', null);
	    validDbForm();
	});
    
    $('#nexusModal').on('hidden.bs.modal', function() {
    	$("#nexusModal").data('bootstrapValidator').destroy();
    	$('#nexusModal').data('bootstrapValidator', null);
    	validNexusForm();
    });
});

/**
 * 加载资源库数据
 */
function loadDbLibrays(){
	oTable = $("#datalibraryTb").bootstrapTable({
    	method: "post",  //使用post请求到服务器获取数据  
    	contentType : "application/x-www-form-urlencoded",
        url: "../datalibrarys/listCustom", //获取数据的Servlet地址
        queryParams: function () {
            return $("#searchForm").serializeArray()
		},
        striped: true,  //表格显示条纹  
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        sortable: false,                     //是否启用排序
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "client", //表示客户端请求  
		locale:'zh-CN',
        clickToSelect: true,    //是否启用点击选中行
        columns: [{
        	checkbox: true
        }, {
        	field:'databaseid',
        	visible: false,
        }, {
        	field: 'databasename',
        	title: '库名',
        	align: 'center',
        	valign: 'middle',
        },{
        	field: 'databasecname',
        	title: '库中文名',
        },{
        	field: 'mouldid',
        	title: '模型id',
        },{
        	field: 'mouldname',
        	title: '模型名称',
        },{
        	field:"description",
        	title: '描述',
	    },{
            field: 'statusname',
            title: '状态',
        }],
        toolbar:"#toolbar"
    });
}

function searchDb(){
	oTable.bootstrapTable("refresh");
}

/**
 * 资源库modal
 * @param optype
 * @returns {Boolean}
 */
function datalibraryModal(optype){
	clearFormData("#addForm");
	$("#nexusDataListDiv").children().remove();
	$("#optype").val(optype);
	var choseId,databaseId = null;
	var db = null;
	if(optype == 1){
		$("#modalTitle")[0].innerText = "修改资源库";
		var selDatalibrarys = $("#datalibraryTb").bootstrapTable('getSelections');
		if(selDatalibrarys.length != 1){
			bootbox.alert('请选择一个资源库', function(){
				return;
	        });
			return;
		}else{
            $("#databaseName").attr("readonly",true);
            $("#dbMouldSelect").attr("disabled",true);
            
			db = selDatalibrarys[0];
			choseId = selDatalibrarys[0].mouldid;
			databaseId = selDatalibrarys[0].databaseid;
			$("#databaseName").val(selDatalibrarys[0].databasename);
			$("#databaseCName").val(selDatalibrarys[0].databasecname);
			$("#databaseId").val(databaseId);
			$("#dbMouldSelect").selectpicker('val',choseId);
			$("#description").val(selDatalibrarys[0].description);
		}
	}else{
		$("#modalTitle")[0].innerText = "新增资源库";
		$("#dbMouldSelect").attr("disabled",false);
		$("#databaseName").attr("readonly",false);
	}
	//获取资源模型下拉框
	loadModalSelect("#dbMouldSelect",choseId,databaseId,db);
	$("#dbLibModal").modal('show');
}

/**
 * 加载模型列表
 * @param mouldId
 */
function loadModalSelect(selId,mouldId,databaseId,database,isAll){
	$(selId).children().remove();
	$.ajax({
		type: "POST",
		url: "../moulds/list",
	    success: function(data) {
	    	if (data.status == 200) {
	    		if(data.data != null){
	    			if(isAll){
	                     $(selId).append("<option value=''>全部</option>");
	    			}
	    			$.each(data.data, function (index, mould) {  
	    				$(selId).append("<option value='" + mould.mouldid + "'>" + mould.mouldname + "</option>");  
	    			});  
	    			if(mouldId == undefined || mouldId == null){
	                	mouldId = data.data[0].mouldid;
	                }
	     			$(selId).selectpicker('val',mouldId);
	                $(selId).selectpicker("refresh");
	                $(selId).selectpicker("show");
	         	}
	    		if(!isAll){
		    		loadLibraryNexus(mouldId,databaseId);
	    		}
	    	}
		}
	});
}


/**
 * 加载库关联
 * @param choseId
 */
function loadLibraryNexus(mouldId,dbId){
	$("#nexusList").children().remove();
	$.ajax({
		type: "POST",
		url: "../librarynexus/listCustom",
		data: {'mouldId':mouldId,'databaseId':dbId},
	    success: function(data) {
	    	if (data.status == 200) {
	    		if(data.data.length > 0){
	    			$("#nexusListDiv").parent().show();
	                var litxt = '';
	                var nexusData;
	                nexusList = data.data;
	                $.each(data.data, function (i, nexus) {
	                	nexusData = nexus.mdbfield + '->' + nexus.sdbsql;
	                	litxt += '<input id="nexus_' + i + '" type="text" class="form-control" value="' + nexusData + '" />';
	                	litxt += '<span class="btn btn-default" onclick="loadNexusModal(' + i + ',' + mouldId + ')">设定关系</span>';
		    			$("#nexusList").append(litxt);
	                });
	                $("#nexusListDiv").show();
	    		}else{
	                $("#nexusListDiv").hide();
	    		}
	    	} else {
                $("#nexusListDiv").hide();
	    	}
		}
	});
}

/**
 * 加载库关系modal
 * @param indexValue
 * @param mouldId
 */
function loadNexusModal(indexValue,mouldId){
	clearFormData("#nexusForm");
	var nexusData = nexusList[indexValue];
	$("#mdbField").val(nexusData.mdbfield);
	$("#sdbSql").val(nexusData.sdbsql);
	//获取模型数据库
	loadDbSelect(mouldId,nexusData);
	$("#indexValue").val(indexValue);
	$("#nexusModal").modal('show');
}

/**
 * 获取关系资源库
 * @param mouldId
 * @param nexusData
 */
function loadDbSelect(mouldId,nexusData){
	$("#sdbNameSel").children().remove();
	var choseId = '';
	/*获取数据库列表选项*/
    $.ajax({
        url: '../datalibrarys/list',
        method: 'GET',
        async: false,
        success: function (data) {
            dboptiondata = data;
    		if(data.data != null){
                $.each(data.data, function (index, db) {  
                    $("#sdbNameSel").append("<option value='" + db.databasename + "'>" + db.databasecname + "</option>");  
                });  
                if(nexusData.sdbname == undefined || nexusData.sdbname == null){
               		choseId = data.data[0].databasename;
                }else{
                	choseId = nexusData.sdbname;
                }
    			$("#sdbNameSel").selectpicker('val',choseId);
                $("#sdbNameSel").selectpicker("refresh");
                $("#sdbNameSel").selectpicker("show");
                
                loadDbMeta(choseId,nexusData);
                return true;
        	}
        }
    });
}

/**
 * 获取资源库元数据
 * @param databaseName
 * @param nexusData
 */
function loadDbMeta(databaseName,nexusData){
	$("#sdbNameFieldSel").children().remove();
	$("#sdbValueSel").children().remove();
	var sdbnamefield,sdbvaluefield = "";
	/*获取数据库列表选项*/
    $.ajax({
        url: '../metadatas/listByDbName',
        method: 'GET',
        data: {'databaseName':databaseName},
        async: false,
        success: function (data) {
            dboptiondata = data;
    		if(data.data != null){
    			 $("#sdbNameFieldSel").append("<option value='seqid'>主键Id</option>");
                 $("#sdbValueSel").append("<option value='seqid'>主键Id</option>");
                $.each(data.data, function (index, meta) {
                    $("#sdbNameFieldSel").append("<option value='" + meta.columnname + "'>" + meta.columncname + "</option>");
                    $("#sdbValueSel").append("<option value='" + meta.columnname + "'>" + meta.columncname + "</option>");
                });
                if(nexusData == null || nexusData.sdbnamefield == undefined || nexusData.sdbnamefield == null){
                	sdbnamefield = data.data[0].columnname;
                }else{
                	sdbnamefield = nexusData.sdbnamefield;
                }
                if(nexusData == null || nexusData.sdbvaluefield == undefined || nexusData.sdbvaluefield == null){
                	sdbvaluefield = data.data[0].columnname;
                }else{
                	sdbvaluefield = nexusData.sdbvaluefield;
                }
    			$("#sdbNameFieldSel").selectpicker('val',sdbnamefield);
                $("#sdbNameFieldSel").selectpicker("refresh");
                $("#sdbNameFieldSel").selectpicker("show");


    			$("#sdbValueSel").selectpicker('val',sdbvaluefield);
                $("#sdbValueSel").selectpicker("refresh");
                $("#sdbValueSel").selectpicker("show");

                loadSdbSql();
        	}
        }
    });
}

/**
 * 加载高级sql语句
 */
function loadSdbSql(){
    var sdbname = $("#sdbNameSel").val();
    var sdbnamefield = $("#sdbNameFieldSel").val();
    var sdbvaluefield = $("#sdbValueSel").val();
    var sdbsql = "select " + sdbvaluefield + "," + sdbnamefield + " from " + sdbname;
    $("#sdbSql").val(sdbsql);
}


//mould表单验证
function validDbForm(){
	$('#dbLibModal').bootstrapValidator({
  	feedbackIcons: faIcon,
  	fields: {
  	'databasename': {
  		validators: {
  			notEmpty: {
  				message: '库名不能为空.'
  			},
			regexp: {
				regexp: /^[A-Za-z0-9_]+/,
				message: '库名必须由字母、数字、下划线组成',
			}
  		}
  	},
  	'databasecname': {
  		validators: {
  			notEmpty: {
  				message: '库中文名不能为空.'
  			},
//			regexp: {
//				regexp: /[^\u4E00-\u9FA5]/g,
//				message: '库中文名必须为中文',
//			}
  		}
  	},
  	}
  }).on('success.field.bv', function(e, data) {
  	var $parent = data.element.parents('.form-group');
  	$parent.removeClass('has-success');
  }).on('success.form.bv', function(e) {
      // Prevent form submission
      e.preventDefault();

      // Get the form instance
      var $form = $(e.target);

      // Get the BootstrapValidator instance
      var bv = $form.data('bootstrapValidator');

      // Use Ajax to submit form data
      var opType = $("#optype").val();
      if(opType == 1){
    	  insertAndUpdate("../datalibrarys/update");
      }else{
    	  insertAndUpdate("../datalibrarys/add");
      }
  });
}

/**
 * 新增、更新资源库信息
 * @param url
 */
function insertAndUpdate(url){
	if($("#isWareCheckbox").prop('checked')){
		$("#isWare").val(1);
	}else{
		$("#isWare").val(0);
	}
	$("#addForm").action = url;
	var dataMsg = "{" + nexusDataMsg + "}";
	$.ajax({
		type: "POST",
		url: url,
		data: $("#addForm").serialize(),
	    success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
		    		//关闭对话框
		    		$('#dbLibModal').modal('hide');
		    		//清空表单数据
		    		clearFormData("#addForm");
		    		//重新加载表格数据
		    		$("#datalibraryTb").bootstrapTable("refresh");
	            });
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
		}
	});
}
/**
 * 删除资源库信息
 */
function delDatalibrary(){
	var selDataLibrary = $("#datalibraryTb").bootstrapTable('getSelections');
	if(selDataLibrary.length == 0){
		bootbox.alert('至少选择一个资源库', function(){
            return;
        });
		return;
	}else{
		var dbIds = "";  
		var isfrist = false;
		for (var i = 0; i < selDataLibrary.length; i++) {
			if (isfrist) {
				dbIds += ",";
			}
			dbIds += selDataLibrary[i].databaseid;
			isfrist = true;
		}
		bootbox.confirm("确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "../datalibrarys/delBatch",
	        		data: "databaseIds=" + dbIds,
	        	    success: function(data) {
	        	    	if (data.status == 200) {
	        	    		bootbox.alert(data.msg);
	        	    		$("#datalibraryTb").bootstrapTable("refresh");
	        	    	} else {
	        	    		bootbox.alert(data.msg);
	        	    	}
	        		}
	        	});
	        }
	    });
	}
}


//nexus表单验证
function validNexusForm(){
	$('#nexusModal').bootstrapValidator({
  	feedbackIcons: faIcon,
  	fields: {
  	'mdbfield': {
  		validators: {
  			notEmpty: {
  				message: '原字段不能为空.'
  			},
  		}
  	},
  	}
  }).on('success.field.bv', function(e, data) {
  	var $parent = data.element.parents('.form-group');
  	$parent.removeClass('has-success');
  }).on('success.form.bv', function(e) {
      // Prevent form submission
      e.preventDefault();

      // Get the form instance
      var $form = $(e.target);

      // Get the BootstrapValidator instance
      var bv = $form.data('bootstrapValidator');

      // Use Ajax to submit form data
      setNexus();
      $("#nexusModal").modal('hide');
  });
}

/**
 * 设定库关系
 */
function setNexus(){
	var index = $("#indexValue").val();
	var mdbfield = '<input type="hidden" name="libraryNexusList[' + index + '].mdbfield" value="' + $("#mdbField").val() + '">';
	var sdbname = '<input type="hidden" name="libraryNexusList[' + index + '].sdbname" value="' + $("#sdbNameSel").val() + '">';
	var sdbnamefield = '<input type="hidden" name="libraryNexusList[' + index + '].sdbnamefield" value="' + $("#sdbNameFieldSel").val() + '">';
	var sdbvaluefield = '<input type="hidden" name="libraryNexusList[' + index + '].sdbvaluefield" value="' + $("#sdbValueSel").val() + '">';
	$("#nexusDataListDiv").append(mdbfield);
  	$("#nexusDataListDiv").append(sdbname);
	$("#nexusDataListDiv").append(sdbnamefield);
	$("#nexusDataListDiv").append(sdbvaluefield);	
	$("#nexus_" + index).val($("#mdbField").val() + "->" + $("#sdbSql").val());
}

/**
 * 启用、禁用
 */
function lockUnlock(type){
	var selDataLibrary = $("#datalibraryTb").bootstrapTable('getSelections');
	if(selDataLibrary.length < 1){
		bootbox.alert('请至少选择一个资源库', function(){
            return;
        });
		return;
	}else{
        var dbId = "";
        var isfrist = false;
        for (var i = 0; i < selDataLibrary.length; i++) {
            if (isfrist) {
                dbId += ",";
            }
            dbId += selDataLibrary[i].databaseid;
            isfrist = true;
        }
        bootbox.confirm("确认操作?",function(result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "../datalibrarys/updateStatus",
                    data: {'databaseid': dbId,'status':type},
                    success: function(data) {
                        if (data.status == 200) {
                            bootbox.alert(data.msg);
                            //重新加载表格数据
                            $("#datalibraryTb").bootstrapTable("refresh");
                        } else {
                            bootbox.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}