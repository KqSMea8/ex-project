package com.yihuo.bihai.newex.security.jwt.crypto;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:53
 */
public class AesJwtTokenCryptoProvider implements JwtTokenCryptoProvider {

    @Override
    public String encrypt(String content, String key) {
        return AESUtil.encrypt(content, key);
    }

    @Override
    public String decrypt(String content, String key) {
        return AESUtil.decrypt(content, key);
    }
}
