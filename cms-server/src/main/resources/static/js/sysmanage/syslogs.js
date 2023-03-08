$(window).on('load', function() {
	loadSyslogs();

	//加载日志类型选项
	var logTypeParam = {'isdictype':0,'dictype':'logtype'};
	loadDicSelectAll("#logTypeSel",logTypeParam,0);

    $("#startTime").click(function () {
		WdatePicker({el: this, dateFmt: 'yyyy-MM-dd', lang: "zh-cn"});
	});
    $("#endTime").click(function () {
    	WdatePicker({el: this, dateFmt: 'yyyy-MM-dd', lang: "zh-cn"});
    });
});

/**
 * 加载系统日志
 */
function loadSyslogs(){
	oTable = $("#syslogTb").bootstrapTable({
    	method: "post",  //使用post请求到服务器获取数据  
    	contentType : "application/x-www-form-urlencoded",
        url: "syslogs/list",
        queryParams: function (params) {
        	var logType = $("#logTypeSel").val();
        	var nameInput = $("#userName").val();
        	var startTime = $("#startTime").val();
        	var endTime = $("#endTime").val();
    	    return {"order[0].column":params.sort,"order[0].dir":params.order,"start": params.offset,"length": params.limit,"logType":logType,"userName":nameInput,"startTime":startTime,"endTime":endTime};
		},
		responseHandler: function(res) {
            return {
                "total": res.data.total,//总页数
                "rows": res.data.rows   //数据
             };
        },
        striped: true,  //表格显示条纹  
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        sortable: true,       //是否启用排序    
        locale:'zh-CN',
        clickToSelect: true,    //是否启用点击选中行
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "server", //表示服务端请求  
        columns: [{   
	    	field:'logTypeName',
        	title: '日志类型',
	    },
	    {   
	    	field:"logdetail",
        	title: '日志内容',
	    },
	    {   
	    	field:"creatorName",
        	title: '创建者',
        	formatter : function (value, row, index) {
        		if(value == null){
        			return "";
        		}else{
        			return value;
        		}
       		}
	    },
	    {   
	    	field:"createtime",
        	title: '创建时间',
        	formatter: function ( data, type, full ) {
        		if(data == null){
        			return '';
        		}else{
		    		var date = new Date(data);
		    		return date.Format("yyyy-MM-dd hh:mm:ss");
        		}
       		},
	    }],
        toolbar:"#toolbar"
    });
}

function searchSysLogs(){
	oTable.bootstrapTable("refresh");
}