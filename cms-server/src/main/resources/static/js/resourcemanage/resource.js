var databaseName = "";
var mouldId = "";
var fileServerUrl="";
// 扩展字段
var extend = "";
var oTable = null;

var faIcon = {
	valid : 'fa fa-check-circle fa-lg text-success',
	invalid : 'fa fa-times-circle fa-lg',
	validating : 'fa fa-refresh'
}


$(window).on('load', function() {
	databaseName = getUrlParam("databaseName");// 获取资源库id
	mouldId = getUrlParam("mouldId");// 获取资源库id
	extend = getUrlParam("extend");// 获取资源库id
	$("#mouldSelect,#mouldExportId").val(mouldId);
	$("#dbSelect,#dbExportId").val(databaseName);
	loadLeaderTitle();
	loadButton(mouldId, databaseName);
	$("#highchart").hide();
	getfileServerUrl();
	loadMetadata(mouldId, databaseName);
	$('#resModal').on('hidden.bs.modal', function() {
		$("#resourceForm").data('bootstrapValidator').destroy();
		$('#resourceForm').data('bootstrapValidator', null);
	});
	$('#resModal').off('shown.bs.modal').on('shown.bs.modal', function (e) {
	    $(document).off('focusin.modal');// 解决编辑器弹出层文本框不能输入的问题
	});
});

function getfileServerUrl(){
	$.ajax({
		url : "../personal/getfileServerUrl",
		success : function(data) {
			fileServerUrl=data;
		},
	});
}

/**
 * 加载按钮button 暂时在js做控制
 * 
 * @param mouldId
 * @param databaseName
 */
function loadButton(mouldId, databaseName) {
	
}


// 表单验证
function validForm(validFormfields) {
	$('#resourceForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : faIcon,
		fields : validFormfields
	}).on('success.field.bv', function(e, data) {
		var $parent = data.element.parents('.form-group');
		$parent.removeClass('has-success');
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		$form.data('bootstrapValidator');
		insertAndUpdateRes();
	});
}

function loadLeaderTitle() {
	var activelink = $("#mainnav-menu", parent.document).find("li .active-link");
	$("#firstMenuVal").text(activelink.parent().parent().find(".menu-title").text());
	$("#secondMenuVal,#headMenuVal").text(activelink.text());
}

/**
 * 加载元数据
 * @param mouldId
 * @param databasename
 */
