package com.yihuo.bihai.newex.security.jwt.exception;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:42
 */
public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String msg) {
        super(msg);
    }

    public JwtTokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }
}
