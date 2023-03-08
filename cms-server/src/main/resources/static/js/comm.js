var loginUser = null;
var faIcon = {
		valid: 'fa fa-check-circle fa-lg text-success',
		invalid: 'fa fa-times-circle fa-lg',
		validating: 'fa fa-refresh'
	};

$.ajaxSetup( {    
    //设置ajax请求结束后的执行动作    
    complete : function(XMLHttpRequest, textStatus) {  
        // 通过XMLHttpRequest取得响应头，REDIRECT    
        var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向  
        if (redirect == "REDIRECT") {  
            var win = window;    
            while (win != win.top){  
                win = win.top;  
            }
          //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求  
          win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");    
        }  
    }    
});

/**
 * 初始化查询条件
 * @param searchCondition
 * @param d
 * @returns
 */
function initialSearchCondition(searchCondition) {
	var d = {};
		$.each(searchCondition, function(index) {
        	d[this.name] = this.value;
        	
        });
		return d;
	}

/**
 * 清空表单数据
 * @param formId
 */
function clearFormData(formId){
    $(formId).find("input, select, textarea, checkbox").removeAttr("disabled").val("");
    $(formId).find("checkbox").attr("checked",false);
}

/**
 * 重新加载表格数据
 * @param oTable
 */
function reloadTable(oTable){
	oTable.ajax.reload();
	oTable.draw();
}

//加载字典数据
function loadDicSelect(selectId,param,defaultValue){
	$(selectId).children().remove();
	$.ajax({
		type: "POST",
		url: "../dics/list",
		data: param,
	    success: function(data) {
	    	if (data.status == 200) {
	    		if(data.data != null && data.data.length > 0){
	    			var multiple=$(selectId).attr("multiple");
	    			if(multiple==undefined){
	    				$(selectId).append("<option value=''>未选择</option>");
	    			}
	    			$.each(data.data, function (index, dic) {  
	                	if(dic.isdictype == 1){
	                        $(selectId).append("<option value='" + dic.dictype + "'>" + dic.dictypename + "</option>");  
	                	}else{
	                        $(selectId).append("<option value='" + dic.dicvalue + "'>" + dic.dicname + "</option>");  
	                	}
	                });  
	                if(defaultValue == undefined || defaultValue == null){
	                	if(param['isdictype'] == 1){
		                	defaultValue = data.data[0].dictype;
	                	}else{
		                	defaultValue = data.data[0].dicvalue;
	                	}
	                }
	    		}
                $(selectId).selectpicker("show");
                var arr=defaultValue.split(',');
            	$(selectId).selectpicker("val",arr);
                $(selectId).selectpicker("refresh");
	    	}
		}
	});
}

//加载字典数据 自动拼接全部选项
function loadDicSelectAll(selectId,param,type){
	var url="../dics/list";
	if(type==0){
		var url="dics/list";
	}
	$(selectId).children().remove();
	$.ajax({
		type: "POST",
		url: url,
		data: param,
	    success: function(data) {
	    	if (data.status == 200) {
	    		if(data.data != null && data.data.length > 0){
	    			$(selectId).append("<option value=''>未选择</option>");  
	    			$.each(data.data, function (index, dic) {  
	                	if(dic.isdictype == 1){
	                        $(selectId).append("<option value='" + dic.dictype + "'>" + dic.dictypename + "</option>");  
	                	}else{
	                        $(selectId).append("<option value='" + dic.dicvalue + "'>" + dic.dicname + "</option>");  
	                	}
	                });  
	    		}
                $(selectId).selectpicker("show");
                $(selectId).selectpicker("refresh");
	    	}
		}
	});
}

//加载关联表数据
function loadTableSelect(selectId,param,defaultValue){
	$(selectId).children().remove();
	$.ajax({
		type: "POST",
		url: "../librarynexus/listTabdata",
		data: param,
		dataType:'json',
		success: function(data) {
			var list="";
			if (data != null) {
				if (data.status == 200) {
					if(data.data != null && data.data.length > 0){
						var name = "";
						var value = "";
						var tmpkeyArr = [];
						list=data.data;
						for(var key in list[0]){
							tmpkeyArr.push(key);
						}
						$(selectId).append("<option value=''>未选择</option>");  
						$.each(list, function (index, item) {  
							$(selectId).append("<option value='" + item[tmpkeyArr[0]] + "'>" + item[tmpkeyArr[1]] + "</option>");
						});  
						if(defaultValue == undefined || defaultValue == null){
							defaultValue = list[0].value;
						}
					}
					$(selectId).selectpicker("show");
					var arr="";
					if(defaultValue != undefined || defaultValue != null){
						arr=defaultValue.split(',');
					}
					$(selectId).selectpicker("val",arr);
					$(selectId).selectpicker("refresh");
				}
			}
		}
	});
}