function loadMetadata(mouldId, databasename) {
	$("#searchDiv").children().remove();
	if (databasename == undefined || databasename == null) {
		return;
	}
	$.ajax({
				type : "POST",
				url : "../metadatas/getMetadataByField",
				data : {
					'databaseName' : databasename,
					'mouldId' : mouldId,
					'fieldName' : 'SearchShow',
					'fieldValue' : 1
				},
				success : function(data) {
					if (data.status == 200) {
						if (data.data != null) {
							var htmlMsg = '';
							var dicMap = {};
							var clsMap = {};
							var tabMap = {};
							var data = data.data;
							$.each(data,function(index, meta) {
												htmlMsg += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ meta.columnname+ '" /> ';
												if (index % 2 == 0) {
													htmlMsg += '<div class="row">';
												}
												htmlMsg += '<div class="col-sm-6">';
												switch (meta.columntype) {// 判断字段类型
												case 3:// 下拉单选
													if (meta.columnsource == 2) {// 数据来源：分类
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<div id="sel_'+ meta.columnname+ '"></div>';
														htmlMsg += '<input type="hidden" name="queryList['+ index+ '].nameValue" id="queryval_'+ meta.columnname+ '">';
														htmlMsg += '</div></div>';
														clsMap["#sel_"+ meta.columnname] = "#queryval_"+ meta.columnname;
													} else if (meta.columnsource == 1) {// 数据来源：字典
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<select class="selectpicker" id="sel_'+ meta.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
														var param = {
															'isdictype' : 0,
															'dictype' : meta.columntyperule
														};
														htmlMsg += '</div></div>';
														dicMap["#sel_"+ meta.columnname] = param;
													} else if (meta.columnsource == 3) {// 数据来源关联表
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<select class="selectpicker show-tick form-control" data-live-search="true" id="sel_'+ meta.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
														htmlMsg += '</div></div>';
														var param = {
															'mdbname' : databasename,
															'mdbfield' : meta.columnname
														};
														tabMap["#sel_"+ meta.columnname] = param;
													}
													break;
												case 4:// 下拉多选
													if (meta.columnsource == 2) {// 数据来源：分类
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<div id="sel_'+ meta.columnname+ '"></div>';
														htmlMsg += '<input type="hidden" name="queryList['+ index+ '].nameValue" id="queryval_'+ meta.columnname+ '">';
														htmlMsg += '</div></div>';
														clsMap["#sel_"+ meta.columnname] = "#queryval_"+ meta.columnname;
													} else if (meta.columnsource == 1) {// 数据来源：字典
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<select class="selectpicker" multiple id="sel_'+ meta.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
														var param = {
															'isdictype' : 0,
															'dictype' : meta.columntyperule
														};
														htmlMsg += '</div></div>';
														dicMap["#sel_"+ meta.columnname] = param;
													} else if (meta.columnsource == 3) {// 数据来源关联表
														htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
														htmlMsg += '<div class="col-sm-9">';
														htmlMsg += '<select class="selectpicker show-tick form-control" multiple data-live-search="true" id="sel_'+ meta.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
														htmlMsg += '</div></div>';
														var param = {
															'mdbname' : databasename,
															'mdbfield' : meta.columnname
														};
														tabMap["#sel_"+ meta.columnname] = param;
													}
													break;
												case 10:// 日期时间
													htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
													htmlMsg += '<div class="col-sm-9">';
													htmlMsg += '<div class="input-daterange input-group" id="datepicker">';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(enddatetime'+index+')}\'})" id="startdatetime'+index+'"/>';
													htmlMsg += '<span class="input-group-addon">to</span>';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(startdatetime'+index+')}\'})" id="enddatetime'+index+'"/>';
													htmlMsg += '</div></div></div>';
													break;
												case 11:// 日期
													htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
													htmlMsg += '<div class="col-sm-9">';
													htmlMsg += '<div class="input-daterange input-group" id="datepicker">';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd\',maxDate:\'#F{$dp.$D(enddate'+index+')}\'})" id="startdate'+index+'"/>';
													htmlMsg += '<span class="input-group-addon">to</span>';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(startdate'+index+')}\'})" id="enddate'+index+'"/>';
													htmlMsg += '</div></div></div>';
													break;
												case 17:// 整数
												case 18:// 小数
													htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
													htmlMsg += '<div class="col-sm-9">';
													htmlMsg += '<div class="input-daterange input-group">';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" id="start_'+ meta.columnname+ '" />';
													htmlMsg += '<span class="input-group-addon"> -- </span>';
													htmlMsg += '<input class="form-control" type="text" name="queryList['+ index+ '].nameValue" id="end_'+ meta.columnname+ '" /> ';
													htmlMsg += '</div></div></div>';
													break;
												default:
													htmlMsg += '<div class="form-group"><label class="col-sm-3 control-label">'+ meta.columncname+ '：</label>';
													htmlMsg += '<div class="col-sm-9">';
													htmlMsg += '<input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="txt_'+ meta.columnname+ '" />';
													htmlMsg += '</div></div>';
													break;
												}
												htmlMsg += '</div>';
												if (index % 2 != 0) {
													htmlMsg += '</div>';
												}
											});
							htmlMsg += '<div class="row"><div class="col-sm-6"><div class="form-group"><label class="col-sm-3 control-label"></label><div class="col-sm-9"><input type="button" class="btn btn-default" onclick="searchResource()" value="查询"></div></div></div></div>';
							$("#searchDiv").append(htmlMsg);
							if (dicMap != null) {
								for ( var dic in dicMap) {
									loadDicSelectAll(dic, dicMap[dic],1);
								}
							}
							if (clsMap != null) {
								for ( var cls in clsMap) {
									loadClasses(cls, clsMap[cls]);
								}
							}
							if (tabMap != null) {
								for ( var tab in tabMap) {
									loadTableSelect(tab, tabMap[tab]);
								}
							}
							loadTbMsg(mouldId, databasename);
						}
					}
				}
			});
}

/**
 * 下载导入数据所需模板
 * 
 * @param type
 *            0只导出列 1导出所有数据
 */
