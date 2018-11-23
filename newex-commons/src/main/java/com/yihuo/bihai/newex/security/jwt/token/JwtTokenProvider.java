package com.yihuo.bihai.newex.security.jwt.token;

import com.yihuo.bihai.newex.security.jwt.crypto.JwtTokenCryptoProvider;
import com.yihuo.bihai.newex.security.jwt.model.JwtConfig;
import com.yihuo.bihai.newex.security.jwt.model.JwtPublicClaims;
import com.yihuo.bihai.newex.security.jwt.model.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author <a href="mailto:dragonjackielee@163.com">������</a>
 * @since 2018/10/30 ����10:27
 */
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final JwtConfig jwtConfig;
    private final JwtTokenCryptoProvider cryptoProvider;

    public JwtTokenProvider(JwtConfig jwtConfig, JwtTokenCryptoProvider cryptoProvider) {
        this.jwtConfig = jwtConfig;
        this.cryptoProvider = cryptoProvider;
    }

    public JwtConfig getJwtConfig() {
        return this.jwtConfig;
    }

    public JwtTokenCryptoProvider getCryptoProvider() {
        return this.cryptoProvider;
    }

    public long getUserId(String token) {
        JwtUserDetails jwtUserDetails = this.getJwtUserDetails(token);
        return jwtUserDetails.getUserId();
    }

    public String getUsername(String token) {
        JwtUserDetails jwtUserDetails = this.getJwtUserDetails(token);
        return jwtUserDetails.getUsername();
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        JwtPublicClaims publicClaims = this.parseClaims(token);
        if (this.isExpiredToken(publicClaims.getExpiration())) {
            log.debug("token is expired");
            return JwtUserDetails.builder().status(-1).build();
        } else {
            return this.getJwtUserDetails(this.parseClaims(token));
        }
    }

    public JwtUserDetails getJwtUserDetails(JwtPublicClaims publicClaims) {
        long userId = NumberUtils.toLong(this.cryptoProvider.decrypt(publicClaims.getUserId(), this.jwtConfig.getCryptoKey()), 0L);
        return JwtUserDetails.builder().sid(publicClaims.getId()).userId(userId).username(publicClaims.getUsername()).email(publicClaims.getEmail()).phone(publicClaims.getPhone()).created(publicClaims.getClaims().getIssuedAt()).lastPasswordResetDate(publicClaims.getLastPasswordResetDate()).status(publicClaims.getStatus()).masterAccountId((long)publicClaims.getMasterAccountId()).build();
    }

    public <T> T getClaims(JwtPublicClaims claims, Function<JwtPublicClaims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    public JwtPublicClaims parseClaims(String token) {
        Claims claims = (Claims)Jwts.parser().setSigningKey(this.jwtConfig.getSecret()).parseClaimsJws(token).getBody();
        return new JwtPublicClaims(claims);
    }

    public String generateToken(JwtUserDetails jwtUserDetails) {
        String encryptUserId = this.cryptoProvider.encrypt(String.valueOf(jwtUserDetails.getUserId()), this.jwtConfig.getCryptoKey());
        Date createdDate = jwtUserDetails.getCreated();
        Date expirationDate = this.getExpirationDate(createdDate);
        log.debug("doGenerateToken createdDate: {}", createdDate);
        JwtPublicClaims publicClaims = new JwtPublicClaims(new DefaultClaims());
        publicClaims.setId((String)StringUtils.defaultIfBlank(jwtUserDetails.getSid(), UUID.randomUUID().toString())).setUserId(encryptUserId).setUsername(jwtUserDetails.getUsername()).setEmail(jwtUserDetails.getEmail()).setPhone(jwtUserDetails.getPhone()).setStatus(jwtUserDetails.getStatus()).setLastPasswordResetDate(jwtUserDetails.getLastPasswordResetDate()).setMasterAccountId(jwtUserDetails.getMasterAccountId()).setIssuedAt(createdDate).setExpiration(expirationDate).setIssuer(this.jwtConfig.getIssuer());
        return Jwts.builder().setClaims(publicClaims.getClaims()).signWith(SignatureAlgorithm.HS512, this.jwtConfig.getSecret()).compact();
    }

    public String refreshToken(String token) {
        Date createdDate = JwtTokenTimeProvider.now();
        Date expirationDate = this.getExpirationDate(createdDate);
        Claims claims = this.parseClaims(token).getClaims();
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.jwtConfig.getSecret()).compact();
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        Date created = jwtPublicClaims.getIssuedAt();
        Date expired = jwtPublicClaims.getExpiration();
        return this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset) || this.isExpiredToken(expired);
    }

    public boolean validateToken(String token, JwtUserDetails user) {
        JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        String username = jwtPublicClaims.getUsername();
        Date created = jwtPublicClaims.getIssuedAt();
        Date expired = jwtPublicClaims.getExpiration();
        return username.equals(user.getUsername()) && !this.isExpiredToken(expired) && !this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }

    public boolean isExpiredToken(Date expired) {
        return expired.before(JwtTokenTimeProvider.now());
    }

    public Date getExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + this.jwtConfig.getExpiration() * 1000L);
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }
}
