package com.example.demo.common.model;

/**
 * @antor ChenYong
 * @Date 16:12 2018/11/1
 */
public class Message {

    private int code;
    private String msg;
    private Object data;

    public static Message suc() {
        return new Message(1, "操作成功", null);
    }

    public static Message sucMsg(String msg) {
        return new Message(1, msg, null);
    }

    public static Message err() {
        return new Message(0, "操作失败", null);
    }

    public static Message errMsg(String msg) {
        return new Message(0, msg, null);
    }

    public static Message ret(Object data, String msg) {
        return new Message(1, msg, data);
    }

    Message(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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
