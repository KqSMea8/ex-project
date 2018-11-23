package com.yihuo.bihai.newex.security.jwt.model;
/**
 *
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:47
 */
public final class JwtConfig {
    private String requestHeaderName = "Authorization";
    private String issuer = "yihuo";
    private String secret;
    private String cryptoKey;
    private long expiration;

    public JwtConfig() {
    }

    public String getRequestHeaderName() {
        return this.requestHeaderName;
    }

    public void setRequestHeaderName(String requestHeaderName) {
        this.requestHeaderName = requestHeaderName;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCryptoKey() {
        return this.cryptoKey;
    }

    public void setCryptoKey(String cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    public long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
