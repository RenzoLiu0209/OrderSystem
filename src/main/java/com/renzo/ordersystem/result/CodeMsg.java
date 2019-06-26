package com.renzo.ordersystem.result;

public enum CodeMsg {
    SUCCESS(200, "Successful!"),
    FAILURE(500, "Server error!");

    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
