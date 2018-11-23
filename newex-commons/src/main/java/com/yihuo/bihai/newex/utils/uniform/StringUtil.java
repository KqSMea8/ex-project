package com.yihuo.bihai.newex.utils.uniform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
    private static long next = 1L;
    static String[] weeks = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    public StringUtil() {
    }

    public static Float toMoney(String s) {
        if (s == null) {
            return 0.0F;
        } else {
            if (s.indexOf(".") != -1) {
                if (s.indexOf(".") == 0) {
                    s = "0" + s;
                }

                s = s + "00";
                s = s.substring(0, s.indexOf(".") + 3);
            }

            Float fl = toFloat(s);
            if (fl == null) {
                fl = 0.0F;
            }

            return fl;
        }
    }

    public static Float toFloat(String s) {
        return toFloat(s, (Float)null);
    }

    public static Double toDouble(String s) {
        return toDouble(s, (Double)null);
    }

    public static Float toFloat(String s, Float defaultValue) {
        if (s != null && !"".equals(s.trim()) && !"Infinity".equals(s.trim()) && !"NaN".equals(s.trim())) {
            try {
                return Float.parseFloat(s.trim());
            } catch (Exception var3) {
                LOGGER.error(var3.getMessage(), var3);
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static Double toDouble(String s, Double defaultValue) {
        if (s != null && !"".equals(s.trim()) && !"Infinity".equals(s.trim()) && !"NaN".equals(s.trim())) {
            try {
                return Double.parseDouble(s.trim());
            } catch (Exception var3) {
                LOGGER.error(var3.getMessage(), var3);
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static Integer toInteger(String s) {
        return toInteger(s, 0);
    }

    public static Integer toInteger(String s, Integer defaultValue) {
        if (s != null && !"".equals(s.trim()) && s.matches("^[-+]?[0-9]+$")) {
            try {
                return Integer.parseInt(s.trim());
            } catch (Exception var3) {
                LOGGER.error(var3.getMessage(), var3);
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static Long toLong(String s) {
        return toLong(s, (Long)null);
    }

    public static Long toLong(String s, Long defaultValue) {
        if (s != null && !"".equals(s.trim())) {
            try {
                return Long.parseLong(s.trim());
            } catch (Exception var3) {
                LOGGER.error(var3.getMessage(), var3);
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static boolean isInt(String id) {
        if (id == null) {
            return false;
        } else {
            try {
                Integer.parseInt(id.trim());
                return true;
            } catch (Exception var2) {
                LOGGER.error(var2.getMessage(), var2);
                return false;
            }
        }
    }

    public static boolean isId(String id) {
        if (id == null) {
            return false;
        } else {
            try {
                int intValue = Integer.parseInt(id.trim());
                return intValue > 0;
            } catch (Exception var2) {
                LOGGER.error(var2.getMessage(), var2);
                return false;
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            String tempStr = str.trim();
            if (tempStr.length() == 0) {
                return true;
            } else {
                return tempStr.equals("null");
            }
        }
    }

    public static boolean isIndicator(String str) {
        if (str == null) {
            return false;
        } else {
            String tempStr = str.trim();
            return tempStr.length() == 1;
        }
    }

    public static String convertStringArrayToStringWithParser(String[] inputStringArray, String parser) {
        if (inputStringArray != null && parser != null) {
            StringBuffer buffer = new StringBuffer();

            int bufferLength;
            for(bufferLength = 0; bufferLength < inputStringArray.length; ++bufferLength) {
                buffer.append(inputStringArray[bufferLength]).append(parser);
            }

            bufferLength = buffer.length();
            StringBuffer newBuffer = buffer.deleteCharAt(bufferLength - 1);
            return newBuffer.toString();
        } else {
            return null;
        }
    }

    public static List<String> parseStringToList(String inputString, String parserString) {
        if (inputString == null) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(inputString, parserString);
            ArrayList result = new ArrayList(20);

            while(st.hasMoreTokens()) {
                result.add(st.nextToken().trim());
            }

            return result;
        }
    }

    public static Set<String> parseStringToSet(String inputString, String parserString) {
        if (inputString == null) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(inputString, parserString);
            HashSet set = new HashSet(10);

            while(st.hasMoreTokens()) {
                set.add(st.nextToken().trim());
            }

            return set;
        }
    }

    public static String getFirstItemString(String inputString, String parserString) {
        if (inputString == null) {
            return null;
        } else {
            String result = null;
            StringTokenizer st = new StringTokenizer(inputString, parserString);
            if (st.hasMoreTokens()) {
                result = st.nextToken().trim();
            }

            return result;
        }
    }

    public static String getLastItemString(String inputString, String parserString) {
        int lastIndex = inputString.lastIndexOf(parserString);
        return inputString.substring(lastIndex + parserString.length());
    }

    public static String removeFirstItemFromString(String inputString, String parserString) {
        int index = inputString.indexOf(parserString);
        return index < 0 ? null : inputString.substring(index + parserString.length());
    }

    public static boolean isPatternInString(String targetString, String stringPattern) {
        if (targetString != null && stringPattern != null) {
            return targetString.toLowerCase().indexOf(stringPattern.toLowerCase()) >= 0;
        } else {
            return false;
        }
    }

    public static String replace(String str, String pattern, String replace) {
        int s = 0;
        StringBuffer result;
        int e;
        for(result = new StringBuffer(); (e = str.indexOf(pattern, s)) >= 0; s = e + pattern.length()) {
            result.append(str.substring(s, e));
            result.append(replace);
        }

        result.append(str.substring(s));
        return result.toString();
    }

    public static String generateRandomString(int length, int seed) {
        StringBuffer sb = new StringBuffer();
        Random r = new Random(System.currentTimeMillis() + (long)seed);
        int var4 = 0;

        while(true) {
            while(var4++ < length) {
                int randomNumber = r.nextInt(62);
                if (randomNumber >= 0 && randomNumber <= 9) {
                    sb.append((char)(randomNumber + 48));
                } else if (randomNumber >= 10 && randomNumber <= 35) {
                    sb.append((char)(randomNumber + 55));
                } else if (randomNumber >= 36 && randomNumber <= 61) {
                    sb.append((char)(randomNumber + 61));
                }
            }

            return sb.toString();
        }
    }

    public static String getIdString(int[] idArray) {
        StringBuffer buffer = new StringBuffer(idArray.length * 2);

        int length;
        for(length = 0; length < idArray.length; ++length) {
            buffer.append(idArray[length]).append(",");
        }

        length = buffer.length();
        int lastCommaIndex = buffer.lastIndexOf(",");
        return length == lastCommaIndex + 1 ? buffer.substring(0, length - 1) : buffer.toString();
    }

    public static String removeWhiteSpace(String str) {
        String res = null;
        if (str != null) {
            char[] chars = str.toCharArray();
            res = "";

            for(int i = 0; i < chars.length; ++i) {
                if (!Character.isWhitespace(chars[i])) {
                    res = res + chars[i];
                }
            }
        }

        return res;
    }

    public static String normalizePhoneNumber(String phoneNumber) {
        String phone = phoneNumber.replaceAll("[^0-9]", "");
        phone = phone.replaceAll("[\\s]", "");
        return phone;
    }

    public static String getString(String s) {
        return isEmpty(s) ? "" : s.trim();
    }

    public static String utf8Togb2312(String str) {
        String gbk = "";

        try {
            String utf8 = new String(str.getBytes("UTF-8"));
            String unicode = new String(utf8.getBytes(), "UTF-8");
            gbk = new String(unicode.getBytes("GBK"));
        } catch (UnsupportedEncodingException var4) {
            LOGGER.error(" StringUtil utf8Togb2312 ", var4);
        }

        return gbk;
    }

    public static String utf8togb2312(String str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch(c) {
            case '%':
                try {
                    sb.append((char)Integer.parseInt(str.substring(i + 1, i + 3), 16));
                } catch (NumberFormatException var6) {
                    throw new IllegalArgumentException();
                }

                i += 2;
                break;
            case '+':
                sb.append(' ');
                break;
            default:
                sb.append(c);
            }
        }

        String result = sb.toString();
        String res = null;

        try {
            byte[] inputBytes = result.getBytes("8859_1");
            res = new String(inputBytes, "UTF-8");
        } catch (Exception var5) {
            ;
        }

        return res;
    }

    public static String subNickName(String name) {
        String nameStr = "";
        if (name.length() > 2) {
            nameStr = name.substring(0, 1) + "**" + name.substring(name.length() - 1);
        } else {
            nameStr = !isEmpty(name) ? name.substring(0, 1) + "**" : "**";
        }

        return nameStr;
    }

    public static String toUTF8(String str) {
        try {
            if (str == null) {
                str = "";
            } else {
                str = str.trim();
                str = new String(str.getBytes("ISO-8859-1"), "utf-8");
            }
        } catch (Exception var2) {
            LOGGER.error(var2.getMessage(), var2);
        }

        return str;
    }

    public static String togbk(String str) {
        try {
            if (str == null) {
                str = "";
            } else {
                str = str.trim();
                str = new String(str.getBytes("gbk"), "gbk");
            }
        } catch (Exception var2) {
            LOGGER.error(var2.getMessage(), var2);
        }

        return str;
    }

    public static String[] splitStr(String str, char c) {
        try {
            str = str + c;
            int n = 0;

            for(int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == c) {
                    ++n;
                }
            }

            String[] out = new String[n];

            for(int i = 0; i < n; ++i) {
                int index = str.indexOf(c);
                out[i] = str.substring(0, index);
                str = str.substring(index + 1, str.length());
            }

            return out;
        } catch (Exception var6) {
            return null;
        }
    }

    public static String UrlDecoder(String url) {
        try {
            return url != null ? URLDecoder.decode(url, "UTF-8") : null;
        } catch (IllegalArgumentException var5) {
            try {
                return URLDecoder.decode(url, "GBK");
            } catch (UnsupportedEncodingException var3) {
                return null;
            } catch (IllegalArgumentException var4) {
                LOGGER.error("url Decoder error : " + url, var4);
                return null;
            }
        } catch (UnsupportedEncodingException var6) {
            return null;
        }
    }

    public static String UrlDecoderGBK(String url) {
        try {
            return url != null ? URLDecoder.decode(url, "GBK") : null;
        } catch (UnsupportedEncodingException var2) {
            return null;
        }
    }

    public static String UrlEncoder(String url) {
        try {
            if (url == null) {
                url = "";
            }

            String tmp = URLEncoder.encode(url, "UTF-8");
            return tmp;
        } catch (UnsupportedEncodingException var2) {
            return null;
        }
    }

    public static String replaceStr(String source, String oldString, String newString) {
        StringBuffer output = new StringBuffer();
        int lengthOfSource = source.length();
        int lengthOfOld = oldString.length();
        int posStart = 0;
        String lower_s = source.toLowerCase();

        int pos;
        for(String lower_o = oldString.toLowerCase(); (pos = lower_s.indexOf(lower_o, posStart)) >= 0; posStart = pos + lengthOfOld) {
            output.append(source.substring(posStart, pos));
            output.append(newString);
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }

    public static String getPointStr(String str, int length) {
        if (str != null && !"".equals(str)) {
            if (length <= 0) {
                return str;
            } else {
                if (getStrLength(str) > length) {
                    str = getOutputPintString(str, length);
                }

                return str;
            }
        } else {
            return "";
        }
    }

    public static String getLeftStr(String str, int length) {
        if (str != null && !"".equals(str)) {
            int index = 0;
            int strLength = str.length();
            if (length < 0) {
                length = 0;
            }

            for(char[] charArray = str.toCharArray(); index < length && index < strLength; ++index) {
                if (charArray[index] >= 12288 && charArray[index] < '\u9fff' || charArray[index] >= '?') {
                    --length;
                }

                if (charArray[index] == '&') {
                    if (strLength > index + 3 && charArray[index + 1] == 'l' && charArray[index + 2] == 't' && charArray[index + 3] == ';') {
                        length += 3;
                        index += 3;
                    }

                    if (strLength > index + 4 && charArray[index + 1] == '#' && charArray[index + 2] == '4' && charArray[index + 3] == '6' && charArray[index + 4] == ';') {
                        length += 4;
                        index += 4;
                    }

                    if (strLength > index + 5 && charArray[index + 1] == 'n' && charArray[index + 2] == 'b' && charArray[index + 3] == 's' && charArray[index + 4] == 'p' && charArray[index + 5] == ';') {
                        length += 5;
                        index += 5;
                    }

                    if (strLength > index + 5 && charArray[index + 1] == 'q' && charArray[index + 2] == 'u' && charArray[index + 3] == 'o' && charArray[index + 4] == 't' && charArray[index + 5] == ';') {
                        length += 5;
                        index += 5;
                    }

                    if (strLength > index + 6 && charArray[index + 1] == 'a' && charArray[index + 2] == 'c' && charArray[index + 3] == 'u' && charArray[index + 4] == 't' && charArray[index + 5] == 'e' && charArray[index + 6] == ';') {
                        length += 6;
                        index += 6;
                    }

                    if (strLength > index + 6 && charArray[index + 1] == 'c' && charArray[index + 2] == 'e' && charArray[index + 3] == 'd' && charArray[index + 4] == 'i' && charArray[index + 5] == 'l' && charArray[index + 6] == ';') {
                        length += 6;
                        index += 6;
                    }
                }
            }

            String returnStr = str.substring(0, index);
            returnStr = returnStr + "..";
            return returnStr;
        } else {
            return "";
        }
    }

    public static boolean isChinese(String str) {
        if (str != null && !"".equals(str)) {
            char[] charArray = str.toCharArray();

            for(int i = 0; i < charArray.length; ++i) {
                if (charArray[i] >= 13312 && charArray[i] < '\u9fff' || charArray[i] >= '?') {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static int getStrLength(String str) {
        if (str != null && !"".equals(str)) {
            char[] charArray = str.toCharArray();
            int length = 0;
            int strLength = str.length();

            for(int i = 0; i < charArray.length; ++i) {
                if ((charArray[i] < 13312 || charArray[i] >= '\u9fff') && charArray[i] < '?') {
                    if (charArray[i] == '&') {
                        if (strLength > i + 3 && charArray[i + 1] == 'l' && charArray[i + 2] == 't' && charArray[i + 3] == ';') {
                            ++length;
                            i += 3;
                        }

                        if (strLength > i + 4 && charArray[i + 1] == '#' && charArray[i + 2] == '4' && charArray[i + 3] == '6' && charArray[i + 4] == ';') {
                            ++length;
                            i += 4;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'n' && charArray[i + 2] == 'b' && charArray[i + 3] == 's' && charArray[i + 4] == 'p' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'q' && charArray[i + 2] == 'u' && charArray[i + 3] == 'o' && charArray[i + 4] == 't' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'a' && charArray[i + 2] == 'c' && charArray[i + 3] == 'u' && charArray[i + 4] == 't' && charArray[i + 5] == 'e' && charArray[i + 6] == ';') {
                            ++length;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'c' && charArray[i + 2] == 'e' && charArray[i + 3] == 'd' && charArray[i + 4] == 'i' && charArray[i + 5] == 'l' && charArray[i + 6] == ';') {
                            ++length;
                            i += 6;
                        }
                    } else {
                        ++length;
                    }
                } else {
                    length += 2;
                }
            }

            return length;
        } else {
            return 0;
        }
    }

    public static int getStrLengthRank(String str) {
        if (str != null && !"".equals(str)) {
            char[] charArray = str.toCharArray();
            int length = 0;
            int strLength = str.length();

            for(int i = 0; i < charArray.length; ++i) {
                if ((charArray[i] < 13312 || charArray[i] >= '\u9fff') && charArray[i] < '?') {
                    if (charArray[i] == '&') {
                        if (strLength > i + 3 && charArray[i + 1] == 'l' && charArray[i + 2] == 't' && charArray[i + 3] == ';') {
                            ++length;
                            i += 3;
                        }

                        if (strLength > i + 4 && charArray[i + 1] == '#' && charArray[i + 2] == '4' && charArray[i + 3] == '6' && charArray[i + 4] == ';') {
                            ++length;
                            i += 4;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'n' && charArray[i + 2] == 'b' && charArray[i + 3] == 's' && charArray[i + 4] == 'p' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'q' && charArray[i + 2] == 'u' && charArray[i + 3] == 'o' && charArray[i + 4] == 't' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'a' && charArray[i + 2] == 'c' && charArray[i + 3] == 'u' && charArray[i + 4] == 't' && charArray[i + 5] == 'e' && charArray[i + 6] == ';') {
                            ++length;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'c' && charArray[i + 2] == 'e' && charArray[i + 3] == 'd' && charArray[i + 4] == 'i' && charArray[i + 5] == 'l' && charArray[i + 6] == ';') {
                            ++length;
                            i += 6;
                        }
                    } else if (strLength > i + 1 && charArray[i] != ' ' && charArray[i + 1] == ' ' || i + 1 == strLength) {
                        ++length;
                    }
                } else {
                    length += 2;
                }
            }

            return length;
        } else {
            return 0;
        }
    }

    public static String inputToHtml(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = replaceStr(str, "<", "&lt;");
            str = replaceStr(str, ">", "&gt;");
            str = replaceStr(str, "/", "&#47;");
            str = replaceStr(str, "\\", "&#92;");
            str = replaceStr(str, "\r\n", "<br/>");
            str = replaceStr(str, " ", "&nbsp;");
            str = replaceStr(str, "'", "&acute;");
            str = replaceStr(str, "\"", "&quot;");
            str = replaceStr(str, "(", "&#40;");
            str = replaceStr(str, ")", "&#41;");
            return str;
        }
    }

    public static String outputToOrgStr(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            str = replaceStr(str, "&lt;", "<");
            str = replaceStr(str, "&gt;", ">");
            str = replaceStr(str, "&#47;", "/");
            str = replaceStr(str, "&#92;", "\\");
            str = replaceStr(str, "<br/>", "\r\n");
            str = replaceStr(str, "&nbsp;", " ");
            str = replaceStr(str, "&acute;", "'");
            str = replaceStr(str, "&quot;", "\"");
            str = replaceStr(str, "&#40;", "(");
            str = replaceStr(str, "&#41;", ")");
            return str;
        }
    }

    public static String outputToHtml(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            String[] html = new String[]{"onwaiting", "onvolumechange", "ontimeupdate", "onsuspend", "onstalled", "onseeking", "onseeked", "onratechange", "onprogress", "onplaying", "onplay", "onpause", "onloadstart", "onloadedmetadata", "onloadeddata", "onended", "onemptied", "ondurationchange", "oncanplaythrough", "oncanplay", "oninvalid", "oninput", "onforminput", "onformchange", "onundo", "onstorage", "onredo", "onpopstate", "onpageshow", "onpagehide", "ononline", "onoffline", "onmessage", "onbeforeonload", "onhaschange", "onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut", "onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onblur", "onbounce", "oncellchange", "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress", "onkeyup", "onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove", "onmoveend", "onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete", "onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload", "javascript", "vbscript", "expression", "applet", "meta", "xml", "blink", "link", "style", "script", "embed", "object", "iframe", "frame", "frameset", "ilayer", "layer", "bgsound", "title", "base"};
            str = outputToOrgStr(str);
            str = replaceStr(str, "<iframe", "");
            str = replaceStr(str, "</iframe", "");
            str = replaceStr(str, "<script", "");
            str = replaceStr(str, "</script", "");
            str = replaceStr(str, "<embed", "");
            str = replaceStr(str, "</embed", "");
            str = replaceStr(str, "<link", "");
            str = replaceStr(str, "</link", "");
            str = replaceStr(str, "script", "");
            str = replaceStr(str, "document", "");
            str = replaceStr(str, "createElement", "");
            str = replaceStr(str, "onerror", "");
            str = replaceStr(str, "body", "");
            str = replaceStr(str, "appendChild", "");
            str = replaceStr(str, "innerHTML", "");

            for(int i = 0; i < html.length; ++i) {
                str = replaceStr(str, html[i], "");
            }

            return str;
        }
    }

    public static String filterWebsiteTag(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            str = fiterHtmlTag(str, "dd");
            str = fiterHtmlTag(str, "DD");
            str = fiterHtmlTag(str, "dl");
            str = fiterHtmlTag(str, "DL");
            str = fiterHtmlTag(str, "dt");
            str = fiterHtmlTag(str, "DT");
            return str;
        }
    }

    public static String Html2Text(String inputString) {
        try {
            String textStr = "";
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile("<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>", 2);
            Matcher m_script = p_script.matcher(inputString);
            String htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile("<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>", 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile("<[^>]+>", 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = replaceStr(htmlStr, "\r\n", "");
            textStr = replaceStr(textStr, "\t", "");
            return textStr;
        } catch (Exception var12) {
            LOGGER.error("Html2Text: ", var12);
            return null;
        }
    }

    public static String fiterHtmlTag(String str, String tag) {
        String regxp = "<\\s*" + tag + "\\s*([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();

        boolean result1;
        for(result1 = matcher.find(); result1; result1 = matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);
        str = sb.toString();
        regxp = "</" + tag + ">";
        pattern = Pattern.compile(regxp);
        matcher = pattern.matcher(str);
        sb = new StringBuffer();

        for(result1 = matcher.find(); result1; result1 = matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String htmlToStr(String htmlStr, int length) {
        if (htmlStr == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer();
            int count = 0;
            boolean flag = true;
            htmlStr = htmlStr.replace("&lt;", "<");
            char[] a = htmlStr.toCharArray();

            for(int i = 0; i < a.length; ++i) {
                if (a[i] == '<') {
                    flag = false;
                } else if (a[i] == '>') {
                    flag = true;
                } else {
                    if (flag) {
                        ++count;
                    }

                    if (count > length) {
                        result.append("...");
                        break;
                    }

                    if (flag) {
                        result.append(a[i]);
                    }
                }
            }

            return result.toString();
        }
    }

    public static String SBCchange(String str) {
        String outStr = "";
        Object var2 = null;

        try {
            for(int i = 0; i < str.length(); ++i) {
                byte[] b = str.substring(i, i + 1).getBytes("unicode");
                if (b[3] == -1) {
                    b[2] = (byte)(b[2] + 32);
                    b[3] = 0;
                    outStr = outStr + new String(b, "unicode");
                } else {
                    outStr = outStr + str.substring(i, i + 1);
                }
            }
        } catch (Exception var4) {
            LOGGER.error(var4.getMessage(), var4);
        }

        return outStr;
    }

    public static String BQchange(String str) {
        String outStr = "";
        Object var2 = null;

        try {
            for(int i = 0; i < str.length(); ++i) {
                byte[] b = str.substring(i, i + 1).getBytes("unicode");
                if (b[3] != -1) {
                    b[2] = (byte)(b[2] - 32);
                    b[3] = -1;
                    outStr = outStr + new String(b, "unicode");
                } else {
                    outStr = outStr + str.substring(i, i + 1);
                }
            }
        } catch (Exception var4) {
            LOGGER.error(var4.getMessage(), var4);
        }

        return outStr;
    }

    public static long getIpValue(String ip) {
        try {
            String[] str = splitStr(ip, '.');
            return Long.valueOf(str[0]) * 256L * 256L * 256L + Long.valueOf(str[1]) * 256L * 256L + Long.valueOf(str[2]) * 256L + Long.valueOf(str[3]);
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static int rand(int begin, int end) {
        int num;
        for(num = Random(); num < begin || num > end; num = Random()) {
            ;
        }

        return num;
    }

    public static int Random() {
        long max = 1L;
        long x = 4294967295L;
        ++x;
        next *= 134775813L;
        System.out.println(next);
        ++next;
        next %= x;
        double i = (double)next / 4.294967295E9D;
        double fina = 1.0D * i;
        int num = (int)(fina * 10.0D);
        return num;
    }

    public static String changeIP(String ip) {
        if (isEmpty(ip)) {
            return "";
        } else {
            String ip_t = ip;
            int t = ip.lastIndexOf(".");
            if (t > 0) {
                ip_t = ip.substring(0, t + 1);
                ip_t = ip_t + "*";
            }

            return ip_t;
        }
    }

    public static String getStringMutiEncode(String s) throws UnsupportedEncodingException {
        String s1 = new String(s);
        String s2 = new String(s.getBytes("GBK"), "UTF8");
        String s3 = new String(s.getBytes("UTF8"), "GBK");
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("s3 = " + s3);
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        c1 = getChineseLength(s1);
        c2 = getChineseLength(s2);
        c3 = getChineseLength(s3);
        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);
        System.out.println("c3 = " + c3);
        if (c2 < c1 && c2 > 0) {
            s1 = s2;
            c1 = c2;
            System.out.println("r2");
        }

        if (c3 < c1 && c3 > 0) {
            s1 = s3;
            System.out.println("r3");
        }

        return s1;
    }

    public static int getChineseLength(String s) {
        int rv = 0;

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c >= 12288 && c <= '\u9fff') {
                ++rv;
            }
        }

        System.out.println(rv);
        return rv;
    }

    public static String getemailByReplace(String s) {
        String bb = s.replaceAll("[a-z|\\d|||_|-|@|.]", "*");
        return bb;
    }

    public static String filterCHNTOENG(String keyword) {
        if (keyword == null) {
            return "";
        } else {
            keyword = replaceStr(keyword, "‘", "'");
            keyword = replaceStr(keyword, "’", "'");
            keyword = replaceStr(keyword, "“", "\"");
            keyword = replaceStr(keyword, "”", "\"");
            keyword = replaceStr(keyword, "。", ".");
            keyword = replaceStr(keyword, "，", ",");
            keyword = replaceStr(keyword, "！", "!");
            keyword = replaceStr(keyword, "？", "?");
            keyword = replaceStr(keyword, "：", ":");
            keyword = replaceStr(keyword, "；", ";");
            return keyword;
        }
    }

    public static int wordLength(String str) {
        if (str != null && str.length() != 0) {
            int flag = -1;
            int sum = 0;

            for(int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) >= 0 && str.charAt(i) <= 255) {
                    if (flag != 0) {
                        flag = 0;
                        ++sum;
                    }
                } else {
                    flag = 1;
                    ++sum;
                }
            }

            return sum;
        } else {
            return 0;
        }
    }

    public static Integer outInt(Integer num) {
        return num == null ? 0 : num;
    }

    public static String toIntegerSplitOut(Integer num) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(true);
        return nf.format(num);
    }

    public static String replTag(String tag) {
        StringBuffer sbuf = new StringBuffer();
        tag = tag.replaceAll(",", " ");
        String[] strArray = tag.split(" +");

        for(int i = 0; i < strArray.length; ++i) {
            if (i != 0) {
                sbuf.append(",");
            }

            sbuf.append(strArray[i]);
        }

        return sbuf.toString();
    }

    public static String filter_word(String keyword) {
        try {
            StringBuffer sbuf = new StringBuffer();
            char[] charArray = keyword.toCharArray();
            int length = charArray.length;

            for(int index = 0; index < length; ++index) {
                if (charArray[index] >= 12288 && charArray[index] < '\u9fff' || charArray[index] >= '?' || charArray[index] >= ' ' && charArray[index] <= '~') {
                    sbuf.append(charArray[index] + "");
                }
            }

            return sbuf.toString();
        } catch (Exception var5) {
            return keyword;
        }
    }

    public static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static String isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*,");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        char[] ch = after.trim().toCharArray();
        StringBuffer sbuf = new StringBuffer();

        for(int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!(c + "").equals(",")) {
                    if (isChinese(c)) {
                        sbuf.append(c + "");
                    }
                } else {
                    sbuf.append(c + "");
                }
            } else {
                sbuf.append(c + "");
            }
        }

        return sbuf.toString();
    }

    public static long ipToLong(String strIP) {
        try {
            if (strIP != null && strIP.length() != 0) {
                long[] ip = new long[4];
                int position1 = strIP.indexOf(".");
                int position2 = strIP.indexOf(".", position1 + 1);
                int position3 = strIP.indexOf(".", position2 + 1);
                ip[0] = Long.parseLong(strIP.substring(0, position1));
                ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
                ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
                ip[3] = Long.parseLong(strIP.substring(position3 + 1));
                return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
            } else {
                return 0L;
            }
        } catch (Exception var5) {
            return 0L;
        }
    }

    public static String longToip(long ipLong) {
        long[] mask = new long[]{255L, 65280L, 16711680L, -16777216L};
        long num = 0L;
        StringBuffer ipInfo = new StringBuffer();

        for(int i = 0; i < 4; ++i) {
            num = (ipLong & mask[i]) >> i * 8;
            if (i > 0) {
                ipInfo.insert(0, ".");
            }

            ipInfo.insert(0, Long.toString(num, 10));
        }

        return ipInfo.toString();
    }

    public static String getSqlSubStatement(long[] ids) {
        StringBuffer sbuf = new StringBuffer();
        if (ids != null && ids.length > 0) {
            for(int i = 0; i < ids.length; ++i) {
                if (i != 0) {
                    sbuf.append(",");
                }

                sbuf.append(ids[i]);
            }

            return sbuf.toString();
        } else {
            return sbuf.toString();
        }
    }

    public static String getSqlSubStatement(int[] ids) {
        StringBuffer sbuf = new StringBuffer();
        if (ids != null && ids.length > 0) {
            for(int i = 0; i < ids.length; ++i) {
                if (i != 0) {
                    sbuf.append(",");
                }

                sbuf.append(ids[i]);
            }

            return sbuf.toString();
        } else {
            return sbuf.toString();
        }
    }

    public static String toRRShare(String html) {
        if (html != null && html.trim().length() != 0) {
            html = replaceStr(html, "\r\n", "<br>");
            return html;
        } else {
            return "";
        }
    }

    public static int getArrangePassRate(int rate) {
        int rateStyle = 0;
        if (rate >= 1 && rate <= 10) {
            rateStyle = 1;
        } else if (rate >= 11 && rate <= 20) {
            rateStyle = 2;
        } else if (rate >= 21 && rate <= 30) {
            rateStyle = 3;
        } else if (rate >= 31 && rate <= 40) {
            rateStyle = 4;
        } else if (rate >= 41 && rate <= 50) {
            rateStyle = 5;
        } else if (rate >= 51) {
            rateStyle = 6;
        }

        return rateStyle;
    }

    public static String null2nothing(String sourceStr) {
        return null2Value(sourceStr, "");
    }

    public static String null2Value(String sourceStr, String defaultValue) {
        return sourceStr == null ? defaultValue : sourceStr;
    }

    public static int[] getSequence(int no) {
        int[] sequence = new int[no];

        for(int i = 0; i < no; sequence[i] = i++) {
            ;
        }

        Random random = new Random();

        for(int i = 0; i < no; ++i) {
            int p = random.nextInt(no);
            int tmp = sequence[i];
            sequence[i] = sequence[p];
            sequence[p] = tmp;
        }

        return sequence;
    }

    public static Set<Long> stringToSet(String ids) {
        String[] idArr = ids.split("\\|");
        Set<Long> set = new HashSet();
        if (idArr != null && idArr.length > 0) {
            for(int i = 0; i < idArr.length; ++i) {
                Long productId = toLong(idArr[i]);
                if (productId != null) {
                    set.add(productId);
                }
            }
        }

        return set;
    }

    public static String setToString(Set<Long> set) {
        if (set != null && set.size() != 0) {
            StringBuilder builder = new StringBuilder();
            Iterator<Long> iter = set.iterator();

            for(int i = 0; iter.hasNext(); ++i) {
                if (i != 0) {
                    builder.append(",");
                }

                builder.append(iter.next());
            }

            return builder.toString();
        } else {
            return null;
        }
    }

    public static String[] setToStringArr(Set<Long> set) {
        if (set != null && set.size() != 0) {
            int size = set.size() % 1000 == 0 ? set.size() / 1000 : set.size() / 1000 + 1;
            int i = 0;
            String[] result = new String[size];
            Iterator<Long> iter = set.iterator();
            HashSet tmp_set = new HashSet();

            while(true) {
                do {
                    if (!iter.hasNext()) {
                        return result;
                    }

                    tmp_set.add(iter.next());
                } while(tmp_set.size() < 1000 && i * 1000 + tmp_set.size() != set.size());

                result[i] = setToString(tmp_set);
                tmp_set = new HashSet();
                ++i;
            }
        } else {
            return null;
        }
    }

    public static String listToString(List<Long> list) {
        if (list != null && list.size() != 0) {
            StringBuilder builder = new StringBuilder();
            Iterator<Long> iter = list.iterator();

            for(int i = 0; iter.hasNext(); ++i) {
                if (i != 0) {
                    builder.append(",");
                }

                builder.append(iter.next());
            }

            return builder.toString();
        } else {
            return null;
        }
    }

    public static float getMoney(float money) {
        float privilege = 0.0F;
        if ((double)money >= 100.0D && (double)money < 200.0D) {
            privilege = 20.0F;
        } else if ((double)money >= 200.0D && (double)money < 500.0D) {
            privilege = 40.0F;
        } else if ((double)money >= 500.0D && (double)money < 1000.0D) {
            privilege = 200.0F;
        } else if ((double)money >= 1000.0D && (double)money < 1500.0D) {
            privilege = 400.0F;
        } else if ((double)money >= 1500.0D && (double)money < 2000.0D) {
            privilege = 600.0F;
        } else if ((double)money >= 2000.0D && (double)money < 5000.0D) {
            privilege = 800.0F;
        } else if ((double)money >= 5000.0D && (double)money < 10000.0D) {
            privilege = 2000.0F;
        } else if ((double)money >= 10000.0D) {
            privilege = 4000.0F;
        }

        money -= privilege;
        return money;
    }

    public static boolean filterCode(String str) {
        boolean flag = false;
        if (str != null && str.trim().length() != 0) {
            String type = "&(amp;)?#\\w{5};";
            Pattern pattern = Pattern.compile("&(amp;)?#\\w{5};");
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
            return flag;
        } else {
            return flag;
        }
    }

    public static String filterString(String str) {
        str = str.toLowerCase().trim();
        str = str.replaceAll("&nbsp;|<br>|<BR>|[\\s]|[　]", "");
        return str;
    }

    public static String tenToEr(long ten) {
        String s;
        for(s = ""; ten > 0L; ten /= 2L) {
            long i = ten % 2L;
            s = i + s;
        }

        int t = s.length();

        for(int i = t; i < 32; ++i) {
            s = "0" + s;
        }

        return s;
    }

    public static int getIPMark(String startIP, String endIP) {
        int result = 0;
        if (startIP != null && startIP.trim().length() != 0 && endIP != null && endIP.trim().length() != 0) {
            for(int i = 0; i < startIP.length(); ++i) {
                char start = startIP.charAt(i);
                char end = endIP.charAt(i);
                if (start != end) {
                    result = i;
                    break;
                }
            }

            return result;
        } else {
            return result;
        }
    }

    public static int getIPMark(long startIP, long endIP) {
        int result = 0;
        if (startIP > 0L && endIP > 0L) {
            result = getIPMark(tenToEr(startIP), tenToEr(endIP));
            return result;
        } else {
            return result;
        }
    }

    public static Boolean isIpAddress(String s) {
        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile("(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static Boolean isEmail(String email) {
        if (isEmpty(email)) {
            return false;
        } else {
            String regex = "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
            Matcher m = p.matcher(email);
            return m.matches();
        }
    }

    public static Boolean isPassword(String password) {
        if (isEmpty(password)) {
            return false;
        } else {
            String regex = "^[A-Za-z0-9_-]+$";
            Pattern p = Pattern.compile("^[A-Za-z0-9_-]+$");
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }

    public static boolean isMobile(String mobiles) {
        if (isEmpty(mobiles)) {
            return false;
        } else {
            Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
    }

    public static long[] getLong(String str) {
        if (str != null && str.trim().length() != 0 && !"".equals(str)) {
            String[] strs = str.split(",");
            long[] ids = null;
            if (strs != null && strs.length > 0) {
                ids = new long[strs.length];

                for(int i = 0; i < strs.length; ++i) {
                    ids[i] = toLong(strs[i], 0L);
                }
            }

            return ids;
        } else {
            return null;
        }
    }

    public static long[] getLong(String str, String charStr) {
        if (str != null && str.trim().length() != 0 && !"".equals(str)) {
            String[] strs = str.split(charStr);
            long[] ids = null;
            if (strs != null && strs.length > 0) {
                ids = new long[strs.length];

                for(int i = 0; i < strs.length; ++i) {
                    ids[i] = toLong(strs[i], 0L);
                }
            }

            return ids;
        } else {
            return null;
        }
    }

    public static int[] getInteger(String str, String charStr) {
        if (str != null && str.trim().length() != 0 && !"".equals(str)) {
            String[] strs = str.split(charStr);
            int[] ids = null;
            if (strs != null && strs.length > 0) {
                ids = new int[strs.length];

                for(int i = 0; i < strs.length; ++i) {
                    ids[i] = toInteger(strs[i], 0);
                }
            }

            return ids;
        } else {
            return null;
        }
    }

    public static String toHtmlSubString(String str, int len) {
        int index = len - 1;
        if (null != str && !"".equals(str.trim())) {
            if (index <= 0) {
                return str;
            } else {
                byte[] bt = null;

                try {
                    bt = str.getBytes();
                } catch (Exception var8) {
                    var8.getMessage();
                }

                if (null != bt && bt.length > len) {
                    if (index > bt.length - 1) {
                        index = bt.length - 1;
                    }

                    String substrx = null;
                    if (bt[index] >= 0) {
                        substrx = null;
                        substrx = new String(bt, 0, index + 1) + "..";
                        return substrx;
                    } else {
                        int jsq = 0;

                        for(int num = index; num >= 0 && bt[num] < 0; --num) {
                            ++jsq;
                        }

                        int m = 0;
                        m = jsq % 2;
                        index -= m;
                        substrx = new String(bt, 0, index + 1) + "..";
                        return substrx;
                    }
                } else {
                    return str;
                }
            }
        } else {
            return "";
        }
    }

    public static String getOutputPintString(String str, int len) {
        try {
            if (str != null && str.trim().length() != 0) {
                byte[] b = str.trim().getBytes();
                if (b.length <= len) {
                    return str;
                } else {
                    StringBuffer sb = new StringBuffer();
                    double sub = 0.0D;

                    for(int i = 0; i < str.length(); ++i) {
                        if (sub >= (double)(len - 2)) {
                            sb.append("..");
                            break;
                        }

                        String tmp = str.substring(i, i + 1);
                        sub += tmp.getBytes().length > 2 ? 2.0D : (double)tmp.getBytes().length;
                        char c = str.charAt(i);
                        if (c >= 'A' && c <= 'Z') {
                            sub += 0.5D;
                        }

                        sb.append(tmp);
                    }

                    return sb.toString();
                }
            } else {
                return "";
            }
        } catch (Exception var9) {
            return str;
        }
    }

    public static String subStringIE(String str, int len) {
        if (str != null && str.trim().length() != 0) {
            byte[] b = str.trim().getBytes();
            if (b.length <= len) {
                return str;
            } else {
                StringBuffer sb = new StringBuffer();
                double sub = 0.0D;

                for(int i = 0; i < str.length(); ++i) {
                    String tmp = str.substring(i, i + 1);
                    sub += getStringIELength(tmp);
                    if (sub > (double)(len - 2)) {
                        sb.append("..");
                        break;
                    }

                    sb.append(tmp);
                }

                return sb.toString();
            }
        } else {
            return "";
        }
    }

    public static double getStringIELength(String s) {
        if (s != null && s.length() != 0) {
            byte[] b = s.getBytes();
            if (b.length > 1) {
                return (double)b.length;
            } else if ("il: ".indexOf(s) > -1) {
                return 0.4D;
            } else if ("fjt".indexOf(s) > -1) {
                return 0.5D;
            } else if ("rI".indexOf(s) > -1) {
                return 0.6D;
            } else if ("sJ-z".indexOf(s) > -1) {
                return 0.7D;
            } else if ("aceghnuvxy".indexOf(s) > -1) {
                return 0.9D;
            } else if ("EFLPSTZ".indexOf(s) > -1) {
                return 1.1D;
            } else if ("BR".indexOf(s) > -1) {
                return 1.2D;
            } else if ("ACDGFHmwUVXY".indexOf(s) > -1) {
                return 1.3D;
            } else if ("KNOQ".indexOf(s) > -1) {
                return 1.5D;
            } else {
                return "MW".indexOf(s) > -1 ? 1.65D : 1.0D;
            }
        } else {
            return 0.0D;
        }
    }

    public static boolean isEnglish(String str) {
        String reg = "[a-zA-Z]";
        int lng = str.length();

        for(int i = 0; i < lng; ++i) {
            String tmp = str.charAt(i) + "";
            if (tmp.matches("[a-zA-Z]")) {
                return true;
            }
        }

        return false;
    }

    public static boolean isContainIllegalChar(String str) {
        return str.indexOf("\\") != -1 || str.indexOf("/") != -1 || str.indexOf(":") != -1 || str.indexOf("*") != -1 || str.indexOf("?") != -1 || str.indexOf("\"") != -1 || str.indexOf(">") != -1 || str.indexOf("<") != -1 || str.indexOf("|") != -1;
    }

    public static String lastCharacterCn2En(String str) {
        if (str != null && str.length() > 0) {
            String lastStr = str.substring(str.length() - 1, str.length());
            String regex = "[，。？：；‘’！“”—……、（）【】{}《》－]";
            Pattern p = null;
            Matcher m = null;
            p = Pattern.compile("[，。？：；‘’！“”—……、（）【】{}《》－]");
            m = p.matcher(lastStr);
            if (m.matches()) {
                str = str + " ";
            }
        }

        return str;
    }

    public static int getCharCount(String str, char c) {
        int j = 0;

        for(int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == c) {
                ++j;
            }
        }

        return j;
    }

    public static String getWeekStr(int week) {
        String weekStr = "";
        switch(week) {
        case 1:
            weekStr = "一";
            break;
        case 2:
            weekStr = "二";
            break;
        case 3:
            weekStr = "三";
            break;
        case 4:
            weekStr = "四";
            break;
        case 5:
            weekStr = "五";
            break;
        case 6:
            weekStr = "六";
            break;
        case 7:
            weekStr = "日";
        }

        return weekStr;
    }

    public static String doubleDecimalCny(double value) {
        if (value == 0.0D) {
            return "0";
        } else {
            String result = value + "";
            if (value % 1.0D == 0.0D) {
                result = (long)value + "";
            }

            if (result.indexOf("E") > -1) {
                NumberFormat formate = NumberFormat.getNumberInstance();
                formate.setMaximumFractionDigits(8);
                formate.setMaximumIntegerDigits(8);
                result = formate.format(value);
            }

            int dotIndex = result.indexOf(".");
            if (dotIndex > -1) {
                String part1 = result.substring(0, dotIndex);
                if (result.length() > part1.length() + 3) {
                    result = part1 + result.substring(dotIndex, part1.length() + 3);
                }

                return result;
            } else {
                return result;
            }
        }
    }

    public static String doubleDecimalBtc(double value) {
        if (value == 0.0D) {
            return "0";
        } else {
            double result = value;
            String resultStr = value + "";
            if (!isEmpty(resultStr) && resultStr.indexOf(".") > -1) {
                String[] resultStrs = resultStr.split("[.]");
                if (resultStrs != null && resultStrs.length > 1 && resultStrs[1].length() > 6) {
                    result = Math.floor(value * 1000000.0D) / 1000000.0D;
                    resultStr = result + "";
                }
            }

            if (result % 1.0D == 0.0D) {
                resultStr = (long)result + "";
            }

            if (resultStr.indexOf("E") > -1) {
                NumberFormat formate = NumberFormat.getNumberInstance();
                formate.setMaximumFractionDigits(8);
                formate.setMaximumIntegerDigits(8);
                resultStr = formate.format(result);
            }

            return resultStr;
        }
    }

    public static String doubleDecimalBtc3(double value) {
        if (value == 0.0D) {
            return "0";
        } else {
            double result = Math.floor(value * 1000.0D) / 1000.0D;
            return result % 1.0D == 0.0D ? (long)result + "" : result + "";
        }
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return codePoint == 0 || codePoint == '\t' || codePoint == '\n' || codePoint == '\r' || codePoint >= ' ' && codePoint <= '\ud7ff' || codePoint >= '\ue000' && codePoint <= '?' || codePoint >= 65536 && codePoint <= 1114111;
    }

    public static String filterEmoji(String source) {
        if (isEmpty(source)) {
            return source;
        } else {
            StringBuilder buf = new StringBuilder();
            int len = source.length();

            for(int i = 0; i < len; ++i) {
                char codePoint = source.charAt(i);
                if (isNotEmojiCharacter(codePoint)) {
                    buf.append(codePoint);
                }
            }

            return buf.toString();
        }
    }

    public static String randomStr(int length) {
        StringBuffer str = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            Random ran = new Random();
            int choice = ran.nextInt(2) % 2 == 0 ? 65 : 97;
            char x = (char)(choice + ran.nextInt(26));
            str.append(x);
        }

        return str.toString();
    }

    public static String formatString(String defstr) {
        if (isEmpty(defstr)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < defstr.length(); ++i) {
                String s = defstr.substring(i, i + 1);
                if (!s.endsWith("\"") && !s.endsWith("[") && !s.endsWith("]")) {
                    sb.append(s);
                }
            }

            return sb.toString();
        }
    }

    public static String gbkToUtf(String content) throws UnsupportedEncodingException {
        if (isEmpty(content)) {
            return "";
        } else {
            String iso = new String(content.getBytes("UTF-8"), "ISO-8859-1");
            String value = new String(iso.getBytes("ISO-8859-1"), "UTF-8");
            return value;
        }
    }

    public static boolean isNum(String ss) {
        if (isEmpty(ss)) {
            return false;
        } else {
            for(int i = 0; i < ss.length(); ++i) {
                char a = ss.charAt(i);
                if (a < '0' || a > '9') {
                    return false;
                }
            }

            return true;
        }
    }

    public static String nickNmeStr(String nikeName) {
        String nikeNamestr = nikeName;
        if (isNum(nikeName)) {
            if (nikeName.length() > 2) {
                nikeNamestr = nikeName.substring(0, 1) + "**" + nikeName.substring(nikeName.length() - 1);
            } else {
                nikeNamestr = nikeName.substring(0, 1) + "**";
            }
        }

        return nikeNamestr;
    }

    public static String replaceNickNme(String nikeName) {
        String nikeNamestr;
        if (nikeName.length() > 2) {
            nikeNamestr = nikeName.substring(0, 1) + "**" + nikeName.substring(nikeName.length() - 1);
        } else {
            nikeNamestr = nikeName.substring(0, 1) + "**";
        }

        return nikeNamestr;
    }

    public static int setBinaryIndex(int value, int bit, int index) {
        if (getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value -= 1 << bit - 1;
            }
        } else if (index == 1) {
            value |= 1 << bit - 1;
        }

        return value;
    }

    public static int getBinaryIndex(int value, int bit) {
        int remainder = 0;

        for(int i = 0; i < bit; ++i) {
            int factor = value / 2;
            remainder = value % 2;
            if (factor == 0) {
                if (i < bit - 1) {
                    remainder = 0;
                }
                break;
            }

            value = factor;
        }

        return remainder;
    }

    public static long setBinaryIndex(long value, int bit, int index) {
        long dex = 1L;
        if (getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value -= 1L << bit - 1;
            }
        } else if (index == 1) {
            value |= 1L << bit - 1;
        }

        return value;
    }

    public static int getBinaryIndex(long value, int bit) {
        long remainder = 0L;

        for(int i = 0; i < bit; ++i) {
            long factor = value / 2L;
            remainder = value % 2L;
            if (factor == 0L) {
                if (i < bit - 1) {
                    remainder = 0L;
                }
                break;
            }

            value = factor;
        }

        return (int)remainder;
    }

    public static String ubb2html(String argString) {
        String tString = argString;
        if (!argString.equals("")) {
            Boolean tState = true;
            tString = argString.replaceAll("\\[br\\]", "<br />");
            String[][] tRegexAry = new String[][]{{"\\[p\\]([^\\[]*?)\\[\\/p\\]", "$1<br />"}, {"\\[b\\]([^\\[]*?)\\[\\/b\\]", "<b>$1</b>"}, {"\\[i\\]([^\\[]*?)\\[\\/i\\]", "<i>$1</i>"}, {"\\[u\\]([^\\[]*?)\\[\\/u\\]", "<u>$1</u>"}, {"\\[ol\\]([^\\[]*?)\\[\\/ol\\]", "<ol>$1</ol>"}, {"\\[ul\\]([^\\[]*?)\\[\\/ul\\]", "<ul>$1</ul>"}, {"\\[li\\]([^\\[]*?)\\[\\/li\\]", "<li>$1</li>"}, {"\\[code\\]([^\\[]*?)\\[\\/code\\]", "<div class=\"ubb_code\">$1</div>"}, {"\\[quote\\]([^\\[]*?)\\[\\/quote\\]", "<div class=\"ubb_quote\">$1</div>"}, {"\\[color=([^\\]]*)\\]([^\\[]*?)\\[\\/color\\]", "<font style=\"color: $1\">$2</font>"}, {"\\[hilitecolor=([^\\]]*)\\]([^\\[]*?)\\[\\/hilitecolor\\]", "<font style=\"background-color: $1\">$2</font>"}, {"\\[align=([^\\]]*)\\]([^\\[]*?)\\[\\/align\\]", "<div style=\"text-align: $1\">$2</div>"}, {"\\[url=([^\\]]*)\\]([^\\[]*?)\\[\\/url\\]", "<a href=\"$1\" target=\"_blank\">$2</a>"}, {"\\[img\\]([^\\[]*?)\\[\\/img\\]", "<a href=\"$1\" target=\"_blank\"><img src=\"$1\" onload=\"cls.img.tResize(this, 600, 1800);\" /></a>"}};

            while(tState) {
                tState = false;

                for(int ti = 0; ti < tRegexAry.length; ++ti) {
                    Pattern tPattern = Pattern.compile(tRegexAry[ti][0]);

                    String tvalue1;
                    String tvalue2;
                    for(Matcher tMatcher = tPattern.matcher(tString); tMatcher.find(); tString = tString.replace(tvalue1, tvalue2)) {
                        tState = true;
                        tvalue1 = tMatcher.group();
                        tvalue2 = tRegexAry[ti][1];

                        for(int tk = 1; tk < tMatcher.groupCount() + 1; ++tk) {
                            tvalue2 = tvalue2.replace("$" + tk, tMatcher.group(tk));
                        }
                    }
                }
            }
        }

        return tString;
    }

    public static boolean isFilterLoginIp(String ip) {
        return !isEmpty(ip) && ip.length() > 10 && !"218.60.".equals(ip.substring(0, 7)) && !"180.97.".equals(ip.substring(0, 7)) && !"120.24.229.124".equals(ip) && !"43.230.88.".equals(ip.substring(0, 10));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    }
}
