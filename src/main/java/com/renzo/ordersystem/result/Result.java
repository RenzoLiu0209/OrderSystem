package com.renzo.ordersystem.result;

public class Result<T> {
    private int code;
    private String msg;
    T data;

    public static<T> Result<T> success(CodeMsg cm, T data) {
        return new Result<>(cm.getCode(), cm.getMsg(), data);
    }

    public static<T> Result<T> success(CodeMsg cm) {
        return new Result<>(cm.getCode(), cm.getMsg());
    }

    public static<T> Result<T> error(CodeMsg cm) {
        return new Result<>(cm.getCode(), cm.getMsg());
    }


    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
