//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.errorcode;

import com.yihuo.api.i18n.LocaleUtils;
import java.util.Arrays;

public enum SystemErrorCode implements ErrorCode {
    SUCCESS(0, "error.code.sys.success"),
    SYSTEM_ERROR(1, "error.code.sys.error"),
    UNKNOWN_ERROR(-1, "error.code.sys.unknow"),
    API_DISABLED(-2, "error.code.sys.apidisabled"),
    HTTP_MESSAGE_NOT_READABLE(900, "error.code.sys.900"),
    DATA_VALIDATION_FAILURE(901, "error.code.sys.901"),
    DATA_BIND_VALIDATION_FAILURE(902, "error.code.sys.902"),
    SQL_EXECUTE_FAILURE(903, "error.code.sys.903"),
    METHOD_ARGUMENT_NOT_VALID(904, "error.code.sys.902");

    private final int code;
    private final String message;

    private SystemErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SystemErrorCode valueOf(int code) {
        return (SystemErrorCode)Arrays.stream(values()).filter((x) -> {
            return x.getCode() == code;
        }).findFirst().orElse(UNKNOWN_ERROR);
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(this.message);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