function downImportAndExportModal(type) {
	var databaseName = getUrlParam("databaseName");// 获取资源库id
	var mouldId = getUrlParam("mouldId");// 获取资源库id
	var form = $("<form>");
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	form.attr("action","../metadatas/getMetadataByField");
	$("body").append(form);
	var tablecname = $("#mainnav-menu", parent.document).find("li .active-link").text().replace("管理", "");
	var input1 = $("<input>");
	input1.attr("type", "hidden");
	input1.attr("name", "databaseName");
	input1.attr("value", databaseName);
	form.append(input1);
	var input2 = $("<input>");
	input2.attr("type", "hidden");
	input2.attr("name", "mouldId");
	input2.attr("value", mouldId);
	form.append(input2);
	var input3 = $("<input>");
	input3.attr("type", "hidden");
	input3.attr("name", "fieldName");
	input3.attr("value", "ImportAndExportShow");
	form.append(input3);
	var input4 = $("<input>");
	input4.attr("type", "hidden");
	input4.attr("name", "fieldValue");
	input4.attr("value", 1);
	form.append(input4);
	var input5 = $("<input>");
	input5.attr("type", "hidden");
	input5.attr("name", "tablecname");
	input5.attr("value", tablecname);
	form.append(input5);
	var input6 = $("<input>");
	input6.attr("type", "hidden");
	input6.attr("name", "type");
	input6.attr("value", type);
	form.append(input6);
	form.submit();
	form.remove();
}

/**
 * 文件上传类型的返回
 * @param row
 * @param value
 * @returns {String}
 */
function fileformtter(row, value) {
	if(row!=null&&row!=undefined){
		var rows=row.split(","); //字符分割 
		var html='';
		$.each(rows,function(index,item){
			if(item!=undefined){
				var rowtmp = item.lastIndexOf("."); // 得到"."在第几位
				rowtmp = item.substring(rowtmp); // 截断"."之前的，得到后缀
				if (rowtmp == ".mp3"|| rowtmp == ".wav"|| rowtmp == ".wma" || rowtmp == ".vqf")  { // 根据后缀，判断是否符合音频、视频格式
					html += '<audio controls="controls">'+'<source src="'+fileServerUrl+item+'" type="audio/ogg">Your browser does not support the audio element</audio>'
				} else if (rowtmp == ".bmp" || rowtmp == ".png" || rowtmp == ".gif"|| rowtmp == ".jpg" || rowtmp == ".jpeg"){ // 根据后缀，判断是否符合图片格式
					html += "<img src=\"" +fileServerUrl+item+ "\" width=\"100px\" heigth=\"40px\"></img>";
				} else if (rowtmp == ".ogg" || rowtmp == ".rmvb"|| rowtmp == ".qsv"|| rowtmp == ".mp4"){
					html += '<video width="360" height="260" src="'+fileServerUrl+item+'" controls="controls">your browser does not support the video tag</video>'
				}
			}else{
				html+='';
			}
		});
	}	
	return html;
}

function dateformtter(row, value) {
	if(row == null||row == ''){
		return '';
	}else{
		var date = new Date(row);
		return date.Format("yyyy-MM-dd");
	}
}
function dateTimeformtter(row, value) {
	if(row == null||row == ''){
		return '';
	}else{
		var date = new Date(row);
		return date.Format("yyyy-MM-dd hh:mm:ss");
	}
}
function textformtter(row, value) {
    if(row == null||row == ''){
        return '';
    }else{
       	var text = row.replace(/\s+/g,'&nbsp;')
        return '<span class="colStyle" title="'+text+'">'+row+'</span>';
    }
}
/**
 * 加载表格数据
 * @param mouldId
 * @param databasename
 */
