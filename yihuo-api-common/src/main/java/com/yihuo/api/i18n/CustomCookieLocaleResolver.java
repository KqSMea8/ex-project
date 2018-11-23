//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.i18n;

import java.util.Locale;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class CustomCookieLocaleResolver extends CookieLocaleResolver {
    private static final char UNDERLINE = '_';
    private static final char DASH = '-';

    public CustomCookieLocaleResolver() {
    }

    @Override
    protected Locale parseLocaleValue(String locale) {
        return locale == null ? this.getDefaultLocale() : StringUtils.parseLocaleString(locale.replace('-', '_'));
    }

    @Override
    protected String toLocaleValue(Locale locale) {
        return locale.toString();
    }
}