//获取分类树
function loadClasses(classSelectId,valueId,choseId){
	$.ajax({  
		url: "../classes/treeList",  
		dataType: "json",  
		success: function (data) { 
			$(classSelectId).treeview({
				data:data.data.list,
                levels: 3,
                showCheckbox: true,
                multiSelect:true,
                onNodeSelected: function(event, node) {
                },
                onNodeChecked :function(event, node){
                	childrenchecked(node,classSelectId,"checkNode");
                	
                	var selectNodes = $(classSelectId).treeview('getChecked');
                	var nodeIds = "";
                	$.each(selectNodes, function (index, node) {  
                		nodeIds += node.id + ",";
                    });
                	nodeIds = nodeIds.substring(0,nodeIds.length-1);
                	$(valueId).val(nodeIds);
                },
                onNodeUnchecked :function(event, node){
                	childrenchecked(node,classSelectId,"uncheckNode");
                	
                	var selectNodes = $(classSelectId).treeview('getChecked');
                	var nodeIds = "";
                	$.each(selectNodes, function (index, node) {  
                		nodeIds += node.id + ",";
                    });
                	nodeIds = nodeIds.substring(0,nodeIds.length-1);
                	$(valueId).val(nodeIds);
                },
        	});
			
        	if(choseId == null || choseId == ""){
        		/*choseId = data.data[0].id;
        		var checkableNodes = $(classSelectId).treeview('search', [ String(choseId), { ignoreCase: false, exactMatch: true } ]);
                $(classSelectId).treeview('selectNode', [ checkableNodes, { silent: true }]);*/
        	}else{
        		var strs= new Array(); //定义一数组 
            	strs=choseId.split(","); //字符分割 
            	
            	$.each(strs, function (index, nodeid) {
    				//exactMatch 是否精确匹配
    		        var checkableNodes = $(classSelectId).treeview('search', [ nodeid, { ignoreCase: false, exactMatch: true } ]);
    		        $(classSelectId).treeview('checkNode', [ checkableNodes, { silent: true }]);
    	        });
        	}
        	$(valueId).val(choseId);
        	$(classSelectId).treeview("collapseAll");//默认折叠所有节点
        },  
          
        error: function (XMLHttpRequest, textStatus, errorThrown) {  

        }  
    });
}

function TreeSingleSelect(treeID, checkNode) {  
    if (!treeID)  
        return;  
    var objs = document.getElementsByTagName("input");  
    for (var i = 0; i < objs.length; i++) {  
        if (objs[i].type == 'checkbox') {  
            var obj = objs[i];  
            if (obj.id.indexOf(treeID) != -1) {  
                if (obj != checkNode) {  
                    obj.checked = false;  
                }  
            }  
        }  
    }  
} 
/**
 * 递归勾选节点的子节点和父节点
 * @param node
 * @param classSelectId
 * @param status
 */
function childrenchecked(node,classSelectId,status){
	if(node.children != null){
		$.each(node.children, function (index, item) {
			var nodeid = item.id+"";
			var checkableNodes = $(classSelectId).treeview('search', [ nodeid, { ignoreCase: false, exactMatch: true } ]);
	        $(classSelectId).treeview(status, [ checkableNodes, { silent: true }]);
	        
			childrenchecked(item,classSelectId,status);
		});
	}else{
		var nodeid = node.id+"";
		var checkableNodes = $(classSelectId).treeview('search', [ nodeid, { ignoreCase: false, exactMatch: true } ]);
        $(classSelectId).treeview(status, [ checkableNodes, { silent: true }]);
	}
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
    	return unescape(r[2]); 
    return null;
}

function getChildNodeIdArr(node) {
    var ts = [];
    if (node.nodes) {
        for (x in node.nodes) {
            ts.push(node.nodes[x].nodeId);
            if (node.nodes[x].nodes) {
                var getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                for (j in getNodeDieDai) {
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    } else {
        ts.push(node.nodeId);
    }
    return ts;
}

Date.prototype.Format = function (fmt) { //author: meizz 
	 var o = {
	     "M+": this.getMonth() + 1, //月份 
	     "d+": this.getDate(), //日 
	     "h+": this.getHours(), //小时 
	     "m+": this.getMinutes(), //分 
	     "s+": this.getSeconds(), //秒 
	     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	     "S": this.getMilliseconds() //毫秒 
	 };
	 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	 for (var k in o)
	 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	 return fmt;
	}

function dateFormat( data, type, full ) {
	if(data == null){
		return '';
	}else{
		var date = new Date(data);
		return date.Format("yyyy-MM-dd hh:mm:ss");
	}
}

// 校验正整数
function isPositiveInteger(s) {// 是否为正整数
    var re = /^[1-9]\d*$/;
    return re.test(s)
}

/**
 * 获取当前时间
 * @returns {String}
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1
        + strDate;
    return currentdate;
}