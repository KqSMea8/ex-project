package com.yihuo.bihai.newex.security.jwt.model;

import io.jsonwebtoken.Claims;
import java.util.Date;
import org.apache.commons.lang3.math.NumberUtils;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:47
 */
public class JwtPublicClaims {
    public static final String USER_ID = "uid";
    public static final String EMAIL = "eml";
    public static final String PHONE = "pho";
    public static final String STATUS = "sta";
    public static final String LAST_PASSWORD_RESET_DATE = "pwr";
    public static final String MASTER_ACCOUNT_ID = "mid";
    private final Claims claims;

    public JwtPublicClaims(Claims claims) {
        this.claims = claims;
    }

    public Claims getClaims() {
        return this.claims;
    }

    public JwtPublicClaims setId(String id) {
        this.claims.setId(id);
        return this;
    }

    public String getId() {
        return this.claims.getId();
    }

    public JwtPublicClaims setExpiration(Date date) {
        this.claims.setExpiration(date);
        return this;
    }

    public Date getExpiration() {
        return this.claims.getExpiration();
    }

    public JwtPublicClaims setIssuedAt(Date date) {
        this.claims.setIssuedAt(date);
        return this;
    }

    public Date getIssuedAt() {
        return this.claims.getIssuedAt();
    }

    public JwtPublicClaims setIssuer(String issuer) {
        this.claims.setIssuer(issuer);
        return this;
    }

    public String getIssuer() {
        return this.claims.getIssuer();
    }

    public String getUserId() {
        return (String)this.claims.get("uid", String.class);
    }

    public JwtPublicClaims setUserId(String userId) {
        this.claims.put("uid", userId);
        return this;
    }

    public String getUsername() {
        return this.claims.getSubject();
    }

    public JwtPublicClaims setUsername(String username) {
        this.claims.setSubject(username);
        return this;
    }

    public String getEmail() {
        return (String)this.claims.get("eml", String.class);
    }

    public JwtPublicClaims setEmail(String email) {
        this.claims.put("eml", email);
        return this;
    }

    public String getPhone() {
        return (String)this.claims.get("pho", String.class);
    }

    public JwtPublicClaims setPhone(String phone) {
        this.claims.put("pho", phone);
        return this;
    }

    public int getStatus() {
        return NumberUtils.toInt(String.valueOf(this.claims.get("sta")), 0);
    }

    public JwtPublicClaims setStatus(int status) {
        this.claims.put("sta", status);
        return this;
    }

    public int getMasterAccountId() {
        return NumberUtils.toInt(String.valueOf(this.claims.get("mid")), -1);
    }

    public JwtPublicClaims setMasterAccountId(long masterAccountId) {
        this.claims.put("mid", masterAccountId);
        return this;
    }

    public Date getLastPasswordResetDate() {
        return (Date)this.claims.get("pwr", Date.class);
    }

    public JwtPublicClaims setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.claims.put("pwr", lastPasswordResetDate);
        return this;
    }
}
