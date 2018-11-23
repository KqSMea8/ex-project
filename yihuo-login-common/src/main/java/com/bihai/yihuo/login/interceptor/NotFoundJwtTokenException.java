package com.bihai.yihuo.login.interceptor;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 下午12:39
 */
public class NotFoundJwtTokenException extends RuntimeException {
    public NotFoundJwtTokenException(String msg) {
        super(msg);
    }

    public NotFoundJwtTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
