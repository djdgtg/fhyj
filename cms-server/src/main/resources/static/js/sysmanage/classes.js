//新增
var faIcon = {
	valid: 'fa fa-check-circle fa-lg text-success',
	invalid: 'fa fa-times-circle fa-lg',
	validating: 'fa fa-refresh'
}

var fileServerUrl="";

$(window).on('load', function() {
	getfileServerUrl();
	//加载分类表数据
	loadClassTreeGrid();
	validForm();
	$('#classModal').on('hidden.bs.modal', function() {
	    $("#addForm").data('bootstrapValidator').destroy();
	    $('#addForm').data('bootstrapValidator', null);
	    validForm();
	});
});

function getfileServerUrl(){
	$.ajax({
		url : "personal/getfileServerUrl",
		success : function(data) {
			fileServerUrl=data;
		},
	});
}

function loadClassTreeGrid(){
    $("#classTb").supertreegrid({
        url: 'classes/listCustom',
        method: 'post',
        columns: [
        { 
        	field: 'classname', 
        	title: '分类名称' , 
    		formatter: function (val, row) {
            	return  val == null ? '' : val;
    		}
        },
		{ 
        	field: 'parentclassname', 
        	title: '父分类名称' , 
    		formatter: function (val, row) {
            	return  val == null ? '' : val;
    		}
        },
		{ 
        	field: 'classimg', 
        	title: '分类图片' , 
    		formatter: function (val, row) {
    			if(val==''||val==null){
    				return '';
    			}else{
    				return "<img src=\""+fileServerUrl+val+"\" width=\"100px\" heigth=\"40px\"></img>";
    			}
    		}
        },
		{ 
        	field: 'classdescription', 
        	title: '分类简介' , 
    		formatter: function (val, row) {
            	return  val == null ? '' : val;
    		}
        },
        { 
        	field: 'sort', 
        	title: '排序' , 
        	formatter: function (val, row) {
        		return  val == null ? '' : val;
        	}
        }
        ],
        idfield: 'classid',
        parentfield: 'parentclassid',
        bordered: true,
    });
}


/**
 * 分类modal
 */
function classModal(optype){
	clearFormData("#addForm");
	$(".formcomboxtree").empty();
	$("#optype").val(optype);
    $('#classSelect').comboxtree({
        url: "options/getClassTreeOptionsByParent",
        data: { 'parentId': -1, 'withNone': true }
    });
	
	if(optype == 1){
		$("#modalTitle")[0].innerText = "修改分类";
		var selClass = $('#classTb').data('selectedRow');
		if(selClass == null){
			bootbox.alert('请选择一条分类信息', function(){
				return;
	        });
			return;
		}else{
			var classId = selClass.classid;
			var parentClassId = selClass.parentclassid;
			var className = selClass.classname;
			var classImg = selClass.classimg;
			var classDescription = selClass.classDescription;
			var sort = selClass.sort;
			var parentClsName;
	        if(parentClassId != 0){
	        	parentClsName = selClass.parentclassname;
	        }
            $(".formcomboxtree-handdiv").find('span').eq(0).text(parentClsName);
            
			$("#classImg").val(classImg);
			$("#classDescription").val(classDescription);
			$("#className").val(className);
			$("#classId").val(classId);
			$("#classSelect").val(parentClassId);
			$("#sort").val(sort);
		}
	}else{
		$("#modalTitle")[0].innerText = "新增分类";
	}
	$("#classModal").modal('show');
	initFileInput('file');
}

//表单验证
function validForm(){
	$('#addForm').bootstrapValidator({
    	feedbackIcons: faIcon,
    	fields: {
    	'classname': {
    		message: '分类名不能为空',
    		validators: {
    			notEmpty: {
    				message: '分类名不能为空.'
    			},
				stringLength: {
					min: 1,
					max: 100,
					message: '分类名长度必须在1~100位之间'
				}
    		}
    	}
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
        var opType = $("#optype").val();;
        if(opType == 1){
        	insertAndUpdateClass("classes/update");
        }else{
        	insertAndUpdateClass("classes/add");
        }
    });
}


//添加更新分类
function insertAndUpdateClass(url){
    $("#parentClassId").val($("#classSelect").val());
	$.ajax({
		type: "POST",
		url: url,
		data: $("#addForm").serialize(),
	    success: function(data) {
	    	if (data.status == 200) {
	    		clearFormData("#addForm");
				$('#classModal').modal('hide');
				bootbox.alert(data.msg);
				$('#classSelect').comboxtree('refresh');
				$("#classTb").supertreegrid('refresh');
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
		}
	});
}

/**
 * 删除分类
 * @returns
 */
function delClass() {
	var selClass = $('#classTb').data('selectedRow');
	if(selClass == null){
		bootbox.alert('请选择一个分类', function(){
            return;
        });
		return;
	}else{
		bootbox.confirm("确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "classes/del",
	        		data: {"classid":selClass.classid},
	        	    success: function(data) {
	        	    	if (data.status == 200) {
	        	    		alert(data.msg);
	        	    		$('#classSelect').comboxtree('refresh');
        			        $("#classTb").supertreegrid('refresh');
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
 * 文件上传
 */
function initFileInput(columnname) {   
	 //上传初始化
	 var control = $('#'+columnname);
     control.fileinput({
    	 language: 'zh', //设置语言
         uploadUrl: "personal/uploadFile", //上传的地址
         allowedFileExtensions:['jpg', 'gif', 'png'],//接收的文件后缀，如['jpg', 'gif', 'png'],不填将不限制上传文件后缀类型
         //uploadExtraData:{"id": 1, "fileName":'123.mp3'},//请求的额外参数
         uploadAsync: true, //默认异步上传
         showUpload: true, //是否显示上传按钮
         showRemove : true, //显示移除按钮
         showPreview : true, //是否显示预览
         showCaption: false,//是否显示标题
         browseClass: "btn btn-primary", //按钮样式     
         dropZoneEnabled: false,//是否显示拖拽区域
         //minImageWidth: 50, //图片的最小宽度
         //minImageHeight: 50,//图片的最小高度
         //maxImageWidth: 1000,//图片的最大宽度
         //maxImageHeight: 1000,//图片的最大高度
         //maxFileSize: 300,//单位为kb，如果为0表示不限制文件大小
         //minFileCount: 0,
         maxFileCount: 1, //表示允许同时上传的最大文件个数
         enctype: 'multipart/form-data',
         validateInitialCount:true,
         previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
         msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
     }).on("filebatchselected", function(event, files) {  
     }).on("fileuploaded", function(event, data,previewId, index) {
	     if(data.response){
	    	 if(data.response.status==200){
	    		 bootbox.alert('文件上传成功');  
	    		 $("#classImg").val(data.response.data);
	    	 }
	     }else{
	    	 bootbox.alert(data.response.msg);
	     }  
     });  
}

