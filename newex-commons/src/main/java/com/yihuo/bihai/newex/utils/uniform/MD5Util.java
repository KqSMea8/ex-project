package com.yihuo.bihai.newex.utils.uniform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:18
 */
public class MD5Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public MD5Util() {
    }

    public static String getFileMD5String(File file) {
        String ret = "";
        FileInputStream in = null;
        FileChannel ch = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            ch = in.getChannel();
            ByteBuffer byteBuffer = ch.map(MapMode.READ_ONLY, 0L, file.length());
            messageDigest.update(byteBuffer);
            ret = bytesToHex(messageDigest.digest());
        } catch (Exception var18) {
            LOGGER.error(var18.getMessage(), var18);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var17) {
                    LOGGER.error(var17.getMessage(), var17);
                }
            }

            if (ch != null) {
                try {
                    ch.close();
                } catch (IOException var16) {
                    LOGGER.error(var16.getMessage(), var16);
                }
            }

        }

        return ret;
    }

    public static String getFileMD5String(String fileName) {
        return getFileMD5String(new File(fileName));
    }

    public static String getMD5String(String str) {
        Object var1 = null;

        byte[] bytes;
        try {
            bytes = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException var3) {
            bytes = str.getBytes();
            LOGGER.error(var3.getMessage(), var3);
        }

        return getMD5String(bytes);
    }

    public static String getMD5String(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException var2) {
            LOGGER.error(var2.getMessage(), var2);
            return "";
        }
    }

    public static boolean checkPassword(String pwd, String md5) {
        return getMD5String(pwd).equalsIgnoreCase(md5);
    }

    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);
    }

    public static boolean checkFileMD5(File file, String md5) {
        return getFileMD5String(file).equalsIgnoreCase(md5);
    }

    public static boolean checkFileMD5(String fileName, String md5) {
        return checkFileMD5(new File(fileName), md5);
    }

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    public static String bytesToHex(byte[] bytes, int start, int end) {
        StringBuilder sb = new StringBuilder();

        for(int i = start; i < start + end; ++i) {
            sb.append(byteToHex(bytes[i]));
        }

        return sb.toString();
    }

    public static String byteToHex(byte bt) {
        return HEX_DIGITS[(bt & 240) >> 4] + "" + HEX_DIGITS[bt & 15];
    }

    public static long byteToInt(String plainText) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var6) {
            LOGGER.error(var6.getMessage(), var6);
        }

        messageDigest.update(plainText.getBytes());
        byte[] b = messageDigest.digest();
        StringBuffer buf = new StringBuffer("");

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                buf.append("0");
            }

            buf.append(Integer.toOctalString(i));
        }

        System.out.println(buf.toString());
        String result = buf.toString().substring(8, 24);
        return StringUtil.toLong(result, 0L);
    }

    public static String encodeBySha1(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);

        for(int j = 0; j < len; ++j) {
            buf.append(HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(HEX_DIGITS[bytes[j] & 15]);
        }

        return buf.toString();
    }

    public static String byteArrayToString(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] b = messageDigest.digest(text.getBytes());
            StringBuffer resultSb = new StringBuffer();

            for(int i = 0; i < b.length; ++i) {
                resultSb.append(byteToNumString(b[i]));
            }

            return resultSb.toString();
        } catch (Exception var5) {
            LOGGER.error(var5.getMessage(), var5);
            return "0";
        }
    }

    private static String byteToNumString(byte b) {
        int _b = b;
        if (b < 0) {
            _b = 256 + b;
        }

        return String.valueOf(_b);
    }

    public static final int byteArrayToShort(byte[] b) {
        return (b[0] << 8) + (b[1] & 255);
    }

    public static Long test(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(text.getBytes());
            byte[] b = messageDigest.digest();
            System.out.println(bytesToHex(b));
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(text.getBytes());
            b = messageDigest.digest();
            System.out.println(bytesToHex(b));
        } catch (Exception var3) {
            LOGGER.error(var3.getMessage(), var3);
        }

        return 0L;
    }

    public static String getMD5String16(String plainText) {
        String result = getMD5String(plainText);
        return result != null && result.length() > 24 ? result.substring(8, 24) : result;
    }

    public static String getMD5String64(String plainText) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            md5.update(plainText.getBytes());
            return bytesToHex(md5.digest());
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String str = "ccccasdfedafde";
        System.out.println(getMD5String("ccccasdfedafde"));
        System.out.println(getMD5String16("ccccasdfedafde"));
        System.out.println(getMD5String64("ccccasdfedafde"));
    }
}
