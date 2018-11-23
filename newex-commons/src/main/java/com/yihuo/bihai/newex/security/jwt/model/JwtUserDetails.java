package com.yihuo.bihai.newex.security.jwt.model;

import java.util.Date;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:44
 */
public class JwtUserDetails {
    private String sid;
    private long userId;
    private String username;
    private String email;
    private String phone;
    private int status;
    private Date created;
    private Date lastPasswordResetDate;
    private long pastDue;
    private long masterAccountId;

    public static JwtUserDetails.JwtUserDetailsBuilder builder() {
        return new JwtUserDetails.JwtUserDetailsBuilder();
    }

    public String getSid() {
        return this.sid;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getStatus() {
        return this.status;
    }

    public Date getCreated() {
        return this.created;
    }

    public Date getLastPasswordResetDate() {
        return this.lastPasswordResetDate;
    }

    public long getPastDue() {
        return this.pastDue;
    }

    public long getMasterAccountId() {
        return this.masterAccountId;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public void setPastDue(long pastDue) {
        this.pastDue = pastDue;
    }

    public void setMasterAccountId(long masterAccountId) {
        this.masterAccountId = masterAccountId;
    }



    public JwtUserDetails() {
    }

    public JwtUserDetails(String sid, long userId, String username, String email, String phone, int status, Date created, Date lastPasswordResetDate, long pastDue, long masterAccountId) {
        this.sid = sid;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.created = created;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.pastDue = pastDue;
        this.masterAccountId = masterAccountId;
    }

    public static class JwtUserDetailsBuilder {
        private String sid;
        private long userId;
        private String username;
        private String email;
        private String phone;
        private int status;
        private Date created;
        private Date lastPasswordResetDate;
        private long pastDue;
        private long masterAccountId;

        JwtUserDetailsBuilder() {
        }

        public JwtUserDetails.JwtUserDetailsBuilder sid(String sid) {
            this.sid = sid;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder email(String email) {
            this.email = email;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder created(Date created) {
            this.created = created;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder lastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder pastDue(long pastDue) {
            this.pastDue = pastDue;
            return this;
        }

        public JwtUserDetails.JwtUserDetailsBuilder masterAccountId(long masterAccountId) {
            this.masterAccountId = masterAccountId;
            return this;
        }

        public JwtUserDetails build() {
            return new JwtUserDetails(this.sid, this.userId, this.username, this.email, this.phone, this.status, this.created, this.lastPasswordResetDate, this.pastDue, this.masterAccountId);
        }
    }
}
