package com.yihuo.bihai.newex.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.yihuo.bihai.newex.auth.UserUniformQuery;
import com.yihuo.bihai.newex.bean.KycTemplateDTO;
import com.yihuo.bihai.newex.bean.UserKycTemplateDTO;
import com.yihuo.bihai.newex.bean.UserUniformBean;
import com.yihuo.bihai.newex.utils.uniform.AES;
import com.yihuo.bihai.newex.utils.uniform.StringUtil;
import com.yihuo.bihai.newex.utils.uniform.UniformJedisUtil;
import com.yihuo.bihai.newex.utils.uniform.exception.RequireTwoStepAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
/**
 * @author <a href="mailto:dragonjackielee@163.com">������</a>
 * @since 2018/10/30 ����10:25
 */
public class CertificateUniform {
    private final int UNIFORM_EMAIL_PHONE_GOOGLE = 600;
    private static UniformJedisUtil jedisUtil;
    private static volatile CertificateUniform certificateUniform;
    private final Supplier<List<KycTemplateDTO>> listSupplier;
    Logger log;
    private final String HOST;

    /** @deprecated */
    @Deprecated
    public static CertificateUniform getInstance(String authHost, String redisHost, String redisPasswd, String AES_KEY, String SIGN_KEY) {
        return getInstance(authHost, redisHost, redisPasswd, AES_KEY, SIGN_KEY, 1);
    }

    /** @deprecated */
    @Deprecated
    public static CertificateUniform getInstance(String authHost, String redisHost, String redisPasswd, String AES_KEY, String SIGN_KEY, int referer) {
        return getInstance(authHost, redisHost, redisPasswd, AES_KEY, SIGN_KEY, referer, 0);
    }

    public static CertificateUniform getInstance(String authHost, String redisHost, String redisPasswd, String AES_KEY, String SIGN_KEY, int referer, int siteId) {
        return getInstance(authHost, redisHost, redisPasswd, AES_KEY, SIGN_KEY, referer, siteId, null);
    }

    public static CertificateUniform getInstance(String authHost, String redisHost, String redisPasswd, String AES_KEY, String SIGN_KEY, int referer, int siteId, RestTemplate restTemplate) {
        if (certificateUniform == null) {
            Class var8 = CertificateUniform.class;
            synchronized(CertificateUniform.class) {
                if (certificateUniform == null) {
                    certificateUniform = new CertificateUniform(authHost);
                    HttpClientUniformUtil.restTemplate = restTemplate;
                    HttpClientUniformUtil.AES_KEY = AES_KEY;
                    HttpClientUniformUtil.SIGN_KEY = SIGN_KEY;
                    HttpClientUniformUtil.referer = referer;
                    HttpClientUniformUtil.siteId = siteId;
                    jedisUtil = UniformJedisUtil.getInstance(redisHost, redisPasswd);
                }
            }
        }

        return certificateUniform;
    }

    private CertificateUniform(String HOST) {
        Supplier<List<KycTemplateDTO>> supplier = this::findAllTemplateRemote;
        this.listSupplier = Suppliers.memoizeWithExpiration(supplier, 10L, TimeUnit.MINUTES);
        this.log = LoggerFactory.getLogger(CertificateUniform.class);
        this.HOST = HOST;
    }

