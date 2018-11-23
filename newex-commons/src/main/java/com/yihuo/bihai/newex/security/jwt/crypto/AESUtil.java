package com.yihuo.bihai.newex.security.jwt.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:54
 */
public class AESUtil {
    public AESUtil() {
    }

    public static String encrypt(String content, String key) {
        Validate.notNull(content, "content参数不能为null", new Object[0]);
        Validate.notNull(key, "key参数不能为null", new Object[0]);

        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return (new Base64()).encodeToString(encrypted);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException var6) {
            throw new RuntimeException(var6);
        }
    }

    public static String decrypt(String content, String key) {
        Validate.notNull(content, "content参数不能为null", new Object[0]);
        Validate.notNull(key, "key参数不能为null", new Object[0]);

        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = (new Base64()).decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException var7) {
            throw new RuntimeException(var7);
        }
    }
}
