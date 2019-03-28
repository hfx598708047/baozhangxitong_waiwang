package com.stylefeng.guns.core.common;

import com.alibaba.fastjson.JSON;


/**
 * api接口返回的结果类
 * Created by lxx on 2017/09/08.
 */
public class Result {
    private int code;  //接口返回代码，不同代码表示正常或异常
    private String msg;  //调用结果返回的消息，可将异常信息返回给调用者
    private Object data; //业务数据对象，一般是json对象，前台获取业务数据

    public Result() {

    }

    public static Result NEW() {
        return new Result();
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(String content) {
        return new Result(200, content, null);
    }

    public static Result success(String content, Object data) {
        return new Result(200, content, data);
    }

    public static Result error(int code, String content) {
        return new Result(code, content, null);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
