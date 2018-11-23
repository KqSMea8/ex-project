package com.yihuo.bihai.newex.bean;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;
/**
 * @author <a href="mailto:dragonjackielee@163.com">¿Ó÷«¡˙</a>
 * @since 2018/10/30 …œŒÁ10:44
 */
public class UserUniformBean {
    private Long userId;
    private String email;
    private String phone;
    private String realName;
    private String nickName;
    private String idNumber;
    private String loginPwd;
    private String tradePwd;
    private String totpKey;
    private int pwdFlag;
    private int deleteFlag;
    private long masterAccountId;
    private int from;
    private int channelId;
    private String preTradePwd;
    private String preLoginPwd;
    private String createdDate;
    private String areaCode;
    private long walletUserId;
    private int loginFrom;
    private long comUserId;
    private int conflictFlag;
    private int userFrom;
    private int firstFrom;
    private int authLogin;
    private int authTrade;
    private int emailValidateFlag;
    private int tradePwdFlag;
    private String emailVerify;

    public UserUniformBean() {
    }

    public static UserUniformBean convertToCom(Map<String, Object> map) {
        UserUniformBean result = convert(map);
        if (result != null) {
            result.setUserId(result.getComUserId());
        }

        return result;
    }

    public static UserUniformBean convert(Map<String, Object> map) {
        if (map == null) {
            return null;
        } else {
            long userId = 0L;
            if (map.get("user_id") != null) {
                userId = NumberUtils.toLong(map.get("user_id").toString(), 0L);
                if (userId == 0L) {
                    return null;
                }
            }

            UserUniformBean userUniformBean = new UserUniformBean();
            userUniformBean.setUserId(userId);
            if (map.get("email") != null) {
                userUniformBean.setEmail(map.get("email").toString());
            }

            if (map.get("phone") != null) {
                userUniformBean.setPhone(map.get("phone").toString());
            }

            if (map.get("real_name") != null) {
                userUniformBean.setRealName(map.get("real_name").toString());
            }

            if (map.get("nick_name") != null) {
                userUniformBean.setNickName(map.get("nick_name").toString());
            }

            if (map.get("id_number") != null) {
                userUniformBean.setIdNumber(map.get("id_number").toString());
            }

            if (map.get("login_pwd_encrypt") != null) {
                userUniformBean.setLoginPwd(map.get("login_pwd_encrypt").toString());
            }

            if (map.get("trade_pwd_encrypt") != null) {
                userUniformBean.setTradePwd(map.get("trade_pwd_encrypt").toString());
            }

            if (map.get("totp_encrypt") != null) {
                userUniformBean.setTotpKey(map.get("totp_encrypt").toString());
            }

            if (map.get("pwd_flag") != null) {
                userUniformBean.setPwdFlag(NumberUtils.toInt(map.get("pwd_flag").toString(), 0));
            }

            if (map.get("delete_flag") != null) {
                userUniformBean.setDeleteFlag(NumberUtils.toInt(map.get("delete_flag").toString(), 0));
            }

            if (map.get("master_account_id") != null) {
                userUniformBean.setMasterAccountId(NumberUtils.toLong(map.get("master_account_id").toString(), 0L));
            }

            if (map.get("from") != null) {
                userUniformBean.setFrom(NumberUtils.toInt(map.get("from").toString(), 0));
            }

            if (map.get("channel_id") != null) {
                userUniformBean.setChannelId(NumberUtils.toInt(map.get("channel_id").toString(), 0));
            }

            if (map.get("login_pwd") != null) {
                userUniformBean.setPreLoginPwd(map.get("login_pwd").toString());
            }

            if (map.get("trade_pwd") != null) {
                userUniformBean.setPreTradePwd(map.get("trade_pwd").toString());
            }

            if (map.get("created_date") != null) {
                userUniformBean.setCreatedDate(map.get("created_date").toString());
            }

            if (map.get("area_code") != null) {
                userUniformBean.setAreaCode(map.get("area_code").toString());
            }

            if (map.get("wallet_user_id") != null) {
                userUniformBean.setWalletUserId(NumberUtils.toLong(map.get("wallet_user_id").toString(), 0L));
            }

            if (map.get("com_user_id") != null) {
                userUniformBean.setComUserId(NumberUtils.toLong(map.get("com_user_id").toString(), 0L));
            }

            if (map.get("conflict_flag") != null) {
                userUniformBean.setConflictFlag(NumberUtils.toInt(map.get("conflict_flag").toString(), 0));
            }

            if (map.get("user_from") != null) {
                userUniformBean.setUserFrom(NumberUtils.toInt(map.get("user_from").toString(), 0));
            }

            if (map.get("auth_login") != null) {
                userUniformBean.setAuthLogin(NumberUtils.toInt(map.get("auth_login").toString(), 0));
            }

            if (map.get("auth_trade") != null) {
                userUniformBean.setAuthTrade(NumberUtils.toInt(map.get("auth_trade").toString(), 0));
            }

            if (map.get("email_validate_flag") != null) {
                userUniformBean.setEmailValidateFlag(NumberUtils.toInt(map.get("email_validate_flag").toString(), 0));
            }

            if (map.get("trade_pwd_flag") != null) {
                userUniformBean.setTradePwdFlag(NumberUtils.toInt(map.get("trade_pwd_flag").toString(), 0));
            }

            if (map.get("email_verify") != null) {
                userUniformBean.setEmailVerify(map.get("email_verify").toString());
            }

            if (map.get("first_from") != null) {
                userUniformBean.setFirstFrom(NumberUtils.toInt(map.get("first_from").toString(), 0));
            }

            return userUniformBean;
        }
    }

