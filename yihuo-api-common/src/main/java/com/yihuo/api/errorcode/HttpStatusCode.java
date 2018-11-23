//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.errorcode;

import com.yihuo.api.i18n.LocaleUtils;
import java.util.Arrays;

public enum HttpStatusCode implements ErrorCode {
    BAD_REQUEST(400, "error.code.httpstatus.400"),
    UNAUTHORIZED(401, "error.code.httpstatus.401"),
    FORBIDDEN(403, "error.code.httpstatus.403"),
    NOT_FOUND(404, "error.code.httpstatus.404"),
    METHOD_NOT_ALLOWED(405, "error.code.httpstatus.405"),
    UNSUPPORTED_MEDIA_TYPE(415, "error.code.httpstatus.415"),
    INTERNAL_SERVER_ERROR(500, "error.code.httpstatus.500");

    private final int code;
    private final String message;

    private HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpStatusCode valueOf(int code) {
        return (HttpStatusCode)Arrays.stream(values()).filter((x) -> {
            return x.getCode() == code;
        }).findFirst().orElse(INTERNAL_SERVER_ERROR);
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
