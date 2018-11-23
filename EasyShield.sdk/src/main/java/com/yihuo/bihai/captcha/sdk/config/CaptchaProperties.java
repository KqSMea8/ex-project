package com.yihuo.bihai.captcha.sdk.config;

public class CaptchaProperties {
    private String captchaId = "1b4befe445394b2ea3741f95b8b534b1";
    private String secretId = "48f5e2f7834edb5923d34ec1407ae721";
    private String secretKey = "7cc358edd2e3795c0676668d4d00589c";

    public CaptchaProperties() {
    }

    public String getCaptchaId() {
        return this.captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getSecretId() {
        return this.secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
