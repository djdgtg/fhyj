var oTable = null;
var faIcon = {
		valid: 'fa fa-check-circle fa-lg text-success',
		invalid: 'fa fa-times-circle fa-lg',
		validating: 'fa fa-refresh'
	}
$(window).on('load', function() {
	loadRoles();
	
	validRoleForm();
	
	$('#roleModal').on('hidden.bs.modal', function() {
	    $("#addForm").data('bootstrapValidator').destroy();
	    $('#addForm').data('bootstrapValidator', null);
	    validRoleForm();
	});
});


//获取角色数据
function loadRoles(){
	oTable = $("#roleTb").bootstrapTable({
    	method: "post",  //使用get请求到服务器获取数据  
//    	contentType : "application/x-www-form-urlencoded",
        url: "roles/list", //获取数据的Servlet地址   
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
        clickToSelect: true,    //是否启用点击选中行
        columns: [{
        	checkbox: true
        }, {
        	field:'roleid',
        	visible: false,
        }, {
        	field: 'rolename',
        	title: '角色名称',
        	align: 'center',
        	valign: 'middle',
        }, {
            field: 'description',
            title: '角色描述',
        }],
        toolbar:"#toolbar",
    });
}

function roleModal(optype){
	clearFormData("#addForm");
	$("#optype").val(optype);
	if(optype == 1){
		$("#modalTitle")[0].innerText = "修改角色";
		var selRoles = $("#roleTb").bootstrapTable('getSelections');
		if(selRoles.length != 1){
			bootbox.alert('请选择一个角色', function(){
				return;
	        });
			return;
		}else{
			var roleId = selRoles[0].roleid;
			var roleName = selRoles[0].rolename;
			var description = selRoles[0].description;
			
			$("#roleId").val(roleId);
			$("#roleName").val(roleName);
			$("#roleDes").val(description);
		}
	}else{
		$("#modalTitle")[0].innerText = "新增角色";
		loadRoles();
	}
	$("#roleModal").modal('show');
}



/**
 * 角色form验证
 */
function validRoleForm(){
    $('#addForm').bootstrapValidator({
		excluded: [':disabled'],
		feedbackIcons: faIcon,
		fields: {
		'rolename': {
			validators: {
				notEmpty: {
					message: '角色名称不能为空'
				},
				stringLength: {
					min: 1,
					max: 20,
					message: '角色名称长度必须在1~20位之间'
				},
			}
		},
		'description': {
			validators: {
				notEmpty: {
					message: '角色描述不能为空'
				},
				stringLength: {
					min: 1,
					max: 200,
					message: '角色描述长度必须在1~200位之间'
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
        var opType = $("#optype").val();;
        if(opType == 1){
        	insertAndUpdate("roles/update");
        }else{
        	insertAndUpdate("roles/add");
        }
    });
}

//增加、更新
function insertAndUpdate(url){
	$.ajax({
		type: "POST",
		url: url,
		data: $("#addForm").serialize(),
	    success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
		    		//关闭对话框
		    		$('#roleModal').modal('hide');
		    		//清空表单数据
		    		clearFormData("#addForm");
		    		//重新加载表格数据
		    		$("#roleTb").bootstrapTable("refresh");
	            });
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
	    	
		}
	});
}

//删除角色
function delRole(){
	var selRoles = $("#roleTb").bootstrapTable('getSelections');
	if(selRoles.length == 0){
		bootbox.alert('至少选择一个角色', function(){
           return;
        });
		return;
	}else{
		var roleIds = "";  
	  
		for (var i = 0; i < selRoles.length; i++) {  
			roleIds += selRoles[i].roleid + ","; 
		} 
		roleIds += 0;
		bootbox.confirm("确定删除?", function(result) {
	        if (result) {
	        	$.ajax({
	        		type: "POST",
	        		url: "roles/delBatch",
	        		data: {"roleIds":roleIds},
	        	    success: function(data) {
	        	    	if (data.status == 200) {
	        	    		bootbox.alert(data.msg, function(){
		        	    		//重新加载表格数据
		        	    		$("#roleTb").bootstrapTable("refresh");
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
 * 配置菜单
 */
function menuModal(){
	var selRoles = $("#roleTb").bootstrapTable('getSelections');
	if(selRoles.length != 1){
		bootbox.alert('请选择一个角色', function(){
            $.niftyNoty({
                type: 'warning',
                icon : 'pli-exclamation icon-2x',
                container : 'floating',
                timer : 5000
            });
        });
		return false;
	}
	var roleId = selRoles[0].roleid;
	$("#roleIdForMenu").val(roleId);
	
	loadRoleMenu(roleId);
	
	$('#menuModal').modal('show');
}

function loadRoleMenu(roleId){
	$.ajax({  
		url: "rolemenu/list",  
		data:{'roleId':roleId},
		dataType: "json",  
		success: function (result) { 
			 $('#menuSelect').treeview({
	                data: result.data,         // 数据源
	                showCheckbox: true,   //是否显示复选框
	                multiSelect: true,    //多选
	                levels : 3,
	                onNodeChecked : function (event,node) {
	                    var selectNodes = getChildNodeIdArr(node); //获取所有子节点
	                    if (selectNodes) { //子节点不为空，则选中所有子节点
	                        $('#menuSelect').treeview('checkNode', [selectNodes, { silent: true }]);
	                    }
	                },
	                onNodeUnchecked : function(event, node) { //取消选中节点
	                    var selectNodes = getChildNodeIdArr(node); //获取所有子节点
	                    if (selectNodes) { //子节点不为空，则取消选中所有子节点
	                        $('#menuSelect').treeview('uncheckNode', [selectNodes, { silent: true }]);
	                    }
	                },
	                onNodeExpanded : function(event, data) {
	                },
	                onNodeSelected: function (event, data) {
	                	console.log(data.nodeId+'-->'+data.Id);
	                }
	            });
        },  
    });
}

function saveRoleMenu(){
	var roleId = $("#roleIdForMenu").val();
	var selectNodes = $('#menuSelect').treeview('getChecked');
	var menuIds = "";
	$.each(selectNodes, function (index, node) {  
		console.log(node)
		menuIds += node.Id + ",";
    }); 
	
	$.ajax({
		type: "POST",
		url: "rolemenu/setRoleMenus",
		data: {'roleId':roleId,'menuIds':menuIds},
	    success: function(data) {
	    	if (data.status == 200) {
	    		bootbox.alert(data.msg, function(){
		    		//重新加载表格数据
		    		$("#roleTb").bootstrapTable("refresh");
		    		$('#menuModal').modal('hide');
	            });
	    	} else {
	    		bootbox.alert(data.msg);
	    	}
		}
	});
}