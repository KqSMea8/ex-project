package com.bihai.yihuo.login.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "yihuo.jwt"
)
public class Jwt {
    private String requestHeaderName = "Authorization";
    private String issuer = "yihuo";
    private String secret;
    private String cryptoKey;
    private String excludePathPatterns = "";
    private long expiration = 604800L;
    private String cryptoAlgorithm = "AES";

    public Jwt() {
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

    public long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
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

    public String getExcludePathPatterns() {
        return this.excludePathPatterns;
    }

    public void setExcludePathPatterns(String excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    public String getCryptoAlgorithm() {
        return this.cryptoAlgorithm;
    }

    public void setCryptoAlgorithm(String cryptoAlgorithm) {
        this.cryptoAlgorithm = cryptoAlgorithm;
    }
}
