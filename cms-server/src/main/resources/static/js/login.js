var faIcon = {
	valid: 'fa fa-check-circle fa-lg text-success',
	invalid: 'fa fa-times-circle fa-lg',
	validating: 'fa fa-refresh'
}

$(load);

/*
* 页面加载
*/
function load() {
	validForm();
}


//表单验证
function validForm(){ 
	$('#loginForm').bootstrapValidator({
  	feedbackIcons: faIcon,
  	fields: {
  	'username': {
  		message: '用户名不能为空',
  		validators: {
  			notEmpty: {
  				message: '用户名不能为空.'
  			}
  		}
  	},
  	'passwd': {
  		message: '密码不能为空',
  		validators: {
  			notEmpty: {
  				message: '密码不能为空.'
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
      login();
  });
}

/**
* 登录
*/
function login() {
    $.ajax({
        url: 'manager/checklogin',
        method: 'POST',
        data: $('#loginForm').serialize(),
        success: function (result) {
        	if (result.status == 200) {
                //window.location.href = 'main';
                window.location.href = 'main';
	    	} else {
	    		bootbox.alert(result.msg);
	    		return;
	    	}
        },
    });
}