function loadTbMsg(mouldId, databasename) {
	var array = [];
	var columns = [];
	$.ajax({
				type : "POST",
				url : "../metadatas/getMetadataByField",
				data : {
					'databaseName' : databasename,
					'mouldId' : mouldId,
					'fieldName' : 'GridShow',
					'fieldValue' : 1
				},
				success : function(data) {
					if (data.status == 200) {
						if (data.data.length > 0) {
							$.each(data.data, function(index, el) {
								var field, formtter;
								if (el.columnname != 'seqid') {
									if (el.columntype == 3 || el.columntype == 4) {
										field = el.columnname + "Name";
									} else {
										field = el.columnname;
									}
									if (el.columntype == 10) {// 日期时间
										formtter = dateTimeformtter;
									} else if (el.columntype == 11) {// 日期
										formtter = dateformtter;
									} else if (el.columntype == 8) {// 文件类型
										formtter = fileformtter;
									} else if (el.columntype == 2) {// 多行
                                        formtter = textformtter;
                                    }
									if(el.editable==1){//允许直接修改列值
										array.push({
											field : field,
											title : el.columncname,
											formatter :  formtter,
											sortable : true,
											editable: {
								                type: 'text',  
								                validate: function (value) {
								                	var re = new RegExp(el.validexp);
								                    if(el.required==1){
								                    	if(value==null||value==undefined||value==''){
								                    		return el.columncname+"不能为空！"; 
								                    	}else if (!re.test(value)) {  
									                        return el.validmsg;  
									                    }
								                    }else if (!re.test(value)) {  
								                        return el.validmsg;  
								                    }
								                }
								            } 
										});
									}else{
										array.push({
											field : field,
											title : el.columncname,
											formatter : formtter,
											sortable : true
										});
									}
								}
							});
							array.unshift({
								checkbox : true,
							});
							columns.push(array);
							$("#resourceTb").bootstrapTable("destroy");
							oTable = $("#resourceTb").bootstrapTable({
								method : "POST",
								contentType:"application/x-www-form-urlencoded; charset=UTF-8",//POST需加;GET需处理中文乱码
								url : "../datalibrarys/getSelfDataList", // 获取数据的Servlet地址
								queryParams : function(params) {
									var searchCondition = $("#searchForm").serializeArray();
									searchCondition.push({
										"name" : "start",
										"value" : params.offset
									});
									searchCondition.push({
										"name" : "length",
										"value" : params.limit
									});
									if (params.sort != undefined&& params.sort.slice(-4) == "Name") {
										searchCondition.push({
											"name" : "order[0].column",
											"value" : params.sort.substr(0,params.sort.length - 4)
										});
									} else {
										searchCondition.push({
											"name" : "order[0].column",
											"value" : params.sort
										});
									}
									searchCondition.push({
										"name" : "order[0].dir",
										"value" : params.order
									});
									initialSearchCondition(searchCondition);
									return searchCondition;
								},
								responseHandler : function(res) {
									return {
										"total" : res.data.length,// 总页数
										"rows" : res.data.list // 数据
									};
								},
								striped : true, // 表格显示条纹
								locale : 'zh-CN',
								pagination : true, // 启动分页
                                paginationShowPageGo: true,
								pageSize : 10, // 每页显示的记录数
								pageNumber : 1, // 当前第几页
								pageList : [ 5, 10, 20, 50, 'ALL' ], // 记录数可选列表
								search : false, // 是否启用查询
								sortable : true, // 是否启用排序
								showColumns : true, // 显示下拉框勾选要显示的列
								showRefresh : true, // 显示刷新按钮
								clickToSelect : true, // 是否启用点击选中行
								sidePagination : "server", // 表示服务端请求
								columns : columns,
								toolbar : "#toolbar",
								showExport : true, // 是否显示导出按钮
								exportDataType : "all", // basic'导出当前页,'all'导出全部,'selected'导出选中项.
								onSort : function(name, order) {
									$("#loadEffect").modal('show');
								},
								onLoadSuccess : function(data) {
									$("#loadEffect").modal('hide');
								},
								onEditableSave: function (field, row, oldValue, $el) {//直接修改列属性：field-字段，row-修改的对应的对象
									var value="";
									Object.keys(row).forEach(function(key){
										if(key==field){
											value=row[key];
										}
									});
									$.ajax({
										type: 'POST',
										url: "../datalibrarys/updateEditableField",
										data: {
											'seqid':row.seqid,
											'field':field,
											'value':value,
											'databasename':databasename,
											'moulId':moulId,
										},
										dataType: 'JSON',
										success: function (msg, textStatus, jqXHR) {
											if (msg.actionResult.code == "SUCCESS") {
												alert(msg.actionResult.msg);
												$("#resourceTb").bootstrapTable("refresh");
											} else {
												bootbox.alert(msg.actionResult.msg);
											}
										},
										error: function () { alert("error");}
									});
								},
							});
						}
					}
				}
			});
}

/**
 * 多条件查询
 */
function searchResource() {
	$("#loadEffect").modal('show');
	$.ajax({
				type : "POST",
				url : "../datalibrarys/getSelfDataList",
				data : $("#searchForm").serializeArray(),
				success : function(data) {
					if (data.status == 200) {
						if (data.data != null && data.data.length > 0) {
							var data = {
								"total" : data.data.length,
								"rows" : data.data.list
							};
							$("#resourceTb").bootstrapTable('load', data);
						} else {
							$("#resourceTb").bootstrapTable("refresh");
						}

					}
					$("#loadEffect").modal('hide');
				}
			});
}

/**
 * 资源modal
 * 
 * @param optype
 * @returns {Boolean}
 */
