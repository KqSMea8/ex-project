
package com.yihuo.api.i18n;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

@Configuration
@Component
public class LocaleUtils {
    private static final Logger log = LoggerFactory.getLogger(LocaleUtils.class);
    public static final String SPIDER = "spider";
    public static final String ANDROID = "android";
    public static final String IPHONE = "iphone";
    public static final String IOS = "ios";
    public static final String USER_AGENT = "User-Agent";
    public static final String[] ENGLISH = new String[]{"en-us", "en_us"};
    public static final String[] CHINA = new String[]{"zh-cn", "zh_cn", "zh-hans", "zh_hans"};
    public static final String[] CHINA_TW = new String[]{"zh-tw", "zh_tw", "zh-hant", "zh_hant"};
    public static final String[] CHINA_HK = new String[]{"zh-hk", "zh_hk"};
    public static final String[] KOREAN = new String[]{"ko-kr", "ko_kr"};
    public static final String[] JAPAN = new String[]{"ja-jp", "ja_jp"};
    public static final String[] SINGAPORE = new String[]{"zh-sg", "zh_sg"};
    public static final String[] TRADITIONAL_CHINESE = new String[]{"zh-tw", "zh_tw", "zh-hk", "zh_hk", "zh-hant", "zh_hant"};
    private static final String[][] REGION_LANGS;
    private static final Pattern LOCALE_PATTERN;
    private static final char UNDERLINE = '_';
    private static final char DASH = '-';
    @Autowired
    private static LocaleResolver localeResolver;
    @Autowired
    private static MessageSource messageSource;

    @Autowired
    public LocaleUtils(LocaleResolver localeResolver, MessageSource messageSource) {
        localeResolver = localeResolver;
        messageSource = messageSource;
    }

    public static void setInitLocale(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = request.getLocale();
        if (localeResolver instanceof CookieLocaleResolver) {
            CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver)localeResolver;
            Cookie cookie = WebUtils.getCookie(request, cookieLocaleResolver.getCookieName());
            if (cookie == null) {
                setLocale(request, response, locale, "Init CookieLocaleResolver locale url :{},country:{},lang:{}");
                return;
            }

            log.debug("CookieLocaleResolver locale name:{} ,value:{}", cookie.getName(), cookie.getValue());
        }

        if (localeResolver instanceof SessionLocaleResolver) {
            Locale sessionLocale = (Locale)WebUtils.getRequiredSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
            if (sessionLocale == null) {
                setLocale(request, response, locale, "Init SessionLocaleResolver locale url :{}, country:{},lang:{}");
                return;
            }

            log.debug("SessionLocaleResolver Locale: {}", sessionLocale.toLanguageTag());
        }

    }

    private static void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale, String format) {
        setLocale(locale.toString(), request, response);
        log.debug(format, new Object[]{request.getRequestURL().toString(), locale.getCountry(), locale.toLanguageTag()});
    }

    public static void setLocale(String lang, HttpServletRequest request, HttpServletResponse response) {
        lang = ((String)StringUtils.defaultIfEmpty(lang, request.getLocale().toString())).toLowerCase();
        String[][] var3 = REGION_LANGS;
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String[] languages = var3[var5];
            if (StringUtils.containsAny(lang, languages)) {
                localeResolver.setLocale(request, response, Locale.forLanguageTag(languages[0]));
                return;
            }
        }

        localeResolver.setLocale(request, response, Locale.US);
    }

    public static void setLocale(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, getLocaleCookieName());
        if (cookie == null) {
            String userAgent = ((String)StringUtils.defaultIfBlank(request.getHeader("User-Agent"), "")).toLowerCase();
            log.debug("USER_AGENT :{}", userAgent);
            if (StringUtils.containsAny(userAgent, new CharSequence[]{"spider", "android", "ios", "iphone"})) {
                Matcher matcher = LOCALE_PATTERN.matcher(userAgent);
                if (matcher.find()) {
                    String lang = matcher.group(1);
                    setLocale(lang, request, response);
                } else {
                    localeResolver.setLocale(request, response, Locale.US);
                }

            } else {
                setInitLocale(request, response);
            }
        }
    }

    public static String getMessage(String code) {
        return getMessage(code, (Object[])null);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        log.debug("Message Locale tag:{},value:{}", locale.toLanguageTag(), locale.toString());
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public static Map<String, String> getMessages(String... codePrefixes) {
        Locale locale = LocaleContextHolder.getLocale();
        return ((CustomResourceBundleMessageSource)messageSource).getMessages(locale, codePrefixes);
    }

    public static String getLocale(HttpServletRequest request) {
        return getLocale(request, getLocaleCookieName());
    }

    public static String getLocale(HttpServletRequest request, String cookieName) {
        Cookie localeCookie = WebUtils.getCookie(request, cookieName);
        String lang = Locale.SIMPLIFIED_CHINESE.toString();
        return localeCookie != null ? StringUtils.replaceChars(localeCookie.getValue(), '-', '_') : lang;
    }

    public static boolean isTraditionalChinese(String lang) {
        return StringUtils.equalsAnyIgnoreCase(lang, TRADITIONAL_CHINESE);
    }

    public static String getLocaleCookieName() {
        return localeResolver instanceof CookieLocaleResolver ? ((CookieLocaleResolver)localeResolver).getCookieName() : "";
    }

    static {
        REGION_LANGS = new String[][]{ENGLISH, CHINA, CHINA_HK, KOREAN, JAPAN, CHINA_TW, SINGAPORE};
        LOCALE_PATTERN = Pattern.compile("locale=(.*)$", 2);
    }
}
