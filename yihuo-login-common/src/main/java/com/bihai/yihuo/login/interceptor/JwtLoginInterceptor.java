package com.bihai.yihuo.login.interceptor;

import com.yihuo.bihai.newex.security.jwt.exception.JwtTokenExpiredException;
import com.yihuo.bihai.newex.security.jwt.model.JwtPublicClaims;
import com.yihuo.bihai.newex.security.jwt.model.JwtUserDetails;
import com.yihuo.bihai.newex.security.jwt.token.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *  
 * 
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 下午12:39
 */
public class JwtLoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(JwtLoginInterceptor.class);
    private final JwtTokenProvider jwtTokenProvider;

    public JwtLoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqMethod = request.getMethod();
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(reqMethod)) {
            return super.preHandle(request, response, handler);
        } else {
            Anonymous anonymous = (Anonymous)((HandlerMethod)handler).getMethodAnnotation(Anonymous.class);
            if (null != anonymous && !anonymous.tokenTransfer()) {
                return super.preHandle(request, response, handler);
            } else {
                String tokenName = this.jwtTokenProvider.getJwtConfig().getRequestHeaderName();
                String token = request.getHeader(tokenName);
                if (null != anonymous && anonymous.tokenTransfer() && StringUtils.isBlank(token)) {
                    return super.preHandle(request, response, handler);
                } else if (null == anonymous && StringUtils.isBlank(token)) {
                    log.debug("Not Found Jwt Token,Not Login!");
                    throw new NotFoundJwtTokenException("Not Found Jwt Token,Not Login!");
                } else {
                    JwtPublicClaims claims = null;

                    try {
                        claims = this.jwtTokenProvider.parseClaims(token);
                    } catch (ExpiredJwtException var10) {
                        if (null == anonymous) {
                            log.debug("Jwt Token is expired!");
                            throw new JwtTokenExpiredException("Jwt Token is expired");
                        }

                        return super.preHandle(request, response, handler);
                    }

                    JwtUserDetails jwtUserDetails = this.jwtTokenProvider.getJwtUserDetails(claims);
                    request.setAttribute("currentUser", jwtUserDetails);
                    return super.preHandle(request, response, handler);
                }
            }
        }
    }
}