function resourceModal(optype) {
	clearFormData("#resourceForm");
	$("#optype").val(optype);
	if (optype == 1) {
		var selRes = $("#resourceTb").bootstrapTable('getSelections');
		if (selRes.length != 1) {
			bootbox.alert('请选择一条数据', function() {
				return;
			});
			return;
		} else {
			if (undefined != typeof (mouldId)&& undefined != typeof (databaseName) && null != mouldId&& null != databaseName) {
				loadResForm(mouldId, databaseName, selRes[0].seqid);
			}
		}
	} else {
		loadResForm(mouldId, databaseName, null);
	}
	$("#resModal").modal('show');
	return true;
}

/**
 * 资源表单
 * 
 * @param mouldId
 * @param databasename
 * @param seqid
 */
function loadResForm(mouldId, databasename, seqid) {
	$("#resFormDiv").children().remove();
	var dbInfo = null;
	if (seqid != null && seqid > 0) {
		$.ajax({
					url : "../datalibrarys/getSelfDbInfo",
					data : {
						'databaseName' : databasename,
						'seqId' : seqid
					},
					method : 'POST',
					async : false,
					success : function(data) {
						if (data.status == 200) {
							dbInfo = data.data[0];
						}
					}
				})
	}
	$.ajax({
				method : 'POST',
				url : "../metadatas/getMetadataByField",
				data : {
					'databaseName' : databasename,
					'mouldId' : mouldId,
					'fieldName' : 'FormShow',
					'fieldValue' : 1
				},
				success : function(result) {
					if (result.status == 200) {
						var validFormfields = {};
						var dataMsg = '<input type="hidden" name="seqId" value="'+ seqid + '"/>';
						dataMsg += '<input type="hidden" name="databaseName" value="'+ databasename + '"/>';
						dataMsg += '<input type="hidden" name="mouldId" value="'+ mouldId + '"/>';
						$("#resFormDiv").append(dataMsg);
						$.each(result.data,function(index, row) {
											var rowValue = '';
											if (dbInfo != null) {
												var test = row.columnname;
												rowValue = dbInfo[test];
												column = row.columncname;
												if (rowValue == undefined||rowValue == null) {
													rowValue='';
												}
											}
											var tmpobj=new Object();
											var validators=new Object();
											if (row.required == 1) {
												validators.notEmpty={message : row.columncname+'不能为空'};
											}if(row.validexp!=null&&row.validexp!=""){
												validators.regexp={regexp : row.validexp,message : row.validmsg};
											}if(row.columnlength!=null&&row.columnlength!=""){
												validators.stringLength={max: row.columnlength,message: '文字长度'+row.columnlength};
											}
											if(row.onlyness==1){
												validators.remote={
														url: "../personal/checkonly",
														message: '该'+row.columncname+'已被使用！',
														type: 'POST',//请求方式,
										                data: function(validator) {
										                      		return {
										                      			 columnname: row.columnname,
										                                 seqid: seqid,
										                                 index:index,
										                                 databasename: databasename,
										                            };
										                }
										                          	
												};
											}
											tmpobj.validators=validators;
											if(JSON.stringify(tmpobj)!='{"validators":{}}'){
												validFormfields['queryList['+ index + '].nameValue'] = tmpobj
											}
											if (row.required == 1) {
												row.columncname = '<span style="color: red" >✲</span>'+ row.columncname;
											}
											var formhtml = '';
											switch (row.columntype) {
											case 1:// 单行文本
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ rowValue+ '" /></div></div>';
												$("#resFormDiv").append(formhtml);
												break;
											case 2:// 多行文本
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><textarea rows="3" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '">'+ rowValue+ '</textarea></div></div>';
												$("#resFormDiv").append(formhtml);
												break;
											case 3:// 下拉单选
												if (row.columnsource == 2) {// 数据来源：分类
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].nameValue" id="val_'+ row.columnname+ '">';
													formhtml += '<div class="col-lg-7"><div id="f_'+ row.columnname+ '"></div></div>';
													$("#resFormDiv").append(formhtml);
													loadClasses("#f_"+ row.columnname,"#val_"+ row.columnname,rowValue);
												} else if (row.columnsource == 1) {// 数据来源：字典
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<div class="col-lg-7"><select class="selectpicker show-tick form-control" id="f_'+ row.columnname+ '" name="queryList['+ index+ '].nameValue"></select></div></div>';
													$("#resFormDiv").append(formhtml);
													var param = {
														'isdictype' : 0,
														'dictype' : row.columntyperule
													};
													loadDicSelect("#f_"+ row.columnname,param, rowValue);
												} else if (row.columnsource == 3) {// 数据来源关联表
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<div class="col-lg-7">';
													formhtml += '<select class="selectpicker show-tick form-control" data-live-search="true" id="f_'+ row.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
													formhtml += '</div></div>';
													$("#resFormDiv").append(formhtml);
													var param = {
														'mdbname' : databasename,
														'mdbfield' : row.columnname
													};
													loadTableSelect("#f_"+ row.columnname,param, rowValue);
												}
												break;
											case 4:// 下拉多选
												if (row.columnsource == 2) {// 数据来源：分类
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].nameValue" id="val_'+ row.columnname+ '">';
													formhtml += '<div class="col-lg-7"><div id="f_'+ row.columnname+ '"></div></div>';
													$("#resFormDiv").append(formhtml);
													loadClasses("#f_"+ row.columnname,"#val_"+ row.columnname,rowValue);
												} else if (row.columnsource == 1) {// 数据来源：字典
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<div class="col-lg-7"><select class="selectpicker show-tick form-control" multiple data-live-search="false" id="f_'+ row.columnname+ '" name="queryList['+ index+ '].nameValue"></select></div></div>';
													$("#resFormDiv").append(formhtml);
													var param = {
														'isdictype' : 0,
														'dictype' : row.columntyperule
													};
													loadDicSelect("#f_"+ row.columnname,param, rowValue);
												} else if (row.columnsource == 3) {// 数据来源关联表
													formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
													formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
													formhtml += '<div class="col-lg-7">';
													formhtml += '<select class="selectpicker show-tick form-control" multiple data-live-search="true" id="f_'+ row.columnname+ '" name="queryList['+ index+ '].nameValue"></select>';
													formhtml += '</div>';
													formhtml += '</div>';
													$("#resFormDiv").append(formhtml);
													var param = {
														'mdbname' : databasename,
														'mdbfield' : row.columnname
													};
													loadTableSelect("#f_"+ row.columnname,param, rowValue);
												}
												break;
											case 5:// 密码
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 col-md-3 control-label">'+row.columncname+'：</label><div class="col-lg-7 col-md-7">';
												formhtml += '<input type="text" class="form-control" name="queryList['+ index+ '].nameValue" placeholder="'+placeholder+'" id="f_'+ row.columnname+ '" value="'+ rowValue+ '" />'
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<input id="input_'+row.columnname +'" name="file" multiple type="file" class="file-loading"/></div></div>';
												$("#resFormDiv").append(formhtml);
												initFileInput(row.columnname);
												break;
											case 7:// 富文本
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 col-md-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" /><div class="col-lg-7 col-md-7">';
												formhtml += '<textarea style="visibility:hidden;" id="f_'+ row.columnname+ '" name="queryList['+ index+ '].nameValue" class="editortext form-control">'+ rowValue+ '</textarea>';
												formhtml += '</div></div>';
												$("#resFormDiv").append(formhtml);
												var editor = KindEditor.create("#f_"+ row.columnname,{
								                	items : ['source','undo','redo','quickformat','justifyleft','justifycenter','justifyright','justifyfull','fontname','fontsize','forecolor','hilitecolor','bold','italic','underline','image','link','unlink'],
								                	filePostName: "file",
								                	filterMode:true,
								                	pasteType:1,// 1纯文本粘贴，2Html粘贴
								                	//cssData: cssData,
								                	formatUploadUrl:false,
												    uploadJson : '/APMKAFManageSystem/resourcemanage/datalibrarysPersonalise/datalibraryPersonalise!uploadkindeditor.action',
												    afterUpload : function(url, data, name) { // 上传文件后执行的回调函数，必须为3个参数
											            if(name=="image" || name=="multiimage"){ // 单个和批量上传图片时
											                var img = new Image(); 
											                img.src = url;
											                img.onload = function(){ // 图片必须加载完成才能获取尺寸
											                	editor.html(editor.html().replace('<img src="' + url + '"','<img src="' + url + '" width="100%"'));
											                }
											            }
											        },
											        //调用kindeditor的afterCreate回调函数对kindeditor功能进行“扩展”，实现图片粘贴上传。
												    afterCreate:function() {
												        var editerDoc = this.edit.doc;
												        $(editerDoc).bind('paste', null, function (e) { 
												            var ele = e.originalEvent.clipboardData.items;
												            for (var i = 0; i < ele.length; ++i) {
												                if ( ele[i].kind == 'file' && ele[i].type.indexOf('image/') !== -1 ) {
												                    var file = ele[i].getAsFile();
												                    var formData = new FormData();
												                    formData.append("file",file);
												                    $.ajax({
												                        url : '../personal/uploadkindeditor',
												                        type : 'POST',
												                        data : formData,
												                        processData : false,
												                        contentType : false,
												                        dataType:"json",
												                        success : function(responseStr) {
												                            var src = responseStr.url;
												                            var imgTag = "<img src='"+src+"' width='100%'/>";
												                            editor.insertHtml(imgTag);
												                        },
												                        error : function(responseStr) {
												                            console.log("error");
												                        }
												                    });
												                }
												            }
												        });
												    },
												    afterChange: function(){
												    	this.sync();
												    }
												});
												break;
											case 8:// 文件上传
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 col-md-3 control-label">'+row.columncname+'：</label><div class="col-lg-7 col-md-7">';
												formhtml += '<input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ rowValue+ '" readonly="readonly"/>'
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<input id="input_'+row.columnname +'" name="file" multiple type="file" class="file-loading"/></div></div>';
												$("#resFormDiv").append(formhtml);
												initFileInput(row.columnname);
												break;
											case 10:// 日期时间
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><input type="text" class="input-group date form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ dateTimeformtter(rowValue)+ '" /></div></div>';
												$("#resFormDiv").append(formhtml);
												$("#f_" + row.columnname).click(function () {
												    WdatePicker({el: this, dateFmt: 'yyyy-MM-dd HH:mm:ss', lang: "zh-cn"});
												});
												break;
											case 11:// 日期
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ dateformtter(rowValue)+ '" /></div></div>';
												$("#resFormDiv").append(formhtml);
												$("#f_" + row.columnname).click(function () {
													WdatePicker({el: this, dateFmt: 'yyyy-MM-dd', lang: "zh-cn"});
												});
												break;
											case 17:// 整数
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ rowValue+ '" /></div></div>';
												$("#resFormDiv").append(formhtml);
												break;
											case 18:// 小数
												formhtml += '<div class="form-group" id="div_'+row.columnname+'"><label class="col-lg-3 control-label">'+ row.columncname+ '：</label>';
												formhtml += '<input type="hidden" name="queryList['+ index+ '].name" value="'+ row.columnname+ '" />';
												formhtml += '<div class="col-lg-7"><input type="text" class="form-control" name="queryList['+ index+ '].nameValue" id="f_'+ row.columnname+ '" value="'+ rowValue+ '" /></div></div>';
												$("#resFormDiv").append(formhtml);
												break;
											default:
												break;
											} 
										});
						validForm(validFormfields);
						return true;
					}
				}
			});
}

