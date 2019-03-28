var fileNum;

$(function () {
    //初始化数据
    fileNum = 1;
    var now = new Date();
    laydate.render({
        elem: '#promise_time',
        value:now
    });
});

/**
 * 保存调查表
 */
function saveFamilySurveyFunction(num) {
    if(num == 1){
        var name = $("input[name=applicantName]").val();
    }else{
        var name = $("#applicantName").val();
    }
    if(name == null || name == "" || name == undefined){
        Feng.info("请填写姓名！");
        return ;
    }

    //手机号判断正则表达式
    var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(num == 1){
        var phone = $("input[name=applicantPhone]").val();
    }else{
        var phone = $("#applicantPhone").val();
    }
    if (!myreg.test(phone)) {
        Feng.info("联系电话格式错误！");
        return ;
    }

    if(num == 1){
        var idCard = $("input[name=applicantCard]").val();
    }else{
        var idCard = $("#applicantCard").val();
    }
    if(idCard == null || idCard == "" || idCard == undefined){
        Feng.info("请填写身份证号！");
        return ;
    }

    if(num == 1){
        var data = $("#projectInfoForm").serializeJSON();
    }else{
        var data = $("#projectInfoCivilForm").serializeJSON();
    }
    data = {json:JSON.stringify(data)};
    $.ajax({
        url:'/house/save_family',
        type:'POST',
        //contentType: 'application/json; charset=UTF-8',
        async:false,
        //dataType:'json',
        data:data,
        success: function (response) {
            var formData=new FormData();
            formData.append('avatar', $("#avatarSlect")[0].files[0]);  /*获取上传的图片对象*/
            formData.append('OpTypeNum', $("#OpTypeNum").val());
            formData.append('RecYear', $("#RecYear").val());
            formData.append('RecNum', $("#RecNum").val());
            formData.append('judge', 1);
            if(typeof ($("#avatarSlect")[0].files[0])!= 'undefined'){

                $.ajax({
                    url: '/house/save_picture',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (args) {
                        location.reload();
                        Feng.success("保存成功!");
                        /* console.log(args);  /!*服务器端的图片地址*!/
                         $("#avatarPreview").attr('src','/'+args);  /!*预览图片*!/
                         $("#avatar").val('/'+args);  /!*将服务端的图片url赋值给form表单的隐藏input标签*!/*/
                    }
                });
            }else {
                location.reload();
                Feng.success("保存成功!");
            }
        }
    })

}

/**
 * 保存申请理由
 */
function saveApplicantReasonFunction(formId) {
    var data = $("#"+formId).serializeJSON();
    data = {json:JSON.stringify(data)};
    $.ajax({
        url:'/house/save_applicant_reason',
        type:'POST',
        //contentType: 'application/json; charset=UTF-8',
        async:false,
        //dataType:'json',
        data:data,
        success: function (response) {
            Feng.success("保存成功!");
        }
    })
}

/**
 * 保存共同申请人
 */
function saveJointApplicantFunction(formId,num) {
    var data = structureDate(formId,num);
    $.ajax({
        url:'/house/save_joint_applicant',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        async:false,
        dataType:'json',
        data:JSON.stringify(data),
        success: function (response) {
            var y = 1;
            for(var i =0;i<data.length;i++){
                var formData=new FormData();
                formData.set('OpTypeNum', $("#OpTypeNum").val());
                formData.set('RecYear', $("#RecYear").val());
                formData.set('RecNum', $("#RecNum").val());
                formData.set('judge', 0);
                formData.set('FamilyID', data[i].jointApplicantId);
                for(var z = y;z<jointApplicantListSize+2;z++){
                    var inputId = z+"Input";
                    if(z == 1 && (data[i].jointApplicantRelation == '2' || data[i].jointApplicantRelation == '9')){
                        inputId = 'spouseInput';
                    }
                    var fileInput = $("#"+inputId).length;
                    if( fileInput > 0){
                        y = z+1;
                        if(typeof ($("#"+inputId)[0].files[0])!= 'undefined'){
                            formData.set('avatar', $("#"+inputId)[0].files[0]);  /*获取上传的图片对象*/
                            $.ajax({
                                url: '/house/save_picture',
                                type: 'POST',
                                data: formData,
                                contentType: false,
                                processData: false,
                                success: function (args) {
                                }
                            });
                        }
                        break;
                    }
                }
                layer.msg('保存中');
                setTimeout("location.reload()",3000);
                setTimeout("Feng.success('保存成功!');",3000);
            }
        }
    })
}

