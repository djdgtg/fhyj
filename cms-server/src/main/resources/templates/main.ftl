<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>党建知家平台管理</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="static/css/nifty.min.css" rel="stylesheet">
    <link href="static/css/demo/nifty-demo-icons.min.css" rel="stylesheet">
    <link href="static/css/demo/nifty-demo.min.css" rel="stylesheet">

    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href="static/plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">

    <!--JAVASCRIPT-->
    <!--Pace - Page Load Progress Par [OPTIONAL]-->
    <link href="static/plugins/pace/pace.min.css" rel="stylesheet">
    <script src="static/plugins/pace/pace.min.js"></script>
    <!--jQuery [ REQUIRED ]-->
    <script src="static/js/jquery-2.2.4.min.js"></script>
    <script src="static/js/comm.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="static/js/bootstrap.js"></script>
    <!--NiftyJS [ RECOMMENDED ]-->
    <script src="static/js/nifty.js"></script>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="static/plugins/bootbox/bootbox.min.js"></script>
    <!--Demo script [ DEMONSTRATION ]-->
    <script src="static/js/demo/nifty-demo.js"></script>
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src="static/plugins/bootstrap-validator/bootstrapValidator.min.js"></script>

    <!--DataTables Sample [ PORTAL ]-->
    <script src="static/js/index.js"></script>
</head>

<!--TIPS-->
<!--You may remove all ID or Class names which contain "demo-", they are only used for demonstration. -->
<body>
<div id="container" class="effect aside-float aside-bright mainnav-lg">
    <!--NAVBAR-->
    <!--===================================================-->
    <header id="navbar">
        <div id="navbar-container" class="boxed">
            <!--Brand logo & name-->
            <!--================================-->
            <div class="navbar-header">
                <a href="main.html" class="navbar-brand"> <img
                        src="static/img/logo.png" class="brand-icon">
                    <div class="brand-title">
                        <span class="brand-text">党建知家平台管理</span>
                    </div>
                </a>
            </div>
            <!--================================-->
            <!--End brand logo & name-->
            <!--Navbar Dropdown-->
            <!--================================-->
            <div class="navbar-content clearfix">
                <ul class="nav navbar-top-links pull-left">
                    <!--Navigation toogle button-->
                    <li class="tgl-menu-btn">
                        <a class="mainnav-toggle" href="#">
                            <i class="demo-pli-view-list"></i>
                        </a>
                    </li>
                    <!--End Navigation toogle button-->
                </ul>
            </div>
            <!--================================-->
            <!--End Navbar Dropdown-->

        </div>
    </header>
    <!--===================================================-->
    <!--END NAVBAR-->
    <div class="boxed">
        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
            <iframe name="_iframe" src="welcome" marginwidth="0"
                    marginheight="0" style="width: 100%; height: 100%; border: none;"></iframe>
        </div>
        <!--===================================================-->
        <!--END CONTENT CONTAINER-->
        <!--MAIN NAVIGATION-->
        <!--===================================================-->
        <nav id="mainnav-container">
            <div id="mainnav">
                <!--Menu-->
                <!--================================-->
                <div id="mainnav-menu-wrap">
                    <div class="nano has-scrollbar">
                        <div class="nano-content">
                            <div id="mainnav-shortcut">
                                <ul class="list-unstyled">
                                    <li class="col-xs-4" data-content="个人信息">
                                        <a class="shortcut-grid" href="#" onclick="userProfile()">
                                            <i class="demo-psi-male"></i>
                                        </a>
                                    </li>
                                    <li class="col-xs-4" data-content="密码修改">
                                        <a class="shortcut-grid" href="#" onclick="editPswd()">
                                            <i class="demo-psi-speech-bubble-3"></i>
                                        </a>
                                    </li>
                                    <li class="col-xs-4" data-content="退出登录">
                                        <a class="shortcut-grid" href="#" onclick="logout()">
                                            <i class="demo-pli-unlock"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="mainnavDiv"></div>
                        </div>
                    </div>
                </div>
                <!--================================-->
                <!--End menu-->
            </div>
        </nav>
        <!--===================================================-->
        <!--END MAIN NAVIGATION-->
    </div>
    <!--Default Bootstrap Modal-->
    <div class="modal fade" id="profileModal" role="dialog" tabindex="-1"
         aria-labelledby="profileModal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <i class="pci-cross pci-circle"></i>
                    </button>
                    <h4 class="modal-title">个人信息</h4>
                </div>
                <form id="profileForm" method="post" class="form-horizontal">
                    <!--Modal body-->
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">真实姓名：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control"
                                       name="realname" id="realName"> <input
                                    type="hidden" class="form-control" name="userid"
                                    id="userIdForProfile">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">联系方式：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control"
                                       name="phone" id="phone">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">邮箱：</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control"
                                       name="email" id="email">
                            </div>
                        </div>
                    </div>

                    <!--Modal footer-->
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default"
                                type="button">取消
                        </button>
                        <button class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--End Default Bootstrap Modal-->
    <!--Default Bootstrap Modal-->
    <div class="modal fade" id="pswdModal" role="dialog" tabindex="-1"
         aria-labelledby="pswdModal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <i class="pci-cross pci-circle"></i>
                    </button>
                    <h4 class="modal-title">密码修改</h4>
                </div>
                <form id="pswdForm" method="post" class="form-horizontal">
                    <!--Modal body-->
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">当前密码：</label>
                            <div class="col-lg-7">
                                <input type="password" class="form-control"
                                       name="oldPswd" id="oldPswd">
                                <input type="hidden" class="form-control"
                                       name="userId" id="userId"> <input
                                    type="hidden" id="pswd"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">新密码：</label>
                            <div class="col-lg-7">
                                <input type="password" class="form-control"
                                       name="password" id="newPswd">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">确认新密码：</label>
                            <div class="col-lg-7">
                                <input type="password" class="form-control" name="reNewPswd"
                                       id="reNewPswd">
                            </div>
                        </div>
                    </div>
                    <!--Modal footer-->
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default"
                                type="button">取消
                        </button>
                        <button class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--End Default Bootstrap Modal-->
    <!-- FOOTER -->
    <footer id="footer">
        <p class="copyright">Copyright &copy; 2018.Company name All rights reserved Retech</p>
    </footer>
    <!-- END FOOTER -->
    <!-- SCROLL PAGE BUTTON -->
    <!--===================================================-->
    <button class="scroll-top btn">
        <i class="pci-chevron chevron-up"></i>
    </button>
