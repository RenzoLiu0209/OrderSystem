package com.renzo.ordersystem.result;

public enum CodeMsg {

    // General
    SUCCESS(200, "Success"),
    FAILURE(500, "Server Error"),
    BIND_ERROR(520, "Parameters exception"),
    REQUEST_ILLEGAL(530, "Illegal request"),
    ACCESS_LIMIT_REACH (550, "Limited Access"),
    MIAOSHA_FAIL (540, "Miaosha fail"),

    // Login
    MOBILE_EMPTY(501, "Phone number cannot be empty"),
    MOBILE_INVALID(502, "Phone number is invalid"),
    PASSWORD_EMPTY(503, "Password cannot be empty"),
    USER_NULL(504, "Input user is null"),
    USER_NOT_EXIST(505, "User does not exist"),
    PASSWORD_CORRECT(506, "Password is right"),
    PASSWORD_WRONG(507, "Password is wrong"),
    LOGIN_SUCCESS(508, "Login success"),
    LOGIN_FAILURE(509, "Username or password is wrong"),



    // Order-placing
    SOLID_OUT(580, "Goods have been sold out"),
    BEEN_BOUGHT(581, "This good has been bought by current user"),
    ORDER_PLACED(582, "Order has been placed"),
    ORDER_NOT_EXIST(583, "Order doesn't exist");

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

//    public CodeMsg fillArgs(String msg) {
//        return new CodeMsg(this.code, this.msg + ":" + msg);
//    }
}
