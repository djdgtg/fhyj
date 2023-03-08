var icons = ['demo-pli-add', 'demo-pli-add-cart', 'demo-pli-add-user', 'demo-pli-add-user-plus-star', 'demo-pli-arrow-down', 'demo-pli-arrow-left', 'demo-pli-arrow-out-right', 'demo-pli-arrow-right', 'demo-pli-arrow-up', 'demo-pli-bag-coins', 'demo-pli-bell', 'demo-pli-building', 'demo-pli-check', 'demo-pli-checked-user', 'demo-pli-clock', 'demo-pli-coding', 'demo-pli-coin', 'demo-pli-computer-secure', 'demo-pli-consulting', 'demo-pli-cross', 'demo-pli-data-settings', 'demo-pli-data-storage', 'demo-pli-download-from-cloud', 'demo-pli-download-window', 'demo-pli-exclamation', 'demo-pli-exclamation-circle', 'demo-pli-facebook', 'demo-pli-file', 'demo-pli-file-add', 'demo-pli-file-csv', 'demo-pli-file-edit', 'demo-pli-file-excel', 'demo-pli-file-html', 'demo-pli-file-jpg', 'demo-pli-file-text-image', 'demo-pli-file-txt', 'demo-pli-file-word', 'demo-pli-file-zip', 'demo-pli-find-user', 'demo-pli-folder-with-document', 'demo-pli-gear', 'demo-pli-go-top', 'demo-pli-google-plus', 'demo-pli-home', 'demo-pli-inbox-full', 'demo-pli-inbox-into', 'demo-pli-information', 'demo-pli-instagram', 'demo-pli-internet', 'demo-pli-laptop', 'demo-pli-layout-grid', 'demo-pli-like', 'demo-pli-lock-user', 'demo-pli-love-user', 'demo-pli-magnifi-glass', 'demo-pli-mail', 'demo-pli-mail-attachment', 'demo-pli-mail-block', 'demo-pli-mail-favorite', 'demo-pli-mail-remove', 'demo-pli-mail-send', 'demo-pli-mail-unread', 'demo-pli-male', 'demo-pli-male-female', 'demo-pli-map', 'demo-pli-map-marker', 'demo-pli-mine', 'demo-pli-office', 'demo-pli-old-telephone', 'demo-pli-paper-plane', 'demo-pli-paperclip', 'demo-pli-plus', 'demo-pli-printer', 'demo-pli-question', 'demo-pli-question-circle', 'demo-pli-recycling', 'demo-pli-refresh', 'demo-pli-remove', 'demo-pli-remove-user', 'demo-pli-share', 'demo-pli-shopping-bag', 'demo-pli-shopping-basket', 'demo-pli-shopping-cart', 'demo-pli-star', 'demo-pli-support', 'demo-pli-tactic', 'demo-pli-tag', 'demo-pli-temperature', 'demo-pli-twitter', 'demo-pli-unlike', 'demo-pli-unlock', 'demo-pli-upload-to-cloud', 'demo-pli-video', 'demo-pli-view-list', 'demo-pli-wrench', 'demo-psi-add', 'demo-psi-arrow-left', 'demo-psi-arrow-right', 'demo-psi-bar-chart', 'demo-psi-building', 'demo-psi-car-coins', 'demo-psi-close', 'demo-psi-computer-secure', 'demo-psi-consulting', 'demo-psi-download-from-cloud', 'demo-psi-facebook', 'demo-psi-file', 'demo-psi-file-csv', 'demo-psi-file-excel', 'demo-psi-file-html', 'demo-psi-file-jpg', 'demo-psi-file-text-image', 'demo-psi-file-txt', 'demo-psi-file-word', 'demo-psi-file-zip', 'demo-psi-folder-organizing', 'demo-psi-gear', 'demo-psi-google-plus', 'demo-psi-happy', 'demo-psi-home', 'demo-psi-inbox-full', 'demo-psi-information', 'demo-psi-instagram', 'demo-psi-internet', 'demo-psi-like', 'demo-psi-mail', 'demo-psi-mail-favorite', 'demo-psi-mail-send', 'demo-psi-mail-unread', 'demo-psi-male', 'demo-psi-office', 'demo-psi-paperclip', 'demo-psi-printer', 'demo-psi-recycling', 'demo-psi-remove', 'demo-psi-repair', 'demo-psi-share', 'demo-psi-sidebar-window', 'demo-psi-star', 'demo-psi-tactic', 'demo-psi-tag', 'demo-psi-thunder', 'demo-psi-twitter', 'demo-psi-unlike', 'demo-psi-upload-to-cloud', 'demo-psi-warning-window'];