</div>
<!--===================================================-->
<!-- END OF CONTAINER -->
<!--===================================================-->
<div id="demo-set" class="demo-set">
    <div id="demo-set-body" class="demo-set-body collapse">
        <div id="demo-set-alert"></div>
        <div class="pad-hor bord-btm clearfix">
            <div class="pull-right pad-top">
                <button id="demo-btn-close-settings" class="btn btn-trans">
                    <i class="pci-cross pci-circle icon-lg"></i>
                </button>
            </div>
            <div class="media">
                <div class="media-left">
                    <i class="demo-pli-gear icon-2x"></i>
                </div>
                <div class="media-body">
                    <span class="text-semibold text-lg text-main">Costomize</span>
                    <p class="text-muted text-sm">Customize Nifty's layout, sidebars, and color schemes.</p>
                </div>
            </div>
        </div>
        <div class="demo-set-content clearfix">
            <div class="col-xs-6 col-md-3">
                <div class="pad-all bg-gray-light">
                    <p class="text-semibold text-main text-lg">Layout</p>
                    <p class="text-semibold text-main">Boxed Layout</p>
                    <div class="pad-btm">
                        <div class="pull-right">
                            <input id="demo-box-lay" class="toggle-switch" type="checkbox">
                            <label for="demo-box-lay"></label>
                        </div>
                        Boxed Layout
                    </div>
                    <div class="pad-btm">
                        <div class="pull-right">
                            <button id="demo-box-img" class="btn btn-sm btn-trans" disabled><i
                                    class="pci-hor-dots"></i></button>
                        </div>
                        Background Images <span class="label label-primary">New</span>
                    </div>

                    <hr class="new-section-xs bord-no">
                    <p class="text-semibold text-main">Animations</p>
                    <div class="pad-btm">
                        <div class="pull-right">
                            <input id="demo-anim" class="toggle-switch" type="checkbox" checked>
                            <label for="demo-anim"></label>
                        </div>
                        Enable Animations
                    </div>
                    <div class="pad-btm">
                        <div class="select pull-right">
                            <select id="demo-ease">
                                <option value="effect" selected>ease (Default)</option>
                                <option value="easeInQuart">easeInQuart</option>
                                <option value="easeOutQuart">easeOutQuart</option>
                                <option value="easeInBack">easeInBack</option>
                                <option value="easeOutBack">easeOutBack</option>
                                <option value="easeInOutBack">easeInOutBack</option>
                                <option value="steps">Steps</option>
                                <option value="jumping">Jumping</option>
                                <option value="rubber">Rubber</option>
                            </select>
                        </div>
                        Transitions
                    </div>

                    <hr class="new-section-xs bord-no">

                    <p class="text-semibold text-main text-lg">Header / Navbar</p>
                    <div>
                        <div class="pull-right">
                            <input id="demo-navbar-fixed" class="toggle-switch" type="checkbox">
                            <label for="demo-navbar-fixed"></label>
                        </div>
                        Fixed Position
                    </div>

                    <hr class="new-section-xs bord-no">

                    <p class="text-semibold text-main text-lg">Footer</p>
                    <div class="pad-btm">
                        <div class="pull-right">
                            <input id="demo-footer-fixed" class="toggle-switch" type="checkbox">
                            <label for="demo-footer-fixed"></label>
                        </div>
                        Fixed Position
                    </div>
                </div>
            </div>
            <div class="col-lg-9 pos-rel">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="pad-all">
                            <p class="text-semibold text-main text-lg">Sidebars</p>
                            <p class="text-semibold text-main">Navigation</p>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-nav-fixed" class="toggle-switch" type="checkbox">
                                    <label for="demo-nav-fixed"></label>
                                </div>
                                Fixed Position
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-nav-profile" class="toggle-switch" type="checkbox" checked>
                                    <label for="demo-nav-profile"></label>
                                </div>
                                Widget Profil
                                <small class="label label-primary">New</small>
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-nav-shortcut" class="toggle-switch" type="checkbox" checked>
                                    <label for="demo-nav-shortcut"></label>
                                </div>
                                Shortcut Buttons
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-nav-coll" class="toggle-switch" type="checkbox">
                                    <label for="demo-nav-coll"></label>
                                </div>
                                Collapsed Mode
                            </div>

                            <div class="clearfix">
                                <div class="select pad-btm pull-right">
                                    <select id="demo-nav-offcanvas">
                                        <option value="none" selected disabled="disabled">-- Select Mode --</option>
                                        <option value="push">Push</option>
                                        <option value="slide">Slide in on top</option>
                                        <option value="reveal">Reveal</option>
                                    </select>
                                </div>
                                Off-Canvas
                            </div>

                            <p class="text-semibold text-main">Aside</p>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-asd-vis" class="toggle-switch" type="checkbox">
                                    <label for="demo-asd-vis"></label>
                                </div>
                                Visible
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-asd-fixed" class="toggle-switch" type="checkbox" checked>
                                    <label for="demo-asd-fixed"></label>
                                </div>
                                Fixed Position
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-asd-float" class="toggle-switch" type="checkbox" checked>
                                    <label for="demo-asd-float"></label>
                                </div>
                                Floating <span class="label label-primary">New</span>
                            </div>
                            <div class="mar-btm">
                                <div class="pull-right">
                                    <input id="demo-asd-align" class="toggle-switch" type="checkbox">
                                    <label for="demo-asd-align"></label>
                                </div>
                                Left Side
                            </div>
                            <div>
                                <div class="pull-right">
                                    <input id="demo-asd-themes" class="toggle-switch" type="checkbox">
                                    <label for="demo-asd-themes"></label>
                                </div>
                                Dark Version
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div id="demo-theme">
                            <div class="row bg-gray-light pad-top">
                                <p class="text-semibold text-main text-lg pad-lft">Color Schemes</p>
                                <div class="demo-theme-btn col-md-4 pad-ver">
                                    <p class="text-semibold text-main">Header</p>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-a-light add-tooltip"
                                           data-theme="theme-light" data-type="a" data-title="(A). Light">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-navy add-tooltip"
                                           data-theme="theme-navy" data-type="a" data-title="(A). Navy Blue">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-ocean add-tooltip"
                                           data-theme="theme-ocean" data-type="a" data-title="(A). Ocean">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-a-lime add-tooltip"
                                           data-theme="theme-lime" data-type="a" data-title="(A). Lime">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-purple add-tooltip"
                                           data-theme="theme-purple" data-type="a" data-title="(A). Purple">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-dust add-tooltip"
                                           data-theme="theme-dust" data-type="a" data-title="(A). Dust">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-a-mint add-tooltip"
                                           data-theme="theme-mint" data-type="a" data-title="(A). Mint">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-yellow add-tooltip"
                                           data-theme="theme-yellow" data-type="a" data-title="(A). Yellow">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-well-red add-tooltip"
                                           data-theme="theme-well-red" data-type="a" data-title="(A). Well Red">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-a-coffee add-tooltip"
                                           data-theme="theme-coffee" data-type="a" data-title="(A). Coffee">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-prickly-pear add-tooltip"
                                           data-theme="theme-prickly-pear" data-type="a"
                                           data-title="(A). Prickly pear">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-a-dark add-tooltip"
                                           data-theme="theme-dark" data-type="a" data-title="(A). Dark">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                </div>
                                <div class="demo-theme-btn col-md-4 pad-ver">
                                    <p class="text-semibold text-main">Brand</p>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-b-light add-tooltip"
                                           data-theme="theme-light" data-type="b" data-title="(B). Light">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-navy add-tooltip"
                                           data-theme="theme-navy" data-type="b" data-title="(B). Navy Blue">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-ocean add-tooltip"
                                           data-theme="theme-ocean" data-type="b" data-title="(B). Ocean">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-b-lime add-tooltip"
                                           data-theme="theme-lime" data-type="b" data-title="(B). Lime">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-purple add-tooltip"
                                           data-theme="theme-purple" data-type="b" data-title="(B). Purple">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-dust add-tooltip"
                                           data-theme="theme-dust" data-type="b" data-title="(B). Dust">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-b-mint add-tooltip"
                                           data-theme="theme-mint" data-type="b" data-title="(B). Mint">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-yellow add-tooltip"
                                           data-theme="theme-yellow" data-type="b" data-title="(B). Yellow">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-well-red add-tooltip"
                                           data-theme="theme-well-red" data-type="b" data-title="(B). Well red">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-b-coffee add-tooltip"
                                           data-theme="theme-coffee" data-type="b" data-title="(B). Coofee">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-prickly-pear add-tooltip"
                                           data-theme="theme-prickly-pear" data-type="b"
                                           data-title="(B). Prickly pear">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-b-dark add-tooltip"
                                           data-theme="theme-dark" data-type="b" data-title="(B). Dark">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                </div>
                                <div class="demo-theme-btn col-md-4 pad-ver">
                                    <p class="text-semibold text-main">Navigation</p>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-c-light add-tooltip"
                                           data-theme="theme-light" data-type="c" data-title="(C). Light">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-navy add-tooltip"
                                           data-theme="theme-navy" data-type="c" data-title="(C). Navy Blue">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-ocean add-tooltip"
                                           data-theme="theme-ocean" data-type="c" data-title="(C). Ocean">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-c-lime add-tooltip"
                                           data-theme="theme-lime" data-type="c" data-title="(C). Lime">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-purple add-tooltip"
                                           data-theme="theme-purple" data-type="c" data-title="(C). Purple">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-dust add-tooltip"
                                           data-theme="theme-dust" data-type="c" data-title="(C). Dust">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-c-mint add-tooltip"
                                           data-theme="theme-mint" data-type="c" data-title="(C). Mint">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-yellow add-tooltip"
                                           data-theme="theme-yellow" data-type="c" data-title="(C). Yellow">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-well-red add-tooltip"
                                           data-theme="theme-well-red" data-type="c" data-title="(C). Well Red">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                    <div class="demo-justify-theme">
                                        <a href="#" class="demo-theme demo-c-coffee add-tooltip"
                                           data-theme="theme-coffee" data-type="c" data-title="(C). Coffee">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-prickly-pear add-tooltip"
                                           data-theme="theme-prickly-pear" data-type="c"
                                           data-title="(C). Prickly pear">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                        <a href="#" class="demo-theme demo-c-dark add-tooltip"
                                           data-theme="theme-dark" data-type="c" data-title="(C). Dark">
                                            <div class="demo-theme-brand"></div>
                                            <div class="demo-theme-head"></div>
                                            <div class="demo-theme-nav"></div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="demo-bg-boxed" class="demo-bg-boxed">
                    <div class="demo-bg-boxed-content">
                        <p class="text-semibold text-main text-lg mar-no">Background Images</p>
                        <p class="text-sm text-muted">Add an image to replace the solid background color</p>
                        <div class="row">
                            <div class="col-lg-4 text-justify">
                                <p class="text-semibold text-main">Blurred</p>
                                <div id="demo-blurred-bg" class="text-justify">
                                    <!--Blurred Backgrounds-->
                                </div>
                            </div>
                            <div class="col-lg-4 text-justify">
                                <p class="text-semibold text-main">Polygon &amp; Geometric</p>
                                <div id="demo-polygon-bg" class="text-justify">
                                    <!--Polygon Backgrounds-->
                                </div>
                            </div>
                            <div class="col-lg-4 text-justify">
                                <p class="text-semibold text-main">Abstract</p>
                                <div id="demo-abstract-bg" class="text-justify">
                                    <!--Abstract Backgrounds-->
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="demo-bg-boxed-footer">
                        <button id="demo-close-boxed-img" class="btn btn-primary">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button id="demo-set-btn" class="btn" data-toggle="collapse" data-target="#demo-set-body"><i
            class="demo-psi-gear icon-lg"></i></button>
</div>
<!--===================================================-->
<!-- END SETTINGS -->
</body>
</html>
