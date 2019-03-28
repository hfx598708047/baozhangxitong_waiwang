var phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;//手机号正则
var count = 60; //间隔函数，1秒执行
var InterValObj1; //timer变量，控制时间
var curCount1 = 0;//当前剩余秒数
$(function () {
    //获取验证码的计时器
    var value = sessionStorage.getItem("curCount1");
    curCount1 = value;
    //判断是否为0，不为0则把获取验证码按钮设置不可编辑按钮
    if(curCount1 != 0){
        $("#btnSendCode1").attr("disabled", "true");
    }
    //启动计时器
    InterValObj1 = window.setInterval(SetRemainTime1, 1000); //启动计时器，1秒执行一次


    //获取验证码时效的计时器
    var verificationCode = sessionStorage.getItem("verificationCodeCount");
    verificationCodeCount = verificationCode;
    //启动计时器
    verificationCodeObj1 = window.setInterval(verificationCodeTime, 1000); //启动计时器，1秒执行一次

});
/*第一*/
function sendMessage1(phoneInput) {
    curCount1 = count;
    var phone = $("#"+phoneInput).val();
    if (!phoneReg.test(phone)) {
        Feng.error("请输入有效的手机号码!");
        return false;
    }
    //设置button效果，开始计时
    $("#btnSendCode1").attr("disabled", "true");
    $("#btnSendCode1").val( + curCount1 + "秒再获取");
    InterValObj1 = window.setInterval(SetRemainTime1, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
    var data = {
        phone:phone
    };
    $.ajax({
        url:'/verificationCode',
        type:'POST',
        async: false,
        data:data,
        success: function (response) {
            if(response == "error"){
                Feng.error("验证码发送失败!");
            }else{
                Feng.success("验证码发送成功!");
                //把验证码写入本地存储
                sessionStorage.setItem("verificationCode", response);
                //把计数器写入本地存储，半小时
                sessionStorage.setItem("verificationCodeCount", 1800);
            }
        }
    })
}
//获取验证码计时器方法
function SetRemainTime1() {
    if (curCount1 <= 0 ) {
        window.clearInterval(InterValObj1);//停止计时器
        $("#btnSendCode1").removeAttr("disabled");//启用按钮
        $("#btnSendCode1").val("获取验证码");
    }
    else {
        curCount1--;
        //把计数器写入本地存储
        sessionStorage.setItem("curCount1", curCount1);
        $("#btnSendCode1").val( + curCount1 + "秒再获取");
    }
}

//计时器变量
var verificationCodeCount = 0;
var verificationCodeObj1; //timer变量，控制时间
//验证码时效性计时器
function verificationCodeTime() {
    if (verificationCodeCount <= 0) {
        window.clearInterval(verificationCodeObj1);//停止计时器
        //删除验证码本地存储
        sessionStorage.removeItem("verificationCode");
    } else {
        verificationCodeCount--;
        //把计数器写入本地存储
        sessionStorage.setItem("verificationCodeCount", verificationCodeCount);
    }
}