/**
 * 新增、修改资源信息
 */
function insertAndUpdateRes() {
	var opType = $("#optype").val();
	var url;
	if (opType == 1) {
		url = "../datalibrarys/updateRes";
	} else {
		url = "../datalibrarys/addRes";
	}
	var params = $("#resourceForm").serialize();
	console.log(params);
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		data : params,
		success : function(data) {
			if (data.status == 200) {
				clearFormData("#resourceForm");
				$('#resModal').modal('hide');
				alert(data.msg);
				$("#resourceTb").bootstrapTable("refresh");
			} else {
				bootbox.alert(data.msg);
			}
		}
	});
}

/**
 * 删除资源信息 逻辑删除，锁定，解锁 status不传的时候默认为删除
 * 
 * @returns {Boolean}
 */
function delResource(status) {
	var selRes = $("#resourceTb").bootstrapTable('getSelections');
	if (selRes.length < 1) {
		bootbox.alert('请至少选择一条数据', function() {
			return;
		});
		return;
	} else {
		var resIds = "";
		for (var i = 0; i < selRes.length; i++) {
			resIds += selRes[i].seqid + ",";
		}
		resIds += 0;
		bootbox.confirm("确定操作?",function(result) {
							if (result) {
								var databaseName = $("#dbSelect").val();
								$.ajax({
											type : "POST",
											url : "../datalibrarys/delResBatch",
											data : {
												'resIds' : resIds,
												'databaseName' : databaseName,
												"status" : status
											},
											success : function(data) {
												if (data.code == 200) {
													alert(data.msg);
													$("#resourceTb").bootstrapTable("refresh");
												} else {
													bootbox.alert(data.msg);
												}
											}
										});
							}
						});
	}
	return true;
}

