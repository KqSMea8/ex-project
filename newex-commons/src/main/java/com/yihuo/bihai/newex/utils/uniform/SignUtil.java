package com.yihuo.bihai.newex.utils.uniform;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:17
 */
public class SignUtil {
    public SignUtil() {
    }

    public static String buildSign(JSONObject sArray, String secretKey) {
        String mysign = "";

        try {
            String prestr = createLinkString(sArray);
            prestr = prestr + "&secret_key=" + secretKey;
            mysign = MD5Util.getMD5String(prestr);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return mysign;
    }

    public static String createLinkString(JSONObject params) {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";

        for(int i = 0; i < keys.size(); ++i) {
            String key = (String)keys.get(i);
            String value = params.get(key).toString();
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static JSONObject paraFilter(JSONObject sArray) {
        JSONObject result = new JSONObject();
        if (sArray != null && sArray.size() > 0) {
            Iterator var2 = sArray.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                Object value = sArray.get(key);
                if (value != null && !key.equalsIgnoreCase("sign") && !key.equalsIgnoreCase("sign_type")) {
                    result.put(key, value);
                }
            }

            return result;
        } else {
            return result;
        }
    }

    private static JSONObject buildRequestPara(JSONObject sParaTemp, String secretKey) {
        JSONObject sPara = paraFilter(sParaTemp);
        String mysign = buildSign(sPara, secretKey);
        sPara.put("sign", mysign);
        return sPara;
    }

    public static String getSign(JSONObject params, String secretKey) {
        JSONObject sParaNew = paraFilter(params);
        return buildSign(sParaNew, secretKey);
    }
}
