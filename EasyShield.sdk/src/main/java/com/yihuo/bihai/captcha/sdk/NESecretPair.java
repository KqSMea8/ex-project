package com.yihuo.bihai.captcha.sdk;

public class NESecretPair {
    public final String secretId;
    public final String secretKey;
    public static volatile NESecretPair neSecretPair = null;

    public static NESecretPair getInstance(String secretId, String secretKey) {
        if (neSecretPair == null) {
            Class var2 = NESecretPair.class;
            synchronized(NESecretPair.class) {
                if (neSecretPair == null) {
                    return new NESecretPair(secretId, secretKey);
                }
            }
        }

        return neSecretPair;
    }

    private NESecretPair(String secretId, String secretKey) {
        this.secretId = secretId;
        this.secretKey = secretKey;
    }
}
