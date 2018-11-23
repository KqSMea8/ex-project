package com.yihuo.bihai.newex.utils.uniform;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午10:45
 */
public class AES {
    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);

    private static final String key = "uniformFiveParam";

    public AES() {
    }

    public static String encrypt(String content) {
        try {
            if ("uniformFiveParam" == null) {
                System.out.print("Key为空null");
                return null;
            }

            if ("uniformFiveParam".length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = "uniformFiveParam".getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return (new Base64()).encodeToString(encrypted);
        } catch (UnsupportedEncodingException var5) {
            LOGGER.error(var5.getMessage(), var5);
        } catch (NoSuchAlgorithmException var6) {
            LOGGER.error(var6.getMessage(), var6);
        } catch (NoSuchPaddingException var7) {
            LOGGER.error(var7.getMessage(), var7);
        } catch (InvalidKeyException var8) {
            LOGGER.error(var8.getMessage(), var8);
        } catch (IllegalBlockSizeException var9) {
            LOGGER.error(var9.getMessage(), var9);
        } catch (BadPaddingException var10) {
            LOGGER.error(var10.getMessage(), var10);
        }

        return null;
    }

    public static String encrypt(String content, String key) {
        try {
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return (new Base64()).encodeToString(encrypted);
        } catch (UnsupportedEncodingException var6) {
            LOGGER.error(var6.getMessage(), var6);
        } catch (NoSuchAlgorithmException var7) {
            LOGGER.error(var7.getMessage(), var7);
        } catch (NoSuchPaddingException var8) {
            LOGGER.error(var8.getMessage(), var8);
        } catch (InvalidKeyException var9) {
            LOGGER.error(var9.getMessage(), var9);
        } catch (IllegalBlockSizeException var10) {
            LOGGER.error(var10.getMessage(), var10);
        } catch (BadPaddingException var11) {
            LOGGER.error(var11.getMessage(), var11);
        }

        return null;
    }

    public static String urlSafeEncrypt(String content, String key) {
        try {
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            String encodeStr = (new Base64()).encodeToString(encrypted);
            return urlsafe_b64encode(encodeStr);
        } catch (UnsupportedEncodingException var7) {
            LOGGER.error(var7.getMessage(), var7);
        } catch (NoSuchAlgorithmException var8) {
            LOGGER.error(var8.getMessage(), var8);
        } catch (NoSuchPaddingException var9) {
            LOGGER.error(var9.getMessage(), var9);
        } catch (InvalidKeyException var10) {
            LOGGER.error(var10.getMessage(), var10);
        } catch (IllegalBlockSizeException var11) {
            LOGGER.error(var11.getMessage(), var11);
        } catch (BadPaddingException var12) {
            LOGGER.error(var12.getMessage(), var12);
        }

        return null;
    }

    public static String decrypt(String content) {
        try {
            if ("uniformFiveParam" == null) {
                System.out.print("Key为空null");
                return null;
            }

            if ("uniformFiveParam".length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = "uniformFiveParam".getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = (new Base64()).decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (UnsupportedEncodingException var7) {
            ;
        } catch (NoSuchAlgorithmException var8) {
            ;
        } catch (NoSuchPaddingException var9) {
            ;
        } catch (InvalidKeyException var10) {
            ;
        } catch (IllegalBlockSizeException var11) {
            ;
        } catch (BadPaddingException var12) {
            ;
        }

        return null;
    }

    public static String decrypt(String content, String key) {
        try {
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = (new Base64()).decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (UnsupportedEncodingException var8) {
            ;
        } catch (NoSuchAlgorithmException var9) {
            ;
        } catch (NoSuchPaddingException var10) {
            ;
        } catch (InvalidKeyException var11) {
            ;
        } catch (IllegalBlockSizeException var12) {
            ;
        } catch (BadPaddingException var13) {
            ;
        }

        return null;
    }

    public static String urlSafeDecrypt(String content, String key) {
        try {
            content = urlsafe_b64decode(content);
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = (new Base64()).decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (UnsupportedEncodingException var8) {
            ;
        } catch (NoSuchAlgorithmException var9) {
            ;
        } catch (NoSuchPaddingException var10) {
            ;
        } catch (InvalidKeyException var11) {
            ;
        } catch (IllegalBlockSizeException var12) {
            ;
        } catch (BadPaddingException var13) {
            ;
        }

        return null;
    }

    public static String urlsafe_b64encode(String content) {
        if (!StringUtils.isEmpty(content)) {
            content = content.replace("/", "-");
        }

        return content;
    }

    public static String urlsafe_b64decode(String content) {
        if (!StringUtils.isEmpty(content)) {
            content = content.replace("-", "/");
        }

        return content;
    }

    public static void main(String[] args) {
        String cSrc = "YQXIKK6N2BXM2CVQ";
        System.out.println("YQXIKK6N2BXM2CVQ");
        String enString = encrypt("YQXIKK6N2BXM2CVQ");
        System.out.println("加密后的字串是：" + enString);
        System.out.println("加密后的字串是：" + enString.length());
        String DeString = decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
        System.out.println("解密后的字串是：" + DeString.length());
    }
}
