package com.yihuo.bihai.newex.security.jwt.token;

import java.util.Date;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:52
 */
public class JwtTokenTimeProvider {
    public JwtTokenTimeProvider() {
    }

    public static Date now() {
        return new Date();
    }
}
