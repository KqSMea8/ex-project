//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.i18n;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class MultiTerminalLocaleChangeInterceptor extends LocaleChangeInterceptor {
    public MultiTerminalLocaleChangeInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        LocaleUtils.setLocale(request, response);
        return true;
    }
}
