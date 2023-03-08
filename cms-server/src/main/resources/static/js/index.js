var loginUser = null;
$(document).ready(function() {
	//获取菜单
    loadUserMsg();
	loadMenus();
	validPswdForm();
	validProfileForm();
});

function loadMenus(){
	$("#mainnavDiv").children().remove();    
	$.ajax({
		type: "POST",
		url: "menus/getRolesMenus",
		success: function (data) { 
			//后台拼接html直接返回
			$("#mainnavDiv").append(data.data);
			$(document).trigger('nifty.ready');     
		}  
	});
}

function loadUserMsg(){
	$.ajax({
		type: "POST",
		url: "manager/loadLoginUser",
		success: function (data) {  
        	if(data != null){
        		loginUser = data;
        	}
        }
	});
	
}

function logout(){
	bootbox.confirm("确定退出?", function(result) {
        if (result) {
		    $.ajax({
		        url: 'manager/logout',
		        method: 'POST',
		        dataType:"json",
		        success: function (result) {
		        	if (result.status == 200) {
		                window.location.href = '';
			    	} else {
			    		bootbox.alert(result.actionResult.msg);
			    		return;
			    	}
		        }
		    });
        }
    });
}


function userProfile(){
	clearFormData("#profileForm");
    $("#realName").val(loginUser.realname);
    $("#userIdForProfile").val(loginUser.userid);
    $("#phone").val(loginUser.phone);
    $("#email").val(loginUser.email);
	$("#profileModal").modal('show');
}

function validProfileForm(){
    //字典类型form验证
    $('#profileForm').bootstrapValidator({
		excluded: [':disabled'],
		feedbackIcons: faIcon,
		fields: {
		'realname': {
			validators: {
				notEmpty: {
					message: '真实姓名不能为空'
				},
				stringLength: {
					min: 1,
					max: 20,
					message: '真实姓名长度必须在1~20位之间'
				},
			}
		},
		'phone': {
			validators: {
				stringLength: {
					min: 0,
					max: 50,
					message: '联系方式长度必须小于50位'
				},
			}
		},
		'email': {
			validators: {
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
        updateUserMsg();
    });
}

function updateUserMsg(){
	$.ajax({
		type: "POST",
		data: $("#profileForm").serialize(),
		dataType:"json",
		url: "manager/updateProfile",
        success: function(msg) {
	    	if (msg.status == 200) {
	    		bootbox.alert('个人信息修改成功',function(){
	    			loadUserMsg();
	        		clearFormData("#profileForm");
	            	$("#profileModal").modal('hide');
	    		});
	    	} else {
	    		bootbox.alert(msg.actionResult.msg);
	    	}
	    	
		}
	});
}

function editPswd(){
	clearFormData("#pswdForm");
    $("#pswd").val(loginUser.passwd);
    $("#userId").val(loginUser.userid);
	$("#pswdModal").modal('show');
}

function validPswdForm(){
    //字典类型form验证
    $('#pswdForm').bootstrapValidator({
		excluded: [':disabled'],
		feedbackIcons: faIcon,
		fields: {
		'oldPswd': {
			validators: {
				notEmpty: {
					message: '当前密码不能为空'
				},
			}
		},
		'password': {
			validators: {
				notEmpty: {
					message: '新密码不能为空'
				},
				stringLength: {
					min: 6,
					max: 12,
					message: '密码长度必须在6~12位之间'
				}
			}
		},
		'reNewPswd': {
			validators: {
				notEmpty: {
					message: '确认新密码不能为空'
				},
                identical: {//相同
                    field: 'password',
                    message: '两次密码不一致'
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
        updateUserPswd();
    });
}

/**
 * 修改密码
 * @returns
 */
function updateUserPswd(){
	$.ajax({
		type: "POST",
		data: $("#pswdForm").serialize(),
		dataType:"json",
		url: "manager/updatePswd",
        success: function(msg) {
	    	if (msg.status == 200) {
	    		bootbox.alert('密码修改成功，请重新登录',function(){
	        		clearFormData("#pswdForm");
	                window.location.href = '';
	    		});
	    	} else {
	    		bootbox.alert(msg.msg);
	    	}
	    	
		}
	});
}