/**
 * 文件上传
 */
function initFileInput(columnname) {   
	 var databaseNameExport = getUrlParam("databaseName");// 获取资源库id
	 var selRes = $("#resourceTb").bootstrapTable('getSelections');
		var seqid = "";
		if (selRes.length < 1) {
		} else {
			seqid = selRes[0].seqid;
		}
	 var extensions=[];
	 var filesize=0;
	 //根据表名以及字段名称给文件上传加文件类型限制,以及文件大小限制
	 if(databaseNameExport=="front_userinfo"){
		 if(columnname=="image"){
			 extensions.push('jpg', 'gif', 'png','jpeg');
			 filesize=500;
		 }
	 }
	 //如果上传多个文件，数据库保存地址多个;
	 var fileurls=[];
	 //上传初始化
	 var control = $('#input_' + columnname);
     control.fileinput({
    	 language: 'zh', //设置语言
         uploadUrl: "../personal/uploadFile", //上传的地址
         allowedFileExtensions:extensions,//接收的文件后缀，如['jpg', 'gif', 'png'],不填将不限制上传文件后缀类型
         //uploadExtraData:{"seqid": seqid,'columnname':columnname},//请求的额外参数
         showUpload: false, // 是否显示上传按钮
         showRemove:false, // 是否显示移除按钮
         showCaption:false, 
         dropZoneEnabled: false,
         validateInitialCount:true,// 验证初始计数,默认false
         maxFileSize: filesize,// 单位为kb，如果为0表示不限制文件大小
         enctype: 'multipart/form-data',
     }).on("fileuploaded", function(ev, data,previewId, index) {//文件上传成功的回调
	     if(data.response){
	    	 if(data.response.status==200){
	    		 bootbox.alert('文件上传成功');
	    		 fileurls.push(data.response.data);
	    	 }
	     }else{
	    	 alert('文件上传失败!');  
	     }  
	     $("#f_"+columnname).val(fileurls);
	     $("#f_"+columnname).trigger("input");
     });
     
}