    public UserUniformBean getUserUniformByIdNumber(String idNumber) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformByIdNumber.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idNumber", idNumber);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public List<Map<String, String>> getUserUniformByUserIdRows(String userId, String rows) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformByUserIdRows.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("rows", rows);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, String>> resultList = back != null && back.get("returnValue") != null ? (List)JSON.parseObject(back.get("returnValue").toString(), new TypeReference<List<Map<String, String>>>() {
        }, new Feature[0]) : null;
        return resultList;
    }

    public List<Map<String, String>> getUserUniformByUserIds(String userIds) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformByUserIds.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userIds", userIds);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, String>> resultList = back != null && back.get("returnValue") != null ? (List)JSON.parseObject(back.get("returnValue").toString(), new TypeReference<List<Map<String, String>>>() {
        }, new Feature[0]) : null;
        return resultList;
    }

    public long updateUniformIdNumber(long userId, String idNumber) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUniformIdNumber.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("idNumber", idNumber);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    /** @deprecated */
    @Deprecated
    public UserUniformBean getUserUniformByLoginName(String logingName) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformByLoginName.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", logingName);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public UserUniformBean getUserUniformByLoginName(String logingName, int areaCode) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformByLoginName.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", logingName);
        jsonObject.put("areaCode", areaCode);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    /** @deprecated */
    @Deprecated
    public UserUniformBean getUserUniformBean(String logingName, String password) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformBean.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", logingName);
        jsonObject.put("password", password);
        JSONObject back = null;

        try {
            back = HttpClientUniformUtil.postMsg(url, jsonObject);
        } catch (Exception var7) {
            this.log.error("getUserUniformBean,loginName=" + logingName, var7);
            return null;
        }

        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public UserUniformBean getUserUniformBean(String logingName, int areaCode, String password) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformBean.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", logingName);
        jsonObject.put("password", password);
        jsonObject.put("areaCode", areaCode);
        JSONObject back = null;

        try {
            back = HttpClientUniformUtil.postMsg(url, jsonObject);
        } catch (Exception var8) {
            this.log.error("getUserUniformBean,loginName=" + logingName, var8);
            return null;
        }

        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    /** @deprecated */
    @Deprecated
    public UserUniformBean getUserUniformBean(String loginName, String password, String ip, String deviceId, String userAgent) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformBean.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", loginName);
        jsonObject.put("password", password);
        if (ip != null) {
            jsonObject.put("ip", ip);
        }

        if (deviceId != null) {
            jsonObject.put("deviceId", deviceId);
        }

        if (userAgent != null) {
            jsonObject.put("userAgent", userAgent);
        }

        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        if (back != null && back.getBooleanValue("requireTwoStepAuth")) {
            throw new RequireTwoStepAuthException(userUniformBean);
        } else {
            return userUniformBean;
        }
    }

    public UserUniformBean getUserUniformBean(String loginName, int areaCode, String password, String ip, String deviceId, String userAgent) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformBean.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", loginName);
        jsonObject.put("password", password);
        jsonObject.put("areaCode", areaCode);
        if (ip != null) {
            jsonObject.put("ip", ip);
        }

        if (deviceId != null) {
            jsonObject.put("deviceId", deviceId);
        }

        if (userAgent != null) {
            jsonObject.put("userAgent", userAgent);
        }

        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        if (back != null && back.getBooleanValue("requireTwoStepAuth")) {
            throw new RequireTwoStepAuthException(userUniformBean);
        } else {
            return userUniformBean;
        }
    }

    public void passTwoStepAuth(long userId, String ip, String deviceId, String userAgent) {
        String url = this.HOST + "/v1/certificateUniform/passTwoStepAuth.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("ip", ip);
        jsonObject.put("deviceId", deviceId);
        jsonObject.put("userAgent", userAgent);
        HttpClientUniformUtil.postMsg(url, jsonObject);
    }

    public long insertUserUniform(String loginName, String password, int passwordFlag, int from) {
        return this.insertUserUniform(loginName, password, passwordFlag, from, 0);
    }

    public long insertUserUniform(String loginName, String password, int passwordFlag, int from, int channelId) {
        return this.insertUserUniform(loginName, password, passwordFlag, from, channelId, "86");
    }

    public long insertUserUniform(String loginName, String password, int passwordFlag, int from, int channelId, String areaCode) {
        return this.insertUserUniform(loginName, password, passwordFlag, from, channelId, areaCode, 0);
    }

    public long insertUserUniform(String loginName, String password, int passwordFlag, int from, int channelId, String areaCode, int userFrom) {
        Long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/insertUserUniform.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginName", loginName);
        jsonObject.put("password", password);
        jsonObject.put("passwordFlag", passwordFlag);
        jsonObject.put("from", from);
        jsonObject.put("channelId", channelId);
        jsonObject.put("areaCode", areaCode);
        jsonObject.put("userFrom", userFrom);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long insertSubUserUniform(long masterId, String password, String nikeName, int passwordFlag, int from) {
        Long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/insertSubUserUniform.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("masterId", masterId);
        jsonObject.put("password", password);
        jsonObject.put("nikeName", nikeName);
        jsonObject.put("passwordFlag", passwordFlag);
        jsonObject.put("from", from);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updateUserUniformPwd(long userId, String password, int passwordFlag, int passwordType) {
        return this.updateUserUniformPwd(userId, password, passwordFlag, passwordType, 0L);
    }

    public long updateUserUniformPwd(long userId, String password, int passwordFlag, int passwordType, long ip) {
        Long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUserUniformPwd.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("password", password);
        jsonObject.put("passwordFlag", passwordFlag);
        jsonObject.put("passwordType", passwordType);
        jsonObject.put("ip", ip);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateUniformGoogelKey(long userId, String googleKey) {
        return this.updateUniformGoogelKey(userId, googleKey, 0L);
    }

    public long updateUniformGoogelKey(long userId, String googleKey, long ip) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUniformGoogelKey.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("googleKey", googleKey);
        jsonObject.put("ip", ip);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long synchronizationAccount(String email, String nikeName, String password, String tradePwd, String phone, String totpKey, int from) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/synchronizationAccount.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("nikeName", nikeName);
        jsonObject.put("password", password);
        jsonObject.put("tradePwd", tradePwd);
        jsonObject.put("phone", phone);
        jsonObject.put("totpKey", totpKey);
        jsonObject.put("from", from);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public UserUniformBean getUserUniformBeanById(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformBeanById.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public UserUniformBean getUserDetailByComUserId(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getUserDetailByComUserId.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("uniformBean") != null ? (UserUniformBean)JSON.parseObject(back.get("uniformBean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public long updateUniformEmail(long userId, String email, String loginPwd, int pwdFlag) {
        return this.updateUniformEmail(userId, email, loginPwd, pwdFlag, 0L);
    }

    public long updateUniformEmail(long userId, String email, String loginPwd, int pwdFlag, long ip) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUniformEmail.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("email", email);
        jsonObject.put("password", loginPwd);
        jsonObject.put("pwdFlag", pwdFlag);
        jsonObject.put("ip", ip);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateUniformPhone(long userId, String phone) {
        return this.updateUniformPhone(userId, phone, "86", 0L);
    }

    public long updateUniformPhone(long userId, String phone, String areaCode, long ip) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUniformPhone.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("phone", phone);
        jsonObject.put("areaCode", areaCode);
        jsonObject.put("ip", ip);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long generateUniformGoogelKey(long userId, String googleKey) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/generateUniformGoogelKey.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("googleKey", googleKey);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public JSONObject validateUniformGoogelCode(long userId, String googleCode) {
        String url = this.HOST + "/v1/certificateUniform/validateUniformGoogelCode.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("googleCode", googleCode);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        return back;
    }

    public JSONObject getUniformGoogelKey(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getUniformGoogelKey.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        return back;
    }

    public JSONObject getPassword(long userId, String originPassword) {
        String url = this.HOST + "/v1/certificateUniform/getPassword.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("originPassword", originPassword);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        return back;
    }

    /** @deprecated */
    @Deprecated
    public long updateUniformGoogleKey(long userId, String phone, String totpKey) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUniformGoogleKey.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("phone", phone);
        jsonObject.put("totpKey", totpKey);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public JSONObject validatePassword(long userId, String originPassword, int type) {
        String url = this.HOST + "/v1/certificateUniform/validatePassword.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("originPassword", originPassword);
        jsonObject.put("type", type);
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        return back;
    }

    public JSONObject validatePassword(long userId, String originPassword, int type, int referer) {
        String url = this.HOST + "/v1/certificateUniform/validatePassword.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("originPassword", originPassword);
        jsonObject.put("type", type);
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject, referer);
        return back;
    }

    public long updateRealName(long userId, String realName) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateRealName.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("realName", realName);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long unBindPhone(long userId, int filed, String filedValue) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/unBindPhone.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("filed", filed);
        jsonObject.put("filedValue", filedValue);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public String getUserIdByRealName(String realName, int from) {
        String returnValue = null;
        String url = this.HOST + "/v1/certificateUniform/getUserIdByRealName.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("realName", realName);
        jsonObject.put("from", from);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getString("returnValue") : returnValue;
        return returnValue;
    }

    /** @deprecated */
    @Deprecated
    public long updateDetailToUniform(long userId, int from) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateDetailToUniform.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("from", from);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updateSysUserWallet(long userId, String email, String phone, String loginPwd, int from, int pwdFlag, String areaCode, int channelId) {
        long returnValue = -1L;
        String url = this.HOST + "/v1/certificateUniform/updateSysUserWallet.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("email", email);
        jsonObject.put("phone", phone);
        jsonObject.put("loginPwd", loginPwd);
        jsonObject.put("from", from);
        jsonObject.put("pwdFlag", pwdFlag);
        jsonObject.put("areaCode", areaCode);
        jsonObject.put("channelId", channelId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long regSysUserWithoutPasswdWallet(String loginName, int from, String areaCode, int channelId) {
        long returnValue = -1L;
        String url = this.HOST + "/v1/certificateUniform/regSysUserWithoutPasswdWallet.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginName", loginName);
        jsonObject.put("from", from);
        jsonObject.put("areaCode", areaCode);
        jsonObject.put("channelId", channelId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public String getWalletUserId(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getWalletUserId.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        return back.getString("walletUserId");
    }

    public UserUniformBean getUserByPhoneAndAreaCode(String phone, String areaCode) {
        String url = this.HOST + "/v1/certificateUniform/getUserByPhoneAndAreaCode.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("areaCode", areaCode);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public Map<String, String> getUniformRedisMap(long userId) {
        String key = AES.encrypt(String.valueOf(userId));
        Map<String, String> map = jedisUtil.hgetAll(key);
        if (map != null && ((Map)map).size() > 0) {
            return (Map)map;
        } else {
            UserUniformBean userUniformBean = this.getUserUniformBeanById(userId);
            if (userUniformBean != null) {
                String email = StringUtil.getString(userUniformBean.getEmail());
                String phone = StringUtil.getString(userUniformBean.getPhone());
                String google = StringUtil.getString(userUniformBean.getTotpKey());
                String loginPwd = StringUtil.isEmpty(userUniformBean.getLoginPwd()) ? "" : "1";
                String tradePwd = StringUtil.isEmpty(userUniformBean.getTradePwd()) ? "" : "1";
                String realName = StringUtil.getString(userUniformBean.getRealName());
                String idNumber = StringUtil.getString(userUniformBean.getIdNumber());
                String channelId = String.valueOf(userUniformBean.getChannelId());
                String from = String.valueOf(userUniformBean.getFrom());
                String areaCode = String.valueOf(userUniformBean.getAreaCode());
                String masterAccountId = String.valueOf(userUniformBean.getMasterAccountId());
                String deleteFlag = String.valueOf(userUniformBean.getDeleteFlag());
                map = new HashMap(10);
                ((Map)map).put("email", email);
                ((Map)map).put("phone", phone);
                ((Map)map).put("google", google);
                ((Map)map).put("loginPwd", loginPwd);
                ((Map)map).put("tradePwd", tradePwd);
                ((Map)map).put("realName", realName);
                ((Map)map).put("idNumber", idNumber);
                ((Map)map).put("channelId", channelId);
                ((Map)map).put("from", from);
                ((Map)map).put("areaCode", areaCode);
                ((Map)map).put("userId", String.valueOf(userId));
                ((Map)map).put("authLogin", String.valueOf(userUniformBean.getAuthLogin()));
                ((Map)map).put("authTrade", String.valueOf(userUniformBean.getAuthTrade()));
                ((Map)map).put("emailValidateFlag", String.valueOf(userUniformBean.getEmailValidateFlag()));
                ((Map)map).put("tradePwdFlag", String.valueOf(userUniformBean.getTradePwdFlag()));
                ((Map)map).put("emailVerify", StringUtil.isEmpty(userUniformBean.getEmailVerify()) ? "" : userUniformBean.getEmailVerify());
                ((Map)map).put("masterAccountId", masterAccountId);
                ((Map)map).put("deleteFlag", deleteFlag);
                jedisUtil.hmset(key, (Map)map);
                UniformJedisUtil var10000 = jedisUtil;
                this.getClass();
                var10000.expire(key, 600);
            } else {
                this.log.error("Get user from Auth is null.UserId: {}. Redis key: {}.", userId, key);
            }

            return (Map)map;
        }
    }

    public void putUniformRedis(long userId, Map<String, String> map) {
        String key = AES.encrypt(String.valueOf(userId));
        jedisUtil.hmset(key, map);
        UniformJedisUtil var10000 = jedisUtil;
        this.getClass();
        var10000.expire(key, 600);
    }

    public void updateEmailPhoneGoogle(long userId, int type, String newValue) {
        Map<String, String> map = this.getUniformRedisMap(userId);
        if (map != null && map.size() != 0) {
            switch(type) {
            case 0:
                map.put("email", newValue);
                break;
            case 1:
                map.put("phone", newValue);
                break;
            case 2:
                map.put("google", newValue);
                break;
            case 3:
                if (Integer.valueOf(newValue) == 0 || !StringUtil.isEmpty((String)map.get("tradePwd"))) {
                    return;
                }

                map.put("tradePwd", "1");
                break;
            case 4:
                map.put("realName", newValue);
                break;
            case 5:
                map.put("idNumber", newValue);
            }

            this.putUniformRedis(userId, map);
        }
    }

    public void delField(long userId, int type) {
        Map<String, String> map = this.getUniformRedisMap(userId);
        if (map != null && map.size() != 0) {
            String key = AES.encrypt(String.valueOf(userId));
            switch(type) {
            case 1:
                jedisUtil.hset(key, "google", "");
                break;
            case 2:
                jedisUtil.hset(key, "phone", "");
            }

        }
    }

    public long insertUserOperator(long userId, int type, int status, String phone, String desc, long ip) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/insertUserOperator.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        jsonObject.put("status", status);
        jsonObject.put("phone", StringUtil.isEmpty(phone) ? "" : phone);
        jsonObject.put("ip", ip);
        jsonObject.put("desc", StringUtil.isEmpty(desc) ? "" : desc);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updateUserOperator(long id, int type, int status, String phone, String desc, long createdDate) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUserOperator.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("type", type);
        jsonObject.put("status", status);
        jsonObject.put("phone", StringUtil.isEmpty(phone) ? "" : phone);
        jsonObject.put("createdDate", createdDate);
        jsonObject.put("desc", StringUtil.isEmpty(desc) ? "" : desc);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public List<Map<String, Object>> getUserOperator(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getUserOperator.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, Object>> operator = back != null && back.get("operator") != null ? (List)JSON.parseObject(back.get("operator").toString(), new TypeReference<List<Map<String, Object>>>() {
        }, new Feature[0]) : null;
        return operator;
    }

    public int getCountOfUserOperatorByType(long userId, int type) {
        int returnValue = 0;
        String url = this.HOST + "/v1/certificateUniform/getCountOfUserOperatorByType.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getIntValue("returnValue") : returnValue;
        return returnValue;
    }

    public List<Map<String, Object>> getUserOperatorByType(long userId, int type, int start, int size) {
        String url = this.HOST + "/v1/certificateUniform/getUserOperatorByType.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        jsonObject.put("start", start);
        jsonObject.put("size", size);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, Object>> userOperators = back != null && back.get("operators") != null ? (List)JSON.parseObject(back.get("operators").toString(), new TypeReference<List<Map<String, Object>>>() {
        }, new Feature[0]) : null;
        return userOperators;
    }

    public List<Map<String, Object>> getUserOperatorByTypeStatus(int status, int type, int size) {
        String url = this.HOST + "/v1/certificateUniform/getUserOperatorByTypeStatus.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("type", type);
        jsonObject.put("size", size);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, Object>> userOperators = back != null && back.get("operators") != null ? (List)JSON.parseObject(back.get("operators").toString(), new TypeReference<List<Map<String, Object>>>() {
        }, new Feature[0]) : null;
        return userOperators;
    }

    public boolean isConflictUser(long userId) {
        String url = this.HOST + "/v1/certificateUniform/isConflictUser.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        boolean returnValue = back != null ? back.getBooleanValue("returnValue") : false;
        return returnValue;
    }

    /** @deprecated */
    @Deprecated
    public UserUniformBean getConflictUser(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getConflictUser.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        UserUniformBean userUniformBean = back != null && back.get("useruniformbean") != null ? (UserUniformBean)JSON.parseObject(back.get("useruniformbean").toString(), UserUniformBean.class) : null;
        return userUniformBean;
    }

    public long mergerConflictUser(long userId) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/mergerConflictUser.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updatePassportName(long userId, String passportName) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updatePassportName.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("passportName", passportName);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updatePassportNumber(long userId, String passportNumber) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updatePassportNumber.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("passportNumber", passportNumber);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public List<UserUniformBean> getUserUniformList(UserUniformQuery userUniformQuery) {
        String url = this.HOST + "/v1/certificateUniform/getUserUniformList.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonRequest", JSON.toJSONString(userUniformQuery));
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        if (back == null) {
            return Collections.emptyList();
        } else {
            JSONArray responseJson = back.getJSONArray("responseJson");
            return responseJson == null ? Collections.emptyList() : responseJson.toJavaList(UserUniformBean.class);
        }
    }

    public List<KycTemplateDTO> findAllTemplate() {
        List<KycTemplateDTO> memory = (List)this.listSupplier.get();
        this.log.debug("template from cache: {}", memory);
        if (memory != null && !memory.isEmpty()) {
            return memory;
        } else {
            this.log.debug("go remote");
            return this.findAllTemplateRemote();
        }
    }

    private List<KycTemplateDTO> findAllTemplateRemote() {
        String url = this.HOST + "/v1/certificateUniform/kyc/findAllTemplate.do";
        JSONObject jsonObject = new JSONObject();
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        this.log.debug("findAllTemplateRemote, result: {}", back);
        if (back == null) {
            return Collections.emptyList();
        } else {
            JSONArray responseJson = back.getJSONArray("responseJson");
            return responseJson == null ? Collections.emptyList() : responseJson.toJavaList(KycTemplateDTO.class);
        }
    }

    public Optional<KycTemplateDTO> findTemplateById(int templateId) {
        return this.findAllTemplate().stream().filter((x) -> {
            return Objects.equals(templateId, x.getTemplateId());
        }).filter((x) -> {
            return Objects.equals(0, x.getDelFlag());
        }).findAny();
    }

    public List<UserKycTemplateDTO> findUserKycTemplateByUserId(long userId) {
        String url = this.HOST + "/v1/certificateUniform/kyc/findUserKycTemplateByUserId.do";
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("jsonRequest", params);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonRequest);
        if (back == null) {
            return Collections.emptyList();
        } else {
            JSONArray responseJson = back.getJSONArray("responseJson");
            return responseJson == null ? Collections.emptyList() : responseJson.toJavaList(UserKycTemplateDTO.class);
        }
    }

    public long newUserTemplate(long userId, int templateId, int status) {
        String url = this.HOST + "/v1/certificateUniform/kyc/newUserTemplate.do";
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("templateId", templateId);
        params.put("status", status);
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("jsonRequest", params);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonRequest);
        return back == null ? -1L : back.getLong("responseJson");
    }

    public boolean updateUserTemplateStatus(long userId, int templateId, int status) {
        String url = this.HOST + "/v1/certificateUniform/kyc/updateUserTemplateStatus.do";
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("templateId", templateId);
        params.put("status", status);
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("jsonRequest", params);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonRequest);
        return back == null ? false : back.getBoolean("responseJson");
    }

    public List<KycTemplateDTO> findTemplateByCountryIdAndLevel(String countryId, int level) {
        return (List)this.findAllTemplate().stream().filter((x) -> {
            return Objects.equals(countryId, x.getCountryId());
        }).filter((x) -> {
            return Objects.equals(level, x.getLevel());
        }).filter((x) -> {
            return Objects.equals(0, x.getDelFlag());
        }).collect(Collectors.toList());
    }

    public List<KycTemplateDTO> findTemplateByUserId(long userId) {
        return (List)this.findUserKycTemplateByUserId(userId).stream().map((x) -> {
            return this.findTemplateById(x.getTemplateId());
        }).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public long updateAuthLogin(long userId, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateAuthLogin.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateAuthTrade(long userId, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateAuthTrade.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateEmailValidateFlag(long userId, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateEmailValidateFlag.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateTradePwdFlag(long userId, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateTradePwdFlag.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public long updateEmailVerify(long userId, String emailVerify) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateEmailVerify.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("emailVerify", emailVerify);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        if (returnValue > 0L) {
            jedisUtil.del(AES.encrypt(String.valueOf(userId)));
        }

        return returnValue;
    }

    public List<Map<String, Object>> getUserConfigure(long userId, int type) {
        String url = this.HOST + "/v1/certificateUniform/getUserConfigure.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        List<Map<String, Object>> userConfigure = back != null && back.get("userConfigure") != null ? (List)JSON.parseObject(back.get("userConfigure").toString(), List.class) : null;
        return userConfigure;
    }

    public long updateUserConfigure(long userId, int type, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateUserConfigure.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long insertUserConfigure(long userId, int type, int status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/insertUserConfigure.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("type", type);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public List<Map<String, Object>> getSubUserInfoByMasterAccountId(long userId) {
        String url = this.HOST + "/v1/certificateUniform/getSubUserInfoByMasterAccountId.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject result = HttpClientUniformUtil.postMsg(url, jsonObject);
        if (result != null && result.get("subAccountList") != null) {
            List<Map<String, Object>> subAccountList = (List)JSON.parseObject(result.get("subAccountList").toString(), List.class);
            return subAccountList;
        } else {
            return null;
        }
    }

    public long freezeAccount(long userId, Integer status, String remark) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/freezeAccount.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("remark", remark);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long deletedAccount(long userId, Integer status) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/deletedAccount.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("status", status);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long unbindGoogle(long userId, long authLogin, long authTrade) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/unbindGoogle.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("authLogin", authLogin);
        jsonObject.put("authTrade", authTrade);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("unbindGoogleResult") : returnValue;
        return returnValue;
    }

    public long unbindPhone(long userId, long authLogin, long authTrade) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/unbindPhone.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("authLogin", authLogin);
        jsonObject.put("authTrade", authTrade);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("unbindPhoneResult") : returnValue;
        return returnValue;
    }

    public boolean checkTradePwd(long userId, String tradePwd) {
        boolean returnValue = false;
        String url = this.HOST + "/v1/certificateUniform/checkTradePwd.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("tradePwd", tradePwd);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back.getBooleanValue("checkTradePwd");
        return returnValue;
    }

    public boolean checkTradePwdExist(long userId) {
        boolean returnValue = false;
        String url = this.HOST + "/v1/certificateUniform/checkTradePwdExist.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back.getBooleanValue("checkTradePwdExist");
        return returnValue;
    }

    public List<UserKycTemplateDTO> getUserKycTemplateByUserIdAndSiteId(long userId, Integer siteId) {
        String url = this.HOST + "/v1/certificateUniform/getUserKycTemplateByUserIdAndSiteId.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("siteId", siteId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        String result = back.getString("userKycTemplateDTOList");
        List<UserKycTemplateDTO> returnValue = JSON.parseArray(result, UserKycTemplateDTO.class);
        return returnValue;
    }

    public long updateNickNameByFirstFrom(long userId) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateNickNameByFirstFrom.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }

    public long updateNickNameByUserId(long userId, String nickName) {
        long returnValue = 0L;
        String url = this.HOST + "/v1/certificateUniform/updateNickNameByUserId.do";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("nickName", nickName);
        JSONObject back = HttpClientUniformUtil.postMsg(url, jsonObject);
        returnValue = back != null ? back.getLong("returnValue") : returnValue;
        return returnValue;
    }
}
