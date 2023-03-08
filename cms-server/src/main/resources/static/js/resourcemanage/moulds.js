var oTable = null;
var metadataTable = null;
var faIcon = {
		valid: 'fa fa-check-circle fa-lg text-success',
		invalid: 'fa fa-times-circle fa-lg',
		validating: 'fa fa-refresh'
	};
$(window).on('load', function() {
	//加载模型数据
	loadMoulds();
	//模型form验证
	validForm();
	//加载字段类型选项
	var columnTypeParam = {'isdictype':0,'dictype':'columntype'};
	loadDicSelect("#columnType",columnTypeParam);
	//加载字段类型选项
	var columnSourceParam = {'isdictype':0,'dictype':'columnsource'};
	loadDicSelect("#columnSource",columnSourceParam);
	
	$('#columnType').on('change',columnTypeBind);
	$('#columnSource').on('change',columnTypeRuleBind);
	
	//字段form验证
	validMetadataForm();
	
	$('#metadataMsgModal').on('hidden.bs.modal', function() {
	    $("#metadataForm").data('bootstrapValidator').destroy();
	    $('#metadataForm').data('bootstrapValidator', null);
	    validMetadataForm();
	});
});

/**
 * 加载资源模型数据
 */
function loadMoulds(){
	oTable = $("#mouldTb").bootstrapTable({
    	method: "post",  //使用get请求到服务器获取数据  
        url: "../moulds/list", //获取数据的Servlet地址
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
        	field:'mouldid',
        	visible: false,
        }, { 
        	field: 'mouldname',
        	title: '模型名',
        	align: 'center',
        	valign: 'middle',
        },{
        	field:"description",
        	title: '描述',
	    }],
        toolbar:"#toolbar"
    });
}

/**
 * 模型modal
 * @param optype
 * @returns {Boolean}
 */
function mouldModal(optype){
	clearFormData("#addForm");
	$("#optype").val(optype);
	if(optype == 1){
		$("#mouldModalTitle")[0].innerText = "修改模型";
		var selMoulds = $("#mouldTb").bootstrapTable('getSelections');
		if(selMoulds.length != 1){
			bootbox.alert('请选择一个模型', function(){
				return;
	        });
			return;
		}else{
			$("#mouldName").val(selMoulds[0].mouldname);
			$("#description").val(selMoulds[0].description);
			$("#mouldId").val(selMoulds[0].mouldid);
		}
	}else{
		$("#mouldModalTitle")[0].innerText = "新增模型";
		$("#mouldType").selectpicker('val',1);
	}
	$("#mouldModal").modal('show');
}

//mould表单验证
function validForm(){
	$('#addForm').bootstrapValidator({
  	feedbackIcons: faIcon,
  	fields: {
  	'mouldname': {
  		validators: {
  			notEmpty: {
  				message: '模型不能为空.'
  			},
			stringLength: {
				min: 1,
				max: 200,
				message: '模型名长度必须在1~200位之间'
			}
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
    	  insertAndUpdate("../moulds/update");
      }else{
    	  insertAndUpdate("../moulds/add");
      }
  });
}

/**
 * 新增、更新模型信息
 * @param url
 */
function insertAndUpdate(url){
	$.ajax({
		type: "POST",
		url: url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data: $("#addForm").serialize(),
	    success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
		    		//关闭对话框
		    		$('#mouldModal').modal('hide');
		    		//清空表单数据
		    		clearFormData("#addForm");
		    		//重新加载表格数据
		    		$("#mouldTb").bootstrapTable("refresh");
	            });
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
		}
	});
}

/**
 * 删除模型信息
 */