/**
 * 导入数据modal
 * 
 * @returns {Boolean}
 */
function resImportModal() {
	 var databaseNameExport = getUrlParam("databaseName");// 获取资源库id
	 var mouldId = getUrlParam("mouldId");// 获取资源库id
	 $("#importResModal").modal('show');
	 var control = $('#importRes');
     control.fileinput({
    	 language: 'zh', //设置语言
         uploadUrl: "../personal/importFile", //上传的地址
         allowedFileExtensions:['xls','xlsx'],//接收的文件后缀，如['jpg', 'gif', 'png'],不填将不限制上传文件后缀类型
         uploadExtraData:{"databaseName": databaseNameExport,"mouldId":mouldId},//额外参数
         uploadAsync: true, //默认异步上传
         showUpload: true, //是否显示上传按钮
         showRemove : false, //显示移除按钮
         showPreview : true, //是否显示预览
         showCaption: false,//是否显示标题
         browseClass: "btn btn-primary", //按钮样式     
         dropZoneEnabled: false,//是否显示拖拽区域
         maxFileCount: 1, //表示允许同时上传的最大文件个数
         //enctype: 'multipart/form-data',
         validateInitialCount:true,
         previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
         msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
     }).on("filebatchselected", function(event, files) {
     }).on("fileuploaded", function(event, data) {
	     if(data.response){
	    	 if(data.response.status==200){
	    		 bootbox.alert(data.response.msg);  
	    	 }else{
	    		 bootbox.alert(data.response.msg); 
	    	 }
	     }else{
	    	 bootbox.alert('导入数据失败');  
	     }  
     }).on('fileerror', function(event, data, msg) {
    	 bootbox.alert('导入数据失败'); 
     });
}

//资源上架 下架
function userlock(status) {
	updatebyfiled("userstatus", status);
}

//书记发布 上架/下架 更改图书状态
function updatebyfiled(fieldname, fieldval) {
	var selRes = $("#resourceTb").bootstrapTable('getSelections');
	if (selRes.length < 1) {
		bootbox.alert('请至少选择一条数据', function() {
			return;
		});
		return;
	} else {
		var resIds = "";
		var isfrist = false;
		for (var i = 0; i < selRes.length; i++) {
			if (isfrist) {
				resIds += ",";
			}
			resIds += selRes[i].seqid;
			isfrist = true;
		}
		bootbox.confirm("确认操作?",function(result) {
							if (result) {
								var databaseName = $("#dbSelect").val();
								$.ajax({
											type : "POST",
											url : "../personal/updateResByFiledBatch",
											data : {
												'resIds' : resIds,
												'databaseName' : databaseName,
												'mouldId' : mouldId,
												"fieldval" : fieldval,
												"fieldname" : fieldname
											},
											success : function(data) {
												if (data.status == 200) {
													bootbox.alert(data.msg);
													$("#resourceTb").bootstrapTable("refresh");
												} else {
													bootbox.alert(data.msg);
												}
											}
										});
							}
						});
	}
	return true;

}