//if (toastr) {
//    toastr.options.positionClass = 'toast-top-center';
//    toastr.options.closeButton = true;
//    toastr.options.debug = true;
//    toastr.options.newestOnTop = true;
//    toastr.options.progressBar = true;
//    toastr.options.preventDuplicates = true;
//    toastr.options.showEasing = 'swing';
//    toastr.options.hideEasing = 'linear';
//    toastr.options.showMethod = 'fadeIn';
//    toastr.options.hideMethod = 'fadeOut';
//    toastr.options.showDuration = 300;
//    toastr.options.hideDuration = 1000;
//    toastr.options.timeOut = 5000;
//    toastr.options.extendedTimeOut = 1000;
//}

if ($.fn.bootstrapTable) {
    $.fn.bootstrapTable.defaults.method = 'GET';
    $.fn.bootstrapTable.defaults.striped = true;
    $.fn.bootstrapTable.defaults.cache = false;
    $.fn.bootstrapTable.defaults.showColumns = true;
    $.fn.bootstrapTable.defaults.showRefresh = true;
    $.fn.bootstrapTable.defaults.clickToSelect = true;
    $.fn.bootstrapTable.defaults.sidePagination = "server";
    $.fn.bootstrapTable.defaults.pagination = true;
    $.fn.bootstrapTable.defaults.pageSize = 12;
}

