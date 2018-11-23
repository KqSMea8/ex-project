package com.yihuo.api.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/11/23
 */
@Slf4j
public class MessageSourceUtil {

    private static ReloadableResourceBundleMessageSource messageSourceReload;

    static  {
        log.info("init messageSource...");
        messageSourceReload = new ReloadableResourceBundleMessageSource();
        messageSourceReload.setCacheSeconds(-1);
        messageSourceReload.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSourceReload.setBasenames("/messages/messages");
    }

    /**
     * 国际化
     *
     * @param code
     * @return
     */
    public static String getMessage(String code, Object[] params) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            log.info("Message Locale tag:{},value:{}", locale.toLanguageTag(), locale.toString());
            message = messageSourceReload.getMessage(code, params, locale);
        } catch (Exception e) {
            log.error("parse message error! ", e);
        }
        return message;
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }
}
