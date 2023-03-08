var oTable = null;

//新增
var faIcon = {
	valid: 'fa fa-check-circle fa-lg text-success',
	invalid: 'fa fa-times-circle fa-lg',
	validating: 'fa fa-refresh'
}
$(window).on('load', function() {
	//加载角色列表
	getRoleslist();
	//加载管理员数据
	loadManagerTb();
    //form验证
	validManagerForm();
	$('#managerModal').on('hidden.bs.modal', function() {
	    $("#addForm").data('bootstrapValidator').destroy();
	    $('#addForm').data('bootstrapValidator', null);
	    validManagerForm();
	});
});

function getRoleslist(){
	$.ajax({
		url : "roles/list",
		dataType : "json",
		success : function(data) {
			$("#_roleid").append("<option value=''>未选择</option>");
			if (data != null) {
				$.each(data, function(index, dic) {
					$("#_roleid").append("<option value='" + dic.roleid + "'>"+ dic.rolename+"</option>");
				});
			}
			$('#_roleid').selectpicker('refresh');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			bootbox.alert('加载失败，请稍后重试');
		}
	});
}

//加载管理员数据
function loadManagerTb(){
	oTable = $("#managerTb").bootstrapTable({
    	method: "post",  //使用get请求到服务器获取数据  
    	contentType : "application/x-www-form-urlencoded",
        url: "manager/list", //获取数据的Servlet地址  
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
        sidePagination: "client", //表示服务端请求  
		locale:'zh-CN',
        clickToSelect: true,    //是否启用点击选中行
        columns: [{
        	checkbox: true
        }, {
        	field:'userid',
        	visible: false,
        }, {
        	field: 'username',
        	title: '用户名',
        	align: 'center',
        	valign: 'middle',
        }, {
        	field: 'realname',
        	title: '真实姓名',
        },  {
            field: 'phone',
            title: '电话',
        	formatter: function ( data, type, full ) {
        		if(data == null){
        			return '';
        		}else{
        			return data;
        		}
       		}
        }, {
        	field: 'email',
        	title: '邮箱',
        }, {
        	field: 'rolename',
        	title: '角色',
        },{
        	field: 'status',
        	title: '状态',
        	formatter: function ( data, type, full ) {
        		if(data == 1){
        			return '正常';
        		}
        		if(data == 0){
        			return '锁定';
        		}
       		}
        }, {
        	field: 'lastlogintime',
        	title: '上次登录时间',
        	formatter: function ( data, type, full ) {
        		if(data == null){
        			return '';
        		}else{
		    		var date = new Date(data);
		    		return date.Format("yyyy-MM-dd hh:mm:ss");
        		}
       		}
        } 
        ],
        toolbar:"#toolbar"
    });
}


function searchManager(){
	oTable.bootstrapTable("refresh");
}

/**
 * 加载角色列表
 */
function loadRoles(roleIdArr){
    $("#roleSelect").children().remove();       
    $.ajax({
		type: "POST",
		url: "roles/list",
		success: function (data) {  
        	if(data != null){
        		$("#roleSelect").append("<option value=''>---请选择角色---</option>");  
                $.each(data, function (index, role) { 
                    $("#roleSelect").append("<option value='" + role.roleid + "'>" + role.rolename + "</option>");  
                });  
        	}
			$("#roleSelect").selectpicker('val',roleIdArr);
        	$("#roleSelect").selectpicker("refresh");
        },  
	});
}

function managerModal(optype){
	clearFormData("#addForm");
	$("#optype").val(optype);
	var roleIdArr = new Array(); 
	if(optype == 1){
		$("#modalTitle")[0].innerText = "修改管理员";
		var selManagers = $("#managerTb").bootstrapTable('getSelections');
		if(selManagers.length != 1){
			bootbox.alert('请选择一个管理员', function(){
				return;
	        });
			return;
		}else{
			$("#userName").val(selManagers[0].username);
			//$('#userName').attr("disabled",true);
			$("#realName").val(selManagers[0].realname);
			$("#email").val(selManagers[0].email);
			$("#phone").val(selManagers[0].phone);
			$("#userId").val(selManagers[0].userid);
			var roleIds = selManagers[0].roleIds;
			if(roleIds != null && roleIds.length > 0){  
				$.each(roleIds, function (index, roleId) {  
					roleIdArr.push(String(roleId));
		        });  
			}
		}
	}else{
		$("#modalTitle")[0].innerText = "新增管理员";
	}
	loadRoles(roleIdArr);
	$("#managerModal").modal('show');
}