function OptionsToTreeViewData(odate) {
    var datastr = JSON.stringify(odate);
    datastr = datastr.replace(/,\"children\"\:\[\]/g, '');
    datastr = datastr.replace(/children/g, 'nodes');
    return JSON.parse(datastr);
}

(function ($) {
    $.fn.supertreegrid = function (method, options) {
        var $this = $(this);
        var $treegrid = null;
        if (method != undefined && typeof method != 'string') {
            options = method;
            method = 'init';
        }
        if (method == 'refresh')
            options = {};
        var settings = $.extend({
            columns: [],
            idfield: 'id',
            parentfield: 'parentid',
            url: '',
            method: 'Get',
            queryParams: null,
            treegridoptions: null,
            loadcomplete: null,
            rowselect: null,
            onClickCell: null,
        }, options);
        switch (method) {
            case 'init':
                init(options);
                break;
            case 'refresh':
                refresh();
                break;
            case 'treegrid':
                return $treegrid;
        }
        function init(options) {
            $this.data('options', options);
            $this.addClass('table table-hover tree');
            var theadhtml = '<thead><tr>';
            $.each(options.columns, function (i, item) {
                theadhtml += '<th data-field="' + item.field + '"><div class="th-inner ">' + item.title + '</div></th>';
            });
            theadhtml += '</thead></tr>';
            $this.append(theadhtml);
            $this.append('<tbody></tbody>');
            $.ajax({
                url: options.url,
                method: options.method,
                data: options.queryParams,
                success: function (result) {
                    load(result.data);
                }
            });
        }
        function load(data) {
            $this.find("tbody").empty();
            $this.data('selectedRow', null);
            addRow(data);
            $treegrid = $this.treegrid(options.treegridoptions);
            if (options.loadcomplete != null)
                options.loadcomplete($this);
            $this.treegrid('collapseAll');
        }
        function addRow(rows) {
            var row, rowdata, id, parentid;
            $.each(rows, function (i, item) {
                row = $('<tr></tr>');
                $this.find("tbody").append(row);
                id = eval('item.' + options.idfield);
                parentid = eval('item.' + options.parentfield);
                row.addClass('treegrid-' + id);
                if (parentid > 0)
                    row.addClass('treegrid-parent-' + parentid);
                rowdata = $.extend(true, {}, item);
                rowdata.children = null;
                row.data('rowData', rowdata);
                row.on('click', function () {
                    $(this).siblings().removeClass('selected');
                    $(this).addClass('selected');
                    $this.data('selectedRow', $(this).data('rowData'));
                    if (options.rowselect != null)
                        options.rowselect($this, $(this).data('rowData'));
                });

                var cell, cellvalue;
                $.each(options.columns, function (j, col) {
                    cellvalue = eval('item.' + col.field);
                    if (col.formatter != undefined && col.formatter != null) {
                        var funname = eval(col.formatter);
                        cellvalue = funname(cellvalue, rows);
                    }
                    if (cellvalue == null)
                        cellvalue = '-';
                    cell = $('<td>' + cellvalue + '</td>');
                    row.append(cell);
                    cell.on('click', function () {
                        cellvalue = eval('item.' + col.field);
                        cell = $(this);
                        rowdata = $(this).parent().data('rowData');
                        if (options.onClickCell != null) {
                            options.onClickCell(col.field, cellvalue, rowdata, cell);
                        }
                    });
                });
                if (item.children != null && item.children.length > 0) {
                    addRow(item.children);
                }
            })
        }
        function refresh() {
            options = $this.data('options');
            $.ajax({
                url: options.url,
                method: options.method,
                data: options.queryParams,
                success: function (result) {
                    load(result.data);
                }
            });
        }
        return $this;
    }

    $.fn.webform = function (method, data) {
        var $this = $(this);
        switch (method) {
            case 'load':
                load(data);
                break;
            case 'clear':
                clear();
                break;
        }
        function load(data) {
            $this.find("input[type!=button]").each(function (i, item) {
                var name = $(this).attr("name");
                var value = null;
                if (data[name] != undefined)
                    value = data[name];
                var type = $(this).attr("type");
                switch (type) {
                    case "radio":
                    case "checkbox":
                        if ($(this).attr("value") == value)
                            $(this).attr('checked', true);
                        else
                            $(this).attr('checked', false);
                        break;
                    default:
                        $(this).val(value);
                        break;
                }
            });
            $this.find("textarea").each(function (i, item) {
                var name = $(this).attr("name");
                var value = null;
                if (data[name] != undefined)
                    value = data[name];
                $(this).val(value);
            })
            //combox
            $this.find('.bootstrap-select>select').each(function (i, item) {
                var name = $(this).attr("name");
                var value = -1;
                if (data[name] != undefined)
                    value = data[name];
                $(this).combox('setvalue', value);
            })
            //comboxtree
            $this.find('.formcomboxtree').each(function (i, item) {
                var $this = $(this);
                var val = $this.prev().val();
                if (val != null) {
                    var treeid = $this.prev().attr('name') + '_seltree';
                    var treenodes = $('#' + treeid).treeview('getEnabled');
                    if (treenodes.length) {
                        $.each(treenodes, function (i, item) {
                            if (item.id == val) {
                                $this.find('span').eq(0).text(item.text);
                                return false;
                            }
                        })
                    }
                } else {
                    $this.find('span').eq(0).text('--请选择--');
                }
            })
        }
        function clear() {
            $this.find("input[type!=button]").each(function (i, item) {
                var type = $(this).attr("type");
                switch (type) {
                    case "radio":
                    case "checkbox":
                        $(this).attr('checked', false);
                        break;
                    default:
                        $(this).val('');
                        break;
                }
            });
            $this.find("textarea").each(function (i, item) {
                $(this).val('');
            })
            //combox
            $this.find('.bootstrap-select>select').each(function (i, item) {
                var data = $(this).data('sources');
                if (!!data && data.length)
                    $(this).combox('setvalue', data[0].id);
            })
            //comboxtree
            $this.find('.formcomboxtree').each(function (i, item) {
                $(this).find('span').eq(0).text('--请选择--');
            })

        }
        return $this;
    }

    $.fn.combox = function (method, options) {
        var $this = $(this);
        if (method != undefined && typeof method != 'string') {
            options = method;
            method = 'init';
        }
        if (method == 'getitem') {
            var val = $this.val().toString();
            if (val == "-1")
                return null;
            var sources = $this.data('sources');
            if (sources == null || sources.length == 0)
                return null;
            var selitem = null;
            $.each(sources, function (i, item) {
                if (item.id == val) {
                    selitem = item;
                    return false;
                }
            })
            return selitem;
        }
        var settings = $.extend({
            url: '',
            method: 'Get',
            data: null,
            sources: null,
            value: null,
            loadcomplete: null,
            selected: null,
        }, options);
        switch (method) {
            case 'init':
                init(options);
                break;
            case 'setvalue':
                setvalue(options);
                break;
        }
        function init(options) {
            var $selector = $this.selector;
            if ($selector.length < 1) {
                var id = $this.attr('id');
                if (!!id) {
                    $selector = '#' + id;
                } else {
                    var localName = $this[0].tagName;
                    var name = $this.attr('name');
                    if (!!localName && !!name) {
                        $selector = localName + '[name=' + name + ']';
                    }
                }
            }
            $this.empty();
            if (options.selected != null) {
                $this.on('change', function (obj) {
                    var val = $(this).val();
                    options.selected($this, val);
                });
            }
            $this.selectpicker({});
            if (options.sources != null) {
                databind($selector, options.sources);
            } else {
                $.ajax({
                    url: options.url,
                    method: options.method,
                    data: options.data,
                    success: function (result) {
                        databind($selector, result.data);
                    }
                });
            }
        }
        function databind($selector, data) {
            var selid = options.value;
            $.each(data, function (i, item) {
                $('<option value="' + item.id + '">' + item.text + '</option>').appendTo($($selector));
                if (selid == undefined || selid == null)
                    selid = item.id;
                if (item.selected)
                    selid = item.id;
            })
            $($selector).data('sources', data);
            if (selid != null) {
                $($selector).selectpicker('val', selid);
            }
            $($selector).selectpicker('render');
            $($selector).selectpicker('refresh');
            if (options.loadcomplete != null)
                options.loadcomplete($($selector));
        }
        function setvalue(data) {
            $this.selectpicker('val', data);
        }
        return $this;
    }

    $.fn.comboxtree = function (method, options) {
        var $this = $(this);
        var $treediv;
        var $tree;

        if (method != undefined && typeof method != 'string') {
            options = method;
            method = 'init';
        }
        if (method == 'refresh')
            options = {};
        var settings = $.extend({
            url: '',
            method: 'Get',
            data: null,
            value: null,
            isroot: false,
            loadcomplete: null,
            selected: null,
            treeconfig: {},
        }, options);
        switch (method) {
            case 'init':
                init(options);
                break;
            case 'setvalue':
                setvalue(options);
                break;
            case 'refresh':
                refresh();
                break;
            case 'tree':
                return getTree();
                break;
        }
        function init(options) {
            $this.data('options', options);
            $this.attr('placeholder', null);
            $this.attr('readonly', 'readonly');
            $tree = getTree();
            if ($tree.length > 0)
                return;
            var treeid = $this.attr('name') + '_seltree';
            $this.parent().append('<div class="formcomboxtree"><div class="formcomboxtree-handdiv"><span>--请选择--</span><span>▼</span></div><div><div id="' + treeid + '"></div></div></div>');
            $tree = $('#' + treeid);
            $treediv = $tree.parent().parent();
            $treediv.children('div').eq(1).hide();
            $treediv.find(".formcomboxtree-handdiv").on('click', function () {
                if ($treediv.children('div').eq(1).is(':hidden'))
                    $treediv.children('div').eq(1).show();
                else
                    $treediv.children('div').eq(1).hide();
            })
            $.ajax({
                url: options.url,
                method: options.method,
                data: options.data,
                success: function (data) {
                    if (options.isroot)
                        data.data.unshift({ id: '0', text: '根节点' });
                    data = OptionsToTreeViewData(data.data);
                    $tree.treeview({
                        data: data,
                        collapseAll: true,
                        selectable: true,
                        multiSelect: false,
                    });
                    $tree.on('nodeSelected', function (event, node) {
                        $this.val(node.id);
                        $treediv.find('span').eq(0).text(node.text);
                        $treediv.children('div').eq(1).hide();
                        if (options.selected != null)
                            options.selected($this, node);
                    })
                    $tree.treeview('collapseAll', { silent: true });
                    if (options.value != null) {
                        setvalue(options.value);
                    }
                    if (options.loadcomplete != null)
                        options.loadcomplete($this);
                }
            })
        }
        function getTree() {
            var treeid = $this.attr('name') + '_seltree';
            $tree = $('#' + treeid);
            return $tree;
        }
        function refresh() {
            options = $this.data('options');
            $tree = getTree();
            $treediv = $tree.parent().parent();
            $.ajax({
                url: options.url,
                method: options.method,
                data: options.data,
                success: function (data) {
                    data.data.unshift({ id: '0', text: '根节点' });
                    data = OptionsToTreeViewData(data.data);
                    $tree.treeview({
                        data: data,
                        collapseAll: true,
                        selectable: true,
                        multiSelect: false,
                    });
                    $tree.on('nodeSelected', function (event, node) {
                        $this.val(node.id);
                        $treediv.find('span').eq(0).text(node.text);
                        $treediv.children('div').eq(1).hide();
                    })
                    $tree.treeview('collapseAll', { silent: true });
                }
            })
        }
        function setvalue(val) {
            $this.val(val);
            $tree = getTree();
            var treenodes = $tree.treeview('getEnabled');
            if (treenodes.length) {
                $.each(treenodes, function (i, item) {
                    if (item.id == val) {
                        $this.find('span').eq(0).text(item.text);
                        return false;
                    }
                })
            }

        }
        return $this;
    }

    $.fn.ExtendSort = function (options) {
        var $this = $(this);
        var lastCell = $this.data('lastCell');
        var settings = $.extend({
            idField: 'id',
            sortField: 'sorts',
            tableName: null,
            cellObj: null,
            id: null,
        }, options);
        function init() {
            if (options.cellObj.find('input').length > 0) {
                endedit(options.cellObj);
                return;
            }
            if (lastCell != undefined && lastCell != null && lastCell != options.cellObj) {
                endedit(lastCell);
            }
            startedit();
        }
        function startedit() {
            var value = options.cellObj.text();
            if (value == null || isNaN(value))
                value = 0;
            var inputobj = $('<input name="' + options.sortField + '" type="number" style="width:50px;" value="' + value + '"/>');
            options.cellObj.empty();
            options.cellObj.append(inputobj);
            inputobj.focus();
            inputobj.on('click', function () {
                return false;
            })
            inputobj.on('keyup', function () {
                var val = $(this).val();
                val = val.replace(/[^0-9]/g, '');
                $(this).val(val);
            })
            lastCell = options.cellObj;
            $this.data('lastCell', lastCell);
        }
        function endedit(cell) {
            var cellinput = cell.find('input');
            if (cellinput.length > 0) {
                var val = cellinput.val();
                if (val == null || isNaN(val))
                    val = 0;
                cell.text(val);
                save(val);
            }
        }
        function save(val) {
            var sdata = {
                idfield: options.idField,
                sortfield: options.sortField,
                tablename: options.tableName,
                idvalue: options.id,
                sortvalue: val
            }
            $.ajax({
                url: '/RIPSP/Base/Options/UpdateDataSorts',
                method: 'Post',
                data: sdata,
                success: function (result) {
                    console.log(result);
                }
            });
        }
        init();
        return $this;
    }
})(jQuery);
