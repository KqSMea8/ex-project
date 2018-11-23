package com.bihai.yihuo.login.interceptor;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.yihuo.bihai.newex.security.jwt.crypto.AesJwtTokenCryptoProvider;
import com.yihuo.bihai.newex.security.jwt.crypto.JwtTokenCryptoProvider;
import com.yihuo.bihai.newex.security.jwt.model.JwtConfig;
import com.yihuo.bihai.newex.security.jwt.token.JwtTokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 下午12:39
 */
@Configuration
@ConditionalOnClass({JwtLoginInterceptor.class})
@Order(99)
@EnableConfigurationProperties({Jwt.class})
public class JwtLoginInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {
    private final Jwt jwt;

    public JwtLoginInterceptorAutoConfiguration(Jwt jwt) {
        this.jwt = jwt;
    }

    @Bean
    @ConditionalOnClass({JwtLoginInterceptor.class})
    @ConditionalOnProperty(
        prefix = "yihuo.jwt",
        name = {"interceptor", "enabled"},
        matchIfMissing = true
    )
    public JwtLoginInterceptor jwtInterceptor() {
        return new JwtLoginInterceptor(this.jwtTokenProvider());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({JwtLoginInterceptor.class})
    @ConditionalOnProperty(
        prefix = "yihuo.jwt",
        name = {"enabled"},
        matchIfMissing = true
    )
    public JwtTokenProvider jwtTokenProvider() {
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setRequestHeaderName(this.jwt.getRequestHeaderName());
        jwtConfig.setCryptoKey(this.jwt.getCryptoKey());
        jwtConfig.setIssuer(this.jwt.getIssuer());
        jwtConfig.setSecret(this.jwt.getSecret());
        jwtConfig.setExpiration(this.jwt.getExpiration());
        JwtTokenCryptoProvider cryptoProvider = new AesJwtTokenCryptoProvider();
        return new JwtTokenProvider(jwtConfig, cryptoProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] jwtPathPatterns = (String[])Iterables.toArray(Splitter.on(",").omitEmptyStrings().trimResults().split(this.jwt.getExcludePathPatterns()), String.class);
        registry.addInterceptor(this.jwtInterceptor()).excludePathPatterns(jwtPathPatterns);
    }
}
