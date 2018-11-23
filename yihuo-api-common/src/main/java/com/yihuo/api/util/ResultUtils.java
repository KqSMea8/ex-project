//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.util;

import com.yihuo.api.ResponseResult;
import com.yihuo.api.errorcode.ErrorCode;

public class ResultUtils {
    public ResultUtils() {
    }

    public static ResponseResult success() {
        return success((Object)null);
    }

    public static <T> ResponseResult success(T data) {
        return build(0, "", data);
    }

    public static ResponseResult failure(String msg) {
        return failure(-1, msg);
    }

    public static ResponseResult failure(ErrorCode errorCode) {
        return failure(errorCode, (Object)null);
    }

    public static <T> ResponseResult failure(ErrorCode errorCode, T data) {
        return build(errorCode.getCode(), errorCode.getMessage(), data);
    }

    public static ResponseResult failure(int code, String msg) {
        return build(code, msg, (Object)null);
    }

    public static <T> ResponseResult build(int code, String msg, T data) {
        return new ResponseResult(code, msg, data);
    }
}
