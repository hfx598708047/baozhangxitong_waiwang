/**
 * 保障房项目初始化
 */
var houseProject = {
    id: "HouseProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    url:"",
    secondLayerIndex:-1
};

/**
 * 初始化表格的列
 */
houseProject.initColumn = function () {
    return [
        {field: 'selectItem', radio: false ,visible: false},
        {title: '序号', field: 'IROW', visible: true, align: 'center', valign: 'middle'},
       /* {title: '申请日期', field: '收件日期', align: 'center', valign: 'middle'},
        {title: '提交日期', field: '提交日期', align: 'center', valign: 'middle',visible: false},*/
        {title: '登记编号', field: '登记编号', align: 'center', valign: 'middle'},
       /* {title: '申请类别', field: '登记类别', visible: true, align: 'center', valign: 'middle'},*/
        {title: '申请人', field: '申请人', align: 'center', valign: 'middle'},
        {title: '身份证件号', field: '身份证件号', align: 'center', valign: 'middle'},
        {title: '案卷状态', field: '案卷状态', align: 'center', valign: 'middle'},

        {title: 'OPTYPENUM', field: 'OPTYPENUM', align: 'center', valign: 'middle',visible: false},
        {title: 'RECYEAR', field: 'RECYEAR', align: 'center', valign: 'middle',visible: false},
        {title: 'RECNUM', field: 'RECNUM', align: 'center', valign: 'middle',visible: false},

        {title: '操作',  align: 'center', valign: 'middle',
            formatter:function(value,row,index){
                return houseProject.judgeChange(row);
            }

        }
    ];
};

houseProject.judgeChange = function (row) {
    var buttonStr = "";
    if(row.案卷状态 == "待提交"){
        buttonStr +='<a onclick="houseProject.detail('+'\'' +row.登记编号+'\',\''+ row.OPTYPENUM + '\',\'' +row.RECYEAR + '\',\''+ row.RECNUM +'\',\''+ row.RECSTATECODE +'\')">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;' +
                '<a onclick="houseProject.submitInfo('+'\'' + row.OPTYPENUM + '\',\'' +row.RECYEAR + '\',\''+ row.RECNUM +'\')">提交</a>';
    }else{
        buttonStr +='<a onclick="houseProject.nodeList('+'\'' + row.OPTYPENUM + '\',\'' +row.RECYEAR + '\',\''+ row.RECNUM +'\')">节点</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
        if(row.OPPARTNUM == 312 || row.OPPARTNUM == 311){
            buttonStr +='<a onclick="houseProject.payRent('+'\'' + row.OPTYPENUM + '\',\'' +row.RECYEAR + '\',\''+ row.RECNUM +'\')">交租</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
        }
       /* if(row.OPFLOWPHASENUM == 3){*/
            buttonStr +='<a onclick="houseProject.exportMaterial('+'\'' + row.OPTYPENUM + '\',\'' +row.RECYEAR + '\',\''+ row.RECNUM +'\',\''+ row.登记编号 +'\')">打印</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
        /*}*/
    }
    return buttonStr;
}

houseProject.exportMaterial = function (OPTYPENUM,RECYEAR,RECNUM,NUM) {
    window.open(Feng.ctxPath + '/house/export_pdf?OpTypeNum='+OPTYPENUM+'&RecYear=' + RECYEAR +'&RecNum=' + RECNUM + '&num=' + NUM);
    // var tmpUrl = Feng.ctxPath +'/static/js/plugins/pdfjs/web/viewer.html?file=' + encodeURIComponent(Feng.ctxPath +'/static/model/test.pdf');
    // window.open(tmpUrl);
    // window.location.href=Feng.ctxPath+"/excel/apply_approval";
}


/**
 * 提交
 * @param OPTYPENUM
 * @param RECYEAR
 * @param RECNUM
 */

houseProject.submitInfo = function (OPTYPENUM,RECYEAR,RECNUM) {
    layer.confirm('确定要提交吗？提交后无法再次修改！', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data = {
            iOptypenum:OPTYPENUM,
            iRecyear:RECYEAR,
            iRecnum:RECNUM
        };
        $.ajax({
            url:'/house/submitInfo',
            type:'POST',
            //contentType: 'application/json; charset=UTF-8',
            async:false,
            //dataType:'json',
            data:data,
            success: function (response) {
                layer.close(layer.index);
                Feng.success("提交成功!");
            }
        })
    }, function(){
    });

}

houseProject.query = function(){
    var IsEspacialElm = $("input[name=transfer_case]:checked");
    var IsEspacialVal = "";
    if(IsEspacialElm.get(0)){
        IsEspacialVal = IsEspacialElm.get(0).value;
    }
    var param = {
        "sOPTYPENUM":$("#business_first").val(),
        "sOPPARTNUM":$("#business_second").val(),
        "sRecNumGather":$("#condition").val(),
        "iBATCHNUM":$("#num").val(),
        "StartDate":$("#beginTime").val(),
        "EndDate":$("#endTime").val(),
        "iFilterMode":$("input[name=fileType]:checked").val(),
        "iRecUserNum":$("#recipients").val(),
        "IsEspacial":IsEspacialVal
    };
    houseProject.table.refresh({query: param});
}


