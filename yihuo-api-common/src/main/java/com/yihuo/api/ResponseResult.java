//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    public static final int SUCCESS_RESPONSE_CODE = 0;
    private int code;
    private String msg;
    private T data;

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult() {
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public boolean checkSuccess() {
        return this.code == 0;
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult res = new ResponseResult();
        res.data = data;
        res.code = 0;
        return res;
    }

    public static <T> ResponseResult<T> error(int errorCode, String errorMsg) {
        ResponseResult res = new ResponseResult();
        res.code = errorCode;
        res.msg = errorMsg;
        return res;
    }

    public static <T> ResponseResult<T> errorWithData(int errorCode, String errorMsg, T data) {
        ResponseResult res = new ResponseResult();
        res.code = errorCode;
        res.msg = errorMsg;
        res.data = data;
        return res;
    }
}
