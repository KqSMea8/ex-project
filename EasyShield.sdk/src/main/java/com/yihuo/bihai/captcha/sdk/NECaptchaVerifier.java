package com.yihuo.bihai.captcha.sdk;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.yihuo.bihai.captcha.sdk.utils.HttpClient4Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NECaptchaVerifier {
    private static final Logger log = LoggerFactory.getLogger(NECaptchaVerifier.class);
    public static final String VERIFY_API = "http://c.dun.163yun.com/api/v2/verify";
    public static final String REQ_VALIDATE = "NECaptchaValidate";
    private static final String VERSION = "v2";
    private String captchaId = "";
    private NESecretPair secretPair = null;
    public static volatile NECaptchaVerifier verifier = null;

    public static NECaptchaVerifier getInstance(String captchaId, NESecretPair secretPair) {
        if (verifier == null) {
            Class var2 = NECaptchaVerifier.class;
            synchronized(NECaptchaVerifier.class) {
                if (verifier == null) {
                    return new NECaptchaVerifier(captchaId, secretPair);
                }
            }
        }

        return verifier;
    }

    private NECaptchaVerifier(String captchaId, NESecretPair secretPair) {
        Validate.notBlank(captchaId, "captchaId为空", new Object[0]);
        Validate.notNull(secretPair, "secret为null", new Object[0]);
        Validate.notBlank(secretPair.secretId, "secretId为空", new Object[0]);
        Validate.notBlank(secretPair.secretKey, "secretKey为空", new Object[0]);
        this.captchaId = captchaId;
        this.secretPair = secretPair;
    }

    public boolean verify(String validate, String user) {
        if (!StringUtils.isEmpty(validate) && !StringUtils.equals(validate, "null")) {
            user = user == null ? "" : user;
            Map<String, String> params = new HashMap();
            params.put("captchaId", this.captchaId);
            params.put("validate", validate);
            params.put("user", user);
            params.put("secretId", this.secretPair.secretId);
            params.put("version", "v2");
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("nonce", String.valueOf(ThreadLocalRandom.current().nextInt()));
            String signature = sign(this.secretPair.secretKey, params);
            params.put("signature", signature);
            String resp = HttpClient4Utils.sendPost("http://c.dun.163yun.com/api/v2/verify", params);
            System.out.println("resp = " + resp);
            return this.verifyRet(resp);
        } else {
            return false;
        }
    }

    public static String sign(String secretKey, Map<String, String> params) {
        String[] keys = (String[])params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuffer sb = new StringBuffer();
        String[] var4 = keys;
        int var5 = keys.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String key = var4[var6];
            sb.append(key).append((String)params.get(key));
        }

        sb.append(secretKey);

        try {
            return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var8) {
            log.error("sign failed", var8);
            return null;
        }
    }

    private boolean verifyRet(String resp) {
        if (StringUtils.isEmpty(resp)) {
            return false;
        } else {
            try {
                JSONObject j = JSONObject.parseObject(resp);
                return j.getBoolean("result");
            } catch (Exception var3) {
                return false;
            }
        }
    }
}
