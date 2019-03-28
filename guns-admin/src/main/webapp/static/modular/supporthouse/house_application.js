var fileNum;

$(function () {
    //初始化数据
    fileNum = 1;
});

function createProject(num){
    var type = 0;
    var typeStr = "";
    var isCivilServant = 0;
    if(num == 1){
        type = 312;
        typeStr="公租房申请";
    }else if(num == 2 || num == 4){
        type = 313;
        typeStr="限价房申请";
        //若为公务员
        if(num == 2){
            isCivilServant = 1;
        }
    }else if(num == 3){
        type = 315;
        typeStr="住房补贴申请";
    }else if(num == 5){
        type = 321;
        typeStr = '腾退申请';
    }
    if(num == 5){
        var param = {
            "iOpTypeNum":82,
            "iOpPartNum":type
        };
    }else{
        var param = {
            "iOpTypeNum":81,
            "iOpPartNum":type,
            "isCivilServant":isCivilServant
        };
    }
    var ajax = new $ax(Feng.ctxPath + "/house/add", function (result) {
        if(result.code == '502' || result.code == '505'){
            Feng.info(result.msg);
            return;
        }

        var index = layer.open({
            type: 2,
            title: typeStr+"(每页都请记得保存)",
            area: ['1200px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/house/detail?page='+result.data.sPage+'&OpTypeNum=' + result.data.iOpTypeNum +'&RecYear=' + result.data.iYear +'&RecNum=' + result.data.iMaxRecNum +'&num=' + num
        });
    }, function (data) {
        Feng.info("系统异常！");
    });
    ajax.set(param);
    ajax.start();
}

/**
 * 保存附件
 */
function saveRecOpinions() {
    var data = structureDateType();
    $.ajax({
        url:'/house/save_file_yu',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        async:false,
        dataType:'json',
        data:JSON.stringify(data),
        success: function (response) {
            if(response == 'ERROR'){
                Feng.info("保存失败！");
            }else{
                Feng.success("保存成功!");
            }
        }
    })
}

//文件上传页面
function openUploadPage(OPTYPENUM,RECYEAR,RECNUM,RECMATNUM) {
    var index = layer.open({
        type: 2,
        title: '材料上传',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/house/open_upload?iOptypenum=' + OPTYPENUM +'&iRecyear=' + RECYEAR +'&iRecnum=' + RECNUM + "&iRecMatnum=" + RECMATNUM
    });

}

//图片显示页面
function openImagePage (OPTYPENUM,RECYEAR,RECNUM,RECMATNUM) {
    var index = layer.open({
        type: 2,
        title: '材料查看',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/house/show_image?iOptypenum=' + OPTYPENUM +'&iRecyear=' + RECYEAR +'&iRecnum=' + RECNUM + "&iRecMatnum=" + RECMATNUM
    });
    layer.full(index);
}

//关闭按钮
function closeLayer() {
    layer.confirm('确定关闭当前窗口？请确保填写数据已保存成功', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = parent.layer.getFrameIndex(window.name);

        parent.layer.close(index);
    }, function(){

    });

}

/**
 * 增加上传文件栏
 */
function addUploadTable(OPTYPENUM,RECYEAR,RECNUM) {
    var materialStr = "";
    if(materialListJson != ''){
        for(var material in materialListJson){
            materialStr += '<option value="'+materialListJson[material].code+'">'+materialListJson[material].value+'</option>'
        }
    }
    fileNum++;
    var trId = "fileTr" + fileNum;
    var str = '<tr id="'+trId+'">' +
        '           <input type="hidden" name="optypenum[]" value="'+OPTYPENUM+'">\n' +
        '           <input type="hidden" name="recyear[]" value="'+RECYEAR+'">\n' +
        '           <input type="hidden" name="recnum[]" value="'+RECNUM+'">' +
        '           <input type="hidden" name="recmatnum[]" value="'+fileNum+'">\n' +
        '           <td>' +
        '               <input type="checkbox">' +
        '           </td>' +
        '           <td><input class="tableInputClass" type="text" name="matname[]"></td>' +
        '           <td>' +
        '           <select class="form-control" name="mattypecode[]">' +
        materialStr+
        '           </select>' +
        '           </td>' +
        '           <td>' +
        '               <input class="tableInputClass" type="text" value="1" name="matcount[]">' +
        '           </td>' +
        '           <td>' +
        '               <input class="tableInputClass" type="text" value="1" name="matpage[]">' +
        '           </td>' +
        '           <td><input class="tableInputClass" type="text" name="matkindcode[]"></td>' +
        '           <td>' +
        '                <button type="button" onclick="openUploadPage('+OPTYPENUM+','+RECYEAR+','+RECNUM+','+fileNum+')">上传</button>' +
        '                <button type="button" onclick="openImagePage('+OPTYPENUM+','+RECYEAR+','+RECNUM+','+fileNum+')">查看</button>' +
        '                <button type="button" onclick="deleteUpload('+trId+')">删除</button>' +
        '           </td>' +
        '      </tr>';
    $("#uploadTbody").append(str);
}

/**
 * 删除多余的tr
 */
function deleteUpload(trId) {
    $(trId).remove();
}

/**
 * 构建新的数据类型
 */
function structureDateType() {
    var data = $("#uploadTbodyForm").serializeJSON();
    var newData = [];
    //构建数据格式
    for(var i = 0;i<data.matname.length;i++){
        var file_yuVo = {};
        file_yuVo.optypenum = data.optypenum[i];
        file_yuVo.recyear = data.recyear[i];
        file_yuVo.recnum = data.recnum[i];
        file_yuVo.recmatnum = data.recmatnum[i];
        file_yuVo.matname = data.matname[i];
        file_yuVo.mattypecode = data.mattypecode[i];
        file_yuVo.matcount = data.matcount[i];
        file_yuVo.matpage = data.matpage[i];
        file_yuVo.matkindcode = data.matkindcode[i];
        newData.push(file_yuVo);
    }
    return newData;
}
