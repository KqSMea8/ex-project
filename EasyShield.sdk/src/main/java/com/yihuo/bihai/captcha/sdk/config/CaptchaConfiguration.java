package com.yihuo.bihai.captcha.sdk.config;

import com.yihuo.bihai.captcha.sdk.verify.CaptchaVerify;
import com.yihuo.bihai.captcha.sdk.verify.impl.CaptchaVerifyImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfiguration {
    public CaptchaConfiguration() {
    }

    @Bean
    @ConfigurationProperties("captcha")
    public CaptchaProperties captchaProperties() {
        return new CaptchaProperties();
    }

    @Bean
    public CaptchaVerify captchaVerify() {
        return new CaptchaVerifyImpl(this.captchaProperties());
    }
}