houseProject.initSecondType = function(pNum){
    if(pNum == ''){
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/support/second_type", function (data) {
        $("#business_second").html('<option value="">请选择</option>');
        $.each(data,function (i,item) {
            $("#business_second").append('<option value="'+item.OPPARTNUM+'">'+item.OPPARTNAME+'</option>')
        })
    }, function (data) {

    });
    ajax.set({pNum:pNum});
    ajax.start();
}

houseProject.detail = function (sRecNumGather,OPTYPENUM,RECYEAR,RECNUM,RECSTATECODE) {
    if(RECSTATECODE == 1){
        Feng.info("已提交，不能修改！！");
        return ;
    }
    var data = {sRecNumGather:sRecNumGather,
                iOpTypeNum:OPTYPENUM,
                iRecYear:RECYEAR,
                iRecNum:RECNUM
                };
    $.ajax({
        url:Feng.ctxPath + '/house/get_page_info',
        data:data,
        type:"post",
        success:function (result) {
            if(result.code != '200'){
                Feng.info(result.msg);
                return;
            }
            if(result.data.sPage){
                var index = layer.open({
                    type: 2,
                    title: '编辑',
                    area: ['80%', '80%'], //宽高
                    fix: false, //不固定
                    maxmin: true,
                    content: Feng.ctxPath + '/house/detail?page='+result.data.sPage+'&OpTypeNum=' + result.data.iOpTypeNum +'&RecYear=' + result.data.iRecYear +'&RecNum=' + result.data.iRecNum + "&readonly=" + result.data.readonly
                });
                houseProject.layerIndex = index;
            }

        },
        error:function (result) {
            Feng.info("系统异常！");
        }
    })
}

/**
 * 新建项目
 */
houseProject.createProject = function(){
    if($("#business_second").val() == ""){
        Feng.info("请选择业务细项！");
        return;
    }
    var param = {
        "iOpTypeNum":$("#business_first").val(),
        "iOpPartNum":$("#business_second").val()
    };
    var ajax = new $ax(Feng.ctxPath + "/support/add", function (result) {
        if(result.code == '502' || result.code == '505'){
            Feng.info(result.msg);
            return;
        }

        var index = layer.open({
            type: 2,
            title: '详细',
            area: ['80%', '80%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/support/detail?page='+result.data.sPage+'&OpTypeNum=' + result.data.iOpTypeNum +'&RecYear=' + result.data.iYear +'&RecNum=' + result.data.iMaxRecNum
        });
        houseProject.layerIndex = index;

    }, function (data) {
        Feng.info("系统异常！");
    });
    ajax.set(param);
    ajax.start();
}


/**
 * 关闭此对话框
 */
houseProject.close = function () {
    parent.layer.close(window.parent.houseProject.layerIndex);
}
/**
 * 关闭二级对话框
 */
houseProject.closeSecond = function () {
    parent.layer.close(window.parent.houseProject.secondLayerIndex);
}



houseProject.nodeList = function (OPTYPENUM,RECYEAR,RECNUM) {
    var index = layer.open({
        type: 2,
        title: '办理节点列表',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/house/node_list?iOptypenum=' + OPTYPENUM +'&iRecyear=' + RECYEAR +'&iRecnum=' + RECNUM
    });
    houseProject.layerIndex = index;
}

//租金页面
houseProject.payRent = function (OPTYPENUM,RECYEAR,RECNUM) {
    var index = layer.open({
        type: 2,
        title: '支付方式',
        area: ['600px', '350px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/house/pay_rent'
    });
    houseProject.layerIndex = index;
}

$(function () {
    var now = new Date();
    var month = now.getMonth();
    var year = now.getFullYear();
    if(month < 4){
        year --;
        month = month + 12;
    }
    laydate.render({
        elem: '#beginTime',
        value:year + '-' + lay.digit(month-4 ) + '-' + lay.digit(now.getDate())
    });
    laydate.render({
        elem: '#endTime',
        value:now
    });

    $("#create_project").attr("disabled","disabled");

    //项目细项初始化
    $("#business_first").change(function () {
        houseProject.initSecondType($(this).children('option:selected').val());
        if($(this).val() != ''){
            $("#create_project").removeAttr("disabled");
        }else{
            $("#create_project").attr("disabled","disabled");
        }
    })

    //项目或申请参数
    houseProject.url = "/house/list?sRECSTATECODE=" + sRECSTATECODE;

    //初始化表格
    var data = {
        "StartDate":year + '-' + lay.digit(month-4 ) + '-' + lay.digit(now.getDate()),
        "EndDate":now.getFullYear() + '-' + lay.digit(now.getMonth()+1 ) + '-' + lay.digit(now.getDate())
    };
    var defaultColunms = houseProject.initColumn();
    var table = new BSTable(houseProject.id, houseProject.url, defaultColunms);
    table.setQueryParams(data);
    houseProject.table = table.init();

    //选择或反选触发事件
    $("#"+houseProject.id).on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table",function () {
        var selectedCount = $("#"+houseProject.id).bootstrapTable('getAllSelections').length;
        $("#selecteds").val(selectedCount);
    })
});
