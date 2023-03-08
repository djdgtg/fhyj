<html lang="en" class="fullscreen-bg">
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- plugins CSS -->
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/plugins/font-awesome/css/font-awesome.min.css">
    <!-- MAIN CSS -->
    <link rel="stylesheet" href="static/css/main.css">
    <!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
    <link rel="stylesheet" href="static/css/demo.css">
    <!-- ICONS -->
    <link rel="apple-touch-icon" sizes="76x76" href="static/img/logo.png">
    <link rel="icon" type="image/png" sizes="96x96" href="static/img/logo.png">
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet"/>
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>
    <script src="static/js/login.js"></script>
</head>

<body>
<!-- WRAPPER -->
<div id="wrapper">
    <div class="vertical-align-wrap">
        <div class="vertical-align-middle">
            <div class="auth-box ">
                <div class="left">
                    <div class="content">
                        <div class="header">
                            <div class="logo text-center"><p class="lead">
                                <div style="font-weight:bold;font-size: 16px;">党员知家管理平台</div>
                            </div>
                            <p class="lead">Login to your account</p>
                        </div>
                        <form class="form-auth-small" id="loginForm">
                            <div class="form-group">
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="username">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="password" name="passwd"
                                       placeholder="password">
                            </div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block">LOGIN</button>
                        </form>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- END WRAPPER -->
</body>

</html>
