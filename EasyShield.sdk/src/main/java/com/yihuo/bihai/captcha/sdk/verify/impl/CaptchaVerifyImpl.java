package com.yihuo.bihai.captcha.sdk.verify.impl;

import com.yihuo.bihai.captcha.sdk.NECaptchaVerifier;
import com.yihuo.bihai.captcha.sdk.NESecretPair;
import com.yihuo.bihai.captcha.sdk.config.CaptchaProperties;
import com.yihuo.bihai.captcha.sdk.verify.CaptchaVerify;

public class CaptchaVerifyImpl implements CaptchaVerify {
    private CaptchaProperties captchaProperties;

    public CaptchaVerifyImpl(CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
    }

    @Override
    public boolean isVerifySuccess(String validate, String user) {
        NECaptchaVerifier verifier = NECaptchaVerifier.getInstance(this.captchaProperties.getCaptchaId(), NESecretPair.getInstance(this.captchaProperties.getSecretId(), this.captchaProperties.getSecretKey()));
        return verifier.verify(validate, user);
    }

    @Override
    public String getCaptchaId() {
        return this.captchaProperties.getCaptchaId();
    }
}