function delMould(){
	var selMoulds = $("#mouldTb").bootstrapTable('getSelections');
	if(selMoulds.length == 0){
		bootbox.alert('至少选择一个模型', function(){
            return;
        });
		return;
	}else{
		var mouldIds = "";  
	  
		for (var i = 0; i < selMoulds.length; i++) {  
			mouldIds += selMoulds[i].mouldid + ",";
		} 
		mouldIds += 0;
		bootbox.confirm("对应的知识库也会被删除,确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "../moulds/delBatch",
	        		data: {"mouldIds": mouldIds},
	        	    success: function(data) {
	        	    	if (data.status==200) {
	        	    		bootbox.alert(data.msg, function(){
		        	    		//重新加载表格数据
		        	    		$("#mouldTb").bootstrapTable("refresh");
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

/**
 * 设定字段
 */
function setMetaData(){
	var selMoulds = $("#mouldTb").bootstrapTable('getSelections');
	if(selMoulds.length != 1){
		bootbox.alert('请选择一个模型', function(){
            return;
        });
		return;
	}else{
		var mouldId = selMoulds[0].mouldid;
		$("#metadatMouldId").val(mouldId);
		//判断资源模型是否已被实例化资源库
		checkIsCre(mouldId);
		//加载资源模型元数据
		loadMetaData(mouldId);
		//资源模型对话框开启
		$("#metadataListModal").modal('show');
	}
}

/**
 * 判断是否实例化
 */
function checkIsCre(mouldId){
	$.ajax({
		type: "POST",
		url: "../datalibrarys/list",
		data: {'mouldId':mouldId},
	    success: function(data) {
	    	if (data.status == 200) {
	    		if(data.data.length > 0){//已被实例化
	    			//设置字段维护按钮失效
	    			$("#metaAdd").attr("disabled",true);
	    			$("#metaUpdate").attr("disabled",true);
	    			$("#metaDel").attr("disabled",true);

	    			var tip = "此模型正在被以下资源库使用：";
	                $.each(data.data, function (index, datalibrary) {  
	                	tip += datalibrary.databasecname + "(" + datalibrary.databasename + "),"; 
	                });  
	                tip += "如需修改此模型，请先将以上资源库删除！";
	    			bootbox.alert(tip);
	    		}else{//未被实例化
	    			//设置字段维护按钮有效
	    			$("#metaAdd").attr("disabled",false);
	    			$("#metaUpdate").attr("disabled",false);
	    			$("#metaDel").attr("disabled",false);
	    		}
	    	} 
		}
	});
}

/**
 * 加载资源模型元数据
 * @param mouldId
 */
function loadMetaData(mouldId){
	$("#metadataTb").bootstrapTable("destroy");
	metadataTable = $("#metadataTb").bootstrapTable({
    	method: "get",  //使用get请求到服务器获取数据  
        url: "../metadatas/listCustom", //获取数据的Servlet地址  
        queryParams:{'mouldId':mouldId},
        striped: true,  //表格显示条纹  
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        sortable: false,                     //是否启用排序
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "client", //表示服务端请求  
        clickToSelect: true,    //是否启用点击选中行
        columns: [{
        	checkbox: true
        }, {
        	field:'metadataid',
        	visible: false,
        }, {
        	field: 'columnname',
        	title: '字段名',
        	align: 'center',
        	valign: 'middle',
        },{
        	field: 'columncname',
        	title: '字段中文名',
        }, {
        	field: 'columntypename',
        	title: '字段类型',
        },{
        	field: 'datatype',
        	title: '数据类型',
        },{
        	field: 'columnsourcename',
        	title: '来源',
        	formatter: function(value, index, row){
        		if(value == null){
        			return "";
        		}else{
        			return value;
        		}
        	}
        }, {
        	field: 'defaultvalue',
        	title: '默认值',
        	formatter: function(value, index, row){
        		if(value == null){
        			return "";
        		}else{
        			return value;
        		}
        	}
        },{
        	field: 'formshow',
        	title: '表单显示',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        }, {
        	field: 'gridshow',
        	title: '列表显示',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{
        	field: 'searchshow',
        	title: '搜索显示',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{
        	field: 'importandexportshow',
        	title: '导出导入显示',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{
        	field: 'required',
        	title: '是否必填项',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{
        	field: 'editable',
        	title: '是否可列修改',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{
        	field: 'only',
        	title: '是否值唯一',
        	formatter: function(value, index, row){
        		if(value == 1){
        			return "√";
        		}else{
        			return "";
        		}
        	}
        },{   
        	field:"sorts",
        	title: '排序',
        	formatter:function(value,index,row){
	    		if(value == null){
	    			return "";
	    		}else{
	    			return value;
	    		}
	    	}
	    }],
        toolbar:"#metadataToolbar"
    });	
}


/**
 * 元数据modal
 * @param optype
 * @returns {Boolean}
 */
function metadataMsgModal(optype){
	clearFormData("#metadataForm");
	$("#opMetadataType").val(optype);
	var columnTypeRule = null;

    $("#columnSource").parent().parent().hide();

	var selMoulds = $("#mouldTb").bootstrapTable('getSelections');
	var mouldId = selMoulds[0].mouldid;
	$("#metadatMouldId").val(mouldId);
	$("#formShowYes,#gridShowYes,#searchShowYes,#importandexportShowYes,#requiredYes,#editableYes,#onlyYes").val("1");
	$("#formShowNo,#gridShowNo,#searchShowNo,#importandexportShowNo,#requiredNo,#editableNo,#onlyNo").val("0");
	if(optype == 1){
		$("#metaModalTitle")[0].innerText = "修改字段";
		var selMetadatas = $("#metadataTb").bootstrapTable('getSelections');
		if(selMetadatas.length != 1){
			bootbox.alert('请选择一条信息', function(){
				return;
	        });
			return;
		}else{
	        $("input[name='formshow']").get(selMetadatas[0].formshow).checked = true;
	        $("input[name='gridshow']").get(selMetadatas[0].gridshow).checked = true;
	        $("input[name='searchshow']").get(selMetadatas[0].searchshow).checked = true;
	        $("input[name='importandexportshow']").get(selMetadatas[0].importandexportshow).checked = true;
	        $("input[name='required']").get(selMetadatas[0].required).checked = true;
	        $("input[name='editable']").get(selMetadatas[0].editable).checked = true;
	        $("input[name='only']").get(selMetadatas[0].only).checked = true;
	        
			$("#columnName").val(selMetadatas[0].columnname);
			$("#columnCname").val(selMetadatas[0].columncname);
			$("#columnType").selectpicker('val',selMetadatas[0].columntype);
			$("#columnSource").selectpicker('val',selMetadatas[0].columnsource);
			$("#validExp").val(selMetadatas[0].validexp);
			$("#validMsg").val(selMetadatas[0].validmsg);
			$("#columnLength").val(selMetadatas[0].columnlength);
			$("#defaultValue").val(selMetadatas[0].defaultvalue);
			$("#metadataId").val(selMetadatas[0].metadataid);
			$("#sorts").val(selMetadatas[0].sorts);
			columnTypeRule = selMetadatas[0].columntyperule;
		}
	}else{
		$("#metaModalTitle")[0].innerText = "新增字段";
		
		$("input[name='formshow']").get(1).checked = true;
        $("input[name='gridshow']").get(1).checked = true;
        $("input[name='searchshow']").get(1).checked = true;
        $("input[name='importandexportshow']").get(0).checked = true;
        $("input[name='required']").get(0).checked = true;
        $("input[name='editable']").get(0).checked = true;
        $("input[name='only']").get(0).checked = true;
	        
        $("#columnType").selectpicker("refresh");
	}
	//加载字段类型
	columnTypeBind();
	//获取字段类型规则
	columnTypeRuleBind(columnTypeRule);
	$("#metadataMsgModal").modal('show');
}

/**
 * 字段类型绑定
 */
function columnTypeBind(){
	var selValue = $('#columnType').val();
	if (selValue == 3 || selValue == 4) {//当字段类型为下拉单选或下拉多选时
		$("#defaultValue").parent().parent().hide();//默认值 行隐藏
		$("#columnSourceDiv").show(); //数据来源 行显示
		$("#columnSourceDiv").children().show(); //数据来源 行显示
		
		//默认加载此行
		$("#ruleDiv").empty();
	    $("#ruleDiv").show();
		$("#ruleDiv").append("<label class='col-lg-3 control-label'>字典类型：</label>\n");
        var comb = $('<div class="col-lg-7"><select id="columnTypeRule" name="columntyperule" class="selectpicker"/></select></div>').appendTo($("#ruleDiv"));
    	//加载字段类型选项
    	var columnTypeRuleParam = {'isdictype':1};
    	//获取字典类型并设置默认值
    	loadDicSelect("#columnTypeRule",columnTypeRuleParam,columnTypeRule);
	} else {
		$("#defaultValue").parent().parent().show();//默认值 行显示
		$("#columnSourceDiv").hide();//数据来源 行隐藏
		$("#ruleDiv").hide();//字段类型规则 隐藏
	}
	if (selValue == 1 || selValue == 2) {//当字段类型为单行文本或多行文本时
		$("#columnLength").parent().parent().children("label").html("字段长度：");//设置字段长度
		$("#columnLength").parent().parent().show();//字段长度、小数位数 行显示
	}else if(selValue == 17||selValue == 18){//当字段类型为小数时
		$("#columnLength").parent().parent().children("label").html("整数位数：");//设置为小数位数
		$("#columnLength").parent().parent().show();//字段长度、小数位数 行显示
	} else {
		$("#columnLength").parent().parent().hide();//字段长度、小数位数 行隐藏
	}
}

/**
 * 字段类型规则绑定
 */
function columnTypeRuleBind(columnTypeRule) {
    $("#ruleDiv").empty();
    $("#ruleDiv").show();
    var sourcetype = $("#columnSource").val();//数据来源
    if (sourcetype == 1) {//字典
        $("#ruleDiv").append("<label class='col-lg-3 control-label'>字典类型：</label>\n");
        var comb = $('<div class="col-lg-7"><select id="columnTypeRule" name="columntyperule" class="selectpicker"/></select></div>').appendTo($("#ruleDiv"));

    	//加载字段类型选项
    	var columnTypeRuleParam = {'isdictype':1};
    	//获取字典类型并设置默认值
    	loadDicSelect("#columnTypeRule",columnTypeRuleParam,columnTypeRule);
    } else {//资源库
        $("#ruleDiv").hide();
    }
}
//metadata表单验证
function validMetadataForm(){
	$('#metadataForm').bootstrapValidator({
  	feedbackIcons: faIcon,
  	fields: {
  	'columnname': {
  		validators: {
  			notEmpty: {
  				message: '字段名不能为空.'
  			},
			regexp: {
				regexp: /^[a-zA-Z][A-Za-z0-9_]*$/i,
				message: '字段名必须以字母开头，由字母、数字、下划线组成'
			}
  		}
  	},
  	'columncname': {
  		validators: {
  			notEmpty: {
  				message: '字段中文名不能为空.'
  			},
  		}
  	},
  	'columntype': {
  		validators: {
  			notEmpty: {
  				message: '字段类型不能为空.'
  			},
  		}
  	},
  	'sorts': {
  		validators: {
  			notEmpty: {
  				message: '排序不能为空.'
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
      var opMetadataType = $("#opMetadataType").val();
      if(opMetadataType == 1){
    	  insertAndUpdateMeta("../metadatas/update");
      }else{
    	  insertAndUpdateMeta("../metadatas/add");
      }
  });
}

/**
 * 新增、更新元数据信息
 * @param url
 */
function insertAndUpdateMeta(url){
	$.ajax({
		type: "POST",
		url: url,
		data: $("#metadataForm").serialize(),
	    success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
		    		//关闭对话框
		    		$('#metadataMsgModal').modal('hide');
		    		//清空表单数据
		    		clearFormData("#metadataForm");
		    		//重新加载表格数据
		    		$("#metadataTb").bootstrapTable("refresh");
	            });
	    	} else {
	    		bootbox.alert(111);
	    	}
		}
	});
}

/**
 * 删除字段信息
 */
function delMetadata(){
	var selMetadatas = $("#metadataTb").bootstrapTable('getSelections');
	if(selMetadatas.length == 0){
		bootbox.alert('至少选择一个字段', function(){
            return;
        });
		return;
	}else{
		var metadataIds = "";  
	  
		for (var i = 0; i < selMetadatas.length; i++) {  
			metadataIds += selMetadatas[i].metadataid + ",";
		} 
		metadataIds += 0;
		bootbox.confirm("确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "../metadatas/delBatch",
	        		data: "metadataIds=" + metadataIds,
	        	    success: function(data) {
	        	    	if (data.status == 200) {
	        	    		bootbox.alert(data.msg, function(){
		        	    		//重新加载表格数据
		        	    		$("#metadataTb").bootstrapTable("refresh");
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