    public static UserUniformBean convertedBean(String toString) {
        return (UserUniformBean)JSON.parseObject(toString, UserUniformBean.class);
    }

    public int getUserFrom() {
        return this.userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPreTradePwd() {
        return this.preTradePwd;
    }

    public void setPreTradePwd(String preTradePwd) {
        this.preTradePwd = preTradePwd;
    }

    public String getPreLoginPwd() {
        return this.preLoginPwd;
    }

    public void setPreLoginPwd(String preLoginPwd) {
        this.preLoginPwd = preLoginPwd;
    }

    public int getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getTradePwd() {
        return this.tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getTotpKey() {
        return this.totpKey;
    }

    public void setTotpKey(String totpKey) {
        this.totpKey = totpKey;
    }

    public int getPwdFlag() {
        return this.pwdFlag;
    }

    public void setPwdFlag(int pwdFlag) {
        this.pwdFlag = pwdFlag;
    }

    public long getMasterAccountId() {
        return this.masterAccountId;
    }

    public void setMasterAccountId(long masterAccountId) {
        this.masterAccountId = masterAccountId;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public long getWalletUserId() {
        return this.walletUserId;
    }

    public void setWalletUserId(long walletUserId) {
        this.walletUserId = walletUserId;
    }

    public int getLoginFrom() {
        return this.loginFrom;
    }

    public void setLoginFrom(int loginFrom) {
        this.loginFrom = loginFrom;
    }

    public long getComUserId() {
        return this.comUserId;
    }

    public void setComUserId(long comUserId) {
        this.comUserId = comUserId;
    }

    public int getConflictFlag() {
        return this.conflictFlag;
    }

    public void setConflictFlag(int conflictFlag) {
        this.conflictFlag = conflictFlag;
    }

    public boolean isConflict() {
        return this.conflictFlag == 1;
    }

    public int getAuthLogin() {
        if (this.authLogin == 1 && this.isEmpty(this.totpKey)) {
            this.authLogin = 0;
        }

        return this.authLogin;
    }

    public void setAuthLogin(int authLogin) {
        this.authLogin = authLogin;
    }

    public int getAuthTrade() {
        if (this.authTrade >= 2 && this.isEmpty(this.phone)) {
            this.authTrade -= 2;
        }

        if ((this.authTrade == 3 || this.authTrade == 1) && this.isEmpty(this.totpKey)) {
            --this.authTrade;
        }

        return this.authTrade;
    }

    private boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            String tempStr = str.trim();
            if (tempStr.length() == 0) {
                return true;
            } else {
                return null == tempStr;
            }
        }
    }

    public void setAuthTrade(int authTrade) {
        this.authTrade = authTrade;
    }

    public int getEmailValidateFlag() {
        return this.emailValidateFlag;
    }

    public void setEmailValidateFlag(int emailValidateFlag) {
        this.emailValidateFlag = emailValidateFlag;
    }

    public int getTradePwdFlag() {
        return this.tradePwdFlag;
    }

    public void setTradePwdFlag(int tradePwdFlag) {
        this.tradePwdFlag = tradePwdFlag;
    }

    public String getEmailVerify() {
        return this.emailVerify;
    }

    public void setEmailVerify(String emailVerify) {
        this.emailVerify = emailVerify;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFirstFrom() {
        return this.firstFrom;
    }

    public void setFirstFrom(int firstFrom) {
        this.firstFrom = firstFrom;
    }
}