/**
 * 构建新的数据类型
 */
function structureDate(formId,num) {
    var data = $("#"+formId).serializeJSON();
    var newData = [];
    //构建数据格式
    for(var i = 0;i<data.jointApplicantName.length;i++){
        var jointApplicant = {};
        jointApplicant.OPTYPENUM = data.OPTYPENUM[i];
        jointApplicant.RECYEAR = data.RECYEAR[i];
        jointApplicant.RECNUM = data.RECNUM[i];
        jointApplicant.jointApplicantId = (i+1);
        jointApplicant.jointApplicantMarry = data.jointApplicantMarry[i];
        jointApplicant.jointApplicantRelation = data.jointApplicantRelation[i];
        jointApplicant.jointApplicantName = data.jointApplicantName[i];
        jointApplicant.jointApplicantSex = data.jointApplicantSex[i];
        jointApplicant.jointApplicantAge = data.jointApplicantAge[i];
        jointApplicant.jointApplicantCard = data.jointApplicantCard[i];
        jointApplicant.jointApplicantWork = data.jointApplicantWork[i];
        jointApplicant.jointApplicantResidence = data.jointApplicantResidence[i];
        jointApplicant.jointApplicantPhone = data.jointApplicantPhone[i];
        jointApplicant.jointApplicantAddress = data.jointApplicantAddress[i];
        if(num == 1){
            jointApplicant.jointApplicantIncome = data.jointApplicantIncome[i];
            jointApplicant.jointApplicantResidenceTime = data.jointApplicantResidenceTime[i];
        }
        newData.push(jointApplicant);
    }
    return newData;
}

function judgeNull(date) {
    if(date == null || date == undefined){
        return "";
    }else{
        return date;
    }
}

/**
 * 删除
 */
function deleteJointApplicant(firstId) {
    $("#"+firstId).remove();
}

/**
 * 增加共同申请人基本情况
 */
