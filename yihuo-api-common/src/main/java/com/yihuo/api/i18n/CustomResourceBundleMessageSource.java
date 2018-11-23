//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.api.i18n;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

public class CustomResourceBundleMessageSource extends ResourceBundleMessageSource {
    public CustomResourceBundleMessageSource() {
    }

    public Map<String, String> getMessages(Locale locale, String... codePrefixes) {
        Map<String, String> messagesMap = new HashMap(128);
        if (ArrayUtils.isEmpty(codePrefixes)) {
            return messagesMap;
        } else {
            Set<String> basenames = this.getBasenameSet();
            Iterator var5 = basenames.iterator();

            while(true) {
                ResourceBundle bundle;
                do {
                    if (!var5.hasNext()) {
                        return messagesMap;
                    }

                    String basename = (String)var5.next();
                    bundle = this.getResourceBundle(basename, locale);
                } while(bundle == null);

                Iterator var8 = bundle.keySet().iterator();

                while(var8.hasNext()) {
                    String key = (String)var8.next();
                    if (StringUtils.startsWithAny(key, codePrefixes)) {
                        messagesMap.put(key, bundle.getString(key));
                    }
                }
            }
        }
    }
}
