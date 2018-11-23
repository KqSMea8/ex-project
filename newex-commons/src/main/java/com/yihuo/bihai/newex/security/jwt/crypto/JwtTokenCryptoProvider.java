package com.yihuo.bihai.newex.security.jwt.crypto;
/**
 *
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:46
 */
public interface JwtTokenCryptoProvider {
    String encrypt(String var1, String var2);

    String decrypt(String var1, String var2);
}
