package com.yihuo.bihai.captcha.sdk.verify;

public interface CaptchaVerify {
    boolean isVerifySuccess(String var1, String var2);

    String getCaptchaId();
}