function validManagerForm(){
    //字典类型form验证
    $('#addForm').bootstrapValidator({
		excluded: [':disabled'],
		feedbackIcons: faIcon,
		fields: {
		'username': {
			validators: {
				notEmpty: {
					message: '管理员名称不能为空'
				},
				stringLength: {
					min: 1,
					max: 20,
					message: '管理员名称长度必须在1~20位之间'
				},
			}
		},
		'phone': {
			validators: {
				notEmpty: {
					message: '联系电话不能为空'
				},
				digits: {
					message: '联系电话只能为数字'
				}
			}
		},
		'roleIdsStr': {
			validators: {
				notEmpty: {
					message: '请至少选择一个角色'
				},
			}
		},
		'email': {
			validators: {
				notEmpty: {
					message: '邮箱不能为空'
				},
                emailAddress: {
                    message: '邮箱地址格式有误'
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
        	insertAndUpdate("manager/update");
        }else{
        	insertAndUpdate("manager/add");
        }
    });
}

function insertAndUpdate(url){
	$.ajax({
		type: "POST",
		async: false,
		url: url,
		data: $("#addForm").serialize(),
	 	success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
	    			//关闭对话框
		    		$('#managerModal').modal('hide');
		    		//清空表单数据
		    		clearFormData("#addForm");
		    		//重新加载表格数据
		    		$("#managerTb").bootstrapTable("refresh");
	            });
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
		}
	});
}
//删除管理员
function delManager(){
	var selManagers = $("#managerTb").bootstrapTable('getSelections');
	if(selManagers.length == 0){
		bootbox.alert('至少选择一个管理员', function(){
            return;
        });
		return;
	}else{
		var managerIds = "";  
		for (var i = 0; i < selManagers.length; i++) {  
			managerIds += selManagers[i].userid + ","; 
		} 
		bootbox.confirm("确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "manager/delBatch",
	        		data: {"managerIds": managerIds},
	        	    success: function(data) {
	        	    	if (data.status == 200) {
	        	    		bootbox.alert(data.msg, function(){
		        	    		//重新加载表格数据
		        	    		$("#managerTb").bootstrapTable("refresh");
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

//重置密码
function resetPasswd(){
	var selManagers = $("#managerTb").bootstrapTable('getSelections');
	if(selManagers.length == 0){
		bootbox.alert('至少选择一个管理员', function(){
            return;
        });
		return;
	}else{
		var managerIds = "";  
		var isfrist = false;
		for (var i = 0; i < selManagers.length; i++) {
			if (isfrist) {
				managerIds += ",";
			}
			managerIds += selManagers[i].userid;
			isfrist = true;
		}
		$.ajax({
    		type: "POST",
    		url: "manager/updateBatch",
    		data: {"managerIds":managerIds},
    	    success: function(data) {
    	    	if (data.status == 200) {
    	    		bootbox.alert(data.msg, function(){
        	    		//重新加载表格数据
        	    		$("#managerTb").bootstrapTable("refresh");
    	            });
    	    	} else {
    	    		bootbox.alert(data.msg);
    	    	}
    		}
    	});
	}
}


//锁定、解锁
function lockUn(status){
	var selManagers = $("#managerTb").bootstrapTable('getSelections');
	if(selManagers.length < 1){
		bootbox.alert('请至少选择一个管理员', function(){
            return;
        });
		return;
	} else {
		var managerIds = "";
		var isfrist = false;
		for (var i = 0; i < selManagers.length; i++) {
			if (isfrist) {
				managerIds += ",";
			}
			managerIds += selManagers[i].userid;
			isfrist = true;
		}
		var msg="";
		if(status==0){
			msg="确定锁定？"
		}else if(status==1){
			msg="确定解锁？"
		}
		bootbox.confirm(msg, function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "manager/updateBatch",
	        		data: {'managerIds':managerIds,'status':status},
	        		success: function(data) {
	         	    	if (data.status == 200) {
	         	    		bootbox.alert(data.msg, function(){
	             	    		//重新加载表格数据
	             	    		$("#managerTb").bootstrapTable("refresh");
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

function sourceModal(){
	bootbox.alert("功能开发中");
}