function addJointTable(OPTYPENUM,RECYEAR,RECNUM,tableId,num) {
    var titleStr = "";
    if(num == 1){
        titleStr = '共同申请人基本情况';
    }else {
        titleStr = '其他家庭成员基本情况';
    }

    var familyStr = "";
    if(familyType != ''){
        for(var family in familyType){
            familyStr += '<option value="'+familyType[family].code+'">'+familyType[family].value+'</option>'
        }
    }
    var dateTime = new Date();
    var tbodyId = "jointTbodyId" + dateTime.getTime();
    jointApplicantListSize++;
    var str = '<tbody id="'+tbodyId+'">' +
        '                            <input type="hidden" name="OPTYPENUM[]" value="'+OPTYPENUM+'">' +
        '                            <input type="hidden" name="RECYEAR[]" value="'+RECYEAR+'">' +
        '                            <input type="hidden" name="RECNUM[]" value="'+RECNUM+'">'+
        '                                        <tr class="tableTrClass">' +
        '                                            <td colspan="13">'+titleStr +
        '                                                <button class="btn btn-danger" type="button" style="font-size: 15px;margin: 0px;float: right;" onclick="deleteUpload('+tbodyId+')">-</button>' +
        '                                            </td>' +
        '                                        </tr>' +
        '                                        <tr class="tableTrClass">' +
        '                                            <td colspan="3">姓名</td>' +
        '                                            <td colspan="3"><input type="text" name="jointApplicantName[]" class="tableInputClass"/></td>' +
        '                                            <td colspan="2">性别</td>' +
        '                                            <td colspan="1">' +
        '                                               <select class="form-control" name="jointApplicantSex[]">' +
        '                                                    <option value="1">男</option>' +
        '                                                   <option value="2">女</option>' +
        '                                               </select>' +
        '                                            </td>' +
        '                                            <td colspan="1">婚否</td>' +
        '                                            <td colspan="1">' +
        '                                               <select class="form-control" name="jointApplicantMarry[]">' +
        '                                                   <option value="0">未婚</option>' +
        '                                                   <option value="1">已婚</option>' +
        '                                            </select>' +
        '                                            </td>' +
        '                                            <td colspan="2" rowspan="4">' +
        '                                            <input id="'+(jointApplicantListSize+1)+'Input" data-num="'+(jointApplicantListSize+1)+'" type="file" style="position: absolute;float: left; z-index: 10; opacity: 0;width: 100px; height: 100px;margin-left: 40px;" accept="image/png, image/jpeg, image/gif, image/jpg">' +
        '                                            <img id="'+(jointApplicantListSize+1)+'Img" src="'+ctxPath+'/static/img/sample.png" title="点击更换图片" style="z-index: 9; float: left; width: 100px; height: 100px;margin-left: 40px;">' +
        '                                            </td>' +
        '                                        </tr>' +
        '                                        <tr class="tableTrClass">' +
        '                                            <td colspan="3">年龄</td>' +
        '                                            <td colspan="3"><input type="number" name="jointApplicantAge[]" class="tableInputClass"/></td>';
    if(num == 1){
        str +=
            '                                            <td colspan="2">联系电话</td>' +
            '                                            <td colspan="3"><input type="number" name="jointApplicantPhone[]" class="tableInputClass"/></td>';
    }else{
        str +=
            '                                            <td colspan="2">与申请人关系</td>' +
            '                                            <td colspan="3">' +
            '<select class="form-control"name="jointApplicantRelation[]" >' +
            familyStr+
            '</select></td>';
    }
    str +=
        '                                        </tr>' +
        '                                        <tr class="tableTrClass">' +
        '                                            <td colspan="3">身份证号</td>' +
        '                                            <td colspan="8"><input type="text" name="jointApplicantCard[]" class="tableInputClass"/></td>' +
        '                                        </tr>' +
        '                                        <tr class="tableTrClass">' +
        '                                            <td colspan="3">职业或工作单位</td>' +
        '                                            <td colspan="8"><input type="text" name="jointApplicantWork[]" class="tableInputClass"/></td>' +
        '                                        </tr>';
    if(num == 1){
        str +=
            '                                        <tr class="tableTrClass">' +
            '                                            <td colspan="3">户口所在地</td>' +
            '                                            <td colspan="3"><input type="text" name="jointApplicantResidence[]" class="tableInputClass"/></td>' +
            '                                            <td colspan="3">现住址</td>' +
            '                                            <td colspan="4"><input type="text" name="jointApplicantAddress[]" class="tableInputClass"/></td>' +
            '                                        </tr>' +
            '                                        <tr class="tableTrClass">' +
            '                                            <td colspan="3">与申请人关系</td>' +
            '                                            <td colspan="3">' +
            '<select class="form-control" name="jointApplicantRelation[]" >' +
            familyStr+
            '</select></td>' +
            '                                            <td colspan="3">本人收入情况</td>' +
            '                                            <td colspan="4"><input type="text" name="jointApplicantIncome[]" class="tableInputClass"/></td>' +
            '                                        </tr>';
    }
    str +=
        '                                    </tbody>';
    $("#"+tableId).append(str);
    //添加监听事件
    $("#"+(jointApplicantListSize+1)+"Input").change(function () {
        var num = $(this).data("num");
        var obj=$("#"+num+"Input")[0].files[0];
        var fr=new FileReader();
        fr.onload=function () {
            $("#"+num+"Img").attr('src',this.result);
        };
        fr.readAsDataURL(obj);
    